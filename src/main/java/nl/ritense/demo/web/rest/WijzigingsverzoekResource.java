package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Wijzigingsverzoek;
import nl.ritense.demo.repository.WijzigingsverzoekRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Wijzigingsverzoek}.
 */
@RestController
@RequestMapping("/api/wijzigingsverzoeks")
@Transactional
public class WijzigingsverzoekResource {

    private final Logger log = LoggerFactory.getLogger(WijzigingsverzoekResource.class);

    private static final String ENTITY_NAME = "wijzigingsverzoek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WijzigingsverzoekRepository wijzigingsverzoekRepository;

    public WijzigingsverzoekResource(WijzigingsverzoekRepository wijzigingsverzoekRepository) {
        this.wijzigingsverzoekRepository = wijzigingsverzoekRepository;
    }

    /**
     * {@code POST  /wijzigingsverzoeks} : Create a new wijzigingsverzoek.
     *
     * @param wijzigingsverzoek the wijzigingsverzoek to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wijzigingsverzoek, or with status {@code 400 (Bad Request)} if the wijzigingsverzoek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wijzigingsverzoek> createWijzigingsverzoek(@RequestBody Wijzigingsverzoek wijzigingsverzoek)
        throws URISyntaxException {
        log.debug("REST request to save Wijzigingsverzoek : {}", wijzigingsverzoek);
        if (wijzigingsverzoek.getId() != null) {
            throw new BadRequestAlertException("A new wijzigingsverzoek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wijzigingsverzoek = wijzigingsverzoekRepository.save(wijzigingsverzoek);
        return ResponseEntity.created(new URI("/api/wijzigingsverzoeks/" + wijzigingsverzoek.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, wijzigingsverzoek.getId().toString()))
            .body(wijzigingsverzoek);
    }

    /**
     * {@code GET  /wijzigingsverzoeks} : get all the wijzigingsverzoeks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wijzigingsverzoeks in body.
     */
    @GetMapping("")
    public List<Wijzigingsverzoek> getAllWijzigingsverzoeks() {
        log.debug("REST request to get all Wijzigingsverzoeks");
        return wijzigingsverzoekRepository.findAll();
    }

    /**
     * {@code GET  /wijzigingsverzoeks/:id} : get the "id" wijzigingsverzoek.
     *
     * @param id the id of the wijzigingsverzoek to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wijzigingsverzoek, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wijzigingsverzoek> getWijzigingsverzoek(@PathVariable("id") Long id) {
        log.debug("REST request to get Wijzigingsverzoek : {}", id);
        Optional<Wijzigingsverzoek> wijzigingsverzoek = wijzigingsverzoekRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wijzigingsverzoek);
    }

    /**
     * {@code DELETE  /wijzigingsverzoeks/:id} : delete the "id" wijzigingsverzoek.
     *
     * @param id the id of the wijzigingsverzoek to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWijzigingsverzoek(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wijzigingsverzoek : {}", id);
        wijzigingsverzoekRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
