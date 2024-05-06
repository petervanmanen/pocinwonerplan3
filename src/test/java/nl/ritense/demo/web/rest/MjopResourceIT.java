package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MjopAsserts.*;
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
import nl.ritense.demo.domain.Mjop;
import nl.ritense.demo.repository.MjopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MjopResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MjopResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mjops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MjopRepository mjopRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMjopMockMvc;

    private Mjop mjop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mjop createEntity(EntityManager em) {
        Mjop mjop = new Mjop().datum(DEFAULT_DATUM).omschrijving(DEFAULT_OMSCHRIJVING);
        return mjop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mjop createUpdatedEntity(EntityManager em) {
        Mjop mjop = new Mjop().datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING);
        return mjop;
    }

    @BeforeEach
    public void initTest() {
        mjop = createEntity(em);
    }

    @Test
    @Transactional
    void createMjop() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mjop
        var returnedMjop = om.readValue(
            restMjopMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mjop)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Mjop.class
        );

        // Validate the Mjop in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMjopUpdatableFieldsEquals(returnedMjop, getPersistedMjop(returnedMjop));
    }

    @Test
    @Transactional
    void createMjopWithExistingId() throws Exception {
        // Create the Mjop with an existing ID
        mjop.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMjopMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mjop)))
            .andExpect(status().isBadRequest());

        // Validate the Mjop in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMjops() throws Exception {
        // Initialize the database
        mjopRepository.saveAndFlush(mjop);

        // Get all the mjopList
        restMjopMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mjop.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getMjop() throws Exception {
        // Initialize the database
        mjopRepository.saveAndFlush(mjop);

        // Get the mjop
        restMjopMockMvc
            .perform(get(ENTITY_API_URL_ID, mjop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mjop.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingMjop() throws Exception {
        // Get the mjop
        restMjopMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMjop() throws Exception {
        // Initialize the database
        mjopRepository.saveAndFlush(mjop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mjop
        Mjop updatedMjop = mjopRepository.findById(mjop.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMjop are not directly saved in db
        em.detach(updatedMjop);
        updatedMjop.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING);

        restMjopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMjop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMjop))
            )
            .andExpect(status().isOk());

        // Validate the Mjop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMjopToMatchAllProperties(updatedMjop);
    }

    @Test
    @Transactional
    void putNonExistingMjop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjop.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMjopMockMvc
            .perform(put(ENTITY_API_URL_ID, mjop.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mjop)))
            .andExpect(status().isBadRequest());

        // Validate the Mjop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMjop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMjopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mjop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mjop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMjop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMjopMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mjop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mjop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMjopWithPatch() throws Exception {
        // Initialize the database
        mjopRepository.saveAndFlush(mjop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mjop using partial update
        Mjop partialUpdatedMjop = new Mjop();
        partialUpdatedMjop.setId(mjop.getId());

        partialUpdatedMjop.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING);

        restMjopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMjop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMjop))
            )
            .andExpect(status().isOk());

        // Validate the Mjop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMjopUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMjop, mjop), getPersistedMjop(mjop));
    }

    @Test
    @Transactional
    void fullUpdateMjopWithPatch() throws Exception {
        // Initialize the database
        mjopRepository.saveAndFlush(mjop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mjop using partial update
        Mjop partialUpdatedMjop = new Mjop();
        partialUpdatedMjop.setId(mjop.getId());

        partialUpdatedMjop.datum(UPDATED_DATUM).omschrijving(UPDATED_OMSCHRIJVING);

        restMjopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMjop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMjop))
            )
            .andExpect(status().isOk());

        // Validate the Mjop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMjopUpdatableFieldsEquals(partialUpdatedMjop, getPersistedMjop(partialUpdatedMjop));
    }

    @Test
    @Transactional
    void patchNonExistingMjop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjop.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMjopMockMvc
            .perform(patch(ENTITY_API_URL_ID, mjop.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mjop)))
            .andExpect(status().isBadRequest());

        // Validate the Mjop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMjop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMjopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mjop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mjop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMjop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMjopMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mjop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mjop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMjop() throws Exception {
        // Initialize the database
        mjopRepository.saveAndFlush(mjop);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mjop
        restMjopMockMvc
            .perform(delete(ENTITY_API_URL_ID, mjop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mjopRepository.count();
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

    protected Mjop getPersistedMjop(Mjop mjop) {
        return mjopRepository.findById(mjop.getId()).orElseThrow();
    }

    protected void assertPersistedMjopToMatchAllProperties(Mjop expectedMjop) {
        assertMjopAllPropertiesEquals(expectedMjop, getPersistedMjop(expectedMjop));
    }

    protected void assertPersistedMjopToMatchUpdatableProperties(Mjop expectedMjop) {
        assertMjopAllUpdatablePropertiesEquals(expectedMjop, getPersistedMjop(expectedMjop));
    }
}
