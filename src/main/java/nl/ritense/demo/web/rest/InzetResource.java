package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Inzet;
import nl.ritense.demo.repository.InzetRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inzet}.
 */
@RestController
@RequestMapping("/api/inzets")
@Transactional
public class InzetResource {

    private final Logger log = LoggerFactory.getLogger(InzetResource.class);

    private static final String ENTITY_NAME = "inzet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InzetRepository inzetRepository;

    public InzetResource(InzetRepository inzetRepository) {
        this.inzetRepository = inzetRepository;
    }

    /**
     * {@code POST  /inzets} : Create a new inzet.
     *
     * @param inzet the inzet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inzet, or with status {@code 400 (Bad Request)} if the inzet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inzet> createInzet(@Valid @RequestBody Inzet inzet) throws URISyntaxException {
        log.debug("REST request to save Inzet : {}", inzet);
        if (inzet.getId() != null) {
            throw new BadRequestAlertException("A new inzet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inzet = inzetRepository.save(inzet);
        return ResponseEntity.created(new URI("/api/inzets/" + inzet.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inzet.getId().toString()))
            .body(inzet);
    }

    /**
     * {@code PUT  /inzets/:id} : Updates an existing inzet.
     *
     * @param id the id of the inzet to save.
     * @param inzet the inzet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inzet,
     * or with status {@code 400 (Bad Request)} if the inzet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inzet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inzet> updateInzet(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Inzet inzet)
        throws URISyntaxException {
        log.debug("REST request to update Inzet : {}, {}", id, inzet);
        if (inzet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inzet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inzetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inzet = inzetRepository.save(inzet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inzet.getId().toString()))
            .body(inzet);
    }

    /**
     * {@code PATCH  /inzets/:id} : Partial updates given fields of an existing inzet, field will ignore if it is null
     *
     * @param id the id of the inzet to save.
     * @param inzet the inzet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inzet,
     * or with status {@code 400 (Bad Request)} if the inzet is not valid,
     * or with status {@code 404 (Not Found)} if the inzet is not found,
     * or with status {@code 500 (Internal Server Error)} if the inzet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inzet> partialUpdateInzet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Inzet inzet
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inzet partially : {}, {}", id, inzet);
        if (inzet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inzet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inzetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inzet> result = inzetRepository
            .findById(inzet.getId())
            .map(existingInzet -> {
                if (inzet.getDatumbegin() != null) {
                    existingInzet.setDatumbegin(inzet.getDatumbegin());
                }
                if (inzet.getDatumeinde() != null) {
                    existingInzet.setDatumeinde(inzet.getDatumeinde());
                }
                if (inzet.getPercentage() != null) {
                    existingInzet.setPercentage(inzet.getPercentage());
                }
                if (inzet.getUren() != null) {
                    existingInzet.setUren(inzet.getUren());
                }

                return existingInzet;
            })
            .map(inzetRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inzet.getId().toString())
        );
    }

    /**
     * {@code GET  /inzets} : get all the inzets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inzets in body.
     */
    @GetMapping("")
    public List<Inzet> getAllInzets() {
        log.debug("REST request to get all Inzets");
        return inzetRepository.findAll();
    }

    /**
     * {@code GET  /inzets/:id} : get the "id" inzet.
     *
     * @param id the id of the inzet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inzet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inzet> getInzet(@PathVariable("id") Long id) {
        log.debug("REST request to get Inzet : {}", id);
        Optional<Inzet> inzet = inzetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inzet);
    }

    /**
     * {@code DELETE  /inzets/:id} : delete the "id" inzet.
     *
     * @param id the id of the inzet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInzet(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inzet : {}", id);
        inzetRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
