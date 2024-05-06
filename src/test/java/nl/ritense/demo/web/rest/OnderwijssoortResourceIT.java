package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnderwijssoortAsserts.*;
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
import nl.ritense.demo.domain.Onderwijssoort;
import nl.ritense.demo.domain.School;
import nl.ritense.demo.repository.OnderwijssoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderwijssoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnderwijssoortResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERWIJSTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ONDERWIJSTYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/onderwijssoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnderwijssoortRepository onderwijssoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderwijssoortMockMvc;

    private Onderwijssoort onderwijssoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijssoort createEntity(EntityManager em) {
        Onderwijssoort onderwijssoort = new Onderwijssoort().omschrijving(DEFAULT_OMSCHRIJVING).onderwijstype(DEFAULT_ONDERWIJSTYPE);
        // Add required entity
        School school;
        if (TestUtil.findAll(em, School.class).isEmpty()) {
            school = SchoolResourceIT.createEntity(em);
            em.persist(school);
            em.flush();
        } else {
            school = TestUtil.findAll(em, School.class).get(0);
        }
        onderwijssoort.getHeeftSchools().add(school);
        return onderwijssoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderwijssoort createUpdatedEntity(EntityManager em) {
        Onderwijssoort onderwijssoort = new Onderwijssoort().omschrijving(UPDATED_OMSCHRIJVING).onderwijstype(UPDATED_ONDERWIJSTYPE);
        // Add required entity
        School school;
        if (TestUtil.findAll(em, School.class).isEmpty()) {
            school = SchoolResourceIT.createUpdatedEntity(em);
            em.persist(school);
            em.flush();
        } else {
            school = TestUtil.findAll(em, School.class).get(0);
        }
        onderwijssoort.getHeeftSchools().add(school);
        return onderwijssoort;
    }

    @BeforeEach
    public void initTest() {
        onderwijssoort = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderwijssoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onderwijssoort
        var returnedOnderwijssoort = om.readValue(
            restOnderwijssoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijssoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onderwijssoort.class
        );

        // Validate the Onderwijssoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnderwijssoortUpdatableFieldsEquals(returnedOnderwijssoort, getPersistedOnderwijssoort(returnedOnderwijssoort));
    }

    @Test
    @Transactional
    void createOnderwijssoortWithExistingId() throws Exception {
        // Create the Onderwijssoort with an existing ID
        onderwijssoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderwijssoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijssoort)))
            .andExpect(status().isBadRequest());

        // Validate the Onderwijssoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnderwijssoorts() throws Exception {
        // Initialize the database
        onderwijssoortRepository.saveAndFlush(onderwijssoort);

        // Get all the onderwijssoortList
        restOnderwijssoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderwijssoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].onderwijstype").value(hasItem(DEFAULT_ONDERWIJSTYPE)));
    }

    @Test
    @Transactional
    void getOnderwijssoort() throws Exception {
        // Initialize the database
        onderwijssoortRepository.saveAndFlush(onderwijssoort);

        // Get the onderwijssoort
        restOnderwijssoortMockMvc
            .perform(get(ENTITY_API_URL_ID, onderwijssoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderwijssoort.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.onderwijstype").value(DEFAULT_ONDERWIJSTYPE));
    }

    @Test
    @Transactional
    void getNonExistingOnderwijssoort() throws Exception {
        // Get the onderwijssoort
        restOnderwijssoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOnderwijssoort() throws Exception {
        // Initialize the database
        onderwijssoortRepository.saveAndFlush(onderwijssoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijssoort
        Onderwijssoort updatedOnderwijssoort = onderwijssoortRepository.findById(onderwijssoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOnderwijssoort are not directly saved in db
        em.detach(updatedOnderwijssoort);
        updatedOnderwijssoort.omschrijving(UPDATED_OMSCHRIJVING).onderwijstype(UPDATED_ONDERWIJSTYPE);

        restOnderwijssoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOnderwijssoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOnderwijssoort))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijssoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOnderwijssoortToMatchAllProperties(updatedOnderwijssoort);
    }

    @Test
    @Transactional
    void putNonExistingOnderwijssoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijssoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderwijssoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, onderwijssoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onderwijssoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijssoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOnderwijssoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijssoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijssoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onderwijssoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijssoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOnderwijssoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijssoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijssoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onderwijssoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderwijssoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOnderwijssoortWithPatch() throws Exception {
        // Initialize the database
        onderwijssoortRepository.saveAndFlush(onderwijssoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijssoort using partial update
        Onderwijssoort partialUpdatedOnderwijssoort = new Onderwijssoort();
        partialUpdatedOnderwijssoort.setId(onderwijssoort.getId());

        restOnderwijssoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderwijssoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnderwijssoort))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijssoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnderwijssoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOnderwijssoort, onderwijssoort),
            getPersistedOnderwijssoort(onderwijssoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateOnderwijssoortWithPatch() throws Exception {
        // Initialize the database
        onderwijssoortRepository.saveAndFlush(onderwijssoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onderwijssoort using partial update
        Onderwijssoort partialUpdatedOnderwijssoort = new Onderwijssoort();
        partialUpdatedOnderwijssoort.setId(onderwijssoort.getId());

        partialUpdatedOnderwijssoort.omschrijving(UPDATED_OMSCHRIJVING).onderwijstype(UPDATED_ONDERWIJSTYPE);

        restOnderwijssoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderwijssoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnderwijssoort))
            )
            .andExpect(status().isOk());

        // Validate the Onderwijssoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnderwijssoortUpdatableFieldsEquals(partialUpdatedOnderwijssoort, getPersistedOnderwijssoort(partialUpdatedOnderwijssoort));
    }

    @Test
    @Transactional
    void patchNonExistingOnderwijssoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijssoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderwijssoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, onderwijssoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onderwijssoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijssoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOnderwijssoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijssoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijssoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onderwijssoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderwijssoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOnderwijssoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onderwijssoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderwijssoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(onderwijssoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderwijssoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOnderwijssoort() throws Exception {
        // Initialize the database
        onderwijssoortRepository.saveAndFlush(onderwijssoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onderwijssoort
        restOnderwijssoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderwijssoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onderwijssoortRepository.count();
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

    protected Onderwijssoort getPersistedOnderwijssoort(Onderwijssoort onderwijssoort) {
        return onderwijssoortRepository.findById(onderwijssoort.getId()).orElseThrow();
    }

    protected void assertPersistedOnderwijssoortToMatchAllProperties(Onderwijssoort expectedOnderwijssoort) {
        assertOnderwijssoortAllPropertiesEquals(expectedOnderwijssoort, getPersistedOnderwijssoort(expectedOnderwijssoort));
    }

    protected void assertPersistedOnderwijssoortToMatchUpdatableProperties(Onderwijssoort expectedOnderwijssoort) {
        assertOnderwijssoortAllUpdatablePropertiesEquals(expectedOnderwijssoort, getPersistedOnderwijssoort(expectedOnderwijssoort));
    }
}
