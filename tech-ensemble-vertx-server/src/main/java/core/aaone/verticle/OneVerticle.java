package core.aaone.verticle;

import common.kafka.KafkaPropertyProvider;
import common.record.VertxMessageRecord;
import common.vertx.BaseVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.CompletableFuture;

public class OneVerticle extends BaseVerticle {
    private final Producer<String, String> kafkaProducer = new KafkaProducer<>(KafkaPropertyProvider.getKafkaProducerProperties());

    @Override
    public void start(Promise<Void> startPromise) {
        MessageConsumer<VertxMessageRecord> messageConsumer = vertx.eventBus().consumer("event-100");  // event-100 주소로 부터 받음.

        messageConsumer.handler((Message<VertxMessageRecord> message) -> {
            VertxMessageRecord vertxMessageRecord = message.body();
            System.out.println("OneVerticle receive message: " + vertxMessageRecord);

            switch(vertxMessageRecord.commandType()) {
                case "a": {
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-100", gson.toJson(vertxMessageRecord));

                    CompletableFuture.runAsync(() -> {
                        try {
                            kafkaProducer.send(producerRecord);
                        } catch(Exception e) {
                            System.out.println("Failed to send Kafka message1: " + e.getMessage());
                        }
                    }).thenAccept(metadata -> {
                        System.out.println("Kafka message sent successfully");
                    }).exceptionally(ex -> {
                        System.err.println("Failed to send Kafka message2: " + ex.getMessage());

                        return null;
                    });

                    break;
                }

                case "b": {
                    System.out.println("bb " + vertxMessageRecord);

                    break;
                }

                case "c": {
                    getMessageProducer("event-200").write(vertxMessageRecord);

                    break;
                }

                default: {
                    break;
                }
            }
        });
    }
}