package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Naderetoegang;
import nl.ritense.demo.repository.NaderetoegangRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Naderetoegang}.
 */
@RestController
@RequestMapping("/api/naderetoegangs")
@Transactional
public class NaderetoegangResource {

    private final Logger log = LoggerFactory.getLogger(NaderetoegangResource.class);

    private static final String ENTITY_NAME = "naderetoegang";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaderetoegangRepository naderetoegangRepository;

    public NaderetoegangResource(NaderetoegangRepository naderetoegangRepository) {
        this.naderetoegangRepository = naderetoegangRepository;
    }

    /**
     * {@code POST  /naderetoegangs} : Create a new naderetoegang.
     *
     * @param naderetoegang the naderetoegang to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naderetoegang, or with status {@code 400 (Bad Request)} if the naderetoegang has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Naderetoegang> createNaderetoegang(@RequestBody Naderetoegang naderetoegang) throws URISyntaxException {
        log.debug("REST request to save Naderetoegang : {}", naderetoegang);
        if (naderetoegang.getId() != null) {
            throw new BadRequestAlertException("A new naderetoegang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        naderetoegang = naderetoegangRepository.save(naderetoegang);
        return ResponseEntity.created(new URI("/api/naderetoegangs/" + naderetoegang.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, naderetoegang.getId().toString()))
            .body(naderetoegang);
    }

    /**
     * {@code GET  /naderetoegangs} : get all the naderetoegangs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naderetoegangs in body.
     */
    @GetMapping("")
    public List<Naderetoegang> getAllNaderetoegangs() {
        log.debug("REST request to get all Naderetoegangs");
        return naderetoegangRepository.findAll();
    }

    /**
     * {@code GET  /naderetoegangs/:id} : get the "id" naderetoegang.
     *
     * @param id the id of the naderetoegang to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naderetoegang, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Naderetoegang> getNaderetoegang(@PathVariable("id") Long id) {
        log.debug("REST request to get Naderetoegang : {}", id);
        Optional<Naderetoegang> naderetoegang = naderetoegangRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(naderetoegang);
    }

    /**
     * {@code DELETE  /naderetoegangs/:id} : delete the "id" naderetoegang.
     *
     * @param id the id of the naderetoegang to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNaderetoegang(@PathVariable("id") Long id) {
        log.debug("REST request to delete Naderetoegang : {}", id);
        naderetoegangRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
