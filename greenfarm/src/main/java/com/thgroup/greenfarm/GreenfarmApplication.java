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


	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		SpringApplication.run(GreenfarmApplication.class, args);
		System.out.println("Application Started");
	}
}
