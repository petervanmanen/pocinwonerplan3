package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Meubilair;
import nl.ritense.demo.repository.MeubilairRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Meubilair}.
 */
@RestController
@RequestMapping("/api/meubilairs")
@Transactional
public class MeubilairResource {

    private final Logger log = LoggerFactory.getLogger(MeubilairResource.class);

    private static final String ENTITY_NAME = "meubilair";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeubilairRepository meubilairRepository;

    public MeubilairResource(MeubilairRepository meubilairRepository) {
        this.meubilairRepository = meubilairRepository;
    }

    /**
     * {@code POST  /meubilairs} : Create a new meubilair.
     *
     * @param meubilair the meubilair to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meubilair, or with status {@code 400 (Bad Request)} if the meubilair has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Meubilair> createMeubilair(@RequestBody Meubilair meubilair) throws URISyntaxException {
        log.debug("REST request to save Meubilair : {}", meubilair);
        if (meubilair.getId() != null) {
            throw new BadRequestAlertException("A new meubilair cannot already have an ID", ENTITY_NAME, "idexists");
        }
        meubilair = meubilairRepository.save(meubilair);
        return ResponseEntity.created(new URI("/api/meubilairs/" + meubilair.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, meubilair.getId().toString()))
            .body(meubilair);
    }

    /**
     * {@code PUT  /meubilairs/:id} : Updates an existing meubilair.
     *
     * @param id the id of the meubilair to save.
     * @param meubilair the meubilair to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meubilair,
     * or with status {@code 400 (Bad Request)} if the meubilair is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meubilair couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Meubilair> updateMeubilair(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Meubilair meubilair
    ) throws URISyntaxException {
        log.debug("REST request to update Meubilair : {}, {}", id, meubilair);
        if (meubilair.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, meubilair.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!meubilairRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        meubilair = meubilairRepository.save(meubilair);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, meubilair.getId().toString()))
            .body(meubilair);
    }

    /**
     * {@code PATCH  /meubilairs/:id} : Partial updates given fields of an existing meubilair, field will ignore if it is null
     *
     * @param id the id of the meubilair to save.
     * @param meubilair the meubilair to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meubilair,
     * or with status {@code 400 (Bad Request)} if the meubilair is not valid,
     * or with status {@code 404 (Not Found)} if the meubilair is not found,
     * or with status {@code 500 (Internal Server Error)} if the meubilair couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Meubilair> partialUpdateMeubilair(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Meubilair meubilair
    ) throws URISyntaxException {
        log.debug("REST request to partial update Meubilair partially : {}, {}", id, meubilair);
        if (meubilair.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, meubilair.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!meubilairRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Meubilair> result = meubilairRepository
            .findById(meubilair.getId())
            .map(existingMeubilair -> {
                if (meubilair.getAanleghoogte() != null) {
                    existingMeubilair.setAanleghoogte(meubilair.getAanleghoogte());
                }
                if (meubilair.getBouwjaar() != null) {
                    existingMeubilair.setBouwjaar(meubilair.getBouwjaar());
                }
                if (meubilair.getBreedte() != null) {
                    existingMeubilair.setBreedte(meubilair.getBreedte());
                }
                if (meubilair.getDatumaanschaf() != null) {
                    existingMeubilair.setDatumaanschaf(meubilair.getDatumaanschaf());
                }
                if (meubilair.getDiameter() != null) {
                    existingMeubilair.setDiameter(meubilair.getDiameter());
                }
                if (meubilair.getFabrikant() != null) {
                    existingMeubilair.setFabrikant(meubilair.getFabrikant());
                }
                if (meubilair.getGewicht() != null) {
                    existingMeubilair.setGewicht(meubilair.getGewicht());
                }
                if (meubilair.getHoogte() != null) {
                    existingMeubilair.setHoogte(meubilair.getHoogte());
                }
                if (meubilair.getInstallateur() != null) {
                    existingMeubilair.setInstallateur(meubilair.getInstallateur());
                }
                if (meubilair.getJaaronderhouduitgevoerd() != null) {
                    existingMeubilair.setJaaronderhouduitgevoerd(meubilair.getJaaronderhouduitgevoerd());
                }
                if (meubilair.getJaarpraktischeinde() != null) {
                    existingMeubilair.setJaarpraktischeinde(meubilair.getJaarpraktischeinde());
                }
                if (meubilair.getKleur() != null) {
                    existingMeubilair.setKleur(meubilair.getKleur());
                }
                if (meubilair.getKwaliteitsniveauactueel() != null) {
                    existingMeubilair.setKwaliteitsniveauactueel(meubilair.getKwaliteitsniveauactueel());
                }
                if (meubilair.getKwaliteitsniveaugewenst() != null) {
                    existingMeubilair.setKwaliteitsniveaugewenst(meubilair.getKwaliteitsniveaugewenst());
                }
                if (meubilair.getLengte() != null) {
                    existingMeubilair.setLengte(meubilair.getLengte());
                }
                if (meubilair.getLeverancier() != null) {
                    existingMeubilair.setLeverancier(meubilair.getLeverancier());
                }
                if (meubilair.getMeubilairmateriaal() != null) {
                    existingMeubilair.setMeubilairmateriaal(meubilair.getMeubilairmateriaal());
                }
                if (meubilair.getModel() != null) {
                    existingMeubilair.setModel(meubilair.getModel());
                }
                if (meubilair.getOndergrond() != null) {
                    existingMeubilair.setOndergrond(meubilair.getOndergrond());
                }
                if (meubilair.getOppervlakte() != null) {
                    existingMeubilair.setOppervlakte(meubilair.getOppervlakte());
                }
                if (meubilair.getPrijsaanschaf() != null) {
                    existingMeubilair.setPrijsaanschaf(meubilair.getPrijsaanschaf());
                }
                if (meubilair.getSerienummer() != null) {
                    existingMeubilair.setSerienummer(meubilair.getSerienummer());
                }
                if (meubilair.getTransponder() != null) {
                    existingMeubilair.setTransponder(meubilair.getTransponder());
                }
                if (meubilair.getTransponderlocatie() != null) {
                    existingMeubilair.setTransponderlocatie(meubilair.getTransponderlocatie());
                }
                if (meubilair.getTypefundering() != null) {
                    existingMeubilair.setTypefundering(meubilair.getTypefundering());
                }
                if (meubilair.getTypeplaat() != null) {
                    existingMeubilair.setTypeplaat(meubilair.getTypeplaat());
                }

                return existingMeubilair;
            })
            .map(meubilairRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, meubilair.getId().toString())
        );
    }

    /**
     * {@code GET  /meubilairs} : get all the meubilairs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meubilairs in body.
     */
    @GetMapping("")
    public List<Meubilair> getAllMeubilairs() {
        log.debug("REST request to get all Meubilairs");
        return meubilairRepository.findAll();
    }

    /**
     * {@code GET  /meubilairs/:id} : get the "id" meubilair.
     *
     * @param id the id of the meubilair to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meubilair, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Meubilair> getMeubilair(@PathVariable("id") Long id) {
        log.debug("REST request to get Meubilair : {}", id);
        Optional<Meubilair> meubilair = meubilairRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(meubilair);
    }

    /**
     * {@code DELETE  /meubilairs/:id} : delete the "id" meubilair.
     *
     * @param id the id of the meubilair to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeubilair(@PathVariable("id") Long id) {
        log.debug("REST request to delete Meubilair : {}", id);
        meubilairRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
