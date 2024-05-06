package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bouwactiviteit;
import nl.ritense.demo.repository.BouwactiviteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bouwactiviteit}.
 */
@RestController
@RequestMapping("/api/bouwactiviteits")
@Transactional
public class BouwactiviteitResource {

    private final Logger log = LoggerFactory.getLogger(BouwactiviteitResource.class);

    private static final String ENTITY_NAME = "bouwactiviteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BouwactiviteitRepository bouwactiviteitRepository;

    public BouwactiviteitResource(BouwactiviteitRepository bouwactiviteitRepository) {
        this.bouwactiviteitRepository = bouwactiviteitRepository;
    }

    /**
     * {@code POST  /bouwactiviteits} : Create a new bouwactiviteit.
     *
     * @param bouwactiviteit the bouwactiviteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bouwactiviteit, or with status {@code 400 (Bad Request)} if the bouwactiviteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bouwactiviteit> createBouwactiviteit(@Valid @RequestBody Bouwactiviteit bouwactiviteit)
        throws URISyntaxException {
        log.debug("REST request to save Bouwactiviteit : {}", bouwactiviteit);
        if (bouwactiviteit.getId() != null) {
            throw new BadRequestAlertException("A new bouwactiviteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bouwactiviteit = bouwactiviteitRepository.save(bouwactiviteit);
        return ResponseEntity.created(new URI("/api/bouwactiviteits/" + bouwactiviteit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bouwactiviteit.getId().toString()))
            .body(bouwactiviteit);
    }

    /**
     * {@code PUT  /bouwactiviteits/:id} : Updates an existing bouwactiviteit.
     *
     * @param id the id of the bouwactiviteit to save.
     * @param bouwactiviteit the bouwactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwactiviteit,
     * or with status {@code 400 (Bad Request)} if the bouwactiviteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bouwactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bouwactiviteit> updateBouwactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bouwactiviteit bouwactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to update Bouwactiviteit : {}, {}", id, bouwactiviteit);
        if (bouwactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bouwactiviteit = bouwactiviteitRepository.save(bouwactiviteit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwactiviteit.getId().toString()))
            .body(bouwactiviteit);
    }

    /**
     * {@code PATCH  /bouwactiviteits/:id} : Partial updates given fields of an existing bouwactiviteit, field will ignore if it is null
     *
     * @param id the id of the bouwactiviteit to save.
     * @param bouwactiviteit the bouwactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwactiviteit,
     * or with status {@code 400 (Bad Request)} if the bouwactiviteit is not valid,
     * or with status {@code 404 (Not Found)} if the bouwactiviteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the bouwactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bouwactiviteit> partialUpdateBouwactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bouwactiviteit bouwactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bouwactiviteit partially : {}, {}", id, bouwactiviteit);
        if (bouwactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bouwactiviteit> result = bouwactiviteitRepository
            .findById(bouwactiviteit.getId())
            .map(existingBouwactiviteit -> {
                if (bouwactiviteit.getBouwjaarklasse() != null) {
                    existingBouwactiviteit.setBouwjaarklasse(bouwactiviteit.getBouwjaarklasse());
                }
                if (bouwactiviteit.getBouwjaartot() != null) {
                    existingBouwactiviteit.setBouwjaartot(bouwactiviteit.getBouwjaartot());
                }
                if (bouwactiviteit.getBouwjaarvan() != null) {
                    existingBouwactiviteit.setBouwjaarvan(bouwactiviteit.getBouwjaarvan());
                }
                if (bouwactiviteit.getIndicatie() != null) {
                    existingBouwactiviteit.setIndicatie(bouwactiviteit.getIndicatie());
                }
                if (bouwactiviteit.getOmschrijving() != null) {
                    existingBouwactiviteit.setOmschrijving(bouwactiviteit.getOmschrijving());
                }

                return existingBouwactiviteit;
            })
            .map(bouwactiviteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwactiviteit.getId().toString())
        );
    }

    /**
     * {@code GET  /bouwactiviteits} : get all the bouwactiviteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bouwactiviteits in body.
     */
    @GetMapping("")
    public List<Bouwactiviteit> getAllBouwactiviteits() {
        log.debug("REST request to get all Bouwactiviteits");
        return bouwactiviteitRepository.findAll();
    }

    /**
     * {@code GET  /bouwactiviteits/:id} : get the "id" bouwactiviteit.
     *
     * @param id the id of the bouwactiviteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bouwactiviteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bouwactiviteit> getBouwactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Bouwactiviteit : {}", id);
        Optional<Bouwactiviteit> bouwactiviteit = bouwactiviteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bouwactiviteit);
    }

    /**
     * {@code DELETE  /bouwactiviteits/:id} : delete the "id" bouwactiviteit.
     *
     * @param id the id of the bouwactiviteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBouwactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bouwactiviteit : {}", id);
        bouwactiviteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
