package com.thgroup.greenfarm;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SMSServiceImpl implements SMSService {

    private static final String SMS_API_URL = "https://api.send-sms.in.th/api/v2/SendSMS";
    private static final String USERNAME = "thgroup";
    private static final String PASSWORD = "ZPyq9urQxm!dvKtEx";
    private static final String API_KEY = "6mp8i/CWGzMSrSono5w/voqSMNnOF4RdZU9xOBo2mUw=";
    private static final String CLIENT_ID = "e09fb523-7aa6-4aa1-b482-eee2249b136c";
    private static final String SENDER_ID = "MAILBITTEST";
    private static final String MESSAGE = "XXX!";
    private static final String MOBILE_NUMBERS = "66996946359";
    private static final String IS_UNICODE = "true";
    private static final String IS_FLASH = "true";

    @Override
    public String sendSMSGet() throws IOException, InterruptedException, URISyntaxException {
        // Build the URL with query parameters
        String url = buildUrl();

        // Create the Basic Authentication header
        String authHeader = createAuthHeader(USERNAME, PASSWORD);

        // Create the HTTP client and request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(new URI(url))
                .GET()
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Return the response body
        return response.body();
    }

    @Override
    public String sendSMSPost() throws IOException, InterruptedException, URISyntaxException {
        // Create the JSON body
        JSONObject body = new JSONObject();
        body.put("ApiKey", API_KEY);
        body.put("ClientId", CLIENT_ID);
        body.put("SenderId", SENDER_ID);
        body.put("Message", MESSAGE);
        body.put("MobileNumbers", MOBILE_NUMBERS);
        body.put("Is_Unicode", IS_UNICODE);
        body.put("Is_Flash", IS_FLASH);

        // Convert JSON object to string
        String jsonBody = body.toString();

        // Build the URL
        String url = SMS_API_URL;

        // Create the Basic Authentication header
        String authHeader = createAuthHeader(USERNAME, PASSWORD);

        // Create the HTTP client and request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Return the response body
        return response.body();
    }

    private String buildUrl() throws URISyntaxException {
        // Encode special characters in parameters
        String encodedApiKey = URLEncoder.encode(API_KEY, StandardCharsets.UTF_8);
        String encodedMessage = URLEncoder.encode(MESSAGE, StandardCharsets.UTF_8);

        return SMS_API_URL +
                "?ApiKey=" + encodedApiKey +
                "&ClientId=" + CLIENT_ID +
                "&SenderId=" + SENDER_ID +
                "&Message=" + encodedMessage +
                "&MobileNumbers=" + MOBILE_NUMBERS +
                "&Is_Unicode=" + IS_UNICODE +
                "&Is_Flash=" + IS_FLASH;
    }

    private String createAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
