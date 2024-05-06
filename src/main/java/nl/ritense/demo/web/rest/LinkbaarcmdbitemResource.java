package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Linkbaarcmdbitem;
import nl.ritense.demo.repository.LinkbaarcmdbitemRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Linkbaarcmdbitem}.
 */
@RestController
@RequestMapping("/api/linkbaarcmdbitems")
@Transactional
public class LinkbaarcmdbitemResource {

    private final Logger log = LoggerFactory.getLogger(LinkbaarcmdbitemResource.class);

    private static final String ENTITY_NAME = "linkbaarcmdbitem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinkbaarcmdbitemRepository linkbaarcmdbitemRepository;

    public LinkbaarcmdbitemResource(LinkbaarcmdbitemRepository linkbaarcmdbitemRepository) {
        this.linkbaarcmdbitemRepository = linkbaarcmdbitemRepository;
    }

    /**
     * {@code POST  /linkbaarcmdbitems} : Create a new linkbaarcmdbitem.
     *
     * @param linkbaarcmdbitem the linkbaarcmdbitem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linkbaarcmdbitem, or with status {@code 400 (Bad Request)} if the linkbaarcmdbitem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Linkbaarcmdbitem> createLinkbaarcmdbitem(@RequestBody Linkbaarcmdbitem linkbaarcmdbitem)
        throws URISyntaxException {
        log.debug("REST request to save Linkbaarcmdbitem : {}", linkbaarcmdbitem);
        if (linkbaarcmdbitem.getId() != null) {
            throw new BadRequestAlertException("A new linkbaarcmdbitem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        linkbaarcmdbitem = linkbaarcmdbitemRepository.save(linkbaarcmdbitem);
        return ResponseEntity.created(new URI("/api/linkbaarcmdbitems/" + linkbaarcmdbitem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, linkbaarcmdbitem.getId().toString()))
            .body(linkbaarcmdbitem);
    }

    /**
     * {@code GET  /linkbaarcmdbitems} : get all the linkbaarcmdbitems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linkbaarcmdbitems in body.
     */
    @GetMapping("")
    public List<Linkbaarcmdbitem> getAllLinkbaarcmdbitems() {
        log.debug("REST request to get all Linkbaarcmdbitems");
        return linkbaarcmdbitemRepository.findAll();
    }

    /**
     * {@code GET  /linkbaarcmdbitems/:id} : get the "id" linkbaarcmdbitem.
     *
     * @param id the id of the linkbaarcmdbitem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linkbaarcmdbitem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Linkbaarcmdbitem> getLinkbaarcmdbitem(@PathVariable("id") Long id) {
        log.debug("REST request to get Linkbaarcmdbitem : {}", id);
        Optional<Linkbaarcmdbitem> linkbaarcmdbitem = linkbaarcmdbitemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(linkbaarcmdbitem);
    }

    /**
     * {@code DELETE  /linkbaarcmdbitems/:id} : delete the "id" linkbaarcmdbitem.
     *
     * @param id the id of the linkbaarcmdbitem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLinkbaarcmdbitem(@PathVariable("id") Long id) {
        log.debug("REST request to delete Linkbaarcmdbitem : {}", id);
        linkbaarcmdbitemRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
