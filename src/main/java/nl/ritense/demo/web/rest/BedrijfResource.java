package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Bedrijf;
import nl.ritense.demo.repository.BedrijfRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bedrijf}.
 */
@RestController
@RequestMapping("/api/bedrijfs")
@Transactional
public class BedrijfResource {

    private final Logger log = LoggerFactory.getLogger(BedrijfResource.class);

    private static final String ENTITY_NAME = "bedrijf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedrijfRepository bedrijfRepository;

    public BedrijfResource(BedrijfRepository bedrijfRepository) {
        this.bedrijfRepository = bedrijfRepository;
    }

    /**
     * {@code POST  /bedrijfs} : Create a new bedrijf.
     *
     * @param bedrijf the bedrijf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedrijf, or with status {@code 400 (Bad Request)} if the bedrijf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bedrijf> createBedrijf(@RequestBody Bedrijf bedrijf) throws URISyntaxException {
        log.debug("REST request to save Bedrijf : {}", bedrijf);
        if (bedrijf.getId() != null) {
            throw new BadRequestAlertException("A new bedrijf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bedrijf = bedrijfRepository.save(bedrijf);
        return ResponseEntity.created(new URI("/api/bedrijfs/" + bedrijf.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bedrijf.getId().toString()))
            .body(bedrijf);
    }

    /**
     * {@code GET  /bedrijfs} : get all the bedrijfs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bedrijfs in body.
     */
    @GetMapping("")
    public List<Bedrijf> getAllBedrijfs() {
        log.debug("REST request to get all Bedrijfs");
        return bedrijfRepository.findAll();
    }

    /**
     * {@code GET  /bedrijfs/:id} : get the "id" bedrijf.
     *
     * @param id the id of the bedrijf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedrijf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bedrijf> getBedrijf(@PathVariable("id") Long id) {
        log.debug("REST request to get Bedrijf : {}", id);
        Optional<Bedrijf> bedrijf = bedrijfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bedrijf);
    }

    /**
     * {@code DELETE  /bedrijfs/:id} : delete the "id" bedrijf.
     *
     * @param id the id of the bedrijf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBedrijf(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bedrijf : {}", id);
        bedrijfRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
