package com.ngsoftware.leon.exceptions;

import com.ngsoftware.leon.utils.enums.Messages;

/**
 * Ésta excepción es usada para informar cuando ha ocurrido errores
 * correspondientes a los empleados.
 * 
 * @author Francisco Guerrero Peláez
 */
public class EmployeeException extends Exception {
    public EmployeeException(Messages message) {
        super(message.getMessage());
    }
}
