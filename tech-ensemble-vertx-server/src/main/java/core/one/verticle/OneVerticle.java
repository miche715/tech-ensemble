package core.one.verticle;

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
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class OneVerticle extends AbstractVerticle {
    private final Gson gson = GsonProvider.getInstance();
    private final Producer<String, String> kafkaProducer = new KafkaProducer<>(KafkaPropertyProvider.getKafkaProducerProperties());

    @Override
    public void start(Promise<Void> startPromise) {
        MessageConsumer<VertxMessageRecord> messageConsumer = vertx.eventBus().consumer("event-100");  // event-100 주소로 부터 받음.

        messageConsumer.handler((Message<VertxMessageRecord> message) -> {
            System.out.println("OneVerticle.");

            VertxMessageRecord vertxMessageRecord = message.body();

            /*
                vertxMessageRecord.commandType 값에 따른 분기.
            */

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic-100", gson.toJson(vertxMessageRecord));

            kafkaProducer.send(producerRecord);
        });
    }
}