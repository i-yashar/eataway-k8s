package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.MenuDTO;
import bg.tuplovdiv.orderservice.model.entity.MenuEntity;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

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
}
