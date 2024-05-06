package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Telefoniegegevens;
import nl.ritense.demo.repository.TelefoniegegevensRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Telefoniegegevens}.
 */
@RestController
@RequestMapping("/api/telefoniegegevens")
@Transactional
public class TelefoniegegevensResource {

    private final Logger log = LoggerFactory.getLogger(TelefoniegegevensResource.class);

    private static final String ENTITY_NAME = "telefoniegegevens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelefoniegegevensRepository telefoniegegevensRepository;

    public TelefoniegegevensResource(TelefoniegegevensRepository telefoniegegevensRepository) {
        this.telefoniegegevensRepository = telefoniegegevensRepository;
    }

    /**
     * {@code POST  /telefoniegegevens} : Create a new telefoniegegevens.
     *
     * @param telefoniegegevens the telefoniegegevens to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telefoniegegevens, or with status {@code 400 (Bad Request)} if the telefoniegegevens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Telefoniegegevens> createTelefoniegegevens(@RequestBody Telefoniegegevens telefoniegegevens)
        throws URISyntaxException {
        log.debug("REST request to save Telefoniegegevens : {}", telefoniegegevens);
        if (telefoniegegevens.getId() != null) {
            throw new BadRequestAlertException("A new telefoniegegevens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        telefoniegegevens = telefoniegegevensRepository.save(telefoniegegevens);
        return ResponseEntity.created(new URI("/api/telefoniegegevens/" + telefoniegegevens.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, telefoniegegevens.getId().toString()))
            .body(telefoniegegevens);
    }

    /**
     * {@code GET  /telefoniegegevens} : get all the telefoniegegevens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telefoniegegevens in body.
     */
    @GetMapping("")
    public List<Telefoniegegevens> getAllTelefoniegegevens() {
        log.debug("REST request to get all Telefoniegegevens");
        return telefoniegegevensRepository.findAll();
    }

    /**
     * {@code GET  /telefoniegegevens/:id} : get the "id" telefoniegegevens.
     *
     * @param id the id of the telefoniegegevens to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telefoniegegevens, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Telefoniegegevens> getTelefoniegegevens(@PathVariable("id") Long id) {
        log.debug("REST request to get Telefoniegegevens : {}", id);
        Optional<Telefoniegegevens> telefoniegegevens = telefoniegegevensRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(telefoniegegevens);
    }

    /**
     * {@code DELETE  /telefoniegegevens/:id} : delete the "id" telefoniegegevens.
     *
     * @param id the id of the telefoniegegevens to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelefoniegegevens(@PathVariable("id") Long id) {
        log.debug("REST request to delete Telefoniegegevens : {}", id);
        telefoniegegevensRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
