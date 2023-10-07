package com.ngsoftware.leon.utils.enums;

import lombok.Getter;

/**
 * Contiene todos los mensaje que se muestran en el backend
 * 
 * @author Francisco Guerrero Peláez
 */
@Getter
public enum Messages {
    NO_AUTHORIZATION_HEADER("No posee el encabezado de autorización, o no es correcto"),
    JWT_TOKEN_NOT_VALID("El token proporcionado no es valido"),
    USER_DISABLED("El usuario no se encuentra activo"),
    USER_BLOCKED("El usuario esta bloqueado"),
    USER_DOES_NOT_PASS_VALIDATIONS("El usuario no ha pasado las diferentes validaciones de seguridad y negocio"),
    NO_EMPLOYEE_FOUND("No se pudo encontrar el registro de empleado del usuario"),
    NO_LICENCES("No se encontraron licencias para la compañia"),
    NO_VALID_LICENCE("No posee una licencia valida");

    /**
     * Cadena del mensaje que se desea mostrar.
     */
    String message;

    private Messages(String message) {
        this.message = message;
    }
}
