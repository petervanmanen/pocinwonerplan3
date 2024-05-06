package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bouwwerk;
import nl.ritense.demo.repository.BouwwerkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bouwwerk}.
 */
@RestController
@RequestMapping("/api/bouwwerks")
@Transactional
public class BouwwerkResource {

    private final Logger log = LoggerFactory.getLogger(BouwwerkResource.class);

    private static final String ENTITY_NAME = "bouwwerk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BouwwerkRepository bouwwerkRepository;

    public BouwwerkResource(BouwwerkRepository bouwwerkRepository) {
        this.bouwwerkRepository = bouwwerkRepository;
    }

    /**
     * {@code POST  /bouwwerks} : Create a new bouwwerk.
     *
     * @param bouwwerk the bouwwerk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bouwwerk, or with status {@code 400 (Bad Request)} if the bouwwerk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bouwwerk> createBouwwerk(@RequestBody Bouwwerk bouwwerk) throws URISyntaxException {
        log.debug("REST request to save Bouwwerk : {}", bouwwerk);
        if (bouwwerk.getId() != null) {
            throw new BadRequestAlertException("A new bouwwerk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bouwwerk = bouwwerkRepository.save(bouwwerk);
        return ResponseEntity.created(new URI("/api/bouwwerks/" + bouwwerk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bouwwerk.getId().toString()))
            .body(bouwwerk);
    }

    /**
     * {@code PUT  /bouwwerks/:id} : Updates an existing bouwwerk.
     *
     * @param id the id of the bouwwerk to save.
     * @param bouwwerk the bouwwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwwerk,
     * or with status {@code 400 (Bad Request)} if the bouwwerk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bouwwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bouwwerk> updateBouwwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bouwwerk bouwwerk
    ) throws URISyntaxException {
        log.debug("REST request to update Bouwwerk : {}, {}", id, bouwwerk);
        if (bouwwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bouwwerk = bouwwerkRepository.save(bouwwerk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwwerk.getId().toString()))
            .body(bouwwerk);
    }

    /**
     * {@code PATCH  /bouwwerks/:id} : Partial updates given fields of an existing bouwwerk, field will ignore if it is null
     *
     * @param id the id of the bouwwerk to save.
     * @param bouwwerk the bouwwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwwerk,
     * or with status {@code 400 (Bad Request)} if the bouwwerk is not valid,
     * or with status {@code 404 (Not Found)} if the bouwwerk is not found,
     * or with status {@code 500 (Internal Server Error)} if the bouwwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bouwwerk> partialUpdateBouwwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bouwwerk bouwwerk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bouwwerk partially : {}, {}", id, bouwwerk);
        if (bouwwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bouwwerk> result = bouwwerkRepository
            .findById(bouwwerk.getId())
            .map(existingBouwwerk -> {
                if (bouwwerk.getAanleghoogte() != null) {
                    existingBouwwerk.setAanleghoogte(bouwwerk.getAanleghoogte());
                }
                if (bouwwerk.getBouwwerkmateriaal() != null) {
                    existingBouwwerk.setBouwwerkmateriaal(bouwwerk.getBouwwerkmateriaal());
                }
                if (bouwwerk.getBreedte() != null) {
                    existingBouwwerk.setBreedte(bouwwerk.getBreedte());
                }
                if (bouwwerk.getFabrikant() != null) {
                    existingBouwwerk.setFabrikant(bouwwerk.getFabrikant());
                }
                if (bouwwerk.getHoogte() != null) {
                    existingBouwwerk.setHoogte(bouwwerk.getHoogte());
                }
                if (bouwwerk.getJaaronderhouduitgevoerd() != null) {
                    existingBouwwerk.setJaaronderhouduitgevoerd(bouwwerk.getJaaronderhouduitgevoerd());
                }
                if (bouwwerk.getLengte() != null) {
                    existingBouwwerk.setLengte(bouwwerk.getLengte());
                }
                if (bouwwerk.getLeverancier() != null) {
                    existingBouwwerk.setLeverancier(bouwwerk.getLeverancier());
                }
                if (bouwwerk.getOppervlakte() != null) {
                    existingBouwwerk.setOppervlakte(bouwwerk.getOppervlakte());
                }
                if (bouwwerk.getTypefundering() != null) {
                    existingBouwwerk.setTypefundering(bouwwerk.getTypefundering());
                }

                return existingBouwwerk;
            })
            .map(bouwwerkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwwerk.getId().toString())
        );
    }

    /**
     * {@code GET  /bouwwerks} : get all the bouwwerks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bouwwerks in body.
     */
    @GetMapping("")
    public List<Bouwwerk> getAllBouwwerks() {
        log.debug("REST request to get all Bouwwerks");
        return bouwwerkRepository.findAll();
    }

    /**
     * {@code GET  /bouwwerks/:id} : get the "id" bouwwerk.
     *
     * @param id the id of the bouwwerk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bouwwerk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bouwwerk> getBouwwerk(@PathVariable("id") Long id) {
        log.debug("REST request to get Bouwwerk : {}", id);
        Optional<Bouwwerk> bouwwerk = bouwwerkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bouwwerk);
    }

    /**
     * {@code DELETE  /bouwwerks/:id} : delete the "id" bouwwerk.
     *
     * @param id the id of the bouwwerk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBouwwerk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bouwwerk : {}", id);
        bouwwerkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
