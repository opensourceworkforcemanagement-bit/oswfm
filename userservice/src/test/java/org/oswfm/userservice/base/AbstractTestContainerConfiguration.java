package org.oswfm.userservice.base;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTestContainerConfiguration {

    static GenericContainer<?> POSTGRESQL_CONTAINER = new GenericContainer<>("postgres:15.3")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "testdb")
            .withEnv("POSTGRES_USER", "testuser")
            .withEnv("POSTGRES_PASSWORD", "testpass")
            .waitingFor(Wait.forListeningPort());

    @BeforeAll
    static void beforeAll() {
        POSTGRESQL_CONTAINER.withReuse(true);
        POSTGRESQL_CONTAINER.start();
    }

    @DynamicPropertySource
    private static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        String jdbcUrl = String.format("jdbc:postgresql://%s:%d/testdb", 
            POSTGRESQL_CONTAINER.getHost(), 
            POSTGRESQL_CONTAINER.getMappedPort(5432));
            
        dynamicPropertyRegistry.add("spring.datasource.username", () -> "testuser");
        dynamicPropertyRegistry.add("spring.datasource.password", () -> "testpass");
        dynamicPropertyRegistry.add("spring.datasource.url", () -> jdbcUrl);
    }
}
