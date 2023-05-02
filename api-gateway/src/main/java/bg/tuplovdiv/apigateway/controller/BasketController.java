package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.BasketItemDTO;
import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import bg.tuplovdiv.apigateway.service.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("eataway")
public class BasketController {

    private static final String BASKET_PATH = "basket";
    private static final String ADD_BASKET_ITEM_PATH = "api/v1/basketItems";
    private static final String DELETE_BASKET_ITEM_PATH = "api/v1/basketItems/{menuId}";

    private final AuthenticatedUserProvider userProvider;
    private final BasketService basketService;

    public BasketController(AuthenticatedUserProvider userProvider, BasketService basketService) {
        this.userProvider = userProvider;
        this.basketService = basketService;
    }

    @ModelAttribute("createOrderRequest")
    public CreateOrderRequest initCreateOrderRequest() {
        return new CreateOrderRequest();
    }

    @GetMapping(BASKET_PATH)
    public String getUserBasket(Model model) {
        model.addAttribute("basket", basketService.getUserBasket(getUserId()));

        return "basket";
    }

    @PutMapping(ADD_BASKET_ITEM_PATH)
    @ResponseBody
    public ResponseEntity<BasketDTO> addBasketItem(@RequestBody BasketItemDTO basketItem) {
        return ResponseEntity.ok(basketService.addBasketItem(getUserId(), basketItem));
    }

    @DeleteMapping(DELETE_BASKET_ITEM_PATH)
    @ResponseBody
    public ResponseEntity<Void> deleteBasketItem(@PathVariable UUID menuId) {
        basketService.deleteBasketItem(getUserId(), menuId);

        return ResponseEntity.noContent().build();
    }

    private String getUserId() {
        AuthenticatedUser user = userProvider.provide();

        return user.getUserId();
    }
}