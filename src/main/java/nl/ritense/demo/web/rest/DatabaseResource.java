package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Database;
import nl.ritense.demo.repository.DatabaseRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Database}.
 */
@RestController
@RequestMapping("/api/databases")
@Transactional
public class DatabaseResource {

    private final Logger log = LoggerFactory.getLogger(DatabaseResource.class);

    private static final String ENTITY_NAME = "database";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatabaseRepository databaseRepository;

    public DatabaseResource(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    /**
     * {@code POST  /databases} : Create a new database.
     *
     * @param database the database to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new database, or with status {@code 400 (Bad Request)} if the database has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Database> createDatabase(@RequestBody Database database) throws URISyntaxException {
        log.debug("REST request to save Database : {}", database);
        if (database.getId() != null) {
            throw new BadRequestAlertException("A new database cannot already have an ID", ENTITY_NAME, "idexists");
        }
        database = databaseRepository.save(database);
        return ResponseEntity.created(new URI("/api/databases/" + database.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, database.getId().toString()))
            .body(database);
    }

    /**
     * {@code PUT  /databases/:id} : Updates an existing database.
     *
     * @param id the id of the database to save.
     * @param database the database to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated database,
     * or with status {@code 400 (Bad Request)} if the database is not valid,
     * or with status {@code 500 (Internal Server Error)} if the database couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Database> updateDatabase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Database database
    ) throws URISyntaxException {
        log.debug("REST request to update Database : {}, {}", id, database);
        if (database.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, database.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!databaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        database = databaseRepository.save(database);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, database.getId().toString()))
            .body(database);
    }

    /**
     * {@code PATCH  /databases/:id} : Partial updates given fields of an existing database, field will ignore if it is null
     *
     * @param id the id of the database to save.
     * @param database the database to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated database,
     * or with status {@code 400 (Bad Request)} if the database is not valid,
     * or with status {@code 404 (Not Found)} if the database is not found,
     * or with status {@code 500 (Internal Server Error)} if the database couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Database> partialUpdateDatabase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Database database
    ) throws URISyntaxException {
        log.debug("REST request to partial update Database partially : {}, {}", id, database);
        if (database.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, database.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!databaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Database> result = databaseRepository
            .findById(database.getId())
            .map(existingDatabase -> {
                if (database.getArchitectuur() != null) {
                    existingDatabase.setArchitectuur(database.getArchitectuur());
                }
                if (database.getDatabase() != null) {
                    existingDatabase.setDatabase(database.getDatabase());
                }
                if (database.getDatabaseversie() != null) {
                    existingDatabase.setDatabaseversie(database.getDatabaseversie());
                }
                if (database.getDbms() != null) {
                    existingDatabase.setDbms(database.getDbms());
                }
                if (database.getOmschrijving() != null) {
                    existingDatabase.setOmschrijving(database.getOmschrijving());
                }
                if (database.getOtap() != null) {
                    existingDatabase.setOtap(database.getOtap());
                }
                if (database.getVlan() != null) {
                    existingDatabase.setVlan(database.getVlan());
                }

                return existingDatabase;
            })
            .map(databaseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, database.getId().toString())
        );
    }

    /**
     * {@code GET  /databases} : get all the databases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of databases in body.
     */
    @GetMapping("")
    public List<Database> getAllDatabases() {
        log.debug("REST request to get all Databases");
        return databaseRepository.findAll();
    }

    /**
     * {@code GET  /databases/:id} : get the "id" database.
     *
     * @param id the id of the database to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the database, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Database> getDatabase(@PathVariable("id") Long id) {
        log.debug("REST request to get Database : {}", id);
        Optional<Database> database = databaseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(database);
    }

    /**
     * {@code DELETE  /databases/:id} : delete the "id" database.
     *
     * @param id the id of the database to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDatabase(@PathVariable("id") Long id) {
        log.debug("REST request to delete Database : {}", id);
        databaseRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
