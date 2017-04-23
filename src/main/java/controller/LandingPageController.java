package controller;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repository.CompanyRepository;
import repository.DocumentRepository;
import repository.ProjectRepository;
import repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LandingPageController {

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
          return Utils.resetDocuments(projectRepository,documentRepository,companyRepository,httpServletRequest);
        }
        return Constants.LOGIN;
    }

    @PostMapping(value = "/signIn")
    public String singIn(Model model, HttpServletRequest httpServletRequest) {
        String login = httpServletRequest.getParameter(Constants.LOGIN);
        String password = httpServletRequest.getParameter(Constants.PASSWORD);
        User user = userRepository.findByLogin(login);
        System.out.println("USER="+user);
        System.out.println("login and pass="+login+" "+password);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("isLogin",false);
            return Constants.LOGIN;
        }

        httpServletRequest.getSession().setAttribute(Constants.USER, user);
        return Utils.resetDocuments(projectRepository,documentRepository,companyRepository,httpServletRequest);
    }
}
