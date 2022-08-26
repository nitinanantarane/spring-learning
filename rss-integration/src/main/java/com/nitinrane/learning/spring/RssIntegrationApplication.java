package com.nitinrane.learning.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
@ImportResource("/integration/integration.xml")
public class RssIntegrationApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext ctx = new
			SpringApplication(RssIntegrationApplication.class).run(args);

		System.out.println("Hit enter to terminate");

		System.in.read();
		ctx.close();
	}

}
