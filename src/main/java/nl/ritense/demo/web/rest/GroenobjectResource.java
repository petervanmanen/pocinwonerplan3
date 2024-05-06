package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Groenobject;
import nl.ritense.demo.repository.GroenobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Groenobject}.
 */
@RestController
@RequestMapping("/api/groenobjects")
@Transactional
public class GroenobjectResource {

    private final Logger log = LoggerFactory.getLogger(GroenobjectResource.class);

    private static final String ENTITY_NAME = "groenobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroenobjectRepository groenobjectRepository;

    public GroenobjectResource(GroenobjectRepository groenobjectRepository) {
        this.groenobjectRepository = groenobjectRepository;
    }

    /**
     * {@code POST  /groenobjects} : Create a new groenobject.
     *
     * @param groenobject the groenobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groenobject, or with status {@code 400 (Bad Request)} if the groenobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Groenobject> createGroenobject(@Valid @RequestBody Groenobject groenobject) throws URISyntaxException {
        log.debug("REST request to save Groenobject : {}", groenobject);
        if (groenobject.getId() != null) {
            throw new BadRequestAlertException("A new groenobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        groenobject = groenobjectRepository.save(groenobject);
        return ResponseEntity.created(new URI("/api/groenobjects/" + groenobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, groenobject.getId().toString()))
            .body(groenobject);
    }

    /**
     * {@code PUT  /groenobjects/:id} : Updates an existing groenobject.
     *
     * @param id the id of the groenobject to save.
     * @param groenobject the groenobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groenobject,
     * or with status {@code 400 (Bad Request)} if the groenobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groenobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Groenobject> updateGroenobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Groenobject groenobject
    ) throws URISyntaxException {
        log.debug("REST request to update Groenobject : {}, {}", id, groenobject);
        if (groenobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groenobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groenobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        groenobject = groenobjectRepository.save(groenobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groenobject.getId().toString()))
            .body(groenobject);
    }

    /**
     * {@code PATCH  /groenobjects/:id} : Partial updates given fields of an existing groenobject, field will ignore if it is null
     *
     * @param id the id of the groenobject to save.
     * @param groenobject the groenobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groenobject,
     * or with status {@code 400 (Bad Request)} if the groenobject is not valid,
     * or with status {@code 404 (Not Found)} if the groenobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the groenobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Groenobject> partialUpdateGroenobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Groenobject groenobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Groenobject partially : {}, {}", id, groenobject);
        if (groenobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groenobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groenobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Groenobject> result = groenobjectRepository
            .findById(groenobject.getId())
            .map(existingGroenobject -> {
                if (groenobject.getAantalobstakels() != null) {
                    existingGroenobject.setAantalobstakels(groenobject.getAantalobstakels());
                }
                if (groenobject.getAantalzijden() != null) {
                    existingGroenobject.setAantalzijden(groenobject.getAantalzijden());
                }
                if (groenobject.getAfvoeren() != null) {
                    existingGroenobject.setAfvoeren(groenobject.getAfvoeren());
                }
                if (groenobject.getBereikbaarheid() != null) {
                    existingGroenobject.setBereikbaarheid(groenobject.getBereikbaarheid());
                }
                if (groenobject.getBergendvermogen() != null) {
                    existingGroenobject.setBergendvermogen(groenobject.getBergendvermogen());
                }
                if (groenobject.getBewerkingspercentage() != null) {
                    existingGroenobject.setBewerkingspercentage(groenobject.getBewerkingspercentage());
                }
                if (groenobject.getBgtfysiekvoorkomen() != null) {
                    existingGroenobject.setBgtfysiekvoorkomen(groenobject.getBgtfysiekvoorkomen());
                }
                if (groenobject.getBollen() != null) {
                    existingGroenobject.setBollen(groenobject.getBollen());
                }
                if (groenobject.getBreedte() != null) {
                    existingGroenobject.setBreedte(groenobject.getBreedte());
                }
                if (groenobject.getBreedteklassehaag() != null) {
                    existingGroenobject.setBreedteklassehaag(groenobject.getBreedteklassehaag());
                }
                if (groenobject.getBvc() != null) {
                    existingGroenobject.setBvc(groenobject.getBvc());
                }
                if (groenobject.getCultuurhistorischwaardevol() != null) {
                    existingGroenobject.setCultuurhistorischwaardevol(groenobject.getCultuurhistorischwaardevol());
                }
                if (groenobject.getDraagkrachtig() != null) {
                    existingGroenobject.setDraagkrachtig(groenobject.getDraagkrachtig());
                }
                if (groenobject.getEcologischbeheer() != null) {
                    existingGroenobject.setEcologischbeheer(groenobject.getEcologischbeheer());
                }
                if (groenobject.getFysiekvoorkomenimgeo() != null) {
                    existingGroenobject.setFysiekvoorkomenimgeo(groenobject.getFysiekvoorkomenimgeo());
                }
                if (groenobject.getGewenstsluitingspercentage() != null) {
                    existingGroenobject.setGewenstsluitingspercentage(groenobject.getGewenstsluitingspercentage());
                }
                if (groenobject.getGroenobjectbereikbaarheidplus() != null) {
                    existingGroenobject.setGroenobjectbereikbaarheidplus(groenobject.getGroenobjectbereikbaarheidplus());
                }
                if (groenobject.getGroenobjectconstructielaag() != null) {
                    existingGroenobject.setGroenobjectconstructielaag(groenobject.getGroenobjectconstructielaag());
                }
                if (groenobject.getGroenobjectrand() != null) {
                    existingGroenobject.setGroenobjectrand(groenobject.getGroenobjectrand());
                }
                if (groenobject.getGroenobjectsoortnaam() != null) {
                    existingGroenobject.setGroenobjectsoortnaam(groenobject.getGroenobjectsoortnaam());
                }
                if (groenobject.getHaagvoetlengte() != null) {
                    existingGroenobject.setHaagvoetlengte(groenobject.getHaagvoetlengte());
                }
                if (groenobject.getHaagvoetoppervlakte() != null) {
                    existingGroenobject.setHaagvoetoppervlakte(groenobject.getHaagvoetoppervlakte());
                }
                if (groenobject.getHerplantplicht() != null) {
                    existingGroenobject.setHerplantplicht(groenobject.getHerplantplicht());
                }
                if (groenobject.getHoogte() != null) {
                    existingGroenobject.setHoogte(groenobject.getHoogte());
                }
                if (groenobject.getHoogteklassehaag() != null) {
                    existingGroenobject.setHoogteklassehaag(groenobject.getHoogteklassehaag());
                }
                if (groenobject.getKnipfrequentie() != null) {
                    existingGroenobject.setKnipfrequentie(groenobject.getKnipfrequentie());
                }
                if (groenobject.getKnipoppervlakte() != null) {
                    existingGroenobject.setKnipoppervlakte(groenobject.getKnipoppervlakte());
                }
                if (groenobject.getKwaliteitsniveauactueel() != null) {
                    existingGroenobject.setKwaliteitsniveauactueel(groenobject.getKwaliteitsniveauactueel());
                }
                if (groenobject.getKwaliteitsniveaugewenst() != null) {
                    existingGroenobject.setKwaliteitsniveaugewenst(groenobject.getKwaliteitsniveaugewenst());
                }
                if (groenobject.getLengte() != null) {
                    existingGroenobject.setLengte(groenobject.getLengte());
                }
                if (groenobject.getLeverancier() != null) {
                    existingGroenobject.setLeverancier(groenobject.getLeverancier());
                }
                if (groenobject.getMaaifrequentie() != null) {
                    existingGroenobject.setMaaifrequentie(groenobject.getMaaifrequentie());
                }
                if (groenobject.getMaximalevalhoogte() != null) {
                    existingGroenobject.setMaximalevalhoogte(groenobject.getMaximalevalhoogte());
                }
                if (groenobject.getEobjectnummer() != null) {
                    existingGroenobject.setEobjectnummer(groenobject.getEobjectnummer());
                }
                if (groenobject.getObstakels() != null) {
                    existingGroenobject.setObstakels(groenobject.getObstakels());
                }
                if (groenobject.getOmtrek() != null) {
                    existingGroenobject.setOmtrek(groenobject.getOmtrek());
                }
                if (groenobject.getOndergroei() != null) {
                    existingGroenobject.setOndergroei(groenobject.getOndergroei());
                }
                if (groenobject.getOppervlakte() != null) {
                    existingGroenobject.setOppervlakte(groenobject.getOppervlakte());
                }
                if (groenobject.getOptalud() != null) {
                    existingGroenobject.setOptalud(groenobject.getOptalud());
                }
                if (groenobject.getTaludsteilte() != null) {
                    existingGroenobject.setTaludsteilte(groenobject.getTaludsteilte());
                }
                if (groenobject.getType() != null) {
                    existingGroenobject.setType(groenobject.getType());
                }
                if (groenobject.getTypebewerking() != null) {
                    existingGroenobject.setTypebewerking(groenobject.getTypebewerking());
                }
                if (groenobject.getTypeomgevingsrisicoklasse() != null) {
                    existingGroenobject.setTypeomgevingsrisicoklasse(groenobject.getTypeomgevingsrisicoklasse());
                }
                if (groenobject.getTypeplus() != null) {
                    existingGroenobject.setTypeplus(groenobject.getTypeplus());
                }
                if (groenobject.getTypeplus2() != null) {
                    existingGroenobject.setTypeplus2(groenobject.getTypeplus2());
                }
                if (groenobject.getVeiligheidsklasseboom() != null) {
                    existingGroenobject.setVeiligheidsklasseboom(groenobject.getVeiligheidsklasseboom());
                }

                return existingGroenobject;
            })
            .map(groenobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groenobject.getId().toString())
        );
    }

    /**
     * {@code GET  /groenobjects} : get all the groenobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groenobjects in body.
     */
    @GetMapping("")
    public List<Groenobject> getAllGroenobjects() {
        log.debug("REST request to get all Groenobjects");
        return groenobjectRepository.findAll();
    }

    /**
     * {@code GET  /groenobjects/:id} : get the "id" groenobject.
     *
     * @param id the id of the groenobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groenobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Groenobject> getGroenobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Groenobject : {}", id);
        Optional<Groenobject> groenobject = groenobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groenobject);
    }

    /**
     * {@code DELETE  /groenobjects/:id} : delete the "id" groenobject.
     *
     * @param id the id of the groenobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroenobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Groenobject : {}", id);
        groenobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
