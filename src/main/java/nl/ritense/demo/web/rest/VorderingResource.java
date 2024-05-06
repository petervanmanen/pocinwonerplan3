package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vordering;
import nl.ritense.demo.repository.VorderingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vordering}.
 */
@RestController
@RequestMapping("/api/vorderings")
@Transactional
public class VorderingResource {

    private final Logger log = LoggerFactory.getLogger(VorderingResource.class);

    private static final String ENTITY_NAME = "vordering";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VorderingRepository vorderingRepository;

    public VorderingResource(VorderingRepository vorderingRepository) {
        this.vorderingRepository = vorderingRepository;
    }

    /**
     * {@code POST  /vorderings} : Create a new vordering.
     *
     * @param vordering the vordering to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vordering, or with status {@code 400 (Bad Request)} if the vordering has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vordering> createVordering(@RequestBody Vordering vordering) throws URISyntaxException {
        log.debug("REST request to save Vordering : {}", vordering);
        if (vordering.getId() != null) {
            throw new BadRequestAlertException("A new vordering cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vordering = vorderingRepository.save(vordering);
        return ResponseEntity.created(new URI("/api/vorderings/" + vordering.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vordering.getId().toString()))
            .body(vordering);
    }

    /**
     * {@code PUT  /vorderings/:id} : Updates an existing vordering.
     *
     * @param id the id of the vordering to save.
     * @param vordering the vordering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vordering,
     * or with status {@code 400 (Bad Request)} if the vordering is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vordering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vordering> updateVordering(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vordering vordering
    ) throws URISyntaxException {
        log.debug("REST request to update Vordering : {}, {}", id, vordering);
        if (vordering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vordering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vorderingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vordering = vorderingRepository.save(vordering);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vordering.getId().toString()))
            .body(vordering);
    }

    /**
     * {@code PATCH  /vorderings/:id} : Partial updates given fields of an existing vordering, field will ignore if it is null
     *
     * @param id the id of the vordering to save.
     * @param vordering the vordering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vordering,
     * or with status {@code 400 (Bad Request)} if the vordering is not valid,
     * or with status {@code 404 (Not Found)} if the vordering is not found,
     * or with status {@code 500 (Internal Server Error)} if the vordering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vordering> partialUpdateVordering(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vordering vordering
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vordering partially : {}, {}", id, vordering);
        if (vordering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vordering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vorderingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vordering> result = vorderingRepository
            .findById(vordering.getId())
            .map(existingVordering -> {
                if (vordering.getAangemaaktdoor() != null) {
                    existingVordering.setAangemaaktdoor(vordering.getAangemaaktdoor());
                }
                if (vordering.getBedragbtw() != null) {
                    existingVordering.setBedragbtw(vordering.getBedragbtw());
                }
                if (vordering.getDatumaanmaak() != null) {
                    existingVordering.setDatumaanmaak(vordering.getDatumaanmaak());
                }
                if (vordering.getDatummutatie() != null) {
                    existingVordering.setDatummutatie(vordering.getDatummutatie());
                }
                if (vordering.getGeaccordeerd() != null) {
                    existingVordering.setGeaccordeerd(vordering.getGeaccordeerd());
                }
                if (vordering.getGeaccordeerddoor() != null) {
                    existingVordering.setGeaccordeerddoor(vordering.getGeaccordeerddoor());
                }
                if (vordering.getGeaccordeerdop() != null) {
                    existingVordering.setGeaccordeerdop(vordering.getGeaccordeerdop());
                }
                if (vordering.getGeexporteerd() != null) {
                    existingVordering.setGeexporteerd(vordering.getGeexporteerd());
                }
                if (vordering.getGemuteerddoor() != null) {
                    existingVordering.setGemuteerddoor(vordering.getGemuteerddoor());
                }
                if (vordering.getOmschrijving() != null) {
                    existingVordering.setOmschrijving(vordering.getOmschrijving());
                }
                if (vordering.getTotaalbedrag() != null) {
                    existingVordering.setTotaalbedrag(vordering.getTotaalbedrag());
                }
                if (vordering.getTotaalbedraginclusief() != null) {
                    existingVordering.setTotaalbedraginclusief(vordering.getTotaalbedraginclusief());
                }
                if (vordering.getVorderingnummer() != null) {
                    existingVordering.setVorderingnummer(vordering.getVorderingnummer());
                }

                return existingVordering;
            })
            .map(vorderingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vordering.getId().toString())
        );
    }

    /**
     * {@code GET  /vorderings} : get all the vorderings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vorderings in body.
     */
    @GetMapping("")
    public List<Vordering> getAllVorderings() {
        log.debug("REST request to get all Vorderings");
        return vorderingRepository.findAll();
    }

    /**
     * {@code GET  /vorderings/:id} : get the "id" vordering.
     *
     * @param id the id of the vordering to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vordering, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vordering> getVordering(@PathVariable("id") Long id) {
        log.debug("REST request to get Vordering : {}", id);
        Optional<Vordering> vordering = vorderingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vordering);
    }

    /**
     * {@code DELETE  /vorderings/:id} : delete the "id" vordering.
     *
     * @param id the id of the vordering to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVordering(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vordering : {}", id);
        vorderingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
