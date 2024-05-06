package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kadastraalperceel;
import nl.ritense.demo.repository.KadastraalperceelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kadastraalperceel}.
 */
@RestController
@RequestMapping("/api/kadastraalperceels")
@Transactional
public class KadastraalperceelResource {

    private final Logger log = LoggerFactory.getLogger(KadastraalperceelResource.class);

    private static final String ENTITY_NAME = "kadastraalperceel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KadastraalperceelRepository kadastraalperceelRepository;

    public KadastraalperceelResource(KadastraalperceelRepository kadastraalperceelRepository) {
        this.kadastraalperceelRepository = kadastraalperceelRepository;
    }

    /**
     * {@code POST  /kadastraalperceels} : Create a new kadastraalperceel.
     *
     * @param kadastraalperceel the kadastraalperceel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kadastraalperceel, or with status {@code 400 (Bad Request)} if the kadastraalperceel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kadastraalperceel> createKadastraalperceel(@Valid @RequestBody Kadastraalperceel kadastraalperceel)
        throws URISyntaxException {
        log.debug("REST request to save Kadastraalperceel : {}", kadastraalperceel);
        if (kadastraalperceel.getId() != null) {
            throw new BadRequestAlertException("A new kadastraalperceel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kadastraalperceel = kadastraalperceelRepository.save(kadastraalperceel);
        return ResponseEntity.created(new URI("/api/kadastraalperceels/" + kadastraalperceel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kadastraalperceel.getId().toString()))
            .body(kadastraalperceel);
    }

    /**
     * {@code PUT  /kadastraalperceels/:id} : Updates an existing kadastraalperceel.
     *
     * @param id the id of the kadastraalperceel to save.
     * @param kadastraalperceel the kadastraalperceel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastraalperceel,
     * or with status {@code 400 (Bad Request)} if the kadastraalperceel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kadastraalperceel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kadastraalperceel> updateKadastraalperceel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Kadastraalperceel kadastraalperceel
    ) throws URISyntaxException {
        log.debug("REST request to update Kadastraalperceel : {}, {}", id, kadastraalperceel);
        if (kadastraalperceel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastraalperceel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastraalperceelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kadastraalperceel = kadastraalperceelRepository.save(kadastraalperceel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastraalperceel.getId().toString()))
            .body(kadastraalperceel);
    }

    /**
     * {@code PATCH  /kadastraalperceels/:id} : Partial updates given fields of an existing kadastraalperceel, field will ignore if it is null
     *
     * @param id the id of the kadastraalperceel to save.
     * @param kadastraalperceel the kadastraalperceel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastraalperceel,
     * or with status {@code 400 (Bad Request)} if the kadastraalperceel is not valid,
     * or with status {@code 404 (Not Found)} if the kadastraalperceel is not found,
     * or with status {@code 500 (Internal Server Error)} if the kadastraalperceel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kadastraalperceel> partialUpdateKadastraalperceel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Kadastraalperceel kadastraalperceel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kadastraalperceel partially : {}, {}", id, kadastraalperceel);
        if (kadastraalperceel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastraalperceel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastraalperceelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kadastraalperceel> result = kadastraalperceelRepository
            .findById(kadastraalperceel.getId())
            .map(existingKadastraalperceel -> {
                if (kadastraalperceel.getAanduidingsoortgrootte() != null) {
                    existingKadastraalperceel.setAanduidingsoortgrootte(kadastraalperceel.getAanduidingsoortgrootte());
                }
                if (kadastraalperceel.getBegrenzingperceel() != null) {
                    existingKadastraalperceel.setBegrenzingperceel(kadastraalperceel.getBegrenzingperceel());
                }
                if (kadastraalperceel.getGrootteperceel() != null) {
                    existingKadastraalperceel.setGrootteperceel(kadastraalperceel.getGrootteperceel());
                }
                if (kadastraalperceel.getIndicatiedeelperceel() != null) {
                    existingKadastraalperceel.setIndicatiedeelperceel(kadastraalperceel.getIndicatiedeelperceel());
                }
                if (kadastraalperceel.getOmschrijvingdeelperceel() != null) {
                    existingKadastraalperceel.setOmschrijvingdeelperceel(kadastraalperceel.getOmschrijvingdeelperceel());
                }
                if (kadastraalperceel.getPlaatscoordinatenperceel() != null) {
                    existingKadastraalperceel.setPlaatscoordinatenperceel(kadastraalperceel.getPlaatscoordinatenperceel());
                }

                return existingKadastraalperceel;
            })
            .map(kadastraalperceelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastraalperceel.getId().toString())
        );
    }

    /**
     * {@code GET  /kadastraalperceels} : get all the kadastraalperceels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kadastraalperceels in body.
     */
    @GetMapping("")
    public List<Kadastraalperceel> getAllKadastraalperceels() {
        log.debug("REST request to get all Kadastraalperceels");
        return kadastraalperceelRepository.findAll();
    }

    /**
     * {@code GET  /kadastraalperceels/:id} : get the "id" kadastraalperceel.
     *
     * @param id the id of the kadastraalperceel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kadastraalperceel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kadastraalperceel> getKadastraalperceel(@PathVariable("id") Long id) {
        log.debug("REST request to get Kadastraalperceel : {}", id);
        Optional<Kadastraalperceel> kadastraalperceel = kadastraalperceelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kadastraalperceel);
    }

    /**
     * {@code DELETE  /kadastraalperceels/:id} : delete the "id" kadastraalperceel.
     *
     * @param id the id of the kadastraalperceel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKadastraalperceel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kadastraalperceel : {}", id);
        kadastraalperceelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
