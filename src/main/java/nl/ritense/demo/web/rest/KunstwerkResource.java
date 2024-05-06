package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kunstwerk;
import nl.ritense.demo.repository.KunstwerkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kunstwerk}.
 */
@RestController
@RequestMapping("/api/kunstwerks")
@Transactional
public class KunstwerkResource {

    private final Logger log = LoggerFactory.getLogger(KunstwerkResource.class);

    private static final String ENTITY_NAME = "kunstwerk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KunstwerkRepository kunstwerkRepository;

    public KunstwerkResource(KunstwerkRepository kunstwerkRepository) {
        this.kunstwerkRepository = kunstwerkRepository;
    }

    /**
     * {@code POST  /kunstwerks} : Create a new kunstwerk.
     *
     * @param kunstwerk the kunstwerk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kunstwerk, or with status {@code 400 (Bad Request)} if the kunstwerk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kunstwerk> createKunstwerk(@Valid @RequestBody Kunstwerk kunstwerk) throws URISyntaxException {
        log.debug("REST request to save Kunstwerk : {}", kunstwerk);
        if (kunstwerk.getId() != null) {
            throw new BadRequestAlertException("A new kunstwerk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kunstwerk = kunstwerkRepository.save(kunstwerk);
        return ResponseEntity.created(new URI("/api/kunstwerks/" + kunstwerk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kunstwerk.getId().toString()))
            .body(kunstwerk);
    }

    /**
     * {@code PUT  /kunstwerks/:id} : Updates an existing kunstwerk.
     *
     * @param id the id of the kunstwerk to save.
     * @param kunstwerk the kunstwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kunstwerk,
     * or with status {@code 400 (Bad Request)} if the kunstwerk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kunstwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kunstwerk> updateKunstwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Kunstwerk kunstwerk
    ) throws URISyntaxException {
        log.debug("REST request to update Kunstwerk : {}, {}", id, kunstwerk);
        if (kunstwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kunstwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kunstwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kunstwerk = kunstwerkRepository.save(kunstwerk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kunstwerk.getId().toString()))
            .body(kunstwerk);
    }

    /**
     * {@code PATCH  /kunstwerks/:id} : Partial updates given fields of an existing kunstwerk, field will ignore if it is null
     *
     * @param id the id of the kunstwerk to save.
     * @param kunstwerk the kunstwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kunstwerk,
     * or with status {@code 400 (Bad Request)} if the kunstwerk is not valid,
     * or with status {@code 404 (Not Found)} if the kunstwerk is not found,
     * or with status {@code 500 (Internal Server Error)} if the kunstwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kunstwerk> partialUpdateKunstwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Kunstwerk kunstwerk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kunstwerk partially : {}, {}", id, kunstwerk);
        if (kunstwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kunstwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kunstwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kunstwerk> result = kunstwerkRepository
            .findById(kunstwerk.getId())
            .map(existingKunstwerk -> {
                if (kunstwerk.getAanleghoogte() != null) {
                    existingKunstwerk.setAanleghoogte(kunstwerk.getAanleghoogte());
                }
                if (kunstwerk.getAntigraffitivoorziening() != null) {
                    existingKunstwerk.setAntigraffitivoorziening(kunstwerk.getAntigraffitivoorziening());
                }
                if (kunstwerk.getBereikbaarheid() != null) {
                    existingKunstwerk.setBereikbaarheid(kunstwerk.getBereikbaarheid());
                }
                if (kunstwerk.getBreedte() != null) {
                    existingKunstwerk.setBreedte(kunstwerk.getBreedte());
                }
                if (kunstwerk.getConstructietype() != null) {
                    existingKunstwerk.setConstructietype(kunstwerk.getConstructietype());
                }
                if (kunstwerk.getGewicht() != null) {
                    existingKunstwerk.setGewicht(kunstwerk.getGewicht());
                }
                if (kunstwerk.getHoogte() != null) {
                    existingKunstwerk.setHoogte(kunstwerk.getHoogte());
                }
                if (kunstwerk.getInstallateur() != null) {
                    existingKunstwerk.setInstallateur(kunstwerk.getInstallateur());
                }
                if (kunstwerk.getJaarconserveren() != null) {
                    existingKunstwerk.setJaarconserveren(kunstwerk.getJaarconserveren());
                }
                if (kunstwerk.getJaaronderhouduitgevoerd() != null) {
                    existingKunstwerk.setJaaronderhouduitgevoerd(kunstwerk.getJaaronderhouduitgevoerd());
                }
                if (kunstwerk.getJaarrenovatie() != null) {
                    existingKunstwerk.setJaarrenovatie(kunstwerk.getJaarrenovatie());
                }
                if (kunstwerk.getJaarvervanging() != null) {
                    existingKunstwerk.setJaarvervanging(kunstwerk.getJaarvervanging());
                }
                if (kunstwerk.getKilometreringbegin() != null) {
                    existingKunstwerk.setKilometreringbegin(kunstwerk.getKilometreringbegin());
                }
                if (kunstwerk.getKilometreringeinde() != null) {
                    existingKunstwerk.setKilometreringeinde(kunstwerk.getKilometreringeinde());
                }
                if (kunstwerk.getKleur() != null) {
                    existingKunstwerk.setKleur(kunstwerk.getKleur());
                }
                if (kunstwerk.getKunstwerkbereikbaarheidplus() != null) {
                    existingKunstwerk.setKunstwerkbereikbaarheidplus(kunstwerk.getKunstwerkbereikbaarheidplus());
                }
                if (kunstwerk.getKunstwerkmateriaal() != null) {
                    existingKunstwerk.setKunstwerkmateriaal(kunstwerk.getKunstwerkmateriaal());
                }
                if (kunstwerk.getKwaliteitsniveauactueel() != null) {
                    existingKunstwerk.setKwaliteitsniveauactueel(kunstwerk.getKwaliteitsniveauactueel());
                }
                if (kunstwerk.getKwaliteitsniveaugewenst() != null) {
                    existingKunstwerk.setKwaliteitsniveaugewenst(kunstwerk.getKwaliteitsniveaugewenst());
                }
                if (kunstwerk.getLengte() != null) {
                    existingKunstwerk.setLengte(kunstwerk.getLengte());
                }
                if (kunstwerk.getLeverancier() != null) {
                    existingKunstwerk.setLeverancier(kunstwerk.getLeverancier());
                }
                if (kunstwerk.getLooprichel() != null) {
                    existingKunstwerk.setLooprichel(kunstwerk.getLooprichel());
                }
                if (kunstwerk.getMinimumconditiescore() != null) {
                    existingKunstwerk.setMinimumconditiescore(kunstwerk.getMinimumconditiescore());
                }
                if (kunstwerk.getMonument() != null) {
                    existingKunstwerk.setMonument(kunstwerk.getMonument());
                }
                if (kunstwerk.getMonumentnummer() != null) {
                    existingKunstwerk.setMonumentnummer(kunstwerk.getMonumentnummer());
                }
                if (kunstwerk.getEobjectnaam() != null) {
                    existingKunstwerk.setEobjectnaam(kunstwerk.getEobjectnaam());
                }
                if (kunstwerk.getEobjectnummer() != null) {
                    existingKunstwerk.setEobjectnummer(kunstwerk.getEobjectnummer());
                }
                if (kunstwerk.getOnderhoudsregime() != null) {
                    existingKunstwerk.setOnderhoudsregime(kunstwerk.getOnderhoudsregime());
                }
                if (kunstwerk.getOppervlakte() != null) {
                    existingKunstwerk.setOppervlakte(kunstwerk.getOppervlakte());
                }
                if (kunstwerk.getOrientatie() != null) {
                    existingKunstwerk.setOrientatie(kunstwerk.getOrientatie());
                }
                if (kunstwerk.getTechnischelevensduur() != null) {
                    existingKunstwerk.setTechnischelevensduur(kunstwerk.getTechnischelevensduur());
                }
                if (kunstwerk.getTypefundering() != null) {
                    existingKunstwerk.setTypefundering(kunstwerk.getTypefundering());
                }
                if (kunstwerk.getTypemonument() != null) {
                    existingKunstwerk.setTypemonument(kunstwerk.getTypemonument());
                }
                if (kunstwerk.getVervangingswaarde() != null) {
                    existingKunstwerk.setVervangingswaarde(kunstwerk.getVervangingswaarde());
                }
                if (kunstwerk.getWegnummer() != null) {
                    existingKunstwerk.setWegnummer(kunstwerk.getWegnummer());
                }

                return existingKunstwerk;
            })
            .map(kunstwerkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kunstwerk.getId().toString())
        );
    }

    /**
     * {@code GET  /kunstwerks} : get all the kunstwerks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kunstwerks in body.
     */
    @GetMapping("")
    public List<Kunstwerk> getAllKunstwerks() {
        log.debug("REST request to get all Kunstwerks");
        return kunstwerkRepository.findAll();
    }

    /**
     * {@code GET  /kunstwerks/:id} : get the "id" kunstwerk.
     *
     * @param id the id of the kunstwerk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kunstwerk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kunstwerk> getKunstwerk(@PathVariable("id") Long id) {
        log.debug("REST request to get Kunstwerk : {}", id);
        Optional<Kunstwerk> kunstwerk = kunstwerkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kunstwerk);
    }

    /**
     * {@code DELETE  /kunstwerks/:id} : delete the "id" kunstwerk.
     *
     * @param id the id of the kunstwerk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKunstwerk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kunstwerk : {}", id);
        kunstwerkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
