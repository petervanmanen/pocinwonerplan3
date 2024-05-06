package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Gemachtigde;
import nl.ritense.demo.repository.GemachtigdeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gemachtigde}.
 */
@RestController
@RequestMapping("/api/gemachtigdes")
@Transactional
public class GemachtigdeResource {

    private final Logger log = LoggerFactory.getLogger(GemachtigdeResource.class);

    private static final String ENTITY_NAME = "gemachtigde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GemachtigdeRepository gemachtigdeRepository;

    public GemachtigdeResource(GemachtigdeRepository gemachtigdeRepository) {
        this.gemachtigdeRepository = gemachtigdeRepository;
    }

    /**
     * {@code POST  /gemachtigdes} : Create a new gemachtigde.
     *
     * @param gemachtigde the gemachtigde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gemachtigde, or with status {@code 400 (Bad Request)} if the gemachtigde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gemachtigde> createGemachtigde(@RequestBody Gemachtigde gemachtigde) throws URISyntaxException {
        log.debug("REST request to save Gemachtigde : {}", gemachtigde);
        if (gemachtigde.getId() != null) {
            throw new BadRequestAlertException("A new gemachtigde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gemachtigde = gemachtigdeRepository.save(gemachtigde);
        return ResponseEntity.created(new URI("/api/gemachtigdes/" + gemachtigde.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gemachtigde.getId().toString()))
            .body(gemachtigde);
    }

    /**
     * {@code GET  /gemachtigdes} : get all the gemachtigdes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gemachtigdes in body.
     */
    @GetMapping("")
    public List<Gemachtigde> getAllGemachtigdes() {
        log.debug("REST request to get all Gemachtigdes");
        return gemachtigdeRepository.findAll();
    }

    /**
     * {@code GET  /gemachtigdes/:id} : get the "id" gemachtigde.
     *
     * @param id the id of the gemachtigde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gemachtigde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gemachtigde> getGemachtigde(@PathVariable("id") Long id) {
        log.debug("REST request to get Gemachtigde : {}", id);
        Optional<Gemachtigde> gemachtigde = gemachtigdeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gemachtigde);
    }

    /**
     * {@code DELETE  /gemachtigdes/:id} : delete the "id" gemachtigde.
     *
     * @param id the id of the gemachtigde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGemachtigde(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gemachtigde : {}", id);
        gemachtigdeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
