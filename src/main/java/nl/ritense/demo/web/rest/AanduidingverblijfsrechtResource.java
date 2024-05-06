package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanduidingverblijfsrecht;
import nl.ritense.demo.repository.AanduidingverblijfsrechtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanduidingverblijfsrecht}.
 */
@RestController
@RequestMapping("/api/aanduidingverblijfsrechts")
@Transactional
public class AanduidingverblijfsrechtResource {

    private final Logger log = LoggerFactory.getLogger(AanduidingverblijfsrechtResource.class);

    private static final String ENTITY_NAME = "aanduidingverblijfsrecht";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanduidingverblijfsrechtRepository aanduidingverblijfsrechtRepository;

    public AanduidingverblijfsrechtResource(AanduidingverblijfsrechtRepository aanduidingverblijfsrechtRepository) {
        this.aanduidingverblijfsrechtRepository = aanduidingverblijfsrechtRepository;
    }

    /**
     * {@code POST  /aanduidingverblijfsrechts} : Create a new aanduidingverblijfsrecht.
     *
     * @param aanduidingverblijfsrecht the aanduidingverblijfsrecht to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanduidingverblijfsrecht, or with status {@code 400 (Bad Request)} if the aanduidingverblijfsrecht has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanduidingverblijfsrecht> createAanduidingverblijfsrecht(
        @RequestBody Aanduidingverblijfsrecht aanduidingverblijfsrecht
    ) throws URISyntaxException {
        log.debug("REST request to save Aanduidingverblijfsrecht : {}", aanduidingverblijfsrecht);
        if (aanduidingverblijfsrecht.getId() != null) {
            throw new BadRequestAlertException("A new aanduidingverblijfsrecht cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanduidingverblijfsrecht = aanduidingverblijfsrechtRepository.save(aanduidingverblijfsrecht);
        return ResponseEntity.created(new URI("/api/aanduidingverblijfsrechts/" + aanduidingverblijfsrecht.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanduidingverblijfsrecht.getId().toString()))
            .body(aanduidingverblijfsrecht);
    }

    /**
     * {@code PUT  /aanduidingverblijfsrechts/:id} : Updates an existing aanduidingverblijfsrecht.
     *
     * @param id the id of the aanduidingverblijfsrecht to save.
     * @param aanduidingverblijfsrecht the aanduidingverblijfsrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanduidingverblijfsrecht,
     * or with status {@code 400 (Bad Request)} if the aanduidingverblijfsrecht is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanduidingverblijfsrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanduidingverblijfsrecht> updateAanduidingverblijfsrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanduidingverblijfsrecht aanduidingverblijfsrecht
    ) throws URISyntaxException {
        log.debug("REST request to update Aanduidingverblijfsrecht : {}, {}", id, aanduidingverblijfsrecht);
        if (aanduidingverblijfsrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanduidingverblijfsrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanduidingverblijfsrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanduidingverblijfsrecht = aanduidingverblijfsrechtRepository.save(aanduidingverblijfsrecht);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanduidingverblijfsrecht.getId().toString()))
            .body(aanduidingverblijfsrecht);
    }

    /**
     * {@code PATCH  /aanduidingverblijfsrechts/:id} : Partial updates given fields of an existing aanduidingverblijfsrecht, field will ignore if it is null
     *
     * @param id the id of the aanduidingverblijfsrecht to save.
     * @param aanduidingverblijfsrecht the aanduidingverblijfsrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanduidingverblijfsrecht,
     * or with status {@code 400 (Bad Request)} if the aanduidingverblijfsrecht is not valid,
     * or with status {@code 404 (Not Found)} if the aanduidingverblijfsrecht is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanduidingverblijfsrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanduidingverblijfsrecht> partialUpdateAanduidingverblijfsrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanduidingverblijfsrecht aanduidingverblijfsrecht
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanduidingverblijfsrecht partially : {}, {}", id, aanduidingverblijfsrecht);
        if (aanduidingverblijfsrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanduidingverblijfsrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanduidingverblijfsrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanduidingverblijfsrecht> result = aanduidingverblijfsrechtRepository
            .findById(aanduidingverblijfsrecht.getId())
            .map(existingAanduidingverblijfsrecht -> {
                if (aanduidingverblijfsrecht.getDatumaanvanggeldigheidverblijfsrecht() != null) {
                    existingAanduidingverblijfsrecht.setDatumaanvanggeldigheidverblijfsrecht(
                        aanduidingverblijfsrecht.getDatumaanvanggeldigheidverblijfsrecht()
                    );
                }
                if (aanduidingverblijfsrecht.getDatumeindegeldigheidverblijfsrecht() != null) {
                    existingAanduidingverblijfsrecht.setDatumeindegeldigheidverblijfsrecht(
                        aanduidingverblijfsrecht.getDatumeindegeldigheidverblijfsrecht()
                    );
                }
                if (aanduidingverblijfsrecht.getVerblijfsrechtnummer() != null) {
                    existingAanduidingverblijfsrecht.setVerblijfsrechtnummer(aanduidingverblijfsrecht.getVerblijfsrechtnummer());
                }
                if (aanduidingverblijfsrecht.getVerblijfsrechtomschrijving() != null) {
                    existingAanduidingverblijfsrecht.setVerblijfsrechtomschrijving(
                        aanduidingverblijfsrecht.getVerblijfsrechtomschrijving()
                    );
                }

                return existingAanduidingverblijfsrecht;
            })
            .map(aanduidingverblijfsrechtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanduidingverblijfsrecht.getId().toString())
        );
    }

    /**
     * {@code GET  /aanduidingverblijfsrechts} : get all the aanduidingverblijfsrechts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanduidingverblijfsrechts in body.
     */
    @GetMapping("")
    public List<Aanduidingverblijfsrecht> getAllAanduidingverblijfsrechts() {
        log.debug("REST request to get all Aanduidingverblijfsrechts");
        return aanduidingverblijfsrechtRepository.findAll();
    }

    /**
     * {@code GET  /aanduidingverblijfsrechts/:id} : get the "id" aanduidingverblijfsrecht.
     *
     * @param id the id of the aanduidingverblijfsrecht to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanduidingverblijfsrecht, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanduidingverblijfsrecht> getAanduidingverblijfsrecht(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanduidingverblijfsrecht : {}", id);
        Optional<Aanduidingverblijfsrecht> aanduidingverblijfsrecht = aanduidingverblijfsrechtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanduidingverblijfsrecht);
    }

    /**
     * {@code DELETE  /aanduidingverblijfsrechts/:id} : delete the "id" aanduidingverblijfsrecht.
     *
     * @param id the id of the aanduidingverblijfsrecht to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanduidingverblijfsrecht(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanduidingverblijfsrecht : {}", id);
        aanduidingverblijfsrechtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
