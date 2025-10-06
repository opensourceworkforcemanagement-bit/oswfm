package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetSummary;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetSummaryRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetSummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetSummaryService {

    @Autowired
    private TimesheetSummaryRepository repository;

    @Autowired
    private TimesheetSummaryMapper mapper;

    /**
     * Create a new TimesheetSummary
     */
    public TimesheetSummaryResponseDTO create(TimesheetSummaryRequestDTO requestDTO) {
        TimesheetSummary entity = mapper.toEntity(requestDTO);
        TimesheetSummary saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetSummary by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetSummaryResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetSummarys
     */
    @Transactional(readOnly = true)
    public List<TimesheetSummaryResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetSummary
     */
    public Optional<TimesheetSummaryResponseDTO> update(Integer id, TimesheetSummaryRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetSummary updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
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
}
