package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StrooirouteuitvoeringAsserts.*;
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
import nl.ritense.demo.domain.Strooirouteuitvoering;
import nl.ritense.demo.repository.StrooirouteuitvoeringRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StrooirouteuitvoeringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrooirouteuitvoeringResourceIT {

    private static final String DEFAULT_GEPLANDEINDE = "AAAAAAAAAA";
    private static final String UPDATED_GEPLANDEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_GEPLANDSTART = "AAAAAAAAAA";
    private static final String UPDATED_GEPLANDSTART = "BBBBBBBBBB";

    private static final String DEFAULT_EROUTE = "AAAAAAAAAA";
    private static final String UPDATED_EROUTE = "BBBBBBBBBB";

    private static final String DEFAULT_WERKELIJKEINDE = "AAAAAAAAAA";
    private static final String UPDATED_WERKELIJKEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_WERKELIJKESTART = "AAAAAAAAAA";
    private static final String UPDATED_WERKELIJKESTART = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/strooirouteuitvoerings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StrooirouteuitvoeringRepository strooirouteuitvoeringRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrooirouteuitvoeringMockMvc;

    private Strooirouteuitvoering strooirouteuitvoering;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strooirouteuitvoering createEntity(EntityManager em) {
        Strooirouteuitvoering strooirouteuitvoering = new Strooirouteuitvoering()
            .geplandeinde(DEFAULT_GEPLANDEINDE)
            .geplandstart(DEFAULT_GEPLANDSTART)
            .eroute(DEFAULT_EROUTE)
            .werkelijkeinde(DEFAULT_WERKELIJKEINDE)
            .werkelijkestart(DEFAULT_WERKELIJKESTART);
        return strooirouteuitvoering;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strooirouteuitvoering createUpdatedEntity(EntityManager em) {
        Strooirouteuitvoering strooirouteuitvoering = new Strooirouteuitvoering()
            .geplandeinde(UPDATED_GEPLANDEINDE)
            .geplandstart(UPDATED_GEPLANDSTART)
            .eroute(UPDATED_EROUTE)
            .werkelijkeinde(UPDATED_WERKELIJKEINDE)
            .werkelijkestart(UPDATED_WERKELIJKESTART);
        return strooirouteuitvoering;
    }

    @BeforeEach
    public void initTest() {
        strooirouteuitvoering = createEntity(em);
    }

    @Test
    @Transactional
    void createStrooirouteuitvoering() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Strooirouteuitvoering
        var returnedStrooirouteuitvoering = om.readValue(
            restStrooirouteuitvoeringMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooirouteuitvoering)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Strooirouteuitvoering.class
        );

        // Validate the Strooirouteuitvoering in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStrooirouteuitvoeringUpdatableFieldsEquals(
            returnedStrooirouteuitvoering,
            getPersistedStrooirouteuitvoering(returnedStrooirouteuitvoering)
        );
    }

    @Test
    @Transactional
    void createStrooirouteuitvoeringWithExistingId() throws Exception {
        // Create the Strooirouteuitvoering with an existing ID
        strooirouteuitvoering.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrooirouteuitvoeringMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooirouteuitvoering)))
            .andExpect(status().isBadRequest());

        // Validate the Strooirouteuitvoering in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStrooirouteuitvoerings() throws Exception {
        // Initialize the database
        strooirouteuitvoeringRepository.saveAndFlush(strooirouteuitvoering);

        // Get all the strooirouteuitvoeringList
        restStrooirouteuitvoeringMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strooirouteuitvoering.getId().intValue())))
            .andExpect(jsonPath("$.[*].geplandeinde").value(hasItem(DEFAULT_GEPLANDEINDE)))
            .andExpect(jsonPath("$.[*].geplandstart").value(hasItem(DEFAULT_GEPLANDSTART)))
            .andExpect(jsonPath("$.[*].eroute").value(hasItem(DEFAULT_EROUTE)))
            .andExpect(jsonPath("$.[*].werkelijkeinde").value(hasItem(DEFAULT_WERKELIJKEINDE)))
            .andExpect(jsonPath("$.[*].werkelijkestart").value(hasItem(DEFAULT_WERKELIJKESTART)));
    }

    @Test
    @Transactional
    void getStrooirouteuitvoering() throws Exception {
        // Initialize the database
        strooirouteuitvoeringRepository.saveAndFlush(strooirouteuitvoering);

        // Get the strooirouteuitvoering
        restStrooirouteuitvoeringMockMvc
            .perform(get(ENTITY_API_URL_ID, strooirouteuitvoering.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strooirouteuitvoering.getId().intValue()))
            .andExpect(jsonPath("$.geplandeinde").value(DEFAULT_GEPLANDEINDE))
            .andExpect(jsonPath("$.geplandstart").value(DEFAULT_GEPLANDSTART))
            .andExpect(jsonPath("$.eroute").value(DEFAULT_EROUTE))
            .andExpect(jsonPath("$.werkelijkeinde").value(DEFAULT_WERKELIJKEINDE))
            .andExpect(jsonPath("$.werkelijkestart").value(DEFAULT_WERKELIJKESTART));
    }

    @Test
    @Transactional
    void getNonExistingStrooirouteuitvoering() throws Exception {
        // Get the strooirouteuitvoering
        restStrooirouteuitvoeringMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStrooirouteuitvoering() throws Exception {
        // Initialize the database
        strooirouteuitvoeringRepository.saveAndFlush(strooirouteuitvoering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooirouteuitvoering
        Strooirouteuitvoering updatedStrooirouteuitvoering = strooirouteuitvoeringRepository
            .findById(strooirouteuitvoering.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedStrooirouteuitvoering are not directly saved in db
        em.detach(updatedStrooirouteuitvoering);
        updatedStrooirouteuitvoering
            .geplandeinde(UPDATED_GEPLANDEINDE)
            .geplandstart(UPDATED_GEPLANDSTART)
            .eroute(UPDATED_EROUTE)
            .werkelijkeinde(UPDATED_WERKELIJKEINDE)
            .werkelijkestart(UPDATED_WERKELIJKESTART);

        restStrooirouteuitvoeringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrooirouteuitvoering.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStrooirouteuitvoering))
            )
            .andExpect(status().isOk());

        // Validate the Strooirouteuitvoering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStrooirouteuitvoeringToMatchAllProperties(updatedStrooirouteuitvoering);
    }

    @Test
    @Transactional
    void putNonExistingStrooirouteuitvoering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooirouteuitvoering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrooirouteuitvoeringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strooirouteuitvoering.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(strooirouteuitvoering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooirouteuitvoering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrooirouteuitvoering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooirouteuitvoering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooirouteuitvoeringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(strooirouteuitvoering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooirouteuitvoering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrooirouteuitvoering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooirouteuitvoering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooirouteuitvoeringMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooirouteuitvoering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strooirouteuitvoering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrooirouteuitvoeringWithPatch() throws Exception {
        // Initialize the database
        strooirouteuitvoeringRepository.saveAndFlush(strooirouteuitvoering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooirouteuitvoering using partial update
        Strooirouteuitvoering partialUpdatedStrooirouteuitvoering = new Strooirouteuitvoering();
        partialUpdatedStrooirouteuitvoering.setId(strooirouteuitvoering.getId());

        partialUpdatedStrooirouteuitvoering
            .geplandstart(UPDATED_GEPLANDSTART)
            .eroute(UPDATED_EROUTE)
            .werkelijkeinde(UPDATED_WERKELIJKEINDE)
            .werkelijkestart(UPDATED_WERKELIJKESTART);

        restStrooirouteuitvoeringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrooirouteuitvoering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStrooirouteuitvoering))
            )
            .andExpect(status().isOk());

        // Validate the Strooirouteuitvoering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStrooirouteuitvoeringUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStrooirouteuitvoering, strooirouteuitvoering),
            getPersistedStrooirouteuitvoering(strooirouteuitvoering)
        );
    }

    @Test
    @Transactional
    void fullUpdateStrooirouteuitvoeringWithPatch() throws Exception {
        // Initialize the database
        strooirouteuitvoeringRepository.saveAndFlush(strooirouteuitvoering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooirouteuitvoering using partial update
        Strooirouteuitvoering partialUpdatedStrooirouteuitvoering = new Strooirouteuitvoering();
        partialUpdatedStrooirouteuitvoering.setId(strooirouteuitvoering.getId());

        partialUpdatedStrooirouteuitvoering
            .geplandeinde(UPDATED_GEPLANDEINDE)
            .geplandstart(UPDATED_GEPLANDSTART)
            .eroute(UPDATED_EROUTE)
            .werkelijkeinde(UPDATED_WERKELIJKEINDE)
            .werkelijkestart(UPDATED_WERKELIJKESTART);

        restStrooirouteuitvoeringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrooirouteuitvoering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStrooirouteuitvoering))
            )
            .andExpect(status().isOk());

        // Validate the Strooirouteuitvoering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStrooirouteuitvoeringUpdatableFieldsEquals(
            partialUpdatedStrooirouteuitvoering,
            getPersistedStrooirouteuitvoering(partialUpdatedStrooirouteuitvoering)
        );
    }

    @Test
    @Transactional
    void patchNonExistingStrooirouteuitvoering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooirouteuitvoering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrooirouteuitvoeringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strooirouteuitvoering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(strooirouteuitvoering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooirouteuitvoering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrooirouteuitvoering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooirouteuitvoering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooirouteuitvoeringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(strooirouteuitvoering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooirouteuitvoering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrooirouteuitvoering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooirouteuitvoering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooirouteuitvoeringMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(strooirouteuitvoering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strooirouteuitvoering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrooirouteuitvoering() throws Exception {
        // Initialize the database
        strooirouteuitvoeringRepository.saveAndFlush(strooirouteuitvoering);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the strooirouteuitvoering
        restStrooirouteuitvoeringMockMvc
            .perform(delete(ENTITY_API_URL_ID, strooirouteuitvoering.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return strooirouteuitvoeringRepository.count();
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

    protected Strooirouteuitvoering getPersistedStrooirouteuitvoering(Strooirouteuitvoering strooirouteuitvoering) {
        return strooirouteuitvoeringRepository.findById(strooirouteuitvoering.getId()).orElseThrow();
    }

    protected void assertPersistedStrooirouteuitvoeringToMatchAllProperties(Strooirouteuitvoering expectedStrooirouteuitvoering) {
        assertStrooirouteuitvoeringAllPropertiesEquals(
            expectedStrooirouteuitvoering,
            getPersistedStrooirouteuitvoering(expectedStrooirouteuitvoering)
        );
    }

    protected void assertPersistedStrooirouteuitvoeringToMatchUpdatableProperties(Strooirouteuitvoering expectedStrooirouteuitvoering) {
        assertStrooirouteuitvoeringAllUpdatablePropertiesEquals(
            expectedStrooirouteuitvoering,
            getPersistedStrooirouteuitvoering(expectedStrooirouteuitvoering)
        );
    }
}
