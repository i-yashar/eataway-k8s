package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.validation.AdminValidator;
import bg.tuplovdiv.apigateway.service.RestaurantService;
import bg.tuplovdiv.apigateway.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("admin/eataway")
public class AdminController {

    private static final String USERS_PATH = "/users";
    private static final String REGISTER_PATH = USERS_PATH + "/{userId}/register";
    private static final String DEREGISTER_PATH = USERS_PATH + "/{userId}/deregister";
    private static final String RESTAURANTS_PATH = "/registration/restaurants";

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final AdminValidator adminValidator;

    public AdminController(UserService userService, RestaurantService restaurantService, AdminValidator adminValidator) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.adminValidator = adminValidator;
    }

    @GetMapping(USERS_PATH)
    @PreAuthorize("@adminValidator.isAdmin()")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "all-users";
    }

    @GetMapping(REGISTER_PATH)
    @PreAuthorize("@adminValidator.isAdmin() && @adminValidator.isEligibleForRegistration(#userId)")
    public String registerAsEmployee(@PathVariable String userId, @RequestParam UUID restaurantId) {
        userService.registerUserAsEmployee(userId, restaurantId);

        return "redirect:/admin/eataway/users";
    }

    @GetMapping(RESTAURANTS_PATH)
    @PreAuthorize("@adminValidator.isEligibleForRegistration(#userId)")
    public String getRestaurantsForUserRegistration(@RequestParam String userId, Model model) {
        model.addAttribute("restaurants", restaurantService.findAllRestaurants(0, 50));
        model.addAttribute("userId", userId);

        return "restaurants-user-registration";
    }

    @GetMapping(DEREGISTER_PATH)
    @PreAuthorize("@adminValidator.isAdmin()")
    public String deregisterAsEmployee(@PathVariable String userId) {
        userService.deregisterUserAsEmployee(userId);

        return "redirect:/admin/eataway/users";
    }
}
