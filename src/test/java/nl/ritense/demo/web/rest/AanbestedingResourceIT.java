package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanbestedingAsserts.*;
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
import nl.ritense.demo.domain.Aanbesteding;
import nl.ritense.demo.repository.AanbestedingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanbestedingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanbestedingResourceIT {

    private static final String DEFAULT_DATUMPUBLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMPUBLICATIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DIGITAAL = "AAAAAAAAAA";
    private static final String UPDATED_DIGITAAL = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDURE = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDURE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENTIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENTIENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_SCOREMAXIMAAL = "AAAAAAAAAA";
    private static final String UPDATED_SCOREMAXIMAAL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TENDERNEDKENMERK = "AAAAAAAAAA";
    private static final String UPDATED_TENDERNEDKENMERK = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VOLGENDESLUITING = "AAAAAAAAAA";
    private static final String UPDATED_VOLGENDESLUITING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aanbestedings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanbestedingRepository aanbestedingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanbestedingMockMvc;

    private Aanbesteding aanbesteding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanbesteding createEntity(EntityManager em) {
        Aanbesteding aanbesteding = new Aanbesteding()
            .datumpublicatie(DEFAULT_DATUMPUBLICATIE)
            .datumstart(DEFAULT_DATUMSTART)
            .digitaal(DEFAULT_DIGITAAL)
            .naam(DEFAULT_NAAM)
            .procedure(DEFAULT_PROCEDURE)
            .referentienummer(DEFAULT_REFERENTIENUMMER)
            .scoremaximaal(DEFAULT_SCOREMAXIMAAL)
            .status(DEFAULT_STATUS)
            .tendernedkenmerk(DEFAULT_TENDERNEDKENMERK)
            .type(DEFAULT_TYPE)
            .volgendesluiting(DEFAULT_VOLGENDESLUITING);
        return aanbesteding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanbesteding createUpdatedEntity(EntityManager em) {
        Aanbesteding aanbesteding = new Aanbesteding()
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumstart(UPDATED_DATUMSTART)
            .digitaal(UPDATED_DIGITAAL)
            .naam(UPDATED_NAAM)
            .procedure(UPDATED_PROCEDURE)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .scoremaximaal(UPDATED_SCOREMAXIMAAL)
            .status(UPDATED_STATUS)
            .tendernedkenmerk(UPDATED_TENDERNEDKENMERK)
            .type(UPDATED_TYPE)
            .volgendesluiting(UPDATED_VOLGENDESLUITING);
        return aanbesteding;
    }

    @BeforeEach
    public void initTest() {
        aanbesteding = createEntity(em);
    }

    @Test
    @Transactional
    void createAanbesteding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanbesteding
        var returnedAanbesteding = om.readValue(
            restAanbestedingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanbesteding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanbesteding.class
        );

        // Validate the Aanbesteding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanbestedingUpdatableFieldsEquals(returnedAanbesteding, getPersistedAanbesteding(returnedAanbesteding));
    }

    @Test
    @Transactional
    void createAanbestedingWithExistingId() throws Exception {
        // Create the Aanbesteding with an existing ID
        aanbesteding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanbestedingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanbesteding)))
            .andExpect(status().isBadRequest());

        // Validate the Aanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanbestedings() throws Exception {
        // Initialize the database
        aanbestedingRepository.saveAndFlush(aanbesteding);

        // Get all the aanbestedingList
        restAanbestedingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanbesteding.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumpublicatie").value(hasItem(DEFAULT_DATUMPUBLICATIE)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].digitaal").value(hasItem(DEFAULT_DIGITAAL)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].procedure").value(hasItem(DEFAULT_PROCEDURE)))
            .andExpect(jsonPath("$.[*].referentienummer").value(hasItem(DEFAULT_REFERENTIENUMMER)))
            .andExpect(jsonPath("$.[*].scoremaximaal").value(hasItem(DEFAULT_SCOREMAXIMAAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].tendernedkenmerk").value(hasItem(DEFAULT_TENDERNEDKENMERK)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].volgendesluiting").value(hasItem(DEFAULT_VOLGENDESLUITING)));
    }

    @Test
    @Transactional
    void getAanbesteding() throws Exception {
        // Initialize the database
        aanbestedingRepository.saveAndFlush(aanbesteding);

        // Get the aanbesteding
        restAanbestedingMockMvc
            .perform(get(ENTITY_API_URL_ID, aanbesteding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanbesteding.getId().intValue()))
            .andExpect(jsonPath("$.datumpublicatie").value(DEFAULT_DATUMPUBLICATIE))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.digitaal").value(DEFAULT_DIGITAAL))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.procedure").value(DEFAULT_PROCEDURE))
            .andExpect(jsonPath("$.referentienummer").value(DEFAULT_REFERENTIENUMMER))
            .andExpect(jsonPath("$.scoremaximaal").value(DEFAULT_SCOREMAXIMAAL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.tendernedkenmerk").value(DEFAULT_TENDERNEDKENMERK))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.volgendesluiting").value(DEFAULT_VOLGENDESLUITING));
    }

    @Test
    @Transactional
    void getNonExistingAanbesteding() throws Exception {
        // Get the aanbesteding
        restAanbestedingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanbesteding() throws Exception {
        // Initialize the database
        aanbestedingRepository.saveAndFlush(aanbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanbesteding
        Aanbesteding updatedAanbesteding = aanbestedingRepository.findById(aanbesteding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAanbesteding are not directly saved in db
        em.detach(updatedAanbesteding);
        updatedAanbesteding
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumstart(UPDATED_DATUMSTART)
            .digitaal(UPDATED_DIGITAAL)
            .naam(UPDATED_NAAM)
            .procedure(UPDATED_PROCEDURE)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .scoremaximaal(UPDATED_SCOREMAXIMAAL)
            .status(UPDATED_STATUS)
            .tendernedkenmerk(UPDATED_TENDERNEDKENMERK)
            .type(UPDATED_TYPE)
            .volgendesluiting(UPDATED_VOLGENDESLUITING);

        restAanbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanbesteding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Aanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanbestedingToMatchAllProperties(updatedAanbesteding);
    }

    @Test
    @Transactional
    void putNonExistingAanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbesteding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanbesteding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanbestedingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanbesteding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanbestedingWithPatch() throws Exception {
        // Initialize the database
        aanbestedingRepository.saveAndFlush(aanbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanbesteding using partial update
        Aanbesteding partialUpdatedAanbesteding = new Aanbesteding();
        partialUpdatedAanbesteding.setId(aanbesteding.getId());

        partialUpdatedAanbesteding
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .digitaal(UPDATED_DIGITAAL)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .status(UPDATED_STATUS);

        restAanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Aanbesteding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanbestedingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAanbesteding, aanbesteding),
            getPersistedAanbesteding(aanbesteding)
        );
    }

    @Test
    @Transactional
    void fullUpdateAanbestedingWithPatch() throws Exception {
        // Initialize the database
        aanbestedingRepository.saveAndFlush(aanbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanbesteding using partial update
        Aanbesteding partialUpdatedAanbesteding = new Aanbesteding();
        partialUpdatedAanbesteding.setId(aanbesteding.getId());

        partialUpdatedAanbesteding
            .datumpublicatie(UPDATED_DATUMPUBLICATIE)
            .datumstart(UPDATED_DATUMSTART)
            .digitaal(UPDATED_DIGITAAL)
            .naam(UPDATED_NAAM)
            .procedure(UPDATED_PROCEDURE)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .scoremaximaal(UPDATED_SCOREMAXIMAAL)
            .status(UPDATED_STATUS)
            .tendernedkenmerk(UPDATED_TENDERNEDKENMERK)
            .type(UPDATED_TYPE)
            .volgendesluiting(UPDATED_VOLGENDESLUITING);

        restAanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Aanbesteding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanbestedingUpdatableFieldsEquals(partialUpdatedAanbesteding, getPersistedAanbesteding(partialUpdatedAanbesteding));
    }

    @Test
    @Transactional
    void patchNonExistingAanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbesteding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanbestedingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanbesteding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanbesteding() throws Exception {
        // Initialize the database
        aanbestedingRepository.saveAndFlush(aanbesteding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanbesteding
        restAanbestedingMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanbesteding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanbestedingRepository.count();
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

    protected Aanbesteding getPersistedAanbesteding(Aanbesteding aanbesteding) {
        return aanbestedingRepository.findById(aanbesteding.getId()).orElseThrow();
    }

    protected void assertPersistedAanbestedingToMatchAllProperties(Aanbesteding expectedAanbesteding) {
        assertAanbestedingAllPropertiesEquals(expectedAanbesteding, getPersistedAanbesteding(expectedAanbesteding));
    }

    protected void assertPersistedAanbestedingToMatchUpdatableProperties(Aanbesteding expectedAanbesteding) {
        assertAanbestedingAllUpdatablePropertiesEquals(expectedAanbesteding, getPersistedAanbesteding(expectedAanbesteding));
    }
}
