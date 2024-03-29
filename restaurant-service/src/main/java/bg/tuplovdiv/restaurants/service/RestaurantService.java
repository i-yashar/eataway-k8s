package bg.tuplovdiv.restaurants.service;

import bg.tuplovdiv.restaurants.dto.RestaurantDTO;
import bg.tuplovdiv.restaurants.dto.page.PageDTO;

import java.util.UUID;

public interface RestaurantService {
    RestaurantDTO findRestaurantByRestaurantId(UUID restaurantId);
    PageDTO<RestaurantDTO> findAllRestaurants(int page, int size);
    RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO);
}
