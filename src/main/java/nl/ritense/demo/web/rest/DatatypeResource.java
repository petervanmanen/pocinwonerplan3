package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Datatype;
import nl.ritense.demo.repository.DatatypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Datatype}.
 */
@RestController
@RequestMapping("/api/datatypes")
@Transactional
public class DatatypeResource {

    private final Logger log = LoggerFactory.getLogger(DatatypeResource.class);

    private static final String ENTITY_NAME = "datatype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatatypeRepository datatypeRepository;

    public DatatypeResource(DatatypeRepository datatypeRepository) {
        this.datatypeRepository = datatypeRepository;
    }

    /**
     * {@code POST  /datatypes} : Create a new datatype.
     *
     * @param datatype the datatype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datatype, or with status {@code 400 (Bad Request)} if the datatype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Datatype> createDatatype(@RequestBody Datatype datatype) throws URISyntaxException {
        log.debug("REST request to save Datatype : {}", datatype);
        if (datatype.getId() != null) {
            throw new BadRequestAlertException("A new datatype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        datatype = datatypeRepository.save(datatype);
        return ResponseEntity.created(new URI("/api/datatypes/" + datatype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, datatype.getId()))
            .body(datatype);
    }

    /**
     * {@code PUT  /datatypes/:id} : Updates an existing datatype.
     *
     * @param id the id of the datatype to save.
     * @param datatype the datatype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datatype,
     * or with status {@code 400 (Bad Request)} if the datatype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datatype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Datatype> updateDatatype(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Datatype datatype
    ) throws URISyntaxException {
        log.debug("REST request to update Datatype : {}, {}", id, datatype);
        if (datatype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datatype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datatypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        datatype = datatypeRepository.save(datatype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, datatype.getId()))
            .body(datatype);
    }

    /**
     * {@code PATCH  /datatypes/:id} : Partial updates given fields of an existing datatype, field will ignore if it is null
     *
     * @param id the id of the datatype to save.
     * @param datatype the datatype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datatype,
     * or with status {@code 400 (Bad Request)} if the datatype is not valid,
     * or with status {@code 404 (Not Found)} if the datatype is not found,
     * or with status {@code 500 (Internal Server Error)} if the datatype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Datatype> partialUpdateDatatype(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Datatype datatype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Datatype partially : {}, {}", id, datatype);
        if (datatype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datatype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datatypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Datatype> result = datatypeRepository
            .findById(datatype.getId())
            .map(existingDatatype -> {
                if (datatype.getDatumopname() != null) {
                    existingDatatype.setDatumopname(datatype.getDatumopname());
                }
                if (datatype.getDefinitie() != null) {
                    existingDatatype.setDefinitie(datatype.getDefinitie());
                }
                if (datatype.getDomein() != null) {
                    existingDatatype.setDomein(datatype.getDomein());
                }
                if (datatype.getEaguid() != null) {
                    existingDatatype.setEaguid(datatype.getEaguid());
                }
                if (datatype.getHerkomst() != null) {
                    existingDatatype.setHerkomst(datatype.getHerkomst());
                }
                if (datatype.getKardinaliteit() != null) {
                    existingDatatype.setKardinaliteit(datatype.getKardinaliteit());
                }
                if (datatype.getLengte() != null) {
                    existingDatatype.setLengte(datatype.getLengte());
                }
                if (datatype.getNaam() != null) {
                    existingDatatype.setNaam(datatype.getNaam());
                }
                if (datatype.getPatroon() != null) {
                    existingDatatype.setPatroon(datatype.getPatroon());
                }
                if (datatype.getToelichting() != null) {
                    existingDatatype.setToelichting(datatype.getToelichting());
                }

                return existingDatatype;
            })
            .map(datatypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, datatype.getId())
        );
    }

    /**
     * {@code GET  /datatypes} : get all the datatypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datatypes in body.
     */
    @GetMapping("")
    public List<Datatype> getAllDatatypes() {
        log.debug("REST request to get all Datatypes");
        return datatypeRepository.findAll();
    }

    /**
     * {@code GET  /datatypes/:id} : get the "id" datatype.
     *
     * @param id the id of the datatype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datatype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Datatype> getDatatype(@PathVariable("id") String id) {
        log.debug("REST request to get Datatype : {}", id);
        Optional<Datatype> datatype = datatypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(datatype);
    }

    /**
     * {@code DELETE  /datatypes/:id} : delete the "id" datatype.
     *
     * @param id the id of the datatype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDatatype(@PathVariable("id") String id) {
        log.debug("REST request to delete Datatype : {}", id);
        datatypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
