package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Nietnatuurlijkpersoon;
import nl.ritense.demo.repository.NietnatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Nietnatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/nietnatuurlijkpersoons")
@Transactional
public class NietnatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(NietnatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "nietnatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NietnatuurlijkpersoonRepository nietnatuurlijkpersoonRepository;

    public NietnatuurlijkpersoonResource(NietnatuurlijkpersoonRepository nietnatuurlijkpersoonRepository) {
        this.nietnatuurlijkpersoonRepository = nietnatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /nietnatuurlijkpersoons} : Create a new nietnatuurlijkpersoon.
     *
     * @param nietnatuurlijkpersoon the nietnatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nietnatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the nietnatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nietnatuurlijkpersoon> createNietnatuurlijkpersoon(
        @Valid @RequestBody Nietnatuurlijkpersoon nietnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Nietnatuurlijkpersoon : {}", nietnatuurlijkpersoon);
        if (nietnatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException("A new nietnatuurlijkpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nietnatuurlijkpersoon = nietnatuurlijkpersoonRepository.save(nietnatuurlijkpersoon);
        return ResponseEntity.created(new URI("/api/nietnatuurlijkpersoons/" + nietnatuurlijkpersoon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, nietnatuurlijkpersoon.getId().toString()))
            .body(nietnatuurlijkpersoon);
    }

    /**
     * {@code PUT  /nietnatuurlijkpersoons/:id} : Updates an existing nietnatuurlijkpersoon.
     *
     * @param id the id of the nietnatuurlijkpersoon to save.
     * @param nietnatuurlijkpersoon the nietnatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nietnatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the nietnatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nietnatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nietnatuurlijkpersoon> updateNietnatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Nietnatuurlijkpersoon nietnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Nietnatuurlijkpersoon : {}, {}", id, nietnatuurlijkpersoon);
        if (nietnatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nietnatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nietnatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nietnatuurlijkpersoon = nietnatuurlijkpersoonRepository.save(nietnatuurlijkpersoon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nietnatuurlijkpersoon.getId().toString()))
            .body(nietnatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /nietnatuurlijkpersoons/:id} : Partial updates given fields of an existing nietnatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the nietnatuurlijkpersoon to save.
     * @param nietnatuurlijkpersoon the nietnatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nietnatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the nietnatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the nietnatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the nietnatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nietnatuurlijkpersoon> partialUpdateNietnatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Nietnatuurlijkpersoon nietnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nietnatuurlijkpersoon partially : {}, {}", id, nietnatuurlijkpersoon);
        if (nietnatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nietnatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nietnatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nietnatuurlijkpersoon> result = nietnatuurlijkpersoonRepository
            .findById(nietnatuurlijkpersoon.getId())
            .map(existingNietnatuurlijkpersoon -> {
                if (nietnatuurlijkpersoon.getDatumaanvang() != null) {
                    existingNietnatuurlijkpersoon.setDatumaanvang(nietnatuurlijkpersoon.getDatumaanvang());
                }
                if (nietnatuurlijkpersoon.getDatumeinde() != null) {
                    existingNietnatuurlijkpersoon.setDatumeinde(nietnatuurlijkpersoon.getDatumeinde());
                }
                if (nietnatuurlijkpersoon.getDatumuitschrijving() != null) {
                    existingNietnatuurlijkpersoon.setDatumuitschrijving(nietnatuurlijkpersoon.getDatumuitschrijving());
                }
                if (nietnatuurlijkpersoon.getDatumvoortzetting() != null) {
                    existingNietnatuurlijkpersoon.setDatumvoortzetting(nietnatuurlijkpersoon.getDatumvoortzetting());
                }
                if (nietnatuurlijkpersoon.getFaxnummer() != null) {
                    existingNietnatuurlijkpersoon.setFaxnummer(nietnatuurlijkpersoon.getFaxnummer());
                }
                if (nietnatuurlijkpersoon.getIngeschreven() != null) {
                    existingNietnatuurlijkpersoon.setIngeschreven(nietnatuurlijkpersoon.getIngeschreven());
                }
                if (nietnatuurlijkpersoon.getInoprichting() != null) {
                    existingNietnatuurlijkpersoon.setInoprichting(nietnatuurlijkpersoon.getInoprichting());
                }
                if (nietnatuurlijkpersoon.getKvknummer() != null) {
                    existingNietnatuurlijkpersoon.setKvknummer(nietnatuurlijkpersoon.getKvknummer());
                }
                if (nietnatuurlijkpersoon.getNnpid() != null) {
                    existingNietnatuurlijkpersoon.setNnpid(nietnatuurlijkpersoon.getNnpid());
                }
                if (nietnatuurlijkpersoon.getRechtsvorm() != null) {
                    existingNietnatuurlijkpersoon.setRechtsvorm(nietnatuurlijkpersoon.getRechtsvorm());
                }
                if (nietnatuurlijkpersoon.getRsinnummer() != null) {
                    existingNietnatuurlijkpersoon.setRsinnummer(nietnatuurlijkpersoon.getRsinnummer());
                }
                if (nietnatuurlijkpersoon.getStatutairenaam() != null) {
                    existingNietnatuurlijkpersoon.setStatutairenaam(nietnatuurlijkpersoon.getStatutairenaam());
                }
                if (nietnatuurlijkpersoon.getStatutairezetel() != null) {
                    existingNietnatuurlijkpersoon.setStatutairezetel(nietnatuurlijkpersoon.getStatutairezetel());
                }
                if (nietnatuurlijkpersoon.getWebsiteurl() != null) {
                    existingNietnatuurlijkpersoon.setWebsiteurl(nietnatuurlijkpersoon.getWebsiteurl());
                }

                return existingNietnatuurlijkpersoon;
            })
            .map(nietnatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nietnatuurlijkpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /nietnatuurlijkpersoons} : get all the nietnatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nietnatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Nietnatuurlijkpersoon> getAllNietnatuurlijkpersoons() {
        log.debug("REST request to get all Nietnatuurlijkpersoons");
        return nietnatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /nietnatuurlijkpersoons/:id} : get the "id" nietnatuurlijkpersoon.
     *
     * @param id the id of the nietnatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nietnatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nietnatuurlijkpersoon> getNietnatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Nietnatuurlijkpersoon : {}", id);
        Optional<Nietnatuurlijkpersoon> nietnatuurlijkpersoon = nietnatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nietnatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /nietnatuurlijkpersoons/:id} : delete the "id" nietnatuurlijkpersoon.
     *
     * @param id the id of the nietnatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNietnatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nietnatuurlijkpersoon : {}", id);
        nietnatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
