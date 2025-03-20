package com.dailycodework.beautifulcare.mapper;

import com.dailycodework.beautifulcare.dto.request.ServiceCreateRequest;
import com.dailycodework.beautifulcare.dto.request.ServiceUpdateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceResponse;
import com.dailycodework.beautifulcare.entity.Service;
import com.dailycodework.beautifulcare.entity.ServiceCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper interface for Service entity.
 * Implementation is provided by ServiceMapperImpl.
 */
@Mapper(componentModel = "spring")
public interface ServiceMapper {

    /**
     * Maps a Service entity to a ServiceResponse DTO.
     *
     * @param service the service entity to map
     * @return the mapped service response DTO
     */
    @Mapping(target = "categoryName", source = "category.name")
    ServiceResponse toServiceResponse(Service service);

    /**
     * Maps a ServiceCreateRequest DTO to a Service entity.
     *
     * @param request  the service create request DTO
     * @param category the service category entity
     * @return the mapped service entity
     */
    Service toService(ServiceCreateRequest request, ServiceCategory category);

    /**
     * Updates a Service entity with values from a ServiceUpdateRequest DTO.
     *
     * @param service  the service entity to update
     * @param request  the service update request DTO
     * @param category the service category entity
     */
    void updateService(Service service, ServiceUpdateRequest request, ServiceCategory category);

    List<ServiceResponse> toServiceResponseList(List<Service> services);
}