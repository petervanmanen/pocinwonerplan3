package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kadastralegemeente;
import nl.ritense.demo.repository.KadastralegemeenteRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kadastralegemeente}.
 */
@RestController
@RequestMapping("/api/kadastralegemeentes")
@Transactional
public class KadastralegemeenteResource {

    private final Logger log = LoggerFactory.getLogger(KadastralegemeenteResource.class);

    private static final String ENTITY_NAME = "kadastralegemeente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KadastralegemeenteRepository kadastralegemeenteRepository;

    public KadastralegemeenteResource(KadastralegemeenteRepository kadastralegemeenteRepository) {
        this.kadastralegemeenteRepository = kadastralegemeenteRepository;
    }

    /**
     * {@code POST  /kadastralegemeentes} : Create a new kadastralegemeente.
     *
     * @param kadastralegemeente the kadastralegemeente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kadastralegemeente, or with status {@code 400 (Bad Request)} if the kadastralegemeente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kadastralegemeente> createKadastralegemeente(@RequestBody Kadastralegemeente kadastralegemeente)
        throws URISyntaxException {
        log.debug("REST request to save Kadastralegemeente : {}", kadastralegemeente);
        if (kadastralegemeente.getId() != null) {
            throw new BadRequestAlertException("A new kadastralegemeente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kadastralegemeente = kadastralegemeenteRepository.save(kadastralegemeente);
        return ResponseEntity.created(new URI("/api/kadastralegemeentes/" + kadastralegemeente.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kadastralegemeente.getId().toString()))
            .body(kadastralegemeente);
    }

    /**
     * {@code PUT  /kadastralegemeentes/:id} : Updates an existing kadastralegemeente.
     *
     * @param id the id of the kadastralegemeente to save.
     * @param kadastralegemeente the kadastralegemeente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastralegemeente,
     * or with status {@code 400 (Bad Request)} if the kadastralegemeente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kadastralegemeente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kadastralegemeente> updateKadastralegemeente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kadastralegemeente kadastralegemeente
    ) throws URISyntaxException {
        log.debug("REST request to update Kadastralegemeente : {}, {}", id, kadastralegemeente);
        if (kadastralegemeente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastralegemeente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastralegemeenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kadastralegemeente = kadastralegemeenteRepository.save(kadastralegemeente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastralegemeente.getId().toString()))
            .body(kadastralegemeente);
    }

    /**
     * {@code PATCH  /kadastralegemeentes/:id} : Partial updates given fields of an existing kadastralegemeente, field will ignore if it is null
     *
     * @param id the id of the kadastralegemeente to save.
     * @param kadastralegemeente the kadastralegemeente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastralegemeente,
     * or with status {@code 400 (Bad Request)} if the kadastralegemeente is not valid,
     * or with status {@code 404 (Not Found)} if the kadastralegemeente is not found,
     * or with status {@code 500 (Internal Server Error)} if the kadastralegemeente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kadastralegemeente> partialUpdateKadastralegemeente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kadastralegemeente kadastralegemeente
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kadastralegemeente partially : {}, {}", id, kadastralegemeente);
        if (kadastralegemeente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastralegemeente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastralegemeenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kadastralegemeente> result = kadastralegemeenteRepository
            .findById(kadastralegemeente.getId())
            .map(existingKadastralegemeente -> {
                if (kadastralegemeente.getDatumbegingeldigheidkadastralegemeente() != null) {
                    existingKadastralegemeente.setDatumbegingeldigheidkadastralegemeente(
                        kadastralegemeente.getDatumbegingeldigheidkadastralegemeente()
                    );
                }
                if (kadastralegemeente.getDatumeindegeldigheidkadastralegemeente() != null) {
                    existingKadastralegemeente.setDatumeindegeldigheidkadastralegemeente(
                        kadastralegemeente.getDatumeindegeldigheidkadastralegemeente()
                    );
                }
                if (kadastralegemeente.getKadastralegemeentecode() != null) {
                    existingKadastralegemeente.setKadastralegemeentecode(kadastralegemeente.getKadastralegemeentecode());
                }
                if (kadastralegemeente.getNaam() != null) {
                    existingKadastralegemeente.setNaam(kadastralegemeente.getNaam());
                }

                return existingKadastralegemeente;
            })
            .map(kadastralegemeenteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastralegemeente.getId().toString())
        );
    }

    /**
     * {@code GET  /kadastralegemeentes} : get all the kadastralegemeentes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kadastralegemeentes in body.
     */
    @GetMapping("")
    public List<Kadastralegemeente> getAllKadastralegemeentes() {
        log.debug("REST request to get all Kadastralegemeentes");
        return kadastralegemeenteRepository.findAll();
    }

    /**
     * {@code GET  /kadastralegemeentes/:id} : get the "id" kadastralegemeente.
     *
     * @param id the id of the kadastralegemeente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kadastralegemeente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kadastralegemeente> getKadastralegemeente(@PathVariable("id") Long id) {
        log.debug("REST request to get Kadastralegemeente : {}", id);
        Optional<Kadastralegemeente> kadastralegemeente = kadastralegemeenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kadastralegemeente);
    }

    /**
     * {@code DELETE  /kadastralegemeentes/:id} : delete the "id" kadastralegemeente.
     *
     * @param id the id of the kadastralegemeente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKadastralegemeente(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kadastralegemeente : {}", id);
        kadastralegemeenteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
