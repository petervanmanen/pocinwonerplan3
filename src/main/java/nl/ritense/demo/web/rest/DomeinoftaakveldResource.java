package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Domeinoftaakveld;
import nl.ritense.demo.repository.DomeinoftaakveldRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Domeinoftaakveld}.
 */
@RestController
@RequestMapping("/api/domeinoftaakvelds")
@Transactional
public class DomeinoftaakveldResource {

    private final Logger log = LoggerFactory.getLogger(DomeinoftaakveldResource.class);

    private static final String ENTITY_NAME = "domeinoftaakveld";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomeinoftaakveldRepository domeinoftaakveldRepository;

    public DomeinoftaakveldResource(DomeinoftaakveldRepository domeinoftaakveldRepository) {
        this.domeinoftaakveldRepository = domeinoftaakveldRepository;
    }

    /**
     * {@code POST  /domeinoftaakvelds} : Create a new domeinoftaakveld.
     *
     * @param domeinoftaakveld the domeinoftaakveld to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new domeinoftaakveld, or with status {@code 400 (Bad Request)} if the domeinoftaakveld has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Domeinoftaakveld> createDomeinoftaakveld(@RequestBody Domeinoftaakveld domeinoftaakveld)
        throws URISyntaxException {
        log.debug("REST request to save Domeinoftaakveld : {}", domeinoftaakveld);
        if (domeinoftaakveld.getId() != null) {
            throw new BadRequestAlertException("A new domeinoftaakveld cannot already have an ID", ENTITY_NAME, "idexists");
        }
        domeinoftaakveld = domeinoftaakveldRepository.save(domeinoftaakveld);
        return ResponseEntity.created(new URI("/api/domeinoftaakvelds/" + domeinoftaakveld.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, domeinoftaakveld.getId().toString()))
            .body(domeinoftaakveld);
    }

    /**
     * {@code GET  /domeinoftaakvelds} : get all the domeinoftaakvelds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domeinoftaakvelds in body.
     */
    @GetMapping("")
    public List<Domeinoftaakveld> getAllDomeinoftaakvelds() {
        log.debug("REST request to get all Domeinoftaakvelds");
        return domeinoftaakveldRepository.findAll();
    }

    /**
     * {@code GET  /domeinoftaakvelds/:id} : get the "id" domeinoftaakveld.
     *
     * @param id the id of the domeinoftaakveld to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domeinoftaakveld, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Domeinoftaakveld> getDomeinoftaakveld(@PathVariable("id") Long id) {
        log.debug("REST request to get Domeinoftaakveld : {}", id);
        Optional<Domeinoftaakveld> domeinoftaakveld = domeinoftaakveldRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(domeinoftaakveld);
    }

    /**
     * {@code DELETE  /domeinoftaakvelds/:id} : delete the "id" domeinoftaakveld.
     *
     * @param id the id of the domeinoftaakveld to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomeinoftaakveld(@PathVariable("id") Long id) {
        log.debug("REST request to delete Domeinoftaakveld : {}", id);
        domeinoftaakveldRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
