package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Voertuig;
import nl.ritense.demo.repository.VoertuigRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Voertuig}.
 */
@RestController
@RequestMapping("/api/voertuigs")
@Transactional
public class VoertuigResource {

    private final Logger log = LoggerFactory.getLogger(VoertuigResource.class);

    private static final String ENTITY_NAME = "voertuig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoertuigRepository voertuigRepository;

    public VoertuigResource(VoertuigRepository voertuigRepository) {
        this.voertuigRepository = voertuigRepository;
    }

    /**
     * {@code POST  /voertuigs} : Create a new voertuig.
     *
     * @param voertuig the voertuig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voertuig, or with status {@code 400 (Bad Request)} if the voertuig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Voertuig> createVoertuig(@Valid @RequestBody Voertuig voertuig) throws URISyntaxException {
        log.debug("REST request to save Voertuig : {}", voertuig);
        if (voertuig.getId() != null) {
            throw new BadRequestAlertException("A new voertuig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        voertuig = voertuigRepository.save(voertuig);
        return ResponseEntity.created(new URI("/api/voertuigs/" + voertuig.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, voertuig.getId().toString()))
            .body(voertuig);
    }

    /**
     * {@code PUT  /voertuigs/:id} : Updates an existing voertuig.
     *
     * @param id the id of the voertuig to save.
     * @param voertuig the voertuig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voertuig,
     * or with status {@code 400 (Bad Request)} if the voertuig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voertuig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Voertuig> updateVoertuig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Voertuig voertuig
    ) throws URISyntaxException {
        log.debug("REST request to update Voertuig : {}, {}", id, voertuig);
        if (voertuig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voertuig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voertuigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        voertuig = voertuigRepository.save(voertuig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voertuig.getId().toString()))
            .body(voertuig);
    }

    /**
     * {@code PATCH  /voertuigs/:id} : Partial updates given fields of an existing voertuig, field will ignore if it is null
     *
     * @param id the id of the voertuig to save.
     * @param voertuig the voertuig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voertuig,
     * or with status {@code 400 (Bad Request)} if the voertuig is not valid,
     * or with status {@code 404 (Not Found)} if the voertuig is not found,
     * or with status {@code 500 (Internal Server Error)} if the voertuig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Voertuig> partialUpdateVoertuig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Voertuig voertuig
    ) throws URISyntaxException {
        log.debug("REST request to partial update Voertuig partially : {}, {}", id, voertuig);
        if (voertuig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voertuig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voertuigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Voertuig> result = voertuigRepository
            .findById(voertuig.getId())
            .map(existingVoertuig -> {
                if (voertuig.getKenteken() != null) {
                    existingVoertuig.setKenteken(voertuig.getKenteken());
                }
                if (voertuig.getKleur() != null) {
                    existingVoertuig.setKleur(voertuig.getKleur());
                }
                if (voertuig.getLand() != null) {
                    existingVoertuig.setLand(voertuig.getLand());
                }
                if (voertuig.getMerk() != null) {
                    existingVoertuig.setMerk(voertuig.getMerk());
                }
                if (voertuig.getType() != null) {
                    existingVoertuig.setType(voertuig.getType());
                }

                return existingVoertuig;
            })
            .map(voertuigRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voertuig.getId().toString())
        );
    }

    /**
     * {@code GET  /voertuigs} : get all the voertuigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voertuigs in body.
     */
    @GetMapping("")
    public List<Voertuig> getAllVoertuigs() {
        log.debug("REST request to get all Voertuigs");
        return voertuigRepository.findAll();
    }

    /**
     * {@code GET  /voertuigs/:id} : get the "id" voertuig.
     *
     * @param id the id of the voertuig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voertuig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Voertuig> getVoertuig(@PathVariable("id") Long id) {
        log.debug("REST request to get Voertuig : {}", id);
        Optional<Voertuig> voertuig = voertuigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(voertuig);
    }

    /**
     * {@code DELETE  /voertuigs/:id} : delete the "id" voertuig.
     *
     * @param id the id of the voertuig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoertuig(@PathVariable("id") Long id) {
        log.debug("REST request to delete Voertuig : {}", id);
        voertuigRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
