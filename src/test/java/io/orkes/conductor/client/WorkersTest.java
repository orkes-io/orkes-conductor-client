package io.orkes.conductor.client;

import com.netflix.conductor.client.worker.Worker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkersTest {

    @Test
    void startWorkers() {
        Workers workers = new Workers();
        List<Worker> taskWorkers = new ArrayList<>();

    }

    @Test
    void shutdown() {
    }
}