package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Tegenprestatie;
import nl.ritense.demo.repository.TegenprestatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Tegenprestatie}.
 */
@RestController
@RequestMapping("/api/tegenprestaties")
@Transactional
public class TegenprestatieResource {

    private final Logger log = LoggerFactory.getLogger(TegenprestatieResource.class);

    private static final String ENTITY_NAME = "tegenprestatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TegenprestatieRepository tegenprestatieRepository;

    public TegenprestatieResource(TegenprestatieRepository tegenprestatieRepository) {
        this.tegenprestatieRepository = tegenprestatieRepository;
    }

    /**
     * {@code POST  /tegenprestaties} : Create a new tegenprestatie.
     *
     * @param tegenprestatie the tegenprestatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tegenprestatie, or with status {@code 400 (Bad Request)} if the tegenprestatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tegenprestatie> createTegenprestatie(@RequestBody Tegenprestatie tegenprestatie) throws URISyntaxException {
        log.debug("REST request to save Tegenprestatie : {}", tegenprestatie);
        if (tegenprestatie.getId() != null) {
            throw new BadRequestAlertException("A new tegenprestatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tegenprestatie = tegenprestatieRepository.save(tegenprestatie);
        return ResponseEntity.created(new URI("/api/tegenprestaties/" + tegenprestatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tegenprestatie.getId().toString()))
            .body(tegenprestatie);
    }

    /**
     * {@code PUT  /tegenprestaties/:id} : Updates an existing tegenprestatie.
     *
     * @param id the id of the tegenprestatie to save.
     * @param tegenprestatie the tegenprestatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tegenprestatie,
     * or with status {@code 400 (Bad Request)} if the tegenprestatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tegenprestatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tegenprestatie> updateTegenprestatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tegenprestatie tegenprestatie
    ) throws URISyntaxException {
        log.debug("REST request to update Tegenprestatie : {}, {}", id, tegenprestatie);
        if (tegenprestatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tegenprestatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tegenprestatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tegenprestatie = tegenprestatieRepository.save(tegenprestatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tegenprestatie.getId().toString()))
            .body(tegenprestatie);
    }

    /**
     * {@code PATCH  /tegenprestaties/:id} : Partial updates given fields of an existing tegenprestatie, field will ignore if it is null
     *
     * @param id the id of the tegenprestatie to save.
     * @param tegenprestatie the tegenprestatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tegenprestatie,
     * or with status {@code 400 (Bad Request)} if the tegenprestatie is not valid,
     * or with status {@code 404 (Not Found)} if the tegenprestatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the tegenprestatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tegenprestatie> partialUpdateTegenprestatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tegenprestatie tegenprestatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tegenprestatie partially : {}, {}", id, tegenprestatie);
        if (tegenprestatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tegenprestatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tegenprestatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tegenprestatie> result = tegenprestatieRepository
            .findById(tegenprestatie.getId())
            .map(existingTegenprestatie -> {
                if (tegenprestatie.getDatumeinde() != null) {
                    existingTegenprestatie.setDatumeinde(tegenprestatie.getDatumeinde());
                }
                if (tegenprestatie.getDatumstart() != null) {
                    existingTegenprestatie.setDatumstart(tegenprestatie.getDatumstart());
                }

                return existingTegenprestatie;
            })
            .map(tegenprestatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tegenprestatie.getId().toString())
        );
    }

    /**
     * {@code GET  /tegenprestaties} : get all the tegenprestaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tegenprestaties in body.
     */
    @GetMapping("")
    public List<Tegenprestatie> getAllTegenprestaties() {
        log.debug("REST request to get all Tegenprestaties");
        return tegenprestatieRepository.findAll();
    }

    /**
     * {@code GET  /tegenprestaties/:id} : get the "id" tegenprestatie.
     *
     * @param id the id of the tegenprestatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tegenprestatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tegenprestatie> getTegenprestatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Tegenprestatie : {}", id);
        Optional<Tegenprestatie> tegenprestatie = tegenprestatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tegenprestatie);
    }

    /**
     * {@code DELETE  /tegenprestaties/:id} : delete the "id" tegenprestatie.
     *
     * @param id the id of the tegenprestatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTegenprestatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tegenprestatie : {}", id);
        tegenprestatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
