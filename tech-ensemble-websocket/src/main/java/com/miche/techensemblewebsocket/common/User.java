package com.miche.techensemblewebsocket.common;

import com.google.gson.Gson;
import com.miche.techensemblewebsocket.common.gson.GsonProvider;
import com.miche.techensemblewebsocket.common.send.ResponseRecord;
import jakarta.websocket.Session;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class User {
    private final Gson gson = GsonProvider.getInstance();
    
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
            System.out.println("sendOne Error: " + e.getMessage());
        }
    }

    private void send(String json) throws IOException {
        this.session.getBasicRemote().sendText(json);
    }
}