package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Tarief;
import nl.ritense.demo.repository.TariefRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Tarief}.
 */
@RestController
@RequestMapping("/api/tariefs")
@Transactional
public class TariefResource {

    private final Logger log = LoggerFactory.getLogger(TariefResource.class);

    private static final String ENTITY_NAME = "tarief";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TariefRepository tariefRepository;

    public TariefResource(TariefRepository tariefRepository) {
        this.tariefRepository = tariefRepository;
    }

    /**
     * {@code POST  /tariefs} : Create a new tarief.
     *
     * @param tarief the tarief to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarief, or with status {@code 400 (Bad Request)} if the tarief has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tarief> createTarief(@Valid @RequestBody Tarief tarief) throws URISyntaxException {
        log.debug("REST request to save Tarief : {}", tarief);
        if (tarief.getId() != null) {
            throw new BadRequestAlertException("A new tarief cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarief = tariefRepository.save(tarief);
        return ResponseEntity.created(new URI("/api/tariefs/" + tarief.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tarief.getId().toString()))
            .body(tarief);
    }

    /**
     * {@code PUT  /tariefs/:id} : Updates an existing tarief.
     *
     * @param id the id of the tarief to save.
     * @param tarief the tarief to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarief,
     * or with status {@code 400 (Bad Request)} if the tarief is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarief couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tarief> updateTarief(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tarief tarief
    ) throws URISyntaxException {
        log.debug("REST request to update Tarief : {}, {}", id, tarief);
        if (tarief.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarief.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tariefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarief = tariefRepository.save(tarief);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tarief.getId().toString()))
            .body(tarief);
    }

    /**
     * {@code PATCH  /tariefs/:id} : Partial updates given fields of an existing tarief, field will ignore if it is null
     *
     * @param id the id of the tarief to save.
     * @param tarief the tarief to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarief,
     * or with status {@code 400 (Bad Request)} if the tarief is not valid,
     * or with status {@code 404 (Not Found)} if the tarief is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarief couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tarief> partialUpdateTarief(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tarief tarief
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tarief partially : {}, {}", id, tarief);
        if (tarief.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarief.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tariefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tarief> result = tariefRepository
            .findById(tarief.getId())
            .map(existingTarief -> {
                if (tarief.getBedrag() != null) {
                    existingTarief.setBedrag(tarief.getBedrag());
                }
                if (tarief.getDatumeinde() != null) {
                    existingTarief.setDatumeinde(tarief.getDatumeinde());
                }
                if (tarief.getDatumstart() != null) {
                    existingTarief.setDatumstart(tarief.getDatumstart());
                }
                if (tarief.getEenheid() != null) {
                    existingTarief.setEenheid(tarief.getEenheid());
                }
                if (tarief.getWet() != null) {
                    existingTarief.setWet(tarief.getWet());
                }

                return existingTarief;
            })
            .map(tariefRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tarief.getId().toString())
        );
    }

    /**
     * {@code GET  /tariefs} : get all the tariefs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tariefs in body.
     */
    @GetMapping("")
    public List<Tarief> getAllTariefs() {
        log.debug("REST request to get all Tariefs");
        return tariefRepository.findAll();
    }

    /**
     * {@code GET  /tariefs/:id} : get the "id" tarief.
     *
     * @param id the id of the tarief to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarief, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tarief> getTarief(@PathVariable("id") Long id) {
        log.debug("REST request to get Tarief : {}", id);
        Optional<Tarief> tarief = tariefRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tarief);
    }

    /**
     * {@code DELETE  /tariefs/:id} : delete the "id" tarief.
     *
     * @param id the id of the tarief to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarief(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tarief : {}", id);
        tariefRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
