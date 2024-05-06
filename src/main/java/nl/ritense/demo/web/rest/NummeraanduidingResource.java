package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Nummeraanduiding;
import nl.ritense.demo.repository.NummeraanduidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Nummeraanduiding}.
 */
@RestController
@RequestMapping("/api/nummeraanduidings")
@Transactional
public class NummeraanduidingResource {

    private final Logger log = LoggerFactory.getLogger(NummeraanduidingResource.class);

    private static final String ENTITY_NAME = "nummeraanduiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NummeraanduidingRepository nummeraanduidingRepository;

    public NummeraanduidingResource(NummeraanduidingRepository nummeraanduidingRepository) {
        this.nummeraanduidingRepository = nummeraanduidingRepository;
    }

    /**
     * {@code POST  /nummeraanduidings} : Create a new nummeraanduiding.
     *
     * @param nummeraanduiding the nummeraanduiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nummeraanduiding, or with status {@code 400 (Bad Request)} if the nummeraanduiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nummeraanduiding> createNummeraanduiding(@Valid @RequestBody Nummeraanduiding nummeraanduiding)
        throws URISyntaxException {
        log.debug("REST request to save Nummeraanduiding : {}", nummeraanduiding);
        if (nummeraanduiding.getId() != null) {
            throw new BadRequestAlertException("A new nummeraanduiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nummeraanduiding = nummeraanduidingRepository.save(nummeraanduiding);
        return ResponseEntity.created(new URI("/api/nummeraanduidings/" + nummeraanduiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, nummeraanduiding.getId().toString()))
            .body(nummeraanduiding);
    }

    /**
     * {@code PUT  /nummeraanduidings/:id} : Updates an existing nummeraanduiding.
     *
     * @param id the id of the nummeraanduiding to save.
     * @param nummeraanduiding the nummeraanduiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nummeraanduiding,
     * or with status {@code 400 (Bad Request)} if the nummeraanduiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nummeraanduiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nummeraanduiding> updateNummeraanduiding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Nummeraanduiding nummeraanduiding
    ) throws URISyntaxException {
        log.debug("REST request to update Nummeraanduiding : {}, {}", id, nummeraanduiding);
        if (nummeraanduiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nummeraanduiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nummeraanduidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nummeraanduiding = nummeraanduidingRepository.save(nummeraanduiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nummeraanduiding.getId().toString()))
            .body(nummeraanduiding);
    }

    /**
     * {@code PATCH  /nummeraanduidings/:id} : Partial updates given fields of an existing nummeraanduiding, field will ignore if it is null
     *
     * @param id the id of the nummeraanduiding to save.
     * @param nummeraanduiding the nummeraanduiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nummeraanduiding,
     * or with status {@code 400 (Bad Request)} if the nummeraanduiding is not valid,
     * or with status {@code 404 (Not Found)} if the nummeraanduiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the nummeraanduiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nummeraanduiding> partialUpdateNummeraanduiding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Nummeraanduiding nummeraanduiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nummeraanduiding partially : {}, {}", id, nummeraanduiding);
        if (nummeraanduiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nummeraanduiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nummeraanduidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nummeraanduiding> result = nummeraanduidingRepository
            .findById(nummeraanduiding.getId())
            .map(existingNummeraanduiding -> {
                if (nummeraanduiding.getDatumbegingeldigheid() != null) {
                    existingNummeraanduiding.setDatumbegingeldigheid(nummeraanduiding.getDatumbegingeldigheid());
                }
                if (nummeraanduiding.getDatumeinde() != null) {
                    existingNummeraanduiding.setDatumeinde(nummeraanduiding.getDatumeinde());
                }
                if (nummeraanduiding.getDatumeindegeldigheid() != null) {
                    existingNummeraanduiding.setDatumeindegeldigheid(nummeraanduiding.getDatumeindegeldigheid());
                }
                if (nummeraanduiding.getDatumingang() != null) {
                    existingNummeraanduiding.setDatumingang(nummeraanduiding.getDatumingang());
                }
                if (nummeraanduiding.getGeconstateerd() != null) {
                    existingNummeraanduiding.setGeconstateerd(nummeraanduiding.getGeconstateerd());
                }
                if (nummeraanduiding.getGeometrie() != null) {
                    existingNummeraanduiding.setGeometrie(nummeraanduiding.getGeometrie());
                }
                if (nummeraanduiding.getHuisletter() != null) {
                    existingNummeraanduiding.setHuisletter(nummeraanduiding.getHuisletter());
                }
                if (nummeraanduiding.getHuisnummer() != null) {
                    existingNummeraanduiding.setHuisnummer(nummeraanduiding.getHuisnummer());
                }
                if (nummeraanduiding.getHuisnummertoevoeging() != null) {
                    existingNummeraanduiding.setHuisnummertoevoeging(nummeraanduiding.getHuisnummertoevoeging());
                }
                if (nummeraanduiding.getIdentificatie() != null) {
                    existingNummeraanduiding.setIdentificatie(nummeraanduiding.getIdentificatie());
                }
                if (nummeraanduiding.getInonderzoek() != null) {
                    existingNummeraanduiding.setInonderzoek(nummeraanduiding.getInonderzoek());
                }
                if (nummeraanduiding.getPostcode() != null) {
                    existingNummeraanduiding.setPostcode(nummeraanduiding.getPostcode());
                }
                if (nummeraanduiding.getStatus() != null) {
                    existingNummeraanduiding.setStatus(nummeraanduiding.getStatus());
                }
                if (nummeraanduiding.getTypeadresseerbaarobject() != null) {
                    existingNummeraanduiding.setTypeadresseerbaarobject(nummeraanduiding.getTypeadresseerbaarobject());
                }
                if (nummeraanduiding.getVersie() != null) {
                    existingNummeraanduiding.setVersie(nummeraanduiding.getVersie());
                }

                return existingNummeraanduiding;
            })
            .map(nummeraanduidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nummeraanduiding.getId().toString())
        );
    }

    /**
     * {@code GET  /nummeraanduidings} : get all the nummeraanduidings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nummeraanduidings in body.
     */
    @GetMapping("")
    public List<Nummeraanduiding> getAllNummeraanduidings(
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("verwijstnaaradresaanduiding-is-null".equals(filter)) {
            log.debug("REST request to get all Nummeraanduidings where verwijstnaarAdresaanduiding is null");
            return StreamSupport.stream(nummeraanduidingRepository.findAll().spliterator(), false)
                .filter(nummeraanduiding -> nummeraanduiding.getVerwijstnaarAdresaanduiding() == null)
                .toList();
        }
        log.debug("REST request to get all Nummeraanduidings");
        if (eagerload) {
            return nummeraanduidingRepository.findAllWithEagerRelationships();
        } else {
            return nummeraanduidingRepository.findAll();
        }
    }

    /**
     * {@code GET  /nummeraanduidings/:id} : get the "id" nummeraanduiding.
     *
     * @param id the id of the nummeraanduiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nummeraanduiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nummeraanduiding> getNummeraanduiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Nummeraanduiding : {}", id);
        Optional<Nummeraanduiding> nummeraanduiding = nummeraanduidingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(nummeraanduiding);
    }

    /**
     * {@code DELETE  /nummeraanduidings/:id} : delete the "id" nummeraanduiding.
     *
     * @param id the id of the nummeraanduiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNummeraanduiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nummeraanduiding : {}", id);
        nummeraanduidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
