package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.security.validation.DeliveryValidator;
import bg.tuplovdiv.apigateway.security.validation.StatusValidator;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("eataway/delivery")
public class DeliveryController {

    private static final String ORDERS_PATH = "orders";
    private static final String ORDER_INFO_PATH = ORDERS_PATH + "/{orderId}";
    private static final String TAKE_ORDERS_PATH = ORDERS_PATH + "/take";
    private static final String UPDATE_ORDERS_PATH = ORDERS_PATH + "/{orderId}/update";
    private static final String ACTIVE_ORDERS_PATH = ORDERS_PATH + "/active";

    private final DeliveryService deliveryService;
    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;
    private final DeliveryValidator deliveryValidator;
    private final StatusValidator statusValidator;

    public DeliveryController(DeliveryService deliveryService, AuthenticatedUserProviderFactory authenticatedUserProviderFactory, DeliveryValidator deliveryValidator, StatusValidator statusValidator) {
        this.deliveryService = deliveryService;
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
        this.deliveryValidator = deliveryValidator;
        this.statusValidator = statusValidator;
    }

    @ModelAttribute
    public void addUserAttribute(Model model) {
        AuthenticatedUserProvider userProvider = authenticatedUserProviderFactory.getProvider();
        model.addAttribute("user", userProvider.provide());
    }

    @GetMapping(ORDERS_PATH)
    @PreAuthorize("@deliveryValidator.isDeliveryDriverFree()")
    public String getRegisteredOrders(Model model) {
        model.addAttribute("orders", deliveryService.getRegisteredOrders(getUserId()));

        return "registered-orders";
    }

    @GetMapping(ACTIVE_ORDERS_PATH)
    @PreAuthorize("@deliveryValidator.isDeliveryDriverValid()")
    public String getActiveOrders(Model model) {
        model.addAttribute("orders", deliveryService.getDeliveryDriverActiveOrders(getUserId()));

        return "active-delivery-driver-orders";
    }

    @GetMapping(ORDER_INFO_PATH)
    @PreAuthorize("@deliveryValidator.isDeliveryDriverValidForInfo(#orderId)")
    public String getOrderInfo(@PathVariable UUID orderId, Model model) {
        OrderDTO orderInfo = deliveryService.getOrderInfo(orderId);
        model.addAttribute("order", orderInfo);
        model.addAttribute("items", orderInfo.getItems());

        return "order-delivery-info";
    }

    @GetMapping(TAKE_ORDERS_PATH)
    @ResponseBody
    @PreAuthorize("@deliveryValidator.isDeliveryDriverValidForTake(#orderId)")
    public ResponseEntity<Void> takeOrder(@RequestBody String orderId) {
        deliveryService.takeOrder(UUID.fromString(orderId), getUserId());

        return ResponseEntity.ok().build();
    }

    @GetMapping(UPDATE_ORDERS_PATH)
    @ResponseBody
    @PreAuthorize("@deliveryValidator.isDeliveryDriverValidForUpdate(#orderId)" +
            "&& @statusValidator.isStatusUpdateValid(#status)")
    public ResponseEntity<Void> updateOrder(@PathVariable UUID orderId,
                                            @RequestParam(name = "status") String status) {
        deliveryService.updateOrder(orderId, status);

        return ResponseEntity.ok().build();
    }

    private String getUserId() {
        AuthenticatedUser user = authenticatedUserProviderFactory.getProvider().provide();

        return user.getUserId();
    }
}
