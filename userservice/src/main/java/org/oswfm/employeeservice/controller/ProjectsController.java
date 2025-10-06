package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.ProjectsRequestDTO;
import org.oswfm.employeeservice.model.dto.ProjectsResponseDTO;
import org.oswfm.employeeservice.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectsController {

    @Autowired
    private ProjectsService service;

    /**
     * Create a new Projects
     * POST /api/projects
     */
    @PostMapping
    public ResponseEntity<ProjectsResponseDTO> create(@Valid @RequestBody ProjectsRequestDTO requestDTO) {
        ProjectsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get Projects by ID
     * GET /api/projects/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all Projectss
     * GET /api/projects
     */
    @GetMapping
    public ResponseEntity<List<ProjectsResponseDTO>> getAll() {
        List<ProjectsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update Projects by ID
     * PUT /api/projects/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProjectsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete Projects by ID
     * DELETE /api/projects/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if Projects exists by ID
     * HEAD /api/projects/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all Projectss
     * GET /api/projects/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}
