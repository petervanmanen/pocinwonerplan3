package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Leerjaar;
import nl.ritense.demo.repository.LeerjaarRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leerjaar}.
 */
@RestController
@RequestMapping("/api/leerjaars")
@Transactional
public class LeerjaarResource {

    private final Logger log = LoggerFactory.getLogger(LeerjaarResource.class);

    private static final String ENTITY_NAME = "leerjaar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeerjaarRepository leerjaarRepository;

    public LeerjaarResource(LeerjaarRepository leerjaarRepository) {
        this.leerjaarRepository = leerjaarRepository;
    }

    /**
     * {@code POST  /leerjaars} : Create a new leerjaar.
     *
     * @param leerjaar the leerjaar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leerjaar, or with status {@code 400 (Bad Request)} if the leerjaar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leerjaar> createLeerjaar(@RequestBody Leerjaar leerjaar) throws URISyntaxException {
        log.debug("REST request to save Leerjaar : {}", leerjaar);
        if (leerjaar.getId() != null) {
            throw new BadRequestAlertException("A new leerjaar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leerjaar = leerjaarRepository.save(leerjaar);
        return ResponseEntity.created(new URI("/api/leerjaars/" + leerjaar.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leerjaar.getId().toString()))
            .body(leerjaar);
    }

    /**
     * {@code PUT  /leerjaars/:id} : Updates an existing leerjaar.
     *
     * @param id the id of the leerjaar to save.
     * @param leerjaar the leerjaar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leerjaar,
     * or with status {@code 400 (Bad Request)} if the leerjaar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leerjaar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Leerjaar> updateLeerjaar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Leerjaar leerjaar
    ) throws URISyntaxException {
        log.debug("REST request to update Leerjaar : {}, {}", id, leerjaar);
        if (leerjaar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leerjaar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leerjaarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        leerjaar = leerjaarRepository.save(leerjaar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leerjaar.getId().toString()))
            .body(leerjaar);
    }

    /**
     * {@code PATCH  /leerjaars/:id} : Partial updates given fields of an existing leerjaar, field will ignore if it is null
     *
     * @param id the id of the leerjaar to save.
     * @param leerjaar the leerjaar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leerjaar,
     * or with status {@code 400 (Bad Request)} if the leerjaar is not valid,
     * or with status {@code 404 (Not Found)} if the leerjaar is not found,
     * or with status {@code 500 (Internal Server Error)} if the leerjaar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Leerjaar> partialUpdateLeerjaar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Leerjaar leerjaar
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leerjaar partially : {}, {}", id, leerjaar);
        if (leerjaar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leerjaar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leerjaarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Leerjaar> result = leerjaarRepository
            .findById(leerjaar.getId())
            .map(existingLeerjaar -> {
                if (leerjaar.getJaareinde() != null) {
                    existingLeerjaar.setJaareinde(leerjaar.getJaareinde());
                }
                if (leerjaar.getJaarstart() != null) {
                    existingLeerjaar.setJaarstart(leerjaar.getJaarstart());
                }

                return existingLeerjaar;
            })
            .map(leerjaarRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leerjaar.getId().toString())
        );
    }

    /**
     * {@code GET  /leerjaars} : get all the leerjaars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leerjaars in body.
     */
    @GetMapping("")
    public List<Leerjaar> getAllLeerjaars() {
        log.debug("REST request to get all Leerjaars");
        return leerjaarRepository.findAll();
    }

    /**
     * {@code GET  /leerjaars/:id} : get the "id" leerjaar.
     *
     * @param id the id of the leerjaar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leerjaar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leerjaar> getLeerjaar(@PathVariable("id") Long id) {
        log.debug("REST request to get Leerjaar : {}", id);
        Optional<Leerjaar> leerjaar = leerjaarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leerjaar);
    }

    /**
     * {@code DELETE  /leerjaars/:id} : delete the "id" leerjaar.
     *
     * @param id the id of the leerjaar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeerjaar(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leerjaar : {}", id);
        leerjaarRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
