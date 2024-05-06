package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WerkorderAsserts.*;
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
import nl.ritense.demo.domain.Werkorder;
import nl.ritense.demo.repository.WerkorderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WerkorderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WerkorderResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_WERKORDERTYPE = "AAAAAAAAAA";
    private static final String UPDATED_WERKORDERTYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/werkorders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WerkorderRepository werkorderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWerkorderMockMvc;

    private Werkorder werkorder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Werkorder createEntity(EntityManager em) {
        Werkorder werkorder = new Werkorder()
            .code(DEFAULT_CODE)
            .documentnummer(DEFAULT_DOCUMENTNUMMER)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .werkordertype(DEFAULT_WERKORDERTYPE);
        return werkorder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Werkorder createUpdatedEntity(EntityManager em) {
        Werkorder werkorder = new Werkorder()
            .code(UPDATED_CODE)
            .documentnummer(UPDATED_DOCUMENTNUMMER)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .werkordertype(UPDATED_WERKORDERTYPE);
        return werkorder;
    }

    @BeforeEach
    public void initTest() {
        werkorder = createEntity(em);
    }

    @Test
    @Transactional
    void createWerkorder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Werkorder
        var returnedWerkorder = om.readValue(
            restWerkorderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkorder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Werkorder.class
        );

        // Validate the Werkorder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWerkorderUpdatableFieldsEquals(returnedWerkorder, getPersistedWerkorder(returnedWerkorder));
    }

    @Test
    @Transactional
    void createWerkorderWithExistingId() throws Exception {
        // Create the Werkorder with an existing ID
        werkorder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWerkorderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkorder)))
            .andExpect(status().isBadRequest());

        // Validate the Werkorder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWerkorders() throws Exception {
        // Initialize the database
        werkorderRepository.saveAndFlush(werkorder);

        // Get all the werkorderList
        restWerkorderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(werkorder.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].documentnummer").value(hasItem(DEFAULT_DOCUMENTNUMMER)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].werkordertype").value(hasItem(DEFAULT_WERKORDERTYPE)));
    }

    @Test
    @Transactional
    void getWerkorder() throws Exception {
        // Initialize the database
        werkorderRepository.saveAndFlush(werkorder);

        // Get the werkorder
        restWerkorderMockMvc
            .perform(get(ENTITY_API_URL_ID, werkorder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(werkorder.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.documentnummer").value(DEFAULT_DOCUMENTNUMMER))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.werkordertype").value(DEFAULT_WERKORDERTYPE));
    }

    @Test
    @Transactional
    void getNonExistingWerkorder() throws Exception {
        // Get the werkorder
        restWerkorderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWerkorder() throws Exception {
        // Initialize the database
        werkorderRepository.saveAndFlush(werkorder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkorder
        Werkorder updatedWerkorder = werkorderRepository.findById(werkorder.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWerkorder are not directly saved in db
        em.detach(updatedWerkorder);
        updatedWerkorder
            .code(UPDATED_CODE)
            .documentnummer(UPDATED_DOCUMENTNUMMER)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .werkordertype(UPDATED_WERKORDERTYPE);

        restWerkorderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWerkorder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWerkorder))
            )
            .andExpect(status().isOk());

        // Validate the Werkorder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWerkorderToMatchAllProperties(updatedWerkorder);
    }

    @Test
    @Transactional
    void putNonExistingWerkorder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkorder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWerkorderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, werkorder.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkorder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkorder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWerkorder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkorder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkorderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(werkorder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkorder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWerkorder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkorder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkorderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkorder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Werkorder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWerkorderWithPatch() throws Exception {
        // Initialize the database
        werkorderRepository.saveAndFlush(werkorder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkorder using partial update
        Werkorder partialUpdatedWerkorder = new Werkorder();
        partialUpdatedWerkorder.setId(werkorder.getId());

        partialUpdatedWerkorder
            .code(UPDATED_CODE)
            .documentnummer(UPDATED_DOCUMENTNUMMER)
            .naam(UPDATED_NAAM)
            .werkordertype(UPDATED_WERKORDERTYPE);

        restWerkorderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWerkorder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWerkorder))
            )
            .andExpect(status().isOk());

        // Validate the Werkorder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWerkorderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWerkorder, werkorder),
            getPersistedWerkorder(werkorder)
        );
    }

    @Test
    @Transactional
    void fullUpdateWerkorderWithPatch() throws Exception {
        // Initialize the database
        werkorderRepository.saveAndFlush(werkorder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkorder using partial update
        Werkorder partialUpdatedWerkorder = new Werkorder();
        partialUpdatedWerkorder.setId(werkorder.getId());

        partialUpdatedWerkorder
            .code(UPDATED_CODE)
            .documentnummer(UPDATED_DOCUMENTNUMMER)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .werkordertype(UPDATED_WERKORDERTYPE);

        restWerkorderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWerkorder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWerkorder))
            )
            .andExpect(status().isOk());

        // Validate the Werkorder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWerkorderUpdatableFieldsEquals(partialUpdatedWerkorder, getPersistedWerkorder(partialUpdatedWerkorder));
    }

    @Test
    @Transactional
    void patchNonExistingWerkorder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkorder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWerkorderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, werkorder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(werkorder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkorder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWerkorder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkorder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkorderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(werkorder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkorder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWerkorder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkorder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkorderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(werkorder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Werkorder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWerkorder() throws Exception {
        // Initialize the database
        werkorderRepository.saveAndFlush(werkorder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the werkorder
        restWerkorderMockMvc
            .perform(delete(ENTITY_API_URL_ID, werkorder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return werkorderRepository.count();
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

    protected Werkorder getPersistedWerkorder(Werkorder werkorder) {
        return werkorderRepository.findById(werkorder.getId()).orElseThrow();
    }

    protected void assertPersistedWerkorderToMatchAllProperties(Werkorder expectedWerkorder) {
        assertWerkorderAllPropertiesEquals(expectedWerkorder, getPersistedWerkorder(expectedWerkorder));
    }

    protected void assertPersistedWerkorderToMatchUpdatableProperties(Werkorder expectedWerkorder) {
        assertWerkorderAllUpdatablePropertiesEquals(expectedWerkorder, getPersistedWerkorder(expectedWerkorder));
    }
}
