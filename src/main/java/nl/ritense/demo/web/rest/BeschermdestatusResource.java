package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beschermdestatus;
import nl.ritense.demo.repository.BeschermdestatusRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beschermdestatus}.
 */
@RestController
@RequestMapping("/api/beschermdestatuses")
@Transactional
public class BeschermdestatusResource {

    private final Logger log = LoggerFactory.getLogger(BeschermdestatusResource.class);

    private static final String ENTITY_NAME = "beschermdestatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeschermdestatusRepository beschermdestatusRepository;

    public BeschermdestatusResource(BeschermdestatusRepository beschermdestatusRepository) {
        this.beschermdestatusRepository = beschermdestatusRepository;
    }

    /**
     * {@code POST  /beschermdestatuses} : Create a new beschermdestatus.
     *
     * @param beschermdestatus the beschermdestatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beschermdestatus, or with status {@code 400 (Bad Request)} if the beschermdestatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beschermdestatus> createBeschermdestatus(@Valid @RequestBody Beschermdestatus beschermdestatus)
        throws URISyntaxException {
        log.debug("REST request to save Beschermdestatus : {}", beschermdestatus);
        if (beschermdestatus.getId() != null) {
            throw new BadRequestAlertException("A new beschermdestatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beschermdestatus = beschermdestatusRepository.save(beschermdestatus);
        return ResponseEntity.created(new URI("/api/beschermdestatuses/" + beschermdestatus.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beschermdestatus.getId().toString()))
            .body(beschermdestatus);
    }

    /**
     * {@code PUT  /beschermdestatuses/:id} : Updates an existing beschermdestatus.
     *
     * @param id the id of the beschermdestatus to save.
     * @param beschermdestatus the beschermdestatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beschermdestatus,
     * or with status {@code 400 (Bad Request)} if the beschermdestatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beschermdestatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beschermdestatus> updateBeschermdestatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Beschermdestatus beschermdestatus
    ) throws URISyntaxException {
        log.debug("REST request to update Beschermdestatus : {}, {}", id, beschermdestatus);
        if (beschermdestatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beschermdestatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beschermdestatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beschermdestatus = beschermdestatusRepository.save(beschermdestatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beschermdestatus.getId().toString()))
            .body(beschermdestatus);
    }

    /**
     * {@code PATCH  /beschermdestatuses/:id} : Partial updates given fields of an existing beschermdestatus, field will ignore if it is null
     *
     * @param id the id of the beschermdestatus to save.
     * @param beschermdestatus the beschermdestatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beschermdestatus,
     * or with status {@code 400 (Bad Request)} if the beschermdestatus is not valid,
     * or with status {@code 404 (Not Found)} if the beschermdestatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the beschermdestatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beschermdestatus> partialUpdateBeschermdestatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Beschermdestatus beschermdestatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beschermdestatus partially : {}, {}", id, beschermdestatus);
        if (beschermdestatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beschermdestatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beschermdestatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beschermdestatus> result = beschermdestatusRepository
            .findById(beschermdestatus.getId())
            .map(existingBeschermdestatus -> {
                if (beschermdestatus.getBronnen() != null) {
                    existingBeschermdestatus.setBronnen(beschermdestatus.getBronnen());
                }
                if (beschermdestatus.getComplex() != null) {
                    existingBeschermdestatus.setComplex(beschermdestatus.getComplex());
                }
                if (beschermdestatus.getDatuminschrijvingregister() != null) {
                    existingBeschermdestatus.setDatuminschrijvingregister(beschermdestatus.getDatuminschrijvingregister());
                }
                if (beschermdestatus.getGemeentelijkmonumentcode() != null) {
                    existingBeschermdestatus.setGemeentelijkmonumentcode(beschermdestatus.getGemeentelijkmonumentcode());
                }
                if (beschermdestatus.getGezichtscode() != null) {
                    existingBeschermdestatus.setGezichtscode(beschermdestatus.getGezichtscode());
                }
                if (beschermdestatus.getNaam() != null) {
                    existingBeschermdestatus.setNaam(beschermdestatus.getNaam());
                }
                if (beschermdestatus.getOmschrijving() != null) {
                    existingBeschermdestatus.setOmschrijving(beschermdestatus.getOmschrijving());
                }
                if (beschermdestatus.getOpmerkingen() != null) {
                    existingBeschermdestatus.setOpmerkingen(beschermdestatus.getOpmerkingen());
                }
                if (beschermdestatus.getRijksmonumentcode() != null) {
                    existingBeschermdestatus.setRijksmonumentcode(beschermdestatus.getRijksmonumentcode());
                }
                if (beschermdestatus.getType() != null) {
                    existingBeschermdestatus.setType(beschermdestatus.getType());
                }

                return existingBeschermdestatus;
            })
            .map(beschermdestatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beschermdestatus.getId().toString())
        );
    }

    /**
     * {@code GET  /beschermdestatuses} : get all the beschermdestatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beschermdestatuses in body.
     */
    @GetMapping("")
    public List<Beschermdestatus> getAllBeschermdestatuses() {
        log.debug("REST request to get all Beschermdestatuses");
        return beschermdestatusRepository.findAll();
    }

    /**
     * {@code GET  /beschermdestatuses/:id} : get the "id" beschermdestatus.
     *
     * @param id the id of the beschermdestatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beschermdestatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beschermdestatus> getBeschermdestatus(@PathVariable("id") Long id) {
        log.debug("REST request to get Beschermdestatus : {}", id);
        Optional<Beschermdestatus> beschermdestatus = beschermdestatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beschermdestatus);
    }

    /**
     * {@code DELETE  /beschermdestatuses/:id} : delete the "id" beschermdestatus.
     *
     * @param id the id of the beschermdestatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeschermdestatus(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beschermdestatus : {}", id);
        beschermdestatusRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
