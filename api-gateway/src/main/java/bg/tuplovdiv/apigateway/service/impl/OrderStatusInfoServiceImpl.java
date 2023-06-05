package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderStatusInfoDTO;
import bg.tuplovdiv.apigateway.service.OrderStatusInfoService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@Component
class OrderStatusInfoServiceImpl implements OrderStatusInfoService {

    private static final String ACTIVE_STATUS_MESSAGE = "Your order was taken by one of our employees";
    private static final String ABOUT_TO_BE_DELIVERED_STATUS_MESSAGE = "Your order will be delivered in a few minutes";
    private static final String DELIVERED_STATUS_MESSAGE = "Your order was delivered. Enjoy your food!";

    private final OrdersRestClient client;

    OrderStatusInfoServiceImpl(OrdersRestClient client) {
        this.client = client;
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
