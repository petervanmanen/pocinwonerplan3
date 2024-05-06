package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Lstclass1;
import nl.ritense.demo.repository.Lstclass1Repository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Lstclass1}.
 */
@RestController
@RequestMapping("/api/lstclass-1-s")
@Transactional
public class Lstclass1Resource {

    private final Logger log = LoggerFactory.getLogger(Lstclass1Resource.class);

    private static final String ENTITY_NAME = "lstclass1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Lstclass1Repository lstclass1Repository;

    public Lstclass1Resource(Lstclass1Repository lstclass1Repository) {
        this.lstclass1Repository = lstclass1Repository;
    }

    /**
     * {@code POST  /lstclass-1-s} : Create a new lstclass1.
     *
     * @param lstclass1 the lstclass1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lstclass1, or with status {@code 400 (Bad Request)} if the lstclass1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Lstclass1> createLstclass1(@RequestBody Lstclass1 lstclass1) throws URISyntaxException {
        log.debug("REST request to save Lstclass1 : {}", lstclass1);
        if (lstclass1.getId() != null) {
            throw new BadRequestAlertException("A new lstclass1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lstclass1 = lstclass1Repository.save(lstclass1);
        return ResponseEntity.created(new URI("/api/lstclass-1-s/" + lstclass1.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lstclass1.getId().toString()))
            .body(lstclass1);
    }

    /**
     * {@code PUT  /lstclass-1-s/:id} : Updates an existing lstclass1.
     *
     * @param id the id of the lstclass1 to save.
     * @param lstclass1 the lstclass1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lstclass1,
     * or with status {@code 400 (Bad Request)} if the lstclass1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lstclass1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lstclass1> updateLstclass1(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Lstclass1 lstclass1
    ) throws URISyntaxException {
        log.debug("REST request to update Lstclass1 : {}, {}", id, lstclass1);
        if (lstclass1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lstclass1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lstclass1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lstclass1 = lstclass1Repository.save(lstclass1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lstclass1.getId().toString()))
            .body(lstclass1);
    }

    /**
     * {@code PATCH  /lstclass-1-s/:id} : Partial updates given fields of an existing lstclass1, field will ignore if it is null
     *
     * @param id the id of the lstclass1 to save.
     * @param lstclass1 the lstclass1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lstclass1,
     * or with status {@code 400 (Bad Request)} if the lstclass1 is not valid,
     * or with status {@code 404 (Not Found)} if the lstclass1 is not found,
     * or with status {@code 500 (Internal Server Error)} if the lstclass1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Lstclass1> partialUpdateLstclass1(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Lstclass1 lstclass1
    ) throws URISyntaxException {
        log.debug("REST request to partial update Lstclass1 partially : {}, {}", id, lstclass1);
        if (lstclass1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lstclass1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lstclass1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Lstclass1> result = lstclass1Repository
            .findById(lstclass1.getId())
            .map(existingLstclass1 -> {
                if (lstclass1.getWaarde() != null) {
                    existingLstclass1.setWaarde(lstclass1.getWaarde());
                }
                if (lstclass1.getDwhrecordid() != null) {
                    existingLstclass1.setDwhrecordid(lstclass1.getDwhrecordid());
                }
                if (lstclass1.getDwhodsrecordid() != null) {
                    existingLstclass1.setDwhodsrecordid(lstclass1.getDwhodsrecordid());
                }
                if (lstclass1.getDwhstartdt() != null) {
                    existingLstclass1.setDwhstartdt(lstclass1.getDwhstartdt());
                }
                if (lstclass1.getDwheinddt() != null) {
                    existingLstclass1.setDwheinddt(lstclass1.getDwheinddt());
                }
                if (lstclass1.getDwhrunid() != null) {
                    existingLstclass1.setDwhrunid(lstclass1.getDwhrunid());
                }
                if (lstclass1.getDwhbron() != null) {
                    existingLstclass1.setDwhbron(lstclass1.getDwhbron());
                }
                if (lstclass1.getDwhlaaddt() != null) {
                    existingLstclass1.setDwhlaaddt(lstclass1.getDwhlaaddt());
                }
                if (lstclass1.getDwhactueel() != null) {
                    existingLstclass1.setDwhactueel(lstclass1.getDwhactueel());
                }
                if (lstclass1.getLstclass1id() != null) {
                    existingLstclass1.setLstclass1id(lstclass1.getLstclass1id());
                }

                return existingLstclass1;
            })
            .map(lstclass1Repository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lstclass1.getId().toString())
        );
    }

    /**
     * {@code GET  /lstclass-1-s} : get all the lstclass1s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lstclass1s in body.
     */
    @GetMapping("")
    public List<Lstclass1> getAllLstclass1s() {
        log.debug("REST request to get all Lstclass1s");
        return lstclass1Repository.findAll();
    }

    /**
     * {@code GET  /lstclass-1-s/:id} : get the "id" lstclass1.
     *
     * @param id the id of the lstclass1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lstclass1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lstclass1> getLstclass1(@PathVariable("id") Long id) {
        log.debug("REST request to get Lstclass1 : {}", id);
        Optional<Lstclass1> lstclass1 = lstclass1Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(lstclass1);
    }

    /**
     * {@code DELETE  /lstclass-1-s/:id} : delete the "id" lstclass1.
     *
     * @param id the id of the lstclass1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLstclass1(@PathVariable("id") Long id) {
        log.debug("REST request to delete Lstclass1 : {}", id);
        lstclass1Repository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
