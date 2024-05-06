package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gemeente;
import nl.ritense.demo.repository.GemeenteRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gemeente}.
 */
@RestController
@RequestMapping("/api/gemeentes")
@Transactional
public class GemeenteResource {

    private final Logger log = LoggerFactory.getLogger(GemeenteResource.class);

    private static final String ENTITY_NAME = "gemeente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GemeenteRepository gemeenteRepository;

    public GemeenteResource(GemeenteRepository gemeenteRepository) {
        this.gemeenteRepository = gemeenteRepository;
    }

    /**
     * {@code POST  /gemeentes} : Create a new gemeente.
     *
     * @param gemeente the gemeente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gemeente, or with status {@code 400 (Bad Request)} if the gemeente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gemeente> createGemeente(@RequestBody Gemeente gemeente) throws URISyntaxException {
        log.debug("REST request to save Gemeente : {}", gemeente);
        if (gemeente.getId() != null) {
            throw new BadRequestAlertException("A new gemeente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gemeente = gemeenteRepository.save(gemeente);
        return ResponseEntity.created(new URI("/api/gemeentes/" + gemeente.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gemeente.getId().toString()))
            .body(gemeente);
    }

    /**
     * {@code PUT  /gemeentes/:id} : Updates an existing gemeente.
     *
     * @param id the id of the gemeente to save.
     * @param gemeente the gemeente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemeente,
     * or with status {@code 400 (Bad Request)} if the gemeente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gemeente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gemeente> updateGemeente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gemeente gemeente
    ) throws URISyntaxException {
        log.debug("REST request to update Gemeente : {}, {}", id, gemeente);
        if (gemeente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemeente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemeenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gemeente = gemeenteRepository.save(gemeente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gemeente.getId().toString()))
            .body(gemeente);
    }

    /**
     * {@code PATCH  /gemeentes/:id} : Partial updates given fields of an existing gemeente, field will ignore if it is null
     *
     * @param id the id of the gemeente to save.
     * @param gemeente the gemeente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemeente,
     * or with status {@code 400 (Bad Request)} if the gemeente is not valid,
     * or with status {@code 404 (Not Found)} if the gemeente is not found,
     * or with status {@code 500 (Internal Server Error)} if the gemeente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gemeente> partialUpdateGemeente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gemeente gemeente
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gemeente partially : {}, {}", id, gemeente);
        if (gemeente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemeente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemeenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gemeente> result = gemeenteRepository
            .findById(gemeente.getId())
            .map(existingGemeente -> {
                if (gemeente.getDatumbegingeldigheid() != null) {
                    existingGemeente.setDatumbegingeldigheid(gemeente.getDatumbegingeldigheid());
                }
                if (gemeente.getDatumeinde() != null) {
                    existingGemeente.setDatumeinde(gemeente.getDatumeinde());
                }
                if (gemeente.getDatumeindegeldigheid() != null) {
                    existingGemeente.setDatumeindegeldigheid(gemeente.getDatumeindegeldigheid());
                }
                if (gemeente.getDatumingang() != null) {
                    existingGemeente.setDatumingang(gemeente.getDatumingang());
                }
                if (gemeente.getGeconstateerd() != null) {
                    existingGemeente.setGeconstateerd(gemeente.getGeconstateerd());
                }
                if (gemeente.getGemeentecode() != null) {
                    existingGemeente.setGemeentecode(gemeente.getGemeentecode());
                }
                if (gemeente.getGemeentenaam() != null) {
                    existingGemeente.setGemeentenaam(gemeente.getGemeentenaam());
                }
                if (gemeente.getGemeentenaamnen() != null) {
                    existingGemeente.setGemeentenaamnen(gemeente.getGemeentenaamnen());
                }
                if (gemeente.getGeometrie() != null) {
                    existingGemeente.setGeometrie(gemeente.getGeometrie());
                }
                if (gemeente.getIdentificatie() != null) {
                    existingGemeente.setIdentificatie(gemeente.getIdentificatie());
                }
                if (gemeente.getInonderzoek() != null) {
                    existingGemeente.setInonderzoek(gemeente.getInonderzoek());
                }
                if (gemeente.getVersie() != null) {
                    existingGemeente.setVersie(gemeente.getVersie());
                }

                return existingGemeente;
            })
            .map(gemeenteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gemeente.getId().toString())
        );
    }

    /**
     * {@code GET  /gemeentes} : get all the gemeentes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gemeentes in body.
     */
    @GetMapping("")
    public List<Gemeente> getAllGemeentes() {
        log.debug("REST request to get all Gemeentes");
        return gemeenteRepository.findAll();
    }

    /**
     * {@code GET  /gemeentes/:id} : get the "id" gemeente.
     *
     * @param id the id of the gemeente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gemeente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gemeente> getGemeente(@PathVariable("id") Long id) {
        log.debug("REST request to get Gemeente : {}", id);
        Optional<Gemeente> gemeente = gemeenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gemeente);
    }

    /**
     * {@code DELETE  /gemeentes/:id} : delete the "id" gemeente.
     *
     * @param id the id of the gemeente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGemeente(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gemeente : {}", id);
        gemeenteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
