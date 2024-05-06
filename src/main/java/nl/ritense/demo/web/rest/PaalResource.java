package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Paal;
import nl.ritense.demo.repository.PaalRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Paal}.
 */
@RestController
@RequestMapping("/api/paals")
@Transactional
public class PaalResource {

    private final Logger log = LoggerFactory.getLogger(PaalResource.class);

    private static final String ENTITY_NAME = "paal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaalRepository paalRepository;

    public PaalResource(PaalRepository paalRepository) {
        this.paalRepository = paalRepository;
    }

    /**
     * {@code POST  /paals} : Create a new paal.
     *
     * @param paal the paal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paal, or with status {@code 400 (Bad Request)} if the paal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Paal> createPaal(@RequestBody Paal paal) throws URISyntaxException {
        log.debug("REST request to save Paal : {}", paal);
        if (paal.getId() != null) {
            throw new BadRequestAlertException("A new paal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paal = paalRepository.save(paal);
        return ResponseEntity.created(new URI("/api/paals/" + paal.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, paal.getId().toString()))
            .body(paal);
    }

    /**
     * {@code PUT  /paals/:id} : Updates an existing paal.
     *
     * @param id the id of the paal to save.
     * @param paal the paal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paal,
     * or with status {@code 400 (Bad Request)} if the paal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paal> updatePaal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Paal paal)
        throws URISyntaxException {
        log.debug("REST request to update Paal : {}, {}", id, paal);
        if (paal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paal = paalRepository.save(paal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paal.getId().toString()))
            .body(paal);
    }

    /**
     * {@code PATCH  /paals/:id} : Partial updates given fields of an existing paal, field will ignore if it is null
     *
     * @param id the id of the paal to save.
     * @param paal the paal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paal,
     * or with status {@code 400 (Bad Request)} if the paal is not valid,
     * or with status {@code 404 (Not Found)} if the paal is not found,
     * or with status {@code 500 (Internal Server Error)} if the paal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Paal> partialUpdatePaal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Paal paal)
        throws URISyntaxException {
        log.debug("REST request to partial update Paal partially : {}, {}", id, paal);
        if (paal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Paal> result = paalRepository
            .findById(paal.getId())
            .map(existingPaal -> {
                if (paal.getBreedte() != null) {
                    existingPaal.setBreedte(paal.getBreedte());
                }
                if (paal.getDiameter() != null) {
                    existingPaal.setDiameter(paal.getDiameter());
                }
                if (paal.getHoogte() != null) {
                    existingPaal.setHoogte(paal.getHoogte());
                }
                if (paal.getJaaronderhouduitgevoerd() != null) {
                    existingPaal.setJaaronderhouduitgevoerd(paal.getJaaronderhouduitgevoerd());
                }
                if (paal.getKwaliteitsniveauactueel() != null) {
                    existingPaal.setKwaliteitsniveauactueel(paal.getKwaliteitsniveauactueel());
                }
                if (paal.getKwaliteitsniveaugewenst() != null) {
                    existingPaal.setKwaliteitsniveaugewenst(paal.getKwaliteitsniveaugewenst());
                }
                if (paal.getLengte() != null) {
                    existingPaal.setLengte(paal.getLengte());
                }
                if (paal.getLeverancier() != null) {
                    existingPaal.setLeverancier(paal.getLeverancier());
                }
                if (paal.getMateriaal() != null) {
                    existingPaal.setMateriaal(paal.getMateriaal());
                }
                if (paal.getVorm() != null) {
                    existingPaal.setVorm(paal.getVorm());
                }

                return existingPaal;
            })
            .map(paalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paal.getId().toString())
        );
    }

    /**
     * {@code GET  /paals} : get all the paals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paals in body.
     */
    @GetMapping("")
    public List<Paal> getAllPaals() {
        log.debug("REST request to get all Paals");
        return paalRepository.findAll();
    }

    /**
     * {@code GET  /paals/:id} : get the "id" paal.
     *
     * @param id the id of the paal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paal> getPaal(@PathVariable("id") Long id) {
        log.debug("REST request to get Paal : {}", id);
        Optional<Paal> paal = paalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paal);
    }

    /**
     * {@code DELETE  /paals/:id} : delete the "id" paal.
     *
     * @param id the id of the paal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaal(@PathVariable("id") Long id) {
        log.debug("REST request to delete Paal : {}", id);
        paalRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
