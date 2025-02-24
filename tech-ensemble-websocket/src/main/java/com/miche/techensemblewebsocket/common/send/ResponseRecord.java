package com.miche.techensemblewebsocket.common.send;

public record ResponseRecord(
        String serviceName,

        String methodName,

        Object data
) {
    public static ResponseRecord of(String serviceName, String methodName, Object data) {
        return new ResponseRecord(serviceName, methodName, data);
    }
}