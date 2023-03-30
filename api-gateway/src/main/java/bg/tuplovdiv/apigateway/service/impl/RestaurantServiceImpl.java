package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.dto.RestaurantDTO;
import bg.tuplovdiv.apigateway.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Override
    public RestaurantDTO findRestaurantByRestaurantId(UUID restaurantId) {
        return null;
    }

    @Override
    public Collection<RestaurantDTO> findAllRestaurants(int page, int size) {
        return null;
    }
}
