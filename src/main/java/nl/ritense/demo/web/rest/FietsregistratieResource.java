package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Fietsregistratie;
import nl.ritense.demo.repository.FietsregistratieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Fietsregistratie}.
 */
@RestController
@RequestMapping("/api/fietsregistraties")
@Transactional
public class FietsregistratieResource {

    private final Logger log = LoggerFactory.getLogger(FietsregistratieResource.class);

    private static final String ENTITY_NAME = "fietsregistratie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FietsregistratieRepository fietsregistratieRepository;

    public FietsregistratieResource(FietsregistratieRepository fietsregistratieRepository) {
        this.fietsregistratieRepository = fietsregistratieRepository;
    }

    /**
     * {@code POST  /fietsregistraties} : Create a new fietsregistratie.
     *
     * @param fietsregistratie the fietsregistratie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fietsregistratie, or with status {@code 400 (Bad Request)} if the fietsregistratie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Fietsregistratie> createFietsregistratie(@RequestBody Fietsregistratie fietsregistratie)
        throws URISyntaxException {
        log.debug("REST request to save Fietsregistratie : {}", fietsregistratie);
        if (fietsregistratie.getId() != null) {
            throw new BadRequestAlertException("A new fietsregistratie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fietsregistratie = fietsregistratieRepository.save(fietsregistratie);
        return ResponseEntity.created(new URI("/api/fietsregistraties/" + fietsregistratie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, fietsregistratie.getId().toString()))
            .body(fietsregistratie);
    }

    /**
     * {@code PUT  /fietsregistraties/:id} : Updates an existing fietsregistratie.
     *
     * @param id the id of the fietsregistratie to save.
     * @param fietsregistratie the fietsregistratie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fietsregistratie,
     * or with status {@code 400 (Bad Request)} if the fietsregistratie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fietsregistratie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Fietsregistratie> updateFietsregistratie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Fietsregistratie fietsregistratie
    ) throws URISyntaxException {
        log.debug("REST request to update Fietsregistratie : {}, {}", id, fietsregistratie);
        if (fietsregistratie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fietsregistratie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fietsregistratieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fietsregistratie = fietsregistratieRepository.save(fietsregistratie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fietsregistratie.getId().toString()))
            .body(fietsregistratie);
    }

    /**
     * {@code PATCH  /fietsregistraties/:id} : Partial updates given fields of an existing fietsregistratie, field will ignore if it is null
     *
     * @param id the id of the fietsregistratie to save.
     * @param fietsregistratie the fietsregistratie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fietsregistratie,
     * or with status {@code 400 (Bad Request)} if the fietsregistratie is not valid,
     * or with status {@code 404 (Not Found)} if the fietsregistratie is not found,
     * or with status {@code 500 (Internal Server Error)} if the fietsregistratie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Fietsregistratie> partialUpdateFietsregistratie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Fietsregistratie fietsregistratie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fietsregistratie partially : {}, {}", id, fietsregistratie);
        if (fietsregistratie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fietsregistratie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fietsregistratieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Fietsregistratie> result = fietsregistratieRepository
            .findById(fietsregistratie.getId())
            .map(existingFietsregistratie -> {
                if (fietsregistratie.getGelabeld() != null) {
                    existingFietsregistratie.setGelabeld(fietsregistratie.getGelabeld());
                }
                if (fietsregistratie.getVerwijderd() != null) {
                    existingFietsregistratie.setVerwijderd(fietsregistratie.getVerwijderd());
                }

                return existingFietsregistratie;
            })
            .map(fietsregistratieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fietsregistratie.getId().toString())
        );
    }

    /**
     * {@code GET  /fietsregistraties} : get all the fietsregistraties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fietsregistraties in body.
     */
    @GetMapping("")
    public List<Fietsregistratie> getAllFietsregistraties() {
        log.debug("REST request to get all Fietsregistraties");
        return fietsregistratieRepository.findAll();
    }

    /**
     * {@code GET  /fietsregistraties/:id} : get the "id" fietsregistratie.
     *
     * @param id the id of the fietsregistratie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fietsregistratie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fietsregistratie> getFietsregistratie(@PathVariable("id") Long id) {
        log.debug("REST request to get Fietsregistratie : {}", id);
        Optional<Fietsregistratie> fietsregistratie = fietsregistratieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fietsregistratie);
    }

    /**
     * {@code DELETE  /fietsregistraties/:id} : delete the "id" fietsregistratie.
     *
     * @param id the id of the fietsregistratie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFietsregistratie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Fietsregistratie : {}", id);
        fietsregistratieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
