package controller;

import domain.Document;
import domain.User;
import repository.CompanyRepository;
import repository.DocumentRepository;
import repository.ProjectRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Artem Pryzhkov
 *         19.04.17.
 */
public class Utils {

    public static String resetDocuments(ProjectRepository projectRepository,
                                        DocumentRepository documentRepository,
                                        CompanyRepository companyRepository,
                                        HttpServletRequest httpServletRequest){
        User user=(User)httpServletRequest.getSession().getAttribute(Constants.USER);
        switch (user.getCompany().getRole()) {
            case HEAD: {
                System.out.println(projectRepository.findProjectByHeadId(
                        user.getCompany().getId()));
                List<Document> documents = documentRepository.findAllByProjectId(
                        projectRepository.findProjectByHeadId(
                                user.getCompany().getId()).getId());
                httpServletRequest.getSession().setAttribute(Constants.DOCS, documents);

                httpServletRequest.getSession().setAttribute(Constants.PROJECT_ID,
                        projectRepository.findProjectByHeadId(
                                user.getCompany().getId()));

                return Constants.MAIN_PAGE_HEAD;
            }
            case DEVELOPER: {
                System.out.println(companyRepository
                        .findCompanyByName(user.getCompany().getName()));
                List<Document> documents = documentRepository
                        .findAllByDeveloper(companyRepository
                                .findCompanyByName(user.getCompany().getName()));
                httpServletRequest.getSession().setAttribute(Constants.DOCS, documents);

                return Constants.MAIN_PAGE_DEV;
            }
            case EXECUTOR: {
                System.out.println(companyRepository
                        .findCompanyByName(user.getCompany().getName()));
                List<Document> documents = documentRepository
                        .findAllByExecutor(companyRepository
                                .findCompanyByName(user.getCompany().getName()));
                httpServletRequest.getSession().setAttribute(Constants.DOCS, documents);

                return Constants.MAIN_PAGE_EXEC;
            }
        }
        return Constants.LOGIN;
    }

    public static String resolvePage(HttpServletRequest httpServletRequest){
        User user = (User) httpServletRequest.getSession().getAttribute(Constants.USER);
        switch (user.getCompany().getRole()) {
            case HEAD: {
                return Constants.MAIN_PAGE_HEAD;
            }
            case DEVELOPER: {
                return Constants.MAIN_PAGE_DEV;
            }
            case EXECUTOR: {
                return Constants.MAIN_PAGE_EXEC;
            }
        }
        return Constants.LOGIN;
    }
}
