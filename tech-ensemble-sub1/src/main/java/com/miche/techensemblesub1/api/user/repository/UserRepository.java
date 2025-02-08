package com.miche.techensemblesub1.api.user.repository;

import com.miche.techensemblesub1.entity.T_USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<T_USER, String> {

}