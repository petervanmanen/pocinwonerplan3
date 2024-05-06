package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Moormelding;
import nl.ritense.demo.repository.MoormeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Moormelding}.
 */
@RestController
@RequestMapping("/api/moormeldings")
@Transactional
public class MoormeldingResource {

    private final Logger log = LoggerFactory.getLogger(MoormeldingResource.class);

    private static final String ENTITY_NAME = "moormelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoormeldingRepository moormeldingRepository;

    public MoormeldingResource(MoormeldingRepository moormeldingRepository) {
        this.moormeldingRepository = moormeldingRepository;
    }

    /**
     * {@code POST  /moormeldings} : Create a new moormelding.
     *
     * @param moormelding the moormelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moormelding, or with status {@code 400 (Bad Request)} if the moormelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Moormelding> createMoormelding(@RequestBody Moormelding moormelding) throws URISyntaxException {
        log.debug("REST request to save Moormelding : {}", moormelding);
        if (moormelding.getId() != null) {
            throw new BadRequestAlertException("A new moormelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        moormelding = moormeldingRepository.save(moormelding);
        return ResponseEntity.created(new URI("/api/moormeldings/" + moormelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, moormelding.getId().toString()))
            .body(moormelding);
    }

    /**
     * {@code PUT  /moormeldings/:id} : Updates an existing moormelding.
     *
     * @param id the id of the moormelding to save.
     * @param moormelding the moormelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moormelding,
     * or with status {@code 400 (Bad Request)} if the moormelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moormelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Moormelding> updateMoormelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Moormelding moormelding
    ) throws URISyntaxException {
        log.debug("REST request to update Moormelding : {}, {}", id, moormelding);
        if (moormelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moormelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moormeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        moormelding = moormeldingRepository.save(moormelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moormelding.getId().toString()))
            .body(moormelding);
    }

    /**
     * {@code PATCH  /moormeldings/:id} : Partial updates given fields of an existing moormelding, field will ignore if it is null
     *
     * @param id the id of the moormelding to save.
     * @param moormelding the moormelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moormelding,
     * or with status {@code 400 (Bad Request)} if the moormelding is not valid,
     * or with status {@code 404 (Not Found)} if the moormelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the moormelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Moormelding> partialUpdateMoormelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Moormelding moormelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Moormelding partially : {}, {}", id, moormelding);
        if (moormelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moormelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moormeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Moormelding> result = moormeldingRepository
            .findById(moormelding.getId())
            .map(existingMoormelding -> {
                if (moormelding.getAdresaanduiding() != null) {
                    existingMoormelding.setAdresaanduiding(moormelding.getAdresaanduiding());
                }
                if (moormelding.getDatumaanmelding() != null) {
                    existingMoormelding.setDatumaanmelding(moormelding.getDatumaanmelding());
                }
                if (moormelding.getDatumgoedkeuring() != null) {
                    existingMoormelding.setDatumgoedkeuring(moormelding.getDatumgoedkeuring());
                }
                if (moormelding.getEindtijd() != null) {
                    existingMoormelding.setEindtijd(moormelding.getEindtijd());
                }
                if (moormelding.getGoedgekeurd() != null) {
                    existingMoormelding.setGoedgekeurd(moormelding.getGoedgekeurd());
                }
                if (moormelding.getHerstelwerkzaamhedenvereist() != null) {
                    existingMoormelding.setHerstelwerkzaamhedenvereist(moormelding.getHerstelwerkzaamhedenvereist());
                }
                if (moormelding.getOmschrijvingherstelwerkzaamheden() != null) {
                    existingMoormelding.setOmschrijvingherstelwerkzaamheden(moormelding.getOmschrijvingherstelwerkzaamheden());
                }
                if (moormelding.getPubliceren() != null) {
                    existingMoormelding.setPubliceren(moormelding.getPubliceren());
                }
                if (moormelding.getStarttijd() != null) {
                    existingMoormelding.setStarttijd(moormelding.getStarttijd());
                }
                if (moormelding.getWegbeheerder() != null) {
                    existingMoormelding.setWegbeheerder(moormelding.getWegbeheerder());
                }

                return existingMoormelding;
            })
            .map(moormeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moormelding.getId().toString())
        );
    }

    /**
     * {@code GET  /moormeldings} : get all the moormeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moormeldings in body.
     */
    @GetMapping("")
    public List<Moormelding> getAllMoormeldings() {
        log.debug("REST request to get all Moormeldings");
        return moormeldingRepository.findAll();
    }

    /**
     * {@code GET  /moormeldings/:id} : get the "id" moormelding.
     *
     * @param id the id of the moormelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moormelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Moormelding> getMoormelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Moormelding : {}", id);
        Optional<Moormelding> moormelding = moormeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(moormelding);
    }

    /**
     * {@code DELETE  /moormeldings/:id} : delete the "id" moormelding.
     *
     * @param id the id of the moormelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoormelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Moormelding : {}", id);
        moormeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
