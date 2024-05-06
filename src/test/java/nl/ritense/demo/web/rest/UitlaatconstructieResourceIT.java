package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.UitlaatconstructieAsserts.*;
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
import nl.ritense.demo.domain.Uitlaatconstructie;
import nl.ritense.demo.repository.UitlaatconstructieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UitlaatconstructieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UitlaatconstructieResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_WATEROBJECT = "AAAAAAAAAA";
    private static final String UPDATED_WATEROBJECT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/uitlaatconstructies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UitlaatconstructieRepository uitlaatconstructieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUitlaatconstructieMockMvc;

    private Uitlaatconstructie uitlaatconstructie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitlaatconstructie createEntity(EntityManager em) {
        Uitlaatconstructie uitlaatconstructie = new Uitlaatconstructie().type(DEFAULT_TYPE).waterobject(DEFAULT_WATEROBJECT);
        return uitlaatconstructie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uitlaatconstructie createUpdatedEntity(EntityManager em) {
        Uitlaatconstructie uitlaatconstructie = new Uitlaatconstructie().type(UPDATED_TYPE).waterobject(UPDATED_WATEROBJECT);
        return uitlaatconstructie;
    }

    @BeforeEach
    public void initTest() {
        uitlaatconstructie = createEntity(em);
    }

    @Test
    @Transactional
    void createUitlaatconstructie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Uitlaatconstructie
        var returnedUitlaatconstructie = om.readValue(
            restUitlaatconstructieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitlaatconstructie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Uitlaatconstructie.class
        );

        // Validate the Uitlaatconstructie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUitlaatconstructieUpdatableFieldsEquals(
            returnedUitlaatconstructie,
            getPersistedUitlaatconstructie(returnedUitlaatconstructie)
        );
    }

    @Test
    @Transactional
    void createUitlaatconstructieWithExistingId() throws Exception {
        // Create the Uitlaatconstructie with an existing ID
        uitlaatconstructie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUitlaatconstructieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitlaatconstructie)))
            .andExpect(status().isBadRequest());

        // Validate the Uitlaatconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUitlaatconstructies() throws Exception {
        // Initialize the database
        uitlaatconstructieRepository.saveAndFlush(uitlaatconstructie);

        // Get all the uitlaatconstructieList
        restUitlaatconstructieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uitlaatconstructie.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].waterobject").value(hasItem(DEFAULT_WATEROBJECT)));
    }

    @Test
    @Transactional
    void getUitlaatconstructie() throws Exception {
        // Initialize the database
        uitlaatconstructieRepository.saveAndFlush(uitlaatconstructie);

        // Get the uitlaatconstructie
        restUitlaatconstructieMockMvc
            .perform(get(ENTITY_API_URL_ID, uitlaatconstructie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uitlaatconstructie.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.waterobject").value(DEFAULT_WATEROBJECT));
    }

    @Test
    @Transactional
    void getNonExistingUitlaatconstructie() throws Exception {
        // Get the uitlaatconstructie
        restUitlaatconstructieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUitlaatconstructie() throws Exception {
        // Initialize the database
        uitlaatconstructieRepository.saveAndFlush(uitlaatconstructie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitlaatconstructie
        Uitlaatconstructie updatedUitlaatconstructie = uitlaatconstructieRepository.findById(uitlaatconstructie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUitlaatconstructie are not directly saved in db
        em.detach(updatedUitlaatconstructie);
        updatedUitlaatconstructie.type(UPDATED_TYPE).waterobject(UPDATED_WATEROBJECT);

        restUitlaatconstructieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUitlaatconstructie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUitlaatconstructie))
            )
            .andExpect(status().isOk());

        // Validate the Uitlaatconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUitlaatconstructieToMatchAllProperties(updatedUitlaatconstructie);
    }

    @Test
    @Transactional
    void putNonExistingUitlaatconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitlaatconstructie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitlaatconstructieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uitlaatconstructie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitlaatconstructie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitlaatconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUitlaatconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitlaatconstructie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitlaatconstructieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uitlaatconstructie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitlaatconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUitlaatconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitlaatconstructie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitlaatconstructieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uitlaatconstructie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitlaatconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUitlaatconstructieWithPatch() throws Exception {
        // Initialize the database
        uitlaatconstructieRepository.saveAndFlush(uitlaatconstructie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitlaatconstructie using partial update
        Uitlaatconstructie partialUpdatedUitlaatconstructie = new Uitlaatconstructie();
        partialUpdatedUitlaatconstructie.setId(uitlaatconstructie.getId());

        partialUpdatedUitlaatconstructie.waterobject(UPDATED_WATEROBJECT);

        restUitlaatconstructieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitlaatconstructie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitlaatconstructie))
            )
            .andExpect(status().isOk());

        // Validate the Uitlaatconstructie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitlaatconstructieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUitlaatconstructie, uitlaatconstructie),
            getPersistedUitlaatconstructie(uitlaatconstructie)
        );
    }

    @Test
    @Transactional
    void fullUpdateUitlaatconstructieWithPatch() throws Exception {
        // Initialize the database
        uitlaatconstructieRepository.saveAndFlush(uitlaatconstructie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uitlaatconstructie using partial update
        Uitlaatconstructie partialUpdatedUitlaatconstructie = new Uitlaatconstructie();
        partialUpdatedUitlaatconstructie.setId(uitlaatconstructie.getId());

        partialUpdatedUitlaatconstructie.type(UPDATED_TYPE).waterobject(UPDATED_WATEROBJECT);

        restUitlaatconstructieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUitlaatconstructie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUitlaatconstructie))
            )
            .andExpect(status().isOk());

        // Validate the Uitlaatconstructie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUitlaatconstructieUpdatableFieldsEquals(
            partialUpdatedUitlaatconstructie,
            getPersistedUitlaatconstructie(partialUpdatedUitlaatconstructie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUitlaatconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitlaatconstructie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUitlaatconstructieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uitlaatconstructie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitlaatconstructie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitlaatconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUitlaatconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitlaatconstructie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitlaatconstructieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uitlaatconstructie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uitlaatconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUitlaatconstructie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uitlaatconstructie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUitlaatconstructieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uitlaatconstructie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uitlaatconstructie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUitlaatconstructie() throws Exception {
        // Initialize the database
        uitlaatconstructieRepository.saveAndFlush(uitlaatconstructie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uitlaatconstructie
        restUitlaatconstructieMockMvc
            .perform(delete(ENTITY_API_URL_ID, uitlaatconstructie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uitlaatconstructieRepository.count();
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

    protected Uitlaatconstructie getPersistedUitlaatconstructie(Uitlaatconstructie uitlaatconstructie) {
        return uitlaatconstructieRepository.findById(uitlaatconstructie.getId()).orElseThrow();
    }

    protected void assertPersistedUitlaatconstructieToMatchAllProperties(Uitlaatconstructie expectedUitlaatconstructie) {
        assertUitlaatconstructieAllPropertiesEquals(expectedUitlaatconstructie, getPersistedUitlaatconstructie(expectedUitlaatconstructie));
    }

    protected void assertPersistedUitlaatconstructieToMatchUpdatableProperties(Uitlaatconstructie expectedUitlaatconstructie) {
        assertUitlaatconstructieAllUpdatablePropertiesEquals(
            expectedUitlaatconstructie,
            getPersistedUitlaatconstructie(expectedUitlaatconstructie)
        );
    }
}
