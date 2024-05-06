package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SubsidieaanvraagAsserts.*;
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
import nl.ritense.demo.domain.Subsidieaanvraag;
import nl.ritense.demo.domain.Subsidiebeschikking;
import nl.ritense.demo.repository.SubsidieaanvraagRepository;
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
 * Integration tests for the {@link SubsidieaanvraagResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class SubsidieaanvraagResourceIT {

    private static final BigDecimal DEFAULT_AANGEVRAAGDBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_AANGEVRAAGDBEDRAG = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUMINDIENING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINDIENING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KENMERK = "AAAAAAAAAA";
    private static final String UPDATED_KENMERK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ONTVANGSTBEVESTIGING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ONTVANGSTBEVESTIGING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VERWACHTEBESCHIKKING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VERWACHTEBESCHIKKING = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/subsidieaanvraags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubsidieaanvraagRepository subsidieaanvraagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubsidieaanvraagMockMvc;

    private Subsidieaanvraag subsidieaanvraag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidieaanvraag createEntity(EntityManager em) {
        Subsidieaanvraag subsidieaanvraag = new Subsidieaanvraag()
            .aangevraagdbedrag(DEFAULT_AANGEVRAAGDBEDRAG)
            .datumindiening(DEFAULT_DATUMINDIENING)
            .kenmerk(DEFAULT_KENMERK)
            .ontvangstbevestiging(DEFAULT_ONTVANGSTBEVESTIGING)
            .verwachtebeschikking(DEFAULT_VERWACHTEBESCHIKKING);
        // Add required entity
        Subsidiebeschikking subsidiebeschikking;
        if (TestUtil.findAll(em, Subsidiebeschikking.class).isEmpty()) {
            subsidiebeschikking = SubsidiebeschikkingResourceIT.createEntity(em);
            em.persist(subsidiebeschikking);
            em.flush();
        } else {
            subsidiebeschikking = TestUtil.findAll(em, Subsidiebeschikking.class).get(0);
        }
        subsidieaanvraag.setMondtuitSubsidiebeschikking(subsidiebeschikking);
        return subsidieaanvraag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidieaanvraag createUpdatedEntity(EntityManager em) {
        Subsidieaanvraag subsidieaanvraag = new Subsidieaanvraag()
            .aangevraagdbedrag(UPDATED_AANGEVRAAGDBEDRAG)
            .datumindiening(UPDATED_DATUMINDIENING)
            .kenmerk(UPDATED_KENMERK)
            .ontvangstbevestiging(UPDATED_ONTVANGSTBEVESTIGING)
            .verwachtebeschikking(UPDATED_VERWACHTEBESCHIKKING);
        // Add required entity
        Subsidiebeschikking subsidiebeschikking;
        if (TestUtil.findAll(em, Subsidiebeschikking.class).isEmpty()) {
            subsidiebeschikking = SubsidiebeschikkingResourceIT.createUpdatedEntity(em);
            em.persist(subsidiebeschikking);
            em.flush();
        } else {
            subsidiebeschikking = TestUtil.findAll(em, Subsidiebeschikking.class).get(0);
        }
        subsidieaanvraag.setMondtuitSubsidiebeschikking(subsidiebeschikking);
        return subsidieaanvraag;
    }

    @BeforeEach
    public void initTest() {
        subsidieaanvraag = createEntity(em);
    }

    @Test
    @Transactional
    void createSubsidieaanvraag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Subsidieaanvraag
        var returnedSubsidieaanvraag = om.readValue(
            restSubsidieaanvraagMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidieaanvraag)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Subsidieaanvraag.class
        );

        // Validate the Subsidieaanvraag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubsidieaanvraagUpdatableFieldsEquals(returnedSubsidieaanvraag, getPersistedSubsidieaanvraag(returnedSubsidieaanvraag));
    }

    @Test
    @Transactional
    void createSubsidieaanvraagWithExistingId() throws Exception {
        // Create the Subsidieaanvraag with an existing ID
        subsidieaanvraag.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubsidieaanvraagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidieaanvraag)))
            .andExpect(status().isBadRequest());

        // Validate the Subsidieaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubsidieaanvraags() throws Exception {
        // Initialize the database
        subsidieaanvraagRepository.saveAndFlush(subsidieaanvraag);

        // Get all the subsidieaanvraagList
        restSubsidieaanvraagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsidieaanvraag.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangevraagdbedrag").value(hasItem(sameNumber(DEFAULT_AANGEVRAAGDBEDRAG))))
            .andExpect(jsonPath("$.[*].datumindiening").value(hasItem(DEFAULT_DATUMINDIENING.toString())))
            .andExpect(jsonPath("$.[*].kenmerk").value(hasItem(DEFAULT_KENMERK)))
            .andExpect(jsonPath("$.[*].ontvangstbevestiging").value(hasItem(DEFAULT_ONTVANGSTBEVESTIGING.toString())))
            .andExpect(jsonPath("$.[*].verwachtebeschikking").value(hasItem(DEFAULT_VERWACHTEBESCHIKKING.toString())));
    }

    @Test
    @Transactional
    void getSubsidieaanvraag() throws Exception {
        // Initialize the database
        subsidieaanvraagRepository.saveAndFlush(subsidieaanvraag);

        // Get the subsidieaanvraag
        restSubsidieaanvraagMockMvc
            .perform(get(ENTITY_API_URL_ID, subsidieaanvraag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subsidieaanvraag.getId().intValue()))
            .andExpect(jsonPath("$.aangevraagdbedrag").value(sameNumber(DEFAULT_AANGEVRAAGDBEDRAG)))
            .andExpect(jsonPath("$.datumindiening").value(DEFAULT_DATUMINDIENING.toString()))
            .andExpect(jsonPath("$.kenmerk").value(DEFAULT_KENMERK))
            .andExpect(jsonPath("$.ontvangstbevestiging").value(DEFAULT_ONTVANGSTBEVESTIGING.toString()))
            .andExpect(jsonPath("$.verwachtebeschikking").value(DEFAULT_VERWACHTEBESCHIKKING.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSubsidieaanvraag() throws Exception {
        // Get the subsidieaanvraag
        restSubsidieaanvraagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubsidieaanvraag() throws Exception {
        // Initialize the database
        subsidieaanvraagRepository.saveAndFlush(subsidieaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidieaanvraag
        Subsidieaanvraag updatedSubsidieaanvraag = subsidieaanvraagRepository.findById(subsidieaanvraag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubsidieaanvraag are not directly saved in db
        em.detach(updatedSubsidieaanvraag);
        updatedSubsidieaanvraag
            .aangevraagdbedrag(UPDATED_AANGEVRAAGDBEDRAG)
            .datumindiening(UPDATED_DATUMINDIENING)
            .kenmerk(UPDATED_KENMERK)
            .ontvangstbevestiging(UPDATED_ONTVANGSTBEVESTIGING)
            .verwachtebeschikking(UPDATED_VERWACHTEBESCHIKKING);

        restSubsidieaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubsidieaanvraag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubsidieaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Subsidieaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubsidieaanvraagToMatchAllProperties(updatedSubsidieaanvraag);
    }

    @Test
    @Transactional
    void putNonExistingSubsidieaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieaanvraag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidieaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subsidieaanvraag.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidieaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidieaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubsidieaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieaanvraagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidieaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidieaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubsidieaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieaanvraagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidieaanvraag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidieaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubsidieaanvraagWithPatch() throws Exception {
        // Initialize the database
        subsidieaanvraagRepository.saveAndFlush(subsidieaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidieaanvraag using partial update
        Subsidieaanvraag partialUpdatedSubsidieaanvraag = new Subsidieaanvraag();
        partialUpdatedSubsidieaanvraag.setId(subsidieaanvraag.getId());

        partialUpdatedSubsidieaanvraag.verwachtebeschikking(UPDATED_VERWACHTEBESCHIKKING);

        restSubsidieaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidieaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidieaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Subsidieaanvraag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidieaanvraagUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubsidieaanvraag, subsidieaanvraag),
            getPersistedSubsidieaanvraag(subsidieaanvraag)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubsidieaanvraagWithPatch() throws Exception {
        // Initialize the database
        subsidieaanvraagRepository.saveAndFlush(subsidieaanvraag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidieaanvraag using partial update
        Subsidieaanvraag partialUpdatedSubsidieaanvraag = new Subsidieaanvraag();
        partialUpdatedSubsidieaanvraag.setId(subsidieaanvraag.getId());

        partialUpdatedSubsidieaanvraag
            .aangevraagdbedrag(UPDATED_AANGEVRAAGDBEDRAG)
            .datumindiening(UPDATED_DATUMINDIENING)
            .kenmerk(UPDATED_KENMERK)
            .ontvangstbevestiging(UPDATED_ONTVANGSTBEVESTIGING)
            .verwachtebeschikking(UPDATED_VERWACHTEBESCHIKKING);

        restSubsidieaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidieaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidieaanvraag))
            )
            .andExpect(status().isOk());

        // Validate the Subsidieaanvraag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidieaanvraagUpdatableFieldsEquals(
            partialUpdatedSubsidieaanvraag,
            getPersistedSubsidieaanvraag(partialUpdatedSubsidieaanvraag)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSubsidieaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieaanvraag.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidieaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subsidieaanvraag.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidieaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidieaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubsidieaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieaanvraagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidieaanvraag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidieaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubsidieaanvraag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidieaanvraag.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidieaanvraagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subsidieaanvraag)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidieaanvraag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubsidieaanvraag() throws Exception {
        // Initialize the database
        subsidieaanvraagRepository.saveAndFlush(subsidieaanvraag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subsidieaanvraag
        restSubsidieaanvraagMockMvc
            .perform(delete(ENTITY_API_URL_ID, subsidieaanvraag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subsidieaanvraagRepository.count();
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

    protected Subsidieaanvraag getPersistedSubsidieaanvraag(Subsidieaanvraag subsidieaanvraag) {
        return subsidieaanvraagRepository.findById(subsidieaanvraag.getId()).orElseThrow();
    }

    protected void assertPersistedSubsidieaanvraagToMatchAllProperties(Subsidieaanvraag expectedSubsidieaanvraag) {
        assertSubsidieaanvraagAllPropertiesEquals(expectedSubsidieaanvraag, getPersistedSubsidieaanvraag(expectedSubsidieaanvraag));
    }

    protected void assertPersistedSubsidieaanvraagToMatchUpdatableProperties(Subsidieaanvraag expectedSubsidieaanvraag) {
        assertSubsidieaanvraagAllUpdatablePropertiesEquals(
            expectedSubsidieaanvraag,
            getPersistedSubsidieaanvraag(expectedSubsidieaanvraag)
        );
    }
}
