package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bord;
import nl.ritense.demo.repository.BordRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bord}.
 */
@RestController
@RequestMapping("/api/bords")
@Transactional
public class BordResource {

    private final Logger log = LoggerFactory.getLogger(BordResource.class);

    private static final String ENTITY_NAME = "bord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BordRepository bordRepository;

    public BordResource(BordRepository bordRepository) {
        this.bordRepository = bordRepository;
    }

    /**
     * {@code POST  /bords} : Create a new bord.
     *
     * @param bord the bord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bord, or with status {@code 400 (Bad Request)} if the bord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bord> createBord(@RequestBody Bord bord) throws URISyntaxException {
        log.debug("REST request to save Bord : {}", bord);
        if (bord.getId() != null) {
            throw new BadRequestAlertException("A new bord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bord = bordRepository.save(bord);
        return ResponseEntity.created(new URI("/api/bords/" + bord.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bord.getId().toString()))
            .body(bord);
    }

    /**
     * {@code PUT  /bords/:id} : Updates an existing bord.
     *
     * @param id the id of the bord to save.
     * @param bord the bord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bord,
     * or with status {@code 400 (Bad Request)} if the bord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bord> updateBord(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bord bord)
        throws URISyntaxException {
        log.debug("REST request to update Bord : {}, {}", id, bord);
        if (bord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bord = bordRepository.save(bord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bord.getId().toString()))
            .body(bord);
    }

    /**
     * {@code PATCH  /bords/:id} : Partial updates given fields of an existing bord, field will ignore if it is null
     *
     * @param id the id of the bord to save.
     * @param bord the bord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bord,
     * or with status {@code 400 (Bad Request)} if the bord is not valid,
     * or with status {@code 404 (Not Found)} if the bord is not found,
     * or with status {@code 500 (Internal Server Error)} if the bord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bord> partialUpdateBord(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bord bord)
        throws URISyntaxException {
        log.debug("REST request to partial update Bord partially : {}, {}", id, bord);
        if (bord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bord> result = bordRepository
            .findById(bord.getId())
            .map(existingBord -> {
                if (bord.getBreedte() != null) {
                    existingBord.setBreedte(bord.getBreedte());
                }
                if (bord.getDiameter() != null) {
                    existingBord.setDiameter(bord.getDiameter());
                }
                if (bord.getDrager() != null) {
                    existingBord.setDrager(bord.getDrager());
                }
                if (bord.getHoogte() != null) {
                    existingBord.setHoogte(bord.getHoogte());
                }
                if (bord.getJaaronderhouduitgevoerd() != null) {
                    existingBord.setJaaronderhouduitgevoerd(bord.getJaaronderhouduitgevoerd());
                }
                if (bord.getLengte() != null) {
                    existingBord.setLengte(bord.getLengte());
                }
                if (bord.getLeverancier() != null) {
                    existingBord.setLeverancier(bord.getLeverancier());
                }
                if (bord.getMateriaal() != null) {
                    existingBord.setMateriaal(bord.getMateriaal());
                }
                if (bord.getVorm() != null) {
                    existingBord.setVorm(bord.getVorm());
                }

                return existingBord;
            })
            .map(bordRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bord.getId().toString())
        );
    }

    /**
     * {@code GET  /bords} : get all the bords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bords in body.
     */
    @GetMapping("")
    public List<Bord> getAllBords() {
        log.debug("REST request to get all Bords");
        return bordRepository.findAll();
    }

    /**
     * {@code GET  /bords/:id} : get the "id" bord.
     *
     * @param id the id of the bord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bord> getBord(@PathVariable("id") Long id) {
        log.debug("REST request to get Bord : {}", id);
        Optional<Bord> bord = bordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bord);
    }

    /**
     * {@code DELETE  /bords/:id} : delete the "id" bord.
     *
     * @param id the id of the bord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBord(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bord : {}", id);
        bordRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
