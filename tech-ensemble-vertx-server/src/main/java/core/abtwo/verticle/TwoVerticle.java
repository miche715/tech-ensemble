package core.abtwo.verticle;

import common.code.CommonCode;
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

public class TwoVerticle extends BaseVerticle {
    private final Producer<String, String> kafkaProducer = new KafkaProducer<>(KafkaPropertyProvider.getKafkaProducerProperties());

    @Override
    public void start(Promise<Void> startPromise) {
        MessageConsumer<VertxMessageRecord> messageConsumer = vertx.eventBus().consumer(CommonCode.EVENT_BUS_ADDRESS_TWO);

        messageConsumer.handler((Message<VertxMessageRecord> message) -> {
            VertxMessageRecord vertxMessageRecord = message.body();
            System.out.println("TwoVerticle receive message: " + vertxMessageRecord);

            switch(vertxMessageRecord.commandType()) {
                case CommonCode.COMMAND_TYPE_A: {

                    break;
                }

                case CommonCode.COMMAND_TYPE_B: {
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(CommonCode.TOPIC_200, gson.toJson(vertxMessageRecord));

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

                case CommonCode.COMMAND_TYPE_C: {
                    getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_THREE).write(vertxMessageRecord);

                    break;
                }

                case CommonCode.COMMAND_TYPE_D: {

                    break;
                }

                default: {
                    break;
                }
            }
        });
    }
}