package manager;

import inside.Epic;
import inside.Subtask;
import inside.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        tests(taskManager);
    }

    private static void tests(TaskManager taskManager) {
        var epic1 = (new Epic("Эпик1", "11"));
        var epic2 = (new Epic("Эпик2", "22"));

        epic1 = taskManager.createEpic(epic1);
        epic2 = taskManager.createEpic(epic2);

        var subtask1 = new Subtask("33", "44");
        var subtask2 = new Subtask("55", "66");
        var subtask3 = new Subtask("77", "88");

        subtask1 = taskManager.createSubTask(subtask1);
        subtask2 = taskManager.createSubTask(subtask2);
        subtask3 = taskManager.createSubTask(subtask3);


        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(subtask3);

        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getEpic(epic2.getId()));
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println(taskManager.getSubtask(subtask2.getId()));
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println(taskManager.getSubtask(subtask2.getId()));
        System.out.println(taskManager.getEpic(epic2.getId()));

        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getEpic(epic2.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getSubtask(subtask2.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getEpic(epic2.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getSubtask(subtask3.getId()));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getHistory());

        taskManager.deleteSubtask(subtask2);
        System.out.println(taskManager.getHistory().size());

        taskManager.deleteTask(epic1);
        System.out.println(taskManager.getHistory().size());
        System.out.println(taskManager.getHistory());


    }
}
