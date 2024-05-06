package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Handelsnamenmaatschappelijkeactiviteit;
import nl.ritense.demo.repository.HandelsnamenmaatschappelijkeactiviteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Handelsnamenmaatschappelijkeactiviteit}.
 */
@RestController
@RequestMapping("/api/handelsnamenmaatschappelijkeactiviteits")
@Transactional
public class HandelsnamenmaatschappelijkeactiviteitResource {

    private final Logger log = LoggerFactory.getLogger(HandelsnamenmaatschappelijkeactiviteitResource.class);

    private static final String ENTITY_NAME = "handelsnamenmaatschappelijkeactiviteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HandelsnamenmaatschappelijkeactiviteitRepository handelsnamenmaatschappelijkeactiviteitRepository;

    public HandelsnamenmaatschappelijkeactiviteitResource(
        HandelsnamenmaatschappelijkeactiviteitRepository handelsnamenmaatschappelijkeactiviteitRepository
    ) {
        this.handelsnamenmaatschappelijkeactiviteitRepository = handelsnamenmaatschappelijkeactiviteitRepository;
    }

    /**
     * {@code POST  /handelsnamenmaatschappelijkeactiviteits} : Create a new handelsnamenmaatschappelijkeactiviteit.
     *
     * @param handelsnamenmaatschappelijkeactiviteit the handelsnamenmaatschappelijkeactiviteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new handelsnamenmaatschappelijkeactiviteit, or with status {@code 400 (Bad Request)} if the handelsnamenmaatschappelijkeactiviteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Handelsnamenmaatschappelijkeactiviteit> createHandelsnamenmaatschappelijkeactiviteit(
        @RequestBody Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to save Handelsnamenmaatschappelijkeactiviteit : {}", handelsnamenmaatschappelijkeactiviteit);
        if (handelsnamenmaatschappelijkeactiviteit.getId() != null) {
            throw new BadRequestAlertException(
                "A new handelsnamenmaatschappelijkeactiviteit cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        handelsnamenmaatschappelijkeactiviteit = handelsnamenmaatschappelijkeactiviteitRepository.save(
            handelsnamenmaatschappelijkeactiviteit
        );
        return ResponseEntity.created(
            new URI("/api/handelsnamenmaatschappelijkeactiviteits/" + handelsnamenmaatschappelijkeactiviteit.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    handelsnamenmaatschappelijkeactiviteit.getId().toString()
                )
            )
            .body(handelsnamenmaatschappelijkeactiviteit);
    }

    /**
     * {@code PUT  /handelsnamenmaatschappelijkeactiviteits/:id} : Updates an existing handelsnamenmaatschappelijkeactiviteit.
     *
     * @param id the id of the handelsnamenmaatschappelijkeactiviteit to save.
     * @param handelsnamenmaatschappelijkeactiviteit the handelsnamenmaatschappelijkeactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated handelsnamenmaatschappelijkeactiviteit,
     * or with status {@code 400 (Bad Request)} if the handelsnamenmaatschappelijkeactiviteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the handelsnamenmaatschappelijkeactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Handelsnamenmaatschappelijkeactiviteit> updateHandelsnamenmaatschappelijkeactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to update Handelsnamenmaatschappelijkeactiviteit : {}, {}", id, handelsnamenmaatschappelijkeactiviteit);
        if (handelsnamenmaatschappelijkeactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, handelsnamenmaatschappelijkeactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!handelsnamenmaatschappelijkeactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        handelsnamenmaatschappelijkeactiviteit = handelsnamenmaatschappelijkeactiviteitRepository.save(
            handelsnamenmaatschappelijkeactiviteit
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    handelsnamenmaatschappelijkeactiviteit.getId().toString()
                )
            )
            .body(handelsnamenmaatschappelijkeactiviteit);
    }

    /**
     * {@code PATCH  /handelsnamenmaatschappelijkeactiviteits/:id} : Partial updates given fields of an existing handelsnamenmaatschappelijkeactiviteit, field will ignore if it is null
     *
     * @param id the id of the handelsnamenmaatschappelijkeactiviteit to save.
     * @param handelsnamenmaatschappelijkeactiviteit the handelsnamenmaatschappelijkeactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated handelsnamenmaatschappelijkeactiviteit,
     * or with status {@code 400 (Bad Request)} if the handelsnamenmaatschappelijkeactiviteit is not valid,
     * or with status {@code 404 (Not Found)} if the handelsnamenmaatschappelijkeactiviteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the handelsnamenmaatschappelijkeactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Handelsnamenmaatschappelijkeactiviteit> partialUpdateHandelsnamenmaatschappelijkeactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Handelsnamenmaatschappelijkeactiviteit partially : {}, {}",
            id,
            handelsnamenmaatschappelijkeactiviteit
        );
        if (handelsnamenmaatschappelijkeactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, handelsnamenmaatschappelijkeactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!handelsnamenmaatschappelijkeactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Handelsnamenmaatschappelijkeactiviteit> result = handelsnamenmaatschappelijkeactiviteitRepository
            .findById(handelsnamenmaatschappelijkeactiviteit.getId())
            .map(existingHandelsnamenmaatschappelijkeactiviteit -> {
                if (handelsnamenmaatschappelijkeactiviteit.getHandelsnaam() != null) {
                    existingHandelsnamenmaatschappelijkeactiviteit.setHandelsnaam(handelsnamenmaatschappelijkeactiviteit.getHandelsnaam());
                }
                if (handelsnamenmaatschappelijkeactiviteit.getVerkortenaam() != null) {
                    existingHandelsnamenmaatschappelijkeactiviteit.setVerkortenaam(
                        handelsnamenmaatschappelijkeactiviteit.getVerkortenaam()
                    );
                }
                if (handelsnamenmaatschappelijkeactiviteit.getVolgorde() != null) {
                    existingHandelsnamenmaatschappelijkeactiviteit.setVolgorde(handelsnamenmaatschappelijkeactiviteit.getVolgorde());
                }

                return existingHandelsnamenmaatschappelijkeactiviteit;
            })
            .map(handelsnamenmaatschappelijkeactiviteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                handelsnamenmaatschappelijkeactiviteit.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /handelsnamenmaatschappelijkeactiviteits} : get all the handelsnamenmaatschappelijkeactiviteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of handelsnamenmaatschappelijkeactiviteits in body.
     */
    @GetMapping("")
    public List<Handelsnamenmaatschappelijkeactiviteit> getAllHandelsnamenmaatschappelijkeactiviteits() {
        log.debug("REST request to get all Handelsnamenmaatschappelijkeactiviteits");
        return handelsnamenmaatschappelijkeactiviteitRepository.findAll();
    }

    /**
     * {@code GET  /handelsnamenmaatschappelijkeactiviteits/:id} : get the "id" handelsnamenmaatschappelijkeactiviteit.
     *
     * @param id the id of the handelsnamenmaatschappelijkeactiviteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the handelsnamenmaatschappelijkeactiviteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Handelsnamenmaatschappelijkeactiviteit> getHandelsnamenmaatschappelijkeactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Handelsnamenmaatschappelijkeactiviteit : {}", id);
        Optional<Handelsnamenmaatschappelijkeactiviteit> handelsnamenmaatschappelijkeactiviteit =
            handelsnamenmaatschappelijkeactiviteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(handelsnamenmaatschappelijkeactiviteit);
    }

    /**
     * {@code DELETE  /handelsnamenmaatschappelijkeactiviteits/:id} : delete the "id" handelsnamenmaatschappelijkeactiviteit.
     *
     * @param id the id of the handelsnamenmaatschappelijkeactiviteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHandelsnamenmaatschappelijkeactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Handelsnamenmaatschappelijkeactiviteit : {}", id);
        handelsnamenmaatschappelijkeactiviteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
