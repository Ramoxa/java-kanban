package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.taskManagers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private static final Integer PORT = 8080;
    private final TaskManager taskManager;
    private final HttpServer server;
    private final Gson gson;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        gson = new GsonBuilder().create();
        server.createContext("/tasks", this::handle);
    }

    public static void main(String[] args) throws IOException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        HttpTaskServer httpTaskServer = new HttpTaskServer(Managers.getDefault());
        httpTaskServer.start();
    }

    public void start() {
        server.createContext("/tasks", this::handle);
        server.setExecutor(null);
        server.start();
        System.out.println("Started userServer " + PORT);
        System.out.println("http://localhost:" + PORT + "/tasks");
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        if (query != null) {
            int idQuery = Integer.parseInt(query.replaceFirst("id=", ""));
        }

        switch (method) {
            case "GET":
                if (query == null) {
                    if (Pattern.matches("/tasks/", path)) {
                        String string = gson.toJson(taskManager.getPrioritizedTasks());
                        writeResponse(exchange, string, 200);
                    }
                    if (Pattern.matches("/tasks/task/", path)) {
                        String string = gson.toJson(taskManager.getTasks());
                        writeResponse(exchange, string, 200);
                    }
                    if (Pattern.matches("/tasks/epic/", path)) {
                        String string = gson.toJson(taskManager.getEpics());
                        writeResponse(exchange, string, 200);
                    }
                    if (Pattern.matches("/tasks/subtask/", path)) {
                        String string = gson.toJson(taskManager.getSubtasks());
                        writeResponse(exchange, string, 200);
                    }
                    if (Pattern.matches("/tasks/history/", path)) {
                        String string = gson.toJson(taskManager.getHistory());
                        writeResponse(exchange, string, 200);
                    }
                } else {
                    if (Pattern.matches("/tasks/task/", path)) {
                        int id = Integer.parseInt(query.substring(3));
                        Task task = taskManager.getTask(id);
                        String taskToString = gson.toJson(task);
                        writeResponse(exchange, taskToString, 200);
                    }
                    if (Pattern.matches("/tasks/epic/", path)) {
                        int id = Integer.parseInt(query.substring(3));
                        Epic epic = taskManager.getEpic(id);
                        String subtaskToString = gson.toJson(epic);
                    }
                    if (Pattern.matches("/tasks/subtask/", path)) {
                        int id = Integer.parseInt(query.substring(3));
                        Subtask subtask = taskManager.getSubtask(id);
                        String subtaskToString = gson.toJson(subtask);
                    }
                }
                break;
            case "POST":
                if (Pattern.matches("/tasks/task/", path)) {
                    String text = readText(exchange);
                    JsonParser parser = new JsonParser();
                    JsonElement jsonElement = parser.parse(text);
                    if (jsonElement.isJsonObject()) {
                        Task task = gson.fromJson(jsonElement, Task.class);
                        if (task.getId() != null) {
                            taskManager.updateTask(task);
                        } else {
                            taskManager.createTask(task);
                        }
                    }
                }
                if (Pattern.matches("/tasks/epic/", path)) {
                    String text = readText(exchange);
                    JsonParser parser = new JsonParser();
                    JsonElement jsonElement = parser.parse(text);
                    if (jsonElement.isJsonObject()) {
                        Epic epic = gson.fromJson(jsonElement, Epic.class);
                        if (epic.getId() != null) {
                            taskManager.updateEpic(epic);
                        } else {
                            taskManager.createEpic(epic);
                        }
                    }
                }
                if (Pattern.matches("/tasks/subtask/", path)) {
                    String text = readText(exchange);
                    JsonParser parser = new JsonParser();
                    JsonElement jsonElement = parser.parse(text);
                    if (jsonElement.isJsonObject()) {
                        Subtask subtask = gson.fromJson(jsonElement, Subtask.class);
                        if (subtask.getId() != null) {
                            taskManager.updateSubtask(subtask);
                        } else {
                            taskManager.createTask(subtask);
                        }
                    }
                }
                break;
            case "DELETE":
                if (query == null) {
                    if (Pattern.matches("/tasks/task/", path)) {
                        taskManager.deleteTasks();
                        exchange.sendResponseHeaders(200, 0);
                    }
                    if (Pattern.matches("/tasks/epic/", path)) {
                        taskManager.deleteEpics();
                        exchange.sendResponseHeaders(200, 0);
                    }
                    if (Pattern.matches("/tasks/subtask/", path)) {
                        taskManager.deleteSubtasks();
                        exchange.sendResponseHeaders(200, 0);
                    }
                } else {
                    if (Pattern.matches("/tasks/task/", path)) {
                        int id = Integer.parseInt(query.substring(3));
                        taskManager.deleteTask(id);
                        exchange.sendResponseHeaders(200, 0);
                    }
                    if (Pattern.matches("/tasks/epic/", path)) {
                        int id = Integer.parseInt(query.substring(3));
                        taskManager.getEpic(id);
                        exchange.sendResponseHeaders(200, 0);
                    }
                    if (Pattern.matches("/tasks/subtask/", path)) {
                        int id = Integer.parseInt(query.substring(3));
                        taskManager.deleteSubtask(id);
                        exchange.sendResponseHeaders(200, 0);
                    }
                }
                break;
            default:
                writeResponse(exchange, "Ошибка запроса ", 404);
        }

        exchange.close();
    }

    private static void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
        if (responseString.equals("")) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            exchange.sendResponseHeaders(responseCode, 0);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(responseString.getBytes(UTF_8));
            }
        }
    }

    private String readText(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return new String(inputStream.readAllBytes(), UTF_8);
    }

}