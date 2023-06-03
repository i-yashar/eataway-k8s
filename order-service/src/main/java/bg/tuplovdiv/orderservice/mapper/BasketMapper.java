package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.ItemDTO;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BasketMapper {

    private final ItemMapper itemMapper;

    public BasketMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public BasketDTO toDTO(BasketEntity basketEntity) {
        Set<ItemDTO> items = itemMapper.toDTO(basketEntity.getItems());
        return new BasketDTO()
                .setBasketId(basketEntity.getExternalId())
                .setOwnerId(basketEntity.getOwner().getUserId())
                .setItems(items);
    }
}
