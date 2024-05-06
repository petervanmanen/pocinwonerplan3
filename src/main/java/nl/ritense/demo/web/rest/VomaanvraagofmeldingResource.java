package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vomaanvraagofmelding;
import nl.ritense.demo.repository.VomaanvraagofmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vomaanvraagofmelding}.
 */
@RestController
@RequestMapping("/api/vomaanvraagofmeldings")
@Transactional
public class VomaanvraagofmeldingResource {

    private final Logger log = LoggerFactory.getLogger(VomaanvraagofmeldingResource.class);

    private static final String ENTITY_NAME = "vomaanvraagofmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VomaanvraagofmeldingRepository vomaanvraagofmeldingRepository;

    public VomaanvraagofmeldingResource(VomaanvraagofmeldingRepository vomaanvraagofmeldingRepository) {
        this.vomaanvraagofmeldingRepository = vomaanvraagofmeldingRepository;
    }

    /**
     * {@code POST  /vomaanvraagofmeldings} : Create a new vomaanvraagofmelding.
     *
     * @param vomaanvraagofmelding the vomaanvraagofmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vomaanvraagofmelding, or with status {@code 400 (Bad Request)} if the vomaanvraagofmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vomaanvraagofmelding> createVomaanvraagofmelding(@RequestBody Vomaanvraagofmelding vomaanvraagofmelding)
        throws URISyntaxException {
        log.debug("REST request to save Vomaanvraagofmelding : {}", vomaanvraagofmelding);
        if (vomaanvraagofmelding.getId() != null) {
            throw new BadRequestAlertException("A new vomaanvraagofmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vomaanvraagofmelding = vomaanvraagofmeldingRepository.save(vomaanvraagofmelding);
        return ResponseEntity.created(new URI("/api/vomaanvraagofmeldings/" + vomaanvraagofmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vomaanvraagofmelding.getId().toString()))
            .body(vomaanvraagofmelding);
    }

    /**
     * {@code PUT  /vomaanvraagofmeldings/:id} : Updates an existing vomaanvraagofmelding.
     *
     * @param id the id of the vomaanvraagofmelding to save.
     * @param vomaanvraagofmelding the vomaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vomaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the vomaanvraagofmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vomaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vomaanvraagofmelding> updateVomaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vomaanvraagofmelding vomaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Vomaanvraagofmelding : {}, {}", id, vomaanvraagofmelding);
        if (vomaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vomaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vomaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vomaanvraagofmelding = vomaanvraagofmeldingRepository.save(vomaanvraagofmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vomaanvraagofmelding.getId().toString()))
            .body(vomaanvraagofmelding);
    }

    /**
     * {@code PATCH  /vomaanvraagofmeldings/:id} : Partial updates given fields of an existing vomaanvraagofmelding, field will ignore if it is null
     *
     * @param id the id of the vomaanvraagofmelding to save.
     * @param vomaanvraagofmelding the vomaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vomaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the vomaanvraagofmelding is not valid,
     * or with status {@code 404 (Not Found)} if the vomaanvraagofmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the vomaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vomaanvraagofmelding> partialUpdateVomaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vomaanvraagofmelding vomaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vomaanvraagofmelding partially : {}, {}", id, vomaanvraagofmelding);
        if (vomaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vomaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vomaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vomaanvraagofmelding> result = vomaanvraagofmeldingRepository
            .findById(vomaanvraagofmelding.getId())
            .map(existingVomaanvraagofmelding -> {
                if (vomaanvraagofmelding.getActiviteiten() != null) {
                    existingVomaanvraagofmelding.setActiviteiten(vomaanvraagofmelding.getActiviteiten());
                }
                if (vomaanvraagofmelding.getAdres() != null) {
                    existingVomaanvraagofmelding.setAdres(vomaanvraagofmelding.getAdres());
                }
                if (vomaanvraagofmelding.getBagid() != null) {
                    existingVomaanvraagofmelding.setBagid(vomaanvraagofmelding.getBagid());
                }
                if (vomaanvraagofmelding.getDossiernummer() != null) {
                    existingVomaanvraagofmelding.setDossiernummer(vomaanvraagofmelding.getDossiernummer());
                }
                if (vomaanvraagofmelding.getIntaketype() != null) {
                    existingVomaanvraagofmelding.setIntaketype(vomaanvraagofmelding.getIntaketype());
                }
                if (vomaanvraagofmelding.getInternnummer() != null) {
                    existingVomaanvraagofmelding.setInternnummer(vomaanvraagofmelding.getInternnummer());
                }
                if (vomaanvraagofmelding.getKadastraleaanduiding() != null) {
                    existingVomaanvraagofmelding.setKadastraleaanduiding(vomaanvraagofmelding.getKadastraleaanduiding());
                }
                if (vomaanvraagofmelding.getKenmerk() != null) {
                    existingVomaanvraagofmelding.setKenmerk(vomaanvraagofmelding.getKenmerk());
                }
                if (vomaanvraagofmelding.getLocatie() != null) {
                    existingVomaanvraagofmelding.setLocatie(vomaanvraagofmelding.getLocatie());
                }
                if (vomaanvraagofmelding.getLocatieomschrijving() != null) {
                    existingVomaanvraagofmelding.setLocatieomschrijving(vomaanvraagofmelding.getLocatieomschrijving());
                }
                if (vomaanvraagofmelding.getToelichting() != null) {
                    existingVomaanvraagofmelding.setToelichting(vomaanvraagofmelding.getToelichting());
                }

                return existingVomaanvraagofmelding;
            })
            .map(vomaanvraagofmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vomaanvraagofmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /vomaanvraagofmeldings} : get all the vomaanvraagofmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vomaanvraagofmeldings in body.
     */
    @GetMapping("")
    public List<Vomaanvraagofmelding> getAllVomaanvraagofmeldings() {
        log.debug("REST request to get all Vomaanvraagofmeldings");
        return vomaanvraagofmeldingRepository.findAll();
    }

    /**
     * {@code GET  /vomaanvraagofmeldings/:id} : get the "id" vomaanvraagofmelding.
     *
     * @param id the id of the vomaanvraagofmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vomaanvraagofmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vomaanvraagofmelding> getVomaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Vomaanvraagofmelding : {}", id);
        Optional<Vomaanvraagofmelding> vomaanvraagofmelding = vomaanvraagofmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vomaanvraagofmelding);
    }

    /**
     * {@code DELETE  /vomaanvraagofmeldings/:id} : delete the "id" vomaanvraagofmelding.
     *
     * @param id the id of the vomaanvraagofmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVomaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vomaanvraagofmelding : {}", id);
        vomaanvraagofmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
