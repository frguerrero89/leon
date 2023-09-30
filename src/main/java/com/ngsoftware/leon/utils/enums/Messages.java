package com.ngsoftware.leon.utils.enums;

import lombok.Getter;

@Getter
public enum Messages {
    NO_AUTHORIZATION_HEADER("No posee el encabezado de autorización, o no es correcto"),
    JWT_TOKEN_NOT_VALID("El token proporcionado no es valido"),
    USER_DISABLED("El usuario no se encuentra activo"), 
    USER_BLOCKED("El usuario esta bloqueado"),
    USER_DOES_NOT_PASS_VALIDATIONS("El usuario no ha pasado las diferentes validaciones de seguridad y negocio");

    String message;

    private Messages(String message) {
        this.message = message;
    }
}
