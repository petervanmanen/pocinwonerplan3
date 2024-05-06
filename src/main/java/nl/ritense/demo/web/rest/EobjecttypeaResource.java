package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjecttypea;
import nl.ritense.demo.repository.EobjecttypeaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjecttypea}.
 */
@RestController
@RequestMapping("/api/eobjecttypeas")
@Transactional
public class EobjecttypeaResource {

    private final Logger log = LoggerFactory.getLogger(EobjecttypeaResource.class);

    private static final String ENTITY_NAME = "eobjecttypea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjecttypeaRepository eobjecttypeaRepository;

    public EobjecttypeaResource(EobjecttypeaRepository eobjecttypeaRepository) {
        this.eobjecttypeaRepository = eobjecttypeaRepository;
    }

    /**
     * {@code POST  /eobjecttypeas} : Create a new eobjecttypea.
     *
     * @param eobjecttypea the eobjecttypea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjecttypea, or with status {@code 400 (Bad Request)} if the eobjecttypea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjecttypea> createEobjecttypea(@RequestBody Eobjecttypea eobjecttypea) throws URISyntaxException {
        log.debug("REST request to save Eobjecttypea : {}", eobjecttypea);
        if (eobjecttypea.getId() != null) {
            throw new BadRequestAlertException("A new eobjecttypea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjecttypea = eobjecttypeaRepository.save(eobjecttypea);
        return ResponseEntity.created(new URI("/api/eobjecttypeas/" + eobjecttypea.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjecttypea.getId().toString()))
            .body(eobjecttypea);
    }

    /**
     * {@code GET  /eobjecttypeas} : get all the eobjecttypeas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjecttypeas in body.
     */
    @GetMapping("")
    public List<Eobjecttypea> getAllEobjecttypeas() {
        log.debug("REST request to get all Eobjecttypeas");
        return eobjecttypeaRepository.findAll();
    }

    /**
     * {@code GET  /eobjecttypeas/:id} : get the "id" eobjecttypea.
     *
     * @param id the id of the eobjecttypea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjecttypea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjecttypea> getEobjecttypea(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjecttypea : {}", id);
        Optional<Eobjecttypea> eobjecttypea = eobjecttypeaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjecttypea);
    }

    /**
     * {@code DELETE  /eobjecttypeas/:id} : delete the "id" eobjecttypea.
     *
     * @param id the id of the eobjecttypea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjecttypea(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjecttypea : {}", id);
        eobjecttypeaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
