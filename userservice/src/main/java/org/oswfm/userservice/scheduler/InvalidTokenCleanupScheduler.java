package org.oswfm.userservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oswfm.userservice.repository.InvalidTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Scheduled job that periodically purges expired entries from the INVALID_TOKEN table.
 * Rows are safe to delete once their token's natural expiry has passed — they can no
 * longer be presented as valid tokens, so there is nothing left to blacklist.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InvalidTokenCleanupScheduler {

    private final InvalidTokenRepository invalidTokenRepository;

    /**
     * Runs every hour and deletes all INVALID_TOKEN rows whose EXPIRES_AT is in the past.
     */
    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void purgeExpiredTokens() {
        final LocalDateTime now = LocalDateTime.now();
        log.info("[InvalidTokenCleanup] Purging expired tokens before {}", now);
        invalidTokenRepository.deleteAllExpiredBefore(now);
        log.info("[InvalidTokenCleanup] Purge complete");
    }

}
