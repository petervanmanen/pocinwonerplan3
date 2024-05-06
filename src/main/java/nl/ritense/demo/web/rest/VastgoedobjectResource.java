package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Vastgoedobject;
import nl.ritense.demo.repository.VastgoedobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vastgoedobject}.
 */
@RestController
@RequestMapping("/api/vastgoedobjects")
@Transactional
public class VastgoedobjectResource {

    private final Logger log = LoggerFactory.getLogger(VastgoedobjectResource.class);

    private static final String ENTITY_NAME = "vastgoedobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VastgoedobjectRepository vastgoedobjectRepository;

    public VastgoedobjectResource(VastgoedobjectRepository vastgoedobjectRepository) {
        this.vastgoedobjectRepository = vastgoedobjectRepository;
    }

    /**
     * {@code POST  /vastgoedobjects} : Create a new vastgoedobject.
     *
     * @param vastgoedobject the vastgoedobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vastgoedobject, or with status {@code 400 (Bad Request)} if the vastgoedobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vastgoedobject> createVastgoedobject(@Valid @RequestBody Vastgoedobject vastgoedobject)
        throws URISyntaxException {
        log.debug("REST request to save Vastgoedobject : {}", vastgoedobject);
        if (vastgoedobject.getId() != null) {
            throw new BadRequestAlertException("A new vastgoedobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vastgoedobject = vastgoedobjectRepository.save(vastgoedobject);
        return ResponseEntity.created(new URI("/api/vastgoedobjects/" + vastgoedobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vastgoedobject.getId().toString()))
            .body(vastgoedobject);
    }

    /**
     * {@code PUT  /vastgoedobjects/:id} : Updates an existing vastgoedobject.
     *
     * @param id the id of the vastgoedobject to save.
     * @param vastgoedobject the vastgoedobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vastgoedobject,
     * or with status {@code 400 (Bad Request)} if the vastgoedobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vastgoedobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vastgoedobject> updateVastgoedobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vastgoedobject vastgoedobject
    ) throws URISyntaxException {
        log.debug("REST request to update Vastgoedobject : {}, {}", id, vastgoedobject);
        if (vastgoedobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vastgoedobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vastgoedobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vastgoedobject = vastgoedobjectRepository.save(vastgoedobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vastgoedobject.getId().toString()))
            .body(vastgoedobject);
    }

    /**
     * {@code PATCH  /vastgoedobjects/:id} : Partial updates given fields of an existing vastgoedobject, field will ignore if it is null
     *
     * @param id the id of the vastgoedobject to save.
     * @param vastgoedobject the vastgoedobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vastgoedobject,
     * or with status {@code 400 (Bad Request)} if the vastgoedobject is not valid,
     * or with status {@code 404 (Not Found)} if the vastgoedobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the vastgoedobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vastgoedobject> partialUpdateVastgoedobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vastgoedobject vastgoedobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vastgoedobject partially : {}, {}", id, vastgoedobject);
        if (vastgoedobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vastgoedobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vastgoedobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vastgoedobject> result = vastgoedobjectRepository
            .findById(vastgoedobject.getId())
            .map(existingVastgoedobject -> {
                if (vastgoedobject.getAantaletages() != null) {
                    existingVastgoedobject.setAantaletages(vastgoedobject.getAantaletages());
                }
                if (vastgoedobject.getAantalparkeerplaatsen() != null) {
                    existingVastgoedobject.setAantalparkeerplaatsen(vastgoedobject.getAantalparkeerplaatsen());
                }
                if (vastgoedobject.getAantalrioleringen() != null) {
                    existingVastgoedobject.setAantalrioleringen(vastgoedobject.getAantalrioleringen());
                }
                if (vastgoedobject.getAdresaanduiding() != null) {
                    existingVastgoedobject.setAdresaanduiding(vastgoedobject.getAdresaanduiding());
                }
                if (vastgoedobject.getAfgekochteerfpacht() != null) {
                    existingVastgoedobject.setAfgekochteerfpacht(vastgoedobject.getAfgekochteerfpacht());
                }
                if (vastgoedobject.getAfgesprokenconditiescore() != null) {
                    existingVastgoedobject.setAfgesprokenconditiescore(vastgoedobject.getAfgesprokenconditiescore());
                }
                if (vastgoedobject.getAfkoopwaarde() != null) {
                    existingVastgoedobject.setAfkoopwaarde(vastgoedobject.getAfkoopwaarde());
                }
                if (vastgoedobject.getAsbestrapportageaanwezig() != null) {
                    existingVastgoedobject.setAsbestrapportageaanwezig(vastgoedobject.getAsbestrapportageaanwezig());
                }
                if (vastgoedobject.getBedragaankoop() != null) {
                    existingVastgoedobject.setBedragaankoop(vastgoedobject.getBedragaankoop());
                }
                if (vastgoedobject.getBestemmingsplan() != null) {
                    existingVastgoedobject.setBestemmingsplan(vastgoedobject.getBestemmingsplan());
                }
                if (vastgoedobject.getBoekwaarde() != null) {
                    existingVastgoedobject.setBoekwaarde(vastgoedobject.getBoekwaarde());
                }
                if (vastgoedobject.getBouwjaar() != null) {
                    existingVastgoedobject.setBouwjaar(vastgoedobject.getBouwjaar());
                }
                if (vastgoedobject.getBouwwerk() != null) {
                    existingVastgoedobject.setBouwwerk(vastgoedobject.getBouwwerk());
                }
                if (vastgoedobject.getBovenliggendniveau() != null) {
                    existingVastgoedobject.setBovenliggendniveau(vastgoedobject.getBovenliggendniveau());
                }
                if (vastgoedobject.getBovenliggendniveaucode() != null) {
                    existingVastgoedobject.setBovenliggendniveaucode(vastgoedobject.getBovenliggendniveaucode());
                }
                if (vastgoedobject.getBrutovloeroppervlakte() != null) {
                    existingVastgoedobject.setBrutovloeroppervlakte(vastgoedobject.getBrutovloeroppervlakte());
                }
                if (vastgoedobject.getCo2uitstoot() != null) {
                    existingVastgoedobject.setCo2uitstoot(vastgoedobject.getCo2uitstoot());
                }
                if (vastgoedobject.getConditiescore() != null) {
                    existingVastgoedobject.setConditiescore(vastgoedobject.getConditiescore());
                }
                if (vastgoedobject.getDatumafstoten() != null) {
                    existingVastgoedobject.setDatumafstoten(vastgoedobject.getDatumafstoten());
                }
                if (vastgoedobject.getDatumberekeningoppervlak() != null) {
                    existingVastgoedobject.setDatumberekeningoppervlak(vastgoedobject.getDatumberekeningoppervlak());
                }
                if (vastgoedobject.getDatumeigendom() != null) {
                    existingVastgoedobject.setDatumeigendom(vastgoedobject.getDatumeigendom());
                }
                if (vastgoedobject.getDatumverkoop() != null) {
                    existingVastgoedobject.setDatumverkoop(vastgoedobject.getDatumverkoop());
                }
                if (vastgoedobject.getDeelportefeuille() != null) {
                    existingVastgoedobject.setDeelportefeuille(vastgoedobject.getDeelportefeuille());
                }
                if (vastgoedobject.getEnergiekosten() != null) {
                    existingVastgoedobject.setEnergiekosten(vastgoedobject.getEnergiekosten());
                }
                if (vastgoedobject.getEnergielabel() != null) {
                    existingVastgoedobject.setEnergielabel(vastgoedobject.getEnergielabel());
                }
                if (vastgoedobject.getEnergieverbruik() != null) {
                    existingVastgoedobject.setEnergieverbruik(vastgoedobject.getEnergieverbruik());
                }
                if (vastgoedobject.getFiscalewaarde() != null) {
                    existingVastgoedobject.setFiscalewaarde(vastgoedobject.getFiscalewaarde());
                }
                if (vastgoedobject.getFoto() != null) {
                    existingVastgoedobject.setFoto(vastgoedobject.getFoto());
                }
                if (vastgoedobject.getGearchiveerd() != null) {
                    existingVastgoedobject.setGearchiveerd(vastgoedobject.getGearchiveerd());
                }
                if (vastgoedobject.getHerbouwwaarde() != null) {
                    existingVastgoedobject.setHerbouwwaarde(vastgoedobject.getHerbouwwaarde());
                }
                if (vastgoedobject.getHoofdstuk() != null) {
                    existingVastgoedobject.setHoofdstuk(vastgoedobject.getHoofdstuk());
                }
                if (vastgoedobject.getIdentificatie() != null) {
                    existingVastgoedobject.setIdentificatie(vastgoedobject.getIdentificatie());
                }
                if (vastgoedobject.getJaarlaatsterenovatie() != null) {
                    existingVastgoedobject.setJaarlaatsterenovatie(vastgoedobject.getJaarlaatsterenovatie());
                }
                if (vastgoedobject.getLocatie() != null) {
                    existingVastgoedobject.setLocatie(vastgoedobject.getLocatie());
                }
                if (vastgoedobject.getMarktwaarde() != null) {
                    existingVastgoedobject.setMarktwaarde(vastgoedobject.getMarktwaarde());
                }
                if (vastgoedobject.getMonument() != null) {
                    existingVastgoedobject.setMonument(vastgoedobject.getMonument());
                }
                if (vastgoedobject.getNaam() != null) {
                    existingVastgoedobject.setNaam(vastgoedobject.getNaam());
                }
                if (vastgoedobject.getEobjectstatus() != null) {
                    existingVastgoedobject.setEobjectstatus(vastgoedobject.getEobjectstatus());
                }
                if (vastgoedobject.getEobjectstatuscode() != null) {
                    existingVastgoedobject.setEobjectstatuscode(vastgoedobject.getEobjectstatuscode());
                }
                if (vastgoedobject.getEobjecttype() != null) {
                    existingVastgoedobject.setEobjecttype(vastgoedobject.getEobjecttype());
                }
                if (vastgoedobject.getEobjecttypecode() != null) {
                    existingVastgoedobject.setEobjecttypecode(vastgoedobject.getEobjecttypecode());
                }
                if (vastgoedobject.getOmschrijving() != null) {
                    existingVastgoedobject.setOmschrijving(vastgoedobject.getOmschrijving());
                }
                if (vastgoedobject.getOnderhoudscategorie() != null) {
                    existingVastgoedobject.setOnderhoudscategorie(vastgoedobject.getOnderhoudscategorie());
                }
                if (vastgoedobject.getOppervlaktekantoor() != null) {
                    existingVastgoedobject.setOppervlaktekantoor(vastgoedobject.getOppervlaktekantoor());
                }
                if (vastgoedobject.getPortefeuille() != null) {
                    existingVastgoedobject.setPortefeuille(vastgoedobject.getPortefeuille());
                }
                if (vastgoedobject.getPortefeuillecode() != null) {
                    existingVastgoedobject.setPortefeuillecode(vastgoedobject.getPortefeuillecode());
                }
                if (vastgoedobject.getProvincie() != null) {
                    existingVastgoedobject.setProvincie(vastgoedobject.getProvincie());
                }
                if (vastgoedobject.getToelichting() != null) {
                    existingVastgoedobject.setToelichting(vastgoedobject.getToelichting());
                }
                if (vastgoedobject.getVerhuurbaarvloeroppervlak() != null) {
                    existingVastgoedobject.setVerhuurbaarvloeroppervlak(vastgoedobject.getVerhuurbaarvloeroppervlak());
                }
                if (vastgoedobject.getVerkoopbaarheid() != null) {
                    existingVastgoedobject.setVerkoopbaarheid(vastgoedobject.getVerkoopbaarheid());
                }
                if (vastgoedobject.getVerkoopbedrag() != null) {
                    existingVastgoedobject.setVerkoopbedrag(vastgoedobject.getVerkoopbedrag());
                }
                if (vastgoedobject.getVerzekerdewaarde() != null) {
                    existingVastgoedobject.setVerzekerdewaarde(vastgoedobject.getVerzekerdewaarde());
                }
                if (vastgoedobject.getWaardegrond() != null) {
                    existingVastgoedobject.setWaardegrond(vastgoedobject.getWaardegrond());
                }
                if (vastgoedobject.getWaardeopstal() != null) {
                    existingVastgoedobject.setWaardeopstal(vastgoedobject.getWaardeopstal());
                }
                if (vastgoedobject.getWijk() != null) {
                    existingVastgoedobject.setWijk(vastgoedobject.getWijk());
                }
                if (vastgoedobject.getWozwaarde() != null) {
                    existingVastgoedobject.setWozwaarde(vastgoedobject.getWozwaarde());
                }

                return existingVastgoedobject;
            })
            .map(vastgoedobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vastgoedobject.getId().toString())
        );
    }

    /**
     * {@code GET  /vastgoedobjects} : get all the vastgoedobjects.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vastgoedobjects in body.
     */
    @GetMapping("")
    public List<Vastgoedobject> getAllVastgoedobjects(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftverblijfsobject-is-null".equals(filter)) {
            log.debug("REST request to get all Vastgoedobjects where heeftVerblijfsobject is null");
            return StreamSupport.stream(vastgoedobjectRepository.findAll().spliterator(), false)
                .filter(vastgoedobject -> vastgoedobject.getHeeftVerblijfsobject() == null)
                .toList();
        }

        if ("heeftpand-is-null".equals(filter)) {
            log.debug("REST request to get all Vastgoedobjects where heeftPand is null");
            return StreamSupport.stream(vastgoedobjectRepository.findAll().spliterator(), false)
                .filter(vastgoedobject -> vastgoedobject.getHeeftPand() == null)
                .toList();
        }
        log.debug("REST request to get all Vastgoedobjects");
        return vastgoedobjectRepository.findAll();
    }

    /**
     * {@code GET  /vastgoedobjects/:id} : get the "id" vastgoedobject.
     *
     * @param id the id of the vastgoedobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vastgoedobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vastgoedobject> getVastgoedobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Vastgoedobject : {}", id);
        Optional<Vastgoedobject> vastgoedobject = vastgoedobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vastgoedobject);
    }

    /**
     * {@code DELETE  /vastgoedobjects/:id} : delete the "id" vastgoedobject.
     *
     * @param id the id of the vastgoedobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVastgoedobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vastgoedobject : {}", id);
        vastgoedobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
