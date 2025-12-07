package org.oswfm.accesscontrolservice.service;

import org.oswfm.accesscontrolservice.dto.UserDTO;
import org.oswfm.accesscontrolservice.exception.DuplicateResourceException;
import org.oswfm.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.oswfm.accesscontrolservice.repository.ACUserRepository;

// @Service
// @RequiredArgsConstructor
public class UserService {
    
    // private final UserRepository userRepository;
    
    // @Transactional(readOnly = true)
    // public List<UserDTO> getAllUsers() {
    //     return userRepository.findAll().stream()
    //             .map(this::convertToDTO)
    //             .collect(Collectors.toList());
    // }
    
    // @Transactional(readOnly = true)
    // public UserDTO getUserById( Integer id) {
    //     User user = userRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    //     return convertToDTO(user);
    // }
    
    // @Transactional(readOnly = true)
    // public UserDTO getUserByUsername(String username) {
    //     User user = userRepository.findByUsername(username)
    //             .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    //     return convertToDTO(user);
    // }
    
    // @Transactional(readOnly = true)
    // public List<UserDTO> getActiveUsers() {
    //     return userRepository.findByIsActive(true).stream()
    //             .map(this::convertToDTO)
    //             .collect(Collectors.toList());
    // }
    
    // @Transactional
    // public UserDTO createUser(UserDTO userDTO) {
    //     if (userRepository.existsByUsername(userDTO.getUsername())) {
    //         throw new DuplicateResourceException("User", "username", userDTO.getUsername());
    //     }
    //     if (userRepository.existsByEmail(userDTO.getEmail())) {
    //         throw new DuplicateResourceException("User", "email", userDTO.getEmail());
    //     }
        
    //     User user = convertToEntity(userDTO);
    //     User savedUser = userRepository.save(user);
    //     return convertToDTO(savedUser);
    // }
    
    // @Transactional
    // public UserDTO updateUser( Integer id, UserDTO userDTO) {
    //     User existingUser = userRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
    //     if (!existingUser.getUsername().equals(userDTO.getUsername()) && 
    //         userRepository.existsByUsername(userDTO.getUsername())) {
    //         throw new DuplicateResourceException("User", "username", userDTO.getUsername());
    //     }
        
    //     if (!existingUser.getEmail().equals(userDTO.getEmail()) && 
    //         userRepository.existsByEmail(userDTO.getEmail())) {
    //         throw new DuplicateResourceException("User", "email", userDTO.getEmail());
    //     }
        
    //     existingUser.setUsername(userDTO.getUsername());
    //     existingUser.setEmail(userDTO.getEmail());
    //     existingUser.setFullName(userDTO.getFullName());
    //     existingUser.setIsActive(userDTO.getIsActive());
        
    //     User updatedUser = userRepository.save(existingUser);
    //     return convertToDTO(updatedUser);
    // }
    
    // @Transactional
    // public void deleteUser( Integer id) {
    //     if (!userRepository.existsById(id)) {
    //         throw new ResourceNotFoundException("User", "id", id);
    //     }
    //     userRepository.deleteById(id);
    // }
    
    // private UserDTO convertToDTO(User user) {
    //     UserDTO dto = new UserDTO();
    //     dto.setUserId(user.getUserId());
    //     dto.setUsername(user.getUsername());
    //     dto.setEmail(user.getEmail());
    //     dto.setFullName(user.getFullName());
    //     dto.setIsActive(user.getIsActive());
    //     dto.setCreatedAt(user.getCreatedAt());
    //     dto.setUpdatedAt(user.getUpdatedAt());
    //     return dto;
    // }
    
    // private User convertToEntity(UserDTO dto) {
    //     User user = new User();
    //     user.setUsername(dto.getUsername());
    //     user.setEmail(dto.getEmail());
    //     user.setFullName(dto.getFullName());
    //     user.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
    //     return user;
    // }
}
