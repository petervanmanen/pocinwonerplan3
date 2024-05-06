package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Onbestemdadres;
import nl.ritense.demo.repository.OnbestemdadresRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onbestemdadres}.
 */
@RestController
@RequestMapping("/api/onbestemdadres")
@Transactional
public class OnbestemdadresResource {

    private final Logger log = LoggerFactory.getLogger(OnbestemdadresResource.class);

    private static final String ENTITY_NAME = "onbestemdadres";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnbestemdadresRepository onbestemdadresRepository;

    public OnbestemdadresResource(OnbestemdadresRepository onbestemdadresRepository) {
        this.onbestemdadresRepository = onbestemdadresRepository;
    }

    /**
     * {@code POST  /onbestemdadres} : Create a new onbestemdadres.
     *
     * @param onbestemdadres the onbestemdadres to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onbestemdadres, or with status {@code 400 (Bad Request)} if the onbestemdadres has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onbestemdadres> createOnbestemdadres(@RequestBody Onbestemdadres onbestemdadres) throws URISyntaxException {
        log.debug("REST request to save Onbestemdadres : {}", onbestemdadres);
        if (onbestemdadres.getId() != null) {
            throw new BadRequestAlertException("A new onbestemdadres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onbestemdadres = onbestemdadresRepository.save(onbestemdadres);
        return ResponseEntity.created(new URI("/api/onbestemdadres/" + onbestemdadres.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onbestemdadres.getId().toString()))
            .body(onbestemdadres);
    }

    /**
     * {@code PUT  /onbestemdadres/:id} : Updates an existing onbestemdadres.
     *
     * @param id the id of the onbestemdadres to save.
     * @param onbestemdadres the onbestemdadres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onbestemdadres,
     * or with status {@code 400 (Bad Request)} if the onbestemdadres is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onbestemdadres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Onbestemdadres> updateOnbestemdadres(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Onbestemdadres onbestemdadres
    ) throws URISyntaxException {
        log.debug("REST request to update Onbestemdadres : {}, {}", id, onbestemdadres);
        if (onbestemdadres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onbestemdadres.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onbestemdadresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        onbestemdadres = onbestemdadresRepository.save(onbestemdadres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onbestemdadres.getId().toString()))
            .body(onbestemdadres);
    }

    /**
     * {@code PATCH  /onbestemdadres/:id} : Partial updates given fields of an existing onbestemdadres, field will ignore if it is null
     *
     * @param id the id of the onbestemdadres to save.
     * @param onbestemdadres the onbestemdadres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onbestemdadres,
     * or with status {@code 400 (Bad Request)} if the onbestemdadres is not valid,
     * or with status {@code 404 (Not Found)} if the onbestemdadres is not found,
     * or with status {@code 500 (Internal Server Error)} if the onbestemdadres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Onbestemdadres> partialUpdateOnbestemdadres(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Onbestemdadres onbestemdadres
    ) throws URISyntaxException {
        log.debug("REST request to partial update Onbestemdadres partially : {}, {}", id, onbestemdadres);
        if (onbestemdadres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onbestemdadres.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onbestemdadresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Onbestemdadres> result = onbestemdadresRepository
            .findById(onbestemdadres.getId())
            .map(existingOnbestemdadres -> {
                if (onbestemdadres.getHuisletter() != null) {
                    existingOnbestemdadres.setHuisletter(onbestemdadres.getHuisletter());
                }
                if (onbestemdadres.getHuisnummer() != null) {
                    existingOnbestemdadres.setHuisnummer(onbestemdadres.getHuisnummer());
                }
                if (onbestemdadres.getHuisnummertoevoeging() != null) {
                    existingOnbestemdadres.setHuisnummertoevoeging(onbestemdadres.getHuisnummertoevoeging());
                }
                if (onbestemdadres.getPostcode() != null) {
                    existingOnbestemdadres.setPostcode(onbestemdadres.getPostcode());
                }
                if (onbestemdadres.getStraatnaam() != null) {
                    existingOnbestemdadres.setStraatnaam(onbestemdadres.getStraatnaam());
                }

                return existingOnbestemdadres;
            })
            .map(onbestemdadresRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onbestemdadres.getId().toString())
        );
    }

    /**
     * {@code GET  /onbestemdadres} : get all the onbestemdadres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onbestemdadres in body.
     */
    @GetMapping("")
    public List<Onbestemdadres> getAllOnbestemdadres() {
        log.debug("REST request to get all Onbestemdadres");
        return onbestemdadresRepository.findAll();
    }

    /**
     * {@code GET  /onbestemdadres/:id} : get the "id" onbestemdadres.
     *
     * @param id the id of the onbestemdadres to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onbestemdadres, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onbestemdadres> getOnbestemdadres(@PathVariable("id") Long id) {
        log.debug("REST request to get Onbestemdadres : {}", id);
        Optional<Onbestemdadres> onbestemdadres = onbestemdadresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onbestemdadres);
    }

    /**
     * {@code DELETE  /onbestemdadres/:id} : delete the "id" onbestemdadres.
     *
     * @param id the id of the onbestemdadres to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnbestemdadres(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onbestemdadres : {}", id);
        onbestemdadresRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
