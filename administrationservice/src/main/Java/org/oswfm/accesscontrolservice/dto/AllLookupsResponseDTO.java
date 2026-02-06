package org.oswfm.accesscontrolservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllLookupsResponseDTO {
    private List<LookupTypeDTO> resourceTypes;
    private List<LookupTypeDTO> attributeCategories;
    private List<LookupTypeDTO> policyTypes;
    private List<LookupTypeDTO> obligationTypes;
    private List<LookupTypeDTO> dataTypes;
}
