package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesInOutArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutArchiveResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesInOutArchiveRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesInOutArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesInOutArchiveService {

    @Autowired
    private TimesheetEntriesInOutArchiveRepository repository;

    @Autowired
    private TimesheetEntriesInOutArchiveMapper mapper;

    /**
     * Create a new TimesheetEntriesInOutArchive
     */
    public TimesheetEntriesInOutArchiveResponseDTO create(TimesheetEntriesInOutArchiveRequestDTO requestDTO) {
        TimesheetEntriesInOutArchive entity = mapper.toEntity(requestDTO);
        TimesheetEntriesInOutArchive saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesInOutArchive by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesInOutArchiveResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesInOutArchives
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesInOutArchiveResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesInOutArchive
     */
    public Optional<TimesheetEntriesInOutArchiveResponseDTO> update(Integer id, TimesheetEntriesInOutArchiveRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntriesInOutArchive updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesInOutArchive by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntriesInOutArchive exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesInOutArchives
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
