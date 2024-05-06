package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Buurt;
import nl.ritense.demo.repository.BuurtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Buurt}.
 */
@RestController
@RequestMapping("/api/buurts")
@Transactional
public class BuurtResource {

    private final Logger log = LoggerFactory.getLogger(BuurtResource.class);

    private static final String ENTITY_NAME = "buurt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuurtRepository buurtRepository;

    public BuurtResource(BuurtRepository buurtRepository) {
        this.buurtRepository = buurtRepository;
    }

    /**
     * {@code POST  /buurts} : Create a new buurt.
     *
     * @param buurt the buurt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buurt, or with status {@code 400 (Bad Request)} if the buurt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Buurt> createBuurt(@Valid @RequestBody Buurt buurt) throws URISyntaxException {
        log.debug("REST request to save Buurt : {}", buurt);
        if (buurt.getId() != null) {
            throw new BadRequestAlertException("A new buurt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        buurt = buurtRepository.save(buurt);
        return ResponseEntity.created(new URI("/api/buurts/" + buurt.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, buurt.getId().toString()))
            .body(buurt);
    }

    /**
     * {@code PUT  /buurts/:id} : Updates an existing buurt.
     *
     * @param id the id of the buurt to save.
     * @param buurt the buurt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buurt,
     * or with status {@code 400 (Bad Request)} if the buurt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buurt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Buurt> updateBuurt(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Buurt buurt)
        throws URISyntaxException {
        log.debug("REST request to update Buurt : {}, {}", id, buurt);
        if (buurt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buurt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buurtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        buurt = buurtRepository.save(buurt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, buurt.getId().toString()))
            .body(buurt);
    }

    /**
     * {@code PATCH  /buurts/:id} : Partial updates given fields of an existing buurt, field will ignore if it is null
     *
     * @param id the id of the buurt to save.
     * @param buurt the buurt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buurt,
     * or with status {@code 400 (Bad Request)} if the buurt is not valid,
     * or with status {@code 404 (Not Found)} if the buurt is not found,
     * or with status {@code 500 (Internal Server Error)} if the buurt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Buurt> partialUpdateBuurt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Buurt buurt
    ) throws URISyntaxException {
        log.debug("REST request to partial update Buurt partially : {}, {}", id, buurt);
        if (buurt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buurt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buurtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Buurt> result = buurtRepository
            .findById(buurt.getId())
            .map(existingBuurt -> {
                if (buurt.getBuurtcode() != null) {
                    existingBuurt.setBuurtcode(buurt.getBuurtcode());
                }
                if (buurt.getBuurtgeometrie() != null) {
                    existingBuurt.setBuurtgeometrie(buurt.getBuurtgeometrie());
                }
                if (buurt.getBuurtnaam() != null) {
                    existingBuurt.setBuurtnaam(buurt.getBuurtnaam());
                }
                if (buurt.getDatumbegingeldigheidbuurt() != null) {
                    existingBuurt.setDatumbegingeldigheidbuurt(buurt.getDatumbegingeldigheidbuurt());
                }
                if (buurt.getDatumeinde() != null) {
                    existingBuurt.setDatumeinde(buurt.getDatumeinde());
                }
                if (buurt.getDatumeindegeldigheidbuurt() != null) {
                    existingBuurt.setDatumeindegeldigheidbuurt(buurt.getDatumeindegeldigheidbuurt());
                }
                if (buurt.getDatumingang() != null) {
                    existingBuurt.setDatumingang(buurt.getDatumingang());
                }
                if (buurt.getGeconstateerd() != null) {
                    existingBuurt.setGeconstateerd(buurt.getGeconstateerd());
                }
                if (buurt.getIdentificatie() != null) {
                    existingBuurt.setIdentificatie(buurt.getIdentificatie());
                }
                if (buurt.getInonderzoek() != null) {
                    existingBuurt.setInonderzoek(buurt.getInonderzoek());
                }
                if (buurt.getStatus() != null) {
                    existingBuurt.setStatus(buurt.getStatus());
                }
                if (buurt.getVersie() != null) {
                    existingBuurt.setVersie(buurt.getVersie());
                }

                return existingBuurt;
            })
            .map(buurtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, buurt.getId().toString())
        );
    }

    /**
     * {@code GET  /buurts} : get all the buurts.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buurts in body.
     */
    @GetMapping("")
    public List<Buurt> getAllBuurts(@RequestParam(name = "filter", required = false) String filter) {
        if ("komtovereengebied-is-null".equals(filter)) {
            log.debug("REST request to get all Buurts where komtovereenGebied is null");
            return StreamSupport.stream(buurtRepository.findAll().spliterator(), false)
                .filter(buurt -> buurt.getKomtovereenGebied() == null)
                .toList();
        }
        log.debug("REST request to get all Buurts");
        return buurtRepository.findAll();
    }

    /**
     * {@code GET  /buurts/:id} : get the "id" buurt.
     *
     * @param id the id of the buurt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buurt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Buurt> getBuurt(@PathVariable("id") Long id) {
        log.debug("REST request to get Buurt : {}", id);
        Optional<Buurt> buurt = buurtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(buurt);
    }

    /**
     * {@code DELETE  /buurts/:id} : delete the "id" buurt.
     *
     * @param id the id of the buurt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuurt(@PathVariable("id") Long id) {
        log.debug("REST request to delete Buurt : {}", id);
        buurtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
