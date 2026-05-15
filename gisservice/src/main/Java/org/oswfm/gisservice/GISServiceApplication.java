package org.oswfm.gisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EntityScan(basePackages = {
    "org.oswfm.gisservice.model.entity",
    "org.oswfm.commons.model.user.entity"
})
public class GISServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GISServiceApplication.class, args);
    }
}
