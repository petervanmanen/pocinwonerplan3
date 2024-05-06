package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Bezoeker;
import nl.ritense.demo.repository.BezoekerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bezoeker}.
 */
@RestController
@RequestMapping("/api/bezoekers")
@Transactional
public class BezoekerResource {

    private final Logger log = LoggerFactory.getLogger(BezoekerResource.class);

    private static final String ENTITY_NAME = "bezoeker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BezoekerRepository bezoekerRepository;

    public BezoekerResource(BezoekerRepository bezoekerRepository) {
        this.bezoekerRepository = bezoekerRepository;
    }

    /**
     * {@code POST  /bezoekers} : Create a new bezoeker.
     *
     * @param bezoeker the bezoeker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bezoeker, or with status {@code 400 (Bad Request)} if the bezoeker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bezoeker> createBezoeker(@Valid @RequestBody Bezoeker bezoeker) throws URISyntaxException {
        log.debug("REST request to save Bezoeker : {}", bezoeker);
        if (bezoeker.getId() != null) {
            throw new BadRequestAlertException("A new bezoeker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bezoeker = bezoekerRepository.save(bezoeker);
        return ResponseEntity.created(new URI("/api/bezoekers/" + bezoeker.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bezoeker.getId().toString()))
            .body(bezoeker);
    }

    /**
     * {@code GET  /bezoekers} : get all the bezoekers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bezoekers in body.
     */
    @GetMapping("")
    public List<Bezoeker> getAllBezoekers() {
        log.debug("REST request to get all Bezoekers");
        return bezoekerRepository.findAll();
    }

    /**
     * {@code GET  /bezoekers/:id} : get the "id" bezoeker.
     *
     * @param id the id of the bezoeker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bezoeker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bezoeker> getBezoeker(@PathVariable("id") Long id) {
        log.debug("REST request to get Bezoeker : {}", id);
        Optional<Bezoeker> bezoeker = bezoekerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bezoeker);
    }

    /**
     * {@code DELETE  /bezoekers/:id} : delete the "id" bezoeker.
     *
     * @param id the id of the bezoeker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBezoeker(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bezoeker : {}", id);
        bezoekerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
