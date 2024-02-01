package manager.taskManagers;
import manager.Managers;
import manager.exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    public static void main(String[] args) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("data"));

        Task task1 = fileBackedTasksManager.createTask(new Task("1", "1111", Instant.EPOCH, 0));
        Task task2 = fileBackedTasksManager.createTask(new Task("22", "22222", Instant.EPOCH, 0));
        Epic epic1 = fileBackedTasksManager.createEpic(new Epic("1", "7777"));
        Epic epic2 = fileBackedTasksManager.createEpic(new Epic("6666", "7777"));
        Subtask subtask1 = fileBackedTasksManager.createSubTask(new Subtask("3333", "44444", Instant.EPOCH, 0));
        Subtask subtask2 = fileBackedTasksManager.createSubTask(new Subtask("5555", "55555", Instant.EPOCH, 0));

        fileBackedTasksManager.getTask(task1.getId());
        fileBackedTasksManager.getTask(task2.getId());
        fileBackedTasksManager.getEpic(epic1.getId());
        fileBackedTasksManager.getEpic(epic2.getId());
        fileBackedTasksManager.getSubtask(subtask1.getId());
        fileBackedTasksManager.getSubtask(subtask2.getId());

        FileBackedTasksManager manager2 = FileBackedTasksManager.loadFromFile(new File("data"));
        System.out.println(manager2.getTasks());
        System.out.println(manager2.getEpics());
        System.out.println(manager2.getSubtasks());

    }
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    private void save() {
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8)) {
            fileWriter.write("id,type,name,status,description,startTime,duration,endTime,epic" + "\n");

            for (Task task : getListAllTasks()) {
                fileWriter.write(Formatter.taskToString(task) + "\n");
            }

            for (Epic epic : getListAllEpic()) {
                fileWriter.write(Formatter.taskToString(epic) + "\n");
            }

            for (Subtask subtask : getListSubTasks()) {
                fileWriter.write(Formatter.taskToString(subtask) + "\n");
            }
            fileWriter.write("\n");
            fileWriter.write(Formatter.historyToString(historyManager));

        } catch (IOException e) {

            throw new ManagerSaveException("Ошибка записи в файл");

        }

    }

    // сделал метод приватным согласно первому ревью
    private void loadFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    break;
                }
                Task task = Formatter.tasksFromString(line);
                String type = line.split(",")[1];

                switch (TaskType.valueOf(type)) {
                    case TASK:
                        createTask(task);
                        break;
                    case EPIC:
                        Epic epic = (Epic) task;
                        createEpic(epic);
                        break;
                    case SUBTASK:
                        Subtask subtask = (Subtask) task;
                        createSubTask(subtask);
                        break;
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла");
        }

    }

    // реализовал тот же метод с учетом комментариев второго ревью
    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        manager.loadFromFile();
        return manager;
    }

    @Override
    public Task getTask(int id) {
        Task savedTask = super.getTask(id);
        save();
        return savedTask;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask savedSubtask = super.getSubtask(id);
        save();
        return savedSubtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic savedEpic = super.getEpic(id);
        save();
        return savedEpic;
    }

    @Override
    public Task createTask(Task task) {
        Task savedTask = super.createTask(task);
        save();
        return savedTask;
    }

    @Override
    public Subtask createSubTask(Subtask subtask) {
        Subtask savedSubtask = super.createSubTask(subtask);
        save();
        return savedSubtask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic savedEpic = super.createEpic(epic);
        save();
        return savedEpic;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }
}


