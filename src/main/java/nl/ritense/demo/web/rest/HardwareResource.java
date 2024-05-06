package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Hardware;
import nl.ritense.demo.repository.HardwareRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Hardware}.
 */
@RestController
@RequestMapping("/api/hardware")
@Transactional
public class HardwareResource {

    private final Logger log = LoggerFactory.getLogger(HardwareResource.class);

    private static final String ENTITY_NAME = "hardware";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HardwareRepository hardwareRepository;

    public HardwareResource(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    /**
     * {@code POST  /hardware} : Create a new hardware.
     *
     * @param hardware the hardware to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hardware, or with status {@code 400 (Bad Request)} if the hardware has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Hardware> createHardware(@RequestBody Hardware hardware) throws URISyntaxException {
        log.debug("REST request to save Hardware : {}", hardware);
        if (hardware.getId() != null) {
            throw new BadRequestAlertException("A new hardware cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hardware = hardwareRepository.save(hardware);
        return ResponseEntity.created(new URI("/api/hardware/" + hardware.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, hardware.getId().toString()))
            .body(hardware);
    }

    /**
     * {@code GET  /hardware} : get all the hardware.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hardware in body.
     */
    @GetMapping("")
    public List<Hardware> getAllHardware() {
        log.debug("REST request to get all Hardware");
        return hardwareRepository.findAll();
    }

    /**
     * {@code GET  /hardware/:id} : get the "id" hardware.
     *
     * @param id the id of the hardware to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hardware, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hardware> getHardware(@PathVariable("id") Long id) {
        log.debug("REST request to get Hardware : {}", id);
        Optional<Hardware> hardware = hardwareRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hardware);
    }

    /**
     * {@code DELETE  /hardware/:id} : delete the "id" hardware.
     *
     * @param id the id of the hardware to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHardware(@PathVariable("id") Long id) {
        log.debug("REST request to delete Hardware : {}", id);
        hardwareRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
