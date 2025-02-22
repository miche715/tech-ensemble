package com.miche.techensemblelogdb.server.db.record;

public record DbKafkaTopic200Record(
        String verticleType,

        String commandType,

        String message
) {
    public static DbKafkaTopic200Record of(String verticleType, String commandType, String message) {
        return new DbKafkaTopic200Record(verticleType, commandType, message);
    }
}