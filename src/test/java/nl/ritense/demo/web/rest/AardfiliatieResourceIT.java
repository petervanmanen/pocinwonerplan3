package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AardfiliatieAsserts.*;
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
import nl.ritense.demo.domain.Aardfiliatie;
import nl.ritense.demo.repository.AardfiliatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AardfiliatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AardfiliatieResourceIT {

    private static final String DEFAULT_CODEAARDFILIATIE = "AAAAAAAAAA";
    private static final String UPDATED_CODEAARDFILIATIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDAARDFILIATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDAARDFILIATIE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDAARDFILIATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDAARDFILIATIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAMAARDFILIATIE = "AAAAAAAAAA";
    private static final String UPDATED_NAAMAARDFILIATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aardfiliaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AardfiliatieRepository aardfiliatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAardfiliatieMockMvc;

    private Aardfiliatie aardfiliatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aardfiliatie createEntity(EntityManager em) {
        Aardfiliatie aardfiliatie = new Aardfiliatie()
            .codeaardfiliatie(DEFAULT_CODEAARDFILIATIE)
            .datumbegingeldigheidaardfiliatie(DEFAULT_DATUMBEGINGELDIGHEIDAARDFILIATIE)
            .datumeindegeldigheidaardfiliatie(DEFAULT_DATUMEINDEGELDIGHEIDAARDFILIATIE)
            .naamaardfiliatie(DEFAULT_NAAMAARDFILIATIE);
        return aardfiliatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aardfiliatie createUpdatedEntity(EntityManager em) {
        Aardfiliatie aardfiliatie = new Aardfiliatie()
            .codeaardfiliatie(UPDATED_CODEAARDFILIATIE)
            .datumbegingeldigheidaardfiliatie(UPDATED_DATUMBEGINGELDIGHEIDAARDFILIATIE)
            .datumeindegeldigheidaardfiliatie(UPDATED_DATUMEINDEGELDIGHEIDAARDFILIATIE)
            .naamaardfiliatie(UPDATED_NAAMAARDFILIATIE);
        return aardfiliatie;
    }

    @BeforeEach
    public void initTest() {
        aardfiliatie = createEntity(em);
    }

    @Test
    @Transactional
    void createAardfiliatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aardfiliatie
        var returnedAardfiliatie = om.readValue(
            restAardfiliatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardfiliatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aardfiliatie.class
        );

        // Validate the Aardfiliatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAardfiliatieUpdatableFieldsEquals(returnedAardfiliatie, getPersistedAardfiliatie(returnedAardfiliatie));
    }

    @Test
    @Transactional
    void createAardfiliatieWithExistingId() throws Exception {
        // Create the Aardfiliatie with an existing ID
        aardfiliatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAardfiliatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardfiliatie)))
            .andExpect(status().isBadRequest());

        // Validate the Aardfiliatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAardfiliaties() throws Exception {
        // Initialize the database
        aardfiliatieRepository.saveAndFlush(aardfiliatie);

        // Get all the aardfiliatieList
        restAardfiliatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aardfiliatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeaardfiliatie").value(hasItem(DEFAULT_CODEAARDFILIATIE)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidaardfiliatie").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDAARDFILIATIE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidaardfiliatie").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDAARDFILIATIE.toString()))
            )
            .andExpect(jsonPath("$.[*].naamaardfiliatie").value(hasItem(DEFAULT_NAAMAARDFILIATIE)));
    }

    @Test
    @Transactional
    void getAardfiliatie() throws Exception {
        // Initialize the database
        aardfiliatieRepository.saveAndFlush(aardfiliatie);

        // Get the aardfiliatie
        restAardfiliatieMockMvc
            .perform(get(ENTITY_API_URL_ID, aardfiliatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aardfiliatie.getId().intValue()))
            .andExpect(jsonPath("$.codeaardfiliatie").value(DEFAULT_CODEAARDFILIATIE))
            .andExpect(jsonPath("$.datumbegingeldigheidaardfiliatie").value(DEFAULT_DATUMBEGINGELDIGHEIDAARDFILIATIE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidaardfiliatie").value(DEFAULT_DATUMEINDEGELDIGHEIDAARDFILIATIE.toString()))
            .andExpect(jsonPath("$.naamaardfiliatie").value(DEFAULT_NAAMAARDFILIATIE));
    }

    @Test
    @Transactional
    void getNonExistingAardfiliatie() throws Exception {
        // Get the aardfiliatie
        restAardfiliatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAardfiliatie() throws Exception {
        // Initialize the database
        aardfiliatieRepository.saveAndFlush(aardfiliatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardfiliatie
        Aardfiliatie updatedAardfiliatie = aardfiliatieRepository.findById(aardfiliatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAardfiliatie are not directly saved in db
        em.detach(updatedAardfiliatie);
        updatedAardfiliatie
            .codeaardfiliatie(UPDATED_CODEAARDFILIATIE)
            .datumbegingeldigheidaardfiliatie(UPDATED_DATUMBEGINGELDIGHEIDAARDFILIATIE)
            .datumeindegeldigheidaardfiliatie(UPDATED_DATUMEINDEGELDIGHEIDAARDFILIATIE)
            .naamaardfiliatie(UPDATED_NAAMAARDFILIATIE);

        restAardfiliatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAardfiliatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAardfiliatie))
            )
            .andExpect(status().isOk());

        // Validate the Aardfiliatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAardfiliatieToMatchAllProperties(updatedAardfiliatie);
    }

    @Test
    @Transactional
    void putNonExistingAardfiliatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardfiliatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAardfiliatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aardfiliatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aardfiliatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardfiliatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAardfiliatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardfiliatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardfiliatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aardfiliatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardfiliatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAardfiliatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardfiliatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardfiliatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardfiliatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aardfiliatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAardfiliatieWithPatch() throws Exception {
        // Initialize the database
        aardfiliatieRepository.saveAndFlush(aardfiliatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardfiliatie using partial update
        Aardfiliatie partialUpdatedAardfiliatie = new Aardfiliatie();
        partialUpdatedAardfiliatie.setId(aardfiliatie.getId());

        partialUpdatedAardfiliatie
            .datumbegingeldigheidaardfiliatie(UPDATED_DATUMBEGINGELDIGHEIDAARDFILIATIE)
            .datumeindegeldigheidaardfiliatie(UPDATED_DATUMEINDEGELDIGHEIDAARDFILIATIE)
            .naamaardfiliatie(UPDATED_NAAMAARDFILIATIE);

        restAardfiliatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAardfiliatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAardfiliatie))
            )
            .andExpect(status().isOk());

        // Validate the Aardfiliatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAardfiliatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAardfiliatie, aardfiliatie),
            getPersistedAardfiliatie(aardfiliatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateAardfiliatieWithPatch() throws Exception {
        // Initialize the database
        aardfiliatieRepository.saveAndFlush(aardfiliatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardfiliatie using partial update
        Aardfiliatie partialUpdatedAardfiliatie = new Aardfiliatie();
        partialUpdatedAardfiliatie.setId(aardfiliatie.getId());

        partialUpdatedAardfiliatie
            .codeaardfiliatie(UPDATED_CODEAARDFILIATIE)
            .datumbegingeldigheidaardfiliatie(UPDATED_DATUMBEGINGELDIGHEIDAARDFILIATIE)
            .datumeindegeldigheidaardfiliatie(UPDATED_DATUMEINDEGELDIGHEIDAARDFILIATIE)
            .naamaardfiliatie(UPDATED_NAAMAARDFILIATIE);

        restAardfiliatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAardfiliatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAardfiliatie))
            )
            .andExpect(status().isOk());

        // Validate the Aardfiliatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAardfiliatieUpdatableFieldsEquals(partialUpdatedAardfiliatie, getPersistedAardfiliatie(partialUpdatedAardfiliatie));
    }

    @Test
    @Transactional
    void patchNonExistingAardfiliatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardfiliatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAardfiliatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aardfiliatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aardfiliatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardfiliatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAardfiliatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardfiliatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardfiliatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aardfiliatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardfiliatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAardfiliatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardfiliatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardfiliatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aardfiliatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aardfiliatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAardfiliatie() throws Exception {
        // Initialize the database
        aardfiliatieRepository.saveAndFlush(aardfiliatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aardfiliatie
        restAardfiliatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, aardfiliatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aardfiliatieRepository.count();
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

    protected Aardfiliatie getPersistedAardfiliatie(Aardfiliatie aardfiliatie) {
        return aardfiliatieRepository.findById(aardfiliatie.getId()).orElseThrow();
    }

    protected void assertPersistedAardfiliatieToMatchAllProperties(Aardfiliatie expectedAardfiliatie) {
        assertAardfiliatieAllPropertiesEquals(expectedAardfiliatie, getPersistedAardfiliatie(expectedAardfiliatie));
    }

    protected void assertPersistedAardfiliatieToMatchUpdatableProperties(Aardfiliatie expectedAardfiliatie) {
        assertAardfiliatieAllUpdatablePropertiesEquals(expectedAardfiliatie, getPersistedAardfiliatie(expectedAardfiliatie));
    }
}
