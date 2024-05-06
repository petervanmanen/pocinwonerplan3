package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kadastraleonroerendezaak;
import nl.ritense.demo.repository.KadastraleonroerendezaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kadastraleonroerendezaak}.
 */
@RestController
@RequestMapping("/api/kadastraleonroerendezaaks")
@Transactional
public class KadastraleonroerendezaakResource {

    private final Logger log = LoggerFactory.getLogger(KadastraleonroerendezaakResource.class);

    private static final String ENTITY_NAME = "kadastraleonroerendezaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KadastraleonroerendezaakRepository kadastraleonroerendezaakRepository;

    public KadastraleonroerendezaakResource(KadastraleonroerendezaakRepository kadastraleonroerendezaakRepository) {
        this.kadastraleonroerendezaakRepository = kadastraleonroerendezaakRepository;
    }

    /**
     * {@code POST  /kadastraleonroerendezaaks} : Create a new kadastraleonroerendezaak.
     *
     * @param kadastraleonroerendezaak the kadastraleonroerendezaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kadastraleonroerendezaak, or with status {@code 400 (Bad Request)} if the kadastraleonroerendezaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kadastraleonroerendezaak> createKadastraleonroerendezaak(
        @Valid @RequestBody Kadastraleonroerendezaak kadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to save Kadastraleonroerendezaak : {}", kadastraleonroerendezaak);
        if (kadastraleonroerendezaak.getId() != null) {
            throw new BadRequestAlertException("A new kadastraleonroerendezaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kadastraleonroerendezaak = kadastraleonroerendezaakRepository.save(kadastraleonroerendezaak);
        return ResponseEntity.created(new URI("/api/kadastraleonroerendezaaks/" + kadastraleonroerendezaak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kadastraleonroerendezaak.getId().toString()))
            .body(kadastraleonroerendezaak);
    }

    /**
     * {@code PUT  /kadastraleonroerendezaaks/:id} : Updates an existing kadastraleonroerendezaak.
     *
     * @param id the id of the kadastraleonroerendezaak to save.
     * @param kadastraleonroerendezaak the kadastraleonroerendezaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastraleonroerendezaak,
     * or with status {@code 400 (Bad Request)} if the kadastraleonroerendezaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kadastraleonroerendezaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kadastraleonroerendezaak> updateKadastraleonroerendezaak(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Kadastraleonroerendezaak kadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to update Kadastraleonroerendezaak : {}, {}", id, kadastraleonroerendezaak);
        if (kadastraleonroerendezaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastraleonroerendezaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastraleonroerendezaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kadastraleonroerendezaak = kadastraleonroerendezaakRepository.save(kadastraleonroerendezaak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastraleonroerendezaak.getId().toString()))
            .body(kadastraleonroerendezaak);
    }

    /**
     * {@code PATCH  /kadastraleonroerendezaaks/:id} : Partial updates given fields of an existing kadastraleonroerendezaak, field will ignore if it is null
     *
     * @param id the id of the kadastraleonroerendezaak to save.
     * @param kadastraleonroerendezaak the kadastraleonroerendezaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kadastraleonroerendezaak,
     * or with status {@code 400 (Bad Request)} if the kadastraleonroerendezaak is not valid,
     * or with status {@code 404 (Not Found)} if the kadastraleonroerendezaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the kadastraleonroerendezaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kadastraleonroerendezaak> partialUpdateKadastraleonroerendezaak(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Kadastraleonroerendezaak kadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kadastraleonroerendezaak partially : {}, {}", id, kadastraleonroerendezaak);
        if (kadastraleonroerendezaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kadastraleonroerendezaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kadastraleonroerendezaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kadastraleonroerendezaak> result = kadastraleonroerendezaakRepository
            .findById(kadastraleonroerendezaak.getId())
            .map(existingKadastraleonroerendezaak -> {
                if (kadastraleonroerendezaak.getEmpty() != null) {
                    existingKadastraleonroerendezaak.setEmpty(kadastraleonroerendezaak.getEmpty());
                }
                if (kadastraleonroerendezaak.getAppartementsrechtvolgnummer() != null) {
                    existingKadastraleonroerendezaak.setAppartementsrechtvolgnummer(
                        kadastraleonroerendezaak.getAppartementsrechtvolgnummer()
                    );
                }
                if (kadastraleonroerendezaak.getBegrenzing() != null) {
                    existingKadastraleonroerendezaak.setBegrenzing(kadastraleonroerendezaak.getBegrenzing());
                }
                if (kadastraleonroerendezaak.getCultuurcodeonbebouwd() != null) {
                    existingKadastraleonroerendezaak.setCultuurcodeonbebouwd(kadastraleonroerendezaak.getCultuurcodeonbebouwd());
                }
                if (kadastraleonroerendezaak.getDatumbegingeldigheidkadastraleonroerendezaak() != null) {
                    existingKadastraleonroerendezaak.setDatumbegingeldigheidkadastraleonroerendezaak(
                        kadastraleonroerendezaak.getDatumbegingeldigheidkadastraleonroerendezaak()
                    );
                }
                if (kadastraleonroerendezaak.getDatumeindegeldigheidkadastraleonroerendezaak() != null) {
                    existingKadastraleonroerendezaak.setDatumeindegeldigheidkadastraleonroerendezaak(
                        kadastraleonroerendezaak.getDatumeindegeldigheidkadastraleonroerendezaak()
                    );
                }
                if (kadastraleonroerendezaak.getIdentificatie() != null) {
                    existingKadastraleonroerendezaak.setIdentificatie(kadastraleonroerendezaak.getIdentificatie());
                }
                if (kadastraleonroerendezaak.getKadastralegemeente() != null) {
                    existingKadastraleonroerendezaak.setKadastralegemeente(kadastraleonroerendezaak.getKadastralegemeente());
                }
                if (kadastraleonroerendezaak.getKadastralegemeentecode() != null) {
                    existingKadastraleonroerendezaak.setKadastralegemeentecode(kadastraleonroerendezaak.getKadastralegemeentecode());
                }
                if (kadastraleonroerendezaak.getKoopjaar() != null) {
                    existingKadastraleonroerendezaak.setKoopjaar(kadastraleonroerendezaak.getKoopjaar());
                }
                if (kadastraleonroerendezaak.getKoopsom() != null) {
                    existingKadastraleonroerendezaak.setKoopsom(kadastraleonroerendezaak.getKoopsom());
                }
                if (kadastraleonroerendezaak.getLandinrichtingrentebedrag() != null) {
                    existingKadastraleonroerendezaak.setLandinrichtingrentebedrag(kadastraleonroerendezaak.getLandinrichtingrentebedrag());
                }
                if (kadastraleonroerendezaak.getLandinrichtingrenteeindejaar() != null) {
                    existingKadastraleonroerendezaak.setLandinrichtingrenteeindejaar(
                        kadastraleonroerendezaak.getLandinrichtingrenteeindejaar()
                    );
                }
                if (kadastraleonroerendezaak.getLigging() != null) {
                    existingKadastraleonroerendezaak.setLigging(kadastraleonroerendezaak.getLigging());
                }
                if (kadastraleonroerendezaak.getLocatieomschrijving() != null) {
                    existingKadastraleonroerendezaak.setLocatieomschrijving(kadastraleonroerendezaak.getLocatieomschrijving());
                }
                if (kadastraleonroerendezaak.getOppervlakte() != null) {
                    existingKadastraleonroerendezaak.setOppervlakte(kadastraleonroerendezaak.getOppervlakte());
                }
                if (kadastraleonroerendezaak.getOud() != null) {
                    existingKadastraleonroerendezaak.setOud(kadastraleonroerendezaak.getOud());
                }
                if (kadastraleonroerendezaak.getPerceelnummer() != null) {
                    existingKadastraleonroerendezaak.setPerceelnummer(kadastraleonroerendezaak.getPerceelnummer());
                }
                if (kadastraleonroerendezaak.getSectie() != null) {
                    existingKadastraleonroerendezaak.setSectie(kadastraleonroerendezaak.getSectie());
                }
                if (kadastraleonroerendezaak.getValutacode() != null) {
                    existingKadastraleonroerendezaak.setValutacode(kadastraleonroerendezaak.getValutacode());
                }

                return existingKadastraleonroerendezaak;
            })
            .map(kadastraleonroerendezaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kadastraleonroerendezaak.getId().toString())
        );
    }

    /**
     * {@code GET  /kadastraleonroerendezaaks} : get all the kadastraleonroerendezaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kadastraleonroerendezaaks in body.
     */
    @GetMapping("")
    public List<Kadastraleonroerendezaak> getAllKadastraleonroerendezaaks() {
        log.debug("REST request to get all Kadastraleonroerendezaaks");
        return kadastraleonroerendezaakRepository.findAll();
    }

    /**
     * {@code GET  /kadastraleonroerendezaaks/:id} : get the "id" kadastraleonroerendezaak.
     *
     * @param id the id of the kadastraleonroerendezaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kadastraleonroerendezaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kadastraleonroerendezaak> getKadastraleonroerendezaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Kadastraleonroerendezaak : {}", id);
        Optional<Kadastraleonroerendezaak> kadastraleonroerendezaak = kadastraleonroerendezaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kadastraleonroerendezaak);
    }

    /**
     * {@code DELETE  /kadastraleonroerendezaaks/:id} : delete the "id" kadastraleonroerendezaak.
     *
     * @param id the id of the kadastraleonroerendezaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKadastraleonroerendezaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kadastraleonroerendezaak : {}", id);
        kadastraleonroerendezaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
