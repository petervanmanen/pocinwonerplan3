package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Eigenaar;
import nl.ritense.demo.repository.EigenaarRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eigenaar}.
 */
@RestController
@RequestMapping("/api/eigenaars")
@Transactional
public class EigenaarResource {

    private final Logger log = LoggerFactory.getLogger(EigenaarResource.class);

    private static final String ENTITY_NAME = "eigenaar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EigenaarRepository eigenaarRepository;

    public EigenaarResource(EigenaarRepository eigenaarRepository) {
        this.eigenaarRepository = eigenaarRepository;
    }

    /**
     * {@code POST  /eigenaars} : Create a new eigenaar.
     *
     * @param eigenaar the eigenaar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eigenaar, or with status {@code 400 (Bad Request)} if the eigenaar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eigenaar> createEigenaar(@RequestBody Eigenaar eigenaar) throws URISyntaxException {
        log.debug("REST request to save Eigenaar : {}", eigenaar);
        if (eigenaar.getId() != null) {
            throw new BadRequestAlertException("A new eigenaar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eigenaar = eigenaarRepository.save(eigenaar);
        return ResponseEntity.created(new URI("/api/eigenaars/" + eigenaar.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eigenaar.getId().toString()))
            .body(eigenaar);
    }

    /**
     * {@code GET  /eigenaars} : get all the eigenaars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eigenaars in body.
     */
    @GetMapping("")
    public List<Eigenaar> getAllEigenaars() {
        log.debug("REST request to get all Eigenaars");
        return eigenaarRepository.findAll();
    }

    /**
     * {@code GET  /eigenaars/:id} : get the "id" eigenaar.
     *
     * @param id the id of the eigenaar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eigenaar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eigenaar> getEigenaar(@PathVariable("id") Long id) {
        log.debug("REST request to get Eigenaar : {}", id);
        Optional<Eigenaar> eigenaar = eigenaarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eigenaar);
    }

    /**
     * {@code DELETE  /eigenaars/:id} : delete the "id" eigenaar.
     *
     * @param id the id of the eigenaar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEigenaar(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eigenaar : {}", id);
        eigenaarRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
