package bg.tuplovdiv.restaurants.rest;

import bg.tuplovdiv.restaurants.dto.RestaurantDTO;
import bg.tuplovdiv.restaurants.dto.page.PageDTO;
import bg.tuplovdiv.restaurants.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<PageDTO<RestaurantDTO>> getRestaurants(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok(restaurantService.findAllRestaurants(page, size));
    }

    @PostMapping(RESTAURANTS_PATH)
    public ResponseEntity<Void> postRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO restaurant = restaurantService.saveRestaurant(restaurantDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{restaurantId}")
                .buildAndExpand(restaurant.getRestaurantId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
