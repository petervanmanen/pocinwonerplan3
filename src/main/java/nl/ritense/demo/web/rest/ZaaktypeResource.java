package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Zaaktype;
import nl.ritense.demo.repository.ZaaktypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Zaaktype}.
 */
@RestController
@RequestMapping("/api/zaaktypes")
@Transactional
public class ZaaktypeResource {

    private final Logger log = LoggerFactory.getLogger(ZaaktypeResource.class);

    private static final String ENTITY_NAME = "zaaktype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZaaktypeRepository zaaktypeRepository;

    public ZaaktypeResource(ZaaktypeRepository zaaktypeRepository) {
        this.zaaktypeRepository = zaaktypeRepository;
    }

    /**
     * {@code POST  /zaaktypes} : Create a new zaaktype.
     *
     * @param zaaktype the zaaktype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zaaktype, or with status {@code 400 (Bad Request)} if the zaaktype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Zaaktype> createZaaktype(@Valid @RequestBody Zaaktype zaaktype) throws URISyntaxException {
        log.debug("REST request to save Zaaktype : {}", zaaktype);
        if (zaaktype.getId() != null) {
            throw new BadRequestAlertException("A new zaaktype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zaaktype = zaaktypeRepository.save(zaaktype);
        return ResponseEntity.created(new URI("/api/zaaktypes/" + zaaktype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, zaaktype.getId().toString()))
            .body(zaaktype);
    }

    /**
     * {@code PUT  /zaaktypes/:id} : Updates an existing zaaktype.
     *
     * @param id the id of the zaaktype to save.
     * @param zaaktype the zaaktype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zaaktype,
     * or with status {@code 400 (Bad Request)} if the zaaktype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zaaktype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Zaaktype> updateZaaktype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Zaaktype zaaktype
    ) throws URISyntaxException {
        log.debug("REST request to update Zaaktype : {}, {}", id, zaaktype);
        if (zaaktype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zaaktype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zaaktypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        zaaktype = zaaktypeRepository.save(zaaktype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zaaktype.getId().toString()))
            .body(zaaktype);
    }

    /**
     * {@code PATCH  /zaaktypes/:id} : Partial updates given fields of an existing zaaktype, field will ignore if it is null
     *
     * @param id the id of the zaaktype to save.
     * @param zaaktype the zaaktype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zaaktype,
     * or with status {@code 400 (Bad Request)} if the zaaktype is not valid,
     * or with status {@code 404 (Not Found)} if the zaaktype is not found,
     * or with status {@code 500 (Internal Server Error)} if the zaaktype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Zaaktype> partialUpdateZaaktype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Zaaktype zaaktype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Zaaktype partially : {}, {}", id, zaaktype);
        if (zaaktype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zaaktype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zaaktypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Zaaktype> result = zaaktypeRepository
            .findById(zaaktype.getId())
            .map(existingZaaktype -> {
                if (zaaktype.getArchiefcode() != null) {
                    existingZaaktype.setArchiefcode(zaaktype.getArchiefcode());
                }
                if (zaaktype.getDatumbegingeldigheidzaaktype() != null) {
                    existingZaaktype.setDatumbegingeldigheidzaaktype(zaaktype.getDatumbegingeldigheidzaaktype());
                }
                if (zaaktype.getDatumeindegeldigheidzaaktype() != null) {
                    existingZaaktype.setDatumeindegeldigheidzaaktype(zaaktype.getDatumeindegeldigheidzaaktype());
                }
                if (zaaktype.getDoorlooptijdbehandeling() != null) {
                    existingZaaktype.setDoorlooptijdbehandeling(zaaktype.getDoorlooptijdbehandeling());
                }
                if (zaaktype.getIndicatiepublicatie() != null) {
                    existingZaaktype.setIndicatiepublicatie(zaaktype.getIndicatiepublicatie());
                }
                if (zaaktype.getPublicatietekst() != null) {
                    existingZaaktype.setPublicatietekst(zaaktype.getPublicatietekst());
                }
                if (zaaktype.getServicenormbehandeling() != null) {
                    existingZaaktype.setServicenormbehandeling(zaaktype.getServicenormbehandeling());
                }
                if (zaaktype.getTrefwoord() != null) {
                    existingZaaktype.setTrefwoord(zaaktype.getTrefwoord());
                }
                if (zaaktype.getVertrouwelijkaanduiding() != null) {
                    existingZaaktype.setVertrouwelijkaanduiding(zaaktype.getVertrouwelijkaanduiding());
                }
                if (zaaktype.getZaakcategorie() != null) {
                    existingZaaktype.setZaakcategorie(zaaktype.getZaakcategorie());
                }
                if (zaaktype.getZaaktypeomschrijving() != null) {
                    existingZaaktype.setZaaktypeomschrijving(zaaktype.getZaaktypeomschrijving());
                }
                if (zaaktype.getZaaktypeomschrijvinggeneriek() != null) {
                    existingZaaktype.setZaaktypeomschrijvinggeneriek(zaaktype.getZaaktypeomschrijvinggeneriek());
                }

                return existingZaaktype;
            })
            .map(zaaktypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zaaktype.getId().toString())
        );
    }

    /**
     * {@code GET  /zaaktypes} : get all the zaaktypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zaaktypes in body.
     */
    @GetMapping("")
    public List<Zaaktype> getAllZaaktypes() {
        log.debug("REST request to get all Zaaktypes");
        return zaaktypeRepository.findAll();
    }

    /**
     * {@code GET  /zaaktypes/:id} : get the "id" zaaktype.
     *
     * @param id the id of the zaaktype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zaaktype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Zaaktype> getZaaktype(@PathVariable("id") Long id) {
        log.debug("REST request to get Zaaktype : {}", id);
        Optional<Zaaktype> zaaktype = zaaktypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zaaktype);
    }

    /**
     * {@code DELETE  /zaaktypes/:id} : delete the "id" zaaktype.
     *
     * @param id the id of the zaaktype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZaaktype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Zaaktype : {}", id);
        zaaktypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
