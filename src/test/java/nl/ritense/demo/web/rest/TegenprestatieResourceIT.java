package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TegenprestatieAsserts.*;
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
import nl.ritense.demo.domain.Tegenprestatie;
import nl.ritense.demo.repository.TegenprestatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TegenprestatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TegenprestatieResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/tegenprestaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TegenprestatieRepository tegenprestatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTegenprestatieMockMvc;

    private Tegenprestatie tegenprestatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tegenprestatie createEntity(EntityManager em) {
        Tegenprestatie tegenprestatie = new Tegenprestatie().datumeinde(DEFAULT_DATUMEINDE).datumstart(DEFAULT_DATUMSTART);
        return tegenprestatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tegenprestatie createUpdatedEntity(EntityManager em) {
        Tegenprestatie tegenprestatie = new Tegenprestatie().datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);
        return tegenprestatie;
    }

    @BeforeEach
    public void initTest() {
        tegenprestatie = createEntity(em);
    }

    @Test
    @Transactional
    void createTegenprestatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tegenprestatie
        var returnedTegenprestatie = om.readValue(
            restTegenprestatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tegenprestatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tegenprestatie.class
        );

        // Validate the Tegenprestatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTegenprestatieUpdatableFieldsEquals(returnedTegenprestatie, getPersistedTegenprestatie(returnedTegenprestatie));
    }

    @Test
    @Transactional
    void createTegenprestatieWithExistingId() throws Exception {
        // Create the Tegenprestatie with an existing ID
        tegenprestatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTegenprestatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tegenprestatie)))
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTegenprestaties() throws Exception {
        // Initialize the database
        tegenprestatieRepository.saveAndFlush(tegenprestatie);

        // Get all the tegenprestatieList
        restTegenprestatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tegenprestatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())));
    }

    @Test
    @Transactional
    void getTegenprestatie() throws Exception {
        // Initialize the database
        tegenprestatieRepository.saveAndFlush(tegenprestatie);

        // Get the tegenprestatie
        restTegenprestatieMockMvc
            .perform(get(ENTITY_API_URL_ID, tegenprestatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tegenprestatie.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTegenprestatie() throws Exception {
        // Get the tegenprestatie
        restTegenprestatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTegenprestatie() throws Exception {
        // Initialize the database
        tegenprestatieRepository.saveAndFlush(tegenprestatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tegenprestatie
        Tegenprestatie updatedTegenprestatie = tegenprestatieRepository.findById(tegenprestatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTegenprestatie are not directly saved in db
        em.detach(updatedTegenprestatie);
        updatedTegenprestatie.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);

        restTegenprestatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTegenprestatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTegenprestatie))
            )
            .andExpect(status().isOk());

        // Validate the Tegenprestatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTegenprestatieToMatchAllProperties(updatedTegenprestatie);
    }

    @Test
    @Transactional
    void putNonExistingTegenprestatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTegenprestatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tegenprestatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tegenprestatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTegenprestatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTegenprestatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tegenprestatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTegenprestatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTegenprestatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tegenprestatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tegenprestatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTegenprestatieWithPatch() throws Exception {
        // Initialize the database
        tegenprestatieRepository.saveAndFlush(tegenprestatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tegenprestatie using partial update
        Tegenprestatie partialUpdatedTegenprestatie = new Tegenprestatie();
        partialUpdatedTegenprestatie.setId(tegenprestatie.getId());

        partialUpdatedTegenprestatie.datumstart(UPDATED_DATUMSTART);

        restTegenprestatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTegenprestatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTegenprestatie))
            )
            .andExpect(status().isOk());

        // Validate the Tegenprestatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTegenprestatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTegenprestatie, tegenprestatie),
            getPersistedTegenprestatie(tegenprestatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateTegenprestatieWithPatch() throws Exception {
        // Initialize the database
        tegenprestatieRepository.saveAndFlush(tegenprestatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tegenprestatie using partial update
        Tegenprestatie partialUpdatedTegenprestatie = new Tegenprestatie();
        partialUpdatedTegenprestatie.setId(tegenprestatie.getId());

        partialUpdatedTegenprestatie.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);

        restTegenprestatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTegenprestatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTegenprestatie))
            )
            .andExpect(status().isOk());

        // Validate the Tegenprestatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTegenprestatieUpdatableFieldsEquals(partialUpdatedTegenprestatie, getPersistedTegenprestatie(partialUpdatedTegenprestatie));
    }

    @Test
    @Transactional
    void patchNonExistingTegenprestatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTegenprestatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tegenprestatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tegenprestatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTegenprestatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTegenprestatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tegenprestatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tegenprestatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTegenprestatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tegenprestatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTegenprestatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tegenprestatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tegenprestatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTegenprestatie() throws Exception {
        // Initialize the database
        tegenprestatieRepository.saveAndFlush(tegenprestatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tegenprestatie
        restTegenprestatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, tegenprestatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tegenprestatieRepository.count();
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

    protected Tegenprestatie getPersistedTegenprestatie(Tegenprestatie tegenprestatie) {
        return tegenprestatieRepository.findById(tegenprestatie.getId()).orElseThrow();
    }

    protected void assertPersistedTegenprestatieToMatchAllProperties(Tegenprestatie expectedTegenprestatie) {
        assertTegenprestatieAllPropertiesEquals(expectedTegenprestatie, getPersistedTegenprestatie(expectedTegenprestatie));
    }

    protected void assertPersistedTegenprestatieToMatchUpdatableProperties(Tegenprestatie expectedTegenprestatie) {
        assertTegenprestatieAllUpdatablePropertiesEquals(expectedTegenprestatie, getPersistedTegenprestatie(expectedTegenprestatie));
    }
}
