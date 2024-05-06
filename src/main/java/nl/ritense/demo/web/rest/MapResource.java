package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Map;
import nl.ritense.demo.repository.MapRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Map}.
 */
@RestController
@RequestMapping("/api/maps")
@Transactional
public class MapResource {

    private final Logger log = LoggerFactory.getLogger(MapResource.class);

    private static final String ENTITY_NAME = "map";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MapRepository mapRepository;

    public MapResource(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    /**
     * {@code POST  /maps} : Create a new map.
     *
     * @param map the map to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new map, or with status {@code 400 (Bad Request)} if the map has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Map> createMap(@RequestBody Map map) throws URISyntaxException {
        log.debug("REST request to save Map : {}", map);
        if (map.getId() != null) {
            throw new BadRequestAlertException("A new map cannot already have an ID", ENTITY_NAME, "idexists");
        }
        map = mapRepository.save(map);
        return ResponseEntity.created(new URI("/api/maps/" + map.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, map.getId().toString()))
            .body(map);
    }

    /**
     * {@code GET  /maps} : get all the maps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maps in body.
     */
    @GetMapping("")
    public List<Map> getAllMaps() {
        log.debug("REST request to get all Maps");
        return mapRepository.findAll();
    }

    /**
     * {@code GET  /maps/:id} : get the "id" map.
     *
     * @param id the id of the map to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the map, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map> getMap(@PathVariable("id") Long id) {
        log.debug("REST request to get Map : {}", id);
        Optional<Map> map = mapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(map);
    }

    /**
     * {@code DELETE  /maps/:id} : delete the "id" map.
     *
     * @param id the id of the map to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable("id") Long id) {
        log.debug("REST request to delete Map : {}", id);
        mapRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
