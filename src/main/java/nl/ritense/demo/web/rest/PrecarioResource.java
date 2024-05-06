package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Precario;
import nl.ritense.demo.repository.PrecarioRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Precario}.
 */
@RestController
@RequestMapping("/api/precarios")
@Transactional
public class PrecarioResource {

    private final Logger log = LoggerFactory.getLogger(PrecarioResource.class);

    private static final String ENTITY_NAME = "precario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrecarioRepository precarioRepository;

    public PrecarioResource(PrecarioRepository precarioRepository) {
        this.precarioRepository = precarioRepository;
    }

    /**
     * {@code POST  /precarios} : Create a new precario.
     *
     * @param precario the precario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new precario, or with status {@code 400 (Bad Request)} if the precario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Precario> createPrecario(@RequestBody Precario precario) throws URISyntaxException {
        log.debug("REST request to save Precario : {}", precario);
        if (precario.getId() != null) {
            throw new BadRequestAlertException("A new precario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        precario = precarioRepository.save(precario);
        return ResponseEntity.created(new URI("/api/precarios/" + precario.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, precario.getId().toString()))
            .body(precario);
    }

    /**
     * {@code GET  /precarios} : get all the precarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of precarios in body.
     */
    @GetMapping("")
    public List<Precario> getAllPrecarios() {
        log.debug("REST request to get all Precarios");
        return precarioRepository.findAll();
    }

    /**
     * {@code GET  /precarios/:id} : get the "id" precario.
     *
     * @param id the id of the precario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the precario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Precario> getPrecario(@PathVariable("id") Long id) {
        log.debug("REST request to get Precario : {}", id);
        Optional<Precario> precario = precarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(precario);
    }

    /**
     * {@code DELETE  /precarios/:id} : delete the "id" precario.
     *
     * @param id the id of the precario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrecario(@PathVariable("id") Long id) {
        log.debug("REST request to delete Precario : {}", id);
        precarioRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
