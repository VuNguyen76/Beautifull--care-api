package com.dailycodework.beautifulcare.service;

import com.dailycodework.beautifulcare.dto.request.SpecialistCreateRequest;
import com.dailycodework.beautifulcare.dto.response.SpecialistResponse;
import com.dailycodework.beautifulcare.dto.response.WorkScheduleResponse;

import java.util.List;

public interface SpecialistService {
    SpecialistResponse createSpecialist(SpecialistCreateRequest request);

    List<SpecialistResponse> getAllSpecialists();

    SpecialistResponse getSpecialistById(String id);

    SpecialistResponse updateSpecialist(String id, SpecialistCreateRequest request);

    void deleteSpecialist(String id);

    List<WorkScheduleResponse> getSpecialistSchedule(String id);

    List<SpecialistResponse> getSpecialistsByService(String serviceId);
}