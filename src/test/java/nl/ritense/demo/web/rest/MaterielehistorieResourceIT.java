package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MaterielehistorieAsserts.*;
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
import nl.ritense.demo.domain.Materielehistorie;
import nl.ritense.demo.repository.MaterielehistorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MaterielehistorieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterielehistorieResourceIT {

    private static final String DEFAULT_DATUMBEGINGELDIGHEIDGEGEVENS = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEIDGEGEVENS = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEIDGEGEVENS = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEIDGEGEVENS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/materielehistories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MaterielehistorieRepository materielehistorieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterielehistorieMockMvc;

    private Materielehistorie materielehistorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materielehistorie createEntity(EntityManager em) {
        Materielehistorie materielehistorie = new Materielehistorie()
            .datumbegingeldigheidgegevens(DEFAULT_DATUMBEGINGELDIGHEIDGEGEVENS)
            .datumeindegeldigheidgegevens(DEFAULT_DATUMEINDEGELDIGHEIDGEGEVENS);
        return materielehistorie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materielehistorie createUpdatedEntity(EntityManager em) {
        Materielehistorie materielehistorie = new Materielehistorie()
            .datumbegingeldigheidgegevens(UPDATED_DATUMBEGINGELDIGHEIDGEGEVENS)
            .datumeindegeldigheidgegevens(UPDATED_DATUMEINDEGELDIGHEIDGEGEVENS);
        return materielehistorie;
    }

    @BeforeEach
    public void initTest() {
        materielehistorie = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterielehistorie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Materielehistorie
        var returnedMaterielehistorie = om.readValue(
            restMaterielehistorieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielehistorie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Materielehistorie.class
        );

        // Validate the Materielehistorie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMaterielehistorieUpdatableFieldsEquals(returnedMaterielehistorie, getPersistedMaterielehistorie(returnedMaterielehistorie));
    }

    @Test
    @Transactional
    void createMaterielehistorieWithExistingId() throws Exception {
        // Create the Materielehistorie with an existing ID
        materielehistorie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterielehistorieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielehistorie)))
            .andExpect(status().isBadRequest());

        // Validate the Materielehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterielehistories() throws Exception {
        // Initialize the database
        materielehistorieRepository.saveAndFlush(materielehistorie);

        // Get all the materielehistorieList
        restMaterielehistorieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materielehistorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidgegevens").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDGEGEVENS)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidgegevens").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDGEGEVENS)));
    }

    @Test
    @Transactional
    void getMaterielehistorie() throws Exception {
        // Initialize the database
        materielehistorieRepository.saveAndFlush(materielehistorie);

        // Get the materielehistorie
        restMaterielehistorieMockMvc
            .perform(get(ENTITY_API_URL_ID, materielehistorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materielehistorie.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidgegevens").value(DEFAULT_DATUMBEGINGELDIGHEIDGEGEVENS))
            .andExpect(jsonPath("$.datumeindegeldigheidgegevens").value(DEFAULT_DATUMEINDEGELDIGHEIDGEGEVENS));
    }

    @Test
    @Transactional
    void getNonExistingMaterielehistorie() throws Exception {
        // Get the materielehistorie
        restMaterielehistorieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterielehistorie() throws Exception {
        // Initialize the database
        materielehistorieRepository.saveAndFlush(materielehistorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielehistorie
        Materielehistorie updatedMaterielehistorie = materielehistorieRepository.findById(materielehistorie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMaterielehistorie are not directly saved in db
        em.detach(updatedMaterielehistorie);
        updatedMaterielehistorie
            .datumbegingeldigheidgegevens(UPDATED_DATUMBEGINGELDIGHEIDGEGEVENS)
            .datumeindegeldigheidgegevens(UPDATED_DATUMEINDEGELDIGHEIDGEGEVENS);

        restMaterielehistorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterielehistorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMaterielehistorie))
            )
            .andExpect(status().isOk());

        // Validate the Materielehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMaterielehistorieToMatchAllProperties(updatedMaterielehistorie);
    }

    @Test
    @Transactional
    void putNonExistingMaterielehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielehistorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterielehistorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materielehistorie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materielehistorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materielehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterielehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielehistorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielehistorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materielehistorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materielehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterielehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielehistorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielehistorieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielehistorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materielehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterielehistorieWithPatch() throws Exception {
        // Initialize the database
        materielehistorieRepository.saveAndFlush(materielehistorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielehistorie using partial update
        Materielehistorie partialUpdatedMaterielehistorie = new Materielehistorie();
        partialUpdatedMaterielehistorie.setId(materielehistorie.getId());

        partialUpdatedMaterielehistorie.datumeindegeldigheidgegevens(UPDATED_DATUMEINDEGELDIGHEIDGEGEVENS);

        restMaterielehistorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterielehistorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaterielehistorie))
            )
            .andExpect(status().isOk());

        // Validate the Materielehistorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaterielehistorieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMaterielehistorie, materielehistorie),
            getPersistedMaterielehistorie(materielehistorie)
        );
    }

    @Test
    @Transactional
    void fullUpdateMaterielehistorieWithPatch() throws Exception {
        // Initialize the database
        materielehistorieRepository.saveAndFlush(materielehistorie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielehistorie using partial update
        Materielehistorie partialUpdatedMaterielehistorie = new Materielehistorie();
        partialUpdatedMaterielehistorie.setId(materielehistorie.getId());

        partialUpdatedMaterielehistorie
            .datumbegingeldigheidgegevens(UPDATED_DATUMBEGINGELDIGHEIDGEGEVENS)
            .datumeindegeldigheidgegevens(UPDATED_DATUMEINDEGELDIGHEIDGEGEVENS);

        restMaterielehistorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterielehistorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaterielehistorie))
            )
            .andExpect(status().isOk());

        // Validate the Materielehistorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaterielehistorieUpdatableFieldsEquals(
            partialUpdatedMaterielehistorie,
            getPersistedMaterielehistorie(partialUpdatedMaterielehistorie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMaterielehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielehistorie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterielehistorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materielehistorie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materielehistorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materielehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterielehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielehistorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielehistorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materielehistorie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materielehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterielehistorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielehistorie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielehistorieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(materielehistorie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materielehistorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterielehistorie() throws Exception {
        // Initialize the database
        materielehistorieRepository.saveAndFlush(materielehistorie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the materielehistorie
        restMaterielehistorieMockMvc
            .perform(delete(ENTITY_API_URL_ID, materielehistorie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return materielehistorieRepository.count();
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

    protected Materielehistorie getPersistedMaterielehistorie(Materielehistorie materielehistorie) {
        return materielehistorieRepository.findById(materielehistorie.getId()).orElseThrow();
    }

    protected void assertPersistedMaterielehistorieToMatchAllProperties(Materielehistorie expectedMaterielehistorie) {
        assertMaterielehistorieAllPropertiesEquals(expectedMaterielehistorie, getPersistedMaterielehistorie(expectedMaterielehistorie));
    }

    protected void assertPersistedMaterielehistorieToMatchUpdatableProperties(Materielehistorie expectedMaterielehistorie) {
        assertMaterielehistorieAllUpdatablePropertiesEquals(
            expectedMaterielehistorie,
            getPersistedMaterielehistorie(expectedMaterielehistorie)
        );
    }
}
