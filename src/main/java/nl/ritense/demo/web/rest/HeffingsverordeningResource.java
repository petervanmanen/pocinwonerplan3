package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Heffingsverordening;
import nl.ritense.demo.repository.HeffingsverordeningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Heffingsverordening}.
 */
@RestController
@RequestMapping("/api/heffingsverordenings")
@Transactional
public class HeffingsverordeningResource {

    private final Logger log = LoggerFactory.getLogger(HeffingsverordeningResource.class);

    private static final String ENTITY_NAME = "heffingsverordening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeffingsverordeningRepository heffingsverordeningRepository;

    public HeffingsverordeningResource(HeffingsverordeningRepository heffingsverordeningRepository) {
        this.heffingsverordeningRepository = heffingsverordeningRepository;
    }

    /**
     * {@code POST  /heffingsverordenings} : Create a new heffingsverordening.
     *
     * @param heffingsverordening the heffingsverordening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new heffingsverordening, or with status {@code 400 (Bad Request)} if the heffingsverordening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Heffingsverordening> createHeffingsverordening(@Valid @RequestBody Heffingsverordening heffingsverordening)
        throws URISyntaxException {
        log.debug("REST request to save Heffingsverordening : {}", heffingsverordening);
        if (heffingsverordening.getId() != null) {
            throw new BadRequestAlertException("A new heffingsverordening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        heffingsverordening = heffingsverordeningRepository.save(heffingsverordening);
        return ResponseEntity.created(new URI("/api/heffingsverordenings/" + heffingsverordening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, heffingsverordening.getId().toString()))
            .body(heffingsverordening);
    }

    /**
     * {@code GET  /heffingsverordenings} : get all the heffingsverordenings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heffingsverordenings in body.
     */
    @GetMapping("")
    public List<Heffingsverordening> getAllHeffingsverordenings() {
        log.debug("REST request to get all Heffingsverordenings");
        return heffingsverordeningRepository.findAll();
    }

    /**
     * {@code GET  /heffingsverordenings/:id} : get the "id" heffingsverordening.
     *
     * @param id the id of the heffingsverordening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the heffingsverordening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Heffingsverordening> getHeffingsverordening(@PathVariable("id") Long id) {
        log.debug("REST request to get Heffingsverordening : {}", id);
        Optional<Heffingsverordening> heffingsverordening = heffingsverordeningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(heffingsverordening);
    }

    /**
     * {@code DELETE  /heffingsverordenings/:id} : delete the "id" heffingsverordening.
     *
     * @param id the id of the heffingsverordening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeffingsverordening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Heffingsverordening : {}", id);
        heffingsverordeningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
