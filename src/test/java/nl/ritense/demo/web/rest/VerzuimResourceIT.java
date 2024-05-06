package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerzuimAsserts.*;
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
import nl.ritense.demo.domain.Verzuim;
import nl.ritense.demo.repository.VerzuimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerzuimResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerzuimResourceIT {

    private static final String DEFAULT_DATUMTIJDEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJDEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTIJDSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJDSTART = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verzuims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerzuimRepository verzuimRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerzuimMockMvc;

    private Verzuim verzuim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzuim createEntity(EntityManager em) {
        Verzuim verzuim = new Verzuim().datumtijdeinde(DEFAULT_DATUMTIJDEINDE).datumtijdstart(DEFAULT_DATUMTIJDSTART);
        return verzuim;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzuim createUpdatedEntity(EntityManager em) {
        Verzuim verzuim = new Verzuim().datumtijdeinde(UPDATED_DATUMTIJDEINDE).datumtijdstart(UPDATED_DATUMTIJDSTART);
        return verzuim;
    }

    @BeforeEach
    public void initTest() {
        verzuim = createEntity(em);
    }

    @Test
    @Transactional
    void createVerzuim() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verzuim
        var returnedVerzuim = om.readValue(
            restVerzuimMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuim)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verzuim.class
        );

        // Validate the Verzuim in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerzuimUpdatableFieldsEquals(returnedVerzuim, getPersistedVerzuim(returnedVerzuim));
    }

    @Test
    @Transactional
    void createVerzuimWithExistingId() throws Exception {
        // Create the Verzuim with an existing ID
        verzuim.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerzuimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuim)))
            .andExpect(status().isBadRequest());

        // Validate the Verzuim in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerzuims() throws Exception {
        // Initialize the database
        verzuimRepository.saveAndFlush(verzuim);

        // Get all the verzuimList
        restVerzuimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verzuim.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumtijdeinde").value(hasItem(DEFAULT_DATUMTIJDEINDE)))
            .andExpect(jsonPath("$.[*].datumtijdstart").value(hasItem(DEFAULT_DATUMTIJDSTART)));
    }

    @Test
    @Transactional
    void getVerzuim() throws Exception {
        // Initialize the database
        verzuimRepository.saveAndFlush(verzuim);

        // Get the verzuim
        restVerzuimMockMvc
            .perform(get(ENTITY_API_URL_ID, verzuim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verzuim.getId().intValue()))
            .andExpect(jsonPath("$.datumtijdeinde").value(DEFAULT_DATUMTIJDEINDE))
            .andExpect(jsonPath("$.datumtijdstart").value(DEFAULT_DATUMTIJDSTART));
    }

    @Test
    @Transactional
    void getNonExistingVerzuim() throws Exception {
        // Get the verzuim
        restVerzuimMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerzuim() throws Exception {
        // Initialize the database
        verzuimRepository.saveAndFlush(verzuim);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuim
        Verzuim updatedVerzuim = verzuimRepository.findById(verzuim.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerzuim are not directly saved in db
        em.detach(updatedVerzuim);
        updatedVerzuim.datumtijdeinde(UPDATED_DATUMTIJDEINDE).datumtijdstart(UPDATED_DATUMTIJDSTART);

        restVerzuimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerzuim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerzuim))
            )
            .andExpect(status().isOk());

        // Validate the Verzuim in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerzuimToMatchAllProperties(updatedVerzuim);
    }

    @Test
    @Transactional
    void putNonExistingVerzuim() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuim.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzuimMockMvc
            .perform(put(ENTITY_API_URL_ID, verzuim.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuim)))
            .andExpect(status().isBadRequest());

        // Validate the Verzuim in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerzuim() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuim.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verzuim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuim in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerzuim() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuim.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzuim in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerzuimWithPatch() throws Exception {
        // Initialize the database
        verzuimRepository.saveAndFlush(verzuim);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuim using partial update
        Verzuim partialUpdatedVerzuim = new Verzuim();
        partialUpdatedVerzuim.setId(verzuim.getId());

        partialUpdatedVerzuim.datumtijdeinde(UPDATED_DATUMTIJDEINDE).datumtijdstart(UPDATED_DATUMTIJDSTART);

        restVerzuimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzuim.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzuim))
            )
            .andExpect(status().isOk());

        // Validate the Verzuim in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzuimUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVerzuim, verzuim), getPersistedVerzuim(verzuim));
    }

    @Test
    @Transactional
    void fullUpdateVerzuimWithPatch() throws Exception {
        // Initialize the database
        verzuimRepository.saveAndFlush(verzuim);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuim using partial update
        Verzuim partialUpdatedVerzuim = new Verzuim();
        partialUpdatedVerzuim.setId(verzuim.getId());

        partialUpdatedVerzuim.datumtijdeinde(UPDATED_DATUMTIJDEINDE).datumtijdstart(UPDATED_DATUMTIJDSTART);

        restVerzuimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzuim.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzuim))
            )
            .andExpect(status().isOk());

        // Validate the Verzuim in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzuimUpdatableFieldsEquals(partialUpdatedVerzuim, getPersistedVerzuim(partialUpdatedVerzuim));
    }

    @Test
    @Transactional
    void patchNonExistingVerzuim() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuim.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzuimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verzuim.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verzuim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuim in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerzuim() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuim.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verzuim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuim in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerzuim() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuim.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verzuim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzuim in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerzuim() throws Exception {
        // Initialize the database
        verzuimRepository.saveAndFlush(verzuim);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verzuim
        restVerzuimMockMvc
            .perform(delete(ENTITY_API_URL_ID, verzuim.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verzuimRepository.count();
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

    protected Verzuim getPersistedVerzuim(Verzuim verzuim) {
        return verzuimRepository.findById(verzuim.getId()).orElseThrow();
    }

    protected void assertPersistedVerzuimToMatchAllProperties(Verzuim expectedVerzuim) {
        assertVerzuimAllPropertiesEquals(expectedVerzuim, getPersistedVerzuim(expectedVerzuim));
    }

    protected void assertPersistedVerzuimToMatchUpdatableProperties(Verzuim expectedVerzuim) {
        assertVerzuimAllUpdatablePropertiesEquals(expectedVerzuim, getPersistedVerzuim(expectedVerzuim));
    }
}
