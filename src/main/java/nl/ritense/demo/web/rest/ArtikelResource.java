package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Artikel;
import nl.ritense.demo.repository.ArtikelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Artikel}.
 */
@RestController
@RequestMapping("/api/artikels")
@Transactional
public class ArtikelResource {

    private final Logger log = LoggerFactory.getLogger(ArtikelResource.class);

    private static final String ENTITY_NAME = "artikel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtikelRepository artikelRepository;

    public ArtikelResource(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    /**
     * {@code POST  /artikels} : Create a new artikel.
     *
     * @param artikel the artikel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artikel, or with status {@code 400 (Bad Request)} if the artikel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Artikel> createArtikel(@RequestBody Artikel artikel) throws URISyntaxException {
        log.debug("REST request to save Artikel : {}", artikel);
        if (artikel.getId() != null) {
            throw new BadRequestAlertException("A new artikel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        artikel = artikelRepository.save(artikel);
        return ResponseEntity.created(new URI("/api/artikels/" + artikel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, artikel.getId().toString()))
            .body(artikel);
    }

    /**
     * {@code GET  /artikels} : get all the artikels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artikels in body.
     */
    @GetMapping("")
    public List<Artikel> getAllArtikels() {
        log.debug("REST request to get all Artikels");
        return artikelRepository.findAll();
    }

    /**
     * {@code GET  /artikels/:id} : get the "id" artikel.
     *
     * @param id the id of the artikel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artikel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Artikel> getArtikel(@PathVariable("id") Long id) {
        log.debug("REST request to get Artikel : {}", id);
        Optional<Artikel> artikel = artikelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(artikel);
    }

    /**
     * {@code DELETE  /artikels/:id} : delete the "id" artikel.
     *
     * @param id the id of the artikel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtikel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Artikel : {}", id);
        artikelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
