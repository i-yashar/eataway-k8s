package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;
import bg.tuplovdiv.orderservice.dto.ItemDTO;
import bg.tuplovdiv.orderservice.dto.MenuDTO;
import bg.tuplovdiv.orderservice.exception.MenuNotFoundException;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.BasketItemEntity;
import bg.tuplovdiv.orderservice.model.entity.ItemEntity;
import bg.tuplovdiv.orderservice.model.entity.MenuEntity;
import bg.tuplovdiv.orderservice.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BasketMapper {

    private final MenuRepository menuRepository;

    public BasketMapper(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public BasketDTO toBasketDTO(BasketEntity basketEntity) {
        return new BasketDTO()
                .setBasketId(basketEntity.getExternalId())
                .setOwnerId(basketEntity.getOwner().getUserId())
                .setItems(mapToBasketItemDTOs(basketEntity.getItems()));
    }

    private Set<BasketItemDTO> mapToBasketItemDTOs(Set<BasketItemEntity> entities) {
        return entities.stream()
                .map(entity -> new BasketItemDTO()
                        .setMenu(mapToMenuDTO(entity.getMenu()))
                        .setCount(entity.getCount()))
                .collect(Collectors.toSet());
    }

    private MenuDTO mapToMenuDTO(MenuEntity entity) {
        return new MenuDTO()
                .setMenuId(entity.getExternalId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setPrice(entity.getPrice())
                .setItems(mapToItemDTOs(entity.getItems()))
                .setRestaurantId(entity.getRestaurant().getExternalId());
    }

    private Set<ItemDTO> mapToItemDTOs(Set<ItemEntity> entities) {
        return entities.stream()
                .map(entity -> new ItemDTO()
                        .setItemId(entity.getExternalId())
                        .setName(entity.getName())
                        .setQuantity(entity.getQuantity()))
                .collect(Collectors.toSet());
    }

    public BasketItemEntity toBasketItemEntity(BasketItemDTO basketItemDTO) {
        return new BasketItemEntity()
                .setMenu(getMenuByMenuId(basketItemDTO.getMenu().getMenuId()))
                .setCount(basketItemDTO.getCount());
    }

    private MenuEntity getMenuByMenuId(UUID menuId) {
        return menuRepository.findMenuEntityByExternalId(menuId)
                .orElseThrow(() -> new MenuNotFoundException("Menu with menuId " + menuId + " not found"));
    }
}
