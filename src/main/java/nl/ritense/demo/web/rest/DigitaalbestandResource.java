package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Digitaalbestand;
import nl.ritense.demo.repository.DigitaalbestandRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Digitaalbestand}.
 */
@RestController
@RequestMapping("/api/digitaalbestands")
@Transactional
public class DigitaalbestandResource {

    private final Logger log = LoggerFactory.getLogger(DigitaalbestandResource.class);

    private static final String ENTITY_NAME = "digitaalbestand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DigitaalbestandRepository digitaalbestandRepository;

    public DigitaalbestandResource(DigitaalbestandRepository digitaalbestandRepository) {
        this.digitaalbestandRepository = digitaalbestandRepository;
    }

    /**
     * {@code POST  /digitaalbestands} : Create a new digitaalbestand.
     *
     * @param digitaalbestand the digitaalbestand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new digitaalbestand, or with status {@code 400 (Bad Request)} if the digitaalbestand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Digitaalbestand> createDigitaalbestand(@Valid @RequestBody Digitaalbestand digitaalbestand)
        throws URISyntaxException {
        log.debug("REST request to save Digitaalbestand : {}", digitaalbestand);
        if (digitaalbestand.getId() != null) {
            throw new BadRequestAlertException("A new digitaalbestand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        digitaalbestand = digitaalbestandRepository.save(digitaalbestand);
        return ResponseEntity.created(new URI("/api/digitaalbestands/" + digitaalbestand.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, digitaalbestand.getId().toString()))
            .body(digitaalbestand);
    }

    /**
     * {@code PUT  /digitaalbestands/:id} : Updates an existing digitaalbestand.
     *
     * @param id the id of the digitaalbestand to save.
     * @param digitaalbestand the digitaalbestand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated digitaalbestand,
     * or with status {@code 400 (Bad Request)} if the digitaalbestand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the digitaalbestand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Digitaalbestand> updateDigitaalbestand(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Digitaalbestand digitaalbestand
    ) throws URISyntaxException {
        log.debug("REST request to update Digitaalbestand : {}, {}", id, digitaalbestand);
        if (digitaalbestand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, digitaalbestand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!digitaalbestandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        digitaalbestand = digitaalbestandRepository.save(digitaalbestand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, digitaalbestand.getId().toString()))
            .body(digitaalbestand);
    }

    /**
     * {@code PATCH  /digitaalbestands/:id} : Partial updates given fields of an existing digitaalbestand, field will ignore if it is null
     *
     * @param id the id of the digitaalbestand to save.
     * @param digitaalbestand the digitaalbestand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated digitaalbestand,
     * or with status {@code 400 (Bad Request)} if the digitaalbestand is not valid,
     * or with status {@code 404 (Not Found)} if the digitaalbestand is not found,
     * or with status {@code 500 (Internal Server Error)} if the digitaalbestand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Digitaalbestand> partialUpdateDigitaalbestand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Digitaalbestand digitaalbestand
    ) throws URISyntaxException {
        log.debug("REST request to partial update Digitaalbestand partially : {}, {}", id, digitaalbestand);
        if (digitaalbestand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, digitaalbestand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!digitaalbestandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Digitaalbestand> result = digitaalbestandRepository
            .findById(digitaalbestand.getId())
            .map(existingDigitaalbestand -> {
                if (digitaalbestand.getBlob() != null) {
                    existingDigitaalbestand.setBlob(digitaalbestand.getBlob());
                }
                if (digitaalbestand.getMimetype() != null) {
                    existingDigitaalbestand.setMimetype(digitaalbestand.getMimetype());
                }
                if (digitaalbestand.getNaam() != null) {
                    existingDigitaalbestand.setNaam(digitaalbestand.getNaam());
                }
                if (digitaalbestand.getOmschrijving() != null) {
                    existingDigitaalbestand.setOmschrijving(digitaalbestand.getOmschrijving());
                }

                return existingDigitaalbestand;
            })
            .map(digitaalbestandRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, digitaalbestand.getId().toString())
        );
    }

    /**
     * {@code GET  /digitaalbestands} : get all the digitaalbestands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of digitaalbestands in body.
     */
    @GetMapping("")
    public List<Digitaalbestand> getAllDigitaalbestands() {
        log.debug("REST request to get all Digitaalbestands");
        return digitaalbestandRepository.findAll();
    }

    /**
     * {@code GET  /digitaalbestands/:id} : get the "id" digitaalbestand.
     *
     * @param id the id of the digitaalbestand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the digitaalbestand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Digitaalbestand> getDigitaalbestand(@PathVariable("id") Long id) {
        log.debug("REST request to get Digitaalbestand : {}", id);
        Optional<Digitaalbestand> digitaalbestand = digitaalbestandRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(digitaalbestand);
    }

    /**
     * {@code DELETE  /digitaalbestands/:id} : delete the "id" digitaalbestand.
     *
     * @param id the id of the digitaalbestand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitaalbestand(@PathVariable("id") Long id) {
        log.debug("REST request to delete Digitaalbestand : {}", id);
        digitaalbestandRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
