package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PlanAsserts.*;
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
import nl.ritense.demo.domain.Plan;
import nl.ritense.demo.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlanResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class PlanResourceIT {

    private static final Boolean DEFAULT_ZEVENTIGPROCENTVERKOCHT = false;
    private static final Boolean UPDATED_ZEVENTIGPROCENTVERKOCHT = true;

    private static final Boolean DEFAULT_AARDGASLOOS = false;
    private static final Boolean UPDATED_AARDGASLOOS = true;

    private static final Boolean DEFAULT_BESTEMMINGGOEDGEKEURD = false;
    private static final Boolean UPDATED_BESTEMMINGGOEDGEKEURD = true;

    private static final LocalDate DEFAULT_EERSTEOPLEVERING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EERSTEOPLEVERING = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_EIGENDOMGEMEENTE = false;
    private static final Boolean UPDATED_EIGENDOMGEMEENTE = true;

    private static final Boolean DEFAULT_GEBIEDSTRANSFORMATIE = false;
    private static final Boolean UPDATED_GEBIEDSTRANSFORMATIE = true;

    private static final Boolean DEFAULT_INTENTIE = false;
    private static final Boolean UPDATED_INTENTIE = true;

    private static final LocalDate DEFAULT_LAATSTEOPLEVERING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAATSTEOPLEVERING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ONHERROEPELIJK = false;
    private static final Boolean UPDATED_ONHERROEPELIJK = true;

    private static final String DEFAULT_PERCELEN = "AAAAAAAAAA";
    private static final String UPDATED_PERCELEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STARTBOUW = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTBOUW = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STARTVERKOOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTVERKOOP = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanMockMvc;

    private Plan plan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plan createEntity(EntityManager em) {
        Plan plan = new Plan()
            .zeventigprocentverkocht(DEFAULT_ZEVENTIGPROCENTVERKOCHT)
            .aardgasloos(DEFAULT_AARDGASLOOS)
            .bestemminggoedgekeurd(DEFAULT_BESTEMMINGGOEDGEKEURD)
            .eersteoplevering(DEFAULT_EERSTEOPLEVERING)
            .eigendomgemeente(DEFAULT_EIGENDOMGEMEENTE)
            .gebiedstransformatie(DEFAULT_GEBIEDSTRANSFORMATIE)
            .intentie(DEFAULT_INTENTIE)
            .laatsteoplevering(DEFAULT_LAATSTEOPLEVERING)
            .naam(DEFAULT_NAAM)
            .nummer(DEFAULT_NUMMER)
            .onherroepelijk(DEFAULT_ONHERROEPELIJK)
            .percelen(DEFAULT_PERCELEN)
            .startbouw(DEFAULT_STARTBOUW)
            .startverkoop(DEFAULT_STARTVERKOOP);
        return plan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plan createUpdatedEntity(EntityManager em) {
        Plan plan = new Plan()
            .zeventigprocentverkocht(UPDATED_ZEVENTIGPROCENTVERKOCHT)
            .aardgasloos(UPDATED_AARDGASLOOS)
            .bestemminggoedgekeurd(UPDATED_BESTEMMINGGOEDGEKEURD)
            .eersteoplevering(UPDATED_EERSTEOPLEVERING)
            .eigendomgemeente(UPDATED_EIGENDOMGEMEENTE)
            .gebiedstransformatie(UPDATED_GEBIEDSTRANSFORMATIE)
            .intentie(UPDATED_INTENTIE)
            .laatsteoplevering(UPDATED_LAATSTEOPLEVERING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .onherroepelijk(UPDATED_ONHERROEPELIJK)
            .percelen(UPDATED_PERCELEN)
            .startbouw(UPDATED_STARTBOUW)
            .startverkoop(UPDATED_STARTVERKOOP);
        return plan;
    }

    @BeforeEach
    public void initTest() {
        plan = createEntity(em);
    }

    @Test
    @Transactional
    void createPlan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Plan
        var returnedPlan = om.readValue(
            restPlanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plan)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Plan.class
        );

        // Validate the Plan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlanUpdatableFieldsEquals(returnedPlan, getPersistedPlan(returnedPlan));
    }

    @Test
    @Transactional
    void createPlanWithExistingId() throws Exception {
        // Create the Plan with an existing ID
        plan.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plan)))
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlans() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get all the planList
        restPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plan.getId().intValue())))
            .andExpect(jsonPath("$.[*].zeventigprocentverkocht").value(hasItem(DEFAULT_ZEVENTIGPROCENTVERKOCHT.booleanValue())))
            .andExpect(jsonPath("$.[*].aardgasloos").value(hasItem(DEFAULT_AARDGASLOOS.booleanValue())))
            .andExpect(jsonPath("$.[*].bestemminggoedgekeurd").value(hasItem(DEFAULT_BESTEMMINGGOEDGEKEURD.booleanValue())))
            .andExpect(jsonPath("$.[*].eersteoplevering").value(hasItem(DEFAULT_EERSTEOPLEVERING.toString())))
            .andExpect(jsonPath("$.[*].eigendomgemeente").value(hasItem(DEFAULT_EIGENDOMGEMEENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].gebiedstransformatie").value(hasItem(DEFAULT_GEBIEDSTRANSFORMATIE.booleanValue())))
            .andExpect(jsonPath("$.[*].intentie").value(hasItem(DEFAULT_INTENTIE.booleanValue())))
            .andExpect(jsonPath("$.[*].laatsteoplevering").value(hasItem(DEFAULT_LAATSTEOPLEVERING.toString())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].onherroepelijk").value(hasItem(DEFAULT_ONHERROEPELIJK.booleanValue())))
            .andExpect(jsonPath("$.[*].percelen").value(hasItem(DEFAULT_PERCELEN)))
            .andExpect(jsonPath("$.[*].startbouw").value(hasItem(DEFAULT_STARTBOUW.toString())))
            .andExpect(jsonPath("$.[*].startverkoop").value(hasItem(DEFAULT_STARTVERKOOP.toString())));
    }

    @Test
    @Transactional
    void getPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get the plan
        restPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, plan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plan.getId().intValue()))
            .andExpect(jsonPath("$.zeventigprocentverkocht").value(DEFAULT_ZEVENTIGPROCENTVERKOCHT.booleanValue()))
            .andExpect(jsonPath("$.aardgasloos").value(DEFAULT_AARDGASLOOS.booleanValue()))
            .andExpect(jsonPath("$.bestemminggoedgekeurd").value(DEFAULT_BESTEMMINGGOEDGEKEURD.booleanValue()))
            .andExpect(jsonPath("$.eersteoplevering").value(DEFAULT_EERSTEOPLEVERING.toString()))
            .andExpect(jsonPath("$.eigendomgemeente").value(DEFAULT_EIGENDOMGEMEENTE.booleanValue()))
            .andExpect(jsonPath("$.gebiedstransformatie").value(DEFAULT_GEBIEDSTRANSFORMATIE.booleanValue()))
            .andExpect(jsonPath("$.intentie").value(DEFAULT_INTENTIE.booleanValue()))
            .andExpect(jsonPath("$.laatsteoplevering").value(DEFAULT_LAATSTEOPLEVERING.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.onherroepelijk").value(DEFAULT_ONHERROEPELIJK.booleanValue()))
            .andExpect(jsonPath("$.percelen").value(DEFAULT_PERCELEN))
            .andExpect(jsonPath("$.startbouw").value(DEFAULT_STARTBOUW.toString()))
            .andExpect(jsonPath("$.startverkoop").value(DEFAULT_STARTVERKOOP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlan() throws Exception {
        // Get the plan
        restPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plan
        Plan updatedPlan = planRepository.findById(plan.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlan are not directly saved in db
        em.detach(updatedPlan);
        updatedPlan
            .zeventigprocentverkocht(UPDATED_ZEVENTIGPROCENTVERKOCHT)
            .aardgasloos(UPDATED_AARDGASLOOS)
            .bestemminggoedgekeurd(UPDATED_BESTEMMINGGOEDGEKEURD)
            .eersteoplevering(UPDATED_EERSTEOPLEVERING)
            .eigendomgemeente(UPDATED_EIGENDOMGEMEENTE)
            .gebiedstransformatie(UPDATED_GEBIEDSTRANSFORMATIE)
            .intentie(UPDATED_INTENTIE)
            .laatsteoplevering(UPDATED_LAATSTEOPLEVERING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .onherroepelijk(UPDATED_ONHERROEPELIJK)
            .percelen(UPDATED_PERCELEN)
            .startbouw(UPDATED_STARTBOUW)
            .startverkoop(UPDATED_STARTVERKOOP);

        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlan))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlanToMatchAllProperties(updatedPlan);
    }

    @Test
    @Transactional
    void putNonExistingPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plan.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(put(ENTITY_API_URL_ID, plan.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plan)))
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanWithPatch() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plan using partial update
        Plan partialUpdatedPlan = new Plan();
        partialUpdatedPlan.setId(plan.getId());

        partialUpdatedPlan
            .zeventigprocentverkocht(UPDATED_ZEVENTIGPROCENTVERKOCHT)
            .eersteoplevering(UPDATED_EERSTEOPLEVERING)
            .eigendomgemeente(UPDATED_EIGENDOMGEMEENTE)
            .gebiedstransformatie(UPDATED_GEBIEDSTRANSFORMATIE)
            .intentie(UPDATED_INTENTIE)
            .laatsteoplevering(UPDATED_LAATSTEOPLEVERING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .startbouw(UPDATED_STARTBOUW)
            .startverkoop(UPDATED_STARTVERKOOP);

        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlan))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPlan, plan), getPersistedPlan(plan));
    }

    @Test
    @Transactional
    void fullUpdatePlanWithPatch() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plan using partial update
        Plan partialUpdatedPlan = new Plan();
        partialUpdatedPlan.setId(plan.getId());

        partialUpdatedPlan
            .zeventigprocentverkocht(UPDATED_ZEVENTIGPROCENTVERKOCHT)
            .aardgasloos(UPDATED_AARDGASLOOS)
            .bestemminggoedgekeurd(UPDATED_BESTEMMINGGOEDGEKEURD)
            .eersteoplevering(UPDATED_EERSTEOPLEVERING)
            .eigendomgemeente(UPDATED_EIGENDOMGEMEENTE)
            .gebiedstransformatie(UPDATED_GEBIEDSTRANSFORMATIE)
            .intentie(UPDATED_INTENTIE)
            .laatsteoplevering(UPDATED_LAATSTEOPLEVERING)
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .onherroepelijk(UPDATED_ONHERROEPELIJK)
            .percelen(UPDATED_PERCELEN)
            .startbouw(UPDATED_STARTBOUW)
            .startverkoop(UPDATED_STARTVERKOOP);

        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlan))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanUpdatableFieldsEquals(partialUpdatedPlan, getPersistedPlan(partialUpdatedPlan));
    }

    @Test
    @Transactional
    void patchNonExistingPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plan.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(patch(ENTITY_API_URL_ID, plan.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(plan)))
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(plan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plan.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(plan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the plan
        restPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, plan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return planRepository.count();
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

    protected Plan getPersistedPlan(Plan plan) {
        return planRepository.findById(plan.getId()).orElseThrow();
    }

    protected void assertPersistedPlanToMatchAllProperties(Plan expectedPlan) {
        assertPlanAllPropertiesEquals(expectedPlan, getPersistedPlan(expectedPlan));
    }

    protected void assertPersistedPlanToMatchUpdatableProperties(Plan expectedPlan) {
        assertPlanAllUpdatablePropertiesEquals(expectedPlan, getPersistedPlan(expectedPlan));
    }
}
