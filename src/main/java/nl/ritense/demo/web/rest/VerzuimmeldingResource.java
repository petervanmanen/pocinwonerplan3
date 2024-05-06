package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verzuimmelding;
import nl.ritense.demo.repository.VerzuimmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verzuimmelding}.
 */
@RestController
@RequestMapping("/api/verzuimmeldings")
@Transactional
public class VerzuimmeldingResource {

    private final Logger log = LoggerFactory.getLogger(VerzuimmeldingResource.class);

    private static final String ENTITY_NAME = "verzuimmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerzuimmeldingRepository verzuimmeldingRepository;

    public VerzuimmeldingResource(VerzuimmeldingRepository verzuimmeldingRepository) {
        this.verzuimmeldingRepository = verzuimmeldingRepository;
    }

    /**
     * {@code POST  /verzuimmeldings} : Create a new verzuimmelding.
     *
     * @param verzuimmelding the verzuimmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verzuimmelding, or with status {@code 400 (Bad Request)} if the verzuimmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verzuimmelding> createVerzuimmelding(@RequestBody Verzuimmelding verzuimmelding) throws URISyntaxException {
        log.debug("REST request to save Verzuimmelding : {}", verzuimmelding);
        if (verzuimmelding.getId() != null) {
            throw new BadRequestAlertException("A new verzuimmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verzuimmelding = verzuimmeldingRepository.save(verzuimmelding);
        return ResponseEntity.created(new URI("/api/verzuimmeldings/" + verzuimmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verzuimmelding.getId().toString()))
            .body(verzuimmelding);
    }

    /**
     * {@code PUT  /verzuimmeldings/:id} : Updates an existing verzuimmelding.
     *
     * @param id the id of the verzuimmelding to save.
     * @param verzuimmelding the verzuimmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzuimmelding,
     * or with status {@code 400 (Bad Request)} if the verzuimmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verzuimmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verzuimmelding> updateVerzuimmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verzuimmelding verzuimmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Verzuimmelding : {}, {}", id, verzuimmelding);
        if (verzuimmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzuimmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzuimmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verzuimmelding = verzuimmeldingRepository.save(verzuimmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzuimmelding.getId().toString()))
            .body(verzuimmelding);
    }

    /**
     * {@code PATCH  /verzuimmeldings/:id} : Partial updates given fields of an existing verzuimmelding, field will ignore if it is null
     *
     * @param id the id of the verzuimmelding to save.
     * @param verzuimmelding the verzuimmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzuimmelding,
     * or with status {@code 400 (Bad Request)} if the verzuimmelding is not valid,
     * or with status {@code 404 (Not Found)} if the verzuimmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the verzuimmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verzuimmelding> partialUpdateVerzuimmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verzuimmelding verzuimmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verzuimmelding partially : {}, {}", id, verzuimmelding);
        if (verzuimmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzuimmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzuimmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verzuimmelding> result = verzuimmeldingRepository
            .findById(verzuimmelding.getId())
            .map(existingVerzuimmelding -> {
                if (verzuimmelding.getDatumeinde() != null) {
                    existingVerzuimmelding.setDatumeinde(verzuimmelding.getDatumeinde());
                }
                if (verzuimmelding.getDatumstart() != null) {
                    existingVerzuimmelding.setDatumstart(verzuimmelding.getDatumstart());
                }
                if (verzuimmelding.getVoorstelschool() != null) {
                    existingVerzuimmelding.setVoorstelschool(verzuimmelding.getVoorstelschool());
                }

                return existingVerzuimmelding;
            })
            .map(verzuimmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzuimmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /verzuimmeldings} : get all the verzuimmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verzuimmeldings in body.
     */
    @GetMapping("")
    public List<Verzuimmelding> getAllVerzuimmeldings() {
        log.debug("REST request to get all Verzuimmeldings");
        return verzuimmeldingRepository.findAll();
    }

    /**
     * {@code GET  /verzuimmeldings/:id} : get the "id" verzuimmelding.
     *
     * @param id the id of the verzuimmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verzuimmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verzuimmelding> getVerzuimmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Verzuimmelding : {}", id);
        Optional<Verzuimmelding> verzuimmelding = verzuimmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verzuimmelding);
    }

    /**
     * {@code DELETE  /verzuimmeldings/:id} : delete the "id" verzuimmelding.
     *
     * @param id the id of the verzuimmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerzuimmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verzuimmelding : {}", id);
        verzuimmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
