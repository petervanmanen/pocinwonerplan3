package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Onderhoud;
import nl.ritense.demo.repository.OnderhoudRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onderhoud}.
 */
@RestController
@RequestMapping("/api/onderhouds")
@Transactional
public class OnderhoudResource {

    private final Logger log = LoggerFactory.getLogger(OnderhoudResource.class);

    private static final String ENTITY_NAME = "onderhoud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderhoudRepository onderhoudRepository;

    public OnderhoudResource(OnderhoudRepository onderhoudRepository) {
        this.onderhoudRepository = onderhoudRepository;
    }

    /**
     * {@code POST  /onderhouds} : Create a new onderhoud.
     *
     * @param onderhoud the onderhoud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderhoud, or with status {@code 400 (Bad Request)} if the onderhoud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onderhoud> createOnderhoud(@RequestBody Onderhoud onderhoud) throws URISyntaxException {
        log.debug("REST request to save Onderhoud : {}", onderhoud);
        if (onderhoud.getId() != null) {
            throw new BadRequestAlertException("A new onderhoud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onderhoud = onderhoudRepository.save(onderhoud);
        return ResponseEntity.created(new URI("/api/onderhouds/" + onderhoud.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onderhoud.getId().toString()))
            .body(onderhoud);
    }

    /**
     * {@code GET  /onderhouds} : get all the onderhouds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderhouds in body.
     */
    @GetMapping("")
    public List<Onderhoud> getAllOnderhouds() {
        log.debug("REST request to get all Onderhouds");
        return onderhoudRepository.findAll();
    }

    /**
     * {@code GET  /onderhouds/:id} : get the "id" onderhoud.
     *
     * @param id the id of the onderhoud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderhoud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onderhoud> getOnderhoud(@PathVariable("id") Long id) {
        log.debug("REST request to get Onderhoud : {}", id);
        Optional<Onderhoud> onderhoud = onderhoudRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderhoud);
    }

    /**
     * {@code DELETE  /onderhouds/:id} : delete the "id" onderhoud.
     *
     * @param id the id of the onderhoud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderhoud(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onderhoud : {}", id);
        onderhoudRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
