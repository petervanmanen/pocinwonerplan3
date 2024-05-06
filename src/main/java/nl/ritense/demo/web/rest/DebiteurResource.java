package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Debiteur;
import nl.ritense.demo.repository.DebiteurRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Debiteur}.
 */
@RestController
@RequestMapping("/api/debiteurs")
@Transactional
public class DebiteurResource {

    private final Logger log = LoggerFactory.getLogger(DebiteurResource.class);

    private static final String ENTITY_NAME = "debiteur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DebiteurRepository debiteurRepository;

    public DebiteurResource(DebiteurRepository debiteurRepository) {
        this.debiteurRepository = debiteurRepository;
    }

    /**
     * {@code POST  /debiteurs} : Create a new debiteur.
     *
     * @param debiteur the debiteur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new debiteur, or with status {@code 400 (Bad Request)} if the debiteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Debiteur> createDebiteur(@Valid @RequestBody Debiteur debiteur) throws URISyntaxException {
        log.debug("REST request to save Debiteur : {}", debiteur);
        if (debiteur.getId() != null) {
            throw new BadRequestAlertException("A new debiteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        debiteur = debiteurRepository.save(debiteur);
        return ResponseEntity.created(new URI("/api/debiteurs/" + debiteur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, debiteur.getId().toString()))
            .body(debiteur);
    }

    /**
     * {@code GET  /debiteurs} : get all the debiteurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of debiteurs in body.
     */
    @GetMapping("")
    public List<Debiteur> getAllDebiteurs() {
        log.debug("REST request to get all Debiteurs");
        return debiteurRepository.findAll();
    }

    /**
     * {@code GET  /debiteurs/:id} : get the "id" debiteur.
     *
     * @param id the id of the debiteur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the debiteur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Debiteur> getDebiteur(@PathVariable("id") Long id) {
        log.debug("REST request to get Debiteur : {}", id);
        Optional<Debiteur> debiteur = debiteurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(debiteur);
    }

    /**
     * {@code DELETE  /debiteurs/:id} : delete the "id" debiteur.
     *
     * @param id the id of the debiteur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebiteur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Debiteur : {}", id);
        debiteurRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
