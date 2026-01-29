package org.oswfm.timesheetservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "code_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_type_id")
    private Integer codeTypeId;

    @Column(name = "code_type_name", nullable = false, unique = true, length = 50)
    private String codeTypeName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
