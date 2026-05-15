package org.oswfm.userservice.service.impl;


import org.oswfm.userservice.exception.TokenAlreadyInvalidatedException;
import org.oswfm.commons.model.user.entity.InvalidTokenEntity;
import org.oswfm.userservice.repository.InvalidTokenRepository;
import org.oswfm.userservice.service.InvalidTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link InvalidTokenService} for managing invalid tokens.
 */
@Service
@RequiredArgsConstructor
public class InvalidTokenServiceImpl implements InvalidTokenService {

    private final InvalidTokenRepository invalidTokenRepository;

    /**
     * Invalidates a set of tokens by saving them as invalid in the repository.
     * Each entry maps a token ID to its natural JWT expiry so the cleanup
     * scheduler can prune rows once they can no longer be presented as valid.
     *
     * @param tokenExpiryMap map of token ID → expiry timestamp.
     */
    @Override
    public void invalidateTokens(Map<String, LocalDateTime> tokenExpiryMap) {

        final List<InvalidTokenEntity> invalidTokenEntities = new ArrayList<>();
        for (Map.Entry<String, LocalDateTime> entry : tokenExpiryMap.entrySet()) {
            invalidTokenEntities.add(new InvalidTokenEntity(entry.getKey(), entry.getValue()));
        }

        invalidTokenRepository.saveAll(invalidTokenEntities);
    }

    /**
     * Checks if a token has been invalidated by its ID.
     *
     * @param tokenId the token ID to check.
     * @throws TokenAlreadyInvalidatedException if the token has already been invalidated.
     */
    @Override
    public void checkForInvalidityOfToken(String tokenId) {

        final boolean isTokenInvalid = invalidTokenRepository.findByTokenId(tokenId).isPresent();

        if (isTokenInvalid) {
            throw new TokenAlreadyInvalidatedException(tokenId);
        }

    }

}
