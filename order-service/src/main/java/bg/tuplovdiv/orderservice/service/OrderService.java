package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.OrderRequest;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;

import java.util.UUID;

public interface OrderService {
    OrderRequest findOrderByOrderId(UUID orderId);
    PageDTO<OrderRequest> findAllUserOrders(UUID userId, int page, int size);
    UUID createOrder(OrderRequest orderDTO);
}
