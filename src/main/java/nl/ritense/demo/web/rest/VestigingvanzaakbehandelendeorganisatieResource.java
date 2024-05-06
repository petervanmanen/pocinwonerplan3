package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Vestigingvanzaakbehandelendeorganisatie;
import nl.ritense.demo.repository.VestigingvanzaakbehandelendeorganisatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vestigingvanzaakbehandelendeorganisatie}.
 */
@RestController
@RequestMapping("/api/vestigingvanzaakbehandelendeorganisaties")
@Transactional
public class VestigingvanzaakbehandelendeorganisatieResource {

    private final Logger log = LoggerFactory.getLogger(VestigingvanzaakbehandelendeorganisatieResource.class);

    private static final String ENTITY_NAME = "vestigingvanzaakbehandelendeorganisatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VestigingvanzaakbehandelendeorganisatieRepository vestigingvanzaakbehandelendeorganisatieRepository;

    public VestigingvanzaakbehandelendeorganisatieResource(
        VestigingvanzaakbehandelendeorganisatieRepository vestigingvanzaakbehandelendeorganisatieRepository
    ) {
        this.vestigingvanzaakbehandelendeorganisatieRepository = vestigingvanzaakbehandelendeorganisatieRepository;
    }

    /**
     * {@code POST  /vestigingvanzaakbehandelendeorganisaties} : Create a new vestigingvanzaakbehandelendeorganisatie.
     *
     * @param vestigingvanzaakbehandelendeorganisatie the vestigingvanzaakbehandelendeorganisatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vestigingvanzaakbehandelendeorganisatie, or with status {@code 400 (Bad Request)} if the vestigingvanzaakbehandelendeorganisatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vestigingvanzaakbehandelendeorganisatie> createVestigingvanzaakbehandelendeorganisatie(
        @RequestBody Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie
    ) throws URISyntaxException {
        log.debug("REST request to save Vestigingvanzaakbehandelendeorganisatie : {}", vestigingvanzaakbehandelendeorganisatie);
        if (vestigingvanzaakbehandelendeorganisatie.getId() != null) {
            throw new BadRequestAlertException(
                "A new vestigingvanzaakbehandelendeorganisatie cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        vestigingvanzaakbehandelendeorganisatie = vestigingvanzaakbehandelendeorganisatieRepository.save(
            vestigingvanzaakbehandelendeorganisatie
        );
        return ResponseEntity.created(
            new URI("/api/vestigingvanzaakbehandelendeorganisaties/" + vestigingvanzaakbehandelendeorganisatie.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    vestigingvanzaakbehandelendeorganisatie.getId().toString()
                )
            )
            .body(vestigingvanzaakbehandelendeorganisatie);
    }

    /**
     * {@code GET  /vestigingvanzaakbehandelendeorganisaties} : get all the vestigingvanzaakbehandelendeorganisaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vestigingvanzaakbehandelendeorganisaties in body.
     */
    @GetMapping("")
    public List<Vestigingvanzaakbehandelendeorganisatie> getAllVestigingvanzaakbehandelendeorganisaties() {
        log.debug("REST request to get all Vestigingvanzaakbehandelendeorganisaties");
        return vestigingvanzaakbehandelendeorganisatieRepository.findAll();
    }

    /**
     * {@code GET  /vestigingvanzaakbehandelendeorganisaties/:id} : get the "id" vestigingvanzaakbehandelendeorganisatie.
     *
     * @param id the id of the vestigingvanzaakbehandelendeorganisatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vestigingvanzaakbehandelendeorganisatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vestigingvanzaakbehandelendeorganisatie> getVestigingvanzaakbehandelendeorganisatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Vestigingvanzaakbehandelendeorganisatie : {}", id);
        Optional<Vestigingvanzaakbehandelendeorganisatie> vestigingvanzaakbehandelendeorganisatie =
            vestigingvanzaakbehandelendeorganisatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vestigingvanzaakbehandelendeorganisatie);
    }

    /**
     * {@code DELETE  /vestigingvanzaakbehandelendeorganisaties/:id} : delete the "id" vestigingvanzaakbehandelendeorganisatie.
     *
     * @param id the id of the vestigingvanzaakbehandelendeorganisatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVestigingvanzaakbehandelendeorganisatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vestigingvanzaakbehandelendeorganisatie : {}", id);
        vestigingvanzaakbehandelendeorganisatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
