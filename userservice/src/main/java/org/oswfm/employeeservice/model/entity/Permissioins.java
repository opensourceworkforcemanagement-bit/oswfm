package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "permissioins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permissioins {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "permissioins_id")
    private Integer permissioinsId;

    @Column(name = "permission_tag", nullable = false)
    private Integer permissionTag;

    @Column(name = "permission_name", nullable = false)
    private String permissionName;

    private String description;

}
