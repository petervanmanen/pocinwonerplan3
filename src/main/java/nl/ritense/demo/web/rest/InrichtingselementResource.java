package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Inrichtingselement;
import nl.ritense.demo.repository.InrichtingselementRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inrichtingselement}.
 */
@RestController
@RequestMapping("/api/inrichtingselements")
@Transactional
public class InrichtingselementResource {

    private final Logger log = LoggerFactory.getLogger(InrichtingselementResource.class);

    private static final String ENTITY_NAME = "inrichtingselement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InrichtingselementRepository inrichtingselementRepository;

    public InrichtingselementResource(InrichtingselementRepository inrichtingselementRepository) {
        this.inrichtingselementRepository = inrichtingselementRepository;
    }

    /**
     * {@code POST  /inrichtingselements} : Create a new inrichtingselement.
     *
     * @param inrichtingselement the inrichtingselement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inrichtingselement, or with status {@code 400 (Bad Request)} if the inrichtingselement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inrichtingselement> createInrichtingselement(@RequestBody Inrichtingselement inrichtingselement)
        throws URISyntaxException {
        log.debug("REST request to save Inrichtingselement : {}", inrichtingselement);
        if (inrichtingselement.getId() != null) {
            throw new BadRequestAlertException("A new inrichtingselement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inrichtingselement = inrichtingselementRepository.save(inrichtingselement);
        return ResponseEntity.created(new URI("/api/inrichtingselements/" + inrichtingselement.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inrichtingselement.getId().toString()))
            .body(inrichtingselement);
    }

    /**
     * {@code PUT  /inrichtingselements/:id} : Updates an existing inrichtingselement.
     *
     * @param id the id of the inrichtingselement to save.
     * @param inrichtingselement the inrichtingselement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inrichtingselement,
     * or with status {@code 400 (Bad Request)} if the inrichtingselement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inrichtingselement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inrichtingselement> updateInrichtingselement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inrichtingselement inrichtingselement
    ) throws URISyntaxException {
        log.debug("REST request to update Inrichtingselement : {}, {}", id, inrichtingselement);
        if (inrichtingselement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inrichtingselement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inrichtingselementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inrichtingselement = inrichtingselementRepository.save(inrichtingselement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inrichtingselement.getId().toString()))
            .body(inrichtingselement);
    }

    /**
     * {@code PATCH  /inrichtingselements/:id} : Partial updates given fields of an existing inrichtingselement, field will ignore if it is null
     *
     * @param id the id of the inrichtingselement to save.
     * @param inrichtingselement the inrichtingselement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inrichtingselement,
     * or with status {@code 400 (Bad Request)} if the inrichtingselement is not valid,
     * or with status {@code 404 (Not Found)} if the inrichtingselement is not found,
     * or with status {@code 500 (Internal Server Error)} if the inrichtingselement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inrichtingselement> partialUpdateInrichtingselement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inrichtingselement inrichtingselement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inrichtingselement partially : {}, {}", id, inrichtingselement);
        if (inrichtingselement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inrichtingselement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inrichtingselementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inrichtingselement> result = inrichtingselementRepository
            .findById(inrichtingselement.getId())
            .map(existingInrichtingselement -> {
                if (inrichtingselement.getDatumbegingeldigheidinrichtingselement() != null) {
                    existingInrichtingselement.setDatumbegingeldigheidinrichtingselement(
                        inrichtingselement.getDatumbegingeldigheidinrichtingselement()
                    );
                }
                if (inrichtingselement.getDatumeindegeldigheidinrichtingselement() != null) {
                    existingInrichtingselement.setDatumeindegeldigheidinrichtingselement(
                        inrichtingselement.getDatumeindegeldigheidinrichtingselement()
                    );
                }
                if (inrichtingselement.getGeometrieinrichtingselement() != null) {
                    existingInrichtingselement.setGeometrieinrichtingselement(inrichtingselement.getGeometrieinrichtingselement());
                }
                if (inrichtingselement.getIdentificatieinrichtingselement() != null) {
                    existingInrichtingselement.setIdentificatieinrichtingselement(inrichtingselement.getIdentificatieinrichtingselement());
                }
                if (inrichtingselement.getLod0geometrieinrichtingselement() != null) {
                    existingInrichtingselement.setLod0geometrieinrichtingselement(inrichtingselement.getLod0geometrieinrichtingselement());
                }
                if (inrichtingselement.getPlustypeinrichtingselement() != null) {
                    existingInrichtingselement.setPlustypeinrichtingselement(inrichtingselement.getPlustypeinrichtingselement());
                }
                if (inrichtingselement.getRelatievehoogteligginginrichtingselement() != null) {
                    existingInrichtingselement.setRelatievehoogteligginginrichtingselement(
                        inrichtingselement.getRelatievehoogteligginginrichtingselement()
                    );
                }
                if (inrichtingselement.getStatusinrichtingselement() != null) {
                    existingInrichtingselement.setStatusinrichtingselement(inrichtingselement.getStatusinrichtingselement());
                }
                if (inrichtingselement.getTypeinrichtingselement() != null) {
                    existingInrichtingselement.setTypeinrichtingselement(inrichtingselement.getTypeinrichtingselement());
                }

                return existingInrichtingselement;
            })
            .map(inrichtingselementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inrichtingselement.getId().toString())
        );
    }

    /**
     * {@code GET  /inrichtingselements} : get all the inrichtingselements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inrichtingselements in body.
     */
    @GetMapping("")
    public List<Inrichtingselement> getAllInrichtingselements() {
        log.debug("REST request to get all Inrichtingselements");
        return inrichtingselementRepository.findAll();
    }

    /**
     * {@code GET  /inrichtingselements/:id} : get the "id" inrichtingselement.
     *
     * @param id the id of the inrichtingselement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inrichtingselement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inrichtingselement> getInrichtingselement(@PathVariable("id") Long id) {
        log.debug("REST request to get Inrichtingselement : {}", id);
        Optional<Inrichtingselement> inrichtingselement = inrichtingselementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inrichtingselement);
    }

    /**
     * {@code DELETE  /inrichtingselements/:id} : delete the "id" inrichtingselement.
     *
     * @param id the id of the inrichtingselement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInrichtingselement(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inrichtingselement : {}", id);
        inrichtingselementRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
