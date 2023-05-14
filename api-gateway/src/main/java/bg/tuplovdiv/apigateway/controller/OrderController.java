package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.order.OrderStatusEmitters;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import bg.tuplovdiv.apigateway.service.OrderService;
import bg.tuplovdiv.orderservice.exception.UnauthorizedAccessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("eataway")
public class OrderController {

    private static final String ORDERS_PATH = "orders";
    private static final String ORDER_LIVE_UPDATE_PATH = ORDERS_PATH + "/sse";

    private final AuthenticatedUserProvider userProvider;
    private final OrderService orderService;

    public OrderController(AuthenticatedUserProvider userProvider, OrderService orderService) {
        this.userProvider = userProvider;
        this.orderService = orderService;
    }

    @ModelAttribute("createOrderRequest")
    public CreateOrderRequest initCreateOrderRequest() {
        return new CreateOrderRequest();
    }

    @PostMapping(ORDERS_PATH)
    public String createOrder(@Valid CreateOrderRequest order,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createOrderRequest", order);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createOrderRequest", bindingResult);
            return "redirect:/eataway/basket";
        }

        order.setClientId(getUserId());
        UUID orderId = orderService.createOrder(order);

        return "redirect:/eataway/orders/" + orderId;
    }

    @GetMapping(ORDERS_PATH + "/{orderId}")
    public String getOrderInfo(@PathVariable UUID orderId, Model model) {
        OrderDTO order = orderService.getOrderInfo(orderId);
        verifyUserAuthorized(order.getClientId());

        model.addAttribute("order", order);

        return "order-info";
    }

    private void verifyUserAuthorized(String ownerId) {
        if (!ownerId.equals(getUserId())) {
            throw new UnauthorizedAccessException("You are not authorized to access this resource");
        }
    }

    @GetMapping(ORDERS_PATH)
    public String getUserOrders(Model model) {
        model.addAttribute("orders", orderService.getUserOrders(getUserId()));

        return "user-orders";
    }

    @GetMapping(ORDER_LIVE_UPDATE_PATH)
    public SseEmitter streamSse() {
        return OrderStatusEmitters.get(getUserId());
    }

    private String getUserId() {
        AuthenticatedUser user = userProvider.provide();

        return user.getUserId();
    }
}
