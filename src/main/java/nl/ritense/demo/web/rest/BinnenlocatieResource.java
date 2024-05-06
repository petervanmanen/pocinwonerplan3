package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Binnenlocatie;
import nl.ritense.demo.repository.BinnenlocatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Binnenlocatie}.
 */
@RestController
@RequestMapping("/api/binnenlocaties")
@Transactional
public class BinnenlocatieResource {

    private final Logger log = LoggerFactory.getLogger(BinnenlocatieResource.class);

    private static final String ENTITY_NAME = "binnenlocatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BinnenlocatieRepository binnenlocatieRepository;

    public BinnenlocatieResource(BinnenlocatieRepository binnenlocatieRepository) {
        this.binnenlocatieRepository = binnenlocatieRepository;
    }

    /**
     * {@code POST  /binnenlocaties} : Create a new binnenlocatie.
     *
     * @param binnenlocatie the binnenlocatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new binnenlocatie, or with status {@code 400 (Bad Request)} if the binnenlocatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Binnenlocatie> createBinnenlocatie(@RequestBody Binnenlocatie binnenlocatie) throws URISyntaxException {
        log.debug("REST request to save Binnenlocatie : {}", binnenlocatie);
        if (binnenlocatie.getId() != null) {
            throw new BadRequestAlertException("A new binnenlocatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        binnenlocatie = binnenlocatieRepository.save(binnenlocatie);
        return ResponseEntity.created(new URI("/api/binnenlocaties/" + binnenlocatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, binnenlocatie.getId().toString()))
            .body(binnenlocatie);
    }

    /**
     * {@code PUT  /binnenlocaties/:id} : Updates an existing binnenlocatie.
     *
     * @param id the id of the binnenlocatie to save.
     * @param binnenlocatie the binnenlocatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated binnenlocatie,
     * or with status {@code 400 (Bad Request)} if the binnenlocatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the binnenlocatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Binnenlocatie> updateBinnenlocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Binnenlocatie binnenlocatie
    ) throws URISyntaxException {
        log.debug("REST request to update Binnenlocatie : {}, {}", id, binnenlocatie);
        if (binnenlocatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, binnenlocatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!binnenlocatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        binnenlocatie = binnenlocatieRepository.save(binnenlocatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, binnenlocatie.getId().toString()))
            .body(binnenlocatie);
    }

    /**
     * {@code PATCH  /binnenlocaties/:id} : Partial updates given fields of an existing binnenlocatie, field will ignore if it is null
     *
     * @param id the id of the binnenlocatie to save.
     * @param binnenlocatie the binnenlocatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated binnenlocatie,
     * or with status {@code 400 (Bad Request)} if the binnenlocatie is not valid,
     * or with status {@code 404 (Not Found)} if the binnenlocatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the binnenlocatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Binnenlocatie> partialUpdateBinnenlocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Binnenlocatie binnenlocatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Binnenlocatie partially : {}, {}", id, binnenlocatie);
        if (binnenlocatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, binnenlocatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!binnenlocatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Binnenlocatie> result = binnenlocatieRepository
            .findById(binnenlocatie.getId())
            .map(existingBinnenlocatie -> {
                if (binnenlocatie.getAdres() != null) {
                    existingBinnenlocatie.setAdres(binnenlocatie.getAdres());
                }
                if (binnenlocatie.getBouwjaar() != null) {
                    existingBinnenlocatie.setBouwjaar(binnenlocatie.getBouwjaar());
                }
                if (binnenlocatie.getGemeentelijk() != null) {
                    existingBinnenlocatie.setGemeentelijk(binnenlocatie.getGemeentelijk());
                }
                if (binnenlocatie.getGeschattekostenperjaar() != null) {
                    existingBinnenlocatie.setGeschattekostenperjaar(binnenlocatie.getGeschattekostenperjaar());
                }
                if (binnenlocatie.getGymzaal() != null) {
                    existingBinnenlocatie.setGymzaal(binnenlocatie.getGymzaal());
                }
                if (binnenlocatie.getKlokurenonderwijs() != null) {
                    existingBinnenlocatie.setKlokurenonderwijs(binnenlocatie.getKlokurenonderwijs());
                }
                if (binnenlocatie.getKlokurenverenigingen() != null) {
                    existingBinnenlocatie.setKlokurenverenigingen(binnenlocatie.getKlokurenverenigingen());
                }
                if (binnenlocatie.getLocatie() != null) {
                    existingBinnenlocatie.setLocatie(binnenlocatie.getLocatie());
                }
                if (binnenlocatie.getOnderhoudsniveau() != null) {
                    existingBinnenlocatie.setOnderhoudsniveau(binnenlocatie.getOnderhoudsniveau());
                }
                if (binnenlocatie.getOnderhoudsstatus() != null) {
                    existingBinnenlocatie.setOnderhoudsstatus(binnenlocatie.getOnderhoudsstatus());
                }
                if (binnenlocatie.getSporthal() != null) {
                    existingBinnenlocatie.setSporthal(binnenlocatie.getSporthal());
                }
                if (binnenlocatie.getVloeroppervlakte() != null) {
                    existingBinnenlocatie.setVloeroppervlakte(binnenlocatie.getVloeroppervlakte());
                }

                return existingBinnenlocatie;
            })
            .map(binnenlocatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, binnenlocatie.getId().toString())
        );
    }

    /**
     * {@code GET  /binnenlocaties} : get all the binnenlocaties.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of binnenlocaties in body.
     */
    @GetMapping("")
    public List<Binnenlocatie> getAllBinnenlocaties(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Binnenlocaties");
        if (eagerload) {
            return binnenlocatieRepository.findAllWithEagerRelationships();
        } else {
            return binnenlocatieRepository.findAll();
        }
    }

    /**
     * {@code GET  /binnenlocaties/:id} : get the "id" binnenlocatie.
     *
     * @param id the id of the binnenlocatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the binnenlocatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Binnenlocatie> getBinnenlocatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Binnenlocatie : {}", id);
        Optional<Binnenlocatie> binnenlocatie = binnenlocatieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(binnenlocatie);
    }

    /**
     * {@code DELETE  /binnenlocaties/:id} : delete the "id" binnenlocatie.
     *
     * @param id the id of the binnenlocatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBinnenlocatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Binnenlocatie : {}", id);
        binnenlocatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
