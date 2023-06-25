package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.security.jwt.JwtProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.util.UUID;

@Component
public class BasketRestClient extends RestClient{

    private static final String HOST = "http://localhost:8083";
    private static final String BASKETS_API_BASE_PATH = HOST + "/baskets/api/v1";
    private static final String BASKETS_API_BASKETS_BASE_PATH = BASKETS_API_BASE_PATH + "/users/%s/basket";
    private static final String BASKETS_API_BASKETS_BASKET_ITEM_PATH = BASKETS_API_BASKETS_BASE_PATH + "/items/%s";

    private static final TypeReference<BasketDTO> BASKET_DTO_TYPE = new TypeReference<>() {};

    public BasketRestClient(@Qualifier("jwtProvider") JwtProvider jwtProvider) {
        super(jwtProvider);
    }
    public BasketDTO getUserBasket(String ownerId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(BASKETS_API_BASKETS_BASE_PATH, ownerId))
                .header("accept", "application/json")
                .header("Authorization", getBearerToken())
                .build();

        return get(request, response -> mapJsonToObject(response.body(), BASKET_DTO_TYPE));
    }

    public BasketDTO addBasketItem(String ownerId, UUID menuId) {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(createRequestBody(menuId))
                .uri(buildURI(BASKETS_API_BASKETS_BASKET_ITEM_PATH, ownerId, menuId.toString()))
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .header("Authorization", getBearerToken())
                .build();

        return put(request, response -> mapJsonToObject(response.body(), BASKET_DTO_TYPE));
    }

    public void deleteBasketItem(String ownerId, UUID menuId) {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(buildURI(BASKETS_API_BASKETS_BASKET_ITEM_PATH, ownerId, menuId.toString()))
                .header("accept", "application/json")
                .header("Authorization", getBearerToken())
                .build();

        delete(request, response -> null);
    }
}
