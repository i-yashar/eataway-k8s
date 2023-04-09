package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.CreateOrderRequest;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toOrderDTO(OrderEntity order) {
        return new OrderDTO()
                .setOrderId(order.getExternalId())
                .setClientId(order.getClientId())
                .setClientPhoneNumber(order.getClientPhoneNumber())
                .setDeliveryDriverId(order.getDeliverDriverId())
                .setAddress(order.getAddress())
                .setBasketId(order.getBasketId())
                .setTotalCost(order.getTotalCost());
    }

    public OrderEntity toOrderEntity(CreateOrderRequest orderRequest) {
        return new OrderEntity()
                .setClientId(orderRequest.getClientId())
                .setClientPhoneNumber(orderRequest.getClientPhoneNumber())
                .setAddress(orderRequest.getAddress())
                .setBasketId(orderRequest.getBasketId());
    }
}
