package bg.tuplovdiv.restaurants.mapper;

import bg.tuplovdiv.restaurants.dto.MenuDTO;
import bg.tuplovdiv.restaurants.model.entity.MenuEntity;
import bg.tuplovdiv.restaurants.model.entity.RestaurantEntity;
import bg.tuplovdiv.restaurants.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MenuMapper {

    private final RestaurantRepository restaurantRepository;

    public MenuMapper(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public MenuEntity toMenuEntity(MenuDTO menuDTO) {
        return new MenuEntity()
                .setExternalId(UUID.randomUUID())
                .setName(menuDTO.getName())
                .setDescription(menuDTO.getDescription())
                .setPrice(menuDTO.getPrice())
                .setRestaurant(getRestaurantById(menuDTO.getRestaurantId()));
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
                .setRestaurantId((entity.getRestaurant().getExternalId()));
    }
}
