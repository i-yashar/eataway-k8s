package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.security.validation.DeliveryValidator;
import bg.tuplovdiv.apigateway.security.validation.StatusValidator;
import bg.tuplovdiv.apigateway.service.DeliveryService;
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
    private static final String ORDER_PATH = ORDERS_PATH + "/{orderId}";
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

    @PutMapping(ORDER_PATH)
    @PreAuthorize("@deliveryValidator.isDeliveryDriverValidForUpdate(#orderId, #status)")
    public String updateOrder(@PathVariable UUID orderId,
                              @RequestParam(name = "currentStatus") String status) {
        status = updateStatus(status);
        deliveryService.updateOrder(orderId, status, getUserId());

        return "redirect:/eataway/delivery/orders/" + orderId;
    }

    private String updateStatus(String status) {
        return switch (status) {
            case "REGISTERED" -> "ACTIVE";
            case "ACTIVE" -> "ABOUT_TO_BE_DELIVERED";
            case "ABOUT_TO_BE_DELIVERED" -> "DELIVERED";
            default -> "";
        };
    }

    private String getUserId() {
        AuthenticatedUser user = authenticatedUserProviderFactory.getProvider().provide();

        return user.getUserId();
    }
}
