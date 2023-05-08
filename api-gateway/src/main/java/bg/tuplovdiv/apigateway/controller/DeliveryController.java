package bg.tuplovdiv.apigateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("eataway/delivery")
public class DeliveryController {

    private static final String ORDERS_PATH = "orders";
    private static final String UPDATE_ORDERS_PATH = ORDERS_PATH + "/{orderId}";

    @PostMapping(ORDERS_PATH)
    public String takeOrder(@RequestBody UUID orderId) {
        return "delivery-info";
    }

    @PutMapping(UPDATE_ORDERS_PATH)
    public String updateOrderStatusToAboutToDeliver(@PathVariable UUID orderId, @RequestParam String action) {
        return "delivery-info";
    }
}
