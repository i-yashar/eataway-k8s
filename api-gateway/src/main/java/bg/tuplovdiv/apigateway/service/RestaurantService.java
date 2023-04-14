package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.MenuDTO;
import bg.tuplovdiv.apigateway.dto.RestaurantDTO;

import java.util.Collection;
import java.util.UUID;

public interface RestaurantService {
    RestaurantDTO findRestaurantByRestaurantId(UUID restaurantId);
    Collection<RestaurantDTO> findAllRestaurants(int page, int size);
    Collection<MenuDTO> findAllRestaurantMenus(UUID restaurantId);
}
