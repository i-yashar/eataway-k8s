package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.dto.OrderStatusInfoDTO;
import bg.tuplovdiv.apigateway.order.OrderStatusEmitters;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import bg.tuplovdiv.apigateway.service.OrderService;
import bg.tuplovdiv.apigateway.service.OrderStatusInfoService;
import bg.tuplovdiv.orderservice.exception.UnauthorizedAccessException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.UUID;

@Controller
@RequestMapping("eataway")
public class OrderController {

    private static final String API_PATH = "api/v1";
    private static final String ORDERS_PATH = "orders";
    private static final String ORDER_PATH = ORDERS_PATH + "/{orderId}";
    private static final String ORDER_LIVE_UPDATE_PATH = ORDERS_PATH + "/sse";
    private static final String ORDERS_STATUS_INFO_PATH = API_PATH + "/" + ORDER_PATH + "/info";

    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;
    private final OrderService orderService;
    private final OrderStatusInfoService orderStatusInfoService;

    public OrderController(AuthenticatedUserProviderFactory authenticatedUserProviderFactory, OrderService orderService, OrderStatusInfoService orderStatusInfoService) {
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
        this.orderService = orderService;
        this.orderStatusInfoService = orderStatusInfoService;
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

    @PostMapping(ORDERS_PATH)
    public String createOrder(@Valid CreateOrderRequestDTO order,
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

    @GetMapping(ORDER_PATH)
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
    public String getUserActiveOrders(Model model) {
        model.addAttribute("orders", orderService.getUserActiveOrders(getUserId()));

        return "user-orders";
    }

    @GetMapping(ORDER_LIVE_UPDATE_PATH)
    public SseEmitter streamSse() {
        return OrderStatusEmitters.get(getUserId());
    }

    private String getUserId() {
        AuthenticatedUser user = authenticatedUserProviderFactory.getProvider().provide();

        return user.getUserId();
    }

    @GetMapping(ORDERS_STATUS_INFO_PATH)
    @ResponseBody
    public ResponseEntity<Collection<OrderStatusInfoDTO>> getOrderStatusInfoMessages(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderStatusInfoService.getOrderStatusInfoMessages(orderId));
    }
}
