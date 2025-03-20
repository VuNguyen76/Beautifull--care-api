package com.dailycodework.beautifulcare.mapper;

import com.dailycodework.beautifulcare.dto.request.SpecialistCreateRequest;
import com.dailycodework.beautifulcare.dto.request.SpecialistUpdateRequest;
import com.dailycodework.beautifulcare.dto.response.SpecialistDetailResponse;
import com.dailycodework.beautifulcare.dto.response.SpecialistResponse;
import com.dailycodework.beautifulcare.entity.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { ServiceMapper.class })
public interface SpecialistMapper {

    @Mapping(target = "user.password", ignore = true)
    Specialist toSpecialist(SpecialistCreateRequest request);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "active", source = "user.active")
    SpecialistResponse toSpecialistResponse(Specialist specialist);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "active", source = "user.active")
    @Mapping(target = "services", source = "services")
    SpecialistDetailResponse toDetailResponse(Specialist specialist);

    void updateSpecialistFromRequest(SpecialistUpdateRequest request, @MappingTarget Specialist specialist);
}