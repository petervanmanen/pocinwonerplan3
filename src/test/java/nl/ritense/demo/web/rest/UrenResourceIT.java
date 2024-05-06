package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UrenAsserts.*;
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
import nl.ritense.demo.domain.Uren;
import nl.ritense.demo.repository.UrenRepository;
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
 * Integration tests for the {@link UrenResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class UrenResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/urens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UrenRepository urenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUrenMockMvc;

    private Uren uren;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uren createEntity(EntityManager em) {
        Uren uren = new Uren().aantal(DEFAULT_AANTAL);
        return uren;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uren createUpdatedEntity(EntityManager em) {
        Uren uren = new Uren().aantal(UPDATED_AANTAL);
        return uren;
    }

    @BeforeEach
    public void initTest() {
        uren = createEntity(em);
    }

    @Test
    @Transactional
    void createUren() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uren
        var returnedUren = om.readValue(
            restUrenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uren)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uren.class
        );

        // Validate the Uren in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUrenUpdatableFieldsEquals(returnedUren, getPersistedUren(returnedUren));
    }

    @Test
    @Transactional
    void createUrenWithExistingId() throws Exception {
        // Create the Uren with an existing ID
        uren.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUrenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uren)))
            .andExpect(status().isBadRequest());

        // Validate the Uren in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUrens() throws Exception {
        // Initialize the database
        urenRepository.saveAndFlush(uren);

        // Get all the urenList
        restUrenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uren.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)));
    }

    @Test
    @Transactional
    void getUren() throws Exception {
        // Initialize the database
        urenRepository.saveAndFlush(uren);

        // Get the uren
        restUrenMockMvc
            .perform(get(ENTITY_API_URL_ID, uren.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uren.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL));
    }

    @Test
    @Transactional
    void getNonExistingUren() throws Exception {
        // Get the uren
        restUrenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUren() throws Exception {
        // Initialize the database
        urenRepository.saveAndFlush(uren);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uren
        Uren updatedUren = urenRepository.findById(uren.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUren are not directly saved in db
        em.detach(updatedUren);
        updatedUren.aantal(UPDATED_AANTAL);

        restUrenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUren.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUren))
            )
            .andExpect(status().isOk());

        // Validate the Uren in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUrenToMatchAllProperties(updatedUren);
    }

    @Test
    @Transactional
    void putNonExistingUren() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uren.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUrenMockMvc
            .perform(put(ENTITY_API_URL_ID, uren.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uren)))
            .andExpect(status().isBadRequest());

        // Validate the Uren in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUren() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uren.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uren))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uren in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUren() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uren.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uren)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uren in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUrenWithPatch() throws Exception {
        // Initialize the database
        urenRepository.saveAndFlush(uren);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uren using partial update
        Uren partialUpdatedUren = new Uren();
        partialUpdatedUren.setId(uren.getId());

        partialUpdatedUren.aantal(UPDATED_AANTAL);

        restUrenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUren.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUren))
            )
            .andExpect(status().isOk());

        // Validate the Uren in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUrenUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedUren, uren), getPersistedUren(uren));
    }

    @Test
    @Transactional
    void fullUpdateUrenWithPatch() throws Exception {
        // Initialize the database
        urenRepository.saveAndFlush(uren);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uren using partial update
        Uren partialUpdatedUren = new Uren();
        partialUpdatedUren.setId(uren.getId());

        partialUpdatedUren.aantal(UPDATED_AANTAL);

        restUrenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUren.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUren))
            )
            .andExpect(status().isOk());

        // Validate the Uren in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUrenUpdatableFieldsEquals(partialUpdatedUren, getPersistedUren(partialUpdatedUren));
    }

    @Test
    @Transactional
    void patchNonExistingUren() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uren.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUrenMockMvc
            .perform(patch(ENTITY_API_URL_ID, uren.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uren)))
            .andExpect(status().isBadRequest());

        // Validate the Uren in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUren() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uren.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uren))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uren in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUren() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uren.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUrenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uren)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uren in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUren() throws Exception {
        // Initialize the database
        urenRepository.saveAndFlush(uren);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uren
        restUrenMockMvc
            .perform(delete(ENTITY_API_URL_ID, uren.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return urenRepository.count();
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

    protected Uren getPersistedUren(Uren uren) {
        return urenRepository.findById(uren.getId()).orElseThrow();
    }

    protected void assertPersistedUrenToMatchAllProperties(Uren expectedUren) {
        assertUrenAllPropertiesEquals(expectedUren, getPersistedUren(expectedUren));
    }

    protected void assertPersistedUrenToMatchUpdatableProperties(Uren expectedUren) {
        assertUrenAllUpdatablePropertiesEquals(expectedUren, getPersistedUren(expectedUren));
    }
}
