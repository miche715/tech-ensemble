package core.acthree.verticle;

import common.code.CommonCode;
import common.kafka.KafkaPropertyProvider;
import common.record.VertxMessageRecord;
import common.sql.SqlQueryString;
import common.vertx.BaseVerticle;
import core.aaone.record.OneUserResponseRecord;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.sqlclient.Row;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class ThreeVerticle extends BaseVerticle {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Producer<String, String> kafkaProducer = new KafkaProducer<>(KafkaPropertyProvider.getKafkaProducerProperties());

    @Override
    public void start(Promise<Void> startPromise) {
        MessageConsumer<VertxMessageRecord> messageConsumer = vertx.eventBus().consumer(CommonCode.EVENT_BUS_ADDRESS_THREE);

        messageConsumer.handler((Message<VertxMessageRecord> message) -> {
            VertxMessageRecord vertxMessageRecord = message.body();

            switch(vertxMessageRecord.commandType()) {
                case CommonCode.COMMAND_TYPE_A: {
                    logger.info("{}A receive event complete", super.getLogPrefix(new Object(){}));
                    String uuid = "'" + vertxMessageRecord.verticleType() + "_uuid" + "'";

                    sqlClient.query(SqlQueryString.selectUser + uuid)
                            .execute()
                            .onSuccess(rows -> {
                                VertxMessageRecord replyVertxMessageRecord = null;

                                for(Row row : rows) {
                                    replyVertxMessageRecord = VertxMessageRecord.of(
                                            vertxMessageRecord.verticleType(),
                                            vertxMessageRecord.commandType(),
                                            OneUserResponseRecord.of(
                                                    row.getString("uuid"),
                                                    row.getString("user_id"),
                                                    row.getString("user_name")));
                                }

                                message.reply(replyVertxMessageRecord);
                            })
                            .onFailure(throwable -> logger.error("{}A query exception -> {}", super.getLogPrefix(new Object(){}), throwable.getMessage()));

                    break;
                }

                case CommonCode.COMMAND_TYPE_B: {
                    logger.info("{}B receive event complete", super.getLogPrefix(new Object(){}));
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(CommonCode.TOPIC_300, gson.toJson(vertxMessageRecord));

                    CompletableFuture.runAsync(() -> {
                        kafkaProducer.send(producerRecord, (metadata, exception) -> {
                            if(exception == null) {
                                logger.info("{}B kafka send complete -> {}", super.getLogPrefix(new Object(){}), metadata);
                            } else {
                                logger.error("{}B exception -> {}", super.getLogPrefix(new Object(){}), exception.getMessage());
                            }
                        });
                    });

                    break;
                }

                case CommonCode.COMMAND_TYPE_C: {
                    getMessageProducer(CommonCode.EVENT_BUS_ADDRESS_ONE).write(vertxMessageRecord);

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