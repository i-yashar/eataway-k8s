package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.dto.RestaurantDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class RestaurantsRestClient extends RestClient {

    private static final String RESTAURANTS_API_BASE_PATH = "/restaurants/api/v1";
    private static final String RESTAURANTS_API_GET_ALL_PATH = RESTAURANTS_API_BASE_PATH + "/restaurants";

    private static final TypeReference<List<RestaurantDTO>> LIST_OF_RESTAURANTS_TYPE = new TypeReference<>() {};
    private static final TypeReference<RestaurantDTO> RESTAURANT_TYPE = new TypeReference<RestaurantDTO>() {};

    public Collection<RestaurantDTO> getRestaurants(int page, int size) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(RESTAURANTS_API_GET_ALL_PATH))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), LIST_OF_RESTAURANTS_TYPE));
    }

    public RestaurantDTO getRestaurant(UUID restaurantId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(RESTAURANTS_API_BASE_PATH + restaurantId.toString()))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), RESTAURANT_TYPE));
    }
}
