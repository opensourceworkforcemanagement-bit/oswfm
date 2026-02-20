package org.oswfm.apigateway.model;

/**
 * Login request body received by the BFF from the SPA.
 * Field names match the userservice {@code LoginRequest} DTO so they can be forwarded as-is.
 */
public record BffLoginRequest(String userName, String password) {}
