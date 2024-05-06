package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Videoopname;
import nl.ritense.demo.repository.VideoopnameRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Videoopname}.
 */
@RestController
@RequestMapping("/api/videoopnames")
@Transactional
public class VideoopnameResource {

    private final Logger log = LoggerFactory.getLogger(VideoopnameResource.class);

    private static final String ENTITY_NAME = "videoopname";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoopnameRepository videoopnameRepository;

    public VideoopnameResource(VideoopnameRepository videoopnameRepository) {
        this.videoopnameRepository = videoopnameRepository;
    }

    /**
     * {@code POST  /videoopnames} : Create a new videoopname.
     *
     * @param videoopname the videoopname to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videoopname, or with status {@code 400 (Bad Request)} if the videoopname has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Videoopname> createVideoopname(@RequestBody Videoopname videoopname) throws URISyntaxException {
        log.debug("REST request to save Videoopname : {}", videoopname);
        if (videoopname.getId() != null) {
            throw new BadRequestAlertException("A new videoopname cannot already have an ID", ENTITY_NAME, "idexists");
        }
        videoopname = videoopnameRepository.save(videoopname);
        return ResponseEntity.created(new URI("/api/videoopnames/" + videoopname.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, videoopname.getId().toString()))
            .body(videoopname);
    }

    /**
     * {@code PUT  /videoopnames/:id} : Updates an existing videoopname.
     *
     * @param id the id of the videoopname to save.
     * @param videoopname the videoopname to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoopname,
     * or with status {@code 400 (Bad Request)} if the videoopname is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videoopname couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Videoopname> updateVideoopname(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Videoopname videoopname
    ) throws URISyntaxException {
        log.debug("REST request to update Videoopname : {}, {}", id, videoopname);
        if (videoopname.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videoopname.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoopnameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        videoopname = videoopnameRepository.save(videoopname);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, videoopname.getId().toString()))
            .body(videoopname);
    }

    /**
     * {@code PATCH  /videoopnames/:id} : Partial updates given fields of an existing videoopname, field will ignore if it is null
     *
     * @param id the id of the videoopname to save.
     * @param videoopname the videoopname to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoopname,
     * or with status {@code 400 (Bad Request)} if the videoopname is not valid,
     * or with status {@code 404 (Not Found)} if the videoopname is not found,
     * or with status {@code 500 (Internal Server Error)} if the videoopname couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Videoopname> partialUpdateVideoopname(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Videoopname videoopname
    ) throws URISyntaxException {
        log.debug("REST request to partial update Videoopname partially : {}, {}", id, videoopname);
        if (videoopname.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videoopname.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoopnameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Videoopname> result = videoopnameRepository
            .findById(videoopname.getId())
            .map(existingVideoopname -> {
                if (videoopname.getBestandsgrootte() != null) {
                    existingVideoopname.setBestandsgrootte(videoopname.getBestandsgrootte());
                }
                if (videoopname.getDatumtijd() != null) {
                    existingVideoopname.setDatumtijd(videoopname.getDatumtijd());
                }
                if (videoopname.getLengte() != null) {
                    existingVideoopname.setLengte(videoopname.getLengte());
                }
                if (videoopname.getVideoformaat() != null) {
                    existingVideoopname.setVideoformaat(videoopname.getVideoformaat());
                }

                return existingVideoopname;
            })
            .map(videoopnameRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, videoopname.getId().toString())
        );
    }

    /**
     * {@code GET  /videoopnames} : get all the videoopnames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videoopnames in body.
     */
    @GetMapping("")
    public List<Videoopname> getAllVideoopnames() {
        log.debug("REST request to get all Videoopnames");
        return videoopnameRepository.findAll();
    }

    /**
     * {@code GET  /videoopnames/:id} : get the "id" videoopname.
     *
     * @param id the id of the videoopname to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoopname, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Videoopname> getVideoopname(@PathVariable("id") Long id) {
        log.debug("REST request to get Videoopname : {}", id);
        Optional<Videoopname> videoopname = videoopnameRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(videoopname);
    }

    /**
     * {@code DELETE  /videoopnames/:id} : delete the "id" videoopname.
     *
     * @param id the id of the videoopname to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoopname(@PathVariable("id") Long id) {
        log.debug("REST request to delete Videoopname : {}", id);
        videoopnameRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
