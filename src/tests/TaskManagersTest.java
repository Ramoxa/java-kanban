package tests;


import manager.taskManagers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagersTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subTask;


    @Test
    public void testGetTask() {
        taskManager.createTask(task);
        List<Task> tasksList = taskManager.getTasks();
        Assertions.assertEquals(2, tasksList.size());
        Task emptyTask = taskManager.getTask(123);
        Assertions.assertNull(emptyTask);
        Task invalidTask = taskManager.getTask(999);
        Assertions.assertNull(invalidTask);
    }

    @Test
    public void testGetSubtask() {
        taskManager.createSubTask(subTask);
        List<Subtask> tasksList = taskManager.getSubtasks();
        Assertions.assertEquals(2, tasksList.size());
        Task emptyTask = taskManager.getSubtask(123);
        Assertions.assertNull(emptyTask);
        Task invalidTask = taskManager.getSubtask(999);
        Assertions.assertNull(invalidTask);
    }

    @Test
    void addNewTask() {
        taskManager.createTask(task);
        List<Task> tasksList = taskManager.getTasks();
        assertNotNull(tasksList);
        Assertions.assertEquals(2, tasksList.size());
        final List<Task> tasks = taskManager.getTasks();
        Assertions.assertEquals(task, tasks.get(0));
    }

    @Test
    void addNewEpic() {
        taskManager.createEpic(epic);
        List<Epic> epicList = taskManager.getEpics();
        assertNotNull(epicList);
        Assertions.assertEquals(2, epicList.size());
        Assertions.assertEquals(epic, epicList.get(0));
    }

    @Test
    void addNewSubtask() {
        taskManager.createSubTask(subTask);
        final Subtask savedTask = taskManager.getSubtask(subTask.getId());
        assertNotNull(savedTask);
        assertEquals(subTask, savedTask);
        final List<Subtask> subtasks = taskManager.getListSubTasks();
        assertNotNull(subtasks);
        Assertions.assertEquals(2, subtasks.size());
        Assertions.assertEquals(subTask, subtasks.get(0));
    }

    @Test
    void deleteTask() {
        taskManager.createTask(task);
        List<Task> tasks = taskManager.getListAllTasks();
        Assertions.assertEquals(2, tasks.size());
    }


    @Test
    public void deleteSubtask() {
        taskManager.deleteSubtask(subTask.getId());
        assertTrue(taskManager.getSubtasks().isEmpty());
    }

    @Test
    void updateTasks() {
        taskManager.createTask(task);
        List<Task> tasks = taskManager.getTasks();
        Assertions.assertEquals(task, tasks.get(0));
        task.setStatus(Status.DONE);
        taskManager.updateTask(task);
        Assertions.assertEquals(task, tasks.get(0));
    }

    @Test
    void updateSubtasks() {
        taskManager.createSubTask(subTask);
        List<Subtask> subtasks = taskManager.getSubtasks();
        Assertions.assertEquals(subTask, subtasks.get(0));
        subTask.setStatus(Status.DONE);
        taskManager.updateSubtask(subTask);
        Assertions.assertEquals(subTask, subtasks.get(0));
    }

    @Test
    public void setEpicEndTimeTest() {
        taskManager.createEpic(epic);
        epic.setEndTime(Instant.ofEpochSecond(0));
        Assertions.assertEquals(Instant.ofEpochSecond(0), epic.getEndTime());
    }


}