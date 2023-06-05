package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.ItemDTO;
import bg.tuplovdiv.orderservice.model.entity.ItemEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ItemMapper {

    private final MenuMapper menuMapper;

    public ItemMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public Set<ItemDTO> toDTO(Set<ItemEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public ItemDTO toDTO(ItemEntity entity) {
        return new ItemDTO()
                .setMenu(menuMapper.toDTO(entity.getMenu()))
                .setCount(entity.getCount());
    }
}
