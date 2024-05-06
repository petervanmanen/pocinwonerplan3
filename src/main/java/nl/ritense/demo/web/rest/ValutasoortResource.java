package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Valutasoort;
import nl.ritense.demo.repository.ValutasoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Valutasoort}.
 */
@RestController
@RequestMapping("/api/valutasoorts")
@Transactional
public class ValutasoortResource {

    private final Logger log = LoggerFactory.getLogger(ValutasoortResource.class);

    private static final String ENTITY_NAME = "valutasoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValutasoortRepository valutasoortRepository;

    public ValutasoortResource(ValutasoortRepository valutasoortRepository) {
        this.valutasoortRepository = valutasoortRepository;
    }

    /**
     * {@code POST  /valutasoorts} : Create a new valutasoort.
     *
     * @param valutasoort the valutasoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new valutasoort, or with status {@code 400 (Bad Request)} if the valutasoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Valutasoort> createValutasoort(@RequestBody Valutasoort valutasoort) throws URISyntaxException {
        log.debug("REST request to save Valutasoort : {}", valutasoort);
        if (valutasoort.getId() != null) {
            throw new BadRequestAlertException("A new valutasoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        valutasoort = valutasoortRepository.save(valutasoort);
        return ResponseEntity.created(new URI("/api/valutasoorts/" + valutasoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, valutasoort.getId().toString()))
            .body(valutasoort);
    }

    /**
     * {@code PUT  /valutasoorts/:id} : Updates an existing valutasoort.
     *
     * @param id the id of the valutasoort to save.
     * @param valutasoort the valutasoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated valutasoort,
     * or with status {@code 400 (Bad Request)} if the valutasoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the valutasoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Valutasoort> updateValutasoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Valutasoort valutasoort
    ) throws URISyntaxException {
        log.debug("REST request to update Valutasoort : {}, {}", id, valutasoort);
        if (valutasoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, valutasoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!valutasoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        valutasoort = valutasoortRepository.save(valutasoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, valutasoort.getId().toString()))
            .body(valutasoort);
    }

    /**
     * {@code PATCH  /valutasoorts/:id} : Partial updates given fields of an existing valutasoort, field will ignore if it is null
     *
     * @param id the id of the valutasoort to save.
     * @param valutasoort the valutasoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated valutasoort,
     * or with status {@code 400 (Bad Request)} if the valutasoort is not valid,
     * or with status {@code 404 (Not Found)} if the valutasoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the valutasoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Valutasoort> partialUpdateValutasoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Valutasoort valutasoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Valutasoort partially : {}, {}", id, valutasoort);
        if (valutasoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, valutasoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!valutasoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Valutasoort> result = valutasoortRepository
            .findById(valutasoort.getId())
            .map(existingValutasoort -> {
                if (valutasoort.getDatumbegingeldigheidvalutasoort() != null) {
                    existingValutasoort.setDatumbegingeldigheidvalutasoort(valutasoort.getDatumbegingeldigheidvalutasoort());
                }
                if (valutasoort.getDatumeindegeldigheidvalutasoort() != null) {
                    existingValutasoort.setDatumeindegeldigheidvalutasoort(valutasoort.getDatumeindegeldigheidvalutasoort());
                }
                if (valutasoort.getNaamvaluta() != null) {
                    existingValutasoort.setNaamvaluta(valutasoort.getNaamvaluta());
                }
                if (valutasoort.getValutacode() != null) {
                    existingValutasoort.setValutacode(valutasoort.getValutacode());
                }

                return existingValutasoort;
            })
            .map(valutasoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, valutasoort.getId().toString())
        );
    }

    /**
     * {@code GET  /valutasoorts} : get all the valutasoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of valutasoorts in body.
     */
    @GetMapping("")
    public List<Valutasoort> getAllValutasoorts() {
        log.debug("REST request to get all Valutasoorts");
        return valutasoortRepository.findAll();
    }

    /**
     * {@code GET  /valutasoorts/:id} : get the "id" valutasoort.
     *
     * @param id the id of the valutasoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the valutasoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Valutasoort> getValutasoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Valutasoort : {}", id);
        Optional<Valutasoort> valutasoort = valutasoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(valutasoort);
    }

    /**
     * {@code DELETE  /valutasoorts/:id} : delete the "id" valutasoort.
     *
     * @param id the id of the valutasoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValutasoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Valutasoort : {}", id);
        valutasoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
