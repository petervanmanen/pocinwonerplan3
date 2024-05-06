package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Appartementsrecht;
import nl.ritense.demo.repository.AppartementsrechtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Appartementsrecht}.
 */
@RestController
@RequestMapping("/api/appartementsrechts")
@Transactional
public class AppartementsrechtResource {

    private final Logger log = LoggerFactory.getLogger(AppartementsrechtResource.class);

    private static final String ENTITY_NAME = "appartementsrecht";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppartementsrechtRepository appartementsrechtRepository;

    public AppartementsrechtResource(AppartementsrechtRepository appartementsrechtRepository) {
        this.appartementsrechtRepository = appartementsrechtRepository;
    }

    /**
     * {@code POST  /appartementsrechts} : Create a new appartementsrecht.
     *
     * @param appartementsrecht the appartementsrecht to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appartementsrecht, or with status {@code 400 (Bad Request)} if the appartementsrecht has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Appartementsrecht> createAppartementsrecht(@RequestBody Appartementsrecht appartementsrecht)
        throws URISyntaxException {
        log.debug("REST request to save Appartementsrecht : {}", appartementsrecht);
        if (appartementsrecht.getId() != null) {
            throw new BadRequestAlertException("A new appartementsrecht cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appartementsrecht = appartementsrechtRepository.save(appartementsrecht);
        return ResponseEntity.created(new URI("/api/appartementsrechts/" + appartementsrecht.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appartementsrecht.getId().toString()))
            .body(appartementsrecht);
    }

    /**
     * {@code GET  /appartementsrechts} : get all the appartementsrechts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appartementsrechts in body.
     */
    @GetMapping("")
    public List<Appartementsrecht> getAllAppartementsrechts() {
        log.debug("REST request to get all Appartementsrechts");
        return appartementsrechtRepository.findAll();
    }

    /**
     * {@code GET  /appartementsrechts/:id} : get the "id" appartementsrecht.
     *
     * @param id the id of the appartementsrecht to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appartementsrecht, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appartementsrecht> getAppartementsrecht(@PathVariable("id") Long id) {
        log.debug("REST request to get Appartementsrecht : {}", id);
        Optional<Appartementsrecht> appartementsrecht = appartementsrechtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appartementsrecht);
    }

    /**
     * {@code DELETE  /appartementsrechts/:id} : delete the "id" appartementsrecht.
     *
     * @param id the id of the appartementsrecht to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppartementsrecht(@PathVariable("id") Long id) {
        log.debug("REST request to delete Appartementsrecht : {}", id);
        appartementsrechtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
