package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Raadsstuk;
import nl.ritense.demo.repository.RaadsstukRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Raadsstuk}.
 */
@RestController
@RequestMapping("/api/raadsstuks")
@Transactional
public class RaadsstukResource {

    private final Logger log = LoggerFactory.getLogger(RaadsstukResource.class);

    private static final String ENTITY_NAME = "raadsstuk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaadsstukRepository raadsstukRepository;

    public RaadsstukResource(RaadsstukRepository raadsstukRepository) {
        this.raadsstukRepository = raadsstukRepository;
    }

    /**
     * {@code POST  /raadsstuks} : Create a new raadsstuk.
     *
     * @param raadsstuk the raadsstuk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raadsstuk, or with status {@code 400 (Bad Request)} if the raadsstuk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Raadsstuk> createRaadsstuk(@Valid @RequestBody Raadsstuk raadsstuk) throws URISyntaxException {
        log.debug("REST request to save Raadsstuk : {}", raadsstuk);
        if (raadsstuk.getId() != null) {
            throw new BadRequestAlertException("A new raadsstuk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        raadsstuk = raadsstukRepository.save(raadsstuk);
        return ResponseEntity.created(new URI("/api/raadsstuks/" + raadsstuk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, raadsstuk.getId().toString()))
            .body(raadsstuk);
    }

    /**
     * {@code PUT  /raadsstuks/:id} : Updates an existing raadsstuk.
     *
     * @param id the id of the raadsstuk to save.
     * @param raadsstuk the raadsstuk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raadsstuk,
     * or with status {@code 400 (Bad Request)} if the raadsstuk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raadsstuk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Raadsstuk> updateRaadsstuk(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Raadsstuk raadsstuk
    ) throws URISyntaxException {
        log.debug("REST request to update Raadsstuk : {}, {}", id, raadsstuk);
        if (raadsstuk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raadsstuk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raadsstukRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        raadsstuk = raadsstukRepository.save(raadsstuk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raadsstuk.getId().toString()))
            .body(raadsstuk);
    }

    /**
     * {@code PATCH  /raadsstuks/:id} : Partial updates given fields of an existing raadsstuk, field will ignore if it is null
     *
     * @param id the id of the raadsstuk to save.
     * @param raadsstuk the raadsstuk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raadsstuk,
     * or with status {@code 400 (Bad Request)} if the raadsstuk is not valid,
     * or with status {@code 404 (Not Found)} if the raadsstuk is not found,
     * or with status {@code 500 (Internal Server Error)} if the raadsstuk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Raadsstuk> partialUpdateRaadsstuk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Raadsstuk raadsstuk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Raadsstuk partially : {}, {}", id, raadsstuk);
        if (raadsstuk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raadsstuk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raadsstukRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Raadsstuk> result = raadsstukRepository
            .findById(raadsstuk.getId())
            .map(existingRaadsstuk -> {
                if (raadsstuk.getBesloten() != null) {
                    existingRaadsstuk.setBesloten(raadsstuk.getBesloten());
                }
                if (raadsstuk.getDatumexpiratie() != null) {
                    existingRaadsstuk.setDatumexpiratie(raadsstuk.getDatumexpiratie());
                }
                if (raadsstuk.getDatumpublicatie() != null) {
                    existingRaadsstuk.setDatumpublicatie(raadsstuk.getDatumpublicatie());
                }
                if (raadsstuk.getDatumregistratie() != null) {
                    existingRaadsstuk.setDatumregistratie(raadsstuk.getDatumregistratie());
                }
                if (raadsstuk.getTyperaadsstuk() != null) {
                    existingRaadsstuk.setTyperaadsstuk(raadsstuk.getTyperaadsstuk());
                }

                return existingRaadsstuk;
            })
            .map(raadsstukRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raadsstuk.getId().toString())
        );
    }

    /**
     * {@code GET  /raadsstuks} : get all the raadsstuks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of raadsstuks in body.
     */
    @GetMapping("")
    public List<Raadsstuk> getAllRaadsstuks(
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("heeftverslagvergadering-is-null".equals(filter)) {
            log.debug("REST request to get all Raadsstuks where heeftverslagVergadering is null");
            return StreamSupport.stream(raadsstukRepository.findAll().spliterator(), false)
                .filter(raadsstuk -> raadsstuk.getHeeftverslagVergadering() == null)
                .toList();
        }

        if ("betreftstemming-is-null".equals(filter)) {
            log.debug("REST request to get all Raadsstuks where betreftStemming is null");
            return StreamSupport.stream(raadsstukRepository.findAll().spliterator(), false)
                .filter(raadsstuk -> raadsstuk.getBetreftStemming() == null)
                .toList();
        }
        log.debug("REST request to get all Raadsstuks");
        if (eagerload) {
            return raadsstukRepository.findAllWithEagerRelationships();
        } else {
            return raadsstukRepository.findAll();
        }
    }

    /**
     * {@code GET  /raadsstuks/:id} : get the "id" raadsstuk.
     *
     * @param id the id of the raadsstuk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raadsstuk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Raadsstuk> getRaadsstuk(@PathVariable("id") Long id) {
        log.debug("REST request to get Raadsstuk : {}", id);
        Optional<Raadsstuk> raadsstuk = raadsstukRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(raadsstuk);
    }

    /**
     * {@code DELETE  /raadsstuks/:id} : delete the "id" raadsstuk.
     *
     * @param id the id of the raadsstuk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRaadsstuk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Raadsstuk : {}", id);
        raadsstukRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
