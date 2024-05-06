package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verplichtingwmojeugd;
import nl.ritense.demo.repository.VerplichtingwmojeugdRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verplichtingwmojeugd}.
 */
@RestController
@RequestMapping("/api/verplichtingwmojeugds")
@Transactional
public class VerplichtingwmojeugdResource {

    private final Logger log = LoggerFactory.getLogger(VerplichtingwmojeugdResource.class);

    private static final String ENTITY_NAME = "verplichtingwmojeugd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerplichtingwmojeugdRepository verplichtingwmojeugdRepository;

    public VerplichtingwmojeugdResource(VerplichtingwmojeugdRepository verplichtingwmojeugdRepository) {
        this.verplichtingwmojeugdRepository = verplichtingwmojeugdRepository;
    }

    /**
     * {@code POST  /verplichtingwmojeugds} : Create a new verplichtingwmojeugd.
     *
     * @param verplichtingwmojeugd the verplichtingwmojeugd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verplichtingwmojeugd, or with status {@code 400 (Bad Request)} if the verplichtingwmojeugd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verplichtingwmojeugd> createVerplichtingwmojeugd(@RequestBody Verplichtingwmojeugd verplichtingwmojeugd)
        throws URISyntaxException {
        log.debug("REST request to save Verplichtingwmojeugd : {}", verplichtingwmojeugd);
        if (verplichtingwmojeugd.getId() != null) {
            throw new BadRequestAlertException("A new verplichtingwmojeugd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verplichtingwmojeugd = verplichtingwmojeugdRepository.save(verplichtingwmojeugd);
        return ResponseEntity.created(new URI("/api/verplichtingwmojeugds/" + verplichtingwmojeugd.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verplichtingwmojeugd.getId().toString()))
            .body(verplichtingwmojeugd);
    }

    /**
     * {@code PUT  /verplichtingwmojeugds/:id} : Updates an existing verplichtingwmojeugd.
     *
     * @param id the id of the verplichtingwmojeugd to save.
     * @param verplichtingwmojeugd the verplichtingwmojeugd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verplichtingwmojeugd,
     * or with status {@code 400 (Bad Request)} if the verplichtingwmojeugd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verplichtingwmojeugd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verplichtingwmojeugd> updateVerplichtingwmojeugd(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verplichtingwmojeugd verplichtingwmojeugd
    ) throws URISyntaxException {
        log.debug("REST request to update Verplichtingwmojeugd : {}, {}", id, verplichtingwmojeugd);
        if (verplichtingwmojeugd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verplichtingwmojeugd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verplichtingwmojeugdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verplichtingwmojeugd = verplichtingwmojeugdRepository.save(verplichtingwmojeugd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verplichtingwmojeugd.getId().toString()))
            .body(verplichtingwmojeugd);
    }

    /**
     * {@code PATCH  /verplichtingwmojeugds/:id} : Partial updates given fields of an existing verplichtingwmojeugd, field will ignore if it is null
     *
     * @param id the id of the verplichtingwmojeugd to save.
     * @param verplichtingwmojeugd the verplichtingwmojeugd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verplichtingwmojeugd,
     * or with status {@code 400 (Bad Request)} if the verplichtingwmojeugd is not valid,
     * or with status {@code 404 (Not Found)} if the verplichtingwmojeugd is not found,
     * or with status {@code 500 (Internal Server Error)} if the verplichtingwmojeugd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verplichtingwmojeugd> partialUpdateVerplichtingwmojeugd(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verplichtingwmojeugd verplichtingwmojeugd
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verplichtingwmojeugd partially : {}, {}", id, verplichtingwmojeugd);
        if (verplichtingwmojeugd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verplichtingwmojeugd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verplichtingwmojeugdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verplichtingwmojeugd> result = verplichtingwmojeugdRepository
            .findById(verplichtingwmojeugd.getId())
            .map(existingVerplichtingwmojeugd -> {
                if (verplichtingwmojeugd.getBudgetsoort() != null) {
                    existingVerplichtingwmojeugd.setBudgetsoort(verplichtingwmojeugd.getBudgetsoort());
                }
                if (verplichtingwmojeugd.getBudgetsoortgroep() != null) {
                    existingVerplichtingwmojeugd.setBudgetsoortgroep(verplichtingwmojeugd.getBudgetsoortgroep());
                }
                if (verplichtingwmojeugd.getEinddatumgepland() != null) {
                    existingVerplichtingwmojeugd.setEinddatumgepland(verplichtingwmojeugd.getEinddatumgepland());
                }
                if (verplichtingwmojeugd.getFeitelijkeeinddatum() != null) {
                    existingVerplichtingwmojeugd.setFeitelijkeeinddatum(verplichtingwmojeugd.getFeitelijkeeinddatum());
                }
                if (verplichtingwmojeugd.getJaar() != null) {
                    existingVerplichtingwmojeugd.setJaar(verplichtingwmojeugd.getJaar());
                }
                if (verplichtingwmojeugd.getPeriodiciteit() != null) {
                    existingVerplichtingwmojeugd.setPeriodiciteit(verplichtingwmojeugd.getPeriodiciteit());
                }
                if (verplichtingwmojeugd.getVerplichtingsoort() != null) {
                    existingVerplichtingwmojeugd.setVerplichtingsoort(verplichtingwmojeugd.getVerplichtingsoort());
                }

                return existingVerplichtingwmojeugd;
            })
            .map(verplichtingwmojeugdRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verplichtingwmojeugd.getId().toString())
        );
    }

    /**
     * {@code GET  /verplichtingwmojeugds} : get all the verplichtingwmojeugds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verplichtingwmojeugds in body.
     */
    @GetMapping("")
    public List<Verplichtingwmojeugd> getAllVerplichtingwmojeugds() {
        log.debug("REST request to get all Verplichtingwmojeugds");
        return verplichtingwmojeugdRepository.findAll();
    }

    /**
     * {@code GET  /verplichtingwmojeugds/:id} : get the "id" verplichtingwmojeugd.
     *
     * @param id the id of the verplichtingwmojeugd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verplichtingwmojeugd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verplichtingwmojeugd> getVerplichtingwmojeugd(@PathVariable("id") Long id) {
        log.debug("REST request to get Verplichtingwmojeugd : {}", id);
        Optional<Verplichtingwmojeugd> verplichtingwmojeugd = verplichtingwmojeugdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verplichtingwmojeugd);
    }

    /**
     * {@code DELETE  /verplichtingwmojeugds/:id} : delete the "id" verplichtingwmojeugd.
     *
     * @param id the id of the verplichtingwmojeugd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerplichtingwmojeugd(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verplichtingwmojeugd : {}", id);
        verplichtingwmojeugdRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
