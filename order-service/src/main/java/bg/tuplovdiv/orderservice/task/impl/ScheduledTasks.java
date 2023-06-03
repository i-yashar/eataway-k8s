package bg.tuplovdiv.orderservice.task.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
class ScheduledTasks {

    private static final long CLEANUP_RATE = 2;
    private static final long INITIAL_DELAY = 1;

    private final CleanupService cleanupService;

    ScheduledTasks(CleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    @Scheduled(fixedRate = CLEANUP_RATE, initialDelay = INITIAL_DELAY, timeUnit = TimeUnit.MINUTES)
    public void cleanUpActiveOrders() {
        cleanupService.cleanUpActiveOrders();
    }
}
