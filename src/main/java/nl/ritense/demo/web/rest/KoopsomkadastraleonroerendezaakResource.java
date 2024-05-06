package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Koopsomkadastraleonroerendezaak;
import nl.ritense.demo.repository.KoopsomkadastraleonroerendezaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Koopsomkadastraleonroerendezaak}.
 */
@RestController
@RequestMapping("/api/koopsomkadastraleonroerendezaaks")
@Transactional
public class KoopsomkadastraleonroerendezaakResource {

    private final Logger log = LoggerFactory.getLogger(KoopsomkadastraleonroerendezaakResource.class);

    private static final String ENTITY_NAME = "koopsomkadastraleonroerendezaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KoopsomkadastraleonroerendezaakRepository koopsomkadastraleonroerendezaakRepository;

    public KoopsomkadastraleonroerendezaakResource(KoopsomkadastraleonroerendezaakRepository koopsomkadastraleonroerendezaakRepository) {
        this.koopsomkadastraleonroerendezaakRepository = koopsomkadastraleonroerendezaakRepository;
    }

    /**
     * {@code POST  /koopsomkadastraleonroerendezaaks} : Create a new koopsomkadastraleonroerendezaak.
     *
     * @param koopsomkadastraleonroerendezaak the koopsomkadastraleonroerendezaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new koopsomkadastraleonroerendezaak, or with status {@code 400 (Bad Request)} if the koopsomkadastraleonroerendezaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Koopsomkadastraleonroerendezaak> createKoopsomkadastraleonroerendezaak(
        @RequestBody Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to save Koopsomkadastraleonroerendezaak : {}", koopsomkadastraleonroerendezaak);
        if (koopsomkadastraleonroerendezaak.getId() != null) {
            throw new BadRequestAlertException("A new koopsomkadastraleonroerendezaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        koopsomkadastraleonroerendezaak = koopsomkadastraleonroerendezaakRepository.save(koopsomkadastraleonroerendezaak);
        return ResponseEntity.created(new URI("/api/koopsomkadastraleonroerendezaaks/" + koopsomkadastraleonroerendezaak.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    koopsomkadastraleonroerendezaak.getId().toString()
                )
            )
            .body(koopsomkadastraleonroerendezaak);
    }

    /**
     * {@code PUT  /koopsomkadastraleonroerendezaaks/:id} : Updates an existing koopsomkadastraleonroerendezaak.
     *
     * @param id the id of the koopsomkadastraleonroerendezaak to save.
     * @param koopsomkadastraleonroerendezaak the koopsomkadastraleonroerendezaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koopsomkadastraleonroerendezaak,
     * or with status {@code 400 (Bad Request)} if the koopsomkadastraleonroerendezaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the koopsomkadastraleonroerendezaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Koopsomkadastraleonroerendezaak> updateKoopsomkadastraleonroerendezaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to update Koopsomkadastraleonroerendezaak : {}, {}", id, koopsomkadastraleonroerendezaak);
        if (koopsomkadastraleonroerendezaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koopsomkadastraleonroerendezaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koopsomkadastraleonroerendezaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        koopsomkadastraleonroerendezaak = koopsomkadastraleonroerendezaakRepository.save(koopsomkadastraleonroerendezaak);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, koopsomkadastraleonroerendezaak.getId().toString())
            )
            .body(koopsomkadastraleonroerendezaak);
    }

    /**
     * {@code PATCH  /koopsomkadastraleonroerendezaaks/:id} : Partial updates given fields of an existing koopsomkadastraleonroerendezaak, field will ignore if it is null
     *
     * @param id the id of the koopsomkadastraleonroerendezaak to save.
     * @param koopsomkadastraleonroerendezaak the koopsomkadastraleonroerendezaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koopsomkadastraleonroerendezaak,
     * or with status {@code 400 (Bad Request)} if the koopsomkadastraleonroerendezaak is not valid,
     * or with status {@code 404 (Not Found)} if the koopsomkadastraleonroerendezaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the koopsomkadastraleonroerendezaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Koopsomkadastraleonroerendezaak> partialUpdateKoopsomkadastraleonroerendezaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Koopsomkadastraleonroerendezaak partially : {}, {}", id, koopsomkadastraleonroerendezaak);
        if (koopsomkadastraleonroerendezaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koopsomkadastraleonroerendezaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koopsomkadastraleonroerendezaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Koopsomkadastraleonroerendezaak> result = koopsomkadastraleonroerendezaakRepository
            .findById(koopsomkadastraleonroerendezaak.getId())
            .map(existingKoopsomkadastraleonroerendezaak -> {
                if (koopsomkadastraleonroerendezaak.getDatumtransactie() != null) {
                    existingKoopsomkadastraleonroerendezaak.setDatumtransactie(koopsomkadastraleonroerendezaak.getDatumtransactie());
                }
                if (koopsomkadastraleonroerendezaak.getKoopsom() != null) {
                    existingKoopsomkadastraleonroerendezaak.setKoopsom(koopsomkadastraleonroerendezaak.getKoopsom());
                }

                return existingKoopsomkadastraleonroerendezaak;
            })
            .map(koopsomkadastraleonroerendezaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, koopsomkadastraleonroerendezaak.getId().toString())
        );
    }

    /**
     * {@code GET  /koopsomkadastraleonroerendezaaks} : get all the koopsomkadastraleonroerendezaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of koopsomkadastraleonroerendezaaks in body.
     */
    @GetMapping("")
    public List<Koopsomkadastraleonroerendezaak> getAllKoopsomkadastraleonroerendezaaks() {
        log.debug("REST request to get all Koopsomkadastraleonroerendezaaks");
        return koopsomkadastraleonroerendezaakRepository.findAll();
    }

    /**
     * {@code GET  /koopsomkadastraleonroerendezaaks/:id} : get the "id" koopsomkadastraleonroerendezaak.
     *
     * @param id the id of the koopsomkadastraleonroerendezaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the koopsomkadastraleonroerendezaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Koopsomkadastraleonroerendezaak> getKoopsomkadastraleonroerendezaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Koopsomkadastraleonroerendezaak : {}", id);
        Optional<Koopsomkadastraleonroerendezaak> koopsomkadastraleonroerendezaak = koopsomkadastraleonroerendezaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(koopsomkadastraleonroerendezaak);
    }

    /**
     * {@code DELETE  /koopsomkadastraleonroerendezaaks/:id} : delete the "id" koopsomkadastraleonroerendezaak.
     *
     * @param id the id of the koopsomkadastraleonroerendezaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKoopsomkadastraleonroerendezaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Koopsomkadastraleonroerendezaak : {}", id);
        koopsomkadastraleonroerendezaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
