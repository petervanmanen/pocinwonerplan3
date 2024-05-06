package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verhardingsobject;
import nl.ritense.demo.repository.VerhardingsobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verhardingsobject}.
 */
@RestController
@RequestMapping("/api/verhardingsobjects")
@Transactional
public class VerhardingsobjectResource {

    private final Logger log = LoggerFactory.getLogger(VerhardingsobjectResource.class);

    private static final String ENTITY_NAME = "verhardingsobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerhardingsobjectRepository verhardingsobjectRepository;

    public VerhardingsobjectResource(VerhardingsobjectRepository verhardingsobjectRepository) {
        this.verhardingsobjectRepository = verhardingsobjectRepository;
    }

    /**
     * {@code POST  /verhardingsobjects} : Create a new verhardingsobject.
     *
     * @param verhardingsobject the verhardingsobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verhardingsobject, or with status {@code 400 (Bad Request)} if the verhardingsobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verhardingsobject> createVerhardingsobject(@Valid @RequestBody Verhardingsobject verhardingsobject)
        throws URISyntaxException {
        log.debug("REST request to save Verhardingsobject : {}", verhardingsobject);
        if (verhardingsobject.getId() != null) {
            throw new BadRequestAlertException("A new verhardingsobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verhardingsobject = verhardingsobjectRepository.save(verhardingsobject);
        return ResponseEntity.created(new URI("/api/verhardingsobjects/" + verhardingsobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verhardingsobject.getId().toString()))
            .body(verhardingsobject);
    }

    /**
     * {@code PUT  /verhardingsobjects/:id} : Updates an existing verhardingsobject.
     *
     * @param id the id of the verhardingsobject to save.
     * @param verhardingsobject the verhardingsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verhardingsobject,
     * or with status {@code 400 (Bad Request)} if the verhardingsobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verhardingsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verhardingsobject> updateVerhardingsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verhardingsobject verhardingsobject
    ) throws URISyntaxException {
        log.debug("REST request to update Verhardingsobject : {}, {}", id, verhardingsobject);
        if (verhardingsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verhardingsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verhardingsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verhardingsobject = verhardingsobjectRepository.save(verhardingsobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verhardingsobject.getId().toString()))
            .body(verhardingsobject);
    }

    /**
     * {@code PATCH  /verhardingsobjects/:id} : Partial updates given fields of an existing verhardingsobject, field will ignore if it is null
     *
     * @param id the id of the verhardingsobject to save.
     * @param verhardingsobject the verhardingsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verhardingsobject,
     * or with status {@code 400 (Bad Request)} if the verhardingsobject is not valid,
     * or with status {@code 404 (Not Found)} if the verhardingsobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the verhardingsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verhardingsobject> partialUpdateVerhardingsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verhardingsobject verhardingsobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verhardingsobject partially : {}, {}", id, verhardingsobject);
        if (verhardingsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verhardingsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verhardingsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verhardingsobject> result = verhardingsobjectRepository
            .findById(verhardingsobject.getId())
            .map(existingVerhardingsobject -> {
                if (verhardingsobject.getAanleghoogte() != null) {
                    existingVerhardingsobject.setAanleghoogte(verhardingsobject.getAanleghoogte());
                }
                if (verhardingsobject.getAanofvrijliggend() != null) {
                    existingVerhardingsobject.setAanofvrijliggend(verhardingsobject.getAanofvrijliggend());
                }
                if (verhardingsobject.getAantaldeklagen() != null) {
                    existingVerhardingsobject.setAantaldeklagen(verhardingsobject.getAantaldeklagen());
                }
                if (verhardingsobject.getAantalonderlagen() != null) {
                    existingVerhardingsobject.setAantalonderlagen(verhardingsobject.getAantalonderlagen());
                }
                if (verhardingsobject.getAantaltussenlagen() != null) {
                    existingVerhardingsobject.setAantaltussenlagen(verhardingsobject.getAantaltussenlagen());
                }
                if (verhardingsobject.getAfmeting() != null) {
                    existingVerhardingsobject.setAfmeting(verhardingsobject.getAfmeting());
                }
                if (verhardingsobject.getBelasting() != null) {
                    existingVerhardingsobject.setBelasting(verhardingsobject.getBelasting());
                }
                if (verhardingsobject.getBergendvermogen() != null) {
                    existingVerhardingsobject.setBergendvermogen(verhardingsobject.getBergendvermogen());
                }
                if (verhardingsobject.getBgtfysiekvoorkomen() != null) {
                    existingVerhardingsobject.setBgtfysiekvoorkomen(verhardingsobject.getBgtfysiekvoorkomen());
                }
                if (verhardingsobject.getBreedte() != null) {
                    existingVerhardingsobject.setBreedte(verhardingsobject.getBreedte());
                }
                if (verhardingsobject.getDikteconstructie() != null) {
                    existingVerhardingsobject.setDikteconstructie(verhardingsobject.getDikteconstructie());
                }
                if (verhardingsobject.getDraagkrachtig() != null) {
                    existingVerhardingsobject.setDraagkrachtig(verhardingsobject.getDraagkrachtig());
                }
                if (verhardingsobject.getFormaat() != null) {
                    existingVerhardingsobject.setFormaat(verhardingsobject.getFormaat());
                }
                if (verhardingsobject.getFysiekvoorkomenimgeo() != null) {
                    existingVerhardingsobject.setFysiekvoorkomenimgeo(verhardingsobject.getFysiekvoorkomenimgeo());
                }
                if (verhardingsobject.getGeluidsreducerend() != null) {
                    existingVerhardingsobject.setGeluidsreducerend(verhardingsobject.getGeluidsreducerend());
                }
                if (verhardingsobject.getJaarconserveren() != null) {
                    existingVerhardingsobject.setJaarconserveren(verhardingsobject.getJaarconserveren());
                }
                if (verhardingsobject.getJaaronderhouduitgevoerd() != null) {
                    existingVerhardingsobject.setJaaronderhouduitgevoerd(verhardingsobject.getJaaronderhouduitgevoerd());
                }
                if (verhardingsobject.getJaarpraktischeinde() != null) {
                    existingVerhardingsobject.setJaarpraktischeinde(verhardingsobject.getJaarpraktischeinde());
                }
                if (verhardingsobject.getKleur() != null) {
                    existingVerhardingsobject.setKleur(verhardingsobject.getKleur());
                }
                if (verhardingsobject.getKwaliteitsniveauactueel() != null) {
                    existingVerhardingsobject.setKwaliteitsniveauactueel(verhardingsobject.getKwaliteitsniveauactueel());
                }
                if (verhardingsobject.getKwaliteitsniveaugewenst() != null) {
                    existingVerhardingsobject.setKwaliteitsniveaugewenst(verhardingsobject.getKwaliteitsniveaugewenst());
                }
                if (verhardingsobject.getLengte() != null) {
                    existingVerhardingsobject.setLengte(verhardingsobject.getLengte());
                }
                if (verhardingsobject.getLengtekunstgras() != null) {
                    existingVerhardingsobject.setLengtekunstgras(verhardingsobject.getLengtekunstgras());
                }
                if (verhardingsobject.getLengtevoegen() != null) {
                    existingVerhardingsobject.setLengtevoegen(verhardingsobject.getLengtevoegen());
                }
                if (verhardingsobject.getLevensduur() != null) {
                    existingVerhardingsobject.setLevensduur(verhardingsobject.getLevensduur());
                }
                if (verhardingsobject.getMateriaal() != null) {
                    existingVerhardingsobject.setMateriaal(verhardingsobject.getMateriaal());
                }
                if (verhardingsobject.getMaximalevalhoogte() != null) {
                    existingVerhardingsobject.setMaximalevalhoogte(verhardingsobject.getMaximalevalhoogte());
                }
                if (verhardingsobject.getOmtrek() != null) {
                    existingVerhardingsobject.setOmtrek(verhardingsobject.getOmtrek());
                }
                if (verhardingsobject.getOndergrondcode() != null) {
                    existingVerhardingsobject.setOndergrondcode(verhardingsobject.getOndergrondcode());
                }
                if (verhardingsobject.getOppervlakte() != null) {
                    existingVerhardingsobject.setOppervlakte(verhardingsobject.getOppervlakte());
                }
                if (verhardingsobject.getOptalud() != null) {
                    existingVerhardingsobject.setOptalud(verhardingsobject.getOptalud());
                }
                if (verhardingsobject.getPlaatsorientatie() != null) {
                    existingVerhardingsobject.setPlaatsorientatie(verhardingsobject.getPlaatsorientatie());
                }
                if (verhardingsobject.getPrijsaanschaf() != null) {
                    existingVerhardingsobject.setPrijsaanschaf(verhardingsobject.getPrijsaanschaf());
                }
                if (verhardingsobject.getRijstrook() != null) {
                    existingVerhardingsobject.setRijstrook(verhardingsobject.getRijstrook());
                }
                if (verhardingsobject.getSoortvoeg() != null) {
                    existingVerhardingsobject.setSoortvoeg(verhardingsobject.getSoortvoeg());
                }
                if (verhardingsobject.getToelichtinggemengdebestrating() != null) {
                    existingVerhardingsobject.setToelichtinggemengdebestrating(verhardingsobject.getToelichtinggemengdebestrating());
                }
                if (verhardingsobject.getType() != null) {
                    existingVerhardingsobject.setType(verhardingsobject.getType());
                }
                if (verhardingsobject.getTypeconstructie() != null) {
                    existingVerhardingsobject.setTypeconstructie(verhardingsobject.getTypeconstructie());
                }
                if (verhardingsobject.getTypefundering() != null) {
                    existingVerhardingsobject.setTypefundering(verhardingsobject.getTypefundering());
                }
                if (verhardingsobject.getTypeplus() != null) {
                    existingVerhardingsobject.setTypeplus(verhardingsobject.getTypeplus());
                }
                if (verhardingsobject.getTypeplus2() != null) {
                    existingVerhardingsobject.setTypeplus2(verhardingsobject.getTypeplus2());
                }
                if (verhardingsobject.getTyperijstrook() != null) {
                    existingVerhardingsobject.setTyperijstrook(verhardingsobject.getTyperijstrook());
                }
                if (verhardingsobject.getTypevoeg() != null) {
                    existingVerhardingsobject.setTypevoeg(verhardingsobject.getTypevoeg());
                }
                if (verhardingsobject.getTypevoegvulling() != null) {
                    existingVerhardingsobject.setTypevoegvulling(verhardingsobject.getTypevoegvulling());
                }
                if (verhardingsobject.getVegen() != null) {
                    existingVerhardingsobject.setVegen(verhardingsobject.getVegen());
                }
                if (verhardingsobject.getVerhardingsobjectconstructielaag() != null) {
                    existingVerhardingsobject.setVerhardingsobjectconstructielaag(verhardingsobject.getVerhardingsobjectconstructielaag());
                }
                if (verhardingsobject.getVerhardingsobjectmodaliteit() != null) {
                    existingVerhardingsobject.setVerhardingsobjectmodaliteit(verhardingsobject.getVerhardingsobjectmodaliteit());
                }
                if (verhardingsobject.getVerhardingsobjectrand() != null) {
                    existingVerhardingsobject.setVerhardingsobjectrand(verhardingsobject.getVerhardingsobjectrand());
                }
                if (verhardingsobject.getVerhardingsobjectwegfunctie() != null) {
                    existingVerhardingsobject.setVerhardingsobjectwegfunctie(verhardingsobject.getVerhardingsobjectwegfunctie());
                }
                if (verhardingsobject.getVerhoogdeligging() != null) {
                    existingVerhardingsobject.setVerhoogdeligging(verhardingsobject.getVerhoogdeligging());
                }
                if (verhardingsobject.getVulmateriaalkunstgras() != null) {
                    existingVerhardingsobject.setVulmateriaalkunstgras(verhardingsobject.getVulmateriaalkunstgras());
                }
                if (verhardingsobject.getWaterdoorlatendheid() != null) {
                    existingVerhardingsobject.setWaterdoorlatendheid(verhardingsobject.getWaterdoorlatendheid());
                }
                if (verhardingsobject.getWegas() != null) {
                    existingVerhardingsobject.setWegas(verhardingsobject.getWegas());
                }
                if (verhardingsobject.getWegcategoriedv() != null) {
                    existingVerhardingsobject.setWegcategoriedv(verhardingsobject.getWegcategoriedv());
                }
                if (verhardingsobject.getWegcategoriedvplus() != null) {
                    existingVerhardingsobject.setWegcategoriedvplus(verhardingsobject.getWegcategoriedvplus());
                }
                if (verhardingsobject.getWegnummer() != null) {
                    existingVerhardingsobject.setWegnummer(verhardingsobject.getWegnummer());
                }
                if (verhardingsobject.getWegtypebestaand() != null) {
                    existingVerhardingsobject.setWegtypebestaand(verhardingsobject.getWegtypebestaand());
                }
                if (verhardingsobject.getWegvak() != null) {
                    existingVerhardingsobject.setWegvak(verhardingsobject.getWegvak());
                }
                if (verhardingsobject.getWegvaknummer() != null) {
                    existingVerhardingsobject.setWegvaknummer(verhardingsobject.getWegvaknummer());
                }

                return existingVerhardingsobject;
            })
            .map(verhardingsobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verhardingsobject.getId().toString())
        );
    }

    /**
     * {@code GET  /verhardingsobjects} : get all the verhardingsobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verhardingsobjects in body.
     */
    @GetMapping("")
    public List<Verhardingsobject> getAllVerhardingsobjects() {
        log.debug("REST request to get all Verhardingsobjects");
        return verhardingsobjectRepository.findAll();
    }

    /**
     * {@code GET  /verhardingsobjects/:id} : get the "id" verhardingsobject.
     *
     * @param id the id of the verhardingsobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verhardingsobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verhardingsobject> getVerhardingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Verhardingsobject : {}", id);
        Optional<Verhardingsobject> verhardingsobject = verhardingsobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verhardingsobject);
    }

    /**
     * {@code DELETE  /verhardingsobjects/:id} : delete the "id" verhardingsobject.
     *
     * @param id the id of the verhardingsobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerhardingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verhardingsobject : {}", id);
        verhardingsobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
