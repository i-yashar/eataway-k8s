package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("eataway")
public class RestaurantController {

    private static final String RESTAURANTS_PATH = "restaurants";
    private static final String RESTAURANT_ID_PATH = "/{restaurantId}";

    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;
    private final RestaurantService restaurantService;

    public RestaurantController(AuthenticatedUserProviderFactory authenticatedUserProviderFactory, RestaurantService restaurantService) {
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
        this.restaurantService = restaurantService;
    }

    @ModelAttribute
    public void addUserAttribute(Model model) {
        AuthenticatedUserProvider userProvider = authenticatedUserProviderFactory.getProvider();
        model.addAttribute("user", userProvider.provide());
    }

    @GetMapping(RESTAURANTS_PATH)
    public String getAllRestaurants(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                    @RequestParam(name = "size", defaultValue = "5", required = false) int size,
                                    Model model) {
        model.addAttribute("restaurants", restaurantService.findAllRestaurants(page, size));

        return "restaurants";
    }

    @GetMapping(RESTAURANTS_PATH + RESTAURANT_ID_PATH)
    public String getRestaurant(@PathVariable UUID restaurantId, Model model) {
        model.addAttribute("restaurant", restaurantService.findRestaurantByRestaurantId(restaurantId));
        model.addAttribute("menus", restaurantService.findAllRestaurantMenus(restaurantId));

        return "restaurant";
    }
}
