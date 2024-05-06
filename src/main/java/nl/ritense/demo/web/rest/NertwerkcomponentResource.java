package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Nertwerkcomponent;
import nl.ritense.demo.repository.NertwerkcomponentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Nertwerkcomponent}.
 */
@RestController
@RequestMapping("/api/nertwerkcomponents")
@Transactional
public class NertwerkcomponentResource {

    private final Logger log = LoggerFactory.getLogger(NertwerkcomponentResource.class);

    private static final String ENTITY_NAME = "nertwerkcomponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NertwerkcomponentRepository nertwerkcomponentRepository;

    public NertwerkcomponentResource(NertwerkcomponentRepository nertwerkcomponentRepository) {
        this.nertwerkcomponentRepository = nertwerkcomponentRepository;
    }

    /**
     * {@code POST  /nertwerkcomponents} : Create a new nertwerkcomponent.
     *
     * @param nertwerkcomponent the nertwerkcomponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nertwerkcomponent, or with status {@code 400 (Bad Request)} if the nertwerkcomponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nertwerkcomponent> createNertwerkcomponent(@RequestBody Nertwerkcomponent nertwerkcomponent)
        throws URISyntaxException {
        log.debug("REST request to save Nertwerkcomponent : {}", nertwerkcomponent);
        if (nertwerkcomponent.getId() != null) {
            throw new BadRequestAlertException("A new nertwerkcomponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nertwerkcomponent = nertwerkcomponentRepository.save(nertwerkcomponent);
        return ResponseEntity.created(new URI("/api/nertwerkcomponents/" + nertwerkcomponent.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, nertwerkcomponent.getId().toString()))
            .body(nertwerkcomponent);
    }

    /**
     * {@code GET  /nertwerkcomponents} : get all the nertwerkcomponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nertwerkcomponents in body.
     */
    @GetMapping("")
    public List<Nertwerkcomponent> getAllNertwerkcomponents() {
        log.debug("REST request to get all Nertwerkcomponents");
        return nertwerkcomponentRepository.findAll();
    }

    /**
     * {@code GET  /nertwerkcomponents/:id} : get the "id" nertwerkcomponent.
     *
     * @param id the id of the nertwerkcomponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nertwerkcomponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nertwerkcomponent> getNertwerkcomponent(@PathVariable("id") Long id) {
        log.debug("REST request to get Nertwerkcomponent : {}", id);
        Optional<Nertwerkcomponent> nertwerkcomponent = nertwerkcomponentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nertwerkcomponent);
    }

    /**
     * {@code DELETE  /nertwerkcomponents/:id} : delete the "id" nertwerkcomponent.
     *
     * @param id the id of the nertwerkcomponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNertwerkcomponent(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nertwerkcomponent : {}", id);
        nertwerkcomponentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
