package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CorrespondentieadresbuitenlandAsserts.*;
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
import nl.ritense.demo.domain.Correspondentieadresbuitenland;
import nl.ritense.demo.repository.CorrespondentieadresbuitenlandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CorrespondentieadresbuitenlandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CorrespondentieadresbuitenlandResourceIT {

    private static final String DEFAULT_ADRESBUITENLAND_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_5 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_5 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND_6 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND_6 = "BBBBBBBBBB";

    private static final String DEFAULT_LANDCORRESPONDENTIEADRES = "AAAAAAAAAA";
    private static final String UPDATED_LANDCORRESPONDENTIEADRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/correspondentieadresbuitenlands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CorrespondentieadresbuitenlandRepository correspondentieadresbuitenlandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCorrespondentieadresbuitenlandMockMvc;

    private Correspondentieadresbuitenland correspondentieadresbuitenland;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Correspondentieadresbuitenland createEntity(EntityManager em) {
        Correspondentieadresbuitenland correspondentieadresbuitenland = new Correspondentieadresbuitenland()
            .adresbuitenland1(DEFAULT_ADRESBUITENLAND_1)
            .adresbuitenland2(DEFAULT_ADRESBUITENLAND_2)
            .adresbuitenland3(DEFAULT_ADRESBUITENLAND_3)
            .adresbuitenland4(DEFAULT_ADRESBUITENLAND_4)
            .adresbuitenland5(DEFAULT_ADRESBUITENLAND_5)
            .adresbuitenland6(DEFAULT_ADRESBUITENLAND_6)
            .landcorrespondentieadres(DEFAULT_LANDCORRESPONDENTIEADRES);
        return correspondentieadresbuitenland;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Correspondentieadresbuitenland createUpdatedEntity(EntityManager em) {
        Correspondentieadresbuitenland correspondentieadresbuitenland = new Correspondentieadresbuitenland()
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
            .adresbuitenland4(UPDATED_ADRESBUITENLAND_4)
            .adresbuitenland5(UPDATED_ADRESBUITENLAND_5)
            .adresbuitenland6(UPDATED_ADRESBUITENLAND_6)
            .landcorrespondentieadres(UPDATED_LANDCORRESPONDENTIEADRES);
        return correspondentieadresbuitenland;
    }

    @BeforeEach
    public void initTest() {
        correspondentieadresbuitenland = createEntity(em);
    }

    @Test
    @Transactional
    void createCorrespondentieadresbuitenland() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Correspondentieadresbuitenland
        var returnedCorrespondentieadresbuitenland = om.readValue(
            restCorrespondentieadresbuitenlandMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(correspondentieadresbuitenland))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Correspondentieadresbuitenland.class
        );

        // Validate the Correspondentieadresbuitenland in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCorrespondentieadresbuitenlandUpdatableFieldsEquals(
            returnedCorrespondentieadresbuitenland,
            getPersistedCorrespondentieadresbuitenland(returnedCorrespondentieadresbuitenland)
        );
    }

    @Test
    @Transactional
    void createCorrespondentieadresbuitenlandWithExistingId() throws Exception {
        // Create the Correspondentieadresbuitenland with an existing ID
        correspondentieadresbuitenland.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(correspondentieadresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Correspondentieadresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCorrespondentieadresbuitenlands() throws Exception {
        // Initialize the database
        correspondentieadresbuitenlandRepository.saveAndFlush(correspondentieadresbuitenland);

        // Get all the correspondentieadresbuitenlandList
        restCorrespondentieadresbuitenlandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentieadresbuitenland.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresbuitenland1").value(hasItem(DEFAULT_ADRESBUITENLAND_1)))
            .andExpect(jsonPath("$.[*].adresbuitenland2").value(hasItem(DEFAULT_ADRESBUITENLAND_2)))
            .andExpect(jsonPath("$.[*].adresbuitenland3").value(hasItem(DEFAULT_ADRESBUITENLAND_3)))
            .andExpect(jsonPath("$.[*].adresbuitenland4").value(hasItem(DEFAULT_ADRESBUITENLAND_4)))
            .andExpect(jsonPath("$.[*].adresbuitenland5").value(hasItem(DEFAULT_ADRESBUITENLAND_5)))
            .andExpect(jsonPath("$.[*].adresbuitenland6").value(hasItem(DEFAULT_ADRESBUITENLAND_6)))
            .andExpect(jsonPath("$.[*].landcorrespondentieadres").value(hasItem(DEFAULT_LANDCORRESPONDENTIEADRES)));
    }

    @Test
    @Transactional
    void getCorrespondentieadresbuitenland() throws Exception {
        // Initialize the database
        correspondentieadresbuitenlandRepository.saveAndFlush(correspondentieadresbuitenland);

        // Get the correspondentieadresbuitenland
        restCorrespondentieadresbuitenlandMockMvc
            .perform(get(ENTITY_API_URL_ID, correspondentieadresbuitenland.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentieadresbuitenland.getId().intValue()))
            .andExpect(jsonPath("$.adresbuitenland1").value(DEFAULT_ADRESBUITENLAND_1))
            .andExpect(jsonPath("$.adresbuitenland2").value(DEFAULT_ADRESBUITENLAND_2))
            .andExpect(jsonPath("$.adresbuitenland3").value(DEFAULT_ADRESBUITENLAND_3))
            .andExpect(jsonPath("$.adresbuitenland4").value(DEFAULT_ADRESBUITENLAND_4))
            .andExpect(jsonPath("$.adresbuitenland5").value(DEFAULT_ADRESBUITENLAND_5))
            .andExpect(jsonPath("$.adresbuitenland6").value(DEFAULT_ADRESBUITENLAND_6))
            .andExpect(jsonPath("$.landcorrespondentieadres").value(DEFAULT_LANDCORRESPONDENTIEADRES));
    }

    @Test
    @Transactional
    void getNonExistingCorrespondentieadresbuitenland() throws Exception {
        // Get the correspondentieadresbuitenland
        restCorrespondentieadresbuitenlandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCorrespondentieadresbuitenland() throws Exception {
        // Initialize the database
        correspondentieadresbuitenlandRepository.saveAndFlush(correspondentieadresbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the correspondentieadresbuitenland
        Correspondentieadresbuitenland updatedCorrespondentieadresbuitenland = correspondentieadresbuitenlandRepository
            .findById(correspondentieadresbuitenland.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCorrespondentieadresbuitenland are not directly saved in db
        em.detach(updatedCorrespondentieadresbuitenland);
        updatedCorrespondentieadresbuitenland
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
            .adresbuitenland4(UPDATED_ADRESBUITENLAND_4)
            .adresbuitenland5(UPDATED_ADRESBUITENLAND_5)
            .adresbuitenland6(UPDATED_ADRESBUITENLAND_6)
            .landcorrespondentieadres(UPDATED_LANDCORRESPONDENTIEADRES);

        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCorrespondentieadresbuitenland.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCorrespondentieadresbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Correspondentieadresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCorrespondentieadresbuitenlandToMatchAllProperties(updatedCorrespondentieadresbuitenland);
    }

    @Test
    @Transactional
    void putNonExistingCorrespondentieadresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        correspondentieadresbuitenland.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, correspondentieadresbuitenland.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(correspondentieadresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Correspondentieadresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCorrespondentieadresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        correspondentieadresbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(correspondentieadresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Correspondentieadresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCorrespondentieadresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        correspondentieadresbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(correspondentieadresbuitenland))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Correspondentieadresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCorrespondentieadresbuitenlandWithPatch() throws Exception {
        // Initialize the database
        correspondentieadresbuitenlandRepository.saveAndFlush(correspondentieadresbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the correspondentieadresbuitenland using partial update
        Correspondentieadresbuitenland partialUpdatedCorrespondentieadresbuitenland = new Correspondentieadresbuitenland();
        partialUpdatedCorrespondentieadresbuitenland.setId(correspondentieadresbuitenland.getId());

        partialUpdatedCorrespondentieadresbuitenland
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland5(UPDATED_ADRESBUITENLAND_5)
            .adresbuitenland6(UPDATED_ADRESBUITENLAND_6)
            .landcorrespondentieadres(UPDATED_LANDCORRESPONDENTIEADRES);

        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCorrespondentieadresbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCorrespondentieadresbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Correspondentieadresbuitenland in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCorrespondentieadresbuitenlandUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCorrespondentieadresbuitenland, correspondentieadresbuitenland),
            getPersistedCorrespondentieadresbuitenland(correspondentieadresbuitenland)
        );
    }

    @Test
    @Transactional
    void fullUpdateCorrespondentieadresbuitenlandWithPatch() throws Exception {
        // Initialize the database
        correspondentieadresbuitenlandRepository.saveAndFlush(correspondentieadresbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the correspondentieadresbuitenland using partial update
        Correspondentieadresbuitenland partialUpdatedCorrespondentieadresbuitenland = new Correspondentieadresbuitenland();
        partialUpdatedCorrespondentieadresbuitenland.setId(correspondentieadresbuitenland.getId());

        partialUpdatedCorrespondentieadresbuitenland
            .adresbuitenland1(UPDATED_ADRESBUITENLAND_1)
            .adresbuitenland2(UPDATED_ADRESBUITENLAND_2)
            .adresbuitenland3(UPDATED_ADRESBUITENLAND_3)
            .adresbuitenland4(UPDATED_ADRESBUITENLAND_4)
            .adresbuitenland5(UPDATED_ADRESBUITENLAND_5)
            .adresbuitenland6(UPDATED_ADRESBUITENLAND_6)
            .landcorrespondentieadres(UPDATED_LANDCORRESPONDENTIEADRES);

        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCorrespondentieadresbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCorrespondentieadresbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Correspondentieadresbuitenland in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCorrespondentieadresbuitenlandUpdatableFieldsEquals(
            partialUpdatedCorrespondentieadresbuitenland,
            getPersistedCorrespondentieadresbuitenland(partialUpdatedCorrespondentieadresbuitenland)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCorrespondentieadresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        correspondentieadresbuitenland.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, correspondentieadresbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(correspondentieadresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Correspondentieadresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCorrespondentieadresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        correspondentieadresbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(correspondentieadresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Correspondentieadresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCorrespondentieadresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        correspondentieadresbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorrespondentieadresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(correspondentieadresbuitenland))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Correspondentieadresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCorrespondentieadresbuitenland() throws Exception {
        // Initialize the database
        correspondentieadresbuitenlandRepository.saveAndFlush(correspondentieadresbuitenland);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the correspondentieadresbuitenland
        restCorrespondentieadresbuitenlandMockMvc
            .perform(delete(ENTITY_API_URL_ID, correspondentieadresbuitenland.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return correspondentieadresbuitenlandRepository.count();
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

    protected Correspondentieadresbuitenland getPersistedCorrespondentieadresbuitenland(
        Correspondentieadresbuitenland correspondentieadresbuitenland
    ) {
        return correspondentieadresbuitenlandRepository.findById(correspondentieadresbuitenland.getId()).orElseThrow();
    }

    protected void assertPersistedCorrespondentieadresbuitenlandToMatchAllProperties(
        Correspondentieadresbuitenland expectedCorrespondentieadresbuitenland
    ) {
        assertCorrespondentieadresbuitenlandAllPropertiesEquals(
            expectedCorrespondentieadresbuitenland,
            getPersistedCorrespondentieadresbuitenland(expectedCorrespondentieadresbuitenland)
        );
    }

    protected void assertPersistedCorrespondentieadresbuitenlandToMatchUpdatableProperties(
        Correspondentieadresbuitenland expectedCorrespondentieadresbuitenland
    ) {
        assertCorrespondentieadresbuitenlandAllUpdatablePropertiesEquals(
            expectedCorrespondentieadresbuitenland,
            getPersistedCorrespondentieadresbuitenland(expectedCorrespondentieadresbuitenland)
        );
    }
}
