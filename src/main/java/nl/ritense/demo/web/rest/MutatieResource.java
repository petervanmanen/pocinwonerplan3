package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Mutatie;
import nl.ritense.demo.repository.MutatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Mutatie}.
 */
@RestController
@RequestMapping("/api/mutaties")
@Transactional
public class MutatieResource {

    private final Logger log = LoggerFactory.getLogger(MutatieResource.class);

    private static final String ENTITY_NAME = "mutatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MutatieRepository mutatieRepository;

    public MutatieResource(MutatieRepository mutatieRepository) {
        this.mutatieRepository = mutatieRepository;
    }

    /**
     * {@code POST  /mutaties} : Create a new mutatie.
     *
     * @param mutatie the mutatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mutatie, or with status {@code 400 (Bad Request)} if the mutatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Mutatie> createMutatie(@RequestBody Mutatie mutatie) throws URISyntaxException {
        log.debug("REST request to save Mutatie : {}", mutatie);
        if (mutatie.getId() != null) {
            throw new BadRequestAlertException("A new mutatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mutatie = mutatieRepository.save(mutatie);
        return ResponseEntity.created(new URI("/api/mutaties/" + mutatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mutatie.getId().toString()))
            .body(mutatie);
    }

    /**
     * {@code PUT  /mutaties/:id} : Updates an existing mutatie.
     *
     * @param id the id of the mutatie to save.
     * @param mutatie the mutatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mutatie,
     * or with status {@code 400 (Bad Request)} if the mutatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mutatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mutatie> updateMutatie(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mutatie mutatie)
        throws URISyntaxException {
        log.debug("REST request to update Mutatie : {}, {}", id, mutatie);
        if (mutatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mutatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mutatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mutatie = mutatieRepository.save(mutatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mutatie.getId().toString()))
            .body(mutatie);
    }

    /**
     * {@code PATCH  /mutaties/:id} : Partial updates given fields of an existing mutatie, field will ignore if it is null
     *
     * @param id the id of the mutatie to save.
     * @param mutatie the mutatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mutatie,
     * or with status {@code 400 (Bad Request)} if the mutatie is not valid,
     * or with status {@code 404 (Not Found)} if the mutatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the mutatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mutatie> partialUpdateMutatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mutatie mutatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mutatie partially : {}, {}", id, mutatie);
        if (mutatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mutatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mutatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mutatie> result = mutatieRepository
            .findById(mutatie.getId())
            .map(existingMutatie -> {
                if (mutatie.getBedrag() != null) {
                    existingMutatie.setBedrag(mutatie.getBedrag());
                }
                if (mutatie.getDatum() != null) {
                    existingMutatie.setDatum(mutatie.getDatum());
                }

                return existingMutatie;
            })
            .map(mutatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mutatie.getId().toString())
        );
    }

    /**
     * {@code GET  /mutaties} : get all the mutaties.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mutaties in body.
     */
    @GetMapping("")
    public List<Mutatie> getAllMutaties(@RequestParam(name = "filter", required = false) String filter) {
        if ("leidttotbankafschriftregel-is-null".equals(filter)) {
            log.debug("REST request to get all Mutaties where leidttotBankafschriftregel is null");
            return StreamSupport.stream(mutatieRepository.findAll().spliterator(), false)
                .filter(mutatie -> mutatie.getLeidttotBankafschriftregel() == null)
                .toList();
        }

        if ("leidttotbatchregel-is-null".equals(filter)) {
            log.debug("REST request to get all Mutaties where leidttotBatchregel is null");
            return StreamSupport.stream(mutatieRepository.findAll().spliterator(), false)
                .filter(mutatie -> mutatie.getLeidttotBatchregel() == null)
                .toList();
        }

        if ("leidttotfactuurregel-is-null".equals(filter)) {
            log.debug("REST request to get all Mutaties where leidttotFactuurregel is null");
            return StreamSupport.stream(mutatieRepository.findAll().spliterator(), false)
                .filter(mutatie -> mutatie.getLeidttotFactuurregel() == null)
                .toList();
        }
        log.debug("REST request to get all Mutaties");
        return mutatieRepository.findAll();
    }

    /**
     * {@code GET  /mutaties/:id} : get the "id" mutatie.
     *
     * @param id the id of the mutatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mutatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mutatie> getMutatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Mutatie : {}", id);
        Optional<Mutatie> mutatie = mutatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mutatie);
    }

    /**
     * {@code DELETE  /mutaties/:id} : delete the "id" mutatie.
     *
     * @param id the id of the mutatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMutatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Mutatie : {}", id);
        mutatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
