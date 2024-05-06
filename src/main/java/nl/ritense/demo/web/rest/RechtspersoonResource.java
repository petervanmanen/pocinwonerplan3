package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Rechtspersoon;
import nl.ritense.demo.repository.RechtspersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Rechtspersoon}.
 */
@RestController
@RequestMapping("/api/rechtspersoons")
@Transactional
public class RechtspersoonResource {

    private final Logger log = LoggerFactory.getLogger(RechtspersoonResource.class);

    private static final String ENTITY_NAME = "rechtspersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RechtspersoonRepository rechtspersoonRepository;

    public RechtspersoonResource(RechtspersoonRepository rechtspersoonRepository) {
        this.rechtspersoonRepository = rechtspersoonRepository;
    }

    /**
     * {@code POST  /rechtspersoons} : Create a new rechtspersoon.
     *
     * @param rechtspersoon the rechtspersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rechtspersoon, or with status {@code 400 (Bad Request)} if the rechtspersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rechtspersoon> createRechtspersoon(@Valid @RequestBody Rechtspersoon rechtspersoon) throws URISyntaxException {
        log.debug("REST request to save Rechtspersoon : {}", rechtspersoon);
        if (rechtspersoon.getId() != null) {
            throw new BadRequestAlertException("A new rechtspersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rechtspersoon = rechtspersoonRepository.save(rechtspersoon);
        return ResponseEntity.created(new URI("/api/rechtspersoons/" + rechtspersoon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rechtspersoon.getId().toString()))
            .body(rechtspersoon);
    }

    /**
     * {@code PUT  /rechtspersoons/:id} : Updates an existing rechtspersoon.
     *
     * @param id the id of the rechtspersoon to save.
     * @param rechtspersoon the rechtspersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rechtspersoon,
     * or with status {@code 400 (Bad Request)} if the rechtspersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rechtspersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rechtspersoon> updateRechtspersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Rechtspersoon rechtspersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Rechtspersoon : {}, {}", id, rechtspersoon);
        if (rechtspersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rechtspersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rechtspersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rechtspersoon = rechtspersoonRepository.save(rechtspersoon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rechtspersoon.getId().toString()))
            .body(rechtspersoon);
    }

    /**
     * {@code PATCH  /rechtspersoons/:id} : Partial updates given fields of an existing rechtspersoon, field will ignore if it is null
     *
     * @param id the id of the rechtspersoon to save.
     * @param rechtspersoon the rechtspersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rechtspersoon,
     * or with status {@code 400 (Bad Request)} if the rechtspersoon is not valid,
     * or with status {@code 404 (Not Found)} if the rechtspersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the rechtspersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rechtspersoon> partialUpdateRechtspersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Rechtspersoon rechtspersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rechtspersoon partially : {}, {}", id, rechtspersoon);
        if (rechtspersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rechtspersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rechtspersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rechtspersoon> result = rechtspersoonRepository
            .findById(rechtspersoon.getId())
            .map(existingRechtspersoon -> {
                if (rechtspersoon.getAdresbinnenland() != null) {
                    existingRechtspersoon.setAdresbinnenland(rechtspersoon.getAdresbinnenland());
                }
                if (rechtspersoon.getAdresbuitenland() != null) {
                    existingRechtspersoon.setAdresbuitenland(rechtspersoon.getAdresbuitenland());
                }
                if (rechtspersoon.getAdrescorrespondentie() != null) {
                    existingRechtspersoon.setAdrescorrespondentie(rechtspersoon.getAdrescorrespondentie());
                }
                if (rechtspersoon.getEmailadres() != null) {
                    existingRechtspersoon.setEmailadres(rechtspersoon.getEmailadres());
                }
                if (rechtspersoon.getFaxnummer() != null) {
                    existingRechtspersoon.setFaxnummer(rechtspersoon.getFaxnummer());
                }
                if (rechtspersoon.getIdentificatie() != null) {
                    existingRechtspersoon.setIdentificatie(rechtspersoon.getIdentificatie());
                }
                if (rechtspersoon.getKvknummer() != null) {
                    existingRechtspersoon.setKvknummer(rechtspersoon.getKvknummer());
                }
                if (rechtspersoon.getNaam() != null) {
                    existingRechtspersoon.setNaam(rechtspersoon.getNaam());
                }
                if (rechtspersoon.getRechtsvorm() != null) {
                    existingRechtspersoon.setRechtsvorm(rechtspersoon.getRechtsvorm());
                }
                if (rechtspersoon.getRekeningnummer() != null) {
                    existingRechtspersoon.setRekeningnummer(rechtspersoon.getRekeningnummer());
                }
                if (rechtspersoon.getTelefoonnummer() != null) {
                    existingRechtspersoon.setTelefoonnummer(rechtspersoon.getTelefoonnummer());
                }

                return existingRechtspersoon;
            })
            .map(rechtspersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rechtspersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /rechtspersoons} : get all the rechtspersoons.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rechtspersoons in body.
     */
    @GetMapping("")
    public List<Rechtspersoon> getAllRechtspersoons(
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("isindiener-is-null".equals(filter)) {
            log.debug("REST request to get all Rechtspersoons where isIndiener is null");
            return StreamSupport.stream(rechtspersoonRepository.findAll().spliterator(), false)
                .filter(rechtspersoon -> rechtspersoon.getIsIndiener() == null)
                .toList();
        }
        log.debug("REST request to get all Rechtspersoons");
        if (eagerload) {
            return rechtspersoonRepository.findAllWithEagerRelationships();
        } else {
            return rechtspersoonRepository.findAll();
        }
    }

    /**
     * {@code GET  /rechtspersoons/:id} : get the "id" rechtspersoon.
     *
     * @param id the id of the rechtspersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rechtspersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rechtspersoon> getRechtspersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Rechtspersoon : {}", id);
        Optional<Rechtspersoon> rechtspersoon = rechtspersoonRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(rechtspersoon);
    }

    /**
     * {@code DELETE  /rechtspersoons/:id} : delete the "id" rechtspersoon.
     *
     * @param id the id of the rechtspersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRechtspersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rechtspersoon : {}", id);
        rechtspersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
