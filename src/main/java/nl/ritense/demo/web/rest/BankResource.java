package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bank;
import nl.ritense.demo.repository.BankRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bank}.
 */
@RestController
@RequestMapping("/api/banks")
@Transactional
public class BankResource {

    private final Logger log = LoggerFactory.getLogger(BankResource.class);

    private static final String ENTITY_NAME = "bank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankRepository bankRepository;

    public BankResource(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    /**
     * {@code POST  /banks} : Create a new bank.
     *
     * @param bank the bank to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bank, or with status {@code 400 (Bad Request)} if the bank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) throws URISyntaxException {
        log.debug("REST request to save Bank : {}", bank);
        if (bank.getId() != null) {
            throw new BadRequestAlertException("A new bank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bank = bankRepository.save(bank);
        return ResponseEntity.created(new URI("/api/banks/" + bank.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bank.getId().toString()))
            .body(bank);
    }

    /**
     * {@code PUT  /banks/:id} : Updates an existing bank.
     *
     * @param id the id of the bank to save.
     * @param bank the bank to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bank,
     * or with status {@code 400 (Bad Request)} if the bank is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bank couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bank bank)
        throws URISyntaxException {
        log.debug("REST request to update Bank : {}, {}", id, bank);
        if (bank.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bank.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bank = bankRepository.save(bank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bank.getId().toString()))
            .body(bank);
    }

    /**
     * {@code PATCH  /banks/:id} : Partial updates given fields of an existing bank, field will ignore if it is null
     *
     * @param id the id of the bank to save.
     * @param bank the bank to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bank,
     * or with status {@code 400 (Bad Request)} if the bank is not valid,
     * or with status {@code 404 (Not Found)} if the bank is not found,
     * or with status {@code 500 (Internal Server Error)} if the bank couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bank> partialUpdateBank(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bank bank)
        throws URISyntaxException {
        log.debug("REST request to partial update Bank partially : {}, {}", id, bank);
        if (bank.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bank.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bank> result = bankRepository
            .findById(bank.getId())
            .map(existingBank -> {
                if (bank.getType() != null) {
                    existingBank.setType(bank.getType());
                }
                if (bank.getTypeplus() != null) {
                    existingBank.setTypeplus(bank.getTypeplus());
                }

                return existingBank;
            })
            .map(bankRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bank.getId().toString())
        );
    }

    /**
     * {@code GET  /banks} : get all the banks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banks in body.
     */
    @GetMapping("")
    public List<Bank> getAllBanks() {
        log.debug("REST request to get all Banks");
        return bankRepository.findAll();
    }

    /**
     * {@code GET  /banks/:id} : get the "id" bank.
     *
     * @param id the id of the bank to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bank, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBank(@PathVariable("id") Long id) {
        log.debug("REST request to get Bank : {}", id);
        Optional<Bank> bank = bankRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bank);
    }

    /**
     * {@code DELETE  /banks/:id} : delete the "id" bank.
     *
     * @param id the id of the bank to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bank : {}", id);
        bankRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
