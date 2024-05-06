package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Magazijnplaatsing;
import nl.ritense.demo.repository.MagazijnplaatsingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Magazijnplaatsing}.
 */
@RestController
@RequestMapping("/api/magazijnplaatsings")
@Transactional
public class MagazijnplaatsingResource {

    private final Logger log = LoggerFactory.getLogger(MagazijnplaatsingResource.class);

    private static final String ENTITY_NAME = "magazijnplaatsing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MagazijnplaatsingRepository magazijnplaatsingRepository;

    public MagazijnplaatsingResource(MagazijnplaatsingRepository magazijnplaatsingRepository) {
        this.magazijnplaatsingRepository = magazijnplaatsingRepository;
    }

    /**
     * {@code POST  /magazijnplaatsings} : Create a new magazijnplaatsing.
     *
     * @param magazijnplaatsing the magazijnplaatsing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new magazijnplaatsing, or with status {@code 400 (Bad Request)} if the magazijnplaatsing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Magazijnplaatsing> createMagazijnplaatsing(@RequestBody Magazijnplaatsing magazijnplaatsing)
        throws URISyntaxException {
        log.debug("REST request to save Magazijnplaatsing : {}", magazijnplaatsing);
        if (magazijnplaatsing.getId() != null) {
            throw new BadRequestAlertException("A new magazijnplaatsing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        magazijnplaatsing = magazijnplaatsingRepository.save(magazijnplaatsing);
        return ResponseEntity.created(new URI("/api/magazijnplaatsings/" + magazijnplaatsing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, magazijnplaatsing.getId().toString()))
            .body(magazijnplaatsing);
    }

    /**
     * {@code PUT  /magazijnplaatsings/:id} : Updates an existing magazijnplaatsing.
     *
     * @param id the id of the magazijnplaatsing to save.
     * @param magazijnplaatsing the magazijnplaatsing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magazijnplaatsing,
     * or with status {@code 400 (Bad Request)} if the magazijnplaatsing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the magazijnplaatsing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Magazijnplaatsing> updateMagazijnplaatsing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Magazijnplaatsing magazijnplaatsing
    ) throws URISyntaxException {
        log.debug("REST request to update Magazijnplaatsing : {}, {}", id, magazijnplaatsing);
        if (magazijnplaatsing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magazijnplaatsing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magazijnplaatsingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        magazijnplaatsing = magazijnplaatsingRepository.save(magazijnplaatsing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magazijnplaatsing.getId().toString()))
            .body(magazijnplaatsing);
    }

    /**
     * {@code PATCH  /magazijnplaatsings/:id} : Partial updates given fields of an existing magazijnplaatsing, field will ignore if it is null
     *
     * @param id the id of the magazijnplaatsing to save.
     * @param magazijnplaatsing the magazijnplaatsing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magazijnplaatsing,
     * or with status {@code 400 (Bad Request)} if the magazijnplaatsing is not valid,
     * or with status {@code 404 (Not Found)} if the magazijnplaatsing is not found,
     * or with status {@code 500 (Internal Server Error)} if the magazijnplaatsing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Magazijnplaatsing> partialUpdateMagazijnplaatsing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Magazijnplaatsing magazijnplaatsing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Magazijnplaatsing partially : {}, {}", id, magazijnplaatsing);
        if (magazijnplaatsing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magazijnplaatsing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magazijnplaatsingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Magazijnplaatsing> result = magazijnplaatsingRepository
            .findById(magazijnplaatsing.getId())
            .map(existingMagazijnplaatsing -> {
                if (magazijnplaatsing.getBeschrijving() != null) {
                    existingMagazijnplaatsing.setBeschrijving(magazijnplaatsing.getBeschrijving());
                }
                if (magazijnplaatsing.getDatumgeplaatst() != null) {
                    existingMagazijnplaatsing.setDatumgeplaatst(magazijnplaatsing.getDatumgeplaatst());
                }
                if (magazijnplaatsing.getHerkomst() != null) {
                    existingMagazijnplaatsing.setHerkomst(magazijnplaatsing.getHerkomst());
                }
                if (magazijnplaatsing.getKey() != null) {
                    existingMagazijnplaatsing.setKey(magazijnplaatsing.getKey());
                }
                if (magazijnplaatsing.getKeydoos() != null) {
                    existingMagazijnplaatsing.setKeydoos(magazijnplaatsing.getKeydoos());
                }
                if (magazijnplaatsing.getKeymagazijnlocatie() != null) {
                    existingMagazijnplaatsing.setKeymagazijnlocatie(magazijnplaatsing.getKeymagazijnlocatie());
                }
                if (magazijnplaatsing.getProjectcd() != null) {
                    existingMagazijnplaatsing.setProjectcd(magazijnplaatsing.getProjectcd());
                }
                if (magazijnplaatsing.getUitgeleend() != null) {
                    existingMagazijnplaatsing.setUitgeleend(magazijnplaatsing.getUitgeleend());
                }

                return existingMagazijnplaatsing;
            })
            .map(magazijnplaatsingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magazijnplaatsing.getId().toString())
        );
    }

    /**
     * {@code GET  /magazijnplaatsings} : get all the magazijnplaatsings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of magazijnplaatsings in body.
     */
    @GetMapping("")
    public List<Magazijnplaatsing> getAllMagazijnplaatsings() {
        log.debug("REST request to get all Magazijnplaatsings");
        return magazijnplaatsingRepository.findAll();
    }

    /**
     * {@code GET  /magazijnplaatsings/:id} : get the "id" magazijnplaatsing.
     *
     * @param id the id of the magazijnplaatsing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the magazijnplaatsing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Magazijnplaatsing> getMagazijnplaatsing(@PathVariable("id") Long id) {
        log.debug("REST request to get Magazijnplaatsing : {}", id);
        Optional<Magazijnplaatsing> magazijnplaatsing = magazijnplaatsingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(magazijnplaatsing);
    }

    /**
     * {@code DELETE  /magazijnplaatsings/:id} : delete the "id" magazijnplaatsing.
     *
     * @param id the id of the magazijnplaatsing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMagazijnplaatsing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Magazijnplaatsing : {}", id);
        magazijnplaatsingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
