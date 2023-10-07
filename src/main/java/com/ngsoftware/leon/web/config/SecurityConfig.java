package com.ngsoftware.leon.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ngsoftware.leon.web.config.filters.AuthorizationFilter;

/**
 * Configuración de seguridad de la aplicación.
 * 
 * @author Francisco Guerrero Peláez
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthorizationFilter authFilter;

    public SecurityConfig(AuthorizationFilter authFilter) {
        this.authFilter = authFilter;
    }

    /**
     * Configura la seguridad de la aplicación, aqui se desactiva el CRSF, CORS y Se
     * aplica Stateless a las peticiones.
     * Se agrega el filtro de authorización para las peticiones.
     * 
     * @param http Objeto de seguridad http
     * @return Candena de filtros de seguridad
     * @throws Exception En caso de error.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().authenticated())
                .csrf(customizer -> customizer.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Retorna un webSecurityCustomizer que desactiva la seguridad a las paginas
     * especificadas.
     * 
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/auth/**", "/error");
    }

    /**
     * Retorna el authentication manager para ser utilizado en los endpoints de
     * login/logout
     * 
     * @param configuration Configuración de autenticacion.
     * @return Manejador de autenticacion.
     * @throws Exception En caso de error.
     */
    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Retorna el objeto de password encoder para verificar contraseñas
     * 
     * @return Objeto de password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
