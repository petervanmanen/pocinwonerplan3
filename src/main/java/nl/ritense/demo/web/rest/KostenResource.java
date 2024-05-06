package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kosten;
import nl.ritense.demo.repository.KostenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kosten}.
 */
@RestController
@RequestMapping("/api/kostens")
@Transactional
public class KostenResource {

    private final Logger log = LoggerFactory.getLogger(KostenResource.class);

    private static final String ENTITY_NAME = "kosten";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KostenRepository kostenRepository;

    public KostenResource(KostenRepository kostenRepository) {
        this.kostenRepository = kostenRepository;
    }

    /**
     * {@code POST  /kostens} : Create a new kosten.
     *
     * @param kosten the kosten to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kosten, or with status {@code 400 (Bad Request)} if the kosten has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kosten> createKosten(@Valid @RequestBody Kosten kosten) throws URISyntaxException {
        log.debug("REST request to save Kosten : {}", kosten);
        if (kosten.getId() != null) {
            throw new BadRequestAlertException("A new kosten cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kosten = kostenRepository.save(kosten);
        return ResponseEntity.created(new URI("/api/kostens/" + kosten.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kosten.getId().toString()))
            .body(kosten);
    }

    /**
     * {@code PUT  /kostens/:id} : Updates an existing kosten.
     *
     * @param id the id of the kosten to save.
     * @param kosten the kosten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kosten,
     * or with status {@code 400 (Bad Request)} if the kosten is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kosten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kosten> updateKosten(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Kosten kosten
    ) throws URISyntaxException {
        log.debug("REST request to update Kosten : {}, {}", id, kosten);
        if (kosten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kosten.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kostenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kosten = kostenRepository.save(kosten);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kosten.getId().toString()))
            .body(kosten);
    }

    /**
     * {@code PATCH  /kostens/:id} : Partial updates given fields of an existing kosten, field will ignore if it is null
     *
     * @param id the id of the kosten to save.
     * @param kosten the kosten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kosten,
     * or with status {@code 400 (Bad Request)} if the kosten is not valid,
     * or with status {@code 404 (Not Found)} if the kosten is not found,
     * or with status {@code 500 (Internal Server Error)} if the kosten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kosten> partialUpdateKosten(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Kosten kosten
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kosten partially : {}, {}", id, kosten);
        if (kosten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kosten.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kostenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kosten> result = kostenRepository
            .findById(kosten.getId())
            .map(existingKosten -> {
                if (kosten.getAangemaaktdoor() != null) {
                    existingKosten.setAangemaaktdoor(kosten.getAangemaaktdoor());
                }
                if (kosten.getAantal() != null) {
                    existingKosten.setAantal(kosten.getAantal());
                }
                if (kosten.getBedrag() != null) {
                    existingKosten.setBedrag(kosten.getBedrag());
                }
                if (kosten.getBedragtotaal() != null) {
                    existingKosten.setBedragtotaal(kosten.getBedragtotaal());
                }
                if (kosten.getDatumaanmaak() != null) {
                    existingKosten.setDatumaanmaak(kosten.getDatumaanmaak());
                }
                if (kosten.getDatummutatie() != null) {
                    existingKosten.setDatummutatie(kosten.getDatummutatie());
                }
                if (kosten.getEenheid() != null) {
                    existingKosten.setEenheid(kosten.getEenheid());
                }
                if (kosten.getGeaccordeerd() != null) {
                    existingKosten.setGeaccordeerd(kosten.getGeaccordeerd());
                }
                if (kosten.getGefactureerdop() != null) {
                    existingKosten.setGefactureerdop(kosten.getGefactureerdop());
                }
                if (kosten.getGemuteerddoor() != null) {
                    existingKosten.setGemuteerddoor(kosten.getGemuteerddoor());
                }
                if (kosten.getNaam() != null) {
                    existingKosten.setNaam(kosten.getNaam());
                }
                if (kosten.getOmschrijving() != null) {
                    existingKosten.setOmschrijving(kosten.getOmschrijving());
                }
                if (kosten.getOpbasisvangrondslag() != null) {
                    existingKosten.setOpbasisvangrondslag(kosten.getOpbasisvangrondslag());
                }
                if (kosten.getTarief() != null) {
                    existingKosten.setTarief(kosten.getTarief());
                }
                if (kosten.getType() != null) {
                    existingKosten.setType(kosten.getType());
                }
                if (kosten.getVastgesteldbedrag() != null) {
                    existingKosten.setVastgesteldbedrag(kosten.getVastgesteldbedrag());
                }

                return existingKosten;
            })
            .map(kostenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kosten.getId().toString())
        );
    }

    /**
     * {@code GET  /kostens} : get all the kostens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kostens in body.
     */
    @GetMapping("")
    public List<Kosten> getAllKostens() {
        log.debug("REST request to get all Kostens");
        return kostenRepository.findAll();
    }

    /**
     * {@code GET  /kostens/:id} : get the "id" kosten.
     *
     * @param id the id of the kosten to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kosten, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kosten> getKosten(@PathVariable("id") Long id) {
        log.debug("REST request to get Kosten : {}", id);
        Optional<Kosten> kosten = kostenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kosten);
    }

    /**
     * {@code DELETE  /kostens/:id} : delete the "id" kosten.
     *
     * @param id the id of the kosten to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKosten(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kosten : {}", id);
        kostenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
