package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.impl.EatawayUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final AuthenticatedUserProvider userProvider;

    public HomeController(AuthenticatedUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @GetMapping("/home")
    public String getHome(Model model, Principal principal) {

        if(principal != null) {
            EatawayUser user = (EatawayUser) userProvider.provide();
            model.addAttribute("user", user);
        }

        return "home-page";
    }

}
