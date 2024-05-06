package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StrijdigheidofnietigheidAsserts.*;
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
import nl.ritense.demo.domain.Strijdigheidofnietigheid;
import nl.ritense.demo.repository.StrijdigheidofnietigheidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StrijdigheidofnietigheidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrijdigheidofnietigheidResourceIT {

    private static final String DEFAULT_AANDUIDINGSTRIJDIGHEIDNIETIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGSTRIJDIGHEIDNIETIGHEID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/strijdigheidofnietigheids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StrijdigheidofnietigheidRepository strijdigheidofnietigheidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrijdigheidofnietigheidMockMvc;

    private Strijdigheidofnietigheid strijdigheidofnietigheid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strijdigheidofnietigheid createEntity(EntityManager em) {
        Strijdigheidofnietigheid strijdigheidofnietigheid = new Strijdigheidofnietigheid()
            .aanduidingstrijdigheidnietigheid(DEFAULT_AANDUIDINGSTRIJDIGHEIDNIETIGHEID);
        return strijdigheidofnietigheid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strijdigheidofnietigheid createUpdatedEntity(EntityManager em) {
        Strijdigheidofnietigheid strijdigheidofnietigheid = new Strijdigheidofnietigheid()
            .aanduidingstrijdigheidnietigheid(UPDATED_AANDUIDINGSTRIJDIGHEIDNIETIGHEID);
        return strijdigheidofnietigheid;
    }

    @BeforeEach
    public void initTest() {
        strijdigheidofnietigheid = createEntity(em);
    }

    @Test
    @Transactional
    void createStrijdigheidofnietigheid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Strijdigheidofnietigheid
        var returnedStrijdigheidofnietigheid = om.readValue(
            restStrijdigheidofnietigheidMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strijdigheidofnietigheid))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Strijdigheidofnietigheid.class
        );

        // Validate the Strijdigheidofnietigheid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStrijdigheidofnietigheidUpdatableFieldsEquals(
            returnedStrijdigheidofnietigheid,
            getPersistedStrijdigheidofnietigheid(returnedStrijdigheidofnietigheid)
        );
    }

    @Test
    @Transactional
    void createStrijdigheidofnietigheidWithExistingId() throws Exception {
        // Create the Strijdigheidofnietigheid with an existing ID
        strijdigheidofnietigheid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrijdigheidofnietigheidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strijdigheidofnietigheid)))
            .andExpect(status().isBadRequest());

        // Validate the Strijdigheidofnietigheid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStrijdigheidofnietigheids() throws Exception {
        // Initialize the database
        strijdigheidofnietigheidRepository.saveAndFlush(strijdigheidofnietigheid);

        // Get all the strijdigheidofnietigheidList
        restStrijdigheidofnietigheidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strijdigheidofnietigheid.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanduidingstrijdigheidnietigheid").value(hasItem(DEFAULT_AANDUIDINGSTRIJDIGHEIDNIETIGHEID)));
    }

    @Test
    @Transactional
    void getStrijdigheidofnietigheid() throws Exception {
        // Initialize the database
        strijdigheidofnietigheidRepository.saveAndFlush(strijdigheidofnietigheid);

        // Get the strijdigheidofnietigheid
        restStrijdigheidofnietigheidMockMvc
            .perform(get(ENTITY_API_URL_ID, strijdigheidofnietigheid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strijdigheidofnietigheid.getId().intValue()))
            .andExpect(jsonPath("$.aanduidingstrijdigheidnietigheid").value(DEFAULT_AANDUIDINGSTRIJDIGHEIDNIETIGHEID));
    }

    @Test
    @Transactional
    void getNonExistingStrijdigheidofnietigheid() throws Exception {
        // Get the strijdigheidofnietigheid
        restStrijdigheidofnietigheidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStrijdigheidofnietigheid() throws Exception {
        // Initialize the database
        strijdigheidofnietigheidRepository.saveAndFlush(strijdigheidofnietigheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strijdigheidofnietigheid
        Strijdigheidofnietigheid updatedStrijdigheidofnietigheid = strijdigheidofnietigheidRepository
            .findById(strijdigheidofnietigheid.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedStrijdigheidofnietigheid are not directly saved in db
        em.detach(updatedStrijdigheidofnietigheid);
        updatedStrijdigheidofnietigheid.aanduidingstrijdigheidnietigheid(UPDATED_AANDUIDINGSTRIJDIGHEIDNIETIGHEID);

        restStrijdigheidofnietigheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrijdigheidofnietigheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStrijdigheidofnietigheid))
            )
            .andExpect(status().isOk());

        // Validate the Strijdigheidofnietigheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStrijdigheidofnietigheidToMatchAllProperties(updatedStrijdigheidofnietigheid);
    }

    @Test
    @Transactional
    void putNonExistingStrijdigheidofnietigheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strijdigheidofnietigheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrijdigheidofnietigheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strijdigheidofnietigheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(strijdigheidofnietigheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strijdigheidofnietigheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrijdigheidofnietigheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strijdigheidofnietigheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrijdigheidofnietigheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(strijdigheidofnietigheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strijdigheidofnietigheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrijdigheidofnietigheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strijdigheidofnietigheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrijdigheidofnietigheidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strijdigheidofnietigheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strijdigheidofnietigheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrijdigheidofnietigheidWithPatch() throws Exception {
        // Initialize the database
        strijdigheidofnietigheidRepository.saveAndFlush(strijdigheidofnietigheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strijdigheidofnietigheid using partial update
        Strijdigheidofnietigheid partialUpdatedStrijdigheidofnietigheid = new Strijdigheidofnietigheid();
        partialUpdatedStrijdigheidofnietigheid.setId(strijdigheidofnietigheid.getId());

        restStrijdigheidofnietigheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrijdigheidofnietigheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStrijdigheidofnietigheid))
            )
            .andExpect(status().isOk());

        // Validate the Strijdigheidofnietigheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStrijdigheidofnietigheidUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStrijdigheidofnietigheid, strijdigheidofnietigheid),
            getPersistedStrijdigheidofnietigheid(strijdigheidofnietigheid)
        );
    }

    @Test
    @Transactional
    void fullUpdateStrijdigheidofnietigheidWithPatch() throws Exception {
        // Initialize the database
        strijdigheidofnietigheidRepository.saveAndFlush(strijdigheidofnietigheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strijdigheidofnietigheid using partial update
        Strijdigheidofnietigheid partialUpdatedStrijdigheidofnietigheid = new Strijdigheidofnietigheid();
        partialUpdatedStrijdigheidofnietigheid.setId(strijdigheidofnietigheid.getId());

        partialUpdatedStrijdigheidofnietigheid.aanduidingstrijdigheidnietigheid(UPDATED_AANDUIDINGSTRIJDIGHEIDNIETIGHEID);

        restStrijdigheidofnietigheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrijdigheidofnietigheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStrijdigheidofnietigheid))
            )
            .andExpect(status().isOk());

        // Validate the Strijdigheidofnietigheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStrijdigheidofnietigheidUpdatableFieldsEquals(
            partialUpdatedStrijdigheidofnietigheid,
            getPersistedStrijdigheidofnietigheid(partialUpdatedStrijdigheidofnietigheid)
        );
    }

    @Test
    @Transactional
    void patchNonExistingStrijdigheidofnietigheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strijdigheidofnietigheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrijdigheidofnietigheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strijdigheidofnietigheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(strijdigheidofnietigheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strijdigheidofnietigheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrijdigheidofnietigheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strijdigheidofnietigheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrijdigheidofnietigheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(strijdigheidofnietigheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strijdigheidofnietigheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrijdigheidofnietigheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strijdigheidofnietigheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrijdigheidofnietigheidMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(strijdigheidofnietigheid))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strijdigheidofnietigheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrijdigheidofnietigheid() throws Exception {
        // Initialize the database
        strijdigheidofnietigheidRepository.saveAndFlush(strijdigheidofnietigheid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the strijdigheidofnietigheid
        restStrijdigheidofnietigheidMockMvc
            .perform(delete(ENTITY_API_URL_ID, strijdigheidofnietigheid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return strijdigheidofnietigheidRepository.count();
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

    protected Strijdigheidofnietigheid getPersistedStrijdigheidofnietigheid(Strijdigheidofnietigheid strijdigheidofnietigheid) {
        return strijdigheidofnietigheidRepository.findById(strijdigheidofnietigheid.getId()).orElseThrow();
    }

    protected void assertPersistedStrijdigheidofnietigheidToMatchAllProperties(Strijdigheidofnietigheid expectedStrijdigheidofnietigheid) {
        assertStrijdigheidofnietigheidAllPropertiesEquals(
            expectedStrijdigheidofnietigheid,
            getPersistedStrijdigheidofnietigheid(expectedStrijdigheidofnietigheid)
        );
    }

    protected void assertPersistedStrijdigheidofnietigheidToMatchUpdatableProperties(
        Strijdigheidofnietigheid expectedStrijdigheidofnietigheid
    ) {
        assertStrijdigheidofnietigheidAllUpdatablePropertiesEquals(
            expectedStrijdigheidofnietigheid,
            getPersistedStrijdigheidofnietigheid(expectedStrijdigheidofnietigheid)
        );
    }
}
