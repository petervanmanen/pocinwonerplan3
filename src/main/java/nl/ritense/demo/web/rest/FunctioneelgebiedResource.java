package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Functioneelgebied;
import nl.ritense.demo.repository.FunctioneelgebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Functioneelgebied}.
 */
@RestController
@RequestMapping("/api/functioneelgebieds")
@Transactional
public class FunctioneelgebiedResource {

    private final Logger log = LoggerFactory.getLogger(FunctioneelgebiedResource.class);

    private static final String ENTITY_NAME = "functioneelgebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctioneelgebiedRepository functioneelgebiedRepository;

    public FunctioneelgebiedResource(FunctioneelgebiedRepository functioneelgebiedRepository) {
        this.functioneelgebiedRepository = functioneelgebiedRepository;
    }

    /**
     * {@code POST  /functioneelgebieds} : Create a new functioneelgebied.
     *
     * @param functioneelgebied the functioneelgebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functioneelgebied, or with status {@code 400 (Bad Request)} if the functioneelgebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Functioneelgebied> createFunctioneelgebied(@Valid @RequestBody Functioneelgebied functioneelgebied)
        throws URISyntaxException {
        log.debug("REST request to save Functioneelgebied : {}", functioneelgebied);
        if (functioneelgebied.getId() != null) {
            throw new BadRequestAlertException("A new functioneelgebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        functioneelgebied = functioneelgebiedRepository.save(functioneelgebied);
        return ResponseEntity.created(new URI("/api/functioneelgebieds/" + functioneelgebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, functioneelgebied.getId().toString()))
            .body(functioneelgebied);
    }

    /**
     * {@code PUT  /functioneelgebieds/:id} : Updates an existing functioneelgebied.
     *
     * @param id the id of the functioneelgebied to save.
     * @param functioneelgebied the functioneelgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functioneelgebied,
     * or with status {@code 400 (Bad Request)} if the functioneelgebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functioneelgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Functioneelgebied> updateFunctioneelgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Functioneelgebied functioneelgebied
    ) throws URISyntaxException {
        log.debug("REST request to update Functioneelgebied : {}, {}", id, functioneelgebied);
        if (functioneelgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functioneelgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functioneelgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        functioneelgebied = functioneelgebiedRepository.save(functioneelgebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functioneelgebied.getId().toString()))
            .body(functioneelgebied);
    }

    /**
     * {@code PATCH  /functioneelgebieds/:id} : Partial updates given fields of an existing functioneelgebied, field will ignore if it is null
     *
     * @param id the id of the functioneelgebied to save.
     * @param functioneelgebied the functioneelgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functioneelgebied,
     * or with status {@code 400 (Bad Request)} if the functioneelgebied is not valid,
     * or with status {@code 404 (Not Found)} if the functioneelgebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the functioneelgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Functioneelgebied> partialUpdateFunctioneelgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Functioneelgebied functioneelgebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Functioneelgebied partially : {}, {}", id, functioneelgebied);
        if (functioneelgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functioneelgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functioneelgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Functioneelgebied> result = functioneelgebiedRepository
            .findById(functioneelgebied.getId())
            .map(existingFunctioneelgebied -> {
                if (functioneelgebied.getFunctioneelgebiedcode() != null) {
                    existingFunctioneelgebied.setFunctioneelgebiedcode(functioneelgebied.getFunctioneelgebiedcode());
                }
                if (functioneelgebied.getFunctioneelgebiednaam() != null) {
                    existingFunctioneelgebied.setFunctioneelgebiednaam(functioneelgebied.getFunctioneelgebiednaam());
                }
                if (functioneelgebied.getOmtrek() != null) {
                    existingFunctioneelgebied.setOmtrek(functioneelgebied.getOmtrek());
                }
                if (functioneelgebied.getOppervlakte() != null) {
                    existingFunctioneelgebied.setOppervlakte(functioneelgebied.getOppervlakte());
                }

                return existingFunctioneelgebied;
            })
            .map(functioneelgebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functioneelgebied.getId().toString())
        );
    }

    /**
     * {@code GET  /functioneelgebieds} : get all the functioneelgebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functioneelgebieds in body.
     */
    @GetMapping("")
    public List<Functioneelgebied> getAllFunctioneelgebieds() {
        log.debug("REST request to get all Functioneelgebieds");
        return functioneelgebiedRepository.findAll();
    }

    /**
     * {@code GET  /functioneelgebieds/:id} : get the "id" functioneelgebied.
     *
     * @param id the id of the functioneelgebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functioneelgebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Functioneelgebied> getFunctioneelgebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Functioneelgebied : {}", id);
        Optional<Functioneelgebied> functioneelgebied = functioneelgebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(functioneelgebied);
    }

    /**
     * {@code DELETE  /functioneelgebieds/:id} : delete the "id" functioneelgebied.
     *
     * @param id the id of the functioneelgebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFunctioneelgebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Functioneelgebied : {}", id);
        functioneelgebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
