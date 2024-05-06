package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Toewijzing;
import nl.ritense.demo.repository.ToewijzingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Toewijzing}.
 */
@RestController
@RequestMapping("/api/toewijzings")
@Transactional
public class ToewijzingResource {

    private final Logger log = LoggerFactory.getLogger(ToewijzingResource.class);

    private static final String ENTITY_NAME = "toewijzing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ToewijzingRepository toewijzingRepository;

    public ToewijzingResource(ToewijzingRepository toewijzingRepository) {
        this.toewijzingRepository = toewijzingRepository;
    }

    /**
     * {@code POST  /toewijzings} : Create a new toewijzing.
     *
     * @param toewijzing the toewijzing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new toewijzing, or with status {@code 400 (Bad Request)} if the toewijzing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Toewijzing> createToewijzing(@Valid @RequestBody Toewijzing toewijzing) throws URISyntaxException {
        log.debug("REST request to save Toewijzing : {}", toewijzing);
        if (toewijzing.getId() != null) {
            throw new BadRequestAlertException("A new toewijzing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        toewijzing = toewijzingRepository.save(toewijzing);
        return ResponseEntity.created(new URI("/api/toewijzings/" + toewijzing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, toewijzing.getId().toString()))
            .body(toewijzing);
    }

    /**
     * {@code PUT  /toewijzings/:id} : Updates an existing toewijzing.
     *
     * @param id the id of the toewijzing to save.
     * @param toewijzing the toewijzing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated toewijzing,
     * or with status {@code 400 (Bad Request)} if the toewijzing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the toewijzing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Toewijzing> updateToewijzing(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Toewijzing toewijzing
    ) throws URISyntaxException {
        log.debug("REST request to update Toewijzing : {}, {}", id, toewijzing);
        if (toewijzing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, toewijzing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!toewijzingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        toewijzing = toewijzingRepository.save(toewijzing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, toewijzing.getId().toString()))
            .body(toewijzing);
    }

    /**
     * {@code PATCH  /toewijzings/:id} : Partial updates given fields of an existing toewijzing, field will ignore if it is null
     *
     * @param id the id of the toewijzing to save.
     * @param toewijzing the toewijzing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated toewijzing,
     * or with status {@code 400 (Bad Request)} if the toewijzing is not valid,
     * or with status {@code 404 (Not Found)} if the toewijzing is not found,
     * or with status {@code 500 (Internal Server Error)} if the toewijzing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Toewijzing> partialUpdateToewijzing(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Toewijzing toewijzing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Toewijzing partially : {}, {}", id, toewijzing);
        if (toewijzing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, toewijzing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!toewijzingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Toewijzing> result = toewijzingRepository
            .findById(toewijzing.getId())
            .map(existingToewijzing -> {
                if (toewijzing.getCode() != null) {
                    existingToewijzing.setCode(toewijzing.getCode());
                }
                if (toewijzing.getCommentaar() != null) {
                    existingToewijzing.setCommentaar(toewijzing.getCommentaar());
                }
                if (toewijzing.getDatumaanschaf() != null) {
                    existingToewijzing.setDatumaanschaf(toewijzing.getDatumaanschaf());
                }
                if (toewijzing.getDatumeindetoewijzing() != null) {
                    existingToewijzing.setDatumeindetoewijzing(toewijzing.getDatumeindetoewijzing());
                }
                if (toewijzing.getDatumstarttoewijzing() != null) {
                    existingToewijzing.setDatumstarttoewijzing(toewijzing.getDatumstarttoewijzing());
                }
                if (toewijzing.getDatumtoewijzing() != null) {
                    existingToewijzing.setDatumtoewijzing(toewijzing.getDatumtoewijzing());
                }
                if (toewijzing.getEenheid() != null) {
                    existingToewijzing.setEenheid(toewijzing.getEenheid());
                }
                if (toewijzing.getFrequentie() != null) {
                    existingToewijzing.setFrequentie(toewijzing.getFrequentie());
                }
                if (toewijzing.getOmvang() != null) {
                    existingToewijzing.setOmvang(toewijzing.getOmvang());
                }
                if (toewijzing.getRedenwijziging() != null) {
                    existingToewijzing.setRedenwijziging(toewijzing.getRedenwijziging());
                }
                if (toewijzing.getToewijzingnummer() != null) {
                    existingToewijzing.setToewijzingnummer(toewijzing.getToewijzingnummer());
                }
                if (toewijzing.getWet() != null) {
                    existingToewijzing.setWet(toewijzing.getWet());
                }

                return existingToewijzing;
            })
            .map(toewijzingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, toewijzing.getId().toString())
        );
    }

    /**
     * {@code GET  /toewijzings} : get all the toewijzings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of toewijzings in body.
     */
    @GetMapping("")
    public List<Toewijzing> getAllToewijzings() {
        log.debug("REST request to get all Toewijzings");
        return toewijzingRepository.findAll();
    }

    /**
     * {@code GET  /toewijzings/:id} : get the "id" toewijzing.
     *
     * @param id the id of the toewijzing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the toewijzing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Toewijzing> getToewijzing(@PathVariable("id") Long id) {
        log.debug("REST request to get Toewijzing : {}", id);
        Optional<Toewijzing> toewijzing = toewijzingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(toewijzing);
    }

    /**
     * {@code DELETE  /toewijzings/:id} : delete the "id" toewijzing.
     *
     * @param id the id of the toewijzing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToewijzing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Toewijzing : {}", id);
        toewijzingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
