package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Actie;
import nl.ritense.demo.repository.ActieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Actie}.
 */
@RestController
@RequestMapping("/api/acties")
@Transactional
public class ActieResource {

    private final Logger log = LoggerFactory.getLogger(ActieResource.class);

    private static final String ENTITY_NAME = "actie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActieRepository actieRepository;

    public ActieResource(ActieRepository actieRepository) {
        this.actieRepository = actieRepository;
    }

    /**
     * {@code POST  /acties} : Create a new actie.
     *
     * @param actie the actie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actie, or with status {@code 400 (Bad Request)} if the actie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Actie> createActie(@RequestBody Actie actie) throws URISyntaxException {
        log.debug("REST request to save Actie : {}", actie);
        if (actie.getId() != null) {
            throw new BadRequestAlertException("A new actie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        actie = actieRepository.save(actie);
        return ResponseEntity.created(new URI("/api/acties/" + actie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, actie.getId().toString()))
            .body(actie);
    }

    /**
     * {@code GET  /acties} : get all the acties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acties in body.
     */
    @GetMapping("")
    public List<Actie> getAllActies() {
        log.debug("REST request to get all Acties");
        return actieRepository.findAll();
    }

    /**
     * {@code GET  /acties/:id} : get the "id" actie.
     *
     * @param id the id of the actie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Actie> getActie(@PathVariable("id") Long id) {
        log.debug("REST request to get Actie : {}", id);
        Optional<Actie> actie = actieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(actie);
    }

    /**
     * {@code DELETE  /acties/:id} : delete the "id" actie.
     *
     * @param id the id of the actie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Actie : {}", id);
        actieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
