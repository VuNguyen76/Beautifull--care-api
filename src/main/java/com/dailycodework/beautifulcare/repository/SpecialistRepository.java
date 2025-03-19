package com.dailycodework.beautifulcare.repository;

import com.dailycodework.beautifulcare.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, String> {
    Optional<Specialist> findByUsername(String username);

    Optional<Specialist> findByEmail(String email);

    List<Specialist> findByServicesId(String serviceId);

    List<Specialist> findByExpertiseContainingIgnoreCase(String expertise);
}