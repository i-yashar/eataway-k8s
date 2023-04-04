package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.BasketDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO getByBasketId(UUID basketId);
}
