package com.miche.techensemblewebsocket.common.send;

import com.miche.techensemblewebsocket.common.Define;
import com.miche.techensemblewebsocket.common.User;

import java.util.Map;

public class Sender {
    public static Sender getInstance() {
        return LazyHolder.SENDER;
    }

    private static class LazyHolder {
        private static final Sender SENDER = new Sender();
    }

    public void sendAll(String serviceName, String methodName, Object data) {
        Define.USERS.values()
                    .parallelStream()
                    .forEach(user -> {
                        user.sendOne(serviceName, methodName, data);
                    });
    }
}