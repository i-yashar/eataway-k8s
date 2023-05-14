package bg.tuplovdiv.orderservice.messaging;

public interface MessageDispatcher {
    void dispatch(Message message);
}
