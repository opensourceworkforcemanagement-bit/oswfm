package org.oswfm.accesscontrolservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.dto.OperationDTO;
import org.oswfm.accesscontrolservice.exception.DuplicateResourceException;
import org.oswfm.accesscontrolservice.model.entity.Operation;
import org.oswfm.accesscontrolservice.repository.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    @Transactional(readOnly = true)
    public List<OperationDTO> getAllOperations() {
        return operationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OperationDTO getOperationById( Integer id) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Operation", "id", id));
        return convertToDTO(operation);
    }

    @Transactional(readOnly = true)
    public OperationDTO getOperationByName(String operationName) {
        Operation operation = operationRepository.findByOperationName(operationName)
                .orElseThrow(() -> new ResourceNotFoundException("Operation", "operationName", operationName));
        return convertToDTO(operation);
    }

    @Transactional
    public OperationDTO createOperation(OperationDTO operationDTO) {
        if (operationRepository.existsByOperationName(operationDTO.getOperationName())) {
            throw new DuplicateResourceException("Operation", "operationName", operationDTO.getOperationName());
        }

        Operation operation = convertToEntity(operationDTO);
        Operation savedOperation = operationRepository.save(operation);
        return convertToDTO(savedOperation);
    }

    @Transactional
    public OperationDTO updateOperation( Integer id, OperationDTO operationDTO) {
        Operation existingOperation = operationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Operation", "id", id));

        if (!existingOperation.getOperationName().equals(operationDTO.getOperationName()) &&
            operationRepository.existsByOperationName(operationDTO.getOperationName())) {
            throw new DuplicateResourceException("Operation", "operationName", operationDTO.getOperationName());
        }

        existingOperation.setOperationName(operationDTO.getOperationName());
        existingOperation.setDescription(operationDTO.getDescription());

        Operation updatedOperation = operationRepository.save(existingOperation);
        return convertToDTO(updatedOperation);
    }

    @Transactional
    public void deleteOperation( Integer id) {
        if (!operationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Operation", "id", id);
        }
        operationRepository.deleteById(id);
    }

    private OperationDTO convertToDTO(Operation operation) {
        OperationDTO dto = new OperationDTO();
        dto.setOperationId(operation.getOperationId());
        dto.setOperationName(operation.getOperationName());
        dto.setDescription(operation.getDescription());
        dto.setCreatedAt(operation.getCreatedAt());
        return dto;
    }

    private Operation convertToEntity(OperationDTO dto) {
        Operation operation = new Operation();
        operation.setOperationName(dto.getOperationName());
        operation.setDescription(dto.getDescription());
        return operation;
    }
}
