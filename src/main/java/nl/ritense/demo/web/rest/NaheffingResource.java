package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Naheffing;
import nl.ritense.demo.repository.NaheffingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Naheffing}.
 */
@RestController
@RequestMapping("/api/naheffings")
@Transactional
public class NaheffingResource {

    private final Logger log = LoggerFactory.getLogger(NaheffingResource.class);

    private static final String ENTITY_NAME = "naheffing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaheffingRepository naheffingRepository;

    public NaheffingResource(NaheffingRepository naheffingRepository) {
        this.naheffingRepository = naheffingRepository;
    }

    /**
     * {@code POST  /naheffings} : Create a new naheffing.
     *
     * @param naheffing the naheffing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naheffing, or with status {@code 400 (Bad Request)} if the naheffing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Naheffing> createNaheffing(@RequestBody Naheffing naheffing) throws URISyntaxException {
        log.debug("REST request to save Naheffing : {}", naheffing);
        if (naheffing.getId() != null) {
            throw new BadRequestAlertException("A new naheffing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        naheffing = naheffingRepository.save(naheffing);
        return ResponseEntity.created(new URI("/api/naheffings/" + naheffing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, naheffing.getId().toString()))
            .body(naheffing);
    }

    /**
     * {@code PUT  /naheffings/:id} : Updates an existing naheffing.
     *
     * @param id the id of the naheffing to save.
     * @param naheffing the naheffing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naheffing,
     * or with status {@code 400 (Bad Request)} if the naheffing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naheffing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Naheffing> updateNaheffing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Naheffing naheffing
    ) throws URISyntaxException {
        log.debug("REST request to update Naheffing : {}, {}", id, naheffing);
        if (naheffing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naheffing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naheffingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        naheffing = naheffingRepository.save(naheffing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naheffing.getId().toString()))
            .body(naheffing);
    }

    /**
     * {@code PATCH  /naheffings/:id} : Partial updates given fields of an existing naheffing, field will ignore if it is null
     *
     * @param id the id of the naheffing to save.
     * @param naheffing the naheffing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naheffing,
     * or with status {@code 400 (Bad Request)} if the naheffing is not valid,
     * or with status {@code 404 (Not Found)} if the naheffing is not found,
     * or with status {@code 500 (Internal Server Error)} if the naheffing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Naheffing> partialUpdateNaheffing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Naheffing naheffing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Naheffing partially : {}, {}", id, naheffing);
        if (naheffing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naheffing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naheffingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Naheffing> result = naheffingRepository
            .findById(naheffing.getId())
            .map(existingNaheffing -> {
                if (naheffing.getBedrag() != null) {
                    existingNaheffing.setBedrag(naheffing.getBedrag());
                }
                if (naheffing.getBezwaarafgehandeld() != null) {
                    existingNaheffing.setBezwaarafgehandeld(naheffing.getBezwaarafgehandeld());
                }
                if (naheffing.getBezwaaringetrokken() != null) {
                    existingNaheffing.setBezwaaringetrokken(naheffing.getBezwaaringetrokken());
                }
                if (naheffing.getBezwaartoegewezen() != null) {
                    existingNaheffing.setBezwaartoegewezen(naheffing.getBezwaartoegewezen());
                }
                if (naheffing.getBonnummer() != null) {
                    existingNaheffing.setBonnummer(naheffing.getBonnummer());
                }
                if (naheffing.getDatumbetaling() != null) {
                    existingNaheffing.setDatumbetaling(naheffing.getDatumbetaling());
                }
                if (naheffing.getDatumbezwaar() != null) {
                    existingNaheffing.setDatumbezwaar(naheffing.getDatumbezwaar());
                }
                if (naheffing.getDatumgeseponeerd() != null) {
                    existingNaheffing.setDatumgeseponeerd(naheffing.getDatumgeseponeerd());
                }
                if (naheffing.getDatumindiening() != null) {
                    existingNaheffing.setDatumindiening(naheffing.getDatumindiening());
                }
                if (naheffing.getDienstcd() != null) {
                    existingNaheffing.setDienstcd(naheffing.getDienstcd());
                }
                if (naheffing.getFiscaal() != null) {
                    existingNaheffing.setFiscaal(naheffing.getFiscaal());
                }
                if (naheffing.getOrganisatie() != null) {
                    existingNaheffing.setOrganisatie(naheffing.getOrganisatie());
                }
                if (naheffing.getOvertreding() != null) {
                    existingNaheffing.setOvertreding(naheffing.getOvertreding());
                }
                if (naheffing.getParkeertarief() != null) {
                    existingNaheffing.setParkeertarief(naheffing.getParkeertarief());
                }
                if (naheffing.getRedenseponeren() != null) {
                    existingNaheffing.setRedenseponeren(naheffing.getRedenseponeren());
                }
                if (naheffing.getVorderingnummer() != null) {
                    existingNaheffing.setVorderingnummer(naheffing.getVorderingnummer());
                }

                return existingNaheffing;
            })
            .map(naheffingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naheffing.getId().toString())
        );
    }

    /**
     * {@code GET  /naheffings} : get all the naheffings.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naheffings in body.
     */
    @GetMapping("")
    public List<Naheffing> getAllNaheffings(@RequestParam(name = "filter", required = false) String filter) {
        if ("komtvoortuitparkeerscan-is-null".equals(filter)) {
            log.debug("REST request to get all Naheffings where komtvoortuitParkeerscan is null");
            return StreamSupport.stream(naheffingRepository.findAll().spliterator(), false)
                .filter(naheffing -> naheffing.getKomtvoortuitParkeerscan() == null)
                .toList();
        }
        log.debug("REST request to get all Naheffings");
        return naheffingRepository.findAll();
    }

    /**
     * {@code GET  /naheffings/:id} : get the "id" naheffing.
     *
     * @param id the id of the naheffing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naheffing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Naheffing> getNaheffing(@PathVariable("id") Long id) {
        log.debug("REST request to get Naheffing : {}", id);
        Optional<Naheffing> naheffing = naheffingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(naheffing);
    }

    /**
     * {@code DELETE  /naheffings/:id} : delete the "id" naheffing.
     *
     * @param id the id of the naheffing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNaheffing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Naheffing : {}", id);
        naheffingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
