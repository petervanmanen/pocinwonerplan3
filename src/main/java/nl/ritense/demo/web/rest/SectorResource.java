package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sector;
import nl.ritense.demo.repository.SectorRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sector}.
 */
@RestController
@RequestMapping("/api/sectors")
@Transactional
public class SectorResource {

    private final Logger log = LoggerFactory.getLogger(SectorResource.class);

    private static final String ENTITY_NAME = "sector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SectorRepository sectorRepository;

    public SectorResource(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    /**
     * {@code POST  /sectors} : Create a new sector.
     *
     * @param sector the sector to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sector, or with status {@code 400 (Bad Request)} if the sector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sector> createSector(@RequestBody Sector sector) throws URISyntaxException {
        log.debug("REST request to save Sector : {}", sector);
        if (sector.getId() != null) {
            throw new BadRequestAlertException("A new sector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sector = sectorRepository.save(sector);
        return ResponseEntity.created(new URI("/api/sectors/" + sector.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sector.getId().toString()))
            .body(sector);
    }

    /**
     * {@code PUT  /sectors/:id} : Updates an existing sector.
     *
     * @param id the id of the sector to save.
     * @param sector the sector to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sector,
     * or with status {@code 400 (Bad Request)} if the sector is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sector couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sector> updateSector(@PathVariable(value = "id", required = false) final Long id, @RequestBody Sector sector)
        throws URISyntaxException {
        log.debug("REST request to update Sector : {}, {}", id, sector);
        if (sector.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sector.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sector = sectorRepository.save(sector);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sector.getId().toString()))
            .body(sector);
    }

    /**
     * {@code PATCH  /sectors/:id} : Partial updates given fields of an existing sector, field will ignore if it is null
     *
     * @param id the id of the sector to save.
     * @param sector the sector to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sector,
     * or with status {@code 400 (Bad Request)} if the sector is not valid,
     * or with status {@code 404 (Not Found)} if the sector is not found,
     * or with status {@code 500 (Internal Server Error)} if the sector couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sector> partialUpdateSector(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sector sector
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sector partially : {}, {}", id, sector);
        if (sector.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sector.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sector> result = sectorRepository
            .findById(sector.getId())
            .map(existingSector -> {
                if (sector.getNaam() != null) {
                    existingSector.setNaam(sector.getNaam());
                }
                if (sector.getOmschrijving() != null) {
                    existingSector.setOmschrijving(sector.getOmschrijving());
                }

                return existingSector;
            })
            .map(sectorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sector.getId().toString())
        );
    }

    /**
     * {@code GET  /sectors} : get all the sectors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sectors in body.
     */
    @GetMapping("")
    public List<Sector> getAllSectors() {
        log.debug("REST request to get all Sectors");
        return sectorRepository.findAll();
    }

    /**
     * {@code GET  /sectors/:id} : get the "id" sector.
     *
     * @param id the id of the sector to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sector, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sector> getSector(@PathVariable("id") Long id) {
        log.debug("REST request to get Sector : {}", id);
        Optional<Sector> sector = sectorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sector);
    }

    /**
     * {@code DELETE  /sectors/:id} : delete the "id" sector.
     *
     * @param id the id of the sector to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSector(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sector : {}", id);
        sectorRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
