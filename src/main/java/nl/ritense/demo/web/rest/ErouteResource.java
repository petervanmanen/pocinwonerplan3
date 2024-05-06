package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Eroute;
import nl.ritense.demo.repository.ErouteRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eroute}.
 */
@RestController
@RequestMapping("/api/eroutes")
@Transactional
public class ErouteResource {

    private final Logger log = LoggerFactory.getLogger(ErouteResource.class);

    private static final String ENTITY_NAME = "eroute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ErouteRepository erouteRepository;

    public ErouteResource(ErouteRepository erouteRepository) {
        this.erouteRepository = erouteRepository;
    }

    /**
     * {@code POST  /eroutes} : Create a new eroute.
     *
     * @param eroute the eroute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eroute, or with status {@code 400 (Bad Request)} if the eroute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eroute> createEroute(@RequestBody Eroute eroute) throws URISyntaxException {
        log.debug("REST request to save Eroute : {}", eroute);
        if (eroute.getId() != null) {
            throw new BadRequestAlertException("A new eroute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eroute = erouteRepository.save(eroute);
        return ResponseEntity.created(new URI("/api/eroutes/" + eroute.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eroute.getId().toString()))
            .body(eroute);
    }

    /**
     * {@code PUT  /eroutes/:id} : Updates an existing eroute.
     *
     * @param id the id of the eroute to save.
     * @param eroute the eroute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eroute,
     * or with status {@code 400 (Bad Request)} if the eroute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eroute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Eroute> updateEroute(@PathVariable(value = "id", required = false) final Long id, @RequestBody Eroute eroute)
        throws URISyntaxException {
        log.debug("REST request to update Eroute : {}, {}", id, eroute);
        if (eroute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eroute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!erouteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eroute = erouteRepository.save(eroute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eroute.getId().toString()))
            .body(eroute);
    }

    /**
     * {@code PATCH  /eroutes/:id} : Partial updates given fields of an existing eroute, field will ignore if it is null
     *
     * @param id the id of the eroute to save.
     * @param eroute the eroute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eroute,
     * or with status {@code 400 (Bad Request)} if the eroute is not valid,
     * or with status {@code 404 (Not Found)} if the eroute is not found,
     * or with status {@code 500 (Internal Server Error)} if the eroute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Eroute> partialUpdateEroute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Eroute eroute
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eroute partially : {}, {}", id, eroute);
        if (eroute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eroute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!erouteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Eroute> result = erouteRepository
            .findById(eroute.getId())
            .map(existingEroute -> {
                if (eroute.getGeometrie() != null) {
                    existingEroute.setGeometrie(eroute.getGeometrie());
                }
                if (eroute.getEroutecode() != null) {
                    existingEroute.setEroutecode(eroute.getEroutecode());
                }
                if (eroute.getEroutesoort() != null) {
                    existingEroute.setEroutesoort(eroute.getEroutesoort());
                }

                return existingEroute;
            })
            .map(erouteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eroute.getId().toString())
        );
    }

    /**
     * {@code GET  /eroutes} : get all the eroutes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eroutes in body.
     */
    @GetMapping("")
    public List<Eroute> getAllEroutes() {
        log.debug("REST request to get all Eroutes");
        return erouteRepository.findAll();
    }

    /**
     * {@code GET  /eroutes/:id} : get the "id" eroute.
     *
     * @param id the id of the eroute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eroute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eroute> getEroute(@PathVariable("id") Long id) {
        log.debug("REST request to get Eroute : {}", id);
        Optional<Eroute> eroute = erouteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eroute);
    }

    /**
     * {@code DELETE  /eroutes/:id} : delete the "id" eroute.
     *
     * @param id the id of the eroute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEroute(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eroute : {}", id);
        erouteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
