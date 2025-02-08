package com.miche.techensemblemain.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

/*
    실제 Kafka Broker에 메세지를 보내는 역할을 하는
    kafkaProducerTemplate을 구성하고 Bean으로 등록하는 역할을 함.
*/

@Configuration
@EnableKafka
public class KafkaProducerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapSevers;

    public Map<String, Object> kafkaProducerProperty() {
        return Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapSevers,
                      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    }

    public ProducerFactory<String, String> kafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(this.kafkaProducerProperty());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaProducerTemplate() {
        return new KafkaTemplate<>(this.kafkaProducerFactory());
    }

    /* 어플리케이션이 실행될 때 자동으로 토픽 생성. */
    @Bean
    public NewTopic createTopic100() {
        return new NewTopic("topic-100", 1, (short)1);
    }
}