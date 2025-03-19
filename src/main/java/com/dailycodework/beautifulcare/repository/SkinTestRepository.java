package com.dailycodework.beautifulcare.repository;

import com.dailycodework.beautifulcare.entity.SkinTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkinTestRepository extends JpaRepository<SkinTest, String> {
    Optional<SkinTest> findByName(String name);

    List<SkinTest> findByActive(boolean active);

    List<SkinTest> findByNameContainingIgnoreCase(String name);
}