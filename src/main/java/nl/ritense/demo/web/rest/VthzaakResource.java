package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vthzaak;
import nl.ritense.demo.repository.VthzaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vthzaak}.
 */
@RestController
@RequestMapping("/api/vthzaaks")
@Transactional
public class VthzaakResource {

    private final Logger log = LoggerFactory.getLogger(VthzaakResource.class);

    private static final String ENTITY_NAME = "vthzaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VthzaakRepository vthzaakRepository;

    public VthzaakResource(VthzaakRepository vthzaakRepository) {
        this.vthzaakRepository = vthzaakRepository;
    }

    /**
     * {@code POST  /vthzaaks} : Create a new vthzaak.
     *
     * @param vthzaak the vthzaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vthzaak, or with status {@code 400 (Bad Request)} if the vthzaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vthzaak> createVthzaak(@Valid @RequestBody Vthzaak vthzaak) throws URISyntaxException {
        log.debug("REST request to save Vthzaak : {}", vthzaak);
        if (vthzaak.getId() != null) {
            throw new BadRequestAlertException("A new vthzaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vthzaak = vthzaakRepository.save(vthzaak);
        return ResponseEntity.created(new URI("/api/vthzaaks/" + vthzaak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vthzaak.getId().toString()))
            .body(vthzaak);
    }

    /**
     * {@code PUT  /vthzaaks/:id} : Updates an existing vthzaak.
     *
     * @param id the id of the vthzaak to save.
     * @param vthzaak the vthzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vthzaak,
     * or with status {@code 400 (Bad Request)} if the vthzaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vthzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vthzaak> updateVthzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vthzaak vthzaak
    ) throws URISyntaxException {
        log.debug("REST request to update Vthzaak : {}, {}", id, vthzaak);
        if (vthzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vthzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vthzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vthzaak = vthzaakRepository.save(vthzaak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vthzaak.getId().toString()))
            .body(vthzaak);
    }

    /**
     * {@code PATCH  /vthzaaks/:id} : Partial updates given fields of an existing vthzaak, field will ignore if it is null
     *
     * @param id the id of the vthzaak to save.
     * @param vthzaak the vthzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vthzaak,
     * or with status {@code 400 (Bad Request)} if the vthzaak is not valid,
     * or with status {@code 404 (Not Found)} if the vthzaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the vthzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vthzaak> partialUpdateVthzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vthzaak vthzaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vthzaak partially : {}, {}", id, vthzaak);
        if (vthzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vthzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vthzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vthzaak> result = vthzaakRepository
            .findById(vthzaak.getId())
            .map(existingVthzaak -> {
                if (vthzaak.getBehandelaar() != null) {
                    existingVthzaak.setBehandelaar(vthzaak.getBehandelaar());
                }
                if (vthzaak.getBevoegdgezag() != null) {
                    existingVthzaak.setBevoegdgezag(vthzaak.getBevoegdgezag());
                }
                if (vthzaak.getPrioritering() != null) {
                    existingVthzaak.setPrioritering(vthzaak.getPrioritering());
                }
                if (vthzaak.getTeambehandelaar() != null) {
                    existingVthzaak.setTeambehandelaar(vthzaak.getTeambehandelaar());
                }
                if (vthzaak.getUitvoerendeinstantie() != null) {
                    existingVthzaak.setUitvoerendeinstantie(vthzaak.getUitvoerendeinstantie());
                }
                if (vthzaak.getVerkamering() != null) {
                    existingVthzaak.setVerkamering(vthzaak.getVerkamering());
                }

                return existingVthzaak;
            })
            .map(vthzaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vthzaak.getId().toString())
        );
    }

    /**
     * {@code GET  /vthzaaks} : get all the vthzaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vthzaaks in body.
     */
    @GetMapping("")
    public List<Vthzaak> getAllVthzaaks() {
        log.debug("REST request to get all Vthzaaks");
        return vthzaakRepository.findAll();
    }

    /**
     * {@code GET  /vthzaaks/:id} : get the "id" vthzaak.
     *
     * @param id the id of the vthzaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vthzaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vthzaak> getVthzaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Vthzaak : {}", id);
        Optional<Vthzaak> vthzaak = vthzaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vthzaak);
    }

    /**
     * {@code DELETE  /vthzaaks/:id} : delete the "id" vthzaak.
     *
     * @param id the id of the vthzaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVthzaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vthzaak : {}", id);
        vthzaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
