package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Inkooporder;
import nl.ritense.demo.repository.InkooporderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inkooporder}.
 */
@RestController
@RequestMapping("/api/inkooporders")
@Transactional
public class InkooporderResource {

    private final Logger log = LoggerFactory.getLogger(InkooporderResource.class);

    private static final String ENTITY_NAME = "inkooporder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InkooporderRepository inkooporderRepository;

    public InkooporderResource(InkooporderRepository inkooporderRepository) {
        this.inkooporderRepository = inkooporderRepository;
    }

    /**
     * {@code POST  /inkooporders} : Create a new inkooporder.
     *
     * @param inkooporder the inkooporder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inkooporder, or with status {@code 400 (Bad Request)} if the inkooporder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inkooporder> createInkooporder(@Valid @RequestBody Inkooporder inkooporder) throws URISyntaxException {
        log.debug("REST request to save Inkooporder : {}", inkooporder);
        if (inkooporder.getId() != null) {
            throw new BadRequestAlertException("A new inkooporder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inkooporder = inkooporderRepository.save(inkooporder);
        return ResponseEntity.created(new URI("/api/inkooporders/" + inkooporder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inkooporder.getId().toString()))
            .body(inkooporder);
    }

    /**
     * {@code PUT  /inkooporders/:id} : Updates an existing inkooporder.
     *
     * @param id the id of the inkooporder to save.
     * @param inkooporder the inkooporder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inkooporder,
     * or with status {@code 400 (Bad Request)} if the inkooporder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inkooporder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inkooporder> updateInkooporder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Inkooporder inkooporder
    ) throws URISyntaxException {
        log.debug("REST request to update Inkooporder : {}, {}", id, inkooporder);
        if (inkooporder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inkooporder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inkooporderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inkooporder = inkooporderRepository.save(inkooporder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inkooporder.getId().toString()))
            .body(inkooporder);
    }

    /**
     * {@code PATCH  /inkooporders/:id} : Partial updates given fields of an existing inkooporder, field will ignore if it is null
     *
     * @param id the id of the inkooporder to save.
     * @param inkooporder the inkooporder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inkooporder,
     * or with status {@code 400 (Bad Request)} if the inkooporder is not valid,
     * or with status {@code 404 (Not Found)} if the inkooporder is not found,
     * or with status {@code 500 (Internal Server Error)} if the inkooporder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inkooporder> partialUpdateInkooporder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Inkooporder inkooporder
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inkooporder partially : {}, {}", id, inkooporder);
        if (inkooporder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inkooporder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inkooporderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inkooporder> result = inkooporderRepository
            .findById(inkooporder.getId())
            .map(existingInkooporder -> {
                if (inkooporder.getArtikelcode() != null) {
                    existingInkooporder.setArtikelcode(inkooporder.getArtikelcode());
                }
                if (inkooporder.getBetalingmeerderejaren() != null) {
                    existingInkooporder.setBetalingmeerderejaren(inkooporder.getBetalingmeerderejaren());
                }
                if (inkooporder.getBetreft() != null) {
                    existingInkooporder.setBetreft(inkooporder.getBetreft());
                }
                if (inkooporder.getDatumeinde() != null) {
                    existingInkooporder.setDatumeinde(inkooporder.getDatumeinde());
                }
                if (inkooporder.getDatumingediend() != null) {
                    existingInkooporder.setDatumingediend(inkooporder.getDatumingediend());
                }
                if (inkooporder.getDatumstart() != null) {
                    existingInkooporder.setDatumstart(inkooporder.getDatumstart());
                }
                if (inkooporder.getGoederencode() != null) {
                    existingInkooporder.setGoederencode(inkooporder.getGoederencode());
                }
                if (inkooporder.getOmschrijving() != null) {
                    existingInkooporder.setOmschrijving(inkooporder.getOmschrijving());
                }
                if (inkooporder.getOrdernummer() != null) {
                    existingInkooporder.setOrdernummer(inkooporder.getOrdernummer());
                }
                if (inkooporder.getSaldo() != null) {
                    existingInkooporder.setSaldo(inkooporder.getSaldo());
                }
                if (inkooporder.getTotaalnettobedrag() != null) {
                    existingInkooporder.setTotaalnettobedrag(inkooporder.getTotaalnettobedrag());
                }
                if (inkooporder.getWijzevanaanbesteden() != null) {
                    existingInkooporder.setWijzevanaanbesteden(inkooporder.getWijzevanaanbesteden());
                }

                return existingInkooporder;
            })
            .map(inkooporderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inkooporder.getId().toString())
        );
    }

    /**
     * {@code GET  /inkooporders} : get all the inkooporders.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inkooporders in body.
     */
    @GetMapping("")
    public List<Inkooporder> getAllInkooporders(
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("oorspronkelijkinkooporder2-is-null".equals(filter)) {
            log.debug("REST request to get all Inkooporders where oorspronkelijkInkooporder2 is null");
            return StreamSupport.stream(inkooporderRepository.findAll().spliterator(), false)
                .filter(inkooporder -> inkooporder.getOorspronkelijkInkooporder2() == null)
                .toList();
        }
        log.debug("REST request to get all Inkooporders");
        if (eagerload) {
            return inkooporderRepository.findAllWithEagerRelationships();
        } else {
            return inkooporderRepository.findAll();
        }
    }

    /**
     * {@code GET  /inkooporders/:id} : get the "id" inkooporder.
     *
     * @param id the id of the inkooporder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inkooporder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inkooporder> getInkooporder(@PathVariable("id") Long id) {
        log.debug("REST request to get Inkooporder : {}", id);
        Optional<Inkooporder> inkooporder = inkooporderRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(inkooporder);
    }

    /**
     * {@code DELETE  /inkooporders/:id} : delete the "id" inkooporder.
     *
     * @param id the id of the inkooporder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInkooporder(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inkooporder : {}", id);
        inkooporderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
