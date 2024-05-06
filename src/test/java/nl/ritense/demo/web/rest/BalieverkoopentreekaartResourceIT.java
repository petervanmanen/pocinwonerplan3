package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BalieverkoopentreekaartAsserts.*;
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
import nl.ritense.demo.domain.Balieverkoopentreekaart;
import nl.ritense.demo.repository.BalieverkoopentreekaartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BalieverkoopentreekaartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BalieverkoopentreekaartResourceIT {

    private static final String DEFAULT_DATUMEINDEGELDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_GEBRUIKTOP = "AAAAAAAAAA";
    private static final String UPDATED_GEBRUIKTOP = "BBBBBBBBBB";

    private static final String DEFAULT_RONDLEIDING = "AAAAAAAAAA";
    private static final String UPDATED_RONDLEIDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/balieverkoopentreekaarts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BalieverkoopentreekaartRepository balieverkoopentreekaartRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBalieverkoopentreekaartMockMvc;

    private Balieverkoopentreekaart balieverkoopentreekaart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Balieverkoopentreekaart createEntity(EntityManager em) {
        Balieverkoopentreekaart balieverkoopentreekaart = new Balieverkoopentreekaart()
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumstart(DEFAULT_DATUMSTART)
            .gebruiktop(DEFAULT_GEBRUIKTOP)
            .rondleiding(DEFAULT_RONDLEIDING);
        return balieverkoopentreekaart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Balieverkoopentreekaart createUpdatedEntity(EntityManager em) {
        Balieverkoopentreekaart balieverkoopentreekaart = new Balieverkoopentreekaart()
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstart(UPDATED_DATUMSTART)
            .gebruiktop(UPDATED_GEBRUIKTOP)
            .rondleiding(UPDATED_RONDLEIDING);
        return balieverkoopentreekaart;
    }

    @BeforeEach
    public void initTest() {
        balieverkoopentreekaart = createEntity(em);
    }

    @Test
    @Transactional
    void createBalieverkoopentreekaart() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Balieverkoopentreekaart
        var returnedBalieverkoopentreekaart = om.readValue(
            restBalieverkoopentreekaartMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieverkoopentreekaart))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Balieverkoopentreekaart.class
        );

        // Validate the Balieverkoopentreekaart in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBalieverkoopentreekaartUpdatableFieldsEquals(
            returnedBalieverkoopentreekaart,
            getPersistedBalieverkoopentreekaart(returnedBalieverkoopentreekaart)
        );
    }

    @Test
    @Transactional
    void createBalieverkoopentreekaartWithExistingId() throws Exception {
        // Create the Balieverkoopentreekaart with an existing ID
        balieverkoopentreekaart.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalieverkoopentreekaartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieverkoopentreekaart)))
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoopentreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBalieverkoopentreekaarts() throws Exception {
        // Initialize the database
        balieverkoopentreekaartRepository.saveAndFlush(balieverkoopentreekaart);

        // Get all the balieverkoopentreekaartList
        restBalieverkoopentreekaartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balieverkoopentreekaart.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].gebruiktop").value(hasItem(DEFAULT_GEBRUIKTOP)))
            .andExpect(jsonPath("$.[*].rondleiding").value(hasItem(DEFAULT_RONDLEIDING)));
    }

    @Test
    @Transactional
    void getBalieverkoopentreekaart() throws Exception {
        // Initialize the database
        balieverkoopentreekaartRepository.saveAndFlush(balieverkoopentreekaart);

        // Get the balieverkoopentreekaart
        restBalieverkoopentreekaartMockMvc
            .perform(get(ENTITY_API_URL_ID, balieverkoopentreekaart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(balieverkoopentreekaart.getId().intValue()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.gebruiktop").value(DEFAULT_GEBRUIKTOP))
            .andExpect(jsonPath("$.rondleiding").value(DEFAULT_RONDLEIDING));
    }

    @Test
    @Transactional
    void getNonExistingBalieverkoopentreekaart() throws Exception {
        // Get the balieverkoopentreekaart
        restBalieverkoopentreekaartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBalieverkoopentreekaart() throws Exception {
        // Initialize the database
        balieverkoopentreekaartRepository.saveAndFlush(balieverkoopentreekaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieverkoopentreekaart
        Balieverkoopentreekaart updatedBalieverkoopentreekaart = balieverkoopentreekaartRepository
            .findById(balieverkoopentreekaart.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedBalieverkoopentreekaart are not directly saved in db
        em.detach(updatedBalieverkoopentreekaart);
        updatedBalieverkoopentreekaart
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstart(UPDATED_DATUMSTART)
            .gebruiktop(UPDATED_GEBRUIKTOP)
            .rondleiding(UPDATED_RONDLEIDING);

        restBalieverkoopentreekaartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBalieverkoopentreekaart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBalieverkoopentreekaart))
            )
            .andExpect(status().isOk());

        // Validate the Balieverkoopentreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBalieverkoopentreekaartToMatchAllProperties(updatedBalieverkoopentreekaart);
    }

    @Test
    @Transactional
    void putNonExistingBalieverkoopentreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoopentreekaart.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalieverkoopentreekaartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, balieverkoopentreekaart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(balieverkoopentreekaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoopentreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBalieverkoopentreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoopentreekaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieverkoopentreekaartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(balieverkoopentreekaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoopentreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBalieverkoopentreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoopentreekaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieverkoopentreekaartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(balieverkoopentreekaart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Balieverkoopentreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBalieverkoopentreekaartWithPatch() throws Exception {
        // Initialize the database
        balieverkoopentreekaartRepository.saveAndFlush(balieverkoopentreekaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieverkoopentreekaart using partial update
        Balieverkoopentreekaart partialUpdatedBalieverkoopentreekaart = new Balieverkoopentreekaart();
        partialUpdatedBalieverkoopentreekaart.setId(balieverkoopentreekaart.getId());

        partialUpdatedBalieverkoopentreekaart.datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restBalieverkoopentreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalieverkoopentreekaart.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBalieverkoopentreekaart))
            )
            .andExpect(status().isOk());

        // Validate the Balieverkoopentreekaart in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBalieverkoopentreekaartUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBalieverkoopentreekaart, balieverkoopentreekaart),
            getPersistedBalieverkoopentreekaart(balieverkoopentreekaart)
        );
    }

    @Test
    @Transactional
    void fullUpdateBalieverkoopentreekaartWithPatch() throws Exception {
        // Initialize the database
        balieverkoopentreekaartRepository.saveAndFlush(balieverkoopentreekaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the balieverkoopentreekaart using partial update
        Balieverkoopentreekaart partialUpdatedBalieverkoopentreekaart = new Balieverkoopentreekaart();
        partialUpdatedBalieverkoopentreekaart.setId(balieverkoopentreekaart.getId());

        partialUpdatedBalieverkoopentreekaart
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstart(UPDATED_DATUMSTART)
            .gebruiktop(UPDATED_GEBRUIKTOP)
            .rondleiding(UPDATED_RONDLEIDING);

        restBalieverkoopentreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalieverkoopentreekaart.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBalieverkoopentreekaart))
            )
            .andExpect(status().isOk());

        // Validate the Balieverkoopentreekaart in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBalieverkoopentreekaartUpdatableFieldsEquals(
            partialUpdatedBalieverkoopentreekaart,
            getPersistedBalieverkoopentreekaart(partialUpdatedBalieverkoopentreekaart)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBalieverkoopentreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoopentreekaart.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalieverkoopentreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, balieverkoopentreekaart.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(balieverkoopentreekaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoopentreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBalieverkoopentreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoopentreekaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieverkoopentreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(balieverkoopentreekaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balieverkoopentreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBalieverkoopentreekaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        balieverkoopentreekaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalieverkoopentreekaartMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(balieverkoopentreekaart))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Balieverkoopentreekaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBalieverkoopentreekaart() throws Exception {
        // Initialize the database
        balieverkoopentreekaartRepository.saveAndFlush(balieverkoopentreekaart);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the balieverkoopentreekaart
        restBalieverkoopentreekaartMockMvc
            .perform(delete(ENTITY_API_URL_ID, balieverkoopentreekaart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return balieverkoopentreekaartRepository.count();
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

    protected Balieverkoopentreekaart getPersistedBalieverkoopentreekaart(Balieverkoopentreekaart balieverkoopentreekaart) {
        return balieverkoopentreekaartRepository.findById(balieverkoopentreekaart.getId()).orElseThrow();
    }

    protected void assertPersistedBalieverkoopentreekaartToMatchAllProperties(Balieverkoopentreekaart expectedBalieverkoopentreekaart) {
        assertBalieverkoopentreekaartAllPropertiesEquals(
            expectedBalieverkoopentreekaart,
            getPersistedBalieverkoopentreekaart(expectedBalieverkoopentreekaart)
        );
    }

    protected void assertPersistedBalieverkoopentreekaartToMatchUpdatableProperties(
        Balieverkoopentreekaart expectedBalieverkoopentreekaart
    ) {
        assertBalieverkoopentreekaartAllUpdatablePropertiesEquals(
            expectedBalieverkoopentreekaart,
            getPersistedBalieverkoopentreekaart(expectedBalieverkoopentreekaart)
        );
    }
}
