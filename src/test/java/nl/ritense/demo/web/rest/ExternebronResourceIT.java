package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ExternebronAsserts.*;
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
import nl.ritense.demo.domain.Externebron;
import nl.ritense.demo.repository.ExternebronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ExternebronResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExternebronResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/externebrons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExternebronRepository externebronRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExternebronMockMvc;

    private Externebron externebron;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Externebron createEntity(EntityManager em) {
        Externebron externebron = new Externebron().guid(DEFAULT_GUID).naam(DEFAULT_NAAM);
        return externebron;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Externebron createUpdatedEntity(EntityManager em) {
        Externebron externebron = new Externebron().guid(UPDATED_GUID).naam(UPDATED_NAAM);
        return externebron;
    }

    @BeforeEach
    public void initTest() {
        externebron = createEntity(em);
    }

    @Test
    @Transactional
    void createExternebron() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Externebron
        var returnedExternebron = om.readValue(
            restExternebronMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(externebron)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Externebron.class
        );

        // Validate the Externebron in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertExternebronUpdatableFieldsEquals(returnedExternebron, getPersistedExternebron(returnedExternebron));
    }

    @Test
    @Transactional
    void createExternebronWithExistingId() throws Exception {
        // Create the Externebron with an existing ID
        externebron.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternebronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(externebron)))
            .andExpect(status().isBadRequest());

        // Validate the Externebron in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExternebrons() throws Exception {
        // Initialize the database
        externebronRepository.saveAndFlush(externebron);

        // Get all the externebronList
        restExternebronMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externebron.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getExternebron() throws Exception {
        // Initialize the database
        externebronRepository.saveAndFlush(externebron);

        // Get the externebron
        restExternebronMockMvc
            .perform(get(ENTITY_API_URL_ID, externebron.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(externebron.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingExternebron() throws Exception {
        // Get the externebron
        restExternebronMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExternebron() throws Exception {
        // Initialize the database
        externebronRepository.saveAndFlush(externebron);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the externebron
        Externebron updatedExternebron = externebronRepository.findById(externebron.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExternebron are not directly saved in db
        em.detach(updatedExternebron);
        updatedExternebron.guid(UPDATED_GUID).naam(UPDATED_NAAM);

        restExternebronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExternebron.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedExternebron))
            )
            .andExpect(status().isOk());

        // Validate the Externebron in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExternebronToMatchAllProperties(updatedExternebron);
    }

    @Test
    @Transactional
    void putNonExistingExternebron() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        externebron.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternebronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, externebron.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(externebron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Externebron in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExternebron() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        externebron.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternebronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(externebron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Externebron in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExternebron() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        externebron.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternebronMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(externebron)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Externebron in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExternebronWithPatch() throws Exception {
        // Initialize the database
        externebronRepository.saveAndFlush(externebron);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the externebron using partial update
        Externebron partialUpdatedExternebron = new Externebron();
        partialUpdatedExternebron.setId(externebron.getId());

        partialUpdatedExternebron.guid(UPDATED_GUID);

        restExternebronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExternebron.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExternebron))
            )
            .andExpect(status().isOk());

        // Validate the Externebron in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExternebronUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedExternebron, externebron),
            getPersistedExternebron(externebron)
        );
    }

    @Test
    @Transactional
    void fullUpdateExternebronWithPatch() throws Exception {
        // Initialize the database
        externebronRepository.saveAndFlush(externebron);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the externebron using partial update
        Externebron partialUpdatedExternebron = new Externebron();
        partialUpdatedExternebron.setId(externebron.getId());

        partialUpdatedExternebron.guid(UPDATED_GUID).naam(UPDATED_NAAM);

        restExternebronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExternebron.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExternebron))
            )
            .andExpect(status().isOk());

        // Validate the Externebron in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExternebronUpdatableFieldsEquals(partialUpdatedExternebron, getPersistedExternebron(partialUpdatedExternebron));
    }

    @Test
    @Transactional
    void patchNonExistingExternebron() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        externebron.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternebronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, externebron.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(externebron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Externebron in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExternebron() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        externebron.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternebronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(externebron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Externebron in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExternebron() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        externebron.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExternebronMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(externebron)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Externebron in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExternebron() throws Exception {
        // Initialize the database
        externebronRepository.saveAndFlush(externebron);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the externebron
        restExternebronMockMvc
            .perform(delete(ENTITY_API_URL_ID, externebron.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return externebronRepository.count();
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

    protected Externebron getPersistedExternebron(Externebron externebron) {
        return externebronRepository.findById(externebron.getId()).orElseThrow();
    }

    protected void assertPersistedExternebronToMatchAllProperties(Externebron expectedExternebron) {
        assertExternebronAllPropertiesEquals(expectedExternebron, getPersistedExternebron(expectedExternebron));
    }

    protected void assertPersistedExternebronToMatchUpdatableProperties(Externebron expectedExternebron) {
        assertExternebronAllUpdatablePropertiesEquals(expectedExternebron, getPersistedExternebron(expectedExternebron));
    }
}
