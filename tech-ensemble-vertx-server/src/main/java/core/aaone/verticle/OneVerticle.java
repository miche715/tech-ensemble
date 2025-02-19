package core.aaone.verticle;

import common.code.CommonCode;
import common.kafka.KafkaPropertyProvider;
import common.record.VertxMessageRecord;
import common.vertx.BaseVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.sqlclient.Row;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.CompletableFuture;

public class OneVerticle extends BaseVerticle {
    private final Producer<String, String> kafkaProducer = new KafkaProducer<>(KafkaPropertyProvider.getKafkaProducerProperties());

    @Override
    public void start(Promise<Void> startPromise) {
        MessageConsumer<VertxMessageRecord> messageConsumer = vertx.eventBus().consumer(CommonCode.EVENT_BUS_ADDRESS_ONE);

        messageConsumer.handler((Message<VertxMessageRecord> message) -> {
            System.out.print("OneVerticle ");
            VertxMessageRecord vertxMessageRecord = message.body();

            switch(vertxMessageRecord.commandType()) {
                case CommonCode.COMMAND_TYPE_A: {
                    System.out.println("A");

                    sqlClient.query("SELECT uuid, user_id FROM t_user")
                             .execute()
                             .onSuccess(rows -> {
                                 for(Row row : rows) {
                                     System.out.println("User ID: " + row.getString("uuid") + ", Name: " + row.getString("user_id"));
                                 }
                             })
                             .onFailure(err -> System.err.println("Query Failed: " + err.getMessage()));

                    break;
                }

                case CommonCode.COMMAND_TYPE_B: {
                    System.out.println("B");
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(CommonCode.TOPIC_100, gson.toJson(vertxMessageRecord));

                    CompletableFuture.runAsync(() -> {
                        kafkaProducer.send(producerRecord, (metadata, exception) -> {
                            if(exception == null) {
                                System.out.println("Kafka message sent successfully: " + metadata);
                            } else {
                                System.err.println("Failed to send Kafka message: " + exception.getMessage());
                            }
                        });
                    });

                    break;
                }

                case CommonCode.COMMAND_TYPE_C: {
                    System.out.println("C");
                    getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_TWO).write(vertxMessageRecord);

                    break;
                }

                case CommonCode.COMMAND_TYPE_D: {
                    System.out.println("D");

                    break;
                }

                default: {
                    break;
                }
            }
        });
    }
}