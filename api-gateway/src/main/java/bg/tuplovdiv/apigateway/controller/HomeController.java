package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.AuthorizationUser;
import bg.tuplovdiv.apigateway.security.AuthorizationUserProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final AuthorizationUserProvider userProvider;

    public HomeController(AuthorizationUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @GetMapping("/home")
    public String getHome(Model model, Principal principal) {

        if(principal != null) {
            AuthorizationUser user = userProvider.provide();
            model.addAttribute("user", user.getName());
        }

        return "home-page";
    }

}
