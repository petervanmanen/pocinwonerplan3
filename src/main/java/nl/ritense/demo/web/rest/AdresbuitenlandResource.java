package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Adresbuitenland;
import nl.ritense.demo.repository.AdresbuitenlandRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Adresbuitenland}.
 */
@RestController
@RequestMapping("/api/adresbuitenlands")
@Transactional
public class AdresbuitenlandResource {

    private final Logger log = LoggerFactory.getLogger(AdresbuitenlandResource.class);

    private static final String ENTITY_NAME = "adresbuitenland";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresbuitenlandRepository adresbuitenlandRepository;

    public AdresbuitenlandResource(AdresbuitenlandRepository adresbuitenlandRepository) {
        this.adresbuitenlandRepository = adresbuitenlandRepository;
    }

    /**
     * {@code POST  /adresbuitenlands} : Create a new adresbuitenland.
     *
     * @param adresbuitenland the adresbuitenland to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adresbuitenland, or with status {@code 400 (Bad Request)} if the adresbuitenland has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Adresbuitenland> createAdresbuitenland(@Valid @RequestBody Adresbuitenland adresbuitenland)
        throws URISyntaxException {
        log.debug("REST request to save Adresbuitenland : {}", adresbuitenland);
        if (adresbuitenland.getId() != null) {
            throw new BadRequestAlertException("A new adresbuitenland cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adresbuitenland = adresbuitenlandRepository.save(adresbuitenland);
        return ResponseEntity.created(new URI("/api/adresbuitenlands/" + adresbuitenland.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, adresbuitenland.getId().toString()))
            .body(adresbuitenland);
    }

    /**
     * {@code PUT  /adresbuitenlands/:id} : Updates an existing adresbuitenland.
     *
     * @param id the id of the adresbuitenland to save.
     * @param adresbuitenland the adresbuitenland to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresbuitenland,
     * or with status {@code 400 (Bad Request)} if the adresbuitenland is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adresbuitenland couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Adresbuitenland> updateAdresbuitenland(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Adresbuitenland adresbuitenland
    ) throws URISyntaxException {
        log.debug("REST request to update Adresbuitenland : {}, {}", id, adresbuitenland);
        if (adresbuitenland.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresbuitenland.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresbuitenlandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adresbuitenland = adresbuitenlandRepository.save(adresbuitenland);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresbuitenland.getId().toString()))
            .body(adresbuitenland);
    }

    /**
     * {@code PATCH  /adresbuitenlands/:id} : Partial updates given fields of an existing adresbuitenland, field will ignore if it is null
     *
     * @param id the id of the adresbuitenland to save.
     * @param adresbuitenland the adresbuitenland to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresbuitenland,
     * or with status {@code 400 (Bad Request)} if the adresbuitenland is not valid,
     * or with status {@code 404 (Not Found)} if the adresbuitenland is not found,
     * or with status {@code 500 (Internal Server Error)} if the adresbuitenland couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Adresbuitenland> partialUpdateAdresbuitenland(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Adresbuitenland adresbuitenland
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adresbuitenland partially : {}, {}", id, adresbuitenland);
        if (adresbuitenland.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresbuitenland.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresbuitenlandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Adresbuitenland> result = adresbuitenlandRepository
            .findById(adresbuitenland.getId())
            .map(existingAdresbuitenland -> {
                if (adresbuitenland.getAdresregelbuitenland1() != null) {
                    existingAdresbuitenland.setAdresregelbuitenland1(adresbuitenland.getAdresregelbuitenland1());
                }
                if (adresbuitenland.getAdresregelbuitenland2() != null) {
                    existingAdresbuitenland.setAdresregelbuitenland2(adresbuitenland.getAdresregelbuitenland2());
                }
                if (adresbuitenland.getAdresregelbuitenland3() != null) {
                    existingAdresbuitenland.setAdresregelbuitenland3(adresbuitenland.getAdresregelbuitenland3());
                }
                if (adresbuitenland.getDatumaanvangadresbuitenland() != null) {
                    existingAdresbuitenland.setDatumaanvangadresbuitenland(adresbuitenland.getDatumaanvangadresbuitenland());
                }
                if (adresbuitenland.getDatuminschrijvinggemeente() != null) {
                    existingAdresbuitenland.setDatuminschrijvinggemeente(adresbuitenland.getDatuminschrijvinggemeente());
                }
                if (adresbuitenland.getDatumvestigingnederland() != null) {
                    existingAdresbuitenland.setDatumvestigingnederland(adresbuitenland.getDatumvestigingnederland());
                }
                if (adresbuitenland.getGemeentevaninschrijving() != null) {
                    existingAdresbuitenland.setGemeentevaninschrijving(adresbuitenland.getGemeentevaninschrijving());
                }
                if (adresbuitenland.getLandadresbuitenland() != null) {
                    existingAdresbuitenland.setLandadresbuitenland(adresbuitenland.getLandadresbuitenland());
                }
                if (adresbuitenland.getLandwaarvandaaningeschreven() != null) {
                    existingAdresbuitenland.setLandwaarvandaaningeschreven(adresbuitenland.getLandwaarvandaaningeschreven());
                }
                if (adresbuitenland.getOmschrijvingvandeaangifteadreshouding() != null) {
                    existingAdresbuitenland.setOmschrijvingvandeaangifteadreshouding(
                        adresbuitenland.getOmschrijvingvandeaangifteadreshouding()
                    );
                }

                return existingAdresbuitenland;
            })
            .map(adresbuitenlandRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresbuitenland.getId().toString())
        );
    }

    /**
     * {@code GET  /adresbuitenlands} : get all the adresbuitenlands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresbuitenlands in body.
     */
    @GetMapping("")
    public List<Adresbuitenland> getAllAdresbuitenlands() {
        log.debug("REST request to get all Adresbuitenlands");
        return adresbuitenlandRepository.findAll();
    }

    /**
     * {@code GET  /adresbuitenlands/:id} : get the "id" adresbuitenland.
     *
     * @param id the id of the adresbuitenland to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adresbuitenland, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Adresbuitenland> getAdresbuitenland(@PathVariable("id") Long id) {
        log.debug("REST request to get Adresbuitenland : {}", id);
        Optional<Adresbuitenland> adresbuitenland = adresbuitenlandRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adresbuitenland);
    }

    /**
     * {@code DELETE  /adresbuitenlands/:id} : delete the "id" adresbuitenland.
     *
     * @param id the id of the adresbuitenland to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdresbuitenland(@PathVariable("id") Long id) {
        log.debug("REST request to delete Adresbuitenland : {}", id);
        adresbuitenlandRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
