package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Aanbestedingvastgoed;
import nl.ritense.demo.repository.AanbestedingvastgoedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanbestedingvastgoed}.
 */
@RestController
@RequestMapping("/api/aanbestedingvastgoeds")
@Transactional
public class AanbestedingvastgoedResource {

    private final Logger log = LoggerFactory.getLogger(AanbestedingvastgoedResource.class);

    private static final String ENTITY_NAME = "aanbestedingvastgoed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanbestedingvastgoedRepository aanbestedingvastgoedRepository;

    public AanbestedingvastgoedResource(AanbestedingvastgoedRepository aanbestedingvastgoedRepository) {
        this.aanbestedingvastgoedRepository = aanbestedingvastgoedRepository;
    }

    /**
     * {@code POST  /aanbestedingvastgoeds} : Create a new aanbestedingvastgoed.
     *
     * @param aanbestedingvastgoed the aanbestedingvastgoed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanbestedingvastgoed, or with status {@code 400 (Bad Request)} if the aanbestedingvastgoed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanbestedingvastgoed> createAanbestedingvastgoed(@RequestBody Aanbestedingvastgoed aanbestedingvastgoed)
        throws URISyntaxException {
        log.debug("REST request to save Aanbestedingvastgoed : {}", aanbestedingvastgoed);
        if (aanbestedingvastgoed.getId() != null) {
            throw new BadRequestAlertException("A new aanbestedingvastgoed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanbestedingvastgoed = aanbestedingvastgoedRepository.save(aanbestedingvastgoed);
        return ResponseEntity.created(new URI("/api/aanbestedingvastgoeds/" + aanbestedingvastgoed.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanbestedingvastgoed.getId().toString()))
            .body(aanbestedingvastgoed);
    }

    /**
     * {@code GET  /aanbestedingvastgoeds} : get all the aanbestedingvastgoeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanbestedingvastgoeds in body.
     */
    @GetMapping("")
    public List<Aanbestedingvastgoed> getAllAanbestedingvastgoeds() {
        log.debug("REST request to get all Aanbestedingvastgoeds");
        return aanbestedingvastgoedRepository.findAll();
    }

    /**
     * {@code GET  /aanbestedingvastgoeds/:id} : get the "id" aanbestedingvastgoed.
     *
     * @param id the id of the aanbestedingvastgoed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanbestedingvastgoed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanbestedingvastgoed> getAanbestedingvastgoed(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanbestedingvastgoed : {}", id);
        Optional<Aanbestedingvastgoed> aanbestedingvastgoed = aanbestedingvastgoedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanbestedingvastgoed);
    }

    /**
     * {@code DELETE  /aanbestedingvastgoeds/:id} : delete the "id" aanbestedingvastgoed.
     *
     * @param id the id of the aanbestedingvastgoed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanbestedingvastgoed(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanbestedingvastgoed : {}", id);
        aanbestedingvastgoedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
