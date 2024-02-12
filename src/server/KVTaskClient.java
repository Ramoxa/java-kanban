package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import manager.exceptions.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    protected final String apikey;
    private final String url;
    private final HttpClient client = HttpClient.newHttpClient();
    private final HttpResponse.BodyHandler handler = HttpResponse.BodyHandlers.ofString();

    public KVTaskClient(String url) {
        this.url = url;
        try {
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url + "/register")).build();

            HttpResponse<String> response = client.send(request, handler);
            apikey = response.body();
        } catch (IOException | InterruptedException exception) {
            throw new ManagerSaveException("Error");
        }
    }

    public void put(String key, String json) {
        try {
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpRequest request = HttpRequest.newBuilder().POST(body).uri(URI.create(url + "/save/" + key + "?API_TOKEN=" + apikey)).build();

            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() != 200) {
                throw new RuntimeException("");
            }
        } catch (IOException | InterruptedException | ManagerSaveException exception) {
            throw new ManagerSaveException("Error");
        }
    }

    public String load(String key) {
        URI uri = URI.create(url + "/load/" + key + "?API_TOKEN=" + apikey);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new ManagerSaveException("Ошибка загрузки данных с сервера");
            }
        } catch (IOException | InterruptedException exception) {
            throw new ManagerSaveException("Ошибка загрузки данных с сервера");
        }
    }
}