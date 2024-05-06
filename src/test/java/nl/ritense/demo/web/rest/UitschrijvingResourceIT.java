package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitschrijvingAsserts.*;
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
import nl.ritense.demo.domain.Uitschrijving;
import nl.ritense.demo.repository.UitschrijvingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitschrijvingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitschrijvingResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DIPLOMABEHAALD = false;
    private static final Boolean UPDATED_DIPLOMABEHAALD = true;

    private static final String ENTITY_API_URL = "/api/uitschrijvings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitschrijvingRepository uitschrijvingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitschrijvingMockMvc;

    private Uitschrijving uitschrijving;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitschrijving createEntity(EntityManager em) {
        Uitschrijving uitschrijving = new Uitschrijving().datum(DEFAULT_DATUM).diplomabehaald(DEFAULT_DIPLOMABEHAALD);
        return uitschrijving;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitschrijving createUpdatedEntity(EntityManager em) {
        Uitschrijving uitschrijving = new Uitschrijving().datum(UPDATED_DATUM).diplomabehaald(UPDATED_DIPLOMABEHAALD);
        return uitschrijving;
    }

    @BeforeEach
    public void initTest() {
        uitschrijving = createEntity(em);
    }

    @Test
    @Transactional
    void createUitschrijving() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitschrijving
        var returnedUitschrijving = om.readValue(
            restUitschrijvingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitschrijving)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitschrijving.class
        );

        // Validate the Uitschrijving in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitschrijvingUpdatableFieldsEquals(returnedUitschrijving, getPersistedUitschrijving(returnedUitschrijving));
    }

    @Test
    @Transactional
    void createUitschrijvingWithExistingId() throws Exception {
        // Create the Uitschrijving with an existing ID
        uitschrijving.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitschrijvingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitschrijving)))
            .andExpect(status().isBadRequest());

        // Validate the Uitschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitschrijvings() throws Exception {
        // Initialize the database
        uitschrijvingRepository.saveAndFlush(uitschrijving);

        // Get all the uitschrijvingList
        restUitschrijvingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitschrijving.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].diplomabehaald").value(hasItem(DEFAULT_DIPLOMABEHAALD.booleanValue())));
    }

    @Test
    @Transactional
    void getUitschrijving() throws Exception {
        // Initialize the database
        uitschrijvingRepository.saveAndFlush(uitschrijving);

        // Get the uitschrijving
        restUitschrijvingMockMvc
            .perform(get(ENTITY_API_URL_ID, uitschrijving.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitschrijving.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.diplomabehaald").value(DEFAULT_DIPLOMABEHAALD.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingUitschrijving() throws Exception {
        // Get the uitschrijving
        restUitschrijvingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUitschrijving() throws Exception {
        // Initialize the database
        uitschrijvingRepository.saveAndFlush(uitschrijving);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitschrijving
        Uitschrijving updatedUitschrijving = uitschrijvingRepository.findById(uitschrijving.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUitschrijving are not directly saved in db
        em.detach(updatedUitschrijving);
        updatedUitschrijving.datum(UPDATED_DATUM).diplomabehaald(UPDATED_DIPLOMABEHAALD);

        restUitschrijvingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUitschrijving.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUitschrijving))
            )
            .andExpect(status().isOk());

        // Validate the Uitschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUitschrijvingToMatchAllProperties(updatedUitschrijving);
    }

    @Test
    @Transactional
    void putNonExistingUitschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitschrijving.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitschrijvingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uitschrijving.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitschrijving))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUitschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitschrijving.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitschrijvingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitschrijving))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUitschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitschrijving.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitschrijvingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitschrijving)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUitschrijvingWithPatch() throws Exception {
        // Initialize the database
        uitschrijvingRepository.saveAndFlush(uitschrijving);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitschrijving using partial update
        Uitschrijving partialUpdatedUitschrijving = new Uitschrijving();
        partialUpdatedUitschrijving.setId(uitschrijving.getId());

        partialUpdatedUitschrijving.diplomabehaald(UPDATED_DIPLOMABEHAALD);

        restUitschrijvingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitschrijving.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitschrijving))
            )
            .andExpect(status().isOk());

        // Validate the Uitschrijving in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitschrijvingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUitschrijving, uitschrijving),
            getPersistedUitschrijving(uitschrijving)
        );
    }

    @Test
    @Transactional
    void fullUpdateUitschrijvingWithPatch() throws Exception {
        // Initialize the database
        uitschrijvingRepository.saveAndFlush(uitschrijving);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitschrijving using partial update
        Uitschrijving partialUpdatedUitschrijving = new Uitschrijving();
        partialUpdatedUitschrijving.setId(uitschrijving.getId());

        partialUpdatedUitschrijving.datum(UPDATED_DATUM).diplomabehaald(UPDATED_DIPLOMABEHAALD);

        restUitschrijvingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitschrijving.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitschrijving))
            )
            .andExpect(status().isOk());

        // Validate the Uitschrijving in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitschrijvingUpdatableFieldsEquals(partialUpdatedUitschrijving, getPersistedUitschrijving(partialUpdatedUitschrijving));
    }

    @Test
    @Transactional
    void patchNonExistingUitschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitschrijving.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitschrijvingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uitschrijving.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitschrijving))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUitschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitschrijving.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitschrijvingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitschrijving))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUitschrijving() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitschrijving.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitschrijvingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uitschrijving)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitschrijving in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUitschrijving() throws Exception {
        // Initialize the database
        uitschrijvingRepository.saveAndFlush(uitschrijving);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitschrijving
        restUitschrijvingMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitschrijving.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitschrijvingRepository.count();
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

    protected Uitschrijving getPersistedUitschrijving(Uitschrijving uitschrijving) {
        return uitschrijvingRepository.findById(uitschrijving.getId()).orElseThrow();
    }

    protected void assertPersistedUitschrijvingToMatchAllProperties(Uitschrijving expectedUitschrijving) {
        assertUitschrijvingAllPropertiesEquals(expectedUitschrijving, getPersistedUitschrijving(expectedUitschrijving));
    }

    protected void assertPersistedUitschrijvingToMatchUpdatableProperties(Uitschrijving expectedUitschrijving) {
        assertUitschrijvingAllUpdatablePropertiesEquals(expectedUitschrijving, getPersistedUitschrijving(expectedUitschrijving));
    }
}
