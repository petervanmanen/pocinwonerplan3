package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Artefact;
import nl.ritense.demo.repository.ArtefactRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Artefact}.
 */
@RestController
@RequestMapping("/api/artefacts")
@Transactional
public class ArtefactResource {

    private final Logger log = LoggerFactory.getLogger(ArtefactResource.class);

    private static final String ENTITY_NAME = "artefact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtefactRepository artefactRepository;

    public ArtefactResource(ArtefactRepository artefactRepository) {
        this.artefactRepository = artefactRepository;
    }

    /**
     * {@code POST  /artefacts} : Create a new artefact.
     *
     * @param artefact the artefact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artefact, or with status {@code 400 (Bad Request)} if the artefact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Artefact> createArtefact(@Valid @RequestBody Artefact artefact) throws URISyntaxException {
        log.debug("REST request to save Artefact : {}", artefact);
        if (artefact.getId() != null) {
            throw new BadRequestAlertException("A new artefact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        artefact = artefactRepository.save(artefact);
        return ResponseEntity.created(new URI("/api/artefacts/" + artefact.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, artefact.getId().toString()))
            .body(artefact);
    }

    /**
     * {@code PUT  /artefacts/:id} : Updates an existing artefact.
     *
     * @param id the id of the artefact to save.
     * @param artefact the artefact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artefact,
     * or with status {@code 400 (Bad Request)} if the artefact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artefact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Artefact> updateArtefact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Artefact artefact
    ) throws URISyntaxException {
        log.debug("REST request to update Artefact : {}, {}", id, artefact);
        if (artefact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artefact.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artefactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        artefact = artefactRepository.save(artefact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artefact.getId().toString()))
            .body(artefact);
    }

    /**
     * {@code PATCH  /artefacts/:id} : Partial updates given fields of an existing artefact, field will ignore if it is null
     *
     * @param id the id of the artefact to save.
     * @param artefact the artefact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artefact,
     * or with status {@code 400 (Bad Request)} if the artefact is not valid,
     * or with status {@code 404 (Not Found)} if the artefact is not found,
     * or with status {@code 500 (Internal Server Error)} if the artefact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Artefact> partialUpdateArtefact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Artefact artefact
    ) throws URISyntaxException {
        log.debug("REST request to partial update Artefact partially : {}, {}", id, artefact);
        if (artefact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artefact.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artefactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Artefact> result = artefactRepository
            .findById(artefact.getId())
            .map(existingArtefact -> {
                if (artefact.getArtefectnummer() != null) {
                    existingArtefact.setArtefectnummer(artefact.getArtefectnummer());
                }
                if (artefact.getBeschrijving() != null) {
                    existingArtefact.setBeschrijving(artefact.getBeschrijving());
                }
                if (artefact.getConserveren() != null) {
                    existingArtefact.setConserveren(artefact.getConserveren());
                }
                if (artefact.getDatering() != null) {
                    existingArtefact.setDatering(artefact.getDatering());
                }
                if (artefact.getDateringcomplex() != null) {
                    existingArtefact.setDateringcomplex(artefact.getDateringcomplex());
                }
                if (artefact.getDeterminatieniveau() != null) {
                    existingArtefact.setDeterminatieniveau(artefact.getDeterminatieniveau());
                }
                if (artefact.getDianummer() != null) {
                    existingArtefact.setDianummer(artefact.getDianummer());
                }
                if (artefact.getDoosnummer() != null) {
                    existingArtefact.setDoosnummer(artefact.getDoosnummer());
                }
                if (artefact.getExposabel() != null) {
                    existingArtefact.setExposabel(artefact.getExposabel());
                }
                if (artefact.getFotonummer() != null) {
                    existingArtefact.setFotonummer(artefact.getFotonummer());
                }
                if (artefact.getFunctie() != null) {
                    existingArtefact.setFunctie(artefact.getFunctie());
                }
                if (artefact.getHerkomst() != null) {
                    existingArtefact.setHerkomst(artefact.getHerkomst());
                }
                if (artefact.getKey() != null) {
                    existingArtefact.setKey(artefact.getKey());
                }
                if (artefact.getKeydoos() != null) {
                    existingArtefact.setKeydoos(artefact.getKeydoos());
                }
                if (artefact.getKeymagazijnplaatsing() != null) {
                    existingArtefact.setKeymagazijnplaatsing(artefact.getKeymagazijnplaatsing());
                }
                if (artefact.getKeyput() != null) {
                    existingArtefact.setKeyput(artefact.getKeyput());
                }
                if (artefact.getKeyvondst() != null) {
                    existingArtefact.setKeyvondst(artefact.getKeyvondst());
                }
                if (artefact.getLiteratuur() != null) {
                    existingArtefact.setLiteratuur(artefact.getLiteratuur());
                }
                if (artefact.getMaten() != null) {
                    existingArtefact.setMaten(artefact.getMaten());
                }
                if (artefact.getNaam() != null) {
                    existingArtefact.setNaam(artefact.getNaam());
                }
                if (artefact.getOpmerkingen() != null) {
                    existingArtefact.setOpmerkingen(artefact.getOpmerkingen());
                }
                if (artefact.getOrigine() != null) {
                    existingArtefact.setOrigine(artefact.getOrigine());
                }
                if (artefact.getProjectcd() != null) {
                    existingArtefact.setProjectcd(artefact.getProjectcd());
                }
                if (artefact.getPutnummer() != null) {
                    existingArtefact.setPutnummer(artefact.getPutnummer());
                }
                if (artefact.getRestauratiewenselijk() != null) {
                    existingArtefact.setRestauratiewenselijk(artefact.getRestauratiewenselijk());
                }
                if (artefact.getTekeningnummer() != null) {
                    existingArtefact.setTekeningnummer(artefact.getTekeningnummer());
                }
                if (artefact.getType() != null) {
                    existingArtefact.setType(artefact.getType());
                }
                if (artefact.getVondstnummer() != null) {
                    existingArtefact.setVondstnummer(artefact.getVondstnummer());
                }

                return existingArtefact;
            })
            .map(artefactRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artefact.getId().toString())
        );
    }

    /**
     * {@code GET  /artefacts} : get all the artefacts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artefacts in body.
     */
    @GetMapping("")
    public List<Artefact> getAllArtefacts() {
        log.debug("REST request to get all Artefacts");
        return artefactRepository.findAll();
    }

    /**
     * {@code GET  /artefacts/:id} : get the "id" artefact.
     *
     * @param id the id of the artefact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artefact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Artefact> getArtefact(@PathVariable("id") Long id) {
        log.debug("REST request to get Artefact : {}", id);
        Optional<Artefact> artefact = artefactRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(artefact);
    }

    /**
     * {@code DELETE  /artefacts/:id} : delete the "id" artefact.
     *
     * @param id the id of the artefact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtefact(@PathVariable("id") Long id) {
        log.debug("REST request to delete Artefact : {}", id);
        artefactRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
