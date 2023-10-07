package com.ngsoftware.leon.web.config.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ngsoftware.leon.exceptions.LicenceException;
import com.ngsoftware.leon.services.LicenceService;
import com.ngsoftware.leon.utils.JWTUtil;
import com.ngsoftware.leon.utils.enums.Messages;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * Filtra las peticiones y las autoriza si se superna las validaciones de
 * negocio,
 * 
 * @author Francisco Guerrero Peláez
 */
@Component
@Log4j2
public class AuthorizationFilter extends OncePerRequestFilter {

    @Value("${app.security.token.type}")
    private String tokenType;

    /**
     * Utilidad para creacióny verificación de JWT
     */
    private final JWTUtil jwtUtil;
    /**
     * Servicio para obtención y manejo de los usuarios de seguridad.
     */
    private final UserDetailsService userDetailsService;
    /**
     * Servicio para obtención y manejo de licencias.
     */
    private final LicenceService licenceService;

    public AuthorizationFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService,
            LicenceService licenceService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.licenceService = licenceService;
    }

    /**
     * Realiza el filtrado del de autorización de acuerdo con los siguientes pasos:
     * <br/>
     * 1. Se ingora toda petición que venga desde login. <br/>
     * 2. Se valida que el encabezado de autorizacion sea valido.<br/>
     * 3. Se verifica que el token JWT sea valido (Ver
     * Utils.JWTUtils.isValid(String))<br/>
     * 4. Se obtiene el username del JWT para cargar el usuario desde la base de
     * datos.<br/>
     * 5. Se verifica que el usuario supere las validaciones de negocio <br/>
     * 
     * Final mente se carga el usuario en el contexto de seguridad
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Procesado solicitud para :{} ", request.getRequestURI());
        if (request.getRequestURI().contains("/api/auth/login")) {
            log.info("Autenticación y autorización no requerida");
            filterChain.doFilter(request, response);
            return;
        }
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith(tokenType)) {
            log.error(Messages.NO_AUTHORIZATION_HEADER.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        var token = authHeader.split(" ")[1].trim();
        if (!this.jwtUtil.isValid(token)) {
            log.error(Messages.JWT_TOKEN_NOT_VALID.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        var username = jwtUtil.getUsername(token);
        var user = (User) userDetailsService.loadUserByUsername(username);
        if (!this.isUserValidated(user, token)) {
            log.error(Messages.USER_DOES_NOT_PASS_VALIDATIONS.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        var authenticatedUser = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        filterChain.doFilter(request, response);
    }

    /**
     * Realiza las validaciones de negocio correspondientes así: <br/>
     * 1. Se valida que el usuario no este desactivado.<br/>
     * 2. Se verifica que el usuario no este bloqueado. <br/>
     * 3. Se obtiene el identificador de la compañia del usuario. <br/>
     * 4. Se valida que la licencia sea valida. <br/>
     * 
     * @param user
     * @return
     */
    private boolean isUserValidated(User user, String validJWTToken) {
        if (!user.isEnabled()) {
            log.error(Messages.USER_DISABLED.getMessage());
            return false;
        }
        if (!user.isAccountNonLocked()) {
            log.error(Messages.USER_BLOCKED.getMessage());
            return false;
        }
        try {
            var companyId = this.jwtUtil.getClaim(validJWTToken, "companyId");
            if (!this.licenceService.licenceIsValid(companyId)) {
                log.error(Messages.NO_VALID_LICENCE.getMessage());
                return false;
            }
        } catch (UsernameNotFoundException | LicenceException e) {
            if (e instanceof LicenceException) {
                log.error(Messages.NO_VALID_LICENCE.getMessage());
                return false;
            }
            e.printStackTrace();
        }
        return true;
    }

}
