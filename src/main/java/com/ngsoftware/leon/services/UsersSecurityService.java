package com.ngsoftware.leon.services;

import com.ngsoftware.leon.persistence.entities.UserEntity;
import com.ngsoftware.leon.persistence.repos.UserRepo;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio para el control de la lógica relacionada a los usuarios del sistema
 * de seguridad de spring, implementa {@link UserDetailService} para ser
 * incluido dentro del contexto de seguridad como proveedor de servicios de
 * usuarios de spring.
 * 
 * @author Francisco Guerrero Peláez
 */
@Service
@Log4j2
public class UsersSecurityService implements UserDetailsService {
    /**
     * Objeto de acceso a los usuarios en la base de datos
     */
    private final UserRepo userRepo;
    /**
     * Objeto de acceso a la lógica de los roles.
     */
    private final RoleService roleService;

    public UsersSecurityService(UserRepo userRepo, RoleService roleService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
    }

    /**
     * Obtiene un usuario de la base de datos a partir de su nombre y lo retorna
     * como entidad de seguridad de spring convertido a la clase
     * {@link UserDetails}.
     * 
     * El método carga el usuario desde la base de datos, luego a través del
     * servicio de roles obtiene un arreglo con todos los roles para convertirlos a
     * authorities y crear el objeto que se va a retornar.
     * 
     * @param username Nombre de usuario a buscar
     * @return Usuario formateado y listo para ser usado en la seguridad de
     *         springframework
     * @throws UsernameNotFoundException cuando no se encuetra el usuario registrado
     *                                   en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        log.info("Encontrado usuario: " + userEntity.toString());
        var roles = this.roleService.getActiveRoles(userEntity.getRoles());
        return User.builder()
                .username(userEntity.getUser())
                .password(userEntity.getPassword())
                .authorities(this.getAuthorities(roles))
                .accountLocked(userEntity.isLocked())
                .disabled(userEntity.isDisabled())
                .build();
    }

    /**
     * Permite obtener un usuario plenamente como la entidad para ser usado durante
     * los procesos de seguridad.
     * 
     * @param username Nombre de usuario a buscar
     * @return Usuario encontrado
     * @throws UsernameNotFoundException cuando no se encuetra el usuario registrado
     *                                   en la base de datos.
     */
    public UserEntity getByUsername(String username) throws UsernameNotFoundException {
        return this.userRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    /**
     * Convierte una lista de roles a autoridades grantizadas de spring que se usan
     * dentro de los filtros de seguridad para validar peticiones.
     * 
     * @param roles roles a convertir en {@link GrantedAuthority}
     * @return Listado obtenido de autoridades.
     */
    private List<GrantedAuthority> getAuthorities(String[] roles) {
        List<GrantedAuthority> authorities = null;
        if (roles.length > 0) {
            authorities = new ArrayList<>(roles.length);
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
        return authorities;
    }
}
