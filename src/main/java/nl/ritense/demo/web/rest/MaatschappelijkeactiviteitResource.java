package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Maatschappelijkeactiviteit;
import nl.ritense.demo.repository.MaatschappelijkeactiviteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Maatschappelijkeactiviteit}.
 */
@RestController
@RequestMapping("/api/maatschappelijkeactiviteits")
@Transactional
public class MaatschappelijkeactiviteitResource {

    private final Logger log = LoggerFactory.getLogger(MaatschappelijkeactiviteitResource.class);

    private static final String ENTITY_NAME = "maatschappelijkeactiviteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaatschappelijkeactiviteitRepository maatschappelijkeactiviteitRepository;

    public MaatschappelijkeactiviteitResource(MaatschappelijkeactiviteitRepository maatschappelijkeactiviteitRepository) {
        this.maatschappelijkeactiviteitRepository = maatschappelijkeactiviteitRepository;
    }

    /**
     * {@code POST  /maatschappelijkeactiviteits} : Create a new maatschappelijkeactiviteit.
     *
     * @param maatschappelijkeactiviteit the maatschappelijkeactiviteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maatschappelijkeactiviteit, or with status {@code 400 (Bad Request)} if the maatschappelijkeactiviteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Maatschappelijkeactiviteit> createMaatschappelijkeactiviteit(
        @Valid @RequestBody Maatschappelijkeactiviteit maatschappelijkeactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to save Maatschappelijkeactiviteit : {}", maatschappelijkeactiviteit);
        if (maatschappelijkeactiviteit.getId() != null) {
            throw new BadRequestAlertException("A new maatschappelijkeactiviteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        maatschappelijkeactiviteit = maatschappelijkeactiviteitRepository.save(maatschappelijkeactiviteit);
        return ResponseEntity.created(new URI("/api/maatschappelijkeactiviteits/" + maatschappelijkeactiviteit.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, maatschappelijkeactiviteit.getId().toString())
            )
            .body(maatschappelijkeactiviteit);
    }

    /**
     * {@code PUT  /maatschappelijkeactiviteits/:id} : Updates an existing maatschappelijkeactiviteit.
     *
     * @param id the id of the maatschappelijkeactiviteit to save.
     * @param maatschappelijkeactiviteit the maatschappelijkeactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maatschappelijkeactiviteit,
     * or with status {@code 400 (Bad Request)} if the maatschappelijkeactiviteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the maatschappelijkeactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Maatschappelijkeactiviteit> updateMaatschappelijkeactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Maatschappelijkeactiviteit maatschappelijkeactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to update Maatschappelijkeactiviteit : {}, {}", id, maatschappelijkeactiviteit);
        if (maatschappelijkeactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maatschappelijkeactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maatschappelijkeactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        maatschappelijkeactiviteit = maatschappelijkeactiviteitRepository.save(maatschappelijkeactiviteit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, maatschappelijkeactiviteit.getId().toString()))
            .body(maatschappelijkeactiviteit);
    }

    /**
     * {@code PATCH  /maatschappelijkeactiviteits/:id} : Partial updates given fields of an existing maatschappelijkeactiviteit, field will ignore if it is null
     *
     * @param id the id of the maatschappelijkeactiviteit to save.
     * @param maatschappelijkeactiviteit the maatschappelijkeactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maatschappelijkeactiviteit,
     * or with status {@code 400 (Bad Request)} if the maatschappelijkeactiviteit is not valid,
     * or with status {@code 404 (Not Found)} if the maatschappelijkeactiviteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the maatschappelijkeactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Maatschappelijkeactiviteit> partialUpdateMaatschappelijkeactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Maatschappelijkeactiviteit maatschappelijkeactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Maatschappelijkeactiviteit partially : {}, {}", id, maatschappelijkeactiviteit);
        if (maatschappelijkeactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maatschappelijkeactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maatschappelijkeactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Maatschappelijkeactiviteit> result = maatschappelijkeactiviteitRepository
            .findById(maatschappelijkeactiviteit.getId())
            .map(existingMaatschappelijkeactiviteit -> {
                if (maatschappelijkeactiviteit.getAdresbinnenland() != null) {
                    existingMaatschappelijkeactiviteit.setAdresbinnenland(maatschappelijkeactiviteit.getAdresbinnenland());
                }
                if (maatschappelijkeactiviteit.getAdrescorrespondentie() != null) {
                    existingMaatschappelijkeactiviteit.setAdrescorrespondentie(maatschappelijkeactiviteit.getAdrescorrespondentie());
                }
                if (maatschappelijkeactiviteit.getDatumaanvang() != null) {
                    existingMaatschappelijkeactiviteit.setDatumaanvang(maatschappelijkeactiviteit.getDatumaanvang());
                }
                if (maatschappelijkeactiviteit.getDatumeindegeldig() != null) {
                    existingMaatschappelijkeactiviteit.setDatumeindegeldig(maatschappelijkeactiviteit.getDatumeindegeldig());
                }
                if (maatschappelijkeactiviteit.getDatumfaillisement() != null) {
                    existingMaatschappelijkeactiviteit.setDatumfaillisement(maatschappelijkeactiviteit.getDatumfaillisement());
                }
                if (maatschappelijkeactiviteit.getIndicatieeconomischactief() != null) {
                    existingMaatschappelijkeactiviteit.setIndicatieeconomischactief(
                        maatschappelijkeactiviteit.getIndicatieeconomischactief()
                    );
                }
                if (maatschappelijkeactiviteit.getKvknummer() != null) {
                    existingMaatschappelijkeactiviteit.setKvknummer(maatschappelijkeactiviteit.getKvknummer());
                }
                if (maatschappelijkeactiviteit.getRechtsvorm() != null) {
                    existingMaatschappelijkeactiviteit.setRechtsvorm(maatschappelijkeactiviteit.getRechtsvorm());
                }
                if (maatschappelijkeactiviteit.getRsin() != null) {
                    existingMaatschappelijkeactiviteit.setRsin(maatschappelijkeactiviteit.getRsin());
                }
                if (maatschappelijkeactiviteit.getStatutairenaam() != null) {
                    existingMaatschappelijkeactiviteit.setStatutairenaam(maatschappelijkeactiviteit.getStatutairenaam());
                }
                if (maatschappelijkeactiviteit.getTelefoonnummer() != null) {
                    existingMaatschappelijkeactiviteit.setTelefoonnummer(maatschappelijkeactiviteit.getTelefoonnummer());
                }
                if (maatschappelijkeactiviteit.getUrl() != null) {
                    existingMaatschappelijkeactiviteit.setUrl(maatschappelijkeactiviteit.getUrl());
                }

                return existingMaatschappelijkeactiviteit;
            })
            .map(maatschappelijkeactiviteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, maatschappelijkeactiviteit.getId().toString())
        );
    }

    /**
     * {@code GET  /maatschappelijkeactiviteits} : get all the maatschappelijkeactiviteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maatschappelijkeactiviteits in body.
     */
    @GetMapping("")
    public List<Maatschappelijkeactiviteit> getAllMaatschappelijkeactiviteits() {
        log.debug("REST request to get all Maatschappelijkeactiviteits");
        return maatschappelijkeactiviteitRepository.findAll();
    }

    /**
     * {@code GET  /maatschappelijkeactiviteits/:id} : get the "id" maatschappelijkeactiviteit.
     *
     * @param id the id of the maatschappelijkeactiviteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maatschappelijkeactiviteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Maatschappelijkeactiviteit> getMaatschappelijkeactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Maatschappelijkeactiviteit : {}", id);
        Optional<Maatschappelijkeactiviteit> maatschappelijkeactiviteit = maatschappelijkeactiviteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(maatschappelijkeactiviteit);
    }

    /**
     * {@code DELETE  /maatschappelijkeactiviteits/:id} : delete the "id" maatschappelijkeactiviteit.
     *
     * @param id the id of the maatschappelijkeactiviteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaatschappelijkeactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Maatschappelijkeactiviteit : {}", id);
        maatschappelijkeactiviteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
