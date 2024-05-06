package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ChildclassaAsserts.*;
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
import nl.ritense.demo.domain.Childclassa;
import nl.ritense.demo.repository.ChildclassaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChildclassaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChildclassaResourceIT {

    private static final String DEFAULT_KLEUR = "AAAAAAAAAA";
    private static final String UPDATED_KLEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/childclassas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChildclassaRepository childclassaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChildclassaMockMvc;

    private Childclassa childclassa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Childclassa createEntity(EntityManager em) {
        Childclassa childclassa = new Childclassa().kleur(DEFAULT_KLEUR);
        return childclassa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Childclassa createUpdatedEntity(EntityManager em) {
        Childclassa childclassa = new Childclassa().kleur(UPDATED_KLEUR);
        return childclassa;
    }

    @BeforeEach
    public void initTest() {
        childclassa = createEntity(em);
    }

    @Test
    @Transactional
    void createChildclassa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Childclassa
        var returnedChildclassa = om.readValue(
            restChildclassaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(childclassa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Childclassa.class
        );

        // Validate the Childclassa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertChildclassaUpdatableFieldsEquals(returnedChildclassa, getPersistedChildclassa(returnedChildclassa));
    }

    @Test
    @Transactional
    void createChildclassaWithExistingId() throws Exception {
        // Create the Childclassa with an existing ID
        childclassa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildclassaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(childclassa)))
            .andExpect(status().isBadRequest());

        // Validate the Childclassa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChildclassas() throws Exception {
        // Initialize the database
        childclassaRepository.saveAndFlush(childclassa);

        // Get all the childclassaList
        restChildclassaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childclassa.getId().intValue())))
            .andExpect(jsonPath("$.[*].kleur").value(hasItem(DEFAULT_KLEUR)));
    }

    @Test
    @Transactional
    void getChildclassa() throws Exception {
        // Initialize the database
        childclassaRepository.saveAndFlush(childclassa);

        // Get the childclassa
        restChildclassaMockMvc
            .perform(get(ENTITY_API_URL_ID, childclassa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(childclassa.getId().intValue()))
            .andExpect(jsonPath("$.kleur").value(DEFAULT_KLEUR));
    }

    @Test
    @Transactional
    void getNonExistingChildclassa() throws Exception {
        // Get the childclassa
        restChildclassaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChildclassa() throws Exception {
        // Initialize the database
        childclassaRepository.saveAndFlush(childclassa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the childclassa
        Childclassa updatedChildclassa = childclassaRepository.findById(childclassa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedChildclassa are not directly saved in db
        em.detach(updatedChildclassa);
        updatedChildclassa.kleur(UPDATED_KLEUR);

        restChildclassaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChildclassa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedChildclassa))
            )
            .andExpect(status().isOk());

        // Validate the Childclassa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChildclassaToMatchAllProperties(updatedChildclassa);
    }

    @Test
    @Transactional
    void putNonExistingChildclassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        childclassa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildclassaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, childclassa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(childclassa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Childclassa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChildclassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        childclassa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildclassaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(childclassa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Childclassa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChildclassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        childclassa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildclassaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(childclassa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Childclassa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChildclassaWithPatch() throws Exception {
        // Initialize the database
        childclassaRepository.saveAndFlush(childclassa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the childclassa using partial update
        Childclassa partialUpdatedChildclassa = new Childclassa();
        partialUpdatedChildclassa.setId(childclassa.getId());

        partialUpdatedChildclassa.kleur(UPDATED_KLEUR);

        restChildclassaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChildclassa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChildclassa))
            )
            .andExpect(status().isOk());

        // Validate the Childclassa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChildclassaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedChildclassa, childclassa),
            getPersistedChildclassa(childclassa)
        );
    }

    @Test
    @Transactional
    void fullUpdateChildclassaWithPatch() throws Exception {
        // Initialize the database
        childclassaRepository.saveAndFlush(childclassa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the childclassa using partial update
        Childclassa partialUpdatedChildclassa = new Childclassa();
        partialUpdatedChildclassa.setId(childclassa.getId());

        partialUpdatedChildclassa.kleur(UPDATED_KLEUR);

        restChildclassaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChildclassa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChildclassa))
            )
            .andExpect(status().isOk());

        // Validate the Childclassa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChildclassaUpdatableFieldsEquals(partialUpdatedChildclassa, getPersistedChildclassa(partialUpdatedChildclassa));
    }

    @Test
    @Transactional
    void patchNonExistingChildclassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        childclassa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildclassaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, childclassa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(childclassa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Childclassa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChildclassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        childclassa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildclassaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(childclassa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Childclassa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChildclassa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        childclassa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildclassaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(childclassa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Childclassa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChildclassa() throws Exception {
        // Initialize the database
        childclassaRepository.saveAndFlush(childclassa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the childclassa
        restChildclassaMockMvc
            .perform(delete(ENTITY_API_URL_ID, childclassa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return childclassaRepository.count();
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

    protected Childclassa getPersistedChildclassa(Childclassa childclassa) {
        return childclassaRepository.findById(childclassa.getId()).orElseThrow();
    }

    protected void assertPersistedChildclassaToMatchAllProperties(Childclassa expectedChildclassa) {
        assertChildclassaAllPropertiesEquals(expectedChildclassa, getPersistedChildclassa(expectedChildclassa));
    }

    protected void assertPersistedChildclassaToMatchUpdatableProperties(Childclassa expectedChildclassa) {
        assertChildclassaAllUpdatablePropertiesEquals(expectedChildclassa, getPersistedChildclassa(expectedChildclassa));
    }
}
