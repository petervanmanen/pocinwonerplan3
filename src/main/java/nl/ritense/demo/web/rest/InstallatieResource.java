package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Installatie;
import nl.ritense.demo.repository.InstallatieRepository;
import nl.ritense.demo.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.ritense.demo.domain.Installatie}.
 */
@RestController
@RequestMapping("/api/installaties")
@Transactional
public class InstallatieResource {

    private final Logger log = LoggerFactory.getLogger(InstallatieResource.class);

    private static final String ENTITY_NAME = "installatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstallatieRepository installatieRepository;

    public InstallatieResource(InstallatieRepository installatieRepository) {
        this.installatieRepository = installatieRepository;
    }

    /**
     * {@code POST  /installaties} : Create a new installatie.
     *
     * @param installatie the installatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new installatie, or with status {@code 400 (Bad Request)} if the installatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Installatie> createInstallatie(@RequestBody Installatie installatie) throws URISyntaxException {
        log.debug("REST request to save Installatie : {}", installatie);
        if (installatie.getId() != null) {
            throw new BadRequestAlertException("A new installatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        installatie = installatieRepository.save(installatie);
        return ResponseEntity.created(new URI("/api/installaties/" + installatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, installatie.getId().toString()))
            .body(installatie);
    }

    /**
     * {@code PUT  /installaties/:id} : Updates an existing installatie.
     *
     * @param id the id of the installatie to save.
     * @param installatie the installatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated installatie,
     * or with status {@code 400 (Bad Request)} if the installatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the installatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Installatie> updateInstallatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Installatie installatie
    ) throws URISyntaxException {
        log.debug("REST request to update Installatie : {}, {}", id, installatie);
        if (installatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, installatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!installatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        installatie = installatieRepository.save(installatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, installatie.getId().toString()))
            .body(installatie);
    }

    /**
     * {@code PATCH  /installaties/:id} : Partial updates given fields of an existing installatie, field will ignore if it is null
     *
     * @param id the id of the installatie to save.
     * @param installatie the installatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated installatie,
     * or with status {@code 400 (Bad Request)} if the installatie is not valid,
     * or with status {@code 404 (Not Found)} if the installatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the installatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Installatie> partialUpdateInstallatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Installatie installatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Installatie partially : {}, {}", id, installatie);
        if (installatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, installatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!installatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Installatie> result = installatieRepository
            .findById(installatie.getId())
            .map(existingInstallatie -> {
                if (installatie.getBreedte() != null) {
                    existingInstallatie.setBreedte(installatie.getBreedte());
                }
                if (installatie.getEancode() != null) {
                    existingInstallatie.setEancode(installatie.getEancode());
                }
                if (installatie.getFabrikant() != null) {
                    existingInstallatie.setFabrikant(installatie.getFabrikant());
                }
                if (installatie.getHoogte() != null) {
                    existingInstallatie.setHoogte(installatie.getHoogte());
                }
                if (installatie.getInbelgegevens() != null) {
                    existingInstallatie.setInbelgegevens(installatie.getInbelgegevens());
                }
                if (installatie.getInstallateur() != null) {
                    existingInstallatie.setInstallateur(installatie.getInstallateur());
                }
                if (installatie.getJaaronderhouduitgevoerd() != null) {
                    existingInstallatie.setJaaronderhouduitgevoerd(installatie.getJaaronderhouduitgevoerd());
                }
                if (installatie.getLengte() != null) {
                    existingInstallatie.setLengte(installatie.getLengte());
                }
                if (installatie.getLeverancier() != null) {
                    existingInstallatie.setLeverancier(installatie.getLeverancier());
                }
                if (installatie.getTypecommunicatie() != null) {
                    existingInstallatie.setTypecommunicatie(installatie.getTypecommunicatie());
                }

                return existingInstallatie;
            })
            .map(installatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, installatie.getId().toString())
        );
    }

    /**
     * {@code GET  /installaties} : get all the installaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of installaties in body.
     */
    @GetMapping("")
    public List<Installatie> getAllInstallaties() {
        log.debug("REST request to get all Installaties");
        return installatieRepository.findAll();
    }

    /**
     * {@code GET  /installaties/:id} : get the "id" installatie.
     *
     * @param id the id of the installatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the installatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Installatie> getInstallatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Installatie : {}", id);
        Optional<Installatie> installatie = installatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(installatie);
    }

    /**
     * {@code DELETE  /installaties/:id} : delete the "id" installatie.
     *
     * @param id the id of the installatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstallatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Installatie : {}", id);
        installatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
