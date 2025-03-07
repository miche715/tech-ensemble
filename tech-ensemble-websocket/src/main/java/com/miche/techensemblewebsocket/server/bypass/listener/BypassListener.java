package com.miche.techensemblewebsocket.server.bypass.listener;

import com.miche.techensemblewebsocket.common.base.BaseClass;
import com.miche.techensemblewebsocket.common.code.CommonCode;
import com.miche.techensemblewebsocket.server.bypass.record.BypassTopic100ResponseRecord;
import com.miche.techensemblewebsocket.server.bypass.record.BypassTopic200ResponseRecord;
import com.miche.techensemblewebsocket.server.bypass.record.BypassTopic300ResponseRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BypassListener extends BaseClass {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public BypassListener(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = CommonCode.TOPIC_100, groupId = CommonCode.GROUP_101)
    public void listenTopic100(ConsumerRecord<String, String> consumerRecord) {
        BypassTopic100ResponseRecord bypassTopic100ResponseRecord = gson.fromJson(consumerRecord.value(), BypassTopic100ResponseRecord.class);
        logger.info("{}receive complete -> {}", super.getLogPrefix(new Object(){}), bypassTopic100ResponseRecord);

        sender.sendAll("BypassListener", "listenTopic100", bypassTopic100ResponseRecord);
        logger.info("{}send complete", super.getLogPrefix(new Object(){}));

        redisTemplate.opsForList().rightPush(CommonCode.TOPIC_100, bypassTopic100ResponseRecord);
        redisTemplate.opsForList().rightPush(CommonCode.TOPIC, bypassTopic100ResponseRecord);
        logger.info("{}redis save complete", super.getLogPrefix(new Object(){}));
    }

    @KafkaListener(topics = CommonCode.TOPIC_200, groupId = CommonCode.GROUP_201)
    public void listenTopic200(ConsumerRecord<String, String> consumerRecord) {
        BypassTopic200ResponseRecord bypassTopic200ResponseRecord = gson.fromJson(consumerRecord.value(), BypassTopic200ResponseRecord.class);
        logger.info("{}receive complete -> {}", super.getLogPrefix(new Object(){}), bypassTopic200ResponseRecord);

        sender.sendAll("BypassListener", "listenTopic200", bypassTopic200ResponseRecord);
        logger.info("{}send complete", super.getLogPrefix(new Object(){}));

        redisTemplate.opsForList().rightPush(CommonCode.TOPIC_200, bypassTopic200ResponseRecord);
        redisTemplate.opsForList().rightPush(CommonCode.TOPIC, bypassTopic200ResponseRecord);
        logger.info("{}redis save complete", super.getLogPrefix(new Object(){}));
    }

    @KafkaListener(topics = CommonCode.TOPIC_300, groupId = CommonCode.GROUP_301)
    public void listenTopic300(ConsumerRecord<String, String> consumerRecord) {
        BypassTopic300ResponseRecord bypassTopic300ResponseRecord = gson.fromJson(consumerRecord.value(), BypassTopic300ResponseRecord.class);
        logger.info("{}receive complete -> {}", super.getLogPrefix(new Object(){}), bypassTopic300ResponseRecord);

        sender.sendAll("BypassListener", "listenTopic300", bypassTopic300ResponseRecord);
        logger.info("{}send complete", super.getLogPrefix(new Object(){}));

        redisTemplate.opsForList().rightPush(CommonCode.TOPIC_300, bypassTopic300ResponseRecord);
        redisTemplate.opsForList().rightPush(CommonCode.TOPIC, bypassTopic300ResponseRecord);
        logger.info("{}redis save complete", super.getLogPrefix(new Object(){}));
    }
}