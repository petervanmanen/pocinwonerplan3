package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Mulderfeit;
import nl.ritense.demo.repository.MulderfeitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Mulderfeit}.
 */
@RestController
@RequestMapping("/api/mulderfeits")
@Transactional
public class MulderfeitResource {

    private final Logger log = LoggerFactory.getLogger(MulderfeitResource.class);

    private static final String ENTITY_NAME = "mulderfeit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MulderfeitRepository mulderfeitRepository;

    public MulderfeitResource(MulderfeitRepository mulderfeitRepository) {
        this.mulderfeitRepository = mulderfeitRepository;
    }

    /**
     * {@code POST  /mulderfeits} : Create a new mulderfeit.
     *
     * @param mulderfeit the mulderfeit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mulderfeit, or with status {@code 400 (Bad Request)} if the mulderfeit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Mulderfeit> createMulderfeit(@RequestBody Mulderfeit mulderfeit) throws URISyntaxException {
        log.debug("REST request to save Mulderfeit : {}", mulderfeit);
        if (mulderfeit.getId() != null) {
            throw new BadRequestAlertException("A new mulderfeit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mulderfeit = mulderfeitRepository.save(mulderfeit);
        return ResponseEntity.created(new URI("/api/mulderfeits/" + mulderfeit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mulderfeit.getId().toString()))
            .body(mulderfeit);
    }

    /**
     * {@code PUT  /mulderfeits/:id} : Updates an existing mulderfeit.
     *
     * @param id the id of the mulderfeit to save.
     * @param mulderfeit the mulderfeit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mulderfeit,
     * or with status {@code 400 (Bad Request)} if the mulderfeit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mulderfeit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mulderfeit> updateMulderfeit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mulderfeit mulderfeit
    ) throws URISyntaxException {
        log.debug("REST request to update Mulderfeit : {}, {}", id, mulderfeit);
        if (mulderfeit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mulderfeit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mulderfeitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mulderfeit = mulderfeitRepository.save(mulderfeit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mulderfeit.getId().toString()))
            .body(mulderfeit);
    }

    /**
     * {@code PATCH  /mulderfeits/:id} : Partial updates given fields of an existing mulderfeit, field will ignore if it is null
     *
     * @param id the id of the mulderfeit to save.
     * @param mulderfeit the mulderfeit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mulderfeit,
     * or with status {@code 400 (Bad Request)} if the mulderfeit is not valid,
     * or with status {@code 404 (Not Found)} if the mulderfeit is not found,
     * or with status {@code 500 (Internal Server Error)} if the mulderfeit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mulderfeit> partialUpdateMulderfeit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mulderfeit mulderfeit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mulderfeit partially : {}, {}", id, mulderfeit);
        if (mulderfeit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mulderfeit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mulderfeitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mulderfeit> result = mulderfeitRepository
            .findById(mulderfeit.getId())
            .map(existingMulderfeit -> {
                if (mulderfeit.getBedrag() != null) {
                    existingMulderfeit.setBedrag(mulderfeit.getBedrag());
                }
                if (mulderfeit.getBezwaarafgehandeld() != null) {
                    existingMulderfeit.setBezwaarafgehandeld(mulderfeit.getBezwaarafgehandeld());
                }
                if (mulderfeit.getBezwaaringetrokken() != null) {
                    existingMulderfeit.setBezwaaringetrokken(mulderfeit.getBezwaaringetrokken());
                }
                if (mulderfeit.getBezwaartoegewezen() != null) {
                    existingMulderfeit.setBezwaartoegewezen(mulderfeit.getBezwaartoegewezen());
                }
                if (mulderfeit.getBonnummer() != null) {
                    existingMulderfeit.setBonnummer(mulderfeit.getBonnummer());
                }
                if (mulderfeit.getDatumbetaling() != null) {
                    existingMulderfeit.setDatumbetaling(mulderfeit.getDatumbetaling());
                }
                if (mulderfeit.getDatumbezwaar() != null) {
                    existingMulderfeit.setDatumbezwaar(mulderfeit.getDatumbezwaar());
                }
                if (mulderfeit.getDatumgeseponeerd() != null) {
                    existingMulderfeit.setDatumgeseponeerd(mulderfeit.getDatumgeseponeerd());
                }
                if (mulderfeit.getDatumindiening() != null) {
                    existingMulderfeit.setDatumindiening(mulderfeit.getDatumindiening());
                }
                if (mulderfeit.getDienstcd() != null) {
                    existingMulderfeit.setDienstcd(mulderfeit.getDienstcd());
                }
                if (mulderfeit.getOrganisatie() != null) {
                    existingMulderfeit.setOrganisatie(mulderfeit.getOrganisatie());
                }
                if (mulderfeit.getOvertreding() != null) {
                    existingMulderfeit.setOvertreding(mulderfeit.getOvertreding());
                }
                if (mulderfeit.getParkeertarief() != null) {
                    existingMulderfeit.setParkeertarief(mulderfeit.getParkeertarief());
                }
                if (mulderfeit.getRedenseponeren() != null) {
                    existingMulderfeit.setRedenseponeren(mulderfeit.getRedenseponeren());
                }
                if (mulderfeit.getVorderingnummer() != null) {
                    existingMulderfeit.setVorderingnummer(mulderfeit.getVorderingnummer());
                }

                return existingMulderfeit;
            })
            .map(mulderfeitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mulderfeit.getId().toString())
        );
    }

    /**
     * {@code GET  /mulderfeits} : get all the mulderfeits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mulderfeits in body.
     */
    @GetMapping("")
    public List<Mulderfeit> getAllMulderfeits() {
        log.debug("REST request to get all Mulderfeits");
        return mulderfeitRepository.findAll();
    }

    /**
     * {@code GET  /mulderfeits/:id} : get the "id" mulderfeit.
     *
     * @param id the id of the mulderfeit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mulderfeit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mulderfeit> getMulderfeit(@PathVariable("id") Long id) {
        log.debug("REST request to get Mulderfeit : {}", id);
        Optional<Mulderfeit> mulderfeit = mulderfeitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mulderfeit);
    }

    /**
     * {@code DELETE  /mulderfeits/:id} : delete the "id" mulderfeit.
     *
     * @param id the id of the mulderfeit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMulderfeit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Mulderfeit : {}", id);
        mulderfeitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
