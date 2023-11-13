package manager;

import inside.Epic;
import inside.Subtask;
import inside.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        Task task1 = taskManager.createTask(
                new Task("1", "aa")
        );
        Task task2 = taskManager.createTask(
                new Task("2", "вв")
        );

        Epic epic1 = taskManager.createEpic(
                new Epic("3", "гг")
        );
        Epic epic2 = taskManager.createEpic(
                new Epic("4", "йй")
        );

        Subtask subtask1 = taskManager.createSubTask(
                new Subtask("5", "уу"));
        Subtask subtask2 = taskManager.createSubTask(
                new Subtask("6", "pp"));

        }
}
