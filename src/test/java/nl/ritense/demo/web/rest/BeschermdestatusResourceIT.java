package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeschermdestatusAsserts.*;
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
import nl.ritense.demo.domain.Beschermdestatus;
import nl.ritense.demo.repository.BeschermdestatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeschermdestatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeschermdestatusResourceIT {

    private static final String DEFAULT_BRONNEN = "AAAAAAAAAA";
    private static final String UPDATED_BRONNEN = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEX = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEX = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMINSCHRIJVINGREGISTER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINSCHRIJVINGREGISTER = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEMEENTELIJKMONUMENTCODE = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTELIJKMONUMENTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_GEZICHTSCODE = "AAAAAAAAAA";
    private static final String UPDATED_GEZICHTSCODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_RIJKSMONUMENTCODE = "AAAAAAAAAA";
    private static final String UPDATED_RIJKSMONUMENTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beschermdestatuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeschermdestatusRepository beschermdestatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeschermdestatusMockMvc;

    private Beschermdestatus beschermdestatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschermdestatus createEntity(EntityManager em) {
        Beschermdestatus beschermdestatus = new Beschermdestatus()
            .bronnen(DEFAULT_BRONNEN)
            .complex(DEFAULT_COMPLEX)
            .datuminschrijvingregister(DEFAULT_DATUMINSCHRIJVINGREGISTER)
            .gemeentelijkmonumentcode(DEFAULT_GEMEENTELIJKMONUMENTCODE)
            .gezichtscode(DEFAULT_GEZICHTSCODE)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .rijksmonumentcode(DEFAULT_RIJKSMONUMENTCODE)
            .type(DEFAULT_TYPE);
        return beschermdestatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beschermdestatus createUpdatedEntity(EntityManager em) {
        Beschermdestatus beschermdestatus = new Beschermdestatus()
            .bronnen(UPDATED_BRONNEN)
            .complex(UPDATED_COMPLEX)
            .datuminschrijvingregister(UPDATED_DATUMINSCHRIJVINGREGISTER)
            .gemeentelijkmonumentcode(UPDATED_GEMEENTELIJKMONUMENTCODE)
            .gezichtscode(UPDATED_GEZICHTSCODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .rijksmonumentcode(UPDATED_RIJKSMONUMENTCODE)
            .type(UPDATED_TYPE);
        return beschermdestatus;
    }

    @BeforeEach
    public void initTest() {
        beschermdestatus = createEntity(em);
    }

    @Test
    @Transactional
    void createBeschermdestatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beschermdestatus
        var returnedBeschermdestatus = om.readValue(
            restBeschermdestatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschermdestatus)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beschermdestatus.class
        );

        // Validate the Beschermdestatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeschermdestatusUpdatableFieldsEquals(returnedBeschermdestatus, getPersistedBeschermdestatus(returnedBeschermdestatus));
    }

    @Test
    @Transactional
    void createBeschermdestatusWithExistingId() throws Exception {
        // Create the Beschermdestatus with an existing ID
        beschermdestatus.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeschermdestatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschermdestatus)))
            .andExpect(status().isBadRequest());

        // Validate the Beschermdestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeschermdestatuses() throws Exception {
        // Initialize the database
        beschermdestatusRepository.saveAndFlush(beschermdestatus);

        // Get all the beschermdestatusList
        restBeschermdestatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beschermdestatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].bronnen").value(hasItem(DEFAULT_BRONNEN)))
            .andExpect(jsonPath("$.[*].complex").value(hasItem(DEFAULT_COMPLEX)))
            .andExpect(jsonPath("$.[*].datuminschrijvingregister").value(hasItem(DEFAULT_DATUMINSCHRIJVINGREGISTER.toString())))
            .andExpect(jsonPath("$.[*].gemeentelijkmonumentcode").value(hasItem(DEFAULT_GEMEENTELIJKMONUMENTCODE)))
            .andExpect(jsonPath("$.[*].gezichtscode").value(hasItem(DEFAULT_GEZICHTSCODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].rijksmonumentcode").value(hasItem(DEFAULT_RIJKSMONUMENTCODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getBeschermdestatus() throws Exception {
        // Initialize the database
        beschermdestatusRepository.saveAndFlush(beschermdestatus);

        // Get the beschermdestatus
        restBeschermdestatusMockMvc
            .perform(get(ENTITY_API_URL_ID, beschermdestatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beschermdestatus.getId().intValue()))
            .andExpect(jsonPath("$.bronnen").value(DEFAULT_BRONNEN))
            .andExpect(jsonPath("$.complex").value(DEFAULT_COMPLEX))
            .andExpect(jsonPath("$.datuminschrijvingregister").value(DEFAULT_DATUMINSCHRIJVINGREGISTER.toString()))
            .andExpect(jsonPath("$.gemeentelijkmonumentcode").value(DEFAULT_GEMEENTELIJKMONUMENTCODE))
            .andExpect(jsonPath("$.gezichtscode").value(DEFAULT_GEZICHTSCODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.rijksmonumentcode").value(DEFAULT_RIJKSMONUMENTCODE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingBeschermdestatus() throws Exception {
        // Get the beschermdestatus
        restBeschermdestatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeschermdestatus() throws Exception {
        // Initialize the database
        beschermdestatusRepository.saveAndFlush(beschermdestatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschermdestatus
        Beschermdestatus updatedBeschermdestatus = beschermdestatusRepository.findById(beschermdestatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeschermdestatus are not directly saved in db
        em.detach(updatedBeschermdestatus);
        updatedBeschermdestatus
            .bronnen(UPDATED_BRONNEN)
            .complex(UPDATED_COMPLEX)
            .datuminschrijvingregister(UPDATED_DATUMINSCHRIJVINGREGISTER)
            .gemeentelijkmonumentcode(UPDATED_GEMEENTELIJKMONUMENTCODE)
            .gezichtscode(UPDATED_GEZICHTSCODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .rijksmonumentcode(UPDATED_RIJKSMONUMENTCODE)
            .type(UPDATED_TYPE);

        restBeschermdestatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeschermdestatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeschermdestatus))
            )
            .andExpect(status().isOk());

        // Validate the Beschermdestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeschermdestatusToMatchAllProperties(updatedBeschermdestatus);
    }

    @Test
    @Transactional
    void putNonExistingBeschermdestatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschermdestatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeschermdestatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beschermdestatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beschermdestatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschermdestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeschermdestatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschermdestatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschermdestatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beschermdestatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschermdestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeschermdestatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschermdestatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschermdestatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beschermdestatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beschermdestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeschermdestatusWithPatch() throws Exception {
        // Initialize the database
        beschermdestatusRepository.saveAndFlush(beschermdestatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschermdestatus using partial update
        Beschermdestatus partialUpdatedBeschermdestatus = new Beschermdestatus();
        partialUpdatedBeschermdestatus.setId(beschermdestatus.getId());

        partialUpdatedBeschermdestatus.complex(UPDATED_COMPLEX).opmerkingen(UPDATED_OPMERKINGEN).type(UPDATED_TYPE);

        restBeschermdestatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeschermdestatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeschermdestatus))
            )
            .andExpect(status().isOk());

        // Validate the Beschermdestatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeschermdestatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeschermdestatus, beschermdestatus),
            getPersistedBeschermdestatus(beschermdestatus)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeschermdestatusWithPatch() throws Exception {
        // Initialize the database
        beschermdestatusRepository.saveAndFlush(beschermdestatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beschermdestatus using partial update
        Beschermdestatus partialUpdatedBeschermdestatus = new Beschermdestatus();
        partialUpdatedBeschermdestatus.setId(beschermdestatus.getId());

        partialUpdatedBeschermdestatus
            .bronnen(UPDATED_BRONNEN)
            .complex(UPDATED_COMPLEX)
            .datuminschrijvingregister(UPDATED_DATUMINSCHRIJVINGREGISTER)
            .gemeentelijkmonumentcode(UPDATED_GEMEENTELIJKMONUMENTCODE)
            .gezichtscode(UPDATED_GEZICHTSCODE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .rijksmonumentcode(UPDATED_RIJKSMONUMENTCODE)
            .type(UPDATED_TYPE);

        restBeschermdestatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeschermdestatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeschermdestatus))
            )
            .andExpect(status().isOk());

        // Validate the Beschermdestatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeschermdestatusUpdatableFieldsEquals(
            partialUpdatedBeschermdestatus,
            getPersistedBeschermdestatus(partialUpdatedBeschermdestatus)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBeschermdestatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschermdestatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeschermdestatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beschermdestatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beschermdestatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschermdestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeschermdestatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschermdestatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschermdestatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beschermdestatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beschermdestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeschermdestatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beschermdestatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeschermdestatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beschermdestatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beschermdestatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeschermdestatus() throws Exception {
        // Initialize the database
        beschermdestatusRepository.saveAndFlush(beschermdestatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beschermdestatus
        restBeschermdestatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, beschermdestatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beschermdestatusRepository.count();
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

    protected Beschermdestatus getPersistedBeschermdestatus(Beschermdestatus beschermdestatus) {
        return beschermdestatusRepository.findById(beschermdestatus.getId()).orElseThrow();
    }

    protected void assertPersistedBeschermdestatusToMatchAllProperties(Beschermdestatus expectedBeschermdestatus) {
        assertBeschermdestatusAllPropertiesEquals(expectedBeschermdestatus, getPersistedBeschermdestatus(expectedBeschermdestatus));
    }

    protected void assertPersistedBeschermdestatusToMatchUpdatableProperties(Beschermdestatus expectedBeschermdestatus) {
        assertBeschermdestatusAllUpdatablePropertiesEquals(
            expectedBeschermdestatus,
            getPersistedBeschermdestatus(expectedBeschermdestatus)
        );
    }
}
