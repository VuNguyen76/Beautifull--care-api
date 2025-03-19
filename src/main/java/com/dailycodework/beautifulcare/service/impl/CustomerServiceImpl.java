package com.dailycodework.beautifulcare.service.impl;

import com.dailycodework.beautifulcare.dto.request.CustomerCreateRequest;
import com.dailycodework.beautifulcare.dto.response.BookingResponse;
import com.dailycodework.beautifulcare.dto.response.CustomerResponse;
import com.dailycodework.beautifulcare.dto.response.SkinTestResultResponse;
import com.dailycodework.beautifulcare.entity.Customer;
import com.dailycodework.beautifulcare.entity.UserRole;
import com.dailycodework.beautifulcare.exception.AppException;
import com.dailycodework.beautifulcare.exception.ErrorCode;
import com.dailycodework.beautifulcare.mapper.CustomerMapper;
import com.dailycodework.beautifulcare.repository.BookingRepository;
import com.dailycodework.beautifulcare.repository.CustomerRepository;
import com.dailycodework.beautifulcare.repository.SkinTestResultRepository;
import com.dailycodework.beautifulcare.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    BookingRepository bookingRepository;
    SkinTestResultRepository skinTestResultRepository;
    CustomerMapper customerMapper;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Customer customer = customerMapper.toCustomer(request);
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setRole(UserRole.CUSTOMER);

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return customerMapper.toCustomerResponse(findCustomerById(id));
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(String id, CustomerCreateRequest request) {
        Customer customer = findCustomerById(id);

        customerMapper.updateCustomer(customer, request);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public void deleteCustomer(String id) {
        Customer customer = findCustomerById(id);
        customerRepository.delete(customer);
    }

    @Override
    public List<BookingResponse> getCustomerBookings(String id) {
        // Verify customer exists
        findCustomerById(id);

        // This would be implemented with proper mapping to BookingResponse
        // For now, returning empty list as mapping is not fully implemented
        return List.of();
    }

    @Override
    public List<SkinTestResultResponse> getCustomerSkinTestResults(String id) {
        // Verify customer exists
        findCustomerById(id);

        // This would be implemented with proper mapping to SkinTestResultResponse
        // For now, returning empty list as mapping is not fully implemented
        return List.of();
    }

    private Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }
}