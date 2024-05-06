package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Medewerker;
import nl.ritense.demo.repository.MedewerkerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Medewerker}.
 */
@RestController
@RequestMapping("/api/medewerkers")
@Transactional
public class MedewerkerResource {

    private final Logger log = LoggerFactory.getLogger(MedewerkerResource.class);

    private static final String ENTITY_NAME = "medewerker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedewerkerRepository medewerkerRepository;

    public MedewerkerResource(MedewerkerRepository medewerkerRepository) {
        this.medewerkerRepository = medewerkerRepository;
    }

    /**
     * {@code POST  /medewerkers} : Create a new medewerker.
     *
     * @param medewerker the medewerker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medewerker, or with status {@code 400 (Bad Request)} if the medewerker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Medewerker> createMedewerker(@Valid @RequestBody Medewerker medewerker) throws URISyntaxException {
        log.debug("REST request to save Medewerker : {}", medewerker);
        if (medewerker.getId() != null) {
            throw new BadRequestAlertException("A new medewerker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        medewerker = medewerkerRepository.save(medewerker);
        return ResponseEntity.created(new URI("/api/medewerkers/" + medewerker.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, medewerker.getId().toString()))
            .body(medewerker);
    }

    /**
     * {@code PUT  /medewerkers/:id} : Updates an existing medewerker.
     *
     * @param id the id of the medewerker to save.
     * @param medewerker the medewerker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medewerker,
     * or with status {@code 400 (Bad Request)} if the medewerker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medewerker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medewerker> updateMedewerker(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Medewerker medewerker
    ) throws URISyntaxException {
        log.debug("REST request to update Medewerker : {}, {}", id, medewerker);
        if (medewerker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medewerker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medewerkerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        medewerker = medewerkerRepository.save(medewerker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medewerker.getId().toString()))
            .body(medewerker);
    }

    /**
     * {@code PATCH  /medewerkers/:id} : Partial updates given fields of an existing medewerker, field will ignore if it is null
     *
     * @param id the id of the medewerker to save.
     * @param medewerker the medewerker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medewerker,
     * or with status {@code 400 (Bad Request)} if the medewerker is not valid,
     * or with status {@code 404 (Not Found)} if the medewerker is not found,
     * or with status {@code 500 (Internal Server Error)} if the medewerker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Medewerker> partialUpdateMedewerker(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Medewerker medewerker
    ) throws URISyntaxException {
        log.debug("REST request to partial update Medewerker partially : {}, {}", id, medewerker);
        if (medewerker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medewerker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medewerkerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Medewerker> result = medewerkerRepository
            .findById(medewerker.getId())
            .map(existingMedewerker -> {
                if (medewerker.getAchternaam() != null) {
                    existingMedewerker.setAchternaam(medewerker.getAchternaam());
                }
                if (medewerker.getDatumindienst() != null) {
                    existingMedewerker.setDatumindienst(medewerker.getDatumindienst());
                }
                if (medewerker.getDatumuitdienst() != null) {
                    existingMedewerker.setDatumuitdienst(medewerker.getDatumuitdienst());
                }
                if (medewerker.getEmailadres() != null) {
                    existingMedewerker.setEmailadres(medewerker.getEmailadres());
                }
                if (medewerker.getExtern() != null) {
                    existingMedewerker.setExtern(medewerker.getExtern());
                }
                if (medewerker.getFunctie() != null) {
                    existingMedewerker.setFunctie(medewerker.getFunctie());
                }
                if (medewerker.getGeslachtsaanduiding() != null) {
                    existingMedewerker.setGeslachtsaanduiding(medewerker.getGeslachtsaanduiding());
                }
                if (medewerker.getMedewerkeridentificatie() != null) {
                    existingMedewerker.setMedewerkeridentificatie(medewerker.getMedewerkeridentificatie());
                }
                if (medewerker.getMedewerkertoelichting() != null) {
                    existingMedewerker.setMedewerkertoelichting(medewerker.getMedewerkertoelichting());
                }
                if (medewerker.getRoepnaam() != null) {
                    existingMedewerker.setRoepnaam(medewerker.getRoepnaam());
                }
                if (medewerker.getTelefoonnummer() != null) {
                    existingMedewerker.setTelefoonnummer(medewerker.getTelefoonnummer());
                }
                if (medewerker.getVoorletters() != null) {
                    existingMedewerker.setVoorletters(medewerker.getVoorletters());
                }
                if (medewerker.getVoorvoegselachternaam() != null) {
                    existingMedewerker.setVoorvoegselachternaam(medewerker.getVoorvoegselachternaam());
                }

                return existingMedewerker;
            })
            .map(medewerkerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medewerker.getId().toString())
        );
    }

    /**
     * {@code GET  /medewerkers} : get all the medewerkers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medewerkers in body.
     */
    @GetMapping("")
    public List<Medewerker> getAllMedewerkers(@RequestParam(name = "filter", required = false) String filter) {
        if ("isbetrokkene-is-null".equals(filter)) {
            log.debug("REST request to get all Medewerkers where isBetrokkene is null");
            return StreamSupport.stream(medewerkerRepository.findAll().spliterator(), false)
                .filter(medewerker -> medewerker.getIsBetrokkene() == null)
                .toList();
        }
        log.debug("REST request to get all Medewerkers");
        return medewerkerRepository.findAll();
    }

    /**
     * {@code GET  /medewerkers/:id} : get the "id" medewerker.
     *
     * @param id the id of the medewerker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medewerker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medewerker> getMedewerker(@PathVariable("id") Long id) {
        log.debug("REST request to get Medewerker : {}", id);
        Optional<Medewerker> medewerker = medewerkerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medewerker);
    }

    /**
     * {@code DELETE  /medewerkers/:id} : delete the "id" medewerker.
     *
     * @param id the id of the medewerker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedewerker(@PathVariable("id") Long id) {
        log.debug("REST request to delete Medewerker : {}", id);
        medewerkerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
