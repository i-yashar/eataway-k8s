package com.example.basketservice.mapper;

import com.example.basketservice.dto.ItemDTO;
import com.example.basketservice.model.entity.ItemEntity;
import com.example.basketservice.model.entity.MenuEntity;
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

    public ItemEntity fromDTO(ItemDTO dto) {
        MenuEntity menu = menuMapper.fromDTO(dto.getMenu());
        return new ItemEntity()
                .setMenu(menu)
                .setCount(dto.getCount());
    }
}
