package org.oswfm.accesscontrolservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EntityScan(basePackages = {
    "org.oswfm.accesscontrolservice.model.entity",
    "org.oswfm.commons.model.user.entity"
})
public class AccessControlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessControlApiApplication.class, args);
    }
}
