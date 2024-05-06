package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Nationaliteit;
import nl.ritense.demo.repository.NationaliteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Nationaliteit}.
 */
@RestController
@RequestMapping("/api/nationaliteits")
@Transactional
public class NationaliteitResource {

    private final Logger log = LoggerFactory.getLogger(NationaliteitResource.class);

    private static final String ENTITY_NAME = "nationaliteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NationaliteitRepository nationaliteitRepository;

    public NationaliteitResource(NationaliteitRepository nationaliteitRepository) {
        this.nationaliteitRepository = nationaliteitRepository;
    }

    /**
     * {@code POST  /nationaliteits} : Create a new nationaliteit.
     *
     * @param nationaliteit the nationaliteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nationaliteit, or with status {@code 400 (Bad Request)} if the nationaliteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nationaliteit> createNationaliteit(@Valid @RequestBody Nationaliteit nationaliteit) throws URISyntaxException {
        log.debug("REST request to save Nationaliteit : {}", nationaliteit);
        if (nationaliteit.getId() != null) {
            throw new BadRequestAlertException("A new nationaliteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nationaliteit = nationaliteitRepository.save(nationaliteit);
        return ResponseEntity.created(new URI("/api/nationaliteits/" + nationaliteit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, nationaliteit.getId().toString()))
            .body(nationaliteit);
    }

    /**
     * {@code PUT  /nationaliteits/:id} : Updates an existing nationaliteit.
     *
     * @param id the id of the nationaliteit to save.
     * @param nationaliteit the nationaliteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationaliteit,
     * or with status {@code 400 (Bad Request)} if the nationaliteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nationaliteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nationaliteit> updateNationaliteit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Nationaliteit nationaliteit
    ) throws URISyntaxException {
        log.debug("REST request to update Nationaliteit : {}, {}", id, nationaliteit);
        if (nationaliteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nationaliteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nationaliteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nationaliteit = nationaliteitRepository.save(nationaliteit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nationaliteit.getId().toString()))
            .body(nationaliteit);
    }

    /**
     * {@code PATCH  /nationaliteits/:id} : Partial updates given fields of an existing nationaliteit, field will ignore if it is null
     *
     * @param id the id of the nationaliteit to save.
     * @param nationaliteit the nationaliteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationaliteit,
     * or with status {@code 400 (Bad Request)} if the nationaliteit is not valid,
     * or with status {@code 404 (Not Found)} if the nationaliteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the nationaliteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nationaliteit> partialUpdateNationaliteit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Nationaliteit nationaliteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nationaliteit partially : {}, {}", id, nationaliteit);
        if (nationaliteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nationaliteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nationaliteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nationaliteit> result = nationaliteitRepository
            .findById(nationaliteit.getId())
            .map(existingNationaliteit -> {
                if (nationaliteit.getBuitenlandsenationaliteit() != null) {
                    existingNationaliteit.setBuitenlandsenationaliteit(nationaliteit.getBuitenlandsenationaliteit());
                }
                if (nationaliteit.getDatumeindegeldigheid() != null) {
                    existingNationaliteit.setDatumeindegeldigheid(nationaliteit.getDatumeindegeldigheid());
                }
                if (nationaliteit.getDatuminganggeldigheid() != null) {
                    existingNationaliteit.setDatuminganggeldigheid(nationaliteit.getDatuminganggeldigheid());
                }
                if (nationaliteit.getDatumopnamen() != null) {
                    existingNationaliteit.setDatumopnamen(nationaliteit.getDatumopnamen());
                }
                if (nationaliteit.getDatumverliesnationaliteit() != null) {
                    existingNationaliteit.setDatumverliesnationaliteit(nationaliteit.getDatumverliesnationaliteit());
                }
                if (nationaliteit.getNationaliteit() != null) {
                    existingNationaliteit.setNationaliteit(nationaliteit.getNationaliteit());
                }
                if (nationaliteit.getNationaliteitcode() != null) {
                    existingNationaliteit.setNationaliteitcode(nationaliteit.getNationaliteitcode());
                }
                if (nationaliteit.getRedenverkrijgingnederlandsenationaliteit() != null) {
                    existingNationaliteit.setRedenverkrijgingnederlandsenationaliteit(
                        nationaliteit.getRedenverkrijgingnederlandsenationaliteit()
                    );
                }
                if (nationaliteit.getRedenverliesnederlandsenationaliteit() != null) {
                    existingNationaliteit.setRedenverliesnederlandsenationaliteit(nationaliteit.getRedenverliesnederlandsenationaliteit());
                }

                return existingNationaliteit;
            })
            .map(nationaliteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nationaliteit.getId().toString())
        );
    }

    /**
     * {@code GET  /nationaliteits} : get all the nationaliteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nationaliteits in body.
     */
    @GetMapping("")
    public List<Nationaliteit> getAllNationaliteits() {
        log.debug("REST request to get all Nationaliteits");
        return nationaliteitRepository.findAll();
    }

    /**
     * {@code GET  /nationaliteits/:id} : get the "id" nationaliteit.
     *
     * @param id the id of the nationaliteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nationaliteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nationaliteit> getNationaliteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Nationaliteit : {}", id);
        Optional<Nationaliteit> nationaliteit = nationaliteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nationaliteit);
    }

    /**
     * {@code DELETE  /nationaliteits/:id} : delete the "id" nationaliteit.
     *
     * @param id the id of the nationaliteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNationaliteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nationaliteit : {}", id);
        nationaliteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
