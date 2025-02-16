package core.three.verticle;

import com.google.gson.Gson;
import common.gson.GsonProvider;
import common.kafka.KafkaPropertyProvider;
import common.record.VertxMessageRecord;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.CompletableFuture;

public class ThreeVerticle extends AbstractVerticle {
    private final Gson gson = GsonProvider.getInstance();
    private final Producer<String, String> kafkaProducer = new KafkaProducer<>(KafkaPropertyProvider.getKafkaProducerProperties());

    @Override
    public void start(Promise<Void> startPromise) {
        MessageConsumer<VertxMessageRecord> messageConsumer = vertx.eventBus().consumer("event-300");

        messageConsumer.handler((Message<VertxMessageRecord> message) -> {
            System.out.println("ThreeVerticle");

            VertxMessageRecord vertxMessageRecord = message.body();
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-300", gson.toJson(vertxMessageRecord));

            CompletableFuture.runAsync(() -> {
                kafkaProducer.send(producerRecord);
            }).thenAccept(metadata -> {
                System.out.println("Kafka message sent successfully: " + metadata);
            }).exceptionally(ex -> {
                System.err.println("Failed to send Kafka message: " + ex.getMessage());

                return null;
            });

//            vertx.executeBlocking(promise -> {
//                try {
//                    VertxMessageRecord vertxMessageRecord = message.body();
//                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-300", gson.toJson(vertxMessageRecord));
//
//                    kafkaProducer.send(producerRecord);
//                    promise.complete();
//                } catch (Exception e) {
//                    promise.fail(e);
//                }
//            }, res -> {
//                if(res.succeeded()) {
//                    System.out.println("Kafka message sent successfully");
//                } else {
//                    System.err.println("Failed to send Kafka message: " + res.cause());
//                }
//            });
        });
    }
}