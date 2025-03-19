package com.dailycodework.beautifulcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "services")
@EntityListeners(AuditingEntityListener.class)
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String description;
    private double price;
    private int durationMinutes;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private SkinType suitableForSkinType;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    @ManyToMany(mappedBy = "services")
    private Set<Specialist> specialists = new HashSet<>();

    @OneToMany(mappedBy = "service")
    private List<BookingDetail> bookingDetails = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}