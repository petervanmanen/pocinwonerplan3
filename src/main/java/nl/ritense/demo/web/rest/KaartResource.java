package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kaart;
import nl.ritense.demo.repository.KaartRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kaart}.
 */
@RestController
@RequestMapping("/api/kaarts")
@Transactional
public class KaartResource {

    private final Logger log = LoggerFactory.getLogger(KaartResource.class);

    private static final String ENTITY_NAME = "kaart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KaartRepository kaartRepository;

    public KaartResource(KaartRepository kaartRepository) {
        this.kaartRepository = kaartRepository;
    }

    /**
     * {@code POST  /kaarts} : Create a new kaart.
     *
     * @param kaart the kaart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kaart, or with status {@code 400 (Bad Request)} if the kaart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kaart> createKaart(@RequestBody Kaart kaart) throws URISyntaxException {
        log.debug("REST request to save Kaart : {}", kaart);
        if (kaart.getId() != null) {
            throw new BadRequestAlertException("A new kaart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kaart = kaartRepository.save(kaart);
        return ResponseEntity.created(new URI("/api/kaarts/" + kaart.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kaart.getId().toString()))
            .body(kaart);
    }

    /**
     * {@code PUT  /kaarts/:id} : Updates an existing kaart.
     *
     * @param id the id of the kaart to save.
     * @param kaart the kaart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kaart,
     * or with status {@code 400 (Bad Request)} if the kaart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kaart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kaart> updateKaart(@PathVariable(value = "id", required = false) final Long id, @RequestBody Kaart kaart)
        throws URISyntaxException {
        log.debug("REST request to update Kaart : {}, {}", id, kaart);
        if (kaart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kaart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kaartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kaart = kaartRepository.save(kaart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kaart.getId().toString()))
            .body(kaart);
    }

    /**
     * {@code PATCH  /kaarts/:id} : Partial updates given fields of an existing kaart, field will ignore if it is null
     *
     * @param id the id of the kaart to save.
     * @param kaart the kaart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kaart,
     * or with status {@code 400 (Bad Request)} if the kaart is not valid,
     * or with status {@code 404 (Not Found)} if the kaart is not found,
     * or with status {@code 500 (Internal Server Error)} if the kaart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kaart> partialUpdateKaart(@PathVariable(value = "id", required = false) final Long id, @RequestBody Kaart kaart)
        throws URISyntaxException {
        log.debug("REST request to partial update Kaart partially : {}, {}", id, kaart);
        if (kaart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kaart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kaartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kaart> result = kaartRepository
            .findById(kaart.getId())
            .map(existingKaart -> {
                if (kaart.getKaart() != null) {
                    existingKaart.setKaart(kaart.getKaart());
                }
                if (kaart.getNaam() != null) {
                    existingKaart.setNaam(kaart.getNaam());
                }
                if (kaart.getOmschrijving() != null) {
                    existingKaart.setOmschrijving(kaart.getOmschrijving());
                }

                return existingKaart;
            })
            .map(kaartRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kaart.getId().toString())
        );
    }

    /**
     * {@code GET  /kaarts} : get all the kaarts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kaarts in body.
     */
    @GetMapping("")
    public List<Kaart> getAllKaarts() {
        log.debug("REST request to get all Kaarts");
        return kaartRepository.findAll();
    }

    /**
     * {@code GET  /kaarts/:id} : get the "id" kaart.
     *
     * @param id the id of the kaart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kaart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kaart> getKaart(@PathVariable("id") Long id) {
        log.debug("REST request to get Kaart : {}", id);
        Optional<Kaart> kaart = kaartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kaart);
    }

    /**
     * {@code DELETE  /kaarts/:id} : delete the "id" kaart.
     *
     * @param id the id of the kaart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKaart(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kaart : {}", id);
        kaartRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
