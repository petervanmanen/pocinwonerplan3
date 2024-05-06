package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kadastraleonroerendezaakaantekening;
import nl.ritense.demo.repository.KadastraleonroerendezaakaantekeningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kadastraleonroerendezaakaantekening}.
 */
@RestController
@RequestMapping("/api/kadastraleonroerendezaakaantekenings")
@Transactional
public class KadastraleonroerendezaakaantekeningResource {

    private final Logger log = LoggerFactory.getLogger(KadastraleonroerendezaakaantekeningResource.class);

    private static final String ENTITY_NAME = "kadastraleonroerendezaakaantekening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KadastraleonroerendezaakaantekeningRepository kadastraleonroerendezaakaantekeningRepository;

    public KadastraleonroerendezaakaantekeningResource(
        KadastraleonroerendezaakaantekeningRepository kadastraleonroerendezaakaantekeningRepository
    ) {
        this.kadastraleonroerendezaakaantekeningRepository = kadastraleonroerendezaakaantekeningRepository;
    }

    /**
     * {@code POST  /kadastraleonroerendezaakaantekenings} : Create a new kadastraleonroerendezaakaantekening.
     *
     * @param kadastraleonroerendezaakaantekening the kadastraleonroerendezaakaantekening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kadastraleonroerendezaakaantekening, or with status {@code 400 (Bad Request)} if the kadastraleonroerendezaakaantekening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kadastraleonroerendezaakaantekening> createKadastraleonroerendezaakaantekening(
        @RequestBody Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening
    ) throws URISyntaxException {
        log.debug("REST request to save Kadastraleonroerendezaakaantekening : {}", kadastraleonroerendezaakaantekening);
        if (kadastraleonroerendezaakaantekening.getId() != null) {
            throw new BadRequestAlertException(
                "A new kadastraleonroerendezaakaantekening cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        kadastraleonroerendezaakaantekening = kadastraleonroerendezaakaantekeningRepository.save(kadastraleonroerendezaakaantekening);
        return ResponseEntity.created(new URI("/api/kadastraleonroerendezaakaantekenings/" + kadastraleonroerendezaakaantekening.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    kadastraleonroerendezaakaantekening.getId().toString()
                )
            )
            .body(kadastraleonroerendezaakaantekening);
    }

    /**
     * {@code PUT  /kadastraleonroerendezaakaantekenings/:id} : Updates an existing kadastraleonroerendezaakaantekening.
     *
     * @param id the id of the kadastraleonroerendezaakaantekening to save.
     * @param kadastraleonroerendezaakaantekening the kadastraleonroerendezaakaantekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastraleonroerendezaakaantekening,
     * or with status {@code 400 (Bad Request)} if the kadastraleonroerendezaakaantekening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kadastraleonroerendezaakaantekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kadastraleonroerendezaakaantekening> updateKadastraleonroerendezaakaantekening(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening
    ) throws URISyntaxException {
        log.debug("REST request to update Kadastraleonroerendezaakaantekening : {}, {}", id, kadastraleonroerendezaakaantekening);
        if (kadastraleonroerendezaakaantekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastraleonroerendezaakaantekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastraleonroerendezaakaantekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kadastraleonroerendezaakaantekening = kadastraleonroerendezaakaantekeningRepository.save(kadastraleonroerendezaakaantekening);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    kadastraleonroerendezaakaantekening.getId().toString()
                )
            )
            .body(kadastraleonroerendezaakaantekening);
    }

    /**
     * {@code PATCH  /kadastraleonroerendezaakaantekenings/:id} : Partial updates given fields of an existing kadastraleonroerendezaakaantekening, field will ignore if it is null
     *
     * @param id the id of the kadastraleonroerendezaakaantekening to save.
     * @param kadastraleonroerendezaakaantekening the kadastraleonroerendezaakaantekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastraleonroerendezaakaantekening,
     * or with status {@code 400 (Bad Request)} if the kadastraleonroerendezaakaantekening is not valid,
     * or with status {@code 404 (Not Found)} if the kadastraleonroerendezaakaantekening is not found,
     * or with status {@code 500 (Internal Server Error)} if the kadastraleonroerendezaakaantekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kadastraleonroerendezaakaantekening> partialUpdateKadastraleonroerendezaakaantekening(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Kadastraleonroerendezaakaantekening partially : {}, {}",
            id,
            kadastraleonroerendezaakaantekening
        );
        if (kadastraleonroerendezaakaantekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastraleonroerendezaakaantekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastraleonroerendezaakaantekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kadastraleonroerendezaakaantekening> result = kadastraleonroerendezaakaantekeningRepository
            .findById(kadastraleonroerendezaakaantekening.getId())
            .map(existingKadastraleonroerendezaakaantekening -> {
                if (kadastraleonroerendezaakaantekening.getAardaantekeningkadastraalobject() != null) {
                    existingKadastraleonroerendezaakaantekening.setAardaantekeningkadastraalobject(
                        kadastraleonroerendezaakaantekening.getAardaantekeningkadastraalobject()
                    );
                }
                if (kadastraleonroerendezaakaantekening.getBeschrijvingaantekeningkadastraalobject() != null) {
                    existingKadastraleonroerendezaakaantekening.setBeschrijvingaantekeningkadastraalobject(
                        kadastraleonroerendezaakaantekening.getBeschrijvingaantekeningkadastraalobject()
                    );
                }
                if (kadastraleonroerendezaakaantekening.getDatumbeginaantekeningkadastraalobject() != null) {
                    existingKadastraleonroerendezaakaantekening.setDatumbeginaantekeningkadastraalobject(
                        kadastraleonroerendezaakaantekening.getDatumbeginaantekeningkadastraalobject()
                    );
                }
                if (kadastraleonroerendezaakaantekening.getDatumeindeaantekeningkadastraalobject() != null) {
                    existingKadastraleonroerendezaakaantekening.setDatumeindeaantekeningkadastraalobject(
                        kadastraleonroerendezaakaantekening.getDatumeindeaantekeningkadastraalobject()
                    );
                }
                if (kadastraleonroerendezaakaantekening.getKadasteridentificatieaantekening() != null) {
                    existingKadastraleonroerendezaakaantekening.setKadasteridentificatieaantekening(
                        kadastraleonroerendezaakaantekening.getKadasteridentificatieaantekening()
                    );
                }

                return existingKadastraleonroerendezaakaantekening;
            })
            .map(kadastraleonroerendezaakaantekeningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastraleonroerendezaakaantekening.getId().toString())
        );
    }

    /**
     * {@code GET  /kadastraleonroerendezaakaantekenings} : get all the kadastraleonroerendezaakaantekenings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kadastraleonroerendezaakaantekenings in body.
     */
    @GetMapping("")
    public List<Kadastraleonroerendezaakaantekening> getAllKadastraleonroerendezaakaantekenings() {
        log.debug("REST request to get all Kadastraleonroerendezaakaantekenings");
        return kadastraleonroerendezaakaantekeningRepository.findAll();
    }

    /**
     * {@code GET  /kadastraleonroerendezaakaantekenings/:id} : get the "id" kadastraleonroerendezaakaantekening.
     *
     * @param id the id of the kadastraleonroerendezaakaantekening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kadastraleonroerendezaakaantekening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kadastraleonroerendezaakaantekening> getKadastraleonroerendezaakaantekening(@PathVariable("id") Long id) {
        log.debug("REST request to get Kadastraleonroerendezaakaantekening : {}", id);
        Optional<Kadastraleonroerendezaakaantekening> kadastraleonroerendezaakaantekening =
            kadastraleonroerendezaakaantekeningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kadastraleonroerendezaakaantekening);
    }

    /**
     * {@code DELETE  /kadastraleonroerendezaakaantekenings/:id} : delete the "id" kadastraleonroerendezaakaantekening.
     *
     * @param id the id of the kadastraleonroerendezaakaantekening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKadastraleonroerendezaakaantekening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kadastraleonroerendezaakaantekening : {}", id);
        kadastraleonroerendezaakaantekeningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
