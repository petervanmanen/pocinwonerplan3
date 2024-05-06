package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Legesgrondslag;
import nl.ritense.demo.repository.LegesgrondslagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Legesgrondslag}.
 */
@RestController
@RequestMapping("/api/legesgrondslags")
@Transactional
public class LegesgrondslagResource {

    private final Logger log = LoggerFactory.getLogger(LegesgrondslagResource.class);

    private static final String ENTITY_NAME = "legesgrondslag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LegesgrondslagRepository legesgrondslagRepository;

    public LegesgrondslagResource(LegesgrondslagRepository legesgrondslagRepository) {
        this.legesgrondslagRepository = legesgrondslagRepository;
    }

    /**
     * {@code POST  /legesgrondslags} : Create a new legesgrondslag.
     *
     * @param legesgrondslag the legesgrondslag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new legesgrondslag, or with status {@code 400 (Bad Request)} if the legesgrondslag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Legesgrondslag> createLegesgrondslag(@Valid @RequestBody Legesgrondslag legesgrondslag)
        throws URISyntaxException {
        log.debug("REST request to save Legesgrondslag : {}", legesgrondslag);
        if (legesgrondslag.getId() != null) {
            throw new BadRequestAlertException("A new legesgrondslag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        legesgrondslag = legesgrondslagRepository.save(legesgrondslag);
        return ResponseEntity.created(new URI("/api/legesgrondslags/" + legesgrondslag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, legesgrondslag.getId().toString()))
            .body(legesgrondslag);
    }

    /**
     * {@code PUT  /legesgrondslags/:id} : Updates an existing legesgrondslag.
     *
     * @param id the id of the legesgrondslag to save.
     * @param legesgrondslag the legesgrondslag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legesgrondslag,
     * or with status {@code 400 (Bad Request)} if the legesgrondslag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the legesgrondslag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Legesgrondslag> updateLegesgrondslag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Legesgrondslag legesgrondslag
    ) throws URISyntaxException {
        log.debug("REST request to update Legesgrondslag : {}, {}", id, legesgrondslag);
        if (legesgrondslag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, legesgrondslag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!legesgrondslagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        legesgrondslag = legesgrondslagRepository.save(legesgrondslag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, legesgrondslag.getId().toString()))
            .body(legesgrondslag);
    }

    /**
     * {@code PATCH  /legesgrondslags/:id} : Partial updates given fields of an existing legesgrondslag, field will ignore if it is null
     *
     * @param id the id of the legesgrondslag to save.
     * @param legesgrondslag the legesgrondslag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legesgrondslag,
     * or with status {@code 400 (Bad Request)} if the legesgrondslag is not valid,
     * or with status {@code 404 (Not Found)} if the legesgrondslag is not found,
     * or with status {@code 500 (Internal Server Error)} if the legesgrondslag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Legesgrondslag> partialUpdateLegesgrondslag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Legesgrondslag legesgrondslag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Legesgrondslag partially : {}, {}", id, legesgrondslag);
        if (legesgrondslag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, legesgrondslag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!legesgrondslagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Legesgrondslag> result = legesgrondslagRepository
            .findById(legesgrondslag.getId())
            .map(existingLegesgrondslag -> {
                if (legesgrondslag.getAangemaaktdoor() != null) {
                    existingLegesgrondslag.setAangemaaktdoor(legesgrondslag.getAangemaaktdoor());
                }
                if (legesgrondslag.getAantalopgegeven() != null) {
                    existingLegesgrondslag.setAantalopgegeven(legesgrondslag.getAantalopgegeven());
                }
                if (legesgrondslag.getAantalvastgesteld() != null) {
                    existingLegesgrondslag.setAantalvastgesteld(legesgrondslag.getAantalvastgesteld());
                }
                if (legesgrondslag.getAutomatisch() != null) {
                    existingLegesgrondslag.setAutomatisch(legesgrondslag.getAutomatisch());
                }
                if (legesgrondslag.getDatumaanmaak() != null) {
                    existingLegesgrondslag.setDatumaanmaak(legesgrondslag.getDatumaanmaak());
                }
                if (legesgrondslag.getDatummutatie() != null) {
                    existingLegesgrondslag.setDatummutatie(legesgrondslag.getDatummutatie());
                }
                if (legesgrondslag.getEenheid() != null) {
                    existingLegesgrondslag.setEenheid(legesgrondslag.getEenheid());
                }
                if (legesgrondslag.getGemuteerddoor() != null) {
                    existingLegesgrondslag.setGemuteerddoor(legesgrondslag.getGemuteerddoor());
                }
                if (legesgrondslag.getLegesgrondslag() != null) {
                    existingLegesgrondslag.setLegesgrondslag(legesgrondslag.getLegesgrondslag());
                }
                if (legesgrondslag.getOmschrijving() != null) {
                    existingLegesgrondslag.setOmschrijving(legesgrondslag.getOmschrijving());
                }

                return existingLegesgrondslag;
            })
            .map(legesgrondslagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, legesgrondslag.getId().toString())
        );
    }

    /**
     * {@code GET  /legesgrondslags} : get all the legesgrondslags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of legesgrondslags in body.
     */
    @GetMapping("")
    public List<Legesgrondslag> getAllLegesgrondslags() {
        log.debug("REST request to get all Legesgrondslags");
        return legesgrondslagRepository.findAll();
    }

    /**
     * {@code GET  /legesgrondslags/:id} : get the "id" legesgrondslag.
     *
     * @param id the id of the legesgrondslag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the legesgrondslag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Legesgrondslag> getLegesgrondslag(@PathVariable("id") Long id) {
        log.debug("REST request to get Legesgrondslag : {}", id);
        Optional<Legesgrondslag> legesgrondslag = legesgrondslagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(legesgrondslag);
    }

    /**
     * {@code DELETE  /legesgrondslags/:id} : delete the "id" legesgrondslag.
     *
     * @param id the id of the legesgrondslag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLegesgrondslag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Legesgrondslag : {}", id);
        legesgrondslagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
