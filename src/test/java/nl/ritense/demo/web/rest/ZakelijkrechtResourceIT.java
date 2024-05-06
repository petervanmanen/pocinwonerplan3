package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZakelijkrechtAsserts.*;
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
import nl.ritense.demo.domain.Zakelijkrecht;
import nl.ritense.demo.repository.ZakelijkrechtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZakelijkrechtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZakelijkrechtResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_KOSTEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_KOSTEN = new BigDecimal(2);

    private static final String DEFAULT_SOORT = "AAAAAAAAAA";
    private static final String UPDATED_SOORT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zakelijkrechts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZakelijkrechtRepository zakelijkrechtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZakelijkrechtMockMvc;

    private Zakelijkrecht zakelijkrecht;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zakelijkrecht createEntity(EntityManager em) {
        Zakelijkrecht zakelijkrecht = new Zakelijkrecht()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .kosten(DEFAULT_KOSTEN)
            .soort(DEFAULT_SOORT);
        return zakelijkrecht;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zakelijkrecht createUpdatedEntity(EntityManager em) {
        Zakelijkrecht zakelijkrecht = new Zakelijkrecht()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .kosten(UPDATED_KOSTEN)
            .soort(UPDATED_SOORT);
        return zakelijkrecht;
    }

    @BeforeEach
    public void initTest() {
        zakelijkrecht = createEntity(em);
    }

    @Test
    @Transactional
    void createZakelijkrecht() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Zakelijkrecht
        var returnedZakelijkrecht = om.readValue(
            restZakelijkrechtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zakelijkrecht)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Zakelijkrecht.class
        );

        // Validate the Zakelijkrecht in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZakelijkrechtUpdatableFieldsEquals(returnedZakelijkrecht, getPersistedZakelijkrecht(returnedZakelijkrecht));
    }

    @Test
    @Transactional
    void createZakelijkrechtWithExistingId() throws Exception {
        // Create the Zakelijkrecht with an existing ID
        zakelijkrecht.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZakelijkrechtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zakelijkrecht)))
            .andExpect(status().isBadRequest());

        // Validate the Zakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZakelijkrechts() throws Exception {
        // Initialize the database
        zakelijkrechtRepository.saveAndFlush(zakelijkrecht);

        // Get all the zakelijkrechtList
        restZakelijkrechtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zakelijkrecht.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].kosten").value(hasItem(sameNumber(DEFAULT_KOSTEN))))
            .andExpect(jsonPath("$.[*].soort").value(hasItem(DEFAULT_SOORT)));
    }

    @Test
    @Transactional
    void getZakelijkrecht() throws Exception {
        // Initialize the database
        zakelijkrechtRepository.saveAndFlush(zakelijkrecht);

        // Get the zakelijkrecht
        restZakelijkrechtMockMvc
            .perform(get(ENTITY_API_URL_ID, zakelijkrecht.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zakelijkrecht.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.kosten").value(sameNumber(DEFAULT_KOSTEN)))
            .andExpect(jsonPath("$.soort").value(DEFAULT_SOORT));
    }

    @Test
    @Transactional
    void getNonExistingZakelijkrecht() throws Exception {
        // Get the zakelijkrecht
        restZakelijkrechtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZakelijkrecht() throws Exception {
        // Initialize the database
        zakelijkrechtRepository.saveAndFlush(zakelijkrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zakelijkrecht
        Zakelijkrecht updatedZakelijkrecht = zakelijkrechtRepository.findById(zakelijkrecht.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedZakelijkrecht are not directly saved in db
        em.detach(updatedZakelijkrecht);
        updatedZakelijkrecht.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART).kosten(UPDATED_KOSTEN).soort(UPDATED_SOORT);

        restZakelijkrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZakelijkrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedZakelijkrecht))
            )
            .andExpect(status().isOk());

        // Validate the Zakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZakelijkrechtToMatchAllProperties(updatedZakelijkrecht);
    }

    @Test
    @Transactional
    void putNonExistingZakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zakelijkrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZakelijkrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zakelijkrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zakelijkrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zakelijkrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZakelijkrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zakelijkrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zakelijkrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZakelijkrechtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zakelijkrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZakelijkrechtWithPatch() throws Exception {
        // Initialize the database
        zakelijkrechtRepository.saveAndFlush(zakelijkrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zakelijkrecht using partial update
        Zakelijkrecht partialUpdatedZakelijkrecht = new Zakelijkrecht();
        partialUpdatedZakelijkrecht.setId(zakelijkrecht.getId());

        partialUpdatedZakelijkrecht.datumstart(UPDATED_DATUMSTART).soort(UPDATED_SOORT);

        restZakelijkrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZakelijkrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZakelijkrecht))
            )
            .andExpect(status().isOk());

        // Validate the Zakelijkrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZakelijkrechtUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedZakelijkrecht, zakelijkrecht),
            getPersistedZakelijkrecht(zakelijkrecht)
        );
    }

    @Test
    @Transactional
    void fullUpdateZakelijkrechtWithPatch() throws Exception {
        // Initialize the database
        zakelijkrechtRepository.saveAndFlush(zakelijkrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zakelijkrecht using partial update
        Zakelijkrecht partialUpdatedZakelijkrecht = new Zakelijkrecht();
        partialUpdatedZakelijkrecht.setId(zakelijkrecht.getId());

        partialUpdatedZakelijkrecht
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .kosten(UPDATED_KOSTEN)
            .soort(UPDATED_SOORT);

        restZakelijkrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZakelijkrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZakelijkrecht))
            )
            .andExpect(status().isOk());

        // Validate the Zakelijkrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZakelijkrechtUpdatableFieldsEquals(partialUpdatedZakelijkrecht, getPersistedZakelijkrecht(partialUpdatedZakelijkrecht));
    }

    @Test
    @Transactional
    void patchNonExistingZakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zakelijkrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZakelijkrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zakelijkrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zakelijkrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zakelijkrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZakelijkrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zakelijkrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zakelijkrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZakelijkrechtMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zakelijkrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZakelijkrecht() throws Exception {
        // Initialize the database
        zakelijkrechtRepository.saveAndFlush(zakelijkrecht);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zakelijkrecht
        restZakelijkrechtMockMvc
            .perform(delete(ENTITY_API_URL_ID, zakelijkrecht.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zakelijkrechtRepository.count();
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

    protected Zakelijkrecht getPersistedZakelijkrecht(Zakelijkrecht zakelijkrecht) {
        return zakelijkrechtRepository.findById(zakelijkrecht.getId()).orElseThrow();
    }

    protected void assertPersistedZakelijkrechtToMatchAllProperties(Zakelijkrecht expectedZakelijkrecht) {
        assertZakelijkrechtAllPropertiesEquals(expectedZakelijkrecht, getPersistedZakelijkrecht(expectedZakelijkrecht));
    }

    protected void assertPersistedZakelijkrechtToMatchUpdatableProperties(Zakelijkrecht expectedZakelijkrecht) {
        assertZakelijkrechtAllUpdatablePropertiesEquals(expectedZakelijkrecht, getPersistedZakelijkrecht(expectedZakelijkrecht));
    }
}
