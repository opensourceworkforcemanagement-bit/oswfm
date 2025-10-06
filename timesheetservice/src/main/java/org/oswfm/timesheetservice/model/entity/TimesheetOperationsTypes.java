package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "timesheet_operations_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetOperationsTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "operation_type_id")
    private Integer operationTypeId;

    @Column(name = "operation_type_name", nullable = false)
    private String operationTypeName;

    @Column(name = "operation_description")
    private String operationDescription;

}
