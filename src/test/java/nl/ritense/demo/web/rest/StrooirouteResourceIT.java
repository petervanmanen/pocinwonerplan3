package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StrooirouteAsserts.*;
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
import nl.ritense.demo.domain.Strooiroute;
import nl.ritense.demo.repository.StrooirouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StrooirouteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrooirouteResourceIT {

    private static final String DEFAULT_EROUTE = "AAAAAAAAAA";
    private static final String UPDATED_EROUTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/strooiroutes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StrooirouteRepository strooirouteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrooirouteMockMvc;

    private Strooiroute strooiroute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strooiroute createEntity(EntityManager em) {
        Strooiroute strooiroute = new Strooiroute().eroute(DEFAULT_EROUTE);
        return strooiroute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strooiroute createUpdatedEntity(EntityManager em) {
        Strooiroute strooiroute = new Strooiroute().eroute(UPDATED_EROUTE);
        return strooiroute;
    }

    @BeforeEach
    public void initTest() {
        strooiroute = createEntity(em);
    }

    @Test
    @Transactional
    void createStrooiroute() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Strooiroute
        var returnedStrooiroute = om.readValue(
            restStrooirouteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooiroute)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Strooiroute.class
        );

        // Validate the Strooiroute in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStrooirouteUpdatableFieldsEquals(returnedStrooiroute, getPersistedStrooiroute(returnedStrooiroute));
    }

    @Test
    @Transactional
    void createStrooirouteWithExistingId() throws Exception {
        // Create the Strooiroute with an existing ID
        strooiroute.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrooirouteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooiroute)))
            .andExpect(status().isBadRequest());

        // Validate the Strooiroute in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStrooiroutes() throws Exception {
        // Initialize the database
        strooirouteRepository.saveAndFlush(strooiroute);

        // Get all the strooirouteList
        restStrooirouteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strooiroute.getId().intValue())))
            .andExpect(jsonPath("$.[*].eroute").value(hasItem(DEFAULT_EROUTE)));
    }

    @Test
    @Transactional
    void getStrooiroute() throws Exception {
        // Initialize the database
        strooirouteRepository.saveAndFlush(strooiroute);

        // Get the strooiroute
        restStrooirouteMockMvc
            .perform(get(ENTITY_API_URL_ID, strooiroute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strooiroute.getId().intValue()))
            .andExpect(jsonPath("$.eroute").value(DEFAULT_EROUTE));
    }

    @Test
    @Transactional
    void getNonExistingStrooiroute() throws Exception {
        // Get the strooiroute
        restStrooirouteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStrooiroute() throws Exception {
        // Initialize the database
        strooirouteRepository.saveAndFlush(strooiroute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooiroute
        Strooiroute updatedStrooiroute = strooirouteRepository.findById(strooiroute.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStrooiroute are not directly saved in db
        em.detach(updatedStrooiroute);
        updatedStrooiroute.eroute(UPDATED_EROUTE);

        restStrooirouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrooiroute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStrooiroute))
            )
            .andExpect(status().isOk());

        // Validate the Strooiroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStrooirouteToMatchAllProperties(updatedStrooiroute);
    }

    @Test
    @Transactional
    void putNonExistingStrooiroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooiroute.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrooirouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strooiroute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(strooiroute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooiroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrooiroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooiroute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooirouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(strooiroute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooiroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrooiroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooiroute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooirouteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(strooiroute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strooiroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrooirouteWithPatch() throws Exception {
        // Initialize the database
        strooirouteRepository.saveAndFlush(strooiroute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooiroute using partial update
        Strooiroute partialUpdatedStrooiroute = new Strooiroute();
        partialUpdatedStrooiroute.setId(strooiroute.getId());

        partialUpdatedStrooiroute.eroute(UPDATED_EROUTE);

        restStrooirouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrooiroute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStrooiroute))
            )
            .andExpect(status().isOk());

        // Validate the Strooiroute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStrooirouteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStrooiroute, strooiroute),
            getPersistedStrooiroute(strooiroute)
        );
    }

    @Test
    @Transactional
    void fullUpdateStrooirouteWithPatch() throws Exception {
        // Initialize the database
        strooirouteRepository.saveAndFlush(strooiroute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the strooiroute using partial update
        Strooiroute partialUpdatedStrooiroute = new Strooiroute();
        partialUpdatedStrooiroute.setId(strooiroute.getId());

        partialUpdatedStrooiroute.eroute(UPDATED_EROUTE);

        restStrooirouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrooiroute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStrooiroute))
            )
            .andExpect(status().isOk());

        // Validate the Strooiroute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStrooirouteUpdatableFieldsEquals(partialUpdatedStrooiroute, getPersistedStrooiroute(partialUpdatedStrooiroute));
    }

    @Test
    @Transactional
    void patchNonExistingStrooiroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooiroute.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrooirouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strooiroute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(strooiroute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooiroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrooiroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooiroute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooirouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(strooiroute))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strooiroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrooiroute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        strooiroute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrooirouteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(strooiroute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strooiroute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrooiroute() throws Exception {
        // Initialize the database
        strooirouteRepository.saveAndFlush(strooiroute);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the strooiroute
        restStrooirouteMockMvc
            .perform(delete(ENTITY_API_URL_ID, strooiroute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return strooirouteRepository.count();
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

    protected Strooiroute getPersistedStrooiroute(Strooiroute strooiroute) {
        return strooirouteRepository.findById(strooiroute.getId()).orElseThrow();
    }

    protected void assertPersistedStrooirouteToMatchAllProperties(Strooiroute expectedStrooiroute) {
        assertStrooirouteAllPropertiesEquals(expectedStrooiroute, getPersistedStrooiroute(expectedStrooiroute));
    }

    protected void assertPersistedStrooirouteToMatchUpdatableProperties(Strooiroute expectedStrooiroute) {
        assertStrooirouteAllUpdatablePropertiesEquals(expectedStrooiroute, getPersistedStrooiroute(expectedStrooiroute));
    }
}
