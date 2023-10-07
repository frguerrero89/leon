package com.ngsoftware.leon.services;

import org.springframework.stereotype.Service;

import com.ngsoftware.leon.exceptions.EmployeeException;
import com.ngsoftware.leon.persistence.entities.EmployeeEntity;
import com.ngsoftware.leon.persistence.repos.EmployeeRepo;
import com.ngsoftware.leon.utils.enums.Messages;

/**
 * Servicio para el control de la lógica relacionada a los empleados
 * 
 * @author Francisco Guerrero Peláez
 */
@Service
public class EmployeeService {

    private final EmployeeRepo repo;

    public EmployeeService(EmployeeRepo repo) {
        this.repo = repo;
    }

    /**
     * Permite obtener un empleado a partir de su identificación
     * 
     * @param employeeId Identificacion del empleado a encontrar
     * @return Empleado registrado en la base de datos.
     * @throws EmployeeException Lanzado cuando el empleado no puede ser encontrado.
     */
    public EmployeeEntity getEmployeeById(String employeeId) throws EmployeeException {
        return this.repo.findById(employeeId)
                .orElseThrow(() -> new EmployeeException(Messages.NO_EMPLOYEE_FOUND));

    }

}
