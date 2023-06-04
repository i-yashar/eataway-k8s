package bg.tuplovdiv.apigateway.messaging;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.apigateway.dto.OrderStatusChangeDTO;

public interface OrderDispatcher {
    void sendOrderCreateRequest(CreateOrderRequestDTO request);
    void sendOrderStatusUpdate(OrderStatusChangeDTO statusChange);
}
