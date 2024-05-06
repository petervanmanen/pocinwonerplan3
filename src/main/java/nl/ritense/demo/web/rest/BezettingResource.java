package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Bezetting;
import nl.ritense.demo.repository.BezettingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bezetting}.
 */
@RestController
@RequestMapping("/api/bezettings")
@Transactional
public class BezettingResource {

    private final Logger log = LoggerFactory.getLogger(BezettingResource.class);

    private static final String ENTITY_NAME = "bezetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BezettingRepository bezettingRepository;

    public BezettingResource(BezettingRepository bezettingRepository) {
        this.bezettingRepository = bezettingRepository;
    }

    /**
     * {@code POST  /bezettings} : Create a new bezetting.
     *
     * @param bezetting the bezetting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bezetting, or with status {@code 400 (Bad Request)} if the bezetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bezetting> createBezetting(@RequestBody Bezetting bezetting) throws URISyntaxException {
        log.debug("REST request to save Bezetting : {}", bezetting);
        if (bezetting.getId() != null) {
            throw new BadRequestAlertException("A new bezetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bezetting = bezettingRepository.save(bezetting);
        return ResponseEntity.created(new URI("/api/bezettings/" + bezetting.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bezetting.getId().toString()))
            .body(bezetting);
    }

    /**
     * {@code GET  /bezettings} : get all the bezettings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bezettings in body.
     */
    @GetMapping("")
    public List<Bezetting> getAllBezettings() {
        log.debug("REST request to get all Bezettings");
        return bezettingRepository.findAll();
    }

    /**
     * {@code GET  /bezettings/:id} : get the "id" bezetting.
     *
     * @param id the id of the bezetting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bezetting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bezetting> getBezetting(@PathVariable("id") Long id) {
        log.debug("REST request to get Bezetting : {}", id);
        Optional<Bezetting> bezetting = bezettingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bezetting);
    }

    /**
     * {@code DELETE  /bezettings/:id} : delete the "id" bezetting.
     *
     * @param id the id of the bezetting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBezetting(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bezetting : {}", id);
        bezettingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
