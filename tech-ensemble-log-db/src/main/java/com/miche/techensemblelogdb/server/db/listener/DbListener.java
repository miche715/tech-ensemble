package com.miche.techensemblelogdb.server.db.listener;

import com.google.gson.Gson;
import com.miche.techensemblelogdb.common.code.CommonCode;
import com.miche.techensemblelogdb.common.gson.GsonProvider;
import com.miche.techensemblelogdb.entity.T_KAFKA_MESSAGE;
import com.miche.techensemblelogdb.server.db.record.DbKafkaTopic100Record;
import com.miche.techensemblelogdb.server.db.record.DbKafkaTopic200Record;
import com.miche.techensemblelogdb.server.db.record.DbKafkaTopic300Record;
import com.miche.techensemblelogdb.server.db.repository.DbRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DbListener {
    private final Gson gson = GsonProvider.getInstance();
    private final DbRepository dbRepository;

    @Autowired
    public DbListener(DbRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @KafkaListener(topics = CommonCode.TOPIC_100, groupId = CommonCode.GROUP_100)
    public void listenTopic100(ConsumerRecord<String, String> consumerRecord) {
        DbKafkaTopic100Record dbKafkaTopic100Record = gson.fromJson(consumerRecord.value(), DbKafkaTopic100Record.class);
        System.out.println("\nlistenTopic100 received: " + dbKafkaTopic100Record);

        T_KAFKA_MESSAGE tKafkaMessage = T_KAFKA_MESSAGE.builder()
                                                       .verticleType(dbKafkaTopic100Record.verticleType())
                                                       .commandType(dbKafkaTopic100Record.commandType())
                                                       .message(dbKafkaTopic100Record.message())
                                                       .build();
        dbRepository.save(tKafkaMessage);
        System.out.println("listenTopic100 save complete");
    }

    @KafkaListener(topics = CommonCode.TOPIC_200, groupId = CommonCode.GROUP_200)
    public void listenTopic200(ConsumerRecord<String, String> consumerRecord) {
        DbKafkaTopic200Record dbKafkaTopic200Record = gson.fromJson(consumerRecord.value(), DbKafkaTopic200Record.class);
        System.out.println("\nlistenTopic200 received: " + dbKafkaTopic200Record);

        T_KAFKA_MESSAGE tKafkaMessage = T_KAFKA_MESSAGE.builder()
                .verticleType(dbKafkaTopic200Record.verticleType())
                .commandType(dbKafkaTopic200Record.commandType())
                .message(dbKafkaTopic200Record.message())
                .build();
        dbRepository.save(tKafkaMessage);
        System.out.println("listenTopic200 save complete");
    }

    @KafkaListener(topics = CommonCode.TOPIC_300, groupId = CommonCode.GROUP_300)
    public void listenTopic300(ConsumerRecord<String, String> consumerRecord) {
        DbKafkaTopic300Record dbKafkaTopic300Record = gson.fromJson(consumerRecord.value(), DbKafkaTopic300Record.class);
        System.out.println("\nlistenTopic300 received: " + dbKafkaTopic300Record);

        T_KAFKA_MESSAGE tKafkaMessage = T_KAFKA_MESSAGE.builder()
                .verticleType(dbKafkaTopic300Record.verticleType())
                .commandType(dbKafkaTopic300Record.commandType())
                .message(dbKafkaTopic300Record.message())
                .build();
        dbRepository.save(tKafkaMessage);
        System.out.println("listenTopic300 save complete");
    }
}