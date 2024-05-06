package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Tenaamstelling;
import nl.ritense.demo.repository.TenaamstellingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Tenaamstelling}.
 */
@RestController
@RequestMapping("/api/tenaamstellings")
@Transactional
public class TenaamstellingResource {

    private final Logger log = LoggerFactory.getLogger(TenaamstellingResource.class);

    private static final String ENTITY_NAME = "tenaamstelling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenaamstellingRepository tenaamstellingRepository;

    public TenaamstellingResource(TenaamstellingRepository tenaamstellingRepository) {
        this.tenaamstellingRepository = tenaamstellingRepository;
    }

    /**
     * {@code POST  /tenaamstellings} : Create a new tenaamstelling.
     *
     * @param tenaamstelling the tenaamstelling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tenaamstelling, or with status {@code 400 (Bad Request)} if the tenaamstelling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tenaamstelling> createTenaamstelling(@Valid @RequestBody Tenaamstelling tenaamstelling)
        throws URISyntaxException {
        log.debug("REST request to save Tenaamstelling : {}", tenaamstelling);
        if (tenaamstelling.getId() != null) {
            throw new BadRequestAlertException("A new tenaamstelling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tenaamstelling = tenaamstellingRepository.save(tenaamstelling);
        return ResponseEntity.created(new URI("/api/tenaamstellings/" + tenaamstelling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tenaamstelling.getId().toString()))
            .body(tenaamstelling);
    }

    /**
     * {@code PUT  /tenaamstellings/:id} : Updates an existing tenaamstelling.
     *
     * @param id the id of the tenaamstelling to save.
     * @param tenaamstelling the tenaamstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenaamstelling,
     * or with status {@code 400 (Bad Request)} if the tenaamstelling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tenaamstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tenaamstelling> updateTenaamstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tenaamstelling tenaamstelling
    ) throws URISyntaxException {
        log.debug("REST request to update Tenaamstelling : {}, {}", id, tenaamstelling);
        if (tenaamstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tenaamstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tenaamstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tenaamstelling = tenaamstellingRepository.save(tenaamstelling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tenaamstelling.getId().toString()))
            .body(tenaamstelling);
    }

    /**
     * {@code PATCH  /tenaamstellings/:id} : Partial updates given fields of an existing tenaamstelling, field will ignore if it is null
     *
     * @param id the id of the tenaamstelling to save.
     * @param tenaamstelling the tenaamstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenaamstelling,
     * or with status {@code 400 (Bad Request)} if the tenaamstelling is not valid,
     * or with status {@code 404 (Not Found)} if the tenaamstelling is not found,
     * or with status {@code 500 (Internal Server Error)} if the tenaamstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tenaamstelling> partialUpdateTenaamstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tenaamstelling tenaamstelling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tenaamstelling partially : {}, {}", id, tenaamstelling);
        if (tenaamstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tenaamstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tenaamstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tenaamstelling> result = tenaamstellingRepository
            .findById(tenaamstelling.getId())
            .map(existingTenaamstelling -> {
                if (tenaamstelling.getAandeelinrecht() != null) {
                    existingTenaamstelling.setAandeelinrecht(tenaamstelling.getAandeelinrecht());
                }
                if (tenaamstelling.getBurgerlijkestaattentijdevanverkrijging() != null) {
                    existingTenaamstelling.setBurgerlijkestaattentijdevanverkrijging(
                        tenaamstelling.getBurgerlijkestaattentijdevanverkrijging()
                    );
                }
                if (tenaamstelling.getDatumbegingeldigheid() != null) {
                    existingTenaamstelling.setDatumbegingeldigheid(tenaamstelling.getDatumbegingeldigheid());
                }
                if (tenaamstelling.getDatumeindegeldigheid() != null) {
                    existingTenaamstelling.setDatumeindegeldigheid(tenaamstelling.getDatumeindegeldigheid());
                }
                if (tenaamstelling.getExploitantcode() != null) {
                    existingTenaamstelling.setExploitantcode(tenaamstelling.getExploitantcode());
                }
                if (tenaamstelling.getIdentificatietenaamstelling() != null) {
                    existingTenaamstelling.setIdentificatietenaamstelling(tenaamstelling.getIdentificatietenaamstelling());
                }
                if (tenaamstelling.getVerklaringinzakederdenbescherming() != null) {
                    existingTenaamstelling.setVerklaringinzakederdenbescherming(tenaamstelling.getVerklaringinzakederdenbescherming());
                }
                if (tenaamstelling.getVerkregennamenssamenwerkingsverband() != null) {
                    existingTenaamstelling.setVerkregennamenssamenwerkingsverband(tenaamstelling.getVerkregennamenssamenwerkingsverband());
                }

                return existingTenaamstelling;
            })
            .map(tenaamstellingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tenaamstelling.getId().toString())
        );
    }

    /**
     * {@code GET  /tenaamstellings} : get all the tenaamstellings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tenaamstellings in body.
     */
    @GetMapping("")
    public List<Tenaamstelling> getAllTenaamstellings() {
        log.debug("REST request to get all Tenaamstellings");
        return tenaamstellingRepository.findAll();
    }

    /**
     * {@code GET  /tenaamstellings/:id} : get the "id" tenaamstelling.
     *
     * @param id the id of the tenaamstelling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenaamstelling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tenaamstelling> getTenaamstelling(@PathVariable("id") Long id) {
        log.debug("REST request to get Tenaamstelling : {}", id);
        Optional<Tenaamstelling> tenaamstelling = tenaamstellingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tenaamstelling);
    }

    /**
     * {@code DELETE  /tenaamstellings/:id} : delete the "id" tenaamstelling.
     *
     * @param id the id of the tenaamstelling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenaamstelling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tenaamstelling : {}", id);
        tenaamstellingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
