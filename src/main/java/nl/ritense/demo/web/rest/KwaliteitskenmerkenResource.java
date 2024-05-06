package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Kwaliteitskenmerken;
import nl.ritense.demo.repository.KwaliteitskenmerkenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kwaliteitskenmerken}.
 */
@RestController
@RequestMapping("/api/kwaliteitskenmerkens")
@Transactional
public class KwaliteitskenmerkenResource {

    private final Logger log = LoggerFactory.getLogger(KwaliteitskenmerkenResource.class);

    private static final String ENTITY_NAME = "kwaliteitskenmerken";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KwaliteitskenmerkenRepository kwaliteitskenmerkenRepository;

    public KwaliteitskenmerkenResource(KwaliteitskenmerkenRepository kwaliteitskenmerkenRepository) {
        this.kwaliteitskenmerkenRepository = kwaliteitskenmerkenRepository;
    }

    /**
     * {@code POST  /kwaliteitskenmerkens} : Create a new kwaliteitskenmerken.
     *
     * @param kwaliteitskenmerken the kwaliteitskenmerken to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kwaliteitskenmerken, or with status {@code 400 (Bad Request)} if the kwaliteitskenmerken has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kwaliteitskenmerken> createKwaliteitskenmerken(@RequestBody Kwaliteitskenmerken kwaliteitskenmerken)
        throws URISyntaxException {
        log.debug("REST request to save Kwaliteitskenmerken : {}", kwaliteitskenmerken);
        if (kwaliteitskenmerken.getId() != null) {
            throw new BadRequestAlertException("A new kwaliteitskenmerken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kwaliteitskenmerken = kwaliteitskenmerkenRepository.save(kwaliteitskenmerken);
        return ResponseEntity.created(new URI("/api/kwaliteitskenmerkens/" + kwaliteitskenmerken.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kwaliteitskenmerken.getId().toString()))
            .body(kwaliteitskenmerken);
    }

    /**
     * {@code GET  /kwaliteitskenmerkens} : get all the kwaliteitskenmerkens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kwaliteitskenmerkens in body.
     */
    @GetMapping("")
    public List<Kwaliteitskenmerken> getAllKwaliteitskenmerkens() {
        log.debug("REST request to get all Kwaliteitskenmerkens");
        return kwaliteitskenmerkenRepository.findAll();
    }

    /**
     * {@code GET  /kwaliteitskenmerkens/:id} : get the "id" kwaliteitskenmerken.
     *
     * @param id the id of the kwaliteitskenmerken to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kwaliteitskenmerken, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kwaliteitskenmerken> getKwaliteitskenmerken(@PathVariable("id") Long id) {
        log.debug("REST request to get Kwaliteitskenmerken : {}", id);
        Optional<Kwaliteitskenmerken> kwaliteitskenmerken = kwaliteitskenmerkenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kwaliteitskenmerken);
    }

    /**
     * {@code DELETE  /kwaliteitskenmerkens/:id} : delete the "id" kwaliteitskenmerken.
     *
     * @param id the id of the kwaliteitskenmerken to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKwaliteitskenmerken(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kwaliteitskenmerken : {}", id);
        kwaliteitskenmerkenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
