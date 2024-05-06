package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitvoeringsregelAsserts.*;
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
import nl.ritense.demo.domain.Uitvoeringsregel;
import nl.ritense.demo.repository.UitvoeringsregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitvoeringsregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitvoeringsregelResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_REGEL = "AAAAAAAAAA";
    private static final String UPDATED_REGEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/uitvoeringsregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitvoeringsregelRepository uitvoeringsregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitvoeringsregelMockMvc;

    private Uitvoeringsregel uitvoeringsregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitvoeringsregel createEntity(EntityManager em) {
        Uitvoeringsregel uitvoeringsregel = new Uitvoeringsregel()
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .regel(DEFAULT_REGEL);
        return uitvoeringsregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitvoeringsregel createUpdatedEntity(EntityManager em) {
        Uitvoeringsregel uitvoeringsregel = new Uitvoeringsregel()
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .regel(UPDATED_REGEL);
        return uitvoeringsregel;
    }

    @BeforeEach
    public void initTest() {
        uitvoeringsregel = createEntity(em);
    }

    @Test
    @Transactional
    void createUitvoeringsregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitvoeringsregel
        var returnedUitvoeringsregel = om.readValue(
            restUitvoeringsregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitvoeringsregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitvoeringsregel.class
        );

        // Validate the Uitvoeringsregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitvoeringsregelUpdatableFieldsEquals(returnedUitvoeringsregel, getPersistedUitvoeringsregel(returnedUitvoeringsregel));
    }

    @Test
    @Transactional
    void createUitvoeringsregelWithExistingId() throws Exception {
        // Create the Uitvoeringsregel with an existing ID
        uitvoeringsregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitvoeringsregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitvoeringsregel)))
            .andExpect(status().isBadRequest());

        // Validate the Uitvoeringsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitvoeringsregels() throws Exception {
        // Initialize the database
        uitvoeringsregelRepository.saveAndFlush(uitvoeringsregel);

        // Get all the uitvoeringsregelList
        restUitvoeringsregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitvoeringsregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].regel").value(hasItem(DEFAULT_REGEL)));
    }

    @Test
    @Transactional
    void getUitvoeringsregel() throws Exception {
        // Initialize the database
        uitvoeringsregelRepository.saveAndFlush(uitvoeringsregel);

        // Get the uitvoeringsregel
        restUitvoeringsregelMockMvc
            .perform(get(ENTITY_API_URL_ID, uitvoeringsregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitvoeringsregel.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.regel").value(DEFAULT_REGEL));
    }

    @Test
    @Transactional
    void getNonExistingUitvoeringsregel() throws Exception {
        // Get the uitvoeringsregel
        restUitvoeringsregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUitvoeringsregel() throws Exception {
        // Initialize the database
        uitvoeringsregelRepository.saveAndFlush(uitvoeringsregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitvoeringsregel
        Uitvoeringsregel updatedUitvoeringsregel = uitvoeringsregelRepository.findById(uitvoeringsregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUitvoeringsregel are not directly saved in db
        em.detach(updatedUitvoeringsregel);
        updatedUitvoeringsregel.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).regel(UPDATED_REGEL);

        restUitvoeringsregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUitvoeringsregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUitvoeringsregel))
            )
            .andExpect(status().isOk());

        // Validate the Uitvoeringsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUitvoeringsregelToMatchAllProperties(updatedUitvoeringsregel);
    }

    @Test
    @Transactional
    void putNonExistingUitvoeringsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoeringsregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitvoeringsregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uitvoeringsregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitvoeringsregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoeringsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUitvoeringsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoeringsregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitvoeringsregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitvoeringsregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoeringsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUitvoeringsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoeringsregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitvoeringsregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitvoeringsregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitvoeringsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUitvoeringsregelWithPatch() throws Exception {
        // Initialize the database
        uitvoeringsregelRepository.saveAndFlush(uitvoeringsregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitvoeringsregel using partial update
        Uitvoeringsregel partialUpdatedUitvoeringsregel = new Uitvoeringsregel();
        partialUpdatedUitvoeringsregel.setId(uitvoeringsregel.getId());

        partialUpdatedUitvoeringsregel.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).regel(UPDATED_REGEL);

        restUitvoeringsregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitvoeringsregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitvoeringsregel))
            )
            .andExpect(status().isOk());

        // Validate the Uitvoeringsregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitvoeringsregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUitvoeringsregel, uitvoeringsregel),
            getPersistedUitvoeringsregel(uitvoeringsregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateUitvoeringsregelWithPatch() throws Exception {
        // Initialize the database
        uitvoeringsregelRepository.saveAndFlush(uitvoeringsregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitvoeringsregel using partial update
        Uitvoeringsregel partialUpdatedUitvoeringsregel = new Uitvoeringsregel();
        partialUpdatedUitvoeringsregel.setId(uitvoeringsregel.getId());

        partialUpdatedUitvoeringsregel.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).regel(UPDATED_REGEL);

        restUitvoeringsregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitvoeringsregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitvoeringsregel))
            )
            .andExpect(status().isOk());

        // Validate the Uitvoeringsregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitvoeringsregelUpdatableFieldsEquals(
            partialUpdatedUitvoeringsregel,
            getPersistedUitvoeringsregel(partialUpdatedUitvoeringsregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUitvoeringsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoeringsregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitvoeringsregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uitvoeringsregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitvoeringsregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoeringsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUitvoeringsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoeringsregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitvoeringsregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitvoeringsregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitvoeringsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUitvoeringsregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitvoeringsregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitvoeringsregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uitvoeringsregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitvoeringsregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUitvoeringsregel() throws Exception {
        // Initialize the database
        uitvoeringsregelRepository.saveAndFlush(uitvoeringsregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitvoeringsregel
        restUitvoeringsregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitvoeringsregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitvoeringsregelRepository.count();
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

    protected Uitvoeringsregel getPersistedUitvoeringsregel(Uitvoeringsregel uitvoeringsregel) {
        return uitvoeringsregelRepository.findById(uitvoeringsregel.getId()).orElseThrow();
    }

    protected void assertPersistedUitvoeringsregelToMatchAllProperties(Uitvoeringsregel expectedUitvoeringsregel) {
        assertUitvoeringsregelAllPropertiesEquals(expectedUitvoeringsregel, getPersistedUitvoeringsregel(expectedUitvoeringsregel));
    }

    protected void assertPersistedUitvoeringsregelToMatchUpdatableProperties(Uitvoeringsregel expectedUitvoeringsregel) {
        assertUitvoeringsregelAllUpdatablePropertiesEquals(
            expectedUitvoeringsregel,
            getPersistedUitvoeringsregel(expectedUitvoeringsregel)
        );
    }
}
