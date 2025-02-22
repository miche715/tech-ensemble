package com.miche.techensemblelogdb.server.db.record;

public record DbKafkaTopic300Record(
        String verticleType,

        String commandType,

        String message
) {
    public static DbKafkaTopic300Record of(String verticleType, String commandType, String message) {
        return new DbKafkaTopic300Record(verticleType, commandType, message);
    }
}