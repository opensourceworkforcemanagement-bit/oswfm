package org.oswfm.employeeservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.oswfm.commons.model.user.User;
import org.oswfm.commons.model.user.dto.request.RegisterRequest;
import org.oswfm.employeeservice.model.dto.CreateEmployeeWithUserRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeeUserRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesResponseDTO;
import org.oswfm.employeeservice.model.entity.EmployeeDetails;
import org.oswfm.employeeservice.model.mapper.EmployeesMapper;
import org.oswfm.employeeservice.repository.EmployeesRepository;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeesService {

    @Autowired
    private EmployeesRepository repository;

    @Autowired
    private EmployeesMapper mapper;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private EmployeeUserService employeeUserService;

    /**
     * Create a new Employees
     */
    public EmployeesResponseDTO create(EmployeesRequestDTO requestDTO) {
        EmployeeDetails entity = mapper.toEntity(requestDTO);
        EmployeeDetails saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Get Employees by ID
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesResponseDTO> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO);
    }

    /**
     * Get all Employeess
     */
    @Transactional(readOnly = true)
    public List<EmployeesResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing Employees
     */
    public Optional<EmployeesResponseDTO> update(Integer id, EmployeesRequestDTO requestDTO) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDTO(requestDTO, entity);
                    EmployeeDetails updated = repository.save(entity);
                    return mapper.toResponseDTO(updated);
                });
    }

    /**
     * Delete Employees by ID
     */
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if Employees exists by ID
     */
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Get count of all Employeess
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    /**
     * Create a new Employee, optionally creating a User account.
     * If createUser is true and the username is already taken, throws IllegalArgumentException.
     */
    public EmployeesResponseDTO createWithUser(CreateEmployeeWithUserRequestDTO requestDTO) {
        if (requestDTO.isCreateUser()) {
            // Check if username is already taken
            if (userEntityRepository.existsUserEntityByUserName(requestDTO.getUserName())) {
                throw new IllegalArgumentException(
                        "Username is already taken: " + requestDTO.getUserName());
            }

            // Register the user
            RegisterRequest registerRequest = RegisterRequest.builder()
                    .userName(requestDTO.getUserName())
                    .password(requestDTO.getPassword())
                    .firstName(requestDTO.getFirstName())
                    .lastName(requestDTO.getLastName())
                    .middleName(requestDTO.getMiddleName())
                    .build();

            User createdUser = registerService.registerUser(registerRequest);

            // Create the employee
            EmployeesRequestDTO employeeRequest = new EmployeesRequestDTO();
            employeeRequest.setEmployeeIdentifier(requestDTO.getEmployeeIdentifier());
            employeeRequest.setFirstName(requestDTO.getFirstName());
            employeeRequest.setMiddleName(requestDTO.getMiddleName());
            employeeRequest.setLastName(requestDTO.getLastName());
            employeeRequest.setStatus(requestDTO.getStatus());
            employeeRequest.setUserName(requestDTO.getUserName());

            EmployeesResponseDTO employeeResponse = create(employeeRequest);

            // Link the employee to the user
            EmployeeUserRequestDTO linkRequest = new EmployeeUserRequestDTO();
            linkRequest.setEmployeeId(employeeResponse.getEmployeeId());
            linkRequest.setUserId(createdUser.getUserId());
            employeeUserService.create(linkRequest);

            return employeeResponse;
        } else {
            // Just create the employee without a user
            EmployeesRequestDTO employeeRequest = new EmployeesRequestDTO();
            employeeRequest.setEmployeeIdentifier(requestDTO.getEmployeeIdentifier());
            employeeRequest.setFirstName(requestDTO.getFirstName());
            employeeRequest.setMiddleName(requestDTO.getMiddleName());
            employeeRequest.setLastName(requestDTO.getLastName());
            employeeRequest.setStatus(requestDTO.getStatus());
            employeeRequest.setUserName(requestDTO.getUserName());

            return create(employeeRequest);
        }
    }
}
