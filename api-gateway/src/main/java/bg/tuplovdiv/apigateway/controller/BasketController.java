package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.service.BasketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("eataway")
public class BasketController {

    private static final String BASKET_PATH = "basket";
    private static final String ADD_BASKET_ITEM_PATH = "api/v1/basketItems/{menuId}";
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
    public String addBasketItem(@PathVariable UUID menuId) {
        basketService.addBasketItem(getUserId(), menuId);

        return "redirect:/eataway/basket";
    }

    @GetMapping(DELETE_BASKET_ITEM_PATH)
    public String deleteBasketItem(@PathVariable UUID menuId) {
        basketService.deleteBasketItem(getUserId(), menuId);

        return "redirect:/eataway/basket";
    }

    private String getUserId() {
        AuthenticatedUser user = authenticatedUserProviderFactory.getProvider().provide();

        return user.getUserId();
    }
}
