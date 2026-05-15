package org.oswfm.userservice.service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Service interface named {@link InvalidTokenService} for managing invalid tokens.
 */
public interface InvalidTokenService {

    /**
     * Invalidates a set of tokens, each paired with its natural expiry time.
     * The expiry is stored so the cleanup scheduler can prune rows once they
     * are no longer presentable as valid tokens.
     *
     * @param tokenExpiryMap map of token ID → expiry timestamp.
     */
    void invalidateTokens(final Map<String, LocalDateTime> tokenExpiryMap);

    /**
     * Checks if a token has been invalidated by its ID.
     *
     * @param tokenId the token ID to check.
     */
    void checkForInvalidityOfToken(final String tokenId);

}
