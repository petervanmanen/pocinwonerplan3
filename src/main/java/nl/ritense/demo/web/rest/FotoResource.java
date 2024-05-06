package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Foto;
import nl.ritense.demo.repository.FotoRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Foto}.
 */
@RestController
@RequestMapping("/api/fotos")
@Transactional
public class FotoResource {

    private final Logger log = LoggerFactory.getLogger(FotoResource.class);

    private static final String ENTITY_NAME = "foto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoRepository fotoRepository;

    public FotoResource(FotoRepository fotoRepository) {
        this.fotoRepository = fotoRepository;
    }

    /**
     * {@code POST  /fotos} : Create a new foto.
     *
     * @param foto the foto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foto, or with status {@code 400 (Bad Request)} if the foto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Foto> createFoto(@RequestBody Foto foto) throws URISyntaxException {
        log.debug("REST request to save Foto : {}", foto);
        if (foto.getId() != null) {
            throw new BadRequestAlertException("A new foto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        foto = fotoRepository.save(foto);
        return ResponseEntity.created(new URI("/api/fotos/" + foto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, foto.getId().toString()))
            .body(foto);
    }

    /**
     * {@code PUT  /fotos/:id} : Updates an existing foto.
     *
     * @param id the id of the foto to save.
     * @param foto the foto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foto,
     * or with status {@code 400 (Bad Request)} if the foto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Foto> updateFoto(@PathVariable(value = "id", required = false) final Long id, @RequestBody Foto foto)
        throws URISyntaxException {
        log.debug("REST request to update Foto : {}, {}", id, foto);
        if (foto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        foto = fotoRepository.save(foto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, foto.getId().toString()))
            .body(foto);
    }

    /**
     * {@code PATCH  /fotos/:id} : Partial updates given fields of an existing foto, field will ignore if it is null
     *
     * @param id the id of the foto to save.
     * @param foto the foto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foto,
     * or with status {@code 400 (Bad Request)} if the foto is not valid,
     * or with status {@code 404 (Not Found)} if the foto is not found,
     * or with status {@code 500 (Internal Server Error)} if the foto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Foto> partialUpdateFoto(@PathVariable(value = "id", required = false) final Long id, @RequestBody Foto foto)
        throws URISyntaxException {
        log.debug("REST request to partial update Foto partially : {}, {}", id, foto);
        if (foto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Foto> result = fotoRepository
            .findById(foto.getId())
            .map(existingFoto -> {
                if (foto.getBestandsgrootte() != null) {
                    existingFoto.setBestandsgrootte(foto.getBestandsgrootte());
                }
                if (foto.getBestandsnaam() != null) {
                    existingFoto.setBestandsnaam(foto.getBestandsnaam());
                }
                if (foto.getBestandstype() != null) {
                    existingFoto.setBestandstype(foto.getBestandstype());
                }
                if (foto.getDatumtijd() != null) {
                    existingFoto.setDatumtijd(foto.getDatumtijd());
                }
                if (foto.getLocatie() != null) {
                    existingFoto.setLocatie(foto.getLocatie());
                }
                if (foto.getPixelsx() != null) {
                    existingFoto.setPixelsx(foto.getPixelsx());
                }
                if (foto.getPixelsy() != null) {
                    existingFoto.setPixelsy(foto.getPixelsy());
                }

                return existingFoto;
            })
            .map(fotoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, foto.getId().toString())
        );
    }

    /**
     * {@code GET  /fotos} : get all the fotos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotos in body.
     */
    @GetMapping("")
    public List<Foto> getAllFotos() {
        log.debug("REST request to get all Fotos");
        return fotoRepository.findAll();
    }

    /**
     * {@code GET  /fotos/:id} : get the "id" foto.
     *
     * @param id the id of the foto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Foto> getFoto(@PathVariable("id") Long id) {
        log.debug("REST request to get Foto : {}", id);
        Optional<Foto> foto = fotoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(foto);
    }

    /**
     * {@code DELETE  /fotos/:id} : delete the "id" foto.
     *
     * @param id the id of the foto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoto(@PathVariable("id") Long id) {
        log.debug("REST request to delete Foto : {}", id);
        fotoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
