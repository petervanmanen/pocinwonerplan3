package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Inburgeringstraject;
import nl.ritense.demo.repository.InburgeringstrajectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inburgeringstraject}.
 */
@RestController
@RequestMapping("/api/inburgeringstrajects")
@Transactional
public class InburgeringstrajectResource {

    private final Logger log = LoggerFactory.getLogger(InburgeringstrajectResource.class);

    private static final String ENTITY_NAME = "inburgeringstraject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InburgeringstrajectRepository inburgeringstrajectRepository;

    public InburgeringstrajectResource(InburgeringstrajectRepository inburgeringstrajectRepository) {
        this.inburgeringstrajectRepository = inburgeringstrajectRepository;
    }

    /**
     * {@code POST  /inburgeringstrajects} : Create a new inburgeringstraject.
     *
     * @param inburgeringstraject the inburgeringstraject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inburgeringstraject, or with status {@code 400 (Bad Request)} if the inburgeringstraject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inburgeringstraject> createInburgeringstraject(@Valid @RequestBody Inburgeringstraject inburgeringstraject)
        throws URISyntaxException {
        log.debug("REST request to save Inburgeringstraject : {}", inburgeringstraject);
        if (inburgeringstraject.getId() != null) {
            throw new BadRequestAlertException("A new inburgeringstraject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inburgeringstraject = inburgeringstrajectRepository.save(inburgeringstraject);
        return ResponseEntity.created(new URI("/api/inburgeringstrajects/" + inburgeringstraject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inburgeringstraject.getId().toString()))
            .body(inburgeringstraject);
    }

    /**
     * {@code GET  /inburgeringstrajects} : get all the inburgeringstrajects.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inburgeringstrajects in body.
     */
    @GetMapping("")
    public List<Inburgeringstraject> getAllInburgeringstrajects(@RequestParam(name = "filter", required = false) String filter) {
        if ("afgerondmetexamen-is-null".equals(filter)) {
            log.debug("REST request to get all Inburgeringstrajects where afgerondmetExamen is null");
            return StreamSupport.stream(inburgeringstrajectRepository.findAll().spliterator(), false)
                .filter(inburgeringstraject -> inburgeringstraject.getAfgerondmetExamen() == null)
                .toList();
        }
        log.debug("REST request to get all Inburgeringstrajects");
        return inburgeringstrajectRepository.findAll();
    }

    /**
     * {@code GET  /inburgeringstrajects/:id} : get the "id" inburgeringstraject.
     *
     * @param id the id of the inburgeringstraject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inburgeringstraject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inburgeringstraject> getInburgeringstraject(@PathVariable("id") Long id) {
        log.debug("REST request to get Inburgeringstraject : {}", id);
        Optional<Inburgeringstraject> inburgeringstraject = inburgeringstrajectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inburgeringstraject);
    }

    /**
     * {@code DELETE  /inburgeringstrajects/:id} : delete the "id" inburgeringstraject.
     *
     * @param id the id of the inburgeringstraject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInburgeringstraject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inburgeringstraject : {}", id);
        inburgeringstrajectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
