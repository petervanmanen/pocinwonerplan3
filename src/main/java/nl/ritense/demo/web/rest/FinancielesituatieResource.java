package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Financielesituatie;
import nl.ritense.demo.repository.FinancielesituatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Financielesituatie}.
 */
@RestController
@RequestMapping("/api/financielesituaties")
@Transactional
public class FinancielesituatieResource {

    private final Logger log = LoggerFactory.getLogger(FinancielesituatieResource.class);

    private static final String ENTITY_NAME = "financielesituatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinancielesituatieRepository financielesituatieRepository;

    public FinancielesituatieResource(FinancielesituatieRepository financielesituatieRepository) {
        this.financielesituatieRepository = financielesituatieRepository;
    }

    /**
     * {@code POST  /financielesituaties} : Create a new financielesituatie.
     *
     * @param financielesituatie the financielesituatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new financielesituatie, or with status {@code 400 (Bad Request)} if the financielesituatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Financielesituatie> createFinancielesituatie(@RequestBody Financielesituatie financielesituatie)
        throws URISyntaxException {
        log.debug("REST request to save Financielesituatie : {}", financielesituatie);
        if (financielesituatie.getId() != null) {
            throw new BadRequestAlertException("A new financielesituatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        financielesituatie = financielesituatieRepository.save(financielesituatie);
        return ResponseEntity.created(new URI("/api/financielesituaties/" + financielesituatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, financielesituatie.getId().toString()))
            .body(financielesituatie);
    }

    /**
     * {@code PUT  /financielesituaties/:id} : Updates an existing financielesituatie.
     *
     * @param id the id of the financielesituatie to save.
     * @param financielesituatie the financielesituatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financielesituatie,
     * or with status {@code 400 (Bad Request)} if the financielesituatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the financielesituatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Financielesituatie> updateFinancielesituatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Financielesituatie financielesituatie
    ) throws URISyntaxException {
        log.debug("REST request to update Financielesituatie : {}, {}", id, financielesituatie);
        if (financielesituatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, financielesituatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!financielesituatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        financielesituatie = financielesituatieRepository.save(financielesituatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, financielesituatie.getId().toString()))
            .body(financielesituatie);
    }

    /**
     * {@code PATCH  /financielesituaties/:id} : Partial updates given fields of an existing financielesituatie, field will ignore if it is null
     *
     * @param id the id of the financielesituatie to save.
     * @param financielesituatie the financielesituatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financielesituatie,
     * or with status {@code 400 (Bad Request)} if the financielesituatie is not valid,
     * or with status {@code 404 (Not Found)} if the financielesituatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the financielesituatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Financielesituatie> partialUpdateFinancielesituatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Financielesituatie financielesituatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Financielesituatie partially : {}, {}", id, financielesituatie);
        if (financielesituatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, financielesituatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!financielesituatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Financielesituatie> result = financielesituatieRepository
            .findById(financielesituatie.getId())
            .map(existingFinancielesituatie -> {
                if (financielesituatie.getDatumvastgesteld() != null) {
                    existingFinancielesituatie.setDatumvastgesteld(financielesituatie.getDatumvastgesteld());
                }
                if (financielesituatie.getSchuld() != null) {
                    existingFinancielesituatie.setSchuld(financielesituatie.getSchuld());
                }

                return existingFinancielesituatie;
            })
            .map(financielesituatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, financielesituatie.getId().toString())
        );
    }

    /**
     * {@code GET  /financielesituaties} : get all the financielesituaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of financielesituaties in body.
     */
    @GetMapping("")
    public List<Financielesituatie> getAllFinancielesituaties() {
        log.debug("REST request to get all Financielesituaties");
        return financielesituatieRepository.findAll();
    }

    /**
     * {@code GET  /financielesituaties/:id} : get the "id" financielesituatie.
     *
     * @param id the id of the financielesituatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the financielesituatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Financielesituatie> getFinancielesituatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Financielesituatie : {}", id);
        Optional<Financielesituatie> financielesituatie = financielesituatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(financielesituatie);
    }

    /**
     * {@code DELETE  /financielesituaties/:id} : delete the "id" financielesituatie.
     *
     * @param id the id of the financielesituatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinancielesituatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Financielesituatie : {}", id);
        financielesituatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
