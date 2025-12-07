package org.oswfm.accesscontrolservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.dto.ActionDTO;
import org.oswfm.accesscontrolservice.exception.DuplicateResourceException;
import org.oswfm.accesscontrolservice.model.entity.Action;
import org.oswfm.accesscontrolservice.repository.ActionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ActionService {
    
    private final ActionRepository actionRepository;
    
    @Transactional(readOnly = true)
    public List<ActionDTO> getAllActions() {
        return actionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ActionDTO getActionById( Integer id) {
        Action action = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action", "id", id));
        return convertToDTO(action);
    }
    
    @Transactional(readOnly = true)
    public ActionDTO getActionByName(String actionName) {
        Action action = actionRepository.findByActionName(actionName)
                .orElseThrow(() -> new ResourceNotFoundException("Action", "actionName", actionName));
        return convertToDTO(action);
    }
    
    @Transactional
    public ActionDTO createAction(ActionDTO actionDTO) {
        if (actionRepository.existsByActionName(actionDTO.getActionName())) {
            throw new DuplicateResourceException("Action", "actionName", actionDTO.getActionName());
        }
        
        Action action = convertToEntity(actionDTO);
        Action savedAction = actionRepository.save(action);
        return convertToDTO(savedAction);
    }
    
    @Transactional
    public ActionDTO updateAction( Integer id, ActionDTO actionDTO) {
        Action existingAction = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action", "id", id));
        
        if (!existingAction.getActionName().equals(actionDTO.getActionName()) && 
            actionRepository.existsByActionName(actionDTO.getActionName())) {
            throw new DuplicateResourceException("Action", "actionName", actionDTO.getActionName());
        }
        
        existingAction.setActionName(actionDTO.getActionName());
        existingAction.setDescription(actionDTO.getDescription());
        
        Action updatedAction = actionRepository.save(existingAction);
        return convertToDTO(updatedAction);
    }
    
    @Transactional
    public void deleteAction( Integer id) {
        if (!actionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Action", "id", id);
        }
        actionRepository.deleteById(id);
    }
    
    private ActionDTO convertToDTO(Action action) {
        ActionDTO dto = new ActionDTO();
        dto.setActionId(action.getActionId());
        dto.setActionName(action.getActionName());
        dto.setDescription(action.getDescription());
        dto.setCreatedAt(action.getCreatedAt());
        return dto;
    }
    
    private Action convertToEntity(ActionDTO dto) {
        Action action = new Action();
        action.setActionName(dto.getActionName());
        action.setDescription(dto.getDescription());
        return action;
    }
}
