package com.ngsoftware.leon.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Representación de la tabla de base de datos "user" donde se almacena toda
 * la información de los usuarios del sistema.
 * 
 * @author Francisco Guerrero Peláez
 */
@Table
@Entity(name = "users")
@Data
public class UserEntity implements Serializable {

    /**
     * Nombre de usuario. Valor unico
     */
    @Id
    @Column(name = "user", nullable = false)
    private String user;
    /**
     * Contraseña del usuario, este valor se se encuentra encriptado
     */
    @Column(name = "password", nullable = false)
    private String password;
    /**
     * Correo electrónico registrado para el usuario, usualmente el mismo del empleado.
     */
    @Column(name = "email", nullable = false)
    private String email;
    /**
     * Teléfono registrado para el usuario, usualmente el mismo del empleado.
     */
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    /**
     * Identificación del empleado al que corresponde el usuario
     */
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    /**
     * Indica si el usuario está desactivado
     */
    @Column(name = "disabled", nullable = false)
    private boolean disabled;
    /**
     * Indica si el usuario está bloqueado
     */
    @Column(name = "locked", nullable = false)
    private boolean locked;
    /**
     * Litado de roles que prosee el usuario.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_has_role", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private List<RoleEntity> roles;
}
