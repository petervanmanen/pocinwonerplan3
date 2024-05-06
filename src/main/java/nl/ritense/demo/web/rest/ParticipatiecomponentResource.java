package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Participatiecomponent;
import nl.ritense.demo.repository.ParticipatiecomponentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Participatiecomponent}.
 */
@RestController
@RequestMapping("/api/participatiecomponents")
@Transactional
public class ParticipatiecomponentResource {

    private final Logger log = LoggerFactory.getLogger(ParticipatiecomponentResource.class);

    private static final String ENTITY_NAME = "participatiecomponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipatiecomponentRepository participatiecomponentRepository;

    public ParticipatiecomponentResource(ParticipatiecomponentRepository participatiecomponentRepository) {
        this.participatiecomponentRepository = participatiecomponentRepository;
    }

    /**
     * {@code POST  /participatiecomponents} : Create a new participatiecomponent.
     *
     * @param participatiecomponent the participatiecomponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participatiecomponent, or with status {@code 400 (Bad Request)} if the participatiecomponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Participatiecomponent> createParticipatiecomponent(@RequestBody Participatiecomponent participatiecomponent)
        throws URISyntaxException {
        log.debug("REST request to save Participatiecomponent : {}", participatiecomponent);
        if (participatiecomponent.getId() != null) {
            throw new BadRequestAlertException("A new participatiecomponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        participatiecomponent = participatiecomponentRepository.save(participatiecomponent);
        return ResponseEntity.created(new URI("/api/participatiecomponents/" + participatiecomponent.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, participatiecomponent.getId().toString()))
            .body(participatiecomponent);
    }

    /**
     * {@code GET  /participatiecomponents} : get all the participatiecomponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participatiecomponents in body.
     */
    @GetMapping("")
    public List<Participatiecomponent> getAllParticipatiecomponents() {
        log.debug("REST request to get all Participatiecomponents");
        return participatiecomponentRepository.findAll();
    }

    /**
     * {@code GET  /participatiecomponents/:id} : get the "id" participatiecomponent.
     *
     * @param id the id of the participatiecomponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participatiecomponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Participatiecomponent> getParticipatiecomponent(@PathVariable("id") Long id) {
        log.debug("REST request to get Participatiecomponent : {}", id);
        Optional<Participatiecomponent> participatiecomponent = participatiecomponentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(participatiecomponent);
    }

    /**
     * {@code DELETE  /participatiecomponents/:id} : delete the "id" participatiecomponent.
     *
     * @param id the id of the participatiecomponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipatiecomponent(@PathVariable("id") Long id) {
        log.debug("REST request to delete Participatiecomponent : {}", id);
        participatiecomponentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
