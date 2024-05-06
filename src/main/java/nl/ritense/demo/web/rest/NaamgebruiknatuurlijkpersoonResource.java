package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Naamgebruiknatuurlijkpersoon;
import nl.ritense.demo.repository.NaamgebruiknatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Naamgebruiknatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/naamgebruiknatuurlijkpersoons")
@Transactional
public class NaamgebruiknatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(NaamgebruiknatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "naamgebruiknatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaamgebruiknatuurlijkpersoonRepository naamgebruiknatuurlijkpersoonRepository;

    public NaamgebruiknatuurlijkpersoonResource(NaamgebruiknatuurlijkpersoonRepository naamgebruiknatuurlijkpersoonRepository) {
        this.naamgebruiknatuurlijkpersoonRepository = naamgebruiknatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /naamgebruiknatuurlijkpersoons} : Create a new naamgebruiknatuurlijkpersoon.
     *
     * @param naamgebruiknatuurlijkpersoon the naamgebruiknatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naamgebruiknatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the naamgebruiknatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Naamgebruiknatuurlijkpersoon> createNaamgebruiknatuurlijkpersoon(
        @RequestBody Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Naamgebruiknatuurlijkpersoon : {}", naamgebruiknatuurlijkpersoon);
        if (naamgebruiknatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException("A new naamgebruiknatuurlijkpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        naamgebruiknatuurlijkpersoon = naamgebruiknatuurlijkpersoonRepository.save(naamgebruiknatuurlijkpersoon);
        return ResponseEntity.created(new URI("/api/naamgebruiknatuurlijkpersoons/" + naamgebruiknatuurlijkpersoon.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, naamgebruiknatuurlijkpersoon.getId().toString())
            )
            .body(naamgebruiknatuurlijkpersoon);
    }

    /**
     * {@code PUT  /naamgebruiknatuurlijkpersoons/:id} : Updates an existing naamgebruiknatuurlijkpersoon.
     *
     * @param id the id of the naamgebruiknatuurlijkpersoon to save.
     * @param naamgebruiknatuurlijkpersoon the naamgebruiknatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naamgebruiknatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the naamgebruiknatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naamgebruiknatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Naamgebruiknatuurlijkpersoon> updateNaamgebruiknatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Naamgebruiknatuurlijkpersoon : {}, {}", id, naamgebruiknatuurlijkpersoon);
        if (naamgebruiknatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naamgebruiknatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naamgebruiknatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        naamgebruiknatuurlijkpersoon = naamgebruiknatuurlijkpersoonRepository.save(naamgebruiknatuurlijkpersoon);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naamgebruiknatuurlijkpersoon.getId().toString())
            )
            .body(naamgebruiknatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /naamgebruiknatuurlijkpersoons/:id} : Partial updates given fields of an existing naamgebruiknatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the naamgebruiknatuurlijkpersoon to save.
     * @param naamgebruiknatuurlijkpersoon the naamgebruiknatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naamgebruiknatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the naamgebruiknatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the naamgebruiknatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the naamgebruiknatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Naamgebruiknatuurlijkpersoon> partialUpdateNaamgebruiknatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Naamgebruiknatuurlijkpersoon partially : {}, {}", id, naamgebruiknatuurlijkpersoon);
        if (naamgebruiknatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naamgebruiknatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naamgebruiknatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Naamgebruiknatuurlijkpersoon> result = naamgebruiknatuurlijkpersoonRepository
            .findById(naamgebruiknatuurlijkpersoon.getId())
            .map(existingNaamgebruiknatuurlijkpersoon -> {
                if (naamgebruiknatuurlijkpersoon.getAanhefaanschrijving() != null) {
                    existingNaamgebruiknatuurlijkpersoon.setAanhefaanschrijving(naamgebruiknatuurlijkpersoon.getAanhefaanschrijving());
                }
                if (naamgebruiknatuurlijkpersoon.getAdellijketitelnaamgebruik() != null) {
                    existingNaamgebruiknatuurlijkpersoon.setAdellijketitelnaamgebruik(
                        naamgebruiknatuurlijkpersoon.getAdellijketitelnaamgebruik()
                    );
                }
                if (naamgebruiknatuurlijkpersoon.getGeslachtsnaamstamnaamgebruik() != null) {
                    existingNaamgebruiknatuurlijkpersoon.setGeslachtsnaamstamnaamgebruik(
                        naamgebruiknatuurlijkpersoon.getGeslachtsnaamstamnaamgebruik()
                    );
                }

                return existingNaamgebruiknatuurlijkpersoon;
            })
            .map(naamgebruiknatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naamgebruiknatuurlijkpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /naamgebruiknatuurlijkpersoons} : get all the naamgebruiknatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naamgebruiknatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Naamgebruiknatuurlijkpersoon> getAllNaamgebruiknatuurlijkpersoons() {
        log.debug("REST request to get all Naamgebruiknatuurlijkpersoons");
        return naamgebruiknatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /naamgebruiknatuurlijkpersoons/:id} : get the "id" naamgebruiknatuurlijkpersoon.
     *
     * @param id the id of the naamgebruiknatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naamgebruiknatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Naamgebruiknatuurlijkpersoon> getNaamgebruiknatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Naamgebruiknatuurlijkpersoon : {}", id);
        Optional<Naamgebruiknatuurlijkpersoon> naamgebruiknatuurlijkpersoon = naamgebruiknatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(naamgebruiknatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /naamgebruiknatuurlijkpersoons/:id} : delete the "id" naamgebruiknatuurlijkpersoon.
     *
     * @param id the id of the naamgebruiknatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNaamgebruiknatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Naamgebruiknatuurlijkpersoon : {}", id);
        naamgebruiknatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
