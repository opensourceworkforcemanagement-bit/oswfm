package org.oswfm.timesheetservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lightweight reference DTO for workforce codes.
 * Accepts the full object from the frontend but only requires the id
 * to link to the existing WorkforceCodes entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkforceCodeRefDTO {

    private Long id;

    private Integer codeTypeId;
}
