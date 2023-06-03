package bg.tuplovdiv.orderservice.messaging.process;

import bg.tuplovdiv.orderservice.dto.ItemDTO;
import bg.tuplovdiv.orderservice.mapper.ItemMapper;
import bg.tuplovdiv.orderservice.messaging.MessageDispatcher;
import bg.tuplovdiv.orderservice.messaging.OrderContext;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CreateOrderProcess {

    private final MessageDispatcher dispatcher;
    private final ItemMapper itemMapper;

    public CreateOrderProcess(@Qualifier("createOrderDispatcher") MessageDispatcher dispatcher, ItemMapper itemMapper) {
        this.dispatcher = dispatcher;
        this.itemMapper = itemMapper;
    }

    public void start(OrderEntity order) {
        dispatcher.dispatch(buildOrderContext(order));
    }

    private OrderContext buildOrderContext(OrderEntity order) {
        String clientId = order.getClientId();
        Set<ItemDTO> items = itemMapper.toDTO(order.getItems());

        return OrderContext.getBuilder()
                .orderId(order.getExternalId())
                .clientId(clientId)
                .clientPhone(order.getClientPhoneNumber())
                .address(order.getAddress())
                .items(items)
                .totalCost(order.getTotalCost())
                .build();
    }
}
