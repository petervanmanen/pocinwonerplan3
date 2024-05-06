package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraagstadspas;
import nl.ritense.demo.repository.AanvraagstadspasRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanvraagstadspas}.
 */
@RestController
@RequestMapping("/api/aanvraagstadspas")
@Transactional
public class AanvraagstadspasResource {

    private final Logger log = LoggerFactory.getLogger(AanvraagstadspasResource.class);

    private static final String ENTITY_NAME = "aanvraagstadspas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanvraagstadspasRepository aanvraagstadspasRepository;

    public AanvraagstadspasResource(AanvraagstadspasRepository aanvraagstadspasRepository) {
        this.aanvraagstadspasRepository = aanvraagstadspasRepository;
    }

    /**
     * {@code POST  /aanvraagstadspas} : Create a new aanvraagstadspas.
     *
     * @param aanvraagstadspas the aanvraagstadspas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanvraagstadspas, or with status {@code 400 (Bad Request)} if the aanvraagstadspas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanvraagstadspas> createAanvraagstadspas(@RequestBody Aanvraagstadspas aanvraagstadspas)
        throws URISyntaxException {
        log.debug("REST request to save Aanvraagstadspas : {}", aanvraagstadspas);
        if (aanvraagstadspas.getId() != null) {
            throw new BadRequestAlertException("A new aanvraagstadspas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanvraagstadspas = aanvraagstadspasRepository.save(aanvraagstadspas);
        return ResponseEntity.created(new URI("/api/aanvraagstadspas/" + aanvraagstadspas.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanvraagstadspas.getId().toString()))
            .body(aanvraagstadspas);
    }

    /**
     * {@code GET  /aanvraagstadspas} : get all the aanvraagstadspas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanvraagstadspas in body.
     */
    @GetMapping("")
    public List<Aanvraagstadspas> getAllAanvraagstadspas() {
        log.debug("REST request to get all Aanvraagstadspas");
        return aanvraagstadspasRepository.findAll();
    }

    /**
     * {@code GET  /aanvraagstadspas/:id} : get the "id" aanvraagstadspas.
     *
     * @param id the id of the aanvraagstadspas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanvraagstadspas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanvraagstadspas> getAanvraagstadspas(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanvraagstadspas : {}", id);
        Optional<Aanvraagstadspas> aanvraagstadspas = aanvraagstadspasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanvraagstadspas);
    }

    /**
     * {@code DELETE  /aanvraagstadspas/:id} : delete the "id" aanvraagstadspas.
     *
     * @param id the id of the aanvraagstadspas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanvraagstadspas(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanvraagstadspas : {}", id);
        aanvraagstadspasRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
