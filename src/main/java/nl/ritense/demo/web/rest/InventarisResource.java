package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Inventaris;
import nl.ritense.demo.repository.InventarisRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inventaris}.
 */
@RestController
@RequestMapping("/api/inventarises")
@Transactional
public class InventarisResource {

    private final Logger log = LoggerFactory.getLogger(InventarisResource.class);

    private static final String ENTITY_NAME = "inventaris";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventarisRepository inventarisRepository;

    public InventarisResource(InventarisRepository inventarisRepository) {
        this.inventarisRepository = inventarisRepository;
    }

    /**
     * {@code POST  /inventarises} : Create a new inventaris.
     *
     * @param inventaris the inventaris to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventaris, or with status {@code 400 (Bad Request)} if the inventaris has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inventaris> createInventaris(@RequestBody Inventaris inventaris) throws URISyntaxException {
        log.debug("REST request to save Inventaris : {}", inventaris);
        if (inventaris.getId() != null) {
            throw new BadRequestAlertException("A new inventaris cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inventaris = inventarisRepository.save(inventaris);
        return ResponseEntity.created(new URI("/api/inventarises/" + inventaris.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inventaris.getId().toString()))
            .body(inventaris);
    }

    /**
     * {@code GET  /inventarises} : get all the inventarises.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventarises in body.
     */
    @GetMapping("")
    public List<Inventaris> getAllInventarises() {
        log.debug("REST request to get all Inventarises");
        return inventarisRepository.findAll();
    }

    /**
     * {@code GET  /inventarises/:id} : get the "id" inventaris.
     *
     * @param id the id of the inventaris to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventaris, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventaris> getInventaris(@PathVariable("id") Long id) {
        log.debug("REST request to get Inventaris : {}", id);
        Optional<Inventaris> inventaris = inventarisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inventaris);
    }

    /**
     * {@code DELETE  /inventarises/:id} : delete the "id" inventaris.
     *
     * @param id the id of the inventaris to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventaris(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inventaris : {}", id);
        inventarisRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
