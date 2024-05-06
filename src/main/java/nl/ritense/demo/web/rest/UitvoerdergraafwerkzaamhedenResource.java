package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Uitvoerdergraafwerkzaamheden;
import nl.ritense.demo.repository.UitvoerdergraafwerkzaamhedenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitvoerdergraafwerkzaamheden}.
 */
@RestController
@RequestMapping("/api/uitvoerdergraafwerkzaamhedens")
@Transactional
public class UitvoerdergraafwerkzaamhedenResource {

    private final Logger log = LoggerFactory.getLogger(UitvoerdergraafwerkzaamhedenResource.class);

    private static final String ENTITY_NAME = "uitvoerdergraafwerkzaamheden";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitvoerdergraafwerkzaamhedenRepository uitvoerdergraafwerkzaamhedenRepository;

    public UitvoerdergraafwerkzaamhedenResource(UitvoerdergraafwerkzaamhedenRepository uitvoerdergraafwerkzaamhedenRepository) {
        this.uitvoerdergraafwerkzaamhedenRepository = uitvoerdergraafwerkzaamhedenRepository;
    }

    /**
     * {@code POST  /uitvoerdergraafwerkzaamhedens} : Create a new uitvoerdergraafwerkzaamheden.
     *
     * @param uitvoerdergraafwerkzaamheden the uitvoerdergraafwerkzaamheden to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitvoerdergraafwerkzaamheden, or with status {@code 400 (Bad Request)} if the uitvoerdergraafwerkzaamheden has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitvoerdergraafwerkzaamheden> createUitvoerdergraafwerkzaamheden(
        @RequestBody Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden
    ) throws URISyntaxException {
        log.debug("REST request to save Uitvoerdergraafwerkzaamheden : {}", uitvoerdergraafwerkzaamheden);
        if (uitvoerdergraafwerkzaamheden.getId() != null) {
            throw new BadRequestAlertException("A new uitvoerdergraafwerkzaamheden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitvoerdergraafwerkzaamheden = uitvoerdergraafwerkzaamhedenRepository.save(uitvoerdergraafwerkzaamheden);
        return ResponseEntity.created(new URI("/api/uitvoerdergraafwerkzaamhedens/" + uitvoerdergraafwerkzaamheden.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitvoerdergraafwerkzaamheden.getId().toString())
            )
            .body(uitvoerdergraafwerkzaamheden);
    }

    /**
     * {@code GET  /uitvoerdergraafwerkzaamhedens} : get all the uitvoerdergraafwerkzaamhedens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitvoerdergraafwerkzaamhedens in body.
     */
    @GetMapping("")
    public List<Uitvoerdergraafwerkzaamheden> getAllUitvoerdergraafwerkzaamhedens() {
        log.debug("REST request to get all Uitvoerdergraafwerkzaamhedens");
        return uitvoerdergraafwerkzaamhedenRepository.findAll();
    }

    /**
     * {@code GET  /uitvoerdergraafwerkzaamhedens/:id} : get the "id" uitvoerdergraafwerkzaamheden.
     *
     * @param id the id of the uitvoerdergraafwerkzaamheden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitvoerdergraafwerkzaamheden, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitvoerdergraafwerkzaamheden> getUitvoerdergraafwerkzaamheden(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitvoerdergraafwerkzaamheden : {}", id);
        Optional<Uitvoerdergraafwerkzaamheden> uitvoerdergraafwerkzaamheden = uitvoerdergraafwerkzaamhedenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitvoerdergraafwerkzaamheden);
    }

    /**
     * {@code DELETE  /uitvoerdergraafwerkzaamhedens/:id} : delete the "id" uitvoerdergraafwerkzaamheden.
     *
     * @param id the id of the uitvoerdergraafwerkzaamheden to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitvoerdergraafwerkzaamheden(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitvoerdergraafwerkzaamheden : {}", id);
        uitvoerdergraafwerkzaamhedenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
