package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beschiktevoorziening;
import nl.ritense.demo.repository.BeschiktevoorzieningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beschiktevoorziening}.
 */
@RestController
@RequestMapping("/api/beschiktevoorzienings")
@Transactional
public class BeschiktevoorzieningResource {

    private final Logger log = LoggerFactory.getLogger(BeschiktevoorzieningResource.class);

    private static final String ENTITY_NAME = "beschiktevoorziening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeschiktevoorzieningRepository beschiktevoorzieningRepository;

    public BeschiktevoorzieningResource(BeschiktevoorzieningRepository beschiktevoorzieningRepository) {
        this.beschiktevoorzieningRepository = beschiktevoorzieningRepository;
    }

    /**
     * {@code POST  /beschiktevoorzienings} : Create a new beschiktevoorziening.
     *
     * @param beschiktevoorziening the beschiktevoorziening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beschiktevoorziening, or with status {@code 400 (Bad Request)} if the beschiktevoorziening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beschiktevoorziening> createBeschiktevoorziening(@Valid @RequestBody Beschiktevoorziening beschiktevoorziening)
        throws URISyntaxException {
        log.debug("REST request to save Beschiktevoorziening : {}", beschiktevoorziening);
        if (beschiktevoorziening.getId() != null) {
            throw new BadRequestAlertException("A new beschiktevoorziening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beschiktevoorziening = beschiktevoorzieningRepository.save(beschiktevoorziening);
        return ResponseEntity.created(new URI("/api/beschiktevoorzienings/" + beschiktevoorziening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beschiktevoorziening.getId().toString()))
            .body(beschiktevoorziening);
    }

    /**
     * {@code PUT  /beschiktevoorzienings/:id} : Updates an existing beschiktevoorziening.
     *
     * @param id the id of the beschiktevoorziening to save.
     * @param beschiktevoorziening the beschiktevoorziening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beschiktevoorziening,
     * or with status {@code 400 (Bad Request)} if the beschiktevoorziening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beschiktevoorziening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beschiktevoorziening> updateBeschiktevoorziening(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Beschiktevoorziening beschiktevoorziening
    ) throws URISyntaxException {
        log.debug("REST request to update Beschiktevoorziening : {}, {}", id, beschiktevoorziening);
        if (beschiktevoorziening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beschiktevoorziening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beschiktevoorzieningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beschiktevoorziening = beschiktevoorzieningRepository.save(beschiktevoorziening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beschiktevoorziening.getId().toString()))
            .body(beschiktevoorziening);
    }

    /**
     * {@code PATCH  /beschiktevoorzienings/:id} : Partial updates given fields of an existing beschiktevoorziening, field will ignore if it is null
     *
     * @param id the id of the beschiktevoorziening to save.
     * @param beschiktevoorziening the beschiktevoorziening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beschiktevoorziening,
     * or with status {@code 400 (Bad Request)} if the beschiktevoorziening is not valid,
     * or with status {@code 404 (Not Found)} if the beschiktevoorziening is not found,
     * or with status {@code 500 (Internal Server Error)} if the beschiktevoorziening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beschiktevoorziening> partialUpdateBeschiktevoorziening(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Beschiktevoorziening beschiktevoorziening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beschiktevoorziening partially : {}, {}", id, beschiktevoorziening);
        if (beschiktevoorziening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beschiktevoorziening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beschiktevoorzieningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beschiktevoorziening> result = beschiktevoorzieningRepository
            .findById(beschiktevoorziening.getId())
            .map(existingBeschiktevoorziening -> {
                if (beschiktevoorziening.getCode() != null) {
                    existingBeschiktevoorziening.setCode(beschiktevoorziening.getCode());
                }
                if (beschiktevoorziening.getDatumeinde() != null) {
                    existingBeschiktevoorziening.setDatumeinde(beschiktevoorziening.getDatumeinde());
                }
                if (beschiktevoorziening.getDatumeindeoorspronkelijk() != null) {
                    existingBeschiktevoorziening.setDatumeindeoorspronkelijk(beschiktevoorziening.getDatumeindeoorspronkelijk());
                }
                if (beschiktevoorziening.getDatumstart() != null) {
                    existingBeschiktevoorziening.setDatumstart(beschiktevoorziening.getDatumstart());
                }
                if (beschiktevoorziening.getEenheid() != null) {
                    existingBeschiktevoorziening.setEenheid(beschiktevoorziening.getEenheid());
                }
                if (beschiktevoorziening.getFrequentie() != null) {
                    existingBeschiktevoorziening.setFrequentie(beschiktevoorziening.getFrequentie());
                }
                if (beschiktevoorziening.getLeveringsvorm() != null) {
                    existingBeschiktevoorziening.setLeveringsvorm(beschiktevoorziening.getLeveringsvorm());
                }
                if (beschiktevoorziening.getOmvang() != null) {
                    existingBeschiktevoorziening.setOmvang(beschiktevoorziening.getOmvang());
                }
                if (beschiktevoorziening.getRedeneinde() != null) {
                    existingBeschiktevoorziening.setRedeneinde(beschiktevoorziening.getRedeneinde());
                }
                if (beschiktevoorziening.getStatus() != null) {
                    existingBeschiktevoorziening.setStatus(beschiktevoorziening.getStatus());
                }
                if (beschiktevoorziening.getWet() != null) {
                    existingBeschiktevoorziening.setWet(beschiktevoorziening.getWet());
                }

                return existingBeschiktevoorziening;
            })
            .map(beschiktevoorzieningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beschiktevoorziening.getId().toString())
        );
    }

    /**
     * {@code GET  /beschiktevoorzienings} : get all the beschiktevoorzienings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beschiktevoorzienings in body.
     */
    @GetMapping("")
    public List<Beschiktevoorziening> getAllBeschiktevoorzienings() {
        log.debug("REST request to get all Beschiktevoorzienings");
        return beschiktevoorzieningRepository.findAll();
    }

    /**
     * {@code GET  /beschiktevoorzienings/:id} : get the "id" beschiktevoorziening.
     *
     * @param id the id of the beschiktevoorziening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beschiktevoorziening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beschiktevoorziening> getBeschiktevoorziening(@PathVariable("id") Long id) {
        log.debug("REST request to get Beschiktevoorziening : {}", id);
        Optional<Beschiktevoorziening> beschiktevoorziening = beschiktevoorzieningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beschiktevoorziening);
    }

    /**
     * {@code DELETE  /beschiktevoorzienings/:id} : delete the "id" beschiktevoorziening.
     *
     * @param id the id of the beschiktevoorziening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeschiktevoorziening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beschiktevoorziening : {}", id);
        beschiktevoorzieningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
