package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Agendapunt;
import nl.ritense.demo.repository.AgendapuntRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Agendapunt}.
 */
@RestController
@RequestMapping("/api/agendapunts")
@Transactional
public class AgendapuntResource {

    private final Logger log = LoggerFactory.getLogger(AgendapuntResource.class);

    private static final String ENTITY_NAME = "agendapunt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendapuntRepository agendapuntRepository;

    public AgendapuntResource(AgendapuntRepository agendapuntRepository) {
        this.agendapuntRepository = agendapuntRepository;
    }

    /**
     * {@code POST  /agendapunts} : Create a new agendapunt.
     *
     * @param agendapunt the agendapunt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendapunt, or with status {@code 400 (Bad Request)} if the agendapunt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Agendapunt> createAgendapunt(@RequestBody Agendapunt agendapunt) throws URISyntaxException {
        log.debug("REST request to save Agendapunt : {}", agendapunt);
        if (agendapunt.getId() != null) {
            throw new BadRequestAlertException("A new agendapunt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        agendapunt = agendapuntRepository.save(agendapunt);
        return ResponseEntity.created(new URI("/api/agendapunts/" + agendapunt.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, agendapunt.getId().toString()))
            .body(agendapunt);
    }

    /**
     * {@code PUT  /agendapunts/:id} : Updates an existing agendapunt.
     *
     * @param id the id of the agendapunt to save.
     * @param agendapunt the agendapunt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendapunt,
     * or with status {@code 400 (Bad Request)} if the agendapunt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendapunt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Agendapunt> updateAgendapunt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Agendapunt agendapunt
    ) throws URISyntaxException {
        log.debug("REST request to update Agendapunt : {}, {}", id, agendapunt);
        if (agendapunt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendapunt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendapuntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agendapunt = agendapuntRepository.save(agendapunt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agendapunt.getId().toString()))
            .body(agendapunt);
    }

    /**
     * {@code PATCH  /agendapunts/:id} : Partial updates given fields of an existing agendapunt, field will ignore if it is null
     *
     * @param id the id of the agendapunt to save.
     * @param agendapunt the agendapunt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendapunt,
     * or with status {@code 400 (Bad Request)} if the agendapunt is not valid,
     * or with status {@code 404 (Not Found)} if the agendapunt is not found,
     * or with status {@code 500 (Internal Server Error)} if the agendapunt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Agendapunt> partialUpdateAgendapunt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Agendapunt agendapunt
    ) throws URISyntaxException {
        log.debug("REST request to partial update Agendapunt partially : {}, {}", id, agendapunt);
        if (agendapunt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendapunt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendapuntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Agendapunt> result = agendapuntRepository
            .findById(agendapunt.getId())
            .map(existingAgendapunt -> {
                if (agendapunt.getNummer() != null) {
                    existingAgendapunt.setNummer(agendapunt.getNummer());
                }
                if (agendapunt.getOmschrijving() != null) {
                    existingAgendapunt.setOmschrijving(agendapunt.getOmschrijving());
                }
                if (agendapunt.getTitel() != null) {
                    existingAgendapunt.setTitel(agendapunt.getTitel());
                }

                return existingAgendapunt;
            })
            .map(agendapuntRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agendapunt.getId().toString())
        );
    }

    /**
     * {@code GET  /agendapunts} : get all the agendapunts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendapunts in body.
     */
    @GetMapping("")
    public List<Agendapunt> getAllAgendapunts() {
        log.debug("REST request to get all Agendapunts");
        return agendapuntRepository.findAll();
    }

    /**
     * {@code GET  /agendapunts/:id} : get the "id" agendapunt.
     *
     * @param id the id of the agendapunt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendapunt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Agendapunt> getAgendapunt(@PathVariable("id") Long id) {
        log.debug("REST request to get Agendapunt : {}", id);
        Optional<Agendapunt> agendapunt = agendapuntRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agendapunt);
    }

    /**
     * {@code DELETE  /agendapunts/:id} : delete the "id" agendapunt.
     *
     * @param id the id of the agendapunt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendapunt(@PathVariable("id") Long id) {
        log.debug("REST request to delete Agendapunt : {}", id);
        agendapuntRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
