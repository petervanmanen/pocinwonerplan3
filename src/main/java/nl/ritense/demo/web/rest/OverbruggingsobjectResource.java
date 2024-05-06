package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overbruggingsobject;
import nl.ritense.demo.repository.OverbruggingsobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overbruggingsobject}.
 */
@RestController
@RequestMapping("/api/overbruggingsobjects")
@Transactional
public class OverbruggingsobjectResource {

    private final Logger log = LoggerFactory.getLogger(OverbruggingsobjectResource.class);

    private static final String ENTITY_NAME = "overbruggingsobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverbruggingsobjectRepository overbruggingsobjectRepository;

    public OverbruggingsobjectResource(OverbruggingsobjectRepository overbruggingsobjectRepository) {
        this.overbruggingsobjectRepository = overbruggingsobjectRepository;
    }

    /**
     * {@code POST  /overbruggingsobjects} : Create a new overbruggingsobject.
     *
     * @param overbruggingsobject the overbruggingsobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overbruggingsobject, or with status {@code 400 (Bad Request)} if the overbruggingsobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overbruggingsobject> createOverbruggingsobject(@RequestBody Overbruggingsobject overbruggingsobject)
        throws URISyntaxException {
        log.debug("REST request to save Overbruggingsobject : {}", overbruggingsobject);
        if (overbruggingsobject.getId() != null) {
            throw new BadRequestAlertException("A new overbruggingsobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        overbruggingsobject = overbruggingsobjectRepository.save(overbruggingsobject);
        return ResponseEntity.created(new URI("/api/overbruggingsobjects/" + overbruggingsobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, overbruggingsobject.getId().toString()))
            .body(overbruggingsobject);
    }

    /**
     * {@code PUT  /overbruggingsobjects/:id} : Updates an existing overbruggingsobject.
     *
     * @param id the id of the overbruggingsobject to save.
     * @param overbruggingsobject the overbruggingsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overbruggingsobject,
     * or with status {@code 400 (Bad Request)} if the overbruggingsobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overbruggingsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overbruggingsobject> updateOverbruggingsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overbruggingsobject overbruggingsobject
    ) throws URISyntaxException {
        log.debug("REST request to update Overbruggingsobject : {}, {}", id, overbruggingsobject);
        if (overbruggingsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overbruggingsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overbruggingsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overbruggingsobject = overbruggingsobjectRepository.save(overbruggingsobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overbruggingsobject.getId().toString()))
            .body(overbruggingsobject);
    }

    /**
     * {@code PATCH  /overbruggingsobjects/:id} : Partial updates given fields of an existing overbruggingsobject, field will ignore if it is null
     *
     * @param id the id of the overbruggingsobject to save.
     * @param overbruggingsobject the overbruggingsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overbruggingsobject,
     * or with status {@code 400 (Bad Request)} if the overbruggingsobject is not valid,
     * or with status {@code 404 (Not Found)} if the overbruggingsobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the overbruggingsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overbruggingsobject> partialUpdateOverbruggingsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overbruggingsobject overbruggingsobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Overbruggingsobject partially : {}, {}", id, overbruggingsobject);
        if (overbruggingsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overbruggingsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overbruggingsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overbruggingsobject> result = overbruggingsobjectRepository
            .findById(overbruggingsobject.getId())
            .map(existingOverbruggingsobject -> {
                if (overbruggingsobject.getAanleghoogte() != null) {
                    existingOverbruggingsobject.setAanleghoogte(overbruggingsobject.getAanleghoogte());
                }
                if (overbruggingsobject.getAntigraffitivoorziening() != null) {
                    existingOverbruggingsobject.setAntigraffitivoorziening(overbruggingsobject.getAntigraffitivoorziening());
                }
                if (overbruggingsobject.getBereikbaarheid() != null) {
                    existingOverbruggingsobject.setBereikbaarheid(overbruggingsobject.getBereikbaarheid());
                }
                if (overbruggingsobject.getBreedte() != null) {
                    existingOverbruggingsobject.setBreedte(overbruggingsobject.getBreedte());
                }
                if (overbruggingsobject.getHoogte() != null) {
                    existingOverbruggingsobject.setHoogte(overbruggingsobject.getHoogte());
                }
                if (overbruggingsobject.getInstallateur() != null) {
                    existingOverbruggingsobject.setInstallateur(overbruggingsobject.getInstallateur());
                }
                if (overbruggingsobject.getJaarconserveren() != null) {
                    existingOverbruggingsobject.setJaarconserveren(overbruggingsobject.getJaarconserveren());
                }
                if (overbruggingsobject.getJaaronderhouduitgevoerd() != null) {
                    existingOverbruggingsobject.setJaaronderhouduitgevoerd(overbruggingsobject.getJaaronderhouduitgevoerd());
                }
                if (overbruggingsobject.getJaarrenovatie() != null) {
                    existingOverbruggingsobject.setJaarrenovatie(overbruggingsobject.getJaarrenovatie());
                }
                if (overbruggingsobject.getJaarvervanging() != null) {
                    existingOverbruggingsobject.setJaarvervanging(overbruggingsobject.getJaarvervanging());
                }
                if (overbruggingsobject.getKleur() != null) {
                    existingOverbruggingsobject.setKleur(overbruggingsobject.getKleur());
                }
                if (overbruggingsobject.getKwaliteitsniveauactueel() != null) {
                    existingOverbruggingsobject.setKwaliteitsniveauactueel(overbruggingsobject.getKwaliteitsniveauactueel());
                }
                if (overbruggingsobject.getKwaliteitsniveaugewenst() != null) {
                    existingOverbruggingsobject.setKwaliteitsniveaugewenst(overbruggingsobject.getKwaliteitsniveaugewenst());
                }
                if (overbruggingsobject.getLengte() != null) {
                    existingOverbruggingsobject.setLengte(overbruggingsobject.getLengte());
                }
                if (overbruggingsobject.getLooprichel() != null) {
                    existingOverbruggingsobject.setLooprichel(overbruggingsobject.getLooprichel());
                }
                if (overbruggingsobject.getMinimumconditiescore() != null) {
                    existingOverbruggingsobject.setMinimumconditiescore(overbruggingsobject.getMinimumconditiescore());
                }
                if (overbruggingsobject.getOnderhoudsregime() != null) {
                    existingOverbruggingsobject.setOnderhoudsregime(overbruggingsobject.getOnderhoudsregime());
                }
                if (overbruggingsobject.getOppervlakte() != null) {
                    existingOverbruggingsobject.setOppervlakte(overbruggingsobject.getOppervlakte());
                }
                if (overbruggingsobject.getOverbruggingsobjectmateriaal() != null) {
                    existingOverbruggingsobject.setOverbruggingsobjectmateriaal(overbruggingsobject.getOverbruggingsobjectmateriaal());
                }
                if (overbruggingsobject.getOverbruggingsobjectmodaliteit() != null) {
                    existingOverbruggingsobject.setOverbruggingsobjectmodaliteit(overbruggingsobject.getOverbruggingsobjectmodaliteit());
                }
                if (overbruggingsobject.getTechnischelevensduur() != null) {
                    existingOverbruggingsobject.setTechnischelevensduur(overbruggingsobject.getTechnischelevensduur());
                }
                if (overbruggingsobject.getTypefundering() != null) {
                    existingOverbruggingsobject.setTypefundering(overbruggingsobject.getTypefundering());
                }
                if (overbruggingsobject.getVervangingswaarde() != null) {
                    existingOverbruggingsobject.setVervangingswaarde(overbruggingsobject.getVervangingswaarde());
                }

                return existingOverbruggingsobject;
            })
            .map(overbruggingsobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overbruggingsobject.getId().toString())
        );
    }

    /**
     * {@code GET  /overbruggingsobjects} : get all the overbruggingsobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overbruggingsobjects in body.
     */
    @GetMapping("")
    public List<Overbruggingsobject> getAllOverbruggingsobjects() {
        log.debug("REST request to get all Overbruggingsobjects");
        return overbruggingsobjectRepository.findAll();
    }

    /**
     * {@code GET  /overbruggingsobjects/:id} : get the "id" overbruggingsobject.
     *
     * @param id the id of the overbruggingsobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overbruggingsobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overbruggingsobject> getOverbruggingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Overbruggingsobject : {}", id);
        Optional<Overbruggingsobject> overbruggingsobject = overbruggingsobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overbruggingsobject);
    }

    /**
     * {@code DELETE  /overbruggingsobjects/:id} : delete the "id" overbruggingsobject.
     *
     * @param id the id of the overbruggingsobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverbruggingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overbruggingsobject : {}", id);
        overbruggingsobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
