package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Gunning;
import nl.ritense.demo.repository.GunningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gunning}.
 */
@RestController
@RequestMapping("/api/gunnings")
@Transactional
public class GunningResource {

    private final Logger log = LoggerFactory.getLogger(GunningResource.class);

    private static final String ENTITY_NAME = "gunning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GunningRepository gunningRepository;

    public GunningResource(GunningRepository gunningRepository) {
        this.gunningRepository = gunningRepository;
    }

    /**
     * {@code POST  /gunnings} : Create a new gunning.
     *
     * @param gunning the gunning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gunning, or with status {@code 400 (Bad Request)} if the gunning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gunning> createGunning(@RequestBody Gunning gunning) throws URISyntaxException {
        log.debug("REST request to save Gunning : {}", gunning);
        if (gunning.getId() != null) {
            throw new BadRequestAlertException("A new gunning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gunning = gunningRepository.save(gunning);
        return ResponseEntity.created(new URI("/api/gunnings/" + gunning.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gunning.getId().toString()))
            .body(gunning);
    }

    /**
     * {@code PUT  /gunnings/:id} : Updates an existing gunning.
     *
     * @param id the id of the gunning to save.
     * @param gunning the gunning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gunning,
     * or with status {@code 400 (Bad Request)} if the gunning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gunning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gunning> updateGunning(@PathVariable(value = "id", required = false) final Long id, @RequestBody Gunning gunning)
        throws URISyntaxException {
        log.debug("REST request to update Gunning : {}, {}", id, gunning);
        if (gunning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gunning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gunningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gunning = gunningRepository.save(gunning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gunning.getId().toString()))
            .body(gunning);
    }

    /**
     * {@code PATCH  /gunnings/:id} : Partial updates given fields of an existing gunning, field will ignore if it is null
     *
     * @param id the id of the gunning to save.
     * @param gunning the gunning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gunning,
     * or with status {@code 400 (Bad Request)} if the gunning is not valid,
     * or with status {@code 404 (Not Found)} if the gunning is not found,
     * or with status {@code 500 (Internal Server Error)} if the gunning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gunning> partialUpdateGunning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gunning gunning
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gunning partially : {}, {}", id, gunning);
        if (gunning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gunning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gunningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gunning> result = gunningRepository
            .findById(gunning.getId())
            .map(existingGunning -> {
                if (gunning.getBericht() != null) {
                    existingGunning.setBericht(gunning.getBericht());
                }
                if (gunning.getDatumgunning() != null) {
                    existingGunning.setDatumgunning(gunning.getDatumgunning());
                }
                if (gunning.getDatumpublicatie() != null) {
                    existingGunning.setDatumpublicatie(gunning.getDatumpublicatie());
                }
                if (gunning.getDatumvoorlopigegunning() != null) {
                    existingGunning.setDatumvoorlopigegunning(gunning.getDatumvoorlopigegunning());
                }
                if (gunning.getGegundeprijs() != null) {
                    existingGunning.setGegundeprijs(gunning.getGegundeprijs());
                }

                return existingGunning;
            })
            .map(gunningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gunning.getId().toString())
        );
    }

    /**
     * {@code GET  /gunnings} : get all the gunnings.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gunnings in body.
     */
    @GetMapping("")
    public List<Gunning> getAllGunnings(@RequestParam(name = "filter", required = false) String filter) {
        if ("mondtuitaanbesteding-is-null".equals(filter)) {
            log.debug("REST request to get all Gunnings where mondtuitAanbesteding is null");
            return StreamSupport.stream(gunningRepository.findAll().spliterator(), false)
                .filter(gunning -> gunning.getMondtuitAanbesteding() == null)
                .toList();
        }
        log.debug("REST request to get all Gunnings");
        return gunningRepository.findAll();
    }

    /**
     * {@code GET  /gunnings/:id} : get the "id" gunning.
     *
     * @param id the id of the gunning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gunning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gunning> getGunning(@PathVariable("id") Long id) {
        log.debug("REST request to get Gunning : {}", id);
        Optional<Gunning> gunning = gunningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gunning);
    }

    /**
     * {@code DELETE  /gunnings/:id} : delete the "id" gunning.
     *
     * @param id the id of the gunning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGunning(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gunning : {}", id);
        gunningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
