package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "organization")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "organization_id")
    private Integer organizationId;

    @Column(name = "parent_organization_id")
    private Integer parentOrganizationId;

    @Column(nullable = false)
    private String organization;

    private String description;

    @Column(nullable = false)
    private Integer status;

}
