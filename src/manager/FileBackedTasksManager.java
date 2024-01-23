package manager;

import inside.Epic;
import inside.Subtask;
import inside.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static manager.Formatter.historyToString;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private final File file;
    public FileBackedTasksManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    public void load() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Task task = Formatter.tasksFromString(line);
                String type = line.split(",")[1];

                switch (TaskType.valueOf(type)) {
                    case TASK:
                        createTask(task);
                        InMemoryTaskManager.historyManager.addTask(task);
                        break;
                    case EPIC:
                        Epic epic = (Epic) task;
                        createEpic(epic);
                        break;
                    case SUBTASK:
                        Subtask subtask = (Subtask) task;
                        createSubTask(subtask);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла");
        }

    }

    public static void main(String[] args) {
        Path path = Path.of("1.csv");
        File file = new File(String.valueOf(path));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);

        Task task1 = fileBackedTasksManager.createTask(new Task("1", "1111"));
        Task task2 = fileBackedTasksManager.createTask(new Task("22", "22222"));
        Epic epic1 = fileBackedTasksManager.createEpic(new Epic("1", "7777"));
        Epic epic2 = fileBackedTasksManager.createEpic(new Epic("6666", "7777"));
        Subtask subtask1 = fileBackedTasksManager.createSubTask(new Subtask("3333", "44444"));
        Subtask subtask2 = fileBackedTasksManager.createSubTask(new Subtask("5555", "55555"));

        fileBackedTasksManager.getTask(task1.getId());
        fileBackedTasksManager.getTask(task2.getId());
        fileBackedTasksManager.getEpic(epic1.getId());
        fileBackedTasksManager.getEpic(epic2.getId());
        fileBackedTasksManager.getSubtask(subtask1.getId());
        fileBackedTasksManager.getSubtask(subtask2.getId());

        System.out.println(fileBackedTasksManager);
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

    public Subtask createSubTask(Subtask subtask) {
        Subtask savedSubtask = super.createSubTask(subtask);
        save();
        return savedSubtask;
    }

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
    public Epic addSubtaskToEpic(Epic epic, Subtask subtask) {
        Epic savedEpic = super.addSubtaskToEpic(epic, subtask);
        save();
        return savedEpic;
    }

    @Override
    public Subtask deleteSubtask(Subtask subtask) {
        Subtask savedSubtask = super.deleteSubtask(subtask);
        save();
        return savedSubtask;
    }

    @Override
    public Epic deleteSubtaskFromEpic(Epic epic, Subtask subtask) {
        Epic savedEpic = super.deleteSubtaskFromEpic(epic, subtask);
        save();
        return savedEpic;
    }

    @Override
    public Task deleteTask(Task task) {
        Task savedTask = super.deleteTask(task);
        save();
        return savedTask;
    }

    private void save() {
        File autoSave = new File("results.csv");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(autoSave))) {

            if (autoSave.length() == 0) {
                String header = "id,type,name,status,description,epic" + "\n";
                bw.write(header);
            }

            bw.write(Formatter.tasksToString(this) + "\n" + historyToString(historyManager));

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

}

