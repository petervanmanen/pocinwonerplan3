package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SelectietabelaanbestedingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Selectietabelaanbesteding;
import nl.ritense.demo.repository.SelectietabelaanbestedingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SelectietabelaanbestedingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SelectietabelaanbestedingResourceIT {

    private static final String DEFAULT_AANBESTEDINGSOORT = "AAAAAAAAAA";
    private static final String UPDATED_AANBESTEDINGSOORT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_DREMPELBEDRAGTOT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DREMPELBEDRAGTOT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DREMPELBEDRAGVANAF = new BigDecimal(1);
    private static final BigDecimal UPDATED_DREMPELBEDRAGVANAF = new BigDecimal(2);

    private static final String DEFAULT_OPDRACHTCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_OPDRACHTCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_OPENBAAR = "AAAAAAAAAA";
    private static final String UPDATED_OPENBAAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/selectietabelaanbestedings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SelectietabelaanbestedingRepository selectietabelaanbestedingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSelectietabelaanbestedingMockMvc;

    private Selectietabelaanbesteding selectietabelaanbesteding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Selectietabelaanbesteding createEntity(EntityManager em) {
        Selectietabelaanbesteding selectietabelaanbesteding = new Selectietabelaanbesteding()
            .aanbestedingsoort(DEFAULT_AANBESTEDINGSOORT)
            .drempelbedragtot(DEFAULT_DREMPELBEDRAGTOT)
            .drempelbedragvanaf(DEFAULT_DREMPELBEDRAGVANAF)
            .opdrachtcategorie(DEFAULT_OPDRACHTCATEGORIE)
            .openbaar(DEFAULT_OPENBAAR);
        return selectietabelaanbesteding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Selectietabelaanbesteding createUpdatedEntity(EntityManager em) {
        Selectietabelaanbesteding selectietabelaanbesteding = new Selectietabelaanbesteding()
            .aanbestedingsoort(UPDATED_AANBESTEDINGSOORT)
            .drempelbedragtot(UPDATED_DREMPELBEDRAGTOT)
            .drempelbedragvanaf(UPDATED_DREMPELBEDRAGVANAF)
            .opdrachtcategorie(UPDATED_OPDRACHTCATEGORIE)
            .openbaar(UPDATED_OPENBAAR);
        return selectietabelaanbesteding;
    }

    @BeforeEach
    public void initTest() {
        selectietabelaanbesteding = createEntity(em);
    }

    @Test
    @Transactional
    void createSelectietabelaanbesteding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Selectietabelaanbesteding
        var returnedSelectietabelaanbesteding = om.readValue(
            restSelectietabelaanbestedingMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(selectietabelaanbesteding))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Selectietabelaanbesteding.class
        );

        // Validate the Selectietabelaanbesteding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSelectietabelaanbestedingUpdatableFieldsEquals(
            returnedSelectietabelaanbesteding,
            getPersistedSelectietabelaanbesteding(returnedSelectietabelaanbesteding)
        );
    }

    @Test
    @Transactional
    void createSelectietabelaanbestedingWithExistingId() throws Exception {
        // Create the Selectietabelaanbesteding with an existing ID
        selectietabelaanbesteding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelectietabelaanbestedingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(selectietabelaanbesteding)))
            .andExpect(status().isBadRequest());

        // Validate the Selectietabelaanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSelectietabelaanbestedings() throws Exception {
        // Initialize the database
        selectietabelaanbestedingRepository.saveAndFlush(selectietabelaanbesteding);

        // Get all the selectietabelaanbestedingList
        restSelectietabelaanbestedingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selectietabelaanbesteding.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanbestedingsoort").value(hasItem(DEFAULT_AANBESTEDINGSOORT)))
            .andExpect(jsonPath("$.[*].drempelbedragtot").value(hasItem(sameNumber(DEFAULT_DREMPELBEDRAGTOT))))
            .andExpect(jsonPath("$.[*].drempelbedragvanaf").value(hasItem(sameNumber(DEFAULT_DREMPELBEDRAGVANAF))))
            .andExpect(jsonPath("$.[*].opdrachtcategorie").value(hasItem(DEFAULT_OPDRACHTCATEGORIE)))
            .andExpect(jsonPath("$.[*].openbaar").value(hasItem(DEFAULT_OPENBAAR)));
    }

    @Test
    @Transactional
    void getSelectietabelaanbesteding() throws Exception {
        // Initialize the database
        selectietabelaanbestedingRepository.saveAndFlush(selectietabelaanbesteding);

        // Get the selectietabelaanbesteding
        restSelectietabelaanbestedingMockMvc
            .perform(get(ENTITY_API_URL_ID, selectietabelaanbesteding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(selectietabelaanbesteding.getId().intValue()))
            .andExpect(jsonPath("$.aanbestedingsoort").value(DEFAULT_AANBESTEDINGSOORT))
            .andExpect(jsonPath("$.drempelbedragtot").value(sameNumber(DEFAULT_DREMPELBEDRAGTOT)))
            .andExpect(jsonPath("$.drempelbedragvanaf").value(sameNumber(DEFAULT_DREMPELBEDRAGVANAF)))
            .andExpect(jsonPath("$.opdrachtcategorie").value(DEFAULT_OPDRACHTCATEGORIE))
            .andExpect(jsonPath("$.openbaar").value(DEFAULT_OPENBAAR));
    }

    @Test
    @Transactional
    void getNonExistingSelectietabelaanbesteding() throws Exception {
        // Get the selectietabelaanbesteding
        restSelectietabelaanbestedingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSelectietabelaanbesteding() throws Exception {
        // Initialize the database
        selectietabelaanbestedingRepository.saveAndFlush(selectietabelaanbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the selectietabelaanbesteding
        Selectietabelaanbesteding updatedSelectietabelaanbesteding = selectietabelaanbestedingRepository
            .findById(selectietabelaanbesteding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSelectietabelaanbesteding are not directly saved in db
        em.detach(updatedSelectietabelaanbesteding);
        updatedSelectietabelaanbesteding
            .aanbestedingsoort(UPDATED_AANBESTEDINGSOORT)
            .drempelbedragtot(UPDATED_DREMPELBEDRAGTOT)
            .drempelbedragvanaf(UPDATED_DREMPELBEDRAGVANAF)
            .opdrachtcategorie(UPDATED_OPDRACHTCATEGORIE)
            .openbaar(UPDATED_OPENBAAR);

        restSelectietabelaanbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSelectietabelaanbesteding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSelectietabelaanbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Selectietabelaanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSelectietabelaanbestedingToMatchAllProperties(updatedSelectietabelaanbesteding);
    }

    @Test
    @Transactional
    void putNonExistingSelectietabelaanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        selectietabelaanbesteding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSelectietabelaanbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, selectietabelaanbesteding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(selectietabelaanbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selectietabelaanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSelectietabelaanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        selectietabelaanbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSelectietabelaanbestedingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(selectietabelaanbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selectietabelaanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSelectietabelaanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        selectietabelaanbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSelectietabelaanbestedingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(selectietabelaanbesteding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Selectietabelaanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSelectietabelaanbestedingWithPatch() throws Exception {
        // Initialize the database
        selectietabelaanbestedingRepository.saveAndFlush(selectietabelaanbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the selectietabelaanbesteding using partial update
        Selectietabelaanbesteding partialUpdatedSelectietabelaanbesteding = new Selectietabelaanbesteding();
        partialUpdatedSelectietabelaanbesteding.setId(selectietabelaanbesteding.getId());

        partialUpdatedSelectietabelaanbesteding.drempelbedragvanaf(UPDATED_DREMPELBEDRAGVANAF).openbaar(UPDATED_OPENBAAR);

        restSelectietabelaanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSelectietabelaanbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSelectietabelaanbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Selectietabelaanbesteding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSelectietabelaanbestedingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSelectietabelaanbesteding, selectietabelaanbesteding),
            getPersistedSelectietabelaanbesteding(selectietabelaanbesteding)
        );
    }

    @Test
    @Transactional
    void fullUpdateSelectietabelaanbestedingWithPatch() throws Exception {
        // Initialize the database
        selectietabelaanbestedingRepository.saveAndFlush(selectietabelaanbesteding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the selectietabelaanbesteding using partial update
        Selectietabelaanbesteding partialUpdatedSelectietabelaanbesteding = new Selectietabelaanbesteding();
        partialUpdatedSelectietabelaanbesteding.setId(selectietabelaanbesteding.getId());

        partialUpdatedSelectietabelaanbesteding
            .aanbestedingsoort(UPDATED_AANBESTEDINGSOORT)
            .drempelbedragtot(UPDATED_DREMPELBEDRAGTOT)
            .drempelbedragvanaf(UPDATED_DREMPELBEDRAGVANAF)
            .opdrachtcategorie(UPDATED_OPDRACHTCATEGORIE)
            .openbaar(UPDATED_OPENBAAR);

        restSelectietabelaanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSelectietabelaanbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSelectietabelaanbesteding))
            )
            .andExpect(status().isOk());

        // Validate the Selectietabelaanbesteding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSelectietabelaanbestedingUpdatableFieldsEquals(
            partialUpdatedSelectietabelaanbesteding,
            getPersistedSelectietabelaanbesteding(partialUpdatedSelectietabelaanbesteding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSelectietabelaanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        selectietabelaanbesteding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSelectietabelaanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, selectietabelaanbesteding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(selectietabelaanbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selectietabelaanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSelectietabelaanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        selectietabelaanbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSelectietabelaanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(selectietabelaanbesteding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selectietabelaanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSelectietabelaanbesteding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        selectietabelaanbesteding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSelectietabelaanbestedingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(selectietabelaanbesteding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Selectietabelaanbesteding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSelectietabelaanbesteding() throws Exception {
        // Initialize the database
        selectietabelaanbestedingRepository.saveAndFlush(selectietabelaanbesteding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the selectietabelaanbesteding
        restSelectietabelaanbestedingMockMvc
            .perform(delete(ENTITY_API_URL_ID, selectietabelaanbesteding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return selectietabelaanbestedingRepository.count();
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

    protected Selectietabelaanbesteding getPersistedSelectietabelaanbesteding(Selectietabelaanbesteding selectietabelaanbesteding) {
        return selectietabelaanbestedingRepository.findById(selectietabelaanbesteding.getId()).orElseThrow();
    }

    protected void assertPersistedSelectietabelaanbestedingToMatchAllProperties(
        Selectietabelaanbesteding expectedSelectietabelaanbesteding
    ) {
        assertSelectietabelaanbestedingAllPropertiesEquals(
            expectedSelectietabelaanbesteding,
            getPersistedSelectietabelaanbesteding(expectedSelectietabelaanbesteding)
        );
    }

    protected void assertPersistedSelectietabelaanbestedingToMatchUpdatableProperties(
        Selectietabelaanbesteding expectedSelectietabelaanbesteding
    ) {
        assertSelectietabelaanbestedingAllUpdatablePropertiesEquals(
            expectedSelectietabelaanbesteding,
            getPersistedSelectietabelaanbesteding(expectedSelectietabelaanbesteding)
        );
    }
}
