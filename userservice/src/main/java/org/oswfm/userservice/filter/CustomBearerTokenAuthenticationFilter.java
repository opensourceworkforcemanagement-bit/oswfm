package org.oswfm.userservice.filter;


import java.io.IOException;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.oswfm.userservice.model.user.Token;
import org.oswfm.userservice.service.InvalidTokenService;
import org.oswfm.userservice.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom filter named {@link CustomBearerTokenAuthenticationFilter} for handling Bearer token authentication in HTTP requests.
 * This filter extracts the Bearer token from the Authorization header,
 * validates it, and sets the authentication context if the token is valid.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomBearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final InvalidTokenService invalidTokenService;

    /**
     * Processes the HTTP request and checks for Bearer token authentication.
     * <p>
     * This method extracts the Bearer token from the request, verifies its validity,
     * and checks whether the token has been invalidated. If the token is valid, it sets
     * the authentication in the SecurityContext.
     * </p>
     *
     * @param httpServletRequest  the HTTP request
     * @param httpServletResponse the HTTP response
     * @param filterChain         the filter chain to pass the request and response
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest httpServletRequest,
                                    @NonNull final HttpServletResponse httpServletResponse,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {

        log.debug("API Request was secured with Security!");

        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = extractToken(httpServletRequest);

        if (Token.isBearerToken(authorizationHeader) && StringUtils.isNotEmpty(token)) {

            final String jwt = Token.getJwt(authorizationHeader);
            tokenService.verifyAndValidate(jwt);
            
            // Extract claims
            Jws<Claims> claims = tokenService.getClaims(token);
            String userId = claims.getPayload().get("userId", String.class);

            final String tokenId = tokenService.getId(jwt);
            invalidTokenService.checkForInvalidityOfToken(tokenId);

            // CREATE AND SET AUTHENTICATION 
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(
                    userId,  // principal
                    null,    // credentials (not needed after validation) TODO
                    Collections.emptyList()  // authorities (add roles if needed) TODO
                );
            
            // Add additional details if needed
            authentication.setDetails(claims);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

    /**
     * Extracts the Bearer token from the Authorization header of the HTTP request.
     *
     * @param request the HTTP request
     * @return the extracted Bearer token, or null if not present
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}