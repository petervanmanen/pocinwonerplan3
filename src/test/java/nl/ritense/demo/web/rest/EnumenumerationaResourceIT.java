package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EnumenumerationaAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Enumenumerationa;
import nl.ritense.demo.repository.EnumenumerationaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EnumenumerationaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnumenumerationaResourceIT {

    private static final String DEFAULT_OPTIE_1 = "AAAAAAAAAA";
    private static final String UPDATED_OPTIE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_OPTIE_2 = "AAAAAAAAAA";
    private static final String UPDATED_OPTIE_2 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/enumenumerationas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnumenumerationaRepository enumenumerationaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnumenumerationaMockMvc;

    private Enumenumerationa enumenumerationa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enumenumerationa createEntity(EntityManager em) {
        Enumenumerationa enumenumerationa = new Enumenumerationa().optie1(DEFAULT_OPTIE_1).optie2(DEFAULT_OPTIE_2);
        return enumenumerationa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enumenumerationa createUpdatedEntity(EntityManager em) {
        Enumenumerationa enumenumerationa = new Enumenumerationa().optie1(UPDATED_OPTIE_1).optie2(UPDATED_OPTIE_2);
        return enumenumerationa;
    }

    @BeforeEach
    public void initTest() {
        enumenumerationa = createEntity(em);
    }

    @Test
    @Transactional
    void createEnumenumerationa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Enumenumerationa
        var returnedEnumenumerationa = om.readValue(
            restEnumenumerationaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enumenumerationa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Enumenumerationa.class
        );

        // Validate the Enumenumerationa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEnumenumerationaUpdatableFieldsEquals(returnedEnumenumerationa, getPersistedEnumenumerationa(returnedEnumenumerationa));
    }

    @Test
    @Transactional
    void createEnumenumerationaWithExistingId() throws Exception {
        // Create the Enumenumerationa with an existing ID
        enumenumerationa.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnumenumerationaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enumenumerationa)))
            .andExpect(status().isBadRequest());

        // Validate the Enumenumerationa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnumenumerationas() throws Exception {
        // Initialize the database
        enumenumerationaRepository.saveAndFlush(enumenumerationa);

        // Get all the enumenumerationaList
        restEnumenumerationaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enumenumerationa.getId().intValue())))
            .andExpect(jsonPath("$.[*].optie1").value(hasItem(DEFAULT_OPTIE_1)))
            .andExpect(jsonPath("$.[*].optie2").value(hasItem(DEFAULT_OPTIE_2)));
    }

    @Test
    @Transactional
    void getEnumenumerationa() throws Exception {
        // Initialize the database
        enumenumerationaRepository.saveAndFlush(enumenumerationa);

        // Get the enumenumerationa
        restEnumenumerationaMockMvc
            .perform(get(ENTITY_API_URL_ID, enumenumerationa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enumenumerationa.getId().intValue()))
            .andExpect(jsonPath("$.optie1").value(DEFAULT_OPTIE_1))
            .andExpect(jsonPath("$.optie2").value(DEFAULT_OPTIE_2));
    }

    @Test
    @Transactional
    void getNonExistingEnumenumerationa() throws Exception {
        // Get the enumenumerationa
        restEnumenumerationaMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnumenumerationa() throws Exception {
        // Initialize the database
        enumenumerationaRepository.saveAndFlush(enumenumerationa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enumenumerationa
        Enumenumerationa updatedEnumenumerationa = enumenumerationaRepository.findById(enumenumerationa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnumenumerationa are not directly saved in db
        em.detach(updatedEnumenumerationa);
        updatedEnumenumerationa.optie1(UPDATED_OPTIE_1).optie2(UPDATED_OPTIE_2);

        restEnumenumerationaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnumenumerationa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEnumenumerationa))
            )
            .andExpect(status().isOk());

        // Validate the Enumenumerationa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnumenumerationaToMatchAllProperties(updatedEnumenumerationa);
    }

    @Test
    @Transactional
    void putNonExistingEnumenumerationa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enumenumerationa.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnumenumerationaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enumenumerationa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enumenumerationa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enumenumerationa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnumenumerationa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enumenumerationa.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnumenumerationaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enumenumerationa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enumenumerationa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnumenumerationa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enumenumerationa.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnumenumerationaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enumenumerationa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enumenumerationa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnumenumerationaWithPatch() throws Exception {
        // Initialize the database
        enumenumerationaRepository.saveAndFlush(enumenumerationa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enumenumerationa using partial update
        Enumenumerationa partialUpdatedEnumenumerationa = new Enumenumerationa();
        partialUpdatedEnumenumerationa.setId(enumenumerationa.getId());

        restEnumenumerationaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnumenumerationa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnumenumerationa))
            )
            .andExpect(status().isOk());

        // Validate the Enumenumerationa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnumenumerationaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEnumenumerationa, enumenumerationa),
            getPersistedEnumenumerationa(enumenumerationa)
        );
    }

    @Test
    @Transactional
    void fullUpdateEnumenumerationaWithPatch() throws Exception {
        // Initialize the database
        enumenumerationaRepository.saveAndFlush(enumenumerationa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enumenumerationa using partial update
        Enumenumerationa partialUpdatedEnumenumerationa = new Enumenumerationa();
        partialUpdatedEnumenumerationa.setId(enumenumerationa.getId());

        partialUpdatedEnumenumerationa.optie1(UPDATED_OPTIE_1).optie2(UPDATED_OPTIE_2);

        restEnumenumerationaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnumenumerationa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnumenumerationa))
            )
            .andExpect(status().isOk());

        // Validate the Enumenumerationa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnumenumerationaUpdatableFieldsEquals(
            partialUpdatedEnumenumerationa,
            getPersistedEnumenumerationa(partialUpdatedEnumenumerationa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEnumenumerationa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enumenumerationa.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnumenumerationaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enumenumerationa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enumenumerationa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enumenumerationa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnumenumerationa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enumenumerationa.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnumenumerationaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enumenumerationa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enumenumerationa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnumenumerationa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enumenumerationa.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnumenumerationaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(enumenumerationa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enumenumerationa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnumenumerationa() throws Exception {
        // Initialize the database
        enumenumerationaRepository.saveAndFlush(enumenumerationa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enumenumerationa
        restEnumenumerationaMockMvc
            .perform(delete(ENTITY_API_URL_ID, enumenumerationa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enumenumerationaRepository.count();
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

    protected Enumenumerationa getPersistedEnumenumerationa(Enumenumerationa enumenumerationa) {
        return enumenumerationaRepository.findById(enumenumerationa.getId()).orElseThrow();
    }

    protected void assertPersistedEnumenumerationaToMatchAllProperties(Enumenumerationa expectedEnumenumerationa) {
        assertEnumenumerationaAllPropertiesEquals(expectedEnumenumerationa, getPersistedEnumenumerationa(expectedEnumenumerationa));
    }

    protected void assertPersistedEnumenumerationaToMatchUpdatableProperties(Enumenumerationa expectedEnumenumerationa) {
        assertEnumenumerationaAllUpdatablePropertiesEquals(
            expectedEnumenumerationa,
            getPersistedEnumenumerationa(expectedEnumenumerationa)
        );
    }
}
