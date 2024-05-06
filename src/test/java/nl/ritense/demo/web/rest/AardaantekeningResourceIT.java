package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AardaantekeningAsserts.*;
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
import nl.ritense.demo.domain.Aardaantekening;
import nl.ritense.demo.repository.AardaantekeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AardaantekeningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AardaantekeningResourceIT {

    private static final String DEFAULT_CODEAARDAANTEKENING = "AAAAAAAAAA";
    private static final String UPDATED_CODEAARDAANTEKENING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDAARDAANTEKENING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDAARDAANTEKENING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDAARDAANTEKENING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDAARDAANTEKENING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAMAARDAANTEKENING = "AAAAAAAAAA";
    private static final String UPDATED_NAAMAARDAANTEKENING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aardaantekenings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AardaantekeningRepository aardaantekeningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAardaantekeningMockMvc;

    private Aardaantekening aardaantekening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aardaantekening createEntity(EntityManager em) {
        Aardaantekening aardaantekening = new Aardaantekening()
            .codeaardaantekening(DEFAULT_CODEAARDAANTEKENING)
            .datumbegingeldigheidaardaantekening(DEFAULT_DATUMBEGINGELDIGHEIDAARDAANTEKENING)
            .datumeindegeldigheidaardaantekening(DEFAULT_DATUMEINDEGELDIGHEIDAARDAANTEKENING)
            .naamaardaantekening(DEFAULT_NAAMAARDAANTEKENING);
        return aardaantekening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aardaantekening createUpdatedEntity(EntityManager em) {
        Aardaantekening aardaantekening = new Aardaantekening()
            .codeaardaantekening(UPDATED_CODEAARDAANTEKENING)
            .datumbegingeldigheidaardaantekening(UPDATED_DATUMBEGINGELDIGHEIDAARDAANTEKENING)
            .datumeindegeldigheidaardaantekening(UPDATED_DATUMEINDEGELDIGHEIDAARDAANTEKENING)
            .naamaardaantekening(UPDATED_NAAMAARDAANTEKENING);
        return aardaantekening;
    }

    @BeforeEach
    public void initTest() {
        aardaantekening = createEntity(em);
    }

    @Test
    @Transactional
    void createAardaantekening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aardaantekening
        var returnedAardaantekening = om.readValue(
            restAardaantekeningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardaantekening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aardaantekening.class
        );

        // Validate the Aardaantekening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAardaantekeningUpdatableFieldsEquals(returnedAardaantekening, getPersistedAardaantekening(returnedAardaantekening));
    }

    @Test
    @Transactional
    void createAardaantekeningWithExistingId() throws Exception {
        // Create the Aardaantekening with an existing ID
        aardaantekening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAardaantekeningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardaantekening)))
            .andExpect(status().isBadRequest());

        // Validate the Aardaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAardaantekenings() throws Exception {
        // Initialize the database
        aardaantekeningRepository.saveAndFlush(aardaantekening);

        // Get all the aardaantekeningList
        restAardaantekeningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aardaantekening.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeaardaantekening").value(hasItem(DEFAULT_CODEAARDAANTEKENING)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidaardaantekening").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDAARDAANTEKENING.toString()))
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidaardaantekening").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDAARDAANTEKENING.toString()))
            )
            .andExpect(jsonPath("$.[*].naamaardaantekening").value(hasItem(DEFAULT_NAAMAARDAANTEKENING)));
    }

    @Test
    @Transactional
    void getAardaantekening() throws Exception {
        // Initialize the database
        aardaantekeningRepository.saveAndFlush(aardaantekening);

        // Get the aardaantekening
        restAardaantekeningMockMvc
            .perform(get(ENTITY_API_URL_ID, aardaantekening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aardaantekening.getId().intValue()))
            .andExpect(jsonPath("$.codeaardaantekening").value(DEFAULT_CODEAARDAANTEKENING))
            .andExpect(jsonPath("$.datumbegingeldigheidaardaantekening").value(DEFAULT_DATUMBEGINGELDIGHEIDAARDAANTEKENING.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidaardaantekening").value(DEFAULT_DATUMEINDEGELDIGHEIDAARDAANTEKENING.toString()))
            .andExpect(jsonPath("$.naamaardaantekening").value(DEFAULT_NAAMAARDAANTEKENING));
    }

    @Test
    @Transactional
    void getNonExistingAardaantekening() throws Exception {
        // Get the aardaantekening
        restAardaantekeningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAardaantekening() throws Exception {
        // Initialize the database
        aardaantekeningRepository.saveAndFlush(aardaantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardaantekening
        Aardaantekening updatedAardaantekening = aardaantekeningRepository.findById(aardaantekening.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAardaantekening are not directly saved in db
        em.detach(updatedAardaantekening);
        updatedAardaantekening
            .codeaardaantekening(UPDATED_CODEAARDAANTEKENING)
            .datumbegingeldigheidaardaantekening(UPDATED_DATUMBEGINGELDIGHEIDAARDAANTEKENING)
            .datumeindegeldigheidaardaantekening(UPDATED_DATUMEINDEGELDIGHEIDAARDAANTEKENING)
            .naamaardaantekening(UPDATED_NAAMAARDAANTEKENING);

        restAardaantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAardaantekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAardaantekening))
            )
            .andExpect(status().isOk());

        // Validate the Aardaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAardaantekeningToMatchAllProperties(updatedAardaantekening);
    }

    @Test
    @Transactional
    void putNonExistingAardaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardaantekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAardaantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aardaantekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aardaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAardaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardaantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardaantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aardaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAardaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardaantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardaantekeningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardaantekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aardaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAardaantekeningWithPatch() throws Exception {
        // Initialize the database
        aardaantekeningRepository.saveAndFlush(aardaantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardaantekening using partial update
        Aardaantekening partialUpdatedAardaantekening = new Aardaantekening();
        partialUpdatedAardaantekening.setId(aardaantekening.getId());

        partialUpdatedAardaantekening
            .datumeindegeldigheidaardaantekening(UPDATED_DATUMEINDEGELDIGHEIDAARDAANTEKENING)
            .naamaardaantekening(UPDATED_NAAMAARDAANTEKENING);

        restAardaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAardaantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAardaantekening))
            )
            .andExpect(status().isOk());

        // Validate the Aardaantekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAardaantekeningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAardaantekening, aardaantekening),
            getPersistedAardaantekening(aardaantekening)
        );
    }

    @Test
    @Transactional
    void fullUpdateAardaantekeningWithPatch() throws Exception {
        // Initialize the database
        aardaantekeningRepository.saveAndFlush(aardaantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardaantekening using partial update
        Aardaantekening partialUpdatedAardaantekening = new Aardaantekening();
        partialUpdatedAardaantekening.setId(aardaantekening.getId());

        partialUpdatedAardaantekening
            .codeaardaantekening(UPDATED_CODEAARDAANTEKENING)
            .datumbegingeldigheidaardaantekening(UPDATED_DATUMBEGINGELDIGHEIDAARDAANTEKENING)
            .datumeindegeldigheidaardaantekening(UPDATED_DATUMEINDEGELDIGHEIDAARDAANTEKENING)
            .naamaardaantekening(UPDATED_NAAMAARDAANTEKENING);

        restAardaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAardaantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAardaantekening))
            )
            .andExpect(status().isOk());

        // Validate the Aardaantekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAardaantekeningUpdatableFieldsEquals(
            partialUpdatedAardaantekening,
            getPersistedAardaantekening(partialUpdatedAardaantekening)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAardaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardaantekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAardaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aardaantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aardaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAardaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardaantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aardaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAardaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardaantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardaantekeningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aardaantekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aardaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAardaantekening() throws Exception {
        // Initialize the database
        aardaantekeningRepository.saveAndFlush(aardaantekening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aardaantekening
        restAardaantekeningMockMvc
            .perform(delete(ENTITY_API_URL_ID, aardaantekening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aardaantekeningRepository.count();
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

    protected Aardaantekening getPersistedAardaantekening(Aardaantekening aardaantekening) {
        return aardaantekeningRepository.findById(aardaantekening.getId()).orElseThrow();
    }

    protected void assertPersistedAardaantekeningToMatchAllProperties(Aardaantekening expectedAardaantekening) {
        assertAardaantekeningAllPropertiesEquals(expectedAardaantekening, getPersistedAardaantekening(expectedAardaantekening));
    }

    protected void assertPersistedAardaantekeningToMatchUpdatableProperties(Aardaantekening expectedAardaantekening) {
        assertAardaantekeningAllUpdatablePropertiesEquals(expectedAardaantekening, getPersistedAardaantekening(expectedAardaantekening));
    }
}
