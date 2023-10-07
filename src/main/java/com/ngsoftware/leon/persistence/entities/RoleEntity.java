package com.ngsoftware.leon.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Representación de la tabla de base de datos "role" donde se almacena toda
 * la información de los roles asignados a un usuario especificado.
 * 
 * @author Francisco Guerrero Peláez
 */
@Table
@Entity(name = "role")
@Data
public class RoleEntity {
    /**
     * Identificación del rol. Es un valor autogenerado
     */
    @Id
    @Column(name = "id_rol")
    private long idRol;
    /**
     * Nombre del rol, es el valor en código que se usa. Debe corresponder a los
     * registrados en los filtros de seguridad.
     */
    @Column(name = "name", length = 45)
    private String name;
    /**
     * Descripción opcional del rol.
     */
    @Column(name = "description", length = 45)
    private String description;
    /**
     * Indica si el rol está activo o no.
     */
    @Column(name = "enabled")
    private boolean enabled;

}
