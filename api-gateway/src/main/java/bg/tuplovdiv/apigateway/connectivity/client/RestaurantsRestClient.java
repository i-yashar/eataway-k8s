package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.dto.MenuDTO;
import bg.tuplovdiv.apigateway.dto.RestaurantDTO;
import bg.tuplovdiv.apigateway.dto.page.PageDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.UUID;

@Component
public class RestaurantsRestClient extends RestClient {

    private static final String HOST = "http://localhost:8080";
    private static final String RESTAURANTS_API_BASE_PATH = HOST + "/restaurants/api/v1";
    private static final String RESTAURANTS_API_MENU_PATH = RESTAURANTS_API_BASE_PATH + "/menus/%s";
    private static final String RESTAURANTS_API_GET_ALL_PATH = RESTAURANTS_API_BASE_PATH + "/restaurants";
    private static final String RESTAURANTS_API_MENUS_GET_ALL_PATH = RESTAURANTS_API_GET_ALL_PATH + "/%s" + "/menus";

    private static final TypeReference<PageDTO<RestaurantDTO>> PAGE_OF_RESTAURANTS_TYPE = new TypeReference<>() {};
    private static final TypeReference<RestaurantDTO> RESTAURANT_TYPE = new TypeReference<>() {};
    private static final TypeReference<MenuDTO> MENU_DTO_TYPE = new TypeReference<>() {};
    private static final TypeReference<PageDTO<MenuDTO>> PAGE_OF_RESTAURANT_MENUS_TYPE = new TypeReference<>() {};

    public PageDTO<RestaurantDTO> getRestaurants(int page, int size) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(RESTAURANTS_API_GET_ALL_PATH + "?" + "page=" + page + "&" + "size=" + size))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), PAGE_OF_RESTAURANTS_TYPE));
    }

    public RestaurantDTO getRestaurant(UUID restaurantId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(RESTAURANTS_API_GET_ALL_PATH + "/" + restaurantId.toString()))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), RESTAURANT_TYPE));
    }

    public MenuDTO getMenuByMenuId(UUID menuId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(RESTAURANTS_API_MENU_PATH, menuId.toString()))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), MENU_DTO_TYPE));
    }

    public Collection<MenuDTO> getRestaurantMenus(UUID restaurantId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(RESTAURANTS_API_MENUS_GET_ALL_PATH, restaurantId.toString())))
                .header("accept", "application/json")
                .build();

        return get(request, this::extractRestaurantMenus);
    }

    private Collection<MenuDTO> extractRestaurantMenus(HttpResponse<String> response) {
        PageDTO<MenuDTO> page = mapJsonToObject(response.body(), PAGE_OF_RESTAURANT_MENUS_TYPE);
        return page.getContent();
    }
}
