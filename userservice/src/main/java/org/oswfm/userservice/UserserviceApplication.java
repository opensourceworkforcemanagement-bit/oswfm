package org.oswfm.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The entry point for the Timesheet service Spring Boot application.
 * This application is a Eureka client that registers itself with a Eureka server.
 * The application is configured with the {@link SpringBootApplication} annotation.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
	"org.oswfm.userservice",
	"org.oswfm.employeeservice",
	"org.oswfm.accesscontrolservice"
})
@EntityScan(basePackages = {
	"org.oswfm.commons.model.user.entity",
	"org.oswfm.userservice.model.entity",
	"org.oswfm.employeeservice.model.entity",
	"org.oswfm.accesscontrolservice.model.entity"
})
@EnableJpaRepositories(basePackages = {
    "org.oswfm.userservice.repository",
    "org.oswfm.employeeservice.repository",
	"org.oswfm.accesscontrolservice.repository"
})
public class UserserviceApplication {

	/**
	 * Main method to run the Spring Boot application.
	 *
	 * @param args Command-line arguments passed during the application startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
