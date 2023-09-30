package com.ngsoftware.leon.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table
@Entity(name = "employee")
@Data
public class EmployeeEntity {
    @Column(name = "company_id")
    private String companyId;
    @Id
    @Column(name = "employee_id")
    private String employeeId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "personal_number")
    private String personalNumber;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private UserEntity user;
}
