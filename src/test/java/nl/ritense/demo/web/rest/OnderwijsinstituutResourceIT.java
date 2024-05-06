package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnderwijsinstituutAsserts.*;
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
import nl.ritense.demo.domain.Onderwijsinstituut;
import nl.ritense.demo.repository.OnderwijsinstituutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderwijsinstituutResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class OnderwijsinstituutResourceIT {

    private static final String ENTITY_API_URL = "/api/onderwijsinstituuts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnderwijsinstituutRepository onderwijsinstituutRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderwijsinstituutMockMvc;

    private Onderwijsinstituut onderwijsinstituut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijsinstituut createEntity(EntityManager em) {
        Onderwijsinstituut onderwijsinstituut = new Onderwijsinstituut();
        return onderwijsinstituut;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijsinstituut createUpdatedEntity(EntityManager em) {
        Onderwijsinstituut onderwijsinstituut = new Onderwijsinstituut();
        return onderwijsinstituut;
    }

    @BeforeEach
    public void initTest() {
        onderwijsinstituut = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderwijsinstituut() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onderwijsinstituut
        var returnedOnderwijsinstituut = om.readValue(
            restOnderwijsinstituutMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijsinstituut)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onderwijsinstituut.class
        );

        // Validate the Onderwijsinstituut in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnderwijsinstituutUpdatableFieldsEquals(
            returnedOnderwijsinstituut,
            getPersistedOnderwijsinstituut(returnedOnderwijsinstituut)
        );
    }

    @Test
    @Transactional
    void createOnderwijsinstituutWithExistingId() throws Exception {
        // Create the Onderwijsinstituut with an existing ID
        onderwijsinstituut.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderwijsinstituutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijsinstituut)))
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsinstituut in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnderwijsinstituuts() throws Exception {
        // Initialize the database
        onderwijsinstituutRepository.saveAndFlush(onderwijsinstituut);

        // Get all the onderwijsinstituutList
        restOnderwijsinstituutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderwijsinstituut.getId().intValue())));
    }

    @Test
    @Transactional
    void getOnderwijsinstituut() throws Exception {
        // Initialize the database
        onderwijsinstituutRepository.saveAndFlush(onderwijsinstituut);

        // Get the onderwijsinstituut
        restOnderwijsinstituutMockMvc
            .perform(get(ENTITY_API_URL_ID, onderwijsinstituut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderwijsinstituut.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOnderwijsinstituut() throws Exception {
        // Get the onderwijsinstituut
        restOnderwijsinstituutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOnderwijsinstituut() throws Exception {
        // Initialize the database
        onderwijsinstituutRepository.saveAndFlush(onderwijsinstituut);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijsinstituut
        Onderwijsinstituut updatedOnderwijsinstituut = onderwijsinstituutRepository.findById(onderwijsinstituut.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOnderwijsinstituut are not directly saved in db
        em.detach(updatedOnderwijsinstituut);

        restOnderwijsinstituutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOnderwijsinstituut.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOnderwijsinstituut))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijsinstituut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOnderwijsinstituutToMatchAllProperties(updatedOnderwijsinstituut);
    }

    @Test
    @Transactional
    void putNonExistingOnderwijsinstituut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsinstituut.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderwijsinstituutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, onderwijsinstituut.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onderwijsinstituut))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsinstituut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOnderwijsinstituut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsinstituut.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijsinstituutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onderwijsinstituut))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsinstituut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOnderwijsinstituut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsinstituut.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijsinstituutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijsinstituut)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderwijsinstituut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOnderwijsinstituutWithPatch() throws Exception {
        // Initialize the database
        onderwijsinstituutRepository.saveAndFlush(onderwijsinstituut);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijsinstituut using partial update
        Onderwijsinstituut partialUpdatedOnderwijsinstituut = new Onderwijsinstituut();
        partialUpdatedOnderwijsinstituut.setId(onderwijsinstituut.getId());

        restOnderwijsinstituutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderwijsinstituut.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnderwijsinstituut))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijsinstituut in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnderwijsinstituutUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOnderwijsinstituut, onderwijsinstituut),
            getPersistedOnderwijsinstituut(onderwijsinstituut)
        );
    }

    @Test
    @Transactional
    void fullUpdateOnderwijsinstituutWithPatch() throws Exception {
        // Initialize the database
        onderwijsinstituutRepository.saveAndFlush(onderwijsinstituut);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijsinstituut using partial update
        Onderwijsinstituut partialUpdatedOnderwijsinstituut = new Onderwijsinstituut();
        partialUpdatedOnderwijsinstituut.setId(onderwijsinstituut.getId());

        restOnderwijsinstituutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderwijsinstituut.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnderwijsinstituut))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijsinstituut in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnderwijsinstituutUpdatableFieldsEquals(
            partialUpdatedOnderwijsinstituut,
            getPersistedOnderwijsinstituut(partialUpdatedOnderwijsinstituut)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOnderwijsinstituut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsinstituut.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderwijsinstituutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, onderwijsinstituut.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onderwijsinstituut))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsinstituut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOnderwijsinstituut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsinstituut.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijsinstituutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onderwijsinstituut))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijsinstituut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOnderwijsinstituut() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijsinstituut.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijsinstituutMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(onderwijsinstituut)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderwijsinstituut in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOnderwijsinstituut() throws Exception {
        // Initialize the database
        onderwijsinstituutRepository.saveAndFlush(onderwijsinstituut);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onderwijsinstituut
        restOnderwijsinstituutMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderwijsinstituut.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onderwijsinstituutRepository.count();
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

    protected Onderwijsinstituut getPersistedOnderwijsinstituut(Onderwijsinstituut onderwijsinstituut) {
        return onderwijsinstituutRepository.findById(onderwijsinstituut.getId()).orElseThrow();
    }

    protected void assertPersistedOnderwijsinstituutToMatchAllProperties(Onderwijsinstituut expectedOnderwijsinstituut) {
        assertOnderwijsinstituutAllPropertiesEquals(expectedOnderwijsinstituut, getPersistedOnderwijsinstituut(expectedOnderwijsinstituut));
    }

    protected void assertPersistedOnderwijsinstituutToMatchUpdatableProperties(Onderwijsinstituut expectedOnderwijsinstituut) {
        assertOnderwijsinstituutAllUpdatablePropertiesEquals(
            expectedOnderwijsinstituut,
            getPersistedOnderwijsinstituut(expectedOnderwijsinstituut)
        );
    }
}
