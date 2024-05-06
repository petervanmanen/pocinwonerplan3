package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Openbareruimte;
import nl.ritense.demo.repository.OpenbareruimteRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Openbareruimte}.
 */
@RestController
@RequestMapping("/api/openbareruimtes")
@Transactional
public class OpenbareruimteResource {

    private final Logger log = LoggerFactory.getLogger(OpenbareruimteResource.class);

    private static final String ENTITY_NAME = "openbareruimte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpenbareruimteRepository openbareruimteRepository;

    public OpenbareruimteResource(OpenbareruimteRepository openbareruimteRepository) {
        this.openbareruimteRepository = openbareruimteRepository;
    }

    /**
     * {@code POST  /openbareruimtes} : Create a new openbareruimte.
     *
     * @param openbareruimte the openbareruimte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new openbareruimte, or with status {@code 400 (Bad Request)} if the openbareruimte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Openbareruimte> createOpenbareruimte(@RequestBody Openbareruimte openbareruimte) throws URISyntaxException {
        log.debug("REST request to save Openbareruimte : {}", openbareruimte);
        if (openbareruimte.getId() != null) {
            throw new BadRequestAlertException("A new openbareruimte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        openbareruimte = openbareruimteRepository.save(openbareruimte);
        return ResponseEntity.created(new URI("/api/openbareruimtes/" + openbareruimte.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, openbareruimte.getId().toString()))
            .body(openbareruimte);
    }

    /**
     * {@code PUT  /openbareruimtes/:id} : Updates an existing openbareruimte.
     *
     * @param id the id of the openbareruimte to save.
     * @param openbareruimte the openbareruimte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated openbareruimte,
     * or with status {@code 400 (Bad Request)} if the openbareruimte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the openbareruimte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Openbareruimte> updateOpenbareruimte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Openbareruimte openbareruimte
    ) throws URISyntaxException {
        log.debug("REST request to update Openbareruimte : {}, {}", id, openbareruimte);
        if (openbareruimte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, openbareruimte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!openbareruimteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        openbareruimte = openbareruimteRepository.save(openbareruimte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, openbareruimte.getId().toString()))
            .body(openbareruimte);
    }

    /**
     * {@code PATCH  /openbareruimtes/:id} : Partial updates given fields of an existing openbareruimte, field will ignore if it is null
     *
     * @param id the id of the openbareruimte to save.
     * @param openbareruimte the openbareruimte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated openbareruimte,
     * or with status {@code 400 (Bad Request)} if the openbareruimte is not valid,
     * or with status {@code 404 (Not Found)} if the openbareruimte is not found,
     * or with status {@code 500 (Internal Server Error)} if the openbareruimte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Openbareruimte> partialUpdateOpenbareruimte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Openbareruimte openbareruimte
    ) throws URISyntaxException {
        log.debug("REST request to partial update Openbareruimte partially : {}, {}", id, openbareruimte);
        if (openbareruimte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, openbareruimte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!openbareruimteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Openbareruimte> result = openbareruimteRepository
            .findById(openbareruimte.getId())
            .map(existingOpenbareruimte -> {
                if (openbareruimte.getDatumbegingeldigheid() != null) {
                    existingOpenbareruimte.setDatumbegingeldigheid(openbareruimte.getDatumbegingeldigheid());
                }
                if (openbareruimte.getDatumeinde() != null) {
                    existingOpenbareruimte.setDatumeinde(openbareruimte.getDatumeinde());
                }
                if (openbareruimte.getDatumeindegeldigheid() != null) {
                    existingOpenbareruimte.setDatumeindegeldigheid(openbareruimte.getDatumeindegeldigheid());
                }
                if (openbareruimte.getDatumingang() != null) {
                    existingOpenbareruimte.setDatumingang(openbareruimte.getDatumingang());
                }
                if (openbareruimte.getGeconstateerd() != null) {
                    existingOpenbareruimte.setGeconstateerd(openbareruimte.getGeconstateerd());
                }
                if (openbareruimte.getGeometrie() != null) {
                    existingOpenbareruimte.setGeometrie(openbareruimte.getGeometrie());
                }
                if (openbareruimte.getHuisnummerrangeevenenonevennummers() != null) {
                    existingOpenbareruimte.setHuisnummerrangeevenenonevennummers(openbareruimte.getHuisnummerrangeevenenonevennummers());
                }
                if (openbareruimte.getHuisnummerrangeevennummers() != null) {
                    existingOpenbareruimte.setHuisnummerrangeevennummers(openbareruimte.getHuisnummerrangeevennummers());
                }
                if (openbareruimte.getHuisnummerrangeonevennummers() != null) {
                    existingOpenbareruimte.setHuisnummerrangeonevennummers(openbareruimte.getHuisnummerrangeonevennummers());
                }
                if (openbareruimte.getIdentificatie() != null) {
                    existingOpenbareruimte.setIdentificatie(openbareruimte.getIdentificatie());
                }
                if (openbareruimte.getInonderzoek() != null) {
                    existingOpenbareruimte.setInonderzoek(openbareruimte.getInonderzoek());
                }
                if (openbareruimte.getLabelnaam() != null) {
                    existingOpenbareruimte.setLabelnaam(openbareruimte.getLabelnaam());
                }
                if (openbareruimte.getNaamopenbareruimte() != null) {
                    existingOpenbareruimte.setNaamopenbareruimte(openbareruimte.getNaamopenbareruimte());
                }
                if (openbareruimte.getStatus() != null) {
                    existingOpenbareruimte.setStatus(openbareruimte.getStatus());
                }
                if (openbareruimte.getStraatcode() != null) {
                    existingOpenbareruimte.setStraatcode(openbareruimte.getStraatcode());
                }
                if (openbareruimte.getStraatnaam() != null) {
                    existingOpenbareruimte.setStraatnaam(openbareruimte.getStraatnaam());
                }
                if (openbareruimte.getTypeopenbareruimte() != null) {
                    existingOpenbareruimte.setTypeopenbareruimte(openbareruimte.getTypeopenbareruimte());
                }
                if (openbareruimte.getVersie() != null) {
                    existingOpenbareruimte.setVersie(openbareruimte.getVersie());
                }
                if (openbareruimte.getWegsegment() != null) {
                    existingOpenbareruimte.setWegsegment(openbareruimte.getWegsegment());
                }

                return existingOpenbareruimte;
            })
            .map(openbareruimteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, openbareruimte.getId().toString())
        );
    }

    /**
     * {@code GET  /openbareruimtes} : get all the openbareruimtes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of openbareruimtes in body.
     */
    @GetMapping("")
    public List<Openbareruimte> getAllOpenbareruimtes() {
        log.debug("REST request to get all Openbareruimtes");
        return openbareruimteRepository.findAll();
    }

    /**
     * {@code GET  /openbareruimtes/:id} : get the "id" openbareruimte.
     *
     * @param id the id of the openbareruimte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the openbareruimte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Openbareruimte> getOpenbareruimte(@PathVariable("id") Long id) {
        log.debug("REST request to get Openbareruimte : {}", id);
        Optional<Openbareruimte> openbareruimte = openbareruimteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(openbareruimte);
    }

    /**
     * {@code DELETE  /openbareruimtes/:id} : delete the "id" openbareruimte.
     *
     * @param id the id of the openbareruimte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpenbareruimte(@PathVariable("id") Long id) {
        log.debug("REST request to delete Openbareruimte : {}", id);
        openbareruimteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
