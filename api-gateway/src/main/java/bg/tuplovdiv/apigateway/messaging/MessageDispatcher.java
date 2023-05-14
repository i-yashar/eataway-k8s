package bg.tuplovdiv.apigateway.messaging;

public interface MessageDispatcher {
    void dispatch(Message message);
}
