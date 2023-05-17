package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.validation.DeliveryValidator;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("eataway/delivery")
@PreAuthorize("@deliveryValidator.isDeliveryDriverValid()")
public class DeliveryController {

    private static final String ORDERS_PATH = "orders";
    private static final String ORDER_INFO_PATH = ORDERS_PATH + "/{orderId}";
    private static final String UPDATE_ORDERS_PATH = ORDERS_PATH + "/{orderId}";
    private static final String ACTIVE_ORDERS_PATH = ORDERS_PATH + "/active";

    private final DeliveryService deliveryService;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final DeliveryValidator deliveryValidator;

    public DeliveryController(DeliveryService deliveryService, AuthenticatedUserProvider authenticatedUserProvider, DeliveryValidator deliveryValidator) {
        this.deliveryService = deliveryService;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.deliveryValidator = deliveryValidator;
    }

    @GetMapping(ORDERS_PATH)
    @PreAuthorize("@deliveryValidator.isDeliveryDriverFree()")
    public String getRegisteredOrders(Model model) {
        model.addAttribute("orders", deliveryService.getRegisteredOrders());

        return "registered-orders";
    }

    @GetMapping(ACTIVE_ORDERS_PATH)
    public String getActiveOrders(Model model) {
        model.addAttribute("orders", deliveryService.getDeliveryDriverActiveOrders(getUserId()));

        return "active-delivery-driver-orders";
    }

    @GetMapping(ORDER_INFO_PATH)
    public String getOrderInfo(@PathVariable UUID orderId, Model model) {
        model.addAttribute("order", deliveryService.getOrderInfo(orderId));
        model.addAttribute("menus", deliveryService.getOrderBasketInfo(orderId).getItems());

        return "order-delivery-info";
    }

    @PostMapping(ORDERS_PATH)
    @ResponseBody
    public ResponseEntity<Void> takeOrder(@RequestBody String orderId) {
        deliveryService.takeOrder(UUID.fromString(orderId), getUserId());

        return ResponseEntity.ok().build();
    }

    @PutMapping(UPDATE_ORDERS_PATH)
    @ResponseBody
    public ResponseEntity<Void> updateOrder(@PathVariable UUID orderId,
                                      @RequestParam(name = "status") String status) {
        deliveryService.updateOrder(orderId, status);

        return ResponseEntity.ok().build();
    }

    private String getUserId() {
        AuthenticatedUser user = authenticatedUserProvider.provide();

        return user.getUserId();
    }
}
