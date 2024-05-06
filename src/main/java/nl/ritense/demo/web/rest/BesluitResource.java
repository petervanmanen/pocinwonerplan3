package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Besluit;
import nl.ritense.demo.repository.BesluitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Besluit}.
 */
@RestController
@RequestMapping("/api/besluits")
@Transactional
public class BesluitResource {

    private final Logger log = LoggerFactory.getLogger(BesluitResource.class);

    private static final String ENTITY_NAME = "besluit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BesluitRepository besluitRepository;

    public BesluitResource(BesluitRepository besluitRepository) {
        this.besluitRepository = besluitRepository;
    }

    /**
     * {@code POST  /besluits} : Create a new besluit.
     *
     * @param besluit the besluit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new besluit, or with status {@code 400 (Bad Request)} if the besluit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Besluit> createBesluit(@RequestBody Besluit besluit) throws URISyntaxException {
        log.debug("REST request to save Besluit : {}", besluit);
        if (besluit.getId() != null) {
            throw new BadRequestAlertException("A new besluit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        besluit = besluitRepository.save(besluit);
        return ResponseEntity.created(new URI("/api/besluits/" + besluit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, besluit.getId().toString()))
            .body(besluit);
    }

    /**
     * {@code PUT  /besluits/:id} : Updates an existing besluit.
     *
     * @param id the id of the besluit to save.
     * @param besluit the besluit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated besluit,
     * or with status {@code 400 (Bad Request)} if the besluit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the besluit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Besluit> updateBesluit(@PathVariable(value = "id", required = false) final Long id, @RequestBody Besluit besluit)
        throws URISyntaxException {
        log.debug("REST request to update Besluit : {}, {}", id, besluit);
        if (besluit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, besluit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!besluitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        besluit = besluitRepository.save(besluit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, besluit.getId().toString()))
            .body(besluit);
    }

    /**
     * {@code PATCH  /besluits/:id} : Partial updates given fields of an existing besluit, field will ignore if it is null
     *
     * @param id the id of the besluit to save.
     * @param besluit the besluit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated besluit,
     * or with status {@code 400 (Bad Request)} if the besluit is not valid,
     * or with status {@code 404 (Not Found)} if the besluit is not found,
     * or with status {@code 500 (Internal Server Error)} if the besluit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Besluit> partialUpdateBesluit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Besluit besluit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Besluit partially : {}, {}", id, besluit);
        if (besluit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, besluit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!besluitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Besluit> result = besluitRepository
            .findById(besluit.getId())
            .map(existingBesluit -> {
                if (besluit.getBesluit() != null) {
                    existingBesluit.setBesluit(besluit.getBesluit());
                }
                if (besluit.getBesluitidentificatie() != null) {
                    existingBesluit.setBesluitidentificatie(besluit.getBesluitidentificatie());
                }
                if (besluit.getBesluittoelichting() != null) {
                    existingBesluit.setBesluittoelichting(besluit.getBesluittoelichting());
                }
                if (besluit.getDatumbesluit() != null) {
                    existingBesluit.setDatumbesluit(besluit.getDatumbesluit());
                }
                if (besluit.getDatumpublicatie() != null) {
                    existingBesluit.setDatumpublicatie(besluit.getDatumpublicatie());
                }
                if (besluit.getDatumstart() != null) {
                    existingBesluit.setDatumstart(besluit.getDatumstart());
                }
                if (besluit.getDatumuiterlijkereactie() != null) {
                    existingBesluit.setDatumuiterlijkereactie(besluit.getDatumuiterlijkereactie());
                }
                if (besluit.getDatumverval() != null) {
                    existingBesluit.setDatumverval(besluit.getDatumverval());
                }
                if (besluit.getDatumverzending() != null) {
                    existingBesluit.setDatumverzending(besluit.getDatumverzending());
                }
                if (besluit.getRedenverval() != null) {
                    existingBesluit.setRedenverval(besluit.getRedenverval());
                }

                return existingBesluit;
            })
            .map(besluitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, besluit.getId().toString())
        );
    }

    /**
     * {@code GET  /besluits} : get all the besluits.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of besluits in body.
     */
    @GetMapping("")
    public List<Besluit> getAllBesluits(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Besluits");
        if (eagerload) {
            return besluitRepository.findAllWithEagerRelationships();
        } else {
            return besluitRepository.findAll();
        }
    }

    /**
     * {@code GET  /besluits/:id} : get the "id" besluit.
     *
     * @param id the id of the besluit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the besluit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Besluit> getBesluit(@PathVariable("id") Long id) {
        log.debug("REST request to get Besluit : {}", id);
        Optional<Besluit> besluit = besluitRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(besluit);
    }

    /**
     * {@code DELETE  /besluits/:id} : delete the "id" besluit.
     *
     * @param id the id of the besluit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBesluit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Besluit : {}", id);
        besluitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
