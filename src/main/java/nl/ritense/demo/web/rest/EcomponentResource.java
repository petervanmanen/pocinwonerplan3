package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ecomponent;
import nl.ritense.demo.repository.EcomponentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ecomponent}.
 */
@RestController
@RequestMapping("/api/ecomponents")
@Transactional
public class EcomponentResource {

    private final Logger log = LoggerFactory.getLogger(EcomponentResource.class);

    private static final String ENTITY_NAME = "ecomponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcomponentRepository ecomponentRepository;

    public EcomponentResource(EcomponentRepository ecomponentRepository) {
        this.ecomponentRepository = ecomponentRepository;
    }

    /**
     * {@code POST  /ecomponents} : Create a new ecomponent.
     *
     * @param ecomponent the ecomponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecomponent, or with status {@code 400 (Bad Request)} if the ecomponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ecomponent> createEcomponent(@Valid @RequestBody Ecomponent ecomponent) throws URISyntaxException {
        log.debug("REST request to save Ecomponent : {}", ecomponent);
        if (ecomponent.getId() != null) {
            throw new BadRequestAlertException("A new ecomponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ecomponent = ecomponentRepository.save(ecomponent);
        return ResponseEntity.created(new URI("/api/ecomponents/" + ecomponent.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ecomponent.getId().toString()))
            .body(ecomponent);
    }

    /**
     * {@code PUT  /ecomponents/:id} : Updates an existing ecomponent.
     *
     * @param id the id of the ecomponent to save.
     * @param ecomponent the ecomponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecomponent,
     * or with status {@code 400 (Bad Request)} if the ecomponent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecomponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ecomponent> updateEcomponent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ecomponent ecomponent
    ) throws URISyntaxException {
        log.debug("REST request to update Ecomponent : {}, {}", id, ecomponent);
        if (ecomponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecomponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecomponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ecomponent = ecomponentRepository.save(ecomponent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ecomponent.getId().toString()))
            .body(ecomponent);
    }

    /**
     * {@code PATCH  /ecomponents/:id} : Partial updates given fields of an existing ecomponent, field will ignore if it is null
     *
     * @param id the id of the ecomponent to save.
     * @param ecomponent the ecomponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecomponent,
     * or with status {@code 400 (Bad Request)} if the ecomponent is not valid,
     * or with status {@code 404 (Not Found)} if the ecomponent is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecomponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ecomponent> partialUpdateEcomponent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ecomponent ecomponent
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ecomponent partially : {}, {}", id, ecomponent);
        if (ecomponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecomponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecomponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ecomponent> result = ecomponentRepository
            .findById(ecomponent.getId())
            .map(existingEcomponent -> {
                if (ecomponent.getBedrag() != null) {
                    existingEcomponent.setBedrag(ecomponent.getBedrag());
                }
                if (ecomponent.getDatumbeginbetrekkingop() != null) {
                    existingEcomponent.setDatumbeginbetrekkingop(ecomponent.getDatumbeginbetrekkingop());
                }
                if (ecomponent.getDatumeindebetrekkingop() != null) {
                    existingEcomponent.setDatumeindebetrekkingop(ecomponent.getDatumeindebetrekkingop());
                }
                if (ecomponent.getDebetcredit() != null) {
                    existingEcomponent.setDebetcredit(ecomponent.getDebetcredit());
                }
                if (ecomponent.getGroep() != null) {
                    existingEcomponent.setGroep(ecomponent.getGroep());
                }
                if (ecomponent.getGroepcode() != null) {
                    existingEcomponent.setGroepcode(ecomponent.getGroepcode());
                }
                if (ecomponent.getGrootboekcode() != null) {
                    existingEcomponent.setGrootboekcode(ecomponent.getGrootboekcode());
                }
                if (ecomponent.getGrootboekomschrijving() != null) {
                    existingEcomponent.setGrootboekomschrijving(ecomponent.getGrootboekomschrijving());
                }
                if (ecomponent.getKostenplaats() != null) {
                    existingEcomponent.setKostenplaats(ecomponent.getKostenplaats());
                }
                if (ecomponent.getOmschrijving() != null) {
                    existingEcomponent.setOmschrijving(ecomponent.getOmschrijving());
                }
                if (ecomponent.getRekeningnummer() != null) {
                    existingEcomponent.setRekeningnummer(ecomponent.getRekeningnummer());
                }
                if (ecomponent.getToelichting() != null) {
                    existingEcomponent.setToelichting(ecomponent.getToelichting());
                }

                return existingEcomponent;
            })
            .map(ecomponentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ecomponent.getId().toString())
        );
    }

    /**
     * {@code GET  /ecomponents} : get all the ecomponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecomponents in body.
     */
    @GetMapping("")
    public List<Ecomponent> getAllEcomponents() {
        log.debug("REST request to get all Ecomponents");
        return ecomponentRepository.findAll();
    }

    /**
     * {@code GET  /ecomponents/:id} : get the "id" ecomponent.
     *
     * @param id the id of the ecomponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecomponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ecomponent> getEcomponent(@PathVariable("id") Long id) {
        log.debug("REST request to get Ecomponent : {}", id);
        Optional<Ecomponent> ecomponent = ecomponentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ecomponent);
    }

    /**
     * {@code DELETE  /ecomponents/:id} : delete the "id" ecomponent.
     *
     * @param id the id of the ecomponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEcomponent(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ecomponent : {}", id);
        ecomponentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
