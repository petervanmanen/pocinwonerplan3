package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Uitgever;
import nl.ritense.demo.repository.UitgeverRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitgever}.
 */
@RestController
@RequestMapping("/api/uitgevers")
@Transactional
public class UitgeverResource {

    private final Logger log = LoggerFactory.getLogger(UitgeverResource.class);

    private static final String ENTITY_NAME = "uitgever";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitgeverRepository uitgeverRepository;

    public UitgeverResource(UitgeverRepository uitgeverRepository) {
        this.uitgeverRepository = uitgeverRepository;
    }

    /**
     * {@code POST  /uitgevers} : Create a new uitgever.
     *
     * @param uitgever the uitgever to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitgever, or with status {@code 400 (Bad Request)} if the uitgever has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitgever> createUitgever(@RequestBody Uitgever uitgever) throws URISyntaxException {
        log.debug("REST request to save Uitgever : {}", uitgever);
        if (uitgever.getId() != null) {
            throw new BadRequestAlertException("A new uitgever cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitgever = uitgeverRepository.save(uitgever);
        return ResponseEntity.created(new URI("/api/uitgevers/" + uitgever.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitgever.getId().toString()))
            .body(uitgever);
    }

    /**
     * {@code GET  /uitgevers} : get all the uitgevers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitgevers in body.
     */
    @GetMapping("")
    public List<Uitgever> getAllUitgevers() {
        log.debug("REST request to get all Uitgevers");
        return uitgeverRepository.findAll();
    }

    /**
     * {@code GET  /uitgevers/:id} : get the "id" uitgever.
     *
     * @param id the id of the uitgever to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitgever, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitgever> getUitgever(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitgever : {}", id);
        Optional<Uitgever> uitgever = uitgeverRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitgever);
    }

    /**
     * {@code DELETE  /uitgevers/:id} : delete the "id" uitgever.
     *
     * @param id the id of the uitgever to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitgever(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitgever : {}", id);
        uitgeverRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
