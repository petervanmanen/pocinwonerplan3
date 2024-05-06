package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Postadres;
import nl.ritense.demo.repository.PostadresRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Postadres}.
 */
@RestController
@RequestMapping("/api/postadres")
@Transactional
public class PostadresResource {

    private final Logger log = LoggerFactory.getLogger(PostadresResource.class);

    private static final String ENTITY_NAME = "postadres";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostadresRepository postadresRepository;

    public PostadresResource(PostadresRepository postadresRepository) {
        this.postadresRepository = postadresRepository;
    }

    /**
     * {@code POST  /postadres} : Create a new postadres.
     *
     * @param postadres the postadres to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postadres, or with status {@code 400 (Bad Request)} if the postadres has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Postadres> createPostadres(@RequestBody Postadres postadres) throws URISyntaxException {
        log.debug("REST request to save Postadres : {}", postadres);
        if (postadres.getId() != null) {
            throw new BadRequestAlertException("A new postadres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        postadres = postadresRepository.save(postadres);
        return ResponseEntity.created(new URI("/api/postadres/" + postadres.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, postadres.getId().toString()))
            .body(postadres);
    }

    /**
     * {@code PUT  /postadres/:id} : Updates an existing postadres.
     *
     * @param id the id of the postadres to save.
     * @param postadres the postadres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postadres,
     * or with status {@code 400 (Bad Request)} if the postadres is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postadres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Postadres> updatePostadres(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Postadres postadres
    ) throws URISyntaxException {
        log.debug("REST request to update Postadres : {}, {}", id, postadres);
        if (postadres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postadres.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postadresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        postadres = postadresRepository.save(postadres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postadres.getId().toString()))
            .body(postadres);
    }

    /**
     * {@code PATCH  /postadres/:id} : Partial updates given fields of an existing postadres, field will ignore if it is null
     *
     * @param id the id of the postadres to save.
     * @param postadres the postadres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postadres,
     * or with status {@code 400 (Bad Request)} if the postadres is not valid,
     * or with status {@code 404 (Not Found)} if the postadres is not found,
     * or with status {@code 500 (Internal Server Error)} if the postadres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Postadres> partialUpdatePostadres(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Postadres postadres
    ) throws URISyntaxException {
        log.debug("REST request to partial update Postadres partially : {}, {}", id, postadres);
        if (postadres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postadres.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postadresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Postadres> result = postadresRepository
            .findById(postadres.getId())
            .map(existingPostadres -> {
                if (postadres.getPostadrestype() != null) {
                    existingPostadres.setPostadrestype(postadres.getPostadrestype());
                }
                if (postadres.getPostbusofantwoordnummer() != null) {
                    existingPostadres.setPostbusofantwoordnummer(postadres.getPostbusofantwoordnummer());
                }
                if (postadres.getPostcodepostadres() != null) {
                    existingPostadres.setPostcodepostadres(postadres.getPostcodepostadres());
                }

                return existingPostadres;
            })
            .map(postadresRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postadres.getId().toString())
        );
    }

    /**
     * {@code GET  /postadres} : get all the postadres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postadres in body.
     */
    @GetMapping("")
    public List<Postadres> getAllPostadres() {
        log.debug("REST request to get all Postadres");
        return postadresRepository.findAll();
    }

    /**
     * {@code GET  /postadres/:id} : get the "id" postadres.
     *
     * @param id the id of the postadres to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postadres, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Postadres> getPostadres(@PathVariable("id") Long id) {
        log.debug("REST request to get Postadres : {}", id);
        Optional<Postadres> postadres = postadresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(postadres);
    }

    /**
     * {@code DELETE  /postadres/:id} : delete the "id" postadres.
     *
     * @param id the id of the postadres to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostadres(@PathVariable("id") Long id) {
        log.debug("REST request to delete Postadres : {}", id);
        postadresRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
