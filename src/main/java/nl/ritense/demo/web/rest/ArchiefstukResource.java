package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Archiefstuk;
import nl.ritense.demo.repository.ArchiefstukRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Archiefstuk}.
 */
@RestController
@RequestMapping("/api/archiefstuks")
@Transactional
public class ArchiefstukResource {

    private final Logger log = LoggerFactory.getLogger(ArchiefstukResource.class);

    private static final String ENTITY_NAME = "archiefstuk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArchiefstukRepository archiefstukRepository;

    public ArchiefstukResource(ArchiefstukRepository archiefstukRepository) {
        this.archiefstukRepository = archiefstukRepository;
    }

    /**
     * {@code POST  /archiefstuks} : Create a new archiefstuk.
     *
     * @param archiefstuk the archiefstuk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new archiefstuk, or with status {@code 400 (Bad Request)} if the archiefstuk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Archiefstuk> createArchiefstuk(@Valid @RequestBody Archiefstuk archiefstuk) throws URISyntaxException {
        log.debug("REST request to save Archiefstuk : {}", archiefstuk);
        if (archiefstuk.getId() != null) {
            throw new BadRequestAlertException("A new archiefstuk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        archiefstuk = archiefstukRepository.save(archiefstuk);
        return ResponseEntity.created(new URI("/api/archiefstuks/" + archiefstuk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, archiefstuk.getId().toString()))
            .body(archiefstuk);
    }

    /**
     * {@code PUT  /archiefstuks/:id} : Updates an existing archiefstuk.
     *
     * @param id the id of the archiefstuk to save.
     * @param archiefstuk the archiefstuk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archiefstuk,
     * or with status {@code 400 (Bad Request)} if the archiefstuk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the archiefstuk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Archiefstuk> updateArchiefstuk(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Archiefstuk archiefstuk
    ) throws URISyntaxException {
        log.debug("REST request to update Archiefstuk : {}, {}", id, archiefstuk);
        if (archiefstuk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archiefstuk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archiefstukRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        archiefstuk = archiefstukRepository.save(archiefstuk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archiefstuk.getId().toString()))
            .body(archiefstuk);
    }

    /**
     * {@code PATCH  /archiefstuks/:id} : Partial updates given fields of an existing archiefstuk, field will ignore if it is null
     *
     * @param id the id of the archiefstuk to save.
     * @param archiefstuk the archiefstuk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archiefstuk,
     * or with status {@code 400 (Bad Request)} if the archiefstuk is not valid,
     * or with status {@code 404 (Not Found)} if the archiefstuk is not found,
     * or with status {@code 500 (Internal Server Error)} if the archiefstuk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Archiefstuk> partialUpdateArchiefstuk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Archiefstuk archiefstuk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Archiefstuk partially : {}, {}", id, archiefstuk);
        if (archiefstuk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archiefstuk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archiefstukRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Archiefstuk> result = archiefstukRepository
            .findById(archiefstuk.getId())
            .map(existingArchiefstuk -> {
                if (archiefstuk.getBeschrijving() != null) {
                    existingArchiefstuk.setBeschrijving(archiefstuk.getBeschrijving());
                }
                if (archiefstuk.getInventarisnummer() != null) {
                    existingArchiefstuk.setInventarisnummer(archiefstuk.getInventarisnummer());
                }
                if (archiefstuk.getOmvang() != null) {
                    existingArchiefstuk.setOmvang(archiefstuk.getOmvang());
                }
                if (archiefstuk.getOpenbaarheidsbeperking() != null) {
                    existingArchiefstuk.setOpenbaarheidsbeperking(archiefstuk.getOpenbaarheidsbeperking());
                }
                if (archiefstuk.getTrefwoorden() != null) {
                    existingArchiefstuk.setTrefwoorden(archiefstuk.getTrefwoorden());
                }
                if (archiefstuk.getUiterlijkevorm() != null) {
                    existingArchiefstuk.setUiterlijkevorm(archiefstuk.getUiterlijkevorm());
                }

                return existingArchiefstuk;
            })
            .map(archiefstukRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archiefstuk.getId().toString())
        );
    }

    /**
     * {@code GET  /archiefstuks} : get all the archiefstuks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of archiefstuks in body.
     */
    @GetMapping("")
    public List<Archiefstuk> getAllArchiefstuks(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Archiefstuks");
        if (eagerload) {
            return archiefstukRepository.findAllWithEagerRelationships();
        } else {
            return archiefstukRepository.findAll();
        }
    }

    /**
     * {@code GET  /archiefstuks/:id} : get the "id" archiefstuk.
     *
     * @param id the id of the archiefstuk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the archiefstuk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Archiefstuk> getArchiefstuk(@PathVariable("id") Long id) {
        log.debug("REST request to get Archiefstuk : {}", id);
        Optional<Archiefstuk> archiefstuk = archiefstukRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(archiefstuk);
    }

    /**
     * {@code DELETE  /archiefstuks/:id} : delete the "id" archiefstuk.
     *
     * @param id the id of the archiefstuk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArchiefstuk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Archiefstuk : {}", id);
        archiefstukRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
