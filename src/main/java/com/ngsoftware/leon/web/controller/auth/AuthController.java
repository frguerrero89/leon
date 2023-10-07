package com.ngsoftware.leon.web.controller.auth;

import com.ngsoftware.leon.exceptions.EmployeeException;
import com.ngsoftware.leon.services.AuthService;
import com.ngsoftware.leon.services.dto.LoginDto;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    /**
     * Realiza el inicio de sesión de un usuario y retorna el token JWT valido para
     * autorización posterior en el encabezado "Authorization"
     * 
     * @param loginDto Datos de inicio de sesión
     * @return Token JWT valido
     * @throws UserPrincipalNotFoundException cuando no se puede encontrar el
     *                                        usuario
     * @throws EmployeeException              Cunado no se puede encontrar el
     *                                        empleado al que corresponde el
     *                                        usuario.
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto)
            throws UserPrincipalNotFoundException, EmployeeException {
        var login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        this.authenticationManager.authenticate(login);
        var jwt = this.authService.processSuccessAuthentication(loginDto.getUsername());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }

}
