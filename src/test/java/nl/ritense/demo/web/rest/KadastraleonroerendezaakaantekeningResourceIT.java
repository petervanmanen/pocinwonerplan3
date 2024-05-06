package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KadastraleonroerendezaakaantekeningAsserts.*;
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
import nl.ritense.demo.domain.Kadastraleonroerendezaakaantekening;
import nl.ritense.demo.repository.KadastraleonroerendezaakaantekeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KadastraleonroerendezaakaantekeningResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KadastraleonroerendezaakaantekeningResourceIT {

    private static final String DEFAULT_AARDAANTEKENINGKADASTRAALOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_AARDAANTEKENINGKADASTRAALOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINAANTEKENINGKADASTRAALOBJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINAANTEKENINGKADASTRAALOBJECT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEAANTEKENINGKADASTRAALOBJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEAANTEKENINGKADASTRAALOBJECT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KADASTERIDENTIFICATIEAANTEKENING = "AAAAAAAAAA";
    private static final String UPDATED_KADASTERIDENTIFICATIEAANTEKENING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kadastraleonroerendezaakaantekenings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KadastraleonroerendezaakaantekeningRepository kadastraleonroerendezaakaantekeningRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKadastraleonroerendezaakaantekeningMockMvc;

    private Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastraleonroerendezaakaantekening createEntity(EntityManager em) {
        Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening = new Kadastraleonroerendezaakaantekening()
            .aardaantekeningkadastraalobject(DEFAULT_AARDAANTEKENINGKADASTRAALOBJECT)
            .beschrijvingaantekeningkadastraalobject(DEFAULT_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT)
            .datumbeginaantekeningkadastraalobject(DEFAULT_DATUMBEGINAANTEKENINGKADASTRAALOBJECT)
            .datumeindeaantekeningkadastraalobject(DEFAULT_DATUMEINDEAANTEKENINGKADASTRAALOBJECT)
            .kadasteridentificatieaantekening(DEFAULT_KADASTERIDENTIFICATIEAANTEKENING);
        return kadastraleonroerendezaakaantekening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kadastraleonroerendezaakaantekening createUpdatedEntity(EntityManager em) {
        Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening = new Kadastraleonroerendezaakaantekening()
            .aardaantekeningkadastraalobject(UPDATED_AARDAANTEKENINGKADASTRAALOBJECT)
            .beschrijvingaantekeningkadastraalobject(UPDATED_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT)
            .datumbeginaantekeningkadastraalobject(UPDATED_DATUMBEGINAANTEKENINGKADASTRAALOBJECT)
            .datumeindeaantekeningkadastraalobject(UPDATED_DATUMEINDEAANTEKENINGKADASTRAALOBJECT)
            .kadasteridentificatieaantekening(UPDATED_KADASTERIDENTIFICATIEAANTEKENING);
        return kadastraleonroerendezaakaantekening;
    }

    @BeforeEach
    public void initTest() {
        kadastraleonroerendezaakaantekening = createEntity(em);
    }

    @Test
    @Transactional
    void createKadastraleonroerendezaakaantekening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kadastraleonroerendezaakaantekening
        var returnedKadastraleonroerendezaakaantekening = om.readValue(
            restKadastraleonroerendezaakaantekeningMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(kadastraleonroerendezaakaantekening))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kadastraleonroerendezaakaantekening.class
        );

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKadastraleonroerendezaakaantekeningUpdatableFieldsEquals(
            returnedKadastraleonroerendezaakaantekening,
            getPersistedKadastraleonroerendezaakaantekening(returnedKadastraleonroerendezaakaantekening)
        );
    }

    @Test
    @Transactional
    void createKadastraleonroerendezaakaantekeningWithExistingId() throws Exception {
        // Create the Kadastraleonroerendezaakaantekening with an existing ID
        kadastraleonroerendezaakaantekening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKadastraleonroerendezaakaantekenings() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakaantekeningRepository.saveAndFlush(kadastraleonroerendezaakaantekening);

        // Get all the kadastraleonroerendezaakaantekeningList
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kadastraleonroerendezaakaantekening.getId().intValue())))
            .andExpect(jsonPath("$.[*].aardaantekeningkadastraalobject").value(hasItem(DEFAULT_AARDAANTEKENINGKADASTRAALOBJECT)))
            .andExpect(
                jsonPath("$.[*].beschrijvingaantekeningkadastraalobject").value(hasItem(DEFAULT_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT))
            )
            .andExpect(
                jsonPath("$.[*].datumbeginaantekeningkadastraalobject").value(
                    hasItem(DEFAULT_DATUMBEGINAANTEKENINGKADASTRAALOBJECT.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindeaantekeningkadastraalobject").value(
                    hasItem(DEFAULT_DATUMEINDEAANTEKENINGKADASTRAALOBJECT.toString())
                )
            )
            .andExpect(jsonPath("$.[*].kadasteridentificatieaantekening").value(hasItem(DEFAULT_KADASTERIDENTIFICATIEAANTEKENING)));
    }

    @Test
    @Transactional
    void getKadastraleonroerendezaakaantekening() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakaantekeningRepository.saveAndFlush(kadastraleonroerendezaakaantekening);

        // Get the kadastraleonroerendezaakaantekening
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(get(ENTITY_API_URL_ID, kadastraleonroerendezaakaantekening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kadastraleonroerendezaakaantekening.getId().intValue()))
            .andExpect(jsonPath("$.aardaantekeningkadastraalobject").value(DEFAULT_AARDAANTEKENINGKADASTRAALOBJECT))
            .andExpect(jsonPath("$.beschrijvingaantekeningkadastraalobject").value(DEFAULT_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT))
            .andExpect(jsonPath("$.datumbeginaantekeningkadastraalobject").value(DEFAULT_DATUMBEGINAANTEKENINGKADASTRAALOBJECT.toString()))
            .andExpect(jsonPath("$.datumeindeaantekeningkadastraalobject").value(DEFAULT_DATUMEINDEAANTEKENINGKADASTRAALOBJECT.toString()))
            .andExpect(jsonPath("$.kadasteridentificatieaantekening").value(DEFAULT_KADASTERIDENTIFICATIEAANTEKENING));
    }

    @Test
    @Transactional
    void getNonExistingKadastraleonroerendezaakaantekening() throws Exception {
        // Get the kadastraleonroerendezaakaantekening
        restKadastraleonroerendezaakaantekeningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKadastraleonroerendezaakaantekening() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakaantekeningRepository.saveAndFlush(kadastraleonroerendezaakaantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraleonroerendezaakaantekening
        Kadastraleonroerendezaakaantekening updatedKadastraleonroerendezaakaantekening = kadastraleonroerendezaakaantekeningRepository
            .findById(kadastraleonroerendezaakaantekening.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedKadastraleonroerendezaakaantekening are not directly saved in db
        em.detach(updatedKadastraleonroerendezaakaantekening);
        updatedKadastraleonroerendezaakaantekening
            .aardaantekeningkadastraalobject(UPDATED_AARDAANTEKENINGKADASTRAALOBJECT)
            .beschrijvingaantekeningkadastraalobject(UPDATED_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT)
            .datumbeginaantekeningkadastraalobject(UPDATED_DATUMBEGINAANTEKENINGKADASTRAALOBJECT)
            .datumeindeaantekeningkadastraalobject(UPDATED_DATUMEINDEAANTEKENINGKADASTRAALOBJECT)
            .kadasteridentificatieaantekening(UPDATED_KADASTERIDENTIFICATIEAANTEKENING);

        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKadastraleonroerendezaakaantekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKadastraleonroerendezaakaantekeningToMatchAllProperties(updatedKadastraleonroerendezaakaantekening);
    }

    @Test
    @Transactional
    void putNonExistingKadastraleonroerendezaakaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaakaantekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kadastraleonroerendezaakaantekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKadastraleonroerendezaakaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaakaantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKadastraleonroerendezaakaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaakaantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKadastraleonroerendezaakaantekeningWithPatch() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakaantekeningRepository.saveAndFlush(kadastraleonroerendezaakaantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraleonroerendezaakaantekening using partial update
        Kadastraleonroerendezaakaantekening partialUpdatedKadastraleonroerendezaakaantekening = new Kadastraleonroerendezaakaantekening();
        partialUpdatedKadastraleonroerendezaakaantekening.setId(kadastraleonroerendezaakaantekening.getId());

        partialUpdatedKadastraleonroerendezaakaantekening
            .aardaantekeningkadastraalobject(UPDATED_AARDAANTEKENINGKADASTRAALOBJECT)
            .beschrijvingaantekeningkadastraalobject(UPDATED_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT)
            .datumeindeaantekeningkadastraalobject(UPDATED_DATUMEINDEAANTEKENINGKADASTRAALOBJECT)
            .kadasteridentificatieaantekening(UPDATED_KADASTERIDENTIFICATIEAANTEKENING);

        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastraleonroerendezaakaantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraleonroerendezaakaantekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastraleonroerendezaakaantekeningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKadastraleonroerendezaakaantekening, kadastraleonroerendezaakaantekening),
            getPersistedKadastraleonroerendezaakaantekening(kadastraleonroerendezaakaantekening)
        );
    }

    @Test
    @Transactional
    void fullUpdateKadastraleonroerendezaakaantekeningWithPatch() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakaantekeningRepository.saveAndFlush(kadastraleonroerendezaakaantekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kadastraleonroerendezaakaantekening using partial update
        Kadastraleonroerendezaakaantekening partialUpdatedKadastraleonroerendezaakaantekening = new Kadastraleonroerendezaakaantekening();
        partialUpdatedKadastraleonroerendezaakaantekening.setId(kadastraleonroerendezaakaantekening.getId());

        partialUpdatedKadastraleonroerendezaakaantekening
            .aardaantekeningkadastraalobject(UPDATED_AARDAANTEKENINGKADASTRAALOBJECT)
            .beschrijvingaantekeningkadastraalobject(UPDATED_BESCHRIJVINGAANTEKENINGKADASTRAALOBJECT)
            .datumbeginaantekeningkadastraalobject(UPDATED_DATUMBEGINAANTEKENINGKADASTRAALOBJECT)
            .datumeindeaantekeningkadastraalobject(UPDATED_DATUMEINDEAANTEKENINGKADASTRAALOBJECT)
            .kadasteridentificatieaantekening(UPDATED_KADASTERIDENTIFICATIEAANTEKENING);

        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKadastraleonroerendezaakaantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isOk());

        // Validate the Kadastraleonroerendezaakaantekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKadastraleonroerendezaakaantekeningUpdatableFieldsEquals(
            partialUpdatedKadastraleonroerendezaakaantekening,
            getPersistedKadastraleonroerendezaakaantekening(partialUpdatedKadastraleonroerendezaakaantekening)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKadastraleonroerendezaakaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaakaantekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kadastraleonroerendezaakaantekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKadastraleonroerendezaakaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaakaantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKadastraleonroerendezaakaantekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kadastraleonroerendezaakaantekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kadastraleonroerendezaakaantekening))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kadastraleonroerendezaakaantekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKadastraleonroerendezaakaantekening() throws Exception {
        // Initialize the database
        kadastraleonroerendezaakaantekeningRepository.saveAndFlush(kadastraleonroerendezaakaantekening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kadastraleonroerendezaakaantekening
        restKadastraleonroerendezaakaantekeningMockMvc
            .perform(delete(ENTITY_API_URL_ID, kadastraleonroerendezaakaantekening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kadastraleonroerendezaakaantekeningRepository.count();
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

    protected Kadastraleonroerendezaakaantekening getPersistedKadastraleonroerendezaakaantekening(
        Kadastraleonroerendezaakaantekening kadastraleonroerendezaakaantekening
    ) {
        return kadastraleonroerendezaakaantekeningRepository.findById(kadastraleonroerendezaakaantekening.getId()).orElseThrow();
    }

    protected void assertPersistedKadastraleonroerendezaakaantekeningToMatchAllProperties(
        Kadastraleonroerendezaakaantekening expectedKadastraleonroerendezaakaantekening
    ) {
        assertKadastraleonroerendezaakaantekeningAllPropertiesEquals(
            expectedKadastraleonroerendezaakaantekening,
            getPersistedKadastraleonroerendezaakaantekening(expectedKadastraleonroerendezaakaantekening)
        );
    }

    protected void assertPersistedKadastraleonroerendezaakaantekeningToMatchUpdatableProperties(
        Kadastraleonroerendezaakaantekening expectedKadastraleonroerendezaakaantekening
    ) {
        assertKadastraleonroerendezaakaantekeningAllUpdatablePropertiesEquals(
            expectedKadastraleonroerendezaakaantekening,
            getPersistedKadastraleonroerendezaakaantekening(expectedKadastraleonroerendezaakaantekening)
        );
    }
}
