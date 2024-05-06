package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Onderwijs;
import nl.ritense.demo.repository.OnderwijsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onderwijs}.
 */
@RestController
@RequestMapping("/api/onderwijs")
@Transactional
public class OnderwijsResource {

    private final Logger log = LoggerFactory.getLogger(OnderwijsResource.class);

    private static final String ENTITY_NAME = "onderwijs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderwijsRepository onderwijsRepository;

    public OnderwijsResource(OnderwijsRepository onderwijsRepository) {
        this.onderwijsRepository = onderwijsRepository;
    }

    /**
     * {@code POST  /onderwijs} : Create a new onderwijs.
     *
     * @param onderwijs the onderwijs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderwijs, or with status {@code 400 (Bad Request)} if the onderwijs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onderwijs> createOnderwijs(@RequestBody Onderwijs onderwijs) throws URISyntaxException {
        log.debug("REST request to save Onderwijs : {}", onderwijs);
        if (onderwijs.getId() != null) {
            throw new BadRequestAlertException("A new onderwijs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onderwijs = onderwijsRepository.save(onderwijs);
        return ResponseEntity.created(new URI("/api/onderwijs/" + onderwijs.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onderwijs.getId().toString()))
            .body(onderwijs);
    }

    /**
     * {@code GET  /onderwijs} : get all the onderwijs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderwijs in body.
     */
    @GetMapping("")
    public List<Onderwijs> getAllOnderwijs() {
        log.debug("REST request to get all Onderwijs");
        return onderwijsRepository.findAll();
    }

    /**
     * {@code GET  /onderwijs/:id} : get the "id" onderwijs.
     *
     * @param id the id of the onderwijs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderwijs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onderwijs> getOnderwijs(@PathVariable("id") Long id) {
        log.debug("REST request to get Onderwijs : {}", id);
        Optional<Onderwijs> onderwijs = onderwijsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderwijs);
    }

    /**
     * {@code DELETE  /onderwijs/:id} : delete the "id" onderwijs.
     *
     * @param id the id of the onderwijs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderwijs(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onderwijs : {}", id);
        onderwijsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
