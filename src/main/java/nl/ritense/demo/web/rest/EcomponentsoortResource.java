package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ecomponentsoort;
import nl.ritense.demo.repository.EcomponentsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ecomponentsoort}.
 */
@RestController
@RequestMapping("/api/ecomponentsoorts")
@Transactional
public class EcomponentsoortResource {

    private final Logger log = LoggerFactory.getLogger(EcomponentsoortResource.class);

    private static final String ENTITY_NAME = "ecomponentsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcomponentsoortRepository ecomponentsoortRepository;

    public EcomponentsoortResource(EcomponentsoortRepository ecomponentsoortRepository) {
        this.ecomponentsoortRepository = ecomponentsoortRepository;
    }

    /**
     * {@code POST  /ecomponentsoorts} : Create a new ecomponentsoort.
     *
     * @param ecomponentsoort the ecomponentsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecomponentsoort, or with status {@code 400 (Bad Request)} if the ecomponentsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ecomponentsoort> createEcomponentsoort(@Valid @RequestBody Ecomponentsoort ecomponentsoort)
        throws URISyntaxException {
        log.debug("REST request to save Ecomponentsoort : {}", ecomponentsoort);
        if (ecomponentsoort.getId() != null) {
            throw new BadRequestAlertException("A new ecomponentsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ecomponentsoort = ecomponentsoortRepository.save(ecomponentsoort);
        return ResponseEntity.created(new URI("/api/ecomponentsoorts/" + ecomponentsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ecomponentsoort.getId().toString()))
            .body(ecomponentsoort);
    }

    /**
     * {@code PUT  /ecomponentsoorts/:id} : Updates an existing ecomponentsoort.
     *
     * @param id the id of the ecomponentsoort to save.
     * @param ecomponentsoort the ecomponentsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecomponentsoort,
     * or with status {@code 400 (Bad Request)} if the ecomponentsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecomponentsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ecomponentsoort> updateEcomponentsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ecomponentsoort ecomponentsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Ecomponentsoort : {}, {}", id, ecomponentsoort);
        if (ecomponentsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecomponentsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecomponentsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ecomponentsoort = ecomponentsoortRepository.save(ecomponentsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ecomponentsoort.getId().toString()))
            .body(ecomponentsoort);
    }

    /**
     * {@code PATCH  /ecomponentsoorts/:id} : Partial updates given fields of an existing ecomponentsoort, field will ignore if it is null
     *
     * @param id the id of the ecomponentsoort to save.
     * @param ecomponentsoort the ecomponentsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecomponentsoort,
     * or with status {@code 400 (Bad Request)} if the ecomponentsoort is not valid,
     * or with status {@code 404 (Not Found)} if the ecomponentsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecomponentsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ecomponentsoort> partialUpdateEcomponentsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ecomponentsoort ecomponentsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ecomponentsoort partially : {}, {}", id, ecomponentsoort);
        if (ecomponentsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecomponentsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecomponentsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ecomponentsoort> result = ecomponentsoortRepository
            .findById(ecomponentsoort.getId())
            .map(existingEcomponentsoort -> {
                if (ecomponentsoort.getEcomponent() != null) {
                    existingEcomponentsoort.setEcomponent(ecomponentsoort.getEcomponent());
                }
                if (ecomponentsoort.getEcomponentcode() != null) {
                    existingEcomponentsoort.setEcomponentcode(ecomponentsoort.getEcomponentcode());
                }
                if (ecomponentsoort.getKolom() != null) {
                    existingEcomponentsoort.setKolom(ecomponentsoort.getKolom());
                }
                if (ecomponentsoort.getKolomcode() != null) {
                    existingEcomponentsoort.setKolomcode(ecomponentsoort.getKolomcode());
                }
                if (ecomponentsoort.getRegeling() != null) {
                    existingEcomponentsoort.setRegeling(ecomponentsoort.getRegeling());
                }
                if (ecomponentsoort.getRegelingcode() != null) {
                    existingEcomponentsoort.setRegelingcode(ecomponentsoort.getRegelingcode());
                }

                return existingEcomponentsoort;
            })
            .map(ecomponentsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ecomponentsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /ecomponentsoorts} : get all the ecomponentsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecomponentsoorts in body.
     */
    @GetMapping("")
    public List<Ecomponentsoort> getAllEcomponentsoorts() {
        log.debug("REST request to get all Ecomponentsoorts");
        return ecomponentsoortRepository.findAll();
    }

    /**
     * {@code GET  /ecomponentsoorts/:id} : get the "id" ecomponentsoort.
     *
     * @param id the id of the ecomponentsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecomponentsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ecomponentsoort> getEcomponentsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Ecomponentsoort : {}", id);
        Optional<Ecomponentsoort> ecomponentsoort = ecomponentsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ecomponentsoort);
    }

    /**
     * {@code DELETE  /ecomponentsoorts/:id} : delete the "id" ecomponentsoort.
     *
     * @param id the id of the ecomponentsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEcomponentsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ecomponentsoort : {}", id);
        ecomponentsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
