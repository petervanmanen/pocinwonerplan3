package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ErfgoedobjectAsserts.*;
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
import nl.ritense.demo.domain.Erfgoedobject;
import nl.ritense.demo.repository.ErfgoedobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ErfgoedobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ErfgoedobjectResourceIT {

    private static final String DEFAULT_DATERINGTOT = "AAAAAAAAAA";
    private static final String UPDATED_DATERINGTOT = "BBBBBBBBBB";

    private static final String DEFAULT_DATERINGVANAF = "AAAAAAAAAA";
    private static final String UPDATED_DATERINGVANAF = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/erfgoedobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ErfgoedobjectRepository erfgoedobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restErfgoedobjectMockMvc;

    private Erfgoedobject erfgoedobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Erfgoedobject createEntity(EntityManager em) {
        Erfgoedobject erfgoedobject = new Erfgoedobject()
            .dateringtot(DEFAULT_DATERINGTOT)
            .dateringvanaf(DEFAULT_DATERINGVANAF)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .titel(DEFAULT_TITEL);
        return erfgoedobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Erfgoedobject createUpdatedEntity(EntityManager em) {
        Erfgoedobject erfgoedobject = new Erfgoedobject()
            .dateringtot(UPDATED_DATERINGTOT)
            .dateringvanaf(UPDATED_DATERINGVANAF)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .titel(UPDATED_TITEL);
        return erfgoedobject;
    }

    @BeforeEach
    public void initTest() {
        erfgoedobject = createEntity(em);
    }

    @Test
    @Transactional
    void createErfgoedobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Erfgoedobject
        var returnedErfgoedobject = om.readValue(
            restErfgoedobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(erfgoedobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Erfgoedobject.class
        );

        // Validate the Erfgoedobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertErfgoedobjectUpdatableFieldsEquals(returnedErfgoedobject, getPersistedErfgoedobject(returnedErfgoedobject));
    }

    @Test
    @Transactional
    void createErfgoedobjectWithExistingId() throws Exception {
        // Create the Erfgoedobject with an existing ID
        erfgoedobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restErfgoedobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(erfgoedobject)))
            .andExpect(status().isBadRequest());

        // Validate the Erfgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllErfgoedobjects() throws Exception {
        // Initialize the database
        erfgoedobjectRepository.saveAndFlush(erfgoedobject);

        // Get all the erfgoedobjectList
        restErfgoedobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(erfgoedobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateringtot").value(hasItem(DEFAULT_DATERINGTOT)))
            .andExpect(jsonPath("$.[*].dateringvanaf").value(hasItem(DEFAULT_DATERINGVANAF)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)));
    }

    @Test
    @Transactional
    void getErfgoedobject() throws Exception {
        // Initialize the database
        erfgoedobjectRepository.saveAndFlush(erfgoedobject);

        // Get the erfgoedobject
        restErfgoedobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, erfgoedobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(erfgoedobject.getId().intValue()))
            .andExpect(jsonPath("$.dateringtot").value(DEFAULT_DATERINGTOT))
            .andExpect(jsonPath("$.dateringvanaf").value(DEFAULT_DATERINGVANAF))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL));
    }

    @Test
    @Transactional
    void getNonExistingErfgoedobject() throws Exception {
        // Get the erfgoedobject
        restErfgoedobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingErfgoedobject() throws Exception {
        // Initialize the database
        erfgoedobjectRepository.saveAndFlush(erfgoedobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the erfgoedobject
        Erfgoedobject updatedErfgoedobject = erfgoedobjectRepository.findById(erfgoedobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedErfgoedobject are not directly saved in db
        em.detach(updatedErfgoedobject);
        updatedErfgoedobject
            .dateringtot(UPDATED_DATERINGTOT)
            .dateringvanaf(UPDATED_DATERINGVANAF)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .titel(UPDATED_TITEL);

        restErfgoedobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedErfgoedobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedErfgoedobject))
            )
            .andExpect(status().isOk());

        // Validate the Erfgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedErfgoedobjectToMatchAllProperties(updatedErfgoedobject);
    }

    @Test
    @Transactional
    void putNonExistingErfgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        erfgoedobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErfgoedobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, erfgoedobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(erfgoedobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Erfgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchErfgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        erfgoedobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErfgoedobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(erfgoedobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Erfgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamErfgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        erfgoedobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErfgoedobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(erfgoedobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Erfgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateErfgoedobjectWithPatch() throws Exception {
        // Initialize the database
        erfgoedobjectRepository.saveAndFlush(erfgoedobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the erfgoedobject using partial update
        Erfgoedobject partialUpdatedErfgoedobject = new Erfgoedobject();
        partialUpdatedErfgoedobject.setId(erfgoedobject.getId());

        partialUpdatedErfgoedobject.dateringvanaf(UPDATED_DATERINGVANAF).omschrijving(UPDATED_OMSCHRIJVING).titel(UPDATED_TITEL);

        restErfgoedobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedErfgoedobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedErfgoedobject))
            )
            .andExpect(status().isOk());

        // Validate the Erfgoedobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertErfgoedobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedErfgoedobject, erfgoedobject),
            getPersistedErfgoedobject(erfgoedobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateErfgoedobjectWithPatch() throws Exception {
        // Initialize the database
        erfgoedobjectRepository.saveAndFlush(erfgoedobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the erfgoedobject using partial update
        Erfgoedobject partialUpdatedErfgoedobject = new Erfgoedobject();
        partialUpdatedErfgoedobject.setId(erfgoedobject.getId());

        partialUpdatedErfgoedobject
            .dateringtot(UPDATED_DATERINGTOT)
            .dateringvanaf(UPDATED_DATERINGVANAF)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .titel(UPDATED_TITEL);

        restErfgoedobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedErfgoedobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedErfgoedobject))
            )
            .andExpect(status().isOk());

        // Validate the Erfgoedobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertErfgoedobjectUpdatableFieldsEquals(partialUpdatedErfgoedobject, getPersistedErfgoedobject(partialUpdatedErfgoedobject));
    }

    @Test
    @Transactional
    void patchNonExistingErfgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        erfgoedobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErfgoedobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, erfgoedobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(erfgoedobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Erfgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchErfgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        erfgoedobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErfgoedobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(erfgoedobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Erfgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamErfgoedobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        erfgoedobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErfgoedobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(erfgoedobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Erfgoedobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteErfgoedobject() throws Exception {
        // Initialize the database
        erfgoedobjectRepository.saveAndFlush(erfgoedobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the erfgoedobject
        restErfgoedobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, erfgoedobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return erfgoedobjectRepository.count();
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

    protected Erfgoedobject getPersistedErfgoedobject(Erfgoedobject erfgoedobject) {
        return erfgoedobjectRepository.findById(erfgoedobject.getId()).orElseThrow();
    }

    protected void assertPersistedErfgoedobjectToMatchAllProperties(Erfgoedobject expectedErfgoedobject) {
        assertErfgoedobjectAllPropertiesEquals(expectedErfgoedobject, getPersistedErfgoedobject(expectedErfgoedobject));
    }

    protected void assertPersistedErfgoedobjectToMatchUpdatableProperties(Erfgoedobject expectedErfgoedobject) {
        assertErfgoedobjectAllUpdatablePropertiesEquals(expectedErfgoedobject, getPersistedErfgoedobject(expectedErfgoedobject));
    }
}
