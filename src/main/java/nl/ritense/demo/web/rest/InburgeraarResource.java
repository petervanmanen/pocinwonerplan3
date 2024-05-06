package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Inburgeraar;
import nl.ritense.demo.repository.InburgeraarRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inburgeraar}.
 */
@RestController
@RequestMapping("/api/inburgeraars")
@Transactional
public class InburgeraarResource {

    private final Logger log = LoggerFactory.getLogger(InburgeraarResource.class);

    private static final String ENTITY_NAME = "inburgeraar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InburgeraarRepository inburgeraarRepository;

    public InburgeraarResource(InburgeraarRepository inburgeraarRepository) {
        this.inburgeraarRepository = inburgeraarRepository;
    }

    /**
     * {@code POST  /inburgeraars} : Create a new inburgeraar.
     *
     * @param inburgeraar the inburgeraar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inburgeraar, or with status {@code 400 (Bad Request)} if the inburgeraar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inburgeraar> createInburgeraar(@RequestBody Inburgeraar inburgeraar) throws URISyntaxException {
        log.debug("REST request to save Inburgeraar : {}", inburgeraar);
        if (inburgeraar.getId() != null) {
            throw new BadRequestAlertException("A new inburgeraar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inburgeraar = inburgeraarRepository.save(inburgeraar);
        return ResponseEntity.created(new URI("/api/inburgeraars/" + inburgeraar.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inburgeraar.getId().toString()))
            .body(inburgeraar);
    }

    /**
     * {@code PUT  /inburgeraars/:id} : Updates an existing inburgeraar.
     *
     * @param id the id of the inburgeraar to save.
     * @param inburgeraar the inburgeraar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inburgeraar,
     * or with status {@code 400 (Bad Request)} if the inburgeraar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inburgeraar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inburgeraar> updateInburgeraar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inburgeraar inburgeraar
    ) throws URISyntaxException {
        log.debug("REST request to update Inburgeraar : {}, {}", id, inburgeraar);
        if (inburgeraar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inburgeraar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inburgeraarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inburgeraar = inburgeraarRepository.save(inburgeraar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inburgeraar.getId().toString()))
            .body(inburgeraar);
    }

    /**
     * {@code PATCH  /inburgeraars/:id} : Partial updates given fields of an existing inburgeraar, field will ignore if it is null
     *
     * @param id the id of the inburgeraar to save.
     * @param inburgeraar the inburgeraar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inburgeraar,
     * or with status {@code 400 (Bad Request)} if the inburgeraar is not valid,
     * or with status {@code 404 (Not Found)} if the inburgeraar is not found,
     * or with status {@code 500 (Internal Server Error)} if the inburgeraar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inburgeraar> partialUpdateInburgeraar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inburgeraar inburgeraar
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inburgeraar partially : {}, {}", id, inburgeraar);
        if (inburgeraar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inburgeraar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inburgeraarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inburgeraar> result = inburgeraarRepository
            .findById(inburgeraar.getId())
            .map(existingInburgeraar -> {
                if (inburgeraar.getDoelgroep() != null) {
                    existingInburgeraar.setDoelgroep(inburgeraar.getDoelgroep());
                }
                if (inburgeraar.getGedetailleerdedoelgroep() != null) {
                    existingInburgeraar.setGedetailleerdedoelgroep(inburgeraar.getGedetailleerdedoelgroep());
                }

                return existingInburgeraar;
            })
            .map(inburgeraarRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inburgeraar.getId().toString())
        );
    }

    /**
     * {@code GET  /inburgeraars} : get all the inburgeraars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inburgeraars in body.
     */
    @GetMapping("")
    public List<Inburgeraar> getAllInburgeraars() {
        log.debug("REST request to get all Inburgeraars");
        return inburgeraarRepository.findAll();
    }

    /**
     * {@code GET  /inburgeraars/:id} : get the "id" inburgeraar.
     *
     * @param id the id of the inburgeraar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inburgeraar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inburgeraar> getInburgeraar(@PathVariable("id") Long id) {
        log.debug("REST request to get Inburgeraar : {}", id);
        Optional<Inburgeraar> inburgeraar = inburgeraarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inburgeraar);
    }

    /**
     * {@code DELETE  /inburgeraars/:id} : delete the "id" inburgeraar.
     *
     * @param id the id of the inburgeraar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInburgeraar(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inburgeraar : {}", id);
        inburgeraarRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
