package core.aaone.record;

public record OneUserResponseRecord(
        String uuid,

        String userId,

        String userName
) {
    public static OneUserResponseRecord of(String uuid, String userId, String userName) {
        return new OneUserResponseRecord(uuid, userId, userName);
    }
}