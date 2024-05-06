package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Server;
import nl.ritense.demo.repository.ServerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Server}.
 */
@RestController
@RequestMapping("/api/servers")
@Transactional
public class ServerResource {

    private final Logger log = LoggerFactory.getLogger(ServerResource.class);

    private static final String ENTITY_NAME = "server";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServerRepository serverRepository;

    public ServerResource(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    /**
     * {@code POST  /servers} : Create a new server.
     *
     * @param server the server to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new server, or with status {@code 400 (Bad Request)} if the server has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Server> createServer(@Valid @RequestBody Server server) throws URISyntaxException {
        log.debug("REST request to save Server : {}", server);
        if (server.getId() != null) {
            throw new BadRequestAlertException("A new server cannot already have an ID", ENTITY_NAME, "idexists");
        }
        server = serverRepository.save(server);
        return ResponseEntity.created(new URI("/api/servers/" + server.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, server.getId().toString()))
            .body(server);
    }

    /**
     * {@code PUT  /servers/:id} : Updates an existing server.
     *
     * @param id the id of the server to save.
     * @param server the server to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated server,
     * or with status {@code 400 (Bad Request)} if the server is not valid,
     * or with status {@code 500 (Internal Server Error)} if the server couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Server> updateServer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Server server
    ) throws URISyntaxException {
        log.debug("REST request to update Server : {}, {}", id, server);
        if (server.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, server.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        server = serverRepository.save(server);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, server.getId().toString()))
            .body(server);
    }

    /**
     * {@code PATCH  /servers/:id} : Partial updates given fields of an existing server, field will ignore if it is null
     *
     * @param id the id of the server to save.
     * @param server the server to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated server,
     * or with status {@code 400 (Bad Request)} if the server is not valid,
     * or with status {@code 404 (Not Found)} if the server is not found,
     * or with status {@code 500 (Internal Server Error)} if the server couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Server> partialUpdateServer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Server server
    ) throws URISyntaxException {
        log.debug("REST request to partial update Server partially : {}, {}", id, server);
        if (server.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, server.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Server> result = serverRepository
            .findById(server.getId())
            .map(existingServer -> {
                if (server.getActief() != null) {
                    existingServer.setActief(server.getActief());
                }
                if (server.getIpadres() != null) {
                    existingServer.setIpadres(server.getIpadres());
                }
                if (server.getLocatie() != null) {
                    existingServer.setLocatie(server.getLocatie());
                }
                if (server.getOrganisatie() != null) {
                    existingServer.setOrganisatie(server.getOrganisatie());
                }
                if (server.getSerienummer() != null) {
                    existingServer.setSerienummer(server.getSerienummer());
                }
                if (server.getServerid() != null) {
                    existingServer.setServerid(server.getServerid());
                }
                if (server.getServertype() != null) {
                    existingServer.setServertype(server.getServertype());
                }
                if (server.getVlan() != null) {
                    existingServer.setVlan(server.getVlan());
                }

                return existingServer;
            })
            .map(serverRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, server.getId().toString())
        );
    }

    /**
     * {@code GET  /servers} : get all the servers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servers in body.
     */
    @GetMapping("")
    public List<Server> getAllServers() {
        log.debug("REST request to get all Servers");
        return serverRepository.findAll();
    }

    /**
     * {@code GET  /servers/:id} : get the "id" server.
     *
     * @param id the id of the server to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the server, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Server> getServer(@PathVariable("id") Long id) {
        log.debug("REST request to get Server : {}", id);
        Optional<Server> server = serverRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(server);
    }

    /**
     * {@code DELETE  /servers/:id} : delete the "id" server.
     *
     * @param id the id of the server to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Server : {}", id);
        serverRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
