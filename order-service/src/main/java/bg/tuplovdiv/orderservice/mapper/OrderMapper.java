package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toDTO(OrderEntity order) {
        return new OrderDTO()
                .setOrderId(order.getExternalId())
                .setClientId(order.getClientId())
                .setClientPhoneNumber(order.getClientPhoneNumber())
                .setDeliveryDriverId(order.getDeliverDriverId())
                .setBasketId(order.getBasketId())
                .setTotalCost(order.getTotalCost())
                .setStatus(order.getStatus());
    }

    public OrderEntity toEntity(OrderDTO orderDTO) {
        return new OrderEntity()
                .setExternalId(orderDTO.getOrderId())
                .setClientId(orderDTO.getClientId())
                .setClientPhoneNumber(orderDTO.getClientPhoneNumber())
                .setDeliverDriverId(orderDTO.getDeliveryDriverId())
                .setAddress(orderDTO.getAddress())
                .setBasketId(orderDTO.getBasketId())
                .setTotalCost(orderDTO.getTotalCost())
                .setStatus(orderDTO.getStatus());
    }
}
