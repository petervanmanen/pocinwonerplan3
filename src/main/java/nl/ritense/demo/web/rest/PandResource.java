package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Pand;
import nl.ritense.demo.repository.PandRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Pand}.
 */
@RestController
@RequestMapping("/api/pands")
@Transactional
public class PandResource {

    private final Logger log = LoggerFactory.getLogger(PandResource.class);

    private static final String ENTITY_NAME = "pand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PandRepository pandRepository;

    public PandResource(PandRepository pandRepository) {
        this.pandRepository = pandRepository;
    }

    /**
     * {@code POST  /pands} : Create a new pand.
     *
     * @param pand the pand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pand, or with status {@code 400 (Bad Request)} if the pand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pand> createPand(@Valid @RequestBody Pand pand) throws URISyntaxException {
        log.debug("REST request to save Pand : {}", pand);
        if (pand.getId() != null) {
            throw new BadRequestAlertException("A new pand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pand = pandRepository.save(pand);
        return ResponseEntity.created(new URI("/api/pands/" + pand.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, pand.getId().toString()))
            .body(pand);
    }

    /**
     * {@code PUT  /pands/:id} : Updates an existing pand.
     *
     * @param id the id of the pand to save.
     * @param pand the pand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pand,
     * or with status {@code 400 (Bad Request)} if the pand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pand> updatePand(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Pand pand)
        throws URISyntaxException {
        log.debug("REST request to update Pand : {}, {}", id, pand);
        if (pand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pand = pandRepository.save(pand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pand.getId().toString()))
            .body(pand);
    }

    /**
     * {@code PATCH  /pands/:id} : Partial updates given fields of an existing pand, field will ignore if it is null
     *
     * @param id the id of the pand to save.
     * @param pand the pand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pand,
     * or with status {@code 400 (Bad Request)} if the pand is not valid,
     * or with status {@code 404 (Not Found)} if the pand is not found,
     * or with status {@code 500 (Internal Server Error)} if the pand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pand> partialUpdatePand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pand pand
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pand partially : {}, {}", id, pand);
        if (pand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pand> result = pandRepository
            .findById(pand.getId())
            .map(existingPand -> {
                if (pand.getBrutoinhoudpand() != null) {
                    existingPand.setBrutoinhoudpand(pand.getBrutoinhoudpand());
                }
                if (pand.getDatumbegingeldigheid() != null) {
                    existingPand.setDatumbegingeldigheid(pand.getDatumbegingeldigheid());
                }
                if (pand.getDatumeinde() != null) {
                    existingPand.setDatumeinde(pand.getDatumeinde());
                }
                if (pand.getDatumeindegeldigheid() != null) {
                    existingPand.setDatumeindegeldigheid(pand.getDatumeindegeldigheid());
                }
                if (pand.getDatumingang() != null) {
                    existingPand.setDatumingang(pand.getDatumingang());
                }
                if (pand.getGeconstateerd() != null) {
                    existingPand.setGeconstateerd(pand.getGeconstateerd());
                }
                if (pand.getGeometriebovenaanzicht() != null) {
                    existingPand.setGeometriebovenaanzicht(pand.getGeometriebovenaanzicht());
                }
                if (pand.getGeometriemaaiveld() != null) {
                    existingPand.setGeometriemaaiveld(pand.getGeometriemaaiveld());
                }
                if (pand.getGeometriepunt() != null) {
                    existingPand.setGeometriepunt(pand.getGeometriepunt());
                }
                if (pand.getHoogstebouwlaagpand() != null) {
                    existingPand.setHoogstebouwlaagpand(pand.getHoogstebouwlaagpand());
                }
                if (pand.getIdentificatie() != null) {
                    existingPand.setIdentificatie(pand.getIdentificatie());
                }
                if (pand.getInonderzoek() != null) {
                    existingPand.setInonderzoek(pand.getInonderzoek());
                }
                if (pand.getLaagstebouwlaagpand() != null) {
                    existingPand.setLaagstebouwlaagpand(pand.getLaagstebouwlaagpand());
                }
                if (pand.getOorspronkelijkbouwjaar() != null) {
                    existingPand.setOorspronkelijkbouwjaar(pand.getOorspronkelijkbouwjaar());
                }
                if (pand.getOppervlakte() != null) {
                    existingPand.setOppervlakte(pand.getOppervlakte());
                }
                if (pand.getRelatievehoogteliggingpand() != null) {
                    existingPand.setRelatievehoogteliggingpand(pand.getRelatievehoogteliggingpand());
                }
                if (pand.getStatus() != null) {
                    existingPand.setStatus(pand.getStatus());
                }
                if (pand.getStatusvoortgangbouw() != null) {
                    existingPand.setStatusvoortgangbouw(pand.getStatusvoortgangbouw());
                }
                if (pand.getVersie() != null) {
                    existingPand.setVersie(pand.getVersie());
                }

                return existingPand;
            })
            .map(pandRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pand.getId().toString())
        );
    }

    /**
     * {@code GET  /pands} : get all the pands.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pands in body.
     */
    @GetMapping("")
    public List<Pand> getAllPands(@RequestParam(name = "filter", required = false) String filter) {
        if ("betreftvastgoedobject-is-null".equals(filter)) {
            log.debug("REST request to get all Pands where betreftVastgoedobject is null");
            return StreamSupport.stream(pandRepository.findAll().spliterator(), false)
                .filter(pand -> pand.getBetreftVastgoedobject() == null)
                .toList();
        }
        log.debug("REST request to get all Pands");
        return pandRepository.findAll();
    }

    /**
     * {@code GET  /pands/:id} : get the "id" pand.
     *
     * @param id the id of the pand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pand> getPand(@PathVariable("id") Long id) {
        log.debug("REST request to get Pand : {}", id);
        Optional<Pand> pand = pandRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pand);
    }

    /**
     * {@code DELETE  /pands/:id} : delete the "id" pand.
     *
     * @param id the id of the pand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePand(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pand : {}", id);
        pandRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
