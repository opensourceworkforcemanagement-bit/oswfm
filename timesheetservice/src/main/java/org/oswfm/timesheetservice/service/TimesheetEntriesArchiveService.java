package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesArchiveResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesArchiveRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesArchiveService {

    @Autowired
    private TimesheetEntriesArchiveRepository repository;

    @Autowired
    private TimesheetEntriesArchiveMapper mapper;

    /**
     * Create a new TimesheetEntriesArchive
     */
    public TimesheetEntriesArchiveResponseDTO create(TimesheetEntriesArchiveRequestDTO requestDTO) {
        TimesheetEntriesArchive entity = mapper.toEntity(requestDTO);
        TimesheetEntriesArchive saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesArchive by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesArchiveResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesArchives
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesArchiveResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesArchive
     */
    public Optional<TimesheetEntriesArchiveResponseDTO> update(Integer id, TimesheetEntriesArchiveRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntriesArchive updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesArchive by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntriesArchive exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesArchives
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
