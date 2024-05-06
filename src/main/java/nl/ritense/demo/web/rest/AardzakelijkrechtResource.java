package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aardzakelijkrecht;
import nl.ritense.demo.repository.AardzakelijkrechtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aardzakelijkrecht}.
 */
@RestController
@RequestMapping("/api/aardzakelijkrechts")
@Transactional
public class AardzakelijkrechtResource {

    private final Logger log = LoggerFactory.getLogger(AardzakelijkrechtResource.class);

    private static final String ENTITY_NAME = "aardzakelijkrecht";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AardzakelijkrechtRepository aardzakelijkrechtRepository;

    public AardzakelijkrechtResource(AardzakelijkrechtRepository aardzakelijkrechtRepository) {
        this.aardzakelijkrechtRepository = aardzakelijkrechtRepository;
    }

    /**
     * {@code POST  /aardzakelijkrechts} : Create a new aardzakelijkrecht.
     *
     * @param aardzakelijkrecht the aardzakelijkrecht to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aardzakelijkrecht, or with status {@code 400 (Bad Request)} if the aardzakelijkrecht has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aardzakelijkrecht> createAardzakelijkrecht(@RequestBody Aardzakelijkrecht aardzakelijkrecht)
        throws URISyntaxException {
        log.debug("REST request to save Aardzakelijkrecht : {}", aardzakelijkrecht);
        if (aardzakelijkrecht.getId() != null) {
            throw new BadRequestAlertException("A new aardzakelijkrecht cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aardzakelijkrecht = aardzakelijkrechtRepository.save(aardzakelijkrecht);
        return ResponseEntity.created(new URI("/api/aardzakelijkrechts/" + aardzakelijkrecht.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aardzakelijkrecht.getId().toString()))
            .body(aardzakelijkrecht);
    }

    /**
     * {@code PUT  /aardzakelijkrechts/:id} : Updates an existing aardzakelijkrecht.
     *
     * @param id the id of the aardzakelijkrecht to save.
     * @param aardzakelijkrecht the aardzakelijkrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aardzakelijkrecht,
     * or with status {@code 400 (Bad Request)} if the aardzakelijkrecht is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aardzakelijkrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aardzakelijkrecht> updateAardzakelijkrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aardzakelijkrecht aardzakelijkrecht
    ) throws URISyntaxException {
        log.debug("REST request to update Aardzakelijkrecht : {}, {}", id, aardzakelijkrecht);
        if (aardzakelijkrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aardzakelijkrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aardzakelijkrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aardzakelijkrecht = aardzakelijkrechtRepository.save(aardzakelijkrecht);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aardzakelijkrecht.getId().toString()))
            .body(aardzakelijkrecht);
    }

    /**
     * {@code PATCH  /aardzakelijkrechts/:id} : Partial updates given fields of an existing aardzakelijkrecht, field will ignore if it is null
     *
     * @param id the id of the aardzakelijkrecht to save.
     * @param aardzakelijkrecht the aardzakelijkrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aardzakelijkrecht,
     * or with status {@code 400 (Bad Request)} if the aardzakelijkrecht is not valid,
     * or with status {@code 404 (Not Found)} if the aardzakelijkrecht is not found,
     * or with status {@code 500 (Internal Server Error)} if the aardzakelijkrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aardzakelijkrecht> partialUpdateAardzakelijkrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aardzakelijkrecht aardzakelijkrecht
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aardzakelijkrecht partially : {}, {}", id, aardzakelijkrecht);
        if (aardzakelijkrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aardzakelijkrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aardzakelijkrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aardzakelijkrecht> result = aardzakelijkrechtRepository
            .findById(aardzakelijkrecht.getId())
            .map(existingAardzakelijkrecht -> {
                if (aardzakelijkrecht.getCodeaardzakelijkrecht() != null) {
                    existingAardzakelijkrecht.setCodeaardzakelijkrecht(aardzakelijkrecht.getCodeaardzakelijkrecht());
                }
                if (aardzakelijkrecht.getDatumbegingeldigheidaardzakelijkrecht() != null) {
                    existingAardzakelijkrecht.setDatumbegingeldigheidaardzakelijkrecht(
                        aardzakelijkrecht.getDatumbegingeldigheidaardzakelijkrecht()
                    );
                }
                if (aardzakelijkrecht.getDatumeindegeldigheidaardzakelijkrecht() != null) {
                    existingAardzakelijkrecht.setDatumeindegeldigheidaardzakelijkrecht(
                        aardzakelijkrecht.getDatumeindegeldigheidaardzakelijkrecht()
                    );
                }
                if (aardzakelijkrecht.getNaamaardzakelijkrecht() != null) {
                    existingAardzakelijkrecht.setNaamaardzakelijkrecht(aardzakelijkrecht.getNaamaardzakelijkrecht());
                }

                return existingAardzakelijkrecht;
            })
            .map(aardzakelijkrechtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aardzakelijkrecht.getId().toString())
        );
    }

    /**
     * {@code GET  /aardzakelijkrechts} : get all the aardzakelijkrechts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aardzakelijkrechts in body.
     */
    @GetMapping("")
    public List<Aardzakelijkrecht> getAllAardzakelijkrechts() {
        log.debug("REST request to get all Aardzakelijkrechts");
        return aardzakelijkrechtRepository.findAll();
    }

    /**
     * {@code GET  /aardzakelijkrechts/:id} : get the "id" aardzakelijkrecht.
     *
     * @param id the id of the aardzakelijkrecht to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aardzakelijkrecht, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aardzakelijkrecht> getAardzakelijkrecht(@PathVariable("id") Long id) {
        log.debug("REST request to get Aardzakelijkrecht : {}", id);
        Optional<Aardzakelijkrecht> aardzakelijkrecht = aardzakelijkrechtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aardzakelijkrecht);
    }

    /**
     * {@code DELETE  /aardzakelijkrechts/:id} : delete the "id" aardzakelijkrecht.
     *
     * @param id the id of the aardzakelijkrecht to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAardzakelijkrecht(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aardzakelijkrecht : {}", id);
        aardzakelijkrechtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
