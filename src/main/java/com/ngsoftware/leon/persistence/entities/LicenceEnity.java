package com.ngsoftware.leon.persistence.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Representación de la tabla de base de datos "licence" donde se almacena toda
 * la información de la licencia que adquirió un cliente para usar el sistema.
 * 
 * @author Francisco Guerrero Peláez
 */
@Table
@Entity(name = "licence")
@Data
public class LicenceEnity {

    /**
     * Identificación de la empresa propietaria de la licencia
     */
    @Column(name = "company_id")
    private String companyId;
    /**
     * Número de serie de la licencia, actua como identificador único de la misma
     */
    @Id
    @Column(name = "licence_id")
    private String licenceId;
    /**
     * Fecha de cuando se creó la licencia
     */
    @Column(name = "created_at")
    private Date createdAt;
    /**
     * Cantidad de días de duración de la licencia, contados a partir de de la fecha
     * de creación
     */
    @Column(name = "valid_for")
    private int validFor;
    /**
     * Indica si la licencia está activa
     */
    @Column(name = "active")
    private boolean active;

}
