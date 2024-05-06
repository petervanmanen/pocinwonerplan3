package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KlimplantAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Klimplant;
import nl.ritense.demo.repository.KlimplantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KlimplantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KlimplantResourceIT {

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_KNIPFREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_KNIPFREQUENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_KNIPOPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_KNIPOPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERSTEUNINGSVORM = "AAAAAAAAAA";
    private static final String UPDATED_ONDERSTEUNINGSVORM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/klimplants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KlimplantRepository klimplantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKlimplantMockMvc;

    private Klimplant klimplant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klimplant createEntity(EntityManager em) {
        Klimplant klimplant = new Klimplant()
            .hoogte(DEFAULT_HOOGTE)
            .knipfrequentie(DEFAULT_KNIPFREQUENTIE)
            .knipoppervlakte(DEFAULT_KNIPOPPERVLAKTE)
            .ondersteuningsvorm(DEFAULT_ONDERSTEUNINGSVORM)
            .type(DEFAULT_TYPE);
        return klimplant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Klimplant createUpdatedEntity(EntityManager em) {
        Klimplant klimplant = new Klimplant()
            .hoogte(UPDATED_HOOGTE)
            .knipfrequentie(UPDATED_KNIPFREQUENTIE)
            .knipoppervlakte(UPDATED_KNIPOPPERVLAKTE)
            .ondersteuningsvorm(UPDATED_ONDERSTEUNINGSVORM)
            .type(UPDATED_TYPE);
        return klimplant;
    }

    @BeforeEach
    public void initTest() {
        klimplant = createEntity(em);
    }

    @Test
    @Transactional
    void createKlimplant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Klimplant
        var returnedKlimplant = om.readValue(
            restKlimplantMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klimplant)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Klimplant.class
        );

        // Validate the Klimplant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKlimplantUpdatableFieldsEquals(returnedKlimplant, getPersistedKlimplant(returnedKlimplant));
    }

    @Test
    @Transactional
    void createKlimplantWithExistingId() throws Exception {
        // Create the Klimplant with an existing ID
        klimplant.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlimplantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klimplant)))
            .andExpect(status().isBadRequest());

        // Validate the Klimplant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKlimplants() throws Exception {
        // Initialize the database
        klimplantRepository.saveAndFlush(klimplant);

        // Get all the klimplantList
        restKlimplantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klimplant.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].knipfrequentie").value(hasItem(DEFAULT_KNIPFREQUENTIE)))
            .andExpect(jsonPath("$.[*].knipoppervlakte").value(hasItem(DEFAULT_KNIPOPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].ondersteuningsvorm").value(hasItem(DEFAULT_ONDERSTEUNINGSVORM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getKlimplant() throws Exception {
        // Initialize the database
        klimplantRepository.saveAndFlush(klimplant);

        // Get the klimplant
        restKlimplantMockMvc
            .perform(get(ENTITY_API_URL_ID, klimplant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(klimplant.getId().intValue()))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.knipfrequentie").value(DEFAULT_KNIPFREQUENTIE))
            .andExpect(jsonPath("$.knipoppervlakte").value(DEFAULT_KNIPOPPERVLAKTE))
            .andExpect(jsonPath("$.ondersteuningsvorm").value(DEFAULT_ONDERSTEUNINGSVORM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingKlimplant() throws Exception {
        // Get the klimplant
        restKlimplantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKlimplant() throws Exception {
        // Initialize the database
        klimplantRepository.saveAndFlush(klimplant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klimplant
        Klimplant updatedKlimplant = klimplantRepository.findById(klimplant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKlimplant are not directly saved in db
        em.detach(updatedKlimplant);
        updatedKlimplant
            .hoogte(UPDATED_HOOGTE)
            .knipfrequentie(UPDATED_KNIPFREQUENTIE)
            .knipoppervlakte(UPDATED_KNIPOPPERVLAKTE)
            .ondersteuningsvorm(UPDATED_ONDERSTEUNINGSVORM)
            .type(UPDATED_TYPE);

        restKlimplantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKlimplant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKlimplant))
            )
            .andExpect(status().isOk());

        // Validate the Klimplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKlimplantToMatchAllProperties(updatedKlimplant);
    }

    @Test
    @Transactional
    void putNonExistingKlimplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klimplant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlimplantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, klimplant.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klimplant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klimplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKlimplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klimplant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlimplantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(klimplant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klimplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKlimplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klimplant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlimplantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(klimplant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Klimplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKlimplantWithPatch() throws Exception {
        // Initialize the database
        klimplantRepository.saveAndFlush(klimplant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klimplant using partial update
        Klimplant partialUpdatedKlimplant = new Klimplant();
        partialUpdatedKlimplant.setId(klimplant.getId());

        partialUpdatedKlimplant.hoogte(UPDATED_HOOGTE).knipfrequentie(UPDATED_KNIPFREQUENTIE).type(UPDATED_TYPE);

        restKlimplantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlimplant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKlimplant))
            )
            .andExpect(status().isOk());

        // Validate the Klimplant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKlimplantUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKlimplant, klimplant),
            getPersistedKlimplant(klimplant)
        );
    }

    @Test
    @Transactional
    void fullUpdateKlimplantWithPatch() throws Exception {
        // Initialize the database
        klimplantRepository.saveAndFlush(klimplant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the klimplant using partial update
        Klimplant partialUpdatedKlimplant = new Klimplant();
        partialUpdatedKlimplant.setId(klimplant.getId());

        partialUpdatedKlimplant
            .hoogte(UPDATED_HOOGTE)
            .knipfrequentie(UPDATED_KNIPFREQUENTIE)
            .knipoppervlakte(UPDATED_KNIPOPPERVLAKTE)
            .ondersteuningsvorm(UPDATED_ONDERSTEUNINGSVORM)
            .type(UPDATED_TYPE);

        restKlimplantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKlimplant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKlimplant))
            )
            .andExpect(status().isOk());

        // Validate the Klimplant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKlimplantUpdatableFieldsEquals(partialUpdatedKlimplant, getPersistedKlimplant(partialUpdatedKlimplant));
    }

    @Test
    @Transactional
    void patchNonExistingKlimplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klimplant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlimplantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, klimplant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(klimplant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klimplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKlimplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klimplant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlimplantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(klimplant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Klimplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKlimplant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        klimplant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKlimplantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(klimplant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Klimplant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKlimplant() throws Exception {
        // Initialize the database
        klimplantRepository.saveAndFlush(klimplant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the klimplant
        restKlimplantMockMvc
            .perform(delete(ENTITY_API_URL_ID, klimplant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return klimplantRepository.count();
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

    protected Klimplant getPersistedKlimplant(Klimplant klimplant) {
        return klimplantRepository.findById(klimplant.getId()).orElseThrow();
    }

    protected void assertPersistedKlimplantToMatchAllProperties(Klimplant expectedKlimplant) {
        assertKlimplantAllPropertiesEquals(expectedKlimplant, getPersistedKlimplant(expectedKlimplant));
    }

    protected void assertPersistedKlimplantToMatchUpdatableProperties(Klimplant expectedKlimplant) {
        assertKlimplantAllUpdatablePropertiesEquals(expectedKlimplant, getPersistedKlimplant(expectedKlimplant));
    }
}
