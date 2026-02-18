package org.oswfm.timesheetservice.model.entity;

import java.util.Date;

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
@Table(name = "workforce_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkforceCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code_type_id", nullable = false)
    private Integer codeTypeId;

    @Column(name = "code_id", nullable = false)
    private Integer codeId;

    @Column(name = "prefix", length = 10)
    private String prefix;

    @Column(name = "suffix", length = 10)
    private String suffix;

    @Column(name = "short_code_value", nullable = false, length = 10)
    private String shortCodeValue;

    @Column(name = "long_code_value", nullable = false, length = 255)
    private String longCodeValue;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", columnDefinition = "int2")
    private Integer status;

    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "expiration_date")
    private Date expirationDate;
}
