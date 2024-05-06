package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Werkorder;
import nl.ritense.demo.repository.WerkorderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Werkorder}.
 */
@RestController
@RequestMapping("/api/werkorders")
@Transactional
public class WerkorderResource {

    private final Logger log = LoggerFactory.getLogger(WerkorderResource.class);

    private static final String ENTITY_NAME = "werkorder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WerkorderRepository werkorderRepository;

    public WerkorderResource(WerkorderRepository werkorderRepository) {
        this.werkorderRepository = werkorderRepository;
    }

    /**
     * {@code POST  /werkorders} : Create a new werkorder.
     *
     * @param werkorder the werkorder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new werkorder, or with status {@code 400 (Bad Request)} if the werkorder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Werkorder> createWerkorder(@Valid @RequestBody Werkorder werkorder) throws URISyntaxException {
        log.debug("REST request to save Werkorder : {}", werkorder);
        if (werkorder.getId() != null) {
            throw new BadRequestAlertException("A new werkorder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        werkorder = werkorderRepository.save(werkorder);
        return ResponseEntity.created(new URI("/api/werkorders/" + werkorder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, werkorder.getId().toString()))
            .body(werkorder);
    }

    /**
     * {@code PUT  /werkorders/:id} : Updates an existing werkorder.
     *
     * @param id the id of the werkorder to save.
     * @param werkorder the werkorder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated werkorder,
     * or with status {@code 400 (Bad Request)} if the werkorder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the werkorder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Werkorder> updateWerkorder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Werkorder werkorder
    ) throws URISyntaxException {
        log.debug("REST request to update Werkorder : {}, {}", id, werkorder);
        if (werkorder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, werkorder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!werkorderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        werkorder = werkorderRepository.save(werkorder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, werkorder.getId().toString()))
            .body(werkorder);
    }

    /**
     * {@code PATCH  /werkorders/:id} : Partial updates given fields of an existing werkorder, field will ignore if it is null
     *
     * @param id the id of the werkorder to save.
     * @param werkorder the werkorder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated werkorder,
     * or with status {@code 400 (Bad Request)} if the werkorder is not valid,
     * or with status {@code 404 (Not Found)} if the werkorder is not found,
     * or with status {@code 500 (Internal Server Error)} if the werkorder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Werkorder> partialUpdateWerkorder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Werkorder werkorder
    ) throws URISyntaxException {
        log.debug("REST request to partial update Werkorder partially : {}, {}", id, werkorder);
        if (werkorder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, werkorder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!werkorderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Werkorder> result = werkorderRepository
            .findById(werkorder.getId())
            .map(existingWerkorder -> {
                if (werkorder.getCode() != null) {
                    existingWerkorder.setCode(werkorder.getCode());
                }
                if (werkorder.getDocumentnummer() != null) {
                    existingWerkorder.setDocumentnummer(werkorder.getDocumentnummer());
                }
                if (werkorder.getNaam() != null) {
                    existingWerkorder.setNaam(werkorder.getNaam());
                }
                if (werkorder.getOmschrijving() != null) {
                    existingWerkorder.setOmschrijving(werkorder.getOmschrijving());
                }
                if (werkorder.getWerkordertype() != null) {
                    existingWerkorder.setWerkordertype(werkorder.getWerkordertype());
                }

                return existingWerkorder;
            })
            .map(werkorderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, werkorder.getId().toString())
        );
    }

    /**
     * {@code GET  /werkorders} : get all the werkorders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of werkorders in body.
     */
    @GetMapping("")
    public List<Werkorder> getAllWerkorders() {
        log.debug("REST request to get all Werkorders");
        return werkorderRepository.findAll();
    }

    /**
     * {@code GET  /werkorders/:id} : get the "id" werkorder.
     *
     * @param id the id of the werkorder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the werkorder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Werkorder> getWerkorder(@PathVariable("id") Long id) {
        log.debug("REST request to get Werkorder : {}", id);
        Optional<Werkorder> werkorder = werkorderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(werkorder);
    }

    /**
     * {@code DELETE  /werkorders/:id} : delete the "id" werkorder.
     *
     * @param id the id of the werkorder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWerkorder(@PathVariable("id") Long id) {
        log.debug("REST request to delete Werkorder : {}", id);
        werkorderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
