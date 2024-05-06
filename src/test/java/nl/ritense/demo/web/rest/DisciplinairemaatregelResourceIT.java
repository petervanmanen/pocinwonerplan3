package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DisciplinairemaatregelAsserts.*;
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
import nl.ritense.demo.domain.Disciplinairemaatregel;
import nl.ritense.demo.repository.DisciplinairemaatregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DisciplinairemaatregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DisciplinairemaatregelResourceIT {

    private static final LocalDate DEFAULT_DATUMGECONSTATEERD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGECONSTATEERD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMOPGELEGD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPGELEGD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_REDEN = "AAAAAAAAAA";
    private static final String UPDATED_REDEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/disciplinairemaatregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DisciplinairemaatregelRepository disciplinairemaatregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisciplinairemaatregelMockMvc;

    private Disciplinairemaatregel disciplinairemaatregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplinairemaatregel createEntity(EntityManager em) {
        Disciplinairemaatregel disciplinairemaatregel = new Disciplinairemaatregel()
            .datumgeconstateerd(DEFAULT_DATUMGECONSTATEERD)
            .datumopgelegd(DEFAULT_DATUMOPGELEGD)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .reden(DEFAULT_REDEN);
        return disciplinairemaatregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplinairemaatregel createUpdatedEntity(EntityManager em) {
        Disciplinairemaatregel disciplinairemaatregel = new Disciplinairemaatregel()
            .datumgeconstateerd(UPDATED_DATUMGECONSTATEERD)
            .datumopgelegd(UPDATED_DATUMOPGELEGD)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .reden(UPDATED_REDEN);
        return disciplinairemaatregel;
    }

    @BeforeEach
    public void initTest() {
        disciplinairemaatregel = createEntity(em);
    }

    @Test
    @Transactional
    void createDisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Disciplinairemaatregel
        var returnedDisciplinairemaatregel = om.readValue(
            restDisciplinairemaatregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disciplinairemaatregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Disciplinairemaatregel.class
        );

        // Validate the Disciplinairemaatregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDisciplinairemaatregelUpdatableFieldsEquals(
            returnedDisciplinairemaatregel,
            getPersistedDisciplinairemaatregel(returnedDisciplinairemaatregel)
        );
    }

    @Test
    @Transactional
    void createDisciplinairemaatregelWithExistingId() throws Exception {
        // Create the Disciplinairemaatregel with an existing ID
        disciplinairemaatregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplinairemaatregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disciplinairemaatregel)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDisciplinairemaatregels() throws Exception {
        // Initialize the database
        disciplinairemaatregelRepository.saveAndFlush(disciplinairemaatregel);

        // Get all the disciplinairemaatregelList
        restDisciplinairemaatregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplinairemaatregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumgeconstateerd").value(hasItem(DEFAULT_DATUMGECONSTATEERD.toString())))
            .andExpect(jsonPath("$.[*].datumopgelegd").value(hasItem(DEFAULT_DATUMOPGELEGD.toString())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].reden").value(hasItem(DEFAULT_REDEN)));
    }

    @Test
    @Transactional
    void getDisciplinairemaatregel() throws Exception {
        // Initialize the database
        disciplinairemaatregelRepository.saveAndFlush(disciplinairemaatregel);

        // Get the disciplinairemaatregel
        restDisciplinairemaatregelMockMvc
            .perform(get(ENTITY_API_URL_ID, disciplinairemaatregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disciplinairemaatregel.getId().intValue()))
            .andExpect(jsonPath("$.datumgeconstateerd").value(DEFAULT_DATUMGECONSTATEERD.toString()))
            .andExpect(jsonPath("$.datumopgelegd").value(DEFAULT_DATUMOPGELEGD.toString()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.reden").value(DEFAULT_REDEN));
    }

    @Test
    @Transactional
    void getNonExistingDisciplinairemaatregel() throws Exception {
        // Get the disciplinairemaatregel
        restDisciplinairemaatregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDisciplinairemaatregel() throws Exception {
        // Initialize the database
        disciplinairemaatregelRepository.saveAndFlush(disciplinairemaatregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disciplinairemaatregel
        Disciplinairemaatregel updatedDisciplinairemaatregel = disciplinairemaatregelRepository
            .findById(disciplinairemaatregel.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDisciplinairemaatregel are not directly saved in db
        em.detach(updatedDisciplinairemaatregel);
        updatedDisciplinairemaatregel
            .datumgeconstateerd(UPDATED_DATUMGECONSTATEERD)
            .datumopgelegd(UPDATED_DATUMOPGELEGD)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .reden(UPDATED_REDEN);

        restDisciplinairemaatregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDisciplinairemaatregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDisciplinairemaatregel))
            )
            .andExpect(status().isOk());

        // Validate the Disciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDisciplinairemaatregelToMatchAllProperties(updatedDisciplinairemaatregel);
    }

    @Test
    @Transactional
    void putNonExistingDisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinairemaatregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinairemaatregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disciplinairemaatregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(disciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinairemaatregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinairemaatregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(disciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinairemaatregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinairemaatregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disciplinairemaatregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDisciplinairemaatregelWithPatch() throws Exception {
        // Initialize the database
        disciplinairemaatregelRepository.saveAndFlush(disciplinairemaatregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disciplinairemaatregel using partial update
        Disciplinairemaatregel partialUpdatedDisciplinairemaatregel = new Disciplinairemaatregel();
        partialUpdatedDisciplinairemaatregel.setId(disciplinairemaatregel.getId());

        restDisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisciplinairemaatregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDisciplinairemaatregel))
            )
            .andExpect(status().isOk());

        // Validate the Disciplinairemaatregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDisciplinairemaatregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDisciplinairemaatregel, disciplinairemaatregel),
            getPersistedDisciplinairemaatregel(disciplinairemaatregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateDisciplinairemaatregelWithPatch() throws Exception {
        // Initialize the database
        disciplinairemaatregelRepository.saveAndFlush(disciplinairemaatregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disciplinairemaatregel using partial update
        Disciplinairemaatregel partialUpdatedDisciplinairemaatregel = new Disciplinairemaatregel();
        partialUpdatedDisciplinairemaatregel.setId(disciplinairemaatregel.getId());

        partialUpdatedDisciplinairemaatregel
            .datumgeconstateerd(UPDATED_DATUMGECONSTATEERD)
            .datumopgelegd(UPDATED_DATUMOPGELEGD)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .reden(UPDATED_REDEN);

        restDisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisciplinairemaatregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDisciplinairemaatregel))
            )
            .andExpect(status().isOk());

        // Validate the Disciplinairemaatregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDisciplinairemaatregelUpdatableFieldsEquals(
            partialUpdatedDisciplinairemaatregel,
            getPersistedDisciplinairemaatregel(partialUpdatedDisciplinairemaatregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinairemaatregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, disciplinairemaatregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(disciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinairemaatregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(disciplinairemaatregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDisciplinairemaatregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinairemaatregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinairemaatregelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(disciplinairemaatregel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disciplinairemaatregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDisciplinairemaatregel() throws Exception {
        // Initialize the database
        disciplinairemaatregelRepository.saveAndFlush(disciplinairemaatregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the disciplinairemaatregel
        restDisciplinairemaatregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, disciplinairemaatregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return disciplinairemaatregelRepository.count();
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

    protected Disciplinairemaatregel getPersistedDisciplinairemaatregel(Disciplinairemaatregel disciplinairemaatregel) {
        return disciplinairemaatregelRepository.findById(disciplinairemaatregel.getId()).orElseThrow();
    }

    protected void assertPersistedDisciplinairemaatregelToMatchAllProperties(Disciplinairemaatregel expectedDisciplinairemaatregel) {
        assertDisciplinairemaatregelAllPropertiesEquals(
            expectedDisciplinairemaatregel,
            getPersistedDisciplinairemaatregel(expectedDisciplinairemaatregel)
        );
    }

    protected void assertPersistedDisciplinairemaatregelToMatchUpdatableProperties(Disciplinairemaatregel expectedDisciplinairemaatregel) {
        assertDisciplinairemaatregelAllUpdatablePropertiesEquals(
            expectedDisciplinairemaatregel,
            getPersistedDisciplinairemaatregel(expectedDisciplinairemaatregel)
        );
    }
}
