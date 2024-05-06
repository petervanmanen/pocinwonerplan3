package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bevinding;
import nl.ritense.demo.repository.BevindingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bevinding}.
 */
@RestController
@RequestMapping("/api/bevindings")
@Transactional
public class BevindingResource {

    private final Logger log = LoggerFactory.getLogger(BevindingResource.class);

    private static final String ENTITY_NAME = "bevinding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BevindingRepository bevindingRepository;

    public BevindingResource(BevindingRepository bevindingRepository) {
        this.bevindingRepository = bevindingRepository;
    }

    /**
     * {@code POST  /bevindings} : Create a new bevinding.
     *
     * @param bevinding the bevinding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bevinding, or with status {@code 400 (Bad Request)} if the bevinding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bevinding> createBevinding(@Valid @RequestBody Bevinding bevinding) throws URISyntaxException {
        log.debug("REST request to save Bevinding : {}", bevinding);
        if (bevinding.getId() != null) {
            throw new BadRequestAlertException("A new bevinding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bevinding = bevindingRepository.save(bevinding);
        return ResponseEntity.created(new URI("/api/bevindings/" + bevinding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bevinding.getId().toString()))
            .body(bevinding);
    }

    /**
     * {@code PUT  /bevindings/:id} : Updates an existing bevinding.
     *
     * @param id the id of the bevinding to save.
     * @param bevinding the bevinding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bevinding,
     * or with status {@code 400 (Bad Request)} if the bevinding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bevinding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bevinding> updateBevinding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bevinding bevinding
    ) throws URISyntaxException {
        log.debug("REST request to update Bevinding : {}, {}", id, bevinding);
        if (bevinding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bevinding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bevindingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bevinding = bevindingRepository.save(bevinding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bevinding.getId().toString()))
            .body(bevinding);
    }

    /**
     * {@code PATCH  /bevindings/:id} : Partial updates given fields of an existing bevinding, field will ignore if it is null
     *
     * @param id the id of the bevinding to save.
     * @param bevinding the bevinding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bevinding,
     * or with status {@code 400 (Bad Request)} if the bevinding is not valid,
     * or with status {@code 404 (Not Found)} if the bevinding is not found,
     * or with status {@code 500 (Internal Server Error)} if the bevinding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bevinding> partialUpdateBevinding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bevinding bevinding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bevinding partially : {}, {}", id, bevinding);
        if (bevinding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bevinding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bevindingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bevinding> result = bevindingRepository
            .findById(bevinding.getId())
            .map(existingBevinding -> {
                if (bevinding.getAangemaaktdoor() != null) {
                    existingBevinding.setAangemaaktdoor(bevinding.getAangemaaktdoor());
                }
                if (bevinding.getActiviteit() != null) {
                    existingBevinding.setActiviteit(bevinding.getActiviteit());
                }
                if (bevinding.getControleelement() != null) {
                    existingBevinding.setControleelement(bevinding.getControleelement());
                }
                if (bevinding.getControleniveau() != null) {
                    existingBevinding.setControleniveau(bevinding.getControleniveau());
                }
                if (bevinding.getDatumaanmaak() != null) {
                    existingBevinding.setDatumaanmaak(bevinding.getDatumaanmaak());
                }
                if (bevinding.getDatummutatie() != null) {
                    existingBevinding.setDatummutatie(bevinding.getDatummutatie());
                }
                if (bevinding.getDiepte() != null) {
                    existingBevinding.setDiepte(bevinding.getDiepte());
                }
                if (bevinding.getFase() != null) {
                    existingBevinding.setFase(bevinding.getFase());
                }
                if (bevinding.getGemuteerddoor() != null) {
                    existingBevinding.setGemuteerddoor(bevinding.getGemuteerddoor());
                }
                if (bevinding.getResultaat() != null) {
                    existingBevinding.setResultaat(bevinding.getResultaat());
                }
                if (bevinding.getRisico() != null) {
                    existingBevinding.setRisico(bevinding.getRisico());
                }

                return existingBevinding;
            })
            .map(bevindingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bevinding.getId().toString())
        );
    }

    /**
     * {@code GET  /bevindings} : get all the bevindings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bevindings in body.
     */
    @GetMapping("")
    public List<Bevinding> getAllBevindings() {
        log.debug("REST request to get all Bevindings");
        return bevindingRepository.findAll();
    }

    /**
     * {@code GET  /bevindings/:id} : get the "id" bevinding.
     *
     * @param id the id of the bevinding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bevinding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bevinding> getBevinding(@PathVariable("id") Long id) {
        log.debug("REST request to get Bevinding : {}", id);
        Optional<Bevinding> bevinding = bevindingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bevinding);
    }

    /**
     * {@code DELETE  /bevindings/:id} : delete the "id" bevinding.
     *
     * @param id the id of the bevinding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBevinding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bevinding : {}", id);
        bevindingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
