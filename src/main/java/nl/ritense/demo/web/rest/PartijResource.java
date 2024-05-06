package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Partij;
import nl.ritense.demo.repository.PartijRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Partij}.
 */
@RestController
@RequestMapping("/api/partijs")
@Transactional
public class PartijResource {

    private final Logger log = LoggerFactory.getLogger(PartijResource.class);

    private static final String ENTITY_NAME = "partij";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartijRepository partijRepository;

    public PartijResource(PartijRepository partijRepository) {
        this.partijRepository = partijRepository;
    }

    /**
     * {@code POST  /partijs} : Create a new partij.
     *
     * @param partij the partij to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partij, or with status {@code 400 (Bad Request)} if the partij has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Partij> createPartij(@RequestBody Partij partij) throws URISyntaxException {
        log.debug("REST request to save Partij : {}", partij);
        if (partij.getId() != null) {
            throw new BadRequestAlertException("A new partij cannot already have an ID", ENTITY_NAME, "idexists");
        }
        partij = partijRepository.save(partij);
        return ResponseEntity.created(new URI("/api/partijs/" + partij.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, partij.getId().toString()))
            .body(partij);
    }

    /**
     * {@code PUT  /partijs/:id} : Updates an existing partij.
     *
     * @param id the id of the partij to save.
     * @param partij the partij to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partij,
     * or with status {@code 400 (Bad Request)} if the partij is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partij couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Partij> updatePartij(@PathVariable(value = "id", required = false) final Long id, @RequestBody Partij partij)
        throws URISyntaxException {
        log.debug("REST request to update Partij : {}, {}", id, partij);
        if (partij.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partij.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partijRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partij = partijRepository.save(partij);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partij.getId().toString()))
            .body(partij);
    }

    /**
     * {@code PATCH  /partijs/:id} : Partial updates given fields of an existing partij, field will ignore if it is null
     *
     * @param id the id of the partij to save.
     * @param partij the partij to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partij,
     * or with status {@code 400 (Bad Request)} if the partij is not valid,
     * or with status {@code 404 (Not Found)} if the partij is not found,
     * or with status {@code 500 (Internal Server Error)} if the partij couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Partij> partialUpdatePartij(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Partij partij
    ) throws URISyntaxException {
        log.debug("REST request to partial update Partij partially : {}, {}", id, partij);
        if (partij.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partij.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partijRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Partij> result = partijRepository
            .findById(partij.getId())
            .map(existingPartij -> {
                if (partij.getCode() != null) {
                    existingPartij.setCode(partij.getCode());
                }
                if (partij.getDatumaanvanggeldigheidpartij() != null) {
                    existingPartij.setDatumaanvanggeldigheidpartij(partij.getDatumaanvanggeldigheidpartij());
                }
                if (partij.getDatumeindegeldigheidpartij() != null) {
                    existingPartij.setDatumeindegeldigheidpartij(partij.getDatumeindegeldigheidpartij());
                }
                if (partij.getNaam() != null) {
                    existingPartij.setNaam(partij.getNaam());
                }
                if (partij.getSoort() != null) {
                    existingPartij.setSoort(partij.getSoort());
                }
                if (partij.getVerstrekkingsbeperkingmogelijk() != null) {
                    existingPartij.setVerstrekkingsbeperkingmogelijk(partij.getVerstrekkingsbeperkingmogelijk());
                }

                return existingPartij;
            })
            .map(partijRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partij.getId().toString())
        );
    }

    /**
     * {@code GET  /partijs} : get all the partijs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partijs in body.
     */
    @GetMapping("")
    public List<Partij> getAllPartijs() {
        log.debug("REST request to get all Partijs");
        return partijRepository.findAll();
    }

    /**
     * {@code GET  /partijs/:id} : get the "id" partij.
     *
     * @param id the id of the partij to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partij, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Partij> getPartij(@PathVariable("id") Long id) {
        log.debug("REST request to get Partij : {}", id);
        Optional<Partij> partij = partijRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partij);
    }

    /**
     * {@code DELETE  /partijs/:id} : delete the "id" partij.
     *
     * @param id the id of the partij to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartij(@PathVariable("id") Long id) {
        log.debug("REST request to delete Partij : {}", id);
        partijRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
