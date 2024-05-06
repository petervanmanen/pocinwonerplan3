package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Onderhoudskosten;
import nl.ritense.demo.repository.OnderhoudskostenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onderhoudskosten}.
 */
@RestController
@RequestMapping("/api/onderhoudskostens")
@Transactional
public class OnderhoudskostenResource {

    private final Logger log = LoggerFactory.getLogger(OnderhoudskostenResource.class);

    private static final String ENTITY_NAME = "onderhoudskosten";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderhoudskostenRepository onderhoudskostenRepository;

    public OnderhoudskostenResource(OnderhoudskostenRepository onderhoudskostenRepository) {
        this.onderhoudskostenRepository = onderhoudskostenRepository;
    }

    /**
     * {@code POST  /onderhoudskostens} : Create a new onderhoudskosten.
     *
     * @param onderhoudskosten the onderhoudskosten to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderhoudskosten, or with status {@code 400 (Bad Request)} if the onderhoudskosten has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onderhoudskosten> createOnderhoudskosten(@RequestBody Onderhoudskosten onderhoudskosten)
        throws URISyntaxException {
        log.debug("REST request to save Onderhoudskosten : {}", onderhoudskosten);
        if (onderhoudskosten.getId() != null) {
            throw new BadRequestAlertException("A new onderhoudskosten cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onderhoudskosten = onderhoudskostenRepository.save(onderhoudskosten);
        return ResponseEntity.created(new URI("/api/onderhoudskostens/" + onderhoudskosten.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onderhoudskosten.getId().toString()))
            .body(onderhoudskosten);
    }

    /**
     * {@code GET  /onderhoudskostens} : get all the onderhoudskostens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderhoudskostens in body.
     */
    @GetMapping("")
    public List<Onderhoudskosten> getAllOnderhoudskostens() {
        log.debug("REST request to get all Onderhoudskostens");
        return onderhoudskostenRepository.findAll();
    }

    /**
     * {@code GET  /onderhoudskostens/:id} : get the "id" onderhoudskosten.
     *
     * @param id the id of the onderhoudskosten to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderhoudskosten, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onderhoudskosten> getOnderhoudskosten(@PathVariable("id") Long id) {
        log.debug("REST request to get Onderhoudskosten : {}", id);
        Optional<Onderhoudskosten> onderhoudskosten = onderhoudskostenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderhoudskosten);
    }

    /**
     * {@code DELETE  /onderhoudskostens/:id} : delete the "id" onderhoudskosten.
     *
     * @param id the id of the onderhoudskosten to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderhoudskosten(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onderhoudskosten : {}", id);
        onderhoudskostenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
