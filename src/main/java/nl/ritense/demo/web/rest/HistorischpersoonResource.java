package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Historischpersoon;
import nl.ritense.demo.repository.HistorischpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Historischpersoon}.
 */
@RestController
@RequestMapping("/api/historischpersoons")
@Transactional
public class HistorischpersoonResource {

    private final Logger log = LoggerFactory.getLogger(HistorischpersoonResource.class);

    private static final String ENTITY_NAME = "historischpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistorischpersoonRepository historischpersoonRepository;

    public HistorischpersoonResource(HistorischpersoonRepository historischpersoonRepository) {
        this.historischpersoonRepository = historischpersoonRepository;
    }

    /**
     * {@code POST  /historischpersoons} : Create a new historischpersoon.
     *
     * @param historischpersoon the historischpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historischpersoon, or with status {@code 400 (Bad Request)} if the historischpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Historischpersoon> createHistorischpersoon(@RequestBody Historischpersoon historischpersoon)
        throws URISyntaxException {
        log.debug("REST request to save Historischpersoon : {}", historischpersoon);
        if (historischpersoon.getId() != null) {
            throw new BadRequestAlertException("A new historischpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        historischpersoon = historischpersoonRepository.save(historischpersoon);
        return ResponseEntity.created(new URI("/api/historischpersoons/" + historischpersoon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, historischpersoon.getId().toString()))
            .body(historischpersoon);
    }

    /**
     * {@code PUT  /historischpersoons/:id} : Updates an existing historischpersoon.
     *
     * @param id the id of the historischpersoon to save.
     * @param historischpersoon the historischpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historischpersoon,
     * or with status {@code 400 (Bad Request)} if the historischpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historischpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Historischpersoon> updateHistorischpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Historischpersoon historischpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Historischpersoon : {}, {}", id, historischpersoon);
        if (historischpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historischpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historischpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        historischpersoon = historischpersoonRepository.save(historischpersoon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historischpersoon.getId().toString()))
            .body(historischpersoon);
    }

    /**
     * {@code PATCH  /historischpersoons/:id} : Partial updates given fields of an existing historischpersoon, field will ignore if it is null
     *
     * @param id the id of the historischpersoon to save.
     * @param historischpersoon the historischpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historischpersoon,
     * or with status {@code 400 (Bad Request)} if the historischpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the historischpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the historischpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Historischpersoon> partialUpdateHistorischpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Historischpersoon historischpersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Historischpersoon partially : {}, {}", id, historischpersoon);
        if (historischpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historischpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historischpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Historischpersoon> result = historischpersoonRepository
            .findById(historischpersoon.getId())
            .map(existingHistorischpersoon -> {
                if (historischpersoon.getBeroep() != null) {
                    existingHistorischpersoon.setBeroep(historischpersoon.getBeroep());
                }
                if (historischpersoon.getDatumgeboorte() != null) {
                    existingHistorischpersoon.setDatumgeboorte(historischpersoon.getDatumgeboorte());
                }
                if (historischpersoon.getDatumoverlijden() != null) {
                    existingHistorischpersoon.setDatumoverlijden(historischpersoon.getDatumoverlijden());
                }
                if (historischpersoon.getNaam() != null) {
                    existingHistorischpersoon.setNaam(historischpersoon.getNaam());
                }
                if (historischpersoon.getOmschrijving() != null) {
                    existingHistorischpersoon.setOmschrijving(historischpersoon.getOmschrijving());
                }
                if (historischpersoon.getPubliektoegankelijk() != null) {
                    existingHistorischpersoon.setPubliektoegankelijk(historischpersoon.getPubliektoegankelijk());
                }
                if (historischpersoon.getWoondeop() != null) {
                    existingHistorischpersoon.setWoondeop(historischpersoon.getWoondeop());
                }

                return existingHistorischpersoon;
            })
            .map(historischpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historischpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /historischpersoons} : get all the historischpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historischpersoons in body.
     */
    @GetMapping("")
    public List<Historischpersoon> getAllHistorischpersoons() {
        log.debug("REST request to get all Historischpersoons");
        return historischpersoonRepository.findAll();
    }

    /**
     * {@code GET  /historischpersoons/:id} : get the "id" historischpersoon.
     *
     * @param id the id of the historischpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historischpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Historischpersoon> getHistorischpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Historischpersoon : {}", id);
        Optional<Historischpersoon> historischpersoon = historischpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(historischpersoon);
    }

    /**
     * {@code DELETE  /historischpersoons/:id} : delete the "id" historischpersoon.
     *
     * @param id the id of the historischpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorischpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Historischpersoon : {}", id);
        historischpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
