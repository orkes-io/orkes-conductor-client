package io.orkes.conductor.client.automator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

class PollingSemaphore {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollingSemaphore.class);

    private final Semaphore semaphore;

    PollingSemaphore(int numSlots) {
        LOGGER.debug("Polling semaphore initialized with {} permits", numSlots);
        semaphore = new Semaphore(numSlots);
    }

    /**
     * Signals if polling is allowed based on whether a permit can be acquired.
     *
     * @return {@code true} - if permit is acquired {@code false} - if permit could not be acquired
     */
    boolean canPoll() {
        boolean acquired = semaphore.tryAcquire();
        LOGGER.debug("Trying to acquire permit: {}", acquired);
        return acquired;
    }

    /** Signals that processing is complete and the permit can be released. */
    void complete() {
        LOGGER.debug("Completed execution; releasing permit");
        semaphore.release();
    }

    /**
     * Gets the number of threads available for processing.
     *
     * @return number of available permits
     */
    int availableThreads() {
        int available = semaphore.availablePermits();
        LOGGER.debug("Number of available permits: {}", available);
        return available;
    }
}