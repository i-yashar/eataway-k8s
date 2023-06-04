package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {

    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;

    public HomeController(AuthenticatedUserProviderFactory authenticatedUserProviderFactory) {
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
    }

    @ModelAttribute
    public void addUserAttribute(Model model) {
        AuthenticatedUserProvider userProvider = authenticatedUserProviderFactory.getProvider();
        model.addAttribute("user", userProvider.provide());
    }

    @GetMapping("/home")
    public String getHome(Model model, Principal principal) {
        return "home-page";
    }

}
