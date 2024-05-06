package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Collegelid;
import nl.ritense.demo.repository.CollegelidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Collegelid}.
 */
@RestController
@RequestMapping("/api/collegelids")
@Transactional
public class CollegelidResource {

    private final Logger log = LoggerFactory.getLogger(CollegelidResource.class);

    private static final String ENTITY_NAME = "collegelid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollegelidRepository collegelidRepository;

    public CollegelidResource(CollegelidRepository collegelidRepository) {
        this.collegelidRepository = collegelidRepository;
    }

    /**
     * {@code POST  /collegelids} : Create a new collegelid.
     *
     * @param collegelid the collegelid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collegelid, or with status {@code 400 (Bad Request)} if the collegelid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Collegelid> createCollegelid(@RequestBody Collegelid collegelid) throws URISyntaxException {
        log.debug("REST request to save Collegelid : {}", collegelid);
        if (collegelid.getId() != null) {
            throw new BadRequestAlertException("A new collegelid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        collegelid = collegelidRepository.save(collegelid);
        return ResponseEntity.created(new URI("/api/collegelids/" + collegelid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, collegelid.getId().toString()))
            .body(collegelid);
    }

    /**
     * {@code PUT  /collegelids/:id} : Updates an existing collegelid.
     *
     * @param id the id of the collegelid to save.
     * @param collegelid the collegelid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collegelid,
     * or with status {@code 400 (Bad Request)} if the collegelid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collegelid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Collegelid> updateCollegelid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Collegelid collegelid
    ) throws URISyntaxException {
        log.debug("REST request to update Collegelid : {}, {}", id, collegelid);
        if (collegelid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collegelid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collegelidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        collegelid = collegelidRepository.save(collegelid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collegelid.getId().toString()))
            .body(collegelid);
    }

    /**
     * {@code PATCH  /collegelids/:id} : Partial updates given fields of an existing collegelid, field will ignore if it is null
     *
     * @param id the id of the collegelid to save.
     * @param collegelid the collegelid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collegelid,
     * or with status {@code 400 (Bad Request)} if the collegelid is not valid,
     * or with status {@code 404 (Not Found)} if the collegelid is not found,
     * or with status {@code 500 (Internal Server Error)} if the collegelid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Collegelid> partialUpdateCollegelid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Collegelid collegelid
    ) throws URISyntaxException {
        log.debug("REST request to partial update Collegelid partially : {}, {}", id, collegelid);
        if (collegelid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collegelid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collegelidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Collegelid> result = collegelidRepository
            .findById(collegelid.getId())
            .map(existingCollegelid -> {
                if (collegelid.getAchternaam() != null) {
                    existingCollegelid.setAchternaam(collegelid.getAchternaam());
                }
                if (collegelid.getDatumaanstelling() != null) {
                    existingCollegelid.setDatumaanstelling(collegelid.getDatumaanstelling());
                }
                if (collegelid.getDatumuittreding() != null) {
                    existingCollegelid.setDatumuittreding(collegelid.getDatumuittreding());
                }
                if (collegelid.getFractie() != null) {
                    existingCollegelid.setFractie(collegelid.getFractie());
                }
                if (collegelid.getPortefeuille() != null) {
                    existingCollegelid.setPortefeuille(collegelid.getPortefeuille());
                }
                if (collegelid.getTitel() != null) {
                    existingCollegelid.setTitel(collegelid.getTitel());
                }
                if (collegelid.getVoornaam() != null) {
                    existingCollegelid.setVoornaam(collegelid.getVoornaam());
                }

                return existingCollegelid;
            })
            .map(collegelidRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collegelid.getId().toString())
        );
    }

    /**
     * {@code GET  /collegelids} : get all the collegelids.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collegelids in body.
     */
    @GetMapping("")
    public List<Collegelid> getAllCollegelids(@RequestParam(name = "filter", required = false) String filter) {
        if ("isindiener-is-null".equals(filter)) {
            log.debug("REST request to get all Collegelids where isIndiener is null");
            return StreamSupport.stream(collegelidRepository.findAll().spliterator(), false)
                .filter(collegelid -> collegelid.getIsIndiener() == null)
                .toList();
        }
        log.debug("REST request to get all Collegelids");
        return collegelidRepository.findAll();
    }

    /**
     * {@code GET  /collegelids/:id} : get the "id" collegelid.
     *
     * @param id the id of the collegelid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collegelid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Collegelid> getCollegelid(@PathVariable("id") Long id) {
        log.debug("REST request to get Collegelid : {}", id);
        Optional<Collegelid> collegelid = collegelidRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(collegelid);
    }

    /**
     * {@code DELETE  /collegelids/:id} : delete the "id" collegelid.
     *
     * @param id the id of the collegelid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollegelid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Collegelid : {}", id);
        collegelidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
