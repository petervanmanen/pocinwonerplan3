package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerkeersdrempelAsserts.*;
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
import nl.ritense.demo.domain.Verkeersdrempel;
import nl.ritense.demo.repository.VerkeersdrempelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerkeersdrempelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerkeersdrempelResourceIT {

    private static final String DEFAULT_ONTWERPSNELHEID = "AAAAAAAAAA";
    private static final String UPDATED_ONTWERPSNELHEID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verkeersdrempels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerkeersdrempelRepository verkeersdrempelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerkeersdrempelMockMvc;

    private Verkeersdrempel verkeersdrempel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkeersdrempel createEntity(EntityManager em) {
        Verkeersdrempel verkeersdrempel = new Verkeersdrempel()
            .ontwerpsnelheid(DEFAULT_ONTWERPSNELHEID)
            .type(DEFAULT_TYPE)
            .typeplus(DEFAULT_TYPEPLUS);
        return verkeersdrempel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verkeersdrempel createUpdatedEntity(EntityManager em) {
        Verkeersdrempel verkeersdrempel = new Verkeersdrempel()
            .ontwerpsnelheid(UPDATED_ONTWERPSNELHEID)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);
        return verkeersdrempel;
    }

    @BeforeEach
    public void initTest() {
        verkeersdrempel = createEntity(em);
    }

    @Test
    @Transactional
    void createVerkeersdrempel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verkeersdrempel
        var returnedVerkeersdrempel = om.readValue(
            restVerkeersdrempelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeersdrempel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verkeersdrempel.class
        );

        // Validate the Verkeersdrempel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerkeersdrempelUpdatableFieldsEquals(returnedVerkeersdrempel, getPersistedVerkeersdrempel(returnedVerkeersdrempel));
    }

    @Test
    @Transactional
    void createVerkeersdrempelWithExistingId() throws Exception {
        // Create the Verkeersdrempel with an existing ID
        verkeersdrempel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerkeersdrempelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeersdrempel)))
            .andExpect(status().isBadRequest());

        // Validate the Verkeersdrempel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerkeersdrempels() throws Exception {
        // Initialize the database
        verkeersdrempelRepository.saveAndFlush(verkeersdrempel);

        // Get all the verkeersdrempelList
        restVerkeersdrempelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verkeersdrempel.getId().intValue())))
            .andExpect(jsonPath("$.[*].ontwerpsnelheid").value(hasItem(DEFAULT_ONTWERPSNELHEID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)));
    }

    @Test
    @Transactional
    void getVerkeersdrempel() throws Exception {
        // Initialize the database
        verkeersdrempelRepository.saveAndFlush(verkeersdrempel);

        // Get the verkeersdrempel
        restVerkeersdrempelMockMvc
            .perform(get(ENTITY_API_URL_ID, verkeersdrempel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verkeersdrempel.getId().intValue()))
            .andExpect(jsonPath("$.ontwerpsnelheid").value(DEFAULT_ONTWERPSNELHEID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS));
    }

    @Test
    @Transactional
    void getNonExistingVerkeersdrempel() throws Exception {
        // Get the verkeersdrempel
        restVerkeersdrempelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerkeersdrempel() throws Exception {
        // Initialize the database
        verkeersdrempelRepository.saveAndFlush(verkeersdrempel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeersdrempel
        Verkeersdrempel updatedVerkeersdrempel = verkeersdrempelRepository.findById(verkeersdrempel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerkeersdrempel are not directly saved in db
        em.detach(updatedVerkeersdrempel);
        updatedVerkeersdrempel.ontwerpsnelheid(UPDATED_ONTWERPSNELHEID).type(UPDATED_TYPE).typeplus(UPDATED_TYPEPLUS);

        restVerkeersdrempelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerkeersdrempel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerkeersdrempel))
            )
            .andExpect(status().isOk());

        // Validate the Verkeersdrempel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerkeersdrempelToMatchAllProperties(updatedVerkeersdrempel);
    }

    @Test
    @Transactional
    void putNonExistingVerkeersdrempel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersdrempel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerkeersdrempelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verkeersdrempel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verkeersdrempel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeersdrempel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerkeersdrempel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersdrempel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeersdrempelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verkeersdrempel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeersdrempel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerkeersdrempel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersdrempel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeersdrempelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verkeersdrempel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verkeersdrempel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerkeersdrempelWithPatch() throws Exception {
        // Initialize the database
        verkeersdrempelRepository.saveAndFlush(verkeersdrempel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeersdrempel using partial update
        Verkeersdrempel partialUpdatedVerkeersdrempel = new Verkeersdrempel();
        partialUpdatedVerkeersdrempel.setId(verkeersdrempel.getId());

        partialUpdatedVerkeersdrempel.ontwerpsnelheid(UPDATED_ONTWERPSNELHEID).type(UPDATED_TYPE);

        restVerkeersdrempelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerkeersdrempel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerkeersdrempel))
            )
            .andExpect(status().isOk());

        // Validate the Verkeersdrempel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerkeersdrempelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerkeersdrempel, verkeersdrempel),
            getPersistedVerkeersdrempel(verkeersdrempel)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerkeersdrempelWithPatch() throws Exception {
        // Initialize the database
        verkeersdrempelRepository.saveAndFlush(verkeersdrempel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verkeersdrempel using partial update
        Verkeersdrempel partialUpdatedVerkeersdrempel = new Verkeersdrempel();
        partialUpdatedVerkeersdrempel.setId(verkeersdrempel.getId());

        partialUpdatedVerkeersdrempel.ontwerpsnelheid(UPDATED_ONTWERPSNELHEID).type(UPDATED_TYPE).typeplus(UPDATED_TYPEPLUS);

        restVerkeersdrempelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerkeersdrempel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerkeersdrempel))
            )
            .andExpect(status().isOk());

        // Validate the Verkeersdrempel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerkeersdrempelUpdatableFieldsEquals(
            partialUpdatedVerkeersdrempel,
            getPersistedVerkeersdrempel(partialUpdatedVerkeersdrempel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerkeersdrempel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersdrempel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerkeersdrempelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verkeersdrempel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verkeersdrempel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeersdrempel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerkeersdrempel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersdrempel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeersdrempelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verkeersdrempel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verkeersdrempel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerkeersdrempel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verkeersdrempel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerkeersdrempelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verkeersdrempel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verkeersdrempel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerkeersdrempel() throws Exception {
        // Initialize the database
        verkeersdrempelRepository.saveAndFlush(verkeersdrempel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verkeersdrempel
        restVerkeersdrempelMockMvc
            .perform(delete(ENTITY_API_URL_ID, verkeersdrempel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verkeersdrempelRepository.count();
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

    protected Verkeersdrempel getPersistedVerkeersdrempel(Verkeersdrempel verkeersdrempel) {
        return verkeersdrempelRepository.findById(verkeersdrempel.getId()).orElseThrow();
    }

    protected void assertPersistedVerkeersdrempelToMatchAllProperties(Verkeersdrempel expectedVerkeersdrempel) {
        assertVerkeersdrempelAllPropertiesEquals(expectedVerkeersdrempel, getPersistedVerkeersdrempel(expectedVerkeersdrempel));
    }

    protected void assertPersistedVerkeersdrempelToMatchUpdatableProperties(Verkeersdrempel expectedVerkeersdrempel) {
        assertVerkeersdrempelAllUpdatablePropertiesEquals(expectedVerkeersdrempel, getPersistedVerkeersdrempel(expectedVerkeersdrempel));
    }
}
