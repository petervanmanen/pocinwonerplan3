package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Grondbeheerder;
import nl.ritense.demo.repository.GrondbeheerderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Grondbeheerder}.
 */
@RestController
@RequestMapping("/api/grondbeheerders")
@Transactional
public class GrondbeheerderResource {

    private final Logger log = LoggerFactory.getLogger(GrondbeheerderResource.class);

    private static final String ENTITY_NAME = "grondbeheerder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrondbeheerderRepository grondbeheerderRepository;

    public GrondbeheerderResource(GrondbeheerderRepository grondbeheerderRepository) {
        this.grondbeheerderRepository = grondbeheerderRepository;
    }

    /**
     * {@code POST  /grondbeheerders} : Create a new grondbeheerder.
     *
     * @param grondbeheerder the grondbeheerder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grondbeheerder, or with status {@code 400 (Bad Request)} if the grondbeheerder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Grondbeheerder> createGrondbeheerder(@RequestBody Grondbeheerder grondbeheerder) throws URISyntaxException {
        log.debug("REST request to save Grondbeheerder : {}", grondbeheerder);
        if (grondbeheerder.getId() != null) {
            throw new BadRequestAlertException("A new grondbeheerder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grondbeheerder = grondbeheerderRepository.save(grondbeheerder);
        return ResponseEntity.created(new URI("/api/grondbeheerders/" + grondbeheerder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, grondbeheerder.getId().toString()))
            .body(grondbeheerder);
    }

    /**
     * {@code GET  /grondbeheerders} : get all the grondbeheerders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grondbeheerders in body.
     */
    @GetMapping("")
    public List<Grondbeheerder> getAllGrondbeheerders() {
        log.debug("REST request to get all Grondbeheerders");
        return grondbeheerderRepository.findAll();
    }

    /**
     * {@code GET  /grondbeheerders/:id} : get the "id" grondbeheerder.
     *
     * @param id the id of the grondbeheerder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grondbeheerder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Grondbeheerder> getGrondbeheerder(@PathVariable("id") Long id) {
        log.debug("REST request to get Grondbeheerder : {}", id);
        Optional<Grondbeheerder> grondbeheerder = grondbeheerderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grondbeheerder);
    }

    /**
     * {@code DELETE  /grondbeheerders/:id} : delete the "id" grondbeheerder.
     *
     * @param id the id of the grondbeheerder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrondbeheerder(@PathVariable("id") Long id) {
        log.debug("REST request to delete Grondbeheerder : {}", id);
        grondbeheerderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
