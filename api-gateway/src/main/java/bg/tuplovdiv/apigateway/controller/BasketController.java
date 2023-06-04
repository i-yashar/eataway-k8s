package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.apigateway.dto.ItemDTO;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
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

    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;
    private final BasketService basketService;

    public BasketController(AuthenticatedUserProviderFactory authenticatedUserProviderFactory, BasketService basketService) {
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
        this.basketService = basketService;
    }

    @ModelAttribute
    public void addUserAttribute(Model model) {
        AuthenticatedUserProvider userProvider = authenticatedUserProviderFactory.getProvider();
        model.addAttribute("user", userProvider.provide());
    }

    @ModelAttribute("createOrderRequest")
    public CreateOrderRequestDTO initCreateOrderRequest() {
        return new CreateOrderRequestDTO();
    }

    @GetMapping(BASKET_PATH)
    public String getUserBasket(Model model) {
        model.addAttribute("basket", basketService.getUserBasket(getUserId()));

        return "basket";
    }

    @PutMapping(ADD_BASKET_ITEM_PATH)
    @ResponseBody
    public ResponseEntity<BasketDTO> addBasketItem(@RequestBody ItemDTO item) {
        return ResponseEntity.ok(basketService.addBasketItem(getUserId(), item));
    }

    @DeleteMapping(DELETE_BASKET_ITEM_PATH)
    @ResponseBody
    public ResponseEntity<Void> deleteBasketItem(@PathVariable UUID menuId) {
        basketService.deleteBasketItem(getUserId(), menuId);

        return ResponseEntity.noContent().build();
    }

    private String getUserId() {
        AuthenticatedUser user = authenticatedUserProviderFactory.getProvider().provide();

        return user.getUserId();
    }
}
