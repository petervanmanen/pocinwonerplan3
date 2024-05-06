package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gebouwinstallatie;
import nl.ritense.demo.repository.GebouwinstallatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gebouwinstallatie}.
 */
@RestController
@RequestMapping("/api/gebouwinstallaties")
@Transactional
public class GebouwinstallatieResource {

    private final Logger log = LoggerFactory.getLogger(GebouwinstallatieResource.class);

    private static final String ENTITY_NAME = "gebouwinstallatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GebouwinstallatieRepository gebouwinstallatieRepository;

    public GebouwinstallatieResource(GebouwinstallatieRepository gebouwinstallatieRepository) {
        this.gebouwinstallatieRepository = gebouwinstallatieRepository;
    }

    /**
     * {@code POST  /gebouwinstallaties} : Create a new gebouwinstallatie.
     *
     * @param gebouwinstallatie the gebouwinstallatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gebouwinstallatie, or with status {@code 400 (Bad Request)} if the gebouwinstallatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gebouwinstallatie> createGebouwinstallatie(@RequestBody Gebouwinstallatie gebouwinstallatie)
        throws URISyntaxException {
        log.debug("REST request to save Gebouwinstallatie : {}", gebouwinstallatie);
        if (gebouwinstallatie.getId() != null) {
            throw new BadRequestAlertException("A new gebouwinstallatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gebouwinstallatie = gebouwinstallatieRepository.save(gebouwinstallatie);
        return ResponseEntity.created(new URI("/api/gebouwinstallaties/" + gebouwinstallatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gebouwinstallatie.getId().toString()))
            .body(gebouwinstallatie);
    }

    /**
     * {@code PUT  /gebouwinstallaties/:id} : Updates an existing gebouwinstallatie.
     *
     * @param id the id of the gebouwinstallatie to save.
     * @param gebouwinstallatie the gebouwinstallatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebouwinstallatie,
     * or with status {@code 400 (Bad Request)} if the gebouwinstallatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gebouwinstallatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gebouwinstallatie> updateGebouwinstallatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebouwinstallatie gebouwinstallatie
    ) throws URISyntaxException {
        log.debug("REST request to update Gebouwinstallatie : {}, {}", id, gebouwinstallatie);
        if (gebouwinstallatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebouwinstallatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebouwinstallatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gebouwinstallatie = gebouwinstallatieRepository.save(gebouwinstallatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebouwinstallatie.getId().toString()))
            .body(gebouwinstallatie);
    }

    /**
     * {@code PATCH  /gebouwinstallaties/:id} : Partial updates given fields of an existing gebouwinstallatie, field will ignore if it is null
     *
     * @param id the id of the gebouwinstallatie to save.
     * @param gebouwinstallatie the gebouwinstallatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebouwinstallatie,
     * or with status {@code 400 (Bad Request)} if the gebouwinstallatie is not valid,
     * or with status {@code 404 (Not Found)} if the gebouwinstallatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the gebouwinstallatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gebouwinstallatie> partialUpdateGebouwinstallatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebouwinstallatie gebouwinstallatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gebouwinstallatie partially : {}, {}", id, gebouwinstallatie);
        if (gebouwinstallatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebouwinstallatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebouwinstallatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gebouwinstallatie> result = gebouwinstallatieRepository
            .findById(gebouwinstallatie.getId())
            .map(existingGebouwinstallatie -> {
                if (gebouwinstallatie.getDatumbegingeldigheidgebouwinstallatie() != null) {
                    existingGebouwinstallatie.setDatumbegingeldigheidgebouwinstallatie(
                        gebouwinstallatie.getDatumbegingeldigheidgebouwinstallatie()
                    );
                }
                if (gebouwinstallatie.getDatumeindegeldigheidgebouwinstallatie() != null) {
                    existingGebouwinstallatie.setDatumeindegeldigheidgebouwinstallatie(
                        gebouwinstallatie.getDatumeindegeldigheidgebouwinstallatie()
                    );
                }
                if (gebouwinstallatie.getGeometriegebouwinstallatie() != null) {
                    existingGebouwinstallatie.setGeometriegebouwinstallatie(gebouwinstallatie.getGeometriegebouwinstallatie());
                }
                if (gebouwinstallatie.getIdentificatiegebouwinstallatie() != null) {
                    existingGebouwinstallatie.setIdentificatiegebouwinstallatie(gebouwinstallatie.getIdentificatiegebouwinstallatie());
                }
                if (gebouwinstallatie.getLod0geometriegebouwinstallatie() != null) {
                    existingGebouwinstallatie.setLod0geometriegebouwinstallatie(gebouwinstallatie.getLod0geometriegebouwinstallatie());
                }
                if (gebouwinstallatie.getRelatievehoogteligginggebouwinstallatie() != null) {
                    existingGebouwinstallatie.setRelatievehoogteligginggebouwinstallatie(
                        gebouwinstallatie.getRelatievehoogteligginggebouwinstallatie()
                    );
                }
                if (gebouwinstallatie.getStatusgebouwinstallatie() != null) {
                    existingGebouwinstallatie.setStatusgebouwinstallatie(gebouwinstallatie.getStatusgebouwinstallatie());
                }
                if (gebouwinstallatie.getTypegebouwinstallatie() != null) {
                    existingGebouwinstallatie.setTypegebouwinstallatie(gebouwinstallatie.getTypegebouwinstallatie());
                }

                return existingGebouwinstallatie;
            })
            .map(gebouwinstallatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebouwinstallatie.getId().toString())
        );
    }

    /**
     * {@code GET  /gebouwinstallaties} : get all the gebouwinstallaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gebouwinstallaties in body.
     */
    @GetMapping("")
    public List<Gebouwinstallatie> getAllGebouwinstallaties() {
        log.debug("REST request to get all Gebouwinstallaties");
        return gebouwinstallatieRepository.findAll();
    }

    /**
     * {@code GET  /gebouwinstallaties/:id} : get the "id" gebouwinstallatie.
     *
     * @param id the id of the gebouwinstallatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gebouwinstallatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gebouwinstallatie> getGebouwinstallatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Gebouwinstallatie : {}", id);
        Optional<Gebouwinstallatie> gebouwinstallatie = gebouwinstallatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gebouwinstallatie);
    }

    /**
     * {@code DELETE  /gebouwinstallaties/:id} : delete the "id" gebouwinstallatie.
     *
     * @param id the id of the gebouwinstallatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebouwinstallatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gebouwinstallatie : {}", id);
        gebouwinstallatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
