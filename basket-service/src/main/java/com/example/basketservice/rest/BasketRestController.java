package com.example.basketservice.rest;

import com.example.basketservice.dto.BasketDTO;
import com.example.basketservice.dto.ItemDTO;
import com.example.basketservice.service.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders/api/v1")
public class BasketRestController {

    private static final String BASKET_PATH = "/users/{ownerId}/basket";
    private static final String BASKET_ITEM_PATH = BASKET_PATH + "/items/{menuId}";

    private final BasketService basketService;

    public BasketRestController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PutMapping(BASKET_PATH)
    public ResponseEntity<BasketDTO> addBasketItem(@PathVariable String ownerId,
                                                   @RequestBody ItemDTO basketItem) {
        return ResponseEntity.ok(basketService.addBasketItem(ownerId, basketItem));
    }

    @GetMapping(BASKET_PATH)
    public ResponseEntity<BasketDTO> getBasket(@PathVariable String ownerId) {
        return ResponseEntity.ok(basketService.getBasketByOwnerId(ownerId));
    }

    @DeleteMapping(BASKET_ITEM_PATH)
    public ResponseEntity<Void> deleteBasketItem(@PathVariable String ownerId, @PathVariable UUID menuId) {
        basketService.deleteBasketItem(ownerId, menuId);

        return ResponseEntity.noContent().build();
    }
}
