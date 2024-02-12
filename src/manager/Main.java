package manager;

import manager.taskManagers.TaskManager;
import server.HttpTaskServer;
import server.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws IOException {
        new KVServer().start();
        TaskManager taskManager = Managers.getDefault();
        HttpTaskServer taskServer = new HttpTaskServer(taskManager);

        taskServer.start();

        System.out.println("Добавляем задачи");
        Task task1 = new Task("Задача1", "Описание", Instant.EPOCH, 60);
        taskManager.createTask(task1);

        Task task2 = new Task("Задача2", "Описание", Instant.EPOCH, 60);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Эпик1", "Описание");
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Сабтаск1", "Описание", Instant.EPOCH, 10, epic1.getId());
        taskManager.createSubTask(subtask1);
        Subtask subtask2 = new Subtask("Сабтаск2", "Описание", Instant.EPOCH, 10, epic1.getId());
        taskManager.createSubTask(subtask2);

        taskManager.getTasks().forEach(System.out::println);
        taskManager.getEpics().forEach(System.out::println);
        taskManager.getSubtasks().forEach(System.out::println);

    }
}

