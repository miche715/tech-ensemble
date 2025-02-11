package server.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageProducer;

import java.util.HashMap;
import java.util.Map;

public class ServerVerticle extends AbstractVerticle {
    private final Map<String, MessageProducer<String>> messageProducerMap = new HashMap<>();

    private MessageProducer<String> getMessageProducer(String address) {
        return messageProducerMap.computeIfAbsent(address, key -> vertx.eventBus().publisher(key));
    }

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createNetServer().connectHandler(socket -> {
            System.out.println("Client connected: " + socket.remoteAddress() + ".");

            socket.handler(buffer -> {
                String receiveMessage = buffer.toString();
                String[] splitReceiveMessage = receiveMessage.split(" ");
                String protocol = splitReceiveMessage[0];
                String message = splitReceiveMessage[1];
                System.out.println("수신: " + receiveMessage);

                switch(protocol) {
                    case "one":
                        getMessageProducer("event-100").write(message);

                        break;

                    case "two":
                        getMessageProducer("event-200").write(message);

                        break;

                    case "three":
                        getMessageProducer("event-300").write(message);

                        break;

                    default:
                        break;
                }

                String response = "서버 응답: " + receiveMessage.toUpperCase();
                socket.write(response);
            });

            socket.closeHandler(v -> System.out.println("클라이언트 연결 종료"));
        }).listen(1234, result -> {
            if(result.succeeded()) {
                System.out.println("Server started on port 1234.");
                startPromise.complete();
            } else {
                System.out.println("Server start fail: " + result.cause().getMessage() + ".");
                startPromise.fail(result.cause());
            }
        });
    }
}