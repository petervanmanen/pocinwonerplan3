package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Contactpersoonrol;
import nl.ritense.demo.repository.ContactpersoonrolRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Contactpersoonrol}.
 */
@RestController
@RequestMapping("/api/contactpersoonrols")
@Transactional
public class ContactpersoonrolResource {

    private final Logger log = LoggerFactory.getLogger(ContactpersoonrolResource.class);

    private static final String ENTITY_NAME = "contactpersoonrol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactpersoonrolRepository contactpersoonrolRepository;

    public ContactpersoonrolResource(ContactpersoonrolRepository contactpersoonrolRepository) {
        this.contactpersoonrolRepository = contactpersoonrolRepository;
    }

    /**
     * {@code POST  /contactpersoonrols} : Create a new contactpersoonrol.
     *
     * @param contactpersoonrol the contactpersoonrol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactpersoonrol, or with status {@code 400 (Bad Request)} if the contactpersoonrol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Contactpersoonrol> createContactpersoonrol(@Valid @RequestBody Contactpersoonrol contactpersoonrol)
        throws URISyntaxException {
        log.debug("REST request to save Contactpersoonrol : {}", contactpersoonrol);
        if (contactpersoonrol.getId() != null) {
            throw new BadRequestAlertException("A new contactpersoonrol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        contactpersoonrol = contactpersoonrolRepository.save(contactpersoonrol);
        return ResponseEntity.created(new URI("/api/contactpersoonrols/" + contactpersoonrol.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, contactpersoonrol.getId().toString()))
            .body(contactpersoonrol);
    }

    /**
     * {@code PUT  /contactpersoonrols/:id} : Updates an existing contactpersoonrol.
     *
     * @param id the id of the contactpersoonrol to save.
     * @param contactpersoonrol the contactpersoonrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactpersoonrol,
     * or with status {@code 400 (Bad Request)} if the contactpersoonrol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactpersoonrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Contactpersoonrol> updateContactpersoonrol(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Contactpersoonrol contactpersoonrol
    ) throws URISyntaxException {
        log.debug("REST request to update Contactpersoonrol : {}, {}", id, contactpersoonrol);
        if (contactpersoonrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactpersoonrol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactpersoonrolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contactpersoonrol = contactpersoonrolRepository.save(contactpersoonrol);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contactpersoonrol.getId().toString()))
            .body(contactpersoonrol);
    }

    /**
     * {@code PATCH  /contactpersoonrols/:id} : Partial updates given fields of an existing contactpersoonrol, field will ignore if it is null
     *
     * @param id the id of the contactpersoonrol to save.
     * @param contactpersoonrol the contactpersoonrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactpersoonrol,
     * or with status {@code 400 (Bad Request)} if the contactpersoonrol is not valid,
     * or with status {@code 404 (Not Found)} if the contactpersoonrol is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactpersoonrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Contactpersoonrol> partialUpdateContactpersoonrol(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Contactpersoonrol contactpersoonrol
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contactpersoonrol partially : {}, {}", id, contactpersoonrol);
        if (contactpersoonrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactpersoonrol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactpersoonrolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Contactpersoonrol> result = contactpersoonrolRepository
            .findById(contactpersoonrol.getId())
            .map(existingContactpersoonrol -> {
                if (contactpersoonrol.getContactpersoonemailadres() != null) {
                    existingContactpersoonrol.setContactpersoonemailadres(contactpersoonrol.getContactpersoonemailadres());
                }
                if (contactpersoonrol.getContactpersoonfunctie() != null) {
                    existingContactpersoonrol.setContactpersoonfunctie(contactpersoonrol.getContactpersoonfunctie());
                }
                if (contactpersoonrol.getContactpersoonnaam() != null) {
                    existingContactpersoonrol.setContactpersoonnaam(contactpersoonrol.getContactpersoonnaam());
                }
                if (contactpersoonrol.getContactpersoontelefoonnummer() != null) {
                    existingContactpersoonrol.setContactpersoontelefoonnummer(contactpersoonrol.getContactpersoontelefoonnummer());
                }

                return existingContactpersoonrol;
            })
            .map(contactpersoonrolRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contactpersoonrol.getId().toString())
        );
    }

    /**
     * {@code GET  /contactpersoonrols} : get all the contactpersoonrols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactpersoonrols in body.
     */
    @GetMapping("")
    public List<Contactpersoonrol> getAllContactpersoonrols() {
        log.debug("REST request to get all Contactpersoonrols");
        return contactpersoonrolRepository.findAll();
    }

    /**
     * {@code GET  /contactpersoonrols/:id} : get the "id" contactpersoonrol.
     *
     * @param id the id of the contactpersoonrol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactpersoonrol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contactpersoonrol> getContactpersoonrol(@PathVariable("id") Long id) {
        log.debug("REST request to get Contactpersoonrol : {}", id);
        Optional<Contactpersoonrol> contactpersoonrol = contactpersoonrolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contactpersoonrol);
    }

    /**
     * {@code DELETE  /contactpersoonrols/:id} : delete the "id" contactpersoonrol.
     *
     * @param id the id of the contactpersoonrol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactpersoonrol(@PathVariable("id") Long id) {
        log.debug("REST request to delete Contactpersoonrol : {}", id);
        contactpersoonrolRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
