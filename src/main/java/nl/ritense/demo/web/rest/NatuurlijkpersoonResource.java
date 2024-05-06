package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Natuurlijkpersoon;
import nl.ritense.demo.repository.NatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Natuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/natuurlijkpersoons")
@Transactional
public class NatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(NatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "natuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatuurlijkpersoonRepository natuurlijkpersoonRepository;

    public NatuurlijkpersoonResource(NatuurlijkpersoonRepository natuurlijkpersoonRepository) {
        this.natuurlijkpersoonRepository = natuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /natuurlijkpersoons} : Create a new natuurlijkpersoon.
     *
     * @param natuurlijkpersoon the natuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natuurlijkpersoon, or with status {@code 400 (Bad Request)} if the natuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Natuurlijkpersoon> createNatuurlijkpersoon(@Valid @RequestBody Natuurlijkpersoon natuurlijkpersoon)
        throws URISyntaxException {
        log.debug("REST request to save Natuurlijkpersoon : {}", natuurlijkpersoon);
        if (natuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException("A new natuurlijkpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        natuurlijkpersoon = natuurlijkpersoonRepository.save(natuurlijkpersoon);
        return ResponseEntity.created(new URI("/api/natuurlijkpersoons/" + natuurlijkpersoon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, natuurlijkpersoon.getId().toString()))
            .body(natuurlijkpersoon);
    }

    /**
     * {@code PUT  /natuurlijkpersoons/:id} : Updates an existing natuurlijkpersoon.
     *
     * @param id the id of the natuurlijkpersoon to save.
     * @param natuurlijkpersoon the natuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the natuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Natuurlijkpersoon> updateNatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Natuurlijkpersoon natuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Natuurlijkpersoon : {}, {}", id, natuurlijkpersoon);
        if (natuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        natuurlijkpersoon = natuurlijkpersoonRepository.save(natuurlijkpersoon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, natuurlijkpersoon.getId().toString()))
            .body(natuurlijkpersoon);
    }

    /**
     * {@code PATCH  /natuurlijkpersoons/:id} : Partial updates given fields of an existing natuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the natuurlijkpersoon to save.
     * @param natuurlijkpersoon the natuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the natuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the natuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the natuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Natuurlijkpersoon> partialUpdateNatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Natuurlijkpersoon natuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Natuurlijkpersoon partially : {}, {}", id, natuurlijkpersoon);
        if (natuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Natuurlijkpersoon> result = natuurlijkpersoonRepository
            .findById(natuurlijkpersoon.getId())
            .map(existingNatuurlijkpersoon -> {
                if (natuurlijkpersoon.getEmpty() != null) {
                    existingNatuurlijkpersoon.setEmpty(natuurlijkpersoon.getEmpty());
                }
                if (natuurlijkpersoon.getAanduidingbijzondernederlanderschappersoon() != null) {
                    existingNatuurlijkpersoon.setAanduidingbijzondernederlanderschappersoon(
                        natuurlijkpersoon.getAanduidingbijzondernederlanderschappersoon()
                    );
                }
                if (natuurlijkpersoon.getAanduidingnaamgebruik() != null) {
                    existingNatuurlijkpersoon.setAanduidingnaamgebruik(natuurlijkpersoon.getAanduidingnaamgebruik());
                }
                if (natuurlijkpersoon.getAanhefaanschrijving() != null) {
                    existingNatuurlijkpersoon.setAanhefaanschrijving(natuurlijkpersoon.getAanhefaanschrijving());
                }
                if (natuurlijkpersoon.getAcademischetitel() != null) {
                    existingNatuurlijkpersoon.setAcademischetitel(natuurlijkpersoon.getAcademischetitel());
                }
                if (natuurlijkpersoon.getAchternaam() != null) {
                    existingNatuurlijkpersoon.setAchternaam(natuurlijkpersoon.getAchternaam());
                }
                if (natuurlijkpersoon.getAdellijketitelofpredikaat() != null) {
                    existingNatuurlijkpersoon.setAdellijketitelofpredikaat(natuurlijkpersoon.getAdellijketitelofpredikaat());
                }
                if (natuurlijkpersoon.getAnummer() != null) {
                    existingNatuurlijkpersoon.setAnummer(natuurlijkpersoon.getAnummer());
                }
                if (natuurlijkpersoon.getBurgerservicenummer() != null) {
                    existingNatuurlijkpersoon.setBurgerservicenummer(natuurlijkpersoon.getBurgerservicenummer());
                }
                if (natuurlijkpersoon.getDatumgeboorte() != null) {
                    existingNatuurlijkpersoon.setDatumgeboorte(natuurlijkpersoon.getDatumgeboorte());
                }
                if (natuurlijkpersoon.getDatumoverlijden() != null) {
                    existingNatuurlijkpersoon.setDatumoverlijden(natuurlijkpersoon.getDatumoverlijden());
                }
                if (natuurlijkpersoon.getGeboorteland() != null) {
                    existingNatuurlijkpersoon.setGeboorteland(natuurlijkpersoon.getGeboorteland());
                }
                if (natuurlijkpersoon.getGeboorteplaats() != null) {
                    existingNatuurlijkpersoon.setGeboorteplaats(natuurlijkpersoon.getGeboorteplaats());
                }
                if (natuurlijkpersoon.getGeslachtsaanduiding() != null) {
                    existingNatuurlijkpersoon.setGeslachtsaanduiding(natuurlijkpersoon.getGeslachtsaanduiding());
                }
                if (natuurlijkpersoon.getGeslachtsnaam() != null) {
                    existingNatuurlijkpersoon.setGeslachtsnaam(natuurlijkpersoon.getGeslachtsnaam());
                }
                if (natuurlijkpersoon.getGeslachtsnaamaanschrijving() != null) {
                    existingNatuurlijkpersoon.setGeslachtsnaamaanschrijving(natuurlijkpersoon.getGeslachtsnaamaanschrijving());
                }
                if (natuurlijkpersoon.getHandlichting() != null) {
                    existingNatuurlijkpersoon.setHandlichting(natuurlijkpersoon.getHandlichting());
                }
                if (natuurlijkpersoon.getIndicatieafschermingpersoonsgegevens() != null) {
                    existingNatuurlijkpersoon.setIndicatieafschermingpersoonsgegevens(
                        natuurlijkpersoon.getIndicatieafschermingpersoonsgegevens()
                    );
                }
                if (natuurlijkpersoon.getIndicatieoverleden() != null) {
                    existingNatuurlijkpersoon.setIndicatieoverleden(natuurlijkpersoon.getIndicatieoverleden());
                }
                if (natuurlijkpersoon.getLandoverlijden() != null) {
                    existingNatuurlijkpersoon.setLandoverlijden(natuurlijkpersoon.getLandoverlijden());
                }
                if (natuurlijkpersoon.getNationaliteit() != null) {
                    existingNatuurlijkpersoon.setNationaliteit(natuurlijkpersoon.getNationaliteit());
                }
                if (natuurlijkpersoon.getOverlijdensplaats() != null) {
                    existingNatuurlijkpersoon.setOverlijdensplaats(natuurlijkpersoon.getOverlijdensplaats());
                }
                if (natuurlijkpersoon.getVoorlettersaanschrijving() != null) {
                    existingNatuurlijkpersoon.setVoorlettersaanschrijving(natuurlijkpersoon.getVoorlettersaanschrijving());
                }
                if (natuurlijkpersoon.getVoornamen() != null) {
                    existingNatuurlijkpersoon.setVoornamen(natuurlijkpersoon.getVoornamen());
                }
                if (natuurlijkpersoon.getVoornamenaanschrijving() != null) {
                    existingNatuurlijkpersoon.setVoornamenaanschrijving(natuurlijkpersoon.getVoornamenaanschrijving());
                }
                if (natuurlijkpersoon.getVoorvoegselgeslachtsnaam() != null) {
                    existingNatuurlijkpersoon.setVoorvoegselgeslachtsnaam(natuurlijkpersoon.getVoorvoegselgeslachtsnaam());
                }

                return existingNatuurlijkpersoon;
            })
            .map(natuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, natuurlijkpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /natuurlijkpersoons} : get all the natuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Natuurlijkpersoon> getAllNatuurlijkpersoons() {
        log.debug("REST request to get all Natuurlijkpersoons");
        return natuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /natuurlijkpersoons/:id} : get the "id" natuurlijkpersoon.
     *
     * @param id the id of the natuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Natuurlijkpersoon> getNatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Natuurlijkpersoon : {}", id);
        Optional<Natuurlijkpersoon> natuurlijkpersoon = natuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(natuurlijkpersoon);
    }

    /**
     * {@code DELETE  /natuurlijkpersoons/:id} : delete the "id" natuurlijkpersoon.
     *
     * @param id the id of the natuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Natuurlijkpersoon : {}", id);
        natuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
