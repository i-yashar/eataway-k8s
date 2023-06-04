package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.security.authentication.impl.EatawayUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;

    public HomeController(AuthenticatedUserProviderFactory authenticatedUserProviderFactory) {
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
    }


    @GetMapping("/home")
    public String getHome(Model model, Principal principal) {

        if(principal != null) {
            EatawayUser user = (EatawayUser) authenticatedUserProviderFactory.getProvider().provide();
            model.addAttribute("user", user);
        }

        return "home-page";
    }

}
