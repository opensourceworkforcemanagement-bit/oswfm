package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesCommentsArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsArchiveResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesCommentsArchiveRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesCommentsArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesCommentsArchiveService {

    @Autowired
    private TimesheetEntriesCommentsArchiveRepository repository;

    @Autowired
    private TimesheetEntriesCommentsArchiveMapper mapper;

    /**
     * Create a new TimesheetEntriesCommentsArchive
     */
    public TimesheetEntriesCommentsArchiveResponseDTO create(TimesheetEntriesCommentsArchiveRequestDTO requestDTO) {
        TimesheetEntriesCommentsArchive entity = mapper.toEntity(requestDTO);
        TimesheetEntriesCommentsArchive saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesCommentsArchive by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesCommentsArchiveResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesCommentsArchives
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesCommentsArchiveResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesCommentsArchive
     */
    public Optional<TimesheetEntriesCommentsArchiveResponseDTO> update(Integer id, TimesheetEntriesCommentsArchiveRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntriesCommentsArchive updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesCommentsArchive by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntriesCommentsArchive exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesCommentsArchives
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
