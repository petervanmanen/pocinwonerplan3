package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Auteur;
import nl.ritense.demo.repository.AuteurRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Auteur}.
 */
@RestController
@RequestMapping("/api/auteurs")
@Transactional
public class AuteurResource {

    private final Logger log = LoggerFactory.getLogger(AuteurResource.class);

    private static final String ENTITY_NAME = "auteur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuteurRepository auteurRepository;

    public AuteurResource(AuteurRepository auteurRepository) {
        this.auteurRepository = auteurRepository;
    }

    /**
     * {@code POST  /auteurs} : Create a new auteur.
     *
     * @param auteur the auteur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auteur, or with status {@code 400 (Bad Request)} if the auteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Auteur> createAuteur(@RequestBody Auteur auteur) throws URISyntaxException {
        log.debug("REST request to save Auteur : {}", auteur);
        if (auteur.getId() != null) {
            throw new BadRequestAlertException("A new auteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        auteur = auteurRepository.save(auteur);
        return ResponseEntity.created(new URI("/api/auteurs/" + auteur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, auteur.getId().toString()))
            .body(auteur);
    }

    /**
     * {@code PUT  /auteurs/:id} : Updates an existing auteur.
     *
     * @param id the id of the auteur to save.
     * @param auteur the auteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auteur,
     * or with status {@code 400 (Bad Request)} if the auteur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Auteur> updateAuteur(@PathVariable(value = "id", required = false) final Long id, @RequestBody Auteur auteur)
        throws URISyntaxException {
        log.debug("REST request to update Auteur : {}, {}", id, auteur);
        if (auteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auteur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        auteur = auteurRepository.save(auteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, auteur.getId().toString()))
            .body(auteur);
    }

    /**
     * {@code PATCH  /auteurs/:id} : Partial updates given fields of an existing auteur, field will ignore if it is null
     *
     * @param id the id of the auteur to save.
     * @param auteur the auteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auteur,
     * or with status {@code 400 (Bad Request)} if the auteur is not valid,
     * or with status {@code 404 (Not Found)} if the auteur is not found,
     * or with status {@code 500 (Internal Server Error)} if the auteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Auteur> partialUpdateAuteur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Auteur auteur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Auteur partially : {}, {}", id, auteur);
        if (auteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auteur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Auteur> result = auteurRepository
            .findById(auteur.getId())
            .map(existingAuteur -> {
                if (auteur.getDatumgeboorte() != null) {
                    existingAuteur.setDatumgeboorte(auteur.getDatumgeboorte());
                }
                if (auteur.getDatumoverlijden() != null) {
                    existingAuteur.setDatumoverlijden(auteur.getDatumoverlijden());
                }

                return existingAuteur;
            })
            .map(auteurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, auteur.getId().toString())
        );
    }

    /**
     * {@code GET  /auteurs} : get all the auteurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auteurs in body.
     */
    @GetMapping("")
    public List<Auteur> getAllAuteurs() {
        log.debug("REST request to get all Auteurs");
        return auteurRepository.findAll();
    }

    /**
     * {@code GET  /auteurs/:id} : get the "id" auteur.
     *
     * @param id the id of the auteur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auteur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Auteur> getAuteur(@PathVariable("id") Long id) {
        log.debug("REST request to get Auteur : {}", id);
        Optional<Auteur> auteur = auteurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(auteur);
    }

    /**
     * {@code DELETE  /auteurs/:id} : delete the "id" auteur.
     *
     * @param id the id of the auteur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Auteur : {}", id);
        auteurRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
