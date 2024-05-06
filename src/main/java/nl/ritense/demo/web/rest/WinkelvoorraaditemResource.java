package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Winkelvoorraaditem;
import nl.ritense.demo.repository.WinkelvoorraaditemRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Winkelvoorraaditem}.
 */
@RestController
@RequestMapping("/api/winkelvoorraaditems")
@Transactional
public class WinkelvoorraaditemResource {

    private final Logger log = LoggerFactory.getLogger(WinkelvoorraaditemResource.class);

    private static final String ENTITY_NAME = "winkelvoorraaditem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WinkelvoorraaditemRepository winkelvoorraaditemRepository;

    public WinkelvoorraaditemResource(WinkelvoorraaditemRepository winkelvoorraaditemRepository) {
        this.winkelvoorraaditemRepository = winkelvoorraaditemRepository;
    }

    /**
     * {@code POST  /winkelvoorraaditems} : Create a new winkelvoorraaditem.
     *
     * @param winkelvoorraaditem the winkelvoorraaditem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new winkelvoorraaditem, or with status {@code 400 (Bad Request)} if the winkelvoorraaditem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Winkelvoorraaditem> createWinkelvoorraaditem(@RequestBody Winkelvoorraaditem winkelvoorraaditem)
        throws URISyntaxException {
        log.debug("REST request to save Winkelvoorraaditem : {}", winkelvoorraaditem);
        if (winkelvoorraaditem.getId() != null) {
            throw new BadRequestAlertException("A new winkelvoorraaditem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        winkelvoorraaditem = winkelvoorraaditemRepository.save(winkelvoorraaditem);
        return ResponseEntity.created(new URI("/api/winkelvoorraaditems/" + winkelvoorraaditem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, winkelvoorraaditem.getId().toString()))
            .body(winkelvoorraaditem);
    }

    /**
     * {@code PUT  /winkelvoorraaditems/:id} : Updates an existing winkelvoorraaditem.
     *
     * @param id the id of the winkelvoorraaditem to save.
     * @param winkelvoorraaditem the winkelvoorraaditem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated winkelvoorraaditem,
     * or with status {@code 400 (Bad Request)} if the winkelvoorraaditem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the winkelvoorraaditem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Winkelvoorraaditem> updateWinkelvoorraaditem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Winkelvoorraaditem winkelvoorraaditem
    ) throws URISyntaxException {
        log.debug("REST request to update Winkelvoorraaditem : {}, {}", id, winkelvoorraaditem);
        if (winkelvoorraaditem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, winkelvoorraaditem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!winkelvoorraaditemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        winkelvoorraaditem = winkelvoorraaditemRepository.save(winkelvoorraaditem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, winkelvoorraaditem.getId().toString()))
            .body(winkelvoorraaditem);
    }

    /**
     * {@code PATCH  /winkelvoorraaditems/:id} : Partial updates given fields of an existing winkelvoorraaditem, field will ignore if it is null
     *
     * @param id the id of the winkelvoorraaditem to save.
     * @param winkelvoorraaditem the winkelvoorraaditem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated winkelvoorraaditem,
     * or with status {@code 400 (Bad Request)} if the winkelvoorraaditem is not valid,
     * or with status {@code 404 (Not Found)} if the winkelvoorraaditem is not found,
     * or with status {@code 500 (Internal Server Error)} if the winkelvoorraaditem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Winkelvoorraaditem> partialUpdateWinkelvoorraaditem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Winkelvoorraaditem winkelvoorraaditem
    ) throws URISyntaxException {
        log.debug("REST request to partial update Winkelvoorraaditem partially : {}, {}", id, winkelvoorraaditem);
        if (winkelvoorraaditem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, winkelvoorraaditem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!winkelvoorraaditemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Winkelvoorraaditem> result = winkelvoorraaditemRepository
            .findById(winkelvoorraaditem.getId())
            .map(existingWinkelvoorraaditem -> {
                if (winkelvoorraaditem.getAantal() != null) {
                    existingWinkelvoorraaditem.setAantal(winkelvoorraaditem.getAantal());
                }
                if (winkelvoorraaditem.getAantalinbestelling() != null) {
                    existingWinkelvoorraaditem.setAantalinbestelling(winkelvoorraaditem.getAantalinbestelling());
                }
                if (winkelvoorraaditem.getDatumleveringbestelling() != null) {
                    existingWinkelvoorraaditem.setDatumleveringbestelling(winkelvoorraaditem.getDatumleveringbestelling());
                }
                if (winkelvoorraaditem.getLocatie() != null) {
                    existingWinkelvoorraaditem.setLocatie(winkelvoorraaditem.getLocatie());
                }

                return existingWinkelvoorraaditem;
            })
            .map(winkelvoorraaditemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, winkelvoorraaditem.getId().toString())
        );
    }

    /**
     * {@code GET  /winkelvoorraaditems} : get all the winkelvoorraaditems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of winkelvoorraaditems in body.
     */
    @GetMapping("")
    public List<Winkelvoorraaditem> getAllWinkelvoorraaditems() {
        log.debug("REST request to get all Winkelvoorraaditems");
        return winkelvoorraaditemRepository.findAll();
    }

    /**
     * {@code GET  /winkelvoorraaditems/:id} : get the "id" winkelvoorraaditem.
     *
     * @param id the id of the winkelvoorraaditem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the winkelvoorraaditem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Winkelvoorraaditem> getWinkelvoorraaditem(@PathVariable("id") Long id) {
        log.debug("REST request to get Winkelvoorraaditem : {}", id);
        Optional<Winkelvoorraaditem> winkelvoorraaditem = winkelvoorraaditemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(winkelvoorraaditem);
    }

    /**
     * {@code DELETE  /winkelvoorraaditems/:id} : delete the "id" winkelvoorraaditem.
     *
     * @param id the id of the winkelvoorraaditem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWinkelvoorraaditem(@PathVariable("id") Long id) {
        log.debug("REST request to delete Winkelvoorraaditem : {}", id);
        winkelvoorraaditemRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
