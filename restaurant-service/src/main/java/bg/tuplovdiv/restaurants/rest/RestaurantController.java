package bg.tuplovdiv.restaurants.rest;

import bg.tuplovdiv.restaurants.dto.RestaurantDTO;
import bg.tuplovdiv.restaurants.dto.page.PageDTO;
import bg.tuplovdiv.restaurants.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurants/api/v1")
public class RestaurantController {
    private static final String RESTAURANTS_PATH = "/restaurants";
    private static final String RESTAURANT_ID = "/{restaurantId}";

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(RESTAURANTS_PATH + RESTAURANT_ID)
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable("restaurantId") UUID restaurantId) {
        return ResponseEntity.ok(restaurantService.findRestaurantByRestaurantId(restaurantId));
    }

    @GetMapping(RESTAURANTS_PATH)
    public ResponseEntity<PageDTO<RestaurantDTO>> getRestaurants(@RequestParam(name = "page", required = false) Integer page,
                                                                 @RequestParam(name = "size", required = false) Integer size) {
        return ResponseEntity.ok(restaurantService.findAllRestaurants(page, size));
    }
}
