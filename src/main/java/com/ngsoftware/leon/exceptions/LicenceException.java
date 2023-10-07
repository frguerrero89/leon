package com.ngsoftware.leon.exceptions;

import com.ngsoftware.leon.utils.enums.Messages;
/**
 * Ésta excepción es usada para informar cuando ha ocurrido errores
 * correspondientes a licenciamiento.
 * 
 * @author Francisco Guerrero Peláez
 */
public class LicenceException extends Exception {

    public LicenceException(Messages message) {
        super(message.getMessage());
    }
}
