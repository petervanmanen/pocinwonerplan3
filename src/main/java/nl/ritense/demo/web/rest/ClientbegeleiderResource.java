package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Clientbegeleider;
import nl.ritense.demo.repository.ClientbegeleiderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Clientbegeleider}.
 */
@RestController
@RequestMapping("/api/clientbegeleiders")
@Transactional
public class ClientbegeleiderResource {

    private final Logger log = LoggerFactory.getLogger(ClientbegeleiderResource.class);

    private static final String ENTITY_NAME = "clientbegeleider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientbegeleiderRepository clientbegeleiderRepository;

    public ClientbegeleiderResource(ClientbegeleiderRepository clientbegeleiderRepository) {
        this.clientbegeleiderRepository = clientbegeleiderRepository;
    }

    /**
     * {@code POST  /clientbegeleiders} : Create a new clientbegeleider.
     *
     * @param clientbegeleider the clientbegeleider to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientbegeleider, or with status {@code 400 (Bad Request)} if the clientbegeleider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Clientbegeleider> createClientbegeleider(@Valid @RequestBody Clientbegeleider clientbegeleider)
        throws URISyntaxException {
        log.debug("REST request to save Clientbegeleider : {}", clientbegeleider);
        if (clientbegeleider.getId() != null) {
            throw new BadRequestAlertException("A new clientbegeleider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        clientbegeleider = clientbegeleiderRepository.save(clientbegeleider);
        return ResponseEntity.created(new URI("/api/clientbegeleiders/" + clientbegeleider.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, clientbegeleider.getId().toString()))
            .body(clientbegeleider);
    }

    /**
     * {@code PUT  /clientbegeleiders/:id} : Updates an existing clientbegeleider.
     *
     * @param id the id of the clientbegeleider to save.
     * @param clientbegeleider the clientbegeleider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientbegeleider,
     * or with status {@code 400 (Bad Request)} if the clientbegeleider is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientbegeleider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Clientbegeleider> updateClientbegeleider(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Clientbegeleider clientbegeleider
    ) throws URISyntaxException {
        log.debug("REST request to update Clientbegeleider : {}, {}", id, clientbegeleider);
        if (clientbegeleider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientbegeleider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientbegeleiderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        clientbegeleider = clientbegeleiderRepository.save(clientbegeleider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientbegeleider.getId().toString()))
            .body(clientbegeleider);
    }

    /**
     * {@code PATCH  /clientbegeleiders/:id} : Partial updates given fields of an existing clientbegeleider, field will ignore if it is null
     *
     * @param id the id of the clientbegeleider to save.
     * @param clientbegeleider the clientbegeleider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientbegeleider,
     * or with status {@code 400 (Bad Request)} if the clientbegeleider is not valid,
     * or with status {@code 404 (Not Found)} if the clientbegeleider is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientbegeleider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Clientbegeleider> partialUpdateClientbegeleider(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Clientbegeleider clientbegeleider
    ) throws URISyntaxException {
        log.debug("REST request to partial update Clientbegeleider partially : {}, {}", id, clientbegeleider);
        if (clientbegeleider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientbegeleider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientbegeleiderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Clientbegeleider> result = clientbegeleiderRepository
            .findById(clientbegeleider.getId())
            .map(existingClientbegeleider -> {
                if (clientbegeleider.getBegeleiderscode() != null) {
                    existingClientbegeleider.setBegeleiderscode(clientbegeleider.getBegeleiderscode());
                }

                return existingClientbegeleider;
            })
            .map(clientbegeleiderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientbegeleider.getId().toString())
        );
    }

    /**
     * {@code GET  /clientbegeleiders} : get all the clientbegeleiders.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientbegeleiders in body.
     */
    @GetMapping("")
    public List<Clientbegeleider> getAllClientbegeleiders(
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("emptyparticipatiedossier-is-null".equals(filter)) {
            log.debug("REST request to get all Clientbegeleiders where emptyParticipatiedossier is null");
            return StreamSupport.stream(clientbegeleiderRepository.findAll().spliterator(), false)
                .filter(clientbegeleider -> clientbegeleider.getEmptyParticipatiedossier() == null)
                .toList();
        }
        log.debug("REST request to get all Clientbegeleiders");
        if (eagerload) {
            return clientbegeleiderRepository.findAllWithEagerRelationships();
        } else {
            return clientbegeleiderRepository.findAll();
        }
    }

    /**
     * {@code GET  /clientbegeleiders/:id} : get the "id" clientbegeleider.
     *
     * @param id the id of the clientbegeleider to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientbegeleider, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Clientbegeleider> getClientbegeleider(@PathVariable("id") Long id) {
        log.debug("REST request to get Clientbegeleider : {}", id);
        Optional<Clientbegeleider> clientbegeleider = clientbegeleiderRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(clientbegeleider);
    }

    /**
     * {@code DELETE  /clientbegeleiders/:id} : delete the "id" clientbegeleider.
     *
     * @param id the id of the clientbegeleider to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientbegeleider(@PathVariable("id") Long id) {
        log.debug("REST request to delete Clientbegeleider : {}", id);
        clientbegeleiderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
