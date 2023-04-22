package bg.tuplovdiv.apigateway.controller;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("eataway")
public class OrderController {

    private final AuthenticatedUserProvider userProvider;
    private final OrderService orderService;

    public OrderController(AuthenticatedUserProvider userProvider, OrderService orderService) {
        this.userProvider = userProvider;
        this.orderService = orderService;
    }

}
