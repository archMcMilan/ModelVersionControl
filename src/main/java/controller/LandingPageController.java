package controller;

import domain.Document;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repository.CompanyRepository;
import repository.DocumentRepository;
import repository.ProjectRepository;
import repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LandingPageController {
    private Utils utils=new Utils();

    @Autowired
    UserRepository userRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(value = "/")
    public String login(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getSession().getAttribute(Constants.USER)!=null){
            String redirectPage = resolveMainPageElements(httpServletRequest);
            return redirectPage;
        }
        return "login";
    }

    @PostMapping(value = "/signIn")
    public String singIn(HttpServletRequest httpServletRequest) {
        String login = httpServletRequest.getParameter(Constants.LOGIN);
        String password = httpServletRequest.getParameter(Constants.PASSWORD);
        User user = userRepository.findByLogin(login);
        System.out.println("USER="+user);
        System.out.println("login and pass="+login+" "+password);
        if (user == null || !user.getPassword().equals(password)) {
            return "login";

        }
        httpServletRequest.getSession().setAttribute(Constants.USER, user);
        String redirectPage = resolveMainPageElements(httpServletRequest);
        return redirectPage;
    }

    @GetMapping(value = "signOut")
    public String signOut(){
        return "login";
    }

    private String resolveMainPageElements(HttpServletRequest httpServletRequest) {
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
        return "login";
    }
}
