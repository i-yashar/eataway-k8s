package bg.tuplovdiv.restaurants.service.impl;

import bg.tuplovdiv.restaurants.dto.RestaurantDTO;
import bg.tuplovdiv.restaurants.dto.page.PageDTO;
import bg.tuplovdiv.restaurants.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Override
    public RestaurantDTO findRestaurantByRestaurantId(UUID restaurantId) {
        return null;
    }

    @Override
    public PageDTO<RestaurantDTO> findAllRestaurants(int page, int size) {
        return null;
    }
}
