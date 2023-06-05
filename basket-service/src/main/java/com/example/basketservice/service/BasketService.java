package com.example.basketservice.service;

import com.example.basketservice.dto.BasketDTO;
import com.example.basketservice.dto.ItemDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO addBasketItem(String ownerId, ItemDTO basketItem);
    BasketDTO getBasketByOwnerId(String ownerId);
    void deleteBasketItem(String ownerId, UUID menuId);
}
