package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vrijstelling;
import nl.ritense.demo.repository.VrijstellingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vrijstelling}.
 */
@RestController
@RequestMapping("/api/vrijstellings")
@Transactional
public class VrijstellingResource {

    private final Logger log = LoggerFactory.getLogger(VrijstellingResource.class);

    private static final String ENTITY_NAME = "vrijstelling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VrijstellingRepository vrijstellingRepository;

    public VrijstellingResource(VrijstellingRepository vrijstellingRepository) {
        this.vrijstellingRepository = vrijstellingRepository;
    }

    /**
     * {@code POST  /vrijstellings} : Create a new vrijstelling.
     *
     * @param vrijstelling the vrijstelling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vrijstelling, or with status {@code 400 (Bad Request)} if the vrijstelling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vrijstelling> createVrijstelling(@RequestBody Vrijstelling vrijstelling) throws URISyntaxException {
        log.debug("REST request to save Vrijstelling : {}", vrijstelling);
        if (vrijstelling.getId() != null) {
            throw new BadRequestAlertException("A new vrijstelling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vrijstelling = vrijstellingRepository.save(vrijstelling);
        return ResponseEntity.created(new URI("/api/vrijstellings/" + vrijstelling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vrijstelling.getId().toString()))
            .body(vrijstelling);
    }

    /**
     * {@code PUT  /vrijstellings/:id} : Updates an existing vrijstelling.
     *
     * @param id the id of the vrijstelling to save.
     * @param vrijstelling the vrijstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vrijstelling,
     * or with status {@code 400 (Bad Request)} if the vrijstelling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vrijstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vrijstelling> updateVrijstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vrijstelling vrijstelling
    ) throws URISyntaxException {
        log.debug("REST request to update Vrijstelling : {}, {}", id, vrijstelling);
        if (vrijstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vrijstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vrijstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vrijstelling = vrijstellingRepository.save(vrijstelling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vrijstelling.getId().toString()))
            .body(vrijstelling);
    }

    /**
     * {@code PATCH  /vrijstellings/:id} : Partial updates given fields of an existing vrijstelling, field will ignore if it is null
     *
     * @param id the id of the vrijstelling to save.
     * @param vrijstelling the vrijstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vrijstelling,
     * or with status {@code 400 (Bad Request)} if the vrijstelling is not valid,
     * or with status {@code 404 (Not Found)} if the vrijstelling is not found,
     * or with status {@code 500 (Internal Server Error)} if the vrijstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vrijstelling> partialUpdateVrijstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vrijstelling vrijstelling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vrijstelling partially : {}, {}", id, vrijstelling);
        if (vrijstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vrijstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vrijstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vrijstelling> result = vrijstellingRepository
            .findById(vrijstelling.getId())
            .map(existingVrijstelling -> {
                if (vrijstelling.getAanvraagtoegekend() != null) {
                    existingVrijstelling.setAanvraagtoegekend(vrijstelling.getAanvraagtoegekend());
                }
                if (vrijstelling.getBuitenlandseschoollocatie() != null) {
                    existingVrijstelling.setBuitenlandseschoollocatie(vrijstelling.getBuitenlandseschoollocatie());
                }
                if (vrijstelling.getDatumeinde() != null) {
                    existingVrijstelling.setDatumeinde(vrijstelling.getDatumeinde());
                }
                if (vrijstelling.getDatumstart() != null) {
                    existingVrijstelling.setDatumstart(vrijstelling.getDatumstart());
                }
                if (vrijstelling.getVerzuimsoort() != null) {
                    existingVrijstelling.setVerzuimsoort(vrijstelling.getVerzuimsoort());
                }

                return existingVrijstelling;
            })
            .map(vrijstellingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vrijstelling.getId().toString())
        );
    }

    /**
     * {@code GET  /vrijstellings} : get all the vrijstellings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vrijstellings in body.
     */
    @GetMapping("")
    public List<Vrijstelling> getAllVrijstellings() {
        log.debug("REST request to get all Vrijstellings");
        return vrijstellingRepository.findAll();
    }

    /**
     * {@code GET  /vrijstellings/:id} : get the "id" vrijstelling.
     *
     * @param id the id of the vrijstelling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vrijstelling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vrijstelling> getVrijstelling(@PathVariable("id") Long id) {
        log.debug("REST request to get Vrijstelling : {}", id);
        Optional<Vrijstelling> vrijstelling = vrijstellingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vrijstelling);
    }

    /**
     * {@code DELETE  /vrijstellings/:id} : delete the "id" vrijstelling.
     *
     * @param id the id of the vrijstelling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVrijstelling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vrijstelling : {}", id);
        vrijstellingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
