package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vloginfo;
import nl.ritense.demo.repository.VloginfoRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vloginfo}.
 */
@RestController
@RequestMapping("/api/vloginfos")
@Transactional
public class VloginfoResource {

    private final Logger log = LoggerFactory.getLogger(VloginfoResource.class);

    private static final String ENTITY_NAME = "vloginfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VloginfoRepository vloginfoRepository;

    public VloginfoResource(VloginfoRepository vloginfoRepository) {
        this.vloginfoRepository = vloginfoRepository;
    }

    /**
     * {@code POST  /vloginfos} : Create a new vloginfo.
     *
     * @param vloginfo the vloginfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vloginfo, or with status {@code 400 (Bad Request)} if the vloginfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vloginfo> createVloginfo(@RequestBody Vloginfo vloginfo) throws URISyntaxException {
        log.debug("REST request to save Vloginfo : {}", vloginfo);
        if (vloginfo.getId() != null) {
            throw new BadRequestAlertException("A new vloginfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vloginfo = vloginfoRepository.save(vloginfo);
        return ResponseEntity.created(new URI("/api/vloginfos/" + vloginfo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vloginfo.getId().toString()))
            .body(vloginfo);
    }

    /**
     * {@code PUT  /vloginfos/:id} : Updates an existing vloginfo.
     *
     * @param id the id of the vloginfo to save.
     * @param vloginfo the vloginfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vloginfo,
     * or with status {@code 400 (Bad Request)} if the vloginfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vloginfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vloginfo> updateVloginfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vloginfo vloginfo
    ) throws URISyntaxException {
        log.debug("REST request to update Vloginfo : {}, {}", id, vloginfo);
        if (vloginfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vloginfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vloginfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vloginfo = vloginfoRepository.save(vloginfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vloginfo.getId().toString()))
            .body(vloginfo);
    }

    /**
     * {@code PATCH  /vloginfos/:id} : Partial updates given fields of an existing vloginfo, field will ignore if it is null
     *
     * @param id the id of the vloginfo to save.
     * @param vloginfo the vloginfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vloginfo,
     * or with status {@code 400 (Bad Request)} if the vloginfo is not valid,
     * or with status {@code 404 (Not Found)} if the vloginfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the vloginfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vloginfo> partialUpdateVloginfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vloginfo vloginfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vloginfo partially : {}, {}", id, vloginfo);
        if (vloginfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vloginfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vloginfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vloginfo> result = vloginfoRepository
            .findById(vloginfo.getId())
            .map(existingVloginfo -> {
                if (vloginfo.getDetectieverkeer() != null) {
                    existingVloginfo.setDetectieverkeer(vloginfo.getDetectieverkeer());
                }
                if (vloginfo.getEindegroen() != null) {
                    existingVloginfo.setEindegroen(vloginfo.getEindegroen());
                }
                if (vloginfo.getSnelheid() != null) {
                    existingVloginfo.setSnelheid(vloginfo.getSnelheid());
                }
                if (vloginfo.getStartgroen() != null) {
                    existingVloginfo.setStartgroen(vloginfo.getStartgroen());
                }
                if (vloginfo.getTijdstip() != null) {
                    existingVloginfo.setTijdstip(vloginfo.getTijdstip());
                }
                if (vloginfo.getVerkeerwilgroen() != null) {
                    existingVloginfo.setVerkeerwilgroen(vloginfo.getVerkeerwilgroen());
                }
                if (vloginfo.getWachttijd() != null) {
                    existingVloginfo.setWachttijd(vloginfo.getWachttijd());
                }

                return existingVloginfo;
            })
            .map(vloginfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vloginfo.getId().toString())
        );
    }

    /**
     * {@code GET  /vloginfos} : get all the vloginfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vloginfos in body.
     */
    @GetMapping("")
    public List<Vloginfo> getAllVloginfos() {
        log.debug("REST request to get all Vloginfos");
        return vloginfoRepository.findAll();
    }

    /**
     * {@code GET  /vloginfos/:id} : get the "id" vloginfo.
     *
     * @param id the id of the vloginfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vloginfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vloginfo> getVloginfo(@PathVariable("id") Long id) {
        log.debug("REST request to get Vloginfo : {}", id);
        Optional<Vloginfo> vloginfo = vloginfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vloginfo);
    }

    /**
     * {@code DELETE  /vloginfos/:id} : delete the "id" vloginfo.
     *
     * @param id the id of the vloginfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVloginfo(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vloginfo : {}", id);
        vloginfoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
