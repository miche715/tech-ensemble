package com.miche.techensemblemain.api.user.record;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserListResponseRecord {
    private String uuid;

    private String userId;

    private String userName;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}