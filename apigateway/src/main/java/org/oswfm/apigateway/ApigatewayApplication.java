package org.oswfm.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The entry point for the API Gateway Spring Boot application.
 * This application is a Eureka client that registers itself with a Eureka server.
 * The application is configured with the {@link SpringBootApplication} annotation.
 *
 * <p>SessionAutoConfiguration is excluded to prevent Spring Boot from auto-configuring
 * a second WebSessionManager (with the default "SESSION" cookie name) that conflicts
 * with the custom APPSESSION-named WebSessionManager defined in {@link org.oswfm.apigateway.config.SessionConfig}.
 */
@SpringBootApplication(exclude = SessionAutoConfiguration.class)
@EnableFeignClients
public class ApigatewayApplication {

	/**
	 * Main method to run the Spring Boot application.
	 *
	 * @param args Command-line arguments passed during the application startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

}
