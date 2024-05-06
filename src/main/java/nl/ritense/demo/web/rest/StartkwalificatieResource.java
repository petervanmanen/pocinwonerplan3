package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Startkwalificatie;
import nl.ritense.demo.repository.StartkwalificatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Startkwalificatie}.
 */
@RestController
@RequestMapping("/api/startkwalificaties")
@Transactional
public class StartkwalificatieResource {

    private final Logger log = LoggerFactory.getLogger(StartkwalificatieResource.class);

    private static final String ENTITY_NAME = "startkwalificatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StartkwalificatieRepository startkwalificatieRepository;

    public StartkwalificatieResource(StartkwalificatieRepository startkwalificatieRepository) {
        this.startkwalificatieRepository = startkwalificatieRepository;
    }

    /**
     * {@code POST  /startkwalificaties} : Create a new startkwalificatie.
     *
     * @param startkwalificatie the startkwalificatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new startkwalificatie, or with status {@code 400 (Bad Request)} if the startkwalificatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Startkwalificatie> createStartkwalificatie(@RequestBody Startkwalificatie startkwalificatie)
        throws URISyntaxException {
        log.debug("REST request to save Startkwalificatie : {}", startkwalificatie);
        if (startkwalificatie.getId() != null) {
            throw new BadRequestAlertException("A new startkwalificatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        startkwalificatie = startkwalificatieRepository.save(startkwalificatie);
        return ResponseEntity.created(new URI("/api/startkwalificaties/" + startkwalificatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, startkwalificatie.getId().toString()))
            .body(startkwalificatie);
    }

    /**
     * {@code PUT  /startkwalificaties/:id} : Updates an existing startkwalificatie.
     *
     * @param id the id of the startkwalificatie to save.
     * @param startkwalificatie the startkwalificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated startkwalificatie,
     * or with status {@code 400 (Bad Request)} if the startkwalificatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the startkwalificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Startkwalificatie> updateStartkwalificatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Startkwalificatie startkwalificatie
    ) throws URISyntaxException {
        log.debug("REST request to update Startkwalificatie : {}, {}", id, startkwalificatie);
        if (startkwalificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, startkwalificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!startkwalificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        startkwalificatie = startkwalificatieRepository.save(startkwalificatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, startkwalificatie.getId().toString()))
            .body(startkwalificatie);
    }

    /**
     * {@code PATCH  /startkwalificaties/:id} : Partial updates given fields of an existing startkwalificatie, field will ignore if it is null
     *
     * @param id the id of the startkwalificatie to save.
     * @param startkwalificatie the startkwalificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated startkwalificatie,
     * or with status {@code 400 (Bad Request)} if the startkwalificatie is not valid,
     * or with status {@code 404 (Not Found)} if the startkwalificatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the startkwalificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Startkwalificatie> partialUpdateStartkwalificatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Startkwalificatie startkwalificatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Startkwalificatie partially : {}, {}", id, startkwalificatie);
        if (startkwalificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, startkwalificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!startkwalificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Startkwalificatie> result = startkwalificatieRepository
            .findById(startkwalificatie.getId())
            .map(existingStartkwalificatie -> {
                if (startkwalificatie.getDatumbehaald() != null) {
                    existingStartkwalificatie.setDatumbehaald(startkwalificatie.getDatumbehaald());
                }

                return existingStartkwalificatie;
            })
            .map(startkwalificatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, startkwalificatie.getId().toString())
        );
    }

    /**
     * {@code GET  /startkwalificaties} : get all the startkwalificaties.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of startkwalificaties in body.
     */
    @GetMapping("")
    public List<Startkwalificatie> getAllStartkwalificaties(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftleerling-is-null".equals(filter)) {
            log.debug("REST request to get all Startkwalificaties where heeftLeerling is null");
            return StreamSupport.stream(startkwalificatieRepository.findAll().spliterator(), false)
                .filter(startkwalificatie -> startkwalificatie.getHeeftLeerling() == null)
                .toList();
        }
        log.debug("REST request to get all Startkwalificaties");
        return startkwalificatieRepository.findAll();
    }

    /**
     * {@code GET  /startkwalificaties/:id} : get the "id" startkwalificatie.
     *
     * @param id the id of the startkwalificatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the startkwalificatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Startkwalificatie> getStartkwalificatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Startkwalificatie : {}", id);
        Optional<Startkwalificatie> startkwalificatie = startkwalificatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(startkwalificatie);
    }

    /**
     * {@code DELETE  /startkwalificaties/:id} : delete the "id" startkwalificatie.
     *
     * @param id the id of the startkwalificatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStartkwalificatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Startkwalificatie : {}", id);
        startkwalificatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
