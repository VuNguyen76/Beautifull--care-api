package com.dailycodework.beautifulcare.service.impl;

import com.dailycodework.beautifulcare.dto.request.ServiceCategoryCreateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceCategoryResponse;
import com.dailycodework.beautifulcare.entity.ServiceCategory;
import com.dailycodework.beautifulcare.exception.AppException;
import com.dailycodework.beautifulcare.exception.ErrorCode;
import com.dailycodework.beautifulcare.mapper.ServiceCategoryMapper;
import com.dailycodework.beautifulcare.repository.ServiceCategoryRepository;
import com.dailycodework.beautifulcare.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceCategoryMapper serviceCategoryMapper;

    @Override
    @Transactional
    public ServiceCategoryResponse createServiceCategory(ServiceCategoryCreateRequest request) {
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setName(request.getName());
        serviceCategory.setDescription(request.getDescription());
        serviceCategory.setImageUrl(request.getImageUrl());

        ServiceCategory savedCategory = serviceCategoryRepository.save(serviceCategory);
        return serviceCategoryMapper.toServiceCategoryResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCategoryResponse> getAllServiceCategories() {
        List<ServiceCategory> categories = serviceCategoryRepository.findAll();
        return categories.stream()
                .map(serviceCategoryMapper::toServiceCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceCategoryResponse getServiceCategoryById(String id) {
        ServiceCategory category = findServiceCategoryById(id);
        return serviceCategoryMapper.toServiceCategoryResponse(category);
    }

    @Override
    @Transactional
    public ServiceCategoryResponse updateServiceCategory(String id, ServiceCategoryCreateRequest request) {
        ServiceCategory category = findServiceCategoryById(id);

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        ServiceCategory updatedCategory = serviceCategoryRepository.save(category);
        return serviceCategoryMapper.toServiceCategoryResponse(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteServiceCategory(String id) {
        ServiceCategory category = findServiceCategoryById(id);
        serviceCategoryRepository.delete(category);
    }

    private ServiceCategory findServiceCategoryById(String id) {
        return serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_CATEGORY_NOT_FOUND));
    }
}