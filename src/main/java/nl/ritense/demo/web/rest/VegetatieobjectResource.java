package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vegetatieobject;
import nl.ritense.demo.repository.VegetatieobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vegetatieobject}.
 */
@RestController
@RequestMapping("/api/vegetatieobjects")
@Transactional
public class VegetatieobjectResource {

    private final Logger log = LoggerFactory.getLogger(VegetatieobjectResource.class);

    private static final String ENTITY_NAME = "vegetatieobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VegetatieobjectRepository vegetatieobjectRepository;

    public VegetatieobjectResource(VegetatieobjectRepository vegetatieobjectRepository) {
        this.vegetatieobjectRepository = vegetatieobjectRepository;
    }

    /**
     * {@code POST  /vegetatieobjects} : Create a new vegetatieobject.
     *
     * @param vegetatieobject the vegetatieobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vegetatieobject, or with status {@code 400 (Bad Request)} if the vegetatieobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vegetatieobject> createVegetatieobject(@Valid @RequestBody Vegetatieobject vegetatieobject)
        throws URISyntaxException {
        log.debug("REST request to save Vegetatieobject : {}", vegetatieobject);
        if (vegetatieobject.getId() != null) {
            throw new BadRequestAlertException("A new vegetatieobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vegetatieobject = vegetatieobjectRepository.save(vegetatieobject);
        return ResponseEntity.created(new URI("/api/vegetatieobjects/" + vegetatieobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vegetatieobject.getId().toString()))
            .body(vegetatieobject);
    }

    /**
     * {@code PUT  /vegetatieobjects/:id} : Updates an existing vegetatieobject.
     *
     * @param id the id of the vegetatieobject to save.
     * @param vegetatieobject the vegetatieobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vegetatieobject,
     * or with status {@code 400 (Bad Request)} if the vegetatieobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vegetatieobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vegetatieobject> updateVegetatieobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vegetatieobject vegetatieobject
    ) throws URISyntaxException {
        log.debug("REST request to update Vegetatieobject : {}, {}", id, vegetatieobject);
        if (vegetatieobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vegetatieobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vegetatieobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vegetatieobject = vegetatieobjectRepository.save(vegetatieobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vegetatieobject.getId().toString()))
            .body(vegetatieobject);
    }

    /**
     * {@code PATCH  /vegetatieobjects/:id} : Partial updates given fields of an existing vegetatieobject, field will ignore if it is null
     *
     * @param id the id of the vegetatieobject to save.
     * @param vegetatieobject the vegetatieobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vegetatieobject,
     * or with status {@code 400 (Bad Request)} if the vegetatieobject is not valid,
     * or with status {@code 404 (Not Found)} if the vegetatieobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the vegetatieobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vegetatieobject> partialUpdateVegetatieobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vegetatieobject vegetatieobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vegetatieobject partially : {}, {}", id, vegetatieobject);
        if (vegetatieobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vegetatieobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vegetatieobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vegetatieobject> result = vegetatieobjectRepository
            .findById(vegetatieobject.getId())
            .map(existingVegetatieobject -> {
                if (vegetatieobject.getAfvoeren() != null) {
                    existingVegetatieobject.setAfvoeren(vegetatieobject.getAfvoeren());
                }
                if (vegetatieobject.getBereikbaarheid() != null) {
                    existingVegetatieobject.setBereikbaarheid(vegetatieobject.getBereikbaarheid());
                }
                if (vegetatieobject.getEcologischbeheer() != null) {
                    existingVegetatieobject.setEcologischbeheer(vegetatieobject.getEcologischbeheer());
                }
                if (vegetatieobject.getKwaliteitsniveauactueel() != null) {
                    existingVegetatieobject.setKwaliteitsniveauactueel(vegetatieobject.getKwaliteitsniveauactueel());
                }
                if (vegetatieobject.getKwaliteitsniveaugewenst() != null) {
                    existingVegetatieobject.setKwaliteitsniveaugewenst(vegetatieobject.getKwaliteitsniveaugewenst());
                }
                if (vegetatieobject.getKweker() != null) {
                    existingVegetatieobject.setKweker(vegetatieobject.getKweker());
                }
                if (vegetatieobject.getLeverancier() != null) {
                    existingVegetatieobject.setLeverancier(vegetatieobject.getLeverancier());
                }
                if (vegetatieobject.getEobjectnummer() != null) {
                    existingVegetatieobject.setEobjectnummer(vegetatieobject.getEobjectnummer());
                }
                if (vegetatieobject.getSoortnaam() != null) {
                    existingVegetatieobject.setSoortnaam(vegetatieobject.getSoortnaam());
                }
                if (vegetatieobject.getTypestandplaats() != null) {
                    existingVegetatieobject.setTypestandplaats(vegetatieobject.getTypestandplaats());
                }
                if (vegetatieobject.getTypestandplaatsplus() != null) {
                    existingVegetatieobject.setTypestandplaatsplus(vegetatieobject.getTypestandplaatsplus());
                }
                if (vegetatieobject.getVegetatieobjectbereikbaarheidplus() != null) {
                    existingVegetatieobject.setVegetatieobjectbereikbaarheidplus(vegetatieobject.getVegetatieobjectbereikbaarheidplus());
                }

                return existingVegetatieobject;
            })
            .map(vegetatieobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vegetatieobject.getId().toString())
        );
    }

    /**
     * {@code GET  /vegetatieobjects} : get all the vegetatieobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vegetatieobjects in body.
     */
    @GetMapping("")
    public List<Vegetatieobject> getAllVegetatieobjects() {
        log.debug("REST request to get all Vegetatieobjects");
        return vegetatieobjectRepository.findAll();
    }

    /**
     * {@code GET  /vegetatieobjects/:id} : get the "id" vegetatieobject.
     *
     * @param id the id of the vegetatieobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vegetatieobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vegetatieobject> getVegetatieobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Vegetatieobject : {}", id);
        Optional<Vegetatieobject> vegetatieobject = vegetatieobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vegetatieobject);
    }

    /**
     * {@code DELETE  /vegetatieobjects/:id} : delete the "id" vegetatieobject.
     *
     * @param id the id of the vegetatieobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVegetatieobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vegetatieobject : {}", id);
        vegetatieobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
