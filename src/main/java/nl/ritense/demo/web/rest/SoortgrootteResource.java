package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Soortgrootte;
import nl.ritense.demo.repository.SoortgrootteRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Soortgrootte}.
 */
@RestController
@RequestMapping("/api/soortgroottes")
@Transactional
public class SoortgrootteResource {

    private final Logger log = LoggerFactory.getLogger(SoortgrootteResource.class);

    private static final String ENTITY_NAME = "soortgrootte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoortgrootteRepository soortgrootteRepository;

    public SoortgrootteResource(SoortgrootteRepository soortgrootteRepository) {
        this.soortgrootteRepository = soortgrootteRepository;
    }

    /**
     * {@code POST  /soortgroottes} : Create a new soortgrootte.
     *
     * @param soortgrootte the soortgrootte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soortgrootte, or with status {@code 400 (Bad Request)} if the soortgrootte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Soortgrootte> createSoortgrootte(@RequestBody Soortgrootte soortgrootte) throws URISyntaxException {
        log.debug("REST request to save Soortgrootte : {}", soortgrootte);
        if (soortgrootte.getId() != null) {
            throw new BadRequestAlertException("A new soortgrootte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        soortgrootte = soortgrootteRepository.save(soortgrootte);
        return ResponseEntity.created(new URI("/api/soortgroottes/" + soortgrootte.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, soortgrootte.getId().toString()))
            .body(soortgrootte);
    }

    /**
     * {@code PUT  /soortgroottes/:id} : Updates an existing soortgrootte.
     *
     * @param id the id of the soortgrootte to save.
     * @param soortgrootte the soortgrootte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortgrootte,
     * or with status {@code 400 (Bad Request)} if the soortgrootte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soortgrootte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soortgrootte> updateSoortgrootte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortgrootte soortgrootte
    ) throws URISyntaxException {
        log.debug("REST request to update Soortgrootte : {}, {}", id, soortgrootte);
        if (soortgrootte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortgrootte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortgrootteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        soortgrootte = soortgrootteRepository.save(soortgrootte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortgrootte.getId().toString()))
            .body(soortgrootte);
    }

    /**
     * {@code PATCH  /soortgroottes/:id} : Partial updates given fields of an existing soortgrootte, field will ignore if it is null
     *
     * @param id the id of the soortgrootte to save.
     * @param soortgrootte the soortgrootte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortgrootte,
     * or with status {@code 400 (Bad Request)} if the soortgrootte is not valid,
     * or with status {@code 404 (Not Found)} if the soortgrootte is not found,
     * or with status {@code 500 (Internal Server Error)} if the soortgrootte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Soortgrootte> partialUpdateSoortgrootte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortgrootte soortgrootte
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soortgrootte partially : {}, {}", id, soortgrootte);
        if (soortgrootte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortgrootte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortgrootteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Soortgrootte> result = soortgrootteRepository
            .findById(soortgrootte.getId())
            .map(existingSoortgrootte -> {
                if (soortgrootte.getCodesoortgrootte() != null) {
                    existingSoortgrootte.setCodesoortgrootte(soortgrootte.getCodesoortgrootte());
                }
                if (soortgrootte.getDatumbegingeldigheidsoortgrootte() != null) {
                    existingSoortgrootte.setDatumbegingeldigheidsoortgrootte(soortgrootte.getDatumbegingeldigheidsoortgrootte());
                }
                if (soortgrootte.getDatumeindegeldigheidsoortgrootte() != null) {
                    existingSoortgrootte.setDatumeindegeldigheidsoortgrootte(soortgrootte.getDatumeindegeldigheidsoortgrootte());
                }
                if (soortgrootte.getNaamsoortgrootte() != null) {
                    existingSoortgrootte.setNaamsoortgrootte(soortgrootte.getNaamsoortgrootte());
                }

                return existingSoortgrootte;
            })
            .map(soortgrootteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortgrootte.getId().toString())
        );
    }

    /**
     * {@code GET  /soortgroottes} : get all the soortgroottes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soortgroottes in body.
     */
    @GetMapping("")
    public List<Soortgrootte> getAllSoortgroottes() {
        log.debug("REST request to get all Soortgroottes");
        return soortgrootteRepository.findAll();
    }

    /**
     * {@code GET  /soortgroottes/:id} : get the "id" soortgrootte.
     *
     * @param id the id of the soortgrootte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soortgrootte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soortgrootte> getSoortgrootte(@PathVariable("id") Long id) {
        log.debug("REST request to get Soortgrootte : {}", id);
        Optional<Soortgrootte> soortgrootte = soortgrootteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soortgrootte);
    }

    /**
     * {@code DELETE  /soortgroottes/:id} : delete the "id" soortgrootte.
     *
     * @param id the id of the soortgrootte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoortgrootte(@PathVariable("id") Long id) {
        log.debug("REST request to delete Soortgrootte : {}", id);
        soortgrootteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
