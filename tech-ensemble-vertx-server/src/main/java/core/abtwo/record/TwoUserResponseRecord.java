package core.abtwo.record;

public record TwoUserResponseRecord(
        String uuid,

        String userId,

        String userName
) {
    public static TwoUserResponseRecord of(String uuid, String userId, String userName) {
        return new TwoUserResponseRecord(uuid, userId, userName);
    }
}