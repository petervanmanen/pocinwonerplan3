package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Speelterrein;
import nl.ritense.demo.repository.SpeelterreinRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Speelterrein}.
 */
@RestController
@RequestMapping("/api/speelterreins")
@Transactional
public class SpeelterreinResource {

    private final Logger log = LoggerFactory.getLogger(SpeelterreinResource.class);

    private static final String ENTITY_NAME = "speelterrein";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpeelterreinRepository speelterreinRepository;

    public SpeelterreinResource(SpeelterreinRepository speelterreinRepository) {
        this.speelterreinRepository = speelterreinRepository;
    }

    /**
     * {@code POST  /speelterreins} : Create a new speelterrein.
     *
     * @param speelterrein the speelterrein to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speelterrein, or with status {@code 400 (Bad Request)} if the speelterrein has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Speelterrein> createSpeelterrein(@RequestBody Speelterrein speelterrein) throws URISyntaxException {
        log.debug("REST request to save Speelterrein : {}", speelterrein);
        if (speelterrein.getId() != null) {
            throw new BadRequestAlertException("A new speelterrein cannot already have an ID", ENTITY_NAME, "idexists");
        }
        speelterrein = speelterreinRepository.save(speelterrein);
        return ResponseEntity.created(new URI("/api/speelterreins/" + speelterrein.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, speelterrein.getId().toString()))
            .body(speelterrein);
    }

    /**
     * {@code PUT  /speelterreins/:id} : Updates an existing speelterrein.
     *
     * @param id the id of the speelterrein to save.
     * @param speelterrein the speelterrein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speelterrein,
     * or with status {@code 400 (Bad Request)} if the speelterrein is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speelterrein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Speelterrein> updateSpeelterrein(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Speelterrein speelterrein
    ) throws URISyntaxException {
        log.debug("REST request to update Speelterrein : {}, {}", id, speelterrein);
        if (speelterrein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speelterrein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speelterreinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        speelterrein = speelterreinRepository.save(speelterrein);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, speelterrein.getId().toString()))
            .body(speelterrein);
    }

    /**
     * {@code PATCH  /speelterreins/:id} : Partial updates given fields of an existing speelterrein, field will ignore if it is null
     *
     * @param id the id of the speelterrein to save.
     * @param speelterrein the speelterrein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speelterrein,
     * or with status {@code 400 (Bad Request)} if the speelterrein is not valid,
     * or with status {@code 404 (Not Found)} if the speelterrein is not found,
     * or with status {@code 500 (Internal Server Error)} if the speelterrein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Speelterrein> partialUpdateSpeelterrein(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Speelterrein speelterrein
    ) throws URISyntaxException {
        log.debug("REST request to partial update Speelterrein partially : {}, {}", id, speelterrein);
        if (speelterrein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speelterrein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speelterreinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Speelterrein> result = speelterreinRepository
            .findById(speelterrein.getId())
            .map(existingSpeelterrein -> {
                if (speelterrein.getJaarherinrichting() != null) {
                    existingSpeelterrein.setJaarherinrichting(speelterrein.getJaarherinrichting());
                }
                if (speelterrein.getSpeelterreinleeftijddoelgroep() != null) {
                    existingSpeelterrein.setSpeelterreinleeftijddoelgroep(speelterrein.getSpeelterreinleeftijddoelgroep());
                }
                if (speelterrein.getType() != null) {
                    existingSpeelterrein.setType(speelterrein.getType());
                }
                if (speelterrein.getTypeplus() != null) {
                    existingSpeelterrein.setTypeplus(speelterrein.getTypeplus());
                }

                return existingSpeelterrein;
            })
            .map(speelterreinRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, speelterrein.getId().toString())
        );
    }

    /**
     * {@code GET  /speelterreins} : get all the speelterreins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of speelterreins in body.
     */
    @GetMapping("")
    public List<Speelterrein> getAllSpeelterreins() {
        log.debug("REST request to get all Speelterreins");
        return speelterreinRepository.findAll();
    }

    /**
     * {@code GET  /speelterreins/:id} : get the "id" speelterrein.
     *
     * @param id the id of the speelterrein to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speelterrein, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Speelterrein> getSpeelterrein(@PathVariable("id") Long id) {
        log.debug("REST request to get Speelterrein : {}", id);
        Optional<Speelterrein> speelterrein = speelterreinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(speelterrein);
    }

    /**
     * {@code DELETE  /speelterreins/:id} : delete the "id" speelterrein.
     *
     * @param id the id of the speelterrein to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeelterrein(@PathVariable("id") Long id) {
        log.debug("REST request to delete Speelterrein : {}", id);
        speelterreinRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
