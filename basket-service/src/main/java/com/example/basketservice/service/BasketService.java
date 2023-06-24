package com.example.basketservice.service;

import com.example.basketservice.dto.BasketDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO addBasketItem(String ownerId, UUID menuId);
    BasketDTO getBasketByOwnerId(String ownerId);
    void deleteBasketItem(String ownerId, UUID menuId);
}
