package bg.tuplovdiv.apigateway.connectivity.client;

import bg.tuplovdiv.apigateway.dto.*;
import bg.tuplovdiv.apigateway.dto.page.PageDTO;
import bg.tuplovdiv.apigateway.security.jwt.JwtProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.UUID;

@Component
public class OrdersRestClient extends RestClient {

    private static final String HOST = "http://localhost:8081";
    private static final String ORDERS_API_BASE_PATH = HOST + "/orders/api/v1";
    private static final String ORDERS_API_GET_USER_ORDER = ORDERS_API_BASE_PATH + "/orders/%s";
    private static final String ORDERS_API_GET_USER_ORDERS = ORDERS_API_BASE_PATH + "/users/%s/orders";
    private static final String ORDERS_API_GET_DELIVERY_DRIVER_ORDERS = ORDERS_API_BASE_PATH + "/delivery/drivers/%s/orders";
    private static final String ORDERS_API_GET_ORDER_STATUS_INFO = ORDERS_API_GET_USER_ORDER + "/info";

    private static final TypeReference<OrderDTO> ORDER_DTO_TYPE = new TypeReference<>() {};
    private static final TypeReference<PageDTO<OrderDTO>> PAGE_OF_ORDER_DTOS_TYPE = new TypeReference<>() {};
    private static final TypeReference<Collection<OrderDTO>> ORDER_DTOS_TYPE = new TypeReference<>() {};
    private static final TypeReference<Collection<OrderStatusInfoDTO>> ORDER_STATUS_INFO_DTOS_TYPE = new TypeReference<>() {};

    public OrdersRestClient(@Qualifier("jwtProvider") JwtProvider jwtProvider) {
        super(jwtProvider);
    }

    public OrderDTO getUserOrder(UUID orderId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_GET_USER_ORDER, orderId.toString()))
                .header("accept", "application/json")
                .header("Authorization", getBearerToken())
                .build();

        return get(request, response -> mapJsonToObject(response.body(), ORDER_DTO_TYPE));
    }

    public PageDTO<OrderDTO> getUserActiveOrders(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_GET_USER_ORDERS, userId))
                .header("accept", "application/json")
                .header("Authorization", getBearerToken())
                .build();

        return get(request, response -> mapJsonToObject(response.body(), PAGE_OF_ORDER_DTOS_TYPE));
    }

    public Collection<OrderDTO> getDeliveryDriverActiveOrders(String driverId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_GET_DELIVERY_DRIVER_ORDERS, driverId))
                .header("accept", "application/json")
                .header("Authorization", getBearerToken())
                .build();

        return get(request, response -> mapJsonToObject(response.body(), ORDER_DTOS_TYPE));
    }

    public Collection<OrderStatusInfoDTO> getOrderStatusInfoMessages(UUID orderId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURI(ORDERS_API_GET_ORDER_STATUS_INFO, orderId.toString()))
                .header("Content-Type", "application/json")
                .header("Authorization", getBearerToken())
                .build();

        return get(request, response -> mapJsonToObject(response.body(), ORDER_STATUS_INFO_DTOS_TYPE));
    }
}
