package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verzoekomtoewijzing;
import nl.ritense.demo.repository.VerzoekomtoewijzingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verzoekomtoewijzing}.
 */
@RestController
@RequestMapping("/api/verzoekomtoewijzings")
@Transactional
public class VerzoekomtoewijzingResource {

    private final Logger log = LoggerFactory.getLogger(VerzoekomtoewijzingResource.class);

    private static final String ENTITY_NAME = "verzoekomtoewijzing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerzoekomtoewijzingRepository verzoekomtoewijzingRepository;

    public VerzoekomtoewijzingResource(VerzoekomtoewijzingRepository verzoekomtoewijzingRepository) {
        this.verzoekomtoewijzingRepository = verzoekomtoewijzingRepository;
    }

    /**
     * {@code POST  /verzoekomtoewijzings} : Create a new verzoekomtoewijzing.
     *
     * @param verzoekomtoewijzing the verzoekomtoewijzing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verzoekomtoewijzing, or with status {@code 400 (Bad Request)} if the verzoekomtoewijzing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verzoekomtoewijzing> createVerzoekomtoewijzing(@RequestBody Verzoekomtoewijzing verzoekomtoewijzing)
        throws URISyntaxException {
        log.debug("REST request to save Verzoekomtoewijzing : {}", verzoekomtoewijzing);
        if (verzoekomtoewijzing.getId() != null) {
            throw new BadRequestAlertException("A new verzoekomtoewijzing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verzoekomtoewijzing = verzoekomtoewijzingRepository.save(verzoekomtoewijzing);
        return ResponseEntity.created(new URI("/api/verzoekomtoewijzings/" + verzoekomtoewijzing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verzoekomtoewijzing.getId().toString()))
            .body(verzoekomtoewijzing);
    }

    /**
     * {@code PUT  /verzoekomtoewijzings/:id} : Updates an existing verzoekomtoewijzing.
     *
     * @param id the id of the verzoekomtoewijzing to save.
     * @param verzoekomtoewijzing the verzoekomtoewijzing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzoekomtoewijzing,
     * or with status {@code 400 (Bad Request)} if the verzoekomtoewijzing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verzoekomtoewijzing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verzoekomtoewijzing> updateVerzoekomtoewijzing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verzoekomtoewijzing verzoekomtoewijzing
    ) throws URISyntaxException {
        log.debug("REST request to update Verzoekomtoewijzing : {}, {}", id, verzoekomtoewijzing);
        if (verzoekomtoewijzing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzoekomtoewijzing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzoekomtoewijzingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verzoekomtoewijzing = verzoekomtoewijzingRepository.save(verzoekomtoewijzing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzoekomtoewijzing.getId().toString()))
            .body(verzoekomtoewijzing);
    }

    /**
     * {@code PATCH  /verzoekomtoewijzings/:id} : Partial updates given fields of an existing verzoekomtoewijzing, field will ignore if it is null
     *
     * @param id the id of the verzoekomtoewijzing to save.
     * @param verzoekomtoewijzing the verzoekomtoewijzing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzoekomtoewijzing,
     * or with status {@code 400 (Bad Request)} if the verzoekomtoewijzing is not valid,
     * or with status {@code 404 (Not Found)} if the verzoekomtoewijzing is not found,
     * or with status {@code 500 (Internal Server Error)} if the verzoekomtoewijzing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verzoekomtoewijzing> partialUpdateVerzoekomtoewijzing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verzoekomtoewijzing verzoekomtoewijzing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verzoekomtoewijzing partially : {}, {}", id, verzoekomtoewijzing);
        if (verzoekomtoewijzing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzoekomtoewijzing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzoekomtoewijzingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verzoekomtoewijzing> result = verzoekomtoewijzingRepository
            .findById(verzoekomtoewijzing.getId())
            .map(existingVerzoekomtoewijzing -> {
                if (verzoekomtoewijzing.getBeschikkingsnummer() != null) {
                    existingVerzoekomtoewijzing.setBeschikkingsnummer(verzoekomtoewijzing.getBeschikkingsnummer());
                }
                if (verzoekomtoewijzing.getCommentaar() != null) {
                    existingVerzoekomtoewijzing.setCommentaar(verzoekomtoewijzing.getCommentaar());
                }
                if (verzoekomtoewijzing.getDatumeindetoewijzing() != null) {
                    existingVerzoekomtoewijzing.setDatumeindetoewijzing(verzoekomtoewijzing.getDatumeindetoewijzing());
                }
                if (verzoekomtoewijzing.getDatumingangbeschikking() != null) {
                    existingVerzoekomtoewijzing.setDatumingangbeschikking(verzoekomtoewijzing.getDatumingangbeschikking());
                }
                if (verzoekomtoewijzing.getDatumingangtoewijzing() != null) {
                    existingVerzoekomtoewijzing.setDatumingangtoewijzing(verzoekomtoewijzing.getDatumingangtoewijzing());
                }
                if (verzoekomtoewijzing.getDatumontvangst() != null) {
                    existingVerzoekomtoewijzing.setDatumontvangst(verzoekomtoewijzing.getDatumontvangst());
                }
                if (verzoekomtoewijzing.getEenheid() != null) {
                    existingVerzoekomtoewijzing.setEenheid(verzoekomtoewijzing.getEenheid());
                }
                if (verzoekomtoewijzing.getFrequentie() != null) {
                    existingVerzoekomtoewijzing.setFrequentie(verzoekomtoewijzing.getFrequentie());
                }
                if (verzoekomtoewijzing.getRaamcontract() != null) {
                    existingVerzoekomtoewijzing.setRaamcontract(verzoekomtoewijzing.getRaamcontract());
                }
                if (verzoekomtoewijzing.getReferentieaanbieder() != null) {
                    existingVerzoekomtoewijzing.setReferentieaanbieder(verzoekomtoewijzing.getReferentieaanbieder());
                }
                if (verzoekomtoewijzing.getSoortverwijzer() != null) {
                    existingVerzoekomtoewijzing.setSoortverwijzer(verzoekomtoewijzing.getSoortverwijzer());
                }
                if (verzoekomtoewijzing.getVerwijzer() != null) {
                    existingVerzoekomtoewijzing.setVerwijzer(verzoekomtoewijzing.getVerwijzer());
                }
                if (verzoekomtoewijzing.getVolume() != null) {
                    existingVerzoekomtoewijzing.setVolume(verzoekomtoewijzing.getVolume());
                }

                return existingVerzoekomtoewijzing;
            })
            .map(verzoekomtoewijzingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzoekomtoewijzing.getId().toString())
        );
    }

    /**
     * {@code GET  /verzoekomtoewijzings} : get all the verzoekomtoewijzings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verzoekomtoewijzings in body.
     */
    @GetMapping("")
    public List<Verzoekomtoewijzing> getAllVerzoekomtoewijzings() {
        log.debug("REST request to get all Verzoekomtoewijzings");
        return verzoekomtoewijzingRepository.findAll();
    }

    /**
     * {@code GET  /verzoekomtoewijzings/:id} : get the "id" verzoekomtoewijzing.
     *
     * @param id the id of the verzoekomtoewijzing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verzoekomtoewijzing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verzoekomtoewijzing> getVerzoekomtoewijzing(@PathVariable("id") Long id) {
        log.debug("REST request to get Verzoekomtoewijzing : {}", id);
        Optional<Verzoekomtoewijzing> verzoekomtoewijzing = verzoekomtoewijzingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verzoekomtoewijzing);
    }

    /**
     * {@code DELETE  /verzoekomtoewijzings/:id} : delete the "id" verzoekomtoewijzing.
     *
     * @param id the id of the verzoekomtoewijzing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerzoekomtoewijzing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verzoekomtoewijzing : {}", id);
        verzoekomtoewijzingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
