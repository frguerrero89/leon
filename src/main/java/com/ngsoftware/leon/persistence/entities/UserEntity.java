package com.ngsoftware.leon.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Table
@Entity(name = "users")
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
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    @Column(name = "disabled", nullable = false)
    private boolean disabled;
    @Column(name = "locked", nullable = false)
    private boolean locked;
    @ManyToMany
    @JoinTable(name = "user_has_rol", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private List<RolEntity> roles;
}
