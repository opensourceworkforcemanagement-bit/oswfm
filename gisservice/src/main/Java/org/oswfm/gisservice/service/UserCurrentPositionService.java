package org.oswfm.gisservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.oswfm.ResourceNotFoundException;
import org.oswfm.gisservice.dto.UserCurrentPositionDTO;
import org.oswfm.gisservice.model.entity.UserCurrentPosition;
import org.oswfm.gisservice.repository.UserCurrentPositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCurrentPositionService {

    private final UserCurrentPositionRepository repository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional(readOnly = true)
    public List<UserCurrentPositionDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserCurrentPositionDTO getByUserId(Integer userId) {
        return repository.findById(userId)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("UserCurrentPosition", "userId", userId));
    }

    @Transactional(readOnly = true)
    public List<UserCurrentPositionDTO> findNearby(double lat, double lon, double distanceMeters) {
        return repository.findNearby(lat, lon, distanceMeters).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Upserts the current position for a user.
     * The DB trigger on user_current_position fans the write out to all history tables automatically.
     */
    @Transactional
    public UserCurrentPositionDTO upsert(UserCurrentPositionDTO dto) {
        UserCurrentPosition entity = repository.findById(dto.getUserId())
                .orElse(new UserCurrentPosition());
        entity.setUserId(dto.getUserId());
        entity.setLocation(geometryFactory.createPoint(
                new Coordinate(dto.getLongitude(), dto.getLatitude())));
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void deleteByUserId(Integer userId) {
        if (!repository.existsById(userId)) {
            throw new ResourceNotFoundException("UserCurrentPosition", "userId", userId);
        }
        repository.deleteById(userId);
    }

    private UserCurrentPositionDTO toDTO(UserCurrentPosition e) {
        UserCurrentPositionDTO dto = new UserCurrentPositionDTO();
        dto.setUserId(e.getUserId());
        if (e.getLocation() != null) {
            dto.setLatitude(e.getLocation().getY());
            dto.setLongitude(e.getLocation().getX());
        }
        dto.setLastUpdate(e.getLastUpdate());
        return dto;
    }
}
