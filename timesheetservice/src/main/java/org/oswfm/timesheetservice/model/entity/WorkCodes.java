package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "work_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "work_code_id")
    private Integer workCodeId;

    @Column(name = "work_code", nullable = false)
    private String workCode;

    private String description;

    private Integer status;

}
