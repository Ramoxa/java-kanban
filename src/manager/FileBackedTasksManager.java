package manager;

import inside.Epic;
import inside.Subtask;
import inside.Task;

import java.io.*;
import java.nio.file.Path;


public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    public FileBackedTasksManager(HistoryManager historyManager) {
        super(historyManager);
    }

    public static FileBackedTasksManager load(Path path) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(historyManager);

        try (FileInputStream fis = new FileInputStream(path.toFile()); InputStreamReader isr = new InputStreamReader(fis); BufferedReader reader = new BufferedReader(isr)) {

            String line = reader.readLine();

            while (line != null) {
                Task task = Formatter.tasksFromString(line);
                String type = line.split(",")[1];

                switch (TaskType.valueOf(type)) {
                    case TASK:
                        fileBackedTasksManager.createTask(task);
                        historyManager.addTask(fileBackedTasksManager.getTask(task.getId()));
                        break;
                    case EPIC:
                        Epic epic = (Epic) task;
                        fileBackedTasksManager.createEpic(epic);
                        historyManager.addTask(fileBackedTasksManager.getEpic(epic.getId()));
                        break;
                    case SUBTASK:
                        Subtask subtask = (Subtask) task;
                        fileBackedTasksManager.createSubTask(subtask);
                        historyManager.addTask(fileBackedTasksManager.getSubtask(subtask.getId()));
                        break;
                    default:
                        break;
                }

                line = reader.readLine();
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла");
        }

        return fileBackedTasksManager;
    }

    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(historyManager);
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

        FileBackedTasksManager fileManager = FileBackedTasksManager.load(Path.of("results.csv"));


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

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(autoSave)); BufferedReader br = new BufferedReader(new FileReader(autoSave))) {

            if (br.readLine() == null) {
                String header = "id,type,name,status,description,epic" + "\n";
                bw.write(header);
            }

            String values = Formatter.tasksToString(this) + "\n" + Formatter.historyToString(historyManager);

            bw.write(values);

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }


}

