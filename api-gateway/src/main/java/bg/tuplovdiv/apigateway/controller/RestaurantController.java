package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("eataway")
public class RestaurantController {

    private static final String RESTAURANTS_PATH = "restaurants";
    private static final String RESTAURANT_ID_PATH = "/{restaurantId}";

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
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

        return "restaurant";
    }
}
