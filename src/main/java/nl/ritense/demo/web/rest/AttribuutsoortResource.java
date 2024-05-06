package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Attribuutsoort;
import nl.ritense.demo.repository.AttribuutsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Attribuutsoort}.
 */
@RestController
@RequestMapping("/api/attribuutsoorts")
@Transactional
public class AttribuutsoortResource {

    private final Logger log = LoggerFactory.getLogger(AttribuutsoortResource.class);

    private static final String ENTITY_NAME = "attribuutsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttribuutsoortRepository attribuutsoortRepository;

    public AttribuutsoortResource(AttribuutsoortRepository attribuutsoortRepository) {
        this.attribuutsoortRepository = attribuutsoortRepository;
    }

    /**
     * {@code POST  /attribuutsoorts} : Create a new attribuutsoort.
     *
     * @param attribuutsoort the attribuutsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attribuutsoort, or with status {@code 400 (Bad Request)} if the attribuutsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Attribuutsoort> createAttribuutsoort(@RequestBody Attribuutsoort attribuutsoort) throws URISyntaxException {
        log.debug("REST request to save Attribuutsoort : {}", attribuutsoort);
        if (attribuutsoort.getId() != null) {
            throw new BadRequestAlertException("A new attribuutsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        attribuutsoort = attribuutsoortRepository.save(attribuutsoort);
        return ResponseEntity.created(new URI("/api/attribuutsoorts/" + attribuutsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, attribuutsoort.getId()))
            .body(attribuutsoort);
    }

    /**
     * {@code PUT  /attribuutsoorts/:id} : Updates an existing attribuutsoort.
     *
     * @param id the id of the attribuutsoort to save.
     * @param attribuutsoort the attribuutsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attribuutsoort,
     * or with status {@code 400 (Bad Request)} if the attribuutsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attribuutsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Attribuutsoort> updateAttribuutsoort(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Attribuutsoort attribuutsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Attribuutsoort : {}, {}", id, attribuutsoort);
        if (attribuutsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attribuutsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attribuutsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        attribuutsoort = attribuutsoortRepository.save(attribuutsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attribuutsoort.getId()))
            .body(attribuutsoort);
    }

    /**
     * {@code PATCH  /attribuutsoorts/:id} : Partial updates given fields of an existing attribuutsoort, field will ignore if it is null
     *
     * @param id the id of the attribuutsoort to save.
     * @param attribuutsoort the attribuutsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attribuutsoort,
     * or with status {@code 400 (Bad Request)} if the attribuutsoort is not valid,
     * or with status {@code 404 (Not Found)} if the attribuutsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the attribuutsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Attribuutsoort> partialUpdateAttribuutsoort(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Attribuutsoort attribuutsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Attribuutsoort partially : {}, {}", id, attribuutsoort);
        if (attribuutsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attribuutsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attribuutsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Attribuutsoort> result = attribuutsoortRepository
            .findById(attribuutsoort.getId())
            .map(existingAttribuutsoort -> {
                if (attribuutsoort.getAuthentiek() != null) {
                    existingAttribuutsoort.setAuthentiek(attribuutsoort.getAuthentiek());
                }
                if (attribuutsoort.getDatumopname() != null) {
                    existingAttribuutsoort.setDatumopname(attribuutsoort.getDatumopname());
                }
                if (attribuutsoort.getDefinitie() != null) {
                    existingAttribuutsoort.setDefinitie(attribuutsoort.getDefinitie());
                }
                if (attribuutsoort.getDomein() != null) {
                    existingAttribuutsoort.setDomein(attribuutsoort.getDomein());
                }
                if (attribuutsoort.getEaguid() != null) {
                    existingAttribuutsoort.setEaguid(attribuutsoort.getEaguid());
                }
                if (attribuutsoort.getHerkomst() != null) {
                    existingAttribuutsoort.setHerkomst(attribuutsoort.getHerkomst());
                }
                if (attribuutsoort.getHerkomstdefinitie() != null) {
                    existingAttribuutsoort.setHerkomstdefinitie(attribuutsoort.getHerkomstdefinitie());
                }
                if (attribuutsoort.getIdentificerend() != null) {
                    existingAttribuutsoort.setIdentificerend(attribuutsoort.getIdentificerend());
                }
                if (attribuutsoort.getIndicatieafleidbaar() != null) {
                    existingAttribuutsoort.setIndicatieafleidbaar(attribuutsoort.getIndicatieafleidbaar());
                }
                if (attribuutsoort.getIndicatiematerielehistorie() != null) {
                    existingAttribuutsoort.setIndicatiematerielehistorie(attribuutsoort.getIndicatiematerielehistorie());
                }
                if (attribuutsoort.getKardinaliteit() != null) {
                    existingAttribuutsoort.setKardinaliteit(attribuutsoort.getKardinaliteit());
                }
                if (attribuutsoort.getLengte() != null) {
                    existingAttribuutsoort.setLengte(attribuutsoort.getLengte());
                }
                if (attribuutsoort.getMogelijkgeenwaarde() != null) {
                    existingAttribuutsoort.setMogelijkgeenwaarde(attribuutsoort.getMogelijkgeenwaarde());
                }
                if (attribuutsoort.getNaam() != null) {
                    existingAttribuutsoort.setNaam(attribuutsoort.getNaam());
                }
                if (attribuutsoort.getPatroon() != null) {
                    existingAttribuutsoort.setPatroon(attribuutsoort.getPatroon());
                }
                if (attribuutsoort.getPrecisie() != null) {
                    existingAttribuutsoort.setPrecisie(attribuutsoort.getPrecisie());
                }
                if (attribuutsoort.getStereotype() != null) {
                    existingAttribuutsoort.setStereotype(attribuutsoort.getStereotype());
                }
                if (attribuutsoort.getToelichting() != null) {
                    existingAttribuutsoort.setToelichting(attribuutsoort.getToelichting());
                }

                return existingAttribuutsoort;
            })
            .map(attribuutsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attribuutsoort.getId())
        );
    }

    /**
     * {@code GET  /attribuutsoorts} : get all the attribuutsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attribuutsoorts in body.
     */
    @GetMapping("")
    public List<Attribuutsoort> getAllAttribuutsoorts() {
        log.debug("REST request to get all Attribuutsoorts");
        return attribuutsoortRepository.findAll();
    }

    /**
     * {@code GET  /attribuutsoorts/:id} : get the "id" attribuutsoort.
     *
     * @param id the id of the attribuutsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attribuutsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Attribuutsoort> getAttribuutsoort(@PathVariable("id") String id) {
        log.debug("REST request to get Attribuutsoort : {}", id);
        Optional<Attribuutsoort> attribuutsoort = attribuutsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(attribuutsoort);
    }

    /**
     * {@code DELETE  /attribuutsoorts/:id} : delete the "id" attribuutsoort.
     *
     * @param id the id of the attribuutsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribuutsoort(@PathVariable("id") String id) {
        log.debug("REST request to delete Attribuutsoort : {}", id);
        attribuutsoortRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
