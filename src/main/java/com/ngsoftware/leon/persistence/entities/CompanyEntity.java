package com.ngsoftware.leon.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Representación de la tabla de base de datos "company" donde se almacena toda
 * la información de la empresa que esta usando el sistema.
 * 
 * @author Francisco Guerrero Peláez
 */
@Table
@Entity(name = "company")
@Data
public class CompanyEntity {

    /**
     * Identificador de la empresa, normalmente NIT o Cédula en caso de personas naturales
     */
    @Id
    @Column(name = "company_id")
    private String companyId;
    /**
     * Nombre o razón social de la empresa
     */
    @Column(name = "name")
    private String name;
    /**
     * Telefono de de la empresa
     */
    @Column(name = "phone_number")
    private String phoneNumber;
    /**
     * Dirección donde se ubica la sede principal de la empresa
     */
    @Column(name = "address")
    private String address;
    /**
     * Correo electrónico para las comunicaciones de la empresa
     */
    @Column(name = "email")
    private String email;

}
