package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Klimplant;
import nl.ritense.demo.repository.KlimplantRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Klimplant}.
 */
@RestController
@RequestMapping("/api/klimplants")
@Transactional
public class KlimplantResource {

    private final Logger log = LoggerFactory.getLogger(KlimplantResource.class);

    private static final String ENTITY_NAME = "klimplant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KlimplantRepository klimplantRepository;

    public KlimplantResource(KlimplantRepository klimplantRepository) {
        this.klimplantRepository = klimplantRepository;
    }

    /**
     * {@code POST  /klimplants} : Create a new klimplant.
     *
     * @param klimplant the klimplant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new klimplant, or with status {@code 400 (Bad Request)} if the klimplant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Klimplant> createKlimplant(@RequestBody Klimplant klimplant) throws URISyntaxException {
        log.debug("REST request to save Klimplant : {}", klimplant);
        if (klimplant.getId() != null) {
            throw new BadRequestAlertException("A new klimplant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        klimplant = klimplantRepository.save(klimplant);
        return ResponseEntity.created(new URI("/api/klimplants/" + klimplant.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, klimplant.getId().toString()))
            .body(klimplant);
    }

    /**
     * {@code PUT  /klimplants/:id} : Updates an existing klimplant.
     *
     * @param id the id of the klimplant to save.
     * @param klimplant the klimplant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klimplant,
     * or with status {@code 400 (Bad Request)} if the klimplant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the klimplant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Klimplant> updateKlimplant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Klimplant klimplant
    ) throws URISyntaxException {
        log.debug("REST request to update Klimplant : {}, {}", id, klimplant);
        if (klimplant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klimplant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klimplantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        klimplant = klimplantRepository.save(klimplant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, klimplant.getId().toString()))
            .body(klimplant);
    }

    /**
     * {@code PATCH  /klimplants/:id} : Partial updates given fields of an existing klimplant, field will ignore if it is null
     *
     * @param id the id of the klimplant to save.
     * @param klimplant the klimplant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klimplant,
     * or with status {@code 400 (Bad Request)} if the klimplant is not valid,
     * or with status {@code 404 (Not Found)} if the klimplant is not found,
     * or with status {@code 500 (Internal Server Error)} if the klimplant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Klimplant> partialUpdateKlimplant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Klimplant klimplant
    ) throws URISyntaxException {
        log.debug("REST request to partial update Klimplant partially : {}, {}", id, klimplant);
        if (klimplant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klimplant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klimplantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Klimplant> result = klimplantRepository
            .findById(klimplant.getId())
            .map(existingKlimplant -> {
                if (klimplant.getHoogte() != null) {
                    existingKlimplant.setHoogte(klimplant.getHoogte());
                }
                if (klimplant.getKnipfrequentie() != null) {
                    existingKlimplant.setKnipfrequentie(klimplant.getKnipfrequentie());
                }
                if (klimplant.getKnipoppervlakte() != null) {
                    existingKlimplant.setKnipoppervlakte(klimplant.getKnipoppervlakte());
                }
                if (klimplant.getOndersteuningsvorm() != null) {
                    existingKlimplant.setOndersteuningsvorm(klimplant.getOndersteuningsvorm());
                }
                if (klimplant.getType() != null) {
                    existingKlimplant.setType(klimplant.getType());
                }

                return existingKlimplant;
            })
            .map(klimplantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, klimplant.getId().toString())
        );
    }

    /**
     * {@code GET  /klimplants} : get all the klimplants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of klimplants in body.
     */
    @GetMapping("")
    public List<Klimplant> getAllKlimplants() {
        log.debug("REST request to get all Klimplants");
        return klimplantRepository.findAll();
    }

    /**
     * {@code GET  /klimplants/:id} : get the "id" klimplant.
     *
     * @param id the id of the klimplant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the klimplant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Klimplant> getKlimplant(@PathVariable("id") Long id) {
        log.debug("REST request to get Klimplant : {}", id);
        Optional<Klimplant> klimplant = klimplantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(klimplant);
    }

    /**
     * {@code DELETE  /klimplants/:id} : delete the "id" klimplant.
     *
     * @param id the id of the klimplant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKlimplant(@PathVariable("id") Long id) {
        log.debug("REST request to delete Klimplant : {}", id);
        klimplantRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
