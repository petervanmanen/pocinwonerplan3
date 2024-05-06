package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LoopbaanstapAsserts.*;
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
import nl.ritense.demo.domain.Loopbaanstap;
import nl.ritense.demo.repository.LoopbaanstapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LoopbaanstapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoopbaanstapResourceIT {

    private static final String DEFAULT_KLAS = "AAAAAAAAAA";
    private static final String UPDATED_KLAS = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERWIJSTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ONDERWIJSTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOLJAAR = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOLJAAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/loopbaanstaps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LoopbaanstapRepository loopbaanstapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoopbaanstapMockMvc;

    private Loopbaanstap loopbaanstap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loopbaanstap createEntity(EntityManager em) {
        Loopbaanstap loopbaanstap = new Loopbaanstap()
            .klas(DEFAULT_KLAS)
            .onderwijstype(DEFAULT_ONDERWIJSTYPE)
            .schooljaar(DEFAULT_SCHOOLJAAR);
        return loopbaanstap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loopbaanstap createUpdatedEntity(EntityManager em) {
        Loopbaanstap loopbaanstap = new Loopbaanstap()
            .klas(UPDATED_KLAS)
            .onderwijstype(UPDATED_ONDERWIJSTYPE)
            .schooljaar(UPDATED_SCHOOLJAAR);
        return loopbaanstap;
    }

    @BeforeEach
    public void initTest() {
        loopbaanstap = createEntity(em);
    }

    @Test
    @Transactional
    void createLoopbaanstap() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Loopbaanstap
        var returnedLoopbaanstap = om.readValue(
            restLoopbaanstapMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loopbaanstap)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Loopbaanstap.class
        );

        // Validate the Loopbaanstap in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLoopbaanstapUpdatableFieldsEquals(returnedLoopbaanstap, getPersistedLoopbaanstap(returnedLoopbaanstap));
    }

    @Test
    @Transactional
    void createLoopbaanstapWithExistingId() throws Exception {
        // Create the Loopbaanstap with an existing ID
        loopbaanstap.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoopbaanstapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loopbaanstap)))
            .andExpect(status().isBadRequest());

        // Validate the Loopbaanstap in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoopbaanstaps() throws Exception {
        // Initialize the database
        loopbaanstapRepository.saveAndFlush(loopbaanstap);

        // Get all the loopbaanstapList
        restLoopbaanstapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loopbaanstap.getId().intValue())))
            .andExpect(jsonPath("$.[*].klas").value(hasItem(DEFAULT_KLAS)))
            .andExpect(jsonPath("$.[*].onderwijstype").value(hasItem(DEFAULT_ONDERWIJSTYPE)))
            .andExpect(jsonPath("$.[*].schooljaar").value(hasItem(DEFAULT_SCHOOLJAAR)));
    }

    @Test
    @Transactional
    void getLoopbaanstap() throws Exception {
        // Initialize the database
        loopbaanstapRepository.saveAndFlush(loopbaanstap);

        // Get the loopbaanstap
        restLoopbaanstapMockMvc
            .perform(get(ENTITY_API_URL_ID, loopbaanstap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loopbaanstap.getId().intValue()))
            .andExpect(jsonPath("$.klas").value(DEFAULT_KLAS))
            .andExpect(jsonPath("$.onderwijstype").value(DEFAULT_ONDERWIJSTYPE))
            .andExpect(jsonPath("$.schooljaar").value(DEFAULT_SCHOOLJAAR));
    }

    @Test
    @Transactional
    void getNonExistingLoopbaanstap() throws Exception {
        // Get the loopbaanstap
        restLoopbaanstapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoopbaanstap() throws Exception {
        // Initialize the database
        loopbaanstapRepository.saveAndFlush(loopbaanstap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the loopbaanstap
        Loopbaanstap updatedLoopbaanstap = loopbaanstapRepository.findById(loopbaanstap.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLoopbaanstap are not directly saved in db
        em.detach(updatedLoopbaanstap);
        updatedLoopbaanstap.klas(UPDATED_KLAS).onderwijstype(UPDATED_ONDERWIJSTYPE).schooljaar(UPDATED_SCHOOLJAAR);

        restLoopbaanstapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoopbaanstap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLoopbaanstap))
            )
            .andExpect(status().isOk());

        // Validate the Loopbaanstap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLoopbaanstapToMatchAllProperties(updatedLoopbaanstap);
    }

    @Test
    @Transactional
    void putNonExistingLoopbaanstap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loopbaanstap.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoopbaanstapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loopbaanstap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(loopbaanstap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Loopbaanstap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoopbaanstap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loopbaanstap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoopbaanstapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(loopbaanstap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Loopbaanstap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoopbaanstap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loopbaanstap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoopbaanstapMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(loopbaanstap)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Loopbaanstap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoopbaanstapWithPatch() throws Exception {
        // Initialize the database
        loopbaanstapRepository.saveAndFlush(loopbaanstap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the loopbaanstap using partial update
        Loopbaanstap partialUpdatedLoopbaanstap = new Loopbaanstap();
        partialUpdatedLoopbaanstap.setId(loopbaanstap.getId());

        partialUpdatedLoopbaanstap.schooljaar(UPDATED_SCHOOLJAAR);

        restLoopbaanstapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoopbaanstap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLoopbaanstap))
            )
            .andExpect(status().isOk());

        // Validate the Loopbaanstap in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLoopbaanstapUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLoopbaanstap, loopbaanstap),
            getPersistedLoopbaanstap(loopbaanstap)
        );
    }

    @Test
    @Transactional
    void fullUpdateLoopbaanstapWithPatch() throws Exception {
        // Initialize the database
        loopbaanstapRepository.saveAndFlush(loopbaanstap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the loopbaanstap using partial update
        Loopbaanstap partialUpdatedLoopbaanstap = new Loopbaanstap();
        partialUpdatedLoopbaanstap.setId(loopbaanstap.getId());

        partialUpdatedLoopbaanstap.klas(UPDATED_KLAS).onderwijstype(UPDATED_ONDERWIJSTYPE).schooljaar(UPDATED_SCHOOLJAAR);

        restLoopbaanstapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoopbaanstap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLoopbaanstap))
            )
            .andExpect(status().isOk());

        // Validate the Loopbaanstap in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLoopbaanstapUpdatableFieldsEquals(partialUpdatedLoopbaanstap, getPersistedLoopbaanstap(partialUpdatedLoopbaanstap));
    }

    @Test
    @Transactional
    void patchNonExistingLoopbaanstap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loopbaanstap.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoopbaanstapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loopbaanstap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(loopbaanstap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Loopbaanstap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoopbaanstap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loopbaanstap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoopbaanstapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(loopbaanstap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Loopbaanstap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoopbaanstap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        loopbaanstap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoopbaanstapMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(loopbaanstap)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Loopbaanstap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoopbaanstap() throws Exception {
        // Initialize the database
        loopbaanstapRepository.saveAndFlush(loopbaanstap);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the loopbaanstap
        restLoopbaanstapMockMvc
            .perform(delete(ENTITY_API_URL_ID, loopbaanstap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return loopbaanstapRepository.count();
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

    protected Loopbaanstap getPersistedLoopbaanstap(Loopbaanstap loopbaanstap) {
        return loopbaanstapRepository.findById(loopbaanstap.getId()).orElseThrow();
    }

    protected void assertPersistedLoopbaanstapToMatchAllProperties(Loopbaanstap expectedLoopbaanstap) {
        assertLoopbaanstapAllPropertiesEquals(expectedLoopbaanstap, getPersistedLoopbaanstap(expectedLoopbaanstap));
    }

    protected void assertPersistedLoopbaanstapToMatchUpdatableProperties(Loopbaanstap expectedLoopbaanstap) {
        assertLoopbaanstapAllUpdatablePropertiesEquals(expectedLoopbaanstap, getPersistedLoopbaanstap(expectedLoopbaanstap));
    }
}
