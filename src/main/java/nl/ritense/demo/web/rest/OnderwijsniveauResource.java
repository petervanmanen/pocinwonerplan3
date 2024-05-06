package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Onderwijsniveau;
import nl.ritense.demo.repository.OnderwijsniveauRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onderwijsniveau}.
 */
@RestController
@RequestMapping("/api/onderwijsniveaus")
@Transactional
public class OnderwijsniveauResource {

    private final Logger log = LoggerFactory.getLogger(OnderwijsniveauResource.class);

    private static final String ENTITY_NAME = "onderwijsniveau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderwijsniveauRepository onderwijsniveauRepository;

    public OnderwijsniveauResource(OnderwijsniveauRepository onderwijsniveauRepository) {
        this.onderwijsniveauRepository = onderwijsniveauRepository;
    }

    /**
     * {@code POST  /onderwijsniveaus} : Create a new onderwijsniveau.
     *
     * @param onderwijsniveau the onderwijsniveau to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderwijsniveau, or with status {@code 400 (Bad Request)} if the onderwijsniveau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onderwijsniveau> createOnderwijsniveau(@RequestBody Onderwijsniveau onderwijsniveau) throws URISyntaxException {
        log.debug("REST request to save Onderwijsniveau : {}", onderwijsniveau);
        if (onderwijsniveau.getId() != null) {
            throw new BadRequestAlertException("A new onderwijsniveau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onderwijsniveau = onderwijsniveauRepository.save(onderwijsniveau);
        return ResponseEntity.created(new URI("/api/onderwijsniveaus/" + onderwijsniveau.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onderwijsniveau.getId().toString()))
            .body(onderwijsniveau);
    }

    /**
     * {@code GET  /onderwijsniveaus} : get all the onderwijsniveaus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderwijsniveaus in body.
     */
    @GetMapping("")
    public List<Onderwijsniveau> getAllOnderwijsniveaus() {
        log.debug("REST request to get all Onderwijsniveaus");
        return onderwijsniveauRepository.findAll();
    }

    /**
     * {@code GET  /onderwijsniveaus/:id} : get the "id" onderwijsniveau.
     *
     * @param id the id of the onderwijsniveau to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderwijsniveau, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onderwijsniveau> getOnderwijsniveau(@PathVariable("id") Long id) {
        log.debug("REST request to get Onderwijsniveau : {}", id);
        Optional<Onderwijsniveau> onderwijsniveau = onderwijsniveauRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderwijsniveau);
    }

    /**
     * {@code DELETE  /onderwijsniveaus/:id} : delete the "id" onderwijsniveau.
     *
     * @param id the id of the onderwijsniveau to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderwijsniveau(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onderwijsniveau : {}", id);
        onderwijsniveauRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
