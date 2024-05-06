package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HuishoudenAsserts.*;
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
import nl.ritense.demo.domain.Huishouden;
import nl.ritense.demo.repository.HuishoudenRepository;
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
 * Integration tests for the {@link HuishoudenResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class HuishoudenResourceIT {

    private static final String ENTITY_API_URL = "/api/huishoudens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HuishoudenRepository huishoudenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHuishoudenMockMvc;

    private Huishouden huishouden;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Huishouden createEntity(EntityManager em) {
        Huishouden huishouden = new Huishouden();
        return huishouden;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Huishouden createUpdatedEntity(EntityManager em) {
        Huishouden huishouden = new Huishouden();
        return huishouden;
    }

    @BeforeEach
    public void initTest() {
        huishouden = createEntity(em);
    }

    @Test
    @Transactional
    void createHuishouden() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Huishouden
        var returnedHuishouden = om.readValue(
            restHuishoudenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huishouden)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Huishouden.class
        );

        // Validate the Huishouden in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHuishoudenUpdatableFieldsEquals(returnedHuishouden, getPersistedHuishouden(returnedHuishouden));
    }

    @Test
    @Transactional
    void createHuishoudenWithExistingId() throws Exception {
        // Create the Huishouden with an existing ID
        huishouden.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHuishoudenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huishouden)))
            .andExpect(status().isBadRequest());

        // Validate the Huishouden in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHuishoudens() throws Exception {
        // Initialize the database
        huishoudenRepository.saveAndFlush(huishouden);

        // Get all the huishoudenList
        restHuishoudenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(huishouden.getId().intValue())));
    }

    @Test
    @Transactional
    void getHuishouden() throws Exception {
        // Initialize the database
        huishoudenRepository.saveAndFlush(huishouden);

        // Get the huishouden
        restHuishoudenMockMvc
            .perform(get(ENTITY_API_URL_ID, huishouden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(huishouden.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingHuishouden() throws Exception {
        // Get the huishouden
        restHuishoudenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHuishouden() throws Exception {
        // Initialize the database
        huishoudenRepository.saveAndFlush(huishouden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the huishouden
        Huishouden updatedHuishouden = huishoudenRepository.findById(huishouden.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHuishouden are not directly saved in db
        em.detach(updatedHuishouden);

        restHuishoudenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHuishouden.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHuishouden))
            )
            .andExpect(status().isOk());

        // Validate the Huishouden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHuishoudenToMatchAllProperties(updatedHuishouden);
    }

    @Test
    @Transactional
    void putNonExistingHuishouden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huishouden.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHuishoudenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, huishouden.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huishouden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Huishouden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHuishouden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huishouden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuishoudenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(huishouden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Huishouden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHuishouden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huishouden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuishoudenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(huishouden)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Huishouden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHuishoudenWithPatch() throws Exception {
        // Initialize the database
        huishoudenRepository.saveAndFlush(huishouden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the huishouden using partial update
        Huishouden partialUpdatedHuishouden = new Huishouden();
        partialUpdatedHuishouden.setId(huishouden.getId());

        restHuishoudenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHuishouden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHuishouden))
            )
            .andExpect(status().isOk());

        // Validate the Huishouden in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHuishoudenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHuishouden, huishouden),
            getPersistedHuishouden(huishouden)
        );
    }

    @Test
    @Transactional
    void fullUpdateHuishoudenWithPatch() throws Exception {
        // Initialize the database
        huishoudenRepository.saveAndFlush(huishouden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the huishouden using partial update
        Huishouden partialUpdatedHuishouden = new Huishouden();
        partialUpdatedHuishouden.setId(huishouden.getId());

        restHuishoudenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHuishouden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHuishouden))
            )
            .andExpect(status().isOk());

        // Validate the Huishouden in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHuishoudenUpdatableFieldsEquals(partialUpdatedHuishouden, getPersistedHuishouden(partialUpdatedHuishouden));
    }

    @Test
    @Transactional
    void patchNonExistingHuishouden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huishouden.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHuishoudenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, huishouden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(huishouden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Huishouden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHuishouden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huishouden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuishoudenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(huishouden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Huishouden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHuishouden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        huishouden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHuishoudenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(huishouden)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Huishouden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHuishouden() throws Exception {
        // Initialize the database
        huishoudenRepository.saveAndFlush(huishouden);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the huishouden
        restHuishoudenMockMvc
            .perform(delete(ENTITY_API_URL_ID, huishouden.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return huishoudenRepository.count();
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

    protected Huishouden getPersistedHuishouden(Huishouden huishouden) {
        return huishoudenRepository.findById(huishouden.getId()).orElseThrow();
    }

    protected void assertPersistedHuishoudenToMatchAllProperties(Huishouden expectedHuishouden) {
        assertHuishoudenAllPropertiesEquals(expectedHuishouden, getPersistedHuishouden(expectedHuishouden));
    }

    protected void assertPersistedHuishoudenToMatchUpdatableProperties(Huishouden expectedHuishouden) {
        assertHuishoudenAllUpdatablePropertiesEquals(expectedHuishouden, getPersistedHuishouden(expectedHuishouden));
    }
}
