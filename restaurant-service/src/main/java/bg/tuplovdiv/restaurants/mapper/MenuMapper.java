package bg.tuplovdiv.restaurants.mapper;

import bg.tuplovdiv.restaurants.dto.ItemDTO;
import bg.tuplovdiv.restaurants.dto.MenuDTO;
import bg.tuplovdiv.restaurants.model.entity.ItemEntity;
import bg.tuplovdiv.restaurants.model.entity.MenuEntity;
import bg.tuplovdiv.restaurants.model.entity.RestaurantEntity;
import bg.tuplovdiv.restaurants.repository.ItemRepository;
import bg.tuplovdiv.restaurants.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MenuMapper {

    private final ItemRepository itemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuMapper(ItemRepository itemRepository, RestaurantRepository restaurantRepository) {
        this.itemRepository = itemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public MenuEntity toMenuEntity(MenuDTO menuDTO) {
        return new MenuEntity()
                .setExternalId(UUID.randomUUID())
                .setName(menuDTO.getName())
                .setDescription(menuDTO.getDescription())
                .setPrice(menuDTO.getPrice())
                .setItems(mapToItemEntities(menuDTO.getItems()))
                .setRestaurant(getRestaurantById(menuDTO.getRestaurantId()));
    }

    private Set<ItemEntity> mapToItemEntities(Set<ItemDTO> items) {
        return items.stream()
                .map(this::getItemEntity)
                .collect(Collectors.toSet());
    }

    private ItemEntity getItemEntity(ItemDTO item) {
        ItemEntity itemEntity = new ItemEntity()
                .setExternalId(UUID.randomUUID())
                .setName(item.getName())
                .setQuantity(item.getQuantity());
        return itemRepository.save(itemEntity);
    }

    private RestaurantEntity getRestaurantById(UUID restaurantId) {
        //todo: add custom exception
        return restaurantRepository.findByExternalId(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant doesn't exist"));
    }

    public MenuDTO toMenuDTO(MenuEntity entity) {
        return new MenuDTO()
                .setMenuId(entity.getExternalId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setPrice(entity.getPrice())
                .setItems(mapToItemDTOs(entity.getItems()))
                .setRestaurantId((entity.getRestaurant().getExternalId()));
    }

    private Set<ItemDTO> mapToItemDTOs(Set<ItemEntity> items) {
        return items.stream()
                .map(item -> new ItemDTO()
                        .setItemId(item.getExternalId())
                        .setName(item.getName())
                        .setQuantity(item.getQuantity()))
                .collect(Collectors.toSet());
    }
}
