package com.miche.techensemblewebsocket.server.bypass.record;

public record BypassTopic200ResponseRecord(
        String verticleType,

        String commandType,

        String message
) {
    public static BypassTopic200ResponseRecord of(String verticleType, String commandType, String message) {
        return new BypassTopic200ResponseRecord(verticleType, commandType, message);
    }
}