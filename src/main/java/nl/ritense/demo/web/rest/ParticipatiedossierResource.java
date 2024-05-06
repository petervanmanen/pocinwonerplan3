package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Participatiedossier;
import nl.ritense.demo.repository.ParticipatiedossierRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Participatiedossier}.
 */
@RestController
@RequestMapping("/api/participatiedossiers")
@Transactional
public class ParticipatiedossierResource {

    private final Logger log = LoggerFactory.getLogger(ParticipatiedossierResource.class);

    private static final String ENTITY_NAME = "participatiedossier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipatiedossierRepository participatiedossierRepository;

    public ParticipatiedossierResource(ParticipatiedossierRepository participatiedossierRepository) {
        this.participatiedossierRepository = participatiedossierRepository;
    }

    /**
     * {@code POST  /participatiedossiers} : Create a new participatiedossier.
     *
     * @param participatiedossier the participatiedossier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participatiedossier, or with status {@code 400 (Bad Request)} if the participatiedossier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Participatiedossier> createParticipatiedossier(@Valid @RequestBody Participatiedossier participatiedossier)
        throws URISyntaxException {
        log.debug("REST request to save Participatiedossier : {}", participatiedossier);
        if (participatiedossier.getId() != null) {
            throw new BadRequestAlertException("A new participatiedossier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        participatiedossier = participatiedossierRepository.save(participatiedossier);
        return ResponseEntity.created(new URI("/api/participatiedossiers/" + participatiedossier.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, participatiedossier.getId().toString()))
            .body(participatiedossier);
    }

    /**
     * {@code PUT  /participatiedossiers/:id} : Updates an existing participatiedossier.
     *
     * @param id the id of the participatiedossier to save.
     * @param participatiedossier the participatiedossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participatiedossier,
     * or with status {@code 400 (Bad Request)} if the participatiedossier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participatiedossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Participatiedossier> updateParticipatiedossier(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Participatiedossier participatiedossier
    ) throws URISyntaxException {
        log.debug("REST request to update Participatiedossier : {}, {}", id, participatiedossier);
        if (participatiedossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participatiedossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participatiedossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        participatiedossier = participatiedossierRepository.save(participatiedossier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, participatiedossier.getId().toString()))
            .body(participatiedossier);
    }

    /**
     * {@code PATCH  /participatiedossiers/:id} : Partial updates given fields of an existing participatiedossier, field will ignore if it is null
     *
     * @param id the id of the participatiedossier to save.
     * @param participatiedossier the participatiedossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participatiedossier,
     * or with status {@code 400 (Bad Request)} if the participatiedossier is not valid,
     * or with status {@code 404 (Not Found)} if the participatiedossier is not found,
     * or with status {@code 500 (Internal Server Error)} if the participatiedossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Participatiedossier> partialUpdateParticipatiedossier(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Participatiedossier participatiedossier
    ) throws URISyntaxException {
        log.debug("REST request to partial update Participatiedossier partially : {}, {}", id, participatiedossier);
        if (participatiedossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participatiedossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participatiedossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Participatiedossier> result = participatiedossierRepository
            .findById(participatiedossier.getId())
            .map(existingParticipatiedossier -> {
                if (participatiedossier.getArbeidsvermogen() != null) {
                    existingParticipatiedossier.setArbeidsvermogen(participatiedossier.getArbeidsvermogen());
                }
                if (participatiedossier.getBegeleiderscode() != null) {
                    existingParticipatiedossier.setBegeleiderscode(participatiedossier.getBegeleiderscode());
                }
                if (participatiedossier.getBeschutwerk() != null) {
                    existingParticipatiedossier.setBeschutwerk(participatiedossier.getBeschutwerk());
                }
                if (participatiedossier.getClientbegeleiderid() != null) {
                    existingParticipatiedossier.setClientbegeleiderid(participatiedossier.getClientbegeleiderid());
                }
                if (participatiedossier.getDatumeindebegeleiding() != null) {
                    existingParticipatiedossier.setDatumeindebegeleiding(participatiedossier.getDatumeindebegeleiding());
                }
                if (participatiedossier.getDatumstartbegeleiding() != null) {
                    existingParticipatiedossier.setDatumstartbegeleiding(participatiedossier.getDatumstartbegeleiding());
                }
                if (participatiedossier.getIndicatiedoelgroepregister() != null) {
                    existingParticipatiedossier.setIndicatiedoelgroepregister(participatiedossier.getIndicatiedoelgroepregister());
                }

                return existingParticipatiedossier;
            })
            .map(participatiedossierRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, participatiedossier.getId().toString())
        );
    }

    /**
     * {@code GET  /participatiedossiers} : get all the participatiedossiers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participatiedossiers in body.
     */
    @GetMapping("")
    public List<Participatiedossier> getAllParticipatiedossiers(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftclient-is-null".equals(filter)) {
            log.debug("REST request to get all Participatiedossiers where heeftClient is null");
            return StreamSupport.stream(participatiedossierRepository.findAll().spliterator(), false)
                .filter(participatiedossier -> participatiedossier.getHeeftClient() == null)
                .toList();
        }
        log.debug("REST request to get all Participatiedossiers");
        return participatiedossierRepository.findAll();
    }

    /**
     * {@code GET  /participatiedossiers/:id} : get the "id" participatiedossier.
     *
     * @param id the id of the participatiedossier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participatiedossier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Participatiedossier> getParticipatiedossier(@PathVariable("id") Long id) {
        log.debug("REST request to get Participatiedossier : {}", id);
        Optional<Participatiedossier> participatiedossier = participatiedossierRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(participatiedossier);
    }

    /**
     * {@code DELETE  /participatiedossiers/:id} : delete the "id" participatiedossier.
     *
     * @param id the id of the participatiedossier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipatiedossier(@PathVariable("id") Long id) {
        log.debug("REST request to delete Participatiedossier : {}", id);
        participatiedossierRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
