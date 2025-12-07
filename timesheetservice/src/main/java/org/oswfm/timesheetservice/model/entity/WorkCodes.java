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
@Table(name = "work_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "work_code_id")
    private Integer  workCodeId;

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

    @Column(name = "status")    
    private Integer  status;

}
