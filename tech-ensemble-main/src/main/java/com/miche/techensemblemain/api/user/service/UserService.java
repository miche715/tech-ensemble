package com.miche.techensemblemain.api.user.service;

import com.miche.techensemblemain.api.user.record.UserListResponseRecord;
import com.miche.techensemblemain.api.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserListResponseRecord> getUserList() {
        return userRepository.findAll()
                             .stream()
                             .map((t_user) -> {
                                 return UserListResponseRecord.builder()
                                         .uuid(t_user.getUuid())
                                         .userId(t_user.getUserId())
                                         .userName(t_user.getUserName())
                                         .createDate(t_user.getCreateDate())
                                         .updateDate(t_user.getUpdateDate())
                                         .build();
        }).toList();
    }
}