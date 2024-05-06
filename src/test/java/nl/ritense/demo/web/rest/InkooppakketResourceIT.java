package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InkooppakketAsserts.*;
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
import nl.ritense.demo.domain.Inkooppakket;
import nl.ritense.demo.repository.InkooppakketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InkooppakketResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InkooppakketResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inkooppakkets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InkooppakketRepository inkooppakketRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInkooppakketMockMvc;

    private Inkooppakket inkooppakket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inkooppakket createEntity(EntityManager em) {
        Inkooppakket inkooppakket = new Inkooppakket().code(DEFAULT_CODE).naam(DEFAULT_NAAM).type(DEFAULT_TYPE);
        return inkooppakket;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inkooppakket createUpdatedEntity(EntityManager em) {
        Inkooppakket inkooppakket = new Inkooppakket().code(UPDATED_CODE).naam(UPDATED_NAAM).type(UPDATED_TYPE);
        return inkooppakket;
    }

    @BeforeEach
    public void initTest() {
        inkooppakket = createEntity(em);
    }

    @Test
    @Transactional
    void createInkooppakket() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inkooppakket
        var returnedInkooppakket = om.readValue(
            restInkooppakketMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkooppakket)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inkooppakket.class
        );

        // Validate the Inkooppakket in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInkooppakketUpdatableFieldsEquals(returnedInkooppakket, getPersistedInkooppakket(returnedInkooppakket));
    }

    @Test
    @Transactional
    void createInkooppakketWithExistingId() throws Exception {
        // Create the Inkooppakket with an existing ID
        inkooppakket.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInkooppakketMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkooppakket)))
            .andExpect(status().isBadRequest());

        // Validate the Inkooppakket in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInkooppakkets() throws Exception {
        // Initialize the database
        inkooppakketRepository.saveAndFlush(inkooppakket);

        // Get all the inkooppakketList
        restInkooppakketMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inkooppakket.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getInkooppakket() throws Exception {
        // Initialize the database
        inkooppakketRepository.saveAndFlush(inkooppakket);

        // Get the inkooppakket
        restInkooppakketMockMvc
            .perform(get(ENTITY_API_URL_ID, inkooppakket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inkooppakket.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingInkooppakket() throws Exception {
        // Get the inkooppakket
        restInkooppakketMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInkooppakket() throws Exception {
        // Initialize the database
        inkooppakketRepository.saveAndFlush(inkooppakket);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkooppakket
        Inkooppakket updatedInkooppakket = inkooppakketRepository.findById(inkooppakket.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInkooppakket are not directly saved in db
        em.detach(updatedInkooppakket);
        updatedInkooppakket.code(UPDATED_CODE).naam(UPDATED_NAAM).type(UPDATED_TYPE);

        restInkooppakketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInkooppakket.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInkooppakket))
            )
            .andExpect(status().isOk());

        // Validate the Inkooppakket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInkooppakketToMatchAllProperties(updatedInkooppakket);
    }

    @Test
    @Transactional
    void putNonExistingInkooppakket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooppakket.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInkooppakketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inkooppakket.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inkooppakket))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkooppakket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInkooppakket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooppakket.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkooppakketMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inkooppakket))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkooppakket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInkooppakket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooppakket.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkooppakketMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkooppakket)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inkooppakket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInkooppakketWithPatch() throws Exception {
        // Initialize the database
        inkooppakketRepository.saveAndFlush(inkooppakket);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkooppakket using partial update
        Inkooppakket partialUpdatedInkooppakket = new Inkooppakket();
        partialUpdatedInkooppakket.setId(inkooppakket.getId());

        partialUpdatedInkooppakket.naam(UPDATED_NAAM).type(UPDATED_TYPE);

        restInkooppakketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInkooppakket.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInkooppakket))
            )
            .andExpect(status().isOk());

        // Validate the Inkooppakket in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInkooppakketUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInkooppakket, inkooppakket),
            getPersistedInkooppakket(inkooppakket)
        );
    }

    @Test
    @Transactional
    void fullUpdateInkooppakketWithPatch() throws Exception {
        // Initialize the database
        inkooppakketRepository.saveAndFlush(inkooppakket);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkooppakket using partial update
        Inkooppakket partialUpdatedInkooppakket = new Inkooppakket();
        partialUpdatedInkooppakket.setId(inkooppakket.getId());

        partialUpdatedInkooppakket.code(UPDATED_CODE).naam(UPDATED_NAAM).type(UPDATED_TYPE);

        restInkooppakketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInkooppakket.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInkooppakket))
            )
            .andExpect(status().isOk());

        // Validate the Inkooppakket in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInkooppakketUpdatableFieldsEquals(partialUpdatedInkooppakket, getPersistedInkooppakket(partialUpdatedInkooppakket));
    }

    @Test
    @Transactional
    void patchNonExistingInkooppakket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooppakket.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInkooppakketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inkooppakket.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inkooppakket))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkooppakket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInkooppakket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooppakket.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkooppakketMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inkooppakket))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkooppakket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInkooppakket() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooppakket.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkooppakketMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inkooppakket)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inkooppakket in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInkooppakket() throws Exception {
        // Initialize the database
        inkooppakketRepository.saveAndFlush(inkooppakket);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inkooppakket
        restInkooppakketMockMvc
            .perform(delete(ENTITY_API_URL_ID, inkooppakket.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inkooppakketRepository.count();
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

    protected Inkooppakket getPersistedInkooppakket(Inkooppakket inkooppakket) {
        return inkooppakketRepository.findById(inkooppakket.getId()).orElseThrow();
    }

    protected void assertPersistedInkooppakketToMatchAllProperties(Inkooppakket expectedInkooppakket) {
        assertInkooppakketAllPropertiesEquals(expectedInkooppakket, getPersistedInkooppakket(expectedInkooppakket));
    }

    protected void assertPersistedInkooppakketToMatchUpdatableProperties(Inkooppakket expectedInkooppakket) {
        assertInkooppakketAllUpdatablePropertiesEquals(expectedInkooppakket, getPersistedInkooppakket(expectedInkooppakket));
    }
}
