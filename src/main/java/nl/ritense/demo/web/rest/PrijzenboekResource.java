package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Prijzenboek;
import nl.ritense.demo.repository.PrijzenboekRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Prijzenboek}.
 */
@RestController
@RequestMapping("/api/prijzenboeks")
@Transactional
public class PrijzenboekResource {

    private final Logger log = LoggerFactory.getLogger(PrijzenboekResource.class);

    private static final String ENTITY_NAME = "prijzenboek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrijzenboekRepository prijzenboekRepository;

    public PrijzenboekResource(PrijzenboekRepository prijzenboekRepository) {
        this.prijzenboekRepository = prijzenboekRepository;
    }

    /**
     * {@code POST  /prijzenboeks} : Create a new prijzenboek.
     *
     * @param prijzenboek the prijzenboek to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prijzenboek, or with status {@code 400 (Bad Request)} if the prijzenboek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Prijzenboek> createPrijzenboek(@RequestBody Prijzenboek prijzenboek) throws URISyntaxException {
        log.debug("REST request to save Prijzenboek : {}", prijzenboek);
        if (prijzenboek.getId() != null) {
            throw new BadRequestAlertException("A new prijzenboek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prijzenboek = prijzenboekRepository.save(prijzenboek);
        return ResponseEntity.created(new URI("/api/prijzenboeks/" + prijzenboek.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, prijzenboek.getId().toString()))
            .body(prijzenboek);
    }

    /**
     * {@code GET  /prijzenboeks} : get all the prijzenboeks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prijzenboeks in body.
     */
    @GetMapping("")
    public List<Prijzenboek> getAllPrijzenboeks() {
        log.debug("REST request to get all Prijzenboeks");
        return prijzenboekRepository.findAll();
    }

    /**
     * {@code GET  /prijzenboeks/:id} : get the "id" prijzenboek.
     *
     * @param id the id of the prijzenboek to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prijzenboek, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prijzenboek> getPrijzenboek(@PathVariable("id") Long id) {
        log.debug("REST request to get Prijzenboek : {}", id);
        Optional<Prijzenboek> prijzenboek = prijzenboekRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prijzenboek);
    }

    /**
     * {@code DELETE  /prijzenboeks/:id} : delete the "id" prijzenboek.
     *
     * @param id the id of the prijzenboek to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrijzenboek(@PathVariable("id") Long id) {
        log.debug("REST request to delete Prijzenboek : {}", id);
        prijzenboekRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
