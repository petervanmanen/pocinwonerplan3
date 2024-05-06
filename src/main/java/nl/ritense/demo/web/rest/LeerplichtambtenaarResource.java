package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Leerplichtambtenaar;
import nl.ritense.demo.repository.LeerplichtambtenaarRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leerplichtambtenaar}.
 */
@RestController
@RequestMapping("/api/leerplichtambtenaars")
@Transactional
public class LeerplichtambtenaarResource {

    private final Logger log = LoggerFactory.getLogger(LeerplichtambtenaarResource.class);

    private static final String ENTITY_NAME = "leerplichtambtenaar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeerplichtambtenaarRepository leerplichtambtenaarRepository;

    public LeerplichtambtenaarResource(LeerplichtambtenaarRepository leerplichtambtenaarRepository) {
        this.leerplichtambtenaarRepository = leerplichtambtenaarRepository;
    }

    /**
     * {@code POST  /leerplichtambtenaars} : Create a new leerplichtambtenaar.
     *
     * @param leerplichtambtenaar the leerplichtambtenaar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leerplichtambtenaar, or with status {@code 400 (Bad Request)} if the leerplichtambtenaar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leerplichtambtenaar> createLeerplichtambtenaar(@Valid @RequestBody Leerplichtambtenaar leerplichtambtenaar)
        throws URISyntaxException {
        log.debug("REST request to save Leerplichtambtenaar : {}", leerplichtambtenaar);
        if (leerplichtambtenaar.getId() != null) {
            throw new BadRequestAlertException("A new leerplichtambtenaar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leerplichtambtenaar = leerplichtambtenaarRepository.save(leerplichtambtenaar);
        return ResponseEntity.created(new URI("/api/leerplichtambtenaars/" + leerplichtambtenaar.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leerplichtambtenaar.getId().toString()))
            .body(leerplichtambtenaar);
    }

    /**
     * {@code GET  /leerplichtambtenaars} : get all the leerplichtambtenaars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leerplichtambtenaars in body.
     */
    @GetMapping("")
    public List<Leerplichtambtenaar> getAllLeerplichtambtenaars() {
        log.debug("REST request to get all Leerplichtambtenaars");
        return leerplichtambtenaarRepository.findAll();
    }

    /**
     * {@code GET  /leerplichtambtenaars/:id} : get the "id" leerplichtambtenaar.
     *
     * @param id the id of the leerplichtambtenaar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leerplichtambtenaar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leerplichtambtenaar> getLeerplichtambtenaar(@PathVariable("id") Long id) {
        log.debug("REST request to get Leerplichtambtenaar : {}", id);
        Optional<Leerplichtambtenaar> leerplichtambtenaar = leerplichtambtenaarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leerplichtambtenaar);
    }

    /**
     * {@code DELETE  /leerplichtambtenaars/:id} : delete the "id" leerplichtambtenaar.
     *
     * @param id the id of the leerplichtambtenaar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeerplichtambtenaar(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leerplichtambtenaar : {}", id);
        leerplichtambtenaarRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
