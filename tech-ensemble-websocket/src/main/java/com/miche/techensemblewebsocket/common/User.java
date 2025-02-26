package com.miche.techensemblewebsocket.common;

import com.miche.techensemblewebsocket.common.base.BaseClass;
import com.miche.techensemblewebsocket.common.send.ResponseRecord;
import jakarta.websocket.Session;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Getter
@Setter
public class User extends BaseClass {
    private final Logger logger = LoggerFactory.getLogger(User.class);
    
    private Session session;

    public User(Session session) {
        this.session = session;
    }

    public synchronized void sendOne(String serviceName, String methodName, Object data) {
        try {
            if(this.session.isOpen()) {
                send(gson.toJson(ResponseRecord.of(serviceName, methodName, data)));
            }
        } catch(IOException e) {
            logger.info("{}exception -> {}", super.getLogPrefix(new Object(){}), e.getMessage());
        }
    }

    private void send(String json) throws IOException {
        this.session.getBasicRemote().sendText(json);
    }
}