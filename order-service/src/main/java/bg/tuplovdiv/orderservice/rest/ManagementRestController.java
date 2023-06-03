package bg.tuplovdiv.orderservice.rest;

import bg.tuplovdiv.orderservice.task.ManagementTasks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/api/v1/management")
public class ManagementRestController {

    private static final String ORDER_DISPATCH_PATH = "/dispatch";

    private final ManagementTasks managementTasks;

    public ManagementRestController(ManagementTasks managementTasks) {
        this.managementTasks = managementTasks;
    }

    @GetMapping(ORDER_DISPATCH_PATH)
    public ResponseEntity<Void> dispatchRegisteredOrders() {
        managementTasks.dispatchRegisteredOrders();

        return ResponseEntity.accepted().build();
    }
}
