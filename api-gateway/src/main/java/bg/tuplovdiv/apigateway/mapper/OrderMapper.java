package bg.tuplovdiv.apigateway.mapper;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.messaging.OrderContext;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toDTO(OrderContext context) {
        return new OrderDTO()
                .setOrderId(context.getOrderId())
                .setClientId(context.getClientId())
                .setClientPhoneNumber(context.getClientPhoneNumber())
                .setAddress(context.getAddress())
                .setItems(context.getItems())
                .setTotalCost(context.getTotalCost())
                .setStatus("REGISTERED");
    }
}
