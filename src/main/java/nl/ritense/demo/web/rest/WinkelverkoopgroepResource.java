package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Winkelverkoopgroep;
import nl.ritense.demo.repository.WinkelverkoopgroepRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Winkelverkoopgroep}.
 */
@RestController
@RequestMapping("/api/winkelverkoopgroeps")
@Transactional
public class WinkelverkoopgroepResource {

    private final Logger log = LoggerFactory.getLogger(WinkelverkoopgroepResource.class);

    private static final String ENTITY_NAME = "winkelverkoopgroep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WinkelverkoopgroepRepository winkelverkoopgroepRepository;

    public WinkelverkoopgroepResource(WinkelverkoopgroepRepository winkelverkoopgroepRepository) {
        this.winkelverkoopgroepRepository = winkelverkoopgroepRepository;
    }

    /**
     * {@code POST  /winkelverkoopgroeps} : Create a new winkelverkoopgroep.
     *
     * @param winkelverkoopgroep the winkelverkoopgroep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new winkelverkoopgroep, or with status {@code 400 (Bad Request)} if the winkelverkoopgroep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Winkelverkoopgroep> createWinkelverkoopgroep(@RequestBody Winkelverkoopgroep winkelverkoopgroep)
        throws URISyntaxException {
        log.debug("REST request to save Winkelverkoopgroep : {}", winkelverkoopgroep);
        if (winkelverkoopgroep.getId() != null) {
            throw new BadRequestAlertException("A new winkelverkoopgroep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        winkelverkoopgroep = winkelverkoopgroepRepository.save(winkelverkoopgroep);
        return ResponseEntity.created(new URI("/api/winkelverkoopgroeps/" + winkelverkoopgroep.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, winkelverkoopgroep.getId().toString()))
            .body(winkelverkoopgroep);
    }

    /**
     * {@code GET  /winkelverkoopgroeps} : get all the winkelverkoopgroeps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of winkelverkoopgroeps in body.
     */
    @GetMapping("")
    public List<Winkelverkoopgroep> getAllWinkelverkoopgroeps() {
        log.debug("REST request to get all Winkelverkoopgroeps");
        return winkelverkoopgroepRepository.findAll();
    }

    /**
     * {@code GET  /winkelverkoopgroeps/:id} : get the "id" winkelverkoopgroep.
     *
     * @param id the id of the winkelverkoopgroep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the winkelverkoopgroep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Winkelverkoopgroep> getWinkelverkoopgroep(@PathVariable("id") Long id) {
        log.debug("REST request to get Winkelverkoopgroep : {}", id);
        Optional<Winkelverkoopgroep> winkelverkoopgroep = winkelverkoopgroepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(winkelverkoopgroep);
    }

    /**
     * {@code DELETE  /winkelverkoopgroeps/:id} : delete the "id" winkelverkoopgroep.
     *
     * @param id the id of the winkelverkoopgroep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWinkelverkoopgroep(@PathVariable("id") Long id) {
        log.debug("REST request to delete Winkelverkoopgroep : {}", id);
        winkelverkoopgroepRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
