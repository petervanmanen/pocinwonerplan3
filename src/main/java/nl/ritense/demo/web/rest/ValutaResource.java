package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Valuta;
import nl.ritense.demo.repository.ValutaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Valuta}.
 */
@RestController
@RequestMapping("/api/valutas")
@Transactional
public class ValutaResource {

    private final Logger log = LoggerFactory.getLogger(ValutaResource.class);

    private static final String ENTITY_NAME = "valuta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValutaRepository valutaRepository;

    public ValutaResource(ValutaRepository valutaRepository) {
        this.valutaRepository = valutaRepository;
    }

    /**
     * {@code POST  /valutas} : Create a new valuta.
     *
     * @param valuta the valuta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new valuta, or with status {@code 400 (Bad Request)} if the valuta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Valuta> createValuta(@RequestBody Valuta valuta) throws URISyntaxException {
        log.debug("REST request to save Valuta : {}", valuta);
        if (valuta.getId() != null) {
            throw new BadRequestAlertException("A new valuta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        valuta = valutaRepository.save(valuta);
        return ResponseEntity.created(new URI("/api/valutas/" + valuta.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, valuta.getId().toString()))
            .body(valuta);
    }

    /**
     * {@code PUT  /valutas/:id} : Updates an existing valuta.
     *
     * @param id the id of the valuta to save.
     * @param valuta the valuta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated valuta,
     * or with status {@code 400 (Bad Request)} if the valuta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the valuta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Valuta> updateValuta(@PathVariable(value = "id", required = false) final Long id, @RequestBody Valuta valuta)
        throws URISyntaxException {
        log.debug("REST request to update Valuta : {}, {}", id, valuta);
        if (valuta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, valuta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!valutaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        valuta = valutaRepository.save(valuta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, valuta.getId().toString()))
            .body(valuta);
    }

    /**
     * {@code PATCH  /valutas/:id} : Partial updates given fields of an existing valuta, field will ignore if it is null
     *
     * @param id the id of the valuta to save.
     * @param valuta the valuta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated valuta,
     * or with status {@code 400 (Bad Request)} if the valuta is not valid,
     * or with status {@code 404 (Not Found)} if the valuta is not found,
     * or with status {@code 500 (Internal Server Error)} if the valuta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Valuta> partialUpdateValuta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Valuta valuta
    ) throws URISyntaxException {
        log.debug("REST request to partial update Valuta partially : {}, {}", id, valuta);
        if (valuta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, valuta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!valutaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Valuta> result = valutaRepository
            .findById(valuta.getId())
            .map(existingValuta -> {
                if (valuta.getDatumbegingeldigheid() != null) {
                    existingValuta.setDatumbegingeldigheid(valuta.getDatumbegingeldigheid());
                }
                if (valuta.getDatumeindegeldigheid() != null) {
                    existingValuta.setDatumeindegeldigheid(valuta.getDatumeindegeldigheid());
                }
                if (valuta.getNaam() != null) {
                    existingValuta.setNaam(valuta.getNaam());
                }
                if (valuta.getValutacode() != null) {
                    existingValuta.setValutacode(valuta.getValutacode());
                }

                return existingValuta;
            })
            .map(valutaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, valuta.getId().toString())
        );
    }

    /**
     * {@code GET  /valutas} : get all the valutas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of valutas in body.
     */
    @GetMapping("")
    public List<Valuta> getAllValutas() {
        log.debug("REST request to get all Valutas");
        return valutaRepository.findAll();
    }

    /**
     * {@code GET  /valutas/:id} : get the "id" valuta.
     *
     * @param id the id of the valuta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the valuta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Valuta> getValuta(@PathVariable("id") Long id) {
        log.debug("REST request to get Valuta : {}", id);
        Optional<Valuta> valuta = valutaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(valuta);
    }

    /**
     * {@code DELETE  /valutas/:id} : delete the "id" valuta.
     *
     * @param id the id of the valuta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValuta(@PathVariable("id") Long id) {
        log.debug("REST request to delete Valuta : {}", id);
        valutaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
