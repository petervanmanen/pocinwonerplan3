package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bankrekening;
import nl.ritense.demo.repository.BankrekeningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bankrekening}.
 */
@RestController
@RequestMapping("/api/bankrekenings")
@Transactional
public class BankrekeningResource {

    private final Logger log = LoggerFactory.getLogger(BankrekeningResource.class);

    private static final String ENTITY_NAME = "bankrekening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankrekeningRepository bankrekeningRepository;

    public BankrekeningResource(BankrekeningRepository bankrekeningRepository) {
        this.bankrekeningRepository = bankrekeningRepository;
    }

    /**
     * {@code POST  /bankrekenings} : Create a new bankrekening.
     *
     * @param bankrekening the bankrekening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankrekening, or with status {@code 400 (Bad Request)} if the bankrekening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bankrekening> createBankrekening(@Valid @RequestBody Bankrekening bankrekening) throws URISyntaxException {
        log.debug("REST request to save Bankrekening : {}", bankrekening);
        if (bankrekening.getId() != null) {
            throw new BadRequestAlertException("A new bankrekening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bankrekening = bankrekeningRepository.save(bankrekening);
        return ResponseEntity.created(new URI("/api/bankrekenings/" + bankrekening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bankrekening.getId().toString()))
            .body(bankrekening);
    }

    /**
     * {@code PUT  /bankrekenings/:id} : Updates an existing bankrekening.
     *
     * @param id the id of the bankrekening to save.
     * @param bankrekening the bankrekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankrekening,
     * or with status {@code 400 (Bad Request)} if the bankrekening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankrekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bankrekening> updateBankrekening(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bankrekening bankrekening
    ) throws URISyntaxException {
        log.debug("REST request to update Bankrekening : {}, {}", id, bankrekening);
        if (bankrekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankrekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankrekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bankrekening = bankrekeningRepository.save(bankrekening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankrekening.getId().toString()))
            .body(bankrekening);
    }

    /**
     * {@code PATCH  /bankrekenings/:id} : Partial updates given fields of an existing bankrekening, field will ignore if it is null
     *
     * @param id the id of the bankrekening to save.
     * @param bankrekening the bankrekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankrekening,
     * or with status {@code 400 (Bad Request)} if the bankrekening is not valid,
     * or with status {@code 404 (Not Found)} if the bankrekening is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankrekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bankrekening> partialUpdateBankrekening(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bankrekening bankrekening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bankrekening partially : {}, {}", id, bankrekening);
        if (bankrekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankrekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankrekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bankrekening> result = bankrekeningRepository
            .findById(bankrekening.getId())
            .map(existingBankrekening -> {
                if (bankrekening.getBank() != null) {
                    existingBankrekening.setBank(bankrekening.getBank());
                }
                if (bankrekening.getNummer() != null) {
                    existingBankrekening.setNummer(bankrekening.getNummer());
                }
                if (bankrekening.getTennaamstelling() != null) {
                    existingBankrekening.setTennaamstelling(bankrekening.getTennaamstelling());
                }

                return existingBankrekening;
            })
            .map(bankrekeningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankrekening.getId().toString())
        );
    }

    /**
     * {@code GET  /bankrekenings} : get all the bankrekenings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankrekenings in body.
     */
    @GetMapping("")
    public List<Bankrekening> getAllBankrekenings() {
        log.debug("REST request to get all Bankrekenings");
        return bankrekeningRepository.findAll();
    }

    /**
     * {@code GET  /bankrekenings/:id} : get the "id" bankrekening.
     *
     * @param id the id of the bankrekening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankrekening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bankrekening> getBankrekening(@PathVariable("id") Long id) {
        log.debug("REST request to get Bankrekening : {}", id);
        Optional<Bankrekening> bankrekening = bankrekeningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankrekening);
    }

    /**
     * {@code DELETE  /bankrekenings/:id} : delete the "id" bankrekening.
     *
     * @param id the id of the bankrekening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankrekening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bankrekening : {}", id);
        bankrekeningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
