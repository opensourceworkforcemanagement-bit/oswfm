package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.entity.TimesheetNotifications;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsResponseDTO;
import org.oswfm.timesheetservice.repository.TimesheetNotificationsRepository;
import org.oswfm.timesheetservice.model.mapper.TimesheetNotificationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimesheetNotificationsService {

    @Autowired
    private TimesheetNotificationsRepository repository;

    @Autowired
    private TimesheetNotificationsMapper mapper;

    /**
     * Create a new TimesheetNotifications
     */
    public TimesheetNotificationsResponseDTO create(TimesheetNotificationsRequestDTO requestDTO) {
        TimesheetNotifications entity = mapper.toEntity(requestDTO);
        TimesheetNotifications saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get TimesheetNotifications by ID
     */
    @Transactional(readOnly = true)
    public Optional<TimesheetNotificationsResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all TimesheetNotificationss
     */
    @Transactional(readOnly = true)
    public List<TimesheetNotificationsResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing TimesheetNotifications
     */
    public Optional<TimesheetNotificationsResponseDTO> update(Integer id, TimesheetNotificationsRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    TimesheetNotifications updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete TimesheetNotifications by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if TimesheetNotifications exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all TimesheetNotificationss
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
