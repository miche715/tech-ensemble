package core.server.verticle;

import common.record.VertxMessageRecord;
import common.vertx.BaseVerticle;
import common.vertx.VertxMessageRecordCodec;
import io.vertx.core.Promise;

public class ServerVerticle extends BaseVerticle {
//    private final Gson gson = GsonProvider.getInstance();
//    private final Map<String, MessageProducer<VertxMessageRecord>> messageProducerMap = new HashMap<>();
//
//    private MessageProducer<VertxMessageRecord> getMessageProducer(String address) {
//        return messageProducerMap.computeIfAbsent(address, key -> vertx.eventBus().publisher(key));
//    }

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.eventBus().registerDefaultCodec(VertxMessageRecord.class, new VertxMessageRecordCodec());

        vertx.createNetServer().connectHandler(socket -> {
            System.out.println("\nClient connected: " + socket.remoteAddress());

            socket.handler(buffer -> {
                VertxMessageRecord vertxMessageRecord = gson.fromJson(buffer.toString(), VertxMessageRecord.class);
                System.out.println("\nReceive Packet: " + vertxMessageRecord);

                switch(vertxMessageRecord.verticleType()) {
                    case "one": {
                        getMessageProducer("event-100").write(vertxMessageRecord);

                        break;
                    }

                    case "two": {
                        getMessageProducer("event-200").write(vertxMessageRecord);

                        break;
                    }

                    case "three": {
                        getMessageProducer("event-300").write(vertxMessageRecord);

                        break;
                    }

                    default: {
                        break;
                    }
                }

                socket.write(gson.toJson(vertxMessageRecord));
            });

            socket.closeHandler(v -> System.out.println("Client disconnected: " + socket.remoteAddress()));
        }).listen(1234, result -> {
            if(result.succeeded()) {
                System.out.println("\nServer started on port 1234");
                startPromise.complete();
            } else {
                System.out.println("\nServer start fail: " + result.cause().getMessage());
                startPromise.fail(result.cause());
            }
        });
    }
}