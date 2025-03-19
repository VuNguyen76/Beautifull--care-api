package com.dailycodework.beautifulcare.service.impl;

import com.dailycodework.beautifulcare.dto.request.SpecialistCreateRequest;
import com.dailycodework.beautifulcare.dto.response.SpecialistResponse;
import com.dailycodework.beautifulcare.dto.response.WorkScheduleResponse;
import com.dailycodework.beautifulcare.entity.ServiceEntity;
import com.dailycodework.beautifulcare.entity.Specialist;
import com.dailycodework.beautifulcare.entity.UserRole;
import com.dailycodework.beautifulcare.exception.AppException;
import com.dailycodework.beautifulcare.exception.ErrorCode;
import com.dailycodework.beautifulcare.mapper.SpecialistMapper;
import com.dailycodework.beautifulcare.mapper.WorkScheduleMapper;
import com.dailycodework.beautifulcare.repository.ServiceRepository;
import com.dailycodework.beautifulcare.repository.SpecialistRepository;
import com.dailycodework.beautifulcare.repository.WorkScheduleRepository;
import com.dailycodework.beautifulcare.service.SpecialistService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecialistServiceImpl implements SpecialistService {
    SpecialistRepository specialistRepository;
    ServiceRepository serviceRepository;
    WorkScheduleRepository workScheduleRepository;
    SpecialistMapper specialistMapper;
    WorkScheduleMapper workScheduleMapper;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SpecialistResponse createSpecialist(SpecialistCreateRequest request) {
        if (specialistRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Specialist specialist = specialistMapper.toSpecialist(request);
        specialist.setPassword(passwordEncoder.encode(request.getPassword()));
        specialist.setRole(UserRole.SPECIALIST);

        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            Set<ServiceEntity> services = new HashSet<>();
            for (String serviceId : request.getServiceIds()) {
                ServiceEntity service = serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
                services.add(service);
            }
            specialist.setServices(services);
        }

        return specialistMapper.toSpecialistResponse(specialistRepository.save(specialist));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialistResponse> getAllSpecialists() {
        return specialistRepository.findAll().stream()
                .map(specialistMapper::toSpecialistResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialistResponse getSpecialistById(String id) {
        return specialistMapper.toSpecialistResponse(findSpecialistById(id));
    }

    @Override
    @Transactional
    public SpecialistResponse updateSpecialist(String id, SpecialistCreateRequest request) {
        Specialist specialist = findSpecialistById(id);

        specialistMapper.updateSpecialist(specialist, request);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            specialist.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            Set<ServiceEntity> services = new HashSet<>();
            for (String serviceId : request.getServiceIds()) {
                ServiceEntity service = serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
                services.add(service);
            }
            specialist.setServices(services);
        }

        return specialistMapper.toSpecialistResponse(specialistRepository.save(specialist));
    }

    @Override
    @Transactional
    public void deleteSpecialist(String id) {
        Specialist specialist = findSpecialistById(id);
        specialistRepository.delete(specialist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkScheduleResponse> getSpecialistSchedule(String id) {
        // Verify specialist exists
        findSpecialistById(id);

        return workScheduleRepository.findBySpecialistId(id).stream()
                .map(workScheduleMapper::toWorkScheduleResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialistResponse> getSpecialistsByService(String serviceId) {
        return specialistRepository.findByServicesId(serviceId).stream()
                .map(specialistMapper::toSpecialistResponse)
                .collect(Collectors.toList());
    }

    private Specialist findSpecialistById(String id) {
        return specialistRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SPECIALIST_NOT_FOUND));
    }
}