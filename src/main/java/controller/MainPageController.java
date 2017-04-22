package controller;

import domain.Document;
import domain.Project;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repository.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Pryzhkov
 *         15.04.17.
 */
@Controller
public class MainPageController {
    private List<Document> documents = new ArrayList<>();
    private Utils utils = new Utils();

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    MarkRepository markRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @GetMapping(value = "/add")
    public String addDocument() {
        return "main_page";
    }

    @GetMapping(value = "/filter")
    public String filterDocuments(HttpServletRequest httpServletRequest) {
        documents = (List<Document>) httpServletRequest.getSession().getAttribute(Constants.DOCS);
        Long projectId = Long.valueOf(((Project) httpServletRequest.getSession().getAttribute(Constants.PROJECT_ID)).getId());
        String fieldBy = httpServletRequest.getParameter(Constants.FIELD_BY);

        resolveMainPageFilter(httpServletRequest, fieldBy, projectId);
        return Constants.MAIN_PAGE_HEAD;
    }


    @PostMapping(value = "/reset")
    public String reset(HttpServletRequest httpServletRequest) {
        resolveMainPageReset(httpServletRequest);
        return Constants.MAIN_PAGE_HEAD;
    }

    private String resolveMainPageFilter(HttpServletRequest httpServletRequest, String fieldBy, Long projectId) {
        User user = (User) httpServletRequest.getSession().getAttribute(Constants.USER);
        switch (user.getCompany().getRole()) {
            case HEAD: {
                headFilter(httpServletRequest, fieldBy, projectId);
                return Constants.MAIN_PAGE_HEAD;
            }
            case DEVELOPER: {
                devFilter(httpServletRequest, fieldBy);
                return Constants.MAIN_PAGE_DEV;
            }
            case EXECUTOR: {

            }
        }
        return "login";
    }

    private void headFilter(HttpServletRequest httpServletRequest, String fieldBy, Long projectId) {
        switch (fieldBy) {
            case Constants.MARK: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByMarkAndProjectIdIn(markRepository
                                                .findAllByNameIgnoreCaseContaining(httpServletRequest
                                                        .getParameter(Constants.FILTER_VALUE)),
                                        projectId));
                break;
            }
            case Constants.TYPE: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByDocumentTypeAndProjectIdIn(documentTypeRepository
                                        .findByTypeIgnoreCaseContaining(
                                                httpServletRequest.getParameter(Constants.FILTER_VALUE)), projectId));
                break;
            }
            case Constants.BUILDING: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByBuildingAndProjectIdIn(buildingRepository
                                        .findAllByNameIgnoreCaseContaining(
                                                httpServletRequest.getParameter(Constants.FILTER_VALUE)), projectId));
                break;
            }

            case Constants.CODE: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByCodeAndProjectId(Long.valueOf(
                                        httpServletRequest.getParameter(Constants.FILTER_VALUE)), projectId));
                break;
            }
            case Constants.NAME: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByNameIgnoreCaseContainingAndProjectId(httpServletRequest.getParameter(Constants.FILTER_VALUE),
                                        projectId));
                break;
            }
            case Constants.EXECUTOR: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByExecutorAndProjectIdIn(companyRepository.
                                                findAllByNameIgnoreCaseContaining(httpServletRequest.getParameter(Constants.FILTER_VALUE)),
                                        projectId));
                break;
            }
            case Constants.DEVELOPER: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByDeveloperAndProjectIdIn(companyRepository.
                                                findAllByNameIgnoreCaseContaining(httpServletRequest.getParameter(Constants.FILTER_VALUE)),
                                        projectId));
                break;
            }
        }
    }

    private void devFilter(HttpServletRequest httpServletRequest, String fieldBy) {
        switch (fieldBy) {
            case Constants.MARK: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByMarkAndDeveloperIn(markRepository
                                        .findAllByNameIgnoreCaseContaining(httpServletRequest
                                                .getParameter(Constants.FILTER_VALUE)), companyRepository
                                        .findCompanyByName(((User) httpServletRequest
                                                .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
            case Constants.TYPE: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByDocumentTypeAndDeveloperIn(documentTypeRepository
                                        .findByTypeIgnoreCaseContaining(
                                                httpServletRequest.getParameter(Constants.FILTER_VALUE)), companyRepository
                                        .findCompanyByName(((User) httpServletRequest
                                                .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
            case Constants.BUILDING: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByBuildingAndDeveloperIn(buildingRepository
                                        .findAllByNameIgnoreCaseContaining(
                                                httpServletRequest.getParameter(Constants.FILTER_VALUE)), companyRepository
                                        .findCompanyByName(((User) httpServletRequest
                                                .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }

            case Constants.CODE: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByCodeAndDeveloper(Long.valueOf(
                                        httpServletRequest.getParameter(Constants.FILTER_VALUE)), companyRepository
                                        .findCompanyByName(((User) httpServletRequest
                                                .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
            case Constants.NAME: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByNameIgnoreCaseContainingAndDeveloper(httpServletRequest.getParameter(Constants.FILTER_VALUE),
                                        companyRepository
                                                .findCompanyByName(((User) httpServletRequest
                                                        .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
            case Constants.EXECUTOR: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByExecutorAndDeveloperIn(companyRepository.
                                        findAllByNameIgnoreCaseContaining(httpServletRequest
                                                .getParameter(Constants.FILTER_VALUE)),
                                        companyRepository
                                                .findCompanyByName(((User) httpServletRequest
                                                        .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
        }
    }

    private String resolveMainPageReset(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getSession().getAttribute(Constants.USER);
        switch (user.getCompany().getRole()) {
            case HEAD: {
                List<Document> documents = documentRepository.findAllByProjectId(
                        projectRepository.findProjectByHeadId(
                                user.getCompany().getId()).getId());
                httpServletRequest.getSession().setAttribute(Constants.DOCS, documents);
                return Constants.MAIN_PAGE_HEAD;
            }
            case DEVELOPER: {

            }
            case EXECUTOR: {

            }
        }
        return "login";
    }
}
