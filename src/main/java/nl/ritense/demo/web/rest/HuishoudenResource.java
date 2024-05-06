package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Huishouden;
import nl.ritense.demo.repository.HuishoudenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Huishouden}.
 */
@RestController
@RequestMapping("/api/huishoudens")
@Transactional
public class HuishoudenResource {

    private final Logger log = LoggerFactory.getLogger(HuishoudenResource.class);

    private static final String ENTITY_NAME = "huishouden";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HuishoudenRepository huishoudenRepository;

    public HuishoudenResource(HuishoudenRepository huishoudenRepository) {
        this.huishoudenRepository = huishoudenRepository;
    }

    /**
     * {@code POST  /huishoudens} : Create a new huishouden.
     *
     * @param huishouden the huishouden to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new huishouden, or with status {@code 400 (Bad Request)} if the huishouden has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Huishouden> createHuishouden(@Valid @RequestBody Huishouden huishouden) throws URISyntaxException {
        log.debug("REST request to save Huishouden : {}", huishouden);
        if (huishouden.getId() != null) {
            throw new BadRequestAlertException("A new huishouden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        huishouden = huishoudenRepository.save(huishouden);
        return ResponseEntity.created(new URI("/api/huishoudens/" + huishouden.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, huishouden.getId().toString()))
            .body(huishouden);
    }

    /**
     * {@code PUT  /huishoudens/:id} : Updates an existing huishouden.
     *
     * @param id the id of the huishouden to save.
     * @param huishouden the huishouden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated huishouden,
     * or with status {@code 400 (Bad Request)} if the huishouden is not valid,
     * or with status {@code 500 (Internal Server Error)} if the huishouden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Huishouden> updateHuishouden(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Huishouden huishouden
    ) throws URISyntaxException {
        log.debug("REST request to update Huishouden : {}, {}", id, huishouden);
        if (huishouden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, huishouden.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!huishoudenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        huishouden = huishoudenRepository.save(huishouden);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, huishouden.getId().toString()))
            .body(huishouden);
    }

    /**
     * {@code PATCH  /huishoudens/:id} : Partial updates given fields of an existing huishouden, field will ignore if it is null
     *
     * @param id the id of the huishouden to save.
     * @param huishouden the huishouden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated huishouden,
     * or with status {@code 400 (Bad Request)} if the huishouden is not valid,
     * or with status {@code 404 (Not Found)} if the huishouden is not found,
     * or with status {@code 500 (Internal Server Error)} if the huishouden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Huishouden> partialUpdateHuishouden(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Huishouden huishouden
    ) throws URISyntaxException {
        log.debug("REST request to partial update Huishouden partially : {}, {}", id, huishouden);
        if (huishouden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, huishouden.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!huishoudenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Huishouden> result = huishoudenRepository.findById(huishouden.getId()).map(huishoudenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, huishouden.getId().toString())
        );
    }

    /**
     * {@code GET  /huishoudens} : get all the huishoudens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of huishoudens in body.
     */
    @GetMapping("")
    public List<Huishouden> getAllHuishoudens() {
        log.debug("REST request to get all Huishoudens");
        return huishoudenRepository.findAll();
    }

    /**
     * {@code GET  /huishoudens/:id} : get the "id" huishouden.
     *
     * @param id the id of the huishouden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the huishouden, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Huishouden> getHuishouden(@PathVariable("id") Long id) {
        log.debug("REST request to get Huishouden : {}", id);
        Optional<Huishouden> huishouden = huishoudenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(huishouden);
    }

    /**
     * {@code DELETE  /huishoudens/:id} : delete the "id" huishouden.
     *
     * @param id the id of the huishouden to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHuishouden(@PathVariable("id") Long id) {
        log.debug("REST request to delete Huishouden : {}", id);
        huishoudenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
