package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vlak;
import nl.ritense.demo.repository.VlakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vlak}.
 */
@RestController
@RequestMapping("/api/vlaks")
@Transactional
public class VlakResource {

    private final Logger log = LoggerFactory.getLogger(VlakResource.class);

    private static final String ENTITY_NAME = "vlak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VlakRepository vlakRepository;

    public VlakResource(VlakRepository vlakRepository) {
        this.vlakRepository = vlakRepository;
    }

    /**
     * {@code POST  /vlaks} : Create a new vlak.
     *
     * @param vlak the vlak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vlak, or with status {@code 400 (Bad Request)} if the vlak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vlak> createVlak(@Valid @RequestBody Vlak vlak) throws URISyntaxException {
        log.debug("REST request to save Vlak : {}", vlak);
        if (vlak.getId() != null) {
            throw new BadRequestAlertException("A new vlak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vlak = vlakRepository.save(vlak);
        return ResponseEntity.created(new URI("/api/vlaks/" + vlak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vlak.getId().toString()))
            .body(vlak);
    }

    /**
     * {@code PUT  /vlaks/:id} : Updates an existing vlak.
     *
     * @param id the id of the vlak to save.
     * @param vlak the vlak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vlak,
     * or with status {@code 400 (Bad Request)} if the vlak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vlak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vlak> updateVlak(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Vlak vlak)
        throws URISyntaxException {
        log.debug("REST request to update Vlak : {}, {}", id, vlak);
        if (vlak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vlak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vlakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vlak = vlakRepository.save(vlak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vlak.getId().toString()))
            .body(vlak);
    }

    /**
     * {@code PATCH  /vlaks/:id} : Partial updates given fields of an existing vlak, field will ignore if it is null
     *
     * @param id the id of the vlak to save.
     * @param vlak the vlak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vlak,
     * or with status {@code 400 (Bad Request)} if the vlak is not valid,
     * or with status {@code 404 (Not Found)} if the vlak is not found,
     * or with status {@code 500 (Internal Server Error)} if the vlak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vlak> partialUpdateVlak(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vlak vlak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vlak partially : {}, {}", id, vlak);
        if (vlak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vlak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vlakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vlak> result = vlakRepository
            .findById(vlak.getId())
            .map(existingVlak -> {
                if (vlak.getDieptetot() != null) {
                    existingVlak.setDieptetot(vlak.getDieptetot());
                }
                if (vlak.getDieptevan() != null) {
                    existingVlak.setDieptevan(vlak.getDieptevan());
                }
                if (vlak.getKey() != null) {
                    existingVlak.setKey(vlak.getKey());
                }
                if (vlak.getKeyput() != null) {
                    existingVlak.setKeyput(vlak.getKeyput());
                }
                if (vlak.getProjectcd() != null) {
                    existingVlak.setProjectcd(vlak.getProjectcd());
                }
                if (vlak.getPutnummer() != null) {
                    existingVlak.setPutnummer(vlak.getPutnummer());
                }
                if (vlak.getVlaknummer() != null) {
                    existingVlak.setVlaknummer(vlak.getVlaknummer());
                }

                return existingVlak;
            })
            .map(vlakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vlak.getId().toString())
        );
    }

    /**
     * {@code GET  /vlaks} : get all the vlaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vlaks in body.
     */
    @GetMapping("")
    public List<Vlak> getAllVlaks() {
        log.debug("REST request to get all Vlaks");
        return vlakRepository.findAll();
    }

    /**
     * {@code GET  /vlaks/:id} : get the "id" vlak.
     *
     * @param id the id of the vlak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vlak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vlak> getVlak(@PathVariable("id") Long id) {
        log.debug("REST request to get Vlak : {}", id);
        Optional<Vlak> vlak = vlakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vlak);
    }

    /**
     * {@code DELETE  /vlaks/:id} : delete the "id" vlak.
     *
     * @param id the id of the vlak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVlak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vlak : {}", id);
        vlakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
