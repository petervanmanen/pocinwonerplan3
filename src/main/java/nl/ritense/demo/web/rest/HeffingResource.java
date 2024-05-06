package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Heffing;
import nl.ritense.demo.repository.HeffingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Heffing}.
 */
@RestController
@RequestMapping("/api/heffings")
@Transactional
public class HeffingResource {

    private final Logger log = LoggerFactory.getLogger(HeffingResource.class);

    private static final String ENTITY_NAME = "heffing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeffingRepository heffingRepository;

    public HeffingResource(HeffingRepository heffingRepository) {
        this.heffingRepository = heffingRepository;
    }

    /**
     * {@code POST  /heffings} : Create a new heffing.
     *
     * @param heffing the heffing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new heffing, or with status {@code 400 (Bad Request)} if the heffing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Heffing> createHeffing(@RequestBody Heffing heffing) throws URISyntaxException {
        log.debug("REST request to save Heffing : {}", heffing);
        if (heffing.getId() != null) {
            throw new BadRequestAlertException("A new heffing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        heffing = heffingRepository.save(heffing);
        return ResponseEntity.created(new URI("/api/heffings/" + heffing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, heffing.getId().toString()))
            .body(heffing);
    }

    /**
     * {@code PUT  /heffings/:id} : Updates an existing heffing.
     *
     * @param id the id of the heffing to save.
     * @param heffing the heffing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heffing,
     * or with status {@code 400 (Bad Request)} if the heffing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the heffing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Heffing> updateHeffing(@PathVariable(value = "id", required = false) final Long id, @RequestBody Heffing heffing)
        throws URISyntaxException {
        log.debug("REST request to update Heffing : {}, {}", id, heffing);
        if (heffing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heffing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heffingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        heffing = heffingRepository.save(heffing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, heffing.getId().toString()))
            .body(heffing);
    }

    /**
     * {@code PATCH  /heffings/:id} : Partial updates given fields of an existing heffing, field will ignore if it is null
     *
     * @param id the id of the heffing to save.
     * @param heffing the heffing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heffing,
     * or with status {@code 400 (Bad Request)} if the heffing is not valid,
     * or with status {@code 404 (Not Found)} if the heffing is not found,
     * or with status {@code 500 (Internal Server Error)} if the heffing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Heffing> partialUpdateHeffing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Heffing heffing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Heffing partially : {}, {}", id, heffing);
        if (heffing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heffing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heffingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Heffing> result = heffingRepository
            .findById(heffing.getId())
            .map(existingHeffing -> {
                if (heffing.getBedrag() != null) {
                    existingHeffing.setBedrag(heffing.getBedrag());
                }
                if (heffing.getCode() != null) {
                    existingHeffing.setCode(heffing.getCode());
                }
                if (heffing.getDatumindiening() != null) {
                    existingHeffing.setDatumindiening(heffing.getDatumindiening());
                }
                if (heffing.getGefactureerd() != null) {
                    existingHeffing.setGefactureerd(heffing.getGefactureerd());
                }
                if (heffing.getInrekening() != null) {
                    existingHeffing.setInrekening(heffing.getInrekening());
                }
                if (heffing.getNummer() != null) {
                    existingHeffing.setNummer(heffing.getNummer());
                }
                if (heffing.getRunnummer() != null) {
                    existingHeffing.setRunnummer(heffing.getRunnummer());
                }

                return existingHeffing;
            })
            .map(heffingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, heffing.getId().toString())
        );
    }

    /**
     * {@code GET  /heffings} : get all the heffings.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heffings in body.
     */
    @GetMapping("")
    public List<Heffing> getAllHeffings(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftzaak-is-null".equals(filter)) {
            log.debug("REST request to get all Heffings where heeftZaak is null");
            return StreamSupport.stream(heffingRepository.findAll().spliterator(), false)
                .filter(heffing -> heffing.getHeeftZaak() == null)
                .toList();
        }
        log.debug("REST request to get all Heffings");
        return heffingRepository.findAll();
    }

    /**
     * {@code GET  /heffings/:id} : get the "id" heffing.
     *
     * @param id the id of the heffing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the heffing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Heffing> getHeffing(@PathVariable("id") Long id) {
        log.debug("REST request to get Heffing : {}", id);
        Optional<Heffing> heffing = heffingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(heffing);
    }

    /**
     * {@code DELETE  /heffings/:id} : delete the "id" heffing.
     *
     * @param id the id of the heffing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeffing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Heffing : {}", id);
        heffingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
