package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.cache.BasketCache;
import bg.tuplovdiv.apigateway.cache.OrderCache;
import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.dto.OrderStatusInfoDTO;
import bg.tuplovdiv.apigateway.messaging.OrderDispatcher;
import bg.tuplovdiv.apigateway.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
class OrderServiceImpl implements OrderService {

    private static final String ACTIVE_STATUS_MESSAGE = "Your order was taken by one of our employees";
    private static final String ABOUT_TO_BE_DELIVERED_STATUS_MESSAGE = "Your order will be delivered in a few minutes";
    private static final String DELIVERED_STATUS_MESSAGE = "Your order was delivered. Enjoy your food!";

    private final OrderDispatcher orderDispatcher;
    private final OrderCache orderCache;
    private final OrdersRestClient client;
    private final BasketCache basketCache;

    OrderServiceImpl(OrderDispatcher orderDispatcher, OrderCache orderCache, OrdersRestClient client, BasketCache basketCache) {
        this.orderDispatcher = orderDispatcher;
        this.orderCache = orderCache;
        this.client = client;
        this.basketCache = basketCache;
    }

    @Override
    public UUID createOrder(CreateOrderRequestDTO createOrderRequest) {
        OrderDTO order = createOrderDTO(createOrderRequest);

        orderCache.updateOrder(order);
        orderDispatcher.sendOrderCreateRequest(createOrderRequest);

        return order.getOrderId();
    }

    private OrderDTO createOrderDTO(CreateOrderRequestDTO createOrderRequest) {
        BasketDTO basket = basketCache.getUserBasket(createOrderRequest.getClientId());
        return new OrderDTO()
                .setOrderId(UUID.randomUUID())
                .setClientId(createOrderRequest.getClientId())
                .setClientPhoneNumber(createOrderRequest.getClientPhoneNumber())
                .setAddress(createOrderRequest.getAddress())
                .setItems(basket.getItems())
                .setTotalCost(basket.getTotalCost())
                .setStatus("REGISTERED");
    }

    @Override
    public OrderDTO getOrderInfo(UUID orderId) {
        return orderCache.getOrder(orderId);
    }

    @Override
    public Collection<OrderDTO> getUserActiveOrders(String userId) {
        return client.getUserActiveOrders(userId).getContent();
    }

    @Override
    public Collection<OrderStatusInfoDTO> getOrderStatusInfoMessages(UUID orderId) {
        Collection<OrderStatusInfoDTO> statusInfoMessages =
                client.getOrderStatusInfoMessages(orderId);

        addReadableStatusInfoMessages(statusInfoMessages);

        return statusInfoMessages;
    }

    private void addReadableStatusInfoMessages(Collection<OrderStatusInfoDTO> statusInfoMessages) {
        statusInfoMessages.forEach(m -> {
            String message = switch (m.getInfoMessage()) {
                case "ACTIVE" -> ACTIVE_STATUS_MESSAGE;
                case "ABOUT_TO_BE_DELIVERED" -> ABOUT_TO_BE_DELIVERED_STATUS_MESSAGE;
                case "DELIVERED" -> DELIVERED_STATUS_MESSAGE;
                default -> "";
            };
            m.setInfoMessage(message);
        });
    }
}