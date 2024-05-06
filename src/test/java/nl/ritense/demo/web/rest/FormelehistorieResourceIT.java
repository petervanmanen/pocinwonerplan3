package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.FormelehistorieAsserts.*;
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
import nl.ritense.demo.domain.Formelehistorie;
import nl.ritense.demo.repository.FormelehistorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormelehistorieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormelehistorieResourceIT {

    private static final String DEFAULT_TIJDSTIPREGISTRATIEGEGEVENS = "AAAAAAAAAA";
    private static final String UPDATED_TIJDSTIPREGISTRATIEGEGEVENS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formelehistories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormelehistorieRepository formelehistorieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormelehistorieMockMvc;

    private Formelehistorie formelehistorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formelehistorie createEntity(EntityManager em) {
        Formelehistorie formelehistorie = new Formelehistorie().tijdstipregistratiegegevens(DEFAULT_TIJDSTIPREGISTRATIEGEGEVENS);
        return formelehistorie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formelehistorie createUpdatedEntity(EntityManager em) {
        Formelehistorie formelehistorie = new Formelehistorie().tijdstipregistratiegegevens(UPDATED_TIJDSTIPREGISTRATIEGEGEVENS);
        return formelehistorie;
    }

    @BeforeEach
    public void initTest() {
        formelehistorie = createEntity(em);
    }

    @Test
    @Transactional
    void createFormelehistorie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Formelehistorie
        var returnedFormelehistorie = om.readValue(
            restFormelehistorieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formelehistorie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Formelehistorie.class
        );

        // Validate the Formelehistorie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFormelehistorieUpdatableFieldsEquals(returnedFormelehistorie, getPersistedFormelehistorie(returnedFormelehistorie));
    }

    @Test
    @Transactional
    void createFormelehistorieWithExistingId() throws Exception {
        // Create the Formelehistorie with an existing ID
        formelehistorie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormelehistorieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formelehistorie)))
            .andExpect(status().isBadRequest());

        // Validate the Formelehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormelehistories() throws Exception {
        // Initialize the database
        formelehistorieRepository.saveAndFlush(formelehistorie);

        // Get all the formelehistorieList
        restFormelehistorieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formelehistorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].tijdstipregistratiegegevens").value(hasItem(DEFAULT_TIJDSTIPREGISTRATIEGEGEVENS)));
    }

    @Test
    @Transactional
    void getFormelehistorie() throws Exception {
        // Initialize the database
        formelehistorieRepository.saveAndFlush(formelehistorie);

        // Get the formelehistorie
        restFormelehistorieMockMvc
            .perform(get(ENTITY_API_URL_ID, formelehistorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formelehistorie.getId().intValue()))
            .andExpect(jsonPath("$.tijdstipregistratiegegevens").value(DEFAULT_TIJDSTIPREGISTRATIEGEGEVENS));
    }

    @Test
    @Transactional
    void getNonExistingFormelehistorie() throws Exception {
        // Get the formelehistorie
        restFormelehistorieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormelehistorie() throws Exception {
        // Initialize the database
        formelehistorieRepository.saveAndFlush(formelehistorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formelehistorie
        Formelehistorie updatedFormelehistorie = formelehistorieRepository.findById(formelehistorie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormelehistorie are not directly saved in db
        em.detach(updatedFormelehistorie);
        updatedFormelehistorie.tijdstipregistratiegegevens(UPDATED_TIJDSTIPREGISTRATIEGEGEVENS);

        restFormelehistorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormelehistorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFormelehistorie))
            )
            .andExpect(status().isOk());

        // Validate the Formelehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormelehistorieToMatchAllProperties(updatedFormelehistorie);
    }

    @Test
    @Transactional
    void putNonExistingFormelehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formelehistorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormelehistorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formelehistorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formelehistorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formelehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormelehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formelehistorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormelehistorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formelehistorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formelehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormelehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formelehistorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormelehistorieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formelehistorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formelehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormelehistorieWithPatch() throws Exception {
        // Initialize the database
        formelehistorieRepository.saveAndFlush(formelehistorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formelehistorie using partial update
        Formelehistorie partialUpdatedFormelehistorie = new Formelehistorie();
        partialUpdatedFormelehistorie.setId(formelehistorie.getId());

        restFormelehistorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormelehistorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormelehistorie))
            )
            .andExpect(status().isOk());

        // Validate the Formelehistorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormelehistorieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormelehistorie, formelehistorie),
            getPersistedFormelehistorie(formelehistorie)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormelehistorieWithPatch() throws Exception {
        // Initialize the database
        formelehistorieRepository.saveAndFlush(formelehistorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formelehistorie using partial update
        Formelehistorie partialUpdatedFormelehistorie = new Formelehistorie();
        partialUpdatedFormelehistorie.setId(formelehistorie.getId());

        partialUpdatedFormelehistorie.tijdstipregistratiegegevens(UPDATED_TIJDSTIPREGISTRATIEGEGEVENS);

        restFormelehistorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormelehistorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormelehistorie))
            )
            .andExpect(status().isOk());

        // Validate the Formelehistorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormelehistorieUpdatableFieldsEquals(
            partialUpdatedFormelehistorie,
            getPersistedFormelehistorie(partialUpdatedFormelehistorie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFormelehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formelehistorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormelehistorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formelehistorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formelehistorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formelehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormelehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formelehistorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormelehistorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formelehistorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formelehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormelehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formelehistorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormelehistorieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formelehistorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formelehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormelehistorie() throws Exception {
        // Initialize the database
        formelehistorieRepository.saveAndFlush(formelehistorie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formelehistorie
        restFormelehistorieMockMvc
            .perform(delete(ENTITY_API_URL_ID, formelehistorie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formelehistorieRepository.count();
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

    protected Formelehistorie getPersistedFormelehistorie(Formelehistorie formelehistorie) {
        return formelehistorieRepository.findById(formelehistorie.getId()).orElseThrow();
    }

    protected void assertPersistedFormelehistorieToMatchAllProperties(Formelehistorie expectedFormelehistorie) {
        assertFormelehistorieAllPropertiesEquals(expectedFormelehistorie, getPersistedFormelehistorie(expectedFormelehistorie));
    }

    protected void assertPersistedFormelehistorieToMatchUpdatableProperties(Formelehistorie expectedFormelehistorie) {
        assertFormelehistorieAllUpdatablePropertiesEquals(expectedFormelehistorie, getPersistedFormelehistorie(expectedFormelehistorie));
    }
}
