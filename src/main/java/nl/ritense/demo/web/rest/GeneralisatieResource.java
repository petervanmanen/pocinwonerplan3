package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Generalisatie;
import nl.ritense.demo.repository.GeneralisatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Generalisatie}.
 */
@RestController
@RequestMapping("/api/generalisaties")
@Transactional
public class GeneralisatieResource {

    private final Logger log = LoggerFactory.getLogger(GeneralisatieResource.class);

    private static final String ENTITY_NAME = "generalisatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeneralisatieRepository generalisatieRepository;

    public GeneralisatieResource(GeneralisatieRepository generalisatieRepository) {
        this.generalisatieRepository = generalisatieRepository;
    }

    /**
     * {@code POST  /generalisaties} : Create a new generalisatie.
     *
     * @param generalisatie the generalisatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new generalisatie, or with status {@code 400 (Bad Request)} if the generalisatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Generalisatie> createGeneralisatie(@RequestBody Generalisatie generalisatie) throws URISyntaxException {
        log.debug("REST request to save Generalisatie : {}", generalisatie);
        if (generalisatie.getId() != null) {
            throw new BadRequestAlertException("A new generalisatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        generalisatie = generalisatieRepository.save(generalisatie);
        return ResponseEntity.created(new URI("/api/generalisaties/" + generalisatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, generalisatie.getId()))
            .body(generalisatie);
    }

    /**
     * {@code PUT  /generalisaties/:id} : Updates an existing generalisatie.
     *
     * @param id the id of the generalisatie to save.
     * @param generalisatie the generalisatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generalisatie,
     * or with status {@code 400 (Bad Request)} if the generalisatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the generalisatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Generalisatie> updateGeneralisatie(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Generalisatie generalisatie
    ) throws URISyntaxException {
        log.debug("REST request to update Generalisatie : {}, {}", id, generalisatie);
        if (generalisatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generalisatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generalisatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        generalisatie = generalisatieRepository.save(generalisatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generalisatie.getId()))
            .body(generalisatie);
    }

    /**
     * {@code PATCH  /generalisaties/:id} : Partial updates given fields of an existing generalisatie, field will ignore if it is null
     *
     * @param id the id of the generalisatie to save.
     * @param generalisatie the generalisatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generalisatie,
     * or with status {@code 400 (Bad Request)} if the generalisatie is not valid,
     * or with status {@code 404 (Not Found)} if the generalisatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the generalisatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Generalisatie> partialUpdateGeneralisatie(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Generalisatie generalisatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Generalisatie partially : {}, {}", id, generalisatie);
        if (generalisatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generalisatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generalisatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Generalisatie> result = generalisatieRepository
            .findById(generalisatie.getId())
            .map(existingGeneralisatie -> {
                if (generalisatie.getDatumopname() != null) {
                    existingGeneralisatie.setDatumopname(generalisatie.getDatumopname());
                }
                if (generalisatie.getDefinitie() != null) {
                    existingGeneralisatie.setDefinitie(generalisatie.getDefinitie());
                }
                if (generalisatie.getEaguid() != null) {
                    existingGeneralisatie.setEaguid(generalisatie.getEaguid());
                }
                if (generalisatie.getHerkomst() != null) {
                    existingGeneralisatie.setHerkomst(generalisatie.getHerkomst());
                }
                if (generalisatie.getHerkomstdefinitie() != null) {
                    existingGeneralisatie.setHerkomstdefinitie(generalisatie.getHerkomstdefinitie());
                }
                if (generalisatie.getIndicatiematerielehistorie() != null) {
                    existingGeneralisatie.setIndicatiematerielehistorie(generalisatie.getIndicatiematerielehistorie());
                }
                if (generalisatie.getNaam() != null) {
                    existingGeneralisatie.setNaam(generalisatie.getNaam());
                }
                if (generalisatie.getToelichting() != null) {
                    existingGeneralisatie.setToelichting(generalisatie.getToelichting());
                }

                return existingGeneralisatie;
            })
            .map(generalisatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generalisatie.getId())
        );
    }

    /**
     * {@code GET  /generalisaties} : get all the generalisaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of generalisaties in body.
     */
    @GetMapping("")
    public List<Generalisatie> getAllGeneralisaties() {
        log.debug("REST request to get all Generalisaties");
        return generalisatieRepository.findAll();
    }

    /**
     * {@code GET  /generalisaties/:id} : get the "id" generalisatie.
     *
     * @param id the id of the generalisatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the generalisatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Generalisatie> getGeneralisatie(@PathVariable("id") String id) {
        log.debug("REST request to get Generalisatie : {}", id);
        Optional<Generalisatie> generalisatie = generalisatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(generalisatie);
    }

    /**
     * {@code DELETE  /generalisaties/:id} : delete the "id" generalisatie.
     *
     * @param id the id of the generalisatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeneralisatie(@PathVariable("id") String id) {
        log.debug("REST request to delete Generalisatie : {}", id);
        generalisatieRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
