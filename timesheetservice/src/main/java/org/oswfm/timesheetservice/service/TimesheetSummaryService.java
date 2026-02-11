package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.client.EmployeeServiceClient;
import org.oswfm.timesheetservice.model.dto.EmployeeDTO;
import org.oswfm.timesheetservice.model.entity.PayPeriods;
import org.oswfm.timesheetservice.model.entity.TimesheetOperationsTypes;
import org.oswfm.timesheetservice.model.entity.TimesheetSummary;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryResponseDTO;
import org.oswfm.timesheetservice.repository.PayPeriodsRepository;
import org.oswfm.timesheetservice.repository.TimesheetOperationsTypesRepository;
import org.oswfm.timesheetservice.repository.TimesheetSummaryRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetSummaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TimesheetSummaryService {

    @Autowired
    private TimesheetSummaryRepository repository;

    @Autowired
    private TimesheetSummaryMapper mapper;

    @Autowired
    private PayPeriodsRepository payPeriodsRepository;

    @Autowired
    private TimesheetOperationsTypesRepository operationsTypesRepository;

    @Autowired
    private EmployeeServiceClient employeeServiceClient;

    /**
     * Create a new TimesheetSummary
     */
    public TimesheetSummaryResponseDTO create(TimesheetSummaryRequestDTO requestDTO) {
        TimesheetSummary entity = mapper.toEntity(requestDTO);
        TimesheetSummary saved = repository.save(entity);
        TimesheetSummaryResponseDTO dto = mapper.toResponseDTO(saved);
        enrichDto(dto);
        return dto;
    }

    /**
     * Get TimesheetSummary by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetSummaryResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(entity -> {
                    TimesheetSummaryResponseDTO dto = mapper.toResponseDTO(entity);
                    enrichDto(dto);
                    return dto;
                });
    }

    /**
     * Get all TimesheetSummarys
     */
    @Transactional(readOnly = true)
    public List<TimesheetSummaryResponseDTO> getAll() {
        List<TimesheetSummaryResponseDTO> dtos = repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
        enrichDtos(dtos);
        return dtos;
    }

    /**
     * Update an existing TimesheetSummary
     */
    public Optional<TimesheetSummaryResponseDTO> update(Integer id, TimesheetSummaryRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetSummary updated = repository.save(entity);
                    TimesheetSummaryResponseDTO dto = mapper.toResponseDTO(updated);
                    enrichDto(dto);
                    return dto;
                });
    }

    /**
     * Delete TimesheetSummary by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetSummary exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetSummarys
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    /**
     * Enrich a single DTO with employee, pay period, and operation type data.
     */
    private void enrichDto(TimesheetSummaryResponseDTO dto) {
        // Pay period
        if (dto.getPayPeriodId() != null) {
            payPeriodsRepository.findById(dto.getPayPeriodId()).ifPresent(pp -> {
                dto.setPayPeriodStartDate(pp.getStartDate());
                dto.setPayPeriodEndDate(pp.getEndDate());
                dto.setYear(pp.getYear());
                dto.setPeriodNumber(pp.getPeriodNumber());
            });
        }

        // Operation type
        if (dto.getOperationTypeId() != null) {
            operationsTypesRepository.findById(dto.getOperationTypeId()).ifPresent(ot ->
                    dto.setOperationTypeName(ot.getOperationTypeName())
            );
        }

        // Employee (via Feign)
        if (dto.getEmployeeId() != null) {
            try {
                EmployeeDTO employee = employeeServiceClient.getById(dto.getEmployeeId());
                if (employee != null) {
                    dto.setEmployeeName(buildEmployeeName(employee));
                }
            } catch (Exception e) {
                log.warn("Failed to fetch employee {} for timesheet summary: {}", dto.getEmployeeId(), e.getMessage());
            }
        }
    }

    /**
     * Batch-enrich a list of DTOs to avoid N+1 queries for local entities.
     * Employee data is fetched in bulk via Feign.
     */
    private void enrichDtos(List<TimesheetSummaryResponseDTO> dtos) {
        if (dtos.isEmpty()) {
            return;
        }

        // Batch load all pay periods into a map
        Map<Integer, PayPeriods> payPeriodsMap = payPeriodsRepository.findAll().stream()
                .collect(Collectors.toMap(PayPeriods::getPayPeriodId, Function.identity()));

        // Batch load all operation types into a map
        Map<Integer, TimesheetOperationsTypes> operationTypesMap = operationsTypesRepository.findAll().stream()
                .collect(Collectors.toMap(TimesheetOperationsTypes::getOperationTypeId, Function.identity()));

        // Batch load all employees via Feign
        Map<Integer, EmployeeDTO> employeeMap;
        try {
            employeeMap = employeeServiceClient.getAll().stream()
                    .collect(Collectors.toMap(EmployeeDTO::getEmployeeId, Function.identity()));
        } catch (Exception e) {
            log.warn("Failed to fetch employees for timesheet summaries: {}", e.getMessage());
            employeeMap = Map.of();
        }

        for (TimesheetSummaryResponseDTO dto : dtos) {
            // Pay period
            if (dto.getPayPeriodId() != null) {
                PayPeriods pp = payPeriodsMap.get(dto.getPayPeriodId());
                if (pp != null) {
                    dto.setPayPeriodStartDate(pp.getStartDate());
                    dto.setPayPeriodEndDate(pp.getEndDate());
                    dto.setYear(pp.getYear());
                    dto.setPeriodNumber(pp.getPeriodNumber());
                }
            }

            // Operation type
            if (dto.getOperationTypeId() != null) {
                TimesheetOperationsTypes ot = operationTypesMap.get(dto.getOperationTypeId());
                if (ot != null) {
                    dto.setOperationTypeName(ot.getOperationTypeName());
                }
            }

            // Employee
            if (dto.getEmployeeId() != null) {
                EmployeeDTO employee = employeeMap.get(dto.getEmployeeId());
                if (employee != null) {
                    dto.setEmployeeName(buildEmployeeName(employee));
                }
            }
        }
    }

    private String buildEmployeeName(EmployeeDTO employee) {
        String first = employee.getFirstName() != null ? employee.getFirstName() : "";
        String last = employee.getLastName() != null ? employee.getLastName() : "";
        return (first + " " + last).trim();
    }
}
