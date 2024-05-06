package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Thema;
import nl.ritense.demo.repository.ThemaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Thema}.
 */
@RestController
@RequestMapping("/api/themas")
@Transactional
public class ThemaResource {

    private final Logger log = LoggerFactory.getLogger(ThemaResource.class);

    private static final String ENTITY_NAME = "thema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemaRepository themaRepository;

    public ThemaResource(ThemaRepository themaRepository) {
        this.themaRepository = themaRepository;
    }

    /**
     * {@code POST  /themas} : Create a new thema.
     *
     * @param thema the thema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thema, or with status {@code 400 (Bad Request)} if the thema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Thema> createThema(@RequestBody Thema thema) throws URISyntaxException {
        log.debug("REST request to save Thema : {}", thema);
        if (thema.getId() != null) {
            throw new BadRequestAlertException("A new thema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        thema = themaRepository.save(thema);
        return ResponseEntity.created(new URI("/api/themas/" + thema.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, thema.getId().toString()))
            .body(thema);
    }

    /**
     * {@code PUT  /themas/:id} : Updates an existing thema.
     *
     * @param id the id of the thema to save.
     * @param thema the thema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thema,
     * or with status {@code 400 (Bad Request)} if the thema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Thema> updateThema(@PathVariable(value = "id", required = false) final Long id, @RequestBody Thema thema)
        throws URISyntaxException {
        log.debug("REST request to update Thema : {}, {}", id, thema);
        if (thema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!themaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        thema = themaRepository.save(thema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thema.getId().toString()))
            .body(thema);
    }

    /**
     * {@code PATCH  /themas/:id} : Partial updates given fields of an existing thema, field will ignore if it is null
     *
     * @param id the id of the thema to save.
     * @param thema the thema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thema,
     * or with status {@code 400 (Bad Request)} if the thema is not valid,
     * or with status {@code 404 (Not Found)} if the thema is not found,
     * or with status {@code 500 (Internal Server Error)} if the thema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Thema> partialUpdateThema(@PathVariable(value = "id", required = false) final Long id, @RequestBody Thema thema)
        throws URISyntaxException {
        log.debug("REST request to partial update Thema partially : {}, {}", id, thema);
        if (thema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!themaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thema> result = themaRepository
            .findById(thema.getId())
            .map(existingThema -> {
                if (thema.getNaam() != null) {
                    existingThema.setNaam(thema.getNaam());
                }
                if (thema.getOmschrijving() != null) {
                    existingThema.setOmschrijving(thema.getOmschrijving());
                }

                return existingThema;
            })
            .map(themaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thema.getId().toString())
        );
    }

    /**
     * {@code GET  /themas} : get all the themas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themas in body.
     */
    @GetMapping("")
    public List<Thema> getAllThemas() {
        log.debug("REST request to get all Themas");
        return themaRepository.findAll();
    }

    /**
     * {@code GET  /themas/:id} : get the "id" thema.
     *
     * @param id the id of the thema to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thema, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Thema> getThema(@PathVariable("id") Long id) {
        log.debug("REST request to get Thema : {}", id);
        Optional<Thema> thema = themaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thema);
    }

    /**
     * {@code DELETE  /themas/:id} : delete the "id" thema.
     *
     * @param id the id of the thema to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThema(@PathVariable("id") Long id) {
        log.debug("REST request to delete Thema : {}", id);
        themaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
