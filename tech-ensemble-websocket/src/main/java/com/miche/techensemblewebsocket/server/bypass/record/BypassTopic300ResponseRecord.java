package com.miche.techensemblewebsocket.server.bypass.record;

public record BypassTopic300ResponseRecord(
        String verticleType,

        String commandType,

        String message
) {
    public static BypassTopic300ResponseRecord of(String verticleType, String commandType, String message) {
        return new BypassTopic300ResponseRecord(verticleType, commandType, message);
    }
}