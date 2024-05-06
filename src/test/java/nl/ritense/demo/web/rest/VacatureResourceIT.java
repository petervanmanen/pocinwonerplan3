package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VacatureAsserts.*;
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
import nl.ritense.demo.domain.Vacature;
import nl.ritense.demo.repository.VacatureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VacatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VacatureResourceIT {

    private static final LocalDate DEFAULT_DATUMGESLOTEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGESLOTEN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMOPENGESTELD = "AAAAAAAAAA";
    private static final String UPDATED_DATUMOPENGESTELD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEELTIJD = false;
    private static final Boolean UPDATED_DEELTIJD = true;

    private static final Boolean DEFAULT_EXTERN = false;
    private static final Boolean UPDATED_EXTERN = true;

    private static final Boolean DEFAULT_INTERN = false;
    private static final Boolean UPDATED_INTERN = true;

    private static final Boolean DEFAULT_VASTEDIENST = false;
    private static final Boolean UPDATED_VASTEDIENST = true;

    private static final String ENTITY_API_URL = "/api/vacatures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VacatureRepository vacatureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVacatureMockMvc;

    private Vacature vacature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vacature createEntity(EntityManager em) {
        Vacature vacature = new Vacature()
            .datumgesloten(DEFAULT_DATUMGESLOTEN)
            .datumopengesteld(DEFAULT_DATUMOPENGESTELD)
            .deeltijd(DEFAULT_DEELTIJD)
            .extern(DEFAULT_EXTERN)
            .intern(DEFAULT_INTERN)
            .vastedienst(DEFAULT_VASTEDIENST);
        return vacature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vacature createUpdatedEntity(EntityManager em) {
        Vacature vacature = new Vacature()
            .datumgesloten(UPDATED_DATUMGESLOTEN)
            .datumopengesteld(UPDATED_DATUMOPENGESTELD)
            .deeltijd(UPDATED_DEELTIJD)
            .extern(UPDATED_EXTERN)
            .intern(UPDATED_INTERN)
            .vastedienst(UPDATED_VASTEDIENST);
        return vacature;
    }

    @BeforeEach
    public void initTest() {
        vacature = createEntity(em);
    }

    @Test
    @Transactional
    void createVacature() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vacature
        var returnedVacature = om.readValue(
            restVacatureMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vacature)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vacature.class
        );

        // Validate the Vacature in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVacatureUpdatableFieldsEquals(returnedVacature, getPersistedVacature(returnedVacature));
    }

    @Test
    @Transactional
    void createVacatureWithExistingId() throws Exception {
        // Create the Vacature with an existing ID
        vacature.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVacatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vacature)))
            .andExpect(status().isBadRequest());

        // Validate the Vacature in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVacatures() throws Exception {
        // Initialize the database
        vacatureRepository.saveAndFlush(vacature);

        // Get all the vacatureList
        restVacatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vacature.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumgesloten").value(hasItem(DEFAULT_DATUMGESLOTEN.toString())))
            .andExpect(jsonPath("$.[*].datumopengesteld").value(hasItem(DEFAULT_DATUMOPENGESTELD)))
            .andExpect(jsonPath("$.[*].deeltijd").value(hasItem(DEFAULT_DEELTIJD.booleanValue())))
            .andExpect(jsonPath("$.[*].extern").value(hasItem(DEFAULT_EXTERN.booleanValue())))
            .andExpect(jsonPath("$.[*].intern").value(hasItem(DEFAULT_INTERN.booleanValue())))
            .andExpect(jsonPath("$.[*].vastedienst").value(hasItem(DEFAULT_VASTEDIENST.booleanValue())));
    }

    @Test
    @Transactional
    void getVacature() throws Exception {
        // Initialize the database
        vacatureRepository.saveAndFlush(vacature);

        // Get the vacature
        restVacatureMockMvc
            .perform(get(ENTITY_API_URL_ID, vacature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vacature.getId().intValue()))
            .andExpect(jsonPath("$.datumgesloten").value(DEFAULT_DATUMGESLOTEN.toString()))
            .andExpect(jsonPath("$.datumopengesteld").value(DEFAULT_DATUMOPENGESTELD))
            .andExpect(jsonPath("$.deeltijd").value(DEFAULT_DEELTIJD.booleanValue()))
            .andExpect(jsonPath("$.extern").value(DEFAULT_EXTERN.booleanValue()))
            .andExpect(jsonPath("$.intern").value(DEFAULT_INTERN.booleanValue()))
            .andExpect(jsonPath("$.vastedienst").value(DEFAULT_VASTEDIENST.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingVacature() throws Exception {
        // Get the vacature
        restVacatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVacature() throws Exception {
        // Initialize the database
        vacatureRepository.saveAndFlush(vacature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vacature
        Vacature updatedVacature = vacatureRepository.findById(vacature.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVacature are not directly saved in db
        em.detach(updatedVacature);
        updatedVacature
            .datumgesloten(UPDATED_DATUMGESLOTEN)
            .datumopengesteld(UPDATED_DATUMOPENGESTELD)
            .deeltijd(UPDATED_DEELTIJD)
            .extern(UPDATED_EXTERN)
            .intern(UPDATED_INTERN)
            .vastedienst(UPDATED_VASTEDIENST);

        restVacatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVacature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVacature))
            )
            .andExpect(status().isOk());

        // Validate the Vacature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVacatureToMatchAllProperties(updatedVacature);
    }

    @Test
    @Transactional
    void putNonExistingVacature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vacature.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVacatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vacature.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vacature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vacature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVacature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vacature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVacatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vacature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vacature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVacature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vacature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVacatureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vacature)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vacature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVacatureWithPatch() throws Exception {
        // Initialize the database
        vacatureRepository.saveAndFlush(vacature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vacature using partial update
        Vacature partialUpdatedVacature = new Vacature();
        partialUpdatedVacature.setId(vacature.getId());

        partialUpdatedVacature.intern(UPDATED_INTERN);

        restVacatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVacature.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVacature))
            )
            .andExpect(status().isOk());

        // Validate the Vacature in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVacatureUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVacature, vacature), getPersistedVacature(vacature));
    }

    @Test
    @Transactional
    void fullUpdateVacatureWithPatch() throws Exception {
        // Initialize the database
        vacatureRepository.saveAndFlush(vacature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vacature using partial update
        Vacature partialUpdatedVacature = new Vacature();
        partialUpdatedVacature.setId(vacature.getId());

        partialUpdatedVacature
            .datumgesloten(UPDATED_DATUMGESLOTEN)
            .datumopengesteld(UPDATED_DATUMOPENGESTELD)
            .deeltijd(UPDATED_DEELTIJD)
            .extern(UPDATED_EXTERN)
            .intern(UPDATED_INTERN)
            .vastedienst(UPDATED_VASTEDIENST);

        restVacatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVacature.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVacature))
            )
            .andExpect(status().isOk());

        // Validate the Vacature in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVacatureUpdatableFieldsEquals(partialUpdatedVacature, getPersistedVacature(partialUpdatedVacature));
    }

    @Test
    @Transactional
    void patchNonExistingVacature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vacature.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVacatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vacature.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vacature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vacature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVacature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vacature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVacatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vacature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vacature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVacature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vacature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVacatureMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vacature)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vacature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVacature() throws Exception {
        // Initialize the database
        vacatureRepository.saveAndFlush(vacature);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vacature
        restVacatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, vacature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vacatureRepository.count();
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

    protected Vacature getPersistedVacature(Vacature vacature) {
        return vacatureRepository.findById(vacature.getId()).orElseThrow();
    }

    protected void assertPersistedVacatureToMatchAllProperties(Vacature expectedVacature) {
        assertVacatureAllPropertiesEquals(expectedVacature, getPersistedVacature(expectedVacature));
    }

    protected void assertPersistedVacatureToMatchUpdatableProperties(Vacature expectedVacature) {
        assertVacatureAllUpdatablePropertiesEquals(expectedVacature, getPersistedVacature(expectedVacature));
    }
}
