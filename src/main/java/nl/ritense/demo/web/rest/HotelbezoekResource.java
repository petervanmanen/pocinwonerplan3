package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Hotelbezoek;
import nl.ritense.demo.repository.HotelbezoekRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Hotelbezoek}.
 */
@RestController
@RequestMapping("/api/hotelbezoeks")
@Transactional
public class HotelbezoekResource {

    private final Logger log = LoggerFactory.getLogger(HotelbezoekResource.class);

    private static final String ENTITY_NAME = "hotelbezoek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelbezoekRepository hotelbezoekRepository;

    public HotelbezoekResource(HotelbezoekRepository hotelbezoekRepository) {
        this.hotelbezoekRepository = hotelbezoekRepository;
    }

    /**
     * {@code POST  /hotelbezoeks} : Create a new hotelbezoek.
     *
     * @param hotelbezoek the hotelbezoek to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelbezoek, or with status {@code 400 (Bad Request)} if the hotelbezoek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Hotelbezoek> createHotelbezoek(@RequestBody Hotelbezoek hotelbezoek) throws URISyntaxException {
        log.debug("REST request to save Hotelbezoek : {}", hotelbezoek);
        if (hotelbezoek.getId() != null) {
            throw new BadRequestAlertException("A new hotelbezoek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hotelbezoek = hotelbezoekRepository.save(hotelbezoek);
        return ResponseEntity.created(new URI("/api/hotelbezoeks/" + hotelbezoek.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, hotelbezoek.getId().toString()))
            .body(hotelbezoek);
    }

    /**
     * {@code PUT  /hotelbezoeks/:id} : Updates an existing hotelbezoek.
     *
     * @param id the id of the hotelbezoek to save.
     * @param hotelbezoek the hotelbezoek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelbezoek,
     * or with status {@code 400 (Bad Request)} if the hotelbezoek is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelbezoek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hotelbezoek> updateHotelbezoek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Hotelbezoek hotelbezoek
    ) throws URISyntaxException {
        log.debug("REST request to update Hotelbezoek : {}, {}", id, hotelbezoek);
        if (hotelbezoek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelbezoek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelbezoekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hotelbezoek = hotelbezoekRepository.save(hotelbezoek);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hotelbezoek.getId().toString()))
            .body(hotelbezoek);
    }

    /**
     * {@code PATCH  /hotelbezoeks/:id} : Partial updates given fields of an existing hotelbezoek, field will ignore if it is null
     *
     * @param id the id of the hotelbezoek to save.
     * @param hotelbezoek the hotelbezoek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelbezoek,
     * or with status {@code 400 (Bad Request)} if the hotelbezoek is not valid,
     * or with status {@code 404 (Not Found)} if the hotelbezoek is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelbezoek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Hotelbezoek> partialUpdateHotelbezoek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Hotelbezoek hotelbezoek
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hotelbezoek partially : {}, {}", id, hotelbezoek);
        if (hotelbezoek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelbezoek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelbezoekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hotelbezoek> result = hotelbezoekRepository
            .findById(hotelbezoek.getId())
            .map(existingHotelbezoek -> {
                if (hotelbezoek.getDatumeinde() != null) {
                    existingHotelbezoek.setDatumeinde(hotelbezoek.getDatumeinde());
                }
                if (hotelbezoek.getDatumstart() != null) {
                    existingHotelbezoek.setDatumstart(hotelbezoek.getDatumstart());
                }

                return existingHotelbezoek;
            })
            .map(hotelbezoekRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hotelbezoek.getId().toString())
        );
    }

    /**
     * {@code GET  /hotelbezoeks} : get all the hotelbezoeks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelbezoeks in body.
     */
    @GetMapping("")
    public List<Hotelbezoek> getAllHotelbezoeks() {
        log.debug("REST request to get all Hotelbezoeks");
        return hotelbezoekRepository.findAll();
    }

    /**
     * {@code GET  /hotelbezoeks/:id} : get the "id" hotelbezoek.
     *
     * @param id the id of the hotelbezoek to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelbezoek, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotelbezoek> getHotelbezoek(@PathVariable("id") Long id) {
        log.debug("REST request to get Hotelbezoek : {}", id);
        Optional<Hotelbezoek> hotelbezoek = hotelbezoekRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hotelbezoek);
    }

    /**
     * {@code DELETE  /hotelbezoeks/:id} : delete the "id" hotelbezoek.
     *
     * @param id the id of the hotelbezoek to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotelbezoek(@PathVariable("id") Long id) {
        log.debug("REST request to delete Hotelbezoek : {}", id);
        hotelbezoekRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
