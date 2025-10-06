package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "account_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_code_id")
    private Integer accountCodeId;

    @Column(name = "account_code", nullable = false)
    private String accountCode;

    private String description;

    private Integer status;

}
