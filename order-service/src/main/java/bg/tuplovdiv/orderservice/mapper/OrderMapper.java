package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.ItemDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderMapper {

    private final ItemMapper itemMapper;

    public OrderMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public OrderDTO toDTO(OrderEntity order) {
        Set<ItemDTO> items = itemMapper.toDTO(order.getItems());
        return new OrderDTO()
                .setOrderId(order.getExternalId())
                .setClientId(order.getClientId())
                .setDeliveryDriverId(order.getDeliveryDriverId())
                .setClientPhoneNumber(order.getClientPhoneNumber())
                .setAddress(order.getAddress())
                .setItems(items)
                .setTotalCost(order.getTotalCost())
                .setStatus(order.getStatus().name());
    }
}
