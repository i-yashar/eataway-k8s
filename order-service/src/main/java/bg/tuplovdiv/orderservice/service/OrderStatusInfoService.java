package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderStatusInfoDTO;

import java.util.Collection;
import java.util.UUID;

public interface OrderStatusInfoService {

    void saveOrderStatusInfo(OrderDTO order);
    Collection<OrderStatusInfoDTO> getOrderStatusInfoMessages(UUID orderId);
}
