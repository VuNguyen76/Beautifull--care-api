package com.dailycodework.beautifulcare.service.impl;

import com.dailycodework.beautifulcare.dto.request.ServiceCreateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceResponse;
import com.dailycodework.beautifulcare.entity.ServiceCategory;
import com.dailycodework.beautifulcare.entity.ServiceEntity;
import com.dailycodework.beautifulcare.exception.AppException;
import com.dailycodework.beautifulcare.exception.ErrorCode;
import com.dailycodework.beautifulcare.mapper.ServiceMapper;
import com.dailycodework.beautifulcare.repository.ServiceCategoryRepository;
import com.dailycodework.beautifulcare.repository.ServiceRepository;
import com.dailycodework.beautifulcare.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public ServiceResponse createService(ServiceCreateRequest request) {
        ServiceCategory category = serviceCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_CATEGORY_NOT_FOUND));

        ServiceEntity service = serviceMapper.toService(request, category);
        ServiceEntity savedService = serviceRepository.save(service);
        return serviceMapper.toServiceResponse(savedService);
    }

    @Override
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(serviceMapper::toServiceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceResponse getServiceById(String id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        return serviceMapper.toServiceResponse(service);
    }

    @Override
    public ServiceResponse updateService(String id, ServiceCreateRequest request) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        ServiceCategory category = serviceCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_CATEGORY_NOT_FOUND));

        serviceMapper.updateService(service, request, category);
        ServiceEntity updatedService = serviceRepository.save(service);
        return serviceMapper.toServiceResponse(updatedService);
    }

    @Override
    public void deleteService(String id) {
        if (!serviceRepository.existsById(id)) {
            throw new AppException(ErrorCode.SERVICE_NOT_FOUND);
        }
        serviceRepository.deleteById(id);
    }

    @Override
    public List<ServiceResponse> getServicesByCategory(String categoryId) {
        return serviceRepository.findByCategoryId(categoryId).stream()
                .map(serviceMapper::toServiceResponse)
                .collect(Collectors.toList());
    }
}