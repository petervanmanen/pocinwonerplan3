package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Toepasbareregelbestand;
import nl.ritense.demo.repository.ToepasbareregelbestandRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Toepasbareregelbestand}.
 */
@RestController
@RequestMapping("/api/toepasbareregelbestands")
@Transactional
public class ToepasbareregelbestandResource {

    private final Logger log = LoggerFactory.getLogger(ToepasbareregelbestandResource.class);

    private static final String ENTITY_NAME = "toepasbareregelbestand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ToepasbareregelbestandRepository toepasbareregelbestandRepository;

    public ToepasbareregelbestandResource(ToepasbareregelbestandRepository toepasbareregelbestandRepository) {
        this.toepasbareregelbestandRepository = toepasbareregelbestandRepository;
    }

    /**
     * {@code POST  /toepasbareregelbestands} : Create a new toepasbareregelbestand.
     *
     * @param toepasbareregelbestand the toepasbareregelbestand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new toepasbareregelbestand, or with status {@code 400 (Bad Request)} if the toepasbareregelbestand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Toepasbareregelbestand> createToepasbareregelbestand(@RequestBody Toepasbareregelbestand toepasbareregelbestand)
        throws URISyntaxException {
        log.debug("REST request to save Toepasbareregelbestand : {}", toepasbareregelbestand);
        if (toepasbareregelbestand.getId() != null) {
            throw new BadRequestAlertException("A new toepasbareregelbestand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        toepasbareregelbestand = toepasbareregelbestandRepository.save(toepasbareregelbestand);
        return ResponseEntity.created(new URI("/api/toepasbareregelbestands/" + toepasbareregelbestand.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, toepasbareregelbestand.getId().toString()))
            .body(toepasbareregelbestand);
    }

    /**
     * {@code PUT  /toepasbareregelbestands/:id} : Updates an existing toepasbareregelbestand.
     *
     * @param id the id of the toepasbareregelbestand to save.
     * @param toepasbareregelbestand the toepasbareregelbestand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated toepasbareregelbestand,
     * or with status {@code 400 (Bad Request)} if the toepasbareregelbestand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the toepasbareregelbestand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Toepasbareregelbestand> updateToepasbareregelbestand(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Toepasbareregelbestand toepasbareregelbestand
    ) throws URISyntaxException {
        log.debug("REST request to update Toepasbareregelbestand : {}, {}", id, toepasbareregelbestand);
        if (toepasbareregelbestand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, toepasbareregelbestand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!toepasbareregelbestandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        toepasbareregelbestand = toepasbareregelbestandRepository.save(toepasbareregelbestand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, toepasbareregelbestand.getId().toString()))
            .body(toepasbareregelbestand);
    }

    /**
     * {@code PATCH  /toepasbareregelbestands/:id} : Partial updates given fields of an existing toepasbareregelbestand, field will ignore if it is null
     *
     * @param id the id of the toepasbareregelbestand to save.
     * @param toepasbareregelbestand the toepasbareregelbestand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated toepasbareregelbestand,
     * or with status {@code 400 (Bad Request)} if the toepasbareregelbestand is not valid,
     * or with status {@code 404 (Not Found)} if the toepasbareregelbestand is not found,
     * or with status {@code 500 (Internal Server Error)} if the toepasbareregelbestand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Toepasbareregelbestand> partialUpdateToepasbareregelbestand(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Toepasbareregelbestand toepasbareregelbestand
    ) throws URISyntaxException {
        log.debug("REST request to partial update Toepasbareregelbestand partially : {}, {}", id, toepasbareregelbestand);
        if (toepasbareregelbestand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, toepasbareregelbestand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!toepasbareregelbestandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Toepasbareregelbestand> result = toepasbareregelbestandRepository
            .findById(toepasbareregelbestand.getId())
            .map(existingToepasbareregelbestand -> {
                if (toepasbareregelbestand.getDatumeindegeldigheid() != null) {
                    existingToepasbareregelbestand.setDatumeindegeldigheid(toepasbareregelbestand.getDatumeindegeldigheid());
                }
                if (toepasbareregelbestand.getDatumstart() != null) {
                    existingToepasbareregelbestand.setDatumstart(toepasbareregelbestand.getDatumstart());
                }

                return existingToepasbareregelbestand;
            })
            .map(toepasbareregelbestandRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, toepasbareregelbestand.getId().toString())
        );
    }

    /**
     * {@code GET  /toepasbareregelbestands} : get all the toepasbareregelbestands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of toepasbareregelbestands in body.
     */
    @GetMapping("")
    public List<Toepasbareregelbestand> getAllToepasbareregelbestands() {
        log.debug("REST request to get all Toepasbareregelbestands");
        return toepasbareregelbestandRepository.findAll();
    }

    /**
     * {@code GET  /toepasbareregelbestands/:id} : get the "id" toepasbareregelbestand.
     *
     * @param id the id of the toepasbareregelbestand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the toepasbareregelbestand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Toepasbareregelbestand> getToepasbareregelbestand(@PathVariable("id") Long id) {
        log.debug("REST request to get Toepasbareregelbestand : {}", id);
        Optional<Toepasbareregelbestand> toepasbareregelbestand = toepasbareregelbestandRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(toepasbareregelbestand);
    }

    /**
     * {@code DELETE  /toepasbareregelbestands/:id} : delete the "id" toepasbareregelbestand.
     *
     * @param id the id of the toepasbareregelbestand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToepasbareregelbestand(@PathVariable("id") Long id) {
        log.debug("REST request to delete Toepasbareregelbestand : {}", id);
        toepasbareregelbestandRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
