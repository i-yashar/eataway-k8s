package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;

import java.util.Collection;
import java.util.UUID;

public interface OrderService {
    OrderDTO findOrderByOrderId(UUID orderId);
    PageDTO<OrderDTO> findActiveUserOrders(String clientId, int page, int size);
    UUID createOrder(CreateOrderRequestDTO orderRequest);
    OrderDTO updateOrder(OrderDTO order);
    Collection<OrderDTO> getActiveDeliveryDriverOrders(String driverId);
}
