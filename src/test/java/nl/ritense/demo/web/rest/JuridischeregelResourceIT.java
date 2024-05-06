package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.JuridischeregelAsserts.*;
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
import nl.ritense.demo.domain.Juridischeregel;
import nl.ritense.demo.repository.JuridischeregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JuridischeregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JuridischeregelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEKEND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEKEND = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINWERKING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINWERKING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_REGELTEKST = "AAAAAAAAAA";
    private static final String UPDATED_REGELTEKST = "BBBBBBBBBB";

    private static final String DEFAULT_THEMA = "AAAAAAAAAA";
    private static final String UPDATED_THEMA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/juridischeregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JuridischeregelRepository juridischeregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJuridischeregelMockMvc;

    private Juridischeregel juridischeregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Juridischeregel createEntity(EntityManager em) {
        Juridischeregel juridischeregel = new Juridischeregel()
            .datumbekend(DEFAULT_DATUMBEKEND)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datuminwerking(DEFAULT_DATUMINWERKING)
            .datumstart(DEFAULT_DATUMSTART)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .regeltekst(DEFAULT_REGELTEKST)
            .thema(DEFAULT_THEMA);
        return juridischeregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Juridischeregel createUpdatedEntity(EntityManager em) {
        Juridischeregel juridischeregel = new Juridischeregel()
            .datumbekend(UPDATED_DATUMBEKEND)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datuminwerking(UPDATED_DATUMINWERKING)
            .datumstart(UPDATED_DATUMSTART)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .regeltekst(UPDATED_REGELTEKST)
            .thema(UPDATED_THEMA);
        return juridischeregel;
    }

    @BeforeEach
    public void initTest() {
        juridischeregel = createEntity(em);
    }

    @Test
    @Transactional
    void createJuridischeregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Juridischeregel
        var returnedJuridischeregel = om.readValue(
            restJuridischeregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(juridischeregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Juridischeregel.class
        );

        // Validate the Juridischeregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertJuridischeregelUpdatableFieldsEquals(returnedJuridischeregel, getPersistedJuridischeregel(returnedJuridischeregel));
    }

    @Test
    @Transactional
    void createJuridischeregelWithExistingId() throws Exception {
        // Create the Juridischeregel with an existing ID
        juridischeregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJuridischeregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(juridischeregel)))
            .andExpect(status().isBadRequest());

        // Validate the Juridischeregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJuridischeregels() throws Exception {
        // Initialize the database
        juridischeregelRepository.saveAndFlush(juridischeregel);

        // Get all the juridischeregelList
        restJuridischeregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(juridischeregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbekend").value(hasItem(DEFAULT_DATUMBEKEND.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datuminwerking").value(hasItem(DEFAULT_DATUMINWERKING.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].regeltekst").value(hasItem(DEFAULT_REGELTEKST)))
            .andExpect(jsonPath("$.[*].thema").value(hasItem(DEFAULT_THEMA)));
    }

    @Test
    @Transactional
    void getJuridischeregel() throws Exception {
        // Initialize the database
        juridischeregelRepository.saveAndFlush(juridischeregel);

        // Get the juridischeregel
        restJuridischeregelMockMvc
            .perform(get(ENTITY_API_URL_ID, juridischeregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(juridischeregel.getId().intValue()))
            .andExpect(jsonPath("$.datumbekend").value(DEFAULT_DATUMBEKEND.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datuminwerking").value(DEFAULT_DATUMINWERKING.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.regeltekst").value(DEFAULT_REGELTEKST))
            .andExpect(jsonPath("$.thema").value(DEFAULT_THEMA));
    }

    @Test
    @Transactional
    void getNonExistingJuridischeregel() throws Exception {
        // Get the juridischeregel
        restJuridischeregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJuridischeregel() throws Exception {
        // Initialize the database
        juridischeregelRepository.saveAndFlush(juridischeregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the juridischeregel
        Juridischeregel updatedJuridischeregel = juridischeregelRepository.findById(juridischeregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJuridischeregel are not directly saved in db
        em.detach(updatedJuridischeregel);
        updatedJuridischeregel
            .datumbekend(UPDATED_DATUMBEKEND)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datuminwerking(UPDATED_DATUMINWERKING)
            .datumstart(UPDATED_DATUMSTART)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .regeltekst(UPDATED_REGELTEKST)
            .thema(UPDATED_THEMA);

        restJuridischeregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJuridischeregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedJuridischeregel))
            )
            .andExpect(status().isOk());

        // Validate the Juridischeregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJuridischeregelToMatchAllProperties(updatedJuridischeregel);
    }

    @Test
    @Transactional
    void putNonExistingJuridischeregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        juridischeregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJuridischeregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, juridischeregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(juridischeregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Juridischeregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJuridischeregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        juridischeregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuridischeregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(juridischeregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Juridischeregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJuridischeregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        juridischeregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuridischeregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(juridischeregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Juridischeregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJuridischeregelWithPatch() throws Exception {
        // Initialize the database
        juridischeregelRepository.saveAndFlush(juridischeregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the juridischeregel using partial update
        Juridischeregel partialUpdatedJuridischeregel = new Juridischeregel();
        partialUpdatedJuridischeregel.setId(juridischeregel.getId());

        partialUpdatedJuridischeregel
            .datumbekend(UPDATED_DATUMBEKEND)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datuminwerking(UPDATED_DATUMINWERKING)
            .thema(UPDATED_THEMA);

        restJuridischeregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJuridischeregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJuridischeregel))
            )
            .andExpect(status().isOk());

        // Validate the Juridischeregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJuridischeregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedJuridischeregel, juridischeregel),
            getPersistedJuridischeregel(juridischeregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateJuridischeregelWithPatch() throws Exception {
        // Initialize the database
        juridischeregelRepository.saveAndFlush(juridischeregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the juridischeregel using partial update
        Juridischeregel partialUpdatedJuridischeregel = new Juridischeregel();
        partialUpdatedJuridischeregel.setId(juridischeregel.getId());

        partialUpdatedJuridischeregel
            .datumbekend(UPDATED_DATUMBEKEND)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datuminwerking(UPDATED_DATUMINWERKING)
            .datumstart(UPDATED_DATUMSTART)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .regeltekst(UPDATED_REGELTEKST)
            .thema(UPDATED_THEMA);

        restJuridischeregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJuridischeregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJuridischeregel))
            )
            .andExpect(status().isOk());

        // Validate the Juridischeregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJuridischeregelUpdatableFieldsEquals(
            partialUpdatedJuridischeregel,
            getPersistedJuridischeregel(partialUpdatedJuridischeregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingJuridischeregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        juridischeregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJuridischeregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, juridischeregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(juridischeregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Juridischeregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJuridischeregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        juridischeregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuridischeregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(juridischeregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Juridischeregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJuridischeregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        juridischeregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuridischeregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(juridischeregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Juridischeregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJuridischeregel() throws Exception {
        // Initialize the database
        juridischeregelRepository.saveAndFlush(juridischeregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the juridischeregel
        restJuridischeregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, juridischeregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return juridischeregelRepository.count();
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

    protected Juridischeregel getPersistedJuridischeregel(Juridischeregel juridischeregel) {
        return juridischeregelRepository.findById(juridischeregel.getId()).orElseThrow();
    }

    protected void assertPersistedJuridischeregelToMatchAllProperties(Juridischeregel expectedJuridischeregel) {
        assertJuridischeregelAllPropertiesEquals(expectedJuridischeregel, getPersistedJuridischeregel(expectedJuridischeregel));
    }

    protected void assertPersistedJuridischeregelToMatchUpdatableProperties(Juridischeregel expectedJuridischeregel) {
        assertJuridischeregelAllUpdatablePropertiesEquals(expectedJuridischeregel, getPersistedJuridischeregel(expectedJuridischeregel));
    }
}
