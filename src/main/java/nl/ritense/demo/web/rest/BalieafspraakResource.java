package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Balieafspraak;
import nl.ritense.demo.repository.BalieafspraakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Balieafspraak}.
 */
@RestController
@RequestMapping("/api/balieafspraaks")
@Transactional
public class BalieafspraakResource {

    private final Logger log = LoggerFactory.getLogger(BalieafspraakResource.class);

    private static final String ENTITY_NAME = "balieafspraak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BalieafspraakRepository balieafspraakRepository;

    public BalieafspraakResource(BalieafspraakRepository balieafspraakRepository) {
        this.balieafspraakRepository = balieafspraakRepository;
    }

    /**
     * {@code POST  /balieafspraaks} : Create a new balieafspraak.
     *
     * @param balieafspraak the balieafspraak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new balieafspraak, or with status {@code 400 (Bad Request)} if the balieafspraak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Balieafspraak> createBalieafspraak(@RequestBody Balieafspraak balieafspraak) throws URISyntaxException {
        log.debug("REST request to save Balieafspraak : {}", balieafspraak);
        if (balieafspraak.getId() != null) {
            throw new BadRequestAlertException("A new balieafspraak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        balieafspraak = balieafspraakRepository.save(balieafspraak);
        return ResponseEntity.created(new URI("/api/balieafspraaks/" + balieafspraak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, balieafspraak.getId().toString()))
            .body(balieafspraak);
    }

    /**
     * {@code PUT  /balieafspraaks/:id} : Updates an existing balieafspraak.
     *
     * @param id the id of the balieafspraak to save.
     * @param balieafspraak the balieafspraak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balieafspraak,
     * or with status {@code 400 (Bad Request)} if the balieafspraak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the balieafspraak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Balieafspraak> updateBalieafspraak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Balieafspraak balieafspraak
    ) throws URISyntaxException {
        log.debug("REST request to update Balieafspraak : {}, {}", id, balieafspraak);
        if (balieafspraak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balieafspraak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balieafspraakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        balieafspraak = balieafspraakRepository.save(balieafspraak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, balieafspraak.getId().toString()))
            .body(balieafspraak);
    }

    /**
     * {@code PATCH  /balieafspraaks/:id} : Partial updates given fields of an existing balieafspraak, field will ignore if it is null
     *
     * @param id the id of the balieafspraak to save.
     * @param balieafspraak the balieafspraak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balieafspraak,
     * or with status {@code 400 (Bad Request)} if the balieafspraak is not valid,
     * or with status {@code 404 (Not Found)} if the balieafspraak is not found,
     * or with status {@code 500 (Internal Server Error)} if the balieafspraak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Balieafspraak> partialUpdateBalieafspraak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Balieafspraak balieafspraak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Balieafspraak partially : {}, {}", id, balieafspraak);
        if (balieafspraak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balieafspraak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balieafspraakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Balieafspraak> result = balieafspraakRepository
            .findById(balieafspraak.getId())
            .map(existingBalieafspraak -> {
                if (balieafspraak.getEindtijdgepland() != null) {
                    existingBalieafspraak.setEindtijdgepland(balieafspraak.getEindtijdgepland());
                }
                if (balieafspraak.getNotitie() != null) {
                    existingBalieafspraak.setNotitie(balieafspraak.getNotitie());
                }
                if (balieafspraak.getStarttijdgepland() != null) {
                    existingBalieafspraak.setStarttijdgepland(balieafspraak.getStarttijdgepland());
                }
                if (balieafspraak.getTijdaangemaakt() != null) {
                    existingBalieafspraak.setTijdaangemaakt(balieafspraak.getTijdaangemaakt());
                }
                if (balieafspraak.getTijdsduurgepland() != null) {
                    existingBalieafspraak.setTijdsduurgepland(balieafspraak.getTijdsduurgepland());
                }
                if (balieafspraak.getToelichting() != null) {
                    existingBalieafspraak.setToelichting(balieafspraak.getToelichting());
                }
                if (balieafspraak.getWachttijdnastartafspraak() != null) {
                    existingBalieafspraak.setWachttijdnastartafspraak(balieafspraak.getWachttijdnastartafspraak());
                }
                if (balieafspraak.getWachttijdtotaal() != null) {
                    existingBalieafspraak.setWachttijdtotaal(balieafspraak.getWachttijdtotaal());
                }
                if (balieafspraak.getWachttijdvoorstartafspraak() != null) {
                    existingBalieafspraak.setWachttijdvoorstartafspraak(balieafspraak.getWachttijdvoorstartafspraak());
                }
                if (balieafspraak.getWerkelijketijdsduur() != null) {
                    existingBalieafspraak.setWerkelijketijdsduur(balieafspraak.getWerkelijketijdsduur());
                }

                return existingBalieafspraak;
            })
            .map(balieafspraakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, balieafspraak.getId().toString())
        );
    }

    /**
     * {@code GET  /balieafspraaks} : get all the balieafspraaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of balieafspraaks in body.
     */
    @GetMapping("")
    public List<Balieafspraak> getAllBalieafspraaks() {
        log.debug("REST request to get all Balieafspraaks");
        return balieafspraakRepository.findAll();
    }

    /**
     * {@code GET  /balieafspraaks/:id} : get the "id" balieafspraak.
     *
     * @param id the id of the balieafspraak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the balieafspraak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Balieafspraak> getBalieafspraak(@PathVariable("id") Long id) {
        log.debug("REST request to get Balieafspraak : {}", id);
        Optional<Balieafspraak> balieafspraak = balieafspraakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(balieafspraak);
    }

    /**
     * {@code DELETE  /balieafspraaks/:id} : delete the "id" balieafspraak.
     *
     * @param id the id of the balieafspraak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalieafspraak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Balieafspraak : {}", id);
        balieafspraakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
