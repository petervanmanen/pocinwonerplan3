package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Zekerheidsrecht;
import nl.ritense.demo.repository.ZekerheidsrechtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Zekerheidsrecht}.
 */
@RestController
@RequestMapping("/api/zekerheidsrechts")
@Transactional
public class ZekerheidsrechtResource {

    private final Logger log = LoggerFactory.getLogger(ZekerheidsrechtResource.class);

    private static final String ENTITY_NAME = "zekerheidsrecht";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZekerheidsrechtRepository zekerheidsrechtRepository;

    public ZekerheidsrechtResource(ZekerheidsrechtRepository zekerheidsrechtRepository) {
        this.zekerheidsrechtRepository = zekerheidsrechtRepository;
    }

    /**
     * {@code POST  /zekerheidsrechts} : Create a new zekerheidsrecht.
     *
     * @param zekerheidsrecht the zekerheidsrecht to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zekerheidsrecht, or with status {@code 400 (Bad Request)} if the zekerheidsrecht has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Zekerheidsrecht> createZekerheidsrecht(@RequestBody Zekerheidsrecht zekerheidsrecht) throws URISyntaxException {
        log.debug("REST request to save Zekerheidsrecht : {}", zekerheidsrecht);
        if (zekerheidsrecht.getId() != null) {
            throw new BadRequestAlertException("A new zekerheidsrecht cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zekerheidsrecht = zekerheidsrechtRepository.save(zekerheidsrecht);
        return ResponseEntity.created(new URI("/api/zekerheidsrechts/" + zekerheidsrecht.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, zekerheidsrecht.getId().toString()))
            .body(zekerheidsrecht);
    }

    /**
     * {@code PUT  /zekerheidsrechts/:id} : Updates an existing zekerheidsrecht.
     *
     * @param id the id of the zekerheidsrecht to save.
     * @param zekerheidsrecht the zekerheidsrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zekerheidsrecht,
     * or with status {@code 400 (Bad Request)} if the zekerheidsrecht is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zekerheidsrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Zekerheidsrecht> updateZekerheidsrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zekerheidsrecht zekerheidsrecht
    ) throws URISyntaxException {
        log.debug("REST request to update Zekerheidsrecht : {}, {}", id, zekerheidsrecht);
        if (zekerheidsrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zekerheidsrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zekerheidsrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        zekerheidsrecht = zekerheidsrechtRepository.save(zekerheidsrecht);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zekerheidsrecht.getId().toString()))
            .body(zekerheidsrecht);
    }

    /**
     * {@code PATCH  /zekerheidsrechts/:id} : Partial updates given fields of an existing zekerheidsrecht, field will ignore if it is null
     *
     * @param id the id of the zekerheidsrecht to save.
     * @param zekerheidsrecht the zekerheidsrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zekerheidsrecht,
     * or with status {@code 400 (Bad Request)} if the zekerheidsrecht is not valid,
     * or with status {@code 404 (Not Found)} if the zekerheidsrecht is not found,
     * or with status {@code 500 (Internal Server Error)} if the zekerheidsrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Zekerheidsrecht> partialUpdateZekerheidsrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zekerheidsrecht zekerheidsrecht
    ) throws URISyntaxException {
        log.debug("REST request to partial update Zekerheidsrecht partially : {}, {}", id, zekerheidsrecht);
        if (zekerheidsrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zekerheidsrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zekerheidsrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Zekerheidsrecht> result = zekerheidsrechtRepository
            .findById(zekerheidsrecht.getId())
            .map(existingZekerheidsrecht -> {
                if (zekerheidsrecht.getAandeelinbetrokkenrecht() != null) {
                    existingZekerheidsrecht.setAandeelinbetrokkenrecht(zekerheidsrecht.getAandeelinbetrokkenrecht());
                }
                if (zekerheidsrecht.getDatumeinderecht() != null) {
                    existingZekerheidsrecht.setDatumeinderecht(zekerheidsrecht.getDatumeinderecht());
                }
                if (zekerheidsrecht.getDatumingangrecht() != null) {
                    existingZekerheidsrecht.setDatumingangrecht(zekerheidsrecht.getDatumingangrecht());
                }
                if (zekerheidsrecht.getIdentificatiezekerheidsrecht() != null) {
                    existingZekerheidsrecht.setIdentificatiezekerheidsrecht(zekerheidsrecht.getIdentificatiezekerheidsrecht());
                }
                if (zekerheidsrecht.getOmschrijvingbetrokkenrecht() != null) {
                    existingZekerheidsrecht.setOmschrijvingbetrokkenrecht(zekerheidsrecht.getOmschrijvingbetrokkenrecht());
                }
                if (zekerheidsrecht.getTypezekerheidsrecht() != null) {
                    existingZekerheidsrecht.setTypezekerheidsrecht(zekerheidsrecht.getTypezekerheidsrecht());
                }

                return existingZekerheidsrecht;
            })
            .map(zekerheidsrechtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zekerheidsrecht.getId().toString())
        );
    }

    /**
     * {@code GET  /zekerheidsrechts} : get all the zekerheidsrechts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zekerheidsrechts in body.
     */
    @GetMapping("")
    public List<Zekerheidsrecht> getAllZekerheidsrechts() {
        log.debug("REST request to get all Zekerheidsrechts");
        return zekerheidsrechtRepository.findAll();
    }

    /**
     * {@code GET  /zekerheidsrechts/:id} : get the "id" zekerheidsrecht.
     *
     * @param id the id of the zekerheidsrecht to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zekerheidsrecht, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Zekerheidsrecht> getZekerheidsrecht(@PathVariable("id") Long id) {
        log.debug("REST request to get Zekerheidsrecht : {}", id);
        Optional<Zekerheidsrecht> zekerheidsrecht = zekerheidsrechtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zekerheidsrecht);
    }

    /**
     * {@code DELETE  /zekerheidsrechts/:id} : delete the "id" zekerheidsrecht.
     *
     * @param id the id of the zekerheidsrecht to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZekerheidsrecht(@PathVariable("id") Long id) {
        log.debug("REST request to delete Zekerheidsrecht : {}", id);
        zekerheidsrechtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
