package core.acthree.record;

public record ThreeUserResponseRecord(
        String uuid,

        String userId,

        String userName
) {
    public static ThreeUserResponseRecord of(String uuid, String userId, String userName) {
        return new ThreeUserResponseRecord(uuid, userId, userName);
    }
}