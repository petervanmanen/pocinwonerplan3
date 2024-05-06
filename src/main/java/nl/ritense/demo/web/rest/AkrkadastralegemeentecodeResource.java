package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Akrkadastralegemeentecode;
import nl.ritense.demo.repository.AkrkadastralegemeentecodeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Akrkadastralegemeentecode}.
 */
@RestController
@RequestMapping("/api/akrkadastralegemeentecodes")
@Transactional
public class AkrkadastralegemeentecodeResource {

    private final Logger log = LoggerFactory.getLogger(AkrkadastralegemeentecodeResource.class);

    private static final String ENTITY_NAME = "akrkadastralegemeentecode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AkrkadastralegemeentecodeRepository akrkadastralegemeentecodeRepository;

    public AkrkadastralegemeentecodeResource(AkrkadastralegemeentecodeRepository akrkadastralegemeentecodeRepository) {
        this.akrkadastralegemeentecodeRepository = akrkadastralegemeentecodeRepository;
    }

    /**
     * {@code POST  /akrkadastralegemeentecodes} : Create a new akrkadastralegemeentecode.
     *
     * @param akrkadastralegemeentecode the akrkadastralegemeentecode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akrkadastralegemeentecode, or with status {@code 400 (Bad Request)} if the akrkadastralegemeentecode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Akrkadastralegemeentecode> createAkrkadastralegemeentecode(
        @RequestBody Akrkadastralegemeentecode akrkadastralegemeentecode
    ) throws URISyntaxException {
        log.debug("REST request to save Akrkadastralegemeentecode : {}", akrkadastralegemeentecode);
        if (akrkadastralegemeentecode.getId() != null) {
            throw new BadRequestAlertException("A new akrkadastralegemeentecode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        akrkadastralegemeentecode = akrkadastralegemeentecodeRepository.save(akrkadastralegemeentecode);
        return ResponseEntity.created(new URI("/api/akrkadastralegemeentecodes/" + akrkadastralegemeentecode.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, akrkadastralegemeentecode.getId().toString())
            )
            .body(akrkadastralegemeentecode);
    }

    /**
     * {@code PUT  /akrkadastralegemeentecodes/:id} : Updates an existing akrkadastralegemeentecode.
     *
     * @param id the id of the akrkadastralegemeentecode to save.
     * @param akrkadastralegemeentecode the akrkadastralegemeentecode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akrkadastralegemeentecode,
     * or with status {@code 400 (Bad Request)} if the akrkadastralegemeentecode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akrkadastralegemeentecode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Akrkadastralegemeentecode> updateAkrkadastralegemeentecode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Akrkadastralegemeentecode akrkadastralegemeentecode
    ) throws URISyntaxException {
        log.debug("REST request to update Akrkadastralegemeentecode : {}, {}", id, akrkadastralegemeentecode);
        if (akrkadastralegemeentecode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akrkadastralegemeentecode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akrkadastralegemeentecodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        akrkadastralegemeentecode = akrkadastralegemeentecodeRepository.save(akrkadastralegemeentecode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, akrkadastralegemeentecode.getId().toString()))
            .body(akrkadastralegemeentecode);
    }

    /**
     * {@code PATCH  /akrkadastralegemeentecodes/:id} : Partial updates given fields of an existing akrkadastralegemeentecode, field will ignore if it is null
     *
     * @param id the id of the akrkadastralegemeentecode to save.
     * @param akrkadastralegemeentecode the akrkadastralegemeentecode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akrkadastralegemeentecode,
     * or with status {@code 400 (Bad Request)} if the akrkadastralegemeentecode is not valid,
     * or with status {@code 404 (Not Found)} if the akrkadastralegemeentecode is not found,
     * or with status {@code 500 (Internal Server Error)} if the akrkadastralegemeentecode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Akrkadastralegemeentecode> partialUpdateAkrkadastralegemeentecode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Akrkadastralegemeentecode akrkadastralegemeentecode
    ) throws URISyntaxException {
        log.debug("REST request to partial update Akrkadastralegemeentecode partially : {}, {}", id, akrkadastralegemeentecode);
        if (akrkadastralegemeentecode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akrkadastralegemeentecode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akrkadastralegemeentecodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Akrkadastralegemeentecode> result = akrkadastralegemeentecodeRepository
            .findById(akrkadastralegemeentecode.getId())
            .map(existingAkrkadastralegemeentecode -> {
                if (akrkadastralegemeentecode.getAkrcode() != null) {
                    existingAkrkadastralegemeentecode.setAkrcode(akrkadastralegemeentecode.getAkrcode());
                }
                if (akrkadastralegemeentecode.getCodeakrkadadastralegemeentecode() != null) {
                    existingAkrkadastralegemeentecode.setCodeakrkadadastralegemeentecode(
                        akrkadastralegemeentecode.getCodeakrkadadastralegemeentecode()
                    );
                }
                if (akrkadastralegemeentecode.getDatumbegingeldigheidakrcode() != null) {
                    existingAkrkadastralegemeentecode.setDatumbegingeldigheidakrcode(
                        akrkadastralegemeentecode.getDatumbegingeldigheidakrcode()
                    );
                }
                if (akrkadastralegemeentecode.getDatumeindegeldigheidakrcode() != null) {
                    existingAkrkadastralegemeentecode.setDatumeindegeldigheidakrcode(
                        akrkadastralegemeentecode.getDatumeindegeldigheidakrcode()
                    );
                }

                return existingAkrkadastralegemeentecode;
            })
            .map(akrkadastralegemeentecodeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, akrkadastralegemeentecode.getId().toString())
        );
    }

    /**
     * {@code GET  /akrkadastralegemeentecodes} : get all the akrkadastralegemeentecodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akrkadastralegemeentecodes in body.
     */
    @GetMapping("")
    public List<Akrkadastralegemeentecode> getAllAkrkadastralegemeentecodes() {
        log.debug("REST request to get all Akrkadastralegemeentecodes");
        return akrkadastralegemeentecodeRepository.findAll();
    }

    /**
     * {@code GET  /akrkadastralegemeentecodes/:id} : get the "id" akrkadastralegemeentecode.
     *
     * @param id the id of the akrkadastralegemeentecode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akrkadastralegemeentecode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Akrkadastralegemeentecode> getAkrkadastralegemeentecode(@PathVariable("id") Long id) {
        log.debug("REST request to get Akrkadastralegemeentecode : {}", id);
        Optional<Akrkadastralegemeentecode> akrkadastralegemeentecode = akrkadastralegemeentecodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(akrkadastralegemeentecode);
    }

    /**
     * {@code DELETE  /akrkadastralegemeentecodes/:id} : delete the "id" akrkadastralegemeentecode.
     *
     * @param id the id of the akrkadastralegemeentecode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAkrkadastralegemeentecode(@PathVariable("id") Long id) {
        log.debug("REST request to delete Akrkadastralegemeentecode : {}", id);
        akrkadastralegemeentecodeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
