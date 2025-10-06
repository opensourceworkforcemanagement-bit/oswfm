package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.Projects;
import org.oswfm.employeeservice.model.dto.ProjectsRequestDTO;
import org.oswfm.employeeservice.model.dto.ProjectsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectsMapper {

    public Projects toEntity(ProjectsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Projects entity = new Projects();
        entity.setProject(dto.getProject());
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public ProjectsResponseDTO toResponseDTO(Projects entity) {
        if (entity == null) {
            return null;
        }
        
        ProjectsResponseDTO dto = new ProjectsResponseDTO();
        dto.setProjectId(entity.getProjectId());
        dto.setProject(entity.getProject());
        dto.setDescription(entity.getDescription());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(ProjectsRequestDTO dto, Projects entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setProject(dto.getProject());
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
    }
}
