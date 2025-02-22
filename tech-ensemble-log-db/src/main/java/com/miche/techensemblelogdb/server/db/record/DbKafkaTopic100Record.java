package com.miche.techensemblelogdb.server.db.record;

public record DbKafkaTopic100Record(
        String verticleType,

        String commandType,

        String message
) {
    public static DbKafkaTopic100Record of(String verticleType, String commandType, String message) {
        return new DbKafkaTopic100Record(verticleType, commandType, message);
    }
}