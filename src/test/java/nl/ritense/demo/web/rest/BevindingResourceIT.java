package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BevindingAsserts.*;
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
import nl.ritense.demo.domain.Bevinding;
import nl.ritense.demo.repository.BevindingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BevindingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BevindingResourceIT {

    private static final String DEFAULT_AANGEMAAKTDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANGEMAAKTDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITEIT = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROLEELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTROLEELEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROLENIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_CONTROLENIVEAU = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANMAAK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANMAAK = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMMUTATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMMUTATIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DIEPTE = "AAAAAAAAAA";
    private static final String UPDATED_DIEPTE = "BBBBBBBBBB";

    private static final String DEFAULT_FASE = "AAAAAAAAAA";
    private static final String UPDATED_FASE = "BBBBBBBBBB";

    private static final String DEFAULT_GEMUTEERDDOOR = "AAAAAAAAAA";
    private static final String UPDATED_GEMUTEERDDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAAT = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAAT = "BBBBBBBBBB";

    private static final String DEFAULT_RISICO = "AAAAAAAAAA";
    private static final String UPDATED_RISICO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bevindings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BevindingRepository bevindingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBevindingMockMvc;

    private Bevinding bevinding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bevinding createEntity(EntityManager em) {
        Bevinding bevinding = new Bevinding()
            .aangemaaktdoor(DEFAULT_AANGEMAAKTDOOR)
            .activiteit(DEFAULT_ACTIVITEIT)
            .controleelement(DEFAULT_CONTROLEELEMENT)
            .controleniveau(DEFAULT_CONTROLENIVEAU)
            .datumaanmaak(DEFAULT_DATUMAANMAAK)
            .datummutatie(DEFAULT_DATUMMUTATIE)
            .diepte(DEFAULT_DIEPTE)
            .fase(DEFAULT_FASE)
            .gemuteerddoor(DEFAULT_GEMUTEERDDOOR)
            .resultaat(DEFAULT_RESULTAAT)
            .risico(DEFAULT_RISICO);
        return bevinding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bevinding createUpdatedEntity(EntityManager em) {
        Bevinding bevinding = new Bevinding()
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .activiteit(UPDATED_ACTIVITEIT)
            .controleelement(UPDATED_CONTROLEELEMENT)
            .controleniveau(UPDATED_CONTROLENIVEAU)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .diepte(UPDATED_DIEPTE)
            .fase(UPDATED_FASE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .resultaat(UPDATED_RESULTAAT)
            .risico(UPDATED_RISICO);
        return bevinding;
    }

    @BeforeEach
    public void initTest() {
        bevinding = createEntity(em);
    }

    @Test
    @Transactional
    void createBevinding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bevinding
        var returnedBevinding = om.readValue(
            restBevindingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bevinding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bevinding.class
        );

        // Validate the Bevinding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBevindingUpdatableFieldsEquals(returnedBevinding, getPersistedBevinding(returnedBevinding));
    }

    @Test
    @Transactional
    void createBevindingWithExistingId() throws Exception {
        // Create the Bevinding with an existing ID
        bevinding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBevindingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bevinding)))
            .andExpect(status().isBadRequest());

        // Validate the Bevinding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBevindings() throws Exception {
        // Initialize the database
        bevindingRepository.saveAndFlush(bevinding);

        // Get all the bevindingList
        restBevindingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bevinding.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangemaaktdoor").value(hasItem(DEFAULT_AANGEMAAKTDOOR)))
            .andExpect(jsonPath("$.[*].activiteit").value(hasItem(DEFAULT_ACTIVITEIT)))
            .andExpect(jsonPath("$.[*].controleelement").value(hasItem(DEFAULT_CONTROLEELEMENT)))
            .andExpect(jsonPath("$.[*].controleniveau").value(hasItem(DEFAULT_CONTROLENIVEAU)))
            .andExpect(jsonPath("$.[*].datumaanmaak").value(hasItem(DEFAULT_DATUMAANMAAK.toString())))
            .andExpect(jsonPath("$.[*].datummutatie").value(hasItem(DEFAULT_DATUMMUTATIE.toString())))
            .andExpect(jsonPath("$.[*].diepte").value(hasItem(DEFAULT_DIEPTE)))
            .andExpect(jsonPath("$.[*].fase").value(hasItem(DEFAULT_FASE)))
            .andExpect(jsonPath("$.[*].gemuteerddoor").value(hasItem(DEFAULT_GEMUTEERDDOOR)))
            .andExpect(jsonPath("$.[*].resultaat").value(hasItem(DEFAULT_RESULTAAT)))
            .andExpect(jsonPath("$.[*].risico").value(hasItem(DEFAULT_RISICO)));
    }

    @Test
    @Transactional
    void getBevinding() throws Exception {
        // Initialize the database
        bevindingRepository.saveAndFlush(bevinding);

        // Get the bevinding
        restBevindingMockMvc
            .perform(get(ENTITY_API_URL_ID, bevinding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bevinding.getId().intValue()))
            .andExpect(jsonPath("$.aangemaaktdoor").value(DEFAULT_AANGEMAAKTDOOR))
            .andExpect(jsonPath("$.activiteit").value(DEFAULT_ACTIVITEIT))
            .andExpect(jsonPath("$.controleelement").value(DEFAULT_CONTROLEELEMENT))
            .andExpect(jsonPath("$.controleniveau").value(DEFAULT_CONTROLENIVEAU))
            .andExpect(jsonPath("$.datumaanmaak").value(DEFAULT_DATUMAANMAAK.toString()))
            .andExpect(jsonPath("$.datummutatie").value(DEFAULT_DATUMMUTATIE.toString()))
            .andExpect(jsonPath("$.diepte").value(DEFAULT_DIEPTE))
            .andExpect(jsonPath("$.fase").value(DEFAULT_FASE))
            .andExpect(jsonPath("$.gemuteerddoor").value(DEFAULT_GEMUTEERDDOOR))
            .andExpect(jsonPath("$.resultaat").value(DEFAULT_RESULTAAT))
            .andExpect(jsonPath("$.risico").value(DEFAULT_RISICO));
    }

    @Test
    @Transactional
    void getNonExistingBevinding() throws Exception {
        // Get the bevinding
        restBevindingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBevinding() throws Exception {
        // Initialize the database
        bevindingRepository.saveAndFlush(bevinding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bevinding
        Bevinding updatedBevinding = bevindingRepository.findById(bevinding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBevinding are not directly saved in db
        em.detach(updatedBevinding);
        updatedBevinding
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .activiteit(UPDATED_ACTIVITEIT)
            .controleelement(UPDATED_CONTROLEELEMENT)
            .controleniveau(UPDATED_CONTROLENIVEAU)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .diepte(UPDATED_DIEPTE)
            .fase(UPDATED_FASE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .resultaat(UPDATED_RESULTAAT)
            .risico(UPDATED_RISICO);

        restBevindingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBevinding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBevinding))
            )
            .andExpect(status().isOk());

        // Validate the Bevinding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBevindingToMatchAllProperties(updatedBevinding);
    }

    @Test
    @Transactional
    void putNonExistingBevinding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevinding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBevindingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bevinding.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bevinding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bevinding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBevinding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevinding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBevindingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bevinding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bevinding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBevinding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevinding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBevindingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bevinding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bevinding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBevindingWithPatch() throws Exception {
        // Initialize the database
        bevindingRepository.saveAndFlush(bevinding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bevinding using partial update
        Bevinding partialUpdatedBevinding = new Bevinding();
        partialUpdatedBevinding.setId(bevinding.getId());

        partialUpdatedBevinding
            .controleelement(UPDATED_CONTROLEELEMENT)
            .diepte(UPDATED_DIEPTE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .risico(UPDATED_RISICO);

        restBevindingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBevinding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBevinding))
            )
            .andExpect(status().isOk());

        // Validate the Bevinding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBevindingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBevinding, bevinding),
            getPersistedBevinding(bevinding)
        );
    }

    @Test
    @Transactional
    void fullUpdateBevindingWithPatch() throws Exception {
        // Initialize the database
        bevindingRepository.saveAndFlush(bevinding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bevinding using partial update
        Bevinding partialUpdatedBevinding = new Bevinding();
        partialUpdatedBevinding.setId(bevinding.getId());

        partialUpdatedBevinding
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .activiteit(UPDATED_ACTIVITEIT)
            .controleelement(UPDATED_CONTROLEELEMENT)
            .controleniveau(UPDATED_CONTROLENIVEAU)
            .datumaanmaak(UPDATED_DATUMAANMAAK)
            .datummutatie(UPDATED_DATUMMUTATIE)
            .diepte(UPDATED_DIEPTE)
            .fase(UPDATED_FASE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .resultaat(UPDATED_RESULTAAT)
            .risico(UPDATED_RISICO);

        restBevindingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBevinding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBevinding))
            )
            .andExpect(status().isOk());

        // Validate the Bevinding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBevindingUpdatableFieldsEquals(partialUpdatedBevinding, getPersistedBevinding(partialUpdatedBevinding));
    }

    @Test
    @Transactional
    void patchNonExistingBevinding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevinding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBevindingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bevinding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bevinding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bevinding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBevinding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevinding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBevindingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bevinding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bevinding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBevinding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bevinding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBevindingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bevinding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bevinding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBevinding() throws Exception {
        // Initialize the database
        bevindingRepository.saveAndFlush(bevinding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bevinding
        restBevindingMockMvc
            .perform(delete(ENTITY_API_URL_ID, bevinding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bevindingRepository.count();
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

    protected Bevinding getPersistedBevinding(Bevinding bevinding) {
        return bevindingRepository.findById(bevinding.getId()).orElseThrow();
    }

    protected void assertPersistedBevindingToMatchAllProperties(Bevinding expectedBevinding) {
        assertBevindingAllPropertiesEquals(expectedBevinding, getPersistedBevinding(expectedBevinding));
    }

    protected void assertPersistedBevindingToMatchUpdatableProperties(Bevinding expectedBevinding) {
        assertBevindingAllUpdatablePropertiesEquals(expectedBevinding, getPersistedBevinding(expectedBevinding));
    }
}
