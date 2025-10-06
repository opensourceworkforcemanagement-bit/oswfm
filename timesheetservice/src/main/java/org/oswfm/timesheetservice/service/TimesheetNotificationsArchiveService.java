package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetNotificationsArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsArchiveResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetNotificationsArchiveRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetNotificationsArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetNotificationsArchiveService {

    @Autowired
    private TimesheetNotificationsArchiveRepository repository;

    @Autowired
    private TimesheetNotificationsArchiveMapper mapper;

    /**
     * Create a new TimesheetNotificationsArchive
     */
    public TimesheetNotificationsArchiveResponseDTO create(TimesheetNotificationsArchiveRequestDTO requestDTO) {
        TimesheetNotificationsArchive entity = mapper.toEntity(requestDTO);
        TimesheetNotificationsArchive saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetNotificationsArchive by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetNotificationsArchiveResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetNotificationsArchives
     */
    @Transactional(readOnly = true)
    public List<TimesheetNotificationsArchiveResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetNotificationsArchive
     */
    public Optional<TimesheetNotificationsArchiveResponseDTO> update(Integer id, TimesheetNotificationsArchiveRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetNotificationsArchive updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetNotificationsArchive by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetNotificationsArchive exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetNotificationsArchives
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
