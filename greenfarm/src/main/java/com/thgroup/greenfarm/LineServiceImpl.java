package com.thgroup.greenfarm;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class LineServiceImpl implements LineService {

    private static final String LINE_API_URL = "https://api.line.me/v2/bot/message/push";
    private static final String CHANNEL_TOKEN = "MJsL1F7Pqr7ZusUlG5rUbvu6TJWYZG3UevTRnF8sy6HGBDeecA79JAFRHHJLHWTJB7JSBME2J004YDac1mHB4IblpesR1cNYo03zYMNUAlbEB6o0MZNYKs61UQeoWin5lQirCweOzd/gDhEwyC9mxAdB04t89/1O/w1cDnyilFU=";
    private static final String TO = "Ud730cdf6d79ad0f83cbd2e39de2c0d9d";
    private static final String MESSAGE_TEXT = "Hello, world1";

    @Override
    public String sendLinePost() throws IOException, InterruptedException, URISyntaxException {
        // Create the JSON body
        JSONObject body = new JSONObject();
        body.put("to", TO);
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("type", "text");
        message.put("text", MESSAGE_TEXT);
        messages.put(message);
        body.put("messages", messages);

        // Convert JSON object to string
        String jsonBody = body.toString();

        // Create the HTTP client and request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(new URI(LINE_API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Authorization", "Bearer " + CHANNEL_TOKEN)
                .header("Content-Type", "application/json")
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Return the response body
        return response.body();
    }

}
