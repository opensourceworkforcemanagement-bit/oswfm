package org.oswfm.accesscontrolservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LookupTypeDTO {
    private Integer id;
    private String name;
    private String description;
}
