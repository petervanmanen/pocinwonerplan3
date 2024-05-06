package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Besluittype;
import nl.ritense.demo.repository.BesluittypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Besluittype}.
 */
@RestController
@RequestMapping("/api/besluittypes")
@Transactional
public class BesluittypeResource {

    private final Logger log = LoggerFactory.getLogger(BesluittypeResource.class);

    private static final String ENTITY_NAME = "besluittype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BesluittypeRepository besluittypeRepository;

    public BesluittypeResource(BesluittypeRepository besluittypeRepository) {
        this.besluittypeRepository = besluittypeRepository;
    }

    /**
     * {@code POST  /besluittypes} : Create a new besluittype.
     *
     * @param besluittype the besluittype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new besluittype, or with status {@code 400 (Bad Request)} if the besluittype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Besluittype> createBesluittype(@Valid @RequestBody Besluittype besluittype) throws URISyntaxException {
        log.debug("REST request to save Besluittype : {}", besluittype);
        if (besluittype.getId() != null) {
            throw new BadRequestAlertException("A new besluittype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        besluittype = besluittypeRepository.save(besluittype);
        return ResponseEntity.created(new URI("/api/besluittypes/" + besluittype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, besluittype.getId().toString()))
            .body(besluittype);
    }

    /**
     * {@code PUT  /besluittypes/:id} : Updates an existing besluittype.
     *
     * @param id the id of the besluittype to save.
     * @param besluittype the besluittype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated besluittype,
     * or with status {@code 400 (Bad Request)} if the besluittype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the besluittype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Besluittype> updateBesluittype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Besluittype besluittype
    ) throws URISyntaxException {
        log.debug("REST request to update Besluittype : {}, {}", id, besluittype);
        if (besluittype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, besluittype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!besluittypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        besluittype = besluittypeRepository.save(besluittype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, besluittype.getId().toString()))
            .body(besluittype);
    }

    /**
     * {@code PATCH  /besluittypes/:id} : Partial updates given fields of an existing besluittype, field will ignore if it is null
     *
     * @param id the id of the besluittype to save.
     * @param besluittype the besluittype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated besluittype,
     * or with status {@code 400 (Bad Request)} if the besluittype is not valid,
     * or with status {@code 404 (Not Found)} if the besluittype is not found,
     * or with status {@code 500 (Internal Server Error)} if the besluittype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Besluittype> partialUpdateBesluittype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Besluittype besluittype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Besluittype partially : {}, {}", id, besluittype);
        if (besluittype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, besluittype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!besluittypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Besluittype> result = besluittypeRepository
            .findById(besluittype.getId())
            .map(existingBesluittype -> {
                if (besluittype.getBesluitcategorie() != null) {
                    existingBesluittype.setBesluitcategorie(besluittype.getBesluitcategorie());
                }
                if (besluittype.getBesluittypeomschrijving() != null) {
                    existingBesluittype.setBesluittypeomschrijving(besluittype.getBesluittypeomschrijving());
                }
                if (besluittype.getBesluittypeomschrijvinggeneriek() != null) {
                    existingBesluittype.setBesluittypeomschrijvinggeneriek(besluittype.getBesluittypeomschrijvinggeneriek());
                }
                if (besluittype.getDatumbegingeldigheidbesluittype() != null) {
                    existingBesluittype.setDatumbegingeldigheidbesluittype(besluittype.getDatumbegingeldigheidbesluittype());
                }
                if (besluittype.getDatumeindegeldigheidbesluittype() != null) {
                    existingBesluittype.setDatumeindegeldigheidbesluittype(besluittype.getDatumeindegeldigheidbesluittype());
                }
                if (besluittype.getIndicatiepublicatie() != null) {
                    existingBesluittype.setIndicatiepublicatie(besluittype.getIndicatiepublicatie());
                }
                if (besluittype.getPublicatietekst() != null) {
                    existingBesluittype.setPublicatietekst(besluittype.getPublicatietekst());
                }
                if (besluittype.getPublicatietermijn() != null) {
                    existingBesluittype.setPublicatietermijn(besluittype.getPublicatietermijn());
                }
                if (besluittype.getReactietermijn() != null) {
                    existingBesluittype.setReactietermijn(besluittype.getReactietermijn());
                }

                return existingBesluittype;
            })
            .map(besluittypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, besluittype.getId().toString())
        );
    }

    /**
     * {@code GET  /besluittypes} : get all the besluittypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of besluittypes in body.
     */
    @GetMapping("")
    public List<Besluittype> getAllBesluittypes() {
        log.debug("REST request to get all Besluittypes");
        return besluittypeRepository.findAll();
    }

    /**
     * {@code GET  /besluittypes/:id} : get the "id" besluittype.
     *
     * @param id the id of the besluittype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the besluittype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Besluittype> getBesluittype(@PathVariable("id") Long id) {
        log.debug("REST request to get Besluittype : {}", id);
        Optional<Besluittype> besluittype = besluittypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(besluittype);
    }

    /**
     * {@code DELETE  /besluittypes/:id} : delete the "id" besluittype.
     *
     * @param id the id of the besluittype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBesluittype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Besluittype : {}", id);
        besluittypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
