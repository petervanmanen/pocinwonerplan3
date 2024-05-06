package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sbiactiviteit;
import nl.ritense.demo.repository.SbiactiviteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sbiactiviteit}.
 */
@RestController
@RequestMapping("/api/sbiactiviteits")
@Transactional
public class SbiactiviteitResource {

    private final Logger log = LoggerFactory.getLogger(SbiactiviteitResource.class);

    private static final String ENTITY_NAME = "sbiactiviteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SbiactiviteitRepository sbiactiviteitRepository;

    public SbiactiviteitResource(SbiactiviteitRepository sbiactiviteitRepository) {
        this.sbiactiviteitRepository = sbiactiviteitRepository;
    }

    /**
     * {@code POST  /sbiactiviteits} : Create a new sbiactiviteit.
     *
     * @param sbiactiviteit the sbiactiviteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sbiactiviteit, or with status {@code 400 (Bad Request)} if the sbiactiviteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sbiactiviteit> createSbiactiviteit(@Valid @RequestBody Sbiactiviteit sbiactiviteit) throws URISyntaxException {
        log.debug("REST request to save Sbiactiviteit : {}", sbiactiviteit);
        if (sbiactiviteit.getId() != null) {
            throw new BadRequestAlertException("A new sbiactiviteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sbiactiviteit = sbiactiviteitRepository.save(sbiactiviteit);
        return ResponseEntity.created(new URI("/api/sbiactiviteits/" + sbiactiviteit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sbiactiviteit.getId().toString()))
            .body(sbiactiviteit);
    }

    /**
     * {@code PUT  /sbiactiviteits/:id} : Updates an existing sbiactiviteit.
     *
     * @param id the id of the sbiactiviteit to save.
     * @param sbiactiviteit the sbiactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sbiactiviteit,
     * or with status {@code 400 (Bad Request)} if the sbiactiviteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sbiactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sbiactiviteit> updateSbiactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sbiactiviteit sbiactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to update Sbiactiviteit : {}, {}", id, sbiactiviteit);
        if (sbiactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sbiactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sbiactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sbiactiviteit = sbiactiviteitRepository.save(sbiactiviteit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sbiactiviteit.getId().toString()))
            .body(sbiactiviteit);
    }

    /**
     * {@code PATCH  /sbiactiviteits/:id} : Partial updates given fields of an existing sbiactiviteit, field will ignore if it is null
     *
     * @param id the id of the sbiactiviteit to save.
     * @param sbiactiviteit the sbiactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sbiactiviteit,
     * or with status {@code 400 (Bad Request)} if the sbiactiviteit is not valid,
     * or with status {@code 404 (Not Found)} if the sbiactiviteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the sbiactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sbiactiviteit> partialUpdateSbiactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sbiactiviteit sbiactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sbiactiviteit partially : {}, {}", id, sbiactiviteit);
        if (sbiactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sbiactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sbiactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sbiactiviteit> result = sbiactiviteitRepository
            .findById(sbiactiviteit.getId())
            .map(existingSbiactiviteit -> {
                if (sbiactiviteit.getDatumeindesbiactiviteit() != null) {
                    existingSbiactiviteit.setDatumeindesbiactiviteit(sbiactiviteit.getDatumeindesbiactiviteit());
                }
                if (sbiactiviteit.getDatumingangsbiactiviteit() != null) {
                    existingSbiactiviteit.setDatumingangsbiactiviteit(sbiactiviteit.getDatumingangsbiactiviteit());
                }
                if (sbiactiviteit.getHoofdniveau() != null) {
                    existingSbiactiviteit.setHoofdniveau(sbiactiviteit.getHoofdniveau());
                }
                if (sbiactiviteit.getHoofdniveauomschrijving() != null) {
                    existingSbiactiviteit.setHoofdniveauomschrijving(sbiactiviteit.getHoofdniveauomschrijving());
                }
                if (sbiactiviteit.getNaamactiviteit() != null) {
                    existingSbiactiviteit.setNaamactiviteit(sbiactiviteit.getNaamactiviteit());
                }
                if (sbiactiviteit.getSbicode() != null) {
                    existingSbiactiviteit.setSbicode(sbiactiviteit.getSbicode());
                }
                if (sbiactiviteit.getSbigroep() != null) {
                    existingSbiactiviteit.setSbigroep(sbiactiviteit.getSbigroep());
                }
                if (sbiactiviteit.getSbigroepomschrijving() != null) {
                    existingSbiactiviteit.setSbigroepomschrijving(sbiactiviteit.getSbigroepomschrijving());
                }

                return existingSbiactiviteit;
            })
            .map(sbiactiviteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sbiactiviteit.getId().toString())
        );
    }

    /**
     * {@code GET  /sbiactiviteits} : get all the sbiactiviteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sbiactiviteits in body.
     */
    @GetMapping("")
    public List<Sbiactiviteit> getAllSbiactiviteits() {
        log.debug("REST request to get all Sbiactiviteits");
        return sbiactiviteitRepository.findAll();
    }

    /**
     * {@code GET  /sbiactiviteits/:id} : get the "id" sbiactiviteit.
     *
     * @param id the id of the sbiactiviteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sbiactiviteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sbiactiviteit> getSbiactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Sbiactiviteit : {}", id);
        Optional<Sbiactiviteit> sbiactiviteit = sbiactiviteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sbiactiviteit);
    }

    /**
     * {@code DELETE  /sbiactiviteits/:id} : delete the "id" sbiactiviteit.
     *
     * @param id the id of the sbiactiviteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSbiactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sbiactiviteit : {}", id);
        sbiactiviteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
