package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bankafschriftregel;
import nl.ritense.demo.repository.BankafschriftregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bankafschriftregel}.
 */
@RestController
@RequestMapping("/api/bankafschriftregels")
@Transactional
public class BankafschriftregelResource {

    private final Logger log = LoggerFactory.getLogger(BankafschriftregelResource.class);

    private static final String ENTITY_NAME = "bankafschriftregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankafschriftregelRepository bankafschriftregelRepository;

    public BankafschriftregelResource(BankafschriftregelRepository bankafschriftregelRepository) {
        this.bankafschriftregelRepository = bankafschriftregelRepository;
    }

    /**
     * {@code POST  /bankafschriftregels} : Create a new bankafschriftregel.
     *
     * @param bankafschriftregel the bankafschriftregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankafschriftregel, or with status {@code 400 (Bad Request)} if the bankafschriftregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bankafschriftregel> createBankafschriftregel(@RequestBody Bankafschriftregel bankafschriftregel)
        throws URISyntaxException {
        log.debug("REST request to save Bankafschriftregel : {}", bankafschriftregel);
        if (bankafschriftregel.getId() != null) {
            throw new BadRequestAlertException("A new bankafschriftregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bankafschriftregel = bankafschriftregelRepository.save(bankafschriftregel);
        return ResponseEntity.created(new URI("/api/bankafschriftregels/" + bankafschriftregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bankafschriftregel.getId().toString()))
            .body(bankafschriftregel);
    }

    /**
     * {@code PUT  /bankafschriftregels/:id} : Updates an existing bankafschriftregel.
     *
     * @param id the id of the bankafschriftregel to save.
     * @param bankafschriftregel the bankafschriftregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankafschriftregel,
     * or with status {@code 400 (Bad Request)} if the bankafschriftregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankafschriftregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bankafschriftregel> updateBankafschriftregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bankafschriftregel bankafschriftregel
    ) throws URISyntaxException {
        log.debug("REST request to update Bankafschriftregel : {}, {}", id, bankafschriftregel);
        if (bankafschriftregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankafschriftregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankafschriftregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bankafschriftregel = bankafschriftregelRepository.save(bankafschriftregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankafschriftregel.getId().toString()))
            .body(bankafschriftregel);
    }

    /**
     * {@code PATCH  /bankafschriftregels/:id} : Partial updates given fields of an existing bankafschriftregel, field will ignore if it is null
     *
     * @param id the id of the bankafschriftregel to save.
     * @param bankafschriftregel the bankafschriftregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankafschriftregel,
     * or with status {@code 400 (Bad Request)} if the bankafschriftregel is not valid,
     * or with status {@code 404 (Not Found)} if the bankafschriftregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankafschriftregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bankafschriftregel> partialUpdateBankafschriftregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bankafschriftregel bankafschriftregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bankafschriftregel partially : {}, {}", id, bankafschriftregel);
        if (bankafschriftregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankafschriftregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankafschriftregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bankafschriftregel> result = bankafschriftregelRepository
            .findById(bankafschriftregel.getId())
            .map(existingBankafschriftregel -> {
                if (bankafschriftregel.getBedrag() != null) {
                    existingBankafschriftregel.setBedrag(bankafschriftregel.getBedrag());
                }
                if (bankafschriftregel.getBij() != null) {
                    existingBankafschriftregel.setBij(bankafschriftregel.getBij());
                }
                if (bankafschriftregel.getDatum() != null) {
                    existingBankafschriftregel.setDatum(bankafschriftregel.getDatum());
                }
                if (bankafschriftregel.getRekeningvan() != null) {
                    existingBankafschriftregel.setRekeningvan(bankafschriftregel.getRekeningvan());
                }

                return existingBankafschriftregel;
            })
            .map(bankafschriftregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankafschriftregel.getId().toString())
        );
    }

    /**
     * {@code GET  /bankafschriftregels} : get all the bankafschriftregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankafschriftregels in body.
     */
    @GetMapping("")
    public List<Bankafschriftregel> getAllBankafschriftregels() {
        log.debug("REST request to get all Bankafschriftregels");
        return bankafschriftregelRepository.findAll();
    }

    /**
     * {@code GET  /bankafschriftregels/:id} : get the "id" bankafschriftregel.
     *
     * @param id the id of the bankafschriftregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankafschriftregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bankafschriftregel> getBankafschriftregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Bankafschriftregel : {}", id);
        Optional<Bankafschriftregel> bankafschriftregel = bankafschriftregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankafschriftregel);
    }

    /**
     * {@code DELETE  /bankafschriftregels/:id} : delete the "id" bankafschriftregel.
     *
     * @param id the id of the bankafschriftregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankafschriftregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bankafschriftregel : {}", id);
        bankafschriftregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
