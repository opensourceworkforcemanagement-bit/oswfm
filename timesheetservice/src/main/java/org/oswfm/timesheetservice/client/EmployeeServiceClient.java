package org.oswfm.timesheetservice.client;

import org.oswfm.timesheetservice.config.FeignClientConfig;
import org.oswfm.timesheetservice.model.dto.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "userservice", contextId = "employeeServiceClient", path = "/api/v1/employees", configuration = FeignClientConfig.class)
public interface EmployeeServiceClient {

    @GetMapping("/{id}")
    EmployeeDTO getById(@PathVariable("id") Integer id);

    @GetMapping
    List<EmployeeDTO> getAll();

}
