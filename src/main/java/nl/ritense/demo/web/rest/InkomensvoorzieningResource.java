package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Inkomensvoorziening;
import nl.ritense.demo.repository.InkomensvoorzieningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inkomensvoorziening}.
 */
@RestController
@RequestMapping("/api/inkomensvoorzienings")
@Transactional
public class InkomensvoorzieningResource {

    private final Logger log = LoggerFactory.getLogger(InkomensvoorzieningResource.class);

    private static final String ENTITY_NAME = "inkomensvoorziening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InkomensvoorzieningRepository inkomensvoorzieningRepository;

    public InkomensvoorzieningResource(InkomensvoorzieningRepository inkomensvoorzieningRepository) {
        this.inkomensvoorzieningRepository = inkomensvoorzieningRepository;
    }

    /**
     * {@code POST  /inkomensvoorzienings} : Create a new inkomensvoorziening.
     *
     * @param inkomensvoorziening the inkomensvoorziening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inkomensvoorziening, or with status {@code 400 (Bad Request)} if the inkomensvoorziening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inkomensvoorziening> createInkomensvoorziening(@Valid @RequestBody Inkomensvoorziening inkomensvoorziening)
        throws URISyntaxException {
        log.debug("REST request to save Inkomensvoorziening : {}", inkomensvoorziening);
        if (inkomensvoorziening.getId() != null) {
            throw new BadRequestAlertException("A new inkomensvoorziening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inkomensvoorziening = inkomensvoorzieningRepository.save(inkomensvoorziening);
        return ResponseEntity.created(new URI("/api/inkomensvoorzienings/" + inkomensvoorziening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inkomensvoorziening.getId().toString()))
            .body(inkomensvoorziening);
    }

    /**
     * {@code PUT  /inkomensvoorzienings/:id} : Updates an existing inkomensvoorziening.
     *
     * @param id the id of the inkomensvoorziening to save.
     * @param inkomensvoorziening the inkomensvoorziening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inkomensvoorziening,
     * or with status {@code 400 (Bad Request)} if the inkomensvoorziening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inkomensvoorziening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inkomensvoorziening> updateInkomensvoorziening(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Inkomensvoorziening inkomensvoorziening
    ) throws URISyntaxException {
        log.debug("REST request to update Inkomensvoorziening : {}, {}", id, inkomensvoorziening);
        if (inkomensvoorziening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inkomensvoorziening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inkomensvoorzieningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inkomensvoorziening = inkomensvoorzieningRepository.save(inkomensvoorziening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inkomensvoorziening.getId().toString()))
            .body(inkomensvoorziening);
    }

    /**
     * {@code PATCH  /inkomensvoorzienings/:id} : Partial updates given fields of an existing inkomensvoorziening, field will ignore if it is null
     *
     * @param id the id of the inkomensvoorziening to save.
     * @param inkomensvoorziening the inkomensvoorziening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inkomensvoorziening,
     * or with status {@code 400 (Bad Request)} if the inkomensvoorziening is not valid,
     * or with status {@code 404 (Not Found)} if the inkomensvoorziening is not found,
     * or with status {@code 500 (Internal Server Error)} if the inkomensvoorziening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inkomensvoorziening> partialUpdateInkomensvoorziening(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Inkomensvoorziening inkomensvoorziening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inkomensvoorziening partially : {}, {}", id, inkomensvoorziening);
        if (inkomensvoorziening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inkomensvoorziening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inkomensvoorzieningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inkomensvoorziening> result = inkomensvoorzieningRepository
            .findById(inkomensvoorziening.getId())
            .map(existingInkomensvoorziening -> {
                if (inkomensvoorziening.getBedrag() != null) {
                    existingInkomensvoorziening.setBedrag(inkomensvoorziening.getBedrag());
                }
                if (inkomensvoorziening.getDatumeinde() != null) {
                    existingInkomensvoorziening.setDatumeinde(inkomensvoorziening.getDatumeinde());
                }
                if (inkomensvoorziening.getDatumstart() != null) {
                    existingInkomensvoorziening.setDatumstart(inkomensvoorziening.getDatumstart());
                }
                if (inkomensvoorziening.getDatumtoekenning() != null) {
                    existingInkomensvoorziening.setDatumtoekenning(inkomensvoorziening.getDatumtoekenning());
                }
                if (inkomensvoorziening.getEenmalig() != null) {
                    existingInkomensvoorziening.setEenmalig(inkomensvoorziening.getEenmalig());
                }
                if (inkomensvoorziening.getGroep() != null) {
                    existingInkomensvoorziening.setGroep(inkomensvoorziening.getGroep());
                }

                return existingInkomensvoorziening;
            })
            .map(inkomensvoorzieningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inkomensvoorziening.getId().toString())
        );
    }

    /**
     * {@code GET  /inkomensvoorzienings} : get all the inkomensvoorzienings.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inkomensvoorzienings in body.
     */
    @GetMapping("")
    public List<Inkomensvoorziening> getAllInkomensvoorzienings(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftvoorzieningclient-is-null".equals(filter)) {
            log.debug("REST request to get all Inkomensvoorzienings where heeftvoorzieningClient is null");
            return StreamSupport.stream(inkomensvoorzieningRepository.findAll().spliterator(), false)
                .filter(inkomensvoorziening -> inkomensvoorziening.getHeeftvoorzieningClient() == null)
                .toList();
        }
        log.debug("REST request to get all Inkomensvoorzienings");
        return inkomensvoorzieningRepository.findAll();
    }

    /**
     * {@code GET  /inkomensvoorzienings/:id} : get the "id" inkomensvoorziening.
     *
     * @param id the id of the inkomensvoorziening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inkomensvoorziening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inkomensvoorziening> getInkomensvoorziening(@PathVariable("id") Long id) {
        log.debug("REST request to get Inkomensvoorziening : {}", id);
        Optional<Inkomensvoorziening> inkomensvoorziening = inkomensvoorzieningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inkomensvoorziening);
    }

    /**
     * {@code DELETE  /inkomensvoorzienings/:id} : delete the "id" inkomensvoorziening.
     *
     * @param id the id of the inkomensvoorziening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInkomensvoorziening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inkomensvoorziening : {}", id);
        inkomensvoorzieningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
