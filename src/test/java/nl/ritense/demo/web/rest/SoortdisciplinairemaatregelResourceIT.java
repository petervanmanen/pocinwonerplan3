package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoortdisciplinairemaatregelAsserts.*;
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
import nl.ritense.demo.domain.Soortdisciplinairemaatregel;
import nl.ritense.demo.repository.SoortdisciplinairemaatregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoortdisciplinairemaatregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoortdisciplinairemaatregelResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/soortdisciplinairemaatregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoortdisciplinairemaatregelRepository soortdisciplinairemaatregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoortdisciplinairemaatregelMockMvc;

    private Soortdisciplinairemaatregel soortdisciplinairemaatregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortdisciplinairemaatregel createEntity(EntityManager em) {
        Soortdisciplinairemaatregel soortdisciplinairemaatregel = new Soortdisciplinairemaatregel()
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return soortdisciplinairemaatregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortdisciplinairemaatregel createUpdatedEntity(EntityManager em) {
        Soortdisciplinairemaatregel soortdisciplinairemaatregel = new Soortdisciplinairemaatregel()
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return soortdisciplinairemaatregel;
    }

    @BeforeEach
    public void initTest() {
        soortdisciplinairemaatregel = createEntity(em);
    }

    @Test
    @Transactional
    void createSoortdisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Soortdisciplinairemaatregel
        var returnedSoortdisciplinairemaatregel = om.readValue(
            restSoortdisciplinairemaatregelMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortdisciplinairemaatregel))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Soortdisciplinairemaatregel.class
        );

        // Validate the Soortdisciplinairemaatregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoortdisciplinairemaatregelUpdatableFieldsEquals(
            returnedSoortdisciplinairemaatregel,
            getPersistedSoortdisciplinairemaatregel(returnedSoortdisciplinairemaatregel)
        );
    }

    @Test
    @Transactional
    void createSoortdisciplinairemaatregelWithExistingId() throws Exception {
        // Create the Soortdisciplinairemaatregel with an existing ID
        soortdisciplinairemaatregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoortdisciplinairemaatregelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortdisciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortdisciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoortdisciplinairemaatregels() throws Exception {
        // Initialize the database
        soortdisciplinairemaatregelRepository.saveAndFlush(soortdisciplinairemaatregel);

        // Get all the soortdisciplinairemaatregelList
        restSoortdisciplinairemaatregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soortdisciplinairemaatregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getSoortdisciplinairemaatregel() throws Exception {
        // Initialize the database
        soortdisciplinairemaatregelRepository.saveAndFlush(soortdisciplinairemaatregel);

        // Get the soortdisciplinairemaatregel
        restSoortdisciplinairemaatregelMockMvc
            .perform(get(ENTITY_API_URL_ID, soortdisciplinairemaatregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soortdisciplinairemaatregel.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingSoortdisciplinairemaatregel() throws Exception {
        // Get the soortdisciplinairemaatregel
        restSoortdisciplinairemaatregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoortdisciplinairemaatregel() throws Exception {
        // Initialize the database
        soortdisciplinairemaatregelRepository.saveAndFlush(soortdisciplinairemaatregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortdisciplinairemaatregel
        Soortdisciplinairemaatregel updatedSoortdisciplinairemaatregel = soortdisciplinairemaatregelRepository
            .findById(soortdisciplinairemaatregel.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSoortdisciplinairemaatregel are not directly saved in db
        em.detach(updatedSoortdisciplinairemaatregel);
        updatedSoortdisciplinairemaatregel.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restSoortdisciplinairemaatregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoortdisciplinairemaatregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSoortdisciplinairemaatregel))
            )
            .andExpect(status().isOk());

        // Validate the Soortdisciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSoortdisciplinairemaatregelToMatchAllProperties(updatedSoortdisciplinairemaatregel);
    }

    @Test
    @Transactional
    void putNonExistingSoortdisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortdisciplinairemaatregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortdisciplinairemaatregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soortdisciplinairemaatregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortdisciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortdisciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoortdisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortdisciplinairemaatregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortdisciplinairemaatregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortdisciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortdisciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoortdisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortdisciplinairemaatregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortdisciplinairemaatregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortdisciplinairemaatregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortdisciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoortdisciplinairemaatregelWithPatch() throws Exception {
        // Initialize the database
        soortdisciplinairemaatregelRepository.saveAndFlush(soortdisciplinairemaatregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortdisciplinairemaatregel using partial update
        Soortdisciplinairemaatregel partialUpdatedSoortdisciplinairemaatregel = new Soortdisciplinairemaatregel();
        partialUpdatedSoortdisciplinairemaatregel.setId(soortdisciplinairemaatregel.getId());

        restSoortdisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortdisciplinairemaatregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortdisciplinairemaatregel))
            )
            .andExpect(status().isOk());

        // Validate the Soortdisciplinairemaatregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortdisciplinairemaatregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSoortdisciplinairemaatregel, soortdisciplinairemaatregel),
            getPersistedSoortdisciplinairemaatregel(soortdisciplinairemaatregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateSoortdisciplinairemaatregelWithPatch() throws Exception {
        // Initialize the database
        soortdisciplinairemaatregelRepository.saveAndFlush(soortdisciplinairemaatregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortdisciplinairemaatregel using partial update
        Soortdisciplinairemaatregel partialUpdatedSoortdisciplinairemaatregel = new Soortdisciplinairemaatregel();
        partialUpdatedSoortdisciplinairemaatregel.setId(soortdisciplinairemaatregel.getId());

        partialUpdatedSoortdisciplinairemaatregel.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restSoortdisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortdisciplinairemaatregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortdisciplinairemaatregel))
            )
            .andExpect(status().isOk());

        // Validate the Soortdisciplinairemaatregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortdisciplinairemaatregelUpdatableFieldsEquals(
            partialUpdatedSoortdisciplinairemaatregel,
            getPersistedSoortdisciplinairemaatregel(partialUpdatedSoortdisciplinairemaatregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSoortdisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortdisciplinairemaatregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortdisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soortdisciplinairemaatregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortdisciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortdisciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoortdisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortdisciplinairemaatregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortdisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortdisciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortdisciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoortdisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortdisciplinairemaatregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortdisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(soortdisciplinairemaatregel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortdisciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoortdisciplinairemaatregel() throws Exception {
        // Initialize the database
        soortdisciplinairemaatregelRepository.saveAndFlush(soortdisciplinairemaatregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the soortdisciplinairemaatregel
        restSoortdisciplinairemaatregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, soortdisciplinairemaatregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return soortdisciplinairemaatregelRepository.count();
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

    protected Soortdisciplinairemaatregel getPersistedSoortdisciplinairemaatregel(Soortdisciplinairemaatregel soortdisciplinairemaatregel) {
        return soortdisciplinairemaatregelRepository.findById(soortdisciplinairemaatregel.getId()).orElseThrow();
    }

    protected void assertPersistedSoortdisciplinairemaatregelToMatchAllProperties(
        Soortdisciplinairemaatregel expectedSoortdisciplinairemaatregel
    ) {
        assertSoortdisciplinairemaatregelAllPropertiesEquals(
            expectedSoortdisciplinairemaatregel,
            getPersistedSoortdisciplinairemaatregel(expectedSoortdisciplinairemaatregel)
        );
    }

    protected void assertPersistedSoortdisciplinairemaatregelToMatchUpdatableProperties(
        Soortdisciplinairemaatregel expectedSoortdisciplinairemaatregel
    ) {
        assertSoortdisciplinairemaatregelAllUpdatablePropertiesEquals(
            expectedSoortdisciplinairemaatregel,
            getPersistedSoortdisciplinairemaatregel(expectedSoortdisciplinairemaatregel)
        );
    }
}
