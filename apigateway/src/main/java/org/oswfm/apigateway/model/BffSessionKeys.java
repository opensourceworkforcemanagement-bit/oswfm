package org.oswfm.apigateway.model;

/**
 * Session attribute keys used by the BFF Token Vault.
 * Shared between {@code BffAuthController} and {@code JwtAuthenticationFilter}.
 */
public final class BffSessionKeys {

    public static final String ACCESS_TOKEN  = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final String USER_INFO     = "USER_INFO";

    private BffSessionKeys() {}
}
