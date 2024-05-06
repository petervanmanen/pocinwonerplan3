package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Koopwoningen;
import nl.ritense.demo.repository.KoopwoningenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Koopwoningen}.
 */
@RestController
@RequestMapping("/api/koopwoningens")
@Transactional
public class KoopwoningenResource {

    private final Logger log = LoggerFactory.getLogger(KoopwoningenResource.class);

    private static final String ENTITY_NAME = "koopwoningen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KoopwoningenRepository koopwoningenRepository;

    public KoopwoningenResource(KoopwoningenRepository koopwoningenRepository) {
        this.koopwoningenRepository = koopwoningenRepository;
    }

    /**
     * {@code POST  /koopwoningens} : Create a new koopwoningen.
     *
     * @param koopwoningen the koopwoningen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new koopwoningen, or with status {@code 400 (Bad Request)} if the koopwoningen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Koopwoningen> createKoopwoningen(@RequestBody Koopwoningen koopwoningen) throws URISyntaxException {
        log.debug("REST request to save Koopwoningen : {}", koopwoningen);
        if (koopwoningen.getId() != null) {
            throw new BadRequestAlertException("A new koopwoningen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        koopwoningen = koopwoningenRepository.save(koopwoningen);
        return ResponseEntity.created(new URI("/api/koopwoningens/" + koopwoningen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, koopwoningen.getId().toString()))
            .body(koopwoningen);
    }

    /**
     * {@code PUT  /koopwoningens/:id} : Updates an existing koopwoningen.
     *
     * @param id the id of the koopwoningen to save.
     * @param koopwoningen the koopwoningen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koopwoningen,
     * or with status {@code 400 (Bad Request)} if the koopwoningen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the koopwoningen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Koopwoningen> updateKoopwoningen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Koopwoningen koopwoningen
    ) throws URISyntaxException {
        log.debug("REST request to update Koopwoningen : {}, {}", id, koopwoningen);
        if (koopwoningen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koopwoningen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koopwoningenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        koopwoningen = koopwoningenRepository.save(koopwoningen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, koopwoningen.getId().toString()))
            .body(koopwoningen);
    }

    /**
     * {@code PATCH  /koopwoningens/:id} : Partial updates given fields of an existing koopwoningen, field will ignore if it is null
     *
     * @param id the id of the koopwoningen to save.
     * @param koopwoningen the koopwoningen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koopwoningen,
     * or with status {@code 400 (Bad Request)} if the koopwoningen is not valid,
     * or with status {@code 404 (Not Found)} if the koopwoningen is not found,
     * or with status {@code 500 (Internal Server Error)} if the koopwoningen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Koopwoningen> partialUpdateKoopwoningen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Koopwoningen koopwoningen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Koopwoningen partially : {}, {}", id, koopwoningen);
        if (koopwoningen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koopwoningen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koopwoningenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Koopwoningen> result = koopwoningenRepository
            .findById(koopwoningen.getId())
            .map(existingKoopwoningen -> {
                if (koopwoningen.getKoopprijs() != null) {
                    existingKoopwoningen.setKoopprijs(koopwoningen.getKoopprijs());
                }

                return existingKoopwoningen;
            })
            .map(koopwoningenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, koopwoningen.getId().toString())
        );
    }

    /**
     * {@code GET  /koopwoningens} : get all the koopwoningens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of koopwoningens in body.
     */
    @GetMapping("")
    public List<Koopwoningen> getAllKoopwoningens() {
        log.debug("REST request to get all Koopwoningens");
        return koopwoningenRepository.findAll();
    }

    /**
     * {@code GET  /koopwoningens/:id} : get the "id" koopwoningen.
     *
     * @param id the id of the koopwoningen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the koopwoningen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Koopwoningen> getKoopwoningen(@PathVariable("id") Long id) {
        log.debug("REST request to get Koopwoningen : {}", id);
        Optional<Koopwoningen> koopwoningen = koopwoningenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(koopwoningen);
    }

    /**
     * {@code DELETE  /koopwoningens/:id} : delete the "id" koopwoningen.
     *
     * @param id the id of the koopwoningen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKoopwoningen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Koopwoningen : {}", id);
        koopwoningenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
