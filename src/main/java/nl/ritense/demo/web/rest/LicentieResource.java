package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Licentie;
import nl.ritense.demo.repository.LicentieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Licentie}.
 */
@RestController
@RequestMapping("/api/licenties")
@Transactional
public class LicentieResource {

    private final Logger log = LoggerFactory.getLogger(LicentieResource.class);

    private static final String ENTITY_NAME = "licentie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicentieRepository licentieRepository;

    public LicentieResource(LicentieRepository licentieRepository) {
        this.licentieRepository = licentieRepository;
    }

    /**
     * {@code POST  /licenties} : Create a new licentie.
     *
     * @param licentie the licentie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licentie, or with status {@code 400 (Bad Request)} if the licentie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Licentie> createLicentie(@RequestBody Licentie licentie) throws URISyntaxException {
        log.debug("REST request to save Licentie : {}", licentie);
        if (licentie.getId() != null) {
            throw new BadRequestAlertException("A new licentie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        licentie = licentieRepository.save(licentie);
        return ResponseEntity.created(new URI("/api/licenties/" + licentie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, licentie.getId().toString()))
            .body(licentie);
    }

    /**
     * {@code GET  /licenties} : get all the licenties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licenties in body.
     */
    @GetMapping("")
    public List<Licentie> getAllLicenties() {
        log.debug("REST request to get all Licenties");
        return licentieRepository.findAll();
    }

    /**
     * {@code GET  /licenties/:id} : get the "id" licentie.
     *
     * @param id the id of the licentie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licentie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Licentie> getLicentie(@PathVariable("id") Long id) {
        log.debug("REST request to get Licentie : {}", id);
        Optional<Licentie> licentie = licentieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(licentie);
    }

    /**
     * {@code DELETE  /licenties/:id} : delete the "id" licentie.
     *
     * @param id the id of the licentie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicentie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Licentie : {}", id);
        licentieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
