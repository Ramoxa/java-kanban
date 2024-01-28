package tests;
import inside.*;
import manager.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static manager.InMemoryTaskManager.historyManager;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;


public class FileBackedTasksManagerTest extends TaskManagersTest {
    public static final Path path = Path.of("data.test.csv");
    File file = new File(String.valueOf(path));

    @BeforeEach
    public void beforeEach() {
        manager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(path);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void shouldCorrectlySaveAndLoad() {
        Task task = new Task("Description", "Title", Instant.EPOCH, 0);
        manager.createTask(task);
        Epic epic = new Epic("Description", "Title");
        manager.createEpic(epic);
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        fileManager.load();
        assertEquals(List.of(task), manager.getListAllTasks());
        assertEquals(List.of(epic), manager.getListAllEpic());
    }

    @Test
    public void shouldSaveAndLoadEmptyTasksEpicsSubtasks() {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        fileManager.save();
        fileManager.load();
        assertEquals(Collections.EMPTY_LIST, manager.getListAllTasks());
        assertEquals(Collections.EMPTY_LIST, manager.getListAllEpic());
        assertEquals(Collections.EMPTY_LIST, manager.getListSubTasks());
    }

    @Test
    public void shouldSaveAndLoadEmptyHistory() {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        fileManager.save();
        fileManager.load();
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldBeFileBackupManager() {
        Path path3 = Path.of("src//tests//data.test.csv");
        File file = new File(String.valueOf(path3));
        FileBackedTasksManager test = new FileBackedTasksManager(Managers.getDefaultHistory(), file);

        Task task = new Task("Description", "Title", Instant.EPOCH, 0);
        test.createTask(task);
        Epic epic = new Epic("TestEpic", "Test description");
        test.createEpic(epic);
        Subtask subTask = new Subtask("Description", "Title", Instant.EPOCH, 0);
        test.createSubTask(subTask);
        Subtask subTask2 = new Subtask("Description", "Title", Instant.EPOCH, 0);
        test.createSubTask(subTask2);
        Epic epic2 = new Epic("TestEpic 2", "Test description 2");
        test.createEpic(epic2);
        Task task2 = new Task("Description", "Title", Instant.EPOCH, 0) ;
        test.createTask(task2);
        test.getTaskById(1);
        test.getEpicById(2);
        test.getSubtaskById(3);

        assertEquals(test.getTasks(), test.getTasks());
        assertEquals(test.getSubtasks(), test.getSubtasks());
        assertEquals(test.getEpics(), test.getEpics());
        assertEquals(test.getHistory(), test.getHistory());
        assertEquals(test.getPrioritizedTasks(), test.getPrioritizedTasks());
        assertNotNull(test);
    }
}

