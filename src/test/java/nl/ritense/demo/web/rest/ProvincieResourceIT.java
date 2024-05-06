package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ProvincieAsserts.*;
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
import nl.ritense.demo.domain.Provincie;
import nl.ritense.demo.repository.ProvincieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProvincieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProvincieResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDEPROVINCIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEPROVINCIE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANGPROVINCIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANGPROVINCIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HOOFDSTAD = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDSTAD = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTELAND = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTELAND = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIECODE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIECODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIENAAM = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIENAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/provincies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProvincieRepository provincieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProvincieMockMvc;

    private Provincie provincie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provincie createEntity(EntityManager em) {
        Provincie provincie = new Provincie()
            .datumeindeprovincie(DEFAULT_DATUMEINDEPROVINCIE)
            .datumingangprovincie(DEFAULT_DATUMINGANGPROVINCIE)
            .hoofdstad(DEFAULT_HOOFDSTAD)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .oppervlakteland(DEFAULT_OPPERVLAKTELAND)
            .provinciecode(DEFAULT_PROVINCIECODE)
            .provincienaam(DEFAULT_PROVINCIENAAM);
        return provincie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provincie createUpdatedEntity(EntityManager em) {
        Provincie provincie = new Provincie()
            .datumeindeprovincie(UPDATED_DATUMEINDEPROVINCIE)
            .datumingangprovincie(UPDATED_DATUMINGANGPROVINCIE)
            .hoofdstad(UPDATED_HOOFDSTAD)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .oppervlakteland(UPDATED_OPPERVLAKTELAND)
            .provinciecode(UPDATED_PROVINCIECODE)
            .provincienaam(UPDATED_PROVINCIENAAM);
        return provincie;
    }

    @BeforeEach
    public void initTest() {
        provincie = createEntity(em);
    }

    @Test
    @Transactional
    void createProvincie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Provincie
        var returnedProvincie = om.readValue(
            restProvincieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provincie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Provincie.class
        );

        // Validate the Provincie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProvincieUpdatableFieldsEquals(returnedProvincie, getPersistedProvincie(returnedProvincie));
    }

    @Test
    @Transactional
    void createProvincieWithExistingId() throws Exception {
        // Create the Provincie with an existing ID
        provincie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvincieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provincie)))
            .andExpect(status().isBadRequest());

        // Validate the Provincie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProvincies() throws Exception {
        // Initialize the database
        provincieRepository.saveAndFlush(provincie);

        // Get all the provincieList
        restProvincieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provincie.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindeprovincie").value(hasItem(DEFAULT_DATUMEINDEPROVINCIE.toString())))
            .andExpect(jsonPath("$.[*].datumingangprovincie").value(hasItem(DEFAULT_DATUMINGANGPROVINCIE.toString())))
            .andExpect(jsonPath("$.[*].hoofdstad").value(hasItem(DEFAULT_HOOFDSTAD)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].oppervlakteland").value(hasItem(DEFAULT_OPPERVLAKTELAND)))
            .andExpect(jsonPath("$.[*].provinciecode").value(hasItem(DEFAULT_PROVINCIECODE)))
            .andExpect(jsonPath("$.[*].provincienaam").value(hasItem(DEFAULT_PROVINCIENAAM)));
    }

    @Test
    @Transactional
    void getProvincie() throws Exception {
        // Initialize the database
        provincieRepository.saveAndFlush(provincie);

        // Get the provincie
        restProvincieMockMvc
            .perform(get(ENTITY_API_URL_ID, provincie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(provincie.getId().intValue()))
            .andExpect(jsonPath("$.datumeindeprovincie").value(DEFAULT_DATUMEINDEPROVINCIE.toString()))
            .andExpect(jsonPath("$.datumingangprovincie").value(DEFAULT_DATUMINGANGPROVINCIE.toString()))
            .andExpect(jsonPath("$.hoofdstad").value(DEFAULT_HOOFDSTAD))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.oppervlakteland").value(DEFAULT_OPPERVLAKTELAND))
            .andExpect(jsonPath("$.provinciecode").value(DEFAULT_PROVINCIECODE))
            .andExpect(jsonPath("$.provincienaam").value(DEFAULT_PROVINCIENAAM));
    }

    @Test
    @Transactional
    void getNonExistingProvincie() throws Exception {
        // Get the provincie
        restProvincieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProvincie() throws Exception {
        // Initialize the database
        provincieRepository.saveAndFlush(provincie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the provincie
        Provincie updatedProvincie = provincieRepository.findById(provincie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProvincie are not directly saved in db
        em.detach(updatedProvincie);
        updatedProvincie
            .datumeindeprovincie(UPDATED_DATUMEINDEPROVINCIE)
            .datumingangprovincie(UPDATED_DATUMINGANGPROVINCIE)
            .hoofdstad(UPDATED_HOOFDSTAD)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .oppervlakteland(UPDATED_OPPERVLAKTELAND)
            .provinciecode(UPDATED_PROVINCIECODE)
            .provincienaam(UPDATED_PROVINCIENAAM);

        restProvincieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProvincie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProvincie))
            )
            .andExpect(status().isOk());

        // Validate the Provincie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProvincieToMatchAllProperties(updatedProvincie);
    }

    @Test
    @Transactional
    void putNonExistingProvincie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provincie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvincieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provincie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provincie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provincie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProvincie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provincie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvincieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(provincie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provincie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProvincie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provincie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvincieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provincie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Provincie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProvincieWithPatch() throws Exception {
        // Initialize the database
        provincieRepository.saveAndFlush(provincie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the provincie using partial update
        Provincie partialUpdatedProvincie = new Provincie();
        partialUpdatedProvincie.setId(provincie.getId());

        partialUpdatedProvincie
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .oppervlakteland(UPDATED_OPPERVLAKTELAND)
            .provinciecode(UPDATED_PROVINCIECODE)
            .provincienaam(UPDATED_PROVINCIENAAM);

        restProvincieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvincie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProvincie))
            )
            .andExpect(status().isOk());

        // Validate the Provincie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProvincieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProvincie, provincie),
            getPersistedProvincie(provincie)
        );
    }

    @Test
    @Transactional
    void fullUpdateProvincieWithPatch() throws Exception {
        // Initialize the database
        provincieRepository.saveAndFlush(provincie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the provincie using partial update
        Provincie partialUpdatedProvincie = new Provincie();
        partialUpdatedProvincie.setId(provincie.getId());

        partialUpdatedProvincie
            .datumeindeprovincie(UPDATED_DATUMEINDEPROVINCIE)
            .datumingangprovincie(UPDATED_DATUMINGANGPROVINCIE)
            .hoofdstad(UPDATED_HOOFDSTAD)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .oppervlakteland(UPDATED_OPPERVLAKTELAND)
            .provinciecode(UPDATED_PROVINCIECODE)
            .provincienaam(UPDATED_PROVINCIENAAM);

        restProvincieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvincie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProvincie))
            )
            .andExpect(status().isOk());

        // Validate the Provincie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProvincieUpdatableFieldsEquals(partialUpdatedProvincie, getPersistedProvincie(partialUpdatedProvincie));
    }

    @Test
    @Transactional
    void patchNonExistingProvincie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provincie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvincieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, provincie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(provincie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provincie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProvincie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provincie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvincieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(provincie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provincie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProvincie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provincie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvincieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(provincie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Provincie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProvincie() throws Exception {
        // Initialize the database
        provincieRepository.saveAndFlush(provincie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the provincie
        restProvincieMockMvc
            .perform(delete(ENTITY_API_URL_ID, provincie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return provincieRepository.count();
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

    protected Provincie getPersistedProvincie(Provincie provincie) {
        return provincieRepository.findById(provincie.getId()).orElseThrow();
    }

    protected void assertPersistedProvincieToMatchAllProperties(Provincie expectedProvincie) {
        assertProvincieAllPropertiesEquals(expectedProvincie, getPersistedProvincie(expectedProvincie));
    }

    protected void assertPersistedProvincieToMatchUpdatableProperties(Provincie expectedProvincie) {
        assertProvincieAllUpdatablePropertiesEquals(expectedProvincie, getPersistedProvincie(expectedProvincie));
    }
}
