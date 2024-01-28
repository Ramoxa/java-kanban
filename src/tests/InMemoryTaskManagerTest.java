package tests;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

import static manager.InMemoryTaskManager.historyManager;

class InMemoryTaskManagerTest extends TaskManagersTest<InMemoryTaskManager> {

    @BeforeEach
    public void init() { manager = new InMemoryTaskManager(historyManager); }
}
