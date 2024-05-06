package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HandelsnamenmaatschappelijkeactiviteitAsserts.*;
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
import nl.ritense.demo.domain.Handelsnamenmaatschappelijkeactiviteit;
import nl.ritense.demo.repository.HandelsnamenmaatschappelijkeactiviteitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HandelsnamenmaatschappelijkeactiviteitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HandelsnamenmaatschappelijkeactiviteitResourceIT {

    private static final String DEFAULT_HANDELSNAAM = "AAAAAAAAAA";
    private static final String UPDATED_HANDELSNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VERKORTENAAM = "AAAAAAAAAA";
    private static final String UPDATED_VERKORTENAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VOLGORDE = "AAAAAAAAAA";
    private static final String UPDATED_VOLGORDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/handelsnamenmaatschappelijkeactiviteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HandelsnamenmaatschappelijkeactiviteitRepository handelsnamenmaatschappelijkeactiviteitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHandelsnamenmaatschappelijkeactiviteitMockMvc;

    private Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Handelsnamenmaatschappelijkeactiviteit createEntity(EntityManager em) {
        Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit = new Handelsnamenmaatschappelijkeactiviteit()
            .handelsnaam(DEFAULT_HANDELSNAAM)
            .verkortenaam(DEFAULT_VERKORTENAAM)
            .volgorde(DEFAULT_VOLGORDE);
        return handelsnamenmaatschappelijkeactiviteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Handelsnamenmaatschappelijkeactiviteit createUpdatedEntity(EntityManager em) {
        Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit = new Handelsnamenmaatschappelijkeactiviteit()
            .handelsnaam(UPDATED_HANDELSNAAM)
            .verkortenaam(UPDATED_VERKORTENAAM)
            .volgorde(UPDATED_VOLGORDE);
        return handelsnamenmaatschappelijkeactiviteit;
    }

    @BeforeEach
    public void initTest() {
        handelsnamenmaatschappelijkeactiviteit = createEntity(em);
    }

    @Test
    @Transactional
    void createHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Handelsnamenmaatschappelijkeactiviteit
        var returnedHandelsnamenmaatschappelijkeactiviteit = om.readValue(
            restHandelsnamenmaatschappelijkeactiviteitMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(handelsnamenmaatschappelijkeactiviteit))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Handelsnamenmaatschappelijkeactiviteit.class
        );

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHandelsnamenmaatschappelijkeactiviteitUpdatableFieldsEquals(
            returnedHandelsnamenmaatschappelijkeactiviteit,
            getPersistedHandelsnamenmaatschappelijkeactiviteit(returnedHandelsnamenmaatschappelijkeactiviteit)
        );
    }

    @Test
    @Transactional
    void createHandelsnamenmaatschappelijkeactiviteitWithExistingId() throws Exception {
        // Create the Handelsnamenmaatschappelijkeactiviteit with an existing ID
        handelsnamenmaatschappelijkeactiviteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(handelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHandelsnamenmaatschappelijkeactiviteits() throws Exception {
        // Initialize the database
        handelsnamenmaatschappelijkeactiviteitRepository.saveAndFlush(handelsnamenmaatschappelijkeactiviteit);

        // Get all the handelsnamenmaatschappelijkeactiviteitList
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(handelsnamenmaatschappelijkeactiviteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].handelsnaam").value(hasItem(DEFAULT_HANDELSNAAM)))
            .andExpect(jsonPath("$.[*].verkortenaam").value(hasItem(DEFAULT_VERKORTENAAM)))
            .andExpect(jsonPath("$.[*].volgorde").value(hasItem(DEFAULT_VOLGORDE)));
    }

    @Test
    @Transactional
    void getHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        // Initialize the database
        handelsnamenmaatschappelijkeactiviteitRepository.saveAndFlush(handelsnamenmaatschappelijkeactiviteit);

        // Get the handelsnamenmaatschappelijkeactiviteit
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(get(ENTITY_API_URL_ID, handelsnamenmaatschappelijkeactiviteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(handelsnamenmaatschappelijkeactiviteit.getId().intValue()))
            .andExpect(jsonPath("$.handelsnaam").value(DEFAULT_HANDELSNAAM))
            .andExpect(jsonPath("$.verkortenaam").value(DEFAULT_VERKORTENAAM))
            .andExpect(jsonPath("$.volgorde").value(DEFAULT_VOLGORDE));
    }

    @Test
    @Transactional
    void getNonExistingHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        // Get the handelsnamenmaatschappelijkeactiviteit
        restHandelsnamenmaatschappelijkeactiviteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        // Initialize the database
        handelsnamenmaatschappelijkeactiviteitRepository.saveAndFlush(handelsnamenmaatschappelijkeactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the handelsnamenmaatschappelijkeactiviteit
        Handelsnamenmaatschappelijkeactiviteit updatedHandelsnamenmaatschappelijkeactiviteit =
            handelsnamenmaatschappelijkeactiviteitRepository.findById(handelsnamenmaatschappelijkeactiviteit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHandelsnamenmaatschappelijkeactiviteit are not directly saved in db
        em.detach(updatedHandelsnamenmaatschappelijkeactiviteit);
        updatedHandelsnamenmaatschappelijkeactiviteit
            .handelsnaam(UPDATED_HANDELSNAAM)
            .verkortenaam(UPDATED_VERKORTENAAM)
            .volgorde(UPDATED_VOLGORDE);

        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHandelsnamenmaatschappelijkeactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHandelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHandelsnamenmaatschappelijkeactiviteitToMatchAllProperties(updatedHandelsnamenmaatschappelijkeactiviteit);
    }

    @Test
    @Transactional
    void putNonExistingHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenmaatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, handelsnamenmaatschappelijkeactiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(handelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenmaatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(handelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenmaatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(handelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHandelsnamenmaatschappelijkeactiviteitWithPatch() throws Exception {
        // Initialize the database
        handelsnamenmaatschappelijkeactiviteitRepository.saveAndFlush(handelsnamenmaatschappelijkeactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the handelsnamenmaatschappelijkeactiviteit using partial update
        Handelsnamenmaatschappelijkeactiviteit partialUpdatedHandelsnamenmaatschappelijkeactiviteit =
            new Handelsnamenmaatschappelijkeactiviteit();
        partialUpdatedHandelsnamenmaatschappelijkeactiviteit.setId(handelsnamenmaatschappelijkeactiviteit.getId());

        partialUpdatedHandelsnamenmaatschappelijkeactiviteit.handelsnaam(UPDATED_HANDELSNAAM).verkortenaam(UPDATED_VERKORTENAAM);

        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHandelsnamenmaatschappelijkeactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHandelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHandelsnamenmaatschappelijkeactiviteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHandelsnamenmaatschappelijkeactiviteit, handelsnamenmaatschappelijkeactiviteit),
            getPersistedHandelsnamenmaatschappelijkeactiviteit(handelsnamenmaatschappelijkeactiviteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateHandelsnamenmaatschappelijkeactiviteitWithPatch() throws Exception {
        // Initialize the database
        handelsnamenmaatschappelijkeactiviteitRepository.saveAndFlush(handelsnamenmaatschappelijkeactiviteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the handelsnamenmaatschappelijkeactiviteit using partial update
        Handelsnamenmaatschappelijkeactiviteit partialUpdatedHandelsnamenmaatschappelijkeactiviteit =
            new Handelsnamenmaatschappelijkeactiviteit();
        partialUpdatedHandelsnamenmaatschappelijkeactiviteit.setId(handelsnamenmaatschappelijkeactiviteit.getId());

        partialUpdatedHandelsnamenmaatschappelijkeactiviteit
            .handelsnaam(UPDATED_HANDELSNAAM)
            .verkortenaam(UPDATED_VERKORTENAAM)
            .volgorde(UPDATED_VOLGORDE);

        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHandelsnamenmaatschappelijkeactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHandelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHandelsnamenmaatschappelijkeactiviteitUpdatableFieldsEquals(
            partialUpdatedHandelsnamenmaatschappelijkeactiviteit,
            getPersistedHandelsnamenmaatschappelijkeactiviteit(partialUpdatedHandelsnamenmaatschappelijkeactiviteit)
        );
    }

    @Test
    @Transactional
    void patchNonExistingHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenmaatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, handelsnamenmaatschappelijkeactiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(handelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenmaatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(handelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        handelsnamenmaatschappelijkeactiviteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(handelsnamenmaatschappelijkeactiviteit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Handelsnamenmaatschappelijkeactiviteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHandelsnamenmaatschappelijkeactiviteit() throws Exception {
        // Initialize the database
        handelsnamenmaatschappelijkeactiviteitRepository.saveAndFlush(handelsnamenmaatschappelijkeactiviteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the handelsnamenmaatschappelijkeactiviteit
        restHandelsnamenmaatschappelijkeactiviteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, handelsnamenmaatschappelijkeactiviteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return handelsnamenmaatschappelijkeactiviteitRepository.count();
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

    protected Handelsnamenmaatschappelijkeactiviteit getPersistedHandelsnamenmaatschappelijkeactiviteit(
        Handelsnamenmaatschappelijkeactiviteit handelsnamenmaatschappelijkeactiviteit
    ) {
        return handelsnamenmaatschappelijkeactiviteitRepository.findById(handelsnamenmaatschappelijkeactiviteit.getId()).orElseThrow();
    }

    protected void assertPersistedHandelsnamenmaatschappelijkeactiviteitToMatchAllProperties(
        Handelsnamenmaatschappelijkeactiviteit expectedHandelsnamenmaatschappelijkeactiviteit
    ) {
        assertHandelsnamenmaatschappelijkeactiviteitAllPropertiesEquals(
            expectedHandelsnamenmaatschappelijkeactiviteit,
            getPersistedHandelsnamenmaatschappelijkeactiviteit(expectedHandelsnamenmaatschappelijkeactiviteit)
        );
    }

    protected void assertPersistedHandelsnamenmaatschappelijkeactiviteitToMatchUpdatableProperties(
        Handelsnamenmaatschappelijkeactiviteit expectedHandelsnamenmaatschappelijkeactiviteit
    ) {
        assertHandelsnamenmaatschappelijkeactiviteitAllUpdatablePropertiesEquals(
            expectedHandelsnamenmaatschappelijkeactiviteit,
            getPersistedHandelsnamenmaatschappelijkeactiviteit(expectedHandelsnamenmaatschappelijkeactiviteit)
        );
    }
}
