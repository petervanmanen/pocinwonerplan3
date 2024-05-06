package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Reservering;
import nl.ritense.demo.repository.ReserveringRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Reservering}.
 */
@RestController
@RequestMapping("/api/reserverings")
@Transactional
public class ReserveringResource {

    private final Logger log = LoggerFactory.getLogger(ReserveringResource.class);

    private static final String ENTITY_NAME = "reservering";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReserveringRepository reserveringRepository;

    public ReserveringResource(ReserveringRepository reserveringRepository) {
        this.reserveringRepository = reserveringRepository;
    }

    /**
     * {@code POST  /reserverings} : Create a new reservering.
     *
     * @param reservering the reservering to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservering, or with status {@code 400 (Bad Request)} if the reservering has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Reservering> createReservering(@Valid @RequestBody Reservering reservering) throws URISyntaxException {
        log.debug("REST request to save Reservering : {}", reservering);
        if (reservering.getId() != null) {
            throw new BadRequestAlertException("A new reservering cannot already have an ID", ENTITY_NAME, "idexists");
        }
        reservering = reserveringRepository.save(reservering);
        return ResponseEntity.created(new URI("/api/reserverings/" + reservering.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, reservering.getId().toString()))
            .body(reservering);
    }

    /**
     * {@code PUT  /reserverings/:id} : Updates an existing reservering.
     *
     * @param id the id of the reservering to save.
     * @param reservering the reservering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservering,
     * or with status {@code 400 (Bad Request)} if the reservering is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reservering> updateReservering(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Reservering reservering
    ) throws URISyntaxException {
        log.debug("REST request to update Reservering : {}, {}", id, reservering);
        if (reservering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reserveringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        reservering = reserveringRepository.save(reservering);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reservering.getId().toString()))
            .body(reservering);
    }

    /**
     * {@code PATCH  /reserverings/:id} : Partial updates given fields of an existing reservering, field will ignore if it is null
     *
     * @param id the id of the reservering to save.
     * @param reservering the reservering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservering,
     * or with status {@code 400 (Bad Request)} if the reservering is not valid,
     * or with status {@code 404 (Not Found)} if the reservering is not found,
     * or with status {@code 500 (Internal Server Error)} if the reservering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Reservering> partialUpdateReservering(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Reservering reservering
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reservering partially : {}, {}", id, reservering);
        if (reservering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reserveringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Reservering> result = reserveringRepository
            .findById(reservering.getId())
            .map(existingReservering -> {
                if (reservering.getAantal() != null) {
                    existingReservering.setAantal(reservering.getAantal());
                }
                if (reservering.getBtw() != null) {
                    existingReservering.setBtw(reservering.getBtw());
                }
                if (reservering.getTijdtot() != null) {
                    existingReservering.setTijdtot(reservering.getTijdtot());
                }
                if (reservering.getTijdvanaf() != null) {
                    existingReservering.setTijdvanaf(reservering.getTijdvanaf());
                }
                if (reservering.getTotaalprijs() != null) {
                    existingReservering.setTotaalprijs(reservering.getTotaalprijs());
                }

                return existingReservering;
            })
            .map(reserveringRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reservering.getId().toString())
        );
    }

    /**
     * {@code GET  /reserverings} : get all the reserverings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reserverings in body.
     */
    @GetMapping("")
    public List<Reservering> getAllReserverings() {
        log.debug("REST request to get all Reserverings");
        return reserveringRepository.findAll();
    }

    /**
     * {@code GET  /reserverings/:id} : get the "id" reservering.
     *
     * @param id the id of the reservering to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservering, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservering> getReservering(@PathVariable("id") Long id) {
        log.debug("REST request to get Reservering : {}", id);
        Optional<Reservering> reservering = reserveringRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reservering);
    }

    /**
     * {@code DELETE  /reserverings/:id} : delete the "id" reservering.
     *
     * @param id the id of the reservering to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservering(@PathVariable("id") Long id) {
        log.debug("REST request to delete Reservering : {}", id);
        reserveringRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
