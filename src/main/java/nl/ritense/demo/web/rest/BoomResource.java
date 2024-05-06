package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Boom;
import nl.ritense.demo.repository.BoomRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Boom}.
 */
@RestController
@RequestMapping("/api/booms")
@Transactional
public class BoomResource {

    private final Logger log = LoggerFactory.getLogger(BoomResource.class);

    private static final String ENTITY_NAME = "boom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoomRepository boomRepository;

    public BoomResource(BoomRepository boomRepository) {
        this.boomRepository = boomRepository;
    }

    /**
     * {@code POST  /booms} : Create a new boom.
     *
     * @param boom the boom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boom, or with status {@code 400 (Bad Request)} if the boom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Boom> createBoom(@RequestBody Boom boom) throws URISyntaxException {
        log.debug("REST request to save Boom : {}", boom);
        if (boom.getId() != null) {
            throw new BadRequestAlertException("A new boom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        boom = boomRepository.save(boom);
        return ResponseEntity.created(new URI("/api/booms/" + boom.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, boom.getId().toString()))
            .body(boom);
    }

    /**
     * {@code PUT  /booms/:id} : Updates an existing boom.
     *
     * @param id the id of the boom to save.
     * @param boom the boom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boom,
     * or with status {@code 400 (Bad Request)} if the boom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Boom> updateBoom(@PathVariable(value = "id", required = false) final Long id, @RequestBody Boom boom)
        throws URISyntaxException {
        log.debug("REST request to update Boom : {}, {}", id, boom);
        if (boom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boom.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        boom = boomRepository.save(boom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boom.getId().toString()))
            .body(boom);
    }

    /**
     * {@code PATCH  /booms/:id} : Partial updates given fields of an existing boom, field will ignore if it is null
     *
     * @param id the id of the boom to save.
     * @param boom the boom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boom,
     * or with status {@code 400 (Bad Request)} if the boom is not valid,
     * or with status {@code 404 (Not Found)} if the boom is not found,
     * or with status {@code 500 (Internal Server Error)} if the boom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Boom> partialUpdateBoom(@PathVariable(value = "id", required = false) final Long id, @RequestBody Boom boom)
        throws URISyntaxException {
        log.debug("REST request to partial update Boom partially : {}, {}", id, boom);
        if (boom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boom.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Boom> result = boomRepository
            .findById(boom.getId())
            .map(existingBoom -> {
                if (boom.getBeleidsstatus() != null) {
                    existingBoom.setBeleidsstatus(boom.getBeleidsstatus());
                }
                if (boom.getBeoogdeomlooptijd() != null) {
                    existingBoom.setBeoogdeomlooptijd(boom.getBeoogdeomlooptijd());
                }
                if (boom.getBoombeeld() != null) {
                    existingBoom.setBoombeeld(boom.getBoombeeld());
                }
                if (boom.getBoombeschermer() != null) {
                    existingBoom.setBoombeschermer(boom.getBoombeschermer());
                }
                if (boom.getBoomgroep() != null) {
                    existingBoom.setBoomgroep(boom.getBoomgroep());
                }
                if (boom.getBoomhoogteactueel() != null) {
                    existingBoom.setBoomhoogteactueel(boom.getBoomhoogteactueel());
                }
                if (boom.getBoomhoogteklasseactueel() != null) {
                    existingBoom.setBoomhoogteklasseactueel(boom.getBoomhoogteklasseactueel());
                }
                if (boom.getBoomhoogteklasseeindebeeld() != null) {
                    existingBoom.setBoomhoogteklasseeindebeeld(boom.getBoomhoogteklasseeindebeeld());
                }
                if (boom.getBoomspiegel() != null) {
                    existingBoom.setBoomspiegel(boom.getBoomspiegel());
                }
                if (boom.getBoomtypebeschermingsstatusplus() != null) {
                    existingBoom.setBoomtypebeschermingsstatusplus(boom.getBoomtypebeschermingsstatusplus());
                }
                if (boom.getBoomvoorziening() != null) {
                    existingBoom.setBoomvoorziening(boom.getBoomvoorziening());
                }
                if (boom.getControlefrequentie() != null) {
                    existingBoom.setControlefrequentie(boom.getControlefrequentie());
                }
                if (boom.getFeestverlichting() != null) {
                    existingBoom.setFeestverlichting(boom.getFeestverlichting());
                }
                if (boom.getGroeifase() != null) {
                    existingBoom.setGroeifase(boom.getGroeifase());
                }
                if (boom.getGroeiplaatsinrichting() != null) {
                    existingBoom.setGroeiplaatsinrichting(boom.getGroeiplaatsinrichting());
                }
                if (boom.getHerplantplicht() != null) {
                    existingBoom.setHerplantplicht(boom.getHerplantplicht());
                }
                if (boom.getKiemjaar() != null) {
                    existingBoom.setKiemjaar(boom.getKiemjaar());
                }
                if (boom.getKroondiameterklasseactueel() != null) {
                    existingBoom.setKroondiameterklasseactueel(boom.getKroondiameterklasseactueel());
                }
                if (boom.getKroondiameterklasseeindebeeld() != null) {
                    existingBoom.setKroondiameterklasseeindebeeld(boom.getKroondiameterklasseeindebeeld());
                }
                if (boom.getKroonvolume() != null) {
                    existingBoom.setKroonvolume(boom.getKroonvolume());
                }
                if (boom.getLeeftijd() != null) {
                    existingBoom.setLeeftijd(boom.getLeeftijd());
                }
                if (boom.getMeerstammig() != null) {
                    existingBoom.setMeerstammig(boom.getMeerstammig());
                }
                if (boom.getMonetaireboomwaarde() != null) {
                    existingBoom.setMonetaireboomwaarde(boom.getMonetaireboomwaarde());
                }
                if (boom.getSnoeifase() != null) {
                    existingBoom.setSnoeifase(boom.getSnoeifase());
                }
                if (boom.getStamdiameter() != null) {
                    existingBoom.setStamdiameter(boom.getStamdiameter());
                }
                if (boom.getStamdiameterklasse() != null) {
                    existingBoom.setStamdiameterklasse(boom.getStamdiameterklasse());
                }
                if (boom.getTakvrijeruimtetotgebouw() != null) {
                    existingBoom.setTakvrijeruimtetotgebouw(boom.getTakvrijeruimtetotgebouw());
                }
                if (boom.getTakvrijestam() != null) {
                    existingBoom.setTakvrijestam(boom.getTakvrijestam());
                }
                if (boom.getTakvrijezoneprimair() != null) {
                    existingBoom.setTakvrijezoneprimair(boom.getTakvrijezoneprimair());
                }
                if (boom.getTakvrijezonesecundair() != null) {
                    existingBoom.setTakvrijezonesecundair(boom.getTakvrijezonesecundair());
                }
                if (boom.getTransponder() != null) {
                    existingBoom.setTransponder(boom.getTransponder());
                }
                if (boom.getType() != null) {
                    existingBoom.setType(boom.getType());
                }
                if (boom.getTypebeschermingsstatus() != null) {
                    existingBoom.setTypebeschermingsstatus(boom.getTypebeschermingsstatus());
                }
                if (boom.getTypeomgevingsrisicoklasse() != null) {
                    existingBoom.setTypeomgevingsrisicoklasse(boom.getTypeomgevingsrisicoklasse());
                }
                if (boom.getTypeplus() != null) {
                    existingBoom.setTypeplus(boom.getTypeplus());
                }
                if (boom.getTypevermeerderingsvorm() != null) {
                    existingBoom.setTypevermeerderingsvorm(boom.getTypevermeerderingsvorm());
                }
                if (boom.getVeiligheidsklasseboom() != null) {
                    existingBoom.setVeiligheidsklasseboom(boom.getVeiligheidsklasseboom());
                }
                if (boom.getVerplant() != null) {
                    existingBoom.setVerplant(boom.getVerplant());
                }
                if (boom.getVerplantbaar() != null) {
                    existingBoom.setVerplantbaar(boom.getVerplantbaar());
                }
                if (boom.getVrijedoorrijhoogte() != null) {
                    existingBoom.setVrijedoorrijhoogte(boom.getVrijedoorrijhoogte());
                }
                if (boom.getVrijedoorrijhoogteprimair() != null) {
                    existingBoom.setVrijedoorrijhoogteprimair(boom.getVrijedoorrijhoogteprimair());
                }
                if (boom.getVrijedoorrijhoogtesecundair() != null) {
                    existingBoom.setVrijedoorrijhoogtesecundair(boom.getVrijedoorrijhoogtesecundair());
                }
                if (boom.getVrijetakval() != null) {
                    existingBoom.setVrijetakval(boom.getVrijetakval());
                }

                return existingBoom;
            })
            .map(boomRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boom.getId().toString())
        );
    }

    /**
     * {@code GET  /booms} : get all the booms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of booms in body.
     */
    @GetMapping("")
    public List<Boom> getAllBooms() {
        log.debug("REST request to get all Booms");
        return boomRepository.findAll();
    }

    /**
     * {@code GET  /booms/:id} : get the "id" boom.
     *
     * @param id the id of the boom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Boom> getBoom(@PathVariable("id") Long id) {
        log.debug("REST request to get Boom : {}", id);
        Optional<Boom> boom = boomRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(boom);
    }

    /**
     * {@code DELETE  /booms/:id} : delete the "id" boom.
     *
     * @param id the id of the boom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoom(@PathVariable("id") Long id) {
        log.debug("REST request to delete Boom : {}", id);
        boomRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
