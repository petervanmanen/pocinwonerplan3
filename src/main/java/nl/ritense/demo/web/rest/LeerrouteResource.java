package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Leerroute;
import nl.ritense.demo.repository.LeerrouteRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leerroute}.
 */
@RestController
@RequestMapping("/api/leerroutes")
@Transactional
public class LeerrouteResource {

    private final Logger log = LoggerFactory.getLogger(LeerrouteResource.class);

    private static final String ENTITY_NAME = "leerroute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeerrouteRepository leerrouteRepository;

    public LeerrouteResource(LeerrouteRepository leerrouteRepository) {
        this.leerrouteRepository = leerrouteRepository;
    }

    /**
     * {@code POST  /leerroutes} : Create a new leerroute.
     *
     * @param leerroute the leerroute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leerroute, or with status {@code 400 (Bad Request)} if the leerroute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leerroute> createLeerroute(@RequestBody Leerroute leerroute) throws URISyntaxException {
        log.debug("REST request to save Leerroute : {}", leerroute);
        if (leerroute.getId() != null) {
            throw new BadRequestAlertException("A new leerroute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leerroute = leerrouteRepository.save(leerroute);
        return ResponseEntity.created(new URI("/api/leerroutes/" + leerroute.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leerroute.getId().toString()))
            .body(leerroute);
    }

    /**
     * {@code GET  /leerroutes} : get all the leerroutes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leerroutes in body.
     */
    @GetMapping("")
    public List<Leerroute> getAllLeerroutes() {
        log.debug("REST request to get all Leerroutes");
        return leerrouteRepository.findAll();
    }

    /**
     * {@code GET  /leerroutes/:id} : get the "id" leerroute.
     *
     * @param id the id of the leerroute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leerroute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leerroute> getLeerroute(@PathVariable("id") Long id) {
        log.debug("REST request to get Leerroute : {}", id);
        Optional<Leerroute> leerroute = leerrouteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leerroute);
    }

    /**
     * {@code DELETE  /leerroutes/:id} : delete the "id" leerroute.
     *
     * @param id the id of the leerroute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeerroute(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leerroute : {}", id);
        leerrouteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
