package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesComments;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetEntriesCommentsRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetEntriesCommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetEntriesCommentsService {

    @Autowired
    private TimesheetEntriesCommentsRepository repository;

    @Autowired
    private TimesheetEntriesCommentsMapper mapper;

    /**
     * Create a new TimesheetEntriesComments
     */
    public TimesheetEntriesCommentsResponseDTO create(TimesheetEntriesCommentsRequestDTO requestDTO) {
        TimesheetEntriesComments entity = mapper.toEntity(requestDTO);
        TimesheetEntriesComments saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetEntriesComments by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetEntriesCommentsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetEntriesCommentss
     */
    @Transactional(readOnly = true)
    public List<TimesheetEntriesCommentsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetEntriesComments
     */
    public Optional<TimesheetEntriesCommentsResponseDTO> update(Integer id, TimesheetEntriesCommentsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetEntriesComments updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetEntriesComments by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetEntriesComments exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetEntriesCommentss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
