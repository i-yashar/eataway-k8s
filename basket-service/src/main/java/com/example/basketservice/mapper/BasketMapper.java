package com.example.basketservice.mapper;

import com.example.basketservice.dto.BasketDTO;
import com.example.basketservice.dto.ItemDTO;
import com.example.basketservice.model.entity.BasketEntity;
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
                .setOwnerId(basketEntity.getOwner())
                .setItems(items);
    }
}
