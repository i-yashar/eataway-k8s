package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.CreateOrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {

    private final BasketRepository basketRepository;

    public OrderMapper(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public OrderDTO toDTO(OrderEntity order) {
        return new OrderDTO()
                .setOrderId(order.getExternalId())
                .setClientId(order.getClientId())
                .setClientPhoneNumber(order.getClientPhoneNumber())
                .setDeliverDriverId(order.getDeliverDriverId())
                .setItems(order.getBasket().getItems())
                .setTotalCost(order.getTotalCost())
                .setStatus(order.getStatus());
    }

    public OrderEntity toEntity(CreateOrderDTO orderDTO) {
        return new OrderEntity()
                .setExternalId(UUID.randomUUID())
                .setClientId(orderDTO.getClientId())
                .setClientPhoneNumber(orderDTO.getClientPhoneNumber())
                .setAddress(orderDTO.getAddress())
                .setBasket(getBasketByBasketId(orderDTO.getBasketId()))
                .setTotalCost(orderDTO.getTotalCost());
    }

    private BasketEntity getBasketByBasketId(UUID basketId) {
        //todo: add custom exception
        return basketRepository.findBasketEntityByExternalId(basketId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
