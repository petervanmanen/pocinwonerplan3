package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Software;
import nl.ritense.demo.repository.SoftwareRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Software}.
 */
@RestController
@RequestMapping("/api/software")
@Transactional
public class SoftwareResource {

    private final Logger log = LoggerFactory.getLogger(SoftwareResource.class);

    private static final String ENTITY_NAME = "software";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoftwareRepository softwareRepository;

    public SoftwareResource(SoftwareRepository softwareRepository) {
        this.softwareRepository = softwareRepository;
    }

    /**
     * {@code POST  /software} : Create a new software.
     *
     * @param software the software to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new software, or with status {@code 400 (Bad Request)} if the software has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Software> createSoftware(@RequestBody Software software) throws URISyntaxException {
        log.debug("REST request to save Software : {}", software);
        if (software.getId() != null) {
            throw new BadRequestAlertException("A new software cannot already have an ID", ENTITY_NAME, "idexists");
        }
        software = softwareRepository.save(software);
        return ResponseEntity.created(new URI("/api/software/" + software.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, software.getId().toString()))
            .body(software);
    }

    /**
     * {@code GET  /software} : get all the software.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of software in body.
     */
    @GetMapping("")
    public List<Software> getAllSoftware() {
        log.debug("REST request to get all Software");
        return softwareRepository.findAll();
    }

    /**
     * {@code GET  /software/:id} : get the "id" software.
     *
     * @param id the id of the software to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the software, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Software> getSoftware(@PathVariable("id") Long id) {
        log.debug("REST request to get Software : {}", id);
        Optional<Software> software = softwareRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(software);
    }

    /**
     * {@code DELETE  /software/:id} : delete the "id" software.
     *
     * @param id the id of the software to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoftware(@PathVariable("id") Long id) {
        log.debug("REST request to delete Software : {}", id);
        softwareRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
