package com.example.basketservice.repository;

import com.example.basketservice.model.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Optional<MenuEntity> findMenuEntityByExternalId(UUID menuId);
}
