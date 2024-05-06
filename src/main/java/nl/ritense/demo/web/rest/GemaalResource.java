package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gemaal;
import nl.ritense.demo.repository.GemaalRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gemaal}.
 */
@RestController
@RequestMapping("/api/gemaals")
@Transactional
public class GemaalResource {

    private final Logger log = LoggerFactory.getLogger(GemaalResource.class);

    private static final String ENTITY_NAME = "gemaal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GemaalRepository gemaalRepository;

    public GemaalResource(GemaalRepository gemaalRepository) {
        this.gemaalRepository = gemaalRepository;
    }

    /**
     * {@code POST  /gemaals} : Create a new gemaal.
     *
     * @param gemaal the gemaal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gemaal, or with status {@code 400 (Bad Request)} if the gemaal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gemaal> createGemaal(@RequestBody Gemaal gemaal) throws URISyntaxException {
        log.debug("REST request to save Gemaal : {}", gemaal);
        if (gemaal.getId() != null) {
            throw new BadRequestAlertException("A new gemaal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gemaal = gemaalRepository.save(gemaal);
        return ResponseEntity.created(new URI("/api/gemaals/" + gemaal.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gemaal.getId().toString()))
            .body(gemaal);
    }

    /**
     * {@code PUT  /gemaals/:id} : Updates an existing gemaal.
     *
     * @param id the id of the gemaal to save.
     * @param gemaal the gemaal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemaal,
     * or with status {@code 400 (Bad Request)} if the gemaal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gemaal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gemaal> updateGemaal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Gemaal gemaal)
        throws URISyntaxException {
        log.debug("REST request to update Gemaal : {}, {}", id, gemaal);
        if (gemaal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemaal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemaalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gemaal = gemaalRepository.save(gemaal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gemaal.getId().toString()))
            .body(gemaal);
    }

    /**
     * {@code PATCH  /gemaals/:id} : Partial updates given fields of an existing gemaal, field will ignore if it is null
     *
     * @param id the id of the gemaal to save.
     * @param gemaal the gemaal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemaal,
     * or with status {@code 400 (Bad Request)} if the gemaal is not valid,
     * or with status {@code 404 (Not Found)} if the gemaal is not found,
     * or with status {@code 500 (Internal Server Error)} if the gemaal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gemaal> partialUpdateGemaal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gemaal gemaal
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gemaal partially : {}, {}", id, gemaal);
        if (gemaal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemaal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemaalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gemaal> result = gemaalRepository
            .findById(gemaal.getId())
            .map(existingGemaal -> {
                if (gemaal.getAantalbedrijfsaansluitingen() != null) {
                    existingGemaal.setAantalbedrijfsaansluitingen(gemaal.getAantalbedrijfsaansluitingen());
                }
                if (gemaal.getAantalhuisaansluitingen() != null) {
                    existingGemaal.setAantalhuisaansluitingen(gemaal.getAantalhuisaansluitingen());
                }
                if (gemaal.getAantalpompen() != null) {
                    existingGemaal.setAantalpompen(gemaal.getAantalpompen());
                }
                if (gemaal.getBedienaar() != null) {
                    existingGemaal.setBedienaar(gemaal.getBedienaar());
                }
                if (gemaal.getEffectievegemaalcapaciteit() != null) {
                    existingGemaal.setEffectievegemaalcapaciteit(gemaal.getEffectievegemaalcapaciteit());
                }
                if (gemaal.getHijsinrichting() != null) {
                    existingGemaal.setHijsinrichting(gemaal.getHijsinrichting());
                }
                if (gemaal.getLanceerinrichting() != null) {
                    existingGemaal.setLanceerinrichting(gemaal.getLanceerinrichting());
                }
                if (gemaal.getPompeninsamenloop() != null) {
                    existingGemaal.setPompeninsamenloop(gemaal.getPompeninsamenloop());
                }
                if (gemaal.getType() != null) {
                    existingGemaal.setType(gemaal.getType());
                }
                if (gemaal.getVeiligheidsrooster() != null) {
                    existingGemaal.setVeiligheidsrooster(gemaal.getVeiligheidsrooster());
                }

                return existingGemaal;
            })
            .map(gemaalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gemaal.getId().toString())
        );
    }

    /**
     * {@code GET  /gemaals} : get all the gemaals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gemaals in body.
     */
    @GetMapping("")
    public List<Gemaal> getAllGemaals() {
        log.debug("REST request to get all Gemaals");
        return gemaalRepository.findAll();
    }

    /**
     * {@code GET  /gemaals/:id} : get the "id" gemaal.
     *
     * @param id the id of the gemaal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gemaal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gemaal> getGemaal(@PathVariable("id") Long id) {
        log.debug("REST request to get Gemaal : {}", id);
        Optional<Gemaal> gemaal = gemaalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gemaal);
    }

    /**
     * {@code DELETE  /gemaals/:id} : delete the "id" gemaal.
     *
     * @param id the id of the gemaal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGemaal(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gemaal : {}", id);
        gemaalRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
