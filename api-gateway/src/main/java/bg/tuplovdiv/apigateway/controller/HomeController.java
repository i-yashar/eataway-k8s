package bg.tuplovdiv.apigateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHome(Model model, Principal principal) {
        if(principal != null) {
            model.addAttribute("name", principal.getName());
        }

        return "home-page";
    }
}
