package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RegelvooriedereenAsserts.*;
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
import nl.ritense.demo.domain.Regelvooriedereen;
import nl.ritense.demo.repository.RegelvooriedereenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RegelvooriedereenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegelvooriedereenResourceIT {

    private static final String DEFAULT_ACTIVITEITREGELKWALIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITEITREGELKWALIFICATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regelvooriedereens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RegelvooriedereenRepository regelvooriedereenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegelvooriedereenMockMvc;

    private Regelvooriedereen regelvooriedereen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regelvooriedereen createEntity(EntityManager em) {
        Regelvooriedereen regelvooriedereen = new Regelvooriedereen().activiteitregelkwalificatie(DEFAULT_ACTIVITEITREGELKWALIFICATIE);
        return regelvooriedereen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regelvooriedereen createUpdatedEntity(EntityManager em) {
        Regelvooriedereen regelvooriedereen = new Regelvooriedereen().activiteitregelkwalificatie(UPDATED_ACTIVITEITREGELKWALIFICATIE);
        return regelvooriedereen;
    }

    @BeforeEach
    public void initTest() {
        regelvooriedereen = createEntity(em);
    }

    @Test
    @Transactional
    void createRegelvooriedereen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Regelvooriedereen
        var returnedRegelvooriedereen = om.readValue(
            restRegelvooriedereenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regelvooriedereen)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Regelvooriedereen.class
        );

        // Validate the Regelvooriedereen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRegelvooriedereenUpdatableFieldsEquals(returnedRegelvooriedereen, getPersistedRegelvooriedereen(returnedRegelvooriedereen));
    }

    @Test
    @Transactional
    void createRegelvooriedereenWithExistingId() throws Exception {
        // Create the Regelvooriedereen with an existing ID
        regelvooriedereen.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegelvooriedereenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regelvooriedereen)))
            .andExpect(status().isBadRequest());

        // Validate the Regelvooriedereen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRegelvooriedereens() throws Exception {
        // Initialize the database
        regelvooriedereenRepository.saveAndFlush(regelvooriedereen);

        // Get all the regelvooriedereenList
        restRegelvooriedereenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regelvooriedereen.getId().intValue())))
            .andExpect(jsonPath("$.[*].activiteitregelkwalificatie").value(hasItem(DEFAULT_ACTIVITEITREGELKWALIFICATIE)));
    }

    @Test
    @Transactional
    void getRegelvooriedereen() throws Exception {
        // Initialize the database
        regelvooriedereenRepository.saveAndFlush(regelvooriedereen);

        // Get the regelvooriedereen
        restRegelvooriedereenMockMvc
            .perform(get(ENTITY_API_URL_ID, regelvooriedereen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regelvooriedereen.getId().intValue()))
            .andExpect(jsonPath("$.activiteitregelkwalificatie").value(DEFAULT_ACTIVITEITREGELKWALIFICATIE));
    }

    @Test
    @Transactional
    void getNonExistingRegelvooriedereen() throws Exception {
        // Get the regelvooriedereen
        restRegelvooriedereenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegelvooriedereen() throws Exception {
        // Initialize the database
        regelvooriedereenRepository.saveAndFlush(regelvooriedereen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regelvooriedereen
        Regelvooriedereen updatedRegelvooriedereen = regelvooriedereenRepository.findById(regelvooriedereen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRegelvooriedereen are not directly saved in db
        em.detach(updatedRegelvooriedereen);
        updatedRegelvooriedereen.activiteitregelkwalificatie(UPDATED_ACTIVITEITREGELKWALIFICATIE);

        restRegelvooriedereenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegelvooriedereen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRegelvooriedereen))
            )
            .andExpect(status().isOk());

        // Validate the Regelvooriedereen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRegelvooriedereenToMatchAllProperties(updatedRegelvooriedereen);
    }

    @Test
    @Transactional
    void putNonExistingRegelvooriedereen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelvooriedereen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegelvooriedereenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regelvooriedereen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(regelvooriedereen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regelvooriedereen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegelvooriedereen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelvooriedereen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelvooriedereenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(regelvooriedereen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regelvooriedereen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegelvooriedereen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelvooriedereen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelvooriedereenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regelvooriedereen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regelvooriedereen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegelvooriedereenWithPatch() throws Exception {
        // Initialize the database
        regelvooriedereenRepository.saveAndFlush(regelvooriedereen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regelvooriedereen using partial update
        Regelvooriedereen partialUpdatedRegelvooriedereen = new Regelvooriedereen();
        partialUpdatedRegelvooriedereen.setId(regelvooriedereen.getId());

        restRegelvooriedereenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegelvooriedereen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegelvooriedereen))
            )
            .andExpect(status().isOk());

        // Validate the Regelvooriedereen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegelvooriedereenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRegelvooriedereen, regelvooriedereen),
            getPersistedRegelvooriedereen(regelvooriedereen)
        );
    }

    @Test
    @Transactional
    void fullUpdateRegelvooriedereenWithPatch() throws Exception {
        // Initialize the database
        regelvooriedereenRepository.saveAndFlush(regelvooriedereen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regelvooriedereen using partial update
        Regelvooriedereen partialUpdatedRegelvooriedereen = new Regelvooriedereen();
        partialUpdatedRegelvooriedereen.setId(regelvooriedereen.getId());

        partialUpdatedRegelvooriedereen.activiteitregelkwalificatie(UPDATED_ACTIVITEITREGELKWALIFICATIE);

        restRegelvooriedereenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegelvooriedereen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegelvooriedereen))
            )
            .andExpect(status().isOk());

        // Validate the Regelvooriedereen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegelvooriedereenUpdatableFieldsEquals(
            partialUpdatedRegelvooriedereen,
            getPersistedRegelvooriedereen(partialUpdatedRegelvooriedereen)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRegelvooriedereen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelvooriedereen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegelvooriedereenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regelvooriedereen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regelvooriedereen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regelvooriedereen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegelvooriedereen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelvooriedereen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelvooriedereenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regelvooriedereen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regelvooriedereen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegelvooriedereen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regelvooriedereen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegelvooriedereenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(regelvooriedereen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regelvooriedereen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegelvooriedereen() throws Exception {
        // Initialize the database
        regelvooriedereenRepository.saveAndFlush(regelvooriedereen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the regelvooriedereen
        restRegelvooriedereenMockMvc
            .perform(delete(ENTITY_API_URL_ID, regelvooriedereen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return regelvooriedereenRepository.count();
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

    protected Regelvooriedereen getPersistedRegelvooriedereen(Regelvooriedereen regelvooriedereen) {
        return regelvooriedereenRepository.findById(regelvooriedereen.getId()).orElseThrow();
    }

    protected void assertPersistedRegelvooriedereenToMatchAllProperties(Regelvooriedereen expectedRegelvooriedereen) {
        assertRegelvooriedereenAllPropertiesEquals(expectedRegelvooriedereen, getPersistedRegelvooriedereen(expectedRegelvooriedereen));
    }

    protected void assertPersistedRegelvooriedereenToMatchUpdatableProperties(Regelvooriedereen expectedRegelvooriedereen) {
        assertRegelvooriedereenAllUpdatablePropertiesEquals(
            expectedRegelvooriedereen,
            getPersistedRegelvooriedereen(expectedRegelvooriedereen)
        );
    }
}
