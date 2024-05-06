package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AgendapuntAsserts.*;
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
import nl.ritense.demo.domain.Agendapunt;
import nl.ritense.demo.repository.AgendapuntRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AgendapuntResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgendapuntResourceIT {

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agendapunts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgendapuntRepository agendapuntRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendapuntMockMvc;

    private Agendapunt agendapunt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agendapunt createEntity(EntityManager em) {
        Agendapunt agendapunt = new Agendapunt().nummer(DEFAULT_NUMMER).omschrijving(DEFAULT_OMSCHRIJVING).titel(DEFAULT_TITEL);
        return agendapunt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agendapunt createUpdatedEntity(EntityManager em) {
        Agendapunt agendapunt = new Agendapunt().nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING).titel(UPDATED_TITEL);
        return agendapunt;
    }

    @BeforeEach
    public void initTest() {
        agendapunt = createEntity(em);
    }

    @Test
    @Transactional
    void createAgendapunt() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Agendapunt
        var returnedAgendapunt = om.readValue(
            restAgendapuntMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendapunt)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Agendapunt.class
        );

        // Validate the Agendapunt in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAgendapuntUpdatableFieldsEquals(returnedAgendapunt, getPersistedAgendapunt(returnedAgendapunt));
    }

    @Test
    @Transactional
    void createAgendapuntWithExistingId() throws Exception {
        // Create the Agendapunt with an existing ID
        agendapunt.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendapuntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendapunt)))
            .andExpect(status().isBadRequest());

        // Validate the Agendapunt in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgendapunts() throws Exception {
        // Initialize the database
        agendapuntRepository.saveAndFlush(agendapunt);

        // Get all the agendapuntList
        restAgendapuntMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendapunt.getId().intValue())))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)));
    }

    @Test
    @Transactional
    void getAgendapunt() throws Exception {
        // Initialize the database
        agendapuntRepository.saveAndFlush(agendapunt);

        // Get the agendapunt
        restAgendapuntMockMvc
            .perform(get(ENTITY_API_URL_ID, agendapunt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agendapunt.getId().intValue()))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL));
    }

    @Test
    @Transactional
    void getNonExistingAgendapunt() throws Exception {
        // Get the agendapunt
        restAgendapuntMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgendapunt() throws Exception {
        // Initialize the database
        agendapuntRepository.saveAndFlush(agendapunt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendapunt
        Agendapunt updatedAgendapunt = agendapuntRepository.findById(agendapunt.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAgendapunt are not directly saved in db
        em.detach(updatedAgendapunt);
        updatedAgendapunt.nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING).titel(UPDATED_TITEL);

        restAgendapuntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgendapunt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAgendapunt))
            )
            .andExpect(status().isOk());

        // Validate the Agendapunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgendapuntToMatchAllProperties(updatedAgendapunt);
    }

    @Test
    @Transactional
    void putNonExistingAgendapunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendapunt.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendapuntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendapunt.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendapunt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendapunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgendapunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendapunt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendapuntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendapunt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendapunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgendapunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendapunt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendapuntMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendapunt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agendapunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendapuntWithPatch() throws Exception {
        // Initialize the database
        agendapuntRepository.saveAndFlush(agendapunt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendapunt using partial update
        Agendapunt partialUpdatedAgendapunt = new Agendapunt();
        partialUpdatedAgendapunt.setId(agendapunt.getId());

        partialUpdatedAgendapunt.nummer(UPDATED_NUMMER).titel(UPDATED_TITEL);

        restAgendapuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendapunt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendapunt))
            )
            .andExpect(status().isOk());

        // Validate the Agendapunt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendapuntUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAgendapunt, agendapunt),
            getPersistedAgendapunt(agendapunt)
        );
    }

    @Test
    @Transactional
    void fullUpdateAgendapuntWithPatch() throws Exception {
        // Initialize the database
        agendapuntRepository.saveAndFlush(agendapunt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendapunt using partial update
        Agendapunt partialUpdatedAgendapunt = new Agendapunt();
        partialUpdatedAgendapunt.setId(agendapunt.getId());

        partialUpdatedAgendapunt.nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING).titel(UPDATED_TITEL);

        restAgendapuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendapunt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendapunt))
            )
            .andExpect(status().isOk());

        // Validate the Agendapunt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendapuntUpdatableFieldsEquals(partialUpdatedAgendapunt, getPersistedAgendapunt(partialUpdatedAgendapunt));
    }

    @Test
    @Transactional
    void patchNonExistingAgendapunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendapunt.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendapuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendapunt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendapunt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendapunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgendapunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendapunt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendapuntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendapunt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendapunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgendapunt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendapunt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendapuntMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agendapunt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agendapunt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgendapunt() throws Exception {
        // Initialize the database
        agendapuntRepository.saveAndFlush(agendapunt);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agendapunt
        restAgendapuntMockMvc
            .perform(delete(ENTITY_API_URL_ID, agendapunt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agendapuntRepository.count();
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

    protected Agendapunt getPersistedAgendapunt(Agendapunt agendapunt) {
        return agendapuntRepository.findById(agendapunt.getId()).orElseThrow();
    }

    protected void assertPersistedAgendapuntToMatchAllProperties(Agendapunt expectedAgendapunt) {
        assertAgendapuntAllPropertiesEquals(expectedAgendapunt, getPersistedAgendapunt(expectedAgendapunt));
    }

    protected void assertPersistedAgendapuntToMatchUpdatableProperties(Agendapunt expectedAgendapunt) {
        assertAgendapuntAllUpdatablePropertiesEquals(expectedAgendapunt, getPersistedAgendapunt(expectedAgendapunt));
    }
}
