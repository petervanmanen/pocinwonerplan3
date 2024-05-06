package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PutdekselAsserts.*;
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
import nl.ritense.demo.domain.Putdeksel;
import nl.ritense.demo.repository.PutdekselRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PutdekselResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PutdekselResourceIT {

    private static final String DEFAULT_DIAMETER = "AAAAAAAAAA";
    private static final String UPDATED_DIAMETER = "BBBBBBBBBB";

    private static final String DEFAULT_PUT = "AAAAAAAAAA";
    private static final String UPDATED_PUT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VORM = "AAAAAAAAAA";
    private static final String UPDATED_VORM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/putdeksels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PutdekselRepository putdekselRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPutdekselMockMvc;

    private Putdeksel putdeksel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Putdeksel createEntity(EntityManager em) {
        Putdeksel putdeksel = new Putdeksel().diameter(DEFAULT_DIAMETER).put(DEFAULT_PUT).type(DEFAULT_TYPE).vorm(DEFAULT_VORM);
        return putdeksel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Putdeksel createUpdatedEntity(EntityManager em) {
        Putdeksel putdeksel = new Putdeksel().diameter(UPDATED_DIAMETER).put(UPDATED_PUT).type(UPDATED_TYPE).vorm(UPDATED_VORM);
        return putdeksel;
    }

    @BeforeEach
    public void initTest() {
        putdeksel = createEntity(em);
    }

    @Test
    @Transactional
    void createPutdeksel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Putdeksel
        var returnedPutdeksel = om.readValue(
            restPutdekselMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(putdeksel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Putdeksel.class
        );

        // Validate the Putdeksel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPutdekselUpdatableFieldsEquals(returnedPutdeksel, getPersistedPutdeksel(returnedPutdeksel));
    }

    @Test
    @Transactional
    void createPutdekselWithExistingId() throws Exception {
        // Create the Putdeksel with an existing ID
        putdeksel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPutdekselMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(putdeksel)))
            .andExpect(status().isBadRequest());

        // Validate the Putdeksel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPutdeksels() throws Exception {
        // Initialize the database
        putdekselRepository.saveAndFlush(putdeksel);

        // Get all the putdekselList
        restPutdekselMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(putdeksel.getId().intValue())))
            .andExpect(jsonPath("$.[*].diameter").value(hasItem(DEFAULT_DIAMETER)))
            .andExpect(jsonPath("$.[*].put").value(hasItem(DEFAULT_PUT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].vorm").value(hasItem(DEFAULT_VORM)));
    }

    @Test
    @Transactional
    void getPutdeksel() throws Exception {
        // Initialize the database
        putdekselRepository.saveAndFlush(putdeksel);

        // Get the putdeksel
        restPutdekselMockMvc
            .perform(get(ENTITY_API_URL_ID, putdeksel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(putdeksel.getId().intValue()))
            .andExpect(jsonPath("$.diameter").value(DEFAULT_DIAMETER))
            .andExpect(jsonPath("$.put").value(DEFAULT_PUT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.vorm").value(DEFAULT_VORM));
    }

    @Test
    @Transactional
    void getNonExistingPutdeksel() throws Exception {
        // Get the putdeksel
        restPutdekselMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPutdeksel() throws Exception {
        // Initialize the database
        putdekselRepository.saveAndFlush(putdeksel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the putdeksel
        Putdeksel updatedPutdeksel = putdekselRepository.findById(putdeksel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPutdeksel are not directly saved in db
        em.detach(updatedPutdeksel);
        updatedPutdeksel.diameter(UPDATED_DIAMETER).put(UPDATED_PUT).type(UPDATED_TYPE).vorm(UPDATED_VORM);

        restPutdekselMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPutdeksel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPutdeksel))
            )
            .andExpect(status().isOk());

        // Validate the Putdeksel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPutdekselToMatchAllProperties(updatedPutdeksel);
    }

    @Test
    @Transactional
    void putNonExistingPutdeksel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        putdeksel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPutdekselMockMvc
            .perform(
                put(ENTITY_API_URL_ID, putdeksel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(putdeksel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Putdeksel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPutdeksel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        putdeksel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPutdekselMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(putdeksel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Putdeksel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPutdeksel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        putdeksel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPutdekselMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(putdeksel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Putdeksel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePutdekselWithPatch() throws Exception {
        // Initialize the database
        putdekselRepository.saveAndFlush(putdeksel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the putdeksel using partial update
        Putdeksel partialUpdatedPutdeksel = new Putdeksel();
        partialUpdatedPutdeksel.setId(putdeksel.getId());

        partialUpdatedPutdeksel.diameter(UPDATED_DIAMETER).put(UPDATED_PUT).vorm(UPDATED_VORM);

        restPutdekselMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPutdeksel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPutdeksel))
            )
            .andExpect(status().isOk());

        // Validate the Putdeksel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPutdekselUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPutdeksel, putdeksel),
            getPersistedPutdeksel(putdeksel)
        );
    }

    @Test
    @Transactional
    void fullUpdatePutdekselWithPatch() throws Exception {
        // Initialize the database
        putdekselRepository.saveAndFlush(putdeksel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the putdeksel using partial update
        Putdeksel partialUpdatedPutdeksel = new Putdeksel();
        partialUpdatedPutdeksel.setId(putdeksel.getId());

        partialUpdatedPutdeksel.diameter(UPDATED_DIAMETER).put(UPDATED_PUT).type(UPDATED_TYPE).vorm(UPDATED_VORM);

        restPutdekselMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPutdeksel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPutdeksel))
            )
            .andExpect(status().isOk());

        // Validate the Putdeksel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPutdekselUpdatableFieldsEquals(partialUpdatedPutdeksel, getPersistedPutdeksel(partialUpdatedPutdeksel));
    }

    @Test
    @Transactional
    void patchNonExistingPutdeksel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        putdeksel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPutdekselMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, putdeksel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(putdeksel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Putdeksel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPutdeksel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        putdeksel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPutdekselMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(putdeksel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Putdeksel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPutdeksel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        putdeksel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPutdekselMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(putdeksel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Putdeksel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePutdeksel() throws Exception {
        // Initialize the database
        putdekselRepository.saveAndFlush(putdeksel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the putdeksel
        restPutdekselMockMvc
            .perform(delete(ENTITY_API_URL_ID, putdeksel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return putdekselRepository.count();
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

    protected Putdeksel getPersistedPutdeksel(Putdeksel putdeksel) {
        return putdekselRepository.findById(putdeksel.getId()).orElseThrow();
    }

    protected void assertPersistedPutdekselToMatchAllProperties(Putdeksel expectedPutdeksel) {
        assertPutdekselAllPropertiesEquals(expectedPutdeksel, getPersistedPutdeksel(expectedPutdeksel));
    }

    protected void assertPersistedPutdekselToMatchUpdatableProperties(Putdeksel expectedPutdeksel) {
        assertPutdekselAllUpdatablePropertiesEquals(expectedPutdeksel, getPersistedPutdeksel(expectedPutdeksel));
    }
}
