package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.BasketItemEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BasketMapper {

    public BasketDTO toBasketDTO(BasketEntity basketEntity) {
        return new BasketDTO()
                .setBasketId(basketEntity.getExternalId())
                .setOwnerId(basketEntity.getOwner().getExternalId())
                .setItems(mapToBasketItemDTO(basketEntity.getItems()));
    }

    private Set<BasketItemDTO> mapToBasketItemDTO(Set<BasketItemEntity> entities) {
        return entities.stream()
                .map(entity -> new BasketItemDTO()
                        .setMenu(entity.getMenu())
                        .setCount(entity.getCount()))
                .collect(Collectors.toSet());
    }
}
