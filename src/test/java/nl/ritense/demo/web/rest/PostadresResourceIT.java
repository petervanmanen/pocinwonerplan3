package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PostadresAsserts.*;
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
import nl.ritense.demo.domain.Postadres;
import nl.ritense.demo.repository.PostadresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PostadresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PostadresResourceIT {

    private static final String DEFAULT_POSTADRESTYPE = "AAAAAAAAAA";
    private static final String UPDATED_POSTADRESTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTBUSOFANTWOORDNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_POSTBUSOFANTWOORDNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODEPOSTADRES = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODEPOSTADRES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/postadres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PostadresRepository postadresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostadresMockMvc;

    private Postadres postadres;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Postadres createEntity(EntityManager em) {
        Postadres postadres = new Postadres()
            .postadrestype(DEFAULT_POSTADRESTYPE)
            .postbusofantwoordnummer(DEFAULT_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(DEFAULT_POSTCODEPOSTADRES);
        return postadres;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Postadres createUpdatedEntity(EntityManager em) {
        Postadres postadres = new Postadres()
            .postadrestype(UPDATED_POSTADRESTYPE)
            .postbusofantwoordnummer(UPDATED_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(UPDATED_POSTCODEPOSTADRES);
        return postadres;
    }

    @BeforeEach
    public void initTest() {
        postadres = createEntity(em);
    }

    @Test
    @Transactional
    void createPostadres() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Postadres
        var returnedPostadres = om.readValue(
            restPostadresMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postadres)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Postadres.class
        );

        // Validate the Postadres in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPostadresUpdatableFieldsEquals(returnedPostadres, getPersistedPostadres(returnedPostadres));
    }

    @Test
    @Transactional
    void createPostadresWithExistingId() throws Exception {
        // Create the Postadres with an existing ID
        postadres.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostadresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postadres)))
            .andExpect(status().isBadRequest());

        // Validate the Postadres in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPostadres() throws Exception {
        // Initialize the database
        postadresRepository.saveAndFlush(postadres);

        // Get all the postadresList
        restPostadresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postadres.getId().intValue())))
            .andExpect(jsonPath("$.[*].postadrestype").value(hasItem(DEFAULT_POSTADRESTYPE)))
            .andExpect(jsonPath("$.[*].postbusofantwoordnummer").value(hasItem(DEFAULT_POSTBUSOFANTWOORDNUMMER)))
            .andExpect(jsonPath("$.[*].postcodepostadres").value(hasItem(DEFAULT_POSTCODEPOSTADRES)));
    }

    @Test
    @Transactional
    void getPostadres() throws Exception {
        // Initialize the database
        postadresRepository.saveAndFlush(postadres);

        // Get the postadres
        restPostadresMockMvc
            .perform(get(ENTITY_API_URL_ID, postadres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postadres.getId().intValue()))
            .andExpect(jsonPath("$.postadrestype").value(DEFAULT_POSTADRESTYPE))
            .andExpect(jsonPath("$.postbusofantwoordnummer").value(DEFAULT_POSTBUSOFANTWOORDNUMMER))
            .andExpect(jsonPath("$.postcodepostadres").value(DEFAULT_POSTCODEPOSTADRES));
    }

    @Test
    @Transactional
    void getNonExistingPostadres() throws Exception {
        // Get the postadres
        restPostadresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPostadres() throws Exception {
        // Initialize the database
        postadresRepository.saveAndFlush(postadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the postadres
        Postadres updatedPostadres = postadresRepository.findById(postadres.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPostadres are not directly saved in db
        em.detach(updatedPostadres);
        updatedPostadres
            .postadrestype(UPDATED_POSTADRESTYPE)
            .postbusofantwoordnummer(UPDATED_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(UPDATED_POSTCODEPOSTADRES);

        restPostadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPostadres.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPostadres))
            )
            .andExpect(status().isOk());

        // Validate the Postadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPostadresToMatchAllProperties(updatedPostadres);
    }

    @Test
    @Transactional
    void putNonExistingPostadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postadres.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postadres.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(postadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostadresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(postadres)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Postadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostadresWithPatch() throws Exception {
        // Initialize the database
        postadresRepository.saveAndFlush(postadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the postadres using partial update
        Postadres partialUpdatedPostadres = new Postadres();
        partialUpdatedPostadres.setId(postadres.getId());

        partialUpdatedPostadres
            .postadrestype(UPDATED_POSTADRESTYPE)
            .postbusofantwoordnummer(UPDATED_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(UPDATED_POSTCODEPOSTADRES);

        restPostadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPostadres))
            )
            .andExpect(status().isOk());

        // Validate the Postadres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPostadresUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPostadres, postadres),
            getPersistedPostadres(postadres)
        );
    }

    @Test
    @Transactional
    void fullUpdatePostadresWithPatch() throws Exception {
        // Initialize the database
        postadresRepository.saveAndFlush(postadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the postadres using partial update
        Postadres partialUpdatedPostadres = new Postadres();
        partialUpdatedPostadres.setId(postadres.getId());

        partialUpdatedPostadres
            .postadrestype(UPDATED_POSTADRESTYPE)
            .postbusofantwoordnummer(UPDATED_POSTBUSOFANTWOORDNUMMER)
            .postcodepostadres(UPDATED_POSTCODEPOSTADRES);

        restPostadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPostadres))
            )
            .andExpect(status().isOk());

        // Validate the Postadres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPostadresUpdatableFieldsEquals(partialUpdatedPostadres, getPersistedPostadres(partialUpdatedPostadres));
    }

    @Test
    @Transactional
    void patchNonExistingPostadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postadres.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(postadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(postadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostadresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(postadres)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Postadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePostadres() throws Exception {
        // Initialize the database
        postadresRepository.saveAndFlush(postadres);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the postadres
        restPostadresMockMvc
            .perform(delete(ENTITY_API_URL_ID, postadres.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return postadresRepository.count();
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

    protected Postadres getPersistedPostadres(Postadres postadres) {
        return postadresRepository.findById(postadres.getId()).orElseThrow();
    }

    protected void assertPersistedPostadresToMatchAllProperties(Postadres expectedPostadres) {
        assertPostadresAllPropertiesEquals(expectedPostadres, getPersistedPostadres(expectedPostadres));
    }

    protected void assertPersistedPostadresToMatchUpdatableProperties(Postadres expectedPostadres) {
        assertPostadresAllUpdatablePropertiesEquals(expectedPostadres, getPersistedPostadres(expectedPostadres));
    }
}
