package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Telefoontje;
import nl.ritense.demo.repository.TelefoontjeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Telefoontje}.
 */
@RestController
@RequestMapping("/api/telefoontjes")
@Transactional
public class TelefoontjeResource {

    private final Logger log = LoggerFactory.getLogger(TelefoontjeResource.class);

    private static final String ENTITY_NAME = "telefoontje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelefoontjeRepository telefoontjeRepository;

    public TelefoontjeResource(TelefoontjeRepository telefoontjeRepository) {
        this.telefoontjeRepository = telefoontjeRepository;
    }

    /**
     * {@code POST  /telefoontjes} : Create a new telefoontje.
     *
     * @param telefoontje the telefoontje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telefoontje, or with status {@code 400 (Bad Request)} if the telefoontje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Telefoontje> createTelefoontje(@Valid @RequestBody Telefoontje telefoontje) throws URISyntaxException {
        log.debug("REST request to save Telefoontje : {}", telefoontje);
        if (telefoontje.getId() != null) {
            throw new BadRequestAlertException("A new telefoontje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        telefoontje = telefoontjeRepository.save(telefoontje);
        return ResponseEntity.created(new URI("/api/telefoontjes/" + telefoontje.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, telefoontje.getId().toString()))
            .body(telefoontje);
    }

    /**
     * {@code PUT  /telefoontjes/:id} : Updates an existing telefoontje.
     *
     * @param id the id of the telefoontje to save.
     * @param telefoontje the telefoontje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoontje,
     * or with status {@code 400 (Bad Request)} if the telefoontje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telefoontje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Telefoontje> updateTelefoontje(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Telefoontje telefoontje
    ) throws URISyntaxException {
        log.debug("REST request to update Telefoontje : {}, {}", id, telefoontje);
        if (telefoontje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telefoontje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telefoontjeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        telefoontje = telefoontjeRepository.save(telefoontje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telefoontje.getId().toString()))
            .body(telefoontje);
    }

    /**
     * {@code PATCH  /telefoontjes/:id} : Partial updates given fields of an existing telefoontje, field will ignore if it is null
     *
     * @param id the id of the telefoontje to save.
     * @param telefoontje the telefoontje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoontje,
     * or with status {@code 400 (Bad Request)} if the telefoontje is not valid,
     * or with status {@code 404 (Not Found)} if the telefoontje is not found,
     * or with status {@code 500 (Internal Server Error)} if the telefoontje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Telefoontje> partialUpdateTelefoontje(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Telefoontje telefoontje
    ) throws URISyntaxException {
        log.debug("REST request to partial update Telefoontje partially : {}, {}", id, telefoontje);
        if (telefoontje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telefoontje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telefoontjeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Telefoontje> result = telefoontjeRepository
            .findById(telefoontje.getId())
            .map(existingTelefoontje -> {
                if (telefoontje.getAfhandeltijdnagesprek() != null) {
                    existingTelefoontje.setAfhandeltijdnagesprek(telefoontje.getAfhandeltijdnagesprek());
                }
                if (telefoontje.getDeltaisdnconnectie() != null) {
                    existingTelefoontje.setDeltaisdnconnectie(telefoontje.getDeltaisdnconnectie());
                }
                if (telefoontje.getEindtijd() != null) {
                    existingTelefoontje.setEindtijd(telefoontje.getEindtijd());
                }
                if (telefoontje.getStarttijd() != null) {
                    existingTelefoontje.setStarttijd(telefoontje.getStarttijd());
                }
                if (telefoontje.getTotaleonholdtijd() != null) {
                    existingTelefoontje.setTotaleonholdtijd(telefoontje.getTotaleonholdtijd());
                }
                if (telefoontje.getTotalespreektijd() != null) {
                    existingTelefoontje.setTotalespreektijd(telefoontje.getTotalespreektijd());
                }
                if (telefoontje.getTotalewachttijd() != null) {
                    existingTelefoontje.setTotalewachttijd(telefoontje.getTotalewachttijd());
                }
                if (telefoontje.getTotlatetijdsduur() != null) {
                    existingTelefoontje.setTotlatetijdsduur(telefoontje.getTotlatetijdsduur());
                }
                if (telefoontje.getTrackid() != null) {
                    existingTelefoontje.setTrackid(telefoontje.getTrackid());
                }

                return existingTelefoontje;
            })
            .map(telefoontjeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telefoontje.getId().toString())
        );
    }

    /**
     * {@code GET  /telefoontjes} : get all the telefoontjes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telefoontjes in body.
     */
    @GetMapping("")
    public List<Telefoontje> getAllTelefoontjes() {
        log.debug("REST request to get all Telefoontjes");
        return telefoontjeRepository.findAll();
    }

    /**
     * {@code GET  /telefoontjes/:id} : get the "id" telefoontje.
     *
     * @param id the id of the telefoontje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telefoontje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Telefoontje> getTelefoontje(@PathVariable("id") Long id) {
        log.debug("REST request to get Telefoontje : {}", id);
        Optional<Telefoontje> telefoontje = telefoontjeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(telefoontje);
    }

    /**
     * {@code DELETE  /telefoontjes/:id} : delete the "id" telefoontje.
     *
     * @param id the id of the telefoontje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelefoontje(@PathVariable("id") Long id) {
        log.debug("REST request to delete Telefoontje : {}", id);
        telefoontjeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
