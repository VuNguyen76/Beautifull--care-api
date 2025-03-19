package com.dailycodework.beautifulcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "blogs")
@EntityListeners(AuditingEntityListener.class)
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String content;
    private String imageUrl;
    private String slug;
    private boolean published = false;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BlogCategory category;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;
}