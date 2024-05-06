package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Hoofdrekening;
import nl.ritense.demo.repository.HoofdrekeningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Hoofdrekening}.
 */
@RestController
@RequestMapping("/api/hoofdrekenings")
@Transactional
public class HoofdrekeningResource {

    private final Logger log = LoggerFactory.getLogger(HoofdrekeningResource.class);

    private static final String ENTITY_NAME = "hoofdrekening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoofdrekeningRepository hoofdrekeningRepository;

    public HoofdrekeningResource(HoofdrekeningRepository hoofdrekeningRepository) {
        this.hoofdrekeningRepository = hoofdrekeningRepository;
    }

    /**
     * {@code POST  /hoofdrekenings} : Create a new hoofdrekening.
     *
     * @param hoofdrekening the hoofdrekening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hoofdrekening, or with status {@code 400 (Bad Request)} if the hoofdrekening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Hoofdrekening> createHoofdrekening(@Valid @RequestBody Hoofdrekening hoofdrekening) throws URISyntaxException {
        log.debug("REST request to save Hoofdrekening : {}", hoofdrekening);
        if (hoofdrekening.getId() != null) {
            throw new BadRequestAlertException("A new hoofdrekening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hoofdrekening = hoofdrekeningRepository.save(hoofdrekening);
        return ResponseEntity.created(new URI("/api/hoofdrekenings/" + hoofdrekening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, hoofdrekening.getId().toString()))
            .body(hoofdrekening);
    }

    /**
     * {@code PUT  /hoofdrekenings/:id} : Updates an existing hoofdrekening.
     *
     * @param id the id of the hoofdrekening to save.
     * @param hoofdrekening the hoofdrekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoofdrekening,
     * or with status {@code 400 (Bad Request)} if the hoofdrekening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hoofdrekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hoofdrekening> updateHoofdrekening(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Hoofdrekening hoofdrekening
    ) throws URISyntaxException {
        log.debug("REST request to update Hoofdrekening : {}, {}", id, hoofdrekening);
        if (hoofdrekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoofdrekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoofdrekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hoofdrekening = hoofdrekeningRepository.save(hoofdrekening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hoofdrekening.getId().toString()))
            .body(hoofdrekening);
    }

    /**
     * {@code PATCH  /hoofdrekenings/:id} : Partial updates given fields of an existing hoofdrekening, field will ignore if it is null
     *
     * @param id the id of the hoofdrekening to save.
     * @param hoofdrekening the hoofdrekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoofdrekening,
     * or with status {@code 400 (Bad Request)} if the hoofdrekening is not valid,
     * or with status {@code 404 (Not Found)} if the hoofdrekening is not found,
     * or with status {@code 500 (Internal Server Error)} if the hoofdrekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Hoofdrekening> partialUpdateHoofdrekening(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Hoofdrekening hoofdrekening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hoofdrekening partially : {}, {}", id, hoofdrekening);
        if (hoofdrekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoofdrekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoofdrekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hoofdrekening> result = hoofdrekeningRepository
            .findById(hoofdrekening.getId())
            .map(existingHoofdrekening -> {
                if (hoofdrekening.getNaam() != null) {
                    existingHoofdrekening.setNaam(hoofdrekening.getNaam());
                }
                if (hoofdrekening.getNummer() != null) {
                    existingHoofdrekening.setNummer(hoofdrekening.getNummer());
                }
                if (hoofdrekening.getOmschrijving() != null) {
                    existingHoofdrekening.setOmschrijving(hoofdrekening.getOmschrijving());
                }
                if (hoofdrekening.getPiahoofcategorieomschrijving() != null) {
                    existingHoofdrekening.setPiahoofcategorieomschrijving(hoofdrekening.getPiahoofcategorieomschrijving());
                }
                if (hoofdrekening.getPiahoofdcategoriecode() != null) {
                    existingHoofdrekening.setPiahoofdcategoriecode(hoofdrekening.getPiahoofdcategoriecode());
                }
                if (hoofdrekening.getSubcode() != null) {
                    existingHoofdrekening.setSubcode(hoofdrekening.getSubcode());
                }
                if (hoofdrekening.getSubcodeomschrijving() != null) {
                    existingHoofdrekening.setSubcodeomschrijving(hoofdrekening.getSubcodeomschrijving());
                }

                return existingHoofdrekening;
            })
            .map(hoofdrekeningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hoofdrekening.getId().toString())
        );
    }

    /**
     * {@code GET  /hoofdrekenings} : get all the hoofdrekenings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hoofdrekenings in body.
     */
    @GetMapping("")
    public List<Hoofdrekening> getAllHoofdrekenings(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Hoofdrekenings");
        if (eagerload) {
            return hoofdrekeningRepository.findAllWithEagerRelationships();
        } else {
            return hoofdrekeningRepository.findAll();
        }
    }

    /**
     * {@code GET  /hoofdrekenings/:id} : get the "id" hoofdrekening.
     *
     * @param id the id of the hoofdrekening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hoofdrekening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hoofdrekening> getHoofdrekening(@PathVariable("id") Long id) {
        log.debug("REST request to get Hoofdrekening : {}", id);
        Optional<Hoofdrekening> hoofdrekening = hoofdrekeningRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(hoofdrekening);
    }

    /**
     * {@code DELETE  /hoofdrekenings/:id} : delete the "id" hoofdrekening.
     *
     * @param id the id of the hoofdrekening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoofdrekening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Hoofdrekening : {}", id);
        hoofdrekeningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
