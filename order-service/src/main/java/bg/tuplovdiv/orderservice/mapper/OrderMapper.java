package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.messaging.OrderContext;
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
                .setTotalCost(order.getTotalCost())
                .setStatus(order.getStatus().name());
    }

    public OrderEntity toOrderEntity(OrderContext context) {
        return new OrderEntity()
                .setExternalId(context.getOrderId())
                .setClientId(context.getClientId())
                .setClientPhoneNumber(context.getClientPhoneNumber())
                .setAddress(context.getAddress())
                .setBasketId(context.getBasket().getBasketId())
                .setTotalCost(context.getTotalCost());
    }
}
