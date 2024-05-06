package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Waarneming;
import nl.ritense.demo.repository.WaarnemingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Waarneming}.
 */
@RestController
@RequestMapping("/api/waarnemings")
@Transactional
public class WaarnemingResource {

    private final Logger log = LoggerFactory.getLogger(WaarnemingResource.class);

    private static final String ENTITY_NAME = "waarneming";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WaarnemingRepository waarnemingRepository;

    public WaarnemingResource(WaarnemingRepository waarnemingRepository) {
        this.waarnemingRepository = waarnemingRepository;
    }

    /**
     * {@code POST  /waarnemings} : Create a new waarneming.
     *
     * @param waarneming the waarneming to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new waarneming, or with status {@code 400 (Bad Request)} if the waarneming has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Waarneming> createWaarneming(@RequestBody Waarneming waarneming) throws URISyntaxException {
        log.debug("REST request to save Waarneming : {}", waarneming);
        if (waarneming.getId() != null) {
            throw new BadRequestAlertException("A new waarneming cannot already have an ID", ENTITY_NAME, "idexists");
        }
        waarneming = waarnemingRepository.save(waarneming);
        return ResponseEntity.created(new URI("/api/waarnemings/" + waarneming.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, waarneming.getId().toString()))
            .body(waarneming);
    }

    /**
     * {@code GET  /waarnemings} : get all the waarnemings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of waarnemings in body.
     */
    @GetMapping("")
    public List<Waarneming> getAllWaarnemings() {
        log.debug("REST request to get all Waarnemings");
        return waarnemingRepository.findAll();
    }

    /**
     * {@code GET  /waarnemings/:id} : get the "id" waarneming.
     *
     * @param id the id of the waarneming to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the waarneming, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Waarneming> getWaarneming(@PathVariable("id") Long id) {
        log.debug("REST request to get Waarneming : {}", id);
        Optional<Waarneming> waarneming = waarnemingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(waarneming);
    }

    /**
     * {@code DELETE  /waarnemings/:id} : delete the "id" waarneming.
     *
     * @param id the id of the waarneming to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaarneming(@PathVariable("id") Long id) {
        log.debug("REST request to delete Waarneming : {}", id);
        waarnemingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
