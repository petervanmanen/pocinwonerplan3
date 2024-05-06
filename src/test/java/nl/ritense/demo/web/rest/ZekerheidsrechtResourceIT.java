package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZekerheidsrechtAsserts.*;
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
import nl.ritense.demo.domain.Zekerheidsrecht;
import nl.ritense.demo.repository.ZekerheidsrechtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZekerheidsrechtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZekerheidsrechtResourceIT {

    private static final String DEFAULT_AANDEELINBETROKKENRECHT = "AAAAAAAAAA";
    private static final String UPDATED_AANDEELINBETROKKENRECHT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDERECHT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDERECHT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANGRECHT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANGRECHT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDENTIFICATIEZEKERHEIDSRECHT = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEZEKERHEIDSRECHT = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGBETROKKENRECHT = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGBETROKKENRECHT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEZEKERHEIDSRECHT = "AAAAAAAAAA";
    private static final String UPDATED_TYPEZEKERHEIDSRECHT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zekerheidsrechts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZekerheidsrechtRepository zekerheidsrechtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZekerheidsrechtMockMvc;

    private Zekerheidsrecht zekerheidsrecht;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zekerheidsrecht createEntity(EntityManager em) {
        Zekerheidsrecht zekerheidsrecht = new Zekerheidsrecht()
            .aandeelinbetrokkenrecht(DEFAULT_AANDEELINBETROKKENRECHT)
            .datumeinderecht(DEFAULT_DATUMEINDERECHT)
            .datumingangrecht(DEFAULT_DATUMINGANGRECHT)
            .identificatiezekerheidsrecht(DEFAULT_IDENTIFICATIEZEKERHEIDSRECHT)
            .omschrijvingbetrokkenrecht(DEFAULT_OMSCHRIJVINGBETROKKENRECHT)
            .typezekerheidsrecht(DEFAULT_TYPEZEKERHEIDSRECHT);
        return zekerheidsrecht;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zekerheidsrecht createUpdatedEntity(EntityManager em) {
        Zekerheidsrecht zekerheidsrecht = new Zekerheidsrecht()
            .aandeelinbetrokkenrecht(UPDATED_AANDEELINBETROKKENRECHT)
            .datumeinderecht(UPDATED_DATUMEINDERECHT)
            .datumingangrecht(UPDATED_DATUMINGANGRECHT)
            .identificatiezekerheidsrecht(UPDATED_IDENTIFICATIEZEKERHEIDSRECHT)
            .omschrijvingbetrokkenrecht(UPDATED_OMSCHRIJVINGBETROKKENRECHT)
            .typezekerheidsrecht(UPDATED_TYPEZEKERHEIDSRECHT);
        return zekerheidsrecht;
    }

    @BeforeEach
    public void initTest() {
        zekerheidsrecht = createEntity(em);
    }

    @Test
    @Transactional
    void createZekerheidsrecht() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Zekerheidsrecht
        var returnedZekerheidsrecht = om.readValue(
            restZekerheidsrechtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zekerheidsrecht)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Zekerheidsrecht.class
        );

        // Validate the Zekerheidsrecht in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZekerheidsrechtUpdatableFieldsEquals(returnedZekerheidsrecht, getPersistedZekerheidsrecht(returnedZekerheidsrecht));
    }

    @Test
    @Transactional
    void createZekerheidsrechtWithExistingId() throws Exception {
        // Create the Zekerheidsrecht with an existing ID
        zekerheidsrecht.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZekerheidsrechtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zekerheidsrecht)))
            .andExpect(status().isBadRequest());

        // Validate the Zekerheidsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZekerheidsrechts() throws Exception {
        // Initialize the database
        zekerheidsrechtRepository.saveAndFlush(zekerheidsrecht);

        // Get all the zekerheidsrechtList
        restZekerheidsrechtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zekerheidsrecht.getId().intValue())))
            .andExpect(jsonPath("$.[*].aandeelinbetrokkenrecht").value(hasItem(DEFAULT_AANDEELINBETROKKENRECHT)))
            .andExpect(jsonPath("$.[*].datumeinderecht").value(hasItem(DEFAULT_DATUMEINDERECHT.toString())))
            .andExpect(jsonPath("$.[*].datumingangrecht").value(hasItem(DEFAULT_DATUMINGANGRECHT.toString())))
            .andExpect(jsonPath("$.[*].identificatiezekerheidsrecht").value(hasItem(DEFAULT_IDENTIFICATIEZEKERHEIDSRECHT)))
            .andExpect(jsonPath("$.[*].omschrijvingbetrokkenrecht").value(hasItem(DEFAULT_OMSCHRIJVINGBETROKKENRECHT)))
            .andExpect(jsonPath("$.[*].typezekerheidsrecht").value(hasItem(DEFAULT_TYPEZEKERHEIDSRECHT)));
    }

    @Test
    @Transactional
    void getZekerheidsrecht() throws Exception {
        // Initialize the database
        zekerheidsrechtRepository.saveAndFlush(zekerheidsrecht);

        // Get the zekerheidsrecht
        restZekerheidsrechtMockMvc
            .perform(get(ENTITY_API_URL_ID, zekerheidsrecht.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zekerheidsrecht.getId().intValue()))
            .andExpect(jsonPath("$.aandeelinbetrokkenrecht").value(DEFAULT_AANDEELINBETROKKENRECHT))
            .andExpect(jsonPath("$.datumeinderecht").value(DEFAULT_DATUMEINDERECHT.toString()))
            .andExpect(jsonPath("$.datumingangrecht").value(DEFAULT_DATUMINGANGRECHT.toString()))
            .andExpect(jsonPath("$.identificatiezekerheidsrecht").value(DEFAULT_IDENTIFICATIEZEKERHEIDSRECHT))
            .andExpect(jsonPath("$.omschrijvingbetrokkenrecht").value(DEFAULT_OMSCHRIJVINGBETROKKENRECHT))
            .andExpect(jsonPath("$.typezekerheidsrecht").value(DEFAULT_TYPEZEKERHEIDSRECHT));
    }

    @Test
    @Transactional
    void getNonExistingZekerheidsrecht() throws Exception {
        // Get the zekerheidsrecht
        restZekerheidsrechtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZekerheidsrecht() throws Exception {
        // Initialize the database
        zekerheidsrechtRepository.saveAndFlush(zekerheidsrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zekerheidsrecht
        Zekerheidsrecht updatedZekerheidsrecht = zekerheidsrechtRepository.findById(zekerheidsrecht.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedZekerheidsrecht are not directly saved in db
        em.detach(updatedZekerheidsrecht);
        updatedZekerheidsrecht
            .aandeelinbetrokkenrecht(UPDATED_AANDEELINBETROKKENRECHT)
            .datumeinderecht(UPDATED_DATUMEINDERECHT)
            .datumingangrecht(UPDATED_DATUMINGANGRECHT)
            .identificatiezekerheidsrecht(UPDATED_IDENTIFICATIEZEKERHEIDSRECHT)
            .omschrijvingbetrokkenrecht(UPDATED_OMSCHRIJVINGBETROKKENRECHT)
            .typezekerheidsrecht(UPDATED_TYPEZEKERHEIDSRECHT);

        restZekerheidsrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZekerheidsrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedZekerheidsrecht))
            )
            .andExpect(status().isOk());

        // Validate the Zekerheidsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZekerheidsrechtToMatchAllProperties(updatedZekerheidsrecht);
    }

    @Test
    @Transactional
    void putNonExistingZekerheidsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zekerheidsrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZekerheidsrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zekerheidsrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zekerheidsrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zekerheidsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZekerheidsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zekerheidsrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZekerheidsrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zekerheidsrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zekerheidsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZekerheidsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zekerheidsrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZekerheidsrechtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zekerheidsrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zekerheidsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZekerheidsrechtWithPatch() throws Exception {
        // Initialize the database
        zekerheidsrechtRepository.saveAndFlush(zekerheidsrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zekerheidsrecht using partial update
        Zekerheidsrecht partialUpdatedZekerheidsrecht = new Zekerheidsrecht();
        partialUpdatedZekerheidsrecht.setId(zekerheidsrecht.getId());

        partialUpdatedZekerheidsrecht
            .aandeelinbetrokkenrecht(UPDATED_AANDEELINBETROKKENRECHT)
            .datumingangrecht(UPDATED_DATUMINGANGRECHT)
            .identificatiezekerheidsrecht(UPDATED_IDENTIFICATIEZEKERHEIDSRECHT)
            .omschrijvingbetrokkenrecht(UPDATED_OMSCHRIJVINGBETROKKENRECHT);

        restZekerheidsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZekerheidsrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZekerheidsrecht))
            )
            .andExpect(status().isOk());

        // Validate the Zekerheidsrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZekerheidsrechtUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedZekerheidsrecht, zekerheidsrecht),
            getPersistedZekerheidsrecht(zekerheidsrecht)
        );
    }

    @Test
    @Transactional
    void fullUpdateZekerheidsrechtWithPatch() throws Exception {
        // Initialize the database
        zekerheidsrechtRepository.saveAndFlush(zekerheidsrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zekerheidsrecht using partial update
        Zekerheidsrecht partialUpdatedZekerheidsrecht = new Zekerheidsrecht();
        partialUpdatedZekerheidsrecht.setId(zekerheidsrecht.getId());

        partialUpdatedZekerheidsrecht
            .aandeelinbetrokkenrecht(UPDATED_AANDEELINBETROKKENRECHT)
            .datumeinderecht(UPDATED_DATUMEINDERECHT)
            .datumingangrecht(UPDATED_DATUMINGANGRECHT)
            .identificatiezekerheidsrecht(UPDATED_IDENTIFICATIEZEKERHEIDSRECHT)
            .omschrijvingbetrokkenrecht(UPDATED_OMSCHRIJVINGBETROKKENRECHT)
            .typezekerheidsrecht(UPDATED_TYPEZEKERHEIDSRECHT);

        restZekerheidsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZekerheidsrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZekerheidsrecht))
            )
            .andExpect(status().isOk());

        // Validate the Zekerheidsrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZekerheidsrechtUpdatableFieldsEquals(
            partialUpdatedZekerheidsrecht,
            getPersistedZekerheidsrecht(partialUpdatedZekerheidsrecht)
        );
    }

    @Test
    @Transactional
    void patchNonExistingZekerheidsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zekerheidsrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZekerheidsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zekerheidsrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zekerheidsrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zekerheidsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZekerheidsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zekerheidsrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZekerheidsrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zekerheidsrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zekerheidsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZekerheidsrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zekerheidsrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZekerheidsrechtMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zekerheidsrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zekerheidsrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZekerheidsrecht() throws Exception {
        // Initialize the database
        zekerheidsrechtRepository.saveAndFlush(zekerheidsrecht);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zekerheidsrecht
        restZekerheidsrechtMockMvc
            .perform(delete(ENTITY_API_URL_ID, zekerheidsrecht.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zekerheidsrechtRepository.count();
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

    protected Zekerheidsrecht getPersistedZekerheidsrecht(Zekerheidsrecht zekerheidsrecht) {
        return zekerheidsrechtRepository.findById(zekerheidsrecht.getId()).orElseThrow();
    }

    protected void assertPersistedZekerheidsrechtToMatchAllProperties(Zekerheidsrecht expectedZekerheidsrecht) {
        assertZekerheidsrechtAllPropertiesEquals(expectedZekerheidsrecht, getPersistedZekerheidsrecht(expectedZekerheidsrecht));
    }

    protected void assertPersistedZekerheidsrechtToMatchUpdatableProperties(Zekerheidsrecht expectedZekerheidsrecht) {
        assertZekerheidsrechtAllUpdatablePropertiesEquals(expectedZekerheidsrecht, getPersistedZekerheidsrecht(expectedZekerheidsrecht));
    }
}
