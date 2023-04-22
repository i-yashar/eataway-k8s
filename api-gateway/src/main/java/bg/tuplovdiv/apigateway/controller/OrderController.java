package bg.tuplovdiv.apigateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("eataway")
public class OrderController {

    private static final String BASKET_PATH = "basket";

    @GetMapping(BASKET_PATH)
    public String getUserBasket() {
        return "basket";
    }
}
