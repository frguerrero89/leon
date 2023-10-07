package com.ngsoftware.leon.persistence.repos;

import com.ngsoftware.leon.persistence.entities.CompanyEntity;
import org.springframework.data.repository.CrudRepository;
/**
 * Repositorio para obtener los datos de las compañias.
 * 
 * @author Francisco Guerrero Peláez
 */
public interface CompanyRepo extends CrudRepository<CompanyEntity, String> {
    
}
