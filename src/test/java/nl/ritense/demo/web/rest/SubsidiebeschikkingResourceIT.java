package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SubsidiebeschikkingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Subsidiebeschikking;
import nl.ritense.demo.repository.SubsidiebeschikkingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubsidiebeschikkingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubsidiebeschikkingResourceIT {

    private static final String DEFAULT_BESCHIKKINGSNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_BESCHIKKINGSNUMMER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BESCHIKTBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BESCHIKTBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_BESLUIT = "AAAAAAAAAA";
    private static final String UPDATED_BESLUIT = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNKENMERK = "AAAAAAAAAA";
    private static final String UPDATED_INTERNKENMERK = "BBBBBBBBBB";

    private static final String DEFAULT_KENMERK = "AAAAAAAAAA";
    private static final String UPDATED_KENMERK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ONTVANGEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ONTVANGEN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subsidiebeschikkings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubsidiebeschikkingRepository subsidiebeschikkingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubsidiebeschikkingMockMvc;

    private Subsidiebeschikking subsidiebeschikking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidiebeschikking createEntity(EntityManager em) {
        Subsidiebeschikking subsidiebeschikking = new Subsidiebeschikking()
            .beschikkingsnummer(DEFAULT_BESCHIKKINGSNUMMER)
            .beschiktbedrag(DEFAULT_BESCHIKTBEDRAG)
            .besluit(DEFAULT_BESLUIT)
            .internkenmerk(DEFAULT_INTERNKENMERK)
            .kenmerk(DEFAULT_KENMERK)
            .ontvangen(DEFAULT_ONTVANGEN)
            .opmerkingen(DEFAULT_OPMERKINGEN);
        return subsidiebeschikking;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidiebeschikking createUpdatedEntity(EntityManager em) {
        Subsidiebeschikking subsidiebeschikking = new Subsidiebeschikking()
            .beschikkingsnummer(UPDATED_BESCHIKKINGSNUMMER)
            .beschiktbedrag(UPDATED_BESCHIKTBEDRAG)
            .besluit(UPDATED_BESLUIT)
            .internkenmerk(UPDATED_INTERNKENMERK)
            .kenmerk(UPDATED_KENMERK)
            .ontvangen(UPDATED_ONTVANGEN)
            .opmerkingen(UPDATED_OPMERKINGEN);
        return subsidiebeschikking;
    }

    @BeforeEach
    public void initTest() {
        subsidiebeschikking = createEntity(em);
    }

    @Test
    @Transactional
    void createSubsidiebeschikking() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Subsidiebeschikking
        var returnedSubsidiebeschikking = om.readValue(
            restSubsidiebeschikkingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidiebeschikking)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Subsidiebeschikking.class
        );

        // Validate the Subsidiebeschikking in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubsidiebeschikkingUpdatableFieldsEquals(
            returnedSubsidiebeschikking,
            getPersistedSubsidiebeschikking(returnedSubsidiebeschikking)
        );
    }

    @Test
    @Transactional
    void createSubsidiebeschikkingWithExistingId() throws Exception {
        // Create the Subsidiebeschikking with an existing ID
        subsidiebeschikking.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubsidiebeschikkingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidiebeschikking)))
            .andExpect(status().isBadRequest());

        // Validate the Subsidiebeschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubsidiebeschikkings() throws Exception {
        // Initialize the database
        subsidiebeschikkingRepository.saveAndFlush(subsidiebeschikking);

        // Get all the subsidiebeschikkingList
        restSubsidiebeschikkingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsidiebeschikking.getId().intValue())))
            .andExpect(jsonPath("$.[*].beschikkingsnummer").value(hasItem(DEFAULT_BESCHIKKINGSNUMMER)))
            .andExpect(jsonPath("$.[*].beschiktbedrag").value(hasItem(sameNumber(DEFAULT_BESCHIKTBEDRAG))))
            .andExpect(jsonPath("$.[*].besluit").value(hasItem(DEFAULT_BESLUIT)))
            .andExpect(jsonPath("$.[*].internkenmerk").value(hasItem(DEFAULT_INTERNKENMERK)))
            .andExpect(jsonPath("$.[*].kenmerk").value(hasItem(DEFAULT_KENMERK)))
            .andExpect(jsonPath("$.[*].ontvangen").value(hasItem(DEFAULT_ONTVANGEN.toString())))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)));
    }

    @Test
    @Transactional
    void getSubsidiebeschikking() throws Exception {
        // Initialize the database
        subsidiebeschikkingRepository.saveAndFlush(subsidiebeschikking);

        // Get the subsidiebeschikking
        restSubsidiebeschikkingMockMvc
            .perform(get(ENTITY_API_URL_ID, subsidiebeschikking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subsidiebeschikking.getId().intValue()))
            .andExpect(jsonPath("$.beschikkingsnummer").value(DEFAULT_BESCHIKKINGSNUMMER))
            .andExpect(jsonPath("$.beschiktbedrag").value(sameNumber(DEFAULT_BESCHIKTBEDRAG)))
            .andExpect(jsonPath("$.besluit").value(DEFAULT_BESLUIT))
            .andExpect(jsonPath("$.internkenmerk").value(DEFAULT_INTERNKENMERK))
            .andExpect(jsonPath("$.kenmerk").value(DEFAULT_KENMERK))
            .andExpect(jsonPath("$.ontvangen").value(DEFAULT_ONTVANGEN.toString()))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN));
    }

    @Test
    @Transactional
    void getNonExistingSubsidiebeschikking() throws Exception {
        // Get the subsidiebeschikking
        restSubsidiebeschikkingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubsidiebeschikking() throws Exception {
        // Initialize the database
        subsidiebeschikkingRepository.saveAndFlush(subsidiebeschikking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidiebeschikking
        Subsidiebeschikking updatedSubsidiebeschikking = subsidiebeschikkingRepository.findById(subsidiebeschikking.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubsidiebeschikking are not directly saved in db
        em.detach(updatedSubsidiebeschikking);
        updatedSubsidiebeschikking
            .beschikkingsnummer(UPDATED_BESCHIKKINGSNUMMER)
            .beschiktbedrag(UPDATED_BESCHIKTBEDRAG)
            .besluit(UPDATED_BESLUIT)
            .internkenmerk(UPDATED_INTERNKENMERK)
            .kenmerk(UPDATED_KENMERK)
            .ontvangen(UPDATED_ONTVANGEN)
            .opmerkingen(UPDATED_OPMERKINGEN);

        restSubsidiebeschikkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubsidiebeschikking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubsidiebeschikking))
            )
            .andExpect(status().isOk());

        // Validate the Subsidiebeschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubsidiebeschikkingToMatchAllProperties(updatedSubsidiebeschikking);
    }

    @Test
    @Transactional
    void putNonExistingSubsidiebeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiebeschikking.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidiebeschikkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subsidiebeschikking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidiebeschikking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidiebeschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubsidiebeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiebeschikking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidiebeschikkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidiebeschikking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidiebeschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubsidiebeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiebeschikking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidiebeschikkingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidiebeschikking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidiebeschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubsidiebeschikkingWithPatch() throws Exception {
        // Initialize the database
        subsidiebeschikkingRepository.saveAndFlush(subsidiebeschikking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidiebeschikking using partial update
        Subsidiebeschikking partialUpdatedSubsidiebeschikking = new Subsidiebeschikking();
        partialUpdatedSubsidiebeschikking.setId(subsidiebeschikking.getId());

        partialUpdatedSubsidiebeschikking
            .beschikkingsnummer(UPDATED_BESCHIKKINGSNUMMER)
            .internkenmerk(UPDATED_INTERNKENMERK)
            .ontvangen(UPDATED_ONTVANGEN);

        restSubsidiebeschikkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidiebeschikking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidiebeschikking))
            )
            .andExpect(status().isOk());

        // Validate the Subsidiebeschikking in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidiebeschikkingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubsidiebeschikking, subsidiebeschikking),
            getPersistedSubsidiebeschikking(subsidiebeschikking)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubsidiebeschikkingWithPatch() throws Exception {
        // Initialize the database
        subsidiebeschikkingRepository.saveAndFlush(subsidiebeschikking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidiebeschikking using partial update
        Subsidiebeschikking partialUpdatedSubsidiebeschikking = new Subsidiebeschikking();
        partialUpdatedSubsidiebeschikking.setId(subsidiebeschikking.getId());

        partialUpdatedSubsidiebeschikking
            .beschikkingsnummer(UPDATED_BESCHIKKINGSNUMMER)
            .beschiktbedrag(UPDATED_BESCHIKTBEDRAG)
            .besluit(UPDATED_BESLUIT)
            .internkenmerk(UPDATED_INTERNKENMERK)
            .kenmerk(UPDATED_KENMERK)
            .ontvangen(UPDATED_ONTVANGEN)
            .opmerkingen(UPDATED_OPMERKINGEN);

        restSubsidiebeschikkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidiebeschikking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidiebeschikking))
            )
            .andExpect(status().isOk());

        // Validate the Subsidiebeschikking in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidiebeschikkingUpdatableFieldsEquals(
            partialUpdatedSubsidiebeschikking,
            getPersistedSubsidiebeschikking(partialUpdatedSubsidiebeschikking)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSubsidiebeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiebeschikking.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidiebeschikkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subsidiebeschikking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidiebeschikking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidiebeschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubsidiebeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiebeschikking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidiebeschikkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidiebeschikking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidiebeschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubsidiebeschikking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiebeschikking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidiebeschikkingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subsidiebeschikking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidiebeschikking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubsidiebeschikking() throws Exception {
        // Initialize the database
        subsidiebeschikkingRepository.saveAndFlush(subsidiebeschikking);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subsidiebeschikking
        restSubsidiebeschikkingMockMvc
            .perform(delete(ENTITY_API_URL_ID, subsidiebeschikking.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subsidiebeschikkingRepository.count();
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

    protected Subsidiebeschikking getPersistedSubsidiebeschikking(Subsidiebeschikking subsidiebeschikking) {
        return subsidiebeschikkingRepository.findById(subsidiebeschikking.getId()).orElseThrow();
    }

    protected void assertPersistedSubsidiebeschikkingToMatchAllProperties(Subsidiebeschikking expectedSubsidiebeschikking) {
        assertSubsidiebeschikkingAllPropertiesEquals(
            expectedSubsidiebeschikking,
            getPersistedSubsidiebeschikking(expectedSubsidiebeschikking)
        );
    }

    protected void assertPersistedSubsidiebeschikkingToMatchUpdatableProperties(Subsidiebeschikking expectedSubsidiebeschikking) {
        assertSubsidiebeschikkingAllUpdatablePropertiesEquals(
            expectedSubsidiebeschikking,
            getPersistedSubsidiebeschikking(expectedSubsidiebeschikking)
        );
    }
}
