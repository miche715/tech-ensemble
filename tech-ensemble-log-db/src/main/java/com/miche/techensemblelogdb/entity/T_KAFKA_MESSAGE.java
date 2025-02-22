package com.miche.techensemblelogdb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_kafka_message")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class T_KAFKA_MESSAGE {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String verticleType;

    @Column(nullable = false)
    private String commandType;

    @Column()
    private String message;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;
}