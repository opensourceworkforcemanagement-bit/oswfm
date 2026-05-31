package org.oswfm.monolith;

import org.oswfm.accesscontrolservice.AccessControlApiApplication;
import org.oswfm.authservice.AuthserviceApplication;
import org.oswfm.gisservice.GISServiceApplication;
import org.oswfm.kafkaservice.KafkaServiceApplication;
import org.oswfm.notificationservice.NotificationServiceApplication;
import org.oswfm.signalingservice.SignalingServiceApplication;
import org.oswfm.timesheetservice.TimesheetserviceApplication;
import org.oswfm.userservice.UserserviceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Monolith entry point — starts all OSWFM services in a single ApplicationContext.
 *
 * Excluded modules:
 *   - apigateway: uses Spring WebFlux (reactive) which is incompatible with the servlet stack
 *   - eurekaserver: @EnableEurekaServer must not run inside the monolith
 *
 * Each service's own @SpringBootApplication class is excluded so that only
 * MonolithApplication's @EnableJpaRepositories and @EnableFeignClients take effect.
 *
 * Duplicate-named beans from competing services are excluded in favour of the
 * userservice equivalents (security config, filters, exception handlers).
 *
 * Activate with Spring profile "monolith" (set automatically by this class).
 * Infrastructure (Postgres + Kafka) must be started first via docker-compose.monolith.yml.
 */
@SpringBootApplication
@ComponentScan(
    basePackages = {
        "org.oswfm.monolith",
        "org.oswfm.authservice",
        "org.oswfm.userservice",
        "org.oswfm.employeeservice",
        "org.oswfm.gisservice",
        "org.oswfm.signalingservice",
        "org.oswfm.kafkaservice",
        "org.oswfm.kafkaserviceclient",
        "org.oswfm.orderservice",
        "org.oswfm.notificationservice",
        "org.oswfm.timesheetservice",
        "org.oswfm.accesscontrolservice",
        "org.oswfm.commons"
    },
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
            // ── Service bootstrap classes ──────────────────────────────────────
            // Prevent their own @EnableJpaRepositories / @ComponentScan from firing
            AuthserviceApplication.class,
            UserserviceApplication.class,
            GISServiceApplication.class,
            SignalingServiceApplication.class,
            KafkaServiceApplication.class,
            NotificationServiceApplication.class,
            TimesheetserviceApplication.class,
            AccessControlApiApplication.class,

            // ── SecurityConfig duplicates ──────────────────────────────────────
            // Keep org.oswfm.userservice.config.SecurityConfig; exclude the rest
            org.oswfm.authservice.config.SecurityConfig.class,
            org.oswfm.gisservice.config.SecurityConfig.class,
            org.oswfm.notificationservice.config.SecurityConfig.class,
            org.oswfm.timesheetservice.config.SecurityConfig.class,
            org.oswfm.accesscontrolservice.config.SecurityConfig.class,

            // ── authservice beans with same name as userservice equivalents ───
            // AuthController + its 4 service impls all conflict with userservice beans
            // of the same name but different interfaces. Exclude the whole authservice
            // auth stack; UserController at /api/v1/users covers the same operations.
            org.oswfm.authservice.controller.AuthController.class,
            org.oswfm.authservice.service.impl.LogoutServiceImpl.class,
            org.oswfm.authservice.service.impl.RefreshTokenServiceImpl.class,
            org.oswfm.authservice.service.impl.RegisterServiceImpl.class,
            org.oswfm.authservice.service.impl.UserLoginServiceImpl.class,

            // ── CustomBearerTokenAuthenticationFilter duplicates ───────────────
            // Keep org.oswfm.userservice.filter.CustomBearerTokenAuthenticationFilter
            org.oswfm.authservice.filter.CustomBearerTokenAuthenticationFilter.class,
            org.oswfm.timesheetservice.filter.CustomBearerTokenAuthenticationFilter.class,

            // ── CustomAuthenticationEntryPoint duplicates ──────────────────────
            // Keep org.oswfm.userservice.security.CustomAuthenticationEntryPoint
            org.oswfm.authservice.security.CustomAuthenticationEntryPoint.class,
            org.oswfm.gisservice.security.CustomAuthenticationEntryPoint.class,
            org.oswfm.timesheetservice.security.CustomAuthenticationEntryPoint.class,
            org.oswfm.accesscontrolservice.security.CustomAuthenticationEntryPoint.class,

            // ── TokenConfigurationParameter duplicates ─────────────────────────
            // Keep org.oswfm.userservice.config.TokenConfigurationParameter
            org.oswfm.gisservice.config.TokenConfigurationParameter.class,
            org.oswfm.timesheetservice.config.TokenConfigurationParameter.class,
            org.oswfm.accesscontrolservice.config.TokenConfigurationParameter.class,

            // ── GlobalExceptionHandler duplicates ──────────────────────────────
            // Keep org.oswfm.userservice.exception.handler.GlobalExceptionHandler
            org.oswfm.kafkaservice.exception.GlobalExceptionHandler.class,
            org.oswfm.timesheetservice.exception.GlobalExceptionHandler.class,

            // ── FeignClientConfig duplicates ───────────────────────────────────
            // Keep org.oswfm.gisservice.config.FeignClientConfig
            org.oswfm.timesheetservice.config.FeignClientConfig.class,
            org.oswfm.accesscontrolservice.config.FeignClientConfig.class,

            // ── WebSocketConfig duplicates ─────────────────────────────────────
            // Keep org.oswfm.signalingservice.config.WebSocketConfig
            org.oswfm.kafkaservice.config.WebSocketConfig.class,
            org.oswfm.notificationservice.config.WebSocketConfig.class,

            // ── AccessControlExceptionHandler duplicates ───────────────────────
            // Keep org.oswfm.accesscontrolservice.exception.AccessControlExceptionHandler
            org.oswfm.gisservice.exception.AccessControlExceptionHandler.class,
        }
    )
)
@EntityScan(basePackages = {
    "org.oswfm.commons.model.user.entity",
    "org.oswfm.userservice.model.entity",
    "org.oswfm.employeeservice.model.entity",
    "org.oswfm.gisservice.model.entity",
    "org.oswfm.orderservice.entity",
    "org.oswfm.timesheetservice.model.entity",
    "org.oswfm.accesscontrolservice.model.entity"
})
@EnableJpaRepositories(basePackages = {
    "org.oswfm.userservice.repository",
    "org.oswfm.employeeservice.repository",
    "org.oswfm.gisservice.repository",
    "org.oswfm.orderservice.repository",
    "org.oswfm.timesheetservice.repository",
    "org.oswfm.accesscontrolservice.repository"
})
@EnableFeignClients(basePackages = {
    "org.oswfm.authservice",
    "org.oswfm.gisservice",
    "org.oswfm.timesheetservice"
})
@EnableScheduling
public class MonolithApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MonolithApplication.class);
        app.setAdditionalProfiles("monolith");
        app.run(args);
    }
}
