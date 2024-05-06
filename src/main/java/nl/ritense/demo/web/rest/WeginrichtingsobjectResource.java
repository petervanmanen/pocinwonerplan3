package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Weginrichtingsobject;
import nl.ritense.demo.repository.WeginrichtingsobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Weginrichtingsobject}.
 */
@RestController
@RequestMapping("/api/weginrichtingsobjects")
@Transactional
public class WeginrichtingsobjectResource {

    private final Logger log = LoggerFactory.getLogger(WeginrichtingsobjectResource.class);

    private static final String ENTITY_NAME = "weginrichtingsobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeginrichtingsobjectRepository weginrichtingsobjectRepository;

    public WeginrichtingsobjectResource(WeginrichtingsobjectRepository weginrichtingsobjectRepository) {
        this.weginrichtingsobjectRepository = weginrichtingsobjectRepository;
    }

    /**
     * {@code POST  /weginrichtingsobjects} : Create a new weginrichtingsobject.
     *
     * @param weginrichtingsobject the weginrichtingsobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weginrichtingsobject, or with status {@code 400 (Bad Request)} if the weginrichtingsobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Weginrichtingsobject> createWeginrichtingsobject(@RequestBody Weginrichtingsobject weginrichtingsobject)
        throws URISyntaxException {
        log.debug("REST request to save Weginrichtingsobject : {}", weginrichtingsobject);
        if (weginrichtingsobject.getId() != null) {
            throw new BadRequestAlertException("A new weginrichtingsobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        weginrichtingsobject = weginrichtingsobjectRepository.save(weginrichtingsobject);
        return ResponseEntity.created(new URI("/api/weginrichtingsobjects/" + weginrichtingsobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, weginrichtingsobject.getId().toString()))
            .body(weginrichtingsobject);
    }

    /**
     * {@code PUT  /weginrichtingsobjects/:id} : Updates an existing weginrichtingsobject.
     *
     * @param id the id of the weginrichtingsobject to save.
     * @param weginrichtingsobject the weginrichtingsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weginrichtingsobject,
     * or with status {@code 400 (Bad Request)} if the weginrichtingsobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weginrichtingsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Weginrichtingsobject> updateWeginrichtingsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Weginrichtingsobject weginrichtingsobject
    ) throws URISyntaxException {
        log.debug("REST request to update Weginrichtingsobject : {}, {}", id, weginrichtingsobject);
        if (weginrichtingsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weginrichtingsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weginrichtingsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        weginrichtingsobject = weginrichtingsobjectRepository.save(weginrichtingsobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, weginrichtingsobject.getId().toString()))
            .body(weginrichtingsobject);
    }

    /**
     * {@code PATCH  /weginrichtingsobjects/:id} : Partial updates given fields of an existing weginrichtingsobject, field will ignore if it is null
     *
     * @param id the id of the weginrichtingsobject to save.
     * @param weginrichtingsobject the weginrichtingsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weginrichtingsobject,
     * or with status {@code 400 (Bad Request)} if the weginrichtingsobject is not valid,
     * or with status {@code 404 (Not Found)} if the weginrichtingsobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the weginrichtingsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Weginrichtingsobject> partialUpdateWeginrichtingsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Weginrichtingsobject weginrichtingsobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Weginrichtingsobject partially : {}, {}", id, weginrichtingsobject);
        if (weginrichtingsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weginrichtingsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weginrichtingsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Weginrichtingsobject> result = weginrichtingsobjectRepository
            .findById(weginrichtingsobject.getId())
            .map(existingWeginrichtingsobject -> {
                if (weginrichtingsobject.getAanleghoogte() != null) {
                    existingWeginrichtingsobject.setAanleghoogte(weginrichtingsobject.getAanleghoogte());
                }
                if (weginrichtingsobject.getBreedte() != null) {
                    existingWeginrichtingsobject.setBreedte(weginrichtingsobject.getBreedte());
                }
                if (weginrichtingsobject.getHoogte() != null) {
                    existingWeginrichtingsobject.setHoogte(weginrichtingsobject.getHoogte());
                }
                if (weginrichtingsobject.getJaarconserveren() != null) {
                    existingWeginrichtingsobject.setJaarconserveren(weginrichtingsobject.getJaarconserveren());
                }
                if (weginrichtingsobject.getJaaronderhouduitgevoerd() != null) {
                    existingWeginrichtingsobject.setJaaronderhouduitgevoerd(weginrichtingsobject.getJaaronderhouduitgevoerd());
                }
                if (weginrichtingsobject.getKwaliteitsniveauactueel() != null) {
                    existingWeginrichtingsobject.setKwaliteitsniveauactueel(weginrichtingsobject.getKwaliteitsniveauactueel());
                }
                if (weginrichtingsobject.getKwaliteitsniveaugewenst() != null) {
                    existingWeginrichtingsobject.setKwaliteitsniveaugewenst(weginrichtingsobject.getKwaliteitsniveaugewenst());
                }
                if (weginrichtingsobject.getLengte() != null) {
                    existingWeginrichtingsobject.setLengte(weginrichtingsobject.getLengte());
                }
                if (weginrichtingsobject.getLeverancier() != null) {
                    existingWeginrichtingsobject.setLeverancier(weginrichtingsobject.getLeverancier());
                }
                if (weginrichtingsobject.getMateriaal() != null) {
                    existingWeginrichtingsobject.setMateriaal(weginrichtingsobject.getMateriaal());
                }
                if (weginrichtingsobject.getOppervlakte() != null) {
                    existingWeginrichtingsobject.setOppervlakte(weginrichtingsobject.getOppervlakte());
                }
                if (weginrichtingsobject.getWeginrichtingsobjectwegfunctie() != null) {
                    existingWeginrichtingsobject.setWeginrichtingsobjectwegfunctie(
                        weginrichtingsobject.getWeginrichtingsobjectwegfunctie()
                    );
                }

                return existingWeginrichtingsobject;
            })
            .map(weginrichtingsobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, weginrichtingsobject.getId().toString())
        );
    }

    /**
     * {@code GET  /weginrichtingsobjects} : get all the weginrichtingsobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weginrichtingsobjects in body.
     */
    @GetMapping("")
    public List<Weginrichtingsobject> getAllWeginrichtingsobjects() {
        log.debug("REST request to get all Weginrichtingsobjects");
        return weginrichtingsobjectRepository.findAll();
    }

    /**
     * {@code GET  /weginrichtingsobjects/:id} : get the "id" weginrichtingsobject.
     *
     * @param id the id of the weginrichtingsobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weginrichtingsobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Weginrichtingsobject> getWeginrichtingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Weginrichtingsobject : {}", id);
        Optional<Weginrichtingsobject> weginrichtingsobject = weginrichtingsobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(weginrichtingsobject);
    }

    /**
     * {@code DELETE  /weginrichtingsobjects/:id} : delete the "id" weginrichtingsobject.
     *
     * @param id the id of the weginrichtingsobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeginrichtingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Weginrichtingsobject : {}", id);
        weginrichtingsobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
