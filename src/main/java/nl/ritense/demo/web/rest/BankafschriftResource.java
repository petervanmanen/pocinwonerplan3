package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bankafschrift;
import nl.ritense.demo.repository.BankafschriftRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bankafschrift}.
 */
@RestController
@RequestMapping("/api/bankafschrifts")
@Transactional
public class BankafschriftResource {

    private final Logger log = LoggerFactory.getLogger(BankafschriftResource.class);

    private static final String ENTITY_NAME = "bankafschrift";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankafschriftRepository bankafschriftRepository;

    public BankafschriftResource(BankafschriftRepository bankafschriftRepository) {
        this.bankafschriftRepository = bankafschriftRepository;
    }

    /**
     * {@code POST  /bankafschrifts} : Create a new bankafschrift.
     *
     * @param bankafschrift the bankafschrift to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankafschrift, or with status {@code 400 (Bad Request)} if the bankafschrift has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bankafschrift> createBankafschrift(@Valid @RequestBody Bankafschrift bankafschrift) throws URISyntaxException {
        log.debug("REST request to save Bankafschrift : {}", bankafschrift);
        if (bankafschrift.getId() != null) {
            throw new BadRequestAlertException("A new bankafschrift cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bankafschrift = bankafschriftRepository.save(bankafschrift);
        return ResponseEntity.created(new URI("/api/bankafschrifts/" + bankafschrift.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bankafschrift.getId().toString()))
            .body(bankafschrift);
    }

    /**
     * {@code PUT  /bankafschrifts/:id} : Updates an existing bankafschrift.
     *
     * @param id the id of the bankafschrift to save.
     * @param bankafschrift the bankafschrift to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankafschrift,
     * or with status {@code 400 (Bad Request)} if the bankafschrift is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankafschrift couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bankafschrift> updateBankafschrift(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bankafschrift bankafschrift
    ) throws URISyntaxException {
        log.debug("REST request to update Bankafschrift : {}, {}", id, bankafschrift);
        if (bankafschrift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankafschrift.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankafschriftRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bankafschrift = bankafschriftRepository.save(bankafschrift);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankafschrift.getId().toString()))
            .body(bankafschrift);
    }

    /**
     * {@code PATCH  /bankafschrifts/:id} : Partial updates given fields of an existing bankafschrift, field will ignore if it is null
     *
     * @param id the id of the bankafschrift to save.
     * @param bankafschrift the bankafschrift to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankafschrift,
     * or with status {@code 400 (Bad Request)} if the bankafschrift is not valid,
     * or with status {@code 404 (Not Found)} if the bankafschrift is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankafschrift couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bankafschrift> partialUpdateBankafschrift(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bankafschrift bankafschrift
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bankafschrift partially : {}, {}", id, bankafschrift);
        if (bankafschrift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankafschrift.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankafschriftRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bankafschrift> result = bankafschriftRepository
            .findById(bankafschrift.getId())
            .map(existingBankafschrift -> {
                if (bankafschrift.getDatum() != null) {
                    existingBankafschrift.setDatum(bankafschrift.getDatum());
                }
                if (bankafschrift.getNummer() != null) {
                    existingBankafschrift.setNummer(bankafschrift.getNummer());
                }

                return existingBankafschrift;
            })
            .map(bankafschriftRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankafschrift.getId().toString())
        );
    }

    /**
     * {@code GET  /bankafschrifts} : get all the bankafschrifts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankafschrifts in body.
     */
    @GetMapping("")
    public List<Bankafschrift> getAllBankafschrifts() {
        log.debug("REST request to get all Bankafschrifts");
        return bankafschriftRepository.findAll();
    }

    /**
     * {@code GET  /bankafschrifts/:id} : get the "id" bankafschrift.
     *
     * @param id the id of the bankafschrift to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankafschrift, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bankafschrift> getBankafschrift(@PathVariable("id") Long id) {
        log.debug("REST request to get Bankafschrift : {}", id);
        Optional<Bankafschrift> bankafschrift = bankafschriftRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankafschrift);
    }

    /**
     * {@code DELETE  /bankafschrifts/:id} : delete the "id" bankafschrift.
     *
     * @param id the id of the bankafschrift to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankafschrift(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bankafschrift : {}", id);
        bankafschriftRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
