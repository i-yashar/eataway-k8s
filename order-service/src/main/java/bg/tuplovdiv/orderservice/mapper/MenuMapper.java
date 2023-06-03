package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.MenuDTO;
import bg.tuplovdiv.orderservice.exception.MenuNotFoundException;
import bg.tuplovdiv.orderservice.model.entity.MenuEntity;
import bg.tuplovdiv.orderservice.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MenuMapper {

    private final MenuRepository menuRepository;

    public MenuMapper(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Set<MenuDTO> toDTO(Set<MenuEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public MenuDTO toDTO(MenuEntity entity) {
        return new MenuDTO()
                .setMenuId(entity.getExternalId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setPrice(entity.getPrice())
                .setRestaurantId(entity.getRestaurant().getExternalId())
                .setRestaurantName(entity.getRestaurant().getName())
                .setRestaurantAddress(entity.getRestaurant().getAddress());
    }

    public MenuEntity fromDTO(MenuDTO dto) {
        return menuRepository.findMenuEntityByExternalId(dto.getMenuId())
                .orElseThrow(() -> new MenuNotFoundException("Menu not found"));
    }
}
