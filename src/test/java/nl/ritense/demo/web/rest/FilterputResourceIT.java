package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FilterputAsserts.*;
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
import nl.ritense.demo.domain.Filterput;
import nl.ritense.demo.repository.FilterputRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FilterputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FilterputResourceIT {

    private static final String DEFAULT_DRAIN = "AAAAAAAAAA";
    private static final String UPDATED_DRAIN = "BBBBBBBBBB";

    private static final String DEFAULT_RISICOGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_RISICOGEBIED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/filterputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FilterputRepository filterputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilterputMockMvc;

    private Filterput filterput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filterput createEntity(EntityManager em) {
        Filterput filterput = new Filterput().drain(DEFAULT_DRAIN).risicogebied(DEFAULT_RISICOGEBIED);
        return filterput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filterput createUpdatedEntity(EntityManager em) {
        Filterput filterput = new Filterput().drain(UPDATED_DRAIN).risicogebied(UPDATED_RISICOGEBIED);
        return filterput;
    }

    @BeforeEach
    public void initTest() {
        filterput = createEntity(em);
    }

    @Test
    @Transactional
    void createFilterput() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Filterput
        var returnedFilterput = om.readValue(
            restFilterputMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(filterput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Filterput.class
        );

        // Validate the Filterput in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFilterputUpdatableFieldsEquals(returnedFilterput, getPersistedFilterput(returnedFilterput));
    }

    @Test
    @Transactional
    void createFilterputWithExistingId() throws Exception {
        // Create the Filterput with an existing ID
        filterput.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(filterput)))
            .andExpect(status().isBadRequest());

        // Validate the Filterput in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFilterputs() throws Exception {
        // Initialize the database
        filterputRepository.saveAndFlush(filterput);

        // Get all the filterputList
        restFilterputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filterput.getId().intValue())))
            .andExpect(jsonPath("$.[*].drain").value(hasItem(DEFAULT_DRAIN)))
            .andExpect(jsonPath("$.[*].risicogebied").value(hasItem(DEFAULT_RISICOGEBIED)));
    }

    @Test
    @Transactional
    void getFilterput() throws Exception {
        // Initialize the database
        filterputRepository.saveAndFlush(filterput);

        // Get the filterput
        restFilterputMockMvc
            .perform(get(ENTITY_API_URL_ID, filterput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filterput.getId().intValue()))
            .andExpect(jsonPath("$.drain").value(DEFAULT_DRAIN))
            .andExpect(jsonPath("$.risicogebied").value(DEFAULT_RISICOGEBIED));
    }

    @Test
    @Transactional
    void getNonExistingFilterput() throws Exception {
        // Get the filterput
        restFilterputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFilterput() throws Exception {
        // Initialize the database
        filterputRepository.saveAndFlush(filterput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the filterput
        Filterput updatedFilterput = filterputRepository.findById(filterput.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFilterput are not directly saved in db
        em.detach(updatedFilterput);
        updatedFilterput.drain(UPDATED_DRAIN).risicogebied(UPDATED_RISICOGEBIED);

        restFilterputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFilterput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFilterput))
            )
            .andExpect(status().isOk());

        // Validate the Filterput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFilterputToMatchAllProperties(updatedFilterput);
    }

    @Test
    @Transactional
    void putNonExistingFilterput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        filterput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilterputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filterput.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(filterput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Filterput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFilterput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        filterput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(filterput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Filterput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFilterput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        filterput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(filterput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Filterput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFilterputWithPatch() throws Exception {
        // Initialize the database
        filterputRepository.saveAndFlush(filterput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the filterput using partial update
        Filterput partialUpdatedFilterput = new Filterput();
        partialUpdatedFilterput.setId(filterput.getId());

        partialUpdatedFilterput.drain(UPDATED_DRAIN);

        restFilterputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilterput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFilterput))
            )
            .andExpect(status().isOk());

        // Validate the Filterput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFilterputUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFilterput, filterput),
            getPersistedFilterput(filterput)
        );
    }

    @Test
    @Transactional
    void fullUpdateFilterputWithPatch() throws Exception {
        // Initialize the database
        filterputRepository.saveAndFlush(filterput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the filterput using partial update
        Filterput partialUpdatedFilterput = new Filterput();
        partialUpdatedFilterput.setId(filterput.getId());

        partialUpdatedFilterput.drain(UPDATED_DRAIN).risicogebied(UPDATED_RISICOGEBIED);

        restFilterputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilterput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFilterput))
            )
            .andExpect(status().isOk());

        // Validate the Filterput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFilterputUpdatableFieldsEquals(partialUpdatedFilterput, getPersistedFilterput(partialUpdatedFilterput));
    }

    @Test
    @Transactional
    void patchNonExistingFilterput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        filterput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilterputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, filterput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(filterput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Filterput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFilterput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        filterput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(filterput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Filterput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFilterput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        filterput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterputMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(filterput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Filterput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFilterput() throws Exception {
        // Initialize the database
        filterputRepository.saveAndFlush(filterput);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the filterput
        restFilterputMockMvc
            .perform(delete(ENTITY_API_URL_ID, filterput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return filterputRepository.count();
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

    protected Filterput getPersistedFilterput(Filterput filterput) {
        return filterputRepository.findById(filterput.getId()).orElseThrow();
    }

    protected void assertPersistedFilterputToMatchAllProperties(Filterput expectedFilterput) {
        assertFilterputAllPropertiesEquals(expectedFilterput, getPersistedFilterput(expectedFilterput));
    }

    protected void assertPersistedFilterputToMatchUpdatableProperties(Filterput expectedFilterput) {
        assertFilterputAllUpdatablePropertiesEquals(expectedFilterput, getPersistedFilterput(expectedFilterput));
    }
}
