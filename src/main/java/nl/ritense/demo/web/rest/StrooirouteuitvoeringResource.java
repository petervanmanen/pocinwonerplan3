package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Strooirouteuitvoering;
import nl.ritense.demo.repository.StrooirouteuitvoeringRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Strooirouteuitvoering}.
 */
@RestController
@RequestMapping("/api/strooirouteuitvoerings")
@Transactional
public class StrooirouteuitvoeringResource {

    private final Logger log = LoggerFactory.getLogger(StrooirouteuitvoeringResource.class);

    private static final String ENTITY_NAME = "strooirouteuitvoering";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrooirouteuitvoeringRepository strooirouteuitvoeringRepository;

    public StrooirouteuitvoeringResource(StrooirouteuitvoeringRepository strooirouteuitvoeringRepository) {
        this.strooirouteuitvoeringRepository = strooirouteuitvoeringRepository;
    }

    /**
     * {@code POST  /strooirouteuitvoerings} : Create a new strooirouteuitvoering.
     *
     * @param strooirouteuitvoering the strooirouteuitvoering to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strooirouteuitvoering, or with status {@code 400 (Bad Request)} if the strooirouteuitvoering has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Strooirouteuitvoering> createStrooirouteuitvoering(@RequestBody Strooirouteuitvoering strooirouteuitvoering)
        throws URISyntaxException {
        log.debug("REST request to save Strooirouteuitvoering : {}", strooirouteuitvoering);
        if (strooirouteuitvoering.getId() != null) {
            throw new BadRequestAlertException("A new strooirouteuitvoering cannot already have an ID", ENTITY_NAME, "idexists");
        }
        strooirouteuitvoering = strooirouteuitvoeringRepository.save(strooirouteuitvoering);
        return ResponseEntity.created(new URI("/api/strooirouteuitvoerings/" + strooirouteuitvoering.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, strooirouteuitvoering.getId().toString()))
            .body(strooirouteuitvoering);
    }

    /**
     * {@code PUT  /strooirouteuitvoerings/:id} : Updates an existing strooirouteuitvoering.
     *
     * @param id the id of the strooirouteuitvoering to save.
     * @param strooirouteuitvoering the strooirouteuitvoering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strooirouteuitvoering,
     * or with status {@code 400 (Bad Request)} if the strooirouteuitvoering is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strooirouteuitvoering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Strooirouteuitvoering> updateStrooirouteuitvoering(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Strooirouteuitvoering strooirouteuitvoering
    ) throws URISyntaxException {
        log.debug("REST request to update Strooirouteuitvoering : {}, {}", id, strooirouteuitvoering);
        if (strooirouteuitvoering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strooirouteuitvoering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strooirouteuitvoeringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        strooirouteuitvoering = strooirouteuitvoeringRepository.save(strooirouteuitvoering);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, strooirouteuitvoering.getId().toString()))
            .body(strooirouteuitvoering);
    }

    /**
     * {@code PATCH  /strooirouteuitvoerings/:id} : Partial updates given fields of an existing strooirouteuitvoering, field will ignore if it is null
     *
     * @param id the id of the strooirouteuitvoering to save.
     * @param strooirouteuitvoering the strooirouteuitvoering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strooirouteuitvoering,
     * or with status {@code 400 (Bad Request)} if the strooirouteuitvoering is not valid,
     * or with status {@code 404 (Not Found)} if the strooirouteuitvoering is not found,
     * or with status {@code 500 (Internal Server Error)} if the strooirouteuitvoering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Strooirouteuitvoering> partialUpdateStrooirouteuitvoering(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Strooirouteuitvoering strooirouteuitvoering
    ) throws URISyntaxException {
        log.debug("REST request to partial update Strooirouteuitvoering partially : {}, {}", id, strooirouteuitvoering);
        if (strooirouteuitvoering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strooirouteuitvoering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strooirouteuitvoeringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Strooirouteuitvoering> result = strooirouteuitvoeringRepository
            .findById(strooirouteuitvoering.getId())
            .map(existingStrooirouteuitvoering -> {
                if (strooirouteuitvoering.getGeplandeinde() != null) {
                    existingStrooirouteuitvoering.setGeplandeinde(strooirouteuitvoering.getGeplandeinde());
                }
                if (strooirouteuitvoering.getGeplandstart() != null) {
                    existingStrooirouteuitvoering.setGeplandstart(strooirouteuitvoering.getGeplandstart());
                }
                if (strooirouteuitvoering.getEroute() != null) {
                    existingStrooirouteuitvoering.setEroute(strooirouteuitvoering.getEroute());
                }
                if (strooirouteuitvoering.getWerkelijkeinde() != null) {
                    existingStrooirouteuitvoering.setWerkelijkeinde(strooirouteuitvoering.getWerkelijkeinde());
                }
                if (strooirouteuitvoering.getWerkelijkestart() != null) {
                    existingStrooirouteuitvoering.setWerkelijkestart(strooirouteuitvoering.getWerkelijkestart());
                }

                return existingStrooirouteuitvoering;
            })
            .map(strooirouteuitvoeringRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, strooirouteuitvoering.getId().toString())
        );
    }

    /**
     * {@code GET  /strooirouteuitvoerings} : get all the strooirouteuitvoerings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strooirouteuitvoerings in body.
     */
    @GetMapping("")
    public List<Strooirouteuitvoering> getAllStrooirouteuitvoerings() {
        log.debug("REST request to get all Strooirouteuitvoerings");
        return strooirouteuitvoeringRepository.findAll();
    }

    /**
     * {@code GET  /strooirouteuitvoerings/:id} : get the "id" strooirouteuitvoering.
     *
     * @param id the id of the strooirouteuitvoering to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strooirouteuitvoering, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Strooirouteuitvoering> getStrooirouteuitvoering(@PathVariable("id") Long id) {
        log.debug("REST request to get Strooirouteuitvoering : {}", id);
        Optional<Strooirouteuitvoering> strooirouteuitvoering = strooirouteuitvoeringRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(strooirouteuitvoering);
    }

    /**
     * {@code DELETE  /strooirouteuitvoerings/:id} : delete the "id" strooirouteuitvoering.
     *
     * @param id the id of the strooirouteuitvoering to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrooirouteuitvoering(@PathVariable("id") Long id) {
        log.debug("REST request to delete Strooirouteuitvoering : {}", id);
        strooirouteuitvoeringRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
