package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TaskCodes;
import org.oswfm.timesheetservice.model.dto.TaskCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TaskCodesResponseDTO;
import org.oswfm.timesheetservice.repository.TaskCodesRepository;
import org.oswfm.timesheetservice.model.mapper.TaskCodesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskCodesService {

    @Autowired
    private TaskCodesRepository repository;

    @Autowired
    private TaskCodesMapper mapper;

    /**
     * Create a new TaskCodes
     */
    public TaskCodesResponseDTO create(TaskCodesRequestDTO requestDTO) {
        TaskCodes entity = mapper.toEntity(requestDTO);
        TaskCodes saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TaskCodes by ID
     */
    @Transactional(readOnly = true)
    public Optional<TaskCodesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TaskCodess
     */
    @Transactional(readOnly = true)
    public List<TaskCodesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TaskCodes
     */
    public Optional<TaskCodesResponseDTO> update(Integer id, TaskCodesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TaskCodes updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TaskCodes by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TaskCodes exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TaskCodess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
