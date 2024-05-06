package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Tunnelobject;
import nl.ritense.demo.repository.TunnelobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Tunnelobject}.
 */
@RestController
@RequestMapping("/api/tunnelobjects")
@Transactional
public class TunnelobjectResource {

    private final Logger log = LoggerFactory.getLogger(TunnelobjectResource.class);

    private static final String ENTITY_NAME = "tunnelobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TunnelobjectRepository tunnelobjectRepository;

    public TunnelobjectResource(TunnelobjectRepository tunnelobjectRepository) {
        this.tunnelobjectRepository = tunnelobjectRepository;
    }

    /**
     * {@code POST  /tunnelobjects} : Create a new tunnelobject.
     *
     * @param tunnelobject the tunnelobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tunnelobject, or with status {@code 400 (Bad Request)} if the tunnelobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tunnelobject> createTunnelobject(@Valid @RequestBody Tunnelobject tunnelobject) throws URISyntaxException {
        log.debug("REST request to save Tunnelobject : {}", tunnelobject);
        if (tunnelobject.getId() != null) {
            throw new BadRequestAlertException("A new tunnelobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tunnelobject = tunnelobjectRepository.save(tunnelobject);
        return ResponseEntity.created(new URI("/api/tunnelobjects/" + tunnelobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tunnelobject.getId().toString()))
            .body(tunnelobject);
    }

    /**
     * {@code PUT  /tunnelobjects/:id} : Updates an existing tunnelobject.
     *
     * @param id the id of the tunnelobject to save.
     * @param tunnelobject the tunnelobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tunnelobject,
     * or with status {@code 400 (Bad Request)} if the tunnelobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tunnelobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tunnelobject> updateTunnelobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tunnelobject tunnelobject
    ) throws URISyntaxException {
        log.debug("REST request to update Tunnelobject : {}, {}", id, tunnelobject);
        if (tunnelobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tunnelobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tunnelobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tunnelobject = tunnelobjectRepository.save(tunnelobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tunnelobject.getId().toString()))
            .body(tunnelobject);
    }

    /**
     * {@code PATCH  /tunnelobjects/:id} : Partial updates given fields of an existing tunnelobject, field will ignore if it is null
     *
     * @param id the id of the tunnelobject to save.
     * @param tunnelobject the tunnelobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tunnelobject,
     * or with status {@code 400 (Bad Request)} if the tunnelobject is not valid,
     * or with status {@code 404 (Not Found)} if the tunnelobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the tunnelobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tunnelobject> partialUpdateTunnelobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tunnelobject tunnelobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tunnelobject partially : {}, {}", id, tunnelobject);
        if (tunnelobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tunnelobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tunnelobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tunnelobject> result = tunnelobjectRepository
            .findById(tunnelobject.getId())
            .map(existingTunnelobject -> {
                if (tunnelobject.getAanleghoogte() != null) {
                    existingTunnelobject.setAanleghoogte(tunnelobject.getAanleghoogte());
                }
                if (tunnelobject.getAantaltunnelbuizen() != null) {
                    existingTunnelobject.setAantaltunnelbuizen(tunnelobject.getAantaltunnelbuizen());
                }
                if (tunnelobject.getBreedte() != null) {
                    existingTunnelobject.setBreedte(tunnelobject.getBreedte());
                }
                if (tunnelobject.getDoorrijbreedte() != null) {
                    existingTunnelobject.setDoorrijbreedte(tunnelobject.getDoorrijbreedte());
                }
                if (tunnelobject.getDoorrijhoogte() != null) {
                    existingTunnelobject.setDoorrijhoogte(tunnelobject.getDoorrijhoogte());
                }
                if (tunnelobject.getHoogte() != null) {
                    existingTunnelobject.setHoogte(tunnelobject.getHoogte());
                }
                if (tunnelobject.getJaarconserveren() != null) {
                    existingTunnelobject.setJaarconserveren(tunnelobject.getJaarconserveren());
                }
                if (tunnelobject.getJaaronderhouduitgevoerd() != null) {
                    existingTunnelobject.setJaaronderhouduitgevoerd(tunnelobject.getJaaronderhouduitgevoerd());
                }
                if (tunnelobject.getLengte() != null) {
                    existingTunnelobject.setLengte(tunnelobject.getLengte());
                }
                if (tunnelobject.getLeverancier() != null) {
                    existingTunnelobject.setLeverancier(tunnelobject.getLeverancier());
                }
                if (tunnelobject.getEobjectnaam() != null) {
                    existingTunnelobject.setEobjectnaam(tunnelobject.getEobjectnaam());
                }
                if (tunnelobject.getEobjectnummer() != null) {
                    existingTunnelobject.setEobjectnummer(tunnelobject.getEobjectnummer());
                }
                if (tunnelobject.getOppervlakte() != null) {
                    existingTunnelobject.setOppervlakte(tunnelobject.getOppervlakte());
                }
                if (tunnelobject.getTunnelobjectmateriaal() != null) {
                    existingTunnelobject.setTunnelobjectmateriaal(tunnelobject.getTunnelobjectmateriaal());
                }

                return existingTunnelobject;
            })
            .map(tunnelobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tunnelobject.getId().toString())
        );
    }

    /**
     * {@code GET  /tunnelobjects} : get all the tunnelobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tunnelobjects in body.
     */
    @GetMapping("")
    public List<Tunnelobject> getAllTunnelobjects() {
        log.debug("REST request to get all Tunnelobjects");
        return tunnelobjectRepository.findAll();
    }

    /**
     * {@code GET  /tunnelobjects/:id} : get the "id" tunnelobject.
     *
     * @param id the id of the tunnelobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tunnelobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tunnelobject> getTunnelobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Tunnelobject : {}", id);
        Optional<Tunnelobject> tunnelobject = tunnelobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tunnelobject);
    }

    /**
     * {@code DELETE  /tunnelobjects/:id} : delete the "id" tunnelobject.
     *
     * @param id the id of the tunnelobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTunnelobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tunnelobject : {}", id);
        tunnelobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
