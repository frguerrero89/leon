package com.ngsoftware.leon.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ngsoftware.leon.exceptions.LicenceException;
import com.ngsoftware.leon.persistence.entities.LicenceEnity;
import com.ngsoftware.leon.persistence.repos.LicenceRepo;
import com.ngsoftware.leon.utils.enums.Messages;

/**
 * Servicio para el control de la lógica relacionada a las licencias
 * 
 * @author Francisco Guerrero Peláez
 */
@Service
public class LicenceService {
    /**
     * Objeto de acceso al repositorio de licencias
     */
    private final LicenceRepo repo;

    public LicenceService(LicenceRepo repo) {
        this.repo = repo;
    }

    /**
     * Confirma si la empresa porvista a través de su identificación posee una
     * licencia valida.
     * 
     * @param companyId Identificación de la empresa a confirmar.
     * @return true: La licencia que posee es valida. false: La licencia que posee
     *         no es valida.
     * @throws LicenceException
     */
    public boolean licenceIsValid(String companyId) throws LicenceException {
        var licences = this.repo.findLicenceByCompanyId(companyId)
                .orElseThrow(() -> new LicenceException(Messages.NO_LICENCES));
        return this.hasValidLicence(licences);
    }

    /**
     * Verifica que se tenga al menos una licencia valida. Para este proposito se
     * obtiene todas las licencias, luego se verifica una a una que este activa y si
     * lo esta entonce se verifica que los días restantes superen la fecha actual
     * del sistema.
     * 
     * @param licences Grupo de licencias que registra el cliente.
     * @return true: Cuando existe una licencia activa y con días de uso restantes.
     *         false: Caso contrario.
     */
    private boolean hasValidLicence(List<LicenceEnity> licences) {
        boolean valid = false;
        for (LicenceEnity licence : licences) {
            if (licence.isActive()) {
                var licenceCreatedAt = licence.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                var expiresAt = licenceCreatedAt.plusDays(licence.getValidFor());
                valid = (LocalDate.now()).isBefore(expiresAt);
                if (valid) {
                    break;
                }
            }
        }
        return valid;
    }

}
