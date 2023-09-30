package com.ngsoftware.leon.persistence.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table
@Entity(name = "rol")
@Data
public class RolEntity {
    @Id
    @Column(name = "id_rol")
    private long idRol;
    @Column(name = "name", length = 45)
    private String name;
    @Column(name = "description", length = 45)
    private String description;
    @Column(name = "enabled")
    private boolean enabled;

}
