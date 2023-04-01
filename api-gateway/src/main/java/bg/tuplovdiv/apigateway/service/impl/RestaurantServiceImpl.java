package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.RestaurantsRestClient;
import bg.tuplovdiv.apigateway.dto.RestaurantDTO;
import bg.tuplovdiv.apigateway.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantsRestClient client;

    public RestaurantServiceImpl(RestaurantsRestClient client) {
        this.client = client;
    }

    @Override
    public RestaurantDTO findRestaurantByRestaurantId(UUID restaurantId) {
        return client.getRestaurant(restaurantId);
    }

    @Override
    public Collection<RestaurantDTO> findAllRestaurants(int page, int size) {
        return client.getRestaurants(page, size).getContent();
    }
}
