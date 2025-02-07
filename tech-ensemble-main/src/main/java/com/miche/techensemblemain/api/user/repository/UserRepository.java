package com.miche.techensemblemain.api.user.repository;

import com.miche.techensemblemain.entity.T_USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<T_USER, String> {

}