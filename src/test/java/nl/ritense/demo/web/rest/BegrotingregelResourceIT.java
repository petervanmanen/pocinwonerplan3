package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BegrotingregelAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Begrotingregel;
import nl.ritense.demo.repository.BegrotingregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BegrotingregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BegrotingregelResourceIT {

    private static final String DEFAULT_BATENLASTEN = "AAAAAAAAAA";
    private static final String UPDATED_BATENLASTEN = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final String DEFAULT_SOORTREGEL = "AAAAAAAAAA";
    private static final String UPDATED_SOORTREGEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/begrotingregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BegrotingregelRepository begrotingregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBegrotingregelMockMvc;

    private Begrotingregel begrotingregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Begrotingregel createEntity(EntityManager em) {
        Begrotingregel begrotingregel = new Begrotingregel()
            .batenlasten(DEFAULT_BATENLASTEN)
            .bedrag(DEFAULT_BEDRAG)
            .soortregel(DEFAULT_SOORTREGEL);
        return begrotingregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Begrotingregel createUpdatedEntity(EntityManager em) {
        Begrotingregel begrotingregel = new Begrotingregel()
            .batenlasten(UPDATED_BATENLASTEN)
            .bedrag(UPDATED_BEDRAG)
            .soortregel(UPDATED_SOORTREGEL);
        return begrotingregel;
    }

    @BeforeEach
    public void initTest() {
        begrotingregel = createEntity(em);
    }

    @Test
    @Transactional
    void createBegrotingregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Begrotingregel
        var returnedBegrotingregel = om.readValue(
            restBegrotingregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begrotingregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Begrotingregel.class
        );

        // Validate the Begrotingregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBegrotingregelUpdatableFieldsEquals(returnedBegrotingregel, getPersistedBegrotingregel(returnedBegrotingregel));
    }

    @Test
    @Transactional
    void createBegrotingregelWithExistingId() throws Exception {
        // Create the Begrotingregel with an existing ID
        begrotingregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBegrotingregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begrotingregel)))
            .andExpect(status().isBadRequest());

        // Validate the Begrotingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBegrotingregels() throws Exception {
        // Initialize the database
        begrotingregelRepository.saveAndFlush(begrotingregel);

        // Get all the begrotingregelList
        restBegrotingregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(begrotingregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].batenlasten").value(hasItem(DEFAULT_BATENLASTEN)))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].soortregel").value(hasItem(DEFAULT_SOORTREGEL)));
    }

    @Test
    @Transactional
    void getBegrotingregel() throws Exception {
        // Initialize the database
        begrotingregelRepository.saveAndFlush(begrotingregel);

        // Get the begrotingregel
        restBegrotingregelMockMvc
            .perform(get(ENTITY_API_URL_ID, begrotingregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(begrotingregel.getId().intValue()))
            .andExpect(jsonPath("$.batenlasten").value(DEFAULT_BATENLASTEN))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.soortregel").value(DEFAULT_SOORTREGEL));
    }

    @Test
    @Transactional
    void getNonExistingBegrotingregel() throws Exception {
        // Get the begrotingregel
        restBegrotingregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBegrotingregel() throws Exception {
        // Initialize the database
        begrotingregelRepository.saveAndFlush(begrotingregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begrotingregel
        Begrotingregel updatedBegrotingregel = begrotingregelRepository.findById(begrotingregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBegrotingregel are not directly saved in db
        em.detach(updatedBegrotingregel);
        updatedBegrotingregel.batenlasten(UPDATED_BATENLASTEN).bedrag(UPDATED_BEDRAG).soortregel(UPDATED_SOORTREGEL);

        restBegrotingregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBegrotingregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBegrotingregel))
            )
            .andExpect(status().isOk());

        // Validate the Begrotingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBegrotingregelToMatchAllProperties(updatedBegrotingregel);
    }

    @Test
    @Transactional
    void putNonExistingBegrotingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begrotingregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBegrotingregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, begrotingregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(begrotingregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begrotingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBegrotingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begrotingregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegrotingregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(begrotingregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begrotingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBegrotingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begrotingregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegrotingregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(begrotingregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Begrotingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBegrotingregelWithPatch() throws Exception {
        // Initialize the database
        begrotingregelRepository.saveAndFlush(begrotingregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begrotingregel using partial update
        Begrotingregel partialUpdatedBegrotingregel = new Begrotingregel();
        partialUpdatedBegrotingregel.setId(begrotingregel.getId());

        restBegrotingregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBegrotingregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBegrotingregel))
            )
            .andExpect(status().isOk());

        // Validate the Begrotingregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBegrotingregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBegrotingregel, begrotingregel),
            getPersistedBegrotingregel(begrotingregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateBegrotingregelWithPatch() throws Exception {
        // Initialize the database
        begrotingregelRepository.saveAndFlush(begrotingregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the begrotingregel using partial update
        Begrotingregel partialUpdatedBegrotingregel = new Begrotingregel();
        partialUpdatedBegrotingregel.setId(begrotingregel.getId());

        partialUpdatedBegrotingregel.batenlasten(UPDATED_BATENLASTEN).bedrag(UPDATED_BEDRAG).soortregel(UPDATED_SOORTREGEL);

        restBegrotingregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBegrotingregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBegrotingregel))
            )
            .andExpect(status().isOk());

        // Validate the Begrotingregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBegrotingregelUpdatableFieldsEquals(partialUpdatedBegrotingregel, getPersistedBegrotingregel(partialUpdatedBegrotingregel));
    }

    @Test
    @Transactional
    void patchNonExistingBegrotingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begrotingregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBegrotingregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, begrotingregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(begrotingregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begrotingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBegrotingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begrotingregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegrotingregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(begrotingregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Begrotingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBegrotingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        begrotingregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBegrotingregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(begrotingregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Begrotingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBegrotingregel() throws Exception {
        // Initialize the database
        begrotingregelRepository.saveAndFlush(begrotingregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the begrotingregel
        restBegrotingregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, begrotingregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return begrotingregelRepository.count();
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

    protected Begrotingregel getPersistedBegrotingregel(Begrotingregel begrotingregel) {
        return begrotingregelRepository.findById(begrotingregel.getId()).orElseThrow();
    }

    protected void assertPersistedBegrotingregelToMatchAllProperties(Begrotingregel expectedBegrotingregel) {
        assertBegrotingregelAllPropertiesEquals(expectedBegrotingregel, getPersistedBegrotingregel(expectedBegrotingregel));
    }

    protected void assertPersistedBegrotingregelToMatchUpdatableProperties(Begrotingregel expectedBegrotingregel) {
        assertBegrotingregelAllUpdatablePropertiesEquals(expectedBegrotingregel, getPersistedBegrotingregel(expectedBegrotingregel));
    }
}
