package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Leiding;
import nl.ritense.demo.repository.LeidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leiding}.
 */
@RestController
@RequestMapping("/api/leidings")
@Transactional
public class LeidingResource {

    private final Logger log = LoggerFactory.getLogger(LeidingResource.class);

    private static final String ENTITY_NAME = "leiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeidingRepository leidingRepository;

    public LeidingResource(LeidingRepository leidingRepository) {
        this.leidingRepository = leidingRepository;
    }

    /**
     * {@code POST  /leidings} : Create a new leiding.
     *
     * @param leiding the leiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leiding, or with status {@code 400 (Bad Request)} if the leiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leiding> createLeiding(@RequestBody Leiding leiding) throws URISyntaxException {
        log.debug("REST request to save Leiding : {}", leiding);
        if (leiding.getId() != null) {
            throw new BadRequestAlertException("A new leiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leiding = leidingRepository.save(leiding);
        return ResponseEntity.created(new URI("/api/leidings/" + leiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leiding.getId().toString()))
            .body(leiding);
    }

    /**
     * {@code PUT  /leidings/:id} : Updates an existing leiding.
     *
     * @param id the id of the leiding to save.
     * @param leiding the leiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leiding,
     * or with status {@code 400 (Bad Request)} if the leiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Leiding> updateLeiding(@PathVariable(value = "id", required = false) final Long id, @RequestBody Leiding leiding)
        throws URISyntaxException {
        log.debug("REST request to update Leiding : {}, {}", id, leiding);
        if (leiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        leiding = leidingRepository.save(leiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leiding.getId().toString()))
            .body(leiding);
    }

    /**
     * {@code PATCH  /leidings/:id} : Partial updates given fields of an existing leiding, field will ignore if it is null
     *
     * @param id the id of the leiding to save.
     * @param leiding the leiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leiding,
     * or with status {@code 400 (Bad Request)} if the leiding is not valid,
     * or with status {@code 404 (Not Found)} if the leiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the leiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Leiding> partialUpdateLeiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Leiding leiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leiding partially : {}, {}", id, leiding);
        if (leiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Leiding> result = leidingRepository
            .findById(leiding.getId())
            .map(existingLeiding -> {
                if (leiding.getAfwijkendedieptelegging() != null) {
                    existingLeiding.setAfwijkendedieptelegging(leiding.getAfwijkendedieptelegging());
                }
                if (leiding.getBreedte() != null) {
                    existingLeiding.setBreedte(leiding.getBreedte());
                }
                if (leiding.getDiameter() != null) {
                    existingLeiding.setDiameter(leiding.getDiameter());
                }
                if (leiding.getDiepte() != null) {
                    existingLeiding.setDiepte(leiding.getDiepte());
                }
                if (leiding.getEisvoorzorgsmaatregel() != null) {
                    existingLeiding.setEisvoorzorgsmaatregel(leiding.getEisvoorzorgsmaatregel());
                }
                if (leiding.getGeonauwkeurigheidxy() != null) {
                    existingLeiding.setGeonauwkeurigheidxy(leiding.getGeonauwkeurigheidxy());
                }
                if (leiding.getHoogte() != null) {
                    existingLeiding.setHoogte(leiding.getHoogte());
                }
                if (leiding.getJaaronderhouduitgevoerd() != null) {
                    existingLeiding.setJaaronderhouduitgevoerd(leiding.getJaaronderhouduitgevoerd());
                }
                if (leiding.getLengte() != null) {
                    existingLeiding.setLengte(leiding.getLengte());
                }
                if (leiding.getLeverancier() != null) {
                    existingLeiding.setLeverancier(leiding.getLeverancier());
                }
                if (leiding.getMateriaal() != null) {
                    existingLeiding.setMateriaal(leiding.getMateriaal());
                }
                if (leiding.getThemaimkl() != null) {
                    existingLeiding.setThemaimkl(leiding.getThemaimkl());
                }
                if (leiding.getVerhoogdrisico() != null) {
                    existingLeiding.setVerhoogdrisico(leiding.getVerhoogdrisico());
                }

                return existingLeiding;
            })
            .map(leidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leiding.getId().toString())
        );
    }

    /**
     * {@code GET  /leidings} : get all the leidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leidings in body.
     */
    @GetMapping("")
    public List<Leiding> getAllLeidings() {
        log.debug("REST request to get all Leidings");
        return leidingRepository.findAll();
    }

    /**
     * {@code GET  /leidings/:id} : get the "id" leiding.
     *
     * @param id the id of the leiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leiding> getLeiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Leiding : {}", id);
        Optional<Leiding> leiding = leidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leiding);
    }

    /**
     * {@code DELETE  /leidings/:id} : delete the "id" leiding.
     *
     * @param id the id of the leiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leiding : {}", id);
        leidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
