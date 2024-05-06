package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beperkingscategorie;
import nl.ritense.demo.repository.BeperkingscategorieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beperkingscategorie}.
 */
@RestController
@RequestMapping("/api/beperkingscategories")
@Transactional
public class BeperkingscategorieResource {

    private final Logger log = LoggerFactory.getLogger(BeperkingscategorieResource.class);

    private static final String ENTITY_NAME = "beperkingscategorie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeperkingscategorieRepository beperkingscategorieRepository;

    public BeperkingscategorieResource(BeperkingscategorieRepository beperkingscategorieRepository) {
        this.beperkingscategorieRepository = beperkingscategorieRepository;
    }

    /**
     * {@code POST  /beperkingscategories} : Create a new beperkingscategorie.
     *
     * @param beperkingscategorie the beperkingscategorie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beperkingscategorie, or with status {@code 400 (Bad Request)} if the beperkingscategorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beperkingscategorie> createBeperkingscategorie(@Valid @RequestBody Beperkingscategorie beperkingscategorie)
        throws URISyntaxException {
        log.debug("REST request to save Beperkingscategorie : {}", beperkingscategorie);
        if (beperkingscategorie.getId() != null) {
            throw new BadRequestAlertException("A new beperkingscategorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beperkingscategorie = beperkingscategorieRepository.save(beperkingscategorie);
        return ResponseEntity.created(new URI("/api/beperkingscategories/" + beperkingscategorie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beperkingscategorie.getId().toString()))
            .body(beperkingscategorie);
    }

    /**
     * {@code PUT  /beperkingscategories/:id} : Updates an existing beperkingscategorie.
     *
     * @param id the id of the beperkingscategorie to save.
     * @param beperkingscategorie the beperkingscategorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperkingscategorie,
     * or with status {@code 400 (Bad Request)} if the beperkingscategorie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beperkingscategorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beperkingscategorie> updateBeperkingscategorie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Beperkingscategorie beperkingscategorie
    ) throws URISyntaxException {
        log.debug("REST request to update Beperkingscategorie : {}, {}", id, beperkingscategorie);
        if (beperkingscategorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperkingscategorie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingscategorieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beperkingscategorie = beperkingscategorieRepository.save(beperkingscategorie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperkingscategorie.getId().toString()))
            .body(beperkingscategorie);
    }

    /**
     * {@code PATCH  /beperkingscategories/:id} : Partial updates given fields of an existing beperkingscategorie, field will ignore if it is null
     *
     * @param id the id of the beperkingscategorie to save.
     * @param beperkingscategorie the beperkingscategorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperkingscategorie,
     * or with status {@code 400 (Bad Request)} if the beperkingscategorie is not valid,
     * or with status {@code 404 (Not Found)} if the beperkingscategorie is not found,
     * or with status {@code 500 (Internal Server Error)} if the beperkingscategorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beperkingscategorie> partialUpdateBeperkingscategorie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Beperkingscategorie beperkingscategorie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beperkingscategorie partially : {}, {}", id, beperkingscategorie);
        if (beperkingscategorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperkingscategorie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingscategorieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beperkingscategorie> result = beperkingscategorieRepository
            .findById(beperkingscategorie.getId())
            .map(existingBeperkingscategorie -> {
                if (beperkingscategorie.getCode() != null) {
                    existingBeperkingscategorie.setCode(beperkingscategorie.getCode());
                }
                if (beperkingscategorie.getWet() != null) {
                    existingBeperkingscategorie.setWet(beperkingscategorie.getWet());
                }

                return existingBeperkingscategorie;
            })
            .map(beperkingscategorieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperkingscategorie.getId().toString())
        );
    }

    /**
     * {@code GET  /beperkingscategories} : get all the beperkingscategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beperkingscategories in body.
     */
    @GetMapping("")
    public List<Beperkingscategorie> getAllBeperkingscategories() {
        log.debug("REST request to get all Beperkingscategories");
        return beperkingscategorieRepository.findAll();
    }

    /**
     * {@code GET  /beperkingscategories/:id} : get the "id" beperkingscategorie.
     *
     * @param id the id of the beperkingscategorie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beperkingscategorie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beperkingscategorie> getBeperkingscategorie(@PathVariable("id") Long id) {
        log.debug("REST request to get Beperkingscategorie : {}", id);
        Optional<Beperkingscategorie> beperkingscategorie = beperkingscategorieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beperkingscategorie);
    }

    /**
     * {@code DELETE  /beperkingscategories/:id} : delete the "id" beperkingscategorie.
     *
     * @param id the id of the beperkingscategorie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeperkingscategorie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beperkingscategorie : {}", id);
        beperkingscategorieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
