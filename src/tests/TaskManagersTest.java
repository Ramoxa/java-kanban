package tests;

import inside.Epic;
import inside.Subtask;
import inside.Task;
import manager.InMemoryTaskManager;
import manager.Status;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static manager.InMemoryTaskManager.historyManager;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class TaskManagersTest<T extends TaskManager> {

    protected T manager;
    protected Task task;
    protected Epic epic;
    protected Subtask subTask;


    @BeforeEach
    void beforeEach() {
        task = new Task("1", "1111", Instant.EPOCH, 0);
        epic = new Epic("222", "3333");
        subTask = new Subtask("333", "4444", Instant.EPOCH, 0);
    }


    @Test
    public void testGetTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createTask(task);

        List<Task> tasksList = taskManager.getTasks();
        Assertions.assertEquals(1, tasksList.size());

        Task emptyTask = taskManager.getTask(123);
        Assertions.assertNull(emptyTask);

        Task invalidTask = taskManager.getTask(999);
        Assertions.assertNull(invalidTask);
    }

    @Test
    public void testGetSubtask () {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createSubTask(subTask);

        List<Subtask> tasksList = taskManager.getSubtasks();
        Assertions.assertEquals(1, tasksList.size());

        Task emptyTask = taskManager.getSubtask(123);
        Assertions.assertNull(emptyTask);

        Task invalidTask = taskManager.getSubtask(999);
        Assertions.assertNull(invalidTask);
    }

    @Test
    void addNewTask () {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createTask(task);
        List<Task> tasksList = taskManager.getTasks();
        assertNotNull(tasksList);
        Assertions.assertEquals(1, tasksList.size());
        final List<Task> tasks = taskManager.getTasks();
        Assertions.assertEquals(task, tasks.get(0));
    }

    @Test
    void addNewEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createEpic(epic);
        List<Epic> epicList = taskManager.getEpics();
        assertNotNull(epicList);
        Assertions.assertEquals(1, epicList.size());
        Assertions.assertEquals(epic, epicList.get(0));
    }

    @Test
    void addNewSubtask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createSubTask(subTask);
        final Subtask savedTask = taskManager.getSubtask(subTask.getId());
        assertNotNull(savedTask);
        assertEquals(subTask, savedTask);
        final List<Subtask> subtasks = taskManager.getListSubTasks();
        assertNotNull(subtasks);
        Assertions.assertEquals(1, subtasks.size());
        Assertions.assertEquals(subTask, subtasks.get(0));
    }

    @Test
    void deleteTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createTask(task);
        List<Task> tasks = taskManager.getListAllTasks();
        Assertions.assertEquals(1, tasks.size());
        taskManager.deleteTask(task);
        List<Task> tasks1 = taskManager.getListAllTasks();
        Assertions.assertEquals(0, tasks1.size());
    }


    @Test
    void deleteSubTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createSubTask(subTask);
        List<Subtask> subtasks = taskManager.getSubtasks();
        assertEquals(1, subtasks.size());
        taskManager.deleteSubtask(subTask);
        List<Subtask> subtasks1 = taskManager.getSubtasks();
        assertEquals(0, subtasks1.size());
    }

    @Test
    void updateTasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createTask(task);
        List<Task> tasks = taskManager.getTasks();
        Assertions.assertEquals(task, tasks.get(0));
        task.setStatus(Status.DONE);
        taskManager.updateTask(task);
        Assertions.assertEquals(task, tasks.get(0));
    }

    @Test
    void updateSubtasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createSubTask(subTask);
        List<Subtask> subtasks = taskManager.getSubtasks();
        Assertions.assertEquals(subTask, subtasks.get(0));
        subTask.setStatus(Status.DONE);
        taskManager.updateSubtask(subTask);
        Assertions.assertEquals(subTask, subtasks.get(0));
    }

    @Test
    public void setEpicEndTimeTest() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        taskManager.createEpic(epic);
        epic.setEndTime(Instant.ofEpochSecond(0));
        Assertions.assertEquals(Instant.ofEpochSecond(0), epic.getEndTime());
    }


}



