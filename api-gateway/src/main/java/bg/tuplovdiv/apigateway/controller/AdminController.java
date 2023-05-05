package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.validation.AdminValidator;
import bg.tuplovdiv.apigateway.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/eataway")
public class AdminController {

    private static final String USERS_PATH = "/users";

    private final UserService userService;
    private final AdminValidator adminValidator;

    public AdminController(UserService userService, AdminValidator adminValidator) {
        this.userService = userService;
        this.adminValidator = adminValidator;
    }

    @GetMapping(USERS_PATH)
    @PreAuthorize("@adminValidator.isAdmin()")
    public String getUsers() {

    }
}
