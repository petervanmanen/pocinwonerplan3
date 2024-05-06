package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vthmelding;
import nl.ritense.demo.repository.VthmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vthmelding}.
 */
@RestController
@RequestMapping("/api/vthmeldings")
@Transactional
public class VthmeldingResource {

    private final Logger log = LoggerFactory.getLogger(VthmeldingResource.class);

    private static final String ENTITY_NAME = "vthmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VthmeldingRepository vthmeldingRepository;

    public VthmeldingResource(VthmeldingRepository vthmeldingRepository) {
        this.vthmeldingRepository = vthmeldingRepository;
    }

    /**
     * {@code POST  /vthmeldings} : Create a new vthmelding.
     *
     * @param vthmelding the vthmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vthmelding, or with status {@code 400 (Bad Request)} if the vthmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vthmelding> createVthmelding(@Valid @RequestBody Vthmelding vthmelding) throws URISyntaxException {
        log.debug("REST request to save Vthmelding : {}", vthmelding);
        if (vthmelding.getId() != null) {
            throw new BadRequestAlertException("A new vthmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vthmelding = vthmeldingRepository.save(vthmelding);
        return ResponseEntity.created(new URI("/api/vthmeldings/" + vthmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vthmelding.getId().toString()))
            .body(vthmelding);
    }

    /**
     * {@code PUT  /vthmeldings/:id} : Updates an existing vthmelding.
     *
     * @param id the id of the vthmelding to save.
     * @param vthmelding the vthmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vthmelding,
     * or with status {@code 400 (Bad Request)} if the vthmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vthmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vthmelding> updateVthmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vthmelding vthmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Vthmelding : {}, {}", id, vthmelding);
        if (vthmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vthmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vthmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vthmelding = vthmeldingRepository.save(vthmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vthmelding.getId().toString()))
            .body(vthmelding);
    }

    /**
     * {@code PATCH  /vthmeldings/:id} : Partial updates given fields of an existing vthmelding, field will ignore if it is null
     *
     * @param id the id of the vthmelding to save.
     * @param vthmelding the vthmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vthmelding,
     * or with status {@code 400 (Bad Request)} if the vthmelding is not valid,
     * or with status {@code 404 (Not Found)} if the vthmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the vthmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vthmelding> partialUpdateVthmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vthmelding vthmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vthmelding partially : {}, {}", id, vthmelding);
        if (vthmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vthmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vthmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vthmelding> result = vthmeldingRepository
            .findById(vthmelding.getId())
            .map(existingVthmelding -> {
                if (vthmelding.getActiviteit() != null) {
                    existingVthmelding.setActiviteit(vthmelding.getActiviteit());
                }
                if (vthmelding.getBeoordeling() != null) {
                    existingVthmelding.setBeoordeling(vthmelding.getBeoordeling());
                }
                if (vthmelding.getDatumseponering() != null) {
                    existingVthmelding.setDatumseponering(vthmelding.getDatumseponering());
                }
                if (vthmelding.getDatumtijdtot() != null) {
                    existingVthmelding.setDatumtijdtot(vthmelding.getDatumtijdtot());
                }
                if (vthmelding.getGeseponeerd() != null) {
                    existingVthmelding.setGeseponeerd(vthmelding.getGeseponeerd());
                }
                if (vthmelding.getLocatie() != null) {
                    existingVthmelding.setLocatie(vthmelding.getLocatie());
                }
                if (vthmelding.getOrganisatieonderdeel() != null) {
                    existingVthmelding.setOrganisatieonderdeel(vthmelding.getOrganisatieonderdeel());
                }
                if (vthmelding.getOvertredingscode() != null) {
                    existingVthmelding.setOvertredingscode(vthmelding.getOvertredingscode());
                }
                if (vthmelding.getOvertredingsgroep() != null) {
                    existingVthmelding.setOvertredingsgroep(vthmelding.getOvertredingsgroep());
                }
                if (vthmelding.getReferentienummer() != null) {
                    existingVthmelding.setReferentienummer(vthmelding.getReferentienummer());
                }
                if (vthmelding.getResultaat() != null) {
                    existingVthmelding.setResultaat(vthmelding.getResultaat());
                }
                if (vthmelding.getSoortvthmelding() != null) {
                    existingVthmelding.setSoortvthmelding(vthmelding.getSoortvthmelding());
                }
                if (vthmelding.getStatus() != null) {
                    existingVthmelding.setStatus(vthmelding.getStatus());
                }
                if (vthmelding.getStraatnaam() != null) {
                    existingVthmelding.setStraatnaam(vthmelding.getStraatnaam());
                }
                if (vthmelding.getTaaktype() != null) {
                    existingVthmelding.setTaaktype(vthmelding.getTaaktype());
                }
                if (vthmelding.getZaaknummer() != null) {
                    existingVthmelding.setZaaknummer(vthmelding.getZaaknummer());
                }

                return existingVthmelding;
            })
            .map(vthmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vthmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /vthmeldings} : get all the vthmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vthmeldings in body.
     */
    @GetMapping("")
    public List<Vthmelding> getAllVthmeldings() {
        log.debug("REST request to get all Vthmeldings");
        return vthmeldingRepository.findAll();
    }

    /**
     * {@code GET  /vthmeldings/:id} : get the "id" vthmelding.
     *
     * @param id the id of the vthmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vthmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vthmelding> getVthmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Vthmelding : {}", id);
        Optional<Vthmelding> vthmelding = vthmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vthmelding);
    }

    /**
     * {@code DELETE  /vthmeldings/:id} : delete the "id" vthmelding.
     *
     * @param id the id of the vthmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVthmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vthmelding : {}", id);
        vthmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
