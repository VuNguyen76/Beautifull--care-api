package com.dailycodework.beautifulcare.repository;

import com.dailycodework.beautifulcare.entity.ServiceEntity;
import com.dailycodework.beautifulcare.entity.SkinType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
    List<ServiceEntity> findByCategoryId(String categoryId);

    List<ServiceEntity> findByNameContainingIgnoreCase(String name);

    List<ServiceEntity> findByPriceBetween(double minPrice, double maxPrice);

    List<ServiceEntity> findBySpecialistsId(String specialistId);

    List<ServiceEntity> findBySuitableForSkinType(SkinType skinType);
}