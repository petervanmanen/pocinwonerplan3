package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DoosAsserts.*;
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
import nl.ritense.demo.domain.Doos;
import nl.ritense.demo.repository.DoosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DoosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoosResourceIT {

    private static final String DEFAULT_DOOSNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_DOOSNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_INHOUD = "AAAAAAAAAA";
    private static final String UPDATED_INHOUD = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KEYMAGAZIJNLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_KEYMAGAZIJNLOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DoosRepository doosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoosMockMvc;

    private Doos doos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doos createEntity(EntityManager em) {
        Doos doos = new Doos()
            .doosnummer(DEFAULT_DOOSNUMMER)
            .herkomst(DEFAULT_HERKOMST)
            .inhoud(DEFAULT_INHOUD)
            .key(DEFAULT_KEY)
            .keymagazijnlocatie(DEFAULT_KEYMAGAZIJNLOCATIE)
            .projectcd(DEFAULT_PROJECTCD);
        return doos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doos createUpdatedEntity(EntityManager em) {
        Doos doos = new Doos()
            .doosnummer(UPDATED_DOOSNUMMER)
            .herkomst(UPDATED_HERKOMST)
            .inhoud(UPDATED_INHOUD)
            .key(UPDATED_KEY)
            .keymagazijnlocatie(UPDATED_KEYMAGAZIJNLOCATIE)
            .projectcd(UPDATED_PROJECTCD);
        return doos;
    }

    @BeforeEach
    public void initTest() {
        doos = createEntity(em);
    }

    @Test
    @Transactional
    void createDoos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Doos
        var returnedDoos = om.readValue(
            restDoosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doos)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Doos.class
        );

        // Validate the Doos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDoosUpdatableFieldsEquals(returnedDoos, getPersistedDoos(returnedDoos));
    }

    @Test
    @Transactional
    void createDoosWithExistingId() throws Exception {
        // Create the Doos with an existing ID
        doos.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doos)))
            .andExpect(status().isBadRequest());

        // Validate the Doos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoos() throws Exception {
        // Initialize the database
        doosRepository.saveAndFlush(doos);

        // Get all the doosList
        restDoosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doos.getId().intValue())))
            .andExpect(jsonPath("$.[*].doosnummer").value(hasItem(DEFAULT_DOOSNUMMER)))
            .andExpect(jsonPath("$.[*].herkomst").value(hasItem(DEFAULT_HERKOMST)))
            .andExpect(jsonPath("$.[*].inhoud").value(hasItem(DEFAULT_INHOUD)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].keymagazijnlocatie").value(hasItem(DEFAULT_KEYMAGAZIJNLOCATIE)))
            .andExpect(jsonPath("$.[*].projectcd").value(hasItem(DEFAULT_PROJECTCD)));
    }

    @Test
    @Transactional
    void getDoos() throws Exception {
        // Initialize the database
        doosRepository.saveAndFlush(doos);

        // Get the doos
        restDoosMockMvc
            .perform(get(ENTITY_API_URL_ID, doos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doos.getId().intValue()))
            .andExpect(jsonPath("$.doosnummer").value(DEFAULT_DOOSNUMMER))
            .andExpect(jsonPath("$.herkomst").value(DEFAULT_HERKOMST))
            .andExpect(jsonPath("$.inhoud").value(DEFAULT_INHOUD))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.keymagazijnlocatie").value(DEFAULT_KEYMAGAZIJNLOCATIE))
            .andExpect(jsonPath("$.projectcd").value(DEFAULT_PROJECTCD));
    }

    @Test
    @Transactional
    void getNonExistingDoos() throws Exception {
        // Get the doos
        restDoosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoos() throws Exception {
        // Initialize the database
        doosRepository.saveAndFlush(doos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doos
        Doos updatedDoos = doosRepository.findById(doos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDoos are not directly saved in db
        em.detach(updatedDoos);
        updatedDoos
            .doosnummer(UPDATED_DOOSNUMMER)
            .herkomst(UPDATED_HERKOMST)
            .inhoud(UPDATED_INHOUD)
            .key(UPDATED_KEY)
            .keymagazijnlocatie(UPDATED_KEYMAGAZIJNLOCATIE)
            .projectcd(UPDATED_PROJECTCD);

        restDoosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDoos))
            )
            .andExpect(status().isOk());

        // Validate the Doos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDoosToMatchAllProperties(updatedDoos);
    }

    @Test
    @Transactional
    void putNonExistingDoos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doos.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoosMockMvc
            .perform(put(ENTITY_API_URL_ID, doos.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doos)))
            .andExpect(status().isBadRequest());

        // Validate the Doos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoosWithPatch() throws Exception {
        // Initialize the database
        doosRepository.saveAndFlush(doos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doos using partial update
        Doos partialUpdatedDoos = new Doos();
        partialUpdatedDoos.setId(doos.getId());

        partialUpdatedDoos.herkomst(UPDATED_HERKOMST).inhoud(UPDATED_INHOUD);

        restDoosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoos))
            )
            .andExpect(status().isOk());

        // Validate the Doos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoosUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDoos, doos), getPersistedDoos(doos));
    }

    @Test
    @Transactional
    void fullUpdateDoosWithPatch() throws Exception {
        // Initialize the database
        doosRepository.saveAndFlush(doos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doos using partial update
        Doos partialUpdatedDoos = new Doos();
        partialUpdatedDoos.setId(doos.getId());

        partialUpdatedDoos
            .doosnummer(UPDATED_DOOSNUMMER)
            .herkomst(UPDATED_HERKOMST)
            .inhoud(UPDATED_INHOUD)
            .key(UPDATED_KEY)
            .keymagazijnlocatie(UPDATED_KEYMAGAZIJNLOCATIE)
            .projectcd(UPDATED_PROJECTCD);

        restDoosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoos))
            )
            .andExpect(status().isOk());

        // Validate the Doos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoosUpdatableFieldsEquals(partialUpdatedDoos, getPersistedDoos(partialUpdatedDoos));
    }

    @Test
    @Transactional
    void patchNonExistingDoos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doos.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoosMockMvc
            .perform(patch(ENTITY_API_URL_ID, doos.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doos)))
            .andExpect(status().isBadRequest());

        // Validate the Doos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoos() throws Exception {
        // Initialize the database
        doosRepository.saveAndFlush(doos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the doos
        restDoosMockMvc
            .perform(delete(ENTITY_API_URL_ID, doos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return doosRepository.count();
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

    protected Doos getPersistedDoos(Doos doos) {
        return doosRepository.findById(doos.getId()).orElseThrow();
    }

    protected void assertPersistedDoosToMatchAllProperties(Doos expectedDoos) {
        assertDoosAllPropertiesEquals(expectedDoos, getPersistedDoos(expectedDoos));
    }

    protected void assertPersistedDoosToMatchUpdatableProperties(Doos expectedDoos) {
        assertDoosAllUpdatablePropertiesEquals(expectedDoos, getPersistedDoos(expectedDoos));
    }
}
