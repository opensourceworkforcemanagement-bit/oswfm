package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetSummaryArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryArchiveResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetSummaryArchiveRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetSummaryArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetSummaryArchiveService {

    @Autowired
    private TimesheetSummaryArchiveRepository repository;

    @Autowired
    private TimesheetSummaryArchiveMapper mapper;

    /**
     * Create a new TimesheetSummaryArchive
     */
    public TimesheetSummaryArchiveResponseDTO create(TimesheetSummaryArchiveRequestDTO requestDTO) {
        TimesheetSummaryArchive entity = mapper.toEntity(requestDTO);
        TimesheetSummaryArchive saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetSummaryArchive by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetSummaryArchiveResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetSummaryArchives
     */
    @Transactional(readOnly = true)
    public List<TimesheetSummaryArchiveResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetSummaryArchive
     */
    public Optional<TimesheetSummaryArchiveResponseDTO> update(Integer id, TimesheetSummaryArchiveRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetSummaryArchive updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetSummaryArchive by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetSummaryArchive exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetSummaryArchives
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
