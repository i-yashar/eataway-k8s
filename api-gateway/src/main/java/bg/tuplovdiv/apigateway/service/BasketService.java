package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.BasketItemDTO;
import bg.tuplovdiv.apigateway.dto.DetailedBasketDTO;

import java.util.UUID;

public interface BasketService {
    DetailedBasketDTO getUserBasket(String ownerId);
    BasketDTO addBasketItem(String ownerId, BasketItemDTO basketItem);
    void deleteBasketItem(String ownerId, UUID menuId);
}
