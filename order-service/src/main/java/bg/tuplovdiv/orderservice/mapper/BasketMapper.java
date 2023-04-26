package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;
import bg.tuplovdiv.orderservice.exception.MenuNotFoundException;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.BasketItemEntity;
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
                        .setMenuId(entity.getMenuId())
                        .setCount(entity.getCount()))
                .collect(Collectors.toSet());
    }

    public BasketItemEntity toBasketItemEntity(BasketItemDTO basketItemDTO) {
        return new BasketItemEntity()
                .setMenuId(basketItemDTO.getMenuId())
                .setCount(basketItemDTO.getCount());
    }

    private MenuEntity getMenuByMenuId(UUID menuId) {
        return menuRepository.findMenuEntityByExternalId(menuId)
                .orElseThrow(() -> new MenuNotFoundException("Menu with menuId " + menuId + " not found"));
    }
}
