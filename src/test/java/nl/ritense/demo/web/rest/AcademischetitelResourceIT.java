package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AcademischetitelAsserts.*;
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
import nl.ritense.demo.domain.Academischetitel;
import nl.ritense.demo.repository.AcademischetitelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AcademischetitelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcademischetitelResourceIT {

    private static final String DEFAULT_CODEACADEMISCHETITEL = "AAAAAAAAAA";
    private static final String UPDATED_CODEACADEMISCHETITEL = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMBEGINGELDIGHEIDTITEL = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEIDTITEL = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEIDTITEL = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEIDTITEL = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGACADEMISCHETITEL = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGACADEMISCHETITEL = "BBBBBBBBBB";

    private static final String DEFAULT_POSITIEACADEMISCHETITELTOVNAAM = "AAAAAAAAAA";
    private static final String UPDATED_POSITIEACADEMISCHETITELTOVNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/academischetitels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AcademischetitelRepository academischetitelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademischetitelMockMvc;

    private Academischetitel academischetitel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Academischetitel createEntity(EntityManager em) {
        Academischetitel academischetitel = new Academischetitel()
            .codeacademischetitel(DEFAULT_CODEACADEMISCHETITEL)
            .datumbegingeldigheidtitel(DEFAULT_DATUMBEGINGELDIGHEIDTITEL)
            .datumeindegeldigheidtitel(DEFAULT_DATUMEINDEGELDIGHEIDTITEL)
            .omschrijvingacademischetitel(DEFAULT_OMSCHRIJVINGACADEMISCHETITEL)
            .positieacademischetiteltovnaam(DEFAULT_POSITIEACADEMISCHETITELTOVNAAM);
        return academischetitel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Academischetitel createUpdatedEntity(EntityManager em) {
        Academischetitel academischetitel = new Academischetitel()
            .codeacademischetitel(UPDATED_CODEACADEMISCHETITEL)
            .datumbegingeldigheidtitel(UPDATED_DATUMBEGINGELDIGHEIDTITEL)
            .datumeindegeldigheidtitel(UPDATED_DATUMEINDEGELDIGHEIDTITEL)
            .omschrijvingacademischetitel(UPDATED_OMSCHRIJVINGACADEMISCHETITEL)
            .positieacademischetiteltovnaam(UPDATED_POSITIEACADEMISCHETITELTOVNAAM);
        return academischetitel;
    }

    @BeforeEach
    public void initTest() {
        academischetitel = createEntity(em);
    }

    @Test
    @Transactional
    void createAcademischetitel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Academischetitel
        var returnedAcademischetitel = om.readValue(
            restAcademischetitelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(academischetitel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Academischetitel.class
        );

        // Validate the Academischetitel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAcademischetitelUpdatableFieldsEquals(returnedAcademischetitel, getPersistedAcademischetitel(returnedAcademischetitel));
    }

    @Test
    @Transactional
    void createAcademischetitelWithExistingId() throws Exception {
        // Create the Academischetitel with an existing ID
        academischetitel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademischetitelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(academischetitel)))
            .andExpect(status().isBadRequest());

        // Validate the Academischetitel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcademischetitels() throws Exception {
        // Initialize the database
        academischetitelRepository.saveAndFlush(academischetitel);

        // Get all the academischetitelList
        restAcademischetitelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academischetitel.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeacademischetitel").value(hasItem(DEFAULT_CODEACADEMISCHETITEL)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidtitel").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDTITEL)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidtitel").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDTITEL)))
            .andExpect(jsonPath("$.[*].omschrijvingacademischetitel").value(hasItem(DEFAULT_OMSCHRIJVINGACADEMISCHETITEL)))
            .andExpect(jsonPath("$.[*].positieacademischetiteltovnaam").value(hasItem(DEFAULT_POSITIEACADEMISCHETITELTOVNAAM)));
    }

    @Test
    @Transactional
    void getAcademischetitel() throws Exception {
        // Initialize the database
        academischetitelRepository.saveAndFlush(academischetitel);

        // Get the academischetitel
        restAcademischetitelMockMvc
            .perform(get(ENTITY_API_URL_ID, academischetitel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academischetitel.getId().intValue()))
            .andExpect(jsonPath("$.codeacademischetitel").value(DEFAULT_CODEACADEMISCHETITEL))
            .andExpect(jsonPath("$.datumbegingeldigheidtitel").value(DEFAULT_DATUMBEGINGELDIGHEIDTITEL))
            .andExpect(jsonPath("$.datumeindegeldigheidtitel").value(DEFAULT_DATUMEINDEGELDIGHEIDTITEL))
            .andExpect(jsonPath("$.omschrijvingacademischetitel").value(DEFAULT_OMSCHRIJVINGACADEMISCHETITEL))
            .andExpect(jsonPath("$.positieacademischetiteltovnaam").value(DEFAULT_POSITIEACADEMISCHETITELTOVNAAM));
    }

    @Test
    @Transactional
    void getNonExistingAcademischetitel() throws Exception {
        // Get the academischetitel
        restAcademischetitelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAcademischetitel() throws Exception {
        // Initialize the database
        academischetitelRepository.saveAndFlush(academischetitel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the academischetitel
        Academischetitel updatedAcademischetitel = academischetitelRepository.findById(academischetitel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAcademischetitel are not directly saved in db
        em.detach(updatedAcademischetitel);
        updatedAcademischetitel
            .codeacademischetitel(UPDATED_CODEACADEMISCHETITEL)
            .datumbegingeldigheidtitel(UPDATED_DATUMBEGINGELDIGHEIDTITEL)
            .datumeindegeldigheidtitel(UPDATED_DATUMEINDEGELDIGHEIDTITEL)
            .omschrijvingacademischetitel(UPDATED_OMSCHRIJVINGACADEMISCHETITEL)
            .positieacademischetiteltovnaam(UPDATED_POSITIEACADEMISCHETITELTOVNAAM);

        restAcademischetitelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAcademischetitel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAcademischetitel))
            )
            .andExpect(status().isOk());

        // Validate the Academischetitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAcademischetitelToMatchAllProperties(updatedAcademischetitel);
    }

    @Test
    @Transactional
    void putNonExistingAcademischetitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academischetitel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademischetitelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academischetitel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(academischetitel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Academischetitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcademischetitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academischetitel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademischetitelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(academischetitel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Academischetitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcademischetitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academischetitel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademischetitelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(academischetitel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Academischetitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcademischetitelWithPatch() throws Exception {
        // Initialize the database
        academischetitelRepository.saveAndFlush(academischetitel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the academischetitel using partial update
        Academischetitel partialUpdatedAcademischetitel = new Academischetitel();
        partialUpdatedAcademischetitel.setId(academischetitel.getId());

        partialUpdatedAcademischetitel.datumbegingeldigheidtitel(UPDATED_DATUMBEGINGELDIGHEIDTITEL);

        restAcademischetitelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademischetitel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAcademischetitel))
            )
            .andExpect(status().isOk());

        // Validate the Academischetitel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAcademischetitelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAcademischetitel, academischetitel),
            getPersistedAcademischetitel(academischetitel)
        );
    }

    @Test
    @Transactional
    void fullUpdateAcademischetitelWithPatch() throws Exception {
        // Initialize the database
        academischetitelRepository.saveAndFlush(academischetitel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the academischetitel using partial update
        Academischetitel partialUpdatedAcademischetitel = new Academischetitel();
        partialUpdatedAcademischetitel.setId(academischetitel.getId());

        partialUpdatedAcademischetitel
            .codeacademischetitel(UPDATED_CODEACADEMISCHETITEL)
            .datumbegingeldigheidtitel(UPDATED_DATUMBEGINGELDIGHEIDTITEL)
            .datumeindegeldigheidtitel(UPDATED_DATUMEINDEGELDIGHEIDTITEL)
            .omschrijvingacademischetitel(UPDATED_OMSCHRIJVINGACADEMISCHETITEL)
            .positieacademischetiteltovnaam(UPDATED_POSITIEACADEMISCHETITELTOVNAAM);

        restAcademischetitelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademischetitel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAcademischetitel))
            )
            .andExpect(status().isOk());

        // Validate the Academischetitel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAcademischetitelUpdatableFieldsEquals(
            partialUpdatedAcademischetitel,
            getPersistedAcademischetitel(partialUpdatedAcademischetitel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAcademischetitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academischetitel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademischetitelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, academischetitel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(academischetitel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Academischetitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcademischetitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academischetitel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademischetitelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(academischetitel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Academischetitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcademischetitel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academischetitel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademischetitelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(academischetitel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Academischetitel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcademischetitel() throws Exception {
        // Initialize the database
        academischetitelRepository.saveAndFlush(academischetitel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the academischetitel
        restAcademischetitelMockMvc
            .perform(delete(ENTITY_API_URL_ID, academischetitel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return academischetitelRepository.count();
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

    protected Academischetitel getPersistedAcademischetitel(Academischetitel academischetitel) {
        return academischetitelRepository.findById(academischetitel.getId()).orElseThrow();
    }

    protected void assertPersistedAcademischetitelToMatchAllProperties(Academischetitel expectedAcademischetitel) {
        assertAcademischetitelAllPropertiesEquals(expectedAcademischetitel, getPersistedAcademischetitel(expectedAcademischetitel));
    }

    protected void assertPersistedAcademischetitelToMatchUpdatableProperties(Academischetitel expectedAcademischetitel) {
        assertAcademischetitelAllUpdatablePropertiesEquals(
            expectedAcademischetitel,
            getPersistedAcademischetitel(expectedAcademischetitel)
        );
    }
}
