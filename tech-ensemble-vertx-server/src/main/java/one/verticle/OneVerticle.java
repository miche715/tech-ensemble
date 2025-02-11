package one.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

public class OneVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        MessageConsumer<String> messageConsumer = vertx.eventBus().consumer("event-100");  // event-100 주소로 부터 받음.

        messageConsumer.handler((Message<String> message) -> {
            System.out.println("Message received: " + message.body());
        });
    }
}