package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Prijs;
import nl.ritense.demo.repository.PrijsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Prijs}.
 */
@RestController
@RequestMapping("/api/prijs")
@Transactional
public class PrijsResource {

    private final Logger log = LoggerFactory.getLogger(PrijsResource.class);

    private static final String ENTITY_NAME = "prijs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrijsRepository prijsRepository;

    public PrijsResource(PrijsRepository prijsRepository) {
        this.prijsRepository = prijsRepository;
    }

    /**
     * {@code POST  /prijs} : Create a new prijs.
     *
     * @param prijs the prijs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prijs, or with status {@code 400 (Bad Request)} if the prijs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Prijs> createPrijs(@Valid @RequestBody Prijs prijs) throws URISyntaxException {
        log.debug("REST request to save Prijs : {}", prijs);
        if (prijs.getId() != null) {
            throw new BadRequestAlertException("A new prijs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prijs = prijsRepository.save(prijs);
        return ResponseEntity.created(new URI("/api/prijs/" + prijs.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, prijs.getId().toString()))
            .body(prijs);
    }

    /**
     * {@code PUT  /prijs/:id} : Updates an existing prijs.
     *
     * @param id the id of the prijs to save.
     * @param prijs the prijs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijs,
     * or with status {@code 400 (Bad Request)} if the prijs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prijs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Prijs> updatePrijs(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Prijs prijs)
        throws URISyntaxException {
        log.debug("REST request to update Prijs : {}, {}", id, prijs);
        if (prijs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prijs = prijsRepository.save(prijs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijs.getId().toString()))
            .body(prijs);
    }

    /**
     * {@code PATCH  /prijs/:id} : Partial updates given fields of an existing prijs, field will ignore if it is null
     *
     * @param id the id of the prijs to save.
     * @param prijs the prijs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijs,
     * or with status {@code 400 (Bad Request)} if the prijs is not valid,
     * or with status {@code 404 (Not Found)} if the prijs is not found,
     * or with status {@code 500 (Internal Server Error)} if the prijs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Prijs> partialUpdatePrijs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Prijs prijs
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prijs partially : {}, {}", id, prijs);
        if (prijs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Prijs> result = prijsRepository
            .findById(prijs.getId())
            .map(existingPrijs -> {
                if (prijs.getBedrag() != null) {
                    existingPrijs.setBedrag(prijs.getBedrag());
                }
                if (prijs.getDatumeindegeldigheid() != null) {
                    existingPrijs.setDatumeindegeldigheid(prijs.getDatumeindegeldigheid());
                }
                if (prijs.getDatumstart() != null) {
                    existingPrijs.setDatumstart(prijs.getDatumstart());
                }

                return existingPrijs;
            })
            .map(prijsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijs.getId().toString())
        );
    }

    /**
     * {@code GET  /prijs} : get all the prijs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prijs in body.
     */
    @GetMapping("")
    public List<Prijs> getAllPrijs() {
        log.debug("REST request to get all Prijs");
        return prijsRepository.findAll();
    }

    /**
     * {@code GET  /prijs/:id} : get the "id" prijs.
     *
     * @param id the id of the prijs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prijs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prijs> getPrijs(@PathVariable("id") Long id) {
        log.debug("REST request to get Prijs : {}", id);
        Optional<Prijs> prijs = prijsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prijs);
    }

    /**
     * {@code DELETE  /prijs/:id} : delete the "id" prijs.
     *
     * @param id the id of the prijs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrijs(@PathVariable("id") Long id) {
        log.debug("REST request to delete Prijs : {}", id);
        prijsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
