package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Offerte;
import nl.ritense.demo.repository.OfferteRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Offerte}.
 */
@RestController
@RequestMapping("/api/offertes")
@Transactional
public class OfferteResource {

    private final Logger log = LoggerFactory.getLogger(OfferteResource.class);

    private static final String ENTITY_NAME = "offerte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferteRepository offerteRepository;

    public OfferteResource(OfferteRepository offerteRepository) {
        this.offerteRepository = offerteRepository;
    }

    /**
     * {@code POST  /offertes} : Create a new offerte.
     *
     * @param offerte the offerte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerte, or with status {@code 400 (Bad Request)} if the offerte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Offerte> createOfferte(@RequestBody Offerte offerte) throws URISyntaxException {
        log.debug("REST request to save Offerte : {}", offerte);
        if (offerte.getId() != null) {
            throw new BadRequestAlertException("A new offerte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        offerte = offerteRepository.save(offerte);
        return ResponseEntity.created(new URI("/api/offertes/" + offerte.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, offerte.getId().toString()))
            .body(offerte);
    }

    /**
     * {@code PUT  /offertes/:id} : Updates an existing offerte.
     *
     * @param id the id of the offerte to save.
     * @param offerte the offerte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerte,
     * or with status {@code 400 (Bad Request)} if the offerte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Offerte> updateOfferte(@PathVariable(value = "id", required = false) final Long id, @RequestBody Offerte offerte)
        throws URISyntaxException {
        log.debug("REST request to update Offerte : {}, {}", id, offerte);
        if (offerte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        offerte = offerteRepository.save(offerte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, offerte.getId().toString()))
            .body(offerte);
    }

    /**
     * {@code PATCH  /offertes/:id} : Partial updates given fields of an existing offerte, field will ignore if it is null
     *
     * @param id the id of the offerte to save.
     * @param offerte the offerte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerte,
     * or with status {@code 400 (Bad Request)} if the offerte is not valid,
     * or with status {@code 404 (Not Found)} if the offerte is not found,
     * or with status {@code 500 (Internal Server Error)} if the offerte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Offerte> partialUpdateOfferte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Offerte offerte
    ) throws URISyntaxException {
        log.debug("REST request to partial update Offerte partially : {}, {}", id, offerte);
        if (offerte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Offerte> result = offerteRepository.findById(offerte.getId()).map(offerteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, offerte.getId().toString())
        );
    }

    /**
     * {@code GET  /offertes} : get all the offertes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offertes in body.
     */
    @GetMapping("")
    public List<Offerte> getAllOffertes(@RequestParam(name = "filter", required = false) String filter) {
        if ("betreftgunning-is-null".equals(filter)) {
            log.debug("REST request to get all Offertes where betreftGunning is null");
            return StreamSupport.stream(offerteRepository.findAll().spliterator(), false)
                .filter(offerte -> offerte.getBetreftGunning() == null)
                .toList();
        }
        log.debug("REST request to get all Offertes");
        return offerteRepository.findAll();
    }

    /**
     * {@code GET  /offertes/:id} : get the "id" offerte.
     *
     * @param id the id of the offerte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Offerte> getOfferte(@PathVariable("id") Long id) {
        log.debug("REST request to get Offerte : {}", id);
        Optional<Offerte> offerte = offerteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(offerte);
    }

    /**
     * {@code DELETE  /offertes/:id} : delete the "id" offerte.
     *
     * @param id the id of the offerte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOfferte(@PathVariable("id") Long id) {
        log.debug("REST request to delete Offerte : {}", id);
        offerteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
