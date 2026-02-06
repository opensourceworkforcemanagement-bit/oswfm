package org.oswfm.accesscontrolservice.service;

import org.oswfm.accesscontrolservice.dto.AllLookupsResponseDTO;
import org.oswfm.accesscontrolservice.dto.LookupTypeDTO;
import org.oswfm.accesscontrolservice.model.entity.AttributeCategory;
import org.oswfm.accesscontrolservice.model.entity.DataType;
import org.oswfm.accesscontrolservice.model.entity.ObligationType;
import org.oswfm.accesscontrolservice.model.entity.PolicyType;
import org.oswfm.accesscontrolservice.model.entity.ResourceType;
import org.oswfm.accesscontrolservice.repository.AttributeCategoryRepository;
import org.oswfm.accesscontrolservice.repository.DataTypeRepository;
import org.oswfm.accesscontrolservice.repository.ObligationTypeRepository;
import org.oswfm.accesscontrolservice.repository.PolicyTypeRepository;
import org.oswfm.accesscontrolservice.repository.ResourceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LookupService {

    private final ResourceTypeRepository resourceTypeRepository;
    private final AttributeCategoryRepository attributeCategoryRepository;
    private final PolicyTypeRepository policyTypeRepository;
    private final ObligationTypeRepository obligationTypeRepository;
    private final DataTypeRepository dataTypeRepository;

    @Transactional(readOnly = true)
    public AllLookupsResponseDTO getAllLookups() {
        return new AllLookupsResponseDTO(
                getResourceTypes(),
                getAttributeCategories(),
                getPolicyTypes(),
                getObligationTypes(),
                getDataTypes()
        );
    }

    @Transactional(readOnly = true)
    public List<LookupTypeDTO> getResourceTypes() {
        return resourceTypeRepository.findAll().stream()
                .map(this::convertResourceType)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LookupTypeDTO> getAttributeCategories() {
        return attributeCategoryRepository.findAll().stream()
                .map(this::convertAttributeCategory)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LookupTypeDTO> getPolicyTypes() {
        return policyTypeRepository.findAll().stream()
                .map(this::convertPolicyType)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LookupTypeDTO> getObligationTypes() {
        return obligationTypeRepository.findAll().stream()
                .map(this::convertObligationType)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LookupTypeDTO> getDataTypes() {
        return dataTypeRepository.findAll().stream()
                .map(this::convertDataType)
                .collect(Collectors.toList());
    }

    private LookupTypeDTO convertResourceType(ResourceType entity) {
        return new LookupTypeDTO(
                entity.getResourceTypeId(),
                entity.getTypeName(),
                entity.getDescription()
        );
    }

    private LookupTypeDTO convertAttributeCategory(AttributeCategory entity) {
        return new LookupTypeDTO(
                entity.getAttributeCategoryId(),
                entity.getCategoryName(),
                entity.getDescription()
        );
    }

    private LookupTypeDTO convertPolicyType(PolicyType entity) {
        return new LookupTypeDTO(
                entity.getPolicyTypeId(),
                entity.getTypeName(),
                entity.getDescription()
        );
    }

    private LookupTypeDTO convertObligationType(ObligationType entity) {
        return new LookupTypeDTO(
                entity.getObligationTypeId(),
                entity.getTypeName(),
                entity.getDescription()
        );
    }

    private LookupTypeDTO convertDataType(DataType entity) {
        return new LookupTypeDTO(
                entity.getDataTypeId(),
                entity.getTypeName(),
                entity.getDescription()
        );
    }
}
