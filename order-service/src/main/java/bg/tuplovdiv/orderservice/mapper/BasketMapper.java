package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import org.springframework.stereotype.Component;

@Component
public class BasketMapper {

    public BasketDTO toBasketDTO(BasketEntity basketEntity) {
        return new BasketDTO()
                .setBasketId(basketEntity.getExternalId())
                .setOwnerId(basketEntity.getOwner().getExternalId())
                .setItems(basketEntity.getItems());
    }
}
