package org.oswfm.userservice.controller;

import org.oswfm.commons.model.user.dto.CreatePasswordRequest;
import org.oswfm.commons.model.user.dto.PasswordChangeRequest;
import org.oswfm.commons.model.user.dto.PasswordCredentialDTO;
import org.oswfm.commons.model.user.dto.PasswordStrengthResponse;
import org.oswfm.commons.model.user.dto.PasswordVerificationRequest;
import org.oswfm.commons.model.user.dto.PasswordVerificationResponse;
import org.oswfm.commons.model.user.entity.PasswordCredential;
import org.oswfm.userservice.service.PasswordService;
import org.oswfm.userservice.service.PasswordService.PasswordStrengthResult;
import org.oswfm.userservice.service.PasswordService.PasswordVerificationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/passwords")
@RequiredArgsConstructor
@Tag(name = "Password Management", description = "Secure password management APIs following OWASP standards")
public class PasswordController {
    
    private final PasswordService passwordService;
    
    @PostMapping("/create")
    @Operation(summary = "Create password for user", 
               description = "Creates a new password credential with secure hashing and salting")
    public ResponseEntity<PasswordCredentialDTO> createPassword(
            @Valid @RequestBody CreatePasswordRequest request) {
        
        PasswordCredential credential = passwordService.createPassword(
            request.getUserId(),
            request.getPassword(),
            request.getMustChangePassword() != null ? request.getMustChangePassword() : false
        );
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(convertToDTO(credential));
    }
    
    @PostMapping("/change")
    @Operation(summary = "Change password", 
               description = "Change user password with current password verification")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody PasswordChangeRequest request) {
        
        // Verify passwords match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                .body("New password and confirmation do not match");
        }
        
        passwordService.changePassword(
            request.getUserId(),
            request.getCurrentPassword(),
            request.getNewPassword()
        );
        
        return ResponseEntity.ok("Password changed successfully");
    }
    
    @PostMapping("/reset")
    @Operation(summary = "Reset password", 
               description = "Admin password reset or forgot password flow")
    public ResponseEntity<String> resetPassword(
            @RequestParam Integer  userId,
            @RequestParam String newPassword) {
        
        passwordService.resetPassword(userId, newPassword);
        
        return ResponseEntity.ok("Password reset successfully. User must change password on next login.");
    }
    
    @PostMapping("/verify")
    @Operation(summary = "Verify password", 
               description = "Verify username and password combination")
    public ResponseEntity<PasswordVerificationResponse> verifyPassword(
            @Valid @RequestBody PasswordVerificationRequest request) {
        
        PasswordVerificationResult result = passwordService.verifyPasswordCredential(
            request.getUsername(),
            request.getPassword()
        );
        
        PasswordVerificationResponse response = new PasswordVerificationResponse(
            result.isValid(),
            result.getMessage(),
            result.isAccountLocked(),
            result.isPasswordExpired(),
            result.isMustChangePassword(),
            result.getLockedUntil(),
            result.getFailedAttempts(),
            5 // maxFailedAttempts from config
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/strength")
    @Operation(summary = "Check password strength", 
               description = "Validate password strength without storing it")
    public ResponseEntity<PasswordStrengthResponse> checkPasswordStrength(
            @RequestParam String password,
            @RequestParam(required = false) String username) {
        
        PasswordStrengthResult result = passwordService.validatePasswordStrength(
            password, 
            username
        );
        
        PasswordStrengthResponse response = new PasswordStrengthResponse(
            result.isValid(),
            result.getStrength(),
            result.getStrengthLevel(),
            result.getMessage(),
            result.getViolations(),
            result.getSuggestions()
        );
        
        return ResponseEntity.ok(response);
    }
    
    private PasswordCredentialDTO convertToDTO(PasswordCredential credential) {
        PasswordCredentialDTO dto = new PasswordCredentialDTO();
        dto.setCredentialId(credential.getCredentialId());
        dto.setUserId(credential.getUser().getUserId());
        dto.setUsername(credential.getUser().getUserName());
        dto.setAlgorithm(credential.getAlgorithm());
        dto.setFailedAttempts(credential.getFailedAttempts());
        dto.setLockedUntil(credential.getLockedUntil());
        dto.setLastChangedAt(credential.getLastChangedAt());
        dto.setExpiresAt(credential.getExpiresAt());
        dto.setMustChangePassword(credential.getMustChangePassword());
        dto.setIsLocked(credential.isLocked());
        dto.setIsPasswordExpired(credential.isPasswordExpired());
        
        return dto;
    }
}
