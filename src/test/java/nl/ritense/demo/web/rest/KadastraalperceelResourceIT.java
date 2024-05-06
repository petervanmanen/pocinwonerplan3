package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KadastraalperceelAsserts.*;
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
import nl.ritense.demo.domain.Kadastraalperceel;
import nl.ritense.demo.repository.KadastraalperceelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KadastraalperceelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KadastraalperceelResourceIT {

    private static final String DEFAULT_AANDUIDINGSOORTGROOTTE = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGSOORTGROOTTE = "BBBBBBBBBB";

    private static final String DEFAULT_BEGRENZINGPERCEEL = "AAAAAAAAAA";
    private static final String UPDATED_BEGRENZINGPERCEEL = "BBBBBBBBBB";

    private static final String DEFAULT_GROOTTEPERCEEL = "AAAAAAAAAA";
    private static final String UPDATED_GROOTTEPERCEEL = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEDEELPERCEEL = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEDEELPERCEEL = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGDEELPERCEEL = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGDEELPERCEEL = "BBBBBBBBBB";

    private static final String DEFAULT_PLAATSCOORDINATENPERCEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLAATSCOORDINATENPERCEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kadastraalperceels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KadastraalperceelRepository kadastraalperceelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKadastraalperceelMockMvc;

    private Kadastraalperceel kadastraalperceel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastraalperceel createEntity(EntityManager em) {
        Kadastraalperceel kadastraalperceel = new Kadastraalperceel()
            .aanduidingsoortgrootte(DEFAULT_AANDUIDINGSOORTGROOTTE)
            .begrenzingperceel(DEFAULT_BEGRENZINGPERCEEL)
            .grootteperceel(DEFAULT_GROOTTEPERCEEL)
            .indicatiedeelperceel(DEFAULT_INDICATIEDEELPERCEEL)
            .omschrijvingdeelperceel(DEFAULT_OMSCHRIJVINGDEELPERCEEL)
            .plaatscoordinatenperceel(DEFAULT_PLAATSCOORDINATENPERCEEL);
        return kadastraalperceel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastraalperceel createUpdatedEntity(EntityManager em) {
        Kadastraalperceel kadastraalperceel = new Kadastraalperceel()
            .aanduidingsoortgrootte(UPDATED_AANDUIDINGSOORTGROOTTE)
            .begrenzingperceel(UPDATED_BEGRENZINGPERCEEL)
            .grootteperceel(UPDATED_GROOTTEPERCEEL)
            .indicatiedeelperceel(UPDATED_INDICATIEDEELPERCEEL)
            .omschrijvingdeelperceel(UPDATED_OMSCHRIJVINGDEELPERCEEL)
            .plaatscoordinatenperceel(UPDATED_PLAATSCOORDINATENPERCEEL);
        return kadastraalperceel;
    }

    @BeforeEach
    public void initTest() {
        kadastraalperceel = createEntity(em);
    }

    @Test
    @Transactional
    void createKadastraalperceel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kadastraalperceel
        var returnedKadastraalperceel = om.readValue(
            restKadastraalperceelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastraalperceel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kadastraalperceel.class
        );

        // Validate the Kadastraalperceel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKadastraalperceelUpdatableFieldsEquals(returnedKadastraalperceel, getPersistedKadastraalperceel(returnedKadastraalperceel));
    }

    @Test
    @Transactional
    void createKadastraalperceelWithExistingId() throws Exception {
        // Create the Kadastraalperceel with an existing ID
        kadastraalperceel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKadastraalperceelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastraalperceel)))
            .andExpect(status().isBadRequest());

        // Validate the Kadastraalperceel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKadastraalperceels() throws Exception {
        // Initialize the database
        kadastraalperceelRepository.saveAndFlush(kadastraalperceel);

        // Get all the kadastraalperceelList
        restKadastraalperceelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kadastraalperceel.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanduidingsoortgrootte").value(hasItem(DEFAULT_AANDUIDINGSOORTGROOTTE)))
            .andExpect(jsonPath("$.[*].begrenzingperceel").value(hasItem(DEFAULT_BEGRENZINGPERCEEL)))
            .andExpect(jsonPath("$.[*].grootteperceel").value(hasItem(DEFAULT_GROOTTEPERCEEL)))
            .andExpect(jsonPath("$.[*].indicatiedeelperceel").value(hasItem(DEFAULT_INDICATIEDEELPERCEEL)))
            .andExpect(jsonPath("$.[*].omschrijvingdeelperceel").value(hasItem(DEFAULT_OMSCHRIJVINGDEELPERCEEL)))
            .andExpect(jsonPath("$.[*].plaatscoordinatenperceel").value(hasItem(DEFAULT_PLAATSCOORDINATENPERCEEL)));
    }

    @Test
    @Transactional
    void getKadastraalperceel() throws Exception {
        // Initialize the database
        kadastraalperceelRepository.saveAndFlush(kadastraalperceel);

        // Get the kadastraalperceel
        restKadastraalperceelMockMvc
            .perform(get(ENTITY_API_URL_ID, kadastraalperceel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kadastraalperceel.getId().intValue()))
            .andExpect(jsonPath("$.aanduidingsoortgrootte").value(DEFAULT_AANDUIDINGSOORTGROOTTE))
            .andExpect(jsonPath("$.begrenzingperceel").value(DEFAULT_BEGRENZINGPERCEEL))
            .andExpect(jsonPath("$.grootteperceel").value(DEFAULT_GROOTTEPERCEEL))
            .andExpect(jsonPath("$.indicatiedeelperceel").value(DEFAULT_INDICATIEDEELPERCEEL))
            .andExpect(jsonPath("$.omschrijvingdeelperceel").value(DEFAULT_OMSCHRIJVINGDEELPERCEEL))
            .andExpect(jsonPath("$.plaatscoordinatenperceel").value(DEFAULT_PLAATSCOORDINATENPERCEEL));
    }

    @Test
    @Transactional
    void getNonExistingKadastraalperceel() throws Exception {
        // Get the kadastraalperceel
        restKadastraalperceelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKadastraalperceel() throws Exception {
        // Initialize the database
        kadastraalperceelRepository.saveAndFlush(kadastraalperceel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraalperceel
        Kadastraalperceel updatedKadastraalperceel = kadastraalperceelRepository.findById(kadastraalperceel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKadastraalperceel are not directly saved in db
        em.detach(updatedKadastraalperceel);
        updatedKadastraalperceel
            .aanduidingsoortgrootte(UPDATED_AANDUIDINGSOORTGROOTTE)
            .begrenzingperceel(UPDATED_BEGRENZINGPERCEEL)
            .grootteperceel(UPDATED_GROOTTEPERCEEL)
            .indicatiedeelperceel(UPDATED_INDICATIEDEELPERCEEL)
            .omschrijvingdeelperceel(UPDATED_OMSCHRIJVINGDEELPERCEEL)
            .plaatscoordinatenperceel(UPDATED_PLAATSCOORDINATENPERCEEL);

        restKadastraalperceelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKadastraalperceel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKadastraalperceel))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraalperceel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKadastraalperceelToMatchAllProperties(updatedKadastraalperceel);
    }

    @Test
    @Transactional
    void putNonExistingKadastraalperceel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraalperceel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastraalperceelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kadastraalperceel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastraalperceel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraalperceel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKadastraalperceel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraalperceel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraalperceelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastraalperceel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraalperceel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKadastraalperceel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraalperceel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraalperceelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kadastraalperceel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastraalperceel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKadastraalperceelWithPatch() throws Exception {
        // Initialize the database
        kadastraalperceelRepository.saveAndFlush(kadastraalperceel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraalperceel using partial update
        Kadastraalperceel partialUpdatedKadastraalperceel = new Kadastraalperceel();
        partialUpdatedKadastraalperceel.setId(kadastraalperceel.getId());

        partialUpdatedKadastraalperceel
            .indicatiedeelperceel(UPDATED_INDICATIEDEELPERCEEL)
            .omschrijvingdeelperceel(UPDATED_OMSCHRIJVINGDEELPERCEEL)
            .plaatscoordinatenperceel(UPDATED_PLAATSCOORDINATENPERCEEL);

        restKadastraalperceelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastraalperceel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastraalperceel))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraalperceel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastraalperceelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKadastraalperceel, kadastraalperceel),
            getPersistedKadastraalperceel(kadastraalperceel)
        );
    }

    @Test
    @Transactional
    void fullUpdateKadastraalperceelWithPatch() throws Exception {
        // Initialize the database
        kadastraalperceelRepository.saveAndFlush(kadastraalperceel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraalperceel using partial update
        Kadastraalperceel partialUpdatedKadastraalperceel = new Kadastraalperceel();
        partialUpdatedKadastraalperceel.setId(kadastraalperceel.getId());

        partialUpdatedKadastraalperceel
            .aanduidingsoortgrootte(UPDATED_AANDUIDINGSOORTGROOTTE)
            .begrenzingperceel(UPDATED_BEGRENZINGPERCEEL)
            .grootteperceel(UPDATED_GROOTTEPERCEEL)
            .indicatiedeelperceel(UPDATED_INDICATIEDEELPERCEEL)
            .omschrijvingdeelperceel(UPDATED_OMSCHRIJVINGDEELPERCEEL)
            .plaatscoordinatenperceel(UPDATED_PLAATSCOORDINATENPERCEEL);

        restKadastraalperceelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastraalperceel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastraalperceel))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraalperceel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastraalperceelUpdatableFieldsEquals(
            partialUpdatedKadastraalperceel,
            getPersistedKadastraalperceel(partialUpdatedKadastraalperceel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKadastraalperceel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraalperceel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastraalperceelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kadastraalperceel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastraalperceel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraalperceel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKadastraalperceel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraalperceel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraalperceelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastraalperceel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraalperceel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKadastraalperceel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraalperceel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraalperceelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kadastraalperceel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastraalperceel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKadastraalperceel() throws Exception {
        // Initialize the database
        kadastraalperceelRepository.saveAndFlush(kadastraalperceel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kadastraalperceel
        restKadastraalperceelMockMvc
            .perform(delete(ENTITY_API_URL_ID, kadastraalperceel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kadastraalperceelRepository.count();
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

    protected Kadastraalperceel getPersistedKadastraalperceel(Kadastraalperceel kadastraalperceel) {
        return kadastraalperceelRepository.findById(kadastraalperceel.getId()).orElseThrow();
    }

    protected void assertPersistedKadastraalperceelToMatchAllProperties(Kadastraalperceel expectedKadastraalperceel) {
        assertKadastraalperceelAllPropertiesEquals(expectedKadastraalperceel, getPersistedKadastraalperceel(expectedKadastraalperceel));
    }

    protected void assertPersistedKadastraalperceelToMatchUpdatableProperties(Kadastraalperceel expectedKadastraalperceel) {
        assertKadastraalperceelAllUpdatablePropertiesEquals(
            expectedKadastraalperceel,
            getPersistedKadastraalperceel(expectedKadastraalperceel)
        );
    }
}
