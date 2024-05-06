package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Selectietabelaanbesteding;
import nl.ritense.demo.repository.SelectietabelaanbestedingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Selectietabelaanbesteding}.
 */
@RestController
@RequestMapping("/api/selectietabelaanbestedings")
@Transactional
public class SelectietabelaanbestedingResource {

    private final Logger log = LoggerFactory.getLogger(SelectietabelaanbestedingResource.class);

    private static final String ENTITY_NAME = "selectietabelaanbesteding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SelectietabelaanbestedingRepository selectietabelaanbestedingRepository;

    public SelectietabelaanbestedingResource(SelectietabelaanbestedingRepository selectietabelaanbestedingRepository) {
        this.selectietabelaanbestedingRepository = selectietabelaanbestedingRepository;
    }

    /**
     * {@code POST  /selectietabelaanbestedings} : Create a new selectietabelaanbesteding.
     *
     * @param selectietabelaanbesteding the selectietabelaanbesteding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new selectietabelaanbesteding, or with status {@code 400 (Bad Request)} if the selectietabelaanbesteding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Selectietabelaanbesteding> createSelectietabelaanbesteding(
        @RequestBody Selectietabelaanbesteding selectietabelaanbesteding
    ) throws URISyntaxException {
        log.debug("REST request to save Selectietabelaanbesteding : {}", selectietabelaanbesteding);
        if (selectietabelaanbesteding.getId() != null) {
            throw new BadRequestAlertException("A new selectietabelaanbesteding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        selectietabelaanbesteding = selectietabelaanbestedingRepository.save(selectietabelaanbesteding);
        return ResponseEntity.created(new URI("/api/selectietabelaanbestedings/" + selectietabelaanbesteding.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, selectietabelaanbesteding.getId().toString())
            )
            .body(selectietabelaanbesteding);
    }

    /**
     * {@code PUT  /selectietabelaanbestedings/:id} : Updates an existing selectietabelaanbesteding.
     *
     * @param id the id of the selectietabelaanbesteding to save.
     * @param selectietabelaanbesteding the selectietabelaanbesteding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selectietabelaanbesteding,
     * or with status {@code 400 (Bad Request)} if the selectietabelaanbesteding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the selectietabelaanbesteding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Selectietabelaanbesteding> updateSelectietabelaanbesteding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Selectietabelaanbesteding selectietabelaanbesteding
    ) throws URISyntaxException {
        log.debug("REST request to update Selectietabelaanbesteding : {}, {}", id, selectietabelaanbesteding);
        if (selectietabelaanbesteding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, selectietabelaanbesteding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!selectietabelaanbestedingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        selectietabelaanbesteding = selectietabelaanbestedingRepository.save(selectietabelaanbesteding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, selectietabelaanbesteding.getId().toString()))
            .body(selectietabelaanbesteding);
    }

    /**
     * {@code PATCH  /selectietabelaanbestedings/:id} : Partial updates given fields of an existing selectietabelaanbesteding, field will ignore if it is null
     *
     * @param id the id of the selectietabelaanbesteding to save.
     * @param selectietabelaanbesteding the selectietabelaanbesteding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selectietabelaanbesteding,
     * or with status {@code 400 (Bad Request)} if the selectietabelaanbesteding is not valid,
     * or with status {@code 404 (Not Found)} if the selectietabelaanbesteding is not found,
     * or with status {@code 500 (Internal Server Error)} if the selectietabelaanbesteding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Selectietabelaanbesteding> partialUpdateSelectietabelaanbesteding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Selectietabelaanbesteding selectietabelaanbesteding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Selectietabelaanbesteding partially : {}, {}", id, selectietabelaanbesteding);
        if (selectietabelaanbesteding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, selectietabelaanbesteding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!selectietabelaanbestedingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Selectietabelaanbesteding> result = selectietabelaanbestedingRepository
            .findById(selectietabelaanbesteding.getId())
            .map(existingSelectietabelaanbesteding -> {
                if (selectietabelaanbesteding.getAanbestedingsoort() != null) {
                    existingSelectietabelaanbesteding.setAanbestedingsoort(selectietabelaanbesteding.getAanbestedingsoort());
                }
                if (selectietabelaanbesteding.getDrempelbedragtot() != null) {
                    existingSelectietabelaanbesteding.setDrempelbedragtot(selectietabelaanbesteding.getDrempelbedragtot());
                }
                if (selectietabelaanbesteding.getDrempelbedragvanaf() != null) {
                    existingSelectietabelaanbesteding.setDrempelbedragvanaf(selectietabelaanbesteding.getDrempelbedragvanaf());
                }
                if (selectietabelaanbesteding.getOpdrachtcategorie() != null) {
                    existingSelectietabelaanbesteding.setOpdrachtcategorie(selectietabelaanbesteding.getOpdrachtcategorie());
                }
                if (selectietabelaanbesteding.getOpenbaar() != null) {
                    existingSelectietabelaanbesteding.setOpenbaar(selectietabelaanbesteding.getOpenbaar());
                }

                return existingSelectietabelaanbesteding;
            })
            .map(selectietabelaanbestedingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, selectietabelaanbesteding.getId().toString())
        );
    }

    /**
     * {@code GET  /selectietabelaanbestedings} : get all the selectietabelaanbestedings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selectietabelaanbestedings in body.
     */
    @GetMapping("")
    public List<Selectietabelaanbesteding> getAllSelectietabelaanbestedings() {
        log.debug("REST request to get all Selectietabelaanbestedings");
        return selectietabelaanbestedingRepository.findAll();
    }

    /**
     * {@code GET  /selectietabelaanbestedings/:id} : get the "id" selectietabelaanbesteding.
     *
     * @param id the id of the selectietabelaanbesteding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the selectietabelaanbesteding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Selectietabelaanbesteding> getSelectietabelaanbesteding(@PathVariable("id") Long id) {
        log.debug("REST request to get Selectietabelaanbesteding : {}", id);
        Optional<Selectietabelaanbesteding> selectietabelaanbesteding = selectietabelaanbestedingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(selectietabelaanbesteding);
    }

    /**
     * {@code DELETE  /selectietabelaanbestedings/:id} : delete the "id" selectietabelaanbesteding.
     *
     * @param id the id of the selectietabelaanbesteding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSelectietabelaanbesteding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Selectietabelaanbesteding : {}", id);
        selectietabelaanbestedingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
