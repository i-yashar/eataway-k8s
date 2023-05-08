package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("eataway/delivery")
public class DeliveryController {

    private static final String ORDERS_PATH = "orders";
    private static final String UPDATE_ORDERS_PATH = ORDERS_PATH + "/{orderId}";

    private final DeliveryService deliveryService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public DeliveryController(DeliveryService deliveryService, AuthenticatedUserProvider authenticatedUserProvider) {
        this.deliveryService = deliveryService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @PostMapping(ORDERS_PATH)
    public String takeOrder(@RequestBody UUID orderId, Model model) {
        model.addAttribute("order", deliveryService.takeOrder(orderId, getUserId()));

        return "delivery-info";
    }

    @PutMapping(UPDATE_ORDERS_PATH)
    public String updateOrder(@PathVariable UUID orderId,
                              @RequestParam String status,
                              Model model) {
        model.addAttribute("order", deliveryService.updateOrder(orderId, status));

        return "delivery-info";
    }

    private String getUserId() {
        AuthenticatedUser user = authenticatedUserProvider.provide();

        return user.getUserId();
    }
}
