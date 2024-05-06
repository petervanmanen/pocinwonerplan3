package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Index;
import nl.ritense.demo.repository.IndexRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Index}.
 */
@RestController
@RequestMapping("/api/indices")
@Transactional
public class IndexResource {

    private final Logger log = LoggerFactory.getLogger(IndexResource.class);

    private static final String ENTITY_NAME = "index";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndexRepository indexRepository;

    public IndexResource(IndexRepository indexRepository) {
        this.indexRepository = indexRepository;
    }

    /**
     * {@code POST  /indices} : Create a new index.
     *
     * @param index the index to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new index, or with status {@code 400 (Bad Request)} if the index has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Index> createIndex(@RequestBody Index index) throws URISyntaxException {
        log.debug("REST request to save Index : {}", index);
        if (index.getId() != null) {
            throw new BadRequestAlertException("A new index cannot already have an ID", ENTITY_NAME, "idexists");
        }
        index = indexRepository.save(index);
        return ResponseEntity.created(new URI("/api/indices/" + index.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, index.getId().toString()))
            .body(index);
    }

    /**
     * {@code PUT  /indices/:id} : Updates an existing index.
     *
     * @param id the id of the index to save.
     * @param index the index to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated index,
     * or with status {@code 400 (Bad Request)} if the index is not valid,
     * or with status {@code 500 (Internal Server Error)} if the index couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Index> updateIndex(@PathVariable(value = "id", required = false) final Long id, @RequestBody Index index)
        throws URISyntaxException {
        log.debug("REST request to update Index : {}, {}", id, index);
        if (index.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, index.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indexRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        index = indexRepository.save(index);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, index.getId().toString()))
            .body(index);
    }

    /**
     * {@code PATCH  /indices/:id} : Partial updates given fields of an existing index, field will ignore if it is null
     *
     * @param id the id of the index to save.
     * @param index the index to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated index,
     * or with status {@code 400 (Bad Request)} if the index is not valid,
     * or with status {@code 404 (Not Found)} if the index is not found,
     * or with status {@code 500 (Internal Server Error)} if the index couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Index> partialUpdateIndex(@PathVariable(value = "id", required = false) final Long id, @RequestBody Index index)
        throws URISyntaxException {
        log.debug("REST request to partial update Index partially : {}, {}", id, index);
        if (index.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, index.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indexRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Index> result = indexRepository
            .findById(index.getId())
            .map(existingIndex -> {
                if (index.getIndexnaam() != null) {
                    existingIndex.setIndexnaam(index.getIndexnaam());
                }
                if (index.getIndexwaarde() != null) {
                    existingIndex.setIndexwaarde(index.getIndexwaarde());
                }

                return existingIndex;
            })
            .map(indexRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, index.getId().toString())
        );
    }

    /**
     * {@code GET  /indices} : get all the indices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indices in body.
     */
    @GetMapping("")
    public List<Index> getAllIndices() {
        log.debug("REST request to get all Indices");
        return indexRepository.findAll();
    }

    /**
     * {@code GET  /indices/:id} : get the "id" index.
     *
     * @param id the id of the index to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the index, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Index> getIndex(@PathVariable("id") Long id) {
        log.debug("REST request to get Index : {}", id);
        Optional<Index> index = indexRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(index);
    }

    /**
     * {@code DELETE  /indices/:id} : delete the "id" index.
     *
     * @param id the id of the index to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndex(@PathVariable("id") Long id) {
        log.debug("REST request to delete Index : {}", id);
        indexRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
