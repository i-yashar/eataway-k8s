package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.dto.RestaurantDTO;
import bg.tuplovdiv.apigateway.dto.page.PageDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.UUID;

@Component
public class RestaurantsRestClient extends RestClient {

    private static final String HOST = "http://localhost:8080";
    private static final String RESTAURANTS_API_BASE_PATH = "/restaurants/api/v1";
    private static final String RESTAURANTS_API_GET_ALL_PATH = RESTAURANTS_API_BASE_PATH + "/restaurants";

    private static final TypeReference<PageDTO<RestaurantDTO>> PAGE_OF_RESTAURANTS_TYPE = new TypeReference<>() {};
    private static final TypeReference<RestaurantDTO> RESTAURANT_TYPE = new TypeReference<>() {};

    public PageDTO<RestaurantDTO> getRestaurants(int page, int size) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(HOST + RESTAURANTS_API_GET_ALL_PATH))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), PAGE_OF_RESTAURANTS_TYPE));
    }

    public RestaurantDTO getRestaurant(UUID restaurantId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(HOST + RESTAURANTS_API_GET_ALL_PATH + "/" + restaurantId.toString()))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), RESTAURANT_TYPE));
    }
}
