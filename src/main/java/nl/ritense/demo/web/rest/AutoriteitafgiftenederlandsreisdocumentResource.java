package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Autoriteitafgiftenederlandsreisdocument;
import nl.ritense.demo.repository.AutoriteitafgiftenederlandsreisdocumentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Autoriteitafgiftenederlandsreisdocument}.
 */
@RestController
@RequestMapping("/api/autoriteitafgiftenederlandsreisdocuments")
@Transactional
public class AutoriteitafgiftenederlandsreisdocumentResource {

    private final Logger log = LoggerFactory.getLogger(AutoriteitafgiftenederlandsreisdocumentResource.class);

    private static final String ENTITY_NAME = "autoriteitafgiftenederlandsreisdocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutoriteitafgiftenederlandsreisdocumentRepository autoriteitafgiftenederlandsreisdocumentRepository;

    public AutoriteitafgiftenederlandsreisdocumentResource(
        AutoriteitafgiftenederlandsreisdocumentRepository autoriteitafgiftenederlandsreisdocumentRepository
    ) {
        this.autoriteitafgiftenederlandsreisdocumentRepository = autoriteitafgiftenederlandsreisdocumentRepository;
    }

    /**
     * {@code POST  /autoriteitafgiftenederlandsreisdocuments} : Create a new autoriteitafgiftenederlandsreisdocument.
     *
     * @param autoriteitafgiftenederlandsreisdocument the autoriteitafgiftenederlandsreisdocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autoriteitafgiftenederlandsreisdocument, or with status {@code 400 (Bad Request)} if the autoriteitafgiftenederlandsreisdocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autoriteitafgiftenederlandsreisdocument> createAutoriteitafgiftenederlandsreisdocument(
        @RequestBody Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument
    ) throws URISyntaxException {
        log.debug("REST request to save Autoriteitafgiftenederlandsreisdocument : {}", autoriteitafgiftenederlandsreisdocument);
        if (autoriteitafgiftenederlandsreisdocument.getId() != null) {
            throw new BadRequestAlertException(
                "A new autoriteitafgiftenederlandsreisdocument cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        autoriteitafgiftenederlandsreisdocument = autoriteitafgiftenederlandsreisdocumentRepository.save(
            autoriteitafgiftenederlandsreisdocument
        );
        return ResponseEntity.created(
            new URI("/api/autoriteitafgiftenederlandsreisdocuments/" + autoriteitafgiftenederlandsreisdocument.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autoriteitafgiftenederlandsreisdocument.getId().toString()
                )
            )
            .body(autoriteitafgiftenederlandsreisdocument);
    }

    /**
     * {@code PUT  /autoriteitafgiftenederlandsreisdocuments/:id} : Updates an existing autoriteitafgiftenederlandsreisdocument.
     *
     * @param id the id of the autoriteitafgiftenederlandsreisdocument to save.
     * @param autoriteitafgiftenederlandsreisdocument the autoriteitafgiftenederlandsreisdocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoriteitafgiftenederlandsreisdocument,
     * or with status {@code 400 (Bad Request)} if the autoriteitafgiftenederlandsreisdocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autoriteitafgiftenederlandsreisdocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Autoriteitafgiftenederlandsreisdocument> updateAutoriteitafgiftenederlandsreisdocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument
    ) throws URISyntaxException {
        log.debug("REST request to update Autoriteitafgiftenederlandsreisdocument : {}, {}", id, autoriteitafgiftenederlandsreisdocument);
        if (autoriteitafgiftenederlandsreisdocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoriteitafgiftenederlandsreisdocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoriteitafgiftenederlandsreisdocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autoriteitafgiftenederlandsreisdocument = autoriteitafgiftenederlandsreisdocumentRepository.save(
            autoriteitafgiftenederlandsreisdocument
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autoriteitafgiftenederlandsreisdocument.getId().toString()
                )
            )
            .body(autoriteitafgiftenederlandsreisdocument);
    }

    /**
     * {@code PATCH  /autoriteitafgiftenederlandsreisdocuments/:id} : Partial updates given fields of an existing autoriteitafgiftenederlandsreisdocument, field will ignore if it is null
     *
     * @param id the id of the autoriteitafgiftenederlandsreisdocument to save.
     * @param autoriteitafgiftenederlandsreisdocument the autoriteitafgiftenederlandsreisdocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoriteitafgiftenederlandsreisdocument,
     * or with status {@code 400 (Bad Request)} if the autoriteitafgiftenederlandsreisdocument is not valid,
     * or with status {@code 404 (Not Found)} if the autoriteitafgiftenederlandsreisdocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the autoriteitafgiftenederlandsreisdocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autoriteitafgiftenederlandsreisdocument> partialUpdateAutoriteitafgiftenederlandsreisdocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Autoriteitafgiftenederlandsreisdocument partially : {}, {}",
            id,
            autoriteitafgiftenederlandsreisdocument
        );
        if (autoriteitafgiftenederlandsreisdocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoriteitafgiftenederlandsreisdocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoriteitafgiftenederlandsreisdocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autoriteitafgiftenederlandsreisdocument> result = autoriteitafgiftenederlandsreisdocumentRepository
            .findById(autoriteitafgiftenederlandsreisdocument.getId())
            .map(existingAutoriteitafgiftenederlandsreisdocument -> {
                if (autoriteitafgiftenederlandsreisdocument.getCode() != null) {
                    existingAutoriteitafgiftenederlandsreisdocument.setCode(autoriteitafgiftenederlandsreisdocument.getCode());
                }
                if (autoriteitafgiftenederlandsreisdocument.getDatumbegingeldigheidautoriteitvanafgifte() != null) {
                    existingAutoriteitafgiftenederlandsreisdocument.setDatumbegingeldigheidautoriteitvanafgifte(
                        autoriteitafgiftenederlandsreisdocument.getDatumbegingeldigheidautoriteitvanafgifte()
                    );
                }
                if (autoriteitafgiftenederlandsreisdocument.getDatumeindegeldigheidautoriteitvanafgifte() != null) {
                    existingAutoriteitafgiftenederlandsreisdocument.setDatumeindegeldigheidautoriteitvanafgifte(
                        autoriteitafgiftenederlandsreisdocument.getDatumeindegeldigheidautoriteitvanafgifte()
                    );
                }
                if (autoriteitafgiftenederlandsreisdocument.getOmschrijving() != null) {
                    existingAutoriteitafgiftenederlandsreisdocument.setOmschrijving(
                        autoriteitafgiftenederlandsreisdocument.getOmschrijving()
                    );
                }

                return existingAutoriteitafgiftenederlandsreisdocument;
            })
            .map(autoriteitafgiftenederlandsreisdocumentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                autoriteitafgiftenederlandsreisdocument.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /autoriteitafgiftenederlandsreisdocuments} : get all the autoriteitafgiftenederlandsreisdocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autoriteitafgiftenederlandsreisdocuments in body.
     */
    @GetMapping("")
    public List<Autoriteitafgiftenederlandsreisdocument> getAllAutoriteitafgiftenederlandsreisdocuments() {
        log.debug("REST request to get all Autoriteitafgiftenederlandsreisdocuments");
        return autoriteitafgiftenederlandsreisdocumentRepository.findAll();
    }

    /**
     * {@code GET  /autoriteitafgiftenederlandsreisdocuments/:id} : get the "id" autoriteitafgiftenederlandsreisdocument.
     *
     * @param id the id of the autoriteitafgiftenederlandsreisdocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autoriteitafgiftenederlandsreisdocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autoriteitafgiftenederlandsreisdocument> getAutoriteitafgiftenederlandsreisdocument(@PathVariable("id") Long id) {
        log.debug("REST request to get Autoriteitafgiftenederlandsreisdocument : {}", id);
        Optional<Autoriteitafgiftenederlandsreisdocument> autoriteitafgiftenederlandsreisdocument =
            autoriteitafgiftenederlandsreisdocumentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autoriteitafgiftenederlandsreisdocument);
    }

    /**
     * {@code DELETE  /autoriteitafgiftenederlandsreisdocuments/:id} : delete the "id" autoriteitafgiftenederlandsreisdocument.
     *
     * @param id the id of the autoriteitafgiftenederlandsreisdocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutoriteitafgiftenederlandsreisdocument(@PathVariable("id") Long id) {
        log.debug("REST request to delete Autoriteitafgiftenederlandsreisdocument : {}", id);
        autoriteitafgiftenederlandsreisdocumentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
