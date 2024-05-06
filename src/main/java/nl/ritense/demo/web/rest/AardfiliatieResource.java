package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aardfiliatie;
import nl.ritense.demo.repository.AardfiliatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aardfiliatie}.
 */
@RestController
@RequestMapping("/api/aardfiliaties")
@Transactional
public class AardfiliatieResource {

    private final Logger log = LoggerFactory.getLogger(AardfiliatieResource.class);

    private static final String ENTITY_NAME = "aardfiliatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AardfiliatieRepository aardfiliatieRepository;

    public AardfiliatieResource(AardfiliatieRepository aardfiliatieRepository) {
        this.aardfiliatieRepository = aardfiliatieRepository;
    }

    /**
     * {@code POST  /aardfiliaties} : Create a new aardfiliatie.
     *
     * @param aardfiliatie the aardfiliatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aardfiliatie, or with status {@code 400 (Bad Request)} if the aardfiliatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aardfiliatie> createAardfiliatie(@RequestBody Aardfiliatie aardfiliatie) throws URISyntaxException {
        log.debug("REST request to save Aardfiliatie : {}", aardfiliatie);
        if (aardfiliatie.getId() != null) {
            throw new BadRequestAlertException("A new aardfiliatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aardfiliatie = aardfiliatieRepository.save(aardfiliatie);
        return ResponseEntity.created(new URI("/api/aardfiliaties/" + aardfiliatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aardfiliatie.getId().toString()))
            .body(aardfiliatie);
    }

    /**
     * {@code PUT  /aardfiliaties/:id} : Updates an existing aardfiliatie.
     *
     * @param id the id of the aardfiliatie to save.
     * @param aardfiliatie the aardfiliatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aardfiliatie,
     * or with status {@code 400 (Bad Request)} if the aardfiliatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aardfiliatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aardfiliatie> updateAardfiliatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aardfiliatie aardfiliatie
    ) throws URISyntaxException {
        log.debug("REST request to update Aardfiliatie : {}, {}", id, aardfiliatie);
        if (aardfiliatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aardfiliatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aardfiliatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aardfiliatie = aardfiliatieRepository.save(aardfiliatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aardfiliatie.getId().toString()))
            .body(aardfiliatie);
    }

    /**
     * {@code PATCH  /aardfiliaties/:id} : Partial updates given fields of an existing aardfiliatie, field will ignore if it is null
     *
     * @param id the id of the aardfiliatie to save.
     * @param aardfiliatie the aardfiliatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aardfiliatie,
     * or with status {@code 400 (Bad Request)} if the aardfiliatie is not valid,
     * or with status {@code 404 (Not Found)} if the aardfiliatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the aardfiliatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aardfiliatie> partialUpdateAardfiliatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aardfiliatie aardfiliatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aardfiliatie partially : {}, {}", id, aardfiliatie);
        if (aardfiliatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aardfiliatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aardfiliatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aardfiliatie> result = aardfiliatieRepository
            .findById(aardfiliatie.getId())
            .map(existingAardfiliatie -> {
                if (aardfiliatie.getCodeaardfiliatie() != null) {
                    existingAardfiliatie.setCodeaardfiliatie(aardfiliatie.getCodeaardfiliatie());
                }
                if (aardfiliatie.getDatumbegingeldigheidaardfiliatie() != null) {
                    existingAardfiliatie.setDatumbegingeldigheidaardfiliatie(aardfiliatie.getDatumbegingeldigheidaardfiliatie());
                }
                if (aardfiliatie.getDatumeindegeldigheidaardfiliatie() != null) {
                    existingAardfiliatie.setDatumeindegeldigheidaardfiliatie(aardfiliatie.getDatumeindegeldigheidaardfiliatie());
                }
                if (aardfiliatie.getNaamaardfiliatie() != null) {
                    existingAardfiliatie.setNaamaardfiliatie(aardfiliatie.getNaamaardfiliatie());
                }

                return existingAardfiliatie;
            })
            .map(aardfiliatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aardfiliatie.getId().toString())
        );
    }

    /**
     * {@code GET  /aardfiliaties} : get all the aardfiliaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aardfiliaties in body.
     */
    @GetMapping("")
    public List<Aardfiliatie> getAllAardfiliaties() {
        log.debug("REST request to get all Aardfiliaties");
        return aardfiliatieRepository.findAll();
    }

    /**
     * {@code GET  /aardfiliaties/:id} : get the "id" aardfiliatie.
     *
     * @param id the id of the aardfiliatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aardfiliatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aardfiliatie> getAardfiliatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Aardfiliatie : {}", id);
        Optional<Aardfiliatie> aardfiliatie = aardfiliatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aardfiliatie);
    }

    /**
     * {@code DELETE  /aardfiliaties/:id} : delete the "id" aardfiliatie.
     *
     * @param id the id of the aardfiliatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAardfiliatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aardfiliatie : {}", id);
        aardfiliatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
