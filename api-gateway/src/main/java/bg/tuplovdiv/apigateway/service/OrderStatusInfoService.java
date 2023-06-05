package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.OrderStatusInfoDTO;

import java.util.Collection;
import java.util.UUID;

public interface OrderStatusInfoService {
    Collection<OrderStatusInfoDTO> getOrderStatusInfoMessages(UUID orderId);
}
