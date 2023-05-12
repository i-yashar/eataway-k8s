package bg.tuplovdiv.apigateway.messaging;

public class OrderStatusChangeListener implements MessageListener<OrderStatusChangeEvent, Void> {
    @Override
    public Void accept(OrderStatusChangeEvent message) {
        return null;
    }
}
