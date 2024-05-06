package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beperkingsgebied;
import nl.ritense.demo.repository.BeperkingsgebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beperkingsgebied}.
 */
@RestController
@RequestMapping("/api/beperkingsgebieds")
@Transactional
public class BeperkingsgebiedResource {

    private final Logger log = LoggerFactory.getLogger(BeperkingsgebiedResource.class);

    private static final String ENTITY_NAME = "beperkingsgebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeperkingsgebiedRepository beperkingsgebiedRepository;

    public BeperkingsgebiedResource(BeperkingsgebiedRepository beperkingsgebiedRepository) {
        this.beperkingsgebiedRepository = beperkingsgebiedRepository;
    }

    /**
     * {@code POST  /beperkingsgebieds} : Create a new beperkingsgebied.
     *
     * @param beperkingsgebied the beperkingsgebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beperkingsgebied, or with status {@code 400 (Bad Request)} if the beperkingsgebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beperkingsgebied> createBeperkingsgebied(@RequestBody Beperkingsgebied beperkingsgebied)
        throws URISyntaxException {
        log.debug("REST request to save Beperkingsgebied : {}", beperkingsgebied);
        if (beperkingsgebied.getId() != null) {
            throw new BadRequestAlertException("A new beperkingsgebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beperkingsgebied = beperkingsgebiedRepository.save(beperkingsgebied);
        return ResponseEntity.created(new URI("/api/beperkingsgebieds/" + beperkingsgebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beperkingsgebied.getId().toString()))
            .body(beperkingsgebied);
    }

    /**
     * {@code PUT  /beperkingsgebieds/:id} : Updates an existing beperkingsgebied.
     *
     * @param id the id of the beperkingsgebied to save.
     * @param beperkingsgebied the beperkingsgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperkingsgebied,
     * or with status {@code 400 (Bad Request)} if the beperkingsgebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beperkingsgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beperkingsgebied> updateBeperkingsgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beperkingsgebied beperkingsgebied
    ) throws URISyntaxException {
        log.debug("REST request to update Beperkingsgebied : {}, {}", id, beperkingsgebied);
        if (beperkingsgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperkingsgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingsgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beperkingsgebied = beperkingsgebiedRepository.save(beperkingsgebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperkingsgebied.getId().toString()))
            .body(beperkingsgebied);
    }

    /**
     * {@code PATCH  /beperkingsgebieds/:id} : Partial updates given fields of an existing beperkingsgebied, field will ignore if it is null
     *
     * @param id the id of the beperkingsgebied to save.
     * @param beperkingsgebied the beperkingsgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperkingsgebied,
     * or with status {@code 400 (Bad Request)} if the beperkingsgebied is not valid,
     * or with status {@code 404 (Not Found)} if the beperkingsgebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the beperkingsgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beperkingsgebied> partialUpdateBeperkingsgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beperkingsgebied beperkingsgebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beperkingsgebied partially : {}, {}", id, beperkingsgebied);
        if (beperkingsgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperkingsgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingsgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beperkingsgebied> result = beperkingsgebiedRepository
            .findById(beperkingsgebied.getId())
            .map(existingBeperkingsgebied -> {
                if (beperkingsgebied.getGroep() != null) {
                    existingBeperkingsgebied.setGroep(beperkingsgebied.getGroep());
                }
                if (beperkingsgebied.getNaam() != null) {
                    existingBeperkingsgebied.setNaam(beperkingsgebied.getNaam());
                }

                return existingBeperkingsgebied;
            })
            .map(beperkingsgebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperkingsgebied.getId().toString())
        );
    }

    /**
     * {@code GET  /beperkingsgebieds} : get all the beperkingsgebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beperkingsgebieds in body.
     */
    @GetMapping("")
    public List<Beperkingsgebied> getAllBeperkingsgebieds() {
        log.debug("REST request to get all Beperkingsgebieds");
        return beperkingsgebiedRepository.findAll();
    }

    /**
     * {@code GET  /beperkingsgebieds/:id} : get the "id" beperkingsgebied.
     *
     * @param id the id of the beperkingsgebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beperkingsgebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beperkingsgebied> getBeperkingsgebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Beperkingsgebied : {}", id);
        Optional<Beperkingsgebied> beperkingsgebied = beperkingsgebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beperkingsgebied);
    }

    /**
     * {@code DELETE  /beperkingsgebieds/:id} : delete the "id" beperkingsgebied.
     *
     * @param id the id of the beperkingsgebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeperkingsgebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beperkingsgebied : {}", id);
        beperkingsgebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
