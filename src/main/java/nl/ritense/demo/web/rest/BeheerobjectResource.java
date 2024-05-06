package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beheerobject;
import nl.ritense.demo.repository.BeheerobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beheerobject}.
 */
@RestController
@RequestMapping("/api/beheerobjects")
@Transactional
public class BeheerobjectResource {

    private final Logger log = LoggerFactory.getLogger(BeheerobjectResource.class);

    private static final String ENTITY_NAME = "beheerobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeheerobjectRepository beheerobjectRepository;

    public BeheerobjectResource(BeheerobjectRepository beheerobjectRepository) {
        this.beheerobjectRepository = beheerobjectRepository;
    }

    /**
     * {@code POST  /beheerobjects} : Create a new beheerobject.
     *
     * @param beheerobject the beheerobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beheerobject, or with status {@code 400 (Bad Request)} if the beheerobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beheerobject> createBeheerobject(@Valid @RequestBody Beheerobject beheerobject) throws URISyntaxException {
        log.debug("REST request to save Beheerobject : {}", beheerobject);
        if (beheerobject.getId() != null) {
            throw new BadRequestAlertException("A new beheerobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beheerobject = beheerobjectRepository.save(beheerobject);
        return ResponseEntity.created(new URI("/api/beheerobjects/" + beheerobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beheerobject.getId().toString()))
            .body(beheerobject);
    }

    /**
     * {@code PUT  /beheerobjects/:id} : Updates an existing beheerobject.
     *
     * @param id the id of the beheerobject to save.
     * @param beheerobject the beheerobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beheerobject,
     * or with status {@code 400 (Bad Request)} if the beheerobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beheerobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beheerobject> updateBeheerobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Beheerobject beheerobject
    ) throws URISyntaxException {
        log.debug("REST request to update Beheerobject : {}, {}", id, beheerobject);
        if (beheerobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beheerobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beheerobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beheerobject = beheerobjectRepository.save(beheerobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beheerobject.getId().toString()))
            .body(beheerobject);
    }

    /**
     * {@code PATCH  /beheerobjects/:id} : Partial updates given fields of an existing beheerobject, field will ignore if it is null
     *
     * @param id the id of the beheerobject to save.
     * @param beheerobject the beheerobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beheerobject,
     * or with status {@code 400 (Bad Request)} if the beheerobject is not valid,
     * or with status {@code 404 (Not Found)} if the beheerobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the beheerobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beheerobject> partialUpdateBeheerobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Beheerobject beheerobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beheerobject partially : {}, {}", id, beheerobject);
        if (beheerobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beheerobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beheerobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beheerobject> result = beheerobjectRepository
            .findById(beheerobject.getId())
            .map(existingBeheerobject -> {
                if (beheerobject.getAangemaaktdoor() != null) {
                    existingBeheerobject.setAangemaaktdoor(beheerobject.getAangemaaktdoor());
                }
                if (beheerobject.getBegingarantieperiode() != null) {
                    existingBeheerobject.setBegingarantieperiode(beheerobject.getBegingarantieperiode());
                }
                if (beheerobject.getBeheergebied() != null) {
                    existingBeheerobject.setBeheergebied(beheerobject.getBeheergebied());
                }
                if (beheerobject.getBeheerobjectbeheervak() != null) {
                    existingBeheerobject.setBeheerobjectbeheervak(beheerobject.getBeheerobjectbeheervak());
                }
                if (beheerobject.getBeheerobjectgebruiksfunctie() != null) {
                    existingBeheerobject.setBeheerobjectgebruiksfunctie(beheerobject.getBeheerobjectgebruiksfunctie());
                }
                if (beheerobject.getBeheerobjectmemo() != null) {
                    existingBeheerobject.setBeheerobjectmemo(beheerobject.getBeheerobjectmemo());
                }
                if (beheerobject.getBeschermdefloraenfauna() != null) {
                    existingBeheerobject.setBeschermdefloraenfauna(beheerobject.getBeschermdefloraenfauna());
                }
                if (beheerobject.getBuurt() != null) {
                    existingBeheerobject.setBuurt(beheerobject.getBuurt());
                }
                if (beheerobject.getConversieid() != null) {
                    existingBeheerobject.setConversieid(beheerobject.getConversieid());
                }
                if (beheerobject.getDatummutatie() != null) {
                    existingBeheerobject.setDatummutatie(beheerobject.getDatummutatie());
                }
                if (beheerobject.getDatumoplevering() != null) {
                    existingBeheerobject.setDatumoplevering(beheerobject.getDatumoplevering());
                }
                if (beheerobject.getDatumpublicatielv() != null) {
                    existingBeheerobject.setDatumpublicatielv(beheerobject.getDatumpublicatielv());
                }
                if (beheerobject.getDatumverwijdering() != null) {
                    existingBeheerobject.setDatumverwijdering(beheerobject.getDatumverwijdering());
                }
                if (beheerobject.getEindegarantieperiode() != null) {
                    existingBeheerobject.setEindegarantieperiode(beheerobject.getEindegarantieperiode());
                }
                if (beheerobject.getGebiedstype() != null) {
                    existingBeheerobject.setGebiedstype(beheerobject.getGebiedstype());
                }
                if (beheerobject.getGemeente() != null) {
                    existingBeheerobject.setGemeente(beheerobject.getGemeente());
                }
                if (beheerobject.getGeometrie() != null) {
                    existingBeheerobject.setGeometrie(beheerobject.getGeometrie());
                }
                if (beheerobject.getGewijzigddoor() != null) {
                    existingBeheerobject.setGewijzigddoor(beheerobject.getGewijzigddoor());
                }
                if (beheerobject.getGrondsoort() != null) {
                    existingBeheerobject.setGrondsoort(beheerobject.getGrondsoort());
                }
                if (beheerobject.getGrondsoortplus() != null) {
                    existingBeheerobject.setGrondsoortplus(beheerobject.getGrondsoortplus());
                }
                if (beheerobject.getIdentificatieimbor() != null) {
                    existingBeheerobject.setIdentificatieimbor(beheerobject.getIdentificatieimbor());
                }
                if (beheerobject.getIdentificatieimgeo() != null) {
                    existingBeheerobject.setIdentificatieimgeo(beheerobject.getIdentificatieimgeo());
                }
                if (beheerobject.getJaarvanaanleg() != null) {
                    existingBeheerobject.setJaarvanaanleg(beheerobject.getJaarvanaanleg());
                }
                if (beheerobject.getEobjectbegintijd() != null) {
                    existingBeheerobject.setEobjectbegintijd(beheerobject.getEobjectbegintijd());
                }
                if (beheerobject.getEobjecteindtijd() != null) {
                    existingBeheerobject.setEobjecteindtijd(beheerobject.getEobjecteindtijd());
                }
                if (beheerobject.getOnderhoudsplichtige() != null) {
                    existingBeheerobject.setOnderhoudsplichtige(beheerobject.getOnderhoudsplichtige());
                }
                if (beheerobject.getOpenbareruimte() != null) {
                    existingBeheerobject.setOpenbareruimte(beheerobject.getOpenbareruimte());
                }
                if (beheerobject.getPostcode() != null) {
                    existingBeheerobject.setPostcode(beheerobject.getPostcode());
                }
                if (beheerobject.getRelatievehoogteligging() != null) {
                    existingBeheerobject.setRelatievehoogteligging(beheerobject.getRelatievehoogteligging());
                }
                if (beheerobject.getStadsdeel() != null) {
                    existingBeheerobject.setStadsdeel(beheerobject.getStadsdeel());
                }
                if (beheerobject.getStatus() != null) {
                    existingBeheerobject.setStatus(beheerobject.getStatus());
                }
                if (beheerobject.getTheoretischeindejaar() != null) {
                    existingBeheerobject.setTheoretischeindejaar(beheerobject.getTheoretischeindejaar());
                }
                if (beheerobject.getTijdstipregistratie() != null) {
                    existingBeheerobject.setTijdstipregistratie(beheerobject.getTijdstipregistratie());
                }
                if (beheerobject.getTypebeheerder() != null) {
                    existingBeheerobject.setTypebeheerder(beheerobject.getTypebeheerder());
                }
                if (beheerobject.getTypebeheerderplus() != null) {
                    existingBeheerobject.setTypebeheerderplus(beheerobject.getTypebeheerderplus());
                }
                if (beheerobject.getTypeeigenaar() != null) {
                    existingBeheerobject.setTypeeigenaar(beheerobject.getTypeeigenaar());
                }
                if (beheerobject.getTypeeigenaarplus() != null) {
                    existingBeheerobject.setTypeeigenaarplus(beheerobject.getTypeeigenaarplus());
                }
                if (beheerobject.getTypeligging() != null) {
                    existingBeheerobject.setTypeligging(beheerobject.getTypeligging());
                }
                if (beheerobject.getWaterschap() != null) {
                    existingBeheerobject.setWaterschap(beheerobject.getWaterschap());
                }
                if (beheerobject.getWijk() != null) {
                    existingBeheerobject.setWijk(beheerobject.getWijk());
                }
                if (beheerobject.getWoonplaats() != null) {
                    existingBeheerobject.setWoonplaats(beheerobject.getWoonplaats());
                }
                if (beheerobject.getZettingsgevoeligheid() != null) {
                    existingBeheerobject.setZettingsgevoeligheid(beheerobject.getZettingsgevoeligheid());
                }
                if (beheerobject.getZettingsgevoeligheidplus() != null) {
                    existingBeheerobject.setZettingsgevoeligheidplus(beheerobject.getZettingsgevoeligheidplus());
                }

                return existingBeheerobject;
            })
            .map(beheerobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beheerobject.getId().toString())
        );
    }

    /**
     * {@code GET  /beheerobjects} : get all the beheerobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beheerobjects in body.
     */
    @GetMapping("")
    public List<Beheerobject> getAllBeheerobjects() {
        log.debug("REST request to get all Beheerobjects");
        return beheerobjectRepository.findAll();
    }

    /**
     * {@code GET  /beheerobjects/:id} : get the "id" beheerobject.
     *
     * @param id the id of the beheerobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beheerobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beheerobject> getBeheerobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Beheerobject : {}", id);
        Optional<Beheerobject> beheerobject = beheerobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beheerobject);
    }

    /**
     * {@code DELETE  /beheerobjects/:id} : delete the "id" beheerobject.
     *
     * @param id the id of the beheerobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeheerobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beheerobject : {}", id);
        beheerobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
