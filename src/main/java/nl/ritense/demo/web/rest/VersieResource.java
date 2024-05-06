package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Versie;
import nl.ritense.demo.repository.VersieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Versie}.
 */
@RestController
@RequestMapping("/api/versies")
@Transactional
public class VersieResource {

    private final Logger log = LoggerFactory.getLogger(VersieResource.class);

    private static final String ENTITY_NAME = "versie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VersieRepository versieRepository;

    public VersieResource(VersieRepository versieRepository) {
        this.versieRepository = versieRepository;
    }

    /**
     * {@code POST  /versies} : Create a new versie.
     *
     * @param versie the versie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new versie, or with status {@code 400 (Bad Request)} if the versie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Versie> createVersie(@Valid @RequestBody Versie versie) throws URISyntaxException {
        log.debug("REST request to save Versie : {}", versie);
        if (versie.getId() != null) {
            throw new BadRequestAlertException("A new versie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        versie = versieRepository.save(versie);
        return ResponseEntity.created(new URI("/api/versies/" + versie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, versie.getId().toString()))
            .body(versie);
    }

    /**
     * {@code PUT  /versies/:id} : Updates an existing versie.
     *
     * @param id the id of the versie to save.
     * @param versie the versie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated versie,
     * or with status {@code 400 (Bad Request)} if the versie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the versie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Versie> updateVersie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Versie versie
    ) throws URISyntaxException {
        log.debug("REST request to update Versie : {}, {}", id, versie);
        if (versie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, versie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!versieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        versie = versieRepository.save(versie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, versie.getId().toString()))
            .body(versie);
    }

    /**
     * {@code PATCH  /versies/:id} : Partial updates given fields of an existing versie, field will ignore if it is null
     *
     * @param id the id of the versie to save.
     * @param versie the versie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated versie,
     * or with status {@code 400 (Bad Request)} if the versie is not valid,
     * or with status {@code 404 (Not Found)} if the versie is not found,
     * or with status {@code 500 (Internal Server Error)} if the versie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Versie> partialUpdateVersie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Versie versie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Versie partially : {}, {}", id, versie);
        if (versie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, versie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!versieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Versie> result = versieRepository
            .findById(versie.getId())
            .map(existingVersie -> {
                if (versie.getAantal() != null) {
                    existingVersie.setAantal(versie.getAantal());
                }
                if (versie.getDatumeindesupport() != null) {
                    existingVersie.setDatumeindesupport(versie.getDatumeindesupport());
                }
                if (versie.getKosten() != null) {
                    existingVersie.setKosten(versie.getKosten());
                }
                if (versie.getLicentie() != null) {
                    existingVersie.setLicentie(versie.getLicentie());
                }
                if (versie.getStatus() != null) {
                    existingVersie.setStatus(versie.getStatus());
                }
                if (versie.getVersienummer() != null) {
                    existingVersie.setVersienummer(versie.getVersienummer());
                }

                return existingVersie;
            })
            .map(versieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, versie.getId().toString())
        );
    }

    /**
     * {@code GET  /versies} : get all the versies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of versies in body.
     */
    @GetMapping("")
    public List<Versie> getAllVersies() {
        log.debug("REST request to get all Versies");
        return versieRepository.findAll();
    }

    /**
     * {@code GET  /versies/:id} : get the "id" versie.
     *
     * @param id the id of the versie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the versie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Versie> getVersie(@PathVariable("id") Long id) {
        log.debug("REST request to get Versie : {}", id);
        Optional<Versie> versie = versieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(versie);
    }

    /**
     * {@code DELETE  /versies/:id} : delete the "id" versie.
     *
     * @param id the id of the versie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVersie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Versie : {}", id);
        versieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
