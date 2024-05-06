package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CultuurcodeonbebouwdAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Cultuurcodeonbebouwd;
import nl.ritense.demo.repository.CultuurcodeonbebouwdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CultuurcodeonbebouwdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CultuurcodeonbebouwdResourceIT {

    private static final String DEFAULT_CULTUURCODEONBEBOUWD = "AAAAAAAAAA";
    private static final String UPDATED_CULTUURCODEONBEBOUWD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAMCULTUURCODEONBEBOUWD = "AAAAAAAAAA";
    private static final String UPDATED_NAAMCULTUURCODEONBEBOUWD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cultuurcodeonbebouwds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CultuurcodeonbebouwdRepository cultuurcodeonbebouwdRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCultuurcodeonbebouwdMockMvc;

    private Cultuurcodeonbebouwd cultuurcodeonbebouwd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cultuurcodeonbebouwd createEntity(EntityManager em) {
        Cultuurcodeonbebouwd cultuurcodeonbebouwd = new Cultuurcodeonbebouwd()
            .cultuurcodeonbebouwd(DEFAULT_CULTUURCODEONBEBOUWD)
            .datumbegingeldigheidcultuurcodeonbebouwd(DEFAULT_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD)
            .datumeindegeldigheidcultuurcodeonbebouwd(DEFAULT_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD)
            .naamcultuurcodeonbebouwd(DEFAULT_NAAMCULTUURCODEONBEBOUWD);
        return cultuurcodeonbebouwd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cultuurcodeonbebouwd createUpdatedEntity(EntityManager em) {
        Cultuurcodeonbebouwd cultuurcodeonbebouwd = new Cultuurcodeonbebouwd()
            .cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD)
            .datumbegingeldigheidcultuurcodeonbebouwd(UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD)
            .datumeindegeldigheidcultuurcodeonbebouwd(UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD)
            .naamcultuurcodeonbebouwd(UPDATED_NAAMCULTUURCODEONBEBOUWD);
        return cultuurcodeonbebouwd;
    }

    @BeforeEach
    public void initTest() {
        cultuurcodeonbebouwd = createEntity(em);
    }

    @Test
    @Transactional
    void createCultuurcodeonbebouwd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cultuurcodeonbebouwd
        var returnedCultuurcodeonbebouwd = om.readValue(
            restCultuurcodeonbebouwdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuurcodeonbebouwd)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Cultuurcodeonbebouwd.class
        );

        // Validate the Cultuurcodeonbebouwd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCultuurcodeonbebouwdUpdatableFieldsEquals(
            returnedCultuurcodeonbebouwd,
            getPersistedCultuurcodeonbebouwd(returnedCultuurcodeonbebouwd)
        );
    }

    @Test
    @Transactional
    void createCultuurcodeonbebouwdWithExistingId() throws Exception {
        // Create the Cultuurcodeonbebouwd with an existing ID
        cultuurcodeonbebouwd.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCultuurcodeonbebouwdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuurcodeonbebouwd)))
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodeonbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCultuurcodeonbebouwds() throws Exception {
        // Initialize the database
        cultuurcodeonbebouwdRepository.saveAndFlush(cultuurcodeonbebouwd);

        // Get all the cultuurcodeonbebouwdList
        restCultuurcodeonbebouwdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cultuurcodeonbebouwd.getId().intValue())))
            .andExpect(jsonPath("$.[*].cultuurcodeonbebouwd").value(hasItem(DEFAULT_CULTUURCODEONBEBOUWD)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidcultuurcodeonbebouwd").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidcultuurcodeonbebouwd").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD.toString())
                )
            )
            .andExpect(jsonPath("$.[*].naamcultuurcodeonbebouwd").value(hasItem(DEFAULT_NAAMCULTUURCODEONBEBOUWD)));
    }

    @Test
    @Transactional
    void getCultuurcodeonbebouwd() throws Exception {
        // Initialize the database
        cultuurcodeonbebouwdRepository.saveAndFlush(cultuurcodeonbebouwd);

        // Get the cultuurcodeonbebouwd
        restCultuurcodeonbebouwdMockMvc
            .perform(get(ENTITY_API_URL_ID, cultuurcodeonbebouwd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cultuurcodeonbebouwd.getId().intValue()))
            .andExpect(jsonPath("$.cultuurcodeonbebouwd").value(DEFAULT_CULTUURCODEONBEBOUWD))
            .andExpect(
                jsonPath("$.datumbegingeldigheidcultuurcodeonbebouwd").value(DEFAULT_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD.toString())
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidcultuurcodeonbebouwd").value(DEFAULT_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD.toString())
            )
            .andExpect(jsonPath("$.naamcultuurcodeonbebouwd").value(DEFAULT_NAAMCULTUURCODEONBEBOUWD));
    }

    @Test
    @Transactional
    void getNonExistingCultuurcodeonbebouwd() throws Exception {
        // Get the cultuurcodeonbebouwd
        restCultuurcodeonbebouwdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCultuurcodeonbebouwd() throws Exception {
        // Initialize the database
        cultuurcodeonbebouwdRepository.saveAndFlush(cultuurcodeonbebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuurcodeonbebouwd
        Cultuurcodeonbebouwd updatedCultuurcodeonbebouwd = cultuurcodeonbebouwdRepository
            .findById(cultuurcodeonbebouwd.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCultuurcodeonbebouwd are not directly saved in db
        em.detach(updatedCultuurcodeonbebouwd);
        updatedCultuurcodeonbebouwd
            .cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD)
            .datumbegingeldigheidcultuurcodeonbebouwd(UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD)
            .datumeindegeldigheidcultuurcodeonbebouwd(UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD)
            .naamcultuurcodeonbebouwd(UPDATED_NAAMCULTUURCODEONBEBOUWD);

        restCultuurcodeonbebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCultuurcodeonbebouwd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCultuurcodeonbebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuurcodeonbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCultuurcodeonbebouwdToMatchAllProperties(updatedCultuurcodeonbebouwd);
    }

    @Test
    @Transactional
    void putNonExistingCultuurcodeonbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodeonbebouwd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultuurcodeonbebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cultuurcodeonbebouwd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cultuurcodeonbebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodeonbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCultuurcodeonbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodeonbebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuurcodeonbebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cultuurcodeonbebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodeonbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCultuurcodeonbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodeonbebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuurcodeonbebouwdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuurcodeonbebouwd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cultuurcodeonbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCultuurcodeonbebouwdWithPatch() throws Exception {
        // Initialize the database
        cultuurcodeonbebouwdRepository.saveAndFlush(cultuurcodeonbebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuurcodeonbebouwd using partial update
        Cultuurcodeonbebouwd partialUpdatedCultuurcodeonbebouwd = new Cultuurcodeonbebouwd();
        partialUpdatedCultuurcodeonbebouwd.setId(cultuurcodeonbebouwd.getId());

        partialUpdatedCultuurcodeonbebouwd
            .datumbegingeldigheidcultuurcodeonbebouwd(UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD)
            .datumeindegeldigheidcultuurcodeonbebouwd(UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD);

        restCultuurcodeonbebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCultuurcodeonbebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCultuurcodeonbebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuurcodeonbebouwd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCultuurcodeonbebouwdUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCultuurcodeonbebouwd, cultuurcodeonbebouwd),
            getPersistedCultuurcodeonbebouwd(cultuurcodeonbebouwd)
        );
    }

    @Test
    @Transactional
    void fullUpdateCultuurcodeonbebouwdWithPatch() throws Exception {
        // Initialize the database
        cultuurcodeonbebouwdRepository.saveAndFlush(cultuurcodeonbebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuurcodeonbebouwd using partial update
        Cultuurcodeonbebouwd partialUpdatedCultuurcodeonbebouwd = new Cultuurcodeonbebouwd();
        partialUpdatedCultuurcodeonbebouwd.setId(cultuurcodeonbebouwd.getId());

        partialUpdatedCultuurcodeonbebouwd
            .cultuurcodeonbebouwd(UPDATED_CULTUURCODEONBEBOUWD)
            .datumbegingeldigheidcultuurcodeonbebouwd(UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEONBEBOUWD)
            .datumeindegeldigheidcultuurcodeonbebouwd(UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEONBEBOUWD)
            .naamcultuurcodeonbebouwd(UPDATED_NAAMCULTUURCODEONBEBOUWD);

        restCultuurcodeonbebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCultuurcodeonbebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCultuurcodeonbebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuurcodeonbebouwd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCultuurcodeonbebouwdUpdatableFieldsEquals(
            partialUpdatedCultuurcodeonbebouwd,
            getPersistedCultuurcodeonbebouwd(partialUpdatedCultuurcodeonbebouwd)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCultuurcodeonbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodeonbebouwd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultuurcodeonbebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cultuurcodeonbebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cultuurcodeonbebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodeonbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCultuurcodeonbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodeonbebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuurcodeonbebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cultuurcodeonbebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodeonbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCultuurcodeonbebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodeonbebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuurcodeonbebouwdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cultuurcodeonbebouwd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cultuurcodeonbebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCultuurcodeonbebouwd() throws Exception {
        // Initialize the database
        cultuurcodeonbebouwdRepository.saveAndFlush(cultuurcodeonbebouwd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cultuurcodeonbebouwd
        restCultuurcodeonbebouwdMockMvc
            .perform(delete(ENTITY_API_URL_ID, cultuurcodeonbebouwd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cultuurcodeonbebouwdRepository.count();
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

    protected Cultuurcodeonbebouwd getPersistedCultuurcodeonbebouwd(Cultuurcodeonbebouwd cultuurcodeonbebouwd) {
        return cultuurcodeonbebouwdRepository.findById(cultuurcodeonbebouwd.getId()).orElseThrow();
    }

    protected void assertPersistedCultuurcodeonbebouwdToMatchAllProperties(Cultuurcodeonbebouwd expectedCultuurcodeonbebouwd) {
        assertCultuurcodeonbebouwdAllPropertiesEquals(
            expectedCultuurcodeonbebouwd,
            getPersistedCultuurcodeonbebouwd(expectedCultuurcodeonbebouwd)
        );
    }

    protected void assertPersistedCultuurcodeonbebouwdToMatchUpdatableProperties(Cultuurcodeonbebouwd expectedCultuurcodeonbebouwd) {
        assertCultuurcodeonbebouwdAllUpdatablePropertiesEquals(
            expectedCultuurcodeonbebouwd,
            getPersistedCultuurcodeonbebouwd(expectedCultuurcodeonbebouwd)
        );
    }
}
