package com.ngsoftware.leon.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ngsoftware.leon.persistence.entities.RoleEntity;

/**
 * Servicio para el control de la lógica relacionada a los roles
 * 
 * @author Francisco Guerrero Peláez
 */
@Service
public class RoleService {
    /**
     * Convierte los roles provistos a un arreglo de strings, para esto se filtra
     * por los roles activos y luego se retorna el arreglo solo de los nombres de
     * los roles.
     * 
     * @param roles Roles a obtener como arreglo de string.
     * @return Arreglo de nombres de roles
     */
    public String[] getActiveRoles(List<RoleEntity> roles) {
        List<RoleEntity> activeRoles = null;
        if (roles.size() > 0) {
            activeRoles = new ArrayList<>();
            for (RoleEntity role : roles) {
                if (role.isEnabled()) {
                    activeRoles.add(role);
                }
            }
        }
        return activeRoles != null ? activeRoles.stream().map(RoleEntity::getName)
                .toArray(String[]::new) : null;
    }
}
