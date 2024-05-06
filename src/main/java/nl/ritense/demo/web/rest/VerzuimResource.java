package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verzuim;
import nl.ritense.demo.repository.VerzuimRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verzuim}.
 */
@RestController
@RequestMapping("/api/verzuims")
@Transactional
public class VerzuimResource {

    private final Logger log = LoggerFactory.getLogger(VerzuimResource.class);

    private static final String ENTITY_NAME = "verzuim";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerzuimRepository verzuimRepository;

    public VerzuimResource(VerzuimRepository verzuimRepository) {
        this.verzuimRepository = verzuimRepository;
    }

    /**
     * {@code POST  /verzuims} : Create a new verzuim.
     *
     * @param verzuim the verzuim to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verzuim, or with status {@code 400 (Bad Request)} if the verzuim has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verzuim> createVerzuim(@RequestBody Verzuim verzuim) throws URISyntaxException {
        log.debug("REST request to save Verzuim : {}", verzuim);
        if (verzuim.getId() != null) {
            throw new BadRequestAlertException("A new verzuim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verzuim = verzuimRepository.save(verzuim);
        return ResponseEntity.created(new URI("/api/verzuims/" + verzuim.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verzuim.getId().toString()))
            .body(verzuim);
    }

    /**
     * {@code PUT  /verzuims/:id} : Updates an existing verzuim.
     *
     * @param id the id of the verzuim to save.
     * @param verzuim the verzuim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzuim,
     * or with status {@code 400 (Bad Request)} if the verzuim is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verzuim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verzuim> updateVerzuim(@PathVariable(value = "id", required = false) final Long id, @RequestBody Verzuim verzuim)
        throws URISyntaxException {
        log.debug("REST request to update Verzuim : {}, {}", id, verzuim);
        if (verzuim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzuim.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzuimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verzuim = verzuimRepository.save(verzuim);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzuim.getId().toString()))
            .body(verzuim);
    }

    /**
     * {@code PATCH  /verzuims/:id} : Partial updates given fields of an existing verzuim, field will ignore if it is null
     *
     * @param id the id of the verzuim to save.
     * @param verzuim the verzuim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzuim,
     * or with status {@code 400 (Bad Request)} if the verzuim is not valid,
     * or with status {@code 404 (Not Found)} if the verzuim is not found,
     * or with status {@code 500 (Internal Server Error)} if the verzuim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verzuim> partialUpdateVerzuim(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verzuim verzuim
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verzuim partially : {}, {}", id, verzuim);
        if (verzuim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzuim.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzuimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verzuim> result = verzuimRepository
            .findById(verzuim.getId())
            .map(existingVerzuim -> {
                if (verzuim.getDatumtijdeinde() != null) {
                    existingVerzuim.setDatumtijdeinde(verzuim.getDatumtijdeinde());
                }
                if (verzuim.getDatumtijdstart() != null) {
                    existingVerzuim.setDatumtijdstart(verzuim.getDatumtijdstart());
                }

                return existingVerzuim;
            })
            .map(verzuimRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzuim.getId().toString())
        );
    }

    /**
     * {@code GET  /verzuims} : get all the verzuims.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verzuims in body.
     */
    @GetMapping("")
    public List<Verzuim> getAllVerzuims() {
        log.debug("REST request to get all Verzuims");
        return verzuimRepository.findAll();
    }

    /**
     * {@code GET  /verzuims/:id} : get the "id" verzuim.
     *
     * @param id the id of the verzuim to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verzuim, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verzuim> getVerzuim(@PathVariable("id") Long id) {
        log.debug("REST request to get Verzuim : {}", id);
        Optional<Verzuim> verzuim = verzuimRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verzuim);
    }

    /**
     * {@code DELETE  /verzuims/:id} : delete the "id" verzuim.
     *
     * @param id the id of the verzuim to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerzuim(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verzuim : {}", id);
        verzuimRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
