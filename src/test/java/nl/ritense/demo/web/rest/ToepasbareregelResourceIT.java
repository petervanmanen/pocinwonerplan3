package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ToepasbareregelAsserts.*;
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
import nl.ritense.demo.domain.Toepasbareregel;
import nl.ritense.demo.repository.ToepasbareregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ToepasbareregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ToepasbareregelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOMEIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMEIN = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTAANSLUITPUNT = "AAAAAAAAAA";
    private static final String UPDATED_SOORTAANSLUITPUNT = "BBBBBBBBBB";

    private static final String DEFAULT_TOESTEMMING = "AAAAAAAAAA";
    private static final String UPDATED_TOESTEMMING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/toepasbareregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ToepasbareregelRepository toepasbareregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restToepasbareregelMockMvc;

    private Toepasbareregel toepasbareregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toepasbareregel createEntity(EntityManager em) {
        Toepasbareregel toepasbareregel = new Toepasbareregel()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .domein(DEFAULT_DOMEIN)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .soortaansluitpunt(DEFAULT_SOORTAANSLUITPUNT)
            .toestemming(DEFAULT_TOESTEMMING);
        return toepasbareregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toepasbareregel createUpdatedEntity(EntityManager em) {
        Toepasbareregel toepasbareregel = new Toepasbareregel()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .domein(UPDATED_DOMEIN)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .soortaansluitpunt(UPDATED_SOORTAANSLUITPUNT)
            .toestemming(UPDATED_TOESTEMMING);
        return toepasbareregel;
    }

    @BeforeEach
    public void initTest() {
        toepasbareregel = createEntity(em);
    }

    @Test
    @Transactional
    void createToepasbareregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Toepasbareregel
        var returnedToepasbareregel = om.readValue(
            restToepasbareregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toepasbareregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Toepasbareregel.class
        );

        // Validate the Toepasbareregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertToepasbareregelUpdatableFieldsEquals(returnedToepasbareregel, getPersistedToepasbareregel(returnedToepasbareregel));
    }

    @Test
    @Transactional
    void createToepasbareregelWithExistingId() throws Exception {
        // Create the Toepasbareregel with an existing ID
        toepasbareregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restToepasbareregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toepasbareregel)))
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllToepasbareregels() throws Exception {
        // Initialize the database
        toepasbareregelRepository.saveAndFlush(toepasbareregel);

        // Get all the toepasbareregelList
        restToepasbareregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(toepasbareregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].domein").value(hasItem(DEFAULT_DOMEIN)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].soortaansluitpunt").value(hasItem(DEFAULT_SOORTAANSLUITPUNT)))
            .andExpect(jsonPath("$.[*].toestemming").value(hasItem(DEFAULT_TOESTEMMING)));
    }

    @Test
    @Transactional
    void getToepasbareregel() throws Exception {
        // Initialize the database
        toepasbareregelRepository.saveAndFlush(toepasbareregel);

        // Get the toepasbareregel
        restToepasbareregelMockMvc
            .perform(get(ENTITY_API_URL_ID, toepasbareregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(toepasbareregel.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.domein").value(DEFAULT_DOMEIN))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.soortaansluitpunt").value(DEFAULT_SOORTAANSLUITPUNT))
            .andExpect(jsonPath("$.toestemming").value(DEFAULT_TOESTEMMING));
    }

    @Test
    @Transactional
    void getNonExistingToepasbareregel() throws Exception {
        // Get the toepasbareregel
        restToepasbareregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingToepasbareregel() throws Exception {
        // Initialize the database
        toepasbareregelRepository.saveAndFlush(toepasbareregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toepasbareregel
        Toepasbareregel updatedToepasbareregel = toepasbareregelRepository.findById(toepasbareregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedToepasbareregel are not directly saved in db
        em.detach(updatedToepasbareregel);
        updatedToepasbareregel
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .domein(UPDATED_DOMEIN)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .soortaansluitpunt(UPDATED_SOORTAANSLUITPUNT)
            .toestemming(UPDATED_TOESTEMMING);

        restToepasbareregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedToepasbareregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedToepasbareregel))
            )
            .andExpect(status().isOk());

        // Validate the Toepasbareregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedToepasbareregelToMatchAllProperties(updatedToepasbareregel);
    }

    @Test
    @Transactional
    void putNonExistingToepasbareregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToepasbareregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, toepasbareregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(toepasbareregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchToepasbareregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToepasbareregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(toepasbareregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamToepasbareregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToepasbareregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toepasbareregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Toepasbareregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateToepasbareregelWithPatch() throws Exception {
        // Initialize the database
        toepasbareregelRepository.saveAndFlush(toepasbareregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toepasbareregel using partial update
        Toepasbareregel partialUpdatedToepasbareregel = new Toepasbareregel();
        partialUpdatedToepasbareregel.setId(toepasbareregel.getId());

        partialUpdatedToepasbareregel
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .soortaansluitpunt(UPDATED_SOORTAANSLUITPUNT);

        restToepasbareregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedToepasbareregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedToepasbareregel))
            )
            .andExpect(status().isOk());

        // Validate the Toepasbareregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertToepasbareregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedToepasbareregel, toepasbareregel),
            getPersistedToepasbareregel(toepasbareregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateToepasbareregelWithPatch() throws Exception {
        // Initialize the database
        toepasbareregelRepository.saveAndFlush(toepasbareregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toepasbareregel using partial update
        Toepasbareregel partialUpdatedToepasbareregel = new Toepasbareregel();
        partialUpdatedToepasbareregel.setId(toepasbareregel.getId());

        partialUpdatedToepasbareregel
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .domein(UPDATED_DOMEIN)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .soortaansluitpunt(UPDATED_SOORTAANSLUITPUNT)
            .toestemming(UPDATED_TOESTEMMING);

        restToepasbareregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedToepasbareregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedToepasbareregel))
            )
            .andExpect(status().isOk());

        // Validate the Toepasbareregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertToepasbareregelUpdatableFieldsEquals(
            partialUpdatedToepasbareregel,
            getPersistedToepasbareregel(partialUpdatedToepasbareregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingToepasbareregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToepasbareregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, toepasbareregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(toepasbareregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchToepasbareregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToepasbareregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(toepasbareregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamToepasbareregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToepasbareregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(toepasbareregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Toepasbareregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteToepasbareregel() throws Exception {
        // Initialize the database
        toepasbareregelRepository.saveAndFlush(toepasbareregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the toepasbareregel
        restToepasbareregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, toepasbareregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return toepasbareregelRepository.count();
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

    protected Toepasbareregel getPersistedToepasbareregel(Toepasbareregel toepasbareregel) {
        return toepasbareregelRepository.findById(toepasbareregel.getId()).orElseThrow();
    }

    protected void assertPersistedToepasbareregelToMatchAllProperties(Toepasbareregel expectedToepasbareregel) {
        assertToepasbareregelAllPropertiesEquals(expectedToepasbareregel, getPersistedToepasbareregel(expectedToepasbareregel));
    }

    protected void assertPersistedToepasbareregelToMatchUpdatableProperties(Toepasbareregel expectedToepasbareregel) {
        assertToepasbareregelAllUpdatablePropertiesEquals(expectedToepasbareregel, getPersistedToepasbareregel(expectedToepasbareregel));
    }
}
