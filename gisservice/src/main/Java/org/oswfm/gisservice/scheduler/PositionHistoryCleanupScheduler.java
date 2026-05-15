package org.oswfm.gisservice.scheduler;

import org.oswfm.gisservice.service.UserPositionHistoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Nightly job that prunes each history table to its configured retention window.
 * Runs at 02:00 UTC every day.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PositionHistoryCleanupScheduler {

    private final UserPositionHistoryService historyService;

    @Scheduled(cron = "0 0 2 * * *", zone = "UTC")
    public void purgeExpiredHistory() {
        log.info("PositionHistoryCleanupScheduler: starting expired history purge");
        historyService.purgeExpired();
        log.info("PositionHistoryCleanupScheduler: purge complete");
    }
}
