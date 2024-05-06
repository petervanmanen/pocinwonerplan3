package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerzuimmeldingAsserts.*;
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
import nl.ritense.demo.domain.Verzuimmelding;
import nl.ritense.demo.repository.VerzuimmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerzuimmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerzuimmeldingResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VOORSTELSCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_VOORSTELSCHOOL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verzuimmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerzuimmeldingRepository verzuimmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerzuimmeldingMockMvc;

    private Verzuimmelding verzuimmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzuimmelding createEntity(EntityManager em) {
        Verzuimmelding verzuimmelding = new Verzuimmelding()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .voorstelschool(DEFAULT_VOORSTELSCHOOL);
        return verzuimmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzuimmelding createUpdatedEntity(EntityManager em) {
        Verzuimmelding verzuimmelding = new Verzuimmelding()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .voorstelschool(UPDATED_VOORSTELSCHOOL);
        return verzuimmelding;
    }

    @BeforeEach
    public void initTest() {
        verzuimmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createVerzuimmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verzuimmelding
        var returnedVerzuimmelding = om.readValue(
            restVerzuimmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuimmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verzuimmelding.class
        );

        // Validate the Verzuimmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerzuimmeldingUpdatableFieldsEquals(returnedVerzuimmelding, getPersistedVerzuimmelding(returnedVerzuimmelding));
    }

    @Test
    @Transactional
    void createVerzuimmeldingWithExistingId() throws Exception {
        // Create the Verzuimmelding with an existing ID
        verzuimmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerzuimmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuimmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Verzuimmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerzuimmeldings() throws Exception {
        // Initialize the database
        verzuimmeldingRepository.saveAndFlush(verzuimmelding);

        // Get all the verzuimmeldingList
        restVerzuimmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verzuimmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].voorstelschool").value(hasItem(DEFAULT_VOORSTELSCHOOL)));
    }

    @Test
    @Transactional
    void getVerzuimmelding() throws Exception {
        // Initialize the database
        verzuimmeldingRepository.saveAndFlush(verzuimmelding);

        // Get the verzuimmelding
        restVerzuimmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, verzuimmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verzuimmelding.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.voorstelschool").value(DEFAULT_VOORSTELSCHOOL));
    }

    @Test
    @Transactional
    void getNonExistingVerzuimmelding() throws Exception {
        // Get the verzuimmelding
        restVerzuimmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerzuimmelding() throws Exception {
        // Initialize the database
        verzuimmeldingRepository.saveAndFlush(verzuimmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuimmelding
        Verzuimmelding updatedVerzuimmelding = verzuimmeldingRepository.findById(verzuimmelding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerzuimmelding are not directly saved in db
        em.detach(updatedVerzuimmelding);
        updatedVerzuimmelding.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).voorstelschool(UPDATED_VOORSTELSCHOOL);

        restVerzuimmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerzuimmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerzuimmelding))
            )
            .andExpect(status().isOk());

        // Validate the Verzuimmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerzuimmeldingToMatchAllProperties(updatedVerzuimmelding);
    }

    @Test
    @Transactional
    void putNonExistingVerzuimmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzuimmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verzuimmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verzuimmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuimmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerzuimmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verzuimmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuimmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerzuimmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzuimmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzuimmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerzuimmeldingWithPatch() throws Exception {
        // Initialize the database
        verzuimmeldingRepository.saveAndFlush(verzuimmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuimmelding using partial update
        Verzuimmelding partialUpdatedVerzuimmelding = new Verzuimmelding();
        partialUpdatedVerzuimmelding.setId(verzuimmelding.getId());

        partialUpdatedVerzuimmelding.datumeinde(UPDATED_DATUMEINDE);

        restVerzuimmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzuimmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzuimmelding))
            )
            .andExpect(status().isOk());

        // Validate the Verzuimmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzuimmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerzuimmelding, verzuimmelding),
            getPersistedVerzuimmelding(verzuimmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerzuimmeldingWithPatch() throws Exception {
        // Initialize the database
        verzuimmeldingRepository.saveAndFlush(verzuimmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzuimmelding using partial update
        Verzuimmelding partialUpdatedVerzuimmelding = new Verzuimmelding();
        partialUpdatedVerzuimmelding.setId(verzuimmelding.getId());

        partialUpdatedVerzuimmelding.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).voorstelschool(UPDATED_VOORSTELSCHOOL);

        restVerzuimmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzuimmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzuimmelding))
            )
            .andExpect(status().isOk());

        // Validate the Verzuimmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzuimmeldingUpdatableFieldsEquals(partialUpdatedVerzuimmelding, getPersistedVerzuimmelding(partialUpdatedVerzuimmelding));
    }

    @Test
    @Transactional
    void patchNonExistingVerzuimmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzuimmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verzuimmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verzuimmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuimmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerzuimmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verzuimmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzuimmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerzuimmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzuimmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzuimmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verzuimmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzuimmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerzuimmelding() throws Exception {
        // Initialize the database
        verzuimmeldingRepository.saveAndFlush(verzuimmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verzuimmelding
        restVerzuimmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, verzuimmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verzuimmeldingRepository.count();
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

    protected Verzuimmelding getPersistedVerzuimmelding(Verzuimmelding verzuimmelding) {
        return verzuimmeldingRepository.findById(verzuimmelding.getId()).orElseThrow();
    }

    protected void assertPersistedVerzuimmeldingToMatchAllProperties(Verzuimmelding expectedVerzuimmelding) {
        assertVerzuimmeldingAllPropertiesEquals(expectedVerzuimmelding, getPersistedVerzuimmelding(expectedVerzuimmelding));
    }

    protected void assertPersistedVerzuimmeldingToMatchUpdatableProperties(Verzuimmelding expectedVerzuimmelding) {
        assertVerzuimmeldingAllUpdatablePropertiesEquals(expectedVerzuimmelding, getPersistedVerzuimmelding(expectedVerzuimmelding));
    }
}
