package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.exception.BasketNotFoundException;
import bg.tuplovdiv.orderservice.exception.MenuNotFoundException;
import bg.tuplovdiv.orderservice.messaging.OrderContext;
import bg.tuplovdiv.orderservice.model.OrderStatus;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.MenuEntity;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class OrderMapper {

    private final BasketRepository basketRepository;
    private final MenuRepository menuRepository;

    public OrderMapper(BasketRepository basketRepository, MenuRepository menuRepository) {
        this.basketRepository = basketRepository;
        this.menuRepository = menuRepository;
    }

    public OrderDTO toOrderDTO(OrderEntity order) {
        return new OrderDTO()
                .setOrderId(order.getExternalId())
                .setClientId(order.getClientId())
                .setClientPhoneNumber(order.getClientPhoneNumber())
                .setDeliveryDriverId(order.getDeliveryDriverId())
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
                .setMenus(getMenusFromBasket(context.getBasket().getBasketId()))
                .setTotalCost(context.getTotalCost());
    }
    
    public OrderEntity toOrderEntity(OrderDTO dto) {
        return new OrderEntity()
                .setExternalId(dto.getOrderId())
                .setClientId(dto.getClientId())
                .setClientPhoneNumber(dto.getClientPhoneNumber())
                .setAddress(dto.getAddress())
                .setDeliveryDriverId(dto.getDeliveryDriverId())
                .setMenus(getMenusFromUserBasket(dto.getClientId()))
                .setTotalCost(dto.getTotalCost())
                .setStatus(OrderStatus.valueOf(dto.getStatus()));
    }

    private Set<MenuEntity> getMenusFromUserBasket(String clientId) {
        BasketEntity basket = basketRepository.findBasketEntityByOwnerUserId(clientId)
                .orElseThrow(() -> new BasketNotFoundException("Basket with clientId " + clientId + " not found"));

        Set<MenuEntity> menus = new HashSet<>();

        basket.getItems().forEach(item -> menus.add(getMenu(item.getMenuId())));

        return menus;
    }

    private Set<MenuEntity> getMenusFromBasket(UUID basketId) {
        BasketEntity basket = basketRepository.findBasketEntityByExternalId(basketId)
                .orElseThrow(() -> new BasketNotFoundException("Basket with basketId " + basketId + " not found"));
        Set<MenuEntity> menus = new HashSet<>();

        basket.getItems().forEach(item -> menus.add(getMenu(item.getMenuId())));

        return menus;
    }

    private MenuEntity getMenu(UUID menuId) {
        return menuRepository.findMenuEntityByExternalId(menuId)
                .orElseThrow(() -> new MenuNotFoundException("Menu with menuId " + menuId + " not found"));
    }
}
