package manager.taskManagers;

import manager.exceptions.ManagerSaveException;
import manager.historyManagers.HistoryManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        manager.loadFromFile();
        return manager;
    }

    protected void save() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            fileWriter.write("id,type,name,status,description,startTime,duration,epic" + "\n");

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
            throw new ManagerSaveException("Error writing to file");
        }
    }

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
            String historyLine = bufferedReader.readLine();
            if (historyLine != null && !historyLine.isBlank()) {
                String[] historyIds = historyLine.split(",");
                for (String id : historyIds) {
                    int taskId = Integer.parseInt(id);
                    Task task = getTask(taskId);
                    if (task != null) {
                        historyManager.addTask(task);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error loading from file");
        }
    }

    @Override
    public Task createTask(Task task) {
        Task newTask = super.createTask(task);
        save();
        return newTask;
    }

    @Override
    public Subtask createSubTask(Subtask subtask) {
        Subtask newSubtask = super.createSubTask(subtask);
        save();
        return newSubtask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic newEpic = super.createEpic(epic);
        save();
        return newEpic;
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


