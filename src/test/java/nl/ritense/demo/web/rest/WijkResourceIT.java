package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WijkAsserts.*;
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
import nl.ritense.demo.domain.Areaal;
import nl.ritense.demo.domain.Wijk;
import nl.ritense.demo.repository.WijkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WijkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WijkResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANG = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GECONSTATEERD = false;
    private static final Boolean UPDATED_GECONSTATEERD = true;

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INONDERZOEK = false;
    private static final Boolean UPDATED_INONDERZOEK = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String DEFAULT_WIJKCODE = "AAAAAAAAAA";
    private static final String UPDATED_WIJKCODE = "BBBBBBBBBB";

    private static final String DEFAULT_WIJKNAAM = "AAAAAAAAAA";
    private static final String UPDATED_WIJKNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wijks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WijkRepository wijkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWijkMockMvc;

    private Wijk wijk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wijk createEntity(EntityManager em) {
        Wijk wijk = new Wijk()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumingang(DEFAULT_DATUMINGANG)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .geometrie(DEFAULT_GEOMETRIE)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .status(DEFAULT_STATUS)
            .versie(DEFAULT_VERSIE)
            .wijkcode(DEFAULT_WIJKCODE)
            .wijknaam(DEFAULT_WIJKNAAM);
        // Add required entity
        Areaal areaal;
        if (TestUtil.findAll(em, Areaal.class).isEmpty()) {
            areaal = AreaalResourceIT.createEntity(em);
            em.persist(areaal);
            em.flush();
        } else {
            areaal = TestUtil.findAll(em, Areaal.class).get(0);
        }
        wijk.getValtbinnenAreaals().add(areaal);
        return wijk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wijk createUpdatedEntity(EntityManager em) {
        Wijk wijk = new Wijk()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE)
            .wijkcode(UPDATED_WIJKCODE)
            .wijknaam(UPDATED_WIJKNAAM);
        // Add required entity
        Areaal areaal;
        if (TestUtil.findAll(em, Areaal.class).isEmpty()) {
            areaal = AreaalResourceIT.createUpdatedEntity(em);
            em.persist(areaal);
            em.flush();
        } else {
            areaal = TestUtil.findAll(em, Areaal.class).get(0);
        }
        wijk.getValtbinnenAreaals().add(areaal);
        return wijk;
    }

    @BeforeEach
    public void initTest() {
        wijk = createEntity(em);
    }

    @Test
    @Transactional
    void createWijk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Wijk
        var returnedWijk = om.readValue(
            restWijkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wijk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Wijk.class
        );

        // Validate the Wijk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWijkUpdatableFieldsEquals(returnedWijk, getPersistedWijk(returnedWijk));
    }

    @Test
    @Transactional
    void createWijkWithExistingId() throws Exception {
        // Create the Wijk with an existing ID
        wijk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWijkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wijk)))
            .andExpect(status().isBadRequest());

        // Validate the Wijk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWijks() throws Exception {
        // Initialize the database
        wijkRepository.saveAndFlush(wijk);

        // Get all the wijkList
        restWijkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wijk.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD.booleanValue())))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)))
            .andExpect(jsonPath("$.[*].wijkcode").value(hasItem(DEFAULT_WIJKCODE)))
            .andExpect(jsonPath("$.[*].wijknaam").value(hasItem(DEFAULT_WIJKNAAM)));
    }

    @Test
    @Transactional
    void getWijk() throws Exception {
        // Initialize the database
        wijkRepository.saveAndFlush(wijk);

        // Get the wijk
        restWijkMockMvc
            .perform(get(ENTITY_API_URL_ID, wijk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wijk.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD.booleanValue()))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE))
            .andExpect(jsonPath("$.wijkcode").value(DEFAULT_WIJKCODE))
            .andExpect(jsonPath("$.wijknaam").value(DEFAULT_WIJKNAAM));
    }

    @Test
    @Transactional
    void getNonExistingWijk() throws Exception {
        // Get the wijk
        restWijkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWijk() throws Exception {
        // Initialize the database
        wijkRepository.saveAndFlush(wijk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wijk
        Wijk updatedWijk = wijkRepository.findById(wijk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWijk are not directly saved in db
        em.detach(updatedWijk);
        updatedWijk
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE)
            .wijkcode(UPDATED_WIJKCODE)
            .wijknaam(UPDATED_WIJKNAAM);

        restWijkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWijk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWijk))
            )
            .andExpect(status().isOk());

        // Validate the Wijk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWijkToMatchAllProperties(updatedWijk);
    }

    @Test
    @Transactional
    void putNonExistingWijk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wijk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWijkMockMvc
            .perform(put(ENTITY_API_URL_ID, wijk.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wijk)))
            .andExpect(status().isBadRequest());

        // Validate the Wijk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWijk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wijk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWijkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wijk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wijk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWijk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wijk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWijkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wijk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wijk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWijkWithPatch() throws Exception {
        // Initialize the database
        wijkRepository.saveAndFlush(wijk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wijk using partial update
        Wijk partialUpdatedWijk = new Wijk();
        partialUpdatedWijk.setId(wijk.getId());

        partialUpdatedWijk
            .datumeinde(UPDATED_DATUMEINDE)
            .datumingang(UPDATED_DATUMINGANG)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .versie(UPDATED_VERSIE)
            .wijkcode(UPDATED_WIJKCODE);

        restWijkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWijk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWijk))
            )
            .andExpect(status().isOk());

        // Validate the Wijk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWijkUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWijk, wijk), getPersistedWijk(wijk));
    }

    @Test
    @Transactional
    void fullUpdateWijkWithPatch() throws Exception {
        // Initialize the database
        wijkRepository.saveAndFlush(wijk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wijk using partial update
        Wijk partialUpdatedWijk = new Wijk();
        partialUpdatedWijk.setId(wijk.getId());

        partialUpdatedWijk
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE)
            .wijkcode(UPDATED_WIJKCODE)
            .wijknaam(UPDATED_WIJKNAAM);

        restWijkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWijk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWijk))
            )
            .andExpect(status().isOk());

        // Validate the Wijk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWijkUpdatableFieldsEquals(partialUpdatedWijk, getPersistedWijk(partialUpdatedWijk));
    }

    @Test
    @Transactional
    void patchNonExistingWijk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wijk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWijkMockMvc
            .perform(patch(ENTITY_API_URL_ID, wijk.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wijk)))
            .andExpect(status().isBadRequest());

        // Validate the Wijk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWijk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wijk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWijkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wijk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wijk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWijk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wijk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWijkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wijk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wijk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWijk() throws Exception {
        // Initialize the database
        wijkRepository.saveAndFlush(wijk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the wijk
        restWijkMockMvc
            .perform(delete(ENTITY_API_URL_ID, wijk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wijkRepository.count();
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

    protected Wijk getPersistedWijk(Wijk wijk) {
        return wijkRepository.findById(wijk.getId()).orElseThrow();
    }

    protected void assertPersistedWijkToMatchAllProperties(Wijk expectedWijk) {
        assertWijkAllPropertiesEquals(expectedWijk, getPersistedWijk(expectedWijk));
    }

    protected void assertPersistedWijkToMatchUpdatableProperties(Wijk expectedWijk) {
        assertWijkAllUpdatablePropertiesEquals(expectedWijk, getPersistedWijk(expectedWijk));
    }
}
