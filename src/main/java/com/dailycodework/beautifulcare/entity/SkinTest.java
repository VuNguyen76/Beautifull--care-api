package com.dailycodework.beautifulcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "skin_tests")
@EntityListeners(AuditingEntityListener.class)
public class SkinTest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String description;
    private boolean active = true;

    @OneToMany(mappedBy = "skinTest", cascade = CascadeType.ALL)
    private List<SkinTestQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "skinTest")
    private List<SkinTestResult> results = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}