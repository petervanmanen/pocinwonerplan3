package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Pomp;
import nl.ritense.demo.repository.PompRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Pomp}.
 */
@RestController
@RequestMapping("/api/pomps")
@Transactional
public class PompResource {

    private final Logger log = LoggerFactory.getLogger(PompResource.class);

    private static final String ENTITY_NAME = "pomp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PompRepository pompRepository;

    public PompResource(PompRepository pompRepository) {
        this.pompRepository = pompRepository;
    }

    /**
     * {@code POST  /pomps} : Create a new pomp.
     *
     * @param pomp the pomp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pomp, or with status {@code 400 (Bad Request)} if the pomp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pomp> createPomp(@RequestBody Pomp pomp) throws URISyntaxException {
        log.debug("REST request to save Pomp : {}", pomp);
        if (pomp.getId() != null) {
            throw new BadRequestAlertException("A new pomp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pomp = pompRepository.save(pomp);
        return ResponseEntity.created(new URI("/api/pomps/" + pomp.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, pomp.getId().toString()))
            .body(pomp);
    }

    /**
     * {@code PUT  /pomps/:id} : Updates an existing pomp.
     *
     * @param id the id of the pomp to save.
     * @param pomp the pomp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pomp,
     * or with status {@code 400 (Bad Request)} if the pomp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pomp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pomp> updatePomp(@PathVariable(value = "id", required = false) final Long id, @RequestBody Pomp pomp)
        throws URISyntaxException {
        log.debug("REST request to update Pomp : {}, {}", id, pomp);
        if (pomp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pomp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pompRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pomp = pompRepository.save(pomp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pomp.getId().toString()))
            .body(pomp);
    }

    /**
     * {@code PATCH  /pomps/:id} : Partial updates given fields of an existing pomp, field will ignore if it is null
     *
     * @param id the id of the pomp to save.
     * @param pomp the pomp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pomp,
     * or with status {@code 400 (Bad Request)} if the pomp is not valid,
     * or with status {@code 404 (Not Found)} if the pomp is not found,
     * or with status {@code 500 (Internal Server Error)} if the pomp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pomp> partialUpdatePomp(@PathVariable(value = "id", required = false) final Long id, @RequestBody Pomp pomp)
        throws URISyntaxException {
        log.debug("REST request to partial update Pomp partially : {}, {}", id, pomp);
        if (pomp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pomp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pompRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pomp> result = pompRepository
            .findById(pomp.getId())
            .map(existingPomp -> {
                if (pomp.getAanslagniveau() != null) {
                    existingPomp.setAanslagniveau(pomp.getAanslagniveau());
                }
                if (pomp.getBeginstanddraaiurenteller() != null) {
                    existingPomp.setBeginstanddraaiurenteller(pomp.getBeginstanddraaiurenteller());
                }
                if (pomp.getBesturingskast() != null) {
                    existingPomp.setBesturingskast(pomp.getBesturingskast());
                }
                if (pomp.getLaatstestanddraaiurenteller() != null) {
                    existingPomp.setLaatstestanddraaiurenteller(pomp.getLaatstestanddraaiurenteller());
                }
                if (pomp.getLaatstestandkwhteller() != null) {
                    existingPomp.setLaatstestandkwhteller(pomp.getLaatstestandkwhteller());
                }
                if (pomp.getLevensduur() != null) {
                    existingPomp.setLevensduur(pomp.getLevensduur());
                }
                if (pomp.getModel() != null) {
                    existingPomp.setModel(pomp.getModel());
                }
                if (pomp.getMotorvermogen() != null) {
                    existingPomp.setMotorvermogen(pomp.getMotorvermogen());
                }
                if (pomp.getOnderdeelmetpomp() != null) {
                    existingPomp.setOnderdeelmetpomp(pomp.getOnderdeelmetpomp());
                }
                if (pomp.getOntwerpcapaciteit() != null) {
                    existingPomp.setOntwerpcapaciteit(pomp.getOntwerpcapaciteit());
                }
                if (pomp.getPompcapaciteit() != null) {
                    existingPomp.setPompcapaciteit(pomp.getPompcapaciteit());
                }
                if (pomp.getSerienummer() != null) {
                    existingPomp.setSerienummer(pomp.getSerienummer());
                }
                if (pomp.getType() != null) {
                    existingPomp.setType(pomp.getType());
                }
                if (pomp.getTypeonderdeelmetpomp() != null) {
                    existingPomp.setTypeonderdeelmetpomp(pomp.getTypeonderdeelmetpomp());
                }
                if (pomp.getTypeplus() != null) {
                    existingPomp.setTypeplus(pomp.getTypeplus());
                }
                if (pomp.getTypewaaier() != null) {
                    existingPomp.setTypewaaier(pomp.getTypewaaier());
                }
                if (pomp.getUitslagpeil() != null) {
                    existingPomp.setUitslagpeil(pomp.getUitslagpeil());
                }

                return existingPomp;
            })
            .map(pompRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pomp.getId().toString())
        );
    }

    /**
     * {@code GET  /pomps} : get all the pomps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pomps in body.
     */
    @GetMapping("")
    public List<Pomp> getAllPomps() {
        log.debug("REST request to get all Pomps");
        return pompRepository.findAll();
    }

    /**
     * {@code GET  /pomps/:id} : get the "id" pomp.
     *
     * @param id the id of the pomp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pomp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pomp> getPomp(@PathVariable("id") Long id) {
        log.debug("REST request to get Pomp : {}", id);
        Optional<Pomp> pomp = pompRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pomp);
    }

    /**
     * {@code DELETE  /pomps/:id} : delete the "id" pomp.
     *
     * @param id the id of the pomp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePomp(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pomp : {}", id);
        pompRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
