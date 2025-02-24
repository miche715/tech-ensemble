package com.miche.techensemblewebsocket;

import com.miche.techensemblewebsocket.common.Define;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechEnsembleWebsocketApplication {
    public static void main(String[] args) {
        Define.CONTEXT = SpringApplication.run(TechEnsembleWebsocketApplication.class, args);
    }
}