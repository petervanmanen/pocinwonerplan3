package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoortgrootteAsserts.*;
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
import nl.ritense.demo.domain.Soortgrootte;
import nl.ritense.demo.repository.SoortgrootteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoortgrootteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoortgrootteResourceIT {

    private static final String DEFAULT_CODESOORTGROOTTE = "AAAAAAAAAA";
    private static final String UPDATED_CODESOORTGROOTTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDSOORTGROOTTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDSOORTGROOTTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDSOORTGROOTTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDSOORTGROOTTE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAMSOORTGROOTTE = "AAAAAAAAAA";
    private static final String UPDATED_NAAMSOORTGROOTTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/soortgroottes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoortgrootteRepository soortgrootteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoortgrootteMockMvc;

    private Soortgrootte soortgrootte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortgrootte createEntity(EntityManager em) {
        Soortgrootte soortgrootte = new Soortgrootte()
            .codesoortgrootte(DEFAULT_CODESOORTGROOTTE)
            .datumbegingeldigheidsoortgrootte(DEFAULT_DATUMBEGINGELDIGHEIDSOORTGROOTTE)
            .datumeindegeldigheidsoortgrootte(DEFAULT_DATUMEINDEGELDIGHEIDSOORTGROOTTE)
            .naamsoortgrootte(DEFAULT_NAAMSOORTGROOTTE);
        return soortgrootte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortgrootte createUpdatedEntity(EntityManager em) {
        Soortgrootte soortgrootte = new Soortgrootte()
            .codesoortgrootte(UPDATED_CODESOORTGROOTTE)
            .datumbegingeldigheidsoortgrootte(UPDATED_DATUMBEGINGELDIGHEIDSOORTGROOTTE)
            .datumeindegeldigheidsoortgrootte(UPDATED_DATUMEINDEGELDIGHEIDSOORTGROOTTE)
            .naamsoortgrootte(UPDATED_NAAMSOORTGROOTTE);
        return soortgrootte;
    }

    @BeforeEach
    public void initTest() {
        soortgrootte = createEntity(em);
    }

    @Test
    @Transactional
    void createSoortgrootte() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Soortgrootte
        var returnedSoortgrootte = om.readValue(
            restSoortgrootteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortgrootte)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Soortgrootte.class
        );

        // Validate the Soortgrootte in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoortgrootteUpdatableFieldsEquals(returnedSoortgrootte, getPersistedSoortgrootte(returnedSoortgrootte));
    }

    @Test
    @Transactional
    void createSoortgrootteWithExistingId() throws Exception {
        // Create the Soortgrootte with an existing ID
        soortgrootte.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoortgrootteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortgrootte)))
            .andExpect(status().isBadRequest());

        // Validate the Soortgrootte in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoortgroottes() throws Exception {
        // Initialize the database
        soortgrootteRepository.saveAndFlush(soortgrootte);

        // Get all the soortgrootteList
        restSoortgrootteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soortgrootte.getId().intValue())))
            .andExpect(jsonPath("$.[*].codesoortgrootte").value(hasItem(DEFAULT_CODESOORTGROOTTE)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidsoortgrootte").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDSOORTGROOTTE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidsoortgrootte").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDSOORTGROOTTE.toString()))
            )
            .andExpect(jsonPath("$.[*].naamsoortgrootte").value(hasItem(DEFAULT_NAAMSOORTGROOTTE)));
    }

    @Test
    @Transactional
    void getSoortgrootte() throws Exception {
        // Initialize the database
        soortgrootteRepository.saveAndFlush(soortgrootte);

        // Get the soortgrootte
        restSoortgrootteMockMvc
            .perform(get(ENTITY_API_URL_ID, soortgrootte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soortgrootte.getId().intValue()))
            .andExpect(jsonPath("$.codesoortgrootte").value(DEFAULT_CODESOORTGROOTTE))
            .andExpect(jsonPath("$.datumbegingeldigheidsoortgrootte").value(DEFAULT_DATUMBEGINGELDIGHEIDSOORTGROOTTE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidsoortgrootte").value(DEFAULT_DATUMEINDEGELDIGHEIDSOORTGROOTTE.toString()))
            .andExpect(jsonPath("$.naamsoortgrootte").value(DEFAULT_NAAMSOORTGROOTTE));
    }

    @Test
    @Transactional
    void getNonExistingSoortgrootte() throws Exception {
        // Get the soortgrootte
        restSoortgrootteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoortgrootte() throws Exception {
        // Initialize the database
        soortgrootteRepository.saveAndFlush(soortgrootte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortgrootte
        Soortgrootte updatedSoortgrootte = soortgrootteRepository.findById(soortgrootte.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSoortgrootte are not directly saved in db
        em.detach(updatedSoortgrootte);
        updatedSoortgrootte
            .codesoortgrootte(UPDATED_CODESOORTGROOTTE)
            .datumbegingeldigheidsoortgrootte(UPDATED_DATUMBEGINGELDIGHEIDSOORTGROOTTE)
            .datumeindegeldigheidsoortgrootte(UPDATED_DATUMEINDEGELDIGHEIDSOORTGROOTTE)
            .naamsoortgrootte(UPDATED_NAAMSOORTGROOTTE);

        restSoortgrootteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoortgrootte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSoortgrootte))
            )
            .andExpect(status().isOk());

        // Validate the Soortgrootte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSoortgrootteToMatchAllProperties(updatedSoortgrootte);
    }

    @Test
    @Transactional
    void putNonExistingSoortgrootte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortgrootte.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortgrootteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soortgrootte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortgrootte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortgrootte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoortgrootte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortgrootte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortgrootteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortgrootte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortgrootte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoortgrootte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortgrootte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortgrootteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortgrootte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortgrootte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoortgrootteWithPatch() throws Exception {
        // Initialize the database
        soortgrootteRepository.saveAndFlush(soortgrootte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortgrootte using partial update
        Soortgrootte partialUpdatedSoortgrootte = new Soortgrootte();
        partialUpdatedSoortgrootte.setId(soortgrootte.getId());

        partialUpdatedSoortgrootte
            .codesoortgrootte(UPDATED_CODESOORTGROOTTE)
            .datumeindegeldigheidsoortgrootte(UPDATED_DATUMEINDEGELDIGHEIDSOORTGROOTTE)
            .naamsoortgrootte(UPDATED_NAAMSOORTGROOTTE);

        restSoortgrootteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortgrootte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortgrootte))
            )
            .andExpect(status().isOk());

        // Validate the Soortgrootte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortgrootteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSoortgrootte, soortgrootte),
            getPersistedSoortgrootte(soortgrootte)
        );
    }

    @Test
    @Transactional
    void fullUpdateSoortgrootteWithPatch() throws Exception {
        // Initialize the database
        soortgrootteRepository.saveAndFlush(soortgrootte);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortgrootte using partial update
        Soortgrootte partialUpdatedSoortgrootte = new Soortgrootte();
        partialUpdatedSoortgrootte.setId(soortgrootte.getId());

        partialUpdatedSoortgrootte
            .codesoortgrootte(UPDATED_CODESOORTGROOTTE)
            .datumbegingeldigheidsoortgrootte(UPDATED_DATUMBEGINGELDIGHEIDSOORTGROOTTE)
            .datumeindegeldigheidsoortgrootte(UPDATED_DATUMEINDEGELDIGHEIDSOORTGROOTTE)
            .naamsoortgrootte(UPDATED_NAAMSOORTGROOTTE);

        restSoortgrootteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortgrootte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortgrootte))
            )
            .andExpect(status().isOk());

        // Validate the Soortgrootte in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortgrootteUpdatableFieldsEquals(partialUpdatedSoortgrootte, getPersistedSoortgrootte(partialUpdatedSoortgrootte));
    }

    @Test
    @Transactional
    void patchNonExistingSoortgrootte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortgrootte.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortgrootteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soortgrootte.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortgrootte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortgrootte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoortgrootte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortgrootte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortgrootteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortgrootte))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortgrootte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoortgrootte() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortgrootte.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortgrootteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(soortgrootte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortgrootte in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoortgrootte() throws Exception {
        // Initialize the database
        soortgrootteRepository.saveAndFlush(soortgrootte);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the soortgrootte
        restSoortgrootteMockMvc
            .perform(delete(ENTITY_API_URL_ID, soortgrootte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return soortgrootteRepository.count();
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

    protected Soortgrootte getPersistedSoortgrootte(Soortgrootte soortgrootte) {
        return soortgrootteRepository.findById(soortgrootte.getId()).orElseThrow();
    }

    protected void assertPersistedSoortgrootteToMatchAllProperties(Soortgrootte expectedSoortgrootte) {
        assertSoortgrootteAllPropertiesEquals(expectedSoortgrootte, getPersistedSoortgrootte(expectedSoortgrootte));
    }

    protected void assertPersistedSoortgrootteToMatchUpdatableProperties(Soortgrootte expectedSoortgrootte) {
        assertSoortgrootteAllUpdatablePropertiesEquals(expectedSoortgrootte, getPersistedSoortgrootte(expectedSoortgrootte));
    }
}
