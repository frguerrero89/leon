package com.ngsoftware.leon.web.config.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ngsoftware.leon.utils.JWTUtil;
import com.ngsoftware.leon.utils.enums.Messages;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AuthorizationFilter extends OncePerRequestFilter {

    @Value("${app.security.token.type}")
    private String tokenType;

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthorizationFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Realiza el filtrado del de autorización de acuerdo con los siguientes pasos:
     * <br/>
     * 1. Se valida que el encabezado de autorizacion sea valido.<br/>
     * 2. Se verifica que el token JWT sea valido (Ver
     * Utils.JWTUtils.isValid(String))<br/>
     * 3. Se obtiene el username del JWT para cargar el usuario desde la base de
     * datos.
     * 4. Se verifica que el usuario no este desactivado <br/>
     * 5. Se verifica que el usuario no este bloqueado <br/>
     * 
     * 
     * Cargar usuario en el contexto de seguridad
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith(tokenType)) {
            log.error(Messages.NO_AUTHORIZATION_HEADER.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        var token = authHeader.split(" ")[0].trim();
        if (!this.jwtUtil.isValid(token)) {
            log.error(Messages.JWT_TOKEN_NOT_VALID.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        var username = jwtUtil.getUsername(token);
        var user = (User) userDetailsService.loadUserByUsername(username);
        if (!this.isUserValidated(user)) {
            log.error(Messages.USER_DOES_NOT_PASS_VALIDATIONS.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        var authenticatedUser = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        filterChain.doFilter(request, response);
    }

    private boolean isUserValidated(User user) {
        boolean valid = false;
        if (!user.isEnabled()) {
            log.error(Messages.USER_DISABLED.getMessage());
            return valid;
        }
        if (!user.isAccountNonLocked()) {
            log.error(Messages.USER_BLOCKED.getMessage());
            return valid;
        }

        return valid;
    }

}
