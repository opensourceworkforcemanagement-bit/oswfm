package org.oswfm.timesheetservice.model.entity;


import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
@Table(name = "work_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "work_code_id")
    private Integer  work_code_id;

    @Column(name = "short_work_code", nullable = false)
    private String short_work_code;
 
    @Column(name = "long_work_code", nullable = false)   
    private String long_work_code;    

    @Column(name = "description")
    private String description;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "suffix")
    private String suffix;

    @JdbcTypeCode(SqlTypes.SMALLINT)
    @Column(name = "status", columnDefinition = "int2")
    private Integer  status;

    @Temporal(TemporalType.DATE)
    @Column(name="effective_date")
    private Date effective_date;

    @Temporal(TemporalType.DATE)
    @Column(name="expiration_date")
    private Date expiration_date;

}
