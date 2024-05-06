package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Naamnatuurlijkpersoon;
import nl.ritense.demo.repository.NaamnatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Naamnatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/naamnatuurlijkpersoons")
@Transactional
public class NaamnatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(NaamnatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "naamnatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaamnatuurlijkpersoonRepository naamnatuurlijkpersoonRepository;

    public NaamnatuurlijkpersoonResource(NaamnatuurlijkpersoonRepository naamnatuurlijkpersoonRepository) {
        this.naamnatuurlijkpersoonRepository = naamnatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /naamnatuurlijkpersoons} : Create a new naamnatuurlijkpersoon.
     *
     * @param naamnatuurlijkpersoon the naamnatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naamnatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the naamnatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Naamnatuurlijkpersoon> createNaamnatuurlijkpersoon(
        @Valid @RequestBody Naamnatuurlijkpersoon naamnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Naamnatuurlijkpersoon : {}", naamnatuurlijkpersoon);
        if (naamnatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException("A new naamnatuurlijkpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        naamnatuurlijkpersoon = naamnatuurlijkpersoonRepository.save(naamnatuurlijkpersoon);
        return ResponseEntity.created(new URI("/api/naamnatuurlijkpersoons/" + naamnatuurlijkpersoon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, naamnatuurlijkpersoon.getId().toString()))
            .body(naamnatuurlijkpersoon);
    }

    /**
     * {@code PUT  /naamnatuurlijkpersoons/:id} : Updates an existing naamnatuurlijkpersoon.
     *
     * @param id the id of the naamnatuurlijkpersoon to save.
     * @param naamnatuurlijkpersoon the naamnatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naamnatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the naamnatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naamnatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Naamnatuurlijkpersoon> updateNaamnatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Naamnatuurlijkpersoon naamnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Naamnatuurlijkpersoon : {}, {}", id, naamnatuurlijkpersoon);
        if (naamnatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naamnatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naamnatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        naamnatuurlijkpersoon = naamnatuurlijkpersoonRepository.save(naamnatuurlijkpersoon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naamnatuurlijkpersoon.getId().toString()))
            .body(naamnatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /naamnatuurlijkpersoons/:id} : Partial updates given fields of an existing naamnatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the naamnatuurlijkpersoon to save.
     * @param naamnatuurlijkpersoon the naamnatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naamnatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the naamnatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the naamnatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the naamnatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Naamnatuurlijkpersoon> partialUpdateNaamnatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Naamnatuurlijkpersoon naamnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Naamnatuurlijkpersoon partially : {}, {}", id, naamnatuurlijkpersoon);
        if (naamnatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naamnatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naamnatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Naamnatuurlijkpersoon> result = naamnatuurlijkpersoonRepository
            .findById(naamnatuurlijkpersoon.getId())
            .map(existingNaamnatuurlijkpersoon -> {
                if (naamnatuurlijkpersoon.getAdellijketitelofpredikaat() != null) {
                    existingNaamnatuurlijkpersoon.setAdellijketitelofpredikaat(naamnatuurlijkpersoon.getAdellijketitelofpredikaat());
                }
                if (naamnatuurlijkpersoon.getGeslachtsnaam() != null) {
                    existingNaamnatuurlijkpersoon.setGeslachtsnaam(naamnatuurlijkpersoon.getGeslachtsnaam());
                }
                if (naamnatuurlijkpersoon.getVoornamen() != null) {
                    existingNaamnatuurlijkpersoon.setVoornamen(naamnatuurlijkpersoon.getVoornamen());
                }
                if (naamnatuurlijkpersoon.getVoorvoegselgeslachtsnaam() != null) {
                    existingNaamnatuurlijkpersoon.setVoorvoegselgeslachtsnaam(naamnatuurlijkpersoon.getVoorvoegselgeslachtsnaam());
                }

                return existingNaamnatuurlijkpersoon;
            })
            .map(naamnatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naamnatuurlijkpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /naamnatuurlijkpersoons} : get all the naamnatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naamnatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Naamnatuurlijkpersoon> getAllNaamnatuurlijkpersoons() {
        log.debug("REST request to get all Naamnatuurlijkpersoons");
        return naamnatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /naamnatuurlijkpersoons/:id} : get the "id" naamnatuurlijkpersoon.
     *
     * @param id the id of the naamnatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naamnatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Naamnatuurlijkpersoon> getNaamnatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Naamnatuurlijkpersoon : {}", id);
        Optional<Naamnatuurlijkpersoon> naamnatuurlijkpersoon = naamnatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(naamnatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /naamnatuurlijkpersoons/:id} : delete the "id" naamnatuurlijkpersoon.
     *
     * @param id the id of the naamnatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNaamnatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Naamnatuurlijkpersoon : {}", id);
        naamnatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
