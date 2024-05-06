package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Idealisatie;
import nl.ritense.demo.repository.IdealisatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Idealisatie}.
 */
@RestController
@RequestMapping("/api/idealisaties")
@Transactional
public class IdealisatieResource {

    private final Logger log = LoggerFactory.getLogger(IdealisatieResource.class);

    private static final String ENTITY_NAME = "idealisatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdealisatieRepository idealisatieRepository;

    public IdealisatieResource(IdealisatieRepository idealisatieRepository) {
        this.idealisatieRepository = idealisatieRepository;
    }

    /**
     * {@code POST  /idealisaties} : Create a new idealisatie.
     *
     * @param idealisatie the idealisatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idealisatie, or with status {@code 400 (Bad Request)} if the idealisatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Idealisatie> createIdealisatie(@RequestBody Idealisatie idealisatie) throws URISyntaxException {
        log.debug("REST request to save Idealisatie : {}", idealisatie);
        if (idealisatie.getId() != null) {
            throw new BadRequestAlertException("A new idealisatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        idealisatie = idealisatieRepository.save(idealisatie);
        return ResponseEntity.created(new URI("/api/idealisaties/" + idealisatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, idealisatie.getId().toString()))
            .body(idealisatie);
    }

    /**
     * {@code PUT  /idealisaties/:id} : Updates an existing idealisatie.
     *
     * @param id the id of the idealisatie to save.
     * @param idealisatie the idealisatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idealisatie,
     * or with status {@code 400 (Bad Request)} if the idealisatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idealisatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Idealisatie> updateIdealisatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Idealisatie idealisatie
    ) throws URISyntaxException {
        log.debug("REST request to update Idealisatie : {}, {}", id, idealisatie);
        if (idealisatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, idealisatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!idealisatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        idealisatie = idealisatieRepository.save(idealisatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, idealisatie.getId().toString()))
            .body(idealisatie);
    }

    /**
     * {@code PATCH  /idealisaties/:id} : Partial updates given fields of an existing idealisatie, field will ignore if it is null
     *
     * @param id the id of the idealisatie to save.
     * @param idealisatie the idealisatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idealisatie,
     * or with status {@code 400 (Bad Request)} if the idealisatie is not valid,
     * or with status {@code 404 (Not Found)} if the idealisatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the idealisatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Idealisatie> partialUpdateIdealisatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Idealisatie idealisatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Idealisatie partially : {}, {}", id, idealisatie);
        if (idealisatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, idealisatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!idealisatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Idealisatie> result = idealisatieRepository
            .findById(idealisatie.getId())
            .map(existingIdealisatie -> {
                if (idealisatie.getNaam() != null) {
                    existingIdealisatie.setNaam(idealisatie.getNaam());
                }
                if (idealisatie.getOmschrijving() != null) {
                    existingIdealisatie.setOmschrijving(idealisatie.getOmschrijving());
                }

                return existingIdealisatie;
            })
            .map(idealisatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, idealisatie.getId().toString())
        );
    }

    /**
     * {@code GET  /idealisaties} : get all the idealisaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of idealisaties in body.
     */
    @GetMapping("")
    public List<Idealisatie> getAllIdealisaties() {
        log.debug("REST request to get all Idealisaties");
        return idealisatieRepository.findAll();
    }

    /**
     * {@code GET  /idealisaties/:id} : get the "id" idealisatie.
     *
     * @param id the id of the idealisatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idealisatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Idealisatie> getIdealisatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Idealisatie : {}", id);
        Optional<Idealisatie> idealisatie = idealisatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(idealisatie);
    }

    /**
     * {@code DELETE  /idealisaties/:id} : delete the "id" idealisatie.
     *
     * @param id the id of the idealisatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdealisatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Idealisatie : {}", id);
        idealisatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
