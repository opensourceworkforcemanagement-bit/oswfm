package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "task_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "task_code_id")
    private Integer taskCodeId;

    @Column(name = "task_code", nullable = false)
    private String taskCode;

    private String description;

    private Integer status;

}
