package core.one.verticle;

import com.google.gson.Gson;
import common.gson.GsonProvider;
import common.kafka.KafkaPropertyProvider;
import common.record.VertxMessageRecord;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class OneVerticle extends AbstractVerticle {
    private final Gson gson = GsonProvider.getInstance();
    private final Producer<String, String> kafkaProducer = new KafkaProducer<>(KafkaPropertyProvider.getKafkaProducerProperties());

    @Override
    public void start(Promise<Void> startPromise) {
        MessageConsumer<VertxMessageRecord> messageConsumer = vertx.eventBus().consumer("event-100");  // event-100 주소로 부터 받음.

        messageConsumer.handler((Message<VertxMessageRecord> message) -> {
            System.out.println("OneVerticle");

            VertxMessageRecord vertxMessageRecord = message.body();
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-100", gson.toJson(vertxMessageRecord));

            CompletableFuture.runAsync(() -> {
                try {
                    Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send(producerRecord);

                    System.out.println("sdfsdfsdfsfsfs");
                    System.out.println("get: " + recordMetadataFuture.get());
                    System.out.println("isCancelled: " + recordMetadataFuture.isCancelled());
                    System.out.println("isDone: " + recordMetadataFuture.isDone());
                    System.out.println("topic: " + recordMetadataFuture.get().topic());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }).thenAccept(metadata -> {
                System.out.println("Kafka message sent successfully: " + metadata);
            }).exceptionally(ex -> {
                System.err.println("Failed to send Kafka message: " + ex.getMessage());

                return null;
            });

//            vertx.executeBlocking(promise -> {
//                try {
//                    VertxMessageRecord vertxMessageRecord = message.body();
//                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-100", gson.toJson(vertxMessageRecord));
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