package bg.tuplovdiv.apigateway.order.delivery;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class DriverManager {

    private static final Map<String, UUID> DRIVERS;

    static {
        DRIVERS = new HashMap<>();
    }

    public void registerDriver(String driverId, UUID orderId) {
        DRIVERS.put(driverId, orderId);
    }

    public void deregisterDriver(String driverId) {
        DRIVERS.remove(driverId);
    }

    public boolean isPresent(String driverId) {
        return DRIVERS.containsKey(driverId);
    }
}
