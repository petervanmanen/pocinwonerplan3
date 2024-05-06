package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Brug;
import nl.ritense.demo.repository.BrugRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Brug}.
 */
@RestController
@RequestMapping("/api/brugs")
@Transactional
public class BrugResource {

    private final Logger log = LoggerFactory.getLogger(BrugResource.class);

    private static final String ENTITY_NAME = "brug";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BrugRepository brugRepository;

    public BrugResource(BrugRepository brugRepository) {
        this.brugRepository = brugRepository;
    }

    /**
     * {@code POST  /brugs} : Create a new brug.
     *
     * @param brug the brug to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new brug, or with status {@code 400 (Bad Request)} if the brug has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Brug> createBrug(@RequestBody Brug brug) throws URISyntaxException {
        log.debug("REST request to save Brug : {}", brug);
        if (brug.getId() != null) {
            throw new BadRequestAlertException("A new brug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        brug = brugRepository.save(brug);
        return ResponseEntity.created(new URI("/api/brugs/" + brug.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, brug.getId().toString()))
            .body(brug);
    }

    /**
     * {@code PUT  /brugs/:id} : Updates an existing brug.
     *
     * @param id the id of the brug to save.
     * @param brug the brug to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brug,
     * or with status {@code 400 (Bad Request)} if the brug is not valid,
     * or with status {@code 500 (Internal Server Error)} if the brug couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Brug> updateBrug(@PathVariable(value = "id", required = false) final Long id, @RequestBody Brug brug)
        throws URISyntaxException {
        log.debug("REST request to update Brug : {}, {}", id, brug);
        if (brug.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, brug.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!brugRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        brug = brugRepository.save(brug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, brug.getId().toString()))
            .body(brug);
    }

    /**
     * {@code PATCH  /brugs/:id} : Partial updates given fields of an existing brug, field will ignore if it is null
     *
     * @param id the id of the brug to save.
     * @param brug the brug to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brug,
     * or with status {@code 400 (Bad Request)} if the brug is not valid,
     * or with status {@code 404 (Not Found)} if the brug is not found,
     * or with status {@code 500 (Internal Server Error)} if the brug couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Brug> partialUpdateBrug(@PathVariable(value = "id", required = false) final Long id, @RequestBody Brug brug)
        throws URISyntaxException {
        log.debug("REST request to partial update Brug partially : {}, {}", id, brug);
        if (brug.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, brug.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!brugRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Brug> result = brugRepository
            .findById(brug.getId())
            .map(existingBrug -> {
                if (brug.getAantaloverspanningen() != null) {
                    existingBrug.setAantaloverspanningen(brug.getAantaloverspanningen());
                }
                if (brug.getBedienaar() != null) {
                    existingBrug.setBedienaar(brug.getBedienaar());
                }
                if (brug.getBedieningstijden() != null) {
                    existingBrug.setBedieningstijden(brug.getBedieningstijden());
                }
                if (brug.getBelastingklassenieuw() != null) {
                    existingBrug.setBelastingklassenieuw(brug.getBelastingklassenieuw());
                }
                if (brug.getBelastingklasseoud() != null) {
                    existingBrug.setBelastingklasseoud(brug.getBelastingklasseoud());
                }
                if (brug.getBeweegbaar() != null) {
                    existingBrug.setBeweegbaar(brug.getBeweegbaar());
                }
                if (brug.getDoorrijbreedte() != null) {
                    existingBrug.setDoorrijbreedte(brug.getDoorrijbreedte());
                }
                if (brug.getDraagvermogen() != null) {
                    existingBrug.setDraagvermogen(brug.getDraagvermogen());
                }
                if (brug.getHoofdroute() != null) {
                    existingBrug.setHoofdroute(brug.getHoofdroute());
                }
                if (brug.getHoofdvaarroute() != null) {
                    existingBrug.setHoofdvaarroute(brug.getHoofdvaarroute());
                }
                if (brug.getMaximaaltoelaatbaarvoertuiggewicht() != null) {
                    existingBrug.setMaximaaltoelaatbaarvoertuiggewicht(brug.getMaximaaltoelaatbaarvoertuiggewicht());
                }
                if (brug.getMaximaleasbelasting() != null) {
                    existingBrug.setMaximaleasbelasting(brug.getMaximaleasbelasting());
                }
                if (brug.getMaximaleoverspanning() != null) {
                    existingBrug.setMaximaleoverspanning(brug.getMaximaleoverspanning());
                }
                if (brug.getStatischmoment() != null) {
                    existingBrug.setStatischmoment(brug.getStatischmoment());
                }
                if (brug.getType() != null) {
                    existingBrug.setType(brug.getType());
                }
                if (brug.getTypeplus() != null) {
                    existingBrug.setTypeplus(brug.getTypeplus());
                }
                if (brug.getZwaarstevoertuig() != null) {
                    existingBrug.setZwaarstevoertuig(brug.getZwaarstevoertuig());
                }

                return existingBrug;
            })
            .map(brugRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, brug.getId().toString())
        );
    }

    /**
     * {@code GET  /brugs} : get all the brugs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of brugs in body.
     */
    @GetMapping("")
    public List<Brug> getAllBrugs() {
        log.debug("REST request to get all Brugs");
        return brugRepository.findAll();
    }

    /**
     * {@code GET  /brugs/:id} : get the "id" brug.
     *
     * @param id the id of the brug to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the brug, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Brug> getBrug(@PathVariable("id") Long id) {
        log.debug("REST request to get Brug : {}", id);
        Optional<Brug> brug = brugRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(brug);
    }

    /**
     * {@code DELETE  /brugs/:id} : delete the "id" brug.
     *
     * @param id the id of the brug to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrug(@PathVariable("id") Long id) {
        log.debug("REST request to delete Brug : {}", id);
        brugRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
