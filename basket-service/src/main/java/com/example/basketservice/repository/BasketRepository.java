package com.example.basketservice.repository;

import com.example.basketservice.model.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
    Optional<BasketEntity> findBasketEntityByOwner(String ownerId);
}
