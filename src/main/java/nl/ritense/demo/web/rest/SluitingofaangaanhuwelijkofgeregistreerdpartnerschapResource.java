package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap;
import nl.ritense.demo.repository.SluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap}.
 */
@RestController
@RequestMapping("/api/sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps")
@Transactional
public class SluitingofaangaanhuwelijkofgeregistreerdpartnerschapResource {

    private final Logger log = LoggerFactory.getLogger(SluitingofaangaanhuwelijkofgeregistreerdpartnerschapResource.class);

    private static final String ENTITY_NAME = "sluitingofaangaanhuwelijkofgeregistreerdpartnerschap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository;

    public SluitingofaangaanhuwelijkofgeregistreerdpartnerschapResource(
        SluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository
    ) {
        this.sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository =
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository;
    }

    /**
     * {@code POST  /sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps} : Create a new sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.
     *
     * @param sluitingofaangaanhuwelijkofgeregistreerdpartnerschap the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sluitingofaangaanhuwelijkofgeregistreerdpartnerschap, or with status {@code 400 (Bad Request)} if the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap> createSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(
        @RequestBody Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
    ) throws URISyntaxException {
        log.debug(
            "REST request to save Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap : {}",
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        );
        if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId() != null) {
            throw new BadRequestAlertException(
                "A new sluitingofaangaanhuwelijkofgeregistreerdpartnerschap cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap = sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.save(
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        );
        return ResponseEntity.created(
            new URI(
                "/api/sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps/" + sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId().toString()
                )
            )
            .body(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);
    }

    /**
     * {@code PUT  /sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps/:id} : Updates an existing sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.
     *
     * @param id the id of the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap to save.
     * @param sluitingofaangaanhuwelijkofgeregistreerdpartnerschap the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
     * or with status {@code 400 (Bad Request)} if the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap> updateSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
    ) throws URISyntaxException {
        log.debug(
            "REST request to update Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap : {}, {}",
            id,
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        );
        if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap = sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.save(
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId().toString()
                )
            )
            .body(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);
    }

    /**
     * {@code PATCH  /sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps/:id} : Partial updates given fields of an existing sluitingofaangaanhuwelijkofgeregistreerdpartnerschap, field will ignore if it is null
     *
     * @param id the id of the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap to save.
     * @param sluitingofaangaanhuwelijkofgeregistreerdpartnerschap the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
     * or with status {@code 400 (Bad Request)} if the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap is not valid,
     * or with status {@code 404 (Not Found)} if the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap is not found,
     * or with status {@code 500 (Internal Server Error)} if the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
    > partialUpdateSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap partially : {}, {}",
            id,
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        );
        if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap> result =
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository
                .findById(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())
                .map(existingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap -> {
                    if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getBuitenlandseplaatsaanvang() != null) {
                        existingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setBuitenlandseplaatsaanvang(
                            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getBuitenlandseplaatsaanvang()
                        );
                    }
                    if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getBuitenlandseregioaanvang() != null) {
                        existingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setBuitenlandseregioaanvang(
                            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getBuitenlandseregioaanvang()
                        );
                    }
                    if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getDatumaanvang() != null) {
                        existingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setDatumaanvang(
                            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getDatumaanvang()
                        );
                    }
                    if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getGemeenteaanvang() != null) {
                        existingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setGemeenteaanvang(
                            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getGemeenteaanvang()
                        );
                    }
                    if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getLandofgebiedaanvang() != null) {
                        existingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setLandofgebiedaanvang(
                            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getLandofgebiedaanvang()
                        );
                    }
                    if (sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getOmschrijvinglocatieaanvang() != null) {
                        existingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setOmschrijvinglocatieaanvang(
                            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getOmschrijvinglocatieaanvang()
                        );
                    }

                    return existingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap;
                })
                .map(sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps} : get all the sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps in body.
     */
    @GetMapping("")
    public List<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap> getAllSluitingofaangaanhuwelijkofgeregistreerdpartnerschaps() {
        log.debug("REST request to get all Sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps");
        return sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.findAll();
    }

    /**
     * {@code GET  /sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps/:id} : get the "id" sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.
     *
     * @param id the id of the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap> getSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(
        @PathVariable("id") Long id
    ) {
        log.debug("REST request to get Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap : {}", id);
        Optional<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap> sluitingofaangaanhuwelijkofgeregistreerdpartnerschap =
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);
    }

    /**
     * {@code DELETE  /sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps/:id} : delete the "id" sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.
     *
     * @param id the id of the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap : {}", id);
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
