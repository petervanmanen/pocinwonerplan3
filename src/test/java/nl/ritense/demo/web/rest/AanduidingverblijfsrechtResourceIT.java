package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanduidingverblijfsrechtAsserts.*;
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
import nl.ritense.demo.domain.Aanduidingverblijfsrecht;
import nl.ritense.demo.repository.AanduidingverblijfsrechtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanduidingverblijfsrechtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanduidingverblijfsrechtResourceIT {

    private static final LocalDate DEFAULT_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VERBLIJFSRECHTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VERBLIJFSRECHTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VERBLIJFSRECHTOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_VERBLIJFSRECHTOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aanduidingverblijfsrechts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanduidingverblijfsrechtRepository aanduidingverblijfsrechtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanduidingverblijfsrechtMockMvc;

    private Aanduidingverblijfsrecht aanduidingverblijfsrecht;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanduidingverblijfsrecht createEntity(EntityManager em) {
        Aanduidingverblijfsrecht aanduidingverblijfsrecht = new Aanduidingverblijfsrecht()
            .datumaanvanggeldigheidverblijfsrecht(DEFAULT_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT)
            .datumeindegeldigheidverblijfsrecht(DEFAULT_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT)
            .verblijfsrechtnummer(DEFAULT_VERBLIJFSRECHTNUMMER)
            .verblijfsrechtomschrijving(DEFAULT_VERBLIJFSRECHTOMSCHRIJVING);
        return aanduidingverblijfsrecht;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanduidingverblijfsrecht createUpdatedEntity(EntityManager em) {
        Aanduidingverblijfsrecht aanduidingverblijfsrecht = new Aanduidingverblijfsrecht()
            .datumaanvanggeldigheidverblijfsrecht(UPDATED_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT)
            .datumeindegeldigheidverblijfsrecht(UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT)
            .verblijfsrechtnummer(UPDATED_VERBLIJFSRECHTNUMMER)
            .verblijfsrechtomschrijving(UPDATED_VERBLIJFSRECHTOMSCHRIJVING);
        return aanduidingverblijfsrecht;
    }

    @BeforeEach
    public void initTest() {
        aanduidingverblijfsrecht = createEntity(em);
    }

    @Test
    @Transactional
    void createAanduidingverblijfsrecht() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanduidingverblijfsrecht
        var returnedAanduidingverblijfsrecht = om.readValue(
            restAanduidingverblijfsrechtMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanduidingverblijfsrecht))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanduidingverblijfsrecht.class
        );

        // Validate the Aanduidingverblijfsrecht in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanduidingverblijfsrechtUpdatableFieldsEquals(
            returnedAanduidingverblijfsrecht,
            getPersistedAanduidingverblijfsrecht(returnedAanduidingverblijfsrecht)
        );
    }

    @Test
    @Transactional
    void createAanduidingverblijfsrechtWithExistingId() throws Exception {
        // Create the Aanduidingverblijfsrecht with an existing ID
        aanduidingverblijfsrecht.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanduidingverblijfsrechtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanduidingverblijfsrecht)))
            .andExpect(status().isBadRequest());

        // Validate the Aanduidingverblijfsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanduidingverblijfsrechts() throws Exception {
        // Initialize the database
        aanduidingverblijfsrechtRepository.saveAndFlush(aanduidingverblijfsrecht);

        // Get all the aanduidingverblijfsrechtList
        restAanduidingverblijfsrechtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanduidingverblijfsrecht.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumaanvanggeldigheidverblijfsrecht").value(
                    hasItem(DEFAULT_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidverblijfsrecht").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT.toString()))
            )
            .andExpect(jsonPath("$.[*].verblijfsrechtnummer").value(hasItem(DEFAULT_VERBLIJFSRECHTNUMMER)))
            .andExpect(jsonPath("$.[*].verblijfsrechtomschrijving").value(hasItem(DEFAULT_VERBLIJFSRECHTOMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getAanduidingverblijfsrecht() throws Exception {
        // Initialize the database
        aanduidingverblijfsrechtRepository.saveAndFlush(aanduidingverblijfsrecht);

        // Get the aanduidingverblijfsrecht
        restAanduidingverblijfsrechtMockMvc
            .perform(get(ENTITY_API_URL_ID, aanduidingverblijfsrecht.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanduidingverblijfsrecht.getId().intValue()))
            .andExpect(jsonPath("$.datumaanvanggeldigheidverblijfsrecht").value(DEFAULT_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidverblijfsrecht").value(DEFAULT_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT.toString()))
            .andExpect(jsonPath("$.verblijfsrechtnummer").value(DEFAULT_VERBLIJFSRECHTNUMMER))
            .andExpect(jsonPath("$.verblijfsrechtomschrijving").value(DEFAULT_VERBLIJFSRECHTOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingAanduidingverblijfsrecht() throws Exception {
        // Get the aanduidingverblijfsrecht
        restAanduidingverblijfsrechtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanduidingverblijfsrecht() throws Exception {
        // Initialize the database
        aanduidingverblijfsrechtRepository.saveAndFlush(aanduidingverblijfsrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanduidingverblijfsrecht
        Aanduidingverblijfsrecht updatedAanduidingverblijfsrecht = aanduidingverblijfsrechtRepository
            .findById(aanduidingverblijfsrecht.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAanduidingverblijfsrecht are not directly saved in db
        em.detach(updatedAanduidingverblijfsrecht);
        updatedAanduidingverblijfsrecht
            .datumaanvanggeldigheidverblijfsrecht(UPDATED_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT)
            .datumeindegeldigheidverblijfsrecht(UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT)
            .verblijfsrechtnummer(UPDATED_VERBLIJFSRECHTNUMMER)
            .verblijfsrechtomschrijving(UPDATED_VERBLIJFSRECHTOMSCHRIJVING);

        restAanduidingverblijfsrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanduidingverblijfsrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanduidingverblijfsrecht))
            )
            .andExpect(status().isOk());

        // Validate the Aanduidingverblijfsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanduidingverblijfsrechtToMatchAllProperties(updatedAanduidingverblijfsrecht);
    }

    @Test
    @Transactional
    void putNonExistingAanduidingverblijfsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanduidingverblijfsrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanduidingverblijfsrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanduidingverblijfsrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanduidingverblijfsrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanduidingverblijfsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanduidingverblijfsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanduidingverblijfsrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanduidingverblijfsrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanduidingverblijfsrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanduidingverblijfsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanduidingverblijfsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanduidingverblijfsrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanduidingverblijfsrechtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanduidingverblijfsrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanduidingverblijfsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanduidingverblijfsrechtWithPatch() throws Exception {
        // Initialize the database
        aanduidingverblijfsrechtRepository.saveAndFlush(aanduidingverblijfsrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanduidingverblijfsrecht using partial update
        Aanduidingverblijfsrecht partialUpdatedAanduidingverblijfsrecht = new Aanduidingverblijfsrecht();
        partialUpdatedAanduidingverblijfsrecht.setId(aanduidingverblijfsrecht.getId());

        partialUpdatedAanduidingverblijfsrecht
            .datumaanvanggeldigheidverblijfsrecht(UPDATED_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT)
            .datumeindegeldigheidverblijfsrecht(UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT)
            .verblijfsrechtomschrijving(UPDATED_VERBLIJFSRECHTOMSCHRIJVING);

        restAanduidingverblijfsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanduidingverblijfsrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanduidingverblijfsrecht))
            )
            .andExpect(status().isOk());

        // Validate the Aanduidingverblijfsrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanduidingverblijfsrechtUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAanduidingverblijfsrecht, aanduidingverblijfsrecht),
            getPersistedAanduidingverblijfsrecht(aanduidingverblijfsrecht)
        );
    }

    @Test
    @Transactional
    void fullUpdateAanduidingverblijfsrechtWithPatch() throws Exception {
        // Initialize the database
        aanduidingverblijfsrechtRepository.saveAndFlush(aanduidingverblijfsrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanduidingverblijfsrecht using partial update
        Aanduidingverblijfsrecht partialUpdatedAanduidingverblijfsrecht = new Aanduidingverblijfsrecht();
        partialUpdatedAanduidingverblijfsrecht.setId(aanduidingverblijfsrecht.getId());

        partialUpdatedAanduidingverblijfsrecht
            .datumaanvanggeldigheidverblijfsrecht(UPDATED_DATUMAANVANGGELDIGHEIDVERBLIJFSRECHT)
            .datumeindegeldigheidverblijfsrecht(UPDATED_DATUMEINDEGELDIGHEIDVERBLIJFSRECHT)
            .verblijfsrechtnummer(UPDATED_VERBLIJFSRECHTNUMMER)
            .verblijfsrechtomschrijving(UPDATED_VERBLIJFSRECHTOMSCHRIJVING);

        restAanduidingverblijfsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanduidingverblijfsrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanduidingverblijfsrecht))
            )
            .andExpect(status().isOk());

        // Validate the Aanduidingverblijfsrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanduidingverblijfsrechtUpdatableFieldsEquals(
            partialUpdatedAanduidingverblijfsrecht,
            getPersistedAanduidingverblijfsrecht(partialUpdatedAanduidingverblijfsrecht)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAanduidingverblijfsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanduidingverblijfsrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanduidingverblijfsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanduidingverblijfsrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanduidingverblijfsrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanduidingverblijfsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanduidingverblijfsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanduidingverblijfsrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanduidingverblijfsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanduidingverblijfsrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanduidingverblijfsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanduidingverblijfsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanduidingverblijfsrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanduidingverblijfsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanduidingverblijfsrecht))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanduidingverblijfsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanduidingverblijfsrecht() throws Exception {
        // Initialize the database
        aanduidingverblijfsrechtRepository.saveAndFlush(aanduidingverblijfsrecht);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanduidingverblijfsrecht
        restAanduidingverblijfsrechtMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanduidingverblijfsrecht.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanduidingverblijfsrechtRepository.count();
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

    protected Aanduidingverblijfsrecht getPersistedAanduidingverblijfsrecht(Aanduidingverblijfsrecht aanduidingverblijfsrecht) {
        return aanduidingverblijfsrechtRepository.findById(aanduidingverblijfsrecht.getId()).orElseThrow();
    }

    protected void assertPersistedAanduidingverblijfsrechtToMatchAllProperties(Aanduidingverblijfsrecht expectedAanduidingverblijfsrecht) {
        assertAanduidingverblijfsrechtAllPropertiesEquals(
            expectedAanduidingverblijfsrecht,
            getPersistedAanduidingverblijfsrecht(expectedAanduidingverblijfsrecht)
        );
    }

    protected void assertPersistedAanduidingverblijfsrechtToMatchUpdatableProperties(
        Aanduidingverblijfsrecht expectedAanduidingverblijfsrecht
    ) {
        assertAanduidingverblijfsrechtAllUpdatablePropertiesEquals(
            expectedAanduidingverblijfsrecht,
            getPersistedAanduidingverblijfsrecht(expectedAanduidingverblijfsrecht)
        );
    }
}
