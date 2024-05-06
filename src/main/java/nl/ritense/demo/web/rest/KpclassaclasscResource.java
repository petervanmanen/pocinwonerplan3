package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kpclassaclassc;
import nl.ritense.demo.repository.KpclassaclasscRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kpclassaclassc}.
 */
@RestController
@RequestMapping("/api/kpclassaclasscs")
@Transactional
public class KpclassaclasscResource {

    private final Logger log = LoggerFactory.getLogger(KpclassaclasscResource.class);

    private static final String ENTITY_NAME = "kpclassaclassc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpclassaclasscRepository kpclassaclasscRepository;

    public KpclassaclasscResource(KpclassaclasscRepository kpclassaclasscRepository) {
        this.kpclassaclasscRepository = kpclassaclasscRepository;
    }

    /**
     * {@code POST  /kpclassaclasscs} : Create a new kpclassaclassc.
     *
     * @param kpclassaclassc the kpclassaclassc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kpclassaclassc, or with status {@code 400 (Bad Request)} if the kpclassaclassc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kpclassaclassc> createKpclassaclassc(@RequestBody Kpclassaclassc kpclassaclassc) throws URISyntaxException {
        log.debug("REST request to save Kpclassaclassc : {}", kpclassaclassc);
        if (kpclassaclassc.getId() != null) {
            throw new BadRequestAlertException("A new kpclassaclassc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kpclassaclassc = kpclassaclasscRepository.save(kpclassaclassc);
        return ResponseEntity.created(new URI("/api/kpclassaclasscs/" + kpclassaclassc.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kpclassaclassc.getId().toString()))
            .body(kpclassaclassc);
    }

    /**
     * {@code PUT  /kpclassaclasscs/:id} : Updates an existing kpclassaclassc.
     *
     * @param id the id of the kpclassaclassc to save.
     * @param kpclassaclassc the kpclassaclassc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpclassaclassc,
     * or with status {@code 400 (Bad Request)} if the kpclassaclassc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kpclassaclassc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kpclassaclassc> updateKpclassaclassc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kpclassaclassc kpclassaclassc
    ) throws URISyntaxException {
        log.debug("REST request to update Kpclassaclassc : {}, {}", id, kpclassaclassc);
        if (kpclassaclassc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpclassaclassc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpclassaclasscRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kpclassaclassc = kpclassaclasscRepository.save(kpclassaclassc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpclassaclassc.getId().toString()))
            .body(kpclassaclassc);
    }

    /**
     * {@code PATCH  /kpclassaclasscs/:id} : Partial updates given fields of an existing kpclassaclassc, field will ignore if it is null
     *
     * @param id the id of the kpclassaclassc to save.
     * @param kpclassaclassc the kpclassaclassc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpclassaclassc,
     * or with status {@code 400 (Bad Request)} if the kpclassaclassc is not valid,
     * or with status {@code 404 (Not Found)} if the kpclassaclassc is not found,
     * or with status {@code 500 (Internal Server Error)} if the kpclassaclassc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kpclassaclassc> partialUpdateKpclassaclassc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kpclassaclassc kpclassaclassc
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kpclassaclassc partially : {}, {}", id, kpclassaclassc);
        if (kpclassaclassc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpclassaclassc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpclassaclasscRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kpclassaclassc> result = kpclassaclasscRepository
            .findById(kpclassaclassc.getId())
            .map(existingKpclassaclassc -> {
                if (kpclassaclassc.getMbronsysteem() != null) {
                    existingKpclassaclassc.setMbronsysteem(kpclassaclassc.getMbronsysteem());
                }
                if (kpclassaclassc.getMdatumtijdgeladen() != null) {
                    existingKpclassaclassc.setMdatumtijdgeladen(kpclassaclassc.getMdatumtijdgeladen());
                }
                if (kpclassaclassc.getClasscid() != null) {
                    existingKpclassaclassc.setClasscid(kpclassaclassc.getClasscid());
                }
                if (kpclassaclassc.getClassaid() != null) {
                    existingKpclassaclassc.setClassaid(kpclassaclassc.getClassaid());
                }

                return existingKpclassaclassc;
            })
            .map(kpclassaclasscRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpclassaclassc.getId().toString())
        );
    }

    /**
     * {@code GET  /kpclassaclasscs} : get all the kpclassaclasscs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kpclassaclasscs in body.
     */
    @GetMapping("")
    public List<Kpclassaclassc> getAllKpclassaclasscs() {
        log.debug("REST request to get all Kpclassaclasscs");
        return kpclassaclasscRepository.findAll();
    }

    /**
     * {@code GET  /kpclassaclasscs/:id} : get the "id" kpclassaclassc.
     *
     * @param id the id of the kpclassaclassc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kpclassaclassc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kpclassaclassc> getKpclassaclassc(@PathVariable("id") Long id) {
        log.debug("REST request to get Kpclassaclassc : {}", id);
        Optional<Kpclassaclassc> kpclassaclassc = kpclassaclasscRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kpclassaclassc);
    }

    /**
     * {@code DELETE  /kpclassaclasscs/:id} : delete the "id" kpclassaclassc.
     *
     * @param id the id of the kpclassaclassc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKpclassaclassc(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kpclassaclassc : {}", id);
        kpclassaclasscRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
