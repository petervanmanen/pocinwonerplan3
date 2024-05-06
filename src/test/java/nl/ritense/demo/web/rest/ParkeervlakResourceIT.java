package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ParkeervlakAsserts.*;
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
import nl.ritense.demo.domain.Parkeervlak;
import nl.ritense.demo.repository.ParkeervlakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParkeervlakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkeervlakResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String DEFAULT_COORDINATEN = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATEN = "BBBBBBBBBB";

    private static final String DEFAULT_DOELGROEP = "AAAAAAAAAA";
    private static final String UPDATED_DOELGROEP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FISCAAL = false;
    private static final Boolean UPDATED_FISCAAL = true;

    private static final String DEFAULT_PLAATS = "AAAAAAAAAA";
    private static final String UPDATED_PLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_VLAKID = "AAAAAAAAAA";
    private static final String UPDATED_VLAKID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parkeervlaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParkeervlakRepository parkeervlakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkeervlakMockMvc;

    private Parkeervlak parkeervlak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeervlak createEntity(EntityManager em) {
        Parkeervlak parkeervlak = new Parkeervlak()
            .aantal(DEFAULT_AANTAL)
            .coordinaten(DEFAULT_COORDINATEN)
            .doelgroep(DEFAULT_DOELGROEP)
            .fiscaal(DEFAULT_FISCAAL)
            .plaats(DEFAULT_PLAATS)
            .vlakid(DEFAULT_VLAKID);
        return parkeervlak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeervlak createUpdatedEntity(EntityManager em) {
        Parkeervlak parkeervlak = new Parkeervlak()
            .aantal(UPDATED_AANTAL)
            .coordinaten(UPDATED_COORDINATEN)
            .doelgroep(UPDATED_DOELGROEP)
            .fiscaal(UPDATED_FISCAAL)
            .plaats(UPDATED_PLAATS)
            .vlakid(UPDATED_VLAKID);
        return parkeervlak;
    }

    @BeforeEach
    public void initTest() {
        parkeervlak = createEntity(em);
    }

    @Test
    @Transactional
    void createParkeervlak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Parkeervlak
        var returnedParkeervlak = om.readValue(
            restParkeervlakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeervlak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Parkeervlak.class
        );

        // Validate the Parkeervlak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParkeervlakUpdatableFieldsEquals(returnedParkeervlak, getPersistedParkeervlak(returnedParkeervlak));
    }

    @Test
    @Transactional
    void createParkeervlakWithExistingId() throws Exception {
        // Create the Parkeervlak with an existing ID
        parkeervlak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkeervlakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeervlak)))
            .andExpect(status().isBadRequest());

        // Validate the Parkeervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParkeervlaks() throws Exception {
        // Initialize the database
        parkeervlakRepository.saveAndFlush(parkeervlak);

        // Get all the parkeervlakList
        restParkeervlakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkeervlak.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].coordinaten").value(hasItem(DEFAULT_COORDINATEN)))
            .andExpect(jsonPath("$.[*].doelgroep").value(hasItem(DEFAULT_DOELGROEP)))
            .andExpect(jsonPath("$.[*].fiscaal").value(hasItem(DEFAULT_FISCAAL.booleanValue())))
            .andExpect(jsonPath("$.[*].plaats").value(hasItem(DEFAULT_PLAATS)))
            .andExpect(jsonPath("$.[*].vlakid").value(hasItem(DEFAULT_VLAKID)));
    }

    @Test
    @Transactional
    void getParkeervlak() throws Exception {
        // Initialize the database
        parkeervlakRepository.saveAndFlush(parkeervlak);

        // Get the parkeervlak
        restParkeervlakMockMvc
            .perform(get(ENTITY_API_URL_ID, parkeervlak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkeervlak.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.coordinaten").value(DEFAULT_COORDINATEN))
            .andExpect(jsonPath("$.doelgroep").value(DEFAULT_DOELGROEP))
            .andExpect(jsonPath("$.fiscaal").value(DEFAULT_FISCAAL.booleanValue()))
            .andExpect(jsonPath("$.plaats").value(DEFAULT_PLAATS))
            .andExpect(jsonPath("$.vlakid").value(DEFAULT_VLAKID));
    }

    @Test
    @Transactional
    void getNonExistingParkeervlak() throws Exception {
        // Get the parkeervlak
        restParkeervlakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParkeervlak() throws Exception {
        // Initialize the database
        parkeervlakRepository.saveAndFlush(parkeervlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeervlak
        Parkeervlak updatedParkeervlak = parkeervlakRepository.findById(parkeervlak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParkeervlak are not directly saved in db
        em.detach(updatedParkeervlak);
        updatedParkeervlak
            .aantal(UPDATED_AANTAL)
            .coordinaten(UPDATED_COORDINATEN)
            .doelgroep(UPDATED_DOELGROEP)
            .fiscaal(UPDATED_FISCAAL)
            .plaats(UPDATED_PLAATS)
            .vlakid(UPDATED_VLAKID);

        restParkeervlakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParkeervlak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedParkeervlak))
            )
            .andExpect(status().isOk());

        // Validate the Parkeervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParkeervlakToMatchAllProperties(updatedParkeervlak);
    }

    @Test
    @Transactional
    void putNonExistingParkeervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervlak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeervlakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkeervlak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeervlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParkeervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeervlakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeervlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParkeervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeervlakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeervlak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkeervlakWithPatch() throws Exception {
        // Initialize the database
        parkeervlakRepository.saveAndFlush(parkeervlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeervlak using partial update
        Parkeervlak partialUpdatedParkeervlak = new Parkeervlak();
        partialUpdatedParkeervlak.setId(parkeervlak.getId());

        partialUpdatedParkeervlak.doelgroep(UPDATED_DOELGROEP).fiscaal(UPDATED_FISCAAL).plaats(UPDATED_PLAATS).vlakid(UPDATED_VLAKID);

        restParkeervlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeervlak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeervlak))
            )
            .andExpect(status().isOk());

        // Validate the Parkeervlak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeervlakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParkeervlak, parkeervlak),
            getPersistedParkeervlak(parkeervlak)
        );
    }

    @Test
    @Transactional
    void fullUpdateParkeervlakWithPatch() throws Exception {
        // Initialize the database
        parkeervlakRepository.saveAndFlush(parkeervlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeervlak using partial update
        Parkeervlak partialUpdatedParkeervlak = new Parkeervlak();
        partialUpdatedParkeervlak.setId(parkeervlak.getId());

        partialUpdatedParkeervlak
            .aantal(UPDATED_AANTAL)
            .coordinaten(UPDATED_COORDINATEN)
            .doelgroep(UPDATED_DOELGROEP)
            .fiscaal(UPDATED_FISCAAL)
            .plaats(UPDATED_PLAATS)
            .vlakid(UPDATED_VLAKID);

        restParkeervlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeervlak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeervlak))
            )
            .andExpect(status().isOk());

        // Validate the Parkeervlak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeervlakUpdatableFieldsEquals(partialUpdatedParkeervlak, getPersistedParkeervlak(partialUpdatedParkeervlak));
    }

    @Test
    @Transactional
    void patchNonExistingParkeervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervlak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeervlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkeervlak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeervlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParkeervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeervlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeervlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParkeervlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeervlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeervlakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(parkeervlak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeervlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParkeervlak() throws Exception {
        // Initialize the database
        parkeervlakRepository.saveAndFlush(parkeervlak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parkeervlak
        restParkeervlakMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkeervlak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parkeervlakRepository.count();
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

    protected Parkeervlak getPersistedParkeervlak(Parkeervlak parkeervlak) {
        return parkeervlakRepository.findById(parkeervlak.getId()).orElseThrow();
    }

    protected void assertPersistedParkeervlakToMatchAllProperties(Parkeervlak expectedParkeervlak) {
        assertParkeervlakAllPropertiesEquals(expectedParkeervlak, getPersistedParkeervlak(expectedParkeervlak));
    }

    protected void assertPersistedParkeervlakToMatchUpdatableProperties(Parkeervlak expectedParkeervlak) {
        assertParkeervlakAllUpdatablePropertiesEquals(expectedParkeervlak, getPersistedParkeervlak(expectedParkeervlak));
    }
}
