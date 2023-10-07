package com.ngsoftware.leon.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ngsoftware.leon.exceptions.EmployeeException;
import com.ngsoftware.leon.persistence.entities.EmployeeEntity;
import com.ngsoftware.leon.utils.JWTUtil;

/**
 * Servicio para el control de la lógica relacionada la authenticación de
 * usuarios exclusivamente.
 * 
 * @author Francisco Guerrero Peláez
 */
@Service
public class AuthService {

    private final String COMPANY_ID_CLAIM_KEY = "companyId";

    private final JWTUtil jwtUtil;
    private final UsersSecurityService usersSecurityService;
    private final EmployeeService employeeService;

    public AuthService(JWTUtil jwtUtil, UsersSecurityService usersSecurityService, EmployeeService employeeService) {
        this.jwtUtil = jwtUtil;
        this.usersSecurityService = usersSecurityService;
        this.employeeService = employeeService;
    }

    /**
     * Procesa y crea el token JWT valido tras la autenticación exitosa de un
     * usuario.
     * 
     * @param username usuario al que se genera el token jwt
     * @return token valido.
     */
    public String processSuccessAuthentication(String username) throws UsernameNotFoundException, EmployeeException {
        var user = this.usersSecurityService.getByUsername(username);
        var employee = this.employeeService.getEmployeeById(user.getEmployeeId());
        return this.jwtUtil.create(username, this.generateClaims(employee));
    }

    /**
     * Genera un mapa de claims que se agregarán al token JWT.
     * 
     * @param employeeEntity Datos del empleado.
     * @return Mapa de claims valido.
     */
    private Map<String, String> generateClaims(EmployeeEntity employeeEntity) {
        var claims = new HashMap<String, String>();
        claims.put(COMPANY_ID_CLAIM_KEY, employeeEntity.getCompanyId());
        return claims;
    }
}
