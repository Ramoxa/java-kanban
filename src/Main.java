

public class Main {

    public static void main(String[] args) {
        Manadger manadger = new Manadger(); // пробовал тестить
Task taskA = new Task("Приготовить ужин", "Чтоб вкусно");
manadger.createTask(taskA);
        manadger.printAllTasks(manadger.getTasks());
        manadger.updateTask(taskA);
        manadger.deleteTask(taskA);


        Epic epicA = new Epic("Схлодить в магаз", "В пятерочку");
        manadger.createEpic(epicA);




    }
}
