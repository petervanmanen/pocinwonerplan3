package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TrajectAsserts.*;
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
import nl.ritense.demo.domain.Resultaat;
import nl.ritense.demo.domain.Traject;
import nl.ritense.demo.domain.Uitstroomreden;
import nl.ritense.demo.repository.TrajectRepository;
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
 * Integration tests for the {@link TrajectResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class TrajectResourceIT {

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTOEKENNING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTOEKENNING = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAAT = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAAT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trajects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrajectRepository trajectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrajectMockMvc;

    private Traject traject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traject createEntity(EntityManager em) {
        Traject traject = new Traject()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumtoekenning(DEFAULT_DATUMTOEKENNING)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .resultaat(DEFAULT_RESULTAAT);
        // Add required entity
        Resultaat resultaat;
        if (TestUtil.findAll(em, Resultaat.class).isEmpty()) {
            resultaat = ResultaatResourceIT.createEntity(em);
            em.persist(resultaat);
            em.flush();
        } else {
            resultaat = TestUtil.findAll(em, Resultaat.class).get(0);
        }
        traject.setHeeftresultaatResultaat(resultaat);
        // Add required entity
        Uitstroomreden uitstroomreden;
        if (TestUtil.findAll(em, Uitstroomreden.class).isEmpty()) {
            uitstroomreden = UitstroomredenResourceIT.createEntity(em);
            em.persist(uitstroomreden);
            em.flush();
        } else {
            uitstroomreden = TestUtil.findAll(em, Uitstroomreden.class).get(0);
        }
        traject.setHeeftuitstroomredenUitstroomreden(uitstroomreden);
        return traject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traject createUpdatedEntity(EntityManager em) {
        Traject traject = new Traject()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .resultaat(UPDATED_RESULTAAT);
        // Add required entity
        Resultaat resultaat;
        if (TestUtil.findAll(em, Resultaat.class).isEmpty()) {
            resultaat = ResultaatResourceIT.createUpdatedEntity(em);
            em.persist(resultaat);
            em.flush();
        } else {
            resultaat = TestUtil.findAll(em, Resultaat.class).get(0);
        }
        traject.setHeeftresultaatResultaat(resultaat);
        // Add required entity
        Uitstroomreden uitstroomreden;
        if (TestUtil.findAll(em, Uitstroomreden.class).isEmpty()) {
            uitstroomreden = UitstroomredenResourceIT.createUpdatedEntity(em);
            em.persist(uitstroomreden);
            em.flush();
        } else {
            uitstroomreden = TestUtil.findAll(em, Uitstroomreden.class).get(0);
        }
        traject.setHeeftuitstroomredenUitstroomreden(uitstroomreden);
        return traject;
    }

    @BeforeEach
    public void initTest() {
        traject = createEntity(em);
    }

    @Test
    @Transactional
    void createTraject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Traject
        var returnedTraject = om.readValue(
            restTrajectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(traject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Traject.class
        );

        // Validate the Traject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTrajectUpdatableFieldsEquals(returnedTraject, getPersistedTraject(returnedTraject));
    }

    @Test
    @Transactional
    void createTrajectWithExistingId() throws Exception {
        // Create the Traject with an existing ID
        traject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrajectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(traject)))
            .andExpect(status().isBadRequest());

        // Validate the Traject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrajects() throws Exception {
        // Initialize the database
        trajectRepository.saveAndFlush(traject);

        // Get all the trajectList
        restTrajectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traject.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].datumtoekenning").value(hasItem(DEFAULT_DATUMTOEKENNING)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].resultaat").value(hasItem(DEFAULT_RESULTAAT)));
    }

    @Test
    @Transactional
    void getTraject() throws Exception {
        // Initialize the database
        trajectRepository.saveAndFlush(traject);

        // Get the traject
        restTrajectMockMvc
            .perform(get(ENTITY_API_URL_ID, traject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(traject.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.datumtoekenning").value(DEFAULT_DATUMTOEKENNING))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.resultaat").value(DEFAULT_RESULTAAT));
    }

    @Test
    @Transactional
    void getNonExistingTraject() throws Exception {
        // Get the traject
        restTrajectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTraject() throws Exception {
        // Initialize the database
        trajectRepository.saveAndFlush(traject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the traject
        Traject updatedTraject = trajectRepository.findById(traject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTraject are not directly saved in db
        em.detach(updatedTraject);
        updatedTraject
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .resultaat(UPDATED_RESULTAAT);

        restTrajectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTraject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTraject))
            )
            .andExpect(status().isOk());

        // Validate the Traject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrajectToMatchAllProperties(updatedTraject);
    }

    @Test
    @Transactional
    void putNonExistingTraject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajectMockMvc
            .perform(put(ENTITY_API_URL_ID, traject.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(traject)))
            .andExpect(status().isBadRequest());

        // Validate the Traject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(traject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(traject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrajectWithPatch() throws Exception {
        // Initialize the database
        trajectRepository.saveAndFlush(traject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the traject using partial update
        Traject partialUpdatedTraject = new Traject();
        partialUpdatedTraject.setId(traject.getId());

        partialUpdatedTraject.datumtoekenning(UPDATED_DATUMTOEKENNING).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restTrajectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTraject))
            )
            .andExpect(status().isOk());

        // Validate the Traject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajectUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTraject, traject), getPersistedTraject(traject));
    }

    @Test
    @Transactional
    void fullUpdateTrajectWithPatch() throws Exception {
        // Initialize the database
        trajectRepository.saveAndFlush(traject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the traject using partial update
        Traject partialUpdatedTraject = new Traject();
        partialUpdatedTraject.setId(traject.getId());

        partialUpdatedTraject
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoekenning(UPDATED_DATUMTOEKENNING)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .resultaat(UPDATED_RESULTAAT);

        restTrajectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTraject))
            )
            .andExpect(status().isOk());

        // Validate the Traject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajectUpdatableFieldsEquals(partialUpdatedTraject, getPersistedTraject(partialUpdatedTraject));
    }

    @Test
    @Transactional
    void patchNonExistingTraject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, traject.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(traject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(traject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        traject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(traject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraject() throws Exception {
        // Initialize the database
        trajectRepository.saveAndFlush(traject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the traject
        restTrajectMockMvc
            .perform(delete(ENTITY_API_URL_ID, traject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trajectRepository.count();
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

    protected Traject getPersistedTraject(Traject traject) {
        return trajectRepository.findById(traject.getId()).orElseThrow();
    }

    protected void assertPersistedTrajectToMatchAllProperties(Traject expectedTraject) {
        assertTrajectAllPropertiesEquals(expectedTraject, getPersistedTraject(expectedTraject));
    }

    protected void assertPersistedTrajectToMatchUpdatableProperties(Traject expectedTraject) {
        assertTrajectAllUpdatablePropertiesEquals(expectedTraject, getPersistedTraject(expectedTraject));
    }
}
