package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.BasketItemDTO;
import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.util.UUID;

@Component
public class OrdersRestClient extends RestClient {

    private static final String HOST = "http://localhost:8081";
    private static final String ORDERS_API_BASE_PATH = HOST + "/orders/api/v1";
    private static final String ORDERS_API_BASKETS_BASE_PATH = ORDERS_API_BASE_PATH + "/users/%s/basket";
    private static final String ORDERS_API_BASKETS_BASKET_ITEM_PATH = ORDERS_API_BASKETS_BASE_PATH + "/items/%s";
    private static final String ORDERS_API_GET_USER_ORDER = ORDERS_API_BASE_PATH + "/%s";
    private static final String ORDERS_API_CREATE_ORDER_PATH = ORDERS_API_BASE_PATH + "/orders";
    private static final String ORDERS_API_GET_USER_ORDERS = ORDERS_API_BASE_PATH + "/users/%s/orders";

    private static final TypeReference<BasketDTO> BASKET_DTO_TYPE = new TypeReference<>() {};
    private static final TypeReference<OrderDTO> ORDER_DTO_TYPE = new TypeReference<>() {};
    private static final TypeReference<Page<OrderDTO>> PAGE_OF_ORDER_DTOS_PATH = new TypeReference<>() {};

    private final AuthenticatedUserProvider userProvider;

    public OrdersRestClient(AuthenticatedUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    public OrderDTO getUserOrder(UUID orderId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_GET_USER_ORDER, orderId.toString()))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), ORDER_DTO_TYPE));
    }

    public Page<OrderDTO> getUserOrders() {
        AuthenticatedUser user = userProvider.provide();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_GET_USER_ORDERS, user.getUserId()))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), PAGE_OF_ORDER_DTOS_PATH));
    }

    public String createOrder(CreateOrderRequest createOrderRequest) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(createRequestBody(createOrderRequest))
                .uri(buildURI(ORDERS_API_CREATE_ORDER_PATH))
                .header("Content-Type", "application/json")
                .build();

        return post(request, this::extractLocation);
    }

    public BasketDTO getUserBasket() {
        AuthenticatedUser user = userProvider.provide();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_BASKETS_BASE_PATH, user.getUserId()))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), BASKET_DTO_TYPE));
    }

    public BasketDTO addBasketItem(BasketItemDTO basketItem) {
        AuthenticatedUser user = userProvider.provide();

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(createRequestBody(basketItem))
                .uri(buildURI(ORDERS_API_BASKETS_BASE_PATH, user.getUserId()))
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .build();

        return put(request, response -> mapJsonToObject(response.body(), BASKET_DTO_TYPE));
    }

    public Void deleteBasketItem(UUID menuId) {
        AuthenticatedUser user = userProvider.provide();

        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(buildURI(ORDERS_API_BASKETS_BASKET_ITEM_PATH, user.getUserId(), menuId.toString()))
                .header("accept", "application/json")
                .build();

        return delete(request, response -> null);
    }
}
