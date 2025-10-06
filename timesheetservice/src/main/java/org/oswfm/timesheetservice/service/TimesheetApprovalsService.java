package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetApprovals;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetApprovalsRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetApprovalsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetApprovalsService {

    @Autowired
    private TimesheetApprovalsRepository repository;

    @Autowired
    private TimesheetApprovalsMapper mapper;

    /**
     * Create a new TimesheetApprovals
     */
    public TimesheetApprovalsResponseDTO create(TimesheetApprovalsRequestDTO requestDTO) {
        TimesheetApprovals entity = mapper.toEntity(requestDTO);
        TimesheetApprovals saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetApprovals by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetApprovalsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetApprovalss
     */
    @Transactional(readOnly = true)
    public List<TimesheetApprovalsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetApprovals
     */
    public Optional<TimesheetApprovalsResponseDTO> update(Integer id, TimesheetApprovalsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetApprovals updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetApprovals by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetApprovals exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetApprovalss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
