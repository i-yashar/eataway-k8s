package bg.tuplovdiv.orderservice.messaging;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderStatusChangeDTO;

public interface OrderDispatcher {
    void sendCreatedOrder(OrderDTO order);
    void sendChangedOrderStatus(OrderStatusChangeDTO orderStatusChange);
}
