package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Zelfredzaamheidmatrix;
import nl.ritense.demo.repository.ZelfredzaamheidmatrixRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Zelfredzaamheidmatrix}.
 */
@RestController
@RequestMapping("/api/zelfredzaamheidmatrices")
@Transactional
public class ZelfredzaamheidmatrixResource {

    private final Logger log = LoggerFactory.getLogger(ZelfredzaamheidmatrixResource.class);

    private static final String ENTITY_NAME = "zelfredzaamheidmatrix";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZelfredzaamheidmatrixRepository zelfredzaamheidmatrixRepository;

    public ZelfredzaamheidmatrixResource(ZelfredzaamheidmatrixRepository zelfredzaamheidmatrixRepository) {
        this.zelfredzaamheidmatrixRepository = zelfredzaamheidmatrixRepository;
    }

    /**
     * {@code POST  /zelfredzaamheidmatrices} : Create a new zelfredzaamheidmatrix.
     *
     * @param zelfredzaamheidmatrix the zelfredzaamheidmatrix to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zelfredzaamheidmatrix, or with status {@code 400 (Bad Request)} if the zelfredzaamheidmatrix has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Zelfredzaamheidmatrix> createZelfredzaamheidmatrix(@RequestBody Zelfredzaamheidmatrix zelfredzaamheidmatrix)
        throws URISyntaxException {
        log.debug("REST request to save Zelfredzaamheidmatrix : {}", zelfredzaamheidmatrix);
        if (zelfredzaamheidmatrix.getId() != null) {
            throw new BadRequestAlertException("A new zelfredzaamheidmatrix cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zelfredzaamheidmatrix = zelfredzaamheidmatrixRepository.save(zelfredzaamheidmatrix);
        return ResponseEntity.created(new URI("/api/zelfredzaamheidmatrices/" + zelfredzaamheidmatrix.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, zelfredzaamheidmatrix.getId().toString()))
            .body(zelfredzaamheidmatrix);
    }

    /**
     * {@code PUT  /zelfredzaamheidmatrices/:id} : Updates an existing zelfredzaamheidmatrix.
     *
     * @param id the id of the zelfredzaamheidmatrix to save.
     * @param zelfredzaamheidmatrix the zelfredzaamheidmatrix to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zelfredzaamheidmatrix,
     * or with status {@code 400 (Bad Request)} if the zelfredzaamheidmatrix is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zelfredzaamheidmatrix couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Zelfredzaamheidmatrix> updateZelfredzaamheidmatrix(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zelfredzaamheidmatrix zelfredzaamheidmatrix
    ) throws URISyntaxException {
        log.debug("REST request to update Zelfredzaamheidmatrix : {}, {}", id, zelfredzaamheidmatrix);
        if (zelfredzaamheidmatrix.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zelfredzaamheidmatrix.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zelfredzaamheidmatrixRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        zelfredzaamheidmatrix = zelfredzaamheidmatrixRepository.save(zelfredzaamheidmatrix);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zelfredzaamheidmatrix.getId().toString()))
            .body(zelfredzaamheidmatrix);
    }

    /**
     * {@code PATCH  /zelfredzaamheidmatrices/:id} : Partial updates given fields of an existing zelfredzaamheidmatrix, field will ignore if it is null
     *
     * @param id the id of the zelfredzaamheidmatrix to save.
     * @param zelfredzaamheidmatrix the zelfredzaamheidmatrix to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zelfredzaamheidmatrix,
     * or with status {@code 400 (Bad Request)} if the zelfredzaamheidmatrix is not valid,
     * or with status {@code 404 (Not Found)} if the zelfredzaamheidmatrix is not found,
     * or with status {@code 500 (Internal Server Error)} if the zelfredzaamheidmatrix couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Zelfredzaamheidmatrix> partialUpdateZelfredzaamheidmatrix(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zelfredzaamheidmatrix zelfredzaamheidmatrix
    ) throws URISyntaxException {
        log.debug("REST request to partial update Zelfredzaamheidmatrix partially : {}, {}", id, zelfredzaamheidmatrix);
        if (zelfredzaamheidmatrix.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zelfredzaamheidmatrix.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zelfredzaamheidmatrixRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Zelfredzaamheidmatrix> result = zelfredzaamheidmatrixRepository
            .findById(zelfredzaamheidmatrix.getId())
            .map(existingZelfredzaamheidmatrix -> {
                if (zelfredzaamheidmatrix.getDatumeindegeldigheid() != null) {
                    existingZelfredzaamheidmatrix.setDatumeindegeldigheid(zelfredzaamheidmatrix.getDatumeindegeldigheid());
                }
                if (zelfredzaamheidmatrix.getDatumstartgeldigheid() != null) {
                    existingZelfredzaamheidmatrix.setDatumstartgeldigheid(zelfredzaamheidmatrix.getDatumstartgeldigheid());
                }
                if (zelfredzaamheidmatrix.getNaam() != null) {
                    existingZelfredzaamheidmatrix.setNaam(zelfredzaamheidmatrix.getNaam());
                }
                if (zelfredzaamheidmatrix.getOmschrijving() != null) {
                    existingZelfredzaamheidmatrix.setOmschrijving(zelfredzaamheidmatrix.getOmschrijving());
                }

                return existingZelfredzaamheidmatrix;
            })
            .map(zelfredzaamheidmatrixRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zelfredzaamheidmatrix.getId().toString())
        );
    }

    /**
     * {@code GET  /zelfredzaamheidmatrices} : get all the zelfredzaamheidmatrices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zelfredzaamheidmatrices in body.
     */
    @GetMapping("")
    public List<Zelfredzaamheidmatrix> getAllZelfredzaamheidmatrices() {
        log.debug("REST request to get all Zelfredzaamheidmatrices");
        return zelfredzaamheidmatrixRepository.findAll();
    }

    /**
     * {@code GET  /zelfredzaamheidmatrices/:id} : get the "id" zelfredzaamheidmatrix.
     *
     * @param id the id of the zelfredzaamheidmatrix to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zelfredzaamheidmatrix, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Zelfredzaamheidmatrix> getZelfredzaamheidmatrix(@PathVariable("id") Long id) {
        log.debug("REST request to get Zelfredzaamheidmatrix : {}", id);
        Optional<Zelfredzaamheidmatrix> zelfredzaamheidmatrix = zelfredzaamheidmatrixRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zelfredzaamheidmatrix);
    }

    /**
     * {@code DELETE  /zelfredzaamheidmatrices/:id} : delete the "id" zelfredzaamheidmatrix.
     *
     * @param id the id of the zelfredzaamheidmatrix to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZelfredzaamheidmatrix(@PathVariable("id") Long id) {
        log.debug("REST request to delete Zelfredzaamheidmatrix : {}", id);
        zelfredzaamheidmatrixRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
