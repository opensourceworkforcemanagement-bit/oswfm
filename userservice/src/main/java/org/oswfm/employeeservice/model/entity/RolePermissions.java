package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "role_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_permission_id")
    private Integer rolePermissionId;

    @Column(name = "employee_role_id", nullable = false)
    private String employeeRoleId;

    @Column(name = "permissioins_id", nullable = false)
    private Integer permissioinsId;

}
