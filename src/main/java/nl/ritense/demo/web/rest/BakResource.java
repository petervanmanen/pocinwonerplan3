package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bak;
import nl.ritense.demo.repository.BakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bak}.
 */
@RestController
@RequestMapping("/api/baks")
@Transactional
public class BakResource {

    private final Logger log = LoggerFactory.getLogger(BakResource.class);

    private static final String ENTITY_NAME = "bak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BakRepository bakRepository;

    public BakResource(BakRepository bakRepository) {
        this.bakRepository = bakRepository;
    }

    /**
     * {@code POST  /baks} : Create a new bak.
     *
     * @param bak the bak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bak, or with status {@code 400 (Bad Request)} if the bak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bak> createBak(@RequestBody Bak bak) throws URISyntaxException {
        log.debug("REST request to save Bak : {}", bak);
        if (bak.getId() != null) {
            throw new BadRequestAlertException("A new bak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bak = bakRepository.save(bak);
        return ResponseEntity.created(new URI("/api/baks/" + bak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bak.getId().toString()))
            .body(bak);
    }

    /**
     * {@code PUT  /baks/:id} : Updates an existing bak.
     *
     * @param id the id of the bak to save.
     * @param bak the bak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bak,
     * or with status {@code 400 (Bad Request)} if the bak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bak> updateBak(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bak bak)
        throws URISyntaxException {
        log.debug("REST request to update Bak : {}, {}", id, bak);
        if (bak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bak = bakRepository.save(bak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bak.getId().toString()))
            .body(bak);
    }

    /**
     * {@code PATCH  /baks/:id} : Partial updates given fields of an existing bak, field will ignore if it is null
     *
     * @param id the id of the bak to save.
     * @param bak the bak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bak,
     * or with status {@code 400 (Bad Request)} if the bak is not valid,
     * or with status {@code 404 (Not Found)} if the bak is not found,
     * or with status {@code 500 (Internal Server Error)} if the bak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bak> partialUpdateBak(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bak bak)
        throws URISyntaxException {
        log.debug("REST request to partial update Bak partially : {}, {}", id, bak);
        if (bak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bak> result = bakRepository
            .findById(bak.getId())
            .map(existingBak -> {
                if (bak.getBreedte() != null) {
                    existingBak.setBreedte(bak.getBreedte());
                }
                if (bak.getDiameter() != null) {
                    existingBak.setDiameter(bak.getDiameter());
                }
                if (bak.getGewichtleeg() != null) {
                    existingBak.setGewichtleeg(bak.getGewichtleeg());
                }
                if (bak.getGewichtvol() != null) {
                    existingBak.setGewichtvol(bak.getGewichtvol());
                }
                if (bak.getHoogte() != null) {
                    existingBak.setHoogte(bak.getHoogte());
                }
                if (bak.getInhoud() != null) {
                    existingBak.setInhoud(bak.getInhoud());
                }
                if (bak.getJaaronderhouduitgevoerd() != null) {
                    existingBak.setJaaronderhouduitgevoerd(bak.getJaaronderhouduitgevoerd());
                }
                if (bak.getKwaliteitsniveauactueel() != null) {
                    existingBak.setKwaliteitsniveauactueel(bak.getKwaliteitsniveauactueel());
                }
                if (bak.getKwaliteitsniveaugewenst() != null) {
                    existingBak.setKwaliteitsniveaugewenst(bak.getKwaliteitsniveaugewenst());
                }
                if (bak.getLengte() != null) {
                    existingBak.setLengte(bak.getLengte());
                }
                if (bak.getMateriaal() != null) {
                    existingBak.setMateriaal(bak.getMateriaal());
                }
                if (bak.getVerplaatsbaar() != null) {
                    existingBak.setVerplaatsbaar(bak.getVerplaatsbaar());
                }
                if (bak.getVorm() != null) {
                    existingBak.setVorm(bak.getVorm());
                }

                return existingBak;
            })
            .map(bakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bak.getId().toString())
        );
    }

    /**
     * {@code GET  /baks} : get all the baks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baks in body.
     */
    @GetMapping("")
    public List<Bak> getAllBaks() {
        log.debug("REST request to get all Baks");
        return bakRepository.findAll();
    }

    /**
     * {@code GET  /baks/:id} : get the "id" bak.
     *
     * @param id the id of the bak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bak> getBak(@PathVariable("id") Long id) {
        log.debug("REST request to get Bak : {}", id);
        Optional<Bak> bak = bakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bak);
    }

    /**
     * {@code DELETE  /baks/:id} : delete the "id" bak.
     *
     * @param id the id of the bak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bak : {}", id);
        bakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
