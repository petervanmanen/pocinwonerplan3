package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.CultuurcodebebouwdAsserts.*;
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
import nl.ritense.demo.domain.Cultuurcodebebouwd;
import nl.ritense.demo.repository.CultuurcodebebouwdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CultuurcodebebouwdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CultuurcodebebouwdResourceIT {

    private static final String DEFAULT_CULTUURCODEBEBOUWD = "AAAAAAAAAA";
    private static final String UPDATED_CULTUURCODEBEBOUWD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDCULTUURCODEBEBOUWD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEBEBOUWD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAMCULTUURCODEBEBOUWD = "AAAAAAAAAA";
    private static final String UPDATED_NAAMCULTUURCODEBEBOUWD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cultuurcodebebouwds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CultuurcodebebouwdRepository cultuurcodebebouwdRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCultuurcodebebouwdMockMvc;

    private Cultuurcodebebouwd cultuurcodebebouwd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cultuurcodebebouwd createEntity(EntityManager em) {
        Cultuurcodebebouwd cultuurcodebebouwd = new Cultuurcodebebouwd()
            .cultuurcodebebouwd(DEFAULT_CULTUURCODEBEBOUWD)
            .datumbegingeldigheidcultuurcodebebouwd(DEFAULT_DATUMBEGINGELDIGHEIDCULTUURCODEBEBOUWD)
            .datumeindegeldigheidcultuurcodebebouwd(DEFAULT_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD)
            .naamcultuurcodebebouwd(DEFAULT_NAAMCULTUURCODEBEBOUWD);
        return cultuurcodebebouwd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cultuurcodebebouwd createUpdatedEntity(EntityManager em) {
        Cultuurcodebebouwd cultuurcodebebouwd = new Cultuurcodebebouwd()
            .cultuurcodebebouwd(UPDATED_CULTUURCODEBEBOUWD)
            .datumbegingeldigheidcultuurcodebebouwd(UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEBEBOUWD)
            .datumeindegeldigheidcultuurcodebebouwd(UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD)
            .naamcultuurcodebebouwd(UPDATED_NAAMCULTUURCODEBEBOUWD);
        return cultuurcodebebouwd;
    }

    @BeforeEach
    public void initTest() {
        cultuurcodebebouwd = createEntity(em);
    }

    @Test
    @Transactional
    void createCultuurcodebebouwd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cultuurcodebebouwd
        var returnedCultuurcodebebouwd = om.readValue(
            restCultuurcodebebouwdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuurcodebebouwd)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Cultuurcodebebouwd.class
        );

        // Validate the Cultuurcodebebouwd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCultuurcodebebouwdUpdatableFieldsEquals(
            returnedCultuurcodebebouwd,
            getPersistedCultuurcodebebouwd(returnedCultuurcodebebouwd)
        );
    }

    @Test
    @Transactional
    void createCultuurcodebebouwdWithExistingId() throws Exception {
        // Create the Cultuurcodebebouwd with an existing ID
        cultuurcodebebouwd.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCultuurcodebebouwdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuurcodebebouwd)))
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodebebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCultuurcodebebouwds() throws Exception {
        // Initialize the database
        cultuurcodebebouwdRepository.saveAndFlush(cultuurcodebebouwd);

        // Get all the cultuurcodebebouwdList
        restCultuurcodebebouwdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cultuurcodebebouwd.getId().intValue())))
            .andExpect(jsonPath("$.[*].cultuurcodebebouwd").value(hasItem(DEFAULT_CULTUURCODEBEBOUWD)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidcultuurcodebebouwd").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDCULTUURCODEBEBOUWD.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidcultuurcodebebouwd").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD.toString())
                )
            )
            .andExpect(jsonPath("$.[*].naamcultuurcodebebouwd").value(hasItem(DEFAULT_NAAMCULTUURCODEBEBOUWD)));
    }

    @Test
    @Transactional
    void getCultuurcodebebouwd() throws Exception {
        // Initialize the database
        cultuurcodebebouwdRepository.saveAndFlush(cultuurcodebebouwd);

        // Get the cultuurcodebebouwd
        restCultuurcodebebouwdMockMvc
            .perform(get(ENTITY_API_URL_ID, cultuurcodebebouwd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cultuurcodebebouwd.getId().intValue()))
            .andExpect(jsonPath("$.cultuurcodebebouwd").value(DEFAULT_CULTUURCODEBEBOUWD))
            .andExpect(
                jsonPath("$.datumbegingeldigheidcultuurcodebebouwd").value(DEFAULT_DATUMBEGINGELDIGHEIDCULTUURCODEBEBOUWD.toString())
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidcultuurcodebebouwd").value(DEFAULT_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD.toString())
            )
            .andExpect(jsonPath("$.naamcultuurcodebebouwd").value(DEFAULT_NAAMCULTUURCODEBEBOUWD));
    }

    @Test
    @Transactional
    void getNonExistingCultuurcodebebouwd() throws Exception {
        // Get the cultuurcodebebouwd
        restCultuurcodebebouwdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCultuurcodebebouwd() throws Exception {
        // Initialize the database
        cultuurcodebebouwdRepository.saveAndFlush(cultuurcodebebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuurcodebebouwd
        Cultuurcodebebouwd updatedCultuurcodebebouwd = cultuurcodebebouwdRepository.findById(cultuurcodebebouwd.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCultuurcodebebouwd are not directly saved in db
        em.detach(updatedCultuurcodebebouwd);
        updatedCultuurcodebebouwd
            .cultuurcodebebouwd(UPDATED_CULTUURCODEBEBOUWD)
            .datumbegingeldigheidcultuurcodebebouwd(UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEBEBOUWD)
            .datumeindegeldigheidcultuurcodebebouwd(UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD)
            .naamcultuurcodebebouwd(UPDATED_NAAMCULTUURCODEBEBOUWD);

        restCultuurcodebebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCultuurcodebebouwd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCultuurcodebebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuurcodebebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCultuurcodebebouwdToMatchAllProperties(updatedCultuurcodebebouwd);
    }

    @Test
    @Transactional
    void putNonExistingCultuurcodebebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodebebouwd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultuurcodebebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cultuurcodebebouwd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cultuurcodebebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodebebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCultuurcodebebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodebebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuurcodebebouwdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cultuurcodebebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodebebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCultuurcodebebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodebebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuurcodebebouwdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cultuurcodebebouwd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cultuurcodebebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCultuurcodebebouwdWithPatch() throws Exception {
        // Initialize the database
        cultuurcodebebouwdRepository.saveAndFlush(cultuurcodebebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuurcodebebouwd using partial update
        Cultuurcodebebouwd partialUpdatedCultuurcodebebouwd = new Cultuurcodebebouwd();
        partialUpdatedCultuurcodebebouwd.setId(cultuurcodebebouwd.getId());

        partialUpdatedCultuurcodebebouwd
            .datumeindegeldigheidcultuurcodebebouwd(UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD)
            .naamcultuurcodebebouwd(UPDATED_NAAMCULTUURCODEBEBOUWD);

        restCultuurcodebebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCultuurcodebebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCultuurcodebebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuurcodebebouwd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCultuurcodebebouwdUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCultuurcodebebouwd, cultuurcodebebouwd),
            getPersistedCultuurcodebebouwd(cultuurcodebebouwd)
        );
    }

    @Test
    @Transactional
    void fullUpdateCultuurcodebebouwdWithPatch() throws Exception {
        // Initialize the database
        cultuurcodebebouwdRepository.saveAndFlush(cultuurcodebebouwd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cultuurcodebebouwd using partial update
        Cultuurcodebebouwd partialUpdatedCultuurcodebebouwd = new Cultuurcodebebouwd();
        partialUpdatedCultuurcodebebouwd.setId(cultuurcodebebouwd.getId());

        partialUpdatedCultuurcodebebouwd
            .cultuurcodebebouwd(UPDATED_CULTUURCODEBEBOUWD)
            .datumbegingeldigheidcultuurcodebebouwd(UPDATED_DATUMBEGINGELDIGHEIDCULTUURCODEBEBOUWD)
            .datumeindegeldigheidcultuurcodebebouwd(UPDATED_DATUMEINDEGELDIGHEIDCULTUURCODEBEBOUWD)
            .naamcultuurcodebebouwd(UPDATED_NAAMCULTUURCODEBEBOUWD);

        restCultuurcodebebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCultuurcodebebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCultuurcodebebouwd))
            )
            .andExpect(status().isOk());

        // Validate the Cultuurcodebebouwd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCultuurcodebebouwdUpdatableFieldsEquals(
            partialUpdatedCultuurcodebebouwd,
            getPersistedCultuurcodebebouwd(partialUpdatedCultuurcodebebouwd)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCultuurcodebebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodebebouwd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultuurcodebebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cultuurcodebebouwd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cultuurcodebebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodebebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCultuurcodebebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodebebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuurcodebebouwdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cultuurcodebebouwd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cultuurcodebebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCultuurcodebebouwd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cultuurcodebebouwd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCultuurcodebebouwdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cultuurcodebebouwd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cultuurcodebebouwd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCultuurcodebebouwd() throws Exception {
        // Initialize the database
        cultuurcodebebouwdRepository.saveAndFlush(cultuurcodebebouwd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cultuurcodebebouwd
        restCultuurcodebebouwdMockMvc
            .perform(delete(ENTITY_API_URL_ID, cultuurcodebebouwd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cultuurcodebebouwdRepository.count();
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

    protected Cultuurcodebebouwd getPersistedCultuurcodebebouwd(Cultuurcodebebouwd cultuurcodebebouwd) {
        return cultuurcodebebouwdRepository.findById(cultuurcodebebouwd.getId()).orElseThrow();
    }

    protected void assertPersistedCultuurcodebebouwdToMatchAllProperties(Cultuurcodebebouwd expectedCultuurcodebebouwd) {
        assertCultuurcodebebouwdAllPropertiesEquals(expectedCultuurcodebebouwd, getPersistedCultuurcodebebouwd(expectedCultuurcodebebouwd));
    }

    protected void assertPersistedCultuurcodebebouwdToMatchUpdatableProperties(Cultuurcodebebouwd expectedCultuurcodebebouwd) {
        assertCultuurcodebebouwdAllUpdatablePropertiesEquals(
            expectedCultuurcodebebouwd,
            getPersistedCultuurcodebebouwd(expectedCultuurcodebebouwd)
        );
    }
}
