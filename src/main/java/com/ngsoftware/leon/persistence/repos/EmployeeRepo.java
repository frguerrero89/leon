package com.ngsoftware.leon.persistence.repos;

import com.ngsoftware.leon.persistence.entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para obtener los datos de los empleados.
 * 
 * @author Francisco Guerrero Peláez
 */
public interface EmployeeRepo extends CrudRepository<EmployeeEntity, String> {

}
