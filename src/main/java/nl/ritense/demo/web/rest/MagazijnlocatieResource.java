package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Magazijnlocatie;
import nl.ritense.demo.repository.MagazijnlocatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Magazijnlocatie}.
 */
@RestController
@RequestMapping("/api/magazijnlocaties")
@Transactional
public class MagazijnlocatieResource {

    private final Logger log = LoggerFactory.getLogger(MagazijnlocatieResource.class);

    private static final String ENTITY_NAME = "magazijnlocatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MagazijnlocatieRepository magazijnlocatieRepository;

    public MagazijnlocatieResource(MagazijnlocatieRepository magazijnlocatieRepository) {
        this.magazijnlocatieRepository = magazijnlocatieRepository;
    }

    /**
     * {@code POST  /magazijnlocaties} : Create a new magazijnlocatie.
     *
     * @param magazijnlocatie the magazijnlocatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new magazijnlocatie, or with status {@code 400 (Bad Request)} if the magazijnlocatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Magazijnlocatie> createMagazijnlocatie(@Valid @RequestBody Magazijnlocatie magazijnlocatie)
        throws URISyntaxException {
        log.debug("REST request to save Magazijnlocatie : {}", magazijnlocatie);
        if (magazijnlocatie.getId() != null) {
            throw new BadRequestAlertException("A new magazijnlocatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        magazijnlocatie = magazijnlocatieRepository.save(magazijnlocatie);
        return ResponseEntity.created(new URI("/api/magazijnlocaties/" + magazijnlocatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, magazijnlocatie.getId().toString()))
            .body(magazijnlocatie);
    }

    /**
     * {@code PUT  /magazijnlocaties/:id} : Updates an existing magazijnlocatie.
     *
     * @param id the id of the magazijnlocatie to save.
     * @param magazijnlocatie the magazijnlocatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magazijnlocatie,
     * or with status {@code 400 (Bad Request)} if the magazijnlocatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the magazijnlocatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Magazijnlocatie> updateMagazijnlocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Magazijnlocatie magazijnlocatie
    ) throws URISyntaxException {
        log.debug("REST request to update Magazijnlocatie : {}, {}", id, magazijnlocatie);
        if (magazijnlocatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magazijnlocatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magazijnlocatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        magazijnlocatie = magazijnlocatieRepository.save(magazijnlocatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magazijnlocatie.getId().toString()))
            .body(magazijnlocatie);
    }

    /**
     * {@code PATCH  /magazijnlocaties/:id} : Partial updates given fields of an existing magazijnlocatie, field will ignore if it is null
     *
     * @param id the id of the magazijnlocatie to save.
     * @param magazijnlocatie the magazijnlocatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magazijnlocatie,
     * or with status {@code 400 (Bad Request)} if the magazijnlocatie is not valid,
     * or with status {@code 404 (Not Found)} if the magazijnlocatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the magazijnlocatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Magazijnlocatie> partialUpdateMagazijnlocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Magazijnlocatie magazijnlocatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Magazijnlocatie partially : {}, {}", id, magazijnlocatie);
        if (magazijnlocatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magazijnlocatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magazijnlocatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Magazijnlocatie> result = magazijnlocatieRepository
            .findById(magazijnlocatie.getId())
            .map(existingMagazijnlocatie -> {
                if (magazijnlocatie.getKey() != null) {
                    existingMagazijnlocatie.setKey(magazijnlocatie.getKey());
                }
                if (magazijnlocatie.getVaknummer() != null) {
                    existingMagazijnlocatie.setVaknummer(magazijnlocatie.getVaknummer());
                }
                if (magazijnlocatie.getVolgletter() != null) {
                    existingMagazijnlocatie.setVolgletter(magazijnlocatie.getVolgletter());
                }

                return existingMagazijnlocatie;
            })
            .map(magazijnlocatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magazijnlocatie.getId().toString())
        );
    }

    /**
     * {@code GET  /magazijnlocaties} : get all the magazijnlocaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of magazijnlocaties in body.
     */
    @GetMapping("")
    public List<Magazijnlocatie> getAllMagazijnlocaties() {
        log.debug("REST request to get all Magazijnlocaties");
        return magazijnlocatieRepository.findAll();
    }

    /**
     * {@code GET  /magazijnlocaties/:id} : get the "id" magazijnlocatie.
     *
     * @param id the id of the magazijnlocatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the magazijnlocatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Magazijnlocatie> getMagazijnlocatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Magazijnlocatie : {}", id);
        Optional<Magazijnlocatie> magazijnlocatie = magazijnlocatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(magazijnlocatie);
    }

    /**
     * {@code DELETE  /magazijnlocaties/:id} : delete the "id" magazijnlocatie.
     *
     * @param id the id of the magazijnlocatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMagazijnlocatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Magazijnlocatie : {}", id);
        magazijnlocatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
