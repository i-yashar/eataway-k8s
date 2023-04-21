package bg.tuplovdiv.orderservice.rest;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;
import bg.tuplovdiv.orderservice.service.BasketService;
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
    public ResponseEntity<BasketDTO> addBasketItem(@PathVariable UUID ownerId,
                                              @RequestBody BasketItemDTO basketItem) {
        return ResponseEntity.ok(basketService.addBasketItem(ownerId, basketItem));
    }

    @GetMapping(BASKET_PATH)
    public ResponseEntity<BasketDTO> getBasket(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(basketService.getBasketByOwnerId(ownerId));
    }

    @DeleteMapping(BASKET_ITEM_PATH)
    public ResponseEntity<Void> deleteBasketItem(@PathVariable UUID ownerId, @PathVariable UUID menuId) {
        return null;
    }
}
