package com.miche.techensemblesub1.api.user.listenr;

import com.miche.techensemblesub1.api.user.service.UserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener {
    private UserService userService;

    @Autowired
    public UserListener(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "topic-100", groupId = "group-100")
    public void getUserList1(ConsumerRecord<String, String> consumerRecord) {
        System.out.println(consumerRecord);
    }

    @KafkaListener(topics = "topic-200", groupId = "group-200")
    public void getUserList2(ConsumerRecord<String, String> consumerRecord) {
        System.out.println(consumerRecord);
    }

    @KafkaListener(topics = "topic-300", groupId = "group-300")
    public void getUserList3(ConsumerRecord<String, String> consumerRecord) {
        System.out.println(consumerRecord);
    }
}