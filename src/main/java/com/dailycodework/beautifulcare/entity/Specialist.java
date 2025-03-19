package com.dailycodework.beautifulcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "specialists")
public class Specialist extends User {
    private String bio;
    private String expertise;
    private int yearsOfExperience;
    private String certifications;

    @OneToMany(mappedBy = "specialist")
    private List<WorkSchedule> workSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "specialist")
    private List<Treatment> treatments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "specialist_services", joinColumns = @JoinColumn(name = "specialist_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<ServiceEntity> services = new HashSet<>();
}