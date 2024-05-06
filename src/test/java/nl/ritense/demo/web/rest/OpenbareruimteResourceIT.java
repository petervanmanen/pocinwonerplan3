package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OpenbareruimteAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Openbareruimte;
import nl.ritense.demo.repository.OpenbareruimteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OpenbareruimteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpenbareruimteResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANG = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GECONSTATEERD = false;
    private static final Boolean UPDATED_GECONSTATEERD = true;

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_HUISNUMMERRANGEEVENENONEVENNUMMERS = "AAAAAAAAAA";
    private static final String UPDATED_HUISNUMMERRANGEEVENENONEVENNUMMERS = "BBBBBBBBBB";

    private static final String DEFAULT_HUISNUMMERRANGEEVENNUMMERS = "AAAAAAAAAA";
    private static final String UPDATED_HUISNUMMERRANGEEVENNUMMERS = "BBBBBBBBBB";

    private static final String DEFAULT_HUISNUMMERRANGEONEVENNUMMERS = "AAAAAAAAAA";
    private static final String UPDATED_HUISNUMMERRANGEONEVENNUMMERS = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INONDERZOEK = false;
    private static final Boolean UPDATED_INONDERZOEK = true;

    private static final String DEFAULT_LABELNAAM = "AAAAAAAAAA";
    private static final String UPDATED_LABELNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NAAMOPENBARERUIMTE = "AAAAAAAAAA";
    private static final String UPDATED_NAAMOPENBARERUIMTE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STRAATCODE = "AAAAAAAAAA";
    private static final String UPDATED_STRAATCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STRAATNAAM = "AAAAAAAAAA";
    private static final String UPDATED_STRAATNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEOPENBARERUIMTE = "AAAAAAAAAA";
    private static final String UPDATED_TYPEOPENBARERUIMTE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String DEFAULT_WEGSEGMENT = "AAAAAAAAAA";
    private static final String UPDATED_WEGSEGMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/openbareruimtes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpenbareruimteRepository openbareruimteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpenbareruimteMockMvc;

    private Openbareruimte openbareruimte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Openbareruimte createEntity(EntityManager em) {
        Openbareruimte openbareruimte = new Openbareruimte()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumingang(DEFAULT_DATUMINGANG)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .geometrie(DEFAULT_GEOMETRIE)
            .huisnummerrangeevenenonevennummers(DEFAULT_HUISNUMMERRANGEEVENENONEVENNUMMERS)
            .huisnummerrangeevennummers(DEFAULT_HUISNUMMERRANGEEVENNUMMERS)
            .huisnummerrangeonevennummers(DEFAULT_HUISNUMMERRANGEONEVENNUMMERS)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .labelnaam(DEFAULT_LABELNAAM)
            .naamopenbareruimte(DEFAULT_NAAMOPENBARERUIMTE)
            .status(DEFAULT_STATUS)
            .straatcode(DEFAULT_STRAATCODE)
            .straatnaam(DEFAULT_STRAATNAAM)
            .typeopenbareruimte(DEFAULT_TYPEOPENBARERUIMTE)
            .versie(DEFAULT_VERSIE)
            .wegsegment(DEFAULT_WEGSEGMENT);
        return openbareruimte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Openbareruimte createUpdatedEntity(EntityManager em) {
        Openbareruimte openbareruimte = new Openbareruimte()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .huisnummerrangeevenenonevennummers(UPDATED_HUISNUMMERRANGEEVENENONEVENNUMMERS)
            .huisnummerrangeevennummers(UPDATED_HUISNUMMERRANGEEVENNUMMERS)
            .huisnummerrangeonevennummers(UPDATED_HUISNUMMERRANGEONEVENNUMMERS)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .labelnaam(UPDATED_LABELNAAM)
            .naamopenbareruimte(UPDATED_NAAMOPENBARERUIMTE)
            .status(UPDATED_STATUS)
            .straatcode(UPDATED_STRAATCODE)
            .straatnaam(UPDATED_STRAATNAAM)
            .typeopenbareruimte(UPDATED_TYPEOPENBARERUIMTE)
            .versie(UPDATED_VERSIE)
            .wegsegment(UPDATED_WEGSEGMENT);
        return openbareruimte;
    }

    @BeforeEach
    public void initTest() {
        openbareruimte = createEntity(em);
    }

    @Test
    @Transactional
    void createOpenbareruimte() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Openbareruimte
        var returnedOpenbareruimte = om.readValue(
            restOpenbareruimteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(openbareruimte)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Openbareruimte.class
        );

        // Validate the Openbareruimte in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpenbareruimteUpdatableFieldsEquals(returnedOpenbareruimte, getPersistedOpenbareruimte(returnedOpenbareruimte));
    }

    @Test
    @Transactional
    void createOpenbareruimteWithExistingId() throws Exception {
        // Create the Openbareruimte with an existing ID
        openbareruimte.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpenbareruimteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(openbareruimte)))
            .andExpect(status().isBadRequest());

        // Validate the Openbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpenbareruimtes() throws Exception {
        // Initialize the database
        openbareruimteRepository.saveAndFlush(openbareruimte);

        // Get all the openbareruimteList
        restOpenbareruimteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(openbareruimte.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD.booleanValue())))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].huisnummerrangeevenenonevennummers").value(hasItem(DEFAULT_HUISNUMMERRANGEEVENENONEVENNUMMERS)))
            .andExpect(jsonPath("$.[*].huisnummerrangeevennummers").value(hasItem(DEFAULT_HUISNUMMERRANGEEVENNUMMERS)))
            .andExpect(jsonPath("$.[*].huisnummerrangeonevennummers").value(hasItem(DEFAULT_HUISNUMMERRANGEONEVENNUMMERS)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].labelnaam").value(hasItem(DEFAULT_LABELNAAM)))
            .andExpect(jsonPath("$.[*].naamopenbareruimte").value(hasItem(DEFAULT_NAAMOPENBARERUIMTE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].straatcode").value(hasItem(DEFAULT_STRAATCODE)))
            .andExpect(jsonPath("$.[*].straatnaam").value(hasItem(DEFAULT_STRAATNAAM)))
            .andExpect(jsonPath("$.[*].typeopenbareruimte").value(hasItem(DEFAULT_TYPEOPENBARERUIMTE)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)))
            .andExpect(jsonPath("$.[*].wegsegment").value(hasItem(DEFAULT_WEGSEGMENT)));
    }

    @Test
    @Transactional
    void getOpenbareruimte() throws Exception {
        // Initialize the database
        openbareruimteRepository.saveAndFlush(openbareruimte);

        // Get the openbareruimte
        restOpenbareruimteMockMvc
            .perform(get(ENTITY_API_URL_ID, openbareruimte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(openbareruimte.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD.booleanValue()))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.huisnummerrangeevenenonevennummers").value(DEFAULT_HUISNUMMERRANGEEVENENONEVENNUMMERS))
            .andExpect(jsonPath("$.huisnummerrangeevennummers").value(DEFAULT_HUISNUMMERRANGEEVENNUMMERS))
            .andExpect(jsonPath("$.huisnummerrangeonevennummers").value(DEFAULT_HUISNUMMERRANGEONEVENNUMMERS))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.labelnaam").value(DEFAULT_LABELNAAM))
            .andExpect(jsonPath("$.naamopenbareruimte").value(DEFAULT_NAAMOPENBARERUIMTE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.straatcode").value(DEFAULT_STRAATCODE))
            .andExpect(jsonPath("$.straatnaam").value(DEFAULT_STRAATNAAM))
            .andExpect(jsonPath("$.typeopenbareruimte").value(DEFAULT_TYPEOPENBARERUIMTE))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE))
            .andExpect(jsonPath("$.wegsegment").value(DEFAULT_WEGSEGMENT));
    }

    @Test
    @Transactional
    void getNonExistingOpenbareruimte() throws Exception {
        // Get the openbareruimte
        restOpenbareruimteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpenbareruimte() throws Exception {
        // Initialize the database
        openbareruimteRepository.saveAndFlush(openbareruimte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the openbareruimte
        Openbareruimte updatedOpenbareruimte = openbareruimteRepository.findById(openbareruimte.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOpenbareruimte are not directly saved in db
        em.detach(updatedOpenbareruimte);
        updatedOpenbareruimte
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .huisnummerrangeevenenonevennummers(UPDATED_HUISNUMMERRANGEEVENENONEVENNUMMERS)
            .huisnummerrangeevennummers(UPDATED_HUISNUMMERRANGEEVENNUMMERS)
            .huisnummerrangeonevennummers(UPDATED_HUISNUMMERRANGEONEVENNUMMERS)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .labelnaam(UPDATED_LABELNAAM)
            .naamopenbareruimte(UPDATED_NAAMOPENBARERUIMTE)
            .status(UPDATED_STATUS)
            .straatcode(UPDATED_STRAATCODE)
            .straatnaam(UPDATED_STRAATNAAM)
            .typeopenbareruimte(UPDATED_TYPEOPENBARERUIMTE)
            .versie(UPDATED_VERSIE)
            .wegsegment(UPDATED_WEGSEGMENT);

        restOpenbareruimteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpenbareruimte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOpenbareruimte))
            )
            .andExpect(status().isOk());

        // Validate the Openbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpenbareruimteToMatchAllProperties(updatedOpenbareruimte);
    }

    @Test
    @Transactional
    void putNonExistingOpenbareruimte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareruimte.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpenbareruimteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, openbareruimte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(openbareruimte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Openbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpenbareruimte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareruimte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpenbareruimteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(openbareruimte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Openbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpenbareruimte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareruimte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpenbareruimteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(openbareruimte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Openbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpenbareruimteWithPatch() throws Exception {
        // Initialize the database
        openbareruimteRepository.saveAndFlush(openbareruimte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the openbareruimte using partial update
        Openbareruimte partialUpdatedOpenbareruimte = new Openbareruimte();
        partialUpdatedOpenbareruimte.setId(openbareruimte.getId());

        partialUpdatedOpenbareruimte
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .huisnummerrangeonevennummers(UPDATED_HUISNUMMERRANGEONEVENNUMMERS)
            .naamopenbareruimte(UPDATED_NAAMOPENBARERUIMTE)
            .straatcode(UPDATED_STRAATCODE)
            .versie(UPDATED_VERSIE);

        restOpenbareruimteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpenbareruimte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpenbareruimte))
            )
            .andExpect(status().isOk());

        // Validate the Openbareruimte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpenbareruimteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOpenbareruimte, openbareruimte),
            getPersistedOpenbareruimte(openbareruimte)
        );
    }

    @Test
    @Transactional
    void fullUpdateOpenbareruimteWithPatch() throws Exception {
        // Initialize the database
        openbareruimteRepository.saveAndFlush(openbareruimte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the openbareruimte using partial update
        Openbareruimte partialUpdatedOpenbareruimte = new Openbareruimte();
        partialUpdatedOpenbareruimte.setId(openbareruimte.getId());

        partialUpdatedOpenbareruimte
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .huisnummerrangeevenenonevennummers(UPDATED_HUISNUMMERRANGEEVENENONEVENNUMMERS)
            .huisnummerrangeevennummers(UPDATED_HUISNUMMERRANGEEVENNUMMERS)
            .huisnummerrangeonevennummers(UPDATED_HUISNUMMERRANGEONEVENNUMMERS)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .labelnaam(UPDATED_LABELNAAM)
            .naamopenbareruimte(UPDATED_NAAMOPENBARERUIMTE)
            .status(UPDATED_STATUS)
            .straatcode(UPDATED_STRAATCODE)
            .straatnaam(UPDATED_STRAATNAAM)
            .typeopenbareruimte(UPDATED_TYPEOPENBARERUIMTE)
            .versie(UPDATED_VERSIE)
            .wegsegment(UPDATED_WEGSEGMENT);

        restOpenbareruimteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpenbareruimte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpenbareruimte))
            )
            .andExpect(status().isOk());

        // Validate the Openbareruimte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpenbareruimteUpdatableFieldsEquals(partialUpdatedOpenbareruimte, getPersistedOpenbareruimte(partialUpdatedOpenbareruimte));
    }

    @Test
    @Transactional
    void patchNonExistingOpenbareruimte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareruimte.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpenbareruimteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, openbareruimte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(openbareruimte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Openbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpenbareruimte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareruimte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpenbareruimteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(openbareruimte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Openbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpenbareruimte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        openbareruimte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpenbareruimteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(openbareruimte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Openbareruimte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpenbareruimte() throws Exception {
        // Initialize the database
        openbareruimteRepository.saveAndFlush(openbareruimte);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the openbareruimte
        restOpenbareruimteMockMvc
            .perform(delete(ENTITY_API_URL_ID, openbareruimte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return openbareruimteRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Openbareruimte getPersistedOpenbareruimte(Openbareruimte openbareruimte) {
        return openbareruimteRepository.findById(openbareruimte.getId()).orElseThrow();
    }

    protected void assertPersistedOpenbareruimteToMatchAllProperties(Openbareruimte expectedOpenbareruimte) {
        assertOpenbareruimteAllPropertiesEquals(expectedOpenbareruimte, getPersistedOpenbareruimte(expectedOpenbareruimte));
    }

    protected void assertPersistedOpenbareruimteToMatchUpdatableProperties(Openbareruimte expectedOpenbareruimte) {
        assertOpenbareruimteAllUpdatablePropertiesEquals(expectedOpenbareruimte, getPersistedOpenbareruimte(expectedOpenbareruimte));
    }
}
