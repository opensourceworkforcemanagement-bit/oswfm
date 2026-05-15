package org.oswfm.gisservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.oswfm.ResourceNotFoundException;
import org.oswfm.gisservice.dto.UserPositionDTO;
import org.oswfm.gisservice.model.entity.UserPosition;
import org.oswfm.gisservice.repository.UserPositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPositionService {

    private final UserPositionRepository userPositionRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional(readOnly = true)
    public List<UserPositionDTO> getAllPositions() {
        return userPositionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserPositionDTO getPositionById(Integer id) {
        UserPosition position = userPositionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserPosition", "id", id));
        return convertToDTO(position);
    }

    @Transactional(readOnly = true)
    public UserPositionDTO getPositionByUserId(Integer userId) {
        UserPosition position = userPositionRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserPosition", "userId", userId));
        return convertToDTO(position);
    }

    @Transactional(readOnly = true)
    public List<UserPositionDTO> getLatestPositionPerUser() {
        return userPositionRepository.findLatestPositionPerUser().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserPositionDTO> findUsersWithinDistance(double lat, double lon, double distanceMeters) {
        return userPositionRepository.findUsersWithinDistance(lat, lon, distanceMeters).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserPositionDTO createPosition(UserPositionDTO dto) {
        UserPosition position = convertToEntity(dto);
        UserPosition saved = userPositionRepository.save(position);
        return convertToDTO(saved);
    }

    @Transactional
    public UserPositionDTO updatePosition(Integer id, UserPositionDTO dto) {
        UserPosition existing = userPositionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserPosition", "id", id));

        existing.setUserId(dto.getUserId());
        existing.setLocation(createPoint(dto.getLongitude(), dto.getLatitude()));

        UserPosition updated = userPositionRepository.save(existing);
        return convertToDTO(updated);
    }

    @Transactional
    public void deletePosition(Integer id) {
        if (!userPositionRepository.existsById(id)) {
            throw new ResourceNotFoundException("UserPosition", "id", id);
        }
        userPositionRepository.deleteById(id);
    }

    private UserPositionDTO convertToDTO(UserPosition entity) {
        UserPositionDTO dto = new UserPositionDTO();
        dto.setUserPositionId(entity.getUserPositionId());
        dto.setUserId(entity.getUserId());
        if (entity.getLocation() != null) {
            dto.setLatitude(entity.getLocation().getY());
            dto.setLongitude(entity.getLocation().getX());
        }
        dto.setLastUpdate(entity.getLastUpdate());
        return dto;
    }

    private UserPosition convertToEntity(UserPositionDTO dto) {
        UserPosition entity = new UserPosition();
        entity.setUserId(dto.getUserId());
        entity.setLocation(createPoint(dto.getLongitude(), dto.getLatitude()));
        return entity;
    }

    private Point createPoint(double longitude, double latitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
