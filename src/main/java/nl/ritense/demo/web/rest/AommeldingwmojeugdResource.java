package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aommeldingwmojeugd;
import nl.ritense.demo.repository.AommeldingwmojeugdRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aommeldingwmojeugd}.
 */
@RestController
@RequestMapping("/api/aommeldingwmojeugds")
@Transactional
public class AommeldingwmojeugdResource {

    private final Logger log = LoggerFactory.getLogger(AommeldingwmojeugdResource.class);

    private static final String ENTITY_NAME = "aommeldingwmojeugd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AommeldingwmojeugdRepository aommeldingwmojeugdRepository;

    public AommeldingwmojeugdResource(AommeldingwmojeugdRepository aommeldingwmojeugdRepository) {
        this.aommeldingwmojeugdRepository = aommeldingwmojeugdRepository;
    }

    /**
     * {@code POST  /aommeldingwmojeugds} : Create a new aommeldingwmojeugd.
     *
     * @param aommeldingwmojeugd the aommeldingwmojeugd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aommeldingwmojeugd, or with status {@code 400 (Bad Request)} if the aommeldingwmojeugd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aommeldingwmojeugd> createAommeldingwmojeugd(@Valid @RequestBody Aommeldingwmojeugd aommeldingwmojeugd)
        throws URISyntaxException {
        log.debug("REST request to save Aommeldingwmojeugd : {}", aommeldingwmojeugd);
        if (aommeldingwmojeugd.getId() != null) {
            throw new BadRequestAlertException("A new aommeldingwmojeugd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aommeldingwmojeugd = aommeldingwmojeugdRepository.save(aommeldingwmojeugd);
        return ResponseEntity.created(new URI("/api/aommeldingwmojeugds/" + aommeldingwmojeugd.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aommeldingwmojeugd.getId().toString()))
            .body(aommeldingwmojeugd);
    }

    /**
     * {@code PUT  /aommeldingwmojeugds/:id} : Updates an existing aommeldingwmojeugd.
     *
     * @param id the id of the aommeldingwmojeugd to save.
     * @param aommeldingwmojeugd the aommeldingwmojeugd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aommeldingwmojeugd,
     * or with status {@code 400 (Bad Request)} if the aommeldingwmojeugd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aommeldingwmojeugd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aommeldingwmojeugd> updateAommeldingwmojeugd(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Aommeldingwmojeugd aommeldingwmojeugd
    ) throws URISyntaxException {
        log.debug("REST request to update Aommeldingwmojeugd : {}, {}", id, aommeldingwmojeugd);
        if (aommeldingwmojeugd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aommeldingwmojeugd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aommeldingwmojeugdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aommeldingwmojeugd = aommeldingwmojeugdRepository.save(aommeldingwmojeugd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aommeldingwmojeugd.getId().toString()))
            .body(aommeldingwmojeugd);
    }

    /**
     * {@code PATCH  /aommeldingwmojeugds/:id} : Partial updates given fields of an existing aommeldingwmojeugd, field will ignore if it is null
     *
     * @param id the id of the aommeldingwmojeugd to save.
     * @param aommeldingwmojeugd the aommeldingwmojeugd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aommeldingwmojeugd,
     * or with status {@code 400 (Bad Request)} if the aommeldingwmojeugd is not valid,
     * or with status {@code 404 (Not Found)} if the aommeldingwmojeugd is not found,
     * or with status {@code 500 (Internal Server Error)} if the aommeldingwmojeugd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aommeldingwmojeugd> partialUpdateAommeldingwmojeugd(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Aommeldingwmojeugd aommeldingwmojeugd
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aommeldingwmojeugd partially : {}, {}", id, aommeldingwmojeugd);
        if (aommeldingwmojeugd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aommeldingwmojeugd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aommeldingwmojeugdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aommeldingwmojeugd> result = aommeldingwmojeugdRepository
            .findById(aommeldingwmojeugd.getId())
            .map(existingAommeldingwmojeugd -> {
                if (aommeldingwmojeugd.getAanmelder() != null) {
                    existingAommeldingwmojeugd.setAanmelder(aommeldingwmojeugd.getAanmelder());
                }
                if (aommeldingwmojeugd.getAanmeldingdoor() != null) {
                    existingAommeldingwmojeugd.setAanmeldingdoor(aommeldingwmojeugd.getAanmeldingdoor());
                }
                if (aommeldingwmojeugd.getAanmeldingdoorlandelijk() != null) {
                    existingAommeldingwmojeugd.setAanmeldingdoorlandelijk(aommeldingwmojeugd.getAanmeldingdoorlandelijk());
                }
                if (aommeldingwmojeugd.getAanmeldwijze() != null) {
                    existingAommeldingwmojeugd.setAanmeldwijze(aommeldingwmojeugd.getAanmeldwijze());
                }
                if (aommeldingwmojeugd.getDeskundigheid() != null) {
                    existingAommeldingwmojeugd.setDeskundigheid(aommeldingwmojeugd.getDeskundigheid());
                }
                if (aommeldingwmojeugd.getIsclientopdehoogte() != null) {
                    existingAommeldingwmojeugd.setIsclientopdehoogte(aommeldingwmojeugd.getIsclientopdehoogte());
                }
                if (aommeldingwmojeugd.getOnderzoekswijze() != null) {
                    existingAommeldingwmojeugd.setOnderzoekswijze(aommeldingwmojeugd.getOnderzoekswijze());
                }
                if (aommeldingwmojeugd.getRedenafsluiting() != null) {
                    existingAommeldingwmojeugd.setRedenafsluiting(aommeldingwmojeugd.getRedenafsluiting());
                }
                if (aommeldingwmojeugd.getVervolg() != null) {
                    existingAommeldingwmojeugd.setVervolg(aommeldingwmojeugd.getVervolg());
                }
                if (aommeldingwmojeugd.getVerwezen() != null) {
                    existingAommeldingwmojeugd.setVerwezen(aommeldingwmojeugd.getVerwezen());
                }

                return existingAommeldingwmojeugd;
            })
            .map(aommeldingwmojeugdRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aommeldingwmojeugd.getId().toString())
        );
    }

    /**
     * {@code GET  /aommeldingwmojeugds} : get all the aommeldingwmojeugds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aommeldingwmojeugds in body.
     */
    @GetMapping("")
    public List<Aommeldingwmojeugd> getAllAommeldingwmojeugds() {
        log.debug("REST request to get all Aommeldingwmojeugds");
        return aommeldingwmojeugdRepository.findAll();
    }

    /**
     * {@code GET  /aommeldingwmojeugds/:id} : get the "id" aommeldingwmojeugd.
     *
     * @param id the id of the aommeldingwmojeugd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aommeldingwmojeugd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aommeldingwmojeugd> getAommeldingwmojeugd(@PathVariable("id") Long id) {
        log.debug("REST request to get Aommeldingwmojeugd : {}", id);
        Optional<Aommeldingwmojeugd> aommeldingwmojeugd = aommeldingwmojeugdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aommeldingwmojeugd);
    }

    /**
     * {@code DELETE  /aommeldingwmojeugds/:id} : delete the "id" aommeldingwmojeugd.
     *
     * @param id the id of the aommeldingwmojeugd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAommeldingwmojeugd(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aommeldingwmojeugd : {}", id);
        aommeldingwmojeugdRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
