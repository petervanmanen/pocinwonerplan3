package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Maatregelen;
import nl.ritense.demo.repository.MaatregelenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Maatregelen}.
 */
@RestController
@RequestMapping("/api/maatregelens")
@Transactional
public class MaatregelenResource {

    private final Logger log = LoggerFactory.getLogger(MaatregelenResource.class);

    private static final String ENTITY_NAME = "maatregelen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaatregelenRepository maatregelenRepository;

    public MaatregelenResource(MaatregelenRepository maatregelenRepository) {
        this.maatregelenRepository = maatregelenRepository;
    }

    /**
     * {@code POST  /maatregelens} : Create a new maatregelen.
     *
     * @param maatregelen the maatregelen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maatregelen, or with status {@code 400 (Bad Request)} if the maatregelen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Maatregelen> createMaatregelen(@RequestBody Maatregelen maatregelen) throws URISyntaxException {
        log.debug("REST request to save Maatregelen : {}", maatregelen);
        if (maatregelen.getId() != null) {
            throw new BadRequestAlertException("A new maatregelen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        maatregelen = maatregelenRepository.save(maatregelen);
        return ResponseEntity.created(new URI("/api/maatregelens/" + maatregelen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, maatregelen.getId().toString()))
            .body(maatregelen);
    }

    /**
     * {@code GET  /maatregelens} : get all the maatregelens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maatregelens in body.
     */
    @GetMapping("")
    public List<Maatregelen> getAllMaatregelens() {
        log.debug("REST request to get all Maatregelens");
        return maatregelenRepository.findAll();
    }

    /**
     * {@code GET  /maatregelens/:id} : get the "id" maatregelen.
     *
     * @param id the id of the maatregelen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maatregelen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Maatregelen> getMaatregelen(@PathVariable("id") Long id) {
        log.debug("REST request to get Maatregelen : {}", id);
        Optional<Maatregelen> maatregelen = maatregelenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(maatregelen);
    }

    /**
     * {@code DELETE  /maatregelens/:id} : delete the "id" maatregelen.
     *
     * @param id the id of the maatregelen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaatregelen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Maatregelen : {}", id);
        maatregelenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
