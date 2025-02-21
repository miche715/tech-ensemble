package common.record;

public record VertxMessageRecord(
        String verticleType,  // one, two, three

        String commandType,  // a, b, c

        Object message
)  {
    public static VertxMessageRecord of(String verticleType, String commandType, Object message) {
        return new VertxMessageRecord(verticleType, commandType, message);
    }
}