package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Provincie;
import nl.ritense.demo.repository.ProvincieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Provincie}.
 */
@RestController
@RequestMapping("/api/provincies")
@Transactional
public class ProvincieResource {

    private final Logger log = LoggerFactory.getLogger(ProvincieResource.class);

    private static final String ENTITY_NAME = "provincie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvincieRepository provincieRepository;

    public ProvincieResource(ProvincieRepository provincieRepository) {
        this.provincieRepository = provincieRepository;
    }

    /**
     * {@code POST  /provincies} : Create a new provincie.
     *
     * @param provincie the provincie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provincie, or with status {@code 400 (Bad Request)} if the provincie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Provincie> createProvincie(@RequestBody Provincie provincie) throws URISyntaxException {
        log.debug("REST request to save Provincie : {}", provincie);
        if (provincie.getId() != null) {
            throw new BadRequestAlertException("A new provincie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        provincie = provincieRepository.save(provincie);
        return ResponseEntity.created(new URI("/api/provincies/" + provincie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, provincie.getId().toString()))
            .body(provincie);
    }

    /**
     * {@code PUT  /provincies/:id} : Updates an existing provincie.
     *
     * @param id the id of the provincie to save.
     * @param provincie the provincie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provincie,
     * or with status {@code 400 (Bad Request)} if the provincie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provincie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Provincie> updateProvincie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Provincie provincie
    ) throws URISyntaxException {
        log.debug("REST request to update Provincie : {}, {}", id, provincie);
        if (provincie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provincie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provincieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        provincie = provincieRepository.save(provincie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, provincie.getId().toString()))
            .body(provincie);
    }

    /**
     * {@code PATCH  /provincies/:id} : Partial updates given fields of an existing provincie, field will ignore if it is null
     *
     * @param id the id of the provincie to save.
     * @param provincie the provincie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provincie,
     * or with status {@code 400 (Bad Request)} if the provincie is not valid,
     * or with status {@code 404 (Not Found)} if the provincie is not found,
     * or with status {@code 500 (Internal Server Error)} if the provincie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Provincie> partialUpdateProvincie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Provincie provincie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Provincie partially : {}, {}", id, provincie);
        if (provincie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provincie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provincieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Provincie> result = provincieRepository
            .findById(provincie.getId())
            .map(existingProvincie -> {
                if (provincie.getDatumeindeprovincie() != null) {
                    existingProvincie.setDatumeindeprovincie(provincie.getDatumeindeprovincie());
                }
                if (provincie.getDatumingangprovincie() != null) {
                    existingProvincie.setDatumingangprovincie(provincie.getDatumingangprovincie());
                }
                if (provincie.getHoofdstad() != null) {
                    existingProvincie.setHoofdstad(provincie.getHoofdstad());
                }
                if (provincie.getOppervlakte() != null) {
                    existingProvincie.setOppervlakte(provincie.getOppervlakte());
                }
                if (provincie.getOppervlakteland() != null) {
                    existingProvincie.setOppervlakteland(provincie.getOppervlakteland());
                }
                if (provincie.getProvinciecode() != null) {
                    existingProvincie.setProvinciecode(provincie.getProvinciecode());
                }
                if (provincie.getProvincienaam() != null) {
                    existingProvincie.setProvincienaam(provincie.getProvincienaam());
                }

                return existingProvincie;
            })
            .map(provincieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, provincie.getId().toString())
        );
    }

    /**
     * {@code GET  /provincies} : get all the provincies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provincies in body.
     */
    @GetMapping("")
    public List<Provincie> getAllProvincies() {
        log.debug("REST request to get all Provincies");
        return provincieRepository.findAll();
    }

    /**
     * {@code GET  /provincies/:id} : get the "id" provincie.
     *
     * @param id the id of the provincie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provincie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Provincie> getProvincie(@PathVariable("id") Long id) {
        log.debug("REST request to get Provincie : {}", id);
        Optional<Provincie> provincie = provincieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(provincie);
    }

    /**
     * {@code DELETE  /provincies/:id} : delete the "id" provincie.
     *
     * @param id the id of the provincie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvincie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Provincie : {}", id);
        provincieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
