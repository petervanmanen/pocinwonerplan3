package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Conclusie;
import nl.ritense.demo.repository.ConclusieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Conclusie}.
 */
@RestController
@RequestMapping("/api/conclusies")
@Transactional
public class ConclusieResource {

    private final Logger log = LoggerFactory.getLogger(ConclusieResource.class);

    private static final String ENTITY_NAME = "conclusie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConclusieRepository conclusieRepository;

    public ConclusieResource(ConclusieRepository conclusieRepository) {
        this.conclusieRepository = conclusieRepository;
    }

    /**
     * {@code POST  /conclusies} : Create a new conclusie.
     *
     * @param conclusie the conclusie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conclusie, or with status {@code 400 (Bad Request)} if the conclusie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Conclusie> createConclusie(@RequestBody Conclusie conclusie) throws URISyntaxException {
        log.debug("REST request to save Conclusie : {}", conclusie);
        if (conclusie.getId() != null) {
            throw new BadRequestAlertException("A new conclusie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        conclusie = conclusieRepository.save(conclusie);
        return ResponseEntity.created(new URI("/api/conclusies/" + conclusie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, conclusie.getId().toString()))
            .body(conclusie);
    }

    /**
     * {@code GET  /conclusies} : get all the conclusies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conclusies in body.
     */
    @GetMapping("")
    public List<Conclusie> getAllConclusies() {
        log.debug("REST request to get all Conclusies");
        return conclusieRepository.findAll();
    }

    /**
     * {@code GET  /conclusies/:id} : get the "id" conclusie.
     *
     * @param id the id of the conclusie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conclusie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Conclusie> getConclusie(@PathVariable("id") Long id) {
        log.debug("REST request to get Conclusie : {}", id);
        Optional<Conclusie> conclusie = conclusieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conclusie);
    }

    /**
     * {@code DELETE  /conclusies/:id} : delete the "id" conclusie.
     *
     * @param id the id of the conclusie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConclusie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Conclusie : {}", id);
        conclusieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
