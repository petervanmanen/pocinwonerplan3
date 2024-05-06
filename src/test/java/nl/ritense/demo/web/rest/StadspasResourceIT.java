package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StadspasAsserts.*;
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
import nl.ritense.demo.domain.Stadspas;
import nl.ritense.demo.repository.StadspasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StadspasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StadspasResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/stadspas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StadspasRepository stadspasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStadspasMockMvc;

    private Stadspas stadspas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stadspas createEntity(EntityManager em) {
        Stadspas stadspas = new Stadspas().datumeinde(DEFAULT_DATUMEINDE).datumstart(DEFAULT_DATUMSTART);
        return stadspas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stadspas createUpdatedEntity(EntityManager em) {
        Stadspas stadspas = new Stadspas().datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);
        return stadspas;
    }

    @BeforeEach
    public void initTest() {
        stadspas = createEntity(em);
    }

    @Test
    @Transactional
    void createStadspas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Stadspas
        var returnedStadspas = om.readValue(
            restStadspasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stadspas)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Stadspas.class
        );

        // Validate the Stadspas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStadspasUpdatableFieldsEquals(returnedStadspas, getPersistedStadspas(returnedStadspas));
    }

    @Test
    @Transactional
    void createStadspasWithExistingId() throws Exception {
        // Create the Stadspas with an existing ID
        stadspas.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStadspasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stadspas)))
            .andExpect(status().isBadRequest());

        // Validate the Stadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStadspas() throws Exception {
        // Initialize the database
        stadspasRepository.saveAndFlush(stadspas);

        // Get all the stadspasList
        restStadspasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stadspas.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())));
    }

    @Test
    @Transactional
    void getStadspas() throws Exception {
        // Initialize the database
        stadspasRepository.saveAndFlush(stadspas);

        // Get the stadspas
        restStadspasMockMvc
            .perform(get(ENTITY_API_URL_ID, stadspas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stadspas.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStadspas() throws Exception {
        // Get the stadspas
        restStadspasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStadspas() throws Exception {
        // Initialize the database
        stadspasRepository.saveAndFlush(stadspas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stadspas
        Stadspas updatedStadspas = stadspasRepository.findById(stadspas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStadspas are not directly saved in db
        em.detach(updatedStadspas);
        updatedStadspas.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);

        restStadspasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStadspas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStadspas))
            )
            .andExpect(status().isOk());

        // Validate the Stadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStadspasToMatchAllProperties(updatedStadspas);
    }

    @Test
    @Transactional
    void putNonExistingStadspas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stadspas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStadspasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stadspas.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stadspas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStadspas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stadspas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadspasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stadspas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStadspas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stadspas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadspasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stadspas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStadspasWithPatch() throws Exception {
        // Initialize the database
        stadspasRepository.saveAndFlush(stadspas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stadspas using partial update
        Stadspas partialUpdatedStadspas = new Stadspas();
        partialUpdatedStadspas.setId(stadspas.getId());

        partialUpdatedStadspas.datumeinde(UPDATED_DATUMEINDE);

        restStadspasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStadspas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStadspas))
            )
            .andExpect(status().isOk());

        // Validate the Stadspas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStadspasUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedStadspas, stadspas), getPersistedStadspas(stadspas));
    }

    @Test
    @Transactional
    void fullUpdateStadspasWithPatch() throws Exception {
        // Initialize the database
        stadspasRepository.saveAndFlush(stadspas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stadspas using partial update
        Stadspas partialUpdatedStadspas = new Stadspas();
        partialUpdatedStadspas.setId(stadspas.getId());

        partialUpdatedStadspas.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);

        restStadspasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStadspas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStadspas))
            )
            .andExpect(status().isOk());

        // Validate the Stadspas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStadspasUpdatableFieldsEquals(partialUpdatedStadspas, getPersistedStadspas(partialUpdatedStadspas));
    }

    @Test
    @Transactional
    void patchNonExistingStadspas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stadspas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStadspasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stadspas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stadspas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStadspas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stadspas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadspasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stadspas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStadspas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stadspas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadspasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stadspas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stadspas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStadspas() throws Exception {
        // Initialize the database
        stadspasRepository.saveAndFlush(stadspas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the stadspas
        restStadspasMockMvc
            .perform(delete(ENTITY_API_URL_ID, stadspas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stadspasRepository.count();
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

    protected Stadspas getPersistedStadspas(Stadspas stadspas) {
        return stadspasRepository.findById(stadspas.getId()).orElseThrow();
    }

    protected void assertPersistedStadspasToMatchAllProperties(Stadspas expectedStadspas) {
        assertStadspasAllPropertiesEquals(expectedStadspas, getPersistedStadspas(expectedStadspas));
    }

    protected void assertPersistedStadspasToMatchUpdatableProperties(Stadspas expectedStadspas) {
        assertStadspasAllUpdatablePropertiesEquals(expectedStadspas, getPersistedStadspas(expectedStadspas));
    }
}
