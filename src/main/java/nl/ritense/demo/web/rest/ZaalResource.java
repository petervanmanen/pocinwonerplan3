package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Zaal;
import nl.ritense.demo.repository.ZaalRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Zaal}.
 */
@RestController
@RequestMapping("/api/zaals")
@Transactional
public class ZaalResource {

    private final Logger log = LoggerFactory.getLogger(ZaalResource.class);

    private static final String ENTITY_NAME = "zaal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZaalRepository zaalRepository;

    public ZaalResource(ZaalRepository zaalRepository) {
        this.zaalRepository = zaalRepository;
    }

    /**
     * {@code POST  /zaals} : Create a new zaal.
     *
     * @param zaal the zaal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zaal, or with status {@code 400 (Bad Request)} if the zaal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Zaal> createZaal(@RequestBody Zaal zaal) throws URISyntaxException {
        log.debug("REST request to save Zaal : {}", zaal);
        if (zaal.getId() != null) {
            throw new BadRequestAlertException("A new zaal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zaal = zaalRepository.save(zaal);
        return ResponseEntity.created(new URI("/api/zaals/" + zaal.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, zaal.getId().toString()))
            .body(zaal);
    }

    /**
     * {@code PUT  /zaals/:id} : Updates an existing zaal.
     *
     * @param id the id of the zaal to save.
     * @param zaal the zaal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zaal,
     * or with status {@code 400 (Bad Request)} if the zaal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zaal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Zaal> updateZaal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Zaal zaal)
        throws URISyntaxException {
        log.debug("REST request to update Zaal : {}, {}", id, zaal);
        if (zaal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zaal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zaalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        zaal = zaalRepository.save(zaal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zaal.getId().toString()))
            .body(zaal);
    }

    /**
     * {@code PATCH  /zaals/:id} : Partial updates given fields of an existing zaal, field will ignore if it is null
     *
     * @param id the id of the zaal to save.
     * @param zaal the zaal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zaal,
     * or with status {@code 400 (Bad Request)} if the zaal is not valid,
     * or with status {@code 404 (Not Found)} if the zaal is not found,
     * or with status {@code 500 (Internal Server Error)} if the zaal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Zaal> partialUpdateZaal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Zaal zaal)
        throws URISyntaxException {
        log.debug("REST request to partial update Zaal partially : {}, {}", id, zaal);
        if (zaal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zaal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zaalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Zaal> result = zaalRepository
            .findById(zaal.getId())
            .map(existingZaal -> {
                if (zaal.getCapaciteit() != null) {
                    existingZaal.setCapaciteit(zaal.getCapaciteit());
                }
                if (zaal.getNaam() != null) {
                    existingZaal.setNaam(zaal.getNaam());
                }
                if (zaal.getNummer() != null) {
                    existingZaal.setNummer(zaal.getNummer());
                }
                if (zaal.getOmschrijving() != null) {
                    existingZaal.setOmschrijving(zaal.getOmschrijving());
                }

                return existingZaal;
            })
            .map(zaalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zaal.getId().toString())
        );
    }

    /**
     * {@code GET  /zaals} : get all the zaals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zaals in body.
     */
    @GetMapping("")
    public List<Zaal> getAllZaals() {
        log.debug("REST request to get all Zaals");
        return zaalRepository.findAll();
    }

    /**
     * {@code GET  /zaals/:id} : get the "id" zaal.
     *
     * @param id the id of the zaal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zaal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Zaal> getZaal(@PathVariable("id") Long id) {
        log.debug("REST request to get Zaal : {}", id);
        Optional<Zaal> zaal = zaalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zaal);
    }

    /**
     * {@code DELETE  /zaals/:id} : delete the "id" zaal.
     *
     * @param id the id of the zaal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZaal(@PathVariable("id") Long id) {
        log.debug("REST request to delete Zaal : {}", id);
        zaalRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
