package com.miche.techensemblelogdb.server.db.repository;

import com.miche.techensemblelogdb.entity.T_KAFKA_MESSAGE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbRepository extends JpaRepository<T_KAFKA_MESSAGE, Long> {

}