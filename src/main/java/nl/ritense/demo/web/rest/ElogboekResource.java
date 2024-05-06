package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Elogboek;
import nl.ritense.demo.repository.ElogboekRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Elogboek}.
 */
@RestController
@RequestMapping("/api/elogboeks")
@Transactional
public class ElogboekResource {

    private final Logger log = LoggerFactory.getLogger(ElogboekResource.class);

    private static final String ENTITY_NAME = "elogboek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElogboekRepository elogboekRepository;

    public ElogboekResource(ElogboekRepository elogboekRepository) {
        this.elogboekRepository = elogboekRepository;
    }

    /**
     * {@code POST  /elogboeks} : Create a new elogboek.
     *
     * @param elogboek the elogboek to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elogboek, or with status {@code 400 (Bad Request)} if the elogboek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Elogboek> createElogboek(@RequestBody Elogboek elogboek) throws URISyntaxException {
        log.debug("REST request to save Elogboek : {}", elogboek);
        if (elogboek.getId() != null) {
            throw new BadRequestAlertException("A new elogboek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        elogboek = elogboekRepository.save(elogboek);
        return ResponseEntity.created(new URI("/api/elogboeks/" + elogboek.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, elogboek.getId().toString()))
            .body(elogboek);
    }

    /**
     * {@code GET  /elogboeks} : get all the elogboeks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elogboeks in body.
     */
    @GetMapping("")
    public List<Elogboek> getAllElogboeks() {
        log.debug("REST request to get all Elogboeks");
        return elogboekRepository.findAll();
    }

    /**
     * {@code GET  /elogboeks/:id} : get the "id" elogboek.
     *
     * @param id the id of the elogboek to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elogboek, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Elogboek> getElogboek(@PathVariable("id") Long id) {
        log.debug("REST request to get Elogboek : {}", id);
        Optional<Elogboek> elogboek = elogboekRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(elogboek);
    }

    /**
     * {@code DELETE  /elogboeks/:id} : delete the "id" elogboek.
     *
     * @param id the id of the elogboek to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElogboek(@PathVariable("id") Long id) {
        log.debug("REST request to delete Elogboek : {}", id);
        elogboekRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
