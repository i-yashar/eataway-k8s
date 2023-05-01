package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("eataway")
public class OrderController {

    private static final String ORDERS_PATH = "orders";

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

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createOrderRequest", order);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createOrderRequest", bindingResult);
            return "redirect:/eataway/basket";
        }

        UUID orderId = orderService.createOrder(order);

        return "redirect:/eataway/orders/" + orderId;
    }
}
