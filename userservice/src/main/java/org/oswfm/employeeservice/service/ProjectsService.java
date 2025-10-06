package org.oswfm.employeeservice.service;

import org.oswfm.employeeservice.model.entity.Projects;
import org.oswfm.employeeservice.model.dto.ProjectsRequestDTO;
import org.oswfm.employeeservice.model.dto.ProjectsResponseDTO;
import org.oswfm.employeeservice.repository.ProjectsRepository;
import org.oswfm.employeeservice.model.mapper.ProjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectsService {

    @Autowired
    private ProjectsRepository repository;

    @Autowired
    private ProjectsMapper mapper;

    /**
     * Create a new Projects
     */
    public ProjectsResponseDTO create(ProjectsRequestDTO requestDTO) {
        Projects entity = mapper.toEntity(requestDTO);
        Projects saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get Projects by ID
     */
    @Transactional(readOnly = true)
    public Optional<ProjectsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all Projectss
     */
    @Transactional(readOnly = true)
    public List<ProjectsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing Projects
     */
    public Optional<ProjectsResponseDTO> update(Integer id, ProjectsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    Projects updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete Projects by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if Projects exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all Projectss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
