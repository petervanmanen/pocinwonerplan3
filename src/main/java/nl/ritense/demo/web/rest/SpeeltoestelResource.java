package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Speeltoestel;
import nl.ritense.demo.repository.SpeeltoestelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Speeltoestel}.
 */
@RestController
@RequestMapping("/api/speeltoestels")
@Transactional
public class SpeeltoestelResource {

    private final Logger log = LoggerFactory.getLogger(SpeeltoestelResource.class);

    private static final String ENTITY_NAME = "speeltoestel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpeeltoestelRepository speeltoestelRepository;

    public SpeeltoestelResource(SpeeltoestelRepository speeltoestelRepository) {
        this.speeltoestelRepository = speeltoestelRepository;
    }

    /**
     * {@code POST  /speeltoestels} : Create a new speeltoestel.
     *
     * @param speeltoestel the speeltoestel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speeltoestel, or with status {@code 400 (Bad Request)} if the speeltoestel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Speeltoestel> createSpeeltoestel(@Valid @RequestBody Speeltoestel speeltoestel) throws URISyntaxException {
        log.debug("REST request to save Speeltoestel : {}", speeltoestel);
        if (speeltoestel.getId() != null) {
            throw new BadRequestAlertException("A new speeltoestel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        speeltoestel = speeltoestelRepository.save(speeltoestel);
        return ResponseEntity.created(new URI("/api/speeltoestels/" + speeltoestel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, speeltoestel.getId().toString()))
            .body(speeltoestel);
    }

    /**
     * {@code PUT  /speeltoestels/:id} : Updates an existing speeltoestel.
     *
     * @param id the id of the speeltoestel to save.
     * @param speeltoestel the speeltoestel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speeltoestel,
     * or with status {@code 400 (Bad Request)} if the speeltoestel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speeltoestel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Speeltoestel> updateSpeeltoestel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Speeltoestel speeltoestel
    ) throws URISyntaxException {
        log.debug("REST request to update Speeltoestel : {}, {}", id, speeltoestel);
        if (speeltoestel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speeltoestel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speeltoestelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        speeltoestel = speeltoestelRepository.save(speeltoestel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, speeltoestel.getId().toString()))
            .body(speeltoestel);
    }

    /**
     * {@code PATCH  /speeltoestels/:id} : Partial updates given fields of an existing speeltoestel, field will ignore if it is null
     *
     * @param id the id of the speeltoestel to save.
     * @param speeltoestel the speeltoestel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speeltoestel,
     * or with status {@code 400 (Bad Request)} if the speeltoestel is not valid,
     * or with status {@code 404 (Not Found)} if the speeltoestel is not found,
     * or with status {@code 500 (Internal Server Error)} if the speeltoestel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Speeltoestel> partialUpdateSpeeltoestel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Speeltoestel speeltoestel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Speeltoestel partially : {}, {}", id, speeltoestel);
        if (speeltoestel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speeltoestel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speeltoestelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Speeltoestel> result = speeltoestelRepository
            .findById(speeltoestel.getId())
            .map(existingSpeeltoestel -> {
                if (speeltoestel.getCatalogusprijs() != null) {
                    existingSpeeltoestel.setCatalogusprijs(speeltoestel.getCatalogusprijs());
                }
                if (speeltoestel.getCertificaat() != null) {
                    existingSpeeltoestel.setCertificaat(speeltoestel.getCertificaat());
                }
                if (speeltoestel.getCertificaatnummer() != null) {
                    existingSpeeltoestel.setCertificaatnummer(speeltoestel.getCertificaatnummer());
                }
                if (speeltoestel.getCertificeringsinstantie() != null) {
                    existingSpeeltoestel.setCertificeringsinstantie(speeltoestel.getCertificeringsinstantie());
                }
                if (speeltoestel.getControlefrequentie() != null) {
                    existingSpeeltoestel.setControlefrequentie(speeltoestel.getControlefrequentie());
                }
                if (speeltoestel.getDatumcertificaat() != null) {
                    existingSpeeltoestel.setDatumcertificaat(speeltoestel.getDatumcertificaat());
                }
                if (speeltoestel.getGemakkelijktoegankelijk() != null) {
                    existingSpeeltoestel.setGemakkelijktoegankelijk(speeltoestel.getGemakkelijktoegankelijk());
                }
                if (speeltoestel.getInspectievolgorde() != null) {
                    existingSpeeltoestel.setInspectievolgorde(speeltoestel.getInspectievolgorde());
                }
                if (speeltoestel.getInstallatiekosten() != null) {
                    existingSpeeltoestel.setInstallatiekosten(speeltoestel.getInstallatiekosten());
                }
                if (speeltoestel.getSpeelterrein() != null) {
                    existingSpeeltoestel.setSpeelterrein(speeltoestel.getSpeelterrein());
                }
                if (speeltoestel.getSpeeltoesteltoestelonderdeel() != null) {
                    existingSpeeltoestel.setSpeeltoesteltoestelonderdeel(speeltoestel.getSpeeltoesteltoestelonderdeel());
                }
                if (speeltoestel.getTechnischelevensduur() != null) {
                    existingSpeeltoestel.setTechnischelevensduur(speeltoestel.getTechnischelevensduur());
                }
                if (speeltoestel.getToestelcode() != null) {
                    existingSpeeltoestel.setToestelcode(speeltoestel.getToestelcode());
                }
                if (speeltoestel.getToestelgroep() != null) {
                    existingSpeeltoestel.setToestelgroep(speeltoestel.getToestelgroep());
                }
                if (speeltoestel.getToestelnaam() != null) {
                    existingSpeeltoestel.setToestelnaam(speeltoestel.getToestelnaam());
                }
                if (speeltoestel.getType() != null) {
                    existingSpeeltoestel.setType(speeltoestel.getType());
                }
                if (speeltoestel.getTypenummer() != null) {
                    existingSpeeltoestel.setTypenummer(speeltoestel.getTypenummer());
                }
                if (speeltoestel.getTypeplus() != null) {
                    existingSpeeltoestel.setTypeplus(speeltoestel.getTypeplus());
                }
                if (speeltoestel.getTypeplus2() != null) {
                    existingSpeeltoestel.setTypeplus2(speeltoestel.getTypeplus2());
                }
                if (speeltoestel.getValruimtehoogte() != null) {
                    existingSpeeltoestel.setValruimtehoogte(speeltoestel.getValruimtehoogte());
                }
                if (speeltoestel.getValruimteomvang() != null) {
                    existingSpeeltoestel.setValruimteomvang(speeltoestel.getValruimteomvang());
                }
                if (speeltoestel.getVrijevalhoogte() != null) {
                    existingSpeeltoestel.setVrijevalhoogte(speeltoestel.getVrijevalhoogte());
                }

                return existingSpeeltoestel;
            })
            .map(speeltoestelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, speeltoestel.getId().toString())
        );
    }

    /**
     * {@code GET  /speeltoestels} : get all the speeltoestels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of speeltoestels in body.
     */
    @GetMapping("")
    public List<Speeltoestel> getAllSpeeltoestels() {
        log.debug("REST request to get all Speeltoestels");
        return speeltoestelRepository.findAll();
    }

    /**
     * {@code GET  /speeltoestels/:id} : get the "id" speeltoestel.
     *
     * @param id the id of the speeltoestel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speeltoestel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Speeltoestel> getSpeeltoestel(@PathVariable("id") Long id) {
        log.debug("REST request to get Speeltoestel : {}", id);
        Optional<Speeltoestel> speeltoestel = speeltoestelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(speeltoestel);
    }

    /**
     * {@code DELETE  /speeltoestels/:id} : delete the "id" speeltoestel.
     *
     * @param id the id of the speeltoestel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeeltoestel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Speeltoestel : {}", id);
        speeltoestelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
