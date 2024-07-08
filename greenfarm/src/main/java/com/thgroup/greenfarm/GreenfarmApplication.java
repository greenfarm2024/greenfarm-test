package com.thgroup.greenfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootApplication
public class GreenfarmApplication {

	private static final String SMS_API_URL = "https://api.send-sms.in.th/api/v2/SendSMS";
	private static final String USERNAME = "thgroup";
	private static final String PASSWORD = "ZPyq9urQxm!dvKtEx";
	private static final String API_KEY = "6mp8i/CWGzMSrSono5w/voqSMNnOF4RdZU9xOBo2mUw=";
	private static final String CLIENT_ID = "e09fb523-7aa6-4aa1-b482-eee2249b136c";
	private static final String SENDER_ID = "MAILBITTEST";
	private static final String MESSAGE = "Test from java!";
	private static final String MOBILE_NUMBERS = "66996946359";
	private static final String IS_UNICODE = "true";
	private static final String IS_FLASH = "true";

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		SpringApplication.run(GreenfarmApplication.class, args);
		System.out.println("Application Started");

		// Encode special characters in parameters
		String encodedApiKey = URLEncoder.encode(API_KEY, StandardCharsets.UTF_8);
		String encodedMessage = URLEncoder.encode(MESSAGE, StandardCharsets.UTF_8);

		// Build the URL with encoded query parameters
		String url = SMS_API_URL +
				"?ApiKey=" + encodedApiKey +
				"&ClientId=" + CLIENT_ID +
				"&SenderId=" + SENDER_ID +
				"&Message=" + encodedMessage +
				"&MobileNumbers=" + MOBILE_NUMBERS +
				"&Is_Unicode=" + IS_UNICODE +
				"&Is_Flash=" + IS_FLASH;

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

		// Print the response status code and body
		System.out.println("Response Code: " + response.statusCode());
		System.out.println("Response Body:\n" + response.body());
	}

	private static String createAuthHeader(String username, String password) {
		String auth = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
	}
}
