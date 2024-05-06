package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClassaAsserts.*;
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
import nl.ritense.demo.domain.Classa;
import nl.ritense.demo.repository.ClassaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClassaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassaResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/classas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassaRepository classaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassaMockMvc;

    private Classa classa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classa createEntity(EntityManager em) {
        Classa classa = new Classa().naam(DEFAULT_NAAM);
        return classa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classa createUpdatedEntity(EntityManager em) {
        Classa classa = new Classa().naam(UPDATED_NAAM);
        return classa;
    }

    @BeforeEach
    public void initTest() {
        classa = createEntity(em);
    }

    @Test
    @Transactional
    void createClassa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classa
        var returnedClassa = om.readValue(
            restClassaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Classa.class
        );

        // Validate the Classa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClassaUpdatableFieldsEquals(returnedClassa, getPersistedClassa(returnedClassa));
    }

    @Test
    @Transactional
    void createClassaWithExistingId() throws Exception {
        // Create the Classa with an existing ID
        classa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classa)))
            .andExpect(status().isBadRequest());

        // Validate the Classa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClassas() throws Exception {
        // Initialize the database
        classaRepository.saveAndFlush(classa);

        // Get all the classaList
        restClassaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classa.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getClassa() throws Exception {
        // Initialize the database
        classaRepository.saveAndFlush(classa);

        // Get the classa
        restClassaMockMvc
            .perform(get(ENTITY_API_URL_ID, classa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classa.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingClassa() throws Exception {
        // Get the classa
        restClassaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClassa() throws Exception {
        // Initialize the database
        classaRepository.saveAndFlush(classa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classa
        Classa updatedClassa = classaRepository.findById(classa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClassa are not directly saved in db
        em.detach(updatedClassa);
        updatedClassa.naam(UPDATED_NAAM);

        restClassaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClassa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedClassa))
            )
            .andExpect(status().isOk());

        // Validate the Classa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClassaToMatchAllProperties(updatedClassa);
    }

    @Test
    @Transactional
    void putNonExistingClassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassaMockMvc
            .perform(put(ENTITY_API_URL_ID, classa.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classa)))
            .andExpect(status().isBadRequest());

        // Validate the Classa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassaWithPatch() throws Exception {
        // Initialize the database
        classaRepository.saveAndFlush(classa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classa using partial update
        Classa partialUpdatedClassa = new Classa();
        partialUpdatedClassa.setId(classa.getId());

        restClassaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassa))
            )
            .andExpect(status().isOk());

        // Validate the Classa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedClassa, classa), getPersistedClassa(classa));
    }

    @Test
    @Transactional
    void fullUpdateClassaWithPatch() throws Exception {
        // Initialize the database
        classaRepository.saveAndFlush(classa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classa using partial update
        Classa partialUpdatedClassa = new Classa();
        partialUpdatedClassa.setId(classa.getId());

        partialUpdatedClassa.naam(UPDATED_NAAM);

        restClassaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassa))
            )
            .andExpect(status().isOk());

        // Validate the Classa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassaUpdatableFieldsEquals(partialUpdatedClassa, getPersistedClassa(partialUpdatedClassa));
    }

    @Test
    @Transactional
    void patchNonExistingClassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classa.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassa() throws Exception {
        // Initialize the database
        classaRepository.saveAndFlush(classa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classa
        restClassaMockMvc
            .perform(delete(ENTITY_API_URL_ID, classa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classaRepository.count();
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

    protected Classa getPersistedClassa(Classa classa) {
        return classaRepository.findById(classa.getId()).orElseThrow();
    }

    protected void assertPersistedClassaToMatchAllProperties(Classa expectedClassa) {
        assertClassaAllPropertiesEquals(expectedClassa, getPersistedClassa(expectedClassa));
    }

    protected void assertPersistedClassaToMatchUpdatableProperties(Classa expectedClassa) {
        assertClassaAllUpdatablePropertiesEquals(expectedClassa, getPersistedClassa(expectedClassa));
    }
}
