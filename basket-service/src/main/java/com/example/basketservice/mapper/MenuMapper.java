package com.example.basketservice.mapper;

import com.example.basketservice.dto.MenuDTO;
import com.example.basketservice.exception.MenuNotFoundException;
import com.example.basketservice.model.entity.MenuEntity;
import com.example.basketservice.repository.MenuRepository;
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
