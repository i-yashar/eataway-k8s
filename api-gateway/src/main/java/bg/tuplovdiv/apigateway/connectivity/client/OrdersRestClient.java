package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.BasketItemDTO;
import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.dto.page.PageDTO;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.util.UUID;

@Component
public class OrdersRestClient extends RestClient {

    private static final String HOST = "http://localhost:8081";
    private static final String ORDERS_API_BASE_PATH = HOST + "/orders/api/v1";
    private static final String ORDERS_API_BASKETS_BASE_PATH = ORDERS_API_BASE_PATH + "/users/%s/basket";
    private static final String ORDERS_API_BASKETS_BASKET_ITEM_PATH = ORDERS_API_BASKETS_BASE_PATH + "/items/%s";
    private static final String ORDERS_API_GET_USER_ORDER = ORDERS_API_BASE_PATH + "/orders/%s";
    private static final String ORDERS_API_CREATE_ORDER_PATH = ORDERS_API_BASE_PATH + "/orders";
    private static final String ORDERS_API_GET_USER_ORDERS = ORDERS_API_BASE_PATH + "/users/%s/orders";

    private static final String AUTH_USER_HEADER = "AUTH_USER";

    private static final TypeReference<BasketDTO> BASKET_DTO_TYPE = new TypeReference<>() {};
    private static final TypeReference<OrderDTO> ORDER_DTO_TYPE = new TypeReference<>() {};
    private static final TypeReference<PageDTO<OrderDTO>> PAGE_OF_ORDER_DTOS_PATH = new TypeReference<>() {};

    private final AuthenticatedUserProvider userProvider;

    public OrdersRestClient(AuthenticatedUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    public OrderDTO getUserOrder(UUID orderId) {
        //todo: decide where to get the authenticated user from (controller or directly from rest client class)
        AuthenticatedUser user = userProvider.provide();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_GET_USER_ORDER, orderId.toString()))
                .header("accept", "application/json")
                .header(AUTH_USER_HEADER, user.getUserId())
                .build();

        return get(request, response -> mapJsonToObject(response.body(), ORDER_DTO_TYPE));
    }

    public PageDTO<OrderDTO> getUserOrders(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_GET_USER_ORDERS, userId))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), PAGE_OF_ORDER_DTOS_PATH));
    }

    public OrderDTO takeOrder(UUID orderId) {
        return new OrderDTO();
    }

    public String createOrder(CreateOrderRequest createOrderRequest) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(createRequestBody(createOrderRequest))
                .uri(buildURI(ORDERS_API_CREATE_ORDER_PATH))
                .header("Content-Type", "application/json")
                .build();

        return post(request, this::extractLocation);
    }

    public BasketDTO getUserBasket(String ownerId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_BASKETS_BASE_PATH, ownerId))
                .header("accept", "application/json")
                .build();

        return get(request, response -> mapJsonToObject(response.body(), BASKET_DTO_TYPE));
    }

    public BasketDTO addBasketItem(String ownerId, BasketItemDTO basketItem) {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(createRequestBody(basketItem))
                .uri(buildURI(ORDERS_API_BASKETS_BASE_PATH, ownerId))
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .build();

        return put(request, response -> mapJsonToObject(response.body(), BASKET_DTO_TYPE));
    }

    public Void deleteBasketItem(String ownerId, UUID menuId) {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(buildURI(ORDERS_API_BASKETS_BASKET_ITEM_PATH, ownerId, menuId.toString()))
                .header("accept", "application/json")
                .build();

        return delete(request, response -> null);
    }
}
