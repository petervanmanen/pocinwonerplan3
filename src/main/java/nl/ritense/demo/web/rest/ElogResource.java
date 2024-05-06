package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Elog;
import nl.ritense.demo.repository.ElogRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Elog}.
 */
@RestController
@RequestMapping("/api/elogs")
@Transactional
public class ElogResource {

    private final Logger log = LoggerFactory.getLogger(ElogResource.class);

    private static final String ENTITY_NAME = "elog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElogRepository elogRepository;

    public ElogResource(ElogRepository elogRepository) {
        this.elogRepository = elogRepository;
    }

    /**
     * {@code POST  /elogs} : Create a new elog.
     *
     * @param elog the elog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elog, or with status {@code 400 (Bad Request)} if the elog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Elog> createElog(@RequestBody Elog elog) throws URISyntaxException {
        log.debug("REST request to save Elog : {}", elog);
        if (elog.getId() != null) {
            throw new BadRequestAlertException("A new elog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        elog = elogRepository.save(elog);
        return ResponseEntity.created(new URI("/api/elogs/" + elog.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, elog.getId().toString()))
            .body(elog);
    }

    /**
     * {@code PUT  /elogs/:id} : Updates an existing elog.
     *
     * @param id the id of the elog to save.
     * @param elog the elog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elog,
     * or with status {@code 400 (Bad Request)} if the elog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Elog> updateElog(@PathVariable(value = "id", required = false) final Long id, @RequestBody Elog elog)
        throws URISyntaxException {
        log.debug("REST request to update Elog : {}, {}", id, elog);
        if (elog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        elog = elogRepository.save(elog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, elog.getId().toString()))
            .body(elog);
    }

    /**
     * {@code PATCH  /elogs/:id} : Partial updates given fields of an existing elog, field will ignore if it is null
     *
     * @param id the id of the elog to save.
     * @param elog the elog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elog,
     * or with status {@code 400 (Bad Request)} if the elog is not valid,
     * or with status {@code 404 (Not Found)} if the elog is not found,
     * or with status {@code 500 (Internal Server Error)} if the elog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Elog> partialUpdateElog(@PathVariable(value = "id", required = false) final Long id, @RequestBody Elog elog)
        throws URISyntaxException {
        log.debug("REST request to partial update Elog partially : {}, {}", id, elog);
        if (elog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Elog> result = elogRepository
            .findById(elog.getId())
            .map(existingElog -> {
                if (elog.getKorteomschrijving() != null) {
                    existingElog.setKorteomschrijving(elog.getKorteomschrijving());
                }
                if (elog.getOmschrijving() != null) {
                    existingElog.setOmschrijving(elog.getOmschrijving());
                }
                if (elog.getTijd() != null) {
                    existingElog.setTijd(elog.getTijd());
                }

                return existingElog;
            })
            .map(elogRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, elog.getId().toString())
        );
    }

    /**
     * {@code GET  /elogs} : get all the elogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elogs in body.
     */
    @GetMapping("")
    public List<Elog> getAllElogs() {
        log.debug("REST request to get all Elogs");
        return elogRepository.findAll();
    }

    /**
     * {@code GET  /elogs/:id} : get the "id" elog.
     *
     * @param id the id of the elog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Elog> getElog(@PathVariable("id") Long id) {
        log.debug("REST request to get Elog : {}", id);
        Optional<Elog> elog = elogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(elog);
    }

    /**
     * {@code DELETE  /elogs/:id} : delete the "id" elog.
     *
     * @param id the id of the elog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElog(@PathVariable("id") Long id) {
        log.debug("REST request to delete Elog : {}", id);
        elogRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
