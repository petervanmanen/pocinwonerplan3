package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Productofdienst;
import nl.ritense.demo.repository.ProductofdienstRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Productofdienst}.
 */
@RestController
@RequestMapping("/api/productofdiensts")
@Transactional
public class ProductofdienstResource {

    private final Logger log = LoggerFactory.getLogger(ProductofdienstResource.class);

    private static final String ENTITY_NAME = "productofdienst";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductofdienstRepository productofdienstRepository;

    public ProductofdienstResource(ProductofdienstRepository productofdienstRepository) {
        this.productofdienstRepository = productofdienstRepository;
    }

    /**
     * {@code POST  /productofdiensts} : Create a new productofdienst.
     *
     * @param productofdienst the productofdienst to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productofdienst, or with status {@code 400 (Bad Request)} if the productofdienst has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Productofdienst> createProductofdienst(@RequestBody Productofdienst productofdienst) throws URISyntaxException {
        log.debug("REST request to save Productofdienst : {}", productofdienst);
        if (productofdienst.getId() != null) {
            throw new BadRequestAlertException("A new productofdienst cannot already have an ID", ENTITY_NAME, "idexists");
        }
        productofdienst = productofdienstRepository.save(productofdienst);
        return ResponseEntity.created(new URI("/api/productofdiensts/" + productofdienst.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, productofdienst.getId().toString()))
            .body(productofdienst);
    }

    /**
     * {@code PUT  /productofdiensts/:id} : Updates an existing productofdienst.
     *
     * @param id the id of the productofdienst to save.
     * @param productofdienst the productofdienst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productofdienst,
     * or with status {@code 400 (Bad Request)} if the productofdienst is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productofdienst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Productofdienst> updateProductofdienst(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Productofdienst productofdienst
    ) throws URISyntaxException {
        log.debug("REST request to update Productofdienst : {}, {}", id, productofdienst);
        if (productofdienst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productofdienst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productofdienstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        productofdienst = productofdienstRepository.save(productofdienst);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productofdienst.getId().toString()))
            .body(productofdienst);
    }

    /**
     * {@code PATCH  /productofdiensts/:id} : Partial updates given fields of an existing productofdienst, field will ignore if it is null
     *
     * @param id the id of the productofdienst to save.
     * @param productofdienst the productofdienst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productofdienst,
     * or with status {@code 400 (Bad Request)} if the productofdienst is not valid,
     * or with status {@code 404 (Not Found)} if the productofdienst is not found,
     * or with status {@code 500 (Internal Server Error)} if the productofdienst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Productofdienst> partialUpdateProductofdienst(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Productofdienst productofdienst
    ) throws URISyntaxException {
        log.debug("REST request to partial update Productofdienst partially : {}, {}", id, productofdienst);
        if (productofdienst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productofdienst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productofdienstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Productofdienst> result = productofdienstRepository
            .findById(productofdienst.getId())
            .map(existingProductofdienst -> {
                if (productofdienst.getAfhandeltijd() != null) {
                    existingProductofdienst.setAfhandeltijd(productofdienst.getAfhandeltijd());
                }
                if (productofdienst.getIngebruik() != null) {
                    existingProductofdienst.setIngebruik(productofdienst.getIngebruik());
                }
                if (productofdienst.getNaam() != null) {
                    existingProductofdienst.setNaam(productofdienst.getNaam());
                }

                return existingProductofdienst;
            })
            .map(productofdienstRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productofdienst.getId().toString())
        );
    }

    /**
     * {@code GET  /productofdiensts} : get all the productofdiensts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productofdiensts in body.
     */
    @GetMapping("")
    public List<Productofdienst> getAllProductofdiensts() {
        log.debug("REST request to get all Productofdiensts");
        return productofdienstRepository.findAll();
    }

    /**
     * {@code GET  /productofdiensts/:id} : get the "id" productofdienst.
     *
     * @param id the id of the productofdienst to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productofdienst, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Productofdienst> getProductofdienst(@PathVariable("id") Long id) {
        log.debug("REST request to get Productofdienst : {}", id);
        Optional<Productofdienst> productofdienst = productofdienstRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productofdienst);
    }

    /**
     * {@code DELETE  /productofdiensts/:id} : delete the "id" productofdienst.
     *
     * @param id the id of the productofdienst to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductofdienst(@PathVariable("id") Long id) {
        log.debug("REST request to delete Productofdienst : {}", id);
        productofdienstRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
