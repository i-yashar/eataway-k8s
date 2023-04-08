package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.OrderRequest;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderRequest toDTO(OrderEntity order) {
        return new OrderRequest()
                .setOrderId(order.getExternalId())
                .setClientId(order.getClientId())
                .setClientPhoneNumber(order.getClientPhoneNumber())
                .setDeliveryDriverId(order.getDeliverDriverId())
                .setBasketId(order.getBasketId())
                .setTotalCost(order.getTotalCost())
                .setStatus(order.getStatus());
    }

    public OrderEntity toEntity(OrderRequest orderRequest) {
        return new OrderEntity()
                .setExternalId(orderRequest.getOrderId())
                .setClientId(orderRequest.getClientId())
                .setClientPhoneNumber(orderRequest.getClientPhoneNumber())
                .setDeliverDriverId(orderRequest.getDeliveryDriverId())
                .setAddress(orderRequest.getAddress())
                .setBasketId(orderRequest.getBasketId())
                .setTotalCost(orderRequest.getTotalCost())
                .setStatus(orderRequest.getStatus());
    }
}
