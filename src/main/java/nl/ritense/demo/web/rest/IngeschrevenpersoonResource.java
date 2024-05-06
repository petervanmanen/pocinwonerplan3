package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ingeschrevenpersoon;
import nl.ritense.demo.repository.IngeschrevenpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ingeschrevenpersoon}.
 */
@RestController
@RequestMapping("/api/ingeschrevenpersoons")
@Transactional
public class IngeschrevenpersoonResource {

    private final Logger log = LoggerFactory.getLogger(IngeschrevenpersoonResource.class);

    private static final String ENTITY_NAME = "ingeschrevenpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngeschrevenpersoonRepository ingeschrevenpersoonRepository;

    public IngeschrevenpersoonResource(IngeschrevenpersoonRepository ingeschrevenpersoonRepository) {
        this.ingeschrevenpersoonRepository = ingeschrevenpersoonRepository;
    }

    /**
     * {@code POST  /ingeschrevenpersoons} : Create a new ingeschrevenpersoon.
     *
     * @param ingeschrevenpersoon the ingeschrevenpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingeschrevenpersoon, or with status {@code 400 (Bad Request)} if the ingeschrevenpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ingeschrevenpersoon> createIngeschrevenpersoon(@RequestBody Ingeschrevenpersoon ingeschrevenpersoon)
        throws URISyntaxException {
        log.debug("REST request to save Ingeschrevenpersoon : {}", ingeschrevenpersoon);
        if (ingeschrevenpersoon.getId() != null) {
            throw new BadRequestAlertException("A new ingeschrevenpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ingeschrevenpersoon = ingeschrevenpersoonRepository.save(ingeschrevenpersoon);
        return ResponseEntity.created(new URI("/api/ingeschrevenpersoons/" + ingeschrevenpersoon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ingeschrevenpersoon.getId().toString()))
            .body(ingeschrevenpersoon);
    }

    /**
     * {@code PUT  /ingeschrevenpersoons/:id} : Updates an existing ingeschrevenpersoon.
     *
     * @param id the id of the ingeschrevenpersoon to save.
     * @param ingeschrevenpersoon the ingeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the ingeschrevenpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ingeschrevenpersoon> updateIngeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ingeschrevenpersoon ingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Ingeschrevenpersoon : {}, {}", id, ingeschrevenpersoon);
        if (ingeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ingeschrevenpersoon = ingeschrevenpersoonRepository.save(ingeschrevenpersoon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingeschrevenpersoon.getId().toString()))
            .body(ingeschrevenpersoon);
    }

    /**
     * {@code PATCH  /ingeschrevenpersoons/:id} : Partial updates given fields of an existing ingeschrevenpersoon, field will ignore if it is null
     *
     * @param id the id of the ingeschrevenpersoon to save.
     * @param ingeschrevenpersoon the ingeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the ingeschrevenpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the ingeschrevenpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the ingeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ingeschrevenpersoon> partialUpdateIngeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ingeschrevenpersoon ingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ingeschrevenpersoon partially : {}, {}", id, ingeschrevenpersoon);
        if (ingeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ingeschrevenpersoon> result = ingeschrevenpersoonRepository
            .findById(ingeschrevenpersoon.getId())
            .map(existingIngeschrevenpersoon -> {
                if (ingeschrevenpersoon.getAdresherkomst() != null) {
                    existingIngeschrevenpersoon.setAdresherkomst(ingeschrevenpersoon.getAdresherkomst());
                }
                if (ingeschrevenpersoon.getAnummer() != null) {
                    existingIngeschrevenpersoon.setAnummer(ingeschrevenpersoon.getAnummer());
                }
                if (ingeschrevenpersoon.getBeschrijvinglocatie() != null) {
                    existingIngeschrevenpersoon.setBeschrijvinglocatie(ingeschrevenpersoon.getBeschrijvinglocatie());
                }
                if (ingeschrevenpersoon.getBuitenlandsreisdocument() != null) {
                    existingIngeschrevenpersoon.setBuitenlandsreisdocument(ingeschrevenpersoon.getBuitenlandsreisdocument());
                }
                if (ingeschrevenpersoon.getBurgerlijkestaat() != null) {
                    existingIngeschrevenpersoon.setBurgerlijkestaat(ingeschrevenpersoon.getBurgerlijkestaat());
                }
                if (ingeschrevenpersoon.getDatumbegingeldigheidverblijfplaats() != null) {
                    existingIngeschrevenpersoon.setDatumbegingeldigheidverblijfplaats(
                        ingeschrevenpersoon.getDatumbegingeldigheidverblijfplaats()
                    );
                }
                if (ingeschrevenpersoon.getDatumeindegeldigheidverblijfsplaats() != null) {
                    existingIngeschrevenpersoon.setDatumeindegeldigheidverblijfsplaats(
                        ingeschrevenpersoon.getDatumeindegeldigheidverblijfsplaats()
                    );
                }
                if (ingeschrevenpersoon.getDatuminschrijvinggemeente() != null) {
                    existingIngeschrevenpersoon.setDatuminschrijvinggemeente(ingeschrevenpersoon.getDatuminschrijvinggemeente());
                }
                if (ingeschrevenpersoon.getDatumopschortingbijhouding() != null) {
                    existingIngeschrevenpersoon.setDatumopschortingbijhouding(ingeschrevenpersoon.getDatumopschortingbijhouding());
                }
                if (ingeschrevenpersoon.getDatumvertrekuitnederland() != null) {
                    existingIngeschrevenpersoon.setDatumvertrekuitnederland(ingeschrevenpersoon.getDatumvertrekuitnederland());
                }
                if (ingeschrevenpersoon.getDatumvestigingnederland() != null) {
                    existingIngeschrevenpersoon.setDatumvestigingnederland(ingeschrevenpersoon.getDatumvestigingnederland());
                }
                if (ingeschrevenpersoon.getGemeentevaninschrijving() != null) {
                    existingIngeschrevenpersoon.setGemeentevaninschrijving(ingeschrevenpersoon.getGemeentevaninschrijving());
                }
                if (ingeschrevenpersoon.getGezinsrelatie() != null) {
                    existingIngeschrevenpersoon.setGezinsrelatie(ingeschrevenpersoon.getGezinsrelatie());
                }
                if (ingeschrevenpersoon.getIndicatiegeheim() != null) {
                    existingIngeschrevenpersoon.setIndicatiegeheim(ingeschrevenpersoon.getIndicatiegeheim());
                }
                if (ingeschrevenpersoon.getIngezetene() != null) {
                    existingIngeschrevenpersoon.setIngezetene(ingeschrevenpersoon.getIngezetene());
                }
                if (ingeschrevenpersoon.getLandwaarnaarvertrokken() != null) {
                    existingIngeschrevenpersoon.setLandwaarnaarvertrokken(ingeschrevenpersoon.getLandwaarnaarvertrokken());
                }
                if (ingeschrevenpersoon.getLandwaarvandaaningeschreven() != null) {
                    existingIngeschrevenpersoon.setLandwaarvandaaningeschreven(ingeschrevenpersoon.getLandwaarvandaaningeschreven());
                }
                if (ingeschrevenpersoon.getOuder1() != null) {
                    existingIngeschrevenpersoon.setOuder1(ingeschrevenpersoon.getOuder1());
                }
                if (ingeschrevenpersoon.getOuder2() != null) {
                    existingIngeschrevenpersoon.setOuder2(ingeschrevenpersoon.getOuder2());
                }
                if (ingeschrevenpersoon.getPartnerid() != null) {
                    existingIngeschrevenpersoon.setPartnerid(ingeschrevenpersoon.getPartnerid());
                }
                if (ingeschrevenpersoon.getRedeneindebewoning() != null) {
                    existingIngeschrevenpersoon.setRedeneindebewoning(ingeschrevenpersoon.getRedeneindebewoning());
                }
                if (ingeschrevenpersoon.getRedenopschortingbijhouding() != null) {
                    existingIngeschrevenpersoon.setRedenopschortingbijhouding(ingeschrevenpersoon.getRedenopschortingbijhouding());
                }
                if (ingeschrevenpersoon.getSignaleringreisdocument() != null) {
                    existingIngeschrevenpersoon.setSignaleringreisdocument(ingeschrevenpersoon.getSignaleringreisdocument());
                }
                if (ingeschrevenpersoon.getVerblijfstitel() != null) {
                    existingIngeschrevenpersoon.setVerblijfstitel(ingeschrevenpersoon.getVerblijfstitel());
                }

                return existingIngeschrevenpersoon;
            })
            .map(ingeschrevenpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingeschrevenpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /ingeschrevenpersoons} : get all the ingeschrevenpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingeschrevenpersoons in body.
     */
    @GetMapping("")
    public List<Ingeschrevenpersoon> getAllIngeschrevenpersoons() {
        log.debug("REST request to get all Ingeschrevenpersoons");
        return ingeschrevenpersoonRepository.findAll();
    }

    /**
     * {@code GET  /ingeschrevenpersoons/:id} : get the "id" ingeschrevenpersoon.
     *
     * @param id the id of the ingeschrevenpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingeschrevenpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ingeschrevenpersoon> getIngeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Ingeschrevenpersoon : {}", id);
        Optional<Ingeschrevenpersoon> ingeschrevenpersoon = ingeschrevenpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ingeschrevenpersoon);
    }

    /**
     * {@code DELETE  /ingeschrevenpersoons/:id} : delete the "id" ingeschrevenpersoon.
     *
     * @param id the id of the ingeschrevenpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ingeschrevenpersoon : {}", id);
        ingeschrevenpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
