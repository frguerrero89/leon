package com.ngsoftware.leon.persistence.repos;

import org.springframework.data.repository.CrudRepository;
import com.ngsoftware.leon.persistence.entities.LicenceEnity;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para obtener los datos de las licenicas.
 * 
 * @author Francisco Guerrero Peláez
 */
public interface LicenceRepo extends CrudRepository<LicenceEnity, String> {

    /**
     * Busca una licencia a partir del identificador de la empresa propietaria
     */
    public Optional<List<LicenceEnity>> findLicenceByCompanyId(String companyId);

}
