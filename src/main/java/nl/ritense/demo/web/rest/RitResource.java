package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Rit;
import nl.ritense.demo.repository.RitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Rit}.
 */
@RestController
@RequestMapping("/api/rits")
@Transactional
public class RitResource {

    private final Logger log = LoggerFactory.getLogger(RitResource.class);

    private static final String ENTITY_NAME = "rit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RitRepository ritRepository;

    public RitResource(RitRepository ritRepository) {
        this.ritRepository = ritRepository;
    }

    /**
     * {@code POST  /rits} : Create a new rit.
     *
     * @param rit the rit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rit, or with status {@code 400 (Bad Request)} if the rit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rit> createRit(@Valid @RequestBody Rit rit) throws URISyntaxException {
        log.debug("REST request to save Rit : {}", rit);
        if (rit.getId() != null) {
            throw new BadRequestAlertException("A new rit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rit = ritRepository.save(rit);
        return ResponseEntity.created(new URI("/api/rits/" + rit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rit.getId().toString()))
            .body(rit);
    }

    /**
     * {@code PUT  /rits/:id} : Updates an existing rit.
     *
     * @param id the id of the rit to save.
     * @param rit the rit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rit,
     * or with status {@code 400 (Bad Request)} if the rit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rit> updateRit(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Rit rit)
        throws URISyntaxException {
        log.debug("REST request to update Rit : {}, {}", id, rit);
        if (rit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ritRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rit = ritRepository.save(rit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rit.getId().toString()))
            .body(rit);
    }

    /**
     * {@code PATCH  /rits/:id} : Partial updates given fields of an existing rit, field will ignore if it is null
     *
     * @param id the id of the rit to save.
     * @param rit the rit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rit,
     * or with status {@code 400 (Bad Request)} if the rit is not valid,
     * or with status {@code 404 (Not Found)} if the rit is not found,
     * or with status {@code 500 (Internal Server Error)} if the rit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rit> partialUpdateRit(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Rit rit)
        throws URISyntaxException {
        log.debug("REST request to partial update Rit partially : {}, {}", id, rit);
        if (rit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ritRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rit> result = ritRepository
            .findById(rit.getId())
            .map(existingRit -> {
                if (rit.getEindtijd() != null) {
                    existingRit.setEindtijd(rit.getEindtijd());
                }
                if (rit.getRitcode() != null) {
                    existingRit.setRitcode(rit.getRitcode());
                }
                if (rit.getStarttijd() != null) {
                    existingRit.setStarttijd(rit.getStarttijd());
                }

                return existingRit;
            })
            .map(ritRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rit.getId().toString())
        );
    }

    /**
     * {@code GET  /rits} : get all the rits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rits in body.
     */
    @GetMapping("")
    public List<Rit> getAllRits() {
        log.debug("REST request to get all Rits");
        return ritRepository.findAll();
    }

    /**
     * {@code GET  /rits/:id} : get the "id" rit.
     *
     * @param id the id of the rit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rit> getRit(@PathVariable("id") Long id) {
        log.debug("REST request to get Rit : {}", id);
        Optional<Rit> rit = ritRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rit);
    }

    /**
     * {@code DELETE  /rits/:id} : delete the "id" rit.
     *
     * @param id the id of the rit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rit : {}", id);
        ritRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
