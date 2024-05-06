package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Rechthebbende;
import nl.ritense.demo.repository.RechthebbendeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Rechthebbende}.
 */
@RestController
@RequestMapping("/api/rechthebbendes")
@Transactional
public class RechthebbendeResource {

    private final Logger log = LoggerFactory.getLogger(RechthebbendeResource.class);

    private static final String ENTITY_NAME = "rechthebbende";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RechthebbendeRepository rechthebbendeRepository;

    public RechthebbendeResource(RechthebbendeRepository rechthebbendeRepository) {
        this.rechthebbendeRepository = rechthebbendeRepository;
    }

    /**
     * {@code POST  /rechthebbendes} : Create a new rechthebbende.
     *
     * @param rechthebbende the rechthebbende to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rechthebbende, or with status {@code 400 (Bad Request)} if the rechthebbende has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rechthebbende> createRechthebbende(@RequestBody Rechthebbende rechthebbende) throws URISyntaxException {
        log.debug("REST request to save Rechthebbende : {}", rechthebbende);
        if (rechthebbende.getId() != null) {
            throw new BadRequestAlertException("A new rechthebbende cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rechthebbende = rechthebbendeRepository.save(rechthebbende);
        return ResponseEntity.created(new URI("/api/rechthebbendes/" + rechthebbende.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rechthebbende.getId().toString()))
            .body(rechthebbende);
    }

    /**
     * {@code GET  /rechthebbendes} : get all the rechthebbendes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rechthebbendes in body.
     */
    @GetMapping("")
    public List<Rechthebbende> getAllRechthebbendes() {
        log.debug("REST request to get all Rechthebbendes");
        return rechthebbendeRepository.findAll();
    }

    /**
     * {@code GET  /rechthebbendes/:id} : get the "id" rechthebbende.
     *
     * @param id the id of the rechthebbende to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rechthebbende, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rechthebbende> getRechthebbende(@PathVariable("id") Long id) {
        log.debug("REST request to get Rechthebbende : {}", id);
        Optional<Rechthebbende> rechthebbende = rechthebbendeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rechthebbende);
    }

    /**
     * {@code DELETE  /rechthebbendes/:id} : delete the "id" rechthebbende.
     *
     * @param id the id of the rechthebbende to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRechthebbende(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rechthebbende : {}", id);
        rechthebbendeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
