package controller;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import repository.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Pryzhkov
 *         15.04.17.
 */
@Controller
public class MainPageController {
    private List<Document> documents = new ArrayList<>();

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

    @GetMapping(value = "/filter")
    public String filterDocuments(HttpServletRequest httpServletRequest) {
        documents = (List<Document>) httpServletRequest.getSession().getAttribute(Constants.DOCS);
        String fieldBy = httpServletRequest.getParameter(Constants.FIELD_BY);

        return resolveMainPageFilter(httpServletRequest, fieldBy);
    }

    @PostMapping(value = "/reset")
    public String reset(HttpServletRequest httpServletRequest) {
        return Utils.resetDocuments(projectRepository,documentRepository,companyRepository,httpServletRequest);
    }

    @PostMapping(value = "signOut")
    public String signOut(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().setAttribute(Constants.USER,null);
        return Constants.LOGIN;
    }

    /**
     * dev has ability to edit
     * @param model
     * @param httpServletRequest
     * @return
     */
    @PostMapping(value = "/edit")
    public String editDocument(Model model, HttpServletRequest httpServletRequest){
        model.addAttribute("isEdit",true);
        return Utils.resolvePage(httpServletRequest);
    }

    /**
     * dev function
     * @param file
     * @param httpServletRequest
     * @return
     */
    @PostMapping(value = "/addNewVersion")
    public String addNewVersion(MultipartFile file, HttpServletRequest httpServletRequest){
        System.out.println(file.getContentType());
        return Utils.resolvePage(httpServletRequest);
    }

    /**
     * head add new document
     * @param model
     * @param httpServletRequest
     * @return
     */
    @PostMapping(value = "/addNewDocumentBtn")
    public String addNewDocumentBtn(Model model, HttpServletRequest httpServletRequest){
        model.addAttribute(Constants.IS_ADDNEW_DOCUMENT,true);
        model.addAttribute(Constants.MARKS,markRepository.findAll());
        model.addAttribute(Constants.DEVELOPERS,companyRepository.findAllByRole(Role.DEVELOPER));
        model.addAttribute(Constants.EXECUTORS,companyRepository.findAllByRole(Role.EXECUTOR));
        model.addAttribute(Constants.BUILDINGS,buildingRepository.findAll());
        model.addAttribute(Constants.DOCUMENT_TYPES,documentTypeRepository.findAll());
        return Constants.MAIN_PAGE_HEAD;
    }

    @PostMapping(value = "/cancelNewDocumentBtn")
    public String cancelNewDocumentBtn(HttpServletRequest httpServletRequest){
        return Constants.MAIN_PAGE_HEAD;
    }

    @PostMapping(value = "/addNewDocument")
    public String addNewDocument(HttpServletRequest httpServletRequest){
        Long projectId = Long.valueOf(((Project) httpServletRequest.getSession().getAttribute(Constants.PROJECT_ID)).getId());
        Mark mark=markRepository.findByName(httpServletRequest.getParameter(Constants.DOC_MARK_FIELD));
        DocumentType documentType=documentTypeRepository.findByType(httpServletRequest.getParameter(Constants.DOC_TYPE_FIELD));
        Building building=null;
        if(httpServletRequest.getParameter(Constants.DOC_BUILDING_FIELD)!=null){
            building=buildingRepository.findByName(httpServletRequest.getParameter(Constants.DOC_BUILDING_FIELD));
        }else{
            building=buildingRepository.findByName(httpServletRequest.getParameter(Constants.DOC_NEW_BUILDING_FIELD));
        }
        Company developer=companyRepository.findCompanyByName(httpServletRequest.getParameter(Constants.DOC_DEV_FIELD));
        Company executor=companyRepository.findCompanyByName(httpServletRequest.getParameter(Constants.DOC_EXEC_FIELD));
        String name=httpServletRequest.getParameter(Constants.DOC_NAME_FIELD);
        Long code=httpServletRequest.getDateHeader(Constants.DOC_CODE_FIELD);
        Document newDoc=new Document(null,name,mark,building,projectRepository.findOne(projectId),documentType,code,1,null,
                LocalDateTime.now()+"",null,executor,developer,new ArrayList<>());
        System.out.println(newDoc);
        documentRepository.save(newDoc);
        return Constants.MAIN_PAGE_HEAD;
    }

    private String resolveMainPageFilter(HttpServletRequest httpServletRequest, String fieldBy) {
        User user = (User) httpServletRequest.getSession().getAttribute(Constants.USER);
        switch (user.getCompany().getRole()) {
            case HEAD: {
                Long projectId = Long.valueOf(((Project) httpServletRequest.getSession().getAttribute(Constants.PROJECT_ID)).getId());
                headFilter(httpServletRequest, fieldBy, projectId);
                return Constants.MAIN_PAGE_HEAD;
            }
            case DEVELOPER: {
                devFilter(httpServletRequest, fieldBy);
                return Constants.MAIN_PAGE_DEV;
            }
            case EXECUTOR: {
                execFilter(httpServletRequest,fieldBy);
                return Constants.MAIN_PAGE_EXEC;
            }
        }
        return Constants.LOGIN;
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

    private void execFilter(HttpServletRequest httpServletRequest, String fieldBy){
        switch (fieldBy) {
            case Constants.MARK: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByMarkAndExecutorIn(markRepository
                                        .findAllByNameIgnoreCaseContaining(httpServletRequest
                                                .getParameter(Constants.FILTER_VALUE)), companyRepository
                                        .findCompanyByName(((User) httpServletRequest
                                                .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
            case Constants.TYPE: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByDocumentTypeAndExecutorIn(documentTypeRepository
                                        .findByTypeIgnoreCaseContaining(
                                                httpServletRequest.getParameter(Constants.FILTER_VALUE)), companyRepository
                                        .findCompanyByName(((User) httpServletRequest
                                                .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
            case Constants.BUILDING: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByBuildingAndExecutorIn(buildingRepository
                                        .findAllByNameIgnoreCaseContaining(
                                                httpServletRequest.getParameter(Constants.FILTER_VALUE)), companyRepository
                                        .findCompanyByName(((User) httpServletRequest
                                                .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }

            case Constants.CODE: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByCodeAndExecutor(Long.valueOf(
                                        httpServletRequest.getParameter(Constants.FILTER_VALUE)), companyRepository
                                        .findCompanyByName(((User) httpServletRequest
                                                .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
            case Constants.NAME: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByNameIgnoreCaseContainingAndExecutor(httpServletRequest.getParameter(Constants.FILTER_VALUE),
                                        companyRepository
                                                .findCompanyByName(((User) httpServletRequest
                                                        .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
            case Constants.DEVELOPER: {
                httpServletRequest.getSession()
                        .setAttribute(Constants.DOCS, documentRepository
                                .findAllByExecutorAndExecutorIn(companyRepository.
                                                findAllByNameIgnoreCaseContaining(httpServletRequest
                                                        .getParameter(Constants.FILTER_VALUE)),
                                        companyRepository
                                                .findCompanyByName(((User) httpServletRequest
                                                        .getSession().getAttribute(Constants.USER)).getCompany().getName())));
                break;
            }
        }
    }
}
