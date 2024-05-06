package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Functiehuis;
import nl.ritense.demo.repository.FunctiehuisRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Functiehuis}.
 */
@RestController
@RequestMapping("/api/functiehuis")
@Transactional
public class FunctiehuisResource {

    private final Logger log = LoggerFactory.getLogger(FunctiehuisResource.class);

    private static final String ENTITY_NAME = "functiehuis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctiehuisRepository functiehuisRepository;

    public FunctiehuisResource(FunctiehuisRepository functiehuisRepository) {
        this.functiehuisRepository = functiehuisRepository;
    }

    /**
     * {@code POST  /functiehuis} : Create a new functiehuis.
     *
     * @param functiehuis the functiehuis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functiehuis, or with status {@code 400 (Bad Request)} if the functiehuis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Functiehuis> createFunctiehuis(@RequestBody Functiehuis functiehuis) throws URISyntaxException {
        log.debug("REST request to save Functiehuis : {}", functiehuis);
        if (functiehuis.getId() != null) {
            throw new BadRequestAlertException("A new functiehuis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        functiehuis = functiehuisRepository.save(functiehuis);
        return ResponseEntity.created(new URI("/api/functiehuis/" + functiehuis.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, functiehuis.getId().toString()))
            .body(functiehuis);
    }

    /**
     * {@code PUT  /functiehuis/:id} : Updates an existing functiehuis.
     *
     * @param id the id of the functiehuis to save.
     * @param functiehuis the functiehuis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functiehuis,
     * or with status {@code 400 (Bad Request)} if the functiehuis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functiehuis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Functiehuis> updateFunctiehuis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Functiehuis functiehuis
    ) throws URISyntaxException {
        log.debug("REST request to update Functiehuis : {}, {}", id, functiehuis);
        if (functiehuis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functiehuis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functiehuisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        functiehuis = functiehuisRepository.save(functiehuis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functiehuis.getId().toString()))
            .body(functiehuis);
    }

    /**
     * {@code PATCH  /functiehuis/:id} : Partial updates given fields of an existing functiehuis, field will ignore if it is null
     *
     * @param id the id of the functiehuis to save.
     * @param functiehuis the functiehuis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functiehuis,
     * or with status {@code 400 (Bad Request)} if the functiehuis is not valid,
     * or with status {@code 404 (Not Found)} if the functiehuis is not found,
     * or with status {@code 500 (Internal Server Error)} if the functiehuis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Functiehuis> partialUpdateFunctiehuis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Functiehuis functiehuis
    ) throws URISyntaxException {
        log.debug("REST request to partial update Functiehuis partially : {}, {}", id, functiehuis);
        if (functiehuis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functiehuis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functiehuisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Functiehuis> result = functiehuisRepository
            .findById(functiehuis.getId())
            .map(existingFunctiehuis -> {
                if (functiehuis.getNaam() != null) {
                    existingFunctiehuis.setNaam(functiehuis.getNaam());
                }
                if (functiehuis.getOmschrijving() != null) {
                    existingFunctiehuis.setOmschrijving(functiehuis.getOmschrijving());
                }

                return existingFunctiehuis;
            })
            .map(functiehuisRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functiehuis.getId().toString())
        );
    }

    /**
     * {@code GET  /functiehuis} : get all the functiehuis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functiehuis in body.
     */
    @GetMapping("")
    public List<Functiehuis> getAllFunctiehuis() {
        log.debug("REST request to get all Functiehuis");
        return functiehuisRepository.findAll();
    }

    /**
     * {@code GET  /functiehuis/:id} : get the "id" functiehuis.
     *
     * @param id the id of the functiehuis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functiehuis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Functiehuis> getFunctiehuis(@PathVariable("id") Long id) {
        log.debug("REST request to get Functiehuis : {}", id);
        Optional<Functiehuis> functiehuis = functiehuisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(functiehuis);
    }

    /**
     * {@code DELETE  /functiehuis/:id} : delete the "id" functiehuis.
     *
     * @param id the id of the functiehuis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFunctiehuis(@PathVariable("id") Long id) {
        log.debug("REST request to delete Functiehuis : {}", id);
        functiehuisRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
