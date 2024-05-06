package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Normwaarde;
import nl.ritense.demo.repository.NormwaardeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Normwaarde}.
 */
@RestController
@RequestMapping("/api/normwaardes")
@Transactional
public class NormwaardeResource {

    private final Logger log = LoggerFactory.getLogger(NormwaardeResource.class);

    private static final String ENTITY_NAME = "normwaarde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NormwaardeRepository normwaardeRepository;

    public NormwaardeResource(NormwaardeRepository normwaardeRepository) {
        this.normwaardeRepository = normwaardeRepository;
    }

    /**
     * {@code POST  /normwaardes} : Create a new normwaarde.
     *
     * @param normwaarde the normwaarde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new normwaarde, or with status {@code 400 (Bad Request)} if the normwaarde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Normwaarde> createNormwaarde(@Valid @RequestBody Normwaarde normwaarde) throws URISyntaxException {
        log.debug("REST request to save Normwaarde : {}", normwaarde);
        if (normwaarde.getId() != null) {
            throw new BadRequestAlertException("A new normwaarde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        normwaarde = normwaardeRepository.save(normwaarde);
        return ResponseEntity.created(new URI("/api/normwaardes/" + normwaarde.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, normwaarde.getId().toString()))
            .body(normwaarde);
    }

    /**
     * {@code PUT  /normwaardes/:id} : Updates an existing normwaarde.
     *
     * @param id the id of the normwaarde to save.
     * @param normwaarde the normwaarde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated normwaarde,
     * or with status {@code 400 (Bad Request)} if the normwaarde is not valid,
     * or with status {@code 500 (Internal Server Error)} if the normwaarde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Normwaarde> updateNormwaarde(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Normwaarde normwaarde
    ) throws URISyntaxException {
        log.debug("REST request to update Normwaarde : {}, {}", id, normwaarde);
        if (normwaarde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, normwaarde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!normwaardeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        normwaarde = normwaardeRepository.save(normwaarde);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, normwaarde.getId().toString()))
            .body(normwaarde);
    }

    /**
     * {@code PATCH  /normwaardes/:id} : Partial updates given fields of an existing normwaarde, field will ignore if it is null
     *
     * @param id the id of the normwaarde to save.
     * @param normwaarde the normwaarde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated normwaarde,
     * or with status {@code 400 (Bad Request)} if the normwaarde is not valid,
     * or with status {@code 404 (Not Found)} if the normwaarde is not found,
     * or with status {@code 500 (Internal Server Error)} if the normwaarde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Normwaarde> partialUpdateNormwaarde(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Normwaarde normwaarde
    ) throws URISyntaxException {
        log.debug("REST request to partial update Normwaarde partially : {}, {}", id, normwaarde);
        if (normwaarde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, normwaarde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!normwaardeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Normwaarde> result = normwaardeRepository
            .findById(normwaarde.getId())
            .map(existingNormwaarde -> {
                if (normwaarde.getKwalitatievewaarde() != null) {
                    existingNormwaarde.setKwalitatievewaarde(normwaarde.getKwalitatievewaarde());
                }
                if (normwaarde.getKwantitatievewaardeeenheid() != null) {
                    existingNormwaarde.setKwantitatievewaardeeenheid(normwaarde.getKwantitatievewaardeeenheid());
                }
                if (normwaarde.getKwantitatievewaardeomvang() != null) {
                    existingNormwaarde.setKwantitatievewaardeomvang(normwaarde.getKwantitatievewaardeomvang());
                }

                return existingNormwaarde;
            })
            .map(normwaardeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, normwaarde.getId().toString())
        );
    }

    /**
     * {@code GET  /normwaardes} : get all the normwaardes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of normwaardes in body.
     */
    @GetMapping("")
    public List<Normwaarde> getAllNormwaardes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Normwaardes");
        if (eagerload) {
            return normwaardeRepository.findAllWithEagerRelationships();
        } else {
            return normwaardeRepository.findAll();
        }
    }

    /**
     * {@code GET  /normwaardes/:id} : get the "id" normwaarde.
     *
     * @param id the id of the normwaarde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the normwaarde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Normwaarde> getNormwaarde(@PathVariable("id") Long id) {
        log.debug("REST request to get Normwaarde : {}", id);
        Optional<Normwaarde> normwaarde = normwaardeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(normwaarde);
    }

    /**
     * {@code DELETE  /normwaardes/:id} : delete the "id" normwaarde.
     *
     * @param id the id of the normwaarde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNormwaarde(@PathVariable("id") Long id) {
        log.debug("REST request to delete Normwaarde : {}", id);
        normwaardeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
