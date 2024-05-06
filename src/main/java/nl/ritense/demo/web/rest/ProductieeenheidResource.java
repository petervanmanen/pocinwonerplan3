package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Productieeenheid;
import nl.ritense.demo.repository.ProductieeenheidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Productieeenheid}.
 */
@RestController
@RequestMapping("/api/productieeenheids")
@Transactional
public class ProductieeenheidResource {

    private final Logger log = LoggerFactory.getLogger(ProductieeenheidResource.class);

    private static final String ENTITY_NAME = "productieeenheid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductieeenheidRepository productieeenheidRepository;

    public ProductieeenheidResource(ProductieeenheidRepository productieeenheidRepository) {
        this.productieeenheidRepository = productieeenheidRepository;
    }

    /**
     * {@code POST  /productieeenheids} : Create a new productieeenheid.
     *
     * @param productieeenheid the productieeenheid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productieeenheid, or with status {@code 400 (Bad Request)} if the productieeenheid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Productieeenheid> createProductieeenheid(@RequestBody Productieeenheid productieeenheid)
        throws URISyntaxException {
        log.debug("REST request to save Productieeenheid : {}", productieeenheid);
        if (productieeenheid.getId() != null) {
            throw new BadRequestAlertException("A new productieeenheid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        productieeenheid = productieeenheidRepository.save(productieeenheid);
        return ResponseEntity.created(new URI("/api/productieeenheids/" + productieeenheid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, productieeenheid.getId().toString()))
            .body(productieeenheid);
    }

    /**
     * {@code GET  /productieeenheids} : get all the productieeenheids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productieeenheids in body.
     */
    @GetMapping("")
    public List<Productieeenheid> getAllProductieeenheids() {
        log.debug("REST request to get all Productieeenheids");
        return productieeenheidRepository.findAll();
    }

    /**
     * {@code GET  /productieeenheids/:id} : get the "id" productieeenheid.
     *
     * @param id the id of the productieeenheid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productieeenheid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Productieeenheid> getProductieeenheid(@PathVariable("id") Long id) {
        log.debug("REST request to get Productieeenheid : {}", id);
        Optional<Productieeenheid> productieeenheid = productieeenheidRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productieeenheid);
    }

    /**
     * {@code DELETE  /productieeenheids/:id} : delete the "id" productieeenheid.
     *
     * @param id the id of the productieeenheid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductieeenheid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Productieeenheid : {}", id);
        productieeenheidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
