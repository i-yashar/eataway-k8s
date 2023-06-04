package bg.tuplovdiv.apigateway.messaging;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.apigateway.dto.OrderDTO;

public interface OrderDispatcher {
    void sendOrderCreateRequest(CreateOrderRequestDTO request);
    void sendOrderUpdate(OrderDTO order);
}
