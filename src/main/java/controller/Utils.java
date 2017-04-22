package controller;

import domain.Document;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProjectRepository projectRepository;


    public void setDocumentsInSession(User user, HttpServletRequest httpServletRequest){
        List<Document> documents = documentRepository.findAllByProjectId(
                projectRepository.findProjectByHeadId(
                        user.getCompany().getId()).getId());
        httpServletRequest.getSession().setAttribute(Constants.DOCS, documents);
    }
}
