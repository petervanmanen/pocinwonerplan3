package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aomaanvraagwmojeugd;
import nl.ritense.demo.repository.AomaanvraagwmojeugdRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aomaanvraagwmojeugd}.
 */
@RestController
@RequestMapping("/api/aomaanvraagwmojeugds")
@Transactional
public class AomaanvraagwmojeugdResource {

    private final Logger log = LoggerFactory.getLogger(AomaanvraagwmojeugdResource.class);

    private static final String ENTITY_NAME = "aomaanvraagwmojeugd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AomaanvraagwmojeugdRepository aomaanvraagwmojeugdRepository;

    public AomaanvraagwmojeugdResource(AomaanvraagwmojeugdRepository aomaanvraagwmojeugdRepository) {
        this.aomaanvraagwmojeugdRepository = aomaanvraagwmojeugdRepository;
    }

    /**
     * {@code POST  /aomaanvraagwmojeugds} : Create a new aomaanvraagwmojeugd.
     *
     * @param aomaanvraagwmojeugd the aomaanvraagwmojeugd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aomaanvraagwmojeugd, or with status {@code 400 (Bad Request)} if the aomaanvraagwmojeugd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aomaanvraagwmojeugd> createAomaanvraagwmojeugd(@Valid @RequestBody Aomaanvraagwmojeugd aomaanvraagwmojeugd)
        throws URISyntaxException {
        log.debug("REST request to save Aomaanvraagwmojeugd : {}", aomaanvraagwmojeugd);
        if (aomaanvraagwmojeugd.getId() != null) {
            throw new BadRequestAlertException("A new aomaanvraagwmojeugd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aomaanvraagwmojeugd = aomaanvraagwmojeugdRepository.save(aomaanvraagwmojeugd);
        return ResponseEntity.created(new URI("/api/aomaanvraagwmojeugds/" + aomaanvraagwmojeugd.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aomaanvraagwmojeugd.getId().toString()))
            .body(aomaanvraagwmojeugd);
    }

    /**
     * {@code PUT  /aomaanvraagwmojeugds/:id} : Updates an existing aomaanvraagwmojeugd.
     *
     * @param id the id of the aomaanvraagwmojeugd to save.
     * @param aomaanvraagwmojeugd the aomaanvraagwmojeugd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aomaanvraagwmojeugd,
     * or with status {@code 400 (Bad Request)} if the aomaanvraagwmojeugd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aomaanvraagwmojeugd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aomaanvraagwmojeugd> updateAomaanvraagwmojeugd(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Aomaanvraagwmojeugd aomaanvraagwmojeugd
    ) throws URISyntaxException {
        log.debug("REST request to update Aomaanvraagwmojeugd : {}, {}", id, aomaanvraagwmojeugd);
        if (aomaanvraagwmojeugd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aomaanvraagwmojeugd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aomaanvraagwmojeugdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aomaanvraagwmojeugd = aomaanvraagwmojeugdRepository.save(aomaanvraagwmojeugd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aomaanvraagwmojeugd.getId().toString()))
            .body(aomaanvraagwmojeugd);
    }

    /**
     * {@code PATCH  /aomaanvraagwmojeugds/:id} : Partial updates given fields of an existing aomaanvraagwmojeugd, field will ignore if it is null
     *
     * @param id the id of the aomaanvraagwmojeugd to save.
     * @param aomaanvraagwmojeugd the aomaanvraagwmojeugd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aomaanvraagwmojeugd,
     * or with status {@code 400 (Bad Request)} if the aomaanvraagwmojeugd is not valid,
     * or with status {@code 404 (Not Found)} if the aomaanvraagwmojeugd is not found,
     * or with status {@code 500 (Internal Server Error)} if the aomaanvraagwmojeugd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aomaanvraagwmojeugd> partialUpdateAomaanvraagwmojeugd(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Aomaanvraagwmojeugd aomaanvraagwmojeugd
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aomaanvraagwmojeugd partially : {}, {}", id, aomaanvraagwmojeugd);
        if (aomaanvraagwmojeugd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aomaanvraagwmojeugd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aomaanvraagwmojeugdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aomaanvraagwmojeugd> result = aomaanvraagwmojeugdRepository
            .findById(aomaanvraagwmojeugd.getId())
            .map(existingAomaanvraagwmojeugd -> {
                if (aomaanvraagwmojeugd.getClientreactie() != null) {
                    existingAomaanvraagwmojeugd.setClientreactie(aomaanvraagwmojeugd.getClientreactie());
                }
                if (aomaanvraagwmojeugd.getDatumbeschikking() != null) {
                    existingAomaanvraagwmojeugd.setDatumbeschikking(aomaanvraagwmojeugd.getDatumbeschikking());
                }
                if (aomaanvraagwmojeugd.getDatumeersteafspraak() != null) {
                    existingAomaanvraagwmojeugd.setDatumeersteafspraak(aomaanvraagwmojeugd.getDatumeersteafspraak());
                }
                if (aomaanvraagwmojeugd.getDatumeinde() != null) {
                    existingAomaanvraagwmojeugd.setDatumeinde(aomaanvraagwmojeugd.getDatumeinde());
                }
                if (aomaanvraagwmojeugd.getDatumplanvastgesteld() != null) {
                    existingAomaanvraagwmojeugd.setDatumplanvastgesteld(aomaanvraagwmojeugd.getDatumplanvastgesteld());
                }
                if (aomaanvraagwmojeugd.getDatumstartaanvraag() != null) {
                    existingAomaanvraagwmojeugd.setDatumstartaanvraag(aomaanvraagwmojeugd.getDatumstartaanvraag());
                }
                if (aomaanvraagwmojeugd.getDeskundigheid() != null) {
                    existingAomaanvraagwmojeugd.setDeskundigheid(aomaanvraagwmojeugd.getDeskundigheid());
                }
                if (aomaanvraagwmojeugd.getDoorloopmethodiek() != null) {
                    existingAomaanvraagwmojeugd.setDoorloopmethodiek(aomaanvraagwmojeugd.getDoorloopmethodiek());
                }
                if (aomaanvraagwmojeugd.getMaximaledoorlooptijd() != null) {
                    existingAomaanvraagwmojeugd.setMaximaledoorlooptijd(aomaanvraagwmojeugd.getMaximaledoorlooptijd());
                }
                if (aomaanvraagwmojeugd.getRedenafsluiting() != null) {
                    existingAomaanvraagwmojeugd.setRedenafsluiting(aomaanvraagwmojeugd.getRedenafsluiting());
                }

                return existingAomaanvraagwmojeugd;
            })
            .map(aomaanvraagwmojeugdRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aomaanvraagwmojeugd.getId().toString())
        );
    }

    /**
     * {@code GET  /aomaanvraagwmojeugds} : get all the aomaanvraagwmojeugds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aomaanvraagwmojeugds in body.
     */
    @GetMapping("")
    public List<Aomaanvraagwmojeugd> getAllAomaanvraagwmojeugds() {
        log.debug("REST request to get all Aomaanvraagwmojeugds");
        return aomaanvraagwmojeugdRepository.findAll();
    }

    /**
     * {@code GET  /aomaanvraagwmojeugds/:id} : get the "id" aomaanvraagwmojeugd.
     *
     * @param id the id of the aomaanvraagwmojeugd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aomaanvraagwmojeugd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aomaanvraagwmojeugd> getAomaanvraagwmojeugd(@PathVariable("id") Long id) {
        log.debug("REST request to get Aomaanvraagwmojeugd : {}", id);
        Optional<Aomaanvraagwmojeugd> aomaanvraagwmojeugd = aomaanvraagwmojeugdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aomaanvraagwmojeugd);
    }

    /**
     * {@code DELETE  /aomaanvraagwmojeugds/:id} : delete the "id" aomaanvraagwmojeugd.
     *
     * @param id the id of the aomaanvraagwmojeugd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAomaanvraagwmojeugd(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aomaanvraagwmojeugd : {}", id);
        aomaanvraagwmojeugdRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
