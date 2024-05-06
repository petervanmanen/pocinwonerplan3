package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gemeentebegrafenis;
import nl.ritense.demo.repository.GemeentebegrafenisRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gemeentebegrafenis}.
 */
@RestController
@RequestMapping("/api/gemeentebegrafenis")
@Transactional
public class GemeentebegrafenisResource {

    private final Logger log = LoggerFactory.getLogger(GemeentebegrafenisResource.class);

    private static final String ENTITY_NAME = "gemeentebegrafenis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GemeentebegrafenisRepository gemeentebegrafenisRepository;

    public GemeentebegrafenisResource(GemeentebegrafenisRepository gemeentebegrafenisRepository) {
        this.gemeentebegrafenisRepository = gemeentebegrafenisRepository;
    }

    /**
     * {@code POST  /gemeentebegrafenis} : Create a new gemeentebegrafenis.
     *
     * @param gemeentebegrafenis the gemeentebegrafenis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gemeentebegrafenis, or with status {@code 400 (Bad Request)} if the gemeentebegrafenis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gemeentebegrafenis> createGemeentebegrafenis(@RequestBody Gemeentebegrafenis gemeentebegrafenis)
        throws URISyntaxException {
        log.debug("REST request to save Gemeentebegrafenis : {}", gemeentebegrafenis);
        if (gemeentebegrafenis.getId() != null) {
            throw new BadRequestAlertException("A new gemeentebegrafenis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gemeentebegrafenis = gemeentebegrafenisRepository.save(gemeentebegrafenis);
        return ResponseEntity.created(new URI("/api/gemeentebegrafenis/" + gemeentebegrafenis.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gemeentebegrafenis.getId().toString()))
            .body(gemeentebegrafenis);
    }

    /**
     * {@code PUT  /gemeentebegrafenis/:id} : Updates an existing gemeentebegrafenis.
     *
     * @param id the id of the gemeentebegrafenis to save.
     * @param gemeentebegrafenis the gemeentebegrafenis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemeentebegrafenis,
     * or with status {@code 400 (Bad Request)} if the gemeentebegrafenis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gemeentebegrafenis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gemeentebegrafenis> updateGemeentebegrafenis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gemeentebegrafenis gemeentebegrafenis
    ) throws URISyntaxException {
        log.debug("REST request to update Gemeentebegrafenis : {}, {}", id, gemeentebegrafenis);
        if (gemeentebegrafenis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemeentebegrafenis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemeentebegrafenisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gemeentebegrafenis = gemeentebegrafenisRepository.save(gemeentebegrafenis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gemeentebegrafenis.getId().toString()))
            .body(gemeentebegrafenis);
    }

    /**
     * {@code PATCH  /gemeentebegrafenis/:id} : Partial updates given fields of an existing gemeentebegrafenis, field will ignore if it is null
     *
     * @param id the id of the gemeentebegrafenis to save.
     * @param gemeentebegrafenis the gemeentebegrafenis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemeentebegrafenis,
     * or with status {@code 400 (Bad Request)} if the gemeentebegrafenis is not valid,
     * or with status {@code 404 (Not Found)} if the gemeentebegrafenis is not found,
     * or with status {@code 500 (Internal Server Error)} if the gemeentebegrafenis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gemeentebegrafenis> partialUpdateGemeentebegrafenis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gemeentebegrafenis gemeentebegrafenis
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gemeentebegrafenis partially : {}, {}", id, gemeentebegrafenis);
        if (gemeentebegrafenis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemeentebegrafenis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemeentebegrafenisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gemeentebegrafenis> result = gemeentebegrafenisRepository
            .findById(gemeentebegrafenis.getId())
            .map(existingGemeentebegrafenis -> {
                if (gemeentebegrafenis.getAchtergrondmelding() != null) {
                    existingGemeentebegrafenis.setAchtergrondmelding(gemeentebegrafenis.getAchtergrondmelding());
                }
                if (gemeentebegrafenis.getBegrafeniskosten() != null) {
                    existingGemeentebegrafenis.setBegrafeniskosten(gemeentebegrafenis.getBegrafeniskosten());
                }
                if (gemeentebegrafenis.getDatumafgedaan() != null) {
                    existingGemeentebegrafenis.setDatumafgedaan(gemeentebegrafenis.getDatumafgedaan());
                }
                if (gemeentebegrafenis.getDatumbegrafenis() != null) {
                    existingGemeentebegrafenis.setDatumbegrafenis(gemeentebegrafenis.getDatumbegrafenis());
                }
                if (gemeentebegrafenis.getDatumgemeld() != null) {
                    existingGemeentebegrafenis.setDatumgemeld(gemeentebegrafenis.getDatumgemeld());
                }
                if (gemeentebegrafenis.getDatumruiminggraf() != null) {
                    existingGemeentebegrafenis.setDatumruiminggraf(gemeentebegrafenis.getDatumruiminggraf());
                }
                if (gemeentebegrafenis.getDoodsoorzaak() != null) {
                    existingGemeentebegrafenis.setDoodsoorzaak(gemeentebegrafenis.getDoodsoorzaak());
                }
                if (gemeentebegrafenis.getGemeentelijkekosten() != null) {
                    existingGemeentebegrafenis.setGemeentelijkekosten(gemeentebegrafenis.getGemeentelijkekosten());
                }
                if (gemeentebegrafenis.getInkoopordernummer() != null) {
                    existingGemeentebegrafenis.setInkoopordernummer(gemeentebegrafenis.getInkoopordernummer());
                }
                if (gemeentebegrafenis.getMelder() != null) {
                    existingGemeentebegrafenis.setMelder(gemeentebegrafenis.getMelder());
                }
                if (gemeentebegrafenis.getUrengemeente() != null) {
                    existingGemeentebegrafenis.setUrengemeente(gemeentebegrafenis.getUrengemeente());
                }
                if (gemeentebegrafenis.getVerhaaldbedrag() != null) {
                    existingGemeentebegrafenis.setVerhaaldbedrag(gemeentebegrafenis.getVerhaaldbedrag());
                }

                return existingGemeentebegrafenis;
            })
            .map(gemeentebegrafenisRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gemeentebegrafenis.getId().toString())
        );
    }

    /**
     * {@code GET  /gemeentebegrafenis} : get all the gemeentebegrafenis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gemeentebegrafenis in body.
     */
    @GetMapping("")
    public List<Gemeentebegrafenis> getAllGemeentebegrafenis() {
        log.debug("REST request to get all Gemeentebegrafenis");
        return gemeentebegrafenisRepository.findAll();
    }

    /**
     * {@code GET  /gemeentebegrafenis/:id} : get the "id" gemeentebegrafenis.
     *
     * @param id the id of the gemeentebegrafenis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gemeentebegrafenis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gemeentebegrafenis> getGemeentebegrafenis(@PathVariable("id") Long id) {
        log.debug("REST request to get Gemeentebegrafenis : {}", id);
        Optional<Gemeentebegrafenis> gemeentebegrafenis = gemeentebegrafenisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gemeentebegrafenis);
    }

    /**
     * {@code DELETE  /gemeentebegrafenis/:id} : delete the "id" gemeentebegrafenis.
     *
     * @param id the id of the gemeentebegrafenis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGemeentebegrafenis(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gemeentebegrafenis : {}", id);
        gemeentebegrafenisRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
