package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Subsidie;
import nl.ritense.demo.repository.SubsidieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Subsidie}.
 */
@RestController
@RequestMapping("/api/subsidies")
@Transactional
public class SubsidieResource {

    private final Logger log = LoggerFactory.getLogger(SubsidieResource.class);

    private static final String ENTITY_NAME = "subsidie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubsidieRepository subsidieRepository;

    public SubsidieResource(SubsidieRepository subsidieRepository) {
        this.subsidieRepository = subsidieRepository;
    }

    /**
     * {@code POST  /subsidies} : Create a new subsidie.
     *
     * @param subsidie the subsidie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subsidie, or with status {@code 400 (Bad Request)} if the subsidie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Subsidie> createSubsidie(@Valid @RequestBody Subsidie subsidie) throws URISyntaxException {
        log.debug("REST request to save Subsidie : {}", subsidie);
        if (subsidie.getId() != null) {
            throw new BadRequestAlertException("A new subsidie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subsidie = subsidieRepository.save(subsidie);
        return ResponseEntity.created(new URI("/api/subsidies/" + subsidie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subsidie.getId().toString()))
            .body(subsidie);
    }

    /**
     * {@code PUT  /subsidies/:id} : Updates an existing subsidie.
     *
     * @param id the id of the subsidie to save.
     * @param subsidie the subsidie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidie,
     * or with status {@code 400 (Bad Request)} if the subsidie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subsidie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subsidie> updateSubsidie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Subsidie subsidie
    ) throws URISyntaxException {
        log.debug("REST request to update Subsidie : {}, {}", id, subsidie);
        if (subsidie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subsidie = subsidieRepository.save(subsidie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidie.getId().toString()))
            .body(subsidie);
    }

    /**
     * {@code PATCH  /subsidies/:id} : Partial updates given fields of an existing subsidie, field will ignore if it is null
     *
     * @param id the id of the subsidie to save.
     * @param subsidie the subsidie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidie,
     * or with status {@code 400 (Bad Request)} if the subsidie is not valid,
     * or with status {@code 404 (Not Found)} if the subsidie is not found,
     * or with status {@code 500 (Internal Server Error)} if the subsidie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Subsidie> partialUpdateSubsidie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Subsidie subsidie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subsidie partially : {}, {}", id, subsidie);
        if (subsidie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Subsidie> result = subsidieRepository
            .findById(subsidie.getId())
            .map(existingSubsidie -> {
                if (subsidie.getAccountantscontrole() != null) {
                    existingSubsidie.setAccountantscontrole(subsidie.getAccountantscontrole());
                }
                if (subsidie.getCofinanciering() != null) {
                    existingSubsidie.setCofinanciering(subsidie.getCofinanciering());
                }
                if (subsidie.getDatumbehandeltermijn() != null) {
                    existingSubsidie.setDatumbehandeltermijn(subsidie.getDatumbehandeltermijn());
                }
                if (subsidie.getDatumbewaartermijn() != null) {
                    existingSubsidie.setDatumbewaartermijn(subsidie.getDatumbewaartermijn());
                }
                if (subsidie.getDatumeinde() != null) {
                    existingSubsidie.setDatumeinde(subsidie.getDatumeinde());
                }
                if (subsidie.getDatumstart() != null) {
                    existingSubsidie.setDatumstart(subsidie.getDatumstart());
                }
                if (subsidie.getDatumsubsidievaststelling() != null) {
                    existingSubsidie.setDatumsubsidievaststelling(subsidie.getDatumsubsidievaststelling());
                }
                if (subsidie.getDatumverzendingeindeafrekening() != null) {
                    existingSubsidie.setDatumverzendingeindeafrekening(subsidie.getDatumverzendingeindeafrekening());
                }
                if (subsidie.getDeadlineindiening() != null) {
                    existingSubsidie.setDeadlineindiening(subsidie.getDeadlineindiening());
                }
                if (subsidie.getDoelstelling() != null) {
                    existingSubsidie.setDoelstelling(subsidie.getDoelstelling());
                }
                if (subsidie.getGerealiseerdeprojectkosten() != null) {
                    existingSubsidie.setGerealiseerdeprojectkosten(subsidie.getGerealiseerdeprojectkosten());
                }
                if (subsidie.getHoogtesubsidie() != null) {
                    existingSubsidie.setHoogtesubsidie(subsidie.getHoogtesubsidie());
                }
                if (subsidie.getNiveau() != null) {
                    existingSubsidie.setNiveau(subsidie.getNiveau());
                }
                if (subsidie.getOnderwerp() != null) {
                    existingSubsidie.setOnderwerp(subsidie.getOnderwerp());
                }
                if (subsidie.getOntvangenbedrag() != null) {
                    existingSubsidie.setOntvangenbedrag(subsidie.getOntvangenbedrag());
                }
                if (subsidie.getOpmerkingen() != null) {
                    existingSubsidie.setOpmerkingen(subsidie.getOpmerkingen());
                }
                if (subsidie.getOpmerkingenvoorschotten() != null) {
                    existingSubsidie.setOpmerkingenvoorschotten(subsidie.getOpmerkingenvoorschotten());
                }
                if (subsidie.getPrestatiesubsidie() != null) {
                    existingSubsidie.setPrestatiesubsidie(subsidie.getPrestatiesubsidie());
                }
                if (subsidie.getSocialreturnbedrag() != null) {
                    existingSubsidie.setSocialreturnbedrag(subsidie.getSocialreturnbedrag());
                }
                if (subsidie.getSocialreturnnagekomen() != null) {
                    existingSubsidie.setSocialreturnnagekomen(subsidie.getSocialreturnnagekomen());
                }
                if (subsidie.getSocialreturnverplichting() != null) {
                    existingSubsidie.setSocialreturnverplichting(subsidie.getSocialreturnverplichting());
                }
                if (subsidie.getStatus() != null) {
                    existingSubsidie.setStatus(subsidie.getStatus());
                }
                if (subsidie.getSubsidiebedrag() != null) {
                    existingSubsidie.setSubsidiebedrag(subsidie.getSubsidiebedrag());
                }
                if (subsidie.getSubsidiesoort() != null) {
                    existingSubsidie.setSubsidiesoort(subsidie.getSubsidiesoort());
                }
                if (subsidie.getSubsidievaststellingbedrag() != null) {
                    existingSubsidie.setSubsidievaststellingbedrag(subsidie.getSubsidievaststellingbedrag());
                }
                if (subsidie.getUitgaandesubsidie() != null) {
                    existingSubsidie.setUitgaandesubsidie(subsidie.getUitgaandesubsidie());
                }
                if (subsidie.getVerantwoordenop() != null) {
                    existingSubsidie.setVerantwoordenop(subsidie.getVerantwoordenop());
                }

                return existingSubsidie;
            })
            .map(subsidieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidie.getId().toString())
        );
    }

    /**
     * {@code GET  /subsidies} : get all the subsidies.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subsidies in body.
     */
    @GetMapping("")
    public List<Subsidie> getAllSubsidies(@RequestParam(name = "filter", required = false) String filter) {
        if ("betreftsubsidieaanvraag-is-null".equals(filter)) {
            log.debug("REST request to get all Subsidies where betreftSubsidieaanvraag is null");
            return StreamSupport.stream(subsidieRepository.findAll().spliterator(), false)
                .filter(subsidie -> subsidie.getBetreftSubsidieaanvraag() == null)
                .toList();
        }

        if ("betreftsubsidiebeschikking-is-null".equals(filter)) {
            log.debug("REST request to get all Subsidies where betreftSubsidiebeschikking is null");
            return StreamSupport.stream(subsidieRepository.findAll().spliterator(), false)
                .filter(subsidie -> subsidie.getBetreftSubsidiebeschikking() == null)
                .toList();
        }
        log.debug("REST request to get all Subsidies");
        return subsidieRepository.findAll();
    }

    /**
     * {@code GET  /subsidies/:id} : get the "id" subsidie.
     *
     * @param id the id of the subsidie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subsidie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subsidie> getSubsidie(@PathVariable("id") Long id) {
        log.debug("REST request to get Subsidie : {}", id);
        Optional<Subsidie> subsidie = subsidieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subsidie);
    }

    /**
     * {@code DELETE  /subsidies/:id} : delete the "id" subsidie.
     *
     * @param id the id of the subsidie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubsidie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Subsidie : {}", id);
        subsidieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
