package ch.thgroup.greenfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class GreenfarmApplication {


	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		SpringApplication.run(GreenfarmApplication.class, args);
		System.out.println("Application Started");
	}
}
