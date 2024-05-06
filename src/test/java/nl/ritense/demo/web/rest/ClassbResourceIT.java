package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClassbAsserts.*;
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
import nl.ritense.demo.domain.Classb;
import nl.ritense.demo.repository.ClassbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClassbResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassbResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/classbs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassbRepository classbRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassbMockMvc;

    private Classb classb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classb createEntity(EntityManager em) {
        Classb classb = new Classb().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return classb;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classb createUpdatedEntity(EntityManager em) {
        Classb classb = new Classb().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return classb;
    }

    @BeforeEach
    public void initTest() {
        classb = createEntity(em);
    }

    @Test
    @Transactional
    void createClassb() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classb
        var returnedClassb = om.readValue(
            restClassbMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classb)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Classb.class
        );

        // Validate the Classb in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClassbUpdatableFieldsEquals(returnedClassb, getPersistedClassb(returnedClassb));
    }

    @Test
    @Transactional
    void createClassbWithExistingId() throws Exception {
        // Create the Classb with an existing ID
        classb.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classb)))
            .andExpect(status().isBadRequest());

        // Validate the Classb in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClassbs() throws Exception {
        // Initialize the database
        classbRepository.saveAndFlush(classb);

        // Get all the classbList
        restClassbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classb.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getClassb() throws Exception {
        // Initialize the database
        classbRepository.saveAndFlush(classb);

        // Get the classb
        restClassbMockMvc
            .perform(get(ENTITY_API_URL_ID, classb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classb.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingClassb() throws Exception {
        // Get the classb
        restClassbMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClassb() throws Exception {
        // Initialize the database
        classbRepository.saveAndFlush(classb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classb
        Classb updatedClassb = classbRepository.findById(classb.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClassb are not directly saved in db
        em.detach(updatedClassb);
        updatedClassb.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restClassbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClassb.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedClassb))
            )
            .andExpect(status().isOk());

        // Validate the Classb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClassbToMatchAllProperties(updatedClassb);
    }

    @Test
    @Transactional
    void putNonExistingClassb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classb.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassbMockMvc
            .perform(put(ENTITY_API_URL_ID, classb.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classb)))
            .andExpect(status().isBadRequest());

        // Validate the Classb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classb.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classb))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classb.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassbMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classb)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassbWithPatch() throws Exception {
        // Initialize the database
        classbRepository.saveAndFlush(classb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classb using partial update
        Classb partialUpdatedClassb = new Classb();
        partialUpdatedClassb.setId(classb.getId());

        partialUpdatedClassb.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restClassbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassb.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassb))
            )
            .andExpect(status().isOk());

        // Validate the Classb in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassbUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedClassb, classb), getPersistedClassb(classb));
    }

    @Test
    @Transactional
    void fullUpdateClassbWithPatch() throws Exception {
        // Initialize the database
        classbRepository.saveAndFlush(classb);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classb using partial update
        Classb partialUpdatedClassb = new Classb();
        partialUpdatedClassb.setId(classb.getId());

        partialUpdatedClassb.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restClassbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassb.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassb))
            )
            .andExpect(status().isOk());

        // Validate the Classb in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassbUpdatableFieldsEquals(partialUpdatedClassb, getPersistedClassb(partialUpdatedClassb));
    }

    @Test
    @Transactional
    void patchNonExistingClassb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classb.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classb.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classb))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classb.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classb))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassb() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classb.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassbMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classb)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classb in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassb() throws Exception {
        // Initialize the database
        classbRepository.saveAndFlush(classb);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classb
        restClassbMockMvc
            .perform(delete(ENTITY_API_URL_ID, classb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classbRepository.count();
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

    protected Classb getPersistedClassb(Classb classb) {
        return classbRepository.findById(classb.getId()).orElseThrow();
    }

    protected void assertPersistedClassbToMatchAllProperties(Classb expectedClassb) {
        assertClassbAllPropertiesEquals(expectedClassb, getPersistedClassb(expectedClassb));
    }

    protected void assertPersistedClassbToMatchUpdatableProperties(Classb expectedClassb) {
        assertClassbAllUpdatablePropertiesEquals(expectedClassb, getPersistedClassb(expectedClassb));
    }
}
