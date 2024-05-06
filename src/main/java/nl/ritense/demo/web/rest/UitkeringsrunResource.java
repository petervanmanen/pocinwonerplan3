package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Uitkeringsrun;
import nl.ritense.demo.repository.UitkeringsrunRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitkeringsrun}.
 */
@RestController
@RequestMapping("/api/uitkeringsruns")
@Transactional
public class UitkeringsrunResource {

    private final Logger log = LoggerFactory.getLogger(UitkeringsrunResource.class);

    private static final String ENTITY_NAME = "uitkeringsrun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitkeringsrunRepository uitkeringsrunRepository;

    public UitkeringsrunResource(UitkeringsrunRepository uitkeringsrunRepository) {
        this.uitkeringsrunRepository = uitkeringsrunRepository;
    }

    /**
     * {@code POST  /uitkeringsruns} : Create a new uitkeringsrun.
     *
     * @param uitkeringsrun the uitkeringsrun to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitkeringsrun, or with status {@code 400 (Bad Request)} if the uitkeringsrun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitkeringsrun> createUitkeringsrun(@Valid @RequestBody Uitkeringsrun uitkeringsrun) throws URISyntaxException {
        log.debug("REST request to save Uitkeringsrun : {}", uitkeringsrun);
        if (uitkeringsrun.getId() != null) {
            throw new BadRequestAlertException("A new uitkeringsrun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitkeringsrun = uitkeringsrunRepository.save(uitkeringsrun);
        return ResponseEntity.created(new URI("/api/uitkeringsruns/" + uitkeringsrun.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitkeringsrun.getId().toString()))
            .body(uitkeringsrun);
    }

    /**
     * {@code PUT  /uitkeringsruns/:id} : Updates an existing uitkeringsrun.
     *
     * @param id the id of the uitkeringsrun to save.
     * @param uitkeringsrun the uitkeringsrun to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitkeringsrun,
     * or with status {@code 400 (Bad Request)} if the uitkeringsrun is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uitkeringsrun couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uitkeringsrun> updateUitkeringsrun(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Uitkeringsrun uitkeringsrun
    ) throws URISyntaxException {
        log.debug("REST request to update Uitkeringsrun : {}, {}", id, uitkeringsrun);
        if (uitkeringsrun.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitkeringsrun.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitkeringsrunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uitkeringsrun = uitkeringsrunRepository.save(uitkeringsrun);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitkeringsrun.getId().toString()))
            .body(uitkeringsrun);
    }

    /**
     * {@code PATCH  /uitkeringsruns/:id} : Partial updates given fields of an existing uitkeringsrun, field will ignore if it is null
     *
     * @param id the id of the uitkeringsrun to save.
     * @param uitkeringsrun the uitkeringsrun to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitkeringsrun,
     * or with status {@code 400 (Bad Request)} if the uitkeringsrun is not valid,
     * or with status {@code 404 (Not Found)} if the uitkeringsrun is not found,
     * or with status {@code 500 (Internal Server Error)} if the uitkeringsrun couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uitkeringsrun> partialUpdateUitkeringsrun(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Uitkeringsrun uitkeringsrun
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uitkeringsrun partially : {}, {}", id, uitkeringsrun);
        if (uitkeringsrun.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitkeringsrun.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitkeringsrunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uitkeringsrun> result = uitkeringsrunRepository
            .findById(uitkeringsrun.getId())
            .map(existingUitkeringsrun -> {
                if (uitkeringsrun.getDatumrun() != null) {
                    existingUitkeringsrun.setDatumrun(uitkeringsrun.getDatumrun());
                }
                if (uitkeringsrun.getFrequentie() != null) {
                    existingUitkeringsrun.setFrequentie(uitkeringsrun.getFrequentie());
                }
                if (uitkeringsrun.getPerioderun() != null) {
                    existingUitkeringsrun.setPerioderun(uitkeringsrun.getPerioderun());
                }
                if (uitkeringsrun.getSoortrun() != null) {
                    existingUitkeringsrun.setSoortrun(uitkeringsrun.getSoortrun());
                }

                return existingUitkeringsrun;
            })
            .map(uitkeringsrunRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitkeringsrun.getId().toString())
        );
    }

    /**
     * {@code GET  /uitkeringsruns} : get all the uitkeringsruns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitkeringsruns in body.
     */
    @GetMapping("")
    public List<Uitkeringsrun> getAllUitkeringsruns() {
        log.debug("REST request to get all Uitkeringsruns");
        return uitkeringsrunRepository.findAll();
    }

    /**
     * {@code GET  /uitkeringsruns/:id} : get the "id" uitkeringsrun.
     *
     * @param id the id of the uitkeringsrun to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitkeringsrun, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitkeringsrun> getUitkeringsrun(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitkeringsrun : {}", id);
        Optional<Uitkeringsrun> uitkeringsrun = uitkeringsrunRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitkeringsrun);
    }

    /**
     * {@code DELETE  /uitkeringsruns/:id} : delete the "id" uitkeringsrun.
     *
     * @param id the id of the uitkeringsrun to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitkeringsrun(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitkeringsrun : {}", id);
        uitkeringsrunRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
