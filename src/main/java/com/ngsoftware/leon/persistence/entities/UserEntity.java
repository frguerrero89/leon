package com.ngsoftware.leon.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Table
@Entity(name="users")
@EntityListeners({AuditingEntityListener.class})
@Data
public class UserEntity implements Serializable {

    @Id
    @Column(name = "user", nullable = false)
    private String user;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "company_name", nullable = false)
    private String companyName;
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    @Column(name="disabled", nullable = false)
    private boolean disabled;
    @Column(name="locked", nullable = false)
    private boolean locked;
}
