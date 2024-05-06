package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kadastralemutatie;
import nl.ritense.demo.repository.KadastralemutatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kadastralemutatie}.
 */
@RestController
@RequestMapping("/api/kadastralemutaties")
@Transactional
public class KadastralemutatieResource {

    private final Logger log = LoggerFactory.getLogger(KadastralemutatieResource.class);

    private static final String ENTITY_NAME = "kadastralemutatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KadastralemutatieRepository kadastralemutatieRepository;

    public KadastralemutatieResource(KadastralemutatieRepository kadastralemutatieRepository) {
        this.kadastralemutatieRepository = kadastralemutatieRepository;
    }

    /**
     * {@code POST  /kadastralemutaties} : Create a new kadastralemutatie.
     *
     * @param kadastralemutatie the kadastralemutatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kadastralemutatie, or with status {@code 400 (Bad Request)} if the kadastralemutatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kadastralemutatie> createKadastralemutatie(@RequestBody Kadastralemutatie kadastralemutatie)
        throws URISyntaxException {
        log.debug("REST request to save Kadastralemutatie : {}", kadastralemutatie);
        if (kadastralemutatie.getId() != null) {
            throw new BadRequestAlertException("A new kadastralemutatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kadastralemutatie = kadastralemutatieRepository.save(kadastralemutatie);
        return ResponseEntity.created(new URI("/api/kadastralemutaties/" + kadastralemutatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kadastralemutatie.getId().toString()))
            .body(kadastralemutatie);
    }

    /**
     * {@code PUT  /kadastralemutaties/:id} : Updates an existing kadastralemutatie.
     *
     * @param id the id of the kadastralemutatie to save.
     * @param kadastralemutatie the kadastralemutatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastralemutatie,
     * or with status {@code 400 (Bad Request)} if the kadastralemutatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kadastralemutatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kadastralemutatie> updateKadastralemutatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kadastralemutatie kadastralemutatie
    ) throws URISyntaxException {
        log.debug("REST request to update Kadastralemutatie : {}, {}", id, kadastralemutatie);
        if (kadastralemutatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastralemutatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastralemutatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kadastralemutatie = kadastralemutatieRepository.save(kadastralemutatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastralemutatie.getId().toString()))
            .body(kadastralemutatie);
    }

    /**
     * {@code PATCH  /kadastralemutaties/:id} : Partial updates given fields of an existing kadastralemutatie, field will ignore if it is null
     *
     * @param id the id of the kadastralemutatie to save.
     * @param kadastralemutatie the kadastralemutatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastralemutatie,
     * or with status {@code 400 (Bad Request)} if the kadastralemutatie is not valid,
     * or with status {@code 404 (Not Found)} if the kadastralemutatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the kadastralemutatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kadastralemutatie> partialUpdateKadastralemutatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kadastralemutatie kadastralemutatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kadastralemutatie partially : {}, {}", id, kadastralemutatie);
        if (kadastralemutatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastralemutatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastralemutatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kadastralemutatie> result = kadastralemutatieRepository
            .findById(kadastralemutatie.getId())
            .map(kadastralemutatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastralemutatie.getId().toString())
        );
    }

    /**
     * {@code GET  /kadastralemutaties} : get all the kadastralemutaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kadastralemutaties in body.
     */
    @GetMapping("")
    public List<Kadastralemutatie> getAllKadastralemutaties() {
        log.debug("REST request to get all Kadastralemutaties");
        return kadastralemutatieRepository.findAll();
    }

    /**
     * {@code GET  /kadastralemutaties/:id} : get the "id" kadastralemutatie.
     *
     * @param id the id of the kadastralemutatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kadastralemutatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kadastralemutatie> getKadastralemutatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Kadastralemutatie : {}", id);
        Optional<Kadastralemutatie> kadastralemutatie = kadastralemutatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kadastralemutatie);
    }

    /**
     * {@code DELETE  /kadastralemutaties/:id} : delete the "id" kadastralemutatie.
     *
     * @param id the id of the kadastralemutatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKadastralemutatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kadastralemutatie : {}", id);
        kadastralemutatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
