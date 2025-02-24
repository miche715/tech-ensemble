package com.miche.techensemblewebsocket.server.bypass.record;

public record BypassTopic100ResponseRecord(
        String verticleType,

        String commandType,

        String message
) {
    public static BypassTopic100ResponseRecord of(String verticleType, String commandType, String message) {
        return new BypassTopic100ResponseRecord(verticleType, commandType, message);
    }
}