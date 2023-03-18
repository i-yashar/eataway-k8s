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
                .setDeliverDriverId(order.getDeliverDriverId())
                .setItems(order.getBasket().getItems())
                .setTotalCost(order.getTotalCost());
    }
}
