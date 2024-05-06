package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Classificatie;
import nl.ritense.demo.repository.ClassificatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classificatie}.
 */
@RestController
@RequestMapping("/api/classificaties")
@Transactional
public class ClassificatieResource {

    private final Logger log = LoggerFactory.getLogger(ClassificatieResource.class);

    private static final String ENTITY_NAME = "classificatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassificatieRepository classificatieRepository;

    public ClassificatieResource(ClassificatieRepository classificatieRepository) {
        this.classificatieRepository = classificatieRepository;
    }

    /**
     * {@code POST  /classificaties} : Create a new classificatie.
     *
     * @param classificatie the classificatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classificatie, or with status {@code 400 (Bad Request)} if the classificatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classificatie> createClassificatie(@RequestBody Classificatie classificatie) throws URISyntaxException {
        log.debug("REST request to save Classificatie : {}", classificatie);
        if (classificatie.getId() != null) {
            throw new BadRequestAlertException("A new classificatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classificatie = classificatieRepository.save(classificatie);
        return ResponseEntity.created(new URI("/api/classificaties/" + classificatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classificatie.getId()))
            .body(classificatie);
    }

    /**
     * {@code PUT  /classificaties/:id} : Updates an existing classificatie.
     *
     * @param id the id of the classificatie to save.
     * @param classificatie the classificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classificatie,
     * or with status {@code 400 (Bad Request)} if the classificatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Classificatie> updateClassificatie(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Classificatie classificatie
    ) throws URISyntaxException {
        log.debug("REST request to update Classificatie : {}, {}", id, classificatie);
        if (classificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        classificatie = classificatieRepository.save(classificatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classificatie.getId()))
            .body(classificatie);
    }

    /**
     * {@code PATCH  /classificaties/:id} : Partial updates given fields of an existing classificatie, field will ignore if it is null
     *
     * @param id the id of the classificatie to save.
     * @param classificatie the classificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classificatie,
     * or with status {@code 400 (Bad Request)} if the classificatie is not valid,
     * or with status {@code 404 (Not Found)} if the classificatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the classificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Classificatie> partialUpdateClassificatie(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Classificatie classificatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Classificatie partially : {}, {}", id, classificatie);
        if (classificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Classificatie> result = classificatieRepository
            .findById(classificatie.getId())
            .map(existingClassificatie -> {
                if (classificatie.getBevatpersoonsgegevens() != null) {
                    existingClassificatie.setBevatpersoonsgegevens(classificatie.getBevatpersoonsgegevens());
                }
                if (classificatie.getGerelateerdpersoonsgegevens() != null) {
                    existingClassificatie.setGerelateerdpersoonsgegevens(classificatie.getGerelateerdpersoonsgegevens());
                }

                return existingClassificatie;
            })
            .map(classificatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classificatie.getId())
        );
    }

    /**
     * {@code GET  /classificaties} : get all the classificaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classificaties in body.
     */
    @GetMapping("")
    public List<Classificatie> getAllClassificaties() {
        log.debug("REST request to get all Classificaties");
        return classificatieRepository.findAll();
    }

    /**
     * {@code GET  /classificaties/:id} : get the "id" classificatie.
     *
     * @param id the id of the classificatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classificatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classificatie> getClassificatie(@PathVariable("id") String id) {
        log.debug("REST request to get Classificatie : {}", id);
        Optional<Classificatie> classificatie = classificatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classificatie);
    }

    /**
     * {@code DELETE  /classificaties/:id} : delete the "id" classificatie.
     *
     * @param id the id of the classificatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassificatie(@PathVariable("id") String id) {
        log.debug("REST request to delete Classificatie : {}", id);
        classificatieRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
